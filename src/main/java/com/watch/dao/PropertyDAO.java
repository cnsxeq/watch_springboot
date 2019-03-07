package com.watch.dao;

import com.watch.pojo.Product;
import com.watch.pojo.Property;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyDAO extends JpaRepository<Property,Integer> {
    Property findByProduct(Product product);
}

