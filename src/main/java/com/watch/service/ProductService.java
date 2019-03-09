package com.watch.service;

import com.watch.dao.BrandDAO;
import com.watch.dao.ProductDAO;
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
public class ProductService {
    @Autowired ProductDAO productDAO;
    @Autowired BrandService brandService;
    @Autowired ProductImageService productImageService;


    public Page4Navigator<Product> list(int bid, int start, int size, int navigatePages){
        Brand brand=brandService.get(bid);
        Sort sort = new Sort(Sort.Direction.ASC,"id");
        Pageable pageable = new PageRequest(start,size,sort);
        Page<Product> pageFromJPA = productDAO.findByBrand(brand,pageable);
        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }
    public void delete(int id){
        productDAO.delete(id);
    }
    public Product get(int id){
        return productDAO.getOne(id);
    }
    public void update(Product bean){
        productDAO.save(bean);
    }
    public void add(Product bean){
        productDAO.save(bean);
    }
    public void fill(List<Brand> brands){
        for(Brand brand:brands)
            fill(brand);
    }
    public void fill(Brand brand){
        List<Product> ps =listByBrand(brand);
        productImageService.setFirstProductImages(ps);
        brand.setProducts(ps);
    }

    public List<Product> listByBrand(Brand brand){
        return productDAO.findByBrandOrderById(brand);
    }

    public List<Product> find(Brand brand,String sex,String core,String shell){
        List<Product> products=productDAO.find(brand,sex,core,shell);
        return products;
    }

    public List<Product> search(String keyword,int start,int size){
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        Pageable pageable = new PageRequest(start,size,sort);
        List<Product> products = productDAO.findByNameLike("%"+keyword+"%",pageable);
        return products;
    }
//    public Page4Navigator<Product> search(String keyword, int start, int size, int navigatePages){
//        Sort sort = new Sort(Sort.Direction.DESC,"id");
//        Pageable pageable = new PageRequest(start,size,sort);
//        Page<Product> pageFromJPA = productDAO.findByNameLike("%"+keyword+"%",pageable);
//        return new Page4Navigator<>(pageFromJPA,navigatePages);
//    }

}
