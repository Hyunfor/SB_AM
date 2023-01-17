<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ADMIN PAGE - MEMBER LIST" />
<%@ include file="../../usr/common/head.jsp"%>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<div class="mb-2 flex justify-between items-center">
			<div>
				<span>회원수 : ${membersCount }</span>
			</div>
			<form>
				<select data-value="${authLevel }" class="select select-bordered" name="authLevel">
					<option value="0">전체</option>
					<option value="3">일반</option>
					<option value="7">관리자</option>
				</select>

				<select data-value="${searchKeywordTypeCode }" class="select select-bordered" name="searchKeywordTypeCode">
					<option value="loginId,name,nickname">전체</option>
					<option value="loginId">아이디</option>
					<option value="name">이름</option>
					<option value="nickname">닉네임</option>
				</select>

				<input class="ml-2 w-84 input input-bordered" type="text" name="searchKeyword" placeholder="검색어를 입력해주세요" maxlength="20" value="${searchKeyword }" />

				<button class="ml-2 btn btn-active btn-ghost">검색</button>
			</form>
		</div>
		<c:choose>
			<c:when test="${membersCount == 0}">
				<div class="text-center py-2">조건에 일치하는 검색결과가 없습니다</div>
			</c:when>
			<c:otherwise>
				<div class="table-box-type-1">
					<table class="table w-full">
						<thead>
							<tr>
								<th>번호</th>
								<th>가입날짜</th>
								<th>수정날짜</th>
								<th>아이디</th>
								<th>이름</th>
								<th>닉네임</th>
							</tr>
						</thead>

						<tbody>
							<c:forEach var="member" items="${members}">
								<tr class="hover">
									<td>${member.id}</td>
									<td>${member.regDate.substring(2,16)}</td>
									<td>${member.updateDate.substring(2,16)}</td>
									<td>${member.loginId}</td>
									<td>${member.name}</td>
									<td>${member.nickname}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</c:otherwise>
		</c:choose>
<!-- 		<div class="mt-2 flex justify-end"> -->
<%-- 			<c:if test="${rq.getLoginedMemberId() != 0 }"> --%>
<!-- 				<a class="btn-text-link btn btn-active btn-ghost" href="/usr/article/write">WRITE</a> -->
<%-- 			</c:if> --%>
<!-- 		</div> -->
		<div class="page-menu mt-2 flex justify-center">
			<div class="btn-group">
				<c:set var="pageMenuLen" value="5" />
				<c:set var="startPage" value="${page - pageMenuLen >= 1 ? page - pageMenuLen : 1}" />
				<c:set var="endPage" value="${page + pageMenuLen <= pagesCount ? page + pageMenuLen : pagesCount}" />

				<c:set var="pageBaseUri" value="?searchKeywordTypeCode=${searchKeywordTypeCode }&searchKeyword=${searchKeyword }" />

				<c:if test="${membersCount != 0 }">
					<c:if test="${page == 1 }">
						<a class="btn btn-sm btn-disabled">«</a>
						<a class="btn btn-sm btn-disabled">&lt;</a>
					</c:if>
					<c:if test="${page > 1 }">
						<a class="btn btn-sm" href="${pageBaseUri }&page=1">«</a>
						<a class="btn btn-sm" href="${pageBaseUri }&page=${page - 1 }">&lt;</a>
					</c:if>
					<c:forEach begin="${startPage }" end="${endPage }" var="i">
						<a class="btn btn-sm ${page == i ? 'btn-active' : ''}" href="${pageBaseUri }&page=${i }">${i }</a>
					</c:forEach>
					<c:if test="${page < pagesCount }">
						<a class="btn btn-sm" href="${pageBaseUri }&page=${page + 1 }">&gt;</a>
						<a class="btn btn-sm" href="${pageBaseUri }&page=${pagesCount }">»</a>
					</c:if>
					<c:if test="${page == pagesCount }">
						<a class="btn btn-sm btn-disabled">&gt;</a>
						<a class="btn btn-sm btn-disabled">»</a>
					</c:if>
				</c:if>
			</div>
		</div>
	</div>
</section>
<%@ include file="../../usr/common/foot.jsp"%>