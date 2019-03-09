package com.watch.service;

import com.watch.dao.ProductImageDAO;
import com.watch.pojo.OrderItem;
import com.watch.pojo.Product;
import com.watch.pojo.ProductImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageService {
    public static final String type_single="single";
    public static final String type_plural="plural";
@Autowired ProductImageDAO productImageDAO;
    public void setFirstProductImages(List<Product> products){
        for(Product product:products){
            setFirstProductImage(product);
        }
    }
    public void setFirstProductImage(Product product){
        List<ProductImage> singleImages = listSingleProductImages(product);
        if(!singleImages.isEmpty())
            product.setFirstProductImage(singleImages.get(0));
        else
            product.setFirstProductImage(new ProductImage());
    }
    public List<ProductImage> listSingleProductImages(Product product){
        return productImageDAO.findByProductAndTypeOrderByIdDesc(product,type_single);
    }
    public List<ProductImage> listPluralProductImages(Product product){
        return productImageDAO.findByProductAndTypeOrderByIdDesc(product,type_plural);
    }
    public void setFirstProductImagesOnOrderItems(List<OrderItem> ois){
        for (OrderItem oi:ois){
            setFirstProductImage(oi.getProduct());
        }
    }
}
