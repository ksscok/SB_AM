package com.kss.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.kss.exam.demo.vo.Article;

@Mapper
public interface ArticleRepository {
	
	public Article writeArticle(String title, String body);
	
	@Select("SELECT * FROM article WHERE id = ${id}")
	public Article getArticleById(@Param("id") int id);
	
	public void modifyArticle(int id, String title, String body);
	
	public void removeArticle(int id);
	
	public List<Article> getArticles();

}
