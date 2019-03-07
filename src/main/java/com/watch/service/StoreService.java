package com.watch.service;

import com.watch.dao.StoreDAO;
import com.watch.pojo.Brand;
import com.watch.pojo.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {
    @Autowired StoreDAO storeDAO;

    public List<Store> listByBrand(Brand brand){
        return storeDAO.findByBrandOrderById(brand);
    }
    public void removeBrandFromStore(List<Store> stores){
        for(Store store:stores)
            store.setBrand(null);
    }
}
