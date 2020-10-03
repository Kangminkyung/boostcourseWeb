document.addEventListener("DOMContentLoaded", ()=> {
	var reviewWrite = new Review();
	reviewWrite.init();

});

function Review(){
	let displayInfoId = Review.prototype.getDisplayInfoId();
}

Review.prototype.init = function(){
	Review.prototype.initTitle();
	Review.prototype.initRatingPoint();
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
Review.prototype.initRatingPoint = function(){
	let reviewRating = document.querySelector(".review_rating");
	let rating = 0; // rating 배열 순서 체크
	let visited = [];
	let ratingList = Array.prototype.slice.call(reviewRating.querySelector(".rating").childNodes).filter((element) => {
		if(element.nodeName === "INPUT"){
			visited.push(false);
			return true;
		}
	});
	
	reviewRating.addEventListener("click", function(e){
		if(e.target.tagName === "INPUT"){
			let clickedRating = parseInt(e.target.value);
			console.log(clickedRating);
			
			// 현재 별점이 클릭한 별점보다 크면 checked 추가
			if(rating < clickedRating){
				for(let i = rating; i < clickedRating; i++){
					console.log(i);
					console.log(clickedRating);
					ratingList[i].classList.add("checked");
					console.log(ratingList[i]);
				}
				// 현재 별점이 클릭한 별점보다 작으면 check 해제
			}else if(rating > clickedRating){
				for(let i = rating - 1; i>clickedRating -1; i--){
					ratingList[i].classList.remove("checked");
					console.log(i);
					console.log(clickedRating);
					console.log(ratingList[i]);
					if(visited[i] === false){
						ratingList[i].classList.remove("checked");
					}else{
						ratingList[i].click();
					}
				}
			}
			
			if(visited[clickedRating -1] === false){
				visited[clickedRating - 1] = true;
			}else{
				visited[clickedRating -1] = false;
			}
			rating = clickedRating;
			document.querySelector(".star_rank").innerText = rating;
			if(document.querySelector(".star_rank").innerText > 0){
				document.querySelector(".star_rank").classList.remove("gray_star");
			}
		}
	});
	console.log(document.querySelector(".star_rank.gray_star"));
};

/* 리뷰 텍스트*/

/* 첨부파일 */




