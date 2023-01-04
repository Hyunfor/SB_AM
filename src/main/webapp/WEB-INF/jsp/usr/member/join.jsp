<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="Join" />
<%@ include file="../common/head.jsp"%>
<%@ include file="../common/toastUiEditorLib.jsp"%>

<script>
	function MemberJoin__submit(form) {
		
		form.loginId.value = form.loginId.value.trim();
		if (form.loginId.value.length == 0) {
			alert('아이디를 입력해주세요');
			form.loginId.focus();
			
			return;
		}
		
		form.loginPw.value = form.loginPw.value.trim();
		if (form.loginPw.value.length == 0) {
			alert('비밀번호를 입력해주세요');
			form.loginPw.focus();
			
			return;
		}
		
		form.loginPwConfirm.value = form.loginPwConfirm.value.trim();
		if (form.loginPwConfirm.value.length == 0) {
			alert('비밀번호 확인을 입력해주세요');
			form.loginPwConfirm.focus();
			
			return;
		}
		
		form.name.value = form.name.value.trim();
		if (form.name.value.length == 0) {
			alert('이름을 입력해주세요');
			form.name.focus();
			
			return;
		}
		
		form.nickname.value = form.nickname.value.trim();
		if (form.nickname.value.length == 0) {
			alert('닉네임을 입력해주세요');
			form.nickname.focus();
			
			return;
		}
		
		form.cellphoneNum.value = form.cellphoneNum.value.trim();
		if (form.cellphoneNum.value.length == 0) {
			alert('전화번호를 입력해주세요');
			form.cellphoneNum.focus();
			
			return;
		}
		
		form.email.value = form.email.value.trim();
		
		if (form.email.value.length == 0) {
			alert('이메일을 입력해주세요');
			form.email.focus();
			
			return;
		}
		
		form.submit();
		
	}
</script>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<form action="doJoin" method="POST" onsubmit="MemberJoin__submit(this); return false;">
		<input type="hidden" name="body"/>
			<div class="table-box-type-1">
				<table class="table table-zebra w-full">
					<colgroup>
						<col width="200" />
					</colgroup>

					<tbody>
						<tr>
							<th>아이디</th>
							<td>
								<input class="input input-bordered w-full max-w-xs" type="text" name="loginId" placeholder="아이디를 입력해주세요" />
								<div class="loginId-msg mt-1"></div> <!-- 중복 체크 메시지 -->
							</td>
						</tr>
						<tr>
							<th>비밀번호</th>
							<td><input class="input input-bordered w-full max-w-xs" type="text" name="loginPw" placeholder="비밀번호를 입력해주세요" /></td>
						</tr>
						<tr>
							<th>비밀번호 확인</th>
							<td><input class="input input-bordered w-full max-w-xs" type="text" name="loginPwConfirm" placeholder="비밀번호를 한번 더 입력해주세요" /></td>
						</tr>
						<tr>
							<th>이름</th>
							<td><input class="input input-bordered w-full max-w-xs" type="text" name="name" placeholder="이름을 입력해주세요" /></td>
						</tr>
						<tr>
							<th>닉네임</th>
							<td><input class="input input-bordered w-full max-w-xs" type="text" name="nickname" placeholder="닉네임을 입력해주세요" /></td>
						</tr>
						<tr>
							<th>핸드폰</th>
							<td><input class="input input-bordered w-full max-w-xs" type="text" name="cellphoneNum" placeholder="핸드폰번호를 입력해주세요" /></td>
						</tr>
						<tr>
							<th>이메일</th>
							<td><input class="input input-bordered w-full max-w-xs" type="text" name="email" placeholder="이메일을 입력해주세요" /></td>
						</tr>
						<tr>
							<td colspan="2"><button class="btn btn-active btn-ghost">회원가입</button></td>
						</tr>
					</tbody>
				</table>
			</div>
		</form>
		
		<div class="btns mt-2">
			<button class="btn-text-link btn btn-active btn-ghost" type="button" onclick="history.back();">뒤로가기</button>
		</div>
		
	</div>
</section>
<%@ include file="../common/foot.jsp"%>