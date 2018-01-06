var wrapper = new XHR();
function $(id) {
    return document.getElementById(id);
};

function mulUpload(){
	var xhr = new XMLHttpRequest();
	var formData = new FormData();
	var files = $("files").files;
	console.log(files.length);
	for (var i = 0; i < files.length; i++) {
		  console.log('pao');
		  var file = files[i];
		  formData.append('photos[]', file, file.path);
		}	
	var input = $("texxxt").value;
	var index = $("index").value;
	var serie_id = $("serie_id").innerHTML;
	formData.append("names", input);
	formData.append("numbers", index);
	formData.append("serie_id", serie_id);
	xhr.onreadystatechange = function () {
		if (xhr.status === 200 && xhr.readyState === 4) {
			$("uploadStatus").textContent = xhr.responseText + "\nFiles uploaded";
		}
	}
	xhr.open("post", "./UploadChapter", true);	
	xhr.send(formData);
	
}

function download() {
	var myFile = "";
	var url = "./Download?name="+ myFile;
	$("serie_id").innerHTML = url;
	var downloadWindow = window.open(url);
}


function load_img(){
	wrapper.get('./Index',{},{}).then((data) => {
		console.log(data);
		img = []
		for (var i=0; i<data.length-1;i++) {	
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
				    var add_chapters = document.createElement('button');
				    var like = document.createElement('button');
				    var all_chapters = document.createElement('button');
				    var input = document.createElement('input'); 
				    var comment = document.createElement('button');
				    var download = document.createElement('button');
		            var serie_id = document.createElement('input');
		            serie_id.setAttribute('name', 'serie_id');
		            serie_id.setAttribute('value', 'serie_id');
		            serie_id.innerHTML = data[0].id;
		            serie_id.style.display = 'none';
		            var path = document.createElement('div');
		            path.setAttribute('name', 'path');
		            path.setAttribute('id', 'path');
		            path.innerHTML = data[0].path;
		            path.style.display = 'none';
		            input.type = "text"; 
				    add_chapters.innerHTML = "Add chapters"
					download.innerHTML = "Download"
				    all_chapters.innerHTML = "All chapters"
					like.innerHTML = "Like"
					comment.innerHTML = "Comment"
				    img.setAttribute('src', "Series/" + data[0].path);
				    img.setAttribute("width", "620");
				    img.setAttribute("height", "500");
				    img.style.margin = "0 auto";
				    img.style.display = "block";
				    like.style.margin = "0 auto";
				    like.style.display = "block";
				    comment.style.margin = "0 auto";
				    comment.style.display = "block";
				    download.style.margin = "0 auto";
				    download.style.display = "block";
				    input.style.margin = "0 auto";
				    input.style.display = "block";
				    input.style.padding = "20px 50px";
				    input.setAttribute('id', 'comment');
				    add_chapters.style.display = "block";
				    add_chapters.style.margin = "0 auto";
				    all_chapters.style.display = "block";
				    all_chapters.style.margin = "0 auto";
				    console.log(data[1].logged == true);
				    div.appendChild(img);
				    div.appendChild(download);
				    div.appendChild(all_chapters);
				    if (data[1].logged == true) {
				    	if (data[3].admin == "true" || data[2].current_user === data[0].user_id) {
				    div.appendChild(add_chapters);
				    	}
				    }
				    div.appendChild(serie_id);
				    div.appendChild(path);
				    if (data[1].logged == "true") {
				    div.appendChild(like);
				    div.appendChild(input);
				    div.appendChild(comment);
				    }
				    download.addEventListener('click', function() {
				    	var url = "./Download?serie_id=" + data.id +"&path=/"+data.path;
				    	var downloadWindow = window.open(url);
						alert("done");
				          console.log(data)
			
				    })
				    
				    like.addEventListener('click', function() {
					    	wrapper.post('./LikeSerie', {serie_id:data.id}, {'Content-Type':'application/json'}).then((data)=> {
					    		console.log(data);
					    	})
				    })
				    
				    comment.addEventListener('click', function() {
				    		var comment = $('comment').value;
					    	wrapper.post('./CommentSerie', {serie_id:data.id, comment:comment}, {'Content-Type':'application/json'}).then((data)=> {
					    		console.log(data);
					    	})
				    })
				    
				    add_chapters.addEventListener('click', function(){
				          $('load_form').style.display = "block";
				          $('test').style.display = "none";
				          var serie_id = document.createElement('div');
				          serie_id.setAttribute('name', 'serie_id');
				          serie_id.setAttribute('id', 'serie_id');
				          serie_id.innerHTML = data.id;
				          serie_id.style.display = 'none';
				          $('serie').appendChild(serie_id);
				          $('load').addEventListener('click', function() {
						    	mulUpload();
						    	alert("done");
						    });
				          console.log(data)
				    })
				    
				    
				    
				    all_chapters.addEventListener('click', function(){
				    	if (data[1].logged == true) {
					    	var current_user = data[2].current_user;
					    	var admin = "true";
				    	}
				    	var logged = data[1].logged;
				    	wrapper.post('./Chapters',{serie_id:data[0].id},{'Content-Type':'application/json'}).then((data)=>{
				    		img = []
				    			for (var i=0; i<data.length;i++) {	
				    				var div = $("images");
				    		        img[i] = new Image();
				    		        img[i].src = "chapters/" + data[i].path;
				    		        img[i].setAttribute("id", data[i].id);
				    		        img[i].setAttribute("height", "300");
				    		        img[i].setAttribute("width", "300");
				    		        img[i].style.padding = "60px 20px";
				    		        img[i].setAttribute("path", data[i].path);
					    			div.appendChild(img[i]);
					    			var current_user2 = current_user;
					    			img[i].addEventListener('click', function() {
					    				var div = $("images");
										var title = $("title");
										div.innerHTML = "";
										title.innerHTML = "Chapter";
									    var img = document.createElement('img');
									    var like = document.createElement('button');
									    var comment = document.createElement('button');
									    var input = document.createElement('input'); 
									    var download = document.createElement('button');
							            var chapter_id = document.createElement('input');
							            chapter_id.setAttribute('name', 'chapter_id');
							            chapter_id.setAttribute('value', 'chapter_id');
							            chapter_id.innerHTML = this.id;
							            chapter_id.style.display = 'none';
							            download.innerHTML = "Download"
									    input.type = "text"; 
									    comment.innerHTML = "Comment"
										like.innerHTML = "Like"
										img.setAttribute('id', this.id);
									    img.setAttribute('src', this.src);
									    img.setAttribute("width", "620");
									    img.setAttribute("height", "500");
									    img.style.margin = "0 auto";
									    img.style.display = "block";
									    like.style.margin = "0 auto";
									    like.style.display = "block";
									    input.style.margin = "0 auto";
									    input.style.display = "block";
									    input.style.padding = "20px 50px";
									    input.setAttribute("id", "comment");
									    comment.style.display = "block";
									    comment.style.margin = "0 auto";
									    div.appendChild(img);
									    div.appendChild(chapter_id);
									    div.appendChild(download);
									    if (logged == true) {  	
									    	div.appendChild(like);
									    	div.appendChild(input);
									    	div.appendChild(comment);
									    }
									    
									    download.addEventListener('click', function() {
									    	console.log(img.id)
									    	var url = "./DownloadChap?chapter_id=" + img.id;
									    	var downloadWindow = window.open(url);
									          console.log(data);
									    })
									    
									    comment.addEventListener('click', function() {
									    	console.log(img.id);
									    	var comment = $('comment').value;
									    	wrapper.post('./CommentChapter', {chapter_id:img.id, comment:comment}, {'Content-Type':'application/json'}).then((data)=> {
									    		console.log(data);
									    	})
									    });
									    like.addEventListener('click', function() {
									    	wrapper.post('./LikeChapter', {chapter_id:img.id},{'Content-Type':'application/json'}).then((data)=> {
									    		console.log(data);
									    	})
									    });

				    				});
				    			}
				    	});
				    });
				 });
			});
		}
	});
}

addEventListener('load', load_img);
