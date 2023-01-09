<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="APITest" />
<%@ include file="../common/head.jsp"%>

<script>
	const API_KEY = 'BfR%2FKgS2k9Aj%2BCDwu7FYmk19uBtwKWjUNIxa%2FmBljEuepC%2BD%2BMwPJ1ABkc6yUToD2gzrx1lZHpf%2BHKG6%2BeewnQ%3D%3D';
	
	async function getData() {
		const url = 'http://apis.data.go.kr/1790387/covid19HospitalBedStatus/covid19HospitalBedStatusJson?serviceKey=' + API_KEY;
		
		const response = await fetch(url);
		const data = await response.json();
		
		console.log(data)
		
		$('.APITest').html(data.response.result[0].itsv_bed_avlb);
	}
	
	getData();
	
	
</script>

<div class="container mx-auto px-3">
	<div class="APITest"></div>
</div>

<%@ include file="../common/foot.jsp"%>