package com.cn.tmall.service;

import com.cn.tmall.pojo.ProductImage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface ProductImageService {

    final String TYPE_SINGLE = "type_single";
    final String TYPE_DETAIL = "type_detail";

    List list(int pid, String type);

    void add(ProductImage pi);

    ProductImage get(Integer id);

    void delete(Integer id);
}
