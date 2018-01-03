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
				    input.type = "text"; 
				    add_chapters.innerHTML = "Add chapters"
				    all_chapters.innerHTML = "All chapters"
					like.innerHTML = "Like"
					comment.innerHTML = "Comment"
				    img.setAttribute('src', "Series/" + data.path);
				    img.setAttribute("width", "620");
				    img.setAttribute("height", "500");
				    img.style.margin = "0 auto";
				    img.style.display = "block";
				    like.style.margin = "0 auto";
				    like.style.display = "block";
				    comment.style.margin = "0 auto";
				    comment.style.display = "block";
				    input.style.margin = "0 auto";
				    input.style.display = "block";
				    input.style.padding = "20px 50px";
				    input.setAttribute('id', 'comment');
				    add_chapters.style.display = "block";
				    add_chapters.style.margin = "0 auto";
				    all_chapters.style.display = "block";
				    all_chapters.style.margin = "0 auto";
				    div.appendChild(img);
				    div.appendChild(like);
				    div.appendChild(add_chapters);
				    div.appendChild(all_chapters);
				    div.appendChild(input);
				    div.appendChild(comment);
				    
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
					    	window.location.href = './chload.html';
				    })
				    
				    all_chapters.addEventListener('click', function(){
				    	console.log(data.id);
				    	wrapper.post('./Chapters',{serie_id:data.id},{'Content-Type':'application/json'}).then((data)=>{
				    			img = []
				    			for (var i=0; i<data.length;i++) {	
				    				var div = $("images");
				    		        img[i] = new Image();
				    		        img[i].src = "chapters/" + data[i].path;
				    		        img[i].setAttribute("id", data[i].id);
				    		        img[i].setAttribute("height", "300");
				    		        img[i].setAttribute("width", "300");
				    		        img[i].style.padding = "60px 20px";
					    			div.appendChild(img[i]);
					    			img[i].addEventListener('click', function() {
					    				console.log(this.id);
					    				var div = $("images");
										var title = $("title");
										div.innerHTML = "";
										title.innerHTML = "Chapter";
									    var img = document.createElement('img');
									    var like = document.createElement('button');
									    var comment = document.createElement('button');
									    var input = document.createElement('input'); 
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
									    div.appendChild(like);
									    div.appendChild(input);
									    div.appendChild(comment);
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