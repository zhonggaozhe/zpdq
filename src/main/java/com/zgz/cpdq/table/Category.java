package com.zgz.cpdq.table;

import com.zgz.cpdq.table.base.BaseTable;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "t_category")
@Entity
@Data
public class Category extends BaseTable {

    private Long parentId;

    private String lable;

    private Integer sort;
}
