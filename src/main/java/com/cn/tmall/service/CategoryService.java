package com.cn.tmall.service;

import com.cn.tmall.pojo.Category;
import com.cn.tmall.pojo.Product;
import com.cn.tmall.util.Page;

import java.util.List;


public interface CategoryService {
    List<Category> list();

    void add(Category category);

    void delete(Integer id);

    Category get(Integer id);

    void update(Category category);
}