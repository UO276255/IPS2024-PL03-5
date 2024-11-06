package uo276255.vista.acciones;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Clase que representa la vista para mostrar las acciones disponibles para comprar.
 */
public class AccionesDisponiblesVista extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTable tablaAccionesEnVenta;
    private JLabel lblCampañaActiva;
    private JLabel lblAccionesDisponiblesCampaña;
    private JLabel lblFaseCampaña; // Nuevo JLabel para la fase
    private JButton btnActualizar;
    private JButton btnComprarAccion;
    private JButton btnComprarAccionesCampaña;
    private JTextField txtIdEmpleado;
    private JTextField txtCantidadAcciones;

    /**
     * Constructor de la vista de acciones disponibles.
     */
    public AccionesDisponiblesVista() {
        inicializarComponentes();
    }

    /**
     * Inicializa los componentes de la interfaz gráfica.
     */
    private void inicializarComponentes() {
        setTitle("Acciones Disponibles para Comprar");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 550);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        // Panel superior para información de la campaña
        JPanel panelCampaña = new JPanel(new GridLayout(3, 1)); // Cambiamos a 3 filas
        lblCampañaActiva = new JLabel("Compra de acciones");
        lblCampañaActiva.setFont(new Font("Arial", Font.BOLD, 16));
        lblAccionesDisponiblesCampaña = new JLabel("");
        lblAccionesDisponiblesCampaña.setFont(new Font("Arial", Font.PLAIN, 14));
        lblFaseCampaña = new JLabel(""); // Inicializamos el nuevo JLabel
        lblFaseCampaña.setFont(new Font("Arial", Font.PLAIN, 14));
        panelCampaña.add(lblCampañaActiva);
        panelCampaña.add(lblAccionesDisponiblesCampaña);
        panelCampaña.add(lblFaseCampaña); // Añadimos el JLabel al panel
        panelCampaña.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Tabla para mostrar las acciones en venta
        tablaAccionesEnVenta = new JTable();
        // Centramos los valores de la tabla
        centrarValoresTabla();

        JScrollPane scrollPane = new JScrollPane(tablaAccionesEnVenta);

        // Panel inferior con botones y campos de texto
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        // Componentes para comprar acción seleccionada
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelInferior.add(new JLabel("ID Empleado:"), gbc);

        txtIdEmpleado = new JTextField(10);
        gbc.gridx = 1;
        panelInferior.add(txtIdEmpleado, gbc);

        btnComprarAccion = new JButton("Comprar Acción Seleccionada");
        gbc.gridx = 2;
        panelInferior.add(btnComprarAccion, gbc);

        // Componentes para comprar acciones de campaña
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelInferior.add(new JLabel("Cantidad de Acciones a Comprar:"), gbc);

        txtCantidadAcciones = new JTextField(10);
        gbc.gridx = 1;
        panelInferior.add(txtCantidadAcciones, gbc);

        btnComprarAccionesCampaña = new JButton("Comprar Acciones de Campaña");
        gbc.gridx = 2;
        panelInferior.add(btnComprarAccionesCampaña, gbc);

        // Botón de actualizar
        btnActualizar = new JButton("Actualizar");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        panelInferior.add(btnActualizar, gbc);

        // Añadir componentes al frame
        getContentPane().add(panelCampaña, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(panelInferior, BorderLayout.SOUTH);
    }

    /**
     * Muestra la información de la campaña activa.
     *
     * @param nombreCampaña      Nombre de la campaña activa.
     * @param accionesDisponibles Número de acciones disponibles en la campaña.
     * @param fase                Fase actual de la campaña.
     */
    public void mostrarCampañaActiva(String nombreCampaña, int accionesDisponibles, int fase) {
        lblCampañaActiva.setText("Campaña Activa: " + nombreCampaña);
        lblAccionesDisponiblesCampaña.setText("Acciones disponibles en la campaña: " + accionesDisponibles);
        lblFaseCampaña.setText("Fase de la campaña: " + fase);
    }

    /**
     * Indica que no hay una campaña activa.
     */
    public void mostrarSinCampañaActiva() {
        lblCampañaActiva.setText("Campaña Activa: No hay campaña activa actualmente");
        lblAccionesDisponiblesCampaña.setText("");
        lblFaseCampaña.setText("");
    }

    /**
     * Muestra las acciones en venta en la tabla.
     *
     * @param datos Los datos de las acciones en venta.
     */
    public void mostrarAccionesEnVenta(Object[][] datos) {
        String[] columnas = {"ID Acción", "ID Accionista"};
        DefaultTableModel model = new DefaultTableModel(datos, columnas) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacemos que las celdas no sean editables
            }
        };
        tablaAccionesEnVenta.setModel(model);

        // Centramos los valores de la tabla después de establecer el modelo
        centrarValoresTabla();
    }

    /**
     * Método para centrar los valores de la tabla.
     */
    private void centrarValoresTabla() {
        DefaultTableCellRenderer rendererCentro = new DefaultTableCellRenderer();
        rendererCentro.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tablaAccionesEnVenta.getColumnCount(); i++) {
            tablaAccionesEnVenta.getColumnModel().getColumn(i).setCellRenderer(rendererCentro);
        }
    }

    /**
     * Agrega un ActionListener al botón de actualizar.
     *
     * @param listener El ActionListener a agregar.
     */
    public void agregarListenerActualizar(ActionListener listener) {
        btnActualizar.addActionListener(listener);
    }

    /**
     * Agrega un ActionListener al botón de comprar acción.
     *
     * @param listener El ActionListener a agregar.
     */
    public void agregarListenerComprarAccion(ActionListener listener) {
        btnComprarAccion.addActionListener(listener);
    }

    /**
     * Agrega un ActionListener al botón de comprar acciones de campaña.
     *
     * @param listener El ActionListener a agregar.
     */
    public void agregarListenerComprarAccionesCampaña(ActionListener listener) {
        btnComprarAccionesCampaña.addActionListener(listener);
    }

    /**
     * Obtiene el ID del empleado ingresado por el usuario.
     *
     * @return El ID del empleado, o -1 si no es un entero válido.
     */
    public int getIdEmpleado() {
        try {
            return Integer.parseInt(txtIdEmpleado.getText().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Obtiene el ID de la acción seleccionada en la tabla.
     *
     * @return El ID de la acción seleccionada, o null si no hay selección.
     */
    public String getIdAccionSeleccionada() {
        int filaSeleccionada = tablaAccionesEnVenta.getSelectedRow();
        if (filaSeleccionada >= 0) {
            return (String) tablaAccionesEnVenta.getValueAt(filaSeleccionada, 0);
        } else {
            return null;
        }
    }

    /**
     * Obtiene la cantidad de acciones a comprar ingresada por el usuario.
     *
     * @return La cantidad de acciones, o -1 si no es un entero válido.
     */
    public int getCantidadAccionesComprar() {
        try {
            return Integer.parseInt(txtCantidadAcciones.getText().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Muestra un mensaje de error al usuario.
     *
     * @param mensaje El mensaje a mostrar.
     */
    public void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Muestra un mensaje de éxito al usuario.
     *
     * @param mensaje El mensaje a mostrar.
     */
    public void mostrarMensajeExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
}
