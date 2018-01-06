"use strict";
var w3 = {};

w3.includeHTML = function(cb) {
  var z, i, elmnt, file, xhttp;
  z = document.getElementsByTagName("*");
  for (i = 0; i < z.length; i++) {
    elmnt = z[i];
    file = elmnt.getAttribute("w3-include-html");
    if (file) {
      xhttp = new XMLHttpRequest();
      xhttp.onreadystatechange = function() {
        if (this.readyState == 4) {
          if (this.status == 200) {elmnt.innerHTML = this.responseText;}
          if (this.status == 404) {elmnt.innerHTML = "Page not found.";}
          elmnt.removeAttribute("w3-include-html");
          w3.includeHTML(cb);
        }
      }
      xhttp.open("GET", file, true);
      xhttp.send();
      return;
    }
  }
  if (cb) cb();
};


function $(id) {
    return document.getElementById(id);
};

function log(){
    var xhr = new XHR();
    xhr.get('./Session',{},{}).then((data)=>{
       console.log(data.status);
     if (data.status != "not_logged"){
         $('login').style.display = "none";
         $('signup').style.display = "none";
         $('logout').addEventListener('click', function() {
         	xhr.get('./Logout',{},{}).then((data)=> {
         		window.location.href = "./index.html";
         		alert("you have logged out");
         	}) 
          });
      } else {
         $('home').style.display = "none";
         $('upload').style.display = "none";
         $('logout').style.display = "none";         
     }
  });
};
addEventListener('load', function() {
  w3.includeHTML();
});

addEventListener('load', log);

