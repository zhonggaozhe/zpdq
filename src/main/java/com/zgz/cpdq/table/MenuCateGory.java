package com.zgz.cpdq.table;

import com.zgz.cpdq.table.base.BaseTable;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "t_menu_category")
@Entity
@Data
public class MenuCateGory extends BaseTable {

    private String name;

    private String url;

    private Long partiesId;

    private String source;

    private Long categoryId;

    private String remark;

}
