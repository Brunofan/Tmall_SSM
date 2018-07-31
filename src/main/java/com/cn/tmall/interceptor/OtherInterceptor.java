package com.cn.tmall.interceptor;

import com.cn.tmall.pojo.Category;
import com.cn.tmall.pojo.OrderItem;
import com.cn.tmall.pojo.User;
import com.cn.tmall.service.CategoryService;
import com.cn.tmall.service.OrderItemService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

/**
 * 登录状态拦截器
 * 此拦截器过滤需要登录才能操作的请求
 */
public class OtherInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    CategoryService categoryService;

    @Autowired
    OrderItemService orderItemService;

    /**
     * 在业务处理器处理请求之前被调用
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    /**
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作
     * 可在modelAndView中加入数据，比如当前时间
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //获取分类集合的信息，用于放在搜索栏下面
        List<Category> cs = categoryService.list();
        request.getSession().setAttribute("cs", cs);

        //获取当前contextPath，用于给top.jsp中获取主页地址
        HttpSession session = request.getSession();
        String contextPath = session.getServletContext().getContextPath();
        System.out.println("other拦截器获取的contextPath：" + contextPath);
        request.getSession().setAttribute("contextPath", contextPath);

        //获取购物车中一共有多少数量
        User user = (User) session.getAttribute("user");
        int cartTotalItemNumber = 0;
        if (null != user){
            List<OrderItem> orderItems = orderItemService.listByUser(user.getId());
            for (OrderItem oi : orderItems){
                cartTotalItemNumber += oi.getNumber();
            }
        }
        request.getSession().setAttribute("cartTotalItemNumber", cartTotalItemNumber);
    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等
     *
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //不写
    }
}