package com.zgz.cpdq.dao;

import com.zgz.cpdq.table.MenuData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IMenuDataDao extends JpaRepository<MenuData, Long>, JpaSpecificationExecutor<MenuData> {
}
