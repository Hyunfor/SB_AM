package com.khd.exam.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.khd.exam.demo.service.ArticleService;
import com.khd.exam.demo.service.BoardService;
import com.khd.exam.demo.util.Utility;
import com.khd.exam.demo.vo.Article;
import com.khd.exam.demo.vo.Board;
import com.khd.exam.demo.vo.ResultData;
import com.khd.exam.demo.vo.Rq;


@Controller
public class UsrArticleController {
	
	private ArticleService articleService;
	private BoardService boardService;
	// 의존성 주입 - 객체만들지 않아도 됨
	@Autowired 
	public UsrArticleController(ArticleService articleService, BoardService boardService){
		this.articleService = articleService;
		this.boardService = boardService;
	}
	
// 액션 메서드
	@RequestMapping("/usr/article/write")
	public String showWrite() {
		return "usr/article/write";
	}
	
	@RequestMapping("/usr/article/doWrite")
	@ResponseBody 					// rq에서 꺼내서 출력
	public String doWrite(HttpServletRequest req, String title, String body) { // 리턴 타입을 Article로 정하면 DT에 꽂혀서 출력
		
		Rq rq = (Rq) req.getAttribute("rq"); // Rq형식으로 형변환 후 꺼내야함
		
		if(Utility.empty(title)) { // 유효성 검사(공백)
			return Utility.jsHistoryBack("제목을 입력해주세요.");
		}
		if(Utility.empty(body)) { 
			return Utility.jsHistoryBack("내용을 입력해주세요.");
		}
		
		ResultData<Integer> writeArticleRd = articleService.writeArticle(rq.getLoginedMemberId(), title, body);
			
		int id = (int) writeArticleRd.getData1();
		
		return Utility.jsReplace(Utility.f("%d번 글이 생성되었습니다", id), Utility.f("detail?id=%d", id));
	}
	
	@RequestMapping("/usr/article/list")
	public String showList(Model model, int boardId) {
		
		Board board = boardService.getBoardById(boardId);
		
		List<Article> articles = articleService.getArticles(boardId);
		
		model.addAttribute("board", board); // model에게 articles 속성 추가
		model.addAttribute("articles", articles); // model에게 articles 속성 추가
		
		return "usr/article/list";
	}
	
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody 
	public String doDelete(HttpServletRequest req, int id) {
		
		Rq rq = (Rq) req.getAttribute("rq"); // Rq형식으로 형변환 후 꺼내야함

		Article article = articleService.getArticle(id);

		ResultData actorCanMDRd = articleService.actorCanMD(rq.getLoginedMemberId(), article); 
		
		if(actorCanMDRd.isFail()) {  // 실패시에 
			return Utility.jsHistoryBack(actorCanMDRd.getMsg());
		}
		
		articleService.deleteArticle(id);
																		// 경로
		return Utility.jsReplace(Utility.f("게시물을 삭제했습니다.", id), "list");
	} 
	
	@RequestMapping("/usr/article/modify") // 수정 요청시 수정 페이지를 보이게 jsp에 요청하는 역할		
	public String showModify(HttpServletRequest req, Model model, int id) { 
		
		Rq rq = (Rq) req.getAttribute("rq"); // Rq형식으로 형변환 후 꺼내야함
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(),id);
		
		// 현재 수정이 가능한가 체크
		ResultData actorCanMDRd = articleService.actorCanMD(rq.getLoginedMemberId(), article); 
		
		if(actorCanMDRd.isFail()) {  // 실패시에 
			return rq.jsReturnOnView(actorCanMDRd.getMsg(), true);
		}
		
		model.addAttribute("article", article);
		
		return "/usr/article/modify";
	}

	@RequestMapping("/usr/article/doModify") // 실질적 수정역할
	@ResponseBody 
	public String doModify(HttpServletRequest req, int id, String title, String body) { 
	
		Rq rq = (Rq) req.getAttribute("rq"); // Rq형식으로 형변환 후 꺼내야함
		
		Article article = articleService.getArticle(id);
		
		// 현재 수정이 가능한가 체크
		ResultData actorCanMDRd = articleService.actorCanMD(rq.getLoginedMemberId(), article); 
		
		if(actorCanMDRd.isFail()) {  // 실패시에 
			return Utility.jsHistoryBack(actorCanMDRd.getMsg());
		}
		
		articleService.modifyArticle(id, title, body);
		
		return Utility.jsReplace(Utility.f("게시물을 수정했습니다.", id), Utility.f("detail?id=%d", id));
	}


	@RequestMapping("/usr/article/detail")
	public String showDetail(HttpServletRequest req, Model model, int id) { // 상세보기
		
		Rq rq = (Rq) req.getAttribute("rq"); // Rq형식으로 형변환 후 꺼내야함
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		
		model.addAttribute("article", article); // model 객체에서 article을 넘기기
		
		return "usr/article/detail";
	}

}
