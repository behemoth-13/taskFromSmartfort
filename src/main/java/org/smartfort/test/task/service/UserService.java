package org.smartfort.test.task.service;

import org.smartfort.test.task.model.User;

import java.util.List;

public interface UserService {
    void createUser(User user);
    User getUserById(Integer id);
    List<User> getUsers();
    void updateUser(User user);
    void deleteUserById(Integer id);
}
