package com.example.itokenservicesso.serviceImpl.provider;

import com.example.itokenservicesso.entity.User;
import com.example.itokenservicesso.mapper.UserMapper;
import com.example.itokenservicesso.service.consumer.RedisService;
import com.example.itokenservicesso.service.provider.LoginService;
import com.example.itokenservicesso.utils.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.IOException;

@Service
public class LoginServiceImpl implements LoginService {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public User login(String loginCode, String plantPassword) {
        //从缓存中获取登录用户的数据
        String json = redisService.get(loginCode);

        User user = null;
        //如果缓存中没有数据,从数据库取数据
        if (json == null) {
            user = userMapper.selectAll(loginCode);
            String passwordMd5 = DigestUtils.md5DigestAsHex(plantPassword.getBytes());
            if (user != null && passwordMd5.equals(user.getPassword())) {
                //登录成功，刷新缓存
                try {
                    redisService.put(loginCode, JsonUtil.objectToString(user), 60 * 60 * 24);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return user;
            } else {
                return null;
            }
        }
        //如果缓存中有数据
        else {
            try {
                user = JsonUtil.stringToObject(json, User.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return user;
    }
}
