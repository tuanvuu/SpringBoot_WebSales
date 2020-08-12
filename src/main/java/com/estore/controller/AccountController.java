package com.estore.controller;

import java.io.File;
import java.io.IOException;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.estore.bean.MailInfo;
import com.estore.dao.CustomerDAO;
import com.estore.entity.Customer;
import com.estore.service.CookieService;
import com.estore.service.MailService;

@Controller
public class AccountController {
	@Autowired
	CustomerDAO dao;

	@Autowired
	HttpSession session;

	@Autowired
	CookieService cookie;

	@Autowired
	ServletContext contex;
	
	@Autowired
	MailService mail;
	
	//doi duong dan tim request
	@Autowired
	HttpServletRequest request;

	@GetMapping("/account/login")
	public String login(Model model) {
		Cookie ckid = cookie.read("userid");
		Cookie ckpass = cookie.read("pass");
		if (ckid != null) {
			String uid = ckid.getValue();
			String pw = ckpass.getValue();

			model.addAttribute("uid", uid);
			model.addAttribute("pwd", pw);
		}
		return "account/login";
	}

	@PostMapping("/account/login")
	public String login(Model model, @RequestParam("id") String id, @RequestParam("pw") String pw,
			@RequestParam(value = "rm", defaultValue = "false") boolean rm) {

		Customer user = dao.findById(id);
		if (user == null) {
			model.addAttribute("msg", "invalid username");

		} else if (!pw.equals(user.getPassword())) {
			model.addAttribute("msg", "invalid password");
		} else if (!user.getActivated()) {
			model.addAttribute("msg", "Your account is inactivated");
		} else {
			model.addAttribute("msg", "Login successfully");
			session.setAttribute("user", user);
			// ghi nho tai khoan bang cookie
			if (rm == true) {
				cookie.create("userid", user.getId(), 30);
				cookie.create("pass", user.getPassword(), 30);

			} else {
				cookie.delete("userid");
				cookie.delete("pass");

			}

		}
		return "account/login";
	}

	@RequestMapping("/account/logoff")
	public String logoff() {
		session.removeAttribute("user");
		return "redirect:/home/index";
	}

	@GetMapping("/account/register")
	public String register(Model model) {
		Customer user = new Customer();
		model.addAttribute("form", user);
		return "account/register";
	}

	@PostMapping("/account/register")
	public String register(Model model, @ModelAttribute("form") Customer user,
			@RequestParam("photo_file") MultipartFile file) throws IllegalStateException, IOException, MessagingException {

		if (file.isEmpty()) {
			user.setPhoto("user.png");

		} else {

			String dir = contex.getRealPath("/static/images/customers");
			File f = new File(dir, file.getOriginalFilename());
			file.transferTo(f);
			user.setPhoto(f.getName());
		

		}
		user.setActivated(false);
		user.setAdmin(false);
		dao.create(user);
		model.addAttribute("msg", "Register successfully" );
		
		String from ="tuanvuplbp@gmail.com";
		String to =user.getEmail();
		String subject = "Welcome";
	    String url= request.getRequestURL().toString().replace("register", "activate/" +user.getId());
		String body = "Click <a href='" + url +"'>Activate</a>";
		
		MailInfo email = new MailInfo(from, to ,subject,body);
		mail.send(email);
		return "account/register";
	}

}
