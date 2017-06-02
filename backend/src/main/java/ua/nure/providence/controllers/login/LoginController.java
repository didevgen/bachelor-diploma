package ua.nure.providence.controllers.login;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ua.nure.providence.controllers.BaseController;
import ua.nure.providence.daos.DeviceDAO;
import ua.nure.providence.daos.UserDAO;
import ua.nure.providence.dtos.auth.TokenDTO;
import ua.nure.providence.dtos.login.LoginDTO;
import ua.nure.providence.dtos.login.LogoutDTO;
import ua.nure.providence.dtos.user.UserDTO;
import ua.nure.providence.exceptions.rest.RestException;
import ua.nure.providence.models.authentication.User;
import ua.nure.providence.services.AuthToken;
import ua.nure.providence.services.oauth.IdTokenVerifierAndParser;
import ua.nure.providence.services.redis.IRedisRepository;
import ua.nure.providence.utils.MD5;
import ua.nure.providence.utils.auth.LoginToken;
import ua.nure.providence.utils.auth.TokenGenerator;

import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Providence Team on 19.03.2017.
 */
@RestController
public class LoginController extends BaseController {

    @Autowired
    private UserDAO dao;

    @Autowired
    private DeviceDAO deviceDAO;

    @Autowired
    private Environment env;

    @Autowired
    private IRedisRepository<String, String> redisRepository;

    @Autowired
    private TokenGenerator tokenGenerator;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<UserDTO> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) throws NoSuchAlgorithmException {
        User user = dao.getByEmailAndPassword(loginDTO.getEmail(), MD5.encrypt(loginDTO.getPassword()))
                .orElseThrow(() ->
                        new RestException(HttpStatus.NOT_FOUND, 404001, "Specified user not found"));
        long tokenLifetime = 3600 * 1000;

        AuthToken authToken = new AuthToken(tokenGenerator.issueToken(user.getUuid(), tokenLifetime),
                user.getUuid());

        SecurityContextHolder.getContext()
                .setAuthentication(new LoginToken(user.getEmail(), user.getPassword(),
                        authToken, user));

        response.setHeader(env.getProperty("token.header"), authToken.getTokenValue());
        response.setHeader(env.getProperty("token.expire.header"), String.valueOf(System.currentTimeMillis() + tokenLifetime * 1000));
        return ResponseEntity.ok(new UserDTO().convert(user));
    }

    @RequestMapping(value = "/verifyToken", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<UserDTO> authorizeGoogle(@RequestBody TokenDTO tokenDTO, HttpServletResponse response) throws Exception {
        GoogleIdToken.Payload payLoad;
        try {
            payLoad = IdTokenVerifierAndParser.getPayload(env.getProperty("security.oauth2.client.clientId"), tokenDTO.getToken());
        } catch (Exception ex) {
            throw new RestException(HttpStatus.UNAUTHORIZED, 401002, "Invalid token was provided");
        }
        String email = payLoad.getEmail();
        User user = dao.getByEmail(email);

        if (user == null) {
            throw new RestException(HttpStatus.NOT_FOUND, 404001, "Specified user not found");
        }

        redisRepository.insert(tokenDTO.getToken(), user.getUuid(), payLoad.getExpirationTimeSeconds() * 1000 - System.currentTimeMillis());
        AuthToken authToken = new AuthToken(tokenDTO.getToken(),
                user.getUuid());

        SecurityContextHolder.getContext()
                .setAuthentication(new LoginToken(user.getEmail(), user.getPassword(),
                        authToken, user));
        response.setHeader(env.getProperty("token.header"), authToken.getTokenValue());
        response.setHeader(env.getProperty("token.expire.header"), String.valueOf(payLoad.getExpirationTimeSeconds() * 1000));
        return ResponseEntity.ok(new UserDTO().convert(user));
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity logout(@RequestBody LogoutDTO logoutDTO) {
        LoginToken token = (LoginToken) SecurityContextHolder.getContext().getAuthentication();
        if (logoutDTO.getSubscriptionKey() != null) {
            deviceDAO.deleteSubscribedDevice(logoutDTO.getSubscriptionKey());
        }
        redisRepository.delete(token.getAuthToken().getTokenValue());
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
