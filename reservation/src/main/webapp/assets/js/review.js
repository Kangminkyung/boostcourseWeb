document.addEventListener("DOMContentLoaded", () => {
	reviewObj.initReview();
});

const reviewObj = {
	initReview : function() {
		let displayInfoId = document.querySelector(".ct").dataset.displayId;
		this.requestAjax(displayInfoId);
	},	
	
	requestAjax : function(displayInfoId){
		ajaxRequest.sendRequest("GET", "api/products/"+displayInfoId, this.showReview);
	},
	
	showReview: function(xhr){
		let res = xhr.response;
		reviewObj.getTitle(res.displayInfo); 
		reviewObj.getAverageScore(res.averageScore);
		reviewObj.getComments(res.comments);
	},
	
	getTitle : function(displayInfo){
		let productDescription = displayInfo.productDescription;
		document.querySelector(".top_title .title").innerText = productDescription;
	},
	
	getAverageScore: function(averageScore){
		averageScore = averageScore.toFixed(1);
		document.querySelector(".text_value").firstElementChild.innerText = averageScore;
		document.querySelector(".graph_value").style.width = averageScore * 100 / 5.0 + "%";
	},
	
	getComments: function(comments){
		let commentsHtml = comments.reduce((innerHtml, comment) => {
			comment.score = comment.score.toFixed(1);
		
			return innerHtml + template.review.comment(comment);
		}, "");

		
		document.querySelector(".list_short_review").innerHTML += commentsHtml;
		document.querySelector(".green").innerText = comments.length + "건";
	},	
	
}
