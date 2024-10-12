package uo276255.vista.empleado;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import uo276255.modelo.empleado.EmpleadoDTO;

/**
 * Clase que representa la vista para eliminar o modificar empleados.
 */
public class EliminarEmpleadoVista extends JFrame {

    private static final long serialVersionUID = 1L;
    private JList<EmpleadoDTO> listaEmpleados;
    private JButton btnEliminar;
    private JButton btnModificar;

    /**
     * Constructor que inicializa la interfaz gráfica para eliminar o modificar empleados.
     */
    public EliminarEmpleadoVista() {
        setTitle("Eliminar o Modificar Empleado");
        setBounds(100, 100, 500, 400);
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

        JPanel panelLista = new JPanel(new BorderLayout());
        panelLista.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        listaEmpleados = new JList<>();
        JScrollPane scrollPane = new JScrollPane(listaEmpleados);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Empleados Disponibles"));
        panelLista.add(scrollPane, BorderLayout.CENTER);
        add(panelLista, BorderLayout.CENTER);

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
     * Método para cargar empleados en la lista.
     *
     * @param empleados Lista de empleados a mostrar.
     */
    public void cargarEmpleados(List<EmpleadoDTO> empleados) {
        DefaultListModel<EmpleadoDTO> model = new DefaultListModel<>();
        for (EmpleadoDTO empleado : empleados) {
            model.addElement(empleado);
        }
        listaEmpleados.setModel(model);
        listaEmpleados.setCellRenderer(new EmpleadoListRenderer());
    }

    /**
     * Obtiene el empleado seleccionado de la lista.
     *
     * @return El empleado seleccionado o null si no hay selección.
     */
    public EmpleadoDTO getEmpleadoSeleccionado() {
        return listaEmpleados.getSelectedValue();
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

/**
 * Renderizador personalizado para mostrar el nombre y apellido del empleado en la lista.
 */
class EmpleadoListRenderer extends DefaultListCellRenderer {
    private static final long serialVersionUID = 1L;

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        EmpleadoDTO empleado = (EmpleadoDTO) value;
        String displayText = empleado.toString();
        return super.getListCellRendererComponent(list, displayText, index, isSelected, cellHasFocus);
    }
}
