package tfg;

import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;
import java.awt.Font;
import javax.swing.UIManager;

public class WelcomeInterface extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tFNombre;
	private JTextField tFParticipantes;
	private JTextField tFNumeroGrupos;
	private JTextField tFLimitePersonas;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WelcomeInterface frame = new WelcomeInterface();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public WelcomeInterface() {
		setBackground(UIManager.getColor("CheckBox.interiorBackground"));
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(WelcomeInterface.class.getResource("/recursos/iconoGrupo.png")));
		setTitle("Ventana de inicio");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 384);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("CheckBox.interiorBackground"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblBienvenida1 = new JLabel("Bienvenido a la aplicaci\u00F3n de sinergizaci\u00F3n de grupos.");
		lblBienvenida1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lblBienvenida1.setBounds(143, 11, 448, 20);
		contentPane.add(lblBienvenida1);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lblNombre.setBounds(192, 110, 78, 14);
		contentPane.add(lblNombre);

		tFNombre = new JTextField();
		tFNombre.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		tFNombre.setBounds(280, 107, 154, 20);
		contentPane.add(tFNombre);
		tFNombre.setColumns(10);

		JLabel lblValor = new JLabel("Participantes (6-25):");
		lblValor.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lblValor.setBounds(192, 162, 142, 14);
		contentPane.add(lblValor);

		tFParticipantes = new JTextField();
		tFParticipantes.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		tFParticipantes.setBounds(356, 159, 78, 20);
		contentPane.add(tFParticipantes);
		tFParticipantes.setColumns(10);
		tFParticipantes.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c >= '0' && c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					e.consume();
				}
				if (tFParticipantes.getText().length() >= 2) {
					e.consume();
				}
			}
		});

		JLabel lblnumeroGrupos = new JLabel("Numero de grupos:");
		lblnumeroGrupos.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lblnumeroGrupos.setBounds(40, 221, 142, 17);
		contentPane.add(lblnumeroGrupos);

		tFNumeroGrupos = new JTextField();
		tFNumeroGrupos.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		tFNumeroGrupos.setBounds(192, 219, 60, 20);
		contentPane.add(tFNumeroGrupos);
		tFNumeroGrupos.setColumns(10);
		tFNumeroGrupos.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c >= '0' && c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					e.consume();
				}
				if (tFNumeroGrupos.getText().length() >= 2) {
					e.consume();
				}
			}
		});

		JLabel lblLimitePersonas = new JLabel("Personas por grupo:");
		lblLimitePersonas.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lblLimitePersonas.setBounds(372, 223, 142, 17);
		contentPane.add(lblLimitePersonas);

		tFLimitePersonas = new JTextField();
		tFLimitePersonas.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		tFLimitePersonas.setBounds(524, 221, 60, 20);
		contentPane.add(tFLimitePersonas);
		tFLimitePersonas.setColumns(10);
		tFLimitePersonas.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c >= '0' && c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					e.consume();
				}
				if (tFLimitePersonas.getText().length() >= 2) {
					e.consume();
				}
			}
		});

		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 15));
		btnGuardar.setBounds(240, 305, 120, 29);
		contentPane.add(btnGuardar);

		JLabel lblBienvenida2 = new JLabel("Por favor inserte los campos solicitados a continuaci\u00F3n:");
		lblBienvenida2.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lblBienvenida2.setBounds(143, 29, 448, 20);
		contentPane.add(lblBienvenida2);

		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardarDatos();
			}
		});
	}

	protected void guardarDatos() {
	    String nombre = tFNombre.getText();
	    if (nombre.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "El campo 'Nombre' no puede estar vacío.");
	        return;
	    }

	    int participantes;
	    int nGrupos;
	    int nLimite;
	    try {
	        participantes = Integer.parseInt(tFParticipantes.getText());
	        nGrupos = Integer.parseInt(tFNumeroGrupos.getText());
	        nLimite = Integer.parseInt(tFLimitePersonas.getText());
	    } catch (NumberFormatException ex) {
	        JOptionPane.showMessageDialog(this, "Ingrese números válidos en todos los campos correspondientes.");
	        return;
	    }

	    if (participantes < 6 || participantes > 25) {
	        JOptionPane.showMessageDialog(this, "El límite de participantes es entre 6 y 25.");
	        return;
	    }

	    if (nGrupos * nLimite < participantes) {
	        JOptionPane.showMessageDialog(this,
	                "El producto de 'Número de grupos' y 'Límite de personas' debe ser mayor o igual al número de participantes.");
	        return;
	    }

	    int gruposOptimos = (int) Math.ceil((double) participantes / nLimite);
	    int tamanioOptimo = (int) Math.ceil((double) participantes / nGrupos);

	    while (gruposOptimos * tamanioOptimo < participantes) {
	        if (gruposOptimos < tamanioOptimo) {
	            gruposOptimos++;
	        } else {
	            tamanioOptimo++;
	        }
	    }

	    if (nGrupos != gruposOptimos || nLimite != tamanioOptimo) {
	        // Mostrar ventana emergente con los valores óptimos sugeridos
	        String message = "Los valores ingresados no son óptimos.\n"
	                + "Se sugiere cambiar los valores a: \n"
	                + "Número de grupos: " + gruposOptimos + "\n"
	                + "Límite de personas: " + tamanioOptimo;
	        int respuesta = JOptionPane.showOptionDialog(this, message, "Datos no óptimos",
	                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null,
	                new Object[] { "Aplicar valores óptimos", "Revisar datos", "Continuar con los datos actuales" },
	                "Aplicar valores óptimos");

	        if (respuesta == JOptionPane.YES_OPTION) {
	            nGrupos = gruposOptimos;
	            nLimite = tamanioOptimo;
	        } else if (respuesta == JOptionPane.NO_OPTION) {
	            return; // No hacer cambios y salir del método
	        }
	    }

	    JOptionPane.showMessageDialog(this,
	            "Ahora se procederá a establecer el nivel de animosidad entre los diferentes participantes. "
	                    + "\nSe mostrará una tabla con cada uno de ellos en una fila y columna, de tal manera que la columna del Invitado 1 "
	                    + "mostrará su nivel de animosidad con el resto de participantes y lo mismo sucederá con las filas. "
	                    + "\nDichos niveles están definidos como:"
	                    + "\n- 1: Gran apego con la otra persona, busca mantenerse en el mismo grupo en la medida de lo posible."
	                    + "\n- 2: Hay lazos de amistad o cordialidad con la otra persona"
	                    + "\n- 3: Aunque no se agraden en gran medida, pueden compartir espacio de manera respetuosa"
	                    + "\n- 4: Buscan mantenerse alejados unos de otros por todos los medios"
	                    + "\n- 0: Indiferencia por desconocimiento entre las personas u otro motivo");
	    MainInterface mainInterface = new MainInterface(nombre, participantes, nGrupos, nLimite);
	    mainInterface.setTitle("Organización del evento de " + nombre);
	    mainInterface.setVisible(true);
	    dispose();
	}


}
