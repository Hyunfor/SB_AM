package com.khd.exam.demo.controller;

import java.util.ArrayList;
import java.util.List;
import com.khd.exam.demo.vo.Article;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UsrArticleController {
	
	private int lastArticldId;
	private List<Article> articles;
	
	public UsrArticleController() {
		this.lastArticldId = 0;
		this.articles = new ArrayList<>();
	}
	
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody 
	public Article doAdd(String title, String body) {
		int id = lastArticldId + 1;
		Article article = new Article(id, title, body);
		
		articles.add(article);
		lastArticldId = id;
		
		return article;
	}
	
	@RequestMapping("/usr/article/getArticles")
	@ResponseBody 
	public List<Article> getArticles() {
		
		return articles;
	}

	
}
