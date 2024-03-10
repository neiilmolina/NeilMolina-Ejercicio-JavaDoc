package vistas;
import java.awt.Color;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import Modelo.Entrada;
import Modelo.Facturacion;
import Modelo.Persona;

import javax.swing.JLabel;

/** 
 * @author Neii
 * Interfaz donde se controla el servidor
 */
public class Servidor extends Entrada {

	private JFrame frame;

	private JTextArea txaNormal;
	private JTextArea txaVip;
	private JTextArea txaAbono;

	private JLabel lblFacturacionNormal;
	private JLabel lblFacturacionVip;
	private JLabel lblFacturacionAbono;

	private JLabel lblTotalNormal;
	private JLabel lblTotalVIp;
	private JLabel lblTotalAbono;

	private JLabel lblTotalEntradas;
	
	private String ruta = "PlantillaFactura.jrxml";
	
	private int compraNormal;
	private int compraVIP;
	private int compraAbono;

	private static Semaphore semaphoreNormales = new Semaphore(1);
	private static Semaphore semaphoreVIP = new Semaphore(1);
	private static Semaphore semaphoreAbono = new Semaphore(1);


	/** 
	 * Ejecuta la interfaz Servidor
	 * @param args argumentos del metodo main
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Servidor window = new Servidor();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/** 
	 * Constructor de la interfaz Servidor
	 */
	public Servidor() {
		initialize();

		Thread thread = new Thread(() -> {
			try {
				String nombre = Thread.currentThread().getName();
				startServer(nombre);
			} catch (Exception e) {
				// TODO: handle exception
			}
		});

		thread.start();
	}

	/**
	 * Inicializa los componenentes de la interfaz Servidor
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		txaNormal = new JTextArea();
		txaNormal.setBackground(Color.CYAN);
		txaNormal.setEnabled(false);
		txaNormal.setBounds(44, 50, 102, 63);
		frame.getContentPane().add(txaNormal);

		txaVip = new JTextArea();
		txaVip.setBackground(Color.YELLOW);
		txaVip.setEnabled(false);
		txaVip.setBounds(174, 50, 102, 63);
		frame.getContentPane().add(txaVip);

		txaAbono = new JTextArea();
		txaAbono.setBackground(Color.GREEN);
		txaAbono.setEnabled(false);
		txaAbono.setBounds(300, 50, 102, 63);
		frame.getContentPane().add(txaAbono);

		lblFacturacionNormal = new JLabel("Entradas Normales");
		lblFacturacionNormal.setBounds(44, 123, 120, 13);
		frame.getContentPane().add(lblFacturacionNormal);

		lblFacturacionVip = new JLabel("Entradas VIP");
		lblFacturacionVip.setBounds(174, 123, 116, 13);
		frame.getContentPane().add(lblFacturacionVip);

		lblFacturacionAbono = new JLabel("Abono VIP");
		lblFacturacionAbono.setBounds(300, 123, 120, 13);
		frame.getContentPane().add(lblFacturacionAbono);

		lblTotalNormal = new JLabel(String.valueOf(numEntradasNormales));
		lblTotalNormal.setBounds(77, 146, 45, 13);
		frame.getContentPane().add(lblTotalNormal);

		lblTotalVIp = new JLabel(String.valueOf(numVip));
		lblTotalVIp.setBounds(199, 146, 45, 13);
		frame.getContentPane().add(lblTotalVIp);

		lblTotalAbono = new JLabel(String.valueOf(numAbono));
		lblTotalAbono.setBounds(321, 146, 45, 13);
		frame.getContentPane().add(lblTotalAbono);

		lblTotalEntradas = new JLabel("Total: " + String.valueOf(total));
		lblTotalEntradas.setBounds(199, 223, 77, 13);
		frame.getContentPane().add(lblTotalEntradas);
	}

	/** 
	 * Recibe unos datos que le pasan los clientes y se los
	 * envía a los clientes con la facturación
	 * @param nombre el nombre del cliente
	 * @throws IOException
	 */
	private void startServer(String nombre) throws IOException {
		ServerSocket serverSocket = new ServerSocket(123);

		while (true) {

			Socket socket = serverSocket.accept();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String infoString = bufferedReader.readLine();

			String array[] = infoString.split(" ");
			
			Persona persona = new Persona(nombre);

			compraNormal = Integer.parseInt(array[0]);
			if (compraNormal > 0) {
				Thread normalesThread = new Thread(() -> {
					Color color = txaNormal.getBackground();
					try {
						txaNormal.setBackground(color.white);
						Thread.sleep(1000);
						numEntradasNormales = cambiarNumEntradas(array[0], numEntradasNormales, lblTotalNormal,
								semaphoreNormales);
						lblTotalEntradas.setText("Total: " + total);
						enviarDatos(socket);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						txaNormal.setBackground(color);
					}

				});
				normalesThread.start();
				hacerFacturacion("Entrada normal", compraNormal, 49.99, ruta, persona);
			}

			compraVIP = Integer.parseInt(array[1]);
			if (compraVIP > 0) {
				Thread vipThread = new Thread(() -> {
					Color color = txaVip.getBackground();
					try {
						txaVip.setBackground(color.white);
						Thread.sleep(1000);
						numVip = cambiarNumEntradas(array[1], numVip, lblTotalVIp, semaphoreVIP);
						lblTotalEntradas.setText("Total: " + total);
						enviarDatos(socket);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						txaVip.setBackground(color);
					}
				});
				vipThread.start();
				hacerFacturacion("Entrada VIP", compraVIP, 74.99, ruta, persona);
			}

			compraAbono = Integer.parseInt(array[2]);
			if (compraAbono > 0) {
				Thread abonoThread = new Thread(() -> {
					Color color = txaAbono.getBackground();
					try {
						txaAbono.setBackground(color.WHITE);
						Thread.sleep(1000);
						numAbono = cambiarNumEntradas(array[2], numAbono, lblTotalAbono, semaphoreAbono);
						lblTotalEntradas.setText("Total: " + total);
						enviarDatos(socket);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						txaAbono.setBackground(color);
					}
				});
				abonoThread.start();
				hacerFacturacion("Abono VIP", compraAbono, 99.99, ruta, persona);
			}

		}
	}
	
