package com.kss.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kss.exam.demo.vo.Article;

@Mapper
public interface ArticleRepository {
	
    @Insert("INSERT INTO article SET regDate = NOW(), updateDate = NOW(), title = ${title}, `body` = ${body}")
	public void writeArticle(@Param("title") String title, @Param("body") String body);
	
	@Select("SELECT * FROM article WHERE id = ${id}")
	public Article getArticleById(@Param("id") int id);
	
	@Update("UPDATE article SET title = ${title}, body = ${body}")
	public void modifyArticle(@Param("id") int id, @Param("title") String title, @Param("body") String body);
	
	@Delete("DELETE FROM article WHERE id = ${id}")
	public void removeArticle(@Param("id") int id);
	
	@Select("SELECT * FROM article ORDER BY id DESC")
	public List<Article> getArticles();
	
	@Select("SELECT last_insert_id()")
	public int getLastInsertId();

}
