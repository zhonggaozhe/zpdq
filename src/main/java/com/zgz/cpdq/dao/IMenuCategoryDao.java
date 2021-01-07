package com.zgz.cpdq.dao;

import com.zgz.cpdq.table.MenuCateGory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IMenuCategoryDao extends JpaRepository<MenuCateGory, Long>, JpaSpecificationExecutor<MenuCateGory> {
}
