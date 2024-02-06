import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.management.loading.PrivateClassLoader;
import javax.sound.midi.Soundbank;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;

public class Cliente extends Entrada {

	private JFrame frame;

	private JButton btnComprar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cliente window = new Cliente();
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
	public Cliente() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		btnComprar = new JButton("Comprar");
		btnComprar.setBounds(180, 199, 85, 21);
		frame.getContentPane().add(btnComprar);

		JLabel lblNormal = new JLabel("Entradas Normal");
		lblNormal.setBounds(63, 98, 76, 13);
		frame.getContentPane().add(lblNormal);

		JLabel lblVip = new JLabel("Entradas VIP");
		lblVip.setBounds(180, 98, 76, 13);
		frame.getContentPane().add(lblVip);

		JLabel lblAbono = new JLabel("Abono VIP");
		lblAbono.setHorizontalAlignment(SwingConstants.LEFT);
		lblAbono.setBounds(284, 98, 76, 13);
		frame.getContentPane().add(lblAbono);

		JLabel lblNewLabel = new JLabel("BIENVENIDO");
		lblNewLabel.setBounds(180, 10, 71, 19);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblIntroduceElN = new JLabel("Introduce el nº de entradas");
		lblIntroduceElN.setBounds(148, 39, 126, 19);
		frame.getContentPane().add(lblIntroduceElN);

		JLabel lblNoPuedeSer = new JLabel("No puedes comprar más de 3");
		lblNoPuedeSer.setBounds(148, 54, 143, 19);
		frame.getContentPane().add(lblNoPuedeSer);

		JButton btnInfo = new JButton("Info");
		btnInfo.setBounds(10, 9, 85, 21);
		frame.getContentPane().add(btnInfo);

		JSpinner spNormal = new JSpinner();
		spNormal.setBounds(63, 121, 30, 20);
		frame.getContentPane().add(spNormal);

		JSpinner spVIP = new JSpinner();
		spVIP.setBounds(180, 121, 30, 20);
		frame.getContentPane().add(spVIP);

		JSpinner spAbono = new JSpinner();
		spAbono.setBounds(284, 121, 30, 20);
		frame.getContentPane().add(spAbono);
		
		JLabel lblMensaje = new JLabel("");
		lblMensaje.setBounds(115, 170, 212, 19);
		frame.getContentPane().add(lblMensaje);

		btnComprar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Socket socket;
				try {
					socket = new Socket("localhost", 123);
					OutputStream outputStream = socket.getOutputStream();

					String mensajeString = convertirString(spNormal, spVIP, spAbono);
					
					if(mensajeString != null) {
						outputStream.write((mensajeString + "\n").getBytes());
						outputStream.flush();

						BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

						String infoString = bufferedReader.readLine();
						String array[] = infoString.split(" ");

						numEntradasNormales = Integer.parseInt(array[0]);
						numVip = Integer.parseInt(array[1]);
						numAbono = Integer.parseInt(array[2]); 

						lblMensaje.setText("");
					} else {
						lblMensaje.setText("Tienes que comprar un máximo de 3 entradas");
					}

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Info info = new Info(numEntradasNormales, numVip, numAbono);
				info.main(null);

			}
		});

	}

	private String convertirString(JSpinner sp, JSpinner sp2, JSpinner sp3) {
	    try {
	        int total = Integer.parseInt(sp.getValue().toString()) + 
	                    Integer.parseInt(sp2.getValue().toString()) + 
	                    Integer.parseInt(sp3.getValue().toString());

	        if (total > 3 || total <= 0) {
	            return null;
	        } else {
	            return sp.getValue().toString() + " " + sp2.getValue().toString() + " " + sp3.getValue().toString();
	        }
	    } catch (NumberFormatException e) {
	        // Manejar la excepción si los valores no son números válidos
	    	e.printStackTrace();
	        return null;
	    }
	}
}
