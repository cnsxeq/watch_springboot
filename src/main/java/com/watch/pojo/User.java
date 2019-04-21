package com.watch.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name="user")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    private Integer operator;
    private String password;
    private String name;
    private String email;
    private String code;
    private String address;
    private String mobile;
    private String post;
    private String receiver;

    @Transient
    private String role;

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    public String getRole(){
        if(null!=role)
            return role;
        switch(operator){
            case 0:
                role="ROLE_USER";
                break;
            case 1:
                role="ROLE_ADMIN";
                break;
            default:
                role="ROLE_USER";
        }
        return role;
    }

    public User(String name,String password,String role){
        this.name=name;
        this.password=password;
        this.role=role;
        this.authorities=getAuthorities();

    }
    public User(){ }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        role=getRole();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
