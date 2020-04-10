package com.zsc.flower.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsc.flower.dao.UsersDao;
import com.zsc.flower.service.UsersService;
import model.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("usersService")
public class UsersServiceImpl implements UsersService {
    @Autowired
    UsersDao usersDao;

    @Override
    public Users findUser(String username, String password) {
        return usersDao.selectUser(username,password);
    }

    @Override
    public PageInfo<Users> findAllUsers(int page, int size) {
        PageHelper.startPage(page,size);
        PageHelper.orderBy("id desc");//userId从大到小排列
        PageInfo<Users> pageInfo=new PageInfo<>(usersDao.selectAllUsers());
        return pageInfo;
    }

    @Override
    public int findAddUser(Users user) {
        return usersDao.selectAddUser(user);
    }

    @Override
    public Users findUserById(long id) {
        return usersDao.selectUserById(id);
    }

    @Override
    public int findModifyItself(Users user) {
        return usersDao.selectModifyItself(user);
    }

    @Override
    public int findUserStatusById(long id) {
        return usersDao.selectUserStatusById(id);
    }

    @Override
    public void findChangeUserStatus(long id, int status) {
        usersDao.selectChangeUserStatus(id,status);
    }

    @Override
    public PageInfo<Users> findUserByRegisterDate(int page, int size) {
        PageHelper.startPage(page,size);
        PageHelper.orderBy("registerDate desc");
        PageInfo<Users> pageInfo=new PageInfo<>(usersDao.selectAllUsers());
        return pageInfo;
    }

    @Override
    public Users findUserByUsername(String username) {
        return usersDao.selectUserByUsername(username);
    }

}
