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

	public List<Article> getArticles(int boardId) {
		return articleRepository.getArticles(boardId);
	}

	public ResultData<Integer> writeArticle(int memberId, int boardId, String title, String body) {
		articleRepository.writeArticle(memberId, boardId, title, body);
		int id = articleRepository.getLastInsertId();
		// 서비스에서 결과를 컨트롤러를 받아감
		return ResultData.from("S-1", Utility.f("%d번 게시물이 생성되었습니다.", id), "id", id);
	}

//	public ResultData actorCanModify(int loginedMemberId, Article article) {
//		
//		if(loginedMemberId != article.getMemberId()) { // 권한체크
//			return ResultData.from("F-B", "해당 게시물에 대한 권한이 없습니다.");
//		}
//		
//		return ResultData.from("S-1", " 수정 가능 ");
//	}
//	
//	private ResultData actorCanDelete(int loginedMemberId, Article article) {
//		
//		if(article == null) { // null인 경우
//			return ResultData.from("F-1", Utility.f("해당 게시물은 존재하지 않습니다."));
//		}
//		
//		if(loginedMemberId != article.getMemberId()) { // 권한체크
//			return ResultData.from("F-B", "해당 게시물에 대한 권한이 없습니다.");
//		}
//		
//		return ResultData.from("S-1", " 삭제 가능 ");
//	}
//	
	// actorCanDelete , actorCanModify 같은 기능을 사용하기에 하나로 합침
	public ResultData actorCanMD(int loginedMemberId, Article article) {
		
		if(article == null) { // null인 경우
			return ResultData.from("F-1", Utility.f("해당 게시물은 존재하지 않습니다."));
		}
		
		if(loginedMemberId != article.getMemberId()) { // 권한체크
			return ResultData.from("F-B", "해당 게시물에 대한 권한이 없습니다.");
		}
		
		return ResultData.from("S-1", "가능 ");
	}


	public Article getForPrintArticle(int loginedMemberId, int id) {
		
		Article article = articleRepository.getForPrintArticle(id);
		
		actorCanChangeData(loginedMemberId, article); // 검증
		
		return article;
	}

	private void actorCanChangeData(int loginedMemberId, Article article) {
		
		if(article == null) { // null인 경우
			return;
		}
		
		ResultData actorCanChangeDataRd = actorCanMD(loginedMemberId, article);
		article.setActorCanChangeData(actorCanChangeDataRd.isSuccess());
	}

	public int getArticlesCount(int boardId) {
		
		return articleRepository.getArticlesCount(boardId);
	}


}

