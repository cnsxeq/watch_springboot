package com.watch.service;

import com.watch.dao.OrderItemDAO;
import com.watch.pojo.Order;
import com.watch.pojo.OrderItem;
import com.watch.pojo.Product;
import com.watch.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {
    @Autowired
    OrderItemDAO orderItemDAO;
    @Autowired
    ProductImageService productImageService;

    public List<OrderItem> listByUser(User user){
        return orderItemDAO.findByUserAndOrderIsNull(user);
    }

    public OrderItem get(int id){
        return orderItemDAO.getOne(id);
    }
//    public void fill(List<Order> orders){
//        for(Order order:orders){
//            fill(order);
//        }
//    }
//
//    public void fill(Order order){
//        List<OrderItem> orderItems = listByOrder(order);
//        float total = 0;
//        int totalNubmer = 0;
//        for(OrderItem orderItem:orderItems){
//            total+=orderItem.getNumber()*orderItem.getProduct().getPromotePrice();
//            totalNubmer+=orderItem.getNumber();
//            productImageService.setFirstProductImage(orderItem.getProduct());
//        }
//        order.setTotal(total);
//        order.setTotalNumber(totalNubmer);
//        order.setOrderItems(orderItems);
//
//    }
//
//    public List<OrderItem> listByOrder(Order order){
//        return orderItemDAO.findByOrderOrderByIdDesc(order);
//    }
//    public List<OrderItem> listByProduct(Product product){
//        return orderItemDAO.findByProduct(product);
//    }

//
//    public void add(OrderItem orderItem){
//        orderItemDAO.save(orderItem);
//    }
//    public void delete(int id){
//        orderItemDAO.deleteById(id);
//    }

//    public void update(OrderItem orderItem){
//        orderItemDAO.save(orderItem);
//    }
//
//
//
//    public int getSaleCount(Product product){
//        List<OrderItem> ois = listByProduct(product);
//        int result = 0;
//        for(OrderItem oi:ois){
//            if(null!=oi.getOrder()){
//                if(null!=oi.getOrder() && null!=oi.getOrder().getPayDate())
//                    result+=oi.getNumber();
//            }
//        }
//        return result;
//    }
}
