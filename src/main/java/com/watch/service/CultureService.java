package com.watch.service;

import com.watch.dao.CultureDAO;
import com.watch.pojo.Brand;
import com.watch.pojo.Culture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CultureService {
    @Autowired CultureDAO cultureDAO;
    @Autowired BrandService brandService;



    public Culture get(int id){
       Culture bean=cultureDAO.getOne(id);
        return bean;
    }
    public void update(Culture bean){
        cultureDAO.save(bean);
    }

    public void fill(Brand brand){
        int id=brand.getId();
        Culture culture=cultureDAO.findById(id);
        brand.setCulture(culture);
    }
}
