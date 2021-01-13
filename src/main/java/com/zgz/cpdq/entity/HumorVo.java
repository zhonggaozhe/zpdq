package com.zgz.cpdq.entity;

import com.zgz.cpdq.entity.UserInfo;
import lombok.Data;

import java.io.Serializable;

@Data
public class HumorVo implements Serializable {

    private UserInfo userInfo;

    private String title;

    private String content;

    private String dateTime;
}
