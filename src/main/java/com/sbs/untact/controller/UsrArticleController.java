package com.sbs.untact.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.untact.dto.Article;
import com.sbs.untact.dto.Board;
import com.sbs.untact.dto.ResultData;
import com.sbs.untact.service.ArticleService;

@Controller
public class UsrArticleController {
	
	@Autowired
	private ArticleService articleService;
	

	@RequestMapping("/usr/article/detail")
	@ResponseBody
	public ResultData showDetail(Integer id) {
		if (id == null) {
			return new ResultData("F-1", "id를 입력해주세요.");
		}

		Article article = articleService.getForPrintArticle(id); //forprint 암기 -> 풍성한 데이터가 온다.

		if (article == null) {
			return new ResultData("F-2", "존재하지 않는 게시물번호 입니다.");
		}
		
		return new ResultData("S-1", "성공", "article", article);
	}
	@RequestMapping("/usr/article/list")
	@ResponseBody
	public ResultData showList(@RequestParam(defaultValue = "1") int boardId, String searchKeywordType, String searchKeyword, @RequestParam(defaultValue = "1") int page) {
		
		Board board = articleService.getBoard(boardId);

		if ( board == null ) {
			return new ResultData("F-1", "존재하지 않는 게시판 입니다.");
		}
		
		if (searchKeywordType != null) {
			searchKeywordType = searchKeywordType.trim();
		}
		if( searchKeywordType == null || searchKeywordType.trim().length() == 0) {
			searchKeywordType = "titleAndBody";
		}
		
		if( searchKeyword != null && searchKeyword.length() == 0) {
			searchKeyword = null;
		}
		if( searchKeyword != null) {
			searchKeyword = searchKeyword.trim();
		}
		
		if ( searchKeyword == null) {
			searchKeywordType = null;
		}
		
		int itemsInAPage = 20; //한페이지 안에 몇개를 보여줄지
		List<Article> articles = articleService.getForPrintArticles(boardId, searchKeywordType, searchKeyword, page, itemsInAPage);

		return new ResultData("S-1", "성공", "articles", articles);
	}

	@RequestMapping("/usr/article/doAddReply")
	@ResponseBody
	public ResultData doAddReply(@RequestParam Map<String, Object> param, HttpServletRequest req) {
		int loginedMemberId = (int)req.getAttribute("loginedMemberId");

		if (param.get("body") == null) {
			return new ResultData("F-1", "body를 입력해주세요.");
		}

		if (param.get("articleId") == null) {
			return new ResultData("F-1", "articleId를 입력해주세요.");
		}

		param.put("memberId", loginedMemberId);

		return articleService.addReply(param);
	}
	
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public ResultData doAdd(@RequestParam Map<String, Object> param, HttpServletRequest req) {
		int loginedMemberId = (int)req.getAttribute("loginedMemberId");
		
		if (param.get("title") == null) {
			return new ResultData("F-1", "title을 입력해주세요.");
		}
		if (param.get("body") == null) {
			return new ResultData("F-1", "body를 입력해주세요.");
		}
		
		param.put("memberId", loginedMemberId);

		return articleService.addArticle(param);
		
	}


	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public ResultData doDelete(Integer id, HttpServletRequest req) {
		int loginedMemberId = (int)req.getAttribute("loginedMemberId");

		if (id == null) {
			return new ResultData("F-1", "id를 입력해주세요.");
		}
		Article article = articleService.getArticle(id);
		
		if ( article == null) {
			return new ResultData("F-1", "해당게시물은 존재하지 않습니다.");
		}
		
		ResultData actorCanDeleteRd = articleService.getActorCanDeleteRd(article, loginedMemberId);

		if (actorCanDeleteRd.isFail()) {
			return actorCanDeleteRd;
		}
		return articleService.deleteArticle(id);
	}

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public ResultData doModify(Integer id, String title, String body, HttpServletRequest req) {
		int loginedMemberId = (int)req.getAttribute("loginedMemberId");


		if (id == null) {
			return new ResultData("F-1", "id를 입력해주세요.");
		}
		
		if (title == null) {
			return new ResultData("F-1", "title을 입력해주세요.");
		}
		if (body == null) {
			return new ResultData("F-1", "body를 입력해주세요.");
		}
		
		Article article = articleService.getArticle(id);
		
		if ( article == null) {
			return new ResultData("F-1", "해당게시물은 존재하지 않습니다.");
		}
		
		ResultData actorCanModifyRd = articleService.getActorCanModifyRd(article, loginedMemberId);

		if (actorCanModifyRd.isFail()) {
			return actorCanModifyRd;
		}

		
		return articleService.modifyArticle(id, title, body);
	}
}
