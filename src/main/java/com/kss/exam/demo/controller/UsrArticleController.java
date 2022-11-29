package com.kss.exam.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kss.exam.demo.service.ArticleService;
import com.kss.exam.demo.util.Ut;
import com.kss.exam.demo.vo.Article;
import com.kss.exam.demo.vo.ResultData;

@Controller
public class UsrArticleController {

	@Autowired
	private ArticleService articleService;

	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public ResultData<Article> doAdd(HttpSession httpSession, String title, String body) {
		boolean isLogined = false;
		int memberId = -1;
		
		if(httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			memberId = (int)httpSession.getAttribute("loginedMemberId");
		}
		if(isLogined == false) {
			return ResultData.from("F-A", "로그인 되어있지 않습니다.");
		}
		if(Ut.empty(title)) {
			return ResultData.from("F-1", "title을 입력해주세요.");
		}
		if(Ut.empty(body)) {
			return ResultData.from("F-2", "body를 입력해주세요.");
		}
		
		ResultData<Integer> writeArticleRd = articleService.writeArticle(memberId, title, body);
		int id = writeArticleRd.getData1();
		
		Article article = articleService.getForPrintArticle(memberId, id);
		
		return ResultData.newData(writeArticleRd, "article", article);
	}

	@RequestMapping("/usr/article/list")
	public String showList(HttpSession httpSession, Model model) {
		boolean isLogined = false;
		int memberId = -1;
		
		if(httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			memberId = (int)httpSession.getAttribute("loginedMemberId");
		}
		
		List<Article> articles = articleService.getForPrintArticles(memberId);
		
		model.addAttribute("articles", articles);
		return "usr/article/list";
	}
	
	@RequestMapping("/usr/article/detail")
	public String showDetail(HttpSession httpSession, Model model, int id) {
		boolean isLogined = false;
		int memberId = -1;
		
		if(httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			memberId = (int)httpSession.getAttribute("loginedMemberId");
		}
		
		Article article = articleService.getForPrintArticle(memberId, id);
		
		model.addAttribute("article", article);
		return "usr/article/detail";
	}
	
	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public ResultData<Article> getArticle(HttpSession httpSession, int id) {
		boolean isLogined = false;
		int memberId = -1;
		
		if(httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			memberId = (int)httpSession.getAttribute("loginedMemberId");
		}
		
		Article article = articleService.getForPrintArticle(memberId, id);
		
		if(article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물은 존재하지 않습니다.", id));
		}
		return ResultData.from("S-1", Ut.f("%d번 게시물입니다.", id), "article", article);
	}
	
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(HttpSession httpSession, int id) {
		boolean isLogined = false;
		int memberId = -1;
		
		Article article = articleService.getForPrintArticle(memberId, id);
		
		if(article == null) {
			return Ut.jsHistoryBack(Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}
		
		if(httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			memberId = (int)httpSession.getAttribute("loginedMemberId");
		}
		if(isLogined == false) {
			return Ut.jsHistoryBack("로그인 후 이용해주세요.");
		}
		
		if(article.getMemberId() != memberId) {
			return Ut.jsHistoryBack("권한이 없습니다.");
		}
		
		articleService.deleteArticle(id);
		
		return Ut.jsReplace(Ut.f("%d번 게시물을 삭제하였습니다.", id), "../article/list");
	}
	
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public ResultData<Integer> doModify(HttpSession httpSession, int id, String title, String body) {
		boolean isLogined = false;
		int memberId = -1;
		
		Article article = articleService.getForPrintArticle(memberId, id);
		
		if(article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물은 존재하지 않습니다.", id));
		}
		
		if(httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			memberId = (int)httpSession.getAttribute("loginedMemberId");
		}
		if(isLogined == false) {
			return ResultData.from("F-2", "로그인 후 이용해주세요.");
		}
		if(article.getMemberId() != memberId) {
			return ResultData.from("F-3", "권한이 없습니다.");
		}
		
		articleService.modifyArticle(id, title, body);
		
		return ResultData.from("S-1", Ut.f("%d번 게시물을 수정하였습니다.", id), "id", id);
	}
	
}
