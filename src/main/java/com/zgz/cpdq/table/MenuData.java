package com.zgz.cpdq.table;

import com.zgz.cpdq.table.base.BaseTable;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "t_menu_data")
@Entity
@Data
@ToString
public class MenuData extends BaseTable {

   private String title;

   private String href;

   private String imageurl;

   private Integer sort = 0;

   private Long menuCategoryId;

}
