<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="Join" />
<%@ include file="../common/head.jsp"%>
<%@ include file="../common/toastUiEditorLib.jsp"%>

<script>

	let validLoginId = '';

	function MemberJoin__submit(form) {
		
		form.loginId.value = form.loginId.value.trim();
		if (form.loginId.value.length == 0) {
			alert('아이디를 입력해주세요');
			form.loginId.focus();
			
			return;
		}
		
		if (form.loginId.value != validLoginId) {
			alert(form.loginId.value + '은(는) 사용할 수 없는 아이디 입니다');
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
		
		const maxSizeMb = 10;
		const maxSize = maxSizeMb * 1024 * 1024;
		
		const profileImgFileInput = form["file__member__0__extra__profileImg__1"];
		
		if (profileImgFileInput.value) {
			if (profileImgFileInput.files[0].size > maxSize) {
				alert(maxSizeMb + "MB 이하의 파일을 업로드 해주세요");
				profileImgFileInput.focus();
				return;
			}
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
	
	function checkLoginIdDup(el) {
		$(".loginId-msg").empty();
		const form = $(el).closest('form').get(0);
		
		if (form.loginId.value.length == 0) {
			return;
		}
		
		$.get('getLoginIdDup', {
			loginId : form.loginId.value,
			ajaxMode : 'Y'
		}, function(data){
			console.log(data);
			$(".loginId-msg").html(data.data1 + '은(는) ' + data.msg);
			if (data.success) {
				validLoginId = data.data1;
			}else {
				validLoginId = '';
			}
		}, 'json');
	}
	
	function requiredData(el) {
		$(".required-msg").empty();
		const form = $(el).closest('form').get(0);
		
		if (form.loginPw.value.length == 0) {
			$(".required-msg").html('필수 정보 입니다');
		}
	}
	
	
</script>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<form action="doJoin" method="POST" enctype="multipart/form-data" onsubmit="MemberJoin__submit(this); return false;">
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
								<input class="input input-bordered w-full max-w-xs" type="text" name="loginId" placeholder="아이디를 입력해주세요" onblur="checkLoginIdDup(this);"/>
								<div class="loginId-msg text-sm mt-2 h-5 text-red-500"></div> <!-- 중복 체크 메시지 -->
							</td>
						</tr>
						<tr>
							<th>비밀번호</th>
							<td>
								<input class="input input-bordered w-full max-w-xs" type="text" name="loginPw" placeholder="비밀번호를 입력해주세요" onblur="requiredData(this);"/>
								<div class="required-msg text-sm mt-2 h-5 text-red-500"></div>
							</td>
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
							<th>프로필 이미지</th>
							<td><input accept="image/gif, image/jpeg, image/png" type="file" name="file__member__0__extra__profileImg__1" placeholder="프로필 이미지를 선택해주세요" /></td>
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