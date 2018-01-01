var xhr = new XMLHttpRequest();
function $(id) {
    return document.getElementById(id);
};

function mulUpload(){
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
	formData.append("names", input);
	formData.append("numbers", index);
	xhr.onreadystatechange = function () {
		if (xhr.status === 200 && xhr.readyState === 4) {
			$("uploadStatus").textContent = xhr.responseText + "\nFiles uploaded";
		}
	}
	xhr.open("post", "./UploadChapter", true);	
	xhr.send(formData);
	
}



$('load').addEventListener('click', function() {
	mulUpload();
	alert("done");
});