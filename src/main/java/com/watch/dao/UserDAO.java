package com.watch.dao;

import com.watch.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User,Integer>{
    User findByName(String name);
    User findByEmail(String mail);
    User findById(int id);
    User getByEmailAndPassword(String email,String password);
}
