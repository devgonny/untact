package com.sbs.untact.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.untact.util.Util;

@Controller
public class UsrHomeController {

	// http://localhost:8021/usr/home/main
	@RequestMapping("/usr/home/main")
	@ResponseBody
	public String showMain() {
		System.out.println("hello");
		return "안녕2134";
	}
	
	@RequestMapping("/usr/home/doFormTest")
	@ResponseBody
	public Map<String, Object> doFormTest(String name, int age) {
		return Util.mapOf("name", name, "age", age);
	}
	
}