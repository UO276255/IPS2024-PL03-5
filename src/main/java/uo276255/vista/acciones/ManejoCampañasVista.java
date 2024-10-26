package uo276255.vista.acciones;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Clase que representa la vista para el manejo de campañas.
 */
public class ManejoCampañasVista extends JFrame {

    private static final long serialVersionUID = 1L;
    private JButton btnCrearCampaña;
    private JButton btnAumentarFase;
    private boolean campañaExistente;

    /**
     * Constructor que inicializa la interfaz gráfica para el manejo de campañas.
     */
    public ManejoCampañasVista() {
        setTitle("Manejo de Campañas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font fontButton = new Font("Arial", Font.BOLD, 18);

        btnCrearCampaña = new JButton("Crear Campaña");
        btnCrearCampaña.setFont(fontButton);
        btnCrearCampaña.setBackground(new Color(0, 153, 204));
        btnCrearCampaña.setForeground(Color.WHITE);
        btnCrearCampaña.setFocusPainted(false);
        btnCrearCampaña.setPreferredSize(new Dimension(200, 50));
        btnCrearCampaña.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCrearCampaña.setBackground(new Color(0, 123, 180));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCrearCampaña.setBackground(new Color(0, 153, 204));
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(btnCrearCampaña, gbc);

        btnAumentarFase = new JButton("Aumentar Fase");
        btnAumentarFase.setFont(fontButton);
        btnAumentarFase.setBackground(new Color(0, 153, 204));
        btnAumentarFase.setForeground(Color.WHITE);
        btnAumentarFase.setFocusPainted(false);
        btnAumentarFase.setPreferredSize(new Dimension(200, 50));
        btnAumentarFase.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAumentarFase.setBackground(new Color(0, 123, 180));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAumentarFase.setBackground(new Color(0, 153, 204));
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(btnAumentarFase, gbc);

        getContentPane().add(panel);

        pack();

        // Inicialmente, actualizar los botones según la existencia de campaña
        actualizarEstadoBotones();
    }

    /**
     * Establece si existe una campaña en la base de datos.
     *
     * @param existe true si existe una campaña, false en caso contrario.
     */
    public void setCampañaExistente(boolean existe) {
        this.campañaExistente = existe;
        actualizarEstadoBotones();
    }

    /**
     * Actualiza el estado de los botones según la existencia de una campaña.
     */
    private void actualizarEstadoBotones() {
        if (campañaExistente) {
            btnCrearCampaña.setEnabled(false);
            btnAumentarFase.setEnabled(true);
        } else {
            btnCrearCampaña.setEnabled(true);
            btnAumentarFase.setEnabled(false);
        }
    }

    /**
     * Agrega un ActionListener al botón de crear campaña.
     *
     * @param actionListener El ActionListener que se ejecutará cuando se presione el botón.
     */
    public void agregarListenerCrearCampaña(ActionListener actionListener) {
        btnCrearCampaña.addActionListener(actionListener);
    }

    /**
     * Agrega un ActionListener al botón de aumentar fase.
     *
     * @param actionListener El ActionListener que se ejecutará cuando se presione el botón.
     */
    public void agregarListenerAumentarFase(ActionListener actionListener) {
        btnAumentarFase.addActionListener(actionListener);
    }

    /**
     * Muestra un mensaje de información en un cuadro de diálogo.
     *
     * @param mensaje El mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Muestra un cuadro de diálogo de confirmación.
     *
     * @param mensaje El mensaje a mostrar en el cuadro de diálogo.
     * @return La opción seleccionada por el usuario.
     */
    public int mostrarConfirmacion(String mensaje) {
        return JOptionPane.showConfirmDialog(this, mensaje, "Confirmación", JOptionPane.YES_NO_OPTION);
    }
}
