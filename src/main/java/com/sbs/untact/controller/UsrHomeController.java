package com.sbs.untact.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UsrHomeController {

	// http://localhost:8021/usr/home/main
	@RequestMapping("/usr/home/main")
	@ResponseBody
	public String showMain() {
		System.out.println("hello");
		return "안녕2134";
	}
}