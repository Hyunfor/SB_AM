package com.khd.exam.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.khd.exam.demo.repository.MemberRepository;
import com.khd.exam.demo.util.Utility;
import com.khd.exam.demo.vo.Member;
import com.khd.exam.demo.vo.ResultData;

@Service
public class MemberService {
	
	@Value("${custom.siteMainUri}")
	private String siteMainUri;
	@Value("${custom.siteName}")
	private String siteName;
	
	private MemberRepository memberRepository;
	private AttrService attrService;
	private MailService mailService;

	@Autowired
	public MemberService(MemberRepository memberRepository, AttrService attrService, MailService mailService) {
		this.memberRepository = memberRepository;
		this.attrService = attrService;
		this.mailService = mailService;
	}

	public ResultData<Integer> doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email) {
		
	// 로그인아이디 중복체크
			Member existsMember = getMemberByLoginId(loginId);
			
			if(existsMember != null) {
				return ResultData.from("F-7", Utility.f("이미 사용중인 아이디(%s)입니다", loginId));
			}
			
			// 이름 + 이메일 중복체크
			existsMember = getMemberByNameAndEmail(name, email);
			
			if(existsMember != null) {
				return ResultData.from("F-8", Utility.f("이미 사용중인 이름(%s)과 이메일(%s)입니다", name, email));
			}
			
			memberRepository.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);
			int id = memberRepository.getLastInsertId();
			return ResultData.from("S-1", "회원가입이 완료되었습니다", "id", id);
		}

		public Member getMemberById(int id) {
			return memberRepository.getMemberById(id);
		}
	
	public Member getMemberByLoginId(String loginId) {
		return memberRepository.getMemberByLoginId(loginId);
	}
	
	public Member getMemberByNameAndEmail(String name, String email) {
		return memberRepository.getMemberByNameAndEmail(name, email);
	}
	
	public void doModify(int loginedMemberId, String nickname, String cellphoneNum, String email) {
		memberRepository.doModify(loginedMemberId, nickname, cellphoneNum, email);
	}

	public void doPassWordModify(int loginedMemberId, String loginPw) {
		memberRepository.doPassWordModify(loginedMemberId, loginPw);
	}

	public String genMemberModifyAuthKey(int loginedMemberId) {

		String memberModifyAuthKey = Utility.getTempPassword(10);

		attrService.setValue("member", loginedMemberId, "extra", "memberModifyAuthKey", memberModifyAuthKey, Utility.getDateStrLater(60 * 5));

		return memberModifyAuthKey;
	}

	public ResultData checkMemberModifyAuthKey(int loginedMemberId, String memberModifyAuthKey) {
		String saved = attrService.getValue("member", loginedMemberId, "extra", "memberModifyAuthKey");

		if (!saved.equals(memberModifyAuthKey)) {
			return ResultData.from("F-1", "일치하지 않거나 만료된 인증코드입니다");
		}

		return ResultData.from("S-1", "정상 인증코드입니다");
	}
	
	public ResultData notifyTempLoginPwByEmail(Member member) {

		String title = "[" + siteName + "] 임시 패스워드 발송";
		String tempPassword = Utility.getTempPassword(8);
		String body = "<h1>임시 패스워드 : " + tempPassword + "</h1>";
		body += "<a href=\"" + siteMainUri + "/usr/member/login\" target=\"_blank\">로그인 하러가기</a>";

		ResultData sendRd = mailService.send(member.getEmail(), title, body);

		if (sendRd.isFail()) {
			return sendRd;
		}

		setTempPassword(member, tempPassword);

		return ResultData.from("S-1", "계정의 이메일주소로 임시 패스워드가 발송되었습니다");
	}

	private void setTempPassword(Member member, String tempPassword) {
		memberRepository.doPassWordModify(member.getId(), Utility.sha256(tempPassword));
	}

	

}
