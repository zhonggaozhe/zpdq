package com.zgz.cpdq.service;

import com.zgz.cpdq.entity.UserInfo;

public interface UserService {

    /**
     * 创建用户
     *
     * @param n
     */
    public void initUserInfo(int n);

    /**
     * 根据用户 ID 查询用户
     *
     * @param userId
     */
    public UserInfo getUserById(Integer userId);
}
