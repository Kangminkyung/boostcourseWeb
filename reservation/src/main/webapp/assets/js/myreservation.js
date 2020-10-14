document.addEventListener("DOMContentLoaded", ()=> {
	var mypage = new Mypage();
	mypage.initMypage();

});

let sectionType = {
	reservationType: '',
}

function Mypage(){
	let reservationEmail = Mypage.prototype.getParameters();
	console.log(reservationEmail);
}

Mypage.prototype.initMypage = function(){
	Mypage.prototype.initSummary();
	Mypage.prototype.initLists();
};


Mypage.prototype.moveList = function(res, reservationId){
	Mypage.prototype.initSummary();
 	Mypage.prototype.moveUpdateSection(res, reservationId);
};

Mypage.prototype.initSummary = function(){
	let reservationEmail = Mypage.prototype.getParameters();
	ajaxRequest.sendRequest("GET", "api/reserve/reservations?reservationEmail="+reservationEmail, this.showSummary); 

};

Mypage.prototype.getParameters = function(){
	let url = location.search;
	let params = new URLSearchParams(url);
	let email = params.get("reservationEmail");
	return email;
};

Mypage.prototype.showSummary = function(res){
	let summary = res.response;
	var summaryHtml = {
				canceledCount : summary.canceledReservation.length,
				confirmedCount : summary.confirmedReservation.length,
				usedCount : summary.usedReservation.length,
				totalCount : 0
	}

	// totalCount 갱신		
	summaryHtml.totalCount = summaryHtml.canceledCount + summaryHtml.confirmedCount + summaryHtml.usedCount;
	document.querySelector(".summary_board").innerHTML = template.myreservation.summaryInfo(summaryHtml);	
};

// 2. 예약 리스트 섹션 불러오기
Mypage.prototype.initLists = function(){
	let reservationEmail = this.getParameters();
	
	ajaxRequest.sendRequest("GET", "api/reserve/reservations?reservationEmail="+reservationEmail, this.updateSections); 

	
	this.addButtonClick();
	this.addPopupButtionClick();
};

// 2-1. 예약리스트 불러오기
Mypage.prototype.updateSections = function(res){
	let sections = res.response;
	
	// (1). 이용완료 : userReservation
	if(sections.usedReservation.length >0){
		sections.usedReservation.forEach(function(list){			
			// priceTypeName => 직관적으로 변경시킴
			for(let i=0; i<list.ticketInfo.length; i++){
				list.ticketInfo[i].priceTypeName = Mypage.prototype.getPriceType(list.ticketInfo[i].priceTypeName);
			}
			document.querySelector(".card.used").innerHTML += template.myreservation.usedSection(list);
		});
	}else{
		sectionType.reservationType = "이용완료";
		document.querySelector(".card.used").innerHTML += template.myreservation.emptySection(sectionType);

	}
	
	// (2). 이용예정/예약확정 : confirmedReservation
	if(sections.confirmedReservation.length > 0){
		sections.confirmedReservation.forEach(function(list){
			// priceTypeName => 직관적으로 변경시킴
			for(let i=0; i<list.ticketInfo.length; i++){
				list.ticketInfo[i].priceTypeName = Mypage.prototype.getPriceType(list.ticketInfo[i].priceTypeName);
			}
			document.querySelector(".card.confirmed").innerHTML += template.myreservation.confirmSection(list);
		});
	}else{
		sectionType.reservationType = "예약확정";
		document.querySelector(".card.confirmed").innerHTML += template.myreservation.emptySection(sectionType);
	}
	
	// (3). 취소/환불 : canceledReservation
	if(sections.canceledReservation.length > 0){
		sections.canceledReservation.forEach(function(list){
			// priceTypeName => 직관적으로 변경시킴
			for(let i=0; i<list.ticketInfo.length; i++){
				list.ticketInfo[i].priceTypeName = Mypage.prototype.getPriceType(list.ticketInfo[i].priceTypeName);
			}
			document.querySelector(".card.used.cancel").innerHTML += template.myreservation.cancelSection(list);
		});
	}else{
		sectionType.reservationType = "취소";
		document.querySelector(".card.used.cancel").innerHTML += template.myreservation.emptySection(sectionType);
	}
};

// 2-2. 타입설정
Mypage.prototype.getPriceType = function(type) {
	
	switch(type){
	case "A": return "성인";
	case "Y": return "청소년";
	case "B": return "어린이";
	case "S": return "셋트";
	case "D": return "장애인";
	case "C": return "지역주민";
	case "E": return "얼리버드";
	case "V": return "VIP";
	case "R": return "R석";
	}
};

