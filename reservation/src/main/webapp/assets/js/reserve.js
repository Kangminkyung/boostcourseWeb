
document.addEventListener("DOMContentLoaded", ()=> {
	
	var promotion = new Promotion();
	promotion.initPromotion(); // 상단 이미지 타이틀 및 공연 상세정보


	var price = new Price();
	price.initPrices();  // 상품 가격
	
	var reservation = new Reservation();
	reservation.initReservation(); // 예매자 정보 및 약관 동의
	
});

function Promotion(){};
function Price(){};
function Reservation(){};

Promotion.prototype.initPromotion = function(){
	this.requestAjax();
};

Promotion.prototype.requestAjax =function(){
	let displayInfoId = document.querySelector(".ct").dataset.displayId;			
	ajaxRequest.sendRequest("GET", "api/products/"+displayInfoId, this.showReservation);
};
	
Promotion.prototype.showReservation =function(xhr){
	let res = xhr.response;
	Promotion.prototype.showTitleInfo(res);// 1-1. 타이틀이미지
	Promotion.prototype.showStoreDetails(res); // 1-2. 상세 정보
};

// 1-1. 타이틀 상품 정보
Promotion.prototype.showTitleInfo = function(res){
	
	let titleSection = document.querySelector(".visual_img");

	let productDescription = res.displayInfo.productDescription;
	let productImage = res.productImages[0];
	let minimumPrice = res.productPrices[0].price * ((100 - res.productPrices[0].discountRate)/100);
	
	for(let i=0; i< res.productPrices.length; i++){
		let price = res.productPrices[i].price * ((100 - res.productPrices[i].discountRate)/100);
		
		if(price< minimumPrice)
			minimumPrice = price;
	}
	
	let titles = {productDescription : productDescription,
			productSaveFileName : productImage.productSaveFileName, 
			minimumPrice: minimumPrice};

	titleSection.innerHTML += template.reserve.titleInfo(titles);
};

// 1-2. 상세 정보
Promotion.prototype.showStoreDetails = function(res){
	let detailInfo = res;
	
	// priceTypeName => 직관적으로 변경시킴
	for(let i=0; i<detailInfo.productPrices.length; i++){
		detailInfo.productPrices[i].priceTypeName = Price.prototype.getPriceType(detailInfo.productPrices[i].priceTypeName);
	}

	document.querySelector(".top_title").innerHTML += template.reserve.productDescription(detailInfo.displayInfo);
	document.querySelector(".section_store_details").innerHTML += template.reserve.storeDetails(detailInfo);
};

Price.prototype.initPrices = function(){
	this.requestAjax();
},

Price.prototype.requestAjax = function(){
	let displayInfoId = document.querySelector(".ct").dataset.displayId;			
	ajaxRequest.sendRequest("GET", "api/products/"+displayInfoId, this.showReservation);
};
		
Price.prototype.showReservation = function(xhr){
	let res = xhr.response;
	Price.prototype.showPrices(res.productPrices); // 2. 가격
},	

// 2-1. 가격 html 
Price.prototype.showPrices =function(productPrices){
	let priceHtml = productPrices.reduce(function(innerHtml, price){
		let priceInfo = {
				"createDate" : price.createDate,
				"discountRate": price.discountRate,
				"modifyDate": price.modifyDate,
				"price": price.price,
				"discountPrice": price.price * (100 - price.discountRate)/100,
				"priceTypeName": price.priceTypeName,
				"productId": price.productId,
				"productPriceId": price.productPriceId,
	
		}
		return innerHtml + template.reserve.productPrices(priceInfo);
	}, "");
	
	document.querySelector(".ticket_body").innerHTML = priceHtml;
	
	// 티켓 수량 변경
	document.querySelectorAll("._ticket_count_control").forEach(item => {
		item.addEventListener("click", this.updateTicket); // updateCount, updatePrice
	})
};

