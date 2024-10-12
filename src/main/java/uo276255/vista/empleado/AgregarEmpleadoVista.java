package uo276255.vista.empleado;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Clase que representa la vista para agregar empleados.
 */
public class AgregarEmpleadoVista extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField textNombre;
    private JTextField textApellido;
    private JTextField textDni;
    private JTextField textTelefono;
    private JTextField textFechaNacimiento;
    private JTextField textSalario;
    private JComboBox<String> comboTipoEmpleado;
    private JComboBox<String> comboPosicion;
    private JButton btnAgregar;
    private final String[] posicionesDeportivas = {"Jugador", "Entrenador"};
    private final String[] posicionesNoDeportivas = {
        "Gerente", "Vendedor de entradas/abonos", "Encargado de tienda", 
        "Gestor de instalaciones", "Empleados de tienda", 
        "Jardinería", "Cocina", "Director de comunicaciones"
    };

    /**
     * Constructor que inicializa la interfaz gráfica para agregar un empleado.
     */
    public AgregarEmpleadoVista() {
        setTitle("Añadir Empleado");
        setBounds(100, 100, 450, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); 
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
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
        panel.add(textNombre, gbc);

        JLabel lblApellido = new JLabel("Apellido:");
        lblApellido.setFont(fontLabel);
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(lblApellido, gbc);

        textApellido = new JTextField();
        textApellido.setFont(fontField);
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(textApellido, gbc);

        JLabel lblDni = new JLabel("DNI:");
        lblDni.setFont(fontLabel);
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(lblDni, gbc);

        textDni = new JTextField();
        textDni.setFont(fontField);
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(textDni, gbc);

        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setFont(fontLabel);
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(lblTelefono, gbc);

        textTelefono = new JTextField();
        textTelefono.setFont(fontField);
        gbc.gridx = 1; gbc.gridy = 3;
        panel.add(textTelefono, gbc);

        JLabel lblFechaNacimiento = new JLabel("Fecha Nacimiento (yyyy-mm-dd):");
        lblFechaNacimiento.setFont(fontLabel);
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(lblFechaNacimiento, gbc);

        textFechaNacimiento = new JTextField();
        textFechaNacimiento.setFont(fontField);
        gbc.gridx = 1; gbc.gridy = 4;
        panel.add(textFechaNacimiento, gbc);

        JLabel lblSalario = new JLabel("Salario:");
        lblSalario.setFont(fontLabel);
        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(lblSalario, gbc);

        textSalario = new JTextField();
        textSalario.setFont(fontField);
        gbc.gridx = 1; gbc.gridy = 5;
        panel.add(textSalario, gbc);

        JLabel lblTipoEmpleado = new JLabel("Tipo Empleado:");
        lblTipoEmpleado.setFont(fontLabel);
        gbc.gridx = 0; gbc.gridy = 6;
        panel.add(lblTipoEmpleado, gbc);

        comboTipoEmpleado = new JComboBox<>(new String[]{"Deportivo", "No Deportivo"});
        comboTipoEmpleado.setFont(fontField);
        gbc.gridx = 1; gbc.gridy = 6;
        panel.add(comboTipoEmpleado, gbc);

        JLabel lblPosicion = new JLabel("Posición:");
        lblPosicion.setFont(fontLabel);
        gbc.gridx = 0; gbc.gridy = 7;
        panel.add(lblPosicion, gbc);

        comboPosicion = new JComboBox<>(posicionesDeportivas); 
        comboPosicion.setFont(fontField);
        gbc.gridx = 1; gbc.gridy = 7;
        panel.add(comboPosicion, gbc);

        comboTipoEmpleado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarPosiciones();
            }
        });

        btnAgregar = new JButton("Agregar");
        btnAgregar.setFont(new Font("Arial", Font.BOLD, 16));
        btnAgregar.setBackground(new Color(0, 153, 204)); 
        btnAgregar.setForeground(Color.WHITE); 
        btnAgregar.setFocusPainted(false);
        gbc.gridx = 1; gbc.gridy = 8;
        gbc.gridwidth = 2; 
        gbc.anchor = GridBagConstraints.CENTER; 
        panel.add(btnAgregar, gbc);

        getContentPane().add(panel);

        pack(); 
    }

    /**
     * Actualiza las posiciones disponibles en el ComboBox dependiendo del tipo de empleado seleccionado.
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
     * Obtiene la posición seleccionada.
     * 
     * @return Posición del empleado.
     */
    public String getPosicion() {
        return (String) comboPosicion.getSelectedItem();
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
     * Obtiene la fecha de nacimiento ingresada.
     * 
     * @return Fecha de nacimiento del empleado, o null si la fecha es inválida.
     */
    public LocalDate getFechaNacimiento() {
        try {
            return LocalDate.parse(textFechaNacimiento.getText());
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Obtiene el salario ingresado.
     * 
     * @return Salario anual del empleado, o 0 si el formato es incorrecto.
     */
    public double getSalario() {
        try {
            return Double.parseDouble(textSalario.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Agrega un ActionListener al botón de agregar.
     * 
     * @param actionListener El ActionListener que se ejecutará cuando se presione el botón.
     */
    public void agregarListenerAgregar(ActionListener actionListener) {
        btnAgregar.addActionListener(actionListener);
    }

    /**
     * Muestra un mensaje de éxito en un cuadro de diálogo.
     * 
     * @param mensaje El mensaje de éxito a mostrar.
     */
    public void mostrarMensajeExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Muestra un mensaje de error en un cuadro de diálogo.
     * 
     * @param mensaje El mensaje de error a mostrar.
     */
    public void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
