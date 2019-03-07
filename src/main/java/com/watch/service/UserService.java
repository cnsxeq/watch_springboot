package com.watch.service;

import com.watch.dao.UserDAO;

import com.watch.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired  UserDAO userDAO;

    public User getByName(String name){
       return userDAO.findByName(name);
    }
    public User get(String name,String password){
        return userDAO.getByNameAndPassword(name, password);
    }
    public boolean isExist(String name){
        User user=userDAO.findByName(name);
        return user!=user;
    }
    public void add(User user){
        userDAO.save(user);
    }
}
