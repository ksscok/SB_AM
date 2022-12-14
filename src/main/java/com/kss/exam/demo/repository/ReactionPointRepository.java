package com.kss.exam.demo.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ReactionPointRepository {
	
	@Select("""
			<script>
			SELECT COUNT(RP.point) AS a
			FROM reactionPoint AS RP
			WHERE RP.relTypeCode = 'article'
			AND RP.relId = #{id}
			AND RP.memberId = #{actorId}
			</script>
			""")
	public int actorCanMakeReactionPoint(int actorId, int id);
}
