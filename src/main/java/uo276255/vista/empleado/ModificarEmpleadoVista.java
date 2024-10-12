package uo276255.vista.empleado;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import uo276255.modelo.empleado.EmpleadoDTO;

/**
 * Clase que representa la vista para modificar empleados.
 */
public class ModificarEmpleadoVista extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField textNombre;
    private JTextField textApellido;
    private JTextField textDni;
    private JTextField textTelefono;
    private JTextField textFechaNacimiento;
    private JTextField textSalario;
    private JComboBox<String> comboTipoEmpleado;
    private JComboBox<String> comboPosicion;
    private JButton btnModificar;

    // Listas de posiciones
    private final String[] posicionesDeportivas = {"Jugador", "Entrenador"};
    private final String[] posicionesNoDeportivas = {
        "Gerente", "Vendedor de entradas/abonos", "Encargado de tienda",
        "Gestor de instalaciones", "Empleados de tienda", "Jardinería",
        "Cocina", "Director de comunicaciones"
    };

    /**
     * Constructor que inicializa la interfaz gráfica para modificar un empleado.
     */
    public ModificarEmpleadoVista() {
        setTitle("Modificar Empleado");
        setBounds(100, 100, 1500, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font fontLabel = new Font("Arial", Font.PLAIN, 14);
        Font fontField = new Font("Arial", Font.PLAIN, 14);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(fontLabel);
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(lblNombre, gbc);

        textNombre = new JTextField();
        textNombre.setFont(fontField);
        gbc.gridx = 1; gbc.gridy = 0;
        gbc.gridwidth = 3;
        panel.add(textNombre, gbc);

        gbc.gridwidth = 1;
        JLabel lblApellido = new JLabel("Apellido:");
        lblApellido.setFont(fontLabel);
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(lblApellido, gbc);

        textApellido = new JTextField();
        textApellido.setFont(fontField);
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.gridwidth = 3;
        panel.add(textApellido, gbc);

        gbc.gridwidth = 1;
        JLabel lblDni = new JLabel("DNI:");
        lblDni.setFont(fontLabel);
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(lblDni, gbc);

        textDni = new JTextField();
        textDni.setFont(fontField);
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.gridwidth = 3;
        panel.add(textDni, gbc);

        gbc.gridwidth = 1;
        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setFont(fontLabel);
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(lblTelefono, gbc);

        textTelefono = new JTextField();
        textTelefono.setFont(fontField);
        gbc.gridx = 1; gbc.gridy = 3;
        gbc.gridwidth = 3;
        panel.add(textTelefono, gbc);

        gbc.gridwidth = 1;
        JLabel lblFechaNacimiento = new JLabel("Fecha Nacimiento:");
        lblFechaNacimiento.setFont(fontLabel);
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(lblFechaNacimiento, gbc);

        textFechaNacimiento = new JTextField();
        textFechaNacimiento.setFont(fontField);
        gbc.gridx = 1; gbc.gridy = 4;
        gbc.gridwidth = 3;
        panel.add(textFechaNacimiento, gbc);

        gbc.gridwidth = 1;
        JLabel lblSalario = new JLabel("Salario:");
        lblSalario.setFont(fontLabel);
        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(lblSalario, gbc);

        textSalario = new JTextField();
        textSalario.setFont(fontField);
        gbc.gridx = 1; gbc.gridy = 5;
        gbc.gridwidth = 3;
        panel.add(textSalario, gbc);

        gbc.gridwidth = 1;
        JLabel lblTipoEmpleado = new JLabel("Tipo Empleado:");
        lblTipoEmpleado.setFont(fontLabel);
        gbc.gridx = 0; gbc.gridy = 6;
        panel.add(lblTipoEmpleado, gbc);

        comboTipoEmpleado = new JComboBox<>(new String[]{"Deportivo", "No Deportivo"});
        comboTipoEmpleado.setFont(fontField);
        gbc.gridx = 1; gbc.gridy = 6;
        gbc.gridwidth = 3;
        panel.add(comboTipoEmpleado, gbc);

        gbc.gridwidth = 1;
        JLabel lblPosicion = new JLabel("Posición:");
        lblPosicion.setFont(fontLabel);
        gbc.gridx = 0; gbc.gridy = 7;
        panel.add(lblPosicion, gbc);

        comboPosicion = new JComboBox<>(posicionesDeportivas);
        comboPosicion.setFont(fontField);
        gbc.gridx = 1; gbc.gridy = 7;
        gbc.gridwidth = 3;
        panel.add(comboPosicion, gbc);

        gbc.gridwidth = 1;
        btnModificar = new JButton("Modificar");
        btnModificar.setFont(new Font("Arial", Font.BOLD, 16));
        btnModificar.setBackground(new Color(0, 153, 204));
        btnModificar.setForeground(Color.WHITE);
        btnModificar.setFocusPainted(false);
        gbc.gridx = 0; gbc.gridy = 8;
        gbc.gridwidth = 4;
        panel.add(btnModificar, gbc);

        getContentPane().add(panel);

        pack();

        comboTipoEmpleado.addActionListener(e -> actualizarPosiciones());
    }

    /**
     * Cargar los datos del empleado en el formulario.
     *
     * @param empleado El objeto EmpleadoDTO con los datos a cargar.
     */
    public void cargarEmpleado(EmpleadoDTO empleado) {
        textNombre.setText(empleado.getNombre());
        textApellido.setText(empleado.getApellido());
        textDni.setText(empleado.getDni());
        textTelefono.setText(empleado.getTelefono());
        textFechaNacimiento.setText(empleado.getFechaNacimiento().toString());
        textSalario.setText(String.valueOf(empleado.getSalarioAnualBruto()));
        comboTipoEmpleado.setSelectedItem(empleado.getTipoEmpleado());
        actualizarPosiciones();
        comboPosicion.setSelectedItem(empleado.getTipoDetalle());
    }

    /**
     * Actualiza las posiciones disponibles según el tipo de empleado seleccionado.
     */
    private void actualizarPosiciones() {
        String tipoEmpleado = (String) comboTipoEmpleado.getSelectedItem();
        if (tipoEmpleado.equals("Deportivo")) {
            comboPosicion.setModel(new DefaultComboBoxModel<>(posicionesDeportivas));
        } else {
            comboPosicion.setModel(new DefaultComboBoxModel<>(posicionesNoDeportivas));
        }
    }

    /**
     * Obtiene el nombre ingresado.
     *
     * @return Nombre del empleado.
     */
    public String getNombre() {
        return textNombre.getText();
    }

    /**
     * Obtiene el apellido ingresado.
     *
     * @return Apellido del empleado.
     */
    public String getApellido() {
        return textApellido.getText();
    }

    /**
     * Obtiene el DNI ingresado.
     *
     * @return DNI del empleado.
     */
    public String getDni() {
        return textDni.getText();
    }

    /**
     * Obtiene el teléfono ingresado.
     *
     * @return Teléfono del empleado.
     */
    public String getTelefono() {
        return textTelefono.getText();
    }

    /**
     * Obtiene la fecha de nacimiento ingresada.
     *
     * @return Fecha de nacimiento del empleado.
     */
    public LocalDate getFechaNacimiento() {
        return LocalDate.parse(textFechaNacimiento.getText());
    }

    /**
     * Obtiene el salario anual bruto ingresado.
     *
     * @return Salario anual bruto del empleado.
     */
    public double getSalarioAnualBruto() {
        return Double.parseDouble(textSalario.getText());
    }

    /**
     * Obtiene el tipo de empleado seleccionado.
     *
     * @return Tipo de empleado.
     */
    public String getTipoEmpleado() {
        return (String) comboTipoEmpleado.getSelectedItem();
    }

    /**
     * Obtiene el detalle del tipo de empleado seleccionado.
     *
     * @return Detalle del tipo de empleado.
     */
    public String getTipoDetalle() {
        return (String) comboPosicion.getSelectedItem();
    }

    /**
     * Método para registrar el listener del botón modificar.
     *
     * @param listener El ActionListener a registrar.
     */
    public void agregarListenerModificar(ActionListener listener) {
        btnModificar.addActionListener(listener);
    }

    /**
     * Muestra un mensaje de éxito en un cuadro de diálogo.
     *
     * @param mensaje Mensaje de éxito a mostrar.
     */
    public void mostrarMensajeExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Muestra un mensaje de error en un cuadro de diálogo.
     *
     * @param mensaje Mensaje de error a mostrar.
     */
    public void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
