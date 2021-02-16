package jzt.erp.store.controller;

import jzt.erp.store.model.PageParams;
import jzt.erp.store.model.User;
import jzt.erp.store.service.UserServiceImpl;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @RequestMapping("/addUser")
    @ResponseBody
    //http://localhost:8083/user/addUser

    public User addUser(){
        //int userId = 3337;
        User user = new User();
        user.setAge(20);
       // user.setId(222);
        user.setUserName("pan");
        user.setPassword("tttt");
        this.userService.insertSelective(user);
        //this.userService.addUser2(user);
        return user;
    }

    /**
     * 根据用户名获取用户总数
     *
     * @param
     * @return
     */
    //http://localhost:8083/user/GetUserCount?user_name=a
    @RequestMapping(value = "/GetUserCount", method = RequestMethod.GET)
    @ResponseBody
    public int GetUserCount(@RequestParam(value = "user_name", required = true) String user_name) {
        int usercount = this.userService.GetUserCount(user_name);
        return usercount;
    }

    @RequestMapping(value = "/GetUserCount2", method = RequestMethod.GET)
    @ResponseBody
    //http://localhost:8083/user/GetUserCount2?user_name=s&password=pw
    //多参数查询
    public int GetUserCount2(@RequestParam(value = "user_name", required = true) String user_name,
                            @RequestParam(value = "password", required = true) String password
    ) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("user_name",user_name);
        params.put("password",password);
        int usercount = this.userService.GetUserCount2(params);

        //另一种实现,指定参数，不过参数太多就有点不爽
        usercount = this.userService.GetUserCount3(user_name,password);

        User user= new User();
        user.setUserName(user_name);
        user.setPassword(password);
        usercount = this.userService.GetUserCount4(user);

        PageParams page = new PageParams();
        page.setStart(0);
        page.setLimit(2);
        usercount = this.userService.GetUserCount5(user,page);
        return usercount;
    }

    @RequestMapping(value = "/GetUserList", method = RequestMethod.GET)
    @ResponseBody
    //http://localhost:8083/user/GetUserList?user_name=s&password=pw
    //多参数查询
    public List<User> GetUserList(@RequestParam(value = "user_name", required = true) String user_name,
                             @RequestParam(value = "password", required = true) String password
    ) {
        //mybatis 自带分页,数据量大就不行了
        //RowBounds page = new RowBounds(0,10);
        RowBounds page = new RowBounds(0,1);
        List<User> list= this.userService.GetUserList(user_name,password,page);
        return list;
    }

    //http://localhost:8083/user/showUser?userId=1
    @RequestMapping("/showUser")
    @ResponseBody
    public User getUserById(@RequestParam(value = "userId", required = true) int userid){
        User user = this.userService.getUserById(userid);
        return user;
    }

    /**
     * 根据用户名获取用户信息，包括从库的地址信息
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/updateuser", method = RequestMethod.GET)
    @ResponseBody
    public int updateuser() {

        int userId = 1;
        User user2 = this.userService.getUserById(userId);
        user2.setUserName("name2");
        return userService.updateUser(user2);
    }


    @PostMapping("/updateuser2")
    public int insertUser(@RequestBody User user) {
        return userService.updateUser(user);
    }







}
