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
		
		makeTestData();
	}
	
	
// 서비스 메서드
	private void makeTestData() {
		for(int i = 1; i <= 10; i++) {
			String title = "제목" + i;
			String body = "내용" + i;
			
			writeArticle(title, body);
		}
		
	}
	
	private Article getArticle(int id) {
		
		for(Article article : articles) {
			if(article.getId() == id) {// 데이터 변질을 막기위해 get, set을 사용
				return article;
			} 
		}
		
		return null;
	}
	
	private Article writeArticle(String title, String body) { // doAdd, makeTestData 의 중복코드를 하나로 합치기
		int id = lastArticldId + 1;
		
		Article article = new Article(id, title, body);
		
		articles.add(article);
		lastArticldId = id;
		
		return article;
	}
	
	private void deleteArticle(int id) {
		Article article = getArticle(id);
		
		articles.remove(article);
	}
	
	private void modifyArticle(int id) {
		Article article = getArticle(id);
		
		articles.remove(article);
		
	}

	
// 액션 메서드
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody 
	public Article doAdd(String title, String body) {
		Article article = writeArticle(title, body);
		return article;
	}
	
	@RequestMapping("/usr/article/getArticles")
	@ResponseBody 
	public List<Article> getArticles() {
		
		return articles;
	}
	
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody 
	public String doDelete(int id) {
		
		Article article = getArticle(id);
		
		if(article == null) {
			return id + "번 게시물은 존재하지 않습니다.";
		}
		
		deleteArticle(id);
		
		return id + "번 게시물을 삭제했습니다.";
	}
	

	@RequestMapping("/usr/article/doModify")
	@ResponseBody 
	public String doModify(int id) {
		
		Article article = getArticle(id);
		
		if(article == null) {
			return id + "번 게시물은 존재하지 않습니다.";
		}
		
		modifyArticle(id);
		
		return id + "번 게시물을 수정했습니다.";
	}


	@RequestMapping("/usr/article/getArticle")
	@ResponseBody 
	public String getArticle() {
		
		wrtieArticle(id);
		
		return id + "번 게시물을 게시했습니다.";
	}

	
}
