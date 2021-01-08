package com.zgz.cpdq.table;

import com.zgz.cpdq.entity.UserInfo;
import com.zgz.cpdq.table.base.BaseTable;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "t_humor")
@Entity
public class Humor extends BaseTable {

    private String title;

    @Column(columnDefinition = "varchar(1000) default '' not null")
    private String content;

    private String weight;

    private Date dateTime;

    private Integer userId;

    private String source;

    private String thirdId;

    private Integer goodNum = 0;

    private Integer badNum = 0;
}
