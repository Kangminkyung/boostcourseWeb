document.addEventListener("DOMContentLoaded", ()=> {
	var reviewWrite = new Review();
	reviewWrite.init();

});

function Review(){
	let displayInfoId = Review.prototype.getDisplayInfoId();
	this.reviewWriteInfo = document.querySelector(".review_write_info");
	this.reviewTextarea =document.querySelector(".review_textarea");
	this.guideReview =document.querySelector(".guide_review");
}

Review.prototype.init = function(){
	Review.prototype.initTitle(); // 타이틀 
	Review.prototype.initRatingScore(); // 별점
	Review.prototype.initTextarea();// 텍스트
	Review.prototype.initFile();// 파일
	
	Review.prototype.initFormCheck();// form 체크
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

Review.prototype.getReviewContent = function(){
	let textLimit = parseInt(document.querySelector(".guide_review .textLimit").innerText);
	let textLower = parseInt(document.querySelector(".guide_review .textLower").innerText);
	let count = document.querySelector(".textCount").innerText;
	
	if(count >= textLower && count <= textLimit) return 1;
	else return -1;
	
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
Review.prototype.initRatingScore = function(){
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
			
			// 현재 별점이 클릭한 별점보다 크면 checked 추가
			if(rating < clickedRating){
				for(let i = rating; i < clickedRating; i++){
					ratingList[i].classList.add("checked");
				}
				// 현재 별점이 클릭한 별점보다 작으면 check 해제
			}else if(rating > clickedRating){
				for(let i = rating - 1; i>clickedRating -1; i--){
					ratingList[i].classList.remove("checked");
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
};

/* 리뷰 텍스트*/
Review.prototype.initTextarea = function(){
	
	let reviewWriteInfo = document.querySelector(".review_write_info");
	let reviewTextarea =document.querySelector(".review_textarea");
	
	reviewWriteInfo.addEventListener("focus", function(){
		reviewWriteInfo.classList.add("blind");
		reviewTextarea.classList.add("focus");
	});

	reviewTextarea.addEventListener("blur", function(e){
		if(e.target.value === 0){
			reviewTextarea.classList.remove("focus");
			reviewWriteInfo.classList.remove("blind");
		}
	});
	
	reviewTextarea.addEventListener("keydown", function(e){
		Review.prototype.writingText(e);
	});
	
	reviewTextarea.addEventListener("keyup", function(e){
		Review.prototype.writingText(e);
	});
};

Review.prototype.writingText = function(e){
	let textLimit = parseInt(document.querySelector(".guide_review .textLimit").innerText);
	let guideCount =document.querySelector(".guide_review .textCount");

	let reviewTextarea = document.querySelector(".review_textarea");

	if(e.target.value.length > textLimit){
		reviewTextarea.value = reviewTextarea.value.substring(0, textLimit);
		guideCount.innerText = textLimit;
		alert('글자수가 '+textLimit+'자가 넘습니다.');
		
	}else{
		guideCount.innerText = e.target.value.length;
	}
};

/* 첨부파일 */
Review.prototype.validFile = function(image){
	if(image.type === "image/jpg" || image.type === "image/png" || image.type === "image/jpeg")
		return true;
	else return false;
};

Review.prototype.initFile = function(){
	let imageFile = null;
	
	// 사진 등록
	document.querySelector("#reviewImageFileOpenInput").addEventListener("change", function(e){
		let image = e.target.files[0];
		imageFile = image;
		
		console.log(image);
		if(!Review.prototype.validFile(image)){
			alert("이미지는 jpg와 png 형식만 가능합니다.");
			return;
		}
		document.querySelector(".review_photos").querySelector(".item").style.display = "block";
		document.querySelector(".review_photos").querySelector(".item_thumb").src = window.URL.createObjectURL(image);
	});
	
	// 사진 삭제
	document.querySelector(".ico_del").addEventListener("click", function(e){
		e.target.closest(".item").style.display = "none";
		imageFile = null;
		document.querySelector("#reviewImageFileOpenInput").value = "";
	});
	
};

/* form */

Review.prototype.initFormCheck = function(){

	document.querySelector(".bk_btn").addEventListener("click", this.updateForm);
};

Review.prototype.updateForm = function(){
	
    let reviewForm = document.querySelector("#reviewForm");
    let hiddenSection = document.querySelector("#hiddenSection");
	let textLimit = parseInt(document.querySelector(".guide_review .textLimit").innerText);

    document.querySelector("#reservationId").value = Review.prototype.getReservationId();
    document.querySelector("#productId").value = Review.prototype.getProductId();

    document.querySelector("#score").value = document.querySelector(".star_rank").innerText;
    document.querySelector("#reviewContent").value = Review.prototype.getReviewContent();
    console.log(document.querySelector(".review_textarea").value);
    console.log(document.querySelector(".review_textarea").value.substring(0, textLimit));
    console.log(document.querySelector("#comment"));

    document.querySelector("#comment").value = document.querySelector(".review_textarea").value.substring(0, textLimit);
  
    document.querySelector("#reviewForm").onsubmit = () => {
    	console.log(Review.prototype.checkForm());
    	return Review.prototype.checkForm(); // onsubmit= true일때만 폼 전송
    }
    
};

Review.prototype.checkForm = function(){

	console.log("체크폼");
	if(document.querySelector("#reviewContent").value > 0){
		console.log(typeof(document.querySelector("#reviewContent").value));
		console.log(document.querySelector("#reviewContent").value);
		return true;
		
	}else{
		console.log(document.querySelector("#reviewContent").value);
		alert("텍스트는 5자 이상 400자 이하로 입력하세요");
		return false;
	}	
}

