function XHR() {

	var jsonToParams = json => {
		var res = "";
		for (var attr in json) {
			if (res === "") {
				res = attr + "=" + json[attr];
			} else {
				res += "&" + attr + "=" + json[attr];
			}
		}
		return res;
	};

	this.send = function(method, url, params, headers) { 
		return new Promise(function(fullfill, reject) {
			var wrapper = new XMLHttpRequest();
			wrapper.open(method, url);
			wrapper.onload = function()  {
				if (this.status == 200) {
					fullfill(JSON.parse(wrapper.responseText));
				} else {
					reject({ status: this.status, statusText: wrapper.statusText });
				}
			};

			for (var header in headers) {
				wrapper.setRequestHeader(header, headers[header]);
			}

			if (method === 'POST') {
				wrapper.send(JSON.stringify(params));
			} else {
				wrapper.send();
			}
		});
	}

	this.get = function(url, params, headers) {
		params=typeof params==="string" ? params : jsonToParams(params);
		url += "?" + params;
		this.send('GET', url, params, headers)
			.then(function(data) {
				console.log(data);
				})
			.catch(function(error) {
				console.log(error);
			})
	}

	this.post = function(url, params, headers) {
		this.send('POST', url, params, headers)
			.then(function(data) {
				if(data.status == ("200")) {
					window.location.href = "./index.html";
					alert("Log in successful")
				} else {
					alert("Wrong password or email");
				}
				console.log(data);
			})
			.catch(function(error) {
				console.log(error);
			})
	}
	
	this.img = function(url, params, headers) {
		this.send('GET', url, params, headers)
			.then(function(data) {
				for (var i=0; i<data.length;i++) {	
					var div = document.getElementById("images");
					var elem = document.createElement("img");
					elem.src = "Img/" + data[i].path;
					elem.setAttribute("height", "300");
					elem.setAttribute("width", "300");
					elem.style.padding = "60px 20px";
					div.appendChild(elem);		
					}

				})
			.catch(function(error) {
				console.log(error);
			})
	}
}