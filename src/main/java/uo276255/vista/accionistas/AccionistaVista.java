package uo276255.vista.accionistas;

import uo276255.modelo.accionistas.AccionistaDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Clase que representa la vista para gestionar accionistas.
 */
public class AccionistaVista extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField txtNombre;
    private JTextField txtDNI;
    private JTextField txtTelefono;
    private JTextField txtEmail;
    private JButton btnCrearAccionista;
    private JButton btnLimpiarCampos;

    /**
     * Constructor de la vista de accionistas.
     */
    public AccionistaVista() {
        inicializarComponentes();
    }

    /**
     * Inicializa los componentes de la interfaz gráfica.
     */
    private void inicializarComponentes() {
        setTitle("Agregar Accionista");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        getContentPane().setLayout(new BorderLayout());

        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridBagLayout());
        getContentPane().add(panelPrincipal, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Etiqueta y campo de texto para el nombre
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelPrincipal.add(new JLabel("Nombre:"), gbc);

        txtNombre = new JTextField(20);
        gbc.gridx = 1;
        panelPrincipal.add(txtNombre, gbc);

        // Etiqueta y campo de texto para el DNI
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelPrincipal.add(new JLabel("DNI:"), gbc);

        txtDNI = new JTextField(20);
        gbc.gridx = 1;
        panelPrincipal.add(txtDNI, gbc);

        // Etiqueta y campo de texto para el teléfono
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelPrincipal.add(new JLabel("Teléfono:"), gbc);

        txtTelefono = new JTextField(20);
        gbc.gridx = 1;
        panelPrincipal.add(txtTelefono, gbc);

        // Etiqueta y campo de texto para el email
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelPrincipal.add(new JLabel("Email:"), gbc);

        txtEmail = new JTextField(20);
        gbc.gridx = 1;
        panelPrincipal.add(txtEmail, gbc);

        // Panel para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        getContentPane().add(panelBotones, BorderLayout.SOUTH);

        btnCrearAccionista = new JButton("Crear Accionista");
        panelBotones.add(btnCrearAccionista);

        btnLimpiarCampos = new JButton("Limpiar Campos");
        panelBotones.add(btnLimpiarCampos);
    }

    /**
     * Obtiene los datos ingresados en el formulario y crea un objeto Accionista.
     *
     * @return Un objeto Accionista con los datos ingresados, o null si hay campos obligatorios vacíos.
     */
    public AccionistaDTO obtenerDatosAccionista() {
        String nombre = txtNombre.getText().trim();
        String dni = txtDNI.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String email = txtEmail.getText().trim();

        // Validar campos obligatorios
        if (nombre.isEmpty() || dni.isEmpty()) {
            mostrarMensajeError("Los campos Nombre y DNI son obligatorios.");
            return null;
        }

        AccionistaDTO accionista = new AccionistaDTO();
        accionista.setNombre(nombre);
        accionista.setDni(dni);
        accionista.setTelefono(telefono);
        accionista.setEmail(email);

        return accionista;
    }

    /**
     * Limpia los campos del formulario.
     */
    public void limpiarCampos() {
        txtNombre.setText("");
        txtDNI.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
    }

    /**
     * Muestra un mensaje de éxito al usuario.
     *
     * @param mensaje El mensaje a mostrar.
     */
    public void mostrarMensajeExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
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
     * Agrega un ActionListener al botón de crear accionista.
     *
     * @param listener El ActionListener a agregar.
     */
    public void agregarListenerCrearAccionista(ActionListener listener) {
        btnCrearAccionista.addActionListener(listener);
    }

    /**
     * Agrega un ActionListener al botón de limpiar campos.
     *
     * @param listener El ActionListener a agregar.
     */
    public void agregarListenerLimpiarCampos(ActionListener listener) {
        btnLimpiarCampos.addActionListener(listener);
    }
}