// 2-2. 타입설정
Price.prototype.getPriceType = function(type) {
	
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

// 2-3. updateTicket(updateCount, updatePrice)
Price.prototype.updateTicket = function(click){

	// disabled 상태이면 클릭해도 반응 없음, count_control_input는 클릭해도 수량 변하지 않음
	if(click.target.classList.contains("disabled") || click.target.classList.contains("count_control_input")) 
		return;

	let count = Price.prototype.updateCount(click); // updateCount
	Price.prototype.updatePrice(click, count); // updatePrice
};

Price.prototype.updateCount= function(click){
	
	let count = click.target.parentNode.firstElementChild.nextElementSibling; // .count_control_input 		
	let totalHtml = document.querySelector("#totalCount");
	let totalCount = document.getElementById("totalCount").innerHTML;

	// 빼기
	if(click.target.classList.contains("ico_minus3")){
		count.value = parseInt(count.value) - 1;
		totalHtml.innerHTML = parseInt(totalCount) -1;
		
		// 버튼 disabled 처리
		if(parseInt(count.value) === 0){
			click.target.parentNode.firstElementChild.classList.add("disabled");
		}
		
	}else{
		// 버튼 disabled 처리
		if(parseInt(count.value) === 0){
			click.target.parentNode.firstElementChild.classList.remove("disabled");
		}
		
		 // 더하기
		count.value  = parseInt(count.value) + 1;
		totalHtml.innerHTML = parseInt(totalCount) + 1;

	}
	return count;
};

Price.prototype.updatePrice = function(click, count){
	let priceHtml = click.target.closest(".count_control").querySelector(".total_price");
	let discountPrice= priceHtml.parentElement.dataset.dcPrice;
	let totalPrice = parseInt(discountPrice * count.value);
	
	// 총 가격 삽입
	priceHtml.innerHTML = totalPrice;

	// on_color 변경
	if(totalPrice === 0){
		priceHtml.parentElement.classList.remove("on_color");
	}else{
		priceHtml.parentElement.classList.add("on_color");
	}
};

Reservation.prototype.initReservation = function(){
	this.confirmAgreement(); // 4. 약관 동의
//	this.requestAjax();
};


Reservation.prototype.confirmAgreement = function(){
	
	// 3-1. 약관보기(open)
	document.querySelectorAll(".btn_agreement").forEach(item => {
		item.addEventListener("click", this.viewAgreement);
	});
	
	// 3-2. 이용자 약관 전체동의 체크 -> 예약버튼 disable 해제
	document.querySelector(".chk_txt_label").addEventListener("click", this.checkAgreement);

	// 3-3. 예약하기 버튼 클릭 -> 입력폼 체크 true이고 totalCount != 0이면 폼 전송
	document.querySelector(".bk_btn_wrap").addEventListener("click", this.checkReservationForm);
	
};

// 3-1. 약관보기
Reservation.prototype.viewAgreement = function(click){
    // 약관 보기 클릭 시 agreement에 open 클래스 추가
	let agreement = click.target.closest(".agreement");
	
	if(agreement.classList.contains("open"))
		agreement.classList.remove("open");
	else
		agreement.classList.add("open");

};

// 3-2. 이용자 약관 전체동의 체크
Reservation.prototype.checkAgreement = function(){
	let reservationBtn = document.querySelector(".bk_btn_wrap");
	
	// 예약버튼 disable 추가/해제
	if(reservationBtn.classList.contains("disable")){
		reservationBtn.classList.remove("disable");
	}else
		reservationBtn.classList.add("disable");
};

// 3-3. 예약하기 버튼 클릭 -> 입력폼 체크 true이고 totalCount != 0이면 폼 전송
Reservation.prototype.checkReservationForm = function(){
		
	if (document.querySelector(".bk_btn_wrap").classList.contains("disable")) {
		return;
	}
	
	else if (Number(document.querySelector("#totalCount").innerText) === 0) {
		alert("티켓 수량을 선택해주세요!");
		return;
	}
		
	else{
		let reservationName = document.querySelector("#name");
		let reservationTel = document.querySelector("#tel");
		let reservationEmail = document.querySelector("#email");
				
		// 검증
		let nameCheck = (/[[a-zA-Z가-힣]{1,17}/).test(reservationName.value);
		let telCheck = (/01[01789]-\d{3,4}-\d{4}/).test(reservationTel.value);
		let emailCheck = (/^[\w]\w+\@\w+\.\w+\.*\w*/).test(reservationEmail.value); 

		console.log(reservationName.value + nameCheck);
		console.log(reservationTel.value + telCheck);
		console.log(reservationEmail.value + emailCheck);
			
		reservationName.parentElement.classList.add("name_wrap");
		reservationTel.parentElement.classList.add("tel_wrap");
		reservationEmail.parentElement.classList.add("email_wrap");
		
		if(nameCheck === false){
			console.log("namefouce");
			reservationName.focus();
			reservationName.parentElement.classList.remove("name_wrap");
		}
		if(telCheck === false){
			console.log("telfouce");
			reservationTel.focus();
			reservationTel.parentElement.classList.remove("tel_wrap");
		}
		if(emailCheck === false){
			console.log("emailfouce");
			reservationEmail.focus();
			reservationEmail.parentElement.classList.remove("email_wrap");
		}
			
		if(nameCheck && telCheck && emailCheck){
			Reservation.prototype.makeReservation(reservationName.value, reservationTel.value, reservationEmail.value);
			console.log(document.querySelector(".form_horizontal"));
		
		}
	}

		
};
	
Reservation.prototype.makeReservation = function(name, tel, email){	

	let resrvHtml = "";
	let data = {};
		
	data.name = "displayInfoId";
	data.value = document.querySelector(".ct").dataset.displayId;
	resrvHtml += template.reserve.formData(data);
	
	data.name = "productId";
	data.value = document.querySelector(".ct_wrap").dataset.productId;
	resrvHtml += template.reserve.formData(data);
	
	data.name = "reservationName";
	data.value = name;
	resrvHtml += template.reserve.formData(data);
		
	data.name = "reservationTel";
	data.value = tel;
	resrvHtml += template.reserve.formData(data);
		
	data.name = "reservationEmail";
	data.value = email;
	resrvHtml += template.reserve.formData(data);
		
	data.name = "reservationDate";
	data.value = document.querySelector(".last").dataset.reservationDate;
	resrvHtml += template.reserve.formData(data);
					
	document.querySelectorAll(".qty").forEach(function(price, index) {
		let count = parseInt(price.querySelector(".count_control_input").value);
		if (count !== 0) {
			data.name = "reservationPrices[" + index + "].count";
			data.value = count;
			resrvHtml += template.reserve.formData(data);
				
			data.name = "reservationPrices[" + index + "].productPriceId";
			data.value = price.dataset.priceId;
			resrvHtml += template.reserve.formData(data);
		}
	});
		
	document.querySelector(".form_horizontal").innerHTML += resrvHtml;
	document.querySelector(".form_horizontal").submit();
};