	/** 
	 * Cambia la cantidad del tipo de entrada
	 * @param elemento: la cantidad de entradas que pide el cliente en una cadena
	 * @param numEntradas: la cantidad del tipo de entrada que hay
	 * @param lbl: el JLabel para cambiar en la interfaz
	 * @param semaphore: semaforo para controlar el número de clientes
	 * @return devuelve la nueva cantidad del tipo de entradas
	 */
	private int cambiarNumEntradas(String elemento, int numEntradas, JLabel lbl, Semaphore semaphore) {
		try {
			semaphore.acquire();
			numEntradas = numEntradas - Integer.parseInt(elemento);
			lbl.setText(String.valueOf(numEntradas));
			total = total - Integer.parseInt(elemento);
			semaphore.release();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return numEntradas;

	}
		
	
	/** 
	 * Envia la información de las entradas que ha solicitado el cliente
	 * @param socket el socket del cliente donde se va a enviar
	 * @throws IOException
	 */
	private void enviarDatos(Socket socket) throws IOException {
		String numEntradaString = numEntradasNormales + " " + numVip + " " + numAbono;
		OutputStream outputStream = socket.getOutputStream();
		outputStream.write((numEntradaString + "\n").getBytes());
		outputStream.flush();
	}
	
	/** 
	 * / Crea la facturación
	 * @param tipoDeEntrada el tipo de entrada en la que se va a hacer la factura
	 * @param cantidad la cantidad de entradas que va a comprar el cliente
	 * @param precio el precio de cada entrada
	 * @param ruta la ruta donde se ubica el .jrxml
	 * @param persona los atributos de los clientes que va a comprar la entrada
	 */
	private void hacerFacturacion(String tipoDeEntrada, int cantidad, double precio, String ruta, Persona persona) {
		Facturacion facturacion = new Facturacion(tipoDeEntrada, cantidad, precio, persona);
		facturacion.crearPDF(ruta);
		
	}

	

}
