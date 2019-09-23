package com.example.itokenservicesso.mapper;


import com.example.itokenservicesso.entity.User;

public interface UserMapper {
    User selectAll(String id);
    int insert(User user);
}
