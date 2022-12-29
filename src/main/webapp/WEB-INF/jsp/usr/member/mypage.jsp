<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="MyPage"/>    
<%@ include file="../common/head.jsp" %>

<script>

 	const params = {};
 	
 	// params.id - 게시글 번호
	params.id = parseInt('${param.id}'); 
	
	function ArticleDetail__increaseViewCount() {
		
		// localStorage에 정보를 담아둘 수 있음. ex) 자동 로그인 기능
		const localStorageKey = 'article__' + params.id + '__alreadyView';
		
		if(localStorage.getItem(localStorageKey)) {
			return;
		}
		
		localStorage.setItem(localStorageKey, true);
		
		$.get('doIncreaseViewCountRd', {
			id : params.id,
			ajaxMode: 'Y'
		}, function(data){
			$('.article-detail__view-count').empty().html(data.data1);
		}, 'json');
	}
	
	function ReactionPoint__getReactionPoint() {
		
		$.get('../reactionPoint/getReactionPoint', {
			id : params.id,
			ajaxMode : 'Y'
		}, function(data){
			if(data.data1.sumReactionPoint > 0){ // 리액션 버튼만 교체하는 역할
				let goodBtn = $('#goodBtn'); 
				goodBtn.removeClass('btn-outline');
				goodBtn.prop('href', '../reactionPoint/delReactionPoint?id=${article.id}&point=1')
			}else if(data.data1.sumReactionPoint < 0){
				let badBtn = $('#badBtn');
				badBtn.removeClass('btn-outline');
 				badBtn.prop('href', '../reactionPoint/delReactionPoint?id=${article.id}&point=-1')
			}
		}, 'json');
	}
	
	$(function(){
		// 실전코드
		/* ArticleDetail__increaseViewCount(); */
		ReactionPoint__getReactionPoint();
		
		// 연습코드 - 시간이 지나면 조회수 증가
		setTimeout(ArticleDetail__increaseViewCount(), 2000);
	}) 
	
	
</script>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3 pb-5 border-bottom-line">
		<div class="table-box-type-1">
			<table>
				<colgroup>
					<col width="200"/>
				</colgroup>

				<tbody>
					<tr>
						<th>가입 날짜</th>
						<td>${member.regDate}</td>
					</tr>
					<tr>
						<th>수정 날짜</th>
						<td>${member.updateDate}</td>
					</tr>
					<tr>
						<th>아이디</th>
						<td>${member.memberId}</td>
					</tr>
					<tr>
						<th>이름</th>
						<td>${member.name}</td>
					</tr>
					<tr>
						<th>이메일</th>
						<td>${member.email }</td>
					</tr>
				</tbody>
			</table>
				
		</div>
		
		<div class="mt-2">
			<button class="btn-text-link btn btn-active btn-ghost"  type="button" onclick="history.back();">목록으로</button>
			
			<c:if test="${member.actorCanChangeData }">
				<a class="btn-text-link btn btn-active btn-ghost" href="modify?id=${member.id }">수정</a>
				<a class="btn-text-link btn btn-active btn-ghost"  onclick="if(confirm('정말 탈퇴 하시겠습니까?') == false) return false;" href="doDelete?id=${member.id }">회원 탈퇴</a>
			</c:if>
			
		</div>
		
	</div>
	
</section>

<%@ include file="../common/foot.jsp"%>