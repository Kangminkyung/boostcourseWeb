document.addEventListener("DOMContentLoaded", () => {
	var review = new Review();
	review.initReview(); 
});

function Review(){};

Review.prototype.initReview = function() {
	let displayInfoId = document.querySelector(".ct").dataset.displayId;
	this.requestAjax(displayInfoId);
};

Review.prototype.requestAjax = function(displayInfoId){
	ajaxRequest.sendRequest("GET", "api/products/"+displayInfoId, this.showReview);
};

Review.prototype.showReview = function(xhr){
	let res = xhr.response;
	Review.prototype.getTitle(res.displayInfo); 
	Review.prototype.getAverageScore(res.averageScore);
	Review.prototype.getComments(res.comments);
};

Review.prototype.getTitle = function(displayInfo){
	let productDescription = displayInfo.productDescription;
	document.querySelector(".top_title .title").innerText = productDescription;
};

Review.prototype.getAverageScore = function(averageScore){
	averageScore = averageScore.toFixed(1);
	document.querySelector(".text_value").firstElementChild.innerText = averageScore;
	document.querySelector(".graph_value").style.width = averageScore * 100 / 5.0 + "%";
};

Review.prototype.getComments =function(comments){
	let commentsHtml = comments.reduce((innerHtml, comment) => {
		comment.score = comment.score.toFixed(1);
	
		return innerHtml + template.review.comment(comment);
	}, "");

	
	document.querySelector(".list_short_review").innerHTML += commentsHtml;
	document.querySelector(".green").innerText = comments.length + "건";
};
