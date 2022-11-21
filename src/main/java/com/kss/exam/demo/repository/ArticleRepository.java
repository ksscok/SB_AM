package com.kss.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kss.exam.demo.vo.Article;

@Mapper
public interface ArticleRepository {
	
	public void writeArticle(@Param("title") String title, @Param("body") String body);
	
	public Article getArticleById(@Param("id") int id);
	
	public void modifyArticle(@Param("id") int id, @Param("title") String title, @Param("body") String body);
	
	public void deleteArticle(@Param("id") int id);
	
	public List<Article> getArticles();
	
	public int getLastInsertId();

}
