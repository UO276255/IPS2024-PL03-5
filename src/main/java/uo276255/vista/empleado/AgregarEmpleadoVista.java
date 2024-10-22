package uo276255.vista.empleado;

import javax.swing.*;
import org.jdatepicker.impl.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Properties;

/**
 * Clase que representa la vista para agregar empleados.
 */
public class AgregarEmpleadoVista extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField textNombre;
    private JTextField textApellido;
    private JTextField textDni;
    private JTextField textTelefono;
    private JDatePickerImpl datePickerFechaNacimiento;
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
        setBounds(100, 100, 600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font fontLabel = new Font("Arial", Font.BOLD, 16);
        Font fontField = new Font("Arial", Font.PLAIN, 16);

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

        JLabel lblFechaNacimiento = new JLabel("Fecha Nacimiento:");
        lblFechaNacimiento.setFont(fontLabel);
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(lblFechaNacimiento, gbc);

        // Configuración del JDatePicker
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Hoy");
        p.put("text.month", "Mes");
        p.put("text.year", "Año");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePickerFechaNacimiento = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePickerFechaNacimiento.getJFormattedTextField().setFont(fontField);
        gbc.gridx = 1; gbc.gridy = 4;
        panel.add(datePickerFechaNacimiento, gbc);

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
        btnAgregar.setFont(new Font("Arial", Font.BOLD, 18));
        btnAgregar.setBackground(new Color(0, 153, 204));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setFocusPainted(false);
        btnAgregar.setPreferredSize(new Dimension(150, 40));
        btnAgregar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAgregar.setBackground(new Color(0, 123, 180));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAgregar.setBackground(new Color(0, 153, 204));
            }
        });
        gbc.gridx = 0; gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnAgregar, gbc);

        getContentPane().add(panel);

        pack();
    }

    /**
     * Formateador personalizado para el JDatePicker.
     */
    class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

        private static final long serialVersionUID = 1L;
        private String datePattern = "yyyy-MM-dd";
        private java.text.SimpleDateFormat dateFormatter = new java.text.SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws java.text.ParseException {
            return dateFormatter.parse(text);
        }

        @Override
        public String valueToString(Object value) throws java.text.ParseException {
            if (value != null) {
                java.util.Calendar cal = (java.util.Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }
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
     * Obtiene la fecha de nacimiento seleccionada.
     *
     * @return Fecha de nacimiento del empleado, o null si no se ha seleccionado ninguna fecha.
     */
    public LocalDate getFechaNacimiento() {
        if (datePickerFechaNacimiento.getModel().getValue() != null) {
            java.util.Date selectedDate = (java.util.Date) datePickerFechaNacimiento.getModel().getValue();
            return selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } else {
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
     * Muestra un mensaje de éxito en un cuadro de diálogo y cierra la ventana.
     *
     * @param mensaje El mensaje de éxito a mostrar.
     */
    public void mostrarMensajeExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        dispose(); // Cerrar la ventana después de mostrar el mensaje de éxito
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

/**
 * Formateador personalizado para el JDatePicker.
 */
class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

    private static final long serialVersionUID = 1L;
    private String datePattern = "yyyy-MM-dd";
    private java.text.SimpleDateFormat dateFormatter = new java.text.SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String text) throws java.text.ParseException {
        return dateFormatter.parse(text);
    }

    @Override
    public String valueToString(Object value) throws java.text.ParseException {
        if (value != null) {
            java.util.Calendar cal = (java.util.Calendar) value;
            return dateFormatter.format(cal.getTime());
        }
        return "";
    }
}
