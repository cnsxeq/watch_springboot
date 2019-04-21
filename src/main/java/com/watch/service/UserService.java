package com.watch.service;

import com.watch.dao.UserDAO;

import com.watch.pojo.User;
import com.watch.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService{
    @Autowired  UserDAO userDAO;

    public User getByName(String name){
       return userDAO.findByName(name);
    }
    public User getByEmailAndPassword(String email,String password){
        return userDAO.getByEmailAndPassword(email, password);
    }
    public boolean isExist(String email){
        User user=userDAO.findByEmail(email);
        return null!=user;
    }
    public void add(User user){
        userDAO.save(user);
    }
    public User getByEmail(String email){
        return userDAO.findByEmail(email);
    }
    public void update(User user){
        userDAO.save(user);
    }

    public Page4Navigator<User> list(int start, int size, int navigatePages){
        Sort sort = new Sort(Sort.Direction.ASC,"id");
        Pageable pageable = new PageRequest(start,size,sort);
        Page pageFromJPA = userDAO.findAll(pageable);
        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }
    public User getById(int id){
       return userDAO.getOne(id);

    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user=userDAO.findByEmail(email);
        if(null==user){
            throw new UsernameNotFoundException("无该用户");
        }
        String role=user.getRole();
        String password=user.getPassword();
        return new User(email,password,role);
    }
}
