package com.khd.exam.demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.khd.exam.demo.vo.Article;

@Component
public class ArticleRepository{
	
	// 인스턴스 변수
	private int lastArticleId;
	private List<Article> articles;
	
	// 생성자
	public ArticleRepository() {
		this.lastArticleId = 0;
		this.articles = new ArrayList<>();
		
		makeTestData();
	}
	
// 서비스 메서드
	public void makeTestData() {
		for(int i = 1; i <= 10; i++) {
			String title = "제목" + i;
			String body = "내용" + i;
			
			writeArticle(title, body);
		}
		
	}
	
	public Article writeArticle(String title, String body) {
		int id = lastArticleId + 1;

		Article article = new Article(id, title, body);

		articles.add(article);
		lastArticleId = id;

		return article;
	}

	public Article getArticle(int id) {
		for (Article article : articles) {
			if (article.getId() == id) { // 데이터 변질을 막기위해 get, set을 사용
				return article;
			}
		}
		return null;
	}

	public void deleteArticle(int id) {
		Article article = getArticle(id);

		articles.remove(article);
	}

	public void modifyArticle(int id, String title, String body) {
		Article article = getArticle(id); // get는 데이터를 가져오는것.

		article.setTitle(title); // set는 데이터를 변경하는것
		article.setBody(body);
	}

	public List<Article> getArticles() {
		return articles;
	}
	
}

