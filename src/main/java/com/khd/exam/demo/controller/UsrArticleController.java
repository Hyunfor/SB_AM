package com.khd.exam.demo.controller;

import java.util.List;

import com.khd.exam.demo.service.ArticleService;
import com.khd.exam.demo.vo.Article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UsrArticleController {
	
	private ArticleService articleService;
	// 의존성 주입 - 객체만들지 않아도 됨
	@Autowired 
	public UsrArticleController(ArticleService articleService){
		this.articleService = articleService;
	}
	
// 액션 메서드
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody 
	public Article doAdd(String title, String body) {
		int id = articleService.writeArticle(title, body);

		Article article = articleService.getArticle(id);

		return article;
	}
	
	@RequestMapping("/usr/article/getArticles")
	@ResponseBody 
	public List<Article> getArticles() {
		
		return articleService.getArticles();
	}
	
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody 
	public String doDelete(int id) {
		
		Article article = articleService.getArticle(id);

		if (article == null) {
			return id + "번 게시물은 존재하지 않습니다.";
		}
		
		articleService.deleteArticle(id);
		
		return id + "번 게시물을 삭제했습니다.";
	} 
	

	@RequestMapping("/usr/article/doModify")
	@ResponseBody 
	public Object doModify(int id, String title, String body) { 
		// Object는 모든 class의 최상위라 모든 데이터가 가능하지만 원활한 관리를 위해선 제약이 많은게 좋음
		
		Article article = articleService.getArticle(id);
		
		if(article == null) {
			return id + "번 게시물은 존재하지 않습니다.";
		}
		
		articleService.modifyArticle(id, title, body);
		
		return article;
	}


	@RequestMapping("/usr/article/getArticle")
	@ResponseBody 
	public Object getArticle(int id) { { // 상세보기
		
		Article article = articleService.getArticle(id);
		
		if (article == null) {
			return id + "번 게시물은 존재하지 않습니다.";
		}
		
		return article;
	}

	
}
