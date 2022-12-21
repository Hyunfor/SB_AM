<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="Article Detail"/>    
<%@ include file="../common/head.jsp" %>

<script>

 	const params = {};
	params.id = parseInt('${param.id}')

	function ArticleDetail__increaseViewCount(){
		$.get('doIncreaseViewCountRd', {
			id : params.id,
			ajaxMode: 'Y'
		}, function(data){
			$('.article-detail__view-count').empty().html(data.data1);
		}, 'json');
	}
	$(function(){
		// 실전코드
		/* ArticleDetail__increaseViewCount(); */
		
		// 연습코드 - 시간이 지나면 조회수 증가
		setTimeout(ArticleDetail__increaseViewCount(), 2000);
	}) 
	
	
</script>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<div class="table-box-type-1">
			<table>
				<colgroup>
					<col width="200"/>
				</colgroup>

				<tbody>
					<tr>
						<th>번호</th>
						<td><div class="badge">${article.id}</div></td>
					</tr>
					<tr>
						<th>작성 날짜</th>
						<td>${article.regDate}</td>
					</tr>
					<tr>
						<th>수정 날짜</th>
						<td>${article.updateDate}</td>
					</tr>
					<tr>
						<th>조회 수</th>
						<td><span class="badge article-detail__view-count">${article.viewCount}</span></td>
					</tr>
					<tr>
						<th>작성자</th>
						<td>${article.writerName}</td>
					</tr>
					<tr>
						<th>제목</th>
						<td>${article.title}</td>
					</tr>
					<tr>
						<th>내용</th>
						<td>${article.body}</td>
					</tr>
				</tbody>
			</table>
		</div>
		
		<div class="mt-2">
			<button class="btn-text-link btn btn-active btn-ghost"  type="button" onclick="history.back();">뒤로가기</button>
			
			<c:if test="${article.actorCanChangeData }">
				<a class="btn-text-link btn btn-active btn-ghost" href="modify?id=${article.id }">수정</a>
				<a class="btn-text-link btn btn-active btn-ghost"  onclick="if(confirm('정말 삭제하시겠습니까?') == false) return false;" href="doDelete?id=${article.id }">삭제</a>
			</c:if>
			
		</div>
		
	</div>
	
</section>

<%@ include file="../common/foot.jsp"%>