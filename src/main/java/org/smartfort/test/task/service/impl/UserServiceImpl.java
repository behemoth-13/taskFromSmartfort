package org.smartfort.test.task.service.impl;

import org.smartfort.test.task.dao.UserDAO;
import org.smartfort.test.task.model.User;
import org.smartfort.test.task.service.UserService;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserServiceImpl implements UserService{

    private static UserDAO userDAO= UserDAO.getInstance();

    private static UserService instance = new UserServiceImpl();
    public static UserService getInstance() {
        return instance;
    }

    public void createUser(User user){
        validateUser(user);
        userDAO.createUser(user);
    }

    public User getUserById(Integer id) {
        if (id < 1) {
            throw new IllegalArgumentException("id cannot be less than 1. expected " + id);
        }
        return userDAO.getUserById(id);
    }

    public List<User> getUsers() {
        return userDAO.getUsers();
    }

    public void updateUser(User user){
        if (user.getId() < 1) {
            throw new IllegalArgumentException("id cannot be less than 1. expected " + user.getId());
        }
        validateUser(user);
        userDAO.updateUser(user);
    }

    public void deleteUserById(Integer id){
        if (id < 1) {
            throw new IllegalArgumentException("id cannot be less than 1. expected " + id);
        }
        userDAO.deleteUserById(id);
    }

    private void validateUser(User user) {
        String firstName = user.getFirstName();
        if (firstName.length() > 45 || firstName.length() == 0) {
            throw new IllegalArgumentException("firstName length must be less than 45 and more than 0. expected " + firstName.length());
        }
        String lastName = user.getLastName();
        if (lastName.length() > 45 || lastName.length() == 0) {
            throw new IllegalArgumentException("lastName length must be less than 45 and more than 0. expected " + lastName.length());
        }
        Pattern p = Pattern.compile("[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}");
        Matcher m = p.matcher(user.getEmail());
        if (!m.matches()) {
            throw new IllegalArgumentException("email is not valid. expected " + user.getEmail());
        }
        if ((new Date().getTime() - user.getDateOfBirth().getTime()) < 0) {
            throw new IllegalArgumentException("dateOfBirth cannot be later than now. expected " + user.getDateOfBirth());
        }
    }
}
