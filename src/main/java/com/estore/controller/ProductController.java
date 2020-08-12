package com.estore.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.estore.bean.MailInfo;
import com.estore.dao.CategoryDAO;
import com.estore.dao.ProductDAO;
import com.estore.entity.Category;
import com.estore.entity.Product;
import com.estore.service.CookieService;
import com.estore.service.MailService;

@Controller
public class ProductController {
	@Autowired
	ProductDAO dao;

	@Autowired
	CookieService cookie;

	@Autowired
	MailService mailService;

	@RequestMapping("/product/list-by-category/{cid}")
	public String listByCategory(Model model, @PathVariable("cid") Integer categoryId) {
		// Category cate = dao.findById(categoryId);
		// List<Product> list = cate.getProducts();
		List<Product> list = dao.findByCategoryId(categoryId); // dung thuc thể ket hop để lấy tat ca san pham theo loai
		model.addAttribute("list", list);
		return "product/list";
	}

	@RequestMapping("/product/list-by-keywords")
	public String listByCategory(Model model, @RequestParam("keywords") String keywords) {

		List<Product> list = dao.findByKeywords(keywords);
		// List<Product> list = dao.findByCategoryId(categoryId); // dung thuc thể ket
		// hop để lấy tat ca san pham theo loai
		model.addAttribute("list", list);
		return "product/list";
	}

	@RequestMapping("/product/detail/{id}")
	public String deTail(Model model, @PathVariable("id") Integer id) {
		Product product = dao.findById(id);
		model.addAttribute("prod", product);

		// moi lan vo xem dem tang len 1
		product.setViewCount(product.getViewCount() + 1);
		dao.update(product);

		// tim mat hang cung loai
		List<Product> list = dao.findByCategoryId(product.getCategory().getId());
		model.addAttribute("list", list);

		// lay san pham yeu thich
		Cookie favo = cookie.read("favo");
		if (favo != null) {
			String ids = favo.getValue();
			List<Product> list2 = dao.findByFavorite(ids);
			model.addAttribute("favo", list2);
		}

		// list Product đã xem
		Cookie view = cookie.read("viewEd");
		String value = id.toString();
		if (view != null) {
			value = view.getValue();
			value += "," + id.toString();
		}
		cookie.create("viewEd", value, 10);
		List<Product> view_list = dao.findByFavorite(value);
		model.addAttribute("view_list", view_list);

		return "product/detail";
	}

	@RequestMapping("/product/list-favo")
	public String listFavo(Model model) {
		Cookie favo = cookie.read("favo");
		if (favo != null) {
			String ids = favo.getValue();
			List<Product> favoProduct = dao.findByFavorite(ids); // tim mat hang yeu thich trong cookie
			model.addAttribute("list", favoProduct);
		}
		return "product/list";
	}

	@ResponseBody
	@RequestMapping("/product/add-to-favo/{id}")
	public boolean addFavorite(Model model, @PathVariable("id") Integer id) {
		Cookie favo = cookie.read("favo"); // lay cookie tu client
		String value = id.toString();
		if (favo != null) {
			value = favo.getValue();
			if (!value.contains(id.toString())) {
				value += "," + id.toString();
			} else {
				return false;
			}
		}
		cookie.create("favo", value, 30);
		return true;
	}

	@ResponseBody
	@RequestMapping("/product/send-to-friend")
	public boolean SendEmailToFriend(Model model, MailInfo info, HttpServletRequest request) {
		// Send mail
		// Class MailInfo đã nhận các giá trị từ các thuoc tinh id,to,from ben ajax
		// request để lấy đường dẫn url hiên tại
		info.setSubject("===THÔNG TIN HÀNG HÓA===");

		try {
			String id = request.getParameter("id");
			String link = request.getRequestURL().toString().replace("send-to-friend", "detail/" + id);
			System.out.println("link :" + link);
			info.setBody(info.getBody() + "<hr><a href='" + link + "'>Xem chi tiết..</a>");
			mailService.send(info);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;

		}

	}

	@RequestMapping("/product/special/{id}")
	public String listSpecial(Model model, @PathVariable("id") Integer id) {
		List<Product> list = dao.findBySpecial(id);
		model.addAttribute("list", list);

		return "product/list";

	}

}
