package com.watch.dao;

import com.watch.pojo.Brand;
import com.watch.pojo.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface StoreDAO extends JpaRepository<Store,Integer> {
    List<Store> findByBrandOrderById(Brand brand);
}
