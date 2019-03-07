package com.watch.dao;


import com.watch.pojo.Culture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CultureDAO extends JpaRepository<Culture,Integer> {
    Culture findById(int id);
}
