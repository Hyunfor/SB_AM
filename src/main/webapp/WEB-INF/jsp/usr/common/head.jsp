<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html data-theme="light">
<head>
<meta charset="UTF-8">
<title>${pageTitle}</title>

<!-- 파비콘 불러오기 -->
<link rel="shortcut icon" type="image/x-icon" sizes="16x16" href="/favicon.ico" />
<!-- 테일윈드 불러오기 -->
<!-- 노말라이즈, 라이브러리 -->
<link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2/dist/tailwind.min.css" rel="stylesheet" type="text/css" />
<!-- 데이지 UI -->
<link href="https://cdn.jsdelivr.net/npm/daisyui@2.31.0/dist/full.css" rel="stylesheet" type="text/css" />
<!-- 제이쿼리 불러오기 -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<!-- 폰트어썸 불러오기 -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css" />
<!-- 커스텀 공통 CSS -->
<link rel="stylesheet" href="/resource/common.css" />
<!-- 공통 JS -->
<script src="/resource/common.js" defer="defer"></script>

<script>
	function Theme__toggle() {
		const theme = localStorage.getItem("theme") ?? "light";
	
		if( theme == 'light'){
			localStorage.setItem("theme","dark");
		}
		else {
		    localStorage.setItem("theme", "light");
		}
		
		location.reload();	
	}
	
	function Theme__applyTo(themeName) {
		$('html').attr('data-theme', themeName);
	}
	
	function Theme__init() {
		const theme = localStorage.getItem("theme") ?? "light";
		Theme__applyTo(theme);
	}
	
	Theme__init();
</script>

</head>
<body>
	
	<header>
		<div class="h-20 flex container mx-auto text-3xl">
			<a class="h-full px-3 flex items-center" href="/"><span>로고</span></a>
			<div class="flex-grow"></div>
			<ul class="flex">
				<li>
					<a class="h-full px-3 flex items-center theme-toggle" href="javascript:Theme__toggle();">
						<span><i class="fa-solid fa-sun"></i></span>
						<span><i class="fa-regular fa-sun"></i></span>
					</a>
				</li>
				<li class="hover:underline"><a class="h-full px-3 flex items-center" href="/"><span>HOME</span></a></li>
				<li class="hover:underline"><a class="h-full px-3 flex items-center" href="/usr/article/list?boardId=1"><span>NOTICE</span></a></li>
				<li class="hover:underline"><a class="h-full px-3 flex items-center" href="/usr/article/list?boardId=2"><span>FREE</span></a></li>				
				<c:if test="${rq.getLoginedMemberId() == 0 }"> <!-- 로그인 x 상태 -->
					<li class="hover:underline"><a class="h-full px-3 flex items-center" href="/usr/member/login"><span>LOGIN</span></a></li>
					<li class="hover:underline"><a class="h-full px-3 flex items-center" href="/usr/member/join"><span>JOIN</span></a></li>
				</c:if>
				<c:if test="${rq.getLoginedMemberId() != 0 }"> <!-- 로그인 상태일때 보임 -->
					<c:choose>
						<c:when test="${rq.getLoginedMember().authLevel != 7 }">
							<li class="hover:underline"><a class="h-full px-3 flex items-center" href="/usr/member/myPage"><span>MYPAGE</span></a></li>
						</c:when>
						<c:otherwise>
							<li class="hover:underline"><a class="h-full px-3 flex items-center" href="/adm/member/list"><span>회원관리</span></a></li>
						</c:otherwise>
					</c:choose>
					<li class="hover:underline"><a class="h-full px-3 flex items-center" href="/usr/member/doLogout"><span>LOGOUT</span></a></li>
				</c:if>
				<li class="hover:underline"><a class="h-full px-3 flex items-center" href="/usr/home/APITest"><span>API</span></a></li>
				<li class="hover:underline"><a class="h-full px-3 flex items-center" href="/usr/home/APITest2"><span>API2</span></a></li>
				<li class="hover:underline"><a class="h-full px-3 flex items-center" href="/usr/home/APITest3"><span>API3</span></a></li>
			</ul>
			
		</div>
	</header>

	<section class="my-3 text-2xl">
		<div class="container mx-auto px-3">
			<h1>${pageTitle}&nbsp;Page</h1>
		</div>
		
	</section>
	
	<main>