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
        setBounds(100, 100, 600, 300); // Aumentamos el ancho para acomodar el nuevo botón
        setResizable(false);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout(10, 10));

        JPanel panelTitulo = new JPanel();
        JLabel titulo = new JLabel("Sistema de Gestión de Empleados", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(new Color(0, 102, 204));
        panelTitulo.add(titulo);
        add(panelTitulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new GridLayout(1, 3, 20, 10)); // Cambiamos a GridLayout de 1 fila y 3 columnas
        btnAñadirEmpleado = new JButton("Añadir Empleado");
        btnModificarEliminarEmpleado = new JButton("Modificar/Borrar Empleado");
        btnAgregarHorario = new JButton("Agregar Horario"); // Inicializamos el nuevo botón

        Font buttonFont = new Font("Arial", Font.PLAIN, 16);
        btnAñadirEmpleado.setFont(buttonFont);
        btnModificarEliminarEmpleado.setFont(buttonFont);
        btnAgregarHorario.setFont(buttonFont); // Establecemos la fuente del nuevo botón

        btnAñadirEmpleado.setBackground(new Color(51, 153, 255));
        btnAñadirEmpleado.setForeground(Color.WHITE);
        btnModificarEliminarEmpleado.setBackground(new Color(255, 102, 102));
        btnModificarEliminarEmpleado.setForeground(Color.WHITE);
        btnAgregarHorario.setBackground(new Color(102, 204, 102)); // Establecemos el color del nuevo botón
        btnAgregarHorario.setForeground(Color.WHITE);

        panelBotones.add(btnAñadirEmpleado);
        panelBotones.add(btnModificarEliminarEmpleado);
        panelBotones.add(btnAgregarHorario); // Añadimos el nuevo botón al panel

        add(panelBotones, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel();
        add(panelInferior, BorderLayout.SOUTH);

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

        // Añadimos el ActionListener para el nuevo botón
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
