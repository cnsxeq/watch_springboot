package com.watch.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import javax.xml.soap.SAAJResult;

@Controller
public class ForePageController {
    @GetMapping(value = "/")
    public String index(){
        return "redirect:home";
    }

    @GetMapping(value = "/login")
    public String  login(){
        return "fore/login";
    }

    @GetMapping(value = "/home")
    public String home(){
        return "fore/home";
    }

    @GetMapping(value = "/brand")
    public String brand(){
        return "fore/brand";
    }

    @GetMapping(value="/brandProduct")
    public String brandProduct(){
        return "fore/brandProduct";
    }

    @GetMapping(value="/register")
    public String register(){
        return "fore/register";
    }

    @GetMapping(value="/registerSuccess")
    public String registerSuccess(){return "fore/registerSuccess";}

    @GetMapping(value="/product")
    public String product(){
        return "fore/product";
    }

    @GetMapping(value="/search")
    public String search(){
        return "fore/search";
    }

    @GetMapping(value="/cart")
    public String cart(){
        return "fore/cart";
    }

    @GetMapping(value="/buy")
    public String buy(){
        return "fore/buy";
    }
    @GetMapping(value="/alipay")
    public String alipay(){
        return "fore/alipay";
    }

    @GetMapping(value="/payed")
    public String payed(){
        return "fore/payed";
    }

    @GetMapping(value="/bought")
    public String bought(){
        return "fore/bought";
    }
    @GetMapping(value="forgetPassword")
    public String forgetPassword(){
        return "fore/forgetPassword";
    }

    @GetMapping(value="passwordSuccess")
    public String passwordSuccess(){
        return "fore/passwordSuccess";
    }

    @GetMapping(value="/userCenter")
    public String userCenter(){
        return "fore/userCenter";
    }

    @GetMapping(value="/forelogout")
    public String logout(HttpSession session ) {
        session.removeAttribute("user");
        return "redirect:home";
    }
}
