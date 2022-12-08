package com.khd.exam.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khd.exam.demo.repository.ArticleRepository;
import com.khd.exam.demo.util.Utility;
import com.khd.exam.demo.vo.Article;
import com.khd.exam.demo.vo.ResultData;

@Service
public class ArticleService {
	
	private ArticleRepository articleRepository;

	@Autowired
	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	public Article getArticle(int id) {
		return articleRepository.getArticle(id);
	}

	public void deleteArticle(int id) {
		articleRepository.deleteArticle(id);
	}

	public void modifyArticle(int id, String title, String body) {
		articleRepository.modifyArticle(id, title, body);
	}

	public List<Article> getArticles() {
		return articleRepository.getArticles();
	}

	public ResultData<Integer> writeArticle(int memberId, String title, String body) {
		articleRepository.writeArticle(memberId, title, body);
		int id = articleRepository.getLastInsertId();
		// 서비스에서 결과를 컨트롤러를 받아감
		return ResultData.from("S-1", Utility.f("%d번 게시물이 생성되었습니다.", id), id);
	}

}

