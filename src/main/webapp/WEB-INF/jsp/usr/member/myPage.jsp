<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="MYPAGE" />
<%@ include file="../common/head.jsp"%>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
	<div class="w-40 rounded-xl mb-2">
			<img class="w-full rounded-xl" src="${rq.getProfileImgUri(rq.loginedMemberId) }" onerror="${rq.getProfileFallbackImgOnErrorHtml() }" alt="" />
		</div>
		<div class="table-box-type-1">
			<table class="table table-zebra w-full">
				<colgroup>
					<col width="200" />
				</colgroup>

				<tbody>
					<tr>
						<th>가입 일</th>
						<td>${rq.loginedMember.regDate}</td>
					</tr>
					<tr>
						<th>마지막 정보 수정</th>
						<td>${rq.loginedMember.updateDate}</td>
					</tr>
					<tr>
						<th>아이디</th>
						<td>${rq.loginedMember.loginId}</td>
					</tr>
					<tr>
						<th>이름</th>
						<td>${rq.loginedMember.name}</td>
					</tr>
					<tr>
						<th>닉네임</th>
						<td>${rq.loginedMember.nickname}</td>
					</tr>
					<tr>
						<th>전화번호</th>
						<td>${rq.loginedMember.cellphoneNum }</td>
					</tr>
					<tr>
						<th>이메일</th>
						<td>${rq.loginedMember.email }</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="btns mt-2 flex justify-between">
			<button class="btn-text-link btn btn-active btn-ghost" type="button" onclick="history.back();">뒤로가기</button>
			<a class="btn-text-link btn btn-active btn-ghost" href="checkPassword">회원정보 수정</a>
		</div>
	</div>
</section>
<%@ include file="../common/foot.jsp"%>


					