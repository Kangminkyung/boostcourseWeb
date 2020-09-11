document.addEventListener("DOMContentLoaded", () => {
	loginObj.initLogin();
});

const loginObj = {
		
	initLogin: function(){
		document.querySelector("#resrv_id").addEventListener("keydown", function() {
			loginObj.checkEmail();
		});
	},
	
	checkEmail: function(){
		let email = document.querySelector("#resrv_id").value;
		let emailValid = (/^[\w]\w+\@\w+\.\w+\.*\w*/).test(email);
		
		let loginBtn = document.querySelector(".login_btn.confirm");
		loginBtn.disabled = true;
		
		console.log(document.querySelector(".login_btn.confirm"));

		if(emailValid){
			document.querySelector(".warning_msg").style.display = "none";
			
			// 버튼 비활성화 제거
			if(loginBtn.disabled){
				loginBtn.disabled = false;
			}
			
		}else{
			document.querySelector(".warning_msg").style.display = "block";
		}
	}

}