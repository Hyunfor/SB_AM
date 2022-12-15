<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="Login"/>    
<%@ include file="../common/head.jsp" %>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<form action="doLogin" method="POST">
			<div class="table-box-type-1">
				<table>
					<colgroup>
						<col width="200"/>
					</colgroup>

					<tbody>
						<tr>
							<th>로그인 아이디</th>
							<td><input class="w-96" type="text" name="loginId" placeholder="아이디를 입력해주세요."/></td>
						</tr>
						<tr>
							<th>로그인 비밀번호</th>
							<td><input class="w-96" type="text" name="loginPw" placeholder="비밀번호를 입력해주세요."/></td>
						</tr>
						<tr>
							<!-- button 기본속성은 submit. 다른 이벤트를 주고싶다면 꼭 type을 입력해야함.	 -->
							<td colspan="2"><button>로그인</button></td>
							<!-- colspan = 가로열 합치기, rowspan = 세로열 합치기 -->
						</tr>

					</tbody>
				</table>
			</div>
		</form>
		
		<div class="btns">
			<button class="btn-text-link"  type="button" onclick="history.back();">뒤로가기</button>		
		</div>
		
	</div>
	
</section>

<%@ include file="../common/foot.jsp"%>