package com.cn.tmall.interceptor;

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

/**
 * 登录状态拦截器
 * 此拦截器过滤需要登录才能操作的请求
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    CategoryService categoryService;

    @Autowired
    OrderItemService orderItemService;

    /**
     * 在业务处理器处理请求之前被调用
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String contextPath = session.getServletContext().getContextPath();
        //存放那些不需要登录也能访问的路径
        String[] noNeedAuthPage = new String[]{
            "home",
            "checkLogin",
            "register",
            "loginAjax",
            "login",
            "product",
            "category",
            "search"
        };
        //获取uri
        String uri = request.getRequestURI();
        //删除uri中contextPath部分，去掉前缀
        uri = StringUtils.remove(uri, contextPath);

        if(uri.startsWith("/fore")){
            //截取 /fore 后面的字符串
            String method = StringUtils.substringAfterLast(uri, "/fore");
            //截取后的字符串不在noNeedAuthPage之列的，尝试获取user
            if(!Arrays.asList(noNeedAuthPage).contains(method)){
                User user = (User) session.getAttribute("user");
                if(null == user){
                    response.sendRedirect("loginPage");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作
     * 可在modelAndView中加入数据，比如当前时间
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //不写
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
