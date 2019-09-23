package com.example.itokenservicesso.service.provider;


import com.example.itokenservicesso.entity.User;

public interface LoginService {


    /**
     * 登录  提供者
     * @param loginCode
     * @param plantPassword
     * @return
     */
    public User login(String loginCode,String plantPassword);
}
