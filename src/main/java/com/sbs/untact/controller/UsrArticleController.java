package com.sbs.untact.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.untact.dto.Article;
import com.sbs.untact.dto.ResultData;
import com.sbs.untact.util.Util;

@Controller
public class UsrArticleController {
	private int articlesLastId;
	private List<Article> articles;

	public UsrArticleController() {
		// 멤벼변수 초기화
		articlesLastId = 0;
		articles = new ArrayList<>();
		
		//게시물 2개생성
//		articles.add(new Article(++articlesLastId, "2020-12-12 12:12:12", "제목1", "내용1"));
//		articles.add(new Article(++articlesLastId, "2020-12-12 12:12:12", "제목2", "내용2"));
		articles.add(new Article(++articlesLastId, "2020-12-12 12:12:12", "2020-12-12 12:12:12", "제목1", "내용1"));
		articles.add(new Article(++articlesLastId, "2020-12-12 12:12:12", "2020-12-12 12:12:12", "제목2", "내용2"));
	}

	@RequestMapping("/usr/article/detail")
	@ResponseBody
	public Article showDetail(int id) {
		return articles.get(id - 1);
	}

	@RequestMapping("/usr/article/list")
	@ResponseBody
	public List<Article> showList() {
		return articles;
	}

	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public ResultData doAdd(String title, String body) {
		String regDate = Util.getNowDateStr();
		String updateDate = regDate;
				
		articles.add(new Article(++articlesLastId, regDate, updateDate, title, body));

		/*
		 * Map<String, Object> rs = new HashMap<>(); rs.put("resultCode", "S-1");
		 * rs.put("msg", "성공하였습니다."); rs.put("id", articlesLastId);
		 * 
		 * return rs;
		 */
		// mapOf를 쓰면 아래 코드로 가능!		
//		return Util.mapOf("resultCode", "S-1", "msg", "성공하였습니다.", "id", articlesLastId);
		// ResultData 클래스 생성해서 쓰면(이력서양식) 아래 코드로 가능!
		return new ResultData("S-1", "성공하였습니다", "id", articlesLastId);
	}


	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public ResultData doDelete(int id) {
		boolean deleteArticleRs = deleteArticle(id);

		Map<String, Object> rs = new HashMap<>();

		if (deleteArticleRs == false) {
			return new ResultData("F-1","해당게시물은 존재하지 않습니다.");
			
		}  
		return new ResultData("S-1", "성공하였습니다..", "id", id);
	}

	private boolean deleteArticle(int id) {
		for (Article article : articles) {
			if (article.getId() == id) {
				articles.remove(article);
				return true;
			}
		}
		return false;
	}
	
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public ResultData doModify(int id, String title, String body) {
		Article selArticle = null;
		
		for( Article article : articles) {
			if(article.getId() == id) {
				selArticle = article;
				break;
			}
		}
		
		Map<String, Object> rs = new HashMap<>();
		
		if (selArticle == null) {
			return new ResultData("f-1", String.format("%d번 게시물은 존재하지 않습니다.", id));
		} 
		
		selArticle.setUpdateDate(Util.getNowDateStr());
		selArticle.setTitle(title);
		selArticle.setBody(body);
		
		return new ResultData("s-1", String.format("%d번 게시물이 수정되었습니다.", id),"id", id);
	}
}
