var wrapper = new XHR();
function $(id) {
    return document.getElementById(id);
};

function load_img(){
	wrapper.get('./Index',{},{}).then((data) => {
		console.log(data);
		img = []
		for (var i=0; i<data.length;i++) {	
			var div = $("images");
	        img[i] = new Image();
	        img[i].src = "Series/" + data[i].path;
	        img[i].setAttribute("id", data[i].id);
	        img[i].setAttribute("height", "300");
	        img[i].setAttribute("width", "300");
	        img[i].style.padding = "60px 20px";
			div.appendChild(img[i]);	
			img[i].addEventListener('click', function() {
				wrapper.post('./Index',{serie_id:this.id},{'Content-Type':'application/json'}).then((data)=>{
					console.log(data);
					var div = $("images");
					var title = $("title");
					div.innerHTML = "";
					title.innerHTML = "Movie";
				    var img = document.createElement('img');
				    var chapters = document.createElement('button');
				    var like = document.createElement('button');
				    var input = document.createElement('input'); 
				    input.type = "text"; 
				    chapters.innerHTML = "Chapters"
					like.innerHTML = "Like"
				    img.setAttribute('src', "Series/" + data.path);
				    img.setAttribute("width", "620");
				    img.setAttribute("height", "500");
				    img.style.margin = "0 auto";
				    img.style.display = "block";
				    chapters.style.margin = "0 auto";
				    chapters.style.display = "block";
				    like.style.margin = "0 auto";
				    like.style.display = "block";
				    input.style.display = "block";
				    input.style.margin = "0 auto";
				    input.style.padding = "20px 50px";
				    div.appendChild(img);
				    div.appendChild(like);
				    div.appendChild(chapters);
				    div.appendChild(input);
				})
			});
			}
		}).catch(function(error) {
		console.log(error);
	})
}

addEventListener('load', load_img);