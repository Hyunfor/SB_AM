package com.khd.exam.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	private Rq rq;
	
	// 의존성 주입 - 객체만들지 않아도 됨
	@Autowired 
	public UsrArticleController(ArticleService articleService, BoardService boardService, Rq rq){
		this.articleService = articleService;
		this.boardService = boardService;
		this.rq = rq;
	}
	
// 액션 메서드
	@RequestMapping("/usr/article/write")
	public String showWrite() {
		return "usr/article/write";
	}
	
	@RequestMapping("/usr/article/doWrite")
	@ResponseBody 					// rq에서 꺼내서 출력
	public String doWrite(int boardId, String title, String body) { // 리턴 타입을 Article로 정하면 DT에 꽂혀서 출력
		
		if(boardId != 1 && boardId !=2 ) { // 게시판 번호 검증
			return Utility.jsHistoryBack("존재하지 않는 게시판입니다.");
		}
		
		if(Utility.empty(title)) { // 유효성 검사(공백)
			return Utility.jsHistoryBack("제목을 입력해주세요.");
		}
		if(Utility.empty(body)) { 
			return Utility.jsHistoryBack("내용을 입력해주세요.");
		}
		
		ResultData<Integer> writeArticleRd = articleService.writeArticle(rq.getLoginedMemberId(), boardId, title, body);
			
		int id = (int) writeArticleRd.getData1();
		
		return Utility.jsReplace(Utility.f("%d번 글이 생성되었습니다", id), Utility.f("detail?id=%d", id));
	}
	
	@RequestMapping("/usr/article/list")
	public String showList(Model model, @RequestParam(defaultValue = "1") int boardId,
			@RequestParam(defaultValue = "1") int page, 
			@RequestParam(defaultValue = "title") String searchKeywordTypeCode,
			@RequestParam(defaultValue = "") String searchKeyword) {
		
		if(page <= 0) { // 페이징이 0보다 작을 경우
			return rq.jsReturnOnView("페이지번호가 올바르지 않습니다.", true);
		}
		
		Board board = boardService.getBoardById(boardId);
		
		if(board == null) { // 게시판이 존재하지 않을 경우
			return rq.jsReturnOnView("존재하지 않는 게시판입니다.", true);
		}
		
		int articlesCount = articleService.getArticlesCount(boardId, searchKeywordTypeCode, searchKeyword);
		
		int itemsInAPage = 10;
		
//		ceil은 double 타입이므로 int로 형변환하고, 둘중 하나를 double로 형변환하면 됨
		int pagesCount = (int) Math.ceil((double) articlesCount / itemsInAPage);

		List<Article> articles = articleService.getArticles(boardId,  searchKeywordTypeCode, searchKeyword, itemsInAPage, page);
		
		model.addAttribute("board", board); 
		model.addAttribute("articles", articles); // model에게 articles 속성 추가
		model.addAttribute("articlesCount", articlesCount);
		model.addAttribute("boardId", boardId);
		model.addAttribute("page", page);
		model.addAttribute("pagesCount", pagesCount);
		model.addAttribute("searchKeywordTypeCode", searchKeywordTypeCode);
		model.addAttribute("searchKeyword", searchKeyword);
		
		return "usr/article/list";
	}
	
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody 
	public String doDelete(int id) {

		Article article = articleService.getArticle(id);

		ResultData actorCanMDRd = articleService.actorCanMD(rq.getLoginedMemberId(), article); 
		
		if(actorCanMDRd.isFail()) {  // 실패시에 
			return Utility.jsHistoryBack(actorCanMDRd.getMsg());
		}
		
		articleService.deleteArticle(id);
																		// 경로
		return Utility.jsReplace(Utility.f("게시물을 삭제했습니다.", id), "list?boardId=1");
	} 
	
	@RequestMapping("/usr/article/modify") // 수정 요청시 수정 페이지를 보이게 jsp에 요청하는 역할		
	public String showModify(Model model, int id) { 
		
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
	public String doModify(int id, String title, String body) { 
		
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
	public String showDetail(Model model, int id) { // 상세보기

		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		
		model.addAttribute("article", article); // model 객체에서 article을 넘기기
		
		return "usr/article/detail";
	}
	
	@RequestMapping("/usr/article/doIncreaseViewCouuntRd")
	@ResponseBody
	public ResultData<Integer> doIncreaseViewCouuntRd(int id) { // 조회수
		
		ResultData<Integer> increaseViewCountRd = articleService.increaseViewCount(id);
		
		if(increaseViewCountRd.isFail()) { // 실패한 경우
			return increaseViewCountRd;
		}
		
		return ResultData.from(increaseViewCountRd.getResultCode(), increaseViewCountRd.getMsg(), "viewCount", articleService.getArticleViewCount(id) );
	}


}
