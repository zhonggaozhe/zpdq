package com.zgz.cpdq.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfo implements Serializable {

    private Integer id;

    private String userName;

    private String headUrl;

    public UserInfo(Integer id, String userName, String headUrl) {
        this.id = id;
        this.userName = userName;
        this.headUrl = headUrl;
    }

    public UserInfo() {
    }
}
