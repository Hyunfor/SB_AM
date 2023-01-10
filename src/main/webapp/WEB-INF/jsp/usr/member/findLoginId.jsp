<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="FIND LOGINID" />
<%@ include file="../common/head.jsp"%>
<script>
	function FindLoginId__submit(form) {
			form.name.value = form.name.value.trim();
			if (form.name.value.length == 0) {
				alert('이름을 입력해주세요');
				form.name.focus();
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
		<form action="doFindLoginId" method="POST" onsubmit="FindLoginId__submit(this); return false;">
			<div class="table-box-type-1">
				<table>
					<colgroup>
						<col width="200" />
					</colgroup>

					<tbody>
						<tr>
							<th>이름</th>
							<td><input class="input input-bordered w-full max-w-xs" type="text" name="name" placeholder="이름을 입력해주세요" /></td>
						</tr>
						<tr>
							<th>이메일</th>
							<td><input class="input input-bordered w-full max-w-xs" type="text" name="email" placeholder="이메일을 입력해주세요" /></td>
						</tr>
						<tr>
							<td colspan="2">
								<input class="btn btn-active btn-ghost" type="submit" value="아이디 찾기"/>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</form>
		<div class="btns mt-2 flex justify-between">
			<button class="btn-text-link btn btn-active btn-ghost" type="button" onclick="history.back();">뒤로가기</button>
			<div>
				<a class="btn btn-active btn-ghost" href="findLoginPw">비밀번호 찾기</a>
				<a class="btn btn-active btn-ghost" href="login">로그인</a>
			</div>
		</div>
	</div>
</section>
<%@ include file="../common/foot.jsp"%>