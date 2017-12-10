package org.smartfort.test.task.dao.mappers;

import org.apache.ibatis.annotations.*;
import org.smartfort.test.task.model.User;

import java.util.List;

public interface UserMapper {
    @Insert("INSERT INTO user(first_name,"
            + "last_name, email, date_of_birth) VALUES"
            + "(#{firstName}, #{lastName},"
            + " #{email}, #{dateOfBirth})")
    @Options(useGeneratedKeys=true, flushCache= Options.FlushCachePolicy.TRUE, keyColumn="id")
    void insertUser(User user);

    @Select("SELECT id as id, first_name as firstName, last_name as lastName, "
            + "email as email, date_of_birth as dateOfBirth "
            + "FROM user WHERE id = #{id}")
    User selectUserById(Integer id);

    @Select("SELECT * FROM user")
    @Results({
            @Result(property="firstName", column ="first_name" ),
            @Result(property="lastName", column ="last_name" ),
            @Result(property="dateOfBirth", column ="date_of_birth" ),
    })
    List<User> selectUsers();

    @Update("UPDATE user SET first_name = #{firstName}, last_name = #{lastName}, "
            + "email = #{email}, date_of_birth = #{dateOfBirth} WHERE id = #{id}")
    void updateUser(User user);

    @Delete("DELETE FROM user WHERE id =#{id}")
    void deleteUserById(Integer id);
}
