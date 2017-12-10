package org.smartfort.test.task.dao;

import org.apache.ibatis.session.SqlSession;
import org.smartfort.test.task.dao.mappers.UserMapper;
import org.smartfort.test.task.model.User;

import java.util.List;

public class UserDAO {

    private static UserDAO instance = new UserDAO();

    public static UserDAO getInstance() {
        return instance;
    }

    public void createUser(User user){
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        mapper.insertUser(user);
        session.commit();
        session.close();
    }

    public User getUserById(Integer id) {
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        User user = mapper.selectUserById(id);
        session.commit();
        session.close();
        return user;
    }

    public List<User> getUsers() {
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        List<User> users = mapper.selectUsers();
        session.close();
        return users;
    }

    public void updateUser(User user){
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        mapper.updateUser(user);
        session.commit();
        session.close();
    }

    public void deleteUserById(Integer id){
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        mapper.deleteUserById(id);
        session.commit();
        session.close();
    }
}
