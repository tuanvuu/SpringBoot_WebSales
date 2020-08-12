package com.estore.dao;

import java.util.List;

import com.estore.entity.Category;
import com.estore.entity.Product;

public interface CategoryDAO {
	Category findById(Integer id);
	List<Category> findAll();
	Category create(Category entity);
	void update(Category entity);
	Category delete(Integer id);
	List<Category> getRandoms();
	List<Product> findByKeyWord(String keywords);
}
