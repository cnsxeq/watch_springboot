package com.watch.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.xml.soap.SAAJResult;

@Controller()
public class AdminPageController {
    @GetMapping(value="admin")
    public String admin(){
        return "redirect:admin_login";
    }

    @GetMapping(value="/admin_brand")
    public String  band(){
        return "admin/brand";
    }

    @GetMapping(value = "/admin_brand_edit")
    public String edit(){
        return "admin/editBrand";
    }

    @GetMapping(value="/admin_culture")
    public String culture(){
        return "admin/culture";
    }

    @GetMapping(value="/admin_product_list")
    public String listProduct(){
        return "admin/listProduct";
    }

    @GetMapping(value = "/admin_product_edit")
    public String editProduct(){
        return "admin/editProduct";
    }

    @GetMapping(value= "/admin_property_edit")
    public String editProperty(){
        return "admin/editProperty";
    }

    @GetMapping(value="/admin_users")
    public String passwordSuccess(){
        return "admin/listUser";
    }

    @GetMapping(value="/admin_order")
    public String listOrder(){
        return "admin/admin_order";
    }
}
