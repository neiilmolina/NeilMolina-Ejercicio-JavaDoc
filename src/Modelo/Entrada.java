package Modelo;

/** 
 * @author Neii
 * Establecer el numero de entradas
 */
public class Entrada {
	/** 
	 * numero de entradas de Entradas Normales
	 */
	public static int numEntradasNormales = 7;
	
	/** 
	 * numero de entradas de Entradas VIP
	 */
	public static int numVip = 7;
	
	/** 
	 * numero de entradas de Abono VIP
	 */
	public static int numAbono = 7;
	
	/** 
	 * Suma total de las 3 entradas
	 */
	protected static int total = numEntradasNormales + numVip + numAbono;
}
