package ua.nure.providence.controllers.zk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.nure.providence.controllers.BaseController;
import ua.nure.providence.daos.DoorDAO;
import ua.nure.providence.daos.RoomDAO;
import ua.nure.providence.daos.UserDAO;
import ua.nure.providence.dtos.doors.DoorDTO;
import ua.nure.providence.dtos.doors.DoorUpdateDTO;
import ua.nure.providence.exceptions.rest.RestException;
import ua.nure.providence.models.authentication.User;
import ua.nure.providence.models.business.DoorConfiguration;
import ua.nure.providence.models.business.DoorLocker;
import ua.nure.providence.services.redis.IRedisRepository;
import ua.nure.providence.services.xml.XmlConverter;
import ua.nure.providence.utils.auth.LoginToken;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * Created by Providence Team on 07.05.2017.
 */
@RestController
@Transactional
@RequestMapping("doors")
public class ZKController extends BaseController {

    private static ReentrantLock lock = new ReentrantLock();
    @Autowired
    private DoorDAO dao;

    @Autowired
    private RoomDAO roomDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private IRedisRepository<String, String> redisRepository;

    private static final String MS_BUILD_COMMAND = "cmd.exe /c  msbuild iot\\ZKService\\ZKService.sln /t:Build /p:Configuration=Release";

    @RequestMapping(value = "{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<DoorDTO> getDoorLocker(@PathVariable(name = "uuid") String uuid) {
        LoginToken token = (LoginToken) SecurityContextHolder.getContext().getAuthentication();

        if (!this.dao.exists(uuid, token.getAuthenticatedUser())) {
            throw new RestException(HttpStatus.NOT_FOUND, 404008, "Specified door is not found");
        }

        DoorLocker locker = dao.get(uuid, token.getAuthenticatedUser());
        return ResponseEntity.ok(new DoorDTO().convert(locker));
    }

    @RequestMapping(value = "{uuid}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<DoorDTO> updateDoorLocker(@PathVariable(name = "uuid") String uuid,
                                                    @RequestBody() DoorUpdateDTO dto) {
        if (dto.getConfigurations() == null) {
            throw new RestException(HttpStatus.BAD_REQUEST, 400011, "The configuration is not specified");
        }

        LoginToken token = (LoginToken) SecurityContextHolder.getContext().getAuthentication();

        if (!this.dao.exists(uuid, token.getAuthenticatedUser())) {
            throw new RestException(HttpStatus.NOT_FOUND, 404008, "Specified door is not found");
        }

        DoorLocker locker = dao.get(uuid, token.getAuthenticatedUser());
        dao.deleteDoorConfigurations(locker);
        locker.setConfiguration(dto.getConfigurations().stream().map(doorConfigurationDTO -> {
            DoorConfiguration configuration = new DoorConfiguration();
            doorConfigurationDTO.fromDTO(configuration);
            configuration.setLocker(locker);
            return configuration;
        }).collect(Collectors.toList()));

        if (dto.getRoomUuid() != null) {
            locker.setRoom(roomDAO.get(dto.getRoomUuid()));
        } else {
            throw new RestException(HttpStatus.BAD_REQUEST, 400008, "The room is not specified");
        }

        dao.update(locker);
        return ResponseEntity.ok(new DoorDTO().convert(locker));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<DoorDTO> updateDoorLocker(@RequestBody() DoorUpdateDTO dto) {
        DoorLocker locker = new DoorLocker();

        if (dto.getConfigurations() == null) {
            throw new RestException(HttpStatus.BAD_REQUEST, 400011, "The configuration is not specified");
        }

        locker.setConfiguration(dto.getConfigurations().stream().map(doorConfigurationDTO -> {
            DoorConfiguration configuration = new DoorConfiguration();
            doorConfigurationDTO.fromDTO(configuration);
            configuration.setLocker(locker);
            return configuration;
        }).collect(Collectors.toList()));

        if (dto.getRoomUuid() != null) {
            locker.setRoom(roomDAO.get(dto.getRoomUuid()));
        } else {
            throw new RestException(HttpStatus.BAD_REQUEST, 400008, "The room is not specified");
        }

        dao.insert(locker);
        return ResponseEntity.ok(new DoorDTO().convert(locker));
    }

    @RequestMapping(value = "{uuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Resource> deleteDoorLocker(@PathVariable(name = "uuid") String uuid) {
        LoginToken token = (LoginToken) SecurityContextHolder.getContext().getAuthentication();

        if (!this.dao.exists(uuid, token.getAuthenticatedUser())) {
            throw new RestException(HttpStatus.NOT_FOUND, 404008, "Specified door is not found");
        }

        DoorLocker locker = dao.get(uuid, token.getAuthenticatedUser());
        dao.delete(locker);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/windows/service", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getXMLConfiguration(@RequestParam(value = "token", required = true) String token) throws Exception {
        lock.lock();
        String uuid = redisRepository.get(token);
        if (uuid == null) {
            throw new RestException(HttpStatus.UNAUTHORIZED, 401001, "Token not found!");
        }

        redisRepository.refreshExpirationTime(token);
        User user = userDAO.get(uuid);
        if (user == null) {
            throw new RestException(HttpStatus.UNAUTHORIZED, 401001, "User not found!");
        }

        List<DoorLocker> locker = dao.getAllDoorConfigurations(user.getAccount(),
                Integer.MAX_VALUE, 0);

        locker.forEach(doorLocker -> {
            try {
                new XmlConverter<DoorLocker>().convert(doorLocker, DoorLocker.class);
            } catch (JAXBException | FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        Process p = Runtime.getRuntime().exec(MS_BUILD_COMMAND);
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
        while ((stdInput.readLine()) != null) {
        }

        File file = new File("iot\\ZKService\\ZKAccessInstaller\\Debug\\ZKAccessInstaller.msi");
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        lock.unlock();
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"ZKAccessInstaller.msi\"")
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/windows-installer-database"))
                .body(resource);
    }
}
