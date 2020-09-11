document.addEventListener("DOMContentLoaded", function(){
	promotionObj.initPromotion();
	itemsObj.initItems();
})

let section = {
		width: '',
		promotion: '',
		htmlLeft: '',
		htmlRight: '',
}

// 프로모션 슬라이드
const promotionObj = {

	initPromotion : function(){
		section.promotion = document.querySelector(".visual_img");
		section.width = document.querySelector(".visual_img").clientWidth;
		this.requestAjax();
		this.slide(section.promotion);
	},
	
	requestAjax() {
		ajaxRequest.sendRequest("GET", "api/promotions", showPromotions);
	},
	
	
	slide: function(ul){
		
		setTimeout(function(){
			ul.style.transition = "transform 1s";
			ul.style.transform = "translateX(-100%)";
			setTimeout(function(){
				ul.style.transition = "";
				ul.style.transform = "translate(0)";
				ul.appendChild(ul.querySelector("li.item:first-of-type"));
				promotionObj.slide(ul);
			},1000);
		},2000);
	},
	
}

// 상품 목록
const itemsObj = {
	
	hiddenMoreBtn : function(isHide){
		if(isHide){
			document.querySelector(".more_item .btn_more").style.display = "none";
		}else{
			document.querySelector(".more_item .btn_more").style.display = "block";

		}
	},
	
	change : function (){
		document.querySelector(".event_tab_lst").addEventListener("click", 
			function(clicked){
				if (clicked.target.tagName !== "SPAN") {
					return;
				}	
			
				let categoryId = clicked.target.parentElement.getAttribute('data-category');
				let tempId = clicked.target.parentElement; // <a class='anchor'><span>뮤지컬</span></a>
			
				if(categoryId === null){
					categoryId = tempId.parentElement.getAttribute('data-category');
				}
			
				// classList 써서 active 제거 후 다시 추가함
				let active = document.querySelector(".item .active");
	
				if(active !== null){
					active.classList.remove('active');
				}
				tempId.classList.add('active');
			
				this.hiddenMoreBtn(false);
				this.reset();					
				this.requestAjax("product", categoryId, 0);
					
			}.bind(itemsObj)
		);
	},
	
	more : function(){
		document.querySelector(".btn_more").addEventListener("click",
			function(){
				let categoryId = document.querySelector(".item .active").parentElement.getAttribute('data-category');
				this.requestAjax("product", categoryId, this.getIndexLastProduct());
			}.bind(itemsObj)
		);

	},
	
	append : function(lists){
		let index = 0;
		if(lists.productList.length > 0){			
			lists.productList.forEach(product => {
				// 왼쪽, 오른쪽 나누어서 product 정보들을 저장함
				if(index % 2 == 0){
					section.htmlLeft.innerHTML += template.main.product(product);
				}else{
					section.htmlRight.innerHTML += template.main.product(product);
				}
				index++;
			})
		}
		
		// more 버튼 제거
		if(lists.totalCount === this.getIndexLastProduct()){				
			this.hiddenMoreBtn(true);
		}
	},
	
	reset: function(){
		section.htmlLeft.innerHTML = "";
		section.htmlRight.innerHTML = "";
	},
	
	requestAjax : function(target, categoryId = 0 , start = 0){
		
		switch(target){
			case "product" : 
				let url = "api/products";
				if(categoryId !== 0){
					url += "?categoryId=" +categoryId;
					
					if(start !== 0){
						url += "&" + "start="+ start;
					}
				}else{
					if(start !== 0){
						url += "?start=" +start;
					}
				}
				
				ajaxRequest.sendRequest("GET", url ,showProducts);
				break;
				
			case "category" :
				ajaxRequest.sendRequest("GET", "api/categories", showCategories);
				break;
		}

	},

	getIndexLastProduct : function(){
		return (section.htmlLeft.childElementCount + section.htmlRight.childElementCount );

	},
	
	initItems : function(){
		section.htmlLeft = document.querySelector(".wrap_event_box").firstElementChild;
		section.htmlRight = document.querySelector('.wrap_event_box').firstElementChild.nextElementSibling;
		
		this.change();
		this.more();
		this.requestAjax("product");
		this.requestAjax("category");

	}
}

function showProducts(xhr){
	let lists = xhr.response;
	let productCount = document.querySelector(".pink");
	console.log(lists);
	productCount.innerHTML = lists.totalCount;
	itemsObj.append(lists); // append 함수를 실행해서 productList를 저장함
}


function showCategories(xhr){
	let categories = xhr.response;
	let categoryTab = document.querySelector(".event_tab_lst");
	
	categories.forEach((category) => {
		categoryTab.innerHTML += template.main.category(category);
	});
}

function showPromotions(xhr){
	let promotions = xhr.response;
	let promotionCount = 0;
	promotions.items.forEach((promotion) => {
		section.promotion.innerHTML += template.main.promotion(promotion);
		promotionCount++;
	});
}
