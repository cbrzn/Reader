<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">

<title>Movies</title>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <a class="navbar-brand" href="#">Project #1</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active">
        <a class="nav-link" href="./login.html">Login <span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="./signup.html">Sign up</a>
      </li>
        <li class="nav-item active">
        <a class="nav-link" href="./load.html">Upload serie
         <span class="sr-only">(current)</span></a>
      </li>
    </ul>
  </div>
</nav>
	<h1> <center> ALL MOVIES </center> </h1>

<div class="card-columns">
	<center>
  	<c:forEach items="${images}" var="img" >
  		<div class="card" style="width: 20rem;">
      		<img src="${pageContext.request.contextPath}/Img/${img}" width=150 height=150 class="img-thumbnail">	
    		<div class="card-body">
    		  <h4 class="card-title">Card title that wraps to a new line</h4>
    		  <c:out value="${img}"></c:out>
    		  <p class="card-text"></p>
   			 </div>
   		</div>
   	</c:forEach>	
   	</center>
  </div>
  
  </body>
</html>