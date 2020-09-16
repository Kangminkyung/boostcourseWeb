<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ko">

<head> 
<meta charset="utf-8">
<meta name="description" content="네이버 예약, 네이버 예약이 연동된 곳 어디서나 바로 예약하고, 네이버 예약 홈(나의예약)에서 모두 관리할 수 있습니다.">
<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no">

<title>네이버 예약</title>

<link href="css/bookinglogin.css" type="text/css" rel="stylesheet">
<link href="css/reservation.css" type="text/css" rel="stylesheet">
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.7.6/handlebars.min.js"></script>
<script src="js/ajaxRequest.js"></script>
<script src="js/main.js"></script>
<script src="js/template.js"></script>

</head>

<body>
	<div id="container">
		<!-- 헤더영역 -->
		<div class="header">
			<header class="header_tit">
				<h1 class="logo">
					<a href="https://m.naver.com" class="lnk_logo none_link" title="Naver">
						<span class="spr_bi ico_n_logo">네이버</span>
					</a>
					<a href="" class="lnk_logo none_link">
						<span class="spr_bi ico_bk_logo">예약</span>
					</a>
				</h1>
				<!-- session에 저장된 이메일이 있으면 이메일이 보여지고 아니면 예약확인 아이콘이 보이게 설정함 -->
				<c:choose>				
					<c:when test="${sessionScope.email != null}">
						<a href="<%=request.getContextPath()%>/myreservation?reservationEmail=${sessionScope.email}" class="btn_my"> <span
							class="viewReservation" title="이메일">${sessionScope.email}</span>
						</a>
					</c:when>
					<c:otherwise>
						<a href="<%=request.getContextPath() %>/bookingloginPage" class="btn_my">
							<span class="">예약확인</span>
						</a>
					</c:otherwise>
				</c:choose>
			</header>
		</div>
		
		<!-- 프로모션 영역 -->
		<div class="event">
			<div class="section_visual">
				<div class="group_visual">
					<div class="container_visual">
						<div class="prev_e" style="display:none;">
							<div class="prev_inn" style="display:none;">
								<a href="#" class="btn_pre_e" title="이전">
									<i class="spr_book_event spr_event_pre">이전</i>
								</a>
							</div>
						</div>
						<div class="nxt_e" style="display:none;">
							<div class="nxt_inn">
								<a href="#" class="btn_nxt_e" title="다음">
									<i class="spr_book_event spr_event_nxt">다음</i>
								</a>
							</div>
						</div>
						<div class="container_visual">
							<ul class="visual_img" id="visual_lst"></ul>
						</div>
						<span class="nxt_fix" style="display: none;"></span>
					</div>
				</div>
			</div>
			
			<!-- 카테고리 영역 -->
			<div class="section_event_tab">
				<ul class="event_tab_lst">
					<li class="item" data-category="0">
						<a class="anchor active">
							<span>전체리스트</span>
						</a>
					</li>
				</ul>
			</div>
			
			<!-- 상품리스트(총 갯수, 상품리스트, 더보기) 영역 -->
			<div class="section_event_lst">
				<p class="event_lst_txt">바로 예매 가능한 행사가 <span class="pink"></span>개 있습니다.</p>
				<div class="wrap_event_box">
					<ul class="lst_event_box"></ul>
					<ul class="lst_event_box"></ul>
					<div class="more_item">
						<button class="btn_more">
							<span>더보기</span>
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<footer>
		<div class="gototop">
			<a href="#" class="lnk_top">
				<span class="lnk_top_text">TOP</span>
			</a>
		</div>
		<div class="footer">
			<p class="dsc_footer">네이버(주)는 통신판매의 당사자가 아니며, 상품의정보, 거래조건, 이용 및 환불 등과 관련한 의무와 책임은 각 회원에게 있습니다.</p>
			<span class="copyright">© NAVER Corp.</span>
		</div>
	</footer>
	
	<!-- template -->
	<script type="reservation_template" id="category">
		<li class="item" data-category="{{id}}">
			<a class="anchor">
				<span>{{name}}</span>
			</a>
		</li>
	</script>
	
	<script type="reservation_template" id="promotion">
		<li class="item" style="background-image: url('<%=request.getContextPath() %>/assets/{{productImageUrl}}')">
			<a href="#">
				<span class="img_btm_border"></span>
				<span class="img_right_border"></span>
				<span class="img_bg_gra"></span>
				<div class="event_txt">
					<h4 class="event_txt_tit"></h4>
					<p class="event_txt_adr"></p>
					<p class="event_txt_dsc"></p>
				</div>
			</a>
		</li>
	</script>
	
	<script type="reservation_template" id="product">
		<li class="item">
			<a href="<%=request.getContextPath() %>/detail?displayInfoId={{displayInfoId}}&productId={{productId}}" class="item_book">
				<div class="item_preview">
					<img alt={{productDescription}} class="img_thumb" src = "<%=request.getContextPath() %>/assets/{{productImageUrl}}"/>
					<span class="img_border"></span>
				</div>
				<div class="event_txt">
					<h4 class="event_txt_tit">
						<span>{{productDescription}}</span>
						<small class="sm">{{placeName}} </small>
					</h4>
					<p class="event_txt_dsc">{{productContent}}</p>
				</div>
			</a>
		</li>
	</script>
</body>


</html>