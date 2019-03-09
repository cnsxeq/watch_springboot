package com.watch.service;

import com.watch.dao.OrderDAO;
import com.watch.pojo.Order;
import com.watch.pojo.OrderItem;
import com.watch.pojo.User;
import com.watch.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderDAO orderDAO;
    @Autowired
    OrderItemService orderItemService;
    public static final String waitPay = "waitPay";
    public static final String waitDelivery = "waitDelivery";
    public static final String bought = "bought";

    public static final String delete = "delete";

//    public Page4Navigator<Order> list(int start, int size, int navigatePages){
//        Sort sort = new Sort(Sort.Direction.DESC,"id" );
//        Pageable pageable = new PageRequest(start,size,sort);
//        Page pageFromJPA = orderDAO.findAll(pageable);
//        return new Page4Navigator<>(pageFromJPA,navigatePages);
//    }
//
    public void removeOrderFromOrderItem(List<Order> orders){
        for (Order order:orders){
            removeOrderFromOrderItem(order);
        }
    }

    public void removeOrderFromOrderItem(Order order){
        List<OrderItem> orderItems = order.getOrderItems();
        for(OrderItem orderItem:orderItems){
            orderItem.setOrder(null);
        }
    }
    public Order get(int oid){
        return orderDAO.getOne(oid);
    }
    public void update(Order bean){
        orderDAO.save(bean);
    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackForClassName = "Exception")//事务管理
    public float add(Order order,List<OrderItem> ois){
        float total = 0;
        add(order);

        if (false)
            throw new RuntimeException();
        for (OrderItem oi:ois){
            oi.setOrder(order);
            orderItemService.update(oi);
            total+=oi.getProduct().getPrice();
        }
        return total;
    }
    public void add(Order order){
        orderDAO.save(order);
    }
//
    public List<Order> listByUserAndNotDeleted(User user){
        return orderDAO.findByUserAndStatusNotOrderByIdDesc(user,OrderService.delete);
    }
//
    public List<Order> listByUserWithoutDelete(User user){
        List<Order> orders = listByUserAndNotDeleted(user);
        orderItemService.fill(orders);
        return orders;
    }
//
//    public void cacl(Order o){
//        List<OrderItem> orderItems = o.getOrderItems();
//        float total = 0;
//        for(OrderItem orderItem:orderItems){
//            total+=orderItem.getProduct().getPromotePrice()*orderItem.getNumber();
//        }
//        o.setTotal(total);
//    }

}
