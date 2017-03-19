package ua.nure.providence.controllers.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.nure.providence.controllers.BaseController;
import ua.nure.providence.daos.UserDAO;
import ua.nure.providence.dtos.user.FullUserDTO;
import ua.nure.providence.dtos.user.SimpleUserDTO;
import ua.nure.providence.exceptions.rest.RestException;
import ua.nure.providence.models.User;

@RestController
public class UserController extends BaseController {

    @Autowired
    private UserDAO userDAO;

    @RequestMapping(value = "/user/{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<SimpleUserDTO> readUser(@PathVariable(name = "uuid", required = true) String uuid) {
        return new ResponseEntity<>(new SimpleUserDTO().convert(userDAO.get(uuid)), HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{uuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteUser(@PathVariable(name = "uuid", required = true) String uuid) {
        User user = userDAO.get(uuid);
        if (user != null) {
            new UserDAO().delete(user);
        } else {
            throw new RestException(HttpStatus.NOT_FOUND, 404001, "Entity not found");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{uuid}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<FullUserDTO> updateUser(@PathVariable(name = "uuid", required = true) String uuid,
                                                  @RequestBody FullUserDTO fullUser) {
        User user = userDAO.get(uuid);
        if (user != null) {
            user.setName(fullUser.getName());
            user.setSurname(fullUser.getSurname());
            user.setMiddleName(fullUser.getMiddleName());
            user.setBirthday(fullUser.getBirthday());
            user.setAddress(fullUser.getAddress());
            user.setEmail(fullUser.getEmail());
            user.setPhoneNumber(fullUser.getPhoneNumber());
            user.setRole(fullUser.getRole());
            user.setShifts(fullUser.getShifts());
            user.setPassword(fullUser.getPassword());

            userDAO.update(user);

            return new ResponseEntity<>(new FullUserDTO().convert(user), HttpStatus.OK);
        } else {
            throw new RestException(HttpStatus.NOT_FOUND, 404001, "Entity not found");
        }
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<FullUserDTO> createUser(@RequestBody FullUserDTO fullUser) {
        User user = new User();
        user.setName(fullUser.getName());
        user.setSurname(fullUser.getSurname());
        user.setMiddleName(fullUser.getMiddleName());
        user.setBirthday(fullUser.getBirthday());
        user.setAddress(fullUser.getAddress());
        user.setEmail(fullUser.getEmail());
        user.setPhoneNumber(fullUser.getPhoneNumber());
        user.setRole(fullUser.getRole());
        user.setPassword(fullUser.getPassword());
        userDAO.insert(user);

        return new ResponseEntity<>(new FullUserDTO().convert(user), HttpStatus.OK);
    }

}
