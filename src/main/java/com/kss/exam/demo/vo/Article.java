package com.kss.exam.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //@Getter, @Setter, @RequiredArgsConstructor, @ToString, @EqualsAndHashCode을 한꺼번에 설정
@NoArgsConstructor // Class 모든 필드 값을 파라미터로 받는 생성자를 추가합니다.
@AllArgsConstructor // Class 기본 생성자를 자동으로 추가해줍니다.
public class Article {
	private int id;
	private String regDate;
	private String updateDate;
	private int memberId;
	private String title;
	private String body;
	private int hitCount;
	
	private String extra__writerName;
	private boolean extra__actorCanDelete;
	private boolean extra__actorCanModify;
	
	public String getRegDateForPrint() {
		return regDate.substring(2, 16);
	}
	
	public String getUpdateDateForPrint() {
		return updateDate.substring(2, 16);
	}
}
