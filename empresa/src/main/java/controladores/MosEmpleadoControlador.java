package controladores;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import laboral.BBDD;
import laboral.DatosNoCorrectosException;
import laboral.Empleado;

public class MosEmpleadoControlador extends HttpServlet {
       
    public MosEmpleadoControlador() {
    }

    //MÉTODO DO GET
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request,response);
		
	}

	//MÉTODO DO POST
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		BBDD bd;
		try {
			bd = new BBDD();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/*
		 * Creamos un objeto "RequestDispatcher" y su el metodo "forward()" para terminar de hacer el reenvio.
		 */
		RequestDispatcher rd;
		
		try {
			//LLamamos al método de mostrar empleados para proporcionar los datos de la lista de empleados solicitada
			List<Empleado> emp = bd.mostrarTablaEmpleados();
			
			// RequestDispatcher es una referencia que encapsula el recurso
			//En mostrarEmpleados.jsp es donde cogemos los datos y mostramos la tabla
			rd = request.getRequestDispatcher("mostrarEmpleados.jsp");
			
			/* listaEmp lo he definido como mi item al declarar mi forEach
			 * <c:forEach var="dato" items="${listaEmp}" >
			 */
			request.setAttribute("listaEmp", emp);

			//Este método forward es para terminar de hacer el reenvío. Redirigimos la petición al recurso.
			//Lo redirigimos a otro componente
			rd.forward(request, response);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatosNoCorrectosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
