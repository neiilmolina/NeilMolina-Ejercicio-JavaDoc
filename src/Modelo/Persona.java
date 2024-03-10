package Modelo;

/** 
 * @author Neii
 * Clase que simula una persona
 */
public class Persona {
	private String nombre;
	private String email;
	
	/** 
	 * Crea una instancia con el nombre
	 * @param nombre de la persona
	 */
	
	public Persona(String nombre) {
		super();
		this.nombre = nombre;
		this.email = this.nombre + "@gmail.com";
	}
	
	/** 
	 * Recupera el atributo nombre
	 * @return una cadena con el nombre
	 */
	
	public String getNombre() {
		return nombre;
	}
	
	/** 
	 * Cambia el atributo nombre de la clase
	 * @param nombre recibe una cadena 
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/** 
	 * Recupera el atributo email de la clase
	 * @return devuelve el email
	 */
	public String getEmail() {
		return email;
	}
	
	/** 
	 * Metodo que de cambiar el atributo email de la clase
	 * el cual recibe una cadena
	 * @param email recibe una cadena
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
