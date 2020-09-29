let handlebars = {
	// main
	category: undefined,
	product : undefined,
	promotion: undefined,
	
	// detail
	titleImage : undefined,
	comment: undefined,
	productImageInfo: undefined,
	
	// review
	review: undefined,
	
	// reserve
	productDescription: undefined,
	titleInfo : undefined,
	storeDetails: undefined,
	productPrices : undefined,
	formData : undefined,
	
	// myreservation
	summaryInfo : undefined,
	usedSection : undefined,
	confirmedSection : undefined,
	canceledSection: undefined,
	emptySection : undefined,
	reviewPopup : undefined,
	cancelPopup : undefined,
	
	// login
	showWarningMsg: undefined,
	
	// reviewWrite
	titleDescription : undefined,
	
	precompile(registerTemplate, id, data) {
		
		if (registerTemplate === undefined) {
			let template = document.querySelector(id).innerText; // 핸들바 템플릿 가져오기
			registerTemplate = Handlebars.compile(template); // 핸들바 템플릿 컴파일
		}
		return registerTemplate(data); // 핸들바 템플릿에 데이터를 바인딩
	},
	
}

let template = {
	main: {
		promotion(data){
			return handlebars.precompile(handlebars.promotion, "#promotion", data);
		},
		category(data) {
			return handlebars.precompile(handlebars.category, "#category", data);
		},
		product(data){
			return handlebars.precompile(handlebars.product, "#product", data);
		}
	},
	
	detail: {
		titleImage(data){
			return handlebars.precompile(handlebars.titleImage, "#titleImage", data);
		},
		comment(data){
			return handlebars.precompile(handlebars.comment, "#comment", data);
		},
		productImageInfo(data){
			return handlebars.precompile(handlebars.productImageInfo, "#productImageInfo", data);
		}
	},
	
	review: {
		comment(data){
			return template.detail.comment(data); // detail comment와 동일함
		}
	},
	
	reserve: {
		productDescription(data){
			return handlebars.precompile(handlebars.productDescription, "#productDescription", data);
		},
		titleInfo(data){
			return handlebars.precompile(handlebars.titleInfo, "#titleInfo", data);
		},
		storeDetails(data){
			return handlebars.precompile(handlebars.storeDetails, "#storeDetails", data);
		},
		productPrices(data){
			return handlebars.precompile(handlebars.productPrices, "#productPrices", data);
		},
		formData(data) {
			return handlebars.precompile(handlebars.formData, "#hiddenInfo", data);
		}
	},
	
	myreservation: {
		summaryInfo(data){
			return handlebars.precompile(handlebars.summaryInfo, "#summaryInfo", data);
		},
		usedSection(data){
			return handlebars.precompile(handlebars.showUsedSection, "#usedSection", data);
		},
		confirmSection(data){
			return handlebars.precompile(handlebars.showConfirmedSection, "#confirmSection", data);
		},
		cancelSection(data){
			return handlebars.precompile(handlebars.showCanceledSection, "#cancelSection", data);
		},
		emptySection(data){
			return handlebars.precompile(handlebars.emptySection, "#emptySection", data);
		},
		reviewPopup(data){
			return handlebars.precompile(handlebars.emptySection, "#reviewPopup", data);
		},
		cancelPopup(data){
			return handlebars.precompile(handlebars.emptySection, "#cancelPopup", data);

		}
	},

	login: {
		showWarningMsg(data){
			return handlebars.precompile(handlebars.showWarningMsg, "#warning_msg", data);
		}
	},
	
	reviewWrite: {
		titleDescription(data){
			return handlebars.precompile(handlebars.titleDescription, "#titleDescription", data);
		},
	},
}
