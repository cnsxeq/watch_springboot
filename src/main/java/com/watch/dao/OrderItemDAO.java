package com.watch.dao;

import com.watch.pojo.Order;
import com.watch.pojo.OrderItem;
import com.watch.pojo.Product;
import com.watch.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemDAO extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findByOrderOrderByIdDesc(Order order);
    List<OrderItem> findByProduct(Product product);
    List<OrderItem> findByUserAndOrderIsNull(User user);
    void deleteById(int id);
}
