const MIN_TITLE_IMAGE_COUNT = 1;
const MAX_TITLE_IMAGE_COUNT = 2;

let flag = true;// 클릭 이벤트를 받지 않기 위한 flag

document.addEventListener("DOMContentLoaded", () => {

	var promotion = new Promotion();
	promotion.initPromotion(); // 상단 이벤트 슬라이드 
	
	var product = new Product();
	product.initProducts(); // 상품 정보

	var reservation = new Reservation();
	reservation.initReservation(); // 예매하기  + 한줄평
	
	var tab = new Tab();
	tab.initTab(); // tab

});

function Promotion(){};
function Product(){};
function Reservation(){};
function Tab(){};


Promotion.prototype.initPromotion = function(){
	this.requestAjax();
};

Promotion.prototype.requestAjax = function(){
	let displayInfoId = document.querySelector(".ct.main").dataset.displayId;
	ajaxRequest.sendRequest("GET", "api/products/"+displayInfoId, this.showProductDetail);
};

Promotion.prototype.showProductDetail = function(xhr){
	let res = xhr.response;
	Promotion.prototype.showTitleImages(res);// 1. 타이틀이미지
	
	
};

Promotion.prototype.showTitleImages = function(res){
	console.log(res);
	let productImages = res.productImages;
	let productDescription = res.displayInfo.productDescription;

	let one = {productImage : productImages[0], productDescription};
	let second = {productImage : productImages[1], productDescription};

	let titleImage = document.querySelector(".visual_img.detail_swipe");
	titleImage.innerHTML += template.detail.titleImage(one);

	if (productImages.length === 1) { // 이미지가 1장이면 버튼을 숨김
		document.querySelector(".spr_book2.ico_arr6_lt").classList.add("off");
		document.querySelector(".spr_book2.ico_arr6_rt").classList.add("off");

	} else {
		
		titleImage.innerHTML += template.detail.titleImage(second);
		document.querySelector(".num.off").firstElementChild.innerText = MAX_TITLE_IMAGE_COUNT;
		
		this.slideEvent(productImages);
	}

};

Promotion.prototype.slideEvent = function(productImages){
	document.querySelector(".btn_nxt").addEventListener("click", Promotion.prototype.slideImage);
	document.querySelector(".btn_prev").addEventListener("click", Promotion.prototype.slideImage);
};

Promotion.prototype.slideImage = function(click){
	if (flag) {
		flag = false;
		
		let titleSection = document.querySelector(".visual_img.detail_swipe");
		let firstImage = titleSection.firstElementChild;
		let secondImage = firstImage.nextElementSibling;
		let direction = click.target.closest("a").className;
		
		let width = document.querySelector("#container").clientWidth;
		let imageNum = parseInt(document.querySelector(".figure_pagination .num").innerText);

		
		if (direction === "btn_nxt") {
			titleSection.insertAdjacentElement('beforeEnd', firstImage.cloneNode(true));
			titleSection.style.transitionDuration = "0.3s";
			titleSection.style.transform = "translateX(" + (-width) + "px)";
			
			setTimeout(() => {
				titleSection.removeChild(firstImage);
				titleSection.style.transitionDuration = "0s";
				titleSection.style.transform = "translateX(0)";
				flag = true;
				imageNum = imageNum % MAX_TITLE_IMAGE_COUNT + 1;
				document.querySelector(".figure_pagination .num").innerText = imageNum;
			}, 300);
			
		} else {
			titleSection.insertAdjacentElement('afterBegin', secondImage.cloneNode(true));
			titleSection.style.transitionDuration = "0s";
			titleSection.style.transform = "translateX(" + (-width) + "px)";
			
			setTimeout(() => {
				titleSection.style.transitionDuration = "0.3s";
				titleSection.style.transform = "translateX(0)";
			}, 1);
			
			setTimeout(() => {
				titleSection.removeChild(secondImage);
				flag = true;
				imageNum = imageNum % MAX_TITLE_IMAGE_COUNT + 1;
				document.querySelector(".figure_pagination .num").innerText = imageNum;
			}, 300);
		}
	}

};

Product.prototype.initProducts = function(){
	this.requestAjax();
};

Product.prototype.requestAjax = function(){
	let displayInfoId = document.querySelector(".ct.main").dataset.displayId;
	ajaxRequest.sendRequest("GET", "api/products/"+displayInfoId, this.showProductDetail);
}
	
Product.prototype.showProductDetail = function(xhr){
	let res = xhr.response;
	
	document.querySelector(".bk_more._open").addEventListener("click", Product.prototype.readMore); // 2-1. 펼쳐보기
	document.querySelector(".bk_more._close").addEventListener("click", Product.prototype.readHide); // 2-2. 접기
	
	Product.prototype.showProductContent(res.displayInfo.productContent);  // 2-3. 펼쳐보기 상세설명
	Product.prototype.showEvent(res.displayInfo.productEvent);// 3. 이벤트
	
};

// 2-1.펼쳐보기
Product.prototype.readMore =function(){
	document.querySelector(".store_details.close3").classList.remove("close3");
	document.querySelector(".bk_more._open").style.display = "none";
	document.querySelector(".bk_more._close").style.display = "";
};

