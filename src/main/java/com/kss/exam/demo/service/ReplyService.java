package com.kss.exam.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kss.exam.demo.repository.ReplyRepository;
import com.kss.exam.demo.util.Ut;
import com.kss.exam.demo.vo.Reply;
import com.kss.exam.demo.vo.ResultData;
import com.kss.exam.demo.vo.Rq;

@Service
public class ReplyService {
	private ReplyRepository replyRepository;
	private Rq rq;

	public ReplyService(ReplyRepository replyRepository, Rq rq) {
		this.replyRepository = replyRepository;
		this.rq = rq;
	}

	public ResultData<Integer> writeReply(int actorId, String relTypeCode, int relId, String body) {
		replyRepository.writeReply(actorId, relTypeCode, relId, body);
		int id = replyRepository.getLastInsertId();

		return ResultData.from("S-1", Ut.f("%d번 댓글이 생성되었습니다.", id), "id", id);
	}

	public List<Reply> getForPrintReplies(int actorId, String relTypeCode, int relId) {
		List<Reply> replies = replyRepository.getForPrintReplies(actorId, relTypeCode, relId);
		
		for(Reply reply : replies) {
			updateForPrintData(actorId, reply);
		}
		
		return replies;
	}

	private void updateForPrintData(int actorId, Reply reply) {
		if(reply == null) {
			return;
		}
		
		ResultData actorCanModifyRd = actorCanModify(actorId, reply);
		reply.setExtra__actorCanModify(actorCanModifyRd.isSuccess());
		
		ResultData actorCanDeleteRd = actorCanDelete(actorId, reply);
		reply.setExtra__actorCanDelete(actorCanDeleteRd.isSuccess());
	}

	private ResultData actorCanModify(int actorId, Reply reply) {
		if(reply == null) {
			return ResultData.from("F-1", "댓글이 존재하지 않습니다.");
		}
		
		if(reply.getMemberId() != actorId) {
			return ResultData.from("F-2", "권한이 없습니다.");
		}
		
		return ResultData.from("S-1", "댓글 수정이 가능합니다.");
	}

	private ResultData actorCanDelete(int actorId, Reply reply) {
		if(reply == null) {
			return ResultData.from("F-1", "댓글이 존재하지 않습니다.");
		}
		
		if(reply.getMemberId() != actorId) {
			return ResultData.from("F-2", "권한이 없습니다.");
		}
		
		return ResultData.from("S-1", "댓글 수정이 가능합니다.");
	}

	public Reply getForPrintReply(int loginedMemberId, int id) {
		return replyRepository.getForPrintReply(loginedMemberId, id);
	}

	public ResultData deleteReply(int id) {
		replyRepository.deleteReply(id);
		
		return ResultData.from("S-1", Ut.f("%d번 댓글을 삭제하였습니다.", id));
		
	}
	
	public Reply getReply(int id) {
		return replyRepository.getReply(id);
	}

	public ResultData modifyReply(int id, String body) {
		replyRepository.modifyReply(id, body);

		return ResultData.from("S-1", Ut.f("%d번 댓글을 수정하였습니다.", id));
	}
}