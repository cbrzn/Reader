var wrapper = new XHR();
	function $(id) {
		return document.getElementById(id);
	}

	function new_account() {
		var email = $('email').value;
		var username = $('username').value;
		var password = $('password').value;
		var admin = $('admin').value;
		wrapper.post('./Register',{email:email, username:username, pass:password, admin:admin},{'Content-Type':'application/json'}).then(function(data){
			if(data.status == (200)) {
				window.location.href = "./index.html";
				alert("Log in successful")
			} else {
				alert("Wrong password or email");
			}
			console.log(data);
		}).catch(function(error) {
			console.log(error);
			});
	}	

	function login() {
		var email = $('email').value;
		var password = $('password').value;
		wrapper.post('./Login',{email:email, pass:password},{'Content-Type':'application/x-www-form-urlencoded'}).then(function(data){
			if(data.status == (200)) {
				window.location.href = "./index.html";
				alert("Log in successful")
			} else {
				alert("Wrong password or email");
			}
			console.log(data);
		}).catch(function(error) {
			console.log(error);
			});
	}	
	
	function is_logged() {
		wrapper.get('./Login',{},{'Content-Type':'application/x-www-form-urlencoded'}).then((data) => {
		});
	}
	
	$('login').addEventListener('click', login);