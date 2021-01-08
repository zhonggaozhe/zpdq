package com.zgz.cpdq.dao;

import com.zgz.cpdq.table.Humor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IHumorDao extends JpaRepository<Humor, Long>, JpaSpecificationExecutor<Humor> {
}
