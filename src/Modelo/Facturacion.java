package Modelo;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;


import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**  
 * @author Neii
 * Clase en la que se crea la factura de la entrada
 */
public class Facturacion {
	private String tipoDeEntrada;
	private int cantidad;
	private double precio;
	private double precioTotal;
	private LocalDateTime fechaDate;
	private Persona persona;
	private URL rutaImagen;

	/** 
	 * Crea una instancia con el tipo de entrada, cantidad, precio y persona
	 * @param tipoDeEntrada el nombre de la entrada
	 * @param cantidad la cantidad que se compra
	 * @param precio el precio de la entrada
	 * @param persona quien ha comprado la entrada
	 */
	public Facturacion(String tipoDeEntrada, int cantidad, double precio, Persona persona) {
		super();
		this.tipoDeEntrada = tipoDeEntrada;
		this.cantidad = cantidad;
		this.precio = precio;
		this.precioTotal = cantidad * precio;
		this.fechaDate = LocalDateTime.now();
		this.persona = persona;
		this.rutaImagen = Facturacion.class.getResource("/img/s.jpg");
	}

	/** 
	 * Metodo en el que crea el reporte 
	 * con los atributos del la clase
	 * 
	 * @param ruta recibe la ruta del .jrxml
	 */
	public void crearPDF(String ruta) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("tipoDeEntrada", tipoDeEntrada);
		paraMap.put("cantidad", cantidad);
		paraMap.put("precio", precio);
		paraMap.put("precioTotal", precioTotal);
		paraMap.put("fechaString", fechaString());
		paraMap.put("horaString", horaString());
		paraMap.put("nombre", persona.getNombre());
		paraMap.put("email", persona.getEmail());
		paraMap.put("rutaImagen", rutaImagen);

		try {
			JasperReport jasperReport = JasperCompileManager.compileReport(ruta);
			JasperPrint informePrint = JasperFillManager.fillReport(jasperReport, paraMap, new JREmptyDataSource());
			JasperViewer.viewReport(informePrint);

		} catch (JRException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/** 
	 * 
	 * @return devuelve una cadena en la que te dice la fecha de la facturacion
	 */
	public String fechaString() {
		DayOfWeek diaDeLaSemana = fechaDate.getDayOfWeek();
		int dia = fechaDate.getDayOfMonth();
		Month mes = fechaDate.getMonth();
		int anyo = fechaDate.getYear();
		
		
		return diaDeLaSemana.toString() + " " +dia + " "  + mes.toString() + " " + anyo; 
	}
	
	/** 
	 * 
	 * @return devuelve una cadena en la que te dice la hora de la facturacion
	 */
	public String horaString() {
		int min = fechaDate.getMinute();
		String minString;
		if(min < 10) {
			minString = "0" + min;
		} else {
			minString = String.valueOf(min);
		}
		
		return fechaDate.getHour() + ":" + minString ; 
	}
}
