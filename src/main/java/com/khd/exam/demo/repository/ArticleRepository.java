package com.khd.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.khd.exam.demo.vo.Article;

@Mapper
public interface ArticleRepository { // class는 사용 불가능 100% 추상메서드인 interface로 바꿔야 사용가능 , 모든 구현부 제거
	
	public void writeArticle(int memberId, int boardId, String title, String body);

	public Article getArticle(int id);
	
	public List<Article> getArticles(int boardId, int limitStart, int itemsInAPage);

	public void deleteArticle(int id);

	public void modifyArticle(int id, String title, String body);

	public int getLastInsertId();

	public Article getForPrintArticle(int id);

	public int getArticlesCount(int boardId);

}

