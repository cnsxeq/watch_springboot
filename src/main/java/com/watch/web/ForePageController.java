package com.watch.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
}
