package com.zsc.flower.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsc.flower.service.TokenService;
import com.zsc.flower.service.UsersService;
import com.zsc.flower.tag.UserLoginToken;
import com.zsc.flower.utils.token.TokenUtil;
import model.entity.Users;
import model.entity.WebCts;
import model.result.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/u")
public class UsersController {
    @Autowired
    UsersService usersService;
    @Autowired
    TokenService tokenService;

    @GetMapping("/getAllUsers")
    public ResponseResult getAllUsers(@RequestParam(value = "page", defaultValue = "1") int page,
                                      @RequestParam(value = "size", defaultValue = "20") int size) {
        PageInfo<Users> pageInfo = usersService.findAllUsers(page, size);
        ResponseResult result = new ResponseResult();
        result.setMsg(true);
        result.setData(pageInfo);
        return result;
    }

    @PostMapping("/getAddUser")//插入用get响应
    public ResponseResult getAddUser(@RequestBody Users user) {
        user.setRegisterDate(new Date());
        user.setStatus(WebCts.ACTIVE);//注册默认激活
        user.setRole(WebCts.ROLE_USER);//默认普通用户
        ResponseResult result = new ResponseResult();
        result.setMsg(false);
        if (usersService.findAddUser(user) == 1){
            result.setMsg(true);
            return result;
        }
        else
            return result;
    }

    @GetMapping("/getAll")
    public ResponseResult getAll(@RequestParam(defaultValue = "1", required = true, value = "pn") Integer pn) {
        int pageSize = 10;
        PageHelper.startPage(pn, pageSize);
        PageInfo<Users> pageInfo = usersService.findAllUsers(pn, pageSize);
        ResponseResult result = new ResponseResult();
        result.setMsg(true);
        result.setData(pageInfo);
        return result;
    }

    @RequestMapping(value = "/getUserById", method = RequestMethod.GET)
    public ResponseResult getUserById(long id) {
        Users user = usersService.findUserById(id);
        ResponseResult result = new ResponseResult();
        result.setMsg(true);
        result.setData(user);
        return result;
    }

    //前台用户个人资料修改
    @RequestMapping(value = "/modifyItself", method = RequestMethod.POST)
    public ResponseResult modifyItself(Users user) {
        ResponseResult result = new ResponseResult();
        result.setMsg(false);
        if (usersService.findModifyItself(user) == 1){
            result.setMsg(true);
            return result;
        }
        else
            return result;
    }

    //后台编辑用户信息
    @RequestMapping(value = "/modifyUser", method = RequestMethod.GET)
    public ResponseResult modifyUser(Users user) {
        long id = user.getId();
        Users newUser = usersService.findUserById(id);
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setPhone(user.getPhone());
        newUser.setEmail(user.getEmail());
        newUser.setAddress(user.getAddress());
        ResponseResult result = new ResponseResult();
        result.setMsg(false);
        if (usersService.findModifyItself(newUser) == 1){
            result.setMsg(true);
            return result;
        }
        else
            return result;
    }


    //激活或者停用用户
    @RequestMapping(value = "/changeUserStatus", method = RequestMethod.GET)
    public ResponseResult changeUserStatus(@RequestParam("id") long id) {
        int status = usersService.findUserStatusById(id);
        ResponseResult result = new ResponseResult();
        if (status == 0)
            usersService.findChangeUserStatus(id, 1);
        switch (status) {
            case 1:
                usersService.findChangeUserStatus(id, 0);
                break;
            case 0:
                usersService.findChangeUserStatus(id, 1);
                break;
            default:
                break;
        }
        result.setMsg(true);
        return result;
    }

    @RequestMapping(value = "/listUserByRegisterDate", method = RequestMethod.GET)
    //根据用户注册时间，分页返回5条数据
    public ResponseResult listUserByRegisterDate(@RequestParam(defaultValue = "1", required = true, value = "pn") Integer pn) {
        int pageSize = 5;
        PageHelper.startPage(pn, pageSize);
        PageInfo<Users> pageInfo = usersService.findUserByRegisterDate(pn, pageSize);
        ResponseResult result = new ResponseResult();
        result.setMsg(true);
        result.setData(pageInfo);
        return result;
    }

    @UserLoginToken
    @PostMapping("/alterPersonalMsg")
    public ResponseResult updateUser(@RequestParam("phone") String phone, @RequestParam("email") String email,
                                   @RequestParam("address") String address, HttpServletRequest request) {
        Users users = usersService.findUserById(Long.parseLong(TokenUtil.getTokenUserId()));
        System.out.println("before");
        users.setPhone(phone);
        System.out.println("test");
        users.setEmail(email);
        users.setAddress(address);
        int id = usersService.findModifyItself(users);
        ResponseResult result = new ResponseResult();
        result.setMsg(true);
        result.setData("修改成功");
        return result;

    }

    @UserLoginToken
    @PostMapping("/alterPassword")
    public ResponseResult updatePassword(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword, HttpServletRequest request) {
        ResponseResult result = new ResponseResult();
        result.setMsg(false);
        try {
            Users users = usersService.findUserById(Long.parseLong(TokenUtil.getTokenUserId()));
            if (users.getPassword().equals(oldPassword)) {
                users.setPassword(newPassword);
                usersService.findModifyItself(users);
                result.setMsg(true);
                result.setData("修改成功");
                return result;
            } else {
                result.setData("旧密码不正确");
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setData("修改失败，系统错误!");
            return result;
        }
    }

    @UserLoginToken
    @PostMapping("/alterUsername")
    public ResponseResult updateUsername(@RequestParam("username") String username, HttpServletRequest request) {
        ResponseResult result = new ResponseResult();
        result.setMsg(false);
        try {
            Users name = usersService.findUserByUsername(username);
            if (name == null) {
                Users users = usersService.findUserById(Long.parseLong(TokenUtil.getTokenUserId()));
                users.setUsername(username);
                int id = usersService.findModifyItself(users);
                result.setMsg(true);
                result.setData("修改成功");
                return result;
            } else {
                result.setData("用户名重复");
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setData("修改失败，系统错误!");
            return result;
        }
    }


    @UserLoginToken
    @GetMapping("/getPersonalMsg")
    public ResponseResult getUserMsg() {
        ResponseResult result = new ResponseResult();
        result.setMsg(false);
        String jwt = TokenUtil.getTokenUserId();
        Users user;
        if (!jwt.equals("null")) {
            user = usersService.findUserById(Long.parseLong(jwt));
            result.setMsg(true);
            result.setData(user);
            return result;
        }else{
            return result;
        }
    }


}

