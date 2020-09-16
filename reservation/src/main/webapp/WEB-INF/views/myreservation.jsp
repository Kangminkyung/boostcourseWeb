<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ko">

<head>
	<meta charset="utf-8">
	<meta name="description" content="네이버 예약, 네이버 예약이 연동된 곳 어디서나 바로 예약하고, 네이버 예약 홈(나의예약)에서 모두 관리할 수 있습니다.">
	<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no">
	<title>네이버 예약</title>
	<link href="css/style.css" rel="stylesheet">
	<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.7.6/handlebars.min.js"></script>
	<script src="js/ajaxRequest.js"></script>
	<script src="js/template.js"></script>
	<script src="js/myreservation.js"></script>
	
</head>

<body>
	<input id="reservation_email" type="hidden"
		value="${sessionScope.email}" />
	<div id="container">
		<div class="header">
			<header class="header_tit" name="top">
				<h1 class="logo">
					<a href="./" class="lnk_logo" title="네이버"> <span
						class="spr_bi ico_n_logo">네이버</span>
					</a> 
					<a href="./" class="lnk_logo" title="예약"> 
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
						<a href="bookingLoginForm" class="btn_my"> <span
							class="viewReservation" title="예약확인">예약확인</span>
						</a>
					</c:otherwise>
				</c:choose>
			</header>
		</div>
		<hr />
		<div class="ct">
			<div class="section_my">
				<!-- 예약 현황 -->
				<div class="my_summary" id ="top">
					<ul class="summary_board">
						<!-- summaryInfo-->
					</ul>
				</div>
				<!--// 예약 현황 -->

				<!-- 내 예약 리스트  wrap_mylist hide 추가 가능-->
				<div class="wrap_mylist ">
					<ul class="list_cards">
				  	
						<li class="card confirmed">
							<div class="link_booking_details">
								<div class="card_header">
									<div class="left"></div>
									<div class="middle">
										<i class="spr_book2 ico_check2"></i> <span class="tit">예약확정</span>
									</div>
									<div class="right"></div>
								</div>
							</div>
							<!-- confirmed 예약확정 리스트 -->
						</li>
						<li class="card used">
							<div class="link_booking_details">
								<div class="card_header">
									<div class="left"></div>
									<div class="middle">
										<i class="spr_book2 ico_check2"></i> <span class="tit">이용완료</span>
									</div>
									<div class="right"></div>
								</div>
							</div>
							<!-- used 이용완료  리스트 -->
						</li>
						<li class="card used cancel">
							<div class="link_booking_details">
								<div class="card_header">
									<div class="left"></div>
									<div class="middle">
										<i class="spr_book2 ico_cancel"></i> <span class="tit">취소된예약</span>
									</div>
									<div class="right"></div>
								</div>
							</div>
							<!-- canceled 취소된 예약 리스트 -->
						</li>
					</ul>
				</div>
				<!--// 내 예약 리스트 -->
			</div>
		</div>
		<hr />
	</div>
	<footer>
		<div class="gototop">
			<a href="#top" class="lnk_top"> <span class="lnk_top_text">TOP</span>
			</a>
		</div>
		<div id="footer" class="footer">
			<p class="dsc_footer">네이버(주)는 통신판매의 당사자가 아니며, 상품의정보, 거래조건, 이용 및
				환불 등과 관련한 의무와 책임은 각 회원에게 있습니다.</p>
			<span class="copyright">© NAVER Corp.</span>
		</div>
	</footer>

	<!-- 취소 팝업 -->
	<!-- [D] 활성화 display:block, 아니오 버튼 or 닫기 버튼 클릭 시 숨김 display:none; -->
	<div class="popup_booking_wrapper" style="display: none;">
		<!-- popup -->
	</div>

</body>

<script type="myreservation_template" id="summaryInfo">
	<li class="item">
		<a href="#total_summary"class="link_summary_board on"> 
			<i class="spr_book2 ico_book2"></i>
			<em class="tit">전체</em> <span class="figure">{{totalCount}}</span>
		</a></li>
	<li class="item">
		<a href="#confirmed_summary" class="link_summary_board">
			<i class="spr_book2 ico_book_ss"></i> 
			<em class="tit">이용예정</em>
			<span class="figure">{{confirmedCount}}</span>
		</a>
	</li>
	<li class="item">
		<a href="#used_summary" class="link_summary_board">
			<i class="spr_book2 ico_check"></i> 
			<em class="tit">이용완료</em>  
			<span class="figure">{{usedCount}}</span>
		</a>
	</li>
	<li class="item">
		<a href="#canceled_summary" class="link_summary_board">
			<i class="spr_book2 ico_back"></i> <em class="tit">취소·환불</em> 
			<span class="figure">{{canceledCount}}</span>
		</a>
	</li>
