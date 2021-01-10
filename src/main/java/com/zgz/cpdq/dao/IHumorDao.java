package com.zgz.cpdq.dao;

import com.zgz.cpdq.table.Humor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface IHumorDao extends JpaRepository<Humor, Long>, JpaSpecificationExecutor<Humor> {

    @Query(value = "select count(1) from t_humor where title = ?1 limit 1", nativeQuery = true)
    public int countByTitle(String title);

    @Query(value = "select count(1) from t_humor where third_id = ?1 limit 1", nativeQuery = true)
    public int countByThirdId(String thirdId);

}
