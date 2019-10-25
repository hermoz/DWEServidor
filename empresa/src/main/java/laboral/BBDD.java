
package laboral;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BBDD {
	/*
	 * Realizamos la conexión con MySql
	 * Declaramos las variables de la conexión que serán usadas por la mayoría de los métodos
	 */
	Connection con;
	Statement stmt;
	ResultSet rs;
	int result;
	Nomina nomina=null;
	
	public BBDD() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		
		//establecemos la conexion (direccion,usuario,contraseña)
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/nomina", "root", "");
	}
	
	public List<Empleado> mostrarTablaEmpleados() throws ClassNotFoundException, SQLException, DatosNoCorrectosException {	
		ArrayList<Empleado> lista = new ArrayList<Empleado>();		
			stmt = con.createStatement();			
			rs = stmt.executeQuery("select * from empleados");		
			while(rs.next()) {	
				lista.add(new Empleado(rs.getString("nombre"),rs.getString("dni"),rs.getString("sexo").charAt(0),rs.getInt("categoria"),rs.getInt("anyos")));
			}			
			return lista;
		
	}
	
	public void leerTablaEmpleados() throws ClassNotFoundException {		
		String linea;	
		try {	
			stmt = con.createStatement();			
			rs = stmt.executeQuery("select * from empleados");		
			while(rs.next()) {			
				linea = rs.getString(1) + "-" + rs.getString(2) + "-" + rs.getString(3) + "-" + rs.getInt(4) + "-" + rs.getInt(5);				
				System.out.println(linea);
			}			
		} catch (SQLException sqle) {
			
			sqle.printStackTrace();
			System.out.println();
			System.out.println(sqle.getMessage());
			System.out.println();
			System.out.println(sqle.getSQLState());
			System.out.println();
			System.out.println(sqle.getErrorCode());			
		}		
	}

	
	public void modificarCategoriaEmpleado(String dni, int categoria) {
		
		String linea;
		String arrayEmpleados[];
		String nombre;
		String sexo;
		int anyos;
		
		try {
			
			PreparedStatement pst = con.prepareStatement("SELECT * FROM EMPLEADOS WHERE DNI = ?");		
			pst.setString(1, dni);
			
			rs = pst.executeQuery();
			
			while(rs.next()) {			
				linea = rs.getString(1) + "-" + rs.getString(2) + "-" + rs.getString(3) + "-" + rs.getInt(4) + "-" + rs.getInt(5);
				arrayEmpleados = linea.split("-");		
				nombre = arrayEmpleados[0];
				dni = arrayEmpleados[1];
				sexo = arrayEmpleados[2];
				anyos = Integer.parseInt(arrayEmpleados[4]);
				
				Empleado emp = new Empleado(nombre,dni,sexo.charAt(0),categoria,anyos);
				//Actualizamos categoría
				pst = con.prepareStatement("UPDATE EMPLEADOS SET CATEGORIA = ? WHERE DNI = ?");
				
				pst.setInt(1, categoria);
				pst.setString(2,dni);
				
				pst.executeUpdate();
				
				pst.clearBatch();
				pst = con.prepareStatement("UPDATE NOMINAS SET SUELDO = ? WHERE DNI = ?");
				
				Nomina n = null;
				pst.setInt(1, nomina.sueldo(emp));
				pst.setString(2, emp.dni);
				
				int result = pst.executeUpdate();
				
				if(result>0) {
					System.out.println("Categoria del empleado actualizada.");
				}else {
					System.out.println("No se ha podido actualizar la categoria del empleado.");
				}
						
			}			
			
		} catch (SQLException sqle) {
			
			sqle.printStackTrace();
			System.out.println();
			System.out.println(sqle.getMessage());
			System.out.println();
			System.out.println(sqle.getSQLState());
			System.out.println();
			System.out.println(sqle.getErrorCode());
			
		} catch (DatosNoCorrectosException dao) {
			
			dao.printStackTrace();			
		}
	
	}
	
	
	public void modificarAnyosEmpleadoDNI(int anyos, String dni) {		
		String linea;
		String arrayEmpleados[];
		String nombre;
		String sexo;
		int categoria;		
		try {
			
			stmt = con.createStatement();	
			PreparedStatement pst = con.prepareStatement("SELECT * FROM EMPLEADOS WHERE DNI = ?");
			
			pst.setString(1, dni);		
			rs = pst.executeQuery();
			
			while(rs.next()) {
				
				linea = rs.getString(1) + "-" + rs.getString(2) + "-" + rs.getString(3) + "-" + rs.getInt(4);
				arrayEmpleados = linea.split("-");
				
				nombre = arrayEmpleados[0];
				dni = arrayEmpleados[1];
				sexo = arrayEmpleados[2];
				categoria = Integer.parseInt(arrayEmpleados[3]);
				
				Empleado emp = new Empleado(nombre,dni,sexo.charAt(0),categoria,anyos);
				
				pst = con.prepareStatement("UPDATE EMPLEADOS SET ANYOS = ? WHERE DNI = ?");
				
				pst.setInt(1, anyos);
				pst.setString(2,dni);
				
				pst.executeUpdate();
				
				pst.clearBatch();
				pst = con.prepareStatement("UPDATE NOMINAS SET SUELDO = ? WHERE DNI = ?");
				
				Nomina n = null;
				pst.setInt(1, nomina.sueldo(emp));
				pst.setString(2, emp.dni);
				
				int result = pst.executeUpdate();
				
				if(result>0) {
					System.out.println("Anyosactualizado");
				}else {
					System.out.println("Error al actualizar");
				}
						
			}
			
			
		} catch (SQLException sqle) {		
			sqle.printStackTrace();
			System.out.println();
			System.out.println(sqle.getMessage());
			System.out.println();
			System.out.println(sqle.getSQLState());
			System.out.println();
			System.out.println(sqle.getErrorCode());
			
		} catch (DatosNoCorrectosException dao) {
			dao.printStackTrace();
		}
		
	}

	
	public void tablaNominas() {
		
		String linea;
		
		try {		
			stmt = con.createStatement();					
			rs = stmt.executeQuery("select * from nominas");
			
			while(rs.next()) {				
				linea = rs.getString(1) + "-" + rs.getInt(2);				
				System.out.println(linea);				
			}
			
			
		} catch (SQLException sqle) {			
			sqle.printStackTrace();
			System.out.println();
			System.out.println(sqle.getMessage());
			System.out.println();
			System.out.println(sqle.getSQLState());
			System.out.println();
			System.out.println(sqle.getErrorCode());			
		}
		
	}
	
	public void altaEmpleado(Empleado emp) {
		
		try {
						
			String sexo = Character.toString(emp.sexo);		
			PreparedStatement pst = con.prepareStatement("insert into empleados values(?,?,?,?,?)");
			
			pst.setString(1, emp.nombre);
			pst.setString(2, emp.dni);
			pst.setString(3, sexo);
			pst.setInt(4, emp.getCategoria());
			pst.setInt(5, emp.anyos);
			
			pst.executeUpdate();
						
			pst = con.prepareStatement("insert into Nominas values(?,?)");
			
			pst.setString(1, emp.dni);
			pst.setInt(2,nomina.sueldo(emp));
			
			result = pst.executeUpdate();
			
			if(result>0) {
				System.out.println("Empleado insertado");
			}else {
				System.out.println("Error al insertar");
			}
	
			
		} catch (SQLException sqle) {			
			sqle.printStackTrace();
			System.out.println();
			System.out.println(sqle.getMessage());
			System.out.println();
			System.out.println(sqle.getSQLState());
			System.out.println();
			System.out.println(sqle.getErrorCode());
			
		}
		
		
	}
	
	
	public void altaEmpleado(String ficheroEmpleadoNuevo) throws DatosNoCorrectosException {
		
		Scanner sc = new Scanner(System.in);
		Scanner scc = new Scanner(System.in);
		BufferedReader br;
		String arrayEmpleado[];
		String linea;
		String nombre;
		String dni;
		String sexo;
		int categoria;
		int anyos;
		String salir;
		int opcion;		
		
		do {
			
			System.out.println();
			System.out.println("1.- Dar alta a empleado\n" + 
								"2.- Dar de alta a empleados desde el fichero\n" + 
								"0.- Para salir.");
			
			opcion = sc.nextInt();
			
			if(opcion == 1) {
				
				System.out.println("Dar de alta a empleado único");

					System.out.println("Introduzca los datos:");
					System.out.println("Nombre:");
					nombre = scc.nextLine();
					System.out.println("DNI:");
					dni = scc.nextLine();
					System.out.println("Sexo:");
					sexo = scc.nextLine();
					System.out.println("Categoria");
					categoria = sc.nextInt();
					System.out.println("Anyos:");
					anyos = sc.nextInt();
					
					altaEmpleado(new Empleado(nombre,dni,sexo.charAt(0),categoria,anyos));
					
					System.out.println();
				
				
			}else if(opcion == 2) {

					
				System.out.println("Dar de alta a empleados desde el fichero");

				try {
					stmt = con.createStatement();			
					
					br = new BufferedReader(new FileReader(ficheroEmpleadoNuevo));
					
					
					while((linea = br.readLine()) != null) {
						
						arrayEmpleado = linea.split(",");
						
						nombre = arrayEmpleado[0];
						dni = arrayEmpleado[1];
						sexo = arrayEmpleado[2];
						categoria = Integer.parseInt(arrayEmpleado[3]);
						anyos = Integer.parseInt(arrayEmpleado[4]);
						
						
						PreparedStatement pst = con.prepareStatement("insert into empleados values(?,?,?,?,?)");
						
						pst.setString(1,nombre);
						pst.setString(2, dni);
						pst.setString(3, sexo);
						pst.setInt(4, categoria);
						pst.setInt(5, anyos);
						
						pst.executeUpdate();
						
						pst = con.prepareStatement("insert into nominas values(?,?)");
						
						Empleado emp = new Empleado(nombre,dni,sexo.charAt(0),categoria,anyos);
						
						pst.setString(1, emp.dni);
						pst.setInt(2,nomina.sueldo(emp));
						
						result = pst.executeUpdate();
						
						if(result>0) {
							System.out.println("Empleados anadidos por lote desde el fichero correctamente.");
						}else {
							System.out.println("No se ha podido completar la transferencia de datos.");
						}
					
					}
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException io) {
					io.printStackTrace();
				} catch (SQLException sqle) {
					
					sqle.printStackTrace();
					System.out.println();
					System.out.println(sqle.getMessage());
					System.out.println();
					System.out.println(sqle.getSQLState());
					System.out.println();
					System.out.println(sqle.getErrorCode());
					
				}catch (DatosNoCorrectosException dan) {
					dan.getMessage();
					
				}
				
				
			}else if(opcion == 0) {
				System.out.println("Ha salido");
			}
			
		}while(opcion != 0);
			
		sc.close();
		scc.close();
		
	}

	
	public void mostrarSalarioDni(String dni) throws ClassNotFoundException {
		
		try {
			
			PreparedStatement pst = con.prepareStatement("SELECT SUELDO FROM NOMINAS WHERE DNI = ?");
			
			pst.setString(1, dni);
			
			rs = pst.executeQuery();
			
			while(rs.next()) {
				
				String linea = rs.getString(1);
				
				System.out.println("El salario es: " + linea);
				
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			System.out.println();
			System.out.println(sqle.getMessage());
			System.out.println();
			System.out.println(sqle.getSQLState());
			System.out.println();
			System.out.println(sqle.getErrorCode());
		}
		
		
	}
	
	public String mostrarSalarioPorEmpleado(String dni) throws ClassNotFoundException, SQLException {
			
			String salario = null;
			
			PreparedStatement pst = con.prepareStatement("SELECT SUELDO FROM NOMINAS WHERE DNI = ?");		
			pst.setString(1, dni);
			
			rs = pst.executeQuery();
			
			while(rs.next()) {
				
				salario = rs.getString(1);
				
			}
				
			return salario;
		
	}
	
	public Empleado mostrarEmpleadoDni(String dni) throws ClassNotFoundException, SQLException, DatosNoCorrectosException {
		
		Empleado emp = null;
		
		PreparedStatement pst = con.prepareStatement("SELECT * FROM EMPLEADOS WHERE DNI = ?");		
		pst.setString(1, dni);
		
		rs = pst.executeQuery();
		
		while(rs.next()) {	
			emp = new Empleado(rs.getString("nombre"),rs.getString("dni"),rs.getString("sexo").charAt(0),rs.getInt("categoria"),rs.getInt("anyos"));
			
		}
		
			
		return emp;
	
	}
	
	public void modificarEmpelado(String dni, String nombre, String sexo, String categoria, String anyos) throws ClassNotFoundException, SQLException, DatosNoCorrectosException {	
		stmt = con.createStatement();			
		
		stmt.executeUpdate("UPDATE EMPLEADOS SET NOMBRE = '" + nombre + "' WHERE DNI = '" + dni + "'");
		stmt.executeUpdate("UPDATE EMPLEADOS SET SEXO = '" + sexo + "' WHERE DNI = '" + dni + "'");
		stmt.executeUpdate("UPDATE EMPLEADOS SET CATEGORIA = '" + categoria + "' WHERE DNI = '" + dni + "'");
		stmt.executeUpdate("UPDATE EMPLEADOS SET ANYOS = '" + anyos + "' WHERE DNI = '" + dni + "'");
		stmt.executeUpdate("UPDATE NOMINAS SET SUELDO = " + Nomina.sueldoController(Integer.parseInt(categoria), Integer.parseInt(anyos)) + " WHERE DNI = '" + dni + "'");	
		stmt.close();
		con.close();
	}
	
	public void modificarNombreEmpleado(String nombre, String dni) throws ClassNotFoundException {
		
		try {		
			PreparedStatement pst = con.prepareStatement("UPDATE EMPLEADOS SET NOMBRE = ? WHERE DNI = ?");
			
			pst.setString(1, nombre);
			pst.setString(2, dni);
			
			result = pst.executeUpdate();
			
			if(result>0) {
				System.out.println("Nombre actualizado.");
			}else {
				System.out.println("No se ha podido modificar el nombre.");
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			System.out.println();
			System.out.println(sqle.getMessage());
			System.out.println();
			System.out.println(sqle.getSQLState());
			System.out.println();
			System.out.println(sqle.getErrorCode());
		}
		
	}
	
	
	public void modificarSexoEmpleado(String sexo, String dni) throws ClassNotFoundException {		
		try {		
			PreparedStatement pst = con.prepareStatement("UPDATE EMPLEADOS SET SEXO = ? WHERE DNI = ?");
			
			pst.setString(1, sexo);
			pst.setString(2, dni);
			
			result = pst.executeUpdate();
			
			if(result>0) {
				System.out.println("Sexo actualizado.");
			}else {
				System.out.println("No se ha podido modificar el sexo.");
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			System.out.println();
			System.out.println(sqle.getMessage());
			System.out.println();
			System.out.println(sqle.getSQLState());
			System.out.println();
			System.out.println(sqle.getErrorCode());
		}
		
	}
	
	public void recalcularSueldoDni(String dni) throws ClassNotFoundException {
		
		String linea;
		String arrayEmpleados[];
		String nombre;
		String sexo;
		int categoria;
		int anyos;
		
		try {
		
			
			PreparedStatement pst = con.prepareStatement("SELECT * FROM EMPLEADOS WHERE DNI = ?");
			
			pst.setString(1, dni);
			
			rs = pst.executeQuery();
			
			while(rs.next()) {
				
				linea = rs.getString(1) + "-" + rs.getString(2) + "-" + rs.getString(3) + "-" + rs.getInt(4) + "-" +rs.getInt(5);
				arrayEmpleados = linea.split("-");
				
				nombre = arrayEmpleados[0];
				dni = arrayEmpleados[1];
				sexo = arrayEmpleados[2];
				categoria = Integer.parseInt(arrayEmpleados[3]);
				anyos = Integer.parseInt(arrayEmpleados[4]);
				
				Empleado emp = new Empleado(nombre,dni,sexo.charAt(0),categoria,anyos);
				
				pst = con.prepareStatement("UPDATE NOMINAS SET SUELDO = ? WHERE DNI = ?");
				
				pst.setInt(1, nomina.sueldo(emp));
				pst.setString(2, emp.dni);
				
				result = pst.executeUpdate();
				
				if(result>0) {
					System.out.println("Sueldo recalculado");
				}else {
					System.out.println("Se ha producido un error");
				}
						
			}
			
			
		} catch (SQLException sqle) {
			
			sqle.printStackTrace();
			System.out.println();
			System.out.println(sqle.getMessage());
			System.out.println();
			System.out.println(sqle.getSQLState());
			System.out.println();
			System.out.println(sqle.getErrorCode());
			
		} catch (DatosNoCorrectosException dao) {
			dao.printStackTrace();
		}
		
	}
	
	public void recalcularSueldoEmpleados() throws ClassNotFoundException {	
		Empleado emp;		
		try {			
			stmt = con.createStatement();
			
			rs = stmt.executeQuery("SELECT * FROM EMPLEADOS");
			
			while(rs.next()) {
				
				emp = new Empleado(rs.getString(1), rs.getString(2), rs.getString(3).charAt(0), rs.getInt(4), rs.getInt(5));
				
				PreparedStatement pst = con.prepareStatement("UPDATE NOMINAS SET SUELDO = ? WHERE DNI = ?");
				
				pst.setInt(1, nomina.sueldo(emp));
				pst.setString(2, emp.dni);
				
				result = pst.executeUpdate();
						
			}
			
			if(result>0) {
				System.out.println("Sueldo recalculado con exito.");
			}else {
				System.out.println("No se ha podido recalcular el sueldo.");
			}
			
			
		} catch (SQLException sqle) {
			
			sqle.printStackTrace();
			System.out.println();
			System.out.println(sqle.getMessage());
			System.out.println();
			System.out.println(sqle.getSQLState());
			System.out.println();
			System.out.println(sqle.getErrorCode());
			
		} catch (DatosNoCorrectosException dao) {
			dao.printStackTrace();
		}
		
	}
	
}
