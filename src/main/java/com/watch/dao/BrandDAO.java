package com.watch.dao;


import com.watch.pojo.Brand;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface BrandDAO extends JpaRepository<Brand,Integer> {
//    @Transactional
//    @Modifying(clearAutomatically = true)
//    @Query("alter table brand auto_increment =1;")
//    void resetIncre();

    @Query(value="select name from brand",nativeQuery = true)
     List<String> getName();

    Brand findByName(String name);
}
