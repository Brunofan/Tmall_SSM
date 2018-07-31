package com.cn.tmall.service;

import com.cn.tmall.pojo.Order;
import com.cn.tmall.pojo.OrderItem;

import java.util.List;

public interface OrderItemService {

    /**
     * 增加一条订单数据
     * @param o
     */
    void add(OrderItem o);

    /**
     * 删除一条订单数据
     * @param id
     */
    void delete(int id);

    /**
     * 更新OrderItem，也只是更新number属性
     * @param orderItem
     */
    void update(OrderItem orderItem);

    /**
     * 通过id返回OrderItem项
     * @param id
     * @return
     */
    OrderItem get(int id);

    /**
     * 查询所有的Item
     * @return
     */
    List list();

    /**
     * 为List<Order>填充订单项
     * @param orders
     */
    void fill(List<Order> orders);

    /**
     * 为Order填充订单项
     * @param o
     */
    void fill(Order o);

    /**
     * 根据product_id获取订单项的数量
     * @param product_id
     * @return
     */
    int getSaleCount(int product_id);

    /**
     * 根据user id 查询订单项
     * @param uid
     * @return
     */
    List<OrderItem> listByUser(int uid);

}
