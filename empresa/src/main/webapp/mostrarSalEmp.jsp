<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="laboral.Empleado" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Salario del empleado</title>
</head>
<body>		
	<h5>Salario del empleado ${dniEmp} es:</h3>
	<h5>${salarioEmp}</h5>
    <br>   
     <a href="index.html"><button>Volver</button></a>
     
</body>
</html> 