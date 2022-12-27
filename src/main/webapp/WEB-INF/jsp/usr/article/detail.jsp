<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="Article Detail"/>    
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
					<tr>
						<th>추천</th>
						<td>
							<c:if test="${rq.getLoginedMemberId() == 0 }"> <!-- 로그인 여부. 로그인 하기전-->
								<span class="badge">${article.sumReactionPoint}</span> <!-- 좋아요 -->
								<span class="badge">${article.sumReactionPoint * -1}</span> <!-- 싫어요 -->
							</c:if>
							<c:if test="${rq.getLoginedMemberId() != 0 }"> <!-- 로그인 여부. 로그인 후에 좋아요, 싫어요 클릭 가능-->
								<a id="goodBtn" class="btn btn-outline btn-success btn-xs" href="../reactionPoint/doReactionPoint?id=${article.id }&relTypeCode=article&point=1">좋아요 👍 </a>
								<span class="badge">${article.goodReactionPoint}</span>
								<a id="badtn" class="btn btn-outline btn-error btn-xs" href="../reactionPoint/doReactionPoint?id=${article.id }&relTypeCode=article&point=-1">싫어요 👎 </a>
								<span class="badge">${article.badReactionPoint * -1}</span>
							</c:if>
						</td>
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

<section class="mt-5 text-xl">
	<div class="container mx-auto px-3 ">
	
		<h2>댓글</h2>
		<form action="../reply/doWrite" method="POST">
			
			<div class="mt-4 p-4 border rounded-lg border-gray-200 text-base">
				<div class="mb-2"><span>작성자</span></div>
				<textarea class="textarea textarea-bordered w-full"name="body" cols="2"  placeholder="댓글 작성" ></textarea>
				<div class="flex justify-end">
					<button class="btn btn-outline btn-info btn-sm">등록</button>
				</div>
			</div>
		
		</form>
	
	</div>

</section>

<%@ include file="../common/foot.jsp"%>