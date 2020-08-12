package com.estore.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.estore.dao.ProductDAO;
import com.estore.entity.Product;

@SessionScope // Name : scopedTarget.cartService  // 1 session se co mot bean  cartService ->Tên class luật đổi chư
// trong session da có mot sesion la scopedTarget .. khi nao can thi lay ra
@Service
public class CartService {
	@Autowired
	ProductDAO dao;
    // ve mặt lưu tru dung tapp hop.. có the list set map
	
	Map<Integer, Product> map = new HashMap<>();

	public void add(Integer id) {
		Product p = map.get(id);
		if (p == null) {
			p = dao.findById(id);
			p.setQuantity(1);
			map.put(id, p);

		} else {
			p.setQuantity(p.getQuantity() + 1);
		}
	
	}

	public void remove(Integer id) {
		map.remove(id);
	}

	public void update(Integer id, int qty) {
		Product p = map.get(id); // vo gio hang lay mat hang ra
		p.setQuantity(qty);
	}

	public void clear() {
		map.clear();
	}

	//tinh tong so luong mat hang
	public int getCount() {
		Collection<Product> ps = this.getItems();
		int count = 0;
		for (Product product : ps) {
			count += product.getQuantity();
		}
		return count;

	}

	// tinh tong so tien
	public double getAmount() {
		Collection<Product> products = this.getItems();
		double amount = 0;
		for (Product product : products) {
			// tong so tien
			amount += (product.getQuantity() * product.getUnitPrice() * (1 - product.getDiscount()));
		}
		return amount;

	}

	//lay tât ca mat hang tỏng gio 
	public Collection<Product> getItems() {
		//key ->  id
		//value->product	
		return map.values();
		}

}
