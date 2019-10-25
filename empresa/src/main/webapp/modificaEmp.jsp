<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<form action="modificarEmpleadoBDController" method="POST">
		
		<input type="text" name="dni" value="${empleado.dni}"">
		<br>
		<input type="text" name="nombre" value="${empleado.nombre}">
		<br>
		<input type="text" name="sexo" value="${empleado.sexo}">
		<br>
		<input type="text" name="categoria" value="${empleado.categoria}">
		<br>
		<input type="text" name="anyos" value="${empleado.anyos}">
		<br>
		<input type="submit" name="enviar" value="Modificar">
		
	</form>
	
	
	
	
	
</body>
</html>