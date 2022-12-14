package com.kss.exam.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kss.exam.demo.repository.ArticleRepository;
import com.kss.exam.demo.repository.ReactionPointRepository;
import com.kss.exam.demo.util.Ut;
import com.kss.exam.demo.vo.Article;
import com.kss.exam.demo.vo.ResultData;

@Service
public class ReactionPointService {
	private ReactionPointRepository reactionPointRepository;
	
	public ReactionPointService(ReactionPointRepository reactionPointRepository) {
		this.reactionPointRepository = reactionPointRepository;
	}
	
	public boolean actorCanMakeReactionPoint(int actorId, int id) {
		return reactionPointRepository.actorCanMakeReactionPoint(actorId, id) == 0;
	}
}