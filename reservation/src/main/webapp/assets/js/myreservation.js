
document.addEventListener("DOMContentLoaded", ()=> {
	titleObj.initSummary(); // 상단바 요약 정보
	listsObj.initLists();// 예약 리스트
});


const titleObj = {
	initSummary : function(){
		let reservationEmail = titleObj.getParameters();
		titleObj.requestAjax(reservationEmail);
	},
	
	getParameters: function(){
		let url = location.search;
		let params = new URLSearchParams(url);
		let email = params.get("reservationEmail");
		return email;
	},
	
	requestAjax : function(reservationEmail){
		ajaxRequest.sendRequest("GET", "api/myreservationPage/"+reservationEmail, this.showSummary); 
	},
	
	// 1. 요약정보 불러오기
	showSummary: function(res){
		let summary = res.response;
		
		var summaryHtml = {
				canceledCount : summary.canceledReservation.length,
				confirmedCount : summary.confirmedReservation.length,
				usedCount : summary.usedReservation.length,
				totalCount : 0
		};
		// totalCount 갱신		
		summaryHtml.totalCount = summaryHtml.canceledCount + summaryHtml.confirmedCount + summaryHtml.usedCount;
		document.querySelector(".summary_board").innerHTML += template.myreservation.summaryInfo(summaryHtml);

	}
}

const listsObj = {
	initLists : function(){
		let reservationEmail = titleObj.getParameters();
		listsObj.requestAjax(reservationEmail);
	},
	
	requestAjax : function(reservationEmail){
		ajaxRequest.sendRequest("GET", "api/myreservationPage/"+reservationEmail, this.updateSections); 
	},
	
	// 2-1. 예약리스트 불러오기
	updateSections: function(res){
		let sections = res.response;
		
		sections.usedReservation.forEach(function(list){
			console.log(list);
			
			// priceTypeName => 직관적으로 변경시킴
			for(let i=0; i<list.ticketInfo.length; i++){
				console.log(list.ticketInfo[i]);
				list.ticketInfo[i].priceTypeName = listsObj.getPriceType(list.ticketInfo[i].priceTypeName);
			}
			document.querySelector(".card.used").innerHTML += template.myreservation.usedSection(list);
		});
		
		sections.confirmedReservation.forEach(function(list){
			
			// priceTypeName => 직관적으로 변경시킴
			for(let i=0; i<list.ticketInfo.length; i++){
				console.log(list.ticketInfo[i]);
				list.ticketInfo[i].priceTypeName = listsObj.getPriceType(list.ticketInfo[i].priceTypeName);
			}
			document.querySelector(".card.confirmed").innerHTML += template.myreservation.confirmSection(list);
		});
		
	//	sections.cancelReservation.forEach(function(list){
	//		document.querySelector(".card.used.cancel").innerHTML += template.myreservation.cancelSection(list);
	//	});
		
	},
	
	// 2-2. 타입설정
	getPriceType :function(type) {
		
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
	},
	// 2-3. html 저장
/*	insertSection : function(items){
		var sectionHtml = {
			cancelFlag: items.cancelFlag,
			description: items.description,
			placeLot: items.placeLot,
			placeName: items.placeName,
			placeStreet: items.placeStreet,
			productId: items.productId,
			reservationDate: items.reservationDate,
			reservationId: items.reservationId,
			ticketCount: items.ticketCount,
			ticketInfo: {},
			ticketPrice: items.ticketPrice,
		};
		
		for(var i=0; i<items.ticketInfo.length; i++){
			sectionHtml.ticketInfo[i] = {
				priceTypeName : this.getPriceType(items.ticketInfo[i].priceTypeName),
				price : items.ticketInfo[i].price,
				count : items.ticketInfo[i].count,
			}
		}
		
		return sectionHtml;
	}, */
}
