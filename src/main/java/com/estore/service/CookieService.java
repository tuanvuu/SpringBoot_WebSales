package com.estore.service;

import java.util.Base64;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CookieService {
	@Autowired
	HttpServletRequest request;
	@Autowired
	HttpServletResponse response;

	public Cookie create(String name, String value, int days) {
		String cookieValue = Base64.getEncoder().encodeToString(value.getBytes());
		Cookie cookie = new Cookie(name, cookieValue);
		cookie.setMaxAge(days * 24 * 60 * 60); // tinh theo giay
		cookie.setPath("/"); // duong dan truyen thong tat ca moi url
		response.addCookie(cookie); // gui cookie ve client luu lai
		return cookie;

	}

	public Cookie read(String name) {
		Cookie[] cookies = request.getCookies(); // lay mang cookie
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equalsIgnoreCase(name)) {
					String value = new String(Base64.getDecoder().decode(cookie.getValue()));
					cookie.setValue(value);
					return cookie;
				}
			}

		}
		return null;
	}

	public void delete(String name) {
		this.create(name, "", 0); // xóa gán lại hàm tạo cookie

	}

}
