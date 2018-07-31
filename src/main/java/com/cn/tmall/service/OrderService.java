package com.cn.tmall.service;

import com.cn.tmall.pojo.Order;
import com.cn.tmall.pojo.OrderItem;

import java.util.List;

public interface OrderService {

    String waitPay = "waitPay";
    String waitDelivery = "waitDelivery";
    String waitConfirm = "waitConfirm";
    String waitReview = "waitReview";
    String finish = "finish";
    String delete = "delete";

    void add(Order c);

    //增加订单
    float add(Order c, List<OrderItem> ois);

    void delete(int id);

    void update(Order c);

    Order get(int id);

    List list();

    List list(int uid, String excludedStatus);
}
