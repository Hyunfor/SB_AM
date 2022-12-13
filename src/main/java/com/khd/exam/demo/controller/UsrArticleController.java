package com.khd.exam.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.khd.exam.demo.service.ArticleService;
import com.khd.exam.demo.util.Utility;
import com.khd.exam.demo.vo.Article;
import com.khd.exam.demo.vo.ResultData;
import com.khd.exam.demo.vo.Rq;


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
	public ResultData<Article> doAdd(HttpServletRequest req, String title, String body) { // 리턴 타입을 Article로 정하면 DT에 꽂혀서 출력
		
		Rq rq = new Rq(req);
		
		if(rq.getLoginedMemberId() == 0) { // rq 객체에 값이 씌워지지 못 한 경우 . 0 일경우 세션에 아무것도 없다.
			return ResultData.from("F-A", "로그인 후 이용해주세요.");
		}
		
		if(Utility.empty(title)) { // 유효성 검사(공백)
			return ResultData.from("F-1", "제목을 입력해주세요.");
		}
		if(Utility.empty(body)) { 
			return ResultData.from("F-2", "내용을 입력해주세요.");
		}
		
		ResultData<Integer> writeArticleRd = articleService.writeArticle(rq.getLoginedMemberId(), title, body);
			
		Article article = articleService.getArticle((int)writeArticleRd.getData1()); // Data는 Object라 int로 형변환

		return ResultData.from(writeArticleRd.getResultCode(), writeArticleRd.getMsg(), "article", article);
	}
	
	@RequestMapping("/usr/article/list")
	public String showList(Model model) {
		
		List<Article> articles = articleService.getArticles();
		
		model.addAttribute("articles", articles); // model에게 articles 속성 추가
		
		return "usr/article/list";
	}
	
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody 
	public String doDelete(HttpServletRequest req, int id) {
		
		Rq rq = new Rq(req);
		
		if(rq.getLoginedMemberId() == 0) { // rq 객체에 값이 씌워지지 못 한 경우 . 0 일경우 세션에 아무것도 없다.
			return  Utility.jsHistoryBack("로그인 후 이용해주세요.");
		}
		
		Article article = articleService.getArticle(id);

		if (article == null) {
			return Utility.jsHistoryBack(Utility.f("%번 게시물은 존재하지 않습니다.", id));
		}
		
		if(rq.getLoginedMemberId() != article.getMemberId()) { // 권한체크
			return Utility.jsHistoryBack("해당 게시물에 대한 권한이 없습니다.");
		}
		
		articleService.deleteArticle(id);
																		// 경로
		return Utility.jsReplace(Utility.f("게시물을 삭제했습니다.", id), "list");
	} 
	

	@RequestMapping("/usr/article/doModify")
	@ResponseBody 
	public ResultData<Article> doModify(HttpServletRequest req, int id, String title, String body) { 
		// Object는 모든 class의 최상위라 모든 데이터가 가능하지만 원활한 관리를 위해선 제약이 많은게 좋음
		
		Rq rq = new Rq(req);
		
		if(rq.getLoginedMemberId() == 0) { // 로그인 체크
			return ResultData.from("F-A", "로그인 후 이용해주세요.");
		}
		
		Article article = articleService.getArticle(id);
		
		// actorCanDelete , actorCanModify 같은 기능을 사용하기에 하나로 합친 기능에 포함되어있어서 지워도 됨.
//		if(article == null) { 
//			return ResultData.from("F-1", Utility.f("%번 게시물은 존재하지 않습니다.", id));
//		}
		
		// 현재 수정이 가능한가 체크
		ResultData actorCanModifyRd = articleService.actorCanMD(rq.getLoginedMemberId(), article); 
		
		if(actorCanModifyRd.isFail()) {  // 실패시에 
			return actorCanModifyRd;
		}
		
		return articleService.modifyArticle(id, title, body);
	}


	@RequestMapping("/usr/article/detail")
	public String showDetail(HttpServletRequest req, Model model, int id) { // 상세보기
		
		Rq rq = new Rq(req);
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		
		model.addAttribute("article", article);
		
		return "usr/article/detail";
	}

}
