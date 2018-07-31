package com.cn.tmall.service.impl;

import com.cn.tmall.mapper.ReviewMapper;
import com.cn.tmall.pojo.Review;
import com.cn.tmall.pojo.ReviewExample;
import com.cn.tmall.pojo.User;
import com.cn.tmall.service.ReviewService;
import com.cn.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ReviewService 实现类
 */
@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewMapper reviewMapper;

    @Autowired
    UserService userService;

    @Override
    public void add(Review review) {
        reviewMapper.insert(review);
    }

    @Override
    public void delete(int id) {
        reviewMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Review review) {
        reviewMapper.updateByPrimaryKeySelective(review);
    }

    @Override
    public Review get(int id) {
        return reviewMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Review> listByProductId(Integer product_id) {
        ReviewExample example = new ReviewExample();
        example.createCriteria().andPidEqualTo(product_id);
        example.setOrderByClause("id desc");

        List<Review> list = reviewMapper.selectByExample(example);
        setUser(list);
        return list;
    }

    public void setUser(List<Review> reviews){
        for(Review review:reviews){
            setUser(review);
        }
    }

    private void setUser(Review review){
        int uid = review.getUid();
        User user = userService.get(uid);
        review.setUser(user);
    }

    @Override
    public int getCount(int product_id) {
        return listByProductId(product_id).size();
    }
}
