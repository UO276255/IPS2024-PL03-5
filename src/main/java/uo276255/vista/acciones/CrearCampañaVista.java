package uo276255.vista.acciones;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Clase que representa la vista para crear una campaña.
 */
public class CrearCampañaVista extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField textNombre;
    private JTextField textMaximoAcciones;
    private JButton btnCrear;

    /**
     * Constructor que inicializa la interfaz gráfica para crear una campaña.
     */
    public CrearCampañaVista() {
        setTitle("Crear Campaña");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        panel.setBackground(new Color(245, 245, 245));

        // Constraints para GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fuentes
        Font fontTitulo = new Font("Arial", Font.BOLD, 22);
        Font fontLabel = new Font("Arial", Font.BOLD, 16);
        Font fontField = new Font("Arial", Font.PLAIN, 16);

        // Título
        JLabel lblTitulo = new JLabel("Crear Nueva Campaña", JLabel.CENTER);
        lblTitulo.setFont(fontTitulo);
        lblTitulo.setForeground(new Color(0, 102, 204));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 20, 10);
        panel.add(lblTitulo, gbc);

        // Restablecer gridwidth y insets
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Etiqueta y campo de texto para "Nombre"
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(fontLabel);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0;
        panel.add(lblNombre, gbc);

        textNombre = new JTextField();
        textNombre.setFont(fontField);
        textNombre.setPreferredSize(new Dimension(250, 30)); // Ajuste del tamaño preferido
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1;
        panel.add(textNombre, gbc);

        // Etiqueta y campo de texto para "Máximo de Acciones"
        JLabel lblMaximoAcciones = new JLabel("Máximo de Acciones:");
        lblMaximoAcciones.setFont(fontLabel);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0;
        panel.add(lblMaximoAcciones, gbc);

        textMaximoAcciones = new JTextField();
        textMaximoAcciones.setFont(fontField);
        textMaximoAcciones.setPreferredSize(new Dimension(250, 30)); // Ajuste del tamaño preferido
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1;
        panel.add(textMaximoAcciones, gbc);

        // Botón "Crear"
        btnCrear = new JButton("Crear");
        btnCrear.setFont(new Font("Arial", Font.BOLD, 18));
        btnCrear.setBackground(new Color(0, 153, 204));
        btnCrear.setForeground(Color.WHITE);
        btnCrear.setFocusPainted(false);
        btnCrear.setPreferredSize(new Dimension(150, 40));
        btnCrear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCrear.setBackground(new Color(0, 123, 180));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCrear.setBackground(new Color(0, 153, 204));
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0;
        gbc.insets = new Insets(20, 10, 10, 10);
        panel.add(btnCrear, gbc);

        getContentPane().add(panel);

        pack();
        setMinimumSize(new Dimension(500, 300));
    }

    /**
     * Obtiene el nombre de la campaña ingresado.
     *
     * @return Nombre de la campaña.
     */
    public String getNombre() {
        return textNombre.getText().trim();
    }

    /**
     * Obtiene el máximo de acciones disponibles ingresado.
     *
     * @return Máximo de acciones disponibles, o 0 si el formato es incorrecto.
     */
    public int getMaximoAcciones() {
        try {
            return Integer.parseInt(textMaximoAcciones.getText().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Agrega un ActionListener al botón de crear.
     *
     * @param actionListener El ActionListener que se ejecutará cuando se presione el botón.
     */
    public void agregarListenerCrear(ActionListener actionListener) {
        btnCrear.addActionListener(actionListener);
    }

    /**
     * Muestra un mensaje de éxito en un cuadro de diálogo y cierra la ventana.
     *
     * @param mensaje El mensaje de éxito a mostrar.
     */
    public void mostrarMensajeExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        dispose();
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
