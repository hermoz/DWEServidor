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
import laboral.Empleado;

public class ModEmpleadoControlador extends HttpServlet {

    public ModEmpleadoControlador() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter() nos devuelve un objeto con el que podamos enviar datos al cliente
		response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BBDD bd = new BBDD();
		/*Accedemos a este controlador mediante index por lo que recibimos datos del formulario 
		 * 	<form id="form" action="modificarEmpleadoController" method="POST">	
				<input type="text" name="dni">
				<input type="submit" id="botonMod" value="ModificarEmpleado" >
			</form>
		 */
		Empleado emp;
		/*
		 * Creamos un objeto "RequestDispatcher" y su el metodo "forward()" para terminar de hacer el reenvio.
		 */
		RequestDispatcher rd;
		String dni = request.getParameter("dni");
		//establecemos el atributo para la búsqueda del empleado con ese DNI
		request.setAttribute("dni", dni);
		try {
			//recordamos que a este método le pasamos una cadena DNI y nos devuelve el empleado
			emp = bd.mostrarEmpleadoDni(dni);
			request.setAttribute("empleado", emp);	
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
		
		/*
		 * una vez obtenido el empleado hacemos una request a nuestro archivo modificarEmpleado.jsp
		 * Este archivo contiene mi action para acceder a mi controller que me modifica los datos de la base de datos
		 */
		rd = request.getRequestDispatcher("modificarEmpleado.jsp");
		//Este método forward es para terminar de hacer el reenvío. Redirigimos la petición al recurso.
		//Lo redirigimos a otro componente
		
		rd.forward(request, response);
		
	}

}
