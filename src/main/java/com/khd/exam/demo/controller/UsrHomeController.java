package com.khd.exam.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UsrHomeController {
	public int count;
	
	public UsrHomeController() {
		count = -1;
	}
	
	@RequestMapping("/usr/home/getMap")
	@ResponseBody 
	public Map<String, Object> getMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("철수나이", 22);
		map.put("영희나이", 25);
		
		return map;
	}
	
	@RequestMapping("/usr/home/getList")
	@ResponseBody 
	public List<String> getList() {
		List<String> list = new ArrayList<>();
		list.add("철수나이");
		list.add("영희나이");
		
		return list;
	}
	
	
	@RequestMapping("/usr/home/main4")
	@ResponseBody 
	public int showMain4() {
		
		return this.count++;
	}
	
	@RequestMapping("/usr/home/main5")
	@ResponseBody 
	public String showMain5(int cnt) {
		this.count = cnt;
		return "count의 값이 " + this.count + " 으로 초기화 되었습니다.";
	}
	
}

