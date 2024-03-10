package vistas;

import Modelo.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import Modelo.Entrada;

import javax.swing.JButton;
/** 
 * @author Neii
 * / Interfaz donde se muestra la cantidad de cada entrada
 */
public class Info extends Entrada{

	private JFrame frame;
	
	/** 
	 * Constructor de la interfaz Info
	 * @param numEntradasNormales recibe el numero de entradas normales que quedan
	 * @param numVip recibe el numero de entradas VIP que quedan
	 * @param numAbono recibe el número de abonos VIP que quedan
	 * 
	 */
	public Info(int numEntradasNormales, int numVip, int numAbono) {
		this.numEntradasNormales = numEntradasNormales;
		this.numVip = numVip;
		this.numAbono = numAbono;
		initialize();
	}

	/** 
	 * Ejecuta la interfaz Info
	 * @param args argumentos del metodo main
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Info window = new Info(numEntradasNormales, numVip, numAbono);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	

	/**
	 * Inicializa los componenentes de la interfaz Info
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Entradas Normales");
		lblNewLabel.setBounds(72, 59, 86, 13);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Entradas VIP");
		lblNewLabel_1.setBounds(72, 94, 86, 13);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Abono VIP");
		lblNewLabel_2.setBounds(72, 129, 86, 13);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNumNormales = new JLabel(String.valueOf(numEntradasNormales));
		lblNumNormales.setBounds(227, 59, 76, 13);
		frame.getContentPane().add(lblNumNormales);
		
		JLabel lblEntradaVIP = new JLabel(String.valueOf(numVip));
		lblEntradaVIP.setBounds(227, 94, 45, 13);
		frame.getContentPane().add(lblEntradaVIP);
		
		JLabel lblAbonoVIP = new JLabel(String.valueOf(numAbono));
		lblAbonoVIP.setBounds(227, 129, 45, 13);
		frame.getContentPane().add(lblAbonoVIP);
		
		JLabel lblNewLabel3 = new JLabel("Total:");
		lblNewLabel3.setBounds(72, 181, 45, 13);
		frame.getContentPane().add(lblNewLabel3);
		
		JLabel lblTotal = new JLabel(String.valueOf(numEntradasNormales + numVip + numAbono));
		lblTotal.setBounds(227, 181, 45, 13);
		frame.getContentPane().add(lblTotal);
	}
}
