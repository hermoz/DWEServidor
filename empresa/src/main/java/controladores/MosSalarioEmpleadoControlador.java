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


public class MosSalarioEmpleadoControlador extends HttpServlet {
       
    public MosSalarioEmpleadoControlador() {

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request,response);
	}

	//accedemos mediante método POST
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BBDD bd=new BBDD();
		/*
		 * Creamos un objeto "RequestDispatcher" y su el metodo "forward()" para terminar de hacer el reenvio.
		 */
		RequestDispatcher rd;
		try {		
			/*
			 * Para mostrar el empleado al hacer el request le pasamos el parámetro DNI que hemos obtenido del formulario
			 * <input type="text" name="dni">
			 */
			Empleado emp = bd.mostrarEmpleadoDni(request.getParameter("dni"));
			//Estblecemos el atributo al hacer la request para obtener los datos
			request.setAttribute("dniEmp", emp.getDni());
			//mostramos el salario del la persona con dni requerido
			String salario = bd.mostrarSalarioPorEmpleado(request.getParameter("dni"));	
			
			// RequestDispatcher es una referencia que encapsula el recurso
			//En mostrarSalarioEmpleado.jsp mostramos los datos obtenidos de la consulta a la bbdd
			rd = request.getRequestDispatcher("mostrarSalarioEmpleado.jsp");
			/*Al establecer el request debemos pasar los parámetros necesarios establecidos
			 * ${dniEmp}
			 * ${salarioEmp}
			 */
			request.setAttribute("salarioEmp", salario);
			
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
