package jzt.erp.store.model;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

public interface UserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User addUser2(User user);


    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int GetUserCount(String user_name);

    int GetUserCount2(Map<String, Object> params);

    int GetUserCount3(@Param("user_name") String user_name, @Param("password") String password);

    int GetUserCount4(User user);

    int GetUserCount5(@Param("user") User user,@Param("page") PageParams page);

    List<User> GetUserList(@Param("user_name") String user_name, @Param("password") String password, RowBounds r);

}