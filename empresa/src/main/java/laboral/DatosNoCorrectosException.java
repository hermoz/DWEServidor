
package laboral;

public class DatosNoCorrectosException extends Exception {
	
	public DatosNoCorrectosException(String string) {	
	}

	public DatosNoCorrectosException() {
		super("Error en la introduccion de datos"); 
		
	}
	
}
