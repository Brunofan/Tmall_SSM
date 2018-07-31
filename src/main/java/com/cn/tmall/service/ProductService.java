package com.cn.tmall.service;


import com.cn.tmall.pojo.Category;
import com.cn.tmall.pojo.Product;

import java.util.List;

public interface ProductService {

    /**
     * 根据category_id返回所有对应的Product数据
     */
    List list(Integer cid);

    void add(Product p);

    /**
     * 根据id获取一条数据
     */
    Product get(Integer pid);

    void delete(Integer pid);

    void update(Product p);

    void setFirstProductImage(Product p);

    /**
     * 为多个分类填充产品集合
     */
    void fill(List<Category> cs);

    /**
     * 为一个分类填充产品集合
     */
    void fill(Category c);

    /**
     * 为多个分类填充推荐的产品集合
     */
    void fillByRow(List<Category> cs);

    /**
     * 为产品设置销售和评论数量
     */
    void setSaleAndReviewNumber(Product product);

    /**
     * 为产品设置销售和评论数量
     */
    void setSaleAndReviewNumber(List<Product> products);

    /**
     * 搜索产品
     */
    List<Product> search(String keyword);
}
