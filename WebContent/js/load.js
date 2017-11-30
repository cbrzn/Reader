function searchfor(id) {
	return document.getElementById(id);
}

var xhr = new XMLHttpRequest();
var myFile = "";
function upload() {
	var formData = new FormData();
	var input = document.getElementById("texxxt").value;
	formData.append("names", input);
	formData.append("file", searchfor("file").files[0]);
	myFile = searchfor("file").files[0].name;
	xhr.onreadystatechange = function () {
		if (xhr.status === 200 && xhr.readyState === 4) {
			searchfor("uploadStatus").textContent = xhr.responseText + "\nFile uploaded";
		}
	}
	
	xhr.open("post", "./Upload", true);	
	xhr.send(formData);
	
}

function mulUpload(){
	var formData = new FormData();
	var files = searchfor("files").files;
	console.log(files.length);
	for (var i = 0; i < files.length; i++) {
		  console.log('pao');
		  var file = files[i];
		  formData.append('photos[]', file, file.path);
		}	
	var input = document.getElementById("texxxt").value;
	formData.append("names", input);
	xhr.onreadystatechange = function () {
		if (xhr.status === 200 && xhr.readyState === 4) {
			searchfor("uploadStatus").textContent = xhr.responseText + "\nFiles uploaded";
		}
	}
	xhr.open("post", "./MultiUpload", true);	
	xhr.send(formData);
}

function download() {
	var url = "./Download?name="+ myFile;
	searchfor("myImg").src = url;
	var downloadWindow = window.open(url);
}