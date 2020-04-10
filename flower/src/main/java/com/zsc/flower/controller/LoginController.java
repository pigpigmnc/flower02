package com.zsc.flower.controller;

import com.alibaba.fastjson.JSONObject;
import com.zsc.flower.service.TokenService;
import com.zsc.flower.service.UsersService;
import model.entity.Users;
import model.result.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@CrossOrigin
@RestController
@RequestMapping(value = "/entrance")
public class LoginController {

    @Autowired
    UsersService usersService;
    @Autowired
    TokenService tokenService;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseResult login(@RequestParam String username, @RequestParam String password) {
        ResponseResult result = new ResponseResult();
        JSONObject jsonObject = new JSONObject();
        Users user = usersService.findUser(username,password);
        result.setMsg(false);
        if (user == null) {
            jsonObject.put("message", "登录失败,用户不存在");
            result.setData(jsonObject);
            return result;
        } else {
            if (!user.getPassword().equals(user.getPassword())) {
                jsonObject.put("message", "登录失败,密码错误");
                result.setData(jsonObject);
                return result;
            } else {
                String token = (String) redisTemplate.opsForValue().get(user.getId() + "token");
                if (token == null) {
                    token = tokenService.getToken(user);
                    redisTemplate.opsForValue().set(user.getId() + "token", token);
                    stringRedisTemplate.expire(user.getId() + "token", 30, TimeUnit.MINUTES);
                }
                jsonObject.put("token", token);
                jsonObject.put("user",user);
                result.setMsg(true);
                result.setData(jsonObject);
                return result;
            }
        }
    }

    @RequestMapping(value = "/checkoutusername", method = RequestMethod.GET)
    public ResponseResult checkoutusername(@RequestParam("username")String username)
    {
        ResponseResult result = new ResponseResult();
        Users users=usersService.findUserByUsername(username);
        if(users==null)
        {
            result.setMsg(true);
            return result;
        }
        else
        {
            result.setMsg(false);
            return result;
        }

    }
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ResponseResult register(@RequestParam("username")String username, @RequestParam("password")String password,
                                  @RequestParam("phone")String phone, @RequestParam("email")String email)
    {
        ResponseResult result = new ResponseResult();
        try{
            Users users=usersService.findUserByUsername(username);
            if(users==null)
            {
                Users user=new Users();
                user.setUsername(username);
                user.setPassword(password);
                user.setEmail(email);
                user.setPhone(phone);
                user.setRole(1);
                user.setStatus(1);
                user.setRegisterDate(new Date());
                usersService.findAddUser(user);
                result.setMsg(true);
                return result;
            }
            else
            {
                result.setMsg(false);
                return result;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result.setMsg(false);
            result.setData("系统出现错误!");
            return result;
        }
    }
    @RequestMapping(value = "/checkLogin", method = RequestMethod.GET)
    public ResponseResult checkLogin(HttpServletRequest request){
        String token = request.getHeader("token");
        ResponseResult result = new ResponseResult();
        if(token==null){
            result.setMsg(false);
            return result;
        }
        else{
            result.setMsg(true);
            return result;
        }
    }

}
