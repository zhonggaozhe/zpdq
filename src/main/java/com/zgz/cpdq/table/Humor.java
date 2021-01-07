package com.zgz.cpdq.table;

import com.zgz.cpdq.table.base.BaseTable;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "t_humor")
@Entity
public class Humor extends BaseTable {

    private String content;

    private String weight;

    private Date dateTime;
}
