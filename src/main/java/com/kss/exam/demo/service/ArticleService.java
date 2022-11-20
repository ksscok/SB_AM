package com.kss.exam.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kss.exam.demo.repository.ArticleRepository;
import com.kss.exam.demo.vo.Article;

@Service
public class ArticleService {
	
	private ArticleRepository articleRepository;
	
	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
		articleRepository.makeTestData();
	}
	
	public Article writeArticle(String title, String body) {
		return articleRepository.writeArticle(title, body);
	}
	
	public Article getArticleById(int id) {
		return articleRepository.getArticleById(id);
	}
	
	public void modifyArticle(int id, String title, String body) {
		articleRepository.modifyArticle(id, title, body);
	}
	
	public void removeArticle(int id) {
		articleRepository.removeArticle(id);
	}
	
	public List<Article> getArticles() {
		return articleRepository.getArticles();
	}

}
