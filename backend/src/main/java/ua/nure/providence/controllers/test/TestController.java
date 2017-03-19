package ua.nure.providence.controllers.test;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.nure.providence.exceptions.rest.RestException;
import ua.nure.providence.models.User;
import ua.nure.providence.services.UserService;
import ua.nure.providence.utils.MD5;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RestController
public class TestController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/test")
    @ResponseBody
    public ResponseEntity<?> test() {
        throw new RestException(HttpStatus.BAD_REQUEST, 400001, "You sucked");
    }

    @RequestMapping(value = "/test/{testId}", method = RequestMethod.GET)
    public void tes1(@PathVariable(name = "testId") Integer testId) throws IOException {
    }

    @Transactional
    @RequestMapping("/blah")
    public ResponseEntity someMethod(HttpServletRequest req) throws NoSuchAlgorithmException {
        User user = new User();
        user.setSurname("petrov");
        user.setName("petr");
        user.setBirthday(new DateTime(1, 2, 3, 4, 5));
        user.setEmail("qwe");
        user.setPassword(MD5.encrypt("rty"));
        userService.saveUser(user);
        return new ResponseEntity(HttpStatus.OK);
    }


}
