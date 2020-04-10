package com.zsc.flower.service;

import model.entity.Users;
import org.springframework.stereotype.Service;

@Service
public interface TokenService {

    String getToken(Users userInfo);

    String getAPIToken(Users userInfo);

}
