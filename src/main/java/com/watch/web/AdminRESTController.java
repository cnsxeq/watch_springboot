package com.watch.web;

import com.watch.dao.CultureDAO;
import com.watch.pojo.*;
import com.watch.service.*;
import com.watch.util.ImageUtil;
import com.watch.util.Page4Navigator;
import com.watch.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class AdminRESTController {
    @Autowired UserService userService;
    @Autowired BrandService brandService;
    @Autowired CultureService cultureService;
    @Autowired ProductService productService;
    @Autowired ProductImageService productImageService;
    @Autowired PropertyService propertyService;
    @PostMapping("/admin_login")
    public Object login(@RequestBody User userParam, HttpSession session) {
        String name = userParam.getName();
        name = HtmlUtils.htmlEscape(name);

        User user = userService.get(name, userParam.getPassword());
        if (null == user) {
            String message = "账号或密码错误";
            return Result.fail(message);
        } else {
            session.setAttribute("user", user);
            return Result.success();
        }
    }

    @GetMapping("/brands")
    public Page4Navigator<Brand> list(@RequestParam(value="start",defaultValue = "0")int start,
                                      @RequestParam(value="size",defaultValue = "5")int size)throws Exception{
        start =start<0?0:start;
        Page4Navigator<Brand> page=brandService.list(start,size,5);//5表示导航分页栏最多为5
        return  page;
    }

    @PostMapping("/brands")
    public Object add(Brand bean, MultipartFile image, HttpServletRequest request)throws IOException{
        brandService.add(bean);
        saveOrUpdateImageFile(bean, image, request);
        return bean;
    }
    public void saveOrUpdateImageFile(Brand bean,MultipartFile image,HttpServletRequest request)throws IOException{
        File imageFolder = new File(request.getServletContext().getRealPath("img/brand"));
        File file = new File(imageFolder,bean.getId()+".jpg");
        if(!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        image.transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img,"jpg",file);
    }

    @DeleteMapping("/brands/{id}")
    public String delete(@PathVariable("id") int id,HttpServletRequest request)throws Exception{
        brandService.delete(id);
        File imageFolder = new File(request.getServletContext().getRealPath("img/brand"));
        File file = new File(imageFolder,id+".jpg");
        file.delete();
        return null;
    }

    @GetMapping("/brands/{id}")
    public Brand get(@PathVariable("id") int id)throws Exception{
        Brand bean = brandService.get(id);
        return bean;
    }

    @PutMapping("/brands/{id}")
    public Object update(Brand bean,MultipartFile image,HttpServletRequest request)throws Exception{
        String name=request.getParameter("name");
        bean.setName(name);
        brandService.update(bean);
        if(null!=image){
            saveOrUpdateImageFile(bean,image,request);
        }
        return bean;
    }

    @GetMapping("/cultures/{id}")
    public Culture getCultures(@PathVariable("id") int id)throws Exception{
        Culture bean=cultureService.get(id);
        return bean;
    }

    @PutMapping("/cultures")
    public Object updateCulture( @RequestBody Culture bean)throws Exception{
        cultureService.update(bean);
        return bean;
    }

    @GetMapping("/brands/{bid}/products")
   public Page4Navigator<Product> listProduct(@PathVariable("bid") int bid,
                                       @RequestParam(value = "start",defaultValue = "0") int start,
                                       @RequestParam(value = "size",defaultValue = "5") int size)throws Exception{
        start = start<0?0:start;
        Page4Navigator<Product> page = productService.list(bid,start,size,5);
        productImageService.setFirstProductImages(page.getContent());
        return page;
    }

    @DeleteMapping("/products/{id}")
    public String delete(@PathVariable("id") int id)throws Exception{
        productService.delete(id);
        return null;
    }

    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable("id") int id)throws Exception{
        Product bean = productService.get(id);
        return bean;
    }

    @PutMapping("/products")
    public Object updateProduct(@RequestBody Product bean)throws Exception{
        productService.update(bean);
        return bean;
    }

    @PostMapping("/products")
    public Object addProduct(@RequestBody Product bean)throws Exception{
        productService.add(bean);
        return bean;
    }

    @GetMapping("/properties/{id}")
    public Property getProperty(@PathVariable("id") int id)throws Exception{
        Property bean=propertyService.get(id);
        System.out.print(bean.toString());
        return bean;
    }

    @PutMapping("/properties")
    public Object updateProperty(@RequestBody Property bean)throws Exception{
        propertyService.update(bean);
        return bean;
    }
}