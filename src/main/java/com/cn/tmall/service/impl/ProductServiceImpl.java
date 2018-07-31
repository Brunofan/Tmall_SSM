package com.cn.tmall.service.impl;

import com.cn.tmall.mapper.ProductMapper;
import com.cn.tmall.pojo.Category;
import com.cn.tmall.pojo.Product;
import com.cn.tmall.pojo.ProductExample;
import com.cn.tmall.pojo.ProductImage;
import com.cn.tmall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductMapper productMapper;
	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductImageService productImageService;
	@Autowired
	OrderItemService orderItemService;
	@Autowired
	ReviewService reviewService;

	@Override
	public List<Product> list(Integer cid) {
		ProductExample example = new ProductExample();
		example.createCriteria().andCidEqualTo(cid);
		example.setOrderByClause("id desc");
//      System.out.println(productMapper);
		List<Product> ps = productMapper.selectByExample(example);
		setCategory(ps);
		setFirstProductImage(ps);
		return ps;
	}


	@Override
	public void add(Product p) {
		productMapper.insert(p);
	}

	@Override
	public Product get(Integer pid) {
		Product p = productMapper.selectByPrimaryKey(pid);
		setFirstProductImage(p);
		setCategory(p);
		return p;
	}

	public void setCategory(List<Product> ps) {
		for (Product p : ps) {
			setCategory(p);
		}
	}

	public void setCategory(Product p) {
		Category c = categoryService.get(p.getCid());
		p.setCategory(c);
	}

	@Override
	public void delete(Integer pid) {
		productMapper.deleteByPrimaryKey(pid);
	}

	@Override
	public void update(Product p) {
		productMapper.updateByPrimaryKeySelective(p);
	}

	@Override
	public void setFirstProductImage(Product p) {
		List<ProductImage> pis = productImageService.list(p.getId(), ProductImageService.TYPE_SINGLE);
		if (!pis.isEmpty()) {
			ProductImage pi = pis.get(0);
			p.setFirstProductImage(pi);
		}
	}

	public void setFirstProductImage(List<Product> ps) {
		for (Product p : ps) {
			setFirstProductImage(p);
		}
	}

	@Override
	public void fill(List<Category> cs) {
		for (Category c : cs) {
			fill(c);
		}
	}

	@Override
	public void fill(Category c) {
		List<Product> ps = list(c.getId());
		c.setProducts(ps);
	}

	/**
	 * 为多个分类填充推荐产品集合，即把分类下的产品集合，
	 * 按照8个为一行，拆成多行，以利于后续页面上进行显示
	 */
	@Override
	public void fillByRow(List<Category> cs) {
		int productNumberEachRow = 8;
		for (Category c : cs) {
			List<Product> products = c.getProducts();
			List<List<Product>> productsByRow = new ArrayList<>();
			for (int i = 0; i < products.size(); i += productNumberEachRow) {
				int size = i + productNumberEachRow;
				size = (size > products.size()) ? products.size() : size;
				List<Product> productsOfEachRow = products.subList(i, size);
				productsByRow.add(productsOfEachRow);
			}
			c.setProductsByRow(productsByRow);
		}
	}

	@Override
	public void setSaleAndReviewNumber(Product product) {
		int saleCount = orderItemService.getSaleCount(product.getId());
		product.setSaleCount(saleCount);

		int reviewCount = reviewService.getCount(product.getId());
		product.setReviewCount(reviewCount);
	}

	@Override
	public void setSaleAndReviewNumber(List<Product> products) {
		for (Product p : products) {
			setSaleAndReviewNumber(p);
		}
	}

	@Override
	public List<Product> search(String keyword) {
		ProductExample example = new ProductExample();
		example.createCriteria().andNameLike("%" + keyword + "%");
		example.setOrderByClause("id desc");
		List<Product> products = productMapper.selectByExample(example);
		setFirstProductImage(products);
		setCategory(products);
		return products;
	}
}
