package com.watch.service;

import com.watch.dao.PlaceDAO;
import com.watch.pojo.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceService {
    @Autowired PlaceDAO placeDAO;

//    public void fill(List<Store> stores){
//        for(Store store:stores)
//            fill(store);
//    }
//    public void fill(Store store){
//
//    }

}
