package jzt.erp.store.service;

import jzt.erp.store.model.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Map;


public interface UserService {
    public User getUserById(int userId);

    boolean insertSelective(User record);
    User addUser2(User user);


    int updateUser(User user);

    int GetUserCount(String user_name);

    int GetUserCount2(Map<String, Object> params);

    int GetUserCount3(String user_name, String password );

    int GetUserCount4(User user);

    int GetUserCount5(@Param("user") User user,@Param("page") PageParams page);

    List<User> GetUserList(String user_name, String password, RowBounds r);
}