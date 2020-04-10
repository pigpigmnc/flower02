package com.zsc.flower.service;

import com.github.pagehelper.PageInfo;
import model.entity.Users;
import org.springframework.stereotype.Service;

@Service
public interface UsersService {

    public Users findUser(String username, String password);

    public PageInfo<Users> findAllUsers(int page, int size);

    public int findAddUser(Users user);

    public Users findUserById(long id);

    public int findModifyItself(Users user);

    public int findUserStatusById(long id);

    public void findChangeUserStatus(long id, int status);

    public PageInfo<Users> findUserByRegisterDate(int page, int size);

    public Users findUserByUsername(String username);
}
