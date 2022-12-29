<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="게시물 내용" />
<%@ include file="../common/head.jspf" %>

<section class="mt-5">
	<div class="container mx-auto px-3">
		<form class="table-box-type-1" method="POST" action="../member/doLogin">
		<input type="hidden" name="afterLoginUri" value="${param.afterLoginUri}"/>
			<table>
				<colgroup>
					<col width="200"/>	
				</colgroup>
				<tbody>
					<tr>
						<th>로그인 아이디</th>
						<td>
							<input name="loginId" class="w-96 input input-bordered w-full max-w-xs" type="text" placeholder="로그인 아이디" />
						</td>
					</tr>
					<tr>
						<th>로그인 비밀번호</th>
						<td>
							<input name="loginPw" class="w-96 input input-bordered w-full max-w-xs" type="password" placeholder="로그인 비밀번호" />
						</td>
					</tr>
					<tr>
						<th>로그인</th>
						<td>
							<input type="submit" class="btn btn-primary" value="로그인"/>
							<button type="button" class="btn btn-outline btn-success" onclick="history.back();">뒤로가기</button>
						</td>
					</tr>
					<tr>
						<th>비고</th>
						<td>
							<a href="${rq.findLoginIdUri}" type="submit" class="btn btn-link">아이디 찾기</a>
							<a href="${rq.findLoginPwUri}" type="submit" class="btn btn-link">비밀번호 찾기</a>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
</section>
	
<%@ include file="../common/foot.jspf" %>
