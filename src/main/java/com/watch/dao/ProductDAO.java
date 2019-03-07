package com.watch.dao;

import com.watch.pojo.Brand;
import com.watch.pojo.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductDAO extends JpaRepository<Product,Integer> {
    Page<Product> findByBrand(Brand brand, Pageable pageable);
    List<Product> findByBrandOrderById(Brand brand);

    @Query(value = "select * from product where if(?1 !='',bid=?1,1=1) and if(?2 !='',sex=?2,1=1) and if(?3 !='',core=?3,1=1) and if(?4 !='',shell like ?4,1=1)",nativeQuery = true)
    List<Product> find(Brand brand,String sex,String core,String shell);
}