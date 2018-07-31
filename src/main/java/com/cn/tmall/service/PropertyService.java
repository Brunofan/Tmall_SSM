package com.cn.tmall.service;

import com.cn.tmall.pojo.Property;

import java.util.List;

public interface PropertyService {

    void add (Property p);

    void delete(Integer id);

    void update(Property p);

    Property get(int id);

    List list(Integer cid);
}
