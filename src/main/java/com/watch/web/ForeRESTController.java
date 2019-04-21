package com.watch.web;

import com.watch.dao.CultureDAO;
import com.watch.pojo.*;
import com.watch.service.*;
import com.watch.util.*;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class ForeRESTController {
    @Autowired UserService userService;
    @Autowired BrandService brandService;
    @Autowired CultureService cultureService;
    @Autowired ProductService productService;
    @Autowired ProductImageService productImageService;
    @Autowired PropertyService propertyService;
    @Autowired StoreService storeService;
    @Autowired PlaceService placeService;
    @Autowired OrderItemService orderItemService;
    @Autowired OrderService orderService;
    @Autowired MailService mailService;
    @Autowired AuthServiceImpl authService;

    @GetMapping("/forehome")
    public Object forehome(){
        List<Brand> bs=brandService.list();
        productService.fill(bs);
        brandService.removeBrandFromProduct(bs);
        return bs;
    }

    @GetMapping("/brandRight")
    public Object brandRight(){
        List<String> br=brandService.listOfName();
        return br;
    }

    @GetMapping("/brand/{name}")
    public Object getBrand(@PathVariable("name")String name)throws Exception{
        Brand brand=brandService.getByName(name);
        cultureService.fill(brand);
        return brand;
    }

    @GetMapping("/brandProduct/{bid}")
    public Object getBrandProduct(@PathVariable("bid")int bid)throws Exception{
        Brand brand=brandService.get(bid);
        productService.fill(brand);
        brandService.removeBrandFromProduct(brand);
        return brand;
    }

    @PostMapping("/selectedProducts")
    public Object getSelectedProducts(@RequestBody Category categoryParam)throws Exception{
        int bid=categoryParam.getBid();
        Brand brand=brandService.get(bid);
        String sex=categoryParam.getSex();
        String core=categoryParam.getCore();
        String shell="%"+categoryParam.getShell()+"%";
        List<Product> products=productService.find(brand,sex,core,shell);
        return fillProducts(brand,products);
    }
    public Brand fillProducts(Brand brand,List<Product> products){
        productImageService.setFirstProductImages(products);
        brand.setProducts(products);
        brandService.removeBrandFromProduct(brand);
        return brand;
    }


    @PostMapping("/Login")
    public Object login(@RequestBody User user, HttpSession session)throws Exception {
        String email=user.getEmail();
        String password=user.getPassword();
        try{
           authService.getLoginToken(email,password);
        }catch (DisabledException | BadCredentialsException e){
            return Result.fail("账号/密码错误");
        }
        User userParam=userService.getByEmail(email);
        session.setAttribute("user",userParam);
        String role=userParam.getRole();
        return Result.success("登录成功",role);
        }



    @PostMapping("/register")
    public Object register(@RequestBody User user){
        String email=user.getEmail();
        String code=user.getCode();
        String password=user.getPassword();
        int id=userService.getByEmail(email).getId();
        user.setId(id);
        if(!code.equals(userService.getByEmail(email).getCode())){
            String message="验证码不正确";
            return Result.fail(message);
        }
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        String encoderPassword=encoder.encode(password);
        user.setPassword(encoderPassword);
        user.setOperator(0);
            userService.update(user);
            return Result.success();
    }

    @PostMapping("/ResetPassword")
    public Object ResetPassword(@RequestBody User user)throws Exception{
        String email =user.getEmail();
        String password=user.getPassword();
        String code=user.getCode();
        User userParam=userService.getByEmail(email);
        if(null==userParam){
            String message="用户不存在,请重新输入";
            return Result.fail(message);
        }
        if(!code.equals(userParam.getCode())){
            String message="验证码错误";
            return Result.fail(message);
        }
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        String encoderPassword=encoder.encode(password);
        userParam.setPassword(encoderPassword);
        userService.update(userParam);
        return Result.success("密码重设成功");
    }

    @PostMapping("/passwordCode")
    public Object passwordCode(@RequestBody User user)throws Exception{
        String email =user.getEmail();
        boolean exist=userService.isExist(email);
        if(exist){
            return sendMail(user);
        }else {
            String message="用户不存在,请重新输入";
            return Result.fail(message);
        }
    }
    public Object sendMail(User user) {
        String to=user.getEmail();
        int id=userService.getByEmail(to).getId();
        user.setId(id);
        String code = UUIDUtils.getUUID() ;
        user.setCode(code);
        userService.update(user);
        try {
            mailService.sendSimpleMail(to, "验证码", "验证码为" + code);
            return Result.success();
        } catch (Exception ex) {
            ex.printStackTrace();
            String message=("发送失败");
            return Result.fail(message);
        }
    }

    @PostMapping("/registerCode")
    public Object registerCode(@RequestBody User user)throws  Exception{
        String email=user.getEmail();
        System.out.println(email);
        boolean exist = userService.isExist(email);
        if(exist){
            String message = "邮箱已经被使用";
            return Result.fail(message);
        }else{
            userService.add(user);
            return sendMail(user);
        }
    }

    @GetMapping("/foreProduct/{pid}")
    public Object foreProduct(@PathVariable("pid")int pid){
        Product product = productService.get(pid);
        Property property =propertyService.get(pid);
        property.setProduct(null);

        Brand brand=product.getBrand();
        List<Store> stores=storeService.listByBrand(brand);
        storeService.removeBrandFromStore(stores);

        List<ProductImage> productPluralImages = productImageService.listPluralProductImages(product);
        product.setProductPluralImages(productPluralImages);
        productImageService.setFirstProductImage(product);

        Map<String,Object> map = new HashMap<>();
        map.put("product",product);
        map.put("property",property);
        map.put("stores",stores);
        return Result.success(map);
    }


    @GetMapping("foreCheckLogin")
    public Object checkLogin(HttpSession session){
        User user = (User)session.getAttribute("user");
        if(null!=user)
            return Result.success();
        return Result.fail("未登录");
    }
    @GetMapping("foreCart")
    public Object cart(HttpSession session){
        User user = (User)session.getAttribute("user");
        System.out.println(user.getId());
        List<OrderItem> ois = orderItemService.listByUser(user);
        System.out.println(ois.toString());
        productImageService.setFirstProductImagesOnOrderItems(ois);
        return ois;
    }
    @GetMapping("foreAddCart")
    public Object addCart(int pid,HttpSession session){
        int num=1;
        buyOneAndAddCart(pid, num, session);
        return Result.success();
    }
    @GetMapping("forebuyone")
    public Object buyone(int pid,HttpSession session){
        int num=1;
        return buyOneAndAddCart(pid,num,session);
    }
    private int buyOneAndAddCart(int pid,int num,HttpSession session){
        Product product = productService.get(pid);
        int oiid = 0;

        User user = (User)session.getAttribute("user");
        boolean found = false;
        List<OrderItem> ois = orderItemService.listByUser(user);
        for (OrderItem oi:ois){
            if(oi.getProduct().getId() == product.getId()){
                oi.setNumber(oi.getNumber()+num);
                orderItemService.update(oi);
                found = true;
                oiid = oi.getId();
                break;
            }
        }
        if (!found){
            OrderItem oi = new OrderItem();
            oi.setUser(user);
            oi.setProduct(product);
            oi.setNumber(num);
            orderItemService.add(oi);
            oiid=oi.getId();
        }
        return oiid;
    }

    @PostMapping("foreSearch")
    public List<Product> search(String keyword)throws Exception{
        if(null==keyword)
            keyword="";
        List<Product> ps = productService.search(keyword,0,20);
        productImageService.setFirstProductImages(ps);
        return ps;
    }

    @GetMapping("foreBuy")
    public Object buy(String[] oiid,HttpSession session) {
        List<OrderItem> orderItems = new ArrayList<>();
        float total = 0;
        for (String strid : oiid) {
            int id = Integer.parseInt(strid);
            OrderItem oi = orderItemService.get(id);
            total += oi.getProduct().getPrice();
            orderItems.add(oi);
        }
        productImageService.setFirstProductImagesOnOrderItems(orderItems);
        session.setAttribute("ois",orderItems);
        Map<String,Object> map = new HashMap<>();
        map.put("orderItems",orderItems);
        map.put("total",total);
        return Result.success(map);
    }
    @PostMapping("foreCreateOrder")
    public Object createOrder(@RequestBody Order order,HttpSession session){
        User user = (User)session.getAttribute("user");
        if (null==user)
            return Result.fail("未登录");
        String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+ RandomUtils.nextInt(10000);
        order.setOrderCode(orderCode);
        order.setCreateDate(new Date());
        order.setUser(user);
        order.setStatus(OrderService.waitPay);
        List<OrderItem> ois = (List<OrderItem>)session.getAttribute("ois");

        float total = orderService.add(order,ois);

        Map<String,Object> map = new HashMap<>();
        map.put("oid",order.getId());
        map.put("total",total);
        return Result.success(map);
    }

    @GetMapping("forePayed")
    public Object payed(int oid){
        Order order = orderService.get(oid);
        order.setStatus(OrderService.waitDelivery);
        order.setPayDate(new Date());
        orderService.update(order);
        return order;
    }
    @GetMapping("foreDeleteOrderItem")
    public Object deleteOrderItem(HttpSession session,int oiid){
        User user = (User)session.getAttribute("user");
        if(null==user)
            return Result.fail("未登录");

        orderItemService.delete(oiid);
        return Result.success();
    }
    @PutMapping("foreDeleteOrder")
    public Object deleteOrder(int oid){
        Order o = orderService.get(oid);
    o.setStatus(OrderService.delete);
    orderService.update(o);
    return Result.success();
}
    @GetMapping("foreBought")
    public Object bought(HttpSession session){
        User user = (User)session.getAttribute("user");
        if (null==user)
            return Result.fail("未登录");
        List<Order> os = orderService.listByUserWithoutDelete(user);
        orderService.removeOrderFromOrderItem(os);
        return os;
    }

    @GetMapping("/user_center/{id}")
    public Object getUser(@PathVariable("id")int id)throws Exception{
        User user=userService.getById(id);
        return user;
    }
    @PostMapping(value="/user_update")
    public Object getUser(@RequestBody User user,HttpSession session)throws Exception{
        userService.update(user);
        session.setAttribute("user",user);
        return Result.success();
    }
}
