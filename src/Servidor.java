import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Label;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JLabel;

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

	private static Semaphore semaphoreNormales;
	private static Semaphore semaphoreVIP;
	private static Semaphore semaphoreAbono;

	/**
	 * Launch the application.
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
	 * Create the application.
	 */
	public Servidor() {
		initialize();

		Thread thread = new Thread(() -> {
			try {
				startServer();
			} catch (Exception e) {
				// TODO: handle exception
			}
		});

		thread.start();
	}

	/**
	 * Initialize the contents of the frame.
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

		lblFacturacionNormal = new JLabel("New label");
		lblFacturacionNormal.setBounds(44, 123, 102, 13);
		frame.getContentPane().add(lblFacturacionNormal);

		lblFacturacionVip = new JLabel("New label");
		lblFacturacionVip.setBounds(174, 123, 102, 13);
		frame.getContentPane().add(lblFacturacionVip);

		lblFacturacionAbono = new JLabel("New label");
		lblFacturacionAbono.setBounds(300, 123, 102, 13);
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
		lblTotalEntradas.setBounds(199, 223, 45, 13);
		frame.getContentPane().add(lblTotalEntradas);
	}

	private void startServer() throws IOException {
		ServerSocket serverSocket = new ServerSocket(123);

		while (true) {
			Socket socket = serverSocket.accept();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String infoString = bufferedReader.readLine();

			String array[] = infoString.split(" ");

			numEntradasNormales = cambiarNumEntradas(array[0], numEntradasNormales, lblTotalNormal);

			numVip = cambiarNumEntradas(array[0], numEntradasNormales, lblTotalNormal);
			
			numAbono = cambiarNumEntradas(array[0], numEntradasNormales, lblTotalNormal);

			int totalEntradas = numEntradasNormales + numVip + numAbono;

			String numEntradaString = numEntradasNormales + " " + numVip + " " + numAbono;

			lblTotalEntradas.setText("Total: " + totalEntradas);

			OutputStream outputStream = socket.getOutputStream();
			outputStream.write((numEntradaString + "\n").getBytes());
			outputStream.flush();
		}
	}

	private int cambiarNumEntradas(String elemento, int numEntradas, JLabel lbl) {
		numEntradas = numEntradas - Integer.parseInt(elemento);
		lbl.setText(String.valueOf(numEntradas));
		return numEntradas;
	}

	

}
