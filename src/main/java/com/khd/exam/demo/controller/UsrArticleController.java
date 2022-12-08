package com.khd.exam.demo.controller;

import java.util.List;

import com.khd.exam.demo.service.ArticleService;
import com.khd.exam.demo.util.Utility;
import com.khd.exam.demo.vo.Article;
import com.khd.exam.demo.vo.ResultData;

import jakarta.servlet.http.HttpSession;

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
	public ResultData<Article> doAdd(HttpSession httpSession, String title, String body) { // 리턴 타입을 Article로 정하면 DT에 꽂혀서 출력
		
		if(httpSession.getAttribute("loginedMemberId") != null) { // 로그인 체크
			return ResultData.from("F-A", "로그인 후 이용해주세요.");
		}
		
		int loginedMemberId = (int) httpSession.getAttribute("loginedMemberId"); // 가져와서 쓰려면 형변환
		
		if(Utility.empty(title)) { // 유효성 검사(공백)
			return ResultData.from("F-1", "제목을 입력해주세요.");
		}
		if(Utility.empty(body)) { 
			return ResultData.from("F-2", "내용을 입력해주세요.");
		}
		
		ResultData<Integer> writeArticleRd = articleService.writeArticle(loginedMemberId, title, body);
			
		Article article = articleService.getArticle((int)writeArticleRd.getData1()); // Data는 Object라 int로 형변환

		return ResultData.from(writeArticleRd.getResultCode(), writeArticleRd.getMsg(), article);
	}
	
	@RequestMapping("/usr/article/getArticles")
	@ResponseBody 
	public ResultData<List<Article>> getArticles() {
		
		List<Article> articles = articleService.getArticles();
		
		return ResultData.from("S-1", "게시물 리스트", articles);
	}
	
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody 
	public ResultData<Integer> doDelete(HttpSession httpSession, int id) {
		
		if(httpSession.getAttribute("loginedMemberId") != null) { // 로그인 체크
			return ResultData.from("F-A", "로그인 후 이용해주세요.");
		}
		
		
		Article article = articleService.getArticle(id);

		if (article == null) {
			return ResultData.from("F-1", Utility.f("%번 게시물은 존재하지 않습니다.", id));
		}
		
		articleService.deleteArticle(id);
		
		return ResultData.from("S-1", Utility.f("게시물을 삭제했습니다.", id), id);
	} 
	

	@RequestMapping("/usr/article/doModify")
	@ResponseBody 
	public ResultData<Integer> doModify(HttpSession httpSession, int id, String title, String body) { 
		// Object는 모든 class의 최상위라 모든 데이터가 가능하지만 원활한 관리를 위해선 제약이 많은게 좋음
		
		if(httpSession.getAttribute("loginedMemberId") != null) { // 로그인 체크
			return ResultData.from("F-A", "로그인 후 이용해주세요.");
		}
		
		Article article = articleService.getArticle(id);
		
		if(article == null) {
			return ResultData.from("F-1", Utility.f("%번 게시물은 존재하지 않습니다.", id));
		}
		
		articleService.modifyArticle(id, title, body);
		
		return ResultData.from("S-1", Utility.f("%번 게시물을 수정했습니다.", id), id);
	}


	@RequestMapping("/usr/article/getArticle")
	@ResponseBody 
	public ResultData<Article> getArticle(int id) { // 상세보기
		
		Article article = articleService.getArticle(id);
		
		if (article == null) {
			return ResultData.from("F-1", Utility.f("%d번 게시물은 존재하지 않습니다", id));
//			return id + "번 게시물은 존재하지 않습니다.";
		}
		
		return ResultData.from("S-1", Utility.f("%d번 게시물 입니다", id), article);
	}

}
