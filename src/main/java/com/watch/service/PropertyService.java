package com.watch.service;

import com.watch.dao.PropertyDAO;
import com.watch.pojo.Product;
import com.watch.pojo.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropertyService {
    @Autowired
    PropertyDAO propertyDAO;
    @Autowired ProductService productService;

    public Property get(int pid){
        Product product=productService.get(pid);
        Property bean=propertyDAO.findByProduct(product);
        return bean;
    }
    public void update(Property bean){
        propertyDAO.save(bean);
    }
}
