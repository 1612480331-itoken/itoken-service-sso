package com.example.itokenservicesso.entity;


import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private String id;
    private String name;
    private String password;
}
