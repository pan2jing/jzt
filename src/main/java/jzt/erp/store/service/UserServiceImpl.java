package jzt.erp.store.service;

import jzt.erp.store.model.PageParams;
import jzt.erp.store.model.User;
import jzt.erp.store.model.UserDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("UserService")
public class UserServiceImpl {
    @Resource
    private UserDao userDao;


    public User getUserById(int userId) {
        return userDao.selectByPrimaryKey(userId);
    }

    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public boolean insertSelective(User record){
        boolean result = false;
        try {
            userDao.insertSelective(record);
            userDao.addUser2(record);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("发生异常"+e);
        }

        return result;
    }

    public User addUser2(User user)
    {
        boolean result = false;
        try {

            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    public int updateUser(User user) {
        try {
            return userDao.updateByPrimaryKey(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int GetUserCount(String user_name)
    {
        try {
            return userDao.GetUserCount(user_name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int GetUserCount2(Map<String, Object> params)
    {
        try {
            return userDao.GetUserCount2(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int GetUserCount3(String user_name, String password)
    {
        try {
            int i = userDao.GetUserCount3(user_name, password);
            return i;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int GetUserCount4(User user)
    {
        try {
            return userDao.GetUserCount4(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int GetUserCount5(@Param("user") User user,@Param("page") PageParams page)
    {
        try {
            return userDao.GetUserCount5(user,page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<User> GetUserList(String user_name, String password,RowBounds rowBounds)
    {
        try {

            return userDao.GetUserList(user_name, password,rowBounds);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
