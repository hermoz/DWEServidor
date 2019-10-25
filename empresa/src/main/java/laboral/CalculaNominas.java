
package laboral;

import java.sql.SQLException;
import java.util.Scanner;

public class CalculaNominas {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Scanner sc = new Scanner(System.in);
		Scanner scc = new Scanner(System.in);
		BBDD bd = new BBDD();
		
		//Incluimos todo en un try/catch
		try {
			//creamos nuevos empleados
			Empleado emp = new Empleado("James Cosling","32000032G",'M',4,7);
			Empleado emp2 = new Empleado("Ada Lovelace","32000031R",'F');
				
			bd.altaEmpleado(emp); 
			bd.altaEmpleado(emp2);
			
			try {
				bd.leerTablaEmpleados();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println(" ");
			
			bd.tablaNominas();
			
			System.out.println();
			System.out.println(" ");
			System.out.println();
			
			
			bd.modificarCategoriaEmpleado(emp.dni, 9);
			bd.modificarAnyosEmpleadoDNI(emp2.incrAnyo(), emp2.dni);
			
			bd.leerTablaEmpleados();
			System.out.println(" ");
			bd.tablaNominas();
			
			/**
			 * Dar de alta empleados en el sistema y que, de forma automatica calcule y almacene el sueldo
			 * de los empleados en la base de datos.
			 */
					
			Empleado emp3 = new Empleado("Luis","12478935Z",'H',5,5);
			Empleado emp4 = new Empleado("Ana","12478935Y",'F',4,3);
			
			bd.altaEmpleado(emp3);
			bd.altaEmpleado(emp4);
			
			bd.leerTablaEmpleados();
			System.out.println(" ");
			bd.tablaNominas();

			/* MENU */
				
			System.out.println("Menu");
			
			int opcion;
			int opcion2;
			String nombre;
			String dni;
			String sexo;
			int categoria;
			int anyos;
			
			do {
				System.out.println();
				System.out.println(	"1. Mostrar informacion de los empleados\n" +
									"2. Mostrar el salario de empleado indicando DNI\n" +
									"3. Modificar datos de empleado\n" +
									"4. Recalcular y actualizar sueldo de empleado\n" +
									"5. Recalcular y actualizar sueldos de los empleados.\n" +
						 			"0. Salir del programa.");
				
				opcion = sc.nextInt();
				
				if(opcion == 1) {
					
					System.out.println("Empleados");
					bd.leerTablaEmpleados();;
			
				}else if(opcion == 2) {
					
					System.out.println("Introduzca el dni del empleado que desea ver el salario.");
					dni = scc.nextLine();
					bd.mostrarSalarioDni(dni);
					
				}else if(opcion == 3) {
					
					System.out.println("�Que desea modificar?");
					
					do {
						
						System.out.println("1.- Modificar nombre.\n"
								 + "2.- Modificar sexo.\n"
								 + "3.- Modificar categoria.\n"
								 + "4.- Modificar anyos.\n"
								 + "0.- Salir.");
						
						opcion2 = sc.nextInt();
						
						if(opcion2 == 1) {
							
							System.out.println("DNI del empleado a modificar: ");
							dni = scc.nextLine();
							System.out.println("Nuevo nombre del empleado");
							nombre = scc.nextLine();
							bd.modificarNombreEmpleado(nombre, dni);
							
						}else if(opcion2 == 2) {
							
							System.out.println("DNI del empleado a modificar: ");
							dni = scc.nextLine();
							System.out.println("Nuevo sexo del empleado");
							sexo = scc.nextLine();
							bd.modificarNombreEmpleado(sexo, dni);
							
						}else if(opcion2 == 3) {
							
							System.out.println("DNI del empleado a modificar: ");
							dni = scc.nextLine();
							System.out.println("Nueva categoria del empleado:");
							categoria = sc.nextInt();
							bd.modificarCategoriaEmpleado(dni, categoria);
							
						}else if(opcion2 == 4) {
							
							System.out.println("DNI del empleado a modificar: ");
							dni = scc.nextLine();
							System.out.println("Nuevo anyos trabajados:");
							anyos = sc.nextInt();
							bd.modificarAnyosEmpleadoDNI(anyos, dni);
							
						}
						
						if(opcion2 == 0) {
							
							System.out.println("Saliendo del submenu...");
							
						}
						
					}while(opcion2 != 0);
						
				}else if(opcion == 4) {
					
					System.out.println("DNI del empleado para recalcular su sueldo.");
					dni = scc.nextLine();
					bd.recalcularSueldoDni(dni);
					
				}else if(opcion == 5) {
					
					System.out.println("Recalculando sueldo de todos los empleados...");
					bd.recalcularSueldoEmpleados();
					
				}
				
			}while(opcion != 0);
				System.out.println("Saliendo del menu...");
			
			sc.close();
			scc.close();
				
		} catch (DatosNoCorrectosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

		
}
