package com.watch.interceptor;

import com.watch.pojo.OrderItem;
import com.watch.pojo.User;
import com.watch.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class OtherInterceptor implements HandlerInterceptor {
    @Autowired
    OrderItemService orderItemService;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse,
                             Object o)throws Exception{
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView)throws Exception{
        HttpSession session = httpServletRequest.getSession();
        User user = (User)session.getAttribute("user");
        int cartTotalItemNumber = 0;
        if(null!=user){
            List<OrderItem> ois = orderItemService.listByUser(user);
            for (OrderItem oi:ois){
                cartTotalItemNumber+=oi.getNumber();
            }
        }
        String contextPath = httpServletRequest.getServletContext().getContextPath();

        session.setAttribute("cartTotalItemNumber",cartTotalItemNumber);
        httpServletRequest.getServletContext().setAttribute("contextPath",contextPath);
    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o,Exception e)throws Exception{

    }
}
