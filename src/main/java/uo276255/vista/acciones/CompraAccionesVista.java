package uo276255.vista.acciones;

import uo276255.modelo.accionistas.AccionistaDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Clase que representa la vista para la compra de acciones.
 */
public class CompraAccionesVista extends JFrame {

    private JTextField txtDni;
    private JTextField txtCantidadAcciones;
    private JTextField txtNombre;
    private JTextField txtTelefono;
    private JTextField txtEmail;
    private JButton btnComprarAcciones;

    /**
     * Constructor de la vista de compra de acciones.
     */
    public CompraAccionesVista() {
        inicializarComponentes();
    }

    /**
     * Inicializa los componentes de la interfaz gráfica.
     */
    private void inicializarComponentes() {
        setTitle("Compra de Acciones");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 450);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        // Panel superior con título
        JLabel lblTitulo = new JLabel("Compra de Acciones", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        getContentPane().add(lblTitulo, BorderLayout.NORTH);

        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridBagLayout());
        getContentPane().add(panelPrincipal, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Panel para datos de compra
        JPanel panelCompra = new JPanel(new GridBagLayout());
        panelCompra.setBorder(BorderFactory.createTitledBorder("Datos de Compra"));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panelCompra.add(new JLabel("DNI:"), gbc);

        txtDni = new JTextField(20);
        gbc.gridx = 1;
        panelCompra.add(txtDni, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelCompra.add(new JLabel("Cantidad de Acciones:"), gbc);

        txtCantidadAcciones = new JTextField(20);
        gbc.gridx = 1;
        panelCompra.add(txtCantidadAcciones, gbc);

        // Panel para datos personales (Fase 3)
        JPanel panelDatosPersonales = new JPanel(new GridBagLayout());
        panelDatosPersonales.setBorder(BorderFactory.createTitledBorder("Datos Personales (Fase 3)"));

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelDatosPersonales.add(new JLabel("Nombre:"), gbc);

        txtNombre = new JTextField(20);
        gbc.gridx = 1;
        panelDatosPersonales.add(txtNombre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelDatosPersonales.add(new JLabel("Teléfono:"), gbc);

        txtTelefono = new JTextField(20);
        gbc.gridx = 1;
        panelDatosPersonales.add(txtTelefono, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelDatosPersonales.add(new JLabel("Email:"), gbc);

        txtEmail = new JTextField(20);
        gbc.gridx = 1;
        panelDatosPersonales.add(txtEmail, gbc);

        // Añadir paneles al panel principal
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelPrincipal.add(panelCompra, gbc);

        gbc.gridy = 1;
        panelPrincipal.add(panelDatosPersonales, gbc);

        // Botón para comprar acciones
        btnComprarAcciones = new JButton("Comprar Acciones");
        btnComprarAcciones.setPreferredSize(new Dimension(200, 30));

        JPanel panelBoton = new JPanel();
        panelBoton.add(btnComprarAcciones);

        getContentPane().add(panelBoton, BorderLayout.SOUTH);
    }

    /**
     * Obtiene el DNI ingresado por el usuario.
     *
     * @return El DNI ingresado.
     */
    public String getDniUsuario() {
        return txtDni.getText().trim();
    }

    /**
     * Obtiene la cantidad de acciones ingresada por el usuario.
     *
     * @return La cantidad de acciones.
     */
    public int getCantidadAcciones() {
        try {
            return Integer.parseInt(txtCantidadAcciones.getText().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Obtiene los datos del nuevo accionista en caso de fase 3.
     *
     * @return Un objeto AccionistaDTO con los datos ingresados, o null si faltan datos.
     */
    public AccionistaDTO obtenerDatosNuevoAccionista() {
        String nombre = txtNombre.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String email = txtEmail.getText().trim();
        String dni = getDniUsuario();

        if (nombre.isEmpty() || dni.isEmpty()) {
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
     * Agrega un ActionListener al botón de comprar acciones.
     *
     * @param listener El ActionListener a agregar.
     */
    public void agregarListenerComprarAcciones(ActionListener listener) {
        btnComprarAcciones.addActionListener(listener);
    }
}
