package uo276255.vista;

import javax.swing.*;
import java.awt.*;
import uo276255.modelo.empleado.EmpleadoModel;
import uo276255.util.Database;
import uo276255.controaldor.empleado.EmpleadoController;
import uo276255.vista.empleado.AgregarEmpleadoVista;
import uo276255.vista.empleado.EliminarEmpleadoVista;
import uo276255.vista.horario.AgregarHorarioVista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

/**
 * Clase que representa la ventana principal del sistema de gestión de empleados.
 */
public class VentanaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    private JButton btnAñadirEmpleado;
    private JButton btnModificarEliminarEmpleado;
    private JButton btnAgregarHorario; // Nuevo botón para agregar horarios

    /**
     * Constructor que inicializa la interfaz gráfica principal.
     */
    public VentanaPrincipal() {
        setTitle("Gestión de Empleados");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 400); // Aumentamos el ancho y alto para acomodar el nuevo diseño
        setResizable(false);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout(10, 10));

        // Panel Título
        JPanel panelTitulo = new JPanel();
        JLabel titulo = new JLabel("Sistema de Gestión de Empleados", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(0, 102, 204));
        panelTitulo.setBackground(new Color(230, 230, 250));
        panelTitulo.add(titulo);
        add(panelTitulo, BorderLayout.NORTH);

        // Panel Botones
        JPanel panelBotones = new JPanel(new GridLayout(2, 2, 20, 20)); // Cambiamos a GridLayout de 2 filas y 2 columnas
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelBotones.setBackground(new Color(245, 245, 245));

        btnAñadirEmpleado = new JButton("Añadir Empleado");
        btnModificarEliminarEmpleado = new JButton("Modificar/Borrar Empleado");
        btnAgregarHorario = new JButton("Agregar Horario"); // Inicializamos el nuevo botón

        Font buttonFont = new Font("Arial", Font.PLAIN, 16);
        btnAñadirEmpleado.setFont(buttonFont);
        btnModificarEliminarEmpleado.setFont(buttonFont);
        btnAgregarHorario.setFont(buttonFont); // Establecemos la fuente del nuevo botón

        // Estilos de los botones
        btnAñadirEmpleado.setBackground(new Color(51, 153, 255));
        btnAñadirEmpleado.setForeground(Color.WHITE);
        btnModificarEliminarEmpleado.setBackground(new Color(255, 102, 102));
        btnModificarEliminarEmpleado.setForeground(Color.WHITE);
        btnAgregarHorario.setBackground(new Color(102, 204, 102)); // Establecemos el color del nuevo botón
        btnAgregarHorario.setForeground(Color.WHITE);

        // Efectos de hover para los botones
        btnAñadirEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAñadirEmpleado.setBackground(new Color(0, 123, 255));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAñadirEmpleado.setBackground(new Color(51, 153, 255));
            }
        });

        btnModificarEliminarEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnModificarEliminarEmpleado.setBackground(new Color(255, 77, 77));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnModificarEliminarEmpleado.setBackground(new Color(255, 102, 102));
            }
        });

        btnAgregarHorario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAgregarHorario.setBackground(new Color(76, 175, 80));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAgregarHorario.setBackground(new Color(102, 204, 102));
            }
        });

        panelBotones.add(btnAñadirEmpleado);
        panelBotones.add(btnModificarEliminarEmpleado);
        panelBotones.add(btnAgregarHorario); // Añadimos el nuevo botón al panel

        add(panelBotones, BorderLayout.CENTER);

        // Panel Inferior
        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(new Color(230, 230, 250));
        JLabel lblFooter = new JLabel("© 2024 Empresa de Gestión de Empleados");
        lblFooter.setFont(new Font("Arial", Font.ITALIC, 12));
        panelInferior.add(lblFooter);
        add(panelInferior, BorderLayout.SOUTH);

        // Action Listeners
        btnAñadirEmpleado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirFormularioAñadirEmpleado();
            }
        });

        btnModificarEliminarEmpleado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirVistaModificarEliminarEmpleado();
            }
        });

        btnAgregarHorario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirFormularioAgregarHorario();
            }
        });
    }

    /**
     * Método para abrir el formulario de añadir empleado y deshabilitar el botón hasta que la ventana se cierre.
     */
    private void abrirFormularioAñadirEmpleado() {
        btnAñadirEmpleado.setEnabled(false);
        AgregarEmpleadoVista agregarVista = new AgregarEmpleadoVista();
        EmpleadoModel modelo = new EmpleadoModel();
        Connection conn = Database.getInstance().getConnection();
        EmpleadoController controlador = new EmpleadoController(agregarVista, modelo, conn);
        agregarVista.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                btnAñadirEmpleado.setEnabled(true);
            }
        });

        agregarVista.setVisible(true);
    }

    /**
     * Método para abrir la vista de modificar o eliminar empleado y deshabilitar el botón hasta que la ventana se cierre.
     */
    private void abrirVistaModificarEliminarEmpleado() {
        btnModificarEliminarEmpleado.setEnabled(false);
        EliminarEmpleadoVista modificarEliminarVista = new EliminarEmpleadoVista();
        EmpleadoModel modelo = new EmpleadoModel();
        Connection conn = Database.getInstance().getConnection();
        EmpleadoController controlador = new EmpleadoController(modificarEliminarVista, modelo, conn);
        modificarEliminarVista.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                btnModificarEliminarEmpleado.setEnabled(true);
            }
        });

        modificarEliminarVista.setVisible(true);
    }

    /**
     * Método para abrir el formulario de agregar horario y deshabilitar el botón hasta que la ventana se cierre.
     */
    private void abrirFormularioAgregarHorario() {
        btnAgregarHorario.setEnabled(false);
        AgregarHorarioVista agregarHorarioVista = new AgregarHorarioVista();
        EmpleadoModel modelo = new EmpleadoModel();
        Connection conn = Database.getInstance().getConnection();
        EmpleadoController controlador = new EmpleadoController(agregarHorarioVista, modelo, conn);
        agregarHorarioVista.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                btnAgregarHorario.setEnabled(true);
            }
        });

        agregarHorarioVista.setVisible(true);
    }

    /**
     * Método principal para ejecutar la aplicación.
     *
     * @param args Argumentos del programa.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
                ventanaPrincipal.setVisible(true);
            }
        });
    }
}
