document.addEventListener("DOMContentLoaded", ()=> {
	var reviewWrite = new Review();
	reviewWrite.init();

});

function Review(){
	let displayInfoId = Review.prototype.getParameters();
	console.log(displayInfoId);
}

Review.prototype.init = function(){
	Review.prototype.initTitle();
};

Review.prototype.getParameters = function(){
	let url = location.search;
	let params = new URLSearchParams(url);
	let displayInfoId = params.get("displayInfoId");
	return displayInfoId;	
};

Review.prototype.initTitle = function(){
	let displayInfoId = Review.prototype.getParameters();
	ajaxRequest.sendRequest("GET", "api/products/"+displayInfoId, this.showTitle);
};

Review.prototype.showTitle = function(res){
	let displayInfo = res.response.displayInfo;
	console.log(displayInfo);
	document.querySelector(".top_title").innerHTML += template.reviewWrite.titleDescription(displayInfo);
}