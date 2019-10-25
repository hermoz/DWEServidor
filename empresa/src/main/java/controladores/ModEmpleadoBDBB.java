package controladores;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import laboral.BBDD;
import laboral.DatosNoCorrectosException;

public class ModEmpleadoBDBB extends HttpServlet {

    public ModEmpleadoBDBB() {

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * Accdemos a este controlador desde modificarEmpleado.jsp.
		 * De ese formulario obtenemos los parámetros mediante request get paramenter como hemos venido haciendo
		 * <input type="text" name="dni" value="${empleado.dni}"">
		 */
		BBDD bd = new BBDD(); 
		
		/*
		 * Creamos un objeto "RequestDispatcher" y su el metodo "forward()" para terminar de hacer el reenvio.
		 */
		RequestDispatcher rd;
		
		//Obtenemos los parámetros
		String dni = request.getParameter("dni");	
		String nombre = request.getParameter("nombre");	
		String sexo = request.getParameter("sexo");
		String categoria = request.getParameter("categoria");	
		String anyos = request.getParameter("anyos");
		//obtenidos los datos del formulario
		try {		
			bd.modificarEmpelado(dni, nombre, sexo, categoria, anyos);		
			rd = request.getRequestDispatcher("empleadoModificado.jsp");
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
