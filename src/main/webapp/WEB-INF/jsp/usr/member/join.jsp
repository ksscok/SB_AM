<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.kss.exam.demo.util.Ut" %>

<c:set var="pageTitle" value="회원가입" />
<%@ include file="../common/head.jspf"%>

<script>
 let submitJoinFormDone = false;
 function submitJoinForm(form) {
	 if(submitJoinFormDone) {
		 alert('처리중입니다.');
		 return;
	 }
	 
	 form.loginId.value = form.loginId.value.trim();
	 
	 if(form.loginId.value.length == 0) {
		 alert('로그인 아이디를 입력해주세요.');
		 form.loginId.focus();
		 return;
	 }
	 
	 form.loginPw.value = form.loginPw.value.trim();
	 
	 if(form.loginPw.value.length == 0) {
		 alert('비밀번호를 입력해주세요.');
		 form.loginPw.focus();
		 return;
	 }
	 
	 form.loginPwConfirm.value = form.loginPwConfirm.value.trim();
	 
	 if(form.loginPwConfirm.value.length == 0) {
		 alert('비밀번호 확인을 입력해주세요.');
		 form.loginPwConfirm.focus();
		 return;
	 }
	 
	 if(form.loginPw.value != form.loginPwConfirm.value) {
		 alert('비밀번호 확인이 일치하지 않습니다');
		 form.loginPwConfirm.focus();
		 return;
	 }
	 
	 form.name.value = form.name.value.trim();
	 
	 if(form.name.value.length == 0) {
		 alert('이름을 입력해주세요.');
		 form.loginId.focus();
		 return;
	 }
	 
	 form.nickname.value = form.nickname.value.trim();
	 
	 if(form.nickname.value.length == 0) {
		 alert('닉네임을 입력해주세요.');
		 form.nickname.focus();
		 return;
	 }
	 
	 form.email.value = form.email.value.trim();
	 
	 if(form.email.value.length == 0) {
		 alert('이메일을 입력해주세요.');
		 form.email.focus();
		 return;
	 }
	 
	 form.cellphoneNo.value = form.cellphoneNo.value.trim();
	 
	 if(form.cellphoneNo.value.length == 0) {
		 alert('휴대폰번호를 입력해주세요.');
		 form.cellphoneNo.focus();
		 return;
	 }
	 
	 submotJoinFormDone = true;
	 form.submit();
}
</script>

<section class="mt-5">
  <div class="container mx-auto px-3">
	  <form method="POST" action="../member/doJoin" class="table-box-type-1" onsubmit="submitJoinForm(this); return false;">
	   	<input type="hidden" name="afterJoinUri" value="${param.afterJoinUri}"/>
      <table>
        <colgroup>
          <col width="200" />
        </colgroup>
        <tbody>
          <tr>
            <th>로그인 아이디</th>
            <td>
           	 <input type="text" name="loginId" class="input input-bordered" placeholder="로그인 아이디"/>		
            </td>
          </tr>
          <tr>
            <th>로그인 비밀번호</th>
            <td>
           	 <input type="password" name="loginPw" class="input input-bordered" placeholder="로그인 비밀번호"/>		
            </td>
          </tr>
          <tr>
            <th>로그인 비밀번호 확인</th>
            <td>
           	 <input type="password" name="loginPwConfirm" class="input input-bordered" placeholder="로그인 비밀번호 확인"/>		
            </td>
          </tr>
          <tr>
            <th>이름</th>
            <td>
           	 <input type="text" name="name" class="input input-bordered" placeholder="이름"/>		
            </td>
          </tr>
          <tr>
            <th>닉네임</th>
            <td>
           	 <input type="text" name="nickname" class="input input-bordered" placeholder="닉네임"/>		
            </td>
          </tr>
          <tr>
            <th>이메일</th>
            <td>
           	 <input type="text" name="email" class="input input-bordered" placeholder="이메일"/>		
            </td>
          </tr>
          <tr>
            <th>핸드폰번호</th>
            <td>
           	 <input type="text" name="cellphoneNo" class="input input-bordered" placeholder="핸드폰번호"/>		
            </td>
          </tr>
          <tr>
            <th>비고</th>
            <td>
              <input type="submit" class="btn btn-primary" value="회원가입" />
              <button type="button" class="btn btn-outline btn-success" onclick="history.back();">뒤로가기</button>
            </td>
          </tr>
        </tbody>
      </table>
		</form>		
  </div>
</section>

<%@ include file="../common/foot.jspf"%>