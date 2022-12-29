package com.kss.exam.demo.vo;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.kss.exam.demo.service.MemberService;
import com.kss.exam.demo.util.Ut;

import lombok.Getter;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Rq {
	@Getter
	private boolean isLogined;
	@Getter
	private int loginedMemberId;
	@Getter
	private Member loginedMember;
	
	private HttpServletRequest req;
	private HttpServletResponse resp;
	private HttpSession session;
	private Map<String, String> paramMap;
	
	public Rq(HttpServletRequest req, HttpServletResponse resp, MemberService memberService) {
		this.req = req;
		this.resp = resp;
		this.session = req.getSession();
		
		paramMap = Ut.getParamMap(req);
		
		boolean isLogined = false;
		int loginedMemberId = 0;
		
		if(session.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int)session.getAttribute("loginedMemberId");
			loginedMember = memberService.getMemberById(loginedMemberId);
		}
		
		this.isLogined = isLogined;
		this.loginedMemberId = loginedMemberId;
		this.loginedMember = loginedMember;
		
	}
	
	public void printHistoryBackJs(String msg) {
		resp.setContentType("text/html; charset=UTF-8");
		print(Ut.jsHistoryBack(msg));
	}

	public void print(String str) {
		try {
			resp.getWriter().append(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void println(String str) {
		print(str + "\n");
	}
	
	public void login(Member member) {
		session.setAttribute("loginedMemberId", member.getId());
	}
	
	public void logout() {
		session.removeAttribute("loginedMemberId");
	}
	
	public boolean isNotLogined() {
		return !isLogined;
	}
	
	public String historyBackJsOnView(String msg) {
		req.setAttribute("msg", msg);
		req.setAttribute("historyBack", true);
		return "common/js";
	}
	
	public String historyBackJsOnView(String resultCode, String msg) {
		req.setAttribute("msg", String.format("[%s] %s", resultCode, msg));
		req.setAttribute("historyBack", true);
		return "common/js";
	}
	
	public String jsHistoryBack(String msg) {
		return Ut.jsHistoryBack(msg);
	}
	
	public String jsHistoryBack(String resultCode, String msg) {
		msg = String.format("[%s] %s", resultCode, msg);
		return Ut.jsHistoryBack(msg);
	}

	public String jsReplace(String msg, String uri) {
		return Ut.jsReplace(msg, uri);
	}
	
	public String getCurrentUri() {
		String currentUri = req.getRequestURI();
        String queryString = req.getQueryString();

        if (queryString != null && queryString.length() > 0) {
            currentUri += "?" + queryString;
        }

        return currentUri;
	}

	public String getEncodedCurrentUri() {
		return Ut.getUriEncoded(getCurrentUri());
	}
	
	public void runA() {
		System.out.println("A호출!!");
		runB();
	}

	public void runB() {
		System.out.println("B호출!!");
	}
	
	public void printReplaceJs(String msg, String uri) {
		resp.setContentType("text/html; charset=UTF-8");
		print(Ut.jsReplace(msg, uri));
	}
	
	public String getLoginUri() {
		return "../member/login?afterLoginUri=" + getAfterLoginUri();
	}
	
	public String getLogoutUri() {	
		return "../member/doLogout?afterLogoutUri=" + getAfterLogoutUri();
	}
	
	public String getFindLoginIdUri() {	
		return "../member/findLoginId?afterFindLoginIdUri=" + getAfterFindLoginIdUri();
	}

	public String getFindLoginPwUri() {	
		return "../member/findLoginPw?afterFindLoginPwUri=" + getAfterFindLoginPwUri();
	}

	public String getAfterFindLoginIdUri() {
		String requestUri = req.getRequestURI();

		return getEncodedCurrentUri();
	}

	public String getAfterFindLoginPwUri() {
		String requestUri = req.getRequestURI();

		return getEncodedCurrentUri();
	}
	
	public String getJoinUri() {	
		return "../member/join?afterJoinUri=" + getAfterJoinUri();
	}

	public String getAfterLoginUri() {
		String requestUri = req.getRequestURI();

		switch (requestUri) {
		case "/usr/member/login":
		case "/usr/member/join":
		case "/usr/member/findLoginId":
		case "/usr/member/findLoginPw":
			return Ut.getUriEncoded(Ut.getStrAttr(paramMap, "afterLoginUri", ""));
		}
		
		return getEncodedCurrentUri();
	}
	
	public String getAfterLogoutUri() {
		String requestUri = req.getRequestURI();

		// 필요하다면 활성화
		/*
		switch (requestUri) {
		case "/usr/article/write":
			return "";
		}
		*/

		return getEncodedCurrentUri();
	}
	
	public String getAfterJoinUri() {
		String requestUri = req.getRequestURI();

		// 필요하다면 활성화
		/*
		switch (requestUri) {
		case "/usr/article/write":
			return "";
		}
		*/

		return getEncodedCurrentUri();
	}
	
	public String getArticleDetailUriFromArticleList(Article article) {
		return "../article/detail?id=" + article.getId() + "&listUri=" + getEncodedCurrentUri();
	}
}