// 2-2. 접기
Product.prototype.readHide = function(){
	document.querySelector(".store_details").classList.add("close3");
	document.querySelector(".bk_more._open").style.display = "";
	document.querySelector(".bk_more._close").style.display = "none";

};

// 2-3. 펼쳐보기 상세정보
Product.prototype.showProductContent = function(productContent){
	document.querySelector(".store_details .dsc").innerHTML += productContent;
};

// 3. 이벤트정보(jsp 구현)
Product.prototype.showEvent = function(productEvent){
	document.querySelector(".event_info .in_dsc").innerHTML += productEvent;

};

Reservation.prototype.initReservation = function(){
	this.requestAjax();
};

Reservation.prototype.requestAjax = function(){
	let displayInfoId = document.querySelector(".ct.main").dataset.displayId;
	ajaxRequest.sendRequest("GET", "api/products/"+displayInfoId, this.showProductDetail);
};
		
Reservation.prototype.showProductDetail = function(xhr){
	let res = xhr.response;

	document.querySelector(".section_btn").addEventListener("click", Reservation.prototype.reservate); // 4. 예약하기
	Reservation.prototype.getAverageScore(res.averageScore); // 5-1. 평점
	Reservation.prototype.getComments(res.comments); // 5-2. 한줄평
};

Reservation.prototype.reservate = function(){
	console.log("reservation click");
};

// 5-1. 평점
Reservation.prototype.getAverageScore = function(averageScore){
	
	averageScore = averageScore.toFixed(1);
	document.querySelector(".text_value").firstElementChild.innerText = averageScore;
	document.querySelector(".graph_value").style.width = averageScore * 100 / 5.0 + "%";
};

// 5-2. 한줄평
Reservation.prototype.getComments = function(comments){		
	
	let commentsHtml = comments.slice(0,3).reduce((innerHtml, comment) => {
		comment.score = comment.score.toFixed(1);
		return innerHtml + template.detail.comment(comment);
	}, "");

	document.querySelector(".list_short_review").innerHTML += commentsHtml;
	document.querySelector(".green").innerText = comments.length + "건";
};


Tab.prototype.initTab = function(){
	this.requestAjax();
};

Tab.prototype.requestAjax = function(){
	let displayInfoId = document.querySelector(".ct.main").dataset.displayId;
	ajaxRequest.sendRequest("GET", "api/products/"+displayInfoId, this.showProductDetail);
};
		
Tab.prototype.showProductDetail = function(xhr){
	let res = xhr.response;
	
	document.querySelector(".info_tab_lst").addEventListener("click", Tab.prototype.changeTab); // 상세페이지 탭 변경
	Tab.prototype.getIntroduce(res); // 6-1. 상세정보
	Tab.prototype.getLocation(res.displayInfoImages, res.displayInfo); // 6-2. 오시는길
};

// 6-1. 상세정보
Tab.prototype.getIntroduce = function(res){
	
	// 소개글
	document.querySelector(".detail_info_lst .in_dsc").innerHTML += res.displayInfo.productContent;
	let productImageInfo = document.querySelector(".in_img_group.second");
	// 공연정보이미지
	let productImageHTML = res.productImages.reduce((innerHtml, image) => {
		return innerHtml + template.detail.productImageInfo(image);
	}, "");
	
	productImageInfo.innerHTML += productImageHTML;
};

// 6-2. 오시는길
Tab.prototype.getLocation = function(displayInfoImages, displayInfo){
	
	// 지도
	let imageHTML = document.querySelector(".store_location .store_map.img_thumb");
		displayInfoImages.forEach( (images) => {
		imageHTML.src = images.displayInfoSaveFileName;
	})
	
	// 전시타이틀
	document.querySelector(".store_name").innerHTML = 
		displayInfo.categoryName + " " + displayInfo.productDescription;

	// 새주소
	document.querySelector(".store_addr_bold").innerHTML = displayInfo.placeStreet;
	
	//  지번주소
	document.querySelector(".addr_old_detail").innerHTML = displayInfo.placeLot;
	
	// 세부주소
	document.querySelector(".addr_detail").innerHTML = displayInfo.placeName;

	// 전화번호
	document.querySelector(".store_tel").innerHTML = displayInfo.telephone;
	document.querySelector(".store_tel").href= "tel:" + displayInfo.telephone;
	
};

// 6-3. 상세정보/ 오시는길 Tab 변경
Tab.prototype.changeTab = function(clicked){
	
	let activeTab = document.querySelector(".anchor.active").closest("li");	
	let clickTab = clicked.target.closest("li");
	
	// 현재 탭과 같은 탭을 클릭했을 때
	if(activeTab.querySelector("span") === clickTab.querySelector("span")){
		return;
		
	}else{
		
		// 활성화 변경
		activeTab.firstElementChild.classList.remove("active");
		clickTab.firstElementChild.classList.add("active");
		
		// 탭 페이지 변경
		document.querySelectorAll("[data-info]").forEach((details) => {
			if(details.classList.contains("hide")){
				details.classList.remove("hide");
				
			}else{
				details.classList.add("hide");
			}
		});
	}
};

