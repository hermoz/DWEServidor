<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="laboral.Empleado" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Empleados existentes en la base de datos</title>
<link rel="stylesheet" type="text/css" href="style.css"/>
</head>
<body>
<h5>Listado de empleados</h5>	
	<table id="table">	
		<tr>	
			<td>Nombre</td>
			<td>DNI</td>
			<td>Sexo</td>
			<td>Categoria</td>
			<td>Anyos</td>	
		</tr>
				
			<c:forEach var="dato" items="${listaEmp}" >
				
				<tr>		
					<td>${dato.nombre }</td>
					<td>${dato.dni }</td>
					<td>${dato.sexo }</td>
					<td>${dato.categoria }</td>
					<td>${dato.anyos }</td>				
				</tr>		
			</c:forEach>
	</table>			
			
		<%-- 	
			//guardamos los datos en una lista, recorremos y vamos pintando los datos en la tabla
			<%List<String> emp = (ArrayList<String>) request.getAttribute("listaEmp"); %>		
			<%for(String empleados : emp){ %>
				<%String info[] = empleados.split("-"); %>
					<tr>
						<td><%=info[0] %></td>
						<td><%=info[1] %></td>
						<td><%=info[2] %></td>
						<td><%=info[3] %></td>
						<td><%=info[4] %></td>
					</tr>		
			<%} %> --%>
						

	
	<a href="index.html">
		<button>Volver</button>
	</a>
	
	
</body>
</html>