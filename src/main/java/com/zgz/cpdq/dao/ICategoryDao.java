package com.zgz.cpdq.dao;

import com.zgz.cpdq.table.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ICategoryDao extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {
}
