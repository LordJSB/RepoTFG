package tfg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.Toolkit;
import java.awt.Font;

public class MainInterface extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel panelPpal;
    private JTable tablaPpal;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainInterface frame = new MainInterface("NombreDeEjemplo", 20, 3, 2); // Prueba con 20 filas y 20 columnas
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MainInterface(String nombreAutor, int numPersonas, int nGrupos, int nLimite) {
        setIconImage(Toolkit.getDefaultToolkit().getImage(MainInterface.class.getResource("/recursos/iconoGrupo.png")));
        setTitle("NOMBRE NO RECOGIDO");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1200, 900));
        panelPpal = new JPanel(new BorderLayout());
        panelPpal.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panelPpal);

        DefaultTableModel model = new DefaultTableModel(numPersonas + 1, numPersonas + 1) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return row != 0 && column != 0 && row != column;
            }
        };

        for (int i = 1; i <= numPersonas; i++) {
            model.setValueAt("Persona " + i, 0, i);
            model.setValueAt("Persona " + i, i, 0);
        }

        for (int i = 1; i <= numPersonas; i++) {
            model.setValueAt(0, i, i);
        }

        tablaPpal = new JTable(model);
        tablaPpal.setModel(model);
        tablaPpal.setDefaultRenderer(Object.class, new DiagonalCellRenderer());

        JComboBox<ComboBoxItem> comboBox = new JComboBox<>();
        comboBox.addItem(new ComboBoxItem(0, "0 - Indiferencia"));
        comboBox.addItem(new ComboBoxItem(1, "1 - Muy cercano"));
        comboBox.addItem(new ComboBoxItem(2, "2 - Cercano"));
        comboBox.addItem(new ComboBoxItem(3, "3 - Apatia"));
        comboBox.addItem(new ComboBoxItem(4, "4 - Desprecio"));

        comboBox.setRenderer(new DefaultListCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof ComboBoxItem) {
                    setText(((ComboBoxItem) value).toString());
                }
                return this;
            }
        });

        DefaultCellEditor editor = new DefaultCellEditor(comboBox) {
            private static final long serialVersionUID = 1L;

            @Override
            public Object getCellEditorValue() {
                ComboBoxItem item = (ComboBoxItem) super.getCellEditorValue();
                return item.getValor();
            }
        };

        JScrollPane scrollPane = new JScrollPane(tablaPpal);
        tablaPpal.setDefaultEditor(Object.class, editor);
        panelPpal.add(scrollPane, BorderLayout.CENTER);

        JButton btnDistribuir = new JButton("Calcular grupos");
        btnDistribuir.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
        btnDistribuir.setPreferredSize(new Dimension(180, 30));
        btnDistribuir.setMaximumSize(new Dimension(180, 30));
        btnDistribuir.setMinimumSize(new Dimension(180, 30));
        btnDistribuir.setSize(new Dimension(180, 30));

        btnDistribuir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Grupo> grupos = creaGrupos(nGrupos, nLimite);
                if (grupos != null) {
                    guardarInformeXML(nombreAutor, numPersonas, grupos);
                }
            }
        });

        JPanel panelBoton = new JPanel();
        panelBoton.add(btnDistribuir);
        panelPpal.add(panelBoton, BorderLayout.SOUTH);

        int tamanioColumna = 60; // Establecer la anchura mínima deseada para todas las columnas
        TableColumn firstColumn = tablaPpal.getColumnModel().getColumn(0);
        firstColumn.setPreferredWidth(tamanioColumna);

        for (int i = 0; i < tablaPpal.getColumnCount(); i++) { // Iterar sobre todas las columnas
            TableColumn columna = tablaPpal.getColumnModel().getColumn(i);
            columna.setMinWidth(tamanioColumna); // Establecer la anchura mínima de la columna
        }

        tablaPpal.setRowHeight(17);
        rellenarCeros();
        pack();
        setLocationRelativeTo(null);
    }



	public int[][] getDatosTabla() {
		int fila = tablaPpal.getRowCount();
		int columna = tablaPpal.getColumnCount();
		int[][] datosTabla = new int[fila][columna];
		for (int i = 1; i < fila; i++) {
			for (int j = 1; j < columna; j++) {
				if (tablaPpal.getValueAt(i, j) != null) {
					datosTabla[i][j] = Integer.parseInt(tablaPpal.getValueAt(i, j).toString());
				}
			}
		}
		return datosTabla;
	}

	private class DiagonalCellRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			if (row == column) {
				c.setBackground(Color.DARK_GRAY);
			} else {
				c.setBackground(table.getBackground());
			}
			return c;
		}
	}

	private void rellenarCeros() {
		int rowCount = tablaPpal.getRowCount();
		int columnCount = tablaPpal.getColumnCount();

		for (int i = 1; i < rowCount; i++) {
			for (int j = 1; j < columnCount; j++) {
				if (i != j) { // No rellenar la diagonal
					int valorAleatorio = 0;
					tablaPpal.setValueAt(valorAleatorio, i, j);
				}
			}
		}
	}

	private List<Grupo> creaGrupos(int nGrupos, int nLimite) {
		if (siCeldaVacia()) {
			JOptionPane.showMessageDialog(this, "Falta ingresar datos en la tabla.");
			return null;
		}
		int[][] tableData = getDatosTabla();
		List<List<Integer>> groups = new ArrayList<>();
		for (int[] nums : tableData) {
			if (tableData[0] != nums) {
				List<Integer> gr = new ArrayList<>();
				for (int i = 1; i < nums.length; i++) {
					gr.add(nums[i]);
				}
				groups.add(gr);
			}
		}

		// Crear personas
		List<Persona> personas = new ArrayList<>();
		int numero = 0;
		for (List<Integer> listaNumeros : groups) {

			personas.add(new Persona(numero, "Persona " + (numero + 1), listaNumeros));
			numero++;
		}

		// Distribuir personas en grupos
		DisposicionGrupal disposicion = Distribucion.distribuir(personas, nGrupos, nLimite);

		// Extraer los grupos de la disposición
		List<Grupo> gruposGenerados = disposicion.getListaGrupos();

		StringBuilder message = new StringBuilder();
		message.append("Organización óptima:\n");
		for (Grupo grupo : gruposGenerados) {
			message.append(grupo.toString()).append("\n");
		}
		message.append("\nSe ha creado un archivo 'informe.xml' en la ruta del proyecto/ejecutable.");
		JOptionPane.showMessageDialog(this, message.toString());

		return gruposGenerados;
	}

	private boolean siCeldaVacia() {
		int rowCount = tablaPpal.getRowCount();
		int columnCount = tablaPpal.getColumnCount();
		for (int i = 1; i < rowCount; i++) {
			for (int j = 1; j < columnCount; j++) {
				if (tablaPpal.getValueAt(i, j) == null) {
					return true;
				}
			}
		}
		return false;
	}

	private void guardarInformeXML(String nombre, int participantes, List<Grupo> grupos) {
		Informe informe = new Informe(nombre, participantes);
		informe.guardarParticipantesEnGrupos(grupos);

	}
}

class ComboBoxItem {
	private int valor;
	private String tapadera;

	public ComboBoxItem(int valor, String tapadera) {
		this.valor = valor;
		this.tapadera = tapadera;
	}

	public int getValor() {
		return valor;
	}

	@Override
	public String toString() {
		return tapadera;
	}
}
