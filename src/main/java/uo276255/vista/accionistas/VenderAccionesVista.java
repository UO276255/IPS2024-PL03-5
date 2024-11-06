package uo276255.vista.accionistas;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Clase que representa la vista para que un accionista venda sus acciones.
 */
public class VenderAccionesVista extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField txtIdAccionista;
    private JButton btnBuscarAcciones;
    private JTable tablaAcciones;
    private JButton btnPonerEnVenta;

    /**
     * Constructor de la vista.
     */
    public VenderAccionesVista() {
        inicializarComponentes();
    }

    /**
     * Inicializa los componentes de la interfaz gráfica.
     */
    private void inicializarComponentes() {
        setTitle("Vender Mis Acciones");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        // Panel superior para ingresar ID de accionista
        JPanel panelSuperior = new JPanel(new FlowLayout());
        panelSuperior.add(new JLabel("ID Accionista:"));
        txtIdAccionista = new JTextField(10);
        panelSuperior.add(txtIdAccionista);
        btnBuscarAcciones = new JButton("Buscar Acciones");
        panelSuperior.add(btnBuscarAcciones);

        // Tabla para mostrar las acciones
        tablaAcciones = new JTable();
        JScrollPane scrollPane = new JScrollPane(tablaAcciones);

        // Centramos los valores de la tabla
        centrarValoresTabla();

        // Botón para poner acciones en venta
        btnPonerEnVenta = new JButton("Poner Acciones Seleccionadas en Venta");
        JPanel panelInferior = new JPanel();
        panelInferior.add(btnPonerEnVenta);

        // Añadir componentes al frame
        getContentPane().add(panelSuperior, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(panelInferior, BorderLayout.SOUTH);
    }

    /**
     * Método para centrar los valores de la tabla.
     */
    private void centrarValoresTabla() {
        DefaultTableCellRenderer rendererCentro = new DefaultTableCellRenderer();
        rendererCentro.setHorizontalAlignment(SwingConstants.CENTER);
        tablaAcciones.setDefaultRenderer(Object.class, rendererCentro);
    }

    /**
     * Obtiene el ID del accionista ingresado.
     *
     * @return El ID del accionista, o -1 si no es válido.
     */
    public int getIdAccionista() {
        try {
            return Integer.parseInt(txtIdAccionista.getText().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Muestra las acciones del accionista en la tabla.
     *
     * @param datos Los datos de las acciones.
     */
    public void mostrarAcciones(Object[][] datos) {
        String[] columnas = {"ID Acción", "ID Campaña", "Seleccionar"};
        DefaultTableModel model = new DefaultTableModel(datos, columnas) {
            private static final long serialVersionUID = 1L;

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 2) {
                    return Boolean.class; // La última columna es un checkbox
                }
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2; // Solo la columna de selección es editable
            }
        };
        tablaAcciones.setModel(model);

        // Centramos los valores de la tabla después de establecer el modelo
        centrarValoresTabla();
    }

    /**
     * Obtiene las acciones seleccionadas por el usuario.
     *
     * @return Un arreglo de IDs de acciones seleccionadas.
     */
    public String[] getAccionesSeleccionadas() {
        DefaultTableModel model = (DefaultTableModel) tablaAcciones.getModel();
        int rowCount = model.getRowCount();
        java.util.List<String> accionesSeleccionadas = new java.util.ArrayList<>();
        for (int i = 0; i < rowCount; i++) {
            Boolean isSelected = (Boolean) model.getValueAt(i, 2); // Columna de selección
            if (isSelected != null && isSelected) {
                accionesSeleccionadas.add((String) model.getValueAt(i, 0)); // ID Acción
            }
        }
        return accionesSeleccionadas.toArray(new String[0]);
    }

    /**
     * Agrega un ActionListener al botón de buscar acciones.
     *
     * @param listener El ActionListener a agregar.
     */
    public void agregarListenerBuscarAcciones(ActionListener listener) {
        btnBuscarAcciones.addActionListener(listener);
    }

    /**
     * Agrega un ActionListener al botón de poner en venta.
     *
     * @param listener El ActionListener a agregar.
     */
    public void agregarListenerPonerEnVenta(ActionListener listener) {
        btnPonerEnVenta.addActionListener(listener);
    }

    /**
     * Muestra un mensaje de error al usuario.
     *
     * @param mensaje El mensaje de error a mostrar.
     */
    public void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Muestra un mensaje de éxito al usuario.
     *
     * @param mensaje El mensaje de éxito a mostrar.
     */
    public void mostrarMensajeExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
}
