
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
	
	// 2. 예약리스트 불러오기
	updateSections: function(res){
		let sections = res.response;
		
		console.log(sections);
		
		console.log("canceledReservation");

		sections.canceledReservation.forEach(function(items){
			console.log(items);			
		//	document.querySelector(".card.used.cancel").innerHTML += template.myreservation.showCanceledSection(items);

		});
		
		sections.confirmedReservation.forEach(function(items){
			console.log(items);			
		//	document.querySelector(".card.confirmed").innerHTML += template.myreservation.showConfirmedSection(items);
		});

		console.log("usedReservation");

		sections.usedReservation.forEach(function(items){		
			console.log(items);			
			document.querySelector(".card.used").innerHTML += template.myreservation.showUsedSection(items);

		});
	
	},
	
	insertSection : function(items){
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
				priceNameType : items.ticketInfo[i].priceNameType,
				price : items.ticketInfo[i].price,
				count : items.ticketInfo[i].count,
			}
		}
		
		return sectionHtml;
	},
}