// 3-1. 버튼 등록
Mypage.prototype.addButtonClick = function(){
	let reservationSection = document.querySelector(".list_cards");
	let popup = document.querySelector(".popup_booking_wrapper");
	
	
	reservationSection.addEventListener("click", function(event){
		if(event.target.closest(".booking_cancel")){
			let reservationid = -1;
			if(event.target.closest("#cancelScope")){
				console.log("취소버튼클릭");
				reservationid = event.target.closest("#cancelScope").getAttribute("reservationid");

				Mypage.prototype.requestByReservationId(reservationid, "cancelPopup");
				popup.style.display = "block";
			}else{
				reservationid = event.target.closest("#reviewScope").getAttribute("reservationid");
				Mypage.prototype.requestByReservationId(reservationid, "reviewPopup");
				popup.style.display = "block";
			}
		}
	});
};

// 3-2. 클릭한 상품 정보 받아오기
Mypage.prototype.requestByReservationId = function(reservationid, popupType){		
	// 파라미터가 있는 경우 ajax
	let xhr = new XMLHttpRequest();
	xhr.open("GET", "api/reserve/reservations/id?reservationId="+reservationid, true);
	
	xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	
	xhr.addEventListener("load", function(){
		this.updateReservations(JSON.parse(xhr.responseText), popupType) // 팝업창에 템플릿 삽입
		}.bind(this)
	);
	xhr.send();
};

// 3-3. Myreservation 정보를 팝업 템플릿에 삽입 
Mypage.prototype.updateReservations = function(res, popupType){
	if(popupType === "cancelPopup"){
		document.querySelector(".popup_booking_wrapper").innerHTML = template.myreservation.cancelPopup(res.Myreservation);
	}else if(popupType === "reviewPopup"){
		document.querySelector(".popup_booking_wrapper").innerHTML = template.myreservation.reviewPopup(res.Myreservation);
	}
};


// 3-4. 팝업창 예/아니오 버튼 클릭
Mypage.prototype.addPopupButtionClick = function(){
	let popup = document.querySelector(".popup_booking_wrapper");
	
	popup.addEventListener("click", function(event){
		if(event.target.tagName === "I"){
			popup.style.display = "none";
		}else if(event.target.tagName === "A" || event.target.tagName === "SPAN"){
				if(event.target.innerText === "예"){
					if(event.target.closest(".pop_bottom_btnarea").getAttribute("id") === "cancelId"){
						let reservationId = event.target.closest("div").getAttribute("reservationId");
						Mypage.prototype.cancelReservation(reservationId);
						popup.style.display = "none";
						
					}else if(event.target.closest(".pop_bottom_btnarea").getAttribute("id") === "reviewId"){
						let reservationEmail = Mypage.prototype.getParameters();
						console.log(reservationEmail);
						let reservationId = event.target.closest(".btn_green").getAttribute("reservationId");
						let productId = event.target.closest(".btn_green").getAttribute("productId");
						
						// 리뷰작성 페이지로 넘어감
						location.href="/reviewWrite";
					}
				}else if(event.target.innerText === "아니오"){
					popup.style.display = "none";
				}
		}
	});

};

// 4-1. 예약 취소 기능 추가
Mypage.prototype.cancelReservation = function(reservationId){
	console.log("cancel reservation함수");
	
	let xhr = new XMLHttpRequest();
	xhr.open("PUT", "api/reserve/reservations?reservationId="+reservationId, true);
	xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	
	xhr.addEventListener("load", function(){
		this.moveList(JSON.parse(xhr.responseText), reservationId) // 팝업창에 템플릿 삽입
		}.bind(this)
	);
	xhr.send();
		
};

// 4-2. 예약 취소 후 리스트 업데이트
Mypage.prototype.moveUpdateSection = function(res, reservationId){
	let reservationEmail = this.getParameters();
	
	// 예약 아이디를 받아서 해당 섹션을 삭제함
	Mypage.prototype.deleteSection(reservationId); 
	this.addButtonClick();
	this.addPopupButtionClick();
};

// 4-3. 섹션 삭제
Mypage.prototype.deleteSection = function(reservationId){
	
	console.log(reservationId);

	// 클릭한 섹션 삭제
	document.querySelector('article[data-id="'+reservationId+'"]').remove();
	
	console.log(document.querySelector('.card.used.cancel .err'));
	
	if(document.querySelector('.card.used.cancel .err') != null){
		document.querySelector('.card.used.cancel .err').remove();
	}
		
	// cancel 섹션에 추가할 해당 아이디의 정보를 불러옴
	ajaxRequest.sendRequest("GET", "api/reserve/reservations/id?reservationId="+reservationId, this.addSection); 
};

// 4-4. 섹션 추가
Mypage.prototype.addSection= function(res) {
	let newReservation = res.response.Myreservation;

	let cardSection = document.querySelector(".card.used.cancel");

	// priceTypeName => 직관적으로 변경시킴
	for(let i=0; i<newReservation.ticketInfo.length; i++){
		newReservation.ticketInfo[i].priceTypeName = Mypage.prototype.getPriceType(newReservation.ticketInfo[i].priceTypeName);
	};
	
	// 섹션 추가
	cardSection.innerHTML += template.myreservation.cancelSection(newReservation);
};