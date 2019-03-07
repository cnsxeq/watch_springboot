package com.watch.service;

import com.watch.dao.BrandDAO;

import com.watch.pojo.Brand;
import com.watch.pojo.Product;
import com.watch.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {
    @Autowired  BrandDAO brandDAO;

    public Page4Navigator<Brand> list(int start,int size,int navigatePages){
        Sort sort = new Sort(Sort.Direction.ASC,"id");
        Pageable pageable = new PageRequest(start,size,sort);
        Page pageFromJPA = brandDAO.findAll(pageable);
        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }

    public void add(Brand bean) {
        brandDAO.save(bean);
    }

    public void delete(int id){
      brandDAO.delete(id);
    }

    public void update(Brand bean){
        brandDAO.save(bean);
    }

    public Brand get(int id){
        Brand bean=brandDAO.getOne(id);
        return bean;
    }
    public List<Brand> list(){
        Sort sort=new Sort(Sort.Direction.ASC,"id");
        return brandDAO.findAll(sort);
    }
    public List<String> listOfName(){
        return brandDAO.getName();
    }

    public void removeBrandFromProduct(List<Brand> bs){
        for(Brand brand:bs)
            removeBrandFromProduct(brand);
    }
    public void removeBrandFromProduct(Brand brand){
        List<Product> ps=brand.getProducts();
        if(null!=ps){
            for(Product product:ps)
                product.setBrand(null);
        }
    }
    public Brand getByName(String name){
        Brand brand=brandDAO.findByName(name);
        return brand;
    }
}
