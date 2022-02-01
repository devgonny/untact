package com.sbs.untact.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.untact.dto.Article;
import com.sbs.untact.dto.ResultData;
import com.sbs.untact.service.ArticleService;
import com.sbs.untact.util.Util;

@Controller
public class UsrArticleController {
	
	@Autowired
	private ArticleService articleService;
	

	@RequestMapping("/usr/article/detail")
	@ResponseBody
	public Article showDetail(int id) {
		Article article = articleService.getArticle(id);
		return article;
	}

	@RequestMapping("/usr/article/list")
	@ResponseBody
	public List<Article> showList() {
		return articleService.getArticles();
	}

	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public ResultData doAdd(String title, String body) {
		if (title != null) {
			return new ResultData("F-1", "title을 입력해주세요.");
		}
		if (body != null) {
			return new ResultData("F-1", "body를 입력해주세요.");
		}
		
		ResultData rsData = articleService.add(title, body);
		
		return rsData;

		/*
		 * Map<String, Object> rs = new HashMap<>(); rs.put("resultCode", "S-1");
		 * rs.put("msg", "성공하였습니다."); rs.put("id", articlesLastId);
		 * 
		 * return rs;
		 */
		// mapOf를 쓰면 아래 코드로 가능!		
//		return Util.mapOf("resultCode", "S-1", "msg", "성공하였습니다.", "id", articlesLastId);
		// ResultData 클래스 생성해서 쓰면(이력서양식) 아래 코드로 가능!
		
	}


	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public ResultData doDelete(int id) {
		
		Article article = articleService.getArticle(id);
		
		if ( article == null) {
			return new ResultData("F-1", "해당게시물은 존재하지 않습니다.");
		}
		return articleService.deleteArticle(id);
	}

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public ResultData doModify(Integer id, String title, String body) {
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
		return articleService.modify(id, title, body);
	}
}
