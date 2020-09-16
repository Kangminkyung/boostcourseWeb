
let ajaxRequest = {
	sendRequest(method, url, callBack) {
		let xhr = new XMLHttpRequest();
		xhr.responseType = 'json';
		
		xhr.addEventListener("load", () => {
			if (xhr.status === 200) {
				callBack(xhr);
			} else {
				alert("문제가 발생했습니다.")
			}
		});
		
		xhr.open(method, url);
		xhr.send();
	},

}