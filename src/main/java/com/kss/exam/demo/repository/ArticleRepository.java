package com.kss.exam.demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.kss.exam.demo.vo.Article;

@Component
public class ArticleRepository {
	
	private int lastArticleId;
	private List<Article> articles;
	
	public ArticleRepository() {
		lastArticleId = 0;
		articles = new ArrayList<>();
	}
	
	public void makeTestData() {

		for (int i = 1; i <= 10; i++) {

			String title = "제목" + i;
			String body = "내용" + i;

			writeArticle(title, body);
		}
	}
	
	public Article writeArticle(String title, String body) {

		int id = lastArticleId + 1;

		Article article = new Article(id, title, body);
		articles.add(article);
		
		lastArticleId = id;

		return article;
	}
	
	public Article getArticleById(int id) {
		
		for(Article article : articles) {
			if(article.getId() == id) {
				return article;
			}
		}
		return null;
	}
	
	public void modifyArticle(int id, String title, String body) {
		Article article = getArticleById(id);
		
		article.setTitle(title);
		article.setBody(body);
	}
	
	public void removeArticle(int id) {
		Article article = getArticleById(id);
		
		articles.remove(article);
	}
	
	public List<Article> getArticles() {
		return articles;
	}

}
