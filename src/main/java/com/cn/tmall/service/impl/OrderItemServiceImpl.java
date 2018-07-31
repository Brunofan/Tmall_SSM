package com.cn.tmall.service.impl;

import com.cn.tmall.mapper.OrderItemMapper;
import com.cn.tmall.pojo.Order;
import com.cn.tmall.pojo.OrderItem;
import com.cn.tmall.pojo.OrderItemExample;
import com.cn.tmall.pojo.Product;
import com.cn.tmall.service.OrderItemService;
import com.cn.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    OrderItemMapper orderItemMapper;

    @Autowired
    ProductService productService;


    @Override
    public void add(OrderItem o) {
        orderItemMapper.insert(o);
    }

    @Override
    public void delete(int id) {
        orderItemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(OrderItem o) {
        orderItemMapper.updateByPrimaryKeySelective(o);
    }

    @Override
    public OrderItem get(int id) {
        OrderItem o = orderItemMapper.selectByPrimaryKey(id);
        setProduct(o);
        return o;
    }

    @Override
    public List<OrderItem> list() {
        OrderItemExample example = new OrderItemExample();
        example.setOrderByClause("id desc");
        return orderItemMapper.selectByExample(example);
    }

    @Override
    public void fill(List<Order> os) {
        for (Order o : os) {
            fill(o);
        }
    }

    @Override
    public void fill(Order o) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andOidEqualTo(o.getId());
        example.setOrderByClause("id desc");
        List<OrderItem> ois = orderItemMapper.selectByExample(example);
        setProduct(ois);

        float total = 0;
        int totalNumber = 0;
        for(OrderItem oi: ois){
            total += oi.getNumber() * oi.getProduct().getPromotePrice();
            totalNumber += oi.getNumber();
        }

        o.setTotal(total);
        o.setTotalNumber(totalNumber);
        o.setOrderItems(ois);
    }

    @Override
    public int getSaleCount(int product_id) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andPidEqualTo(product_id);
        List<OrderItem> orderItems = orderItemMapper.selectByExample(example);
        int result = 0;
        for(OrderItem orderItem:orderItems){
            result += orderItem.getNumber();
        }
        return result;
    }

    @Override
    public List<OrderItem> listByUser(int uid) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andUidEqualTo(uid).andOidIsNull();
        List<OrderItem> orderItems = orderItemMapper.selectByExample(example);
        setProduct(orderItems);
        return orderItems;
    }

    public void setProduct(List<OrderItem> ois){
        for(OrderItem oi:ois){
            setProduct(oi);
        }
    }

    private void setProduct(OrderItem oi){
        Product p = productService.get(oi.getPid());
        oi.setProduct(p);
    }

}
