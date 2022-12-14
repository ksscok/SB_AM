package com.kss.exam.demo.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.kss.exam.demo.service.GenFileService;
import com.kss.exam.demo.service.MemberService;
import com.kss.exam.demo.util.Ut;
import com.kss.exam.demo.vo.Member;
import com.kss.exam.demo.vo.ResultData;
import com.kss.exam.demo.vo.Rq;

@Controller
public class UsrMemberController {	
	private MemberService memberService;
	private GenFileService genFileService;
	private Rq rq;
	
	public UsrMemberController(MemberService memberService, GenFileService genFileService, Rq rq) {
		this.memberService = memberService;
		this.genFileService = genFileService;
		this.rq = rq;
	}

	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public String doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNo, String email,  @RequestParam(defaultValue = "/") String afterLoginUri, MultipartRequest multipartRequest) {
		if ( Ut.empty(loginId) ) {
			return rq.jsHistoryBack("F-1", "loginId(을)를 입력해주세요.");
		}
		
		if ( Ut.empty(loginPw) ) {
			return rq.jsHistoryBack("F-2", "loginPw(을)를 입력해주세요.");
		}
		
		if ( Ut.empty(name) ) {
			return rq.jsHistoryBack("F-3", "name(을)를 입력해주세요.");
		}
		
		if ( Ut.empty(nickname) ) {
			return rq.jsHistoryBack("F-4", "nickname(을)를 입력해주세요.");
		}
		
		if ( Ut.empty(cellphoneNo) ) {
			return rq.jsHistoryBack("F-5", "cellphoneNo(을)를 입력해주세요.");
		}
		
		if ( Ut.empty(email) ) {
			return rq.jsHistoryBack("F-6", "email(을)를 입력해주세요.");
		}
		
		ResultData<Integer> joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNo, email);

		if ( joinRd.isFail() ) {
			return rq.jsHistoryBack(joinRd.getResultCode(), joinRd.getMsg());
		}

		int newMemberId = (int)joinRd.getBody().get("id");

		String afterJoinUri = "../member/login?afterLoginUri=" +  Ut.getUriEncoded(afterLoginUri);

		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		
		for (String fileInputName : fileMap.keySet()) {
            MultipartFile multipartFile = fileMap.get(fileInputName);
            if ( multipartFile.isEmpty() == false ) {
                genFileService.save(multipartFile, newMemberId);
            }
        }
		
		return rq.jsReplace("회원가입이 완료되었습니다. 로그인 후 이용해주세요.", afterJoinUri);
	}
	
	@RequestMapping("/usr/member/getLoginIdDup")
	@ResponseBody
	public ResultData getLoginIdDup(String loginId) {
		if ( Ut.empty(loginId) ) {
			return ResultData.from("F-1","loginId(을)를 입력해주세요.");
		}

		Member oldMember = memberService.getMemberByLoginId(loginId);

		if (oldMember != null) {
			return ResultData.from("F-A", "해당 아이디는 이미 사용중입니다.", "loginId", loginId);
		}

		return ResultData.from("S-A", "사용가능한 로그인아이디 입니다.", "loginId", loginId);
	}
	
	@RequestMapping("/usr/member/join")
	public String showJoin() {
		return "usr/member/join";
	}
	
	@RequestMapping("/usr/member/login")
	public String showLogin() {
		return "usr/member/login";
	}
	
	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	public String doLogin(String loginId, String loginPw, @RequestParam(defaultValue = "/") String afterLoginUri) {
		
		if(Ut.empty(loginId)) {
			return rq.jsHistoryBack("loginId를 입력해주세요.");
		}
		if(Ut.empty(loginPw)) {
			return rq.jsHistoryBack("loginPw를 입력해주세요.");
		}
		
		Member member = memberService.getMemberByLoginId(loginId);
		
		if(member == null) {
			return rq.jsHistoryBack("존재하지 않는 아이디입니다.");
		}
		if(member.getLoginPw().equals(Ut.sha256(loginPw)) == false) {
			return rq.jsHistoryBack("비밀번호가 일치하지 않습니다.");
		}
		
		rq.login(member);
		
		String msg = Ut.f("%s님 환영합니다.", member.getNickname());
		
		boolean isUsingTempPassword = memberService.isUsingTempPassword(member.getId());
		
		if(isUsingTempPassword) {
			msg = "임시 비밀번호를 변경해주세요.";
			afterLoginUri = "/usr/member/myPage";
		}
		
		return rq.jsReplace(msg, afterLoginUri);
	}
	
	@RequestMapping("/usr/member/doLogout")
	@ResponseBody
	public String doLogout(@RequestParam(defaultValue = "/") String afterLogoutUri) {
		
		if(rq.isLogined() == false) {
			return rq.jsHistoryBack("로그인 되어있지 않습니다.");
		}
		
		rq.logout();
		
		return rq.jsReplace("로그아웃 되었습니다.", afterLogoutUri);
	}
	
	@RequestMapping("/usr/member/findLoginId")
	public String findLoginId() {
		return "usr/member/findLoginId";
	}
	
	@RequestMapping("/usr/member/doFindLoginId")
	@ResponseBody
	public String doFindLoginId(String name, String email, @RequestParam(defaultValue="/") String afterFindLoginIdUri) {
		if(Ut.empty(name)) {
			return rq.jsHistoryBack("name을 입력해주세요.");
		}
		if(Ut.empty(email)) {
			return rq.jsHistoryBack("email을 입력해주세요.");
		}
		
		Member oldMember = memberService.getMemberByNameAndEmail(name, email);
		
		if(oldMember == null) {
			return rq.jsHistoryBack("해당 회원은 존재하지 않습니다.");
		}
		
		return rq.jsReplace(Ut.f("회원님의 아이디는 [%s] 입니다.", oldMember.getLoginId()), afterFindLoginIdUri);
		
	}
	
	@RequestMapping("/usr/member/findLoginPw")
	public String findLoginPw() {
		return "usr/member/findLoginPw";
	}
	
	@RequestMapping("/usr/member/doFindLoginPw")
	@ResponseBody
	public String doFindLoginPw(String loginId, String email, @RequestParam(defaultValue="/") String afterFindLoginPwUri) {
		if(Ut.empty(loginId)) {
			return rq.jsHistoryBack("loginId(을)를 입력해주세요.");
		}
		if(Ut.empty(email)) {
			return rq.jsHistoryBack("email(을)를 입력해주세요.");
		}
		
		Member member = memberService.getMemberByLoginId(loginId);
		
		if(member == null) {
			return rq.jsHistoryBack("일치하는 회원이 존재하지 않습니다.");
		}
		
		if(member.getEmail().equals(email) == false) {
			return rq.jsHistoryBack("일치하는 회원이 존재하지 않습니다.");
		}
		
		ResultData notifyTempLoginPwByEmailRs = memberService.notifyTempLoginPwByEmail(member);

		return rq.jsReplace(notifyTempLoginPwByEmailRs.getMsg(), afterFindLoginPwUri);		
	}
	
	@RequestMapping("/usr/member/myPage")
	public String showMyPage() {
		return "usr/member/myPage";
	}
	
	@RequestMapping("/usr/member/checkPassword")
	public String showCheckPassword() {
		return "usr/member/checkPassword";
	}
	
	@RequestMapping("/usr/member/doCheckPassword")
	@ResponseBody
	public String doCheckPassword(String loginPw, String replaceUri) {
		if ( Ut.empty(loginPw) ) {
			return rq.jsHistoryBack("loginPw(을)를 입력해주세요.");
		}

		if ( rq.getLoginedMember().getLoginPw().equals(Ut.sha256(loginPw)) == false) {
			return rq.jsHistoryBack("비밀번호가 일치하지 않습니다.");
		}
		
		if(replaceUri.equals("../member/modify")) {
			String memberModifyAuthKey = memberService.genMemberModifyAuthKey(rq.getLoginedMemberId());
			
			replaceUri += "?memberModifyAuthKey=" + memberModifyAuthKey;
		}

		return rq.jsReplace("", replaceUri);
	}
	
	@RequestMapping("/usr/member/modify")
	public String showModify(String memberModifyAuthKey) {
		if(Ut.empty(memberModifyAuthKey)) {
			return rq.historyBackJsOnView("memberModifyAuthKey(이)가 필요합니다.");
		}
		
		ResultData checkMemberModifyAuthKeyRd = memberService.checkMemberModifyAuthKey(rq.getLoginedMemberId(), memberModifyAuthKey);
		
		if(checkMemberModifyAuthKeyRd.isFail()) {
			return rq.historyBackJsOnView(checkMemberModifyAuthKeyRd.getMsg());
		}
		
		return "usr/member/modify";
	}
	
	@RequestMapping("/usr/member/doModify")
	@ResponseBody
	public String doModify(String memberModifyAuthKey, int id, String loginPw, String name, String nickname, String email, String cellphoneNo) {
		
		if(Ut.empty(memberModifyAuthKey)) {
			return rq.historyBackJsOnView("memberModifyAuthKey(이)가 필요합니다.");
		}
		ResultData checkMemberModifyAuthKeyRd = memberService.checkMemberModifyAuthKey(rq.getLoginedMemberId(), memberModifyAuthKey);
		
		if(checkMemberModifyAuthKeyRd.isFail()) {
			return rq.historyBackJsOnView(checkMemberModifyAuthKeyRd.getMsg());
		}
		if(Ut.empty(loginPw)) {
			loginPw = null;
		}
		if(Ut.empty(name)) {
			return rq.jsHistoryBack("name(을) 입력해주세요.");
		}
		if(Ut.empty(nickname)) {
			return rq.jsHistoryBack("nickname(을) 입력해주세요.");
		}
		if(Ut.empty(email)) {
			return rq.jsHistoryBack("email(을) 입력해주세요.");
		}
		if(Ut.empty(cellphoneNo)) {
			return rq.jsHistoryBack("cellphoneNo(을) 입력해주세요.");
		}
		
		ResultData modifyRd = memberService.modify(id, loginPw, name, nickname, email, cellphoneNo);
		
		return rq.jsReplace(modifyRd.getMsg(), Ut.f("../member/myPage"));
	}
}