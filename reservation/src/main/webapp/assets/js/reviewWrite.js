document.addEventListener("DOMContentLoaded", ()=> {
	var reviewWrite = new Review();
	reviewWrite.init();

});

function Review(){
	let displayInfoId = Review.prototype.getDisplayInfoId();
}

Review.prototype.init = function(){
	Review.prototype.initTitle();
};

/* id 값 불러오기 */
Review.prototype.getDisplayInfoId = function(){
	let url = location.search;
	let params = new URLSearchParams(url);
	let displayInfoId= params.get("displayInfoId");
	return displayInfoId;	
};

Review.prototype.getReservationId = function(){
	let url = location.search;
	let params = new URLSearchParams(url);
	let reservationId= params.get("reservationId");
	return reservationId;	
};

Review.prototype.getProductId = function(){
	let url = location.search;
	let params = new URLSearchParams(url);
	let productId= params.get("productId");
	return productId;	
};

/* 타이틀 */
Review.prototype.initTitle = function(){
	let displayInfoId = Review.prototype.getDisplayInfoId();
	ajaxRequest.sendRequest("GET", "api/products/"+displayInfoId, this.showTitle);
};

Review.prototype.showTitle = function(res){
	let displayInfo = res.response.displayInfo;
	document.querySelector(".top_title").innerHTML += template.reviewWrite.titleDescription(displayInfo);
};

/* 별점 */


/* 리뷰 텍스트*/

/* 첨부파일 */




