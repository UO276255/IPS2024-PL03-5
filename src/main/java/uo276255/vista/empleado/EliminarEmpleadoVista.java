package uo276255.vista.empleado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import uo276255.modelo.empleado.EmpleadoDTO;

/**
 * Clase que representa la vista para eliminar o modificar empleados.
 */
public class EliminarEmpleadoVista extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTable tablaEmpleados;
    private JButton btnEliminar;
    private JButton btnModificar;
    private List<EmpleadoDTO> listaEmpleados; // Variable para almacenar la lista original

    /**
     * Constructor que inicializa la interfaz gráfica para eliminar o modificar empleados.
     */
    public EliminarEmpleadoVista() {
        setTitle("Eliminar o Modificar Empleado");
        setBounds(100, 100, 600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        setLayout(new BorderLayout(10, 10));

        JPanel panelTitulo = new JPanel();
        JLabel lblTitulo = new JLabel("Lista de Empleados", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(0, 102, 204));
        panelTitulo.add(lblTitulo);
        add(panelTitulo, BorderLayout.NORTH);

        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        tablaEmpleados = new JTable();
        JScrollPane scrollPane = new JScrollPane(tablaEmpleados);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Empleados Disponibles"));
        panelTabla.add(scrollPane, BorderLayout.CENTER);
        add(panelTabla, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 14));
        btnEliminar.setBackground(new Color(255, 102, 102));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelBotones.add(btnEliminar, gbc);

        btnModificar = new JButton("Modificar");
        btnModificar.setFont(new Font("Arial", Font.BOLD, 14));
        btnModificar.setBackground(new Color(0, 153, 204));
        btnModificar.setForeground(Color.WHITE);
        btnModificar.setFocusPainted(false);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panelBotones.add(btnModificar, gbc);

        add(panelBotones, BorderLayout.SOUTH);
    }

    /**
     * Método para cargar empleados en la tabla.
     *
     * @param empleados Lista de empleados a mostrar.
     */
    public void cargarEmpleados(List<EmpleadoDTO> empleados) {
        this.listaEmpleados = empleados; // Almacenar la lista original

        // Definir las columnas de la tabla
        String[] columnNames = {"ID", "Nombre", "Apellido", "Puesto"}; // Ajustar según los campos de EmpleadoDTO

        // Crear el modelo de la tabla
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Agregar las filas al modelo
        for (EmpleadoDTO empleado : empleados) {
            Object[] rowData = {
                empleado.getId(),
                empleado.getNombre(),
                empleado.getApellido(),
                empleado.getTipoEmpleado()
            };
            model.addRow(rowData);
        }

        tablaEmpleados.setModel(model);
        tablaEmpleados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    /**
     * Obtiene el empleado seleccionado de la tabla.
     *
     * @return El empleado seleccionado o null si no hay selección.
     */
    public EmpleadoDTO getEmpleadoSeleccionado() {
        int selectedRow = tablaEmpleados.getSelectedRow();
        if (selectedRow >= 0) {
            // Obtener el ID del empleado seleccionado
            String id = (String) tablaEmpleados.getValueAt(selectedRow, 0);
            // Buscar el empleado en la lista original
            for (EmpleadoDTO empleado : listaEmpleados) {
                if (empleado.getId() == id) {
                    return empleado;
                }
            }
        }
        return null;
    }

    /**
     * Registrar el listener para el botón eliminar.
     *
     * @param listener El listener a registrar.
     */
    public void agregarListenerEliminar(ActionListener listener) {
        btnEliminar.addActionListener(listener);
    }

    /**
     * Registrar el listener para el botón modificar.
     *
     * @param listener El listener a registrar.
     */
    public void agregarListenerModificar(ActionListener listener) {
        btnModificar.addActionListener(listener);
    }

    /**
     * Mostrar mensaje de éxito.
     *
     * @param mensaje Mensaje a mostrar.
     */
    public void mostrarMensajeExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Mostrar mensaje de error.
     *
     * @param mensaje Mensaje a mostrar.
     */
    public void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
