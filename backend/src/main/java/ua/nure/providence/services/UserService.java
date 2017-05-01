package ua.nure.providence.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.providence.daos.UserDAO;
import ua.nure.providence.models.authentication.User;

@Service
public class UserService {
    @Autowired
    private UserDAO userDao;

    public void saveUser(User user) {
        userDao.insert(user);
    }

    public User getUserByEmail(String email) {
        return userDao.getByEmail(email);
    }

    public User getUserById(String uuid) {
        return userDao.get(uuid);
    }

    public void deleteUser(User user) {
        userDao.delete(user);
    }

    public void updateUser(User user) {
        userDao.update(user);
    }

    public void setUserDao(UserDAO userDao) {
        this.userDao = userDao;
    }
}