</script>

<script type="myreservation_template" id="confirmSection">
  	<article class="card_item" data-id="{{reservationId}}>
    	<a href="#" class="link_booking_details">
    		<div class="card_body">
    			<div class="left"></div>
    			<div class="middle">
    				<div class="card_detail">
    					<em class="booking_number">No.{{reservationId}}</em>
    					<h4 class="tit">{{description}}</h4>
    					<ul class="detail">
    						<li class="item">
    							<span class="item_tit">일정</span>
    							<em class="item_dsc">
    									{{reservationDate}}
    							</em>
    						</li>
    						<li class="item">
    							<span class="item_tit">내역</span>
    							<em class="item_dsc">
    								{{#ticketInfo}}
										{{priceTypeName}}: {{price}}원 {{count}}장	
										<br>
									{{/ticketInfo}}
    							</em>
    						</li>
    						<li class="item">
    							<span class="item_tit">장소</span>
    							<em class="item_dsc">
    								주소: {{placeStreet}} <br> 지번: {{placeLot}}
    							</em>
    						</li>
    						<li class="item">
    							<span class="item_tit">업체</span>
    							<em class="item_dsc">
    								{{placeName}}
    							</em>
    						</li>
    					</ul>
    					<div class="price_summary">
    						<span class="price_tit">결제 예정금액</span>
    						<em class="price_amount">
    							<span>
                   					{{ticketPrice}}
    							</span>
    							<span class="unit">원</span>
    						</em>
    					</div>
						<!-- [D] 예약 신청중, 예약 확정 만 취소가능, 취소 버튼 클릭 시 취소 팝업 활성화 -->
    					<div class="booking_cancel" id="cancelScope" reservationId ={{reservationId}}>
    						<button class="btn"><span>취소</span></button>
    					</div>
    				</div>
    			</div>
    			<div class="right"></div>
    		</div>
    		<div class="card_footer">
    			<div class="left"></div>
    			<div class="middle"></div>
    			<div class="right"></div>
    		</div>
    	</a>
    	<a href="#" class="fn fn-share1 naver-splugin btn_goto_share" title="공유하기"></a>
    </article>
  </script>

<script type="myreservation_template" id="usedSection">
  	<article class="card_item" data-id="{{reservationId}}">
    	<a href="#" class="link_booking_details">
    		<div class="card_body">
    			<div class="left"></div>
    			<div class="middle">
    				<div class="card_detail">
    					<em class="booking_number">No.{{reservationId}}</em>
    					<h4 class="tit">{{description}}</h4>
    					<ul class="detail">
    						<li class="item">
    							<span class="item_tit">일정</span>
    							<em class="item_dsc">
    								{{reservationDate}}
    							</em>
    						</li>
    						<li class="item">
    							<span class="item_tit">내역</span>
    							<em class="item_dsc">
    								{{#ticketInfo}}
										{{priceTypeName}}: {{price}}원 {{count}}장 
										<br>
									{{/ticketInfo}}
    							</em>
    						</li>
    						<li class="item">
    							<span class="item_tit">장소</span>
    							<em class="item_dsc">
    								{{#if placeName}}
    									{{placeName}}
    								{{else}}
    									내역이 없습니다.
    								{{/if}}
    							</em>
    						</li>
    						<li class="item">
    							<span class="item_tit">업체</span>
    							<em class="item_dsc">
    								오디컴퍼니 주식회사
    							</em>
    						</li>
    					</ul>
    					<div class="price_summary">
    						<span class="price_tit">결제 완료금액</span>
    						<em class="price_amount">
    							<span>
    								{{ticketPrice}}
    							</span>
    							<span class="unit">원</span>
    						</em>
    					</div>
    					<div class="booking_cancel" id="reviewScope" reservationId={{reservationId}}>
    						<a href="#">
								<button class="btn">
									<span>예매자 리뷰남기기</span>
								</button>
							</a>
    					</div>
    				</div>
    			</div>
    			<div class="right"></div>
    		</div>
    		<div class="card_footer">
    			<div class="left"></div>
    			<div class="middle"></div>
    			<div class="right"></div>
    		</div>
    	</a>
    </article>
 </script>

<script type="myreservation_template" id="cancelSection">
  <article class="card_item" data-id="{{reservationId}}>
    	<a href="#" class="link_booking_details">
    		<div class="card_body">
    			<div class="left"></div>
    			<div class="middle">
    				<div class="card_detail">
    					<em class="booking_number">No.{{reservationId}}</em>
    					<h4 class="tit">{{description}}</h4>
    					<ul class="detail">
    						<li class="item">
    							<span class="item_tit">일정</span>
    							<em class="item_dsc">
    								{{reservationDate}}
    							</em>
    						</li>
    						<li class="item">
    							<span class="item_tit">내역</span>
    							<em class="item_dsc">
    								{{#ticketInfo}}
										{{priceTypeName}}: {{price}}원 {{count}}장 
										<br>
									{{/ticketInfo}}
    							</em>
    							</em>
    						</li>
    						<li class="item">
    							<span class="item_tit">장소</span>
    							<em class="item_dsc">
    								{{#if placeName}}
    									{{placeName}}
    								{{else}}
    									내역이 없습니다.
    								{{/if}}
    							</em>
    						</li>
    						<li class="item">
    							<span class="item_tit">업체</span>
    							<em class="item_dsc">
    								오디컴퍼니 주식회사
    							</em>
    						</li>
    					</ul>
    					<div class="price_summary">
    						<span class="price_tit">결제 예정금액</span>
    						<em class="price_amount">
    							<span>
                   					{{ticketPrice}}
    							</span>
    							<span class="unit">원</span>
    						</em>
    					</div>
    				</div>
    			</div>
    			<div class="right"></div>
    		</div>
    		<div class="card_footer">
    			<div class="left"></div>
    			<div class="middle"></div>
    			<div class="right"></div>
    		</div>
    	</a>
    </article>
</script>
 
<script type="myreservation_template" id="emptySection">
	<div class="err" style="dis">
		<i class="spr_book ico_info_nolist"></i>
		<h1 class="tit">{{reservationType}} 리스트가 없습니다</h1>
	</div>
</script>

<script type="myreservation_template" id="cancelPopup">
	<div class="dimm_dark" style="display: block"></div>
	<div class="popup_booking refund">
		<h1 class="pop_tit">
			<span>서비스명/상품명</span> 
			<span>{{description}}</span>
			<small class="sm">예약일: {{reservationDate}}</small>
			<small class="sm">내역: 총 {{ticketCount}}장</small>
			<small class="sm">결제금액: 총 {{ticketPrice}}원</small>

		</h1>
		<div class="nomember_alert">
			<p>취소하시겠습니까?</p>
		</div>
		<div class="pop_bottom_btnarea" id="cancelId">
			<div class="btn_gray">
				<a href="#" class="btn_bottom no_btn"><span>아니오</span></a>
			</div>
			<div class="btn_green" reservationId ={{reservationId}}>
				<a href="#" class="btn_bottom yes_btn"><span>예</span></a>
			</div>
		</div>
		<!-- 닫기 -->
		<a href="#" class="popup_btn_close" title="close"> 
			<i class="spr_book2 ico_cls"></i>
		</a>
		<!--// 닫기 -->
	</div>
</script>

<script type="myreservation_template" id="reviewPopup">
	<div class="dimm_dark" style="display: block"></div>
	<div class="popup_booking refund">
		<h1 class="pop_tit">
			<span>서비스명/상품명</span> 
			<span>{{description}}</span>
			<small class="sm">예약일: {{reservationDate}}</small>
			<small class="sm">내역: 총 {{ticketCount}}장</small>
			<small class="sm">결제금액: 총 {{ticketPrice}}원</small>

		</h1>
		<div class="nomember_alert">
			<p>리뷰를 남기시겠습니까?</p>
		</div>
		<div class="pop_bottom_btnarea" id="reviewId">
			<div class="btn_gray">
				<a href="#" class="btn_bottom no_btn"><span>아니오</span></a>
			</div>
			<div class="btn_green" reservationId ={{reservationId}} productId = {{productId}}>
				<a href="#" class="btn_bottom yes_btn"><span>예</span></a>
			</div>
		</div>
		<!-- 닫기 -->
		<a href="#" class="popup_btn_close" title="close"> 
			<i class="spr_book2 ico_cls"></i>
		</a>
		<!--// 닫기 -->
	</div>
</script>
</html>