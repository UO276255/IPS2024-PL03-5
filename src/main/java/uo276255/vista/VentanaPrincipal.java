package uo276255.vista;

import javax.swing.*;
import java.awt.*;

import uo276255.controaldor.acciones.AccionesController;
import uo276255.controaldor.acciones.campaña.CampañaController;
import uo276255.controaldor.compra.CompraController;
import uo276255.controaldor.empleado.EmpleadoController;
import uo276255.controaldor.ventas.VentaController;
import uo276255.modelo.acciones.accion.AccionesModel;
import uo276255.modelo.accionistas.AccionistaModel;
import uo276255.modelo.compra.CompraModel;
import uo276255.modelo.acciones.campaña.CampañaModel;
import uo276255.modelo.empleado.EmpleadoModel;
import uo276255.modelo.productos.ProductoModel;
import uo276255.modelo.ventas.VentasModel;
import uo276255.util.Database;
import uo276255.vista.acciones.AccionesDisponiblesVista;
import uo276255.vista.acciones.ManejoCampañasVista;
import uo276255.vista.accionistas.VenderAccionesVista;
import uo276255.vista.compra.ComprarProductosView;
import uo276255.vista.empleado.AgregarEmpleadoVista;
import uo276255.vista.empleado.EliminarEmpleadoVista;
import uo276255.vista.horario.AgregarHorarioVista;
import uo276255.vista.ventas.VistaVentas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Clase que representa la ventana principal del sistema de gestión de empleados y acciones.
 */
public class VentanaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    private JButton btnAñadirEmpleado;
    private JButton btnModificarEliminarEmpleado;
    private JButton btnAgregarHorario;
    private JButton btnGestionCampañas;
    private JButton btnComprarAcciones;
    private JButton btnGestionarAccionesAccionista;
    private JButton btnComprar; 
    private JButton btnVisualizarVentas; 
    private Connection conn;

    /**
     * Constructor que inicializa la interfaz gráfica principal.
     */
    public VentanaPrincipal() {
        setTitle("Gestión de Empleados y Acciones");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 500);
        setResizable(false);
        setLocationRelativeTo(null);

        // Inicializar conexión a la base de datos
        conn = Database.getInstance().getConnection();

        setLayout(new BorderLayout(10, 10));

        // Panel Título
        JPanel panelTitulo = new JPanel();
        JLabel titulo = new JLabel("Sistema de Gestión de Empleados y Acciones", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(0, 102, 204));
        panelTitulo.setBackground(new Color(230, 230, 250));
        panelTitulo.add(titulo);
        add(panelTitulo, BorderLayout.NORTH);

        // Panel Botones
        JPanel panelBotones = new JPanel(new GridLayout(3, 2, 20, 20)); // 3 filas y 2 columnas
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelBotones.setBackground(new Color(245, 245, 245));

        btnAñadirEmpleado = new JButton("Añadir Empleado");
        btnModificarEliminarEmpleado = new JButton("Modificar/Borrar Empleado");
        btnAgregarHorario = new JButton("Agregar Horario");
        btnGestionCampañas = new JButton("Gestión de campañas");
        btnComprarAcciones = new JButton("Comprar Acciones");
        btnGestionarAccionesAccionista = new JButton("Gestionar Mis Acciones"); // Nuevo botón
        btnComprar = new JButton("Comprar"); // Inicialización del nuevo botón
        btnVisualizarVentas = new JButton("Visualizar Ventas"); // Inicialización del nuevo botón
        
        Font buttonFont = new Font("Arial", Font.PLAIN, 16);
        btnAñadirEmpleado.setFont(buttonFont);
        btnModificarEliminarEmpleado.setFont(buttonFont);
        btnAgregarHorario.setFont(buttonFont);
        btnGestionCampañas.setFont(buttonFont);
        btnComprarAcciones.setFont(buttonFont);
        btnGestionarAccionesAccionista.setFont(buttonFont); // Aplicar fuente al nuevo botón
        btnComprar.setFont(buttonFont); 
        btnVisualizarVentas.setFont(buttonFont); // Aplicar fuente al nuevo botón
        
        // Estilos de los botones
        btnAñadirEmpleado.setBackground(new Color(51, 153, 255));
        btnAñadirEmpleado.setForeground(Color.WHITE);
        btnModificarEliminarEmpleado.setBackground(new Color(255, 102, 102));
        btnModificarEliminarEmpleado.setForeground(Color.WHITE);
        btnAgregarHorario.setBackground(new Color(102, 204, 102));
        btnAgregarHorario.setForeground(Color.WHITE);
        btnGestionCampañas.setBackground(new Color(255, 153, 51)); // Color naranja
        btnGestionCampañas.setForeground(Color.WHITE);
        btnComprarAcciones.setBackground(new Color(153, 102, 255)); // Color morado
        btnComprarAcciones.setForeground(Color.WHITE);
        btnGestionarAccionesAccionista.setBackground(new Color(255, 204, 0)); // Color amarillo
        btnGestionarAccionesAccionista.setForeground(Color.WHITE);
        btnComprar.setBackground(new Color(0, 204, 204)); // Color cian para el nuevo botón
        btnComprar.setForeground(Color.WHITE);
        btnVisualizarVentas.setBackground(new Color(100, 149, 237)); // Color azul cornflower
        btnVisualizarVentas.setForeground(Color.WHITE);
        // Efectos de hover para los botones
        agregarEfectoHover(btnAñadirEmpleado, new Color(0, 123, 255), new Color(51, 153, 255));
        agregarEfectoHover(btnModificarEliminarEmpleado, new Color(255, 77, 77), new Color(255, 102, 102));
        agregarEfectoHover(btnAgregarHorario, new Color(76, 175, 80), new Color(102, 204, 102));
        agregarEfectoHover(btnGestionCampañas, new Color(255, 133, 0), new Color(255, 153, 51));
        agregarEfectoHover(btnComprarAcciones, new Color(142, 68, 173), new Color(153, 102, 255));
        agregarEfectoHover(btnGestionarAccionesAccionista, new Color(255, 179, 0), new Color(255, 204, 0));
        agregarEfectoHover(btnComprar, new Color(0, 153, 153), new Color(0, 204, 204)); // Efecto hover para el nuevo botón
        agregarEfectoHover(btnVisualizarVentas, new Color(65, 105, 225), new Color(100, 149, 237)); // Efecto hover para el nuevo botón
        // Añadimos los botones al panel
        panelBotones.add(btnAñadirEmpleado);
        panelBotones.add(btnModificarEliminarEmpleado);
        panelBotones.add(btnAgregarHorario);
        panelBotones.add(btnGestionCampañas);
        panelBotones.add(btnComprarAcciones);
        panelBotones.add(btnGestionarAccionesAccionista); 
        panelBotones.add(btnComprar);
        panelBotones.add(btnVisualizarVentas);
        add(panelBotones, BorderLayout.CENTER);

        // Panel Inferior
        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(new Color(230, 230, 250));
        JLabel lblFooter = new JLabel("© 2024 Empresa de Gestión de Empleados y Acciones");
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

        btnGestionCampañas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirVistaManejoCampañas();
            }
        });

        btnComprarAcciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirVentanaAccionesDisponibles();
            }
        });

        btnGestionarAccionesAccionista.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirVentanaGestionAccionesAccionista();
            }
        });
        
        btnComprar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirVentanaComprar(); // Método para abrir la ventana de compra
            }
        });
        
        btnVisualizarVentas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirVentanaVisualizarVentas(); // Método para abrir la ventana de visualización de ventas
            }
        });
        
        // Verificar si el botón "Comprar Acciones" debe estar habilitado o no
        verificarDisponibilidadCompraAcciones();
    }

    /**
     * Agrega efecto de hover a un botón.
     *
     * @param button     El botón al que se le aplicará el efecto.
     * @param colorHover El color cuando el cursor está sobre el botón.
     * @param colorBase  El color base del botón.
     */
    private void agregarEfectoHover(JButton button, Color colorHover, Color colorBase) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(colorHover);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(colorBase);
            }
        });
    }

    /**
     * Método para verificar si hay una campaña activa y acciones en venta.
     * Si no las hay, deshabilita el botón "Comprar Acciones".
     */
    private void verificarDisponibilidadCompraAcciones() {
        try {
            AccionesModel compraAccionesModel = new AccionesModel(conn);
            boolean campañaActiva = compraAccionesModel.obtenerCampañaActiva() != null;
            boolean hayAccionesDisponibles = compraAccionesModel.hayAccionesEnVenta();

            if (!campañaActiva || !hayAccionesDisponibles) {
                btnComprarAcciones.setEnabled(false);
                btnComprarAcciones.setToolTipText("No hay campañas activas o acciones disponibles para comprar.");
            } else {
                btnComprarAcciones.setEnabled(true);
                btnComprarAcciones.setToolTipText(null);
            }
        } catch (SQLException e) {
            btnComprarAcciones.setEnabled(false);
            btnComprarAcciones.setToolTipText("Error al verificar disponibilidad de compra.");
            JOptionPane.showMessageDialog(this, "Error al verificar disponibilidad de compra: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método para abrir el formulario de añadir empleado y deshabilitar el botón hasta que la ventana se cierre.
     */
    private void abrirFormularioAñadirEmpleado() {
        btnAñadirEmpleado.setEnabled(false);
        AgregarEmpleadoVista agregarVista = new AgregarEmpleadoVista();
        EmpleadoModel modelo = new EmpleadoModel();
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
     * Método para abrir la vista intermedia de manejo de campañas y deshabilitar el botón hasta que la ventana se cierre.
     */
    private void abrirVistaManejoCampañas() {
        btnGestionCampañas.setEnabled(false);
        CampañaModel modelo = new CampañaModel(conn);
        ManejoCampañasVista vista = new ManejoCampañasVista();
        AccionistaModel ac = new AccionistaModel(conn);
        CampañaController controlador = new CampañaController(vista, modelo, ac);
        vista.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                btnGestionCampañas.setEnabled(true);
                // Actualizar disponibilidad del botón "Comprar Acciones" al cerrar la ventana
                verificarDisponibilidadCompraAcciones();
            }
        });

        vista.setVisible(true);
    }

    /**
     * Método para abrir el formulario de comprar acciones y deshabilitar el botón hasta que la ventana se cierre.
     */
    private void abrirFormularioComprarAcciones() {
        btnComprarAcciones.setEnabled(false);
        AccionesDisponiblesVista compraAccionesVista = new AccionesDisponiblesVista();
        AccionesModel compraAccionesModel = new AccionesModel(conn);
        AccionesController compraAccionesController = new AccionesController(compraAccionesVista, compraAccionesModel);
        compraAccionesVista.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                btnComprarAcciones.setEnabled(true);
                verificarDisponibilidadCompraAcciones();
            }
        });

        compraAccionesVista.setVisible(true);
    }

    /**
     * Método para abrir la ventana de acciones disponibles.
     */
    private void abrirVentanaAccionesDisponibles() {
        btnComprarAcciones.setEnabled(false);
        AccionesDisponiblesVista accionesVista = new AccionesDisponiblesVista();
        AccionesModel compraAccionesModel = new AccionesModel(conn);
        AccionesController accionesController = new AccionesController(accionesVista, compraAccionesModel);
        accionesVista.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                btnComprarAcciones.setEnabled(true);
                verificarDisponibilidadCompraAcciones();
            }
        });

        accionesVista.setVisible(true);
    }

    /**
     * Método para abrir la ventana donde el accionista puede gestionar sus acciones.
     */
    private void abrirVentanaGestionAccionesAccionista() {
        btnGestionarAccionesAccionista.setEnabled(false);
        VenderAccionesVista accionesVista = new VenderAccionesVista();
        AccionistaModel accionistaModel = new AccionistaModel(conn);
        AccionesModel accionesModel = new AccionesModel(conn);
        AccionesController accionesController = new AccionesController(accionesVista, accionistaModel, accionesModel);
        accionesVista.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                btnGestionarAccionesAccionista.setEnabled(true);
            }
        });

        accionesVista.setVisible(true);
    }
    
    /**
     * Método para abrir la ventana de compra y deshabilitar el botón hasta que la ventana se cierre.
     */
    private void abrirVentanaComprar() {
        btnComprar.setEnabled(false);
        // Aquí debes crear la vista y el controlador de la ventana de compra
        // Por ejemplo:
        ComprarProductosView tiendaVista = new ComprarProductosView();
        ProductoModel productoDAO = new ProductoModel(conn);
        CompraModel compraDAO = new CompraModel(conn);
        CompraController tiendaController = new CompraController(tiendaVista, productoDAO,compraDAO);
        tiendaVista.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                btnComprar.setEnabled(true);
            }
        });

        tiendaVista.setVisible(true);
    }
    
    private void abrirVentanaVisualizarVentas() {
        btnVisualizarVentas.setEnabled(false);
        // Crear la vista y el controlador de ventas
        VistaVentas vistaVentas = new VistaVentas();
        VentasModel ventaDAO = new VentasModel(conn);
        CompraModel compraModel = new CompraModel(conn);
        VentaController ventaController = new VentaController(ventaDAO, compraModel, vistaVentas);
        vistaVentas.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                btnVisualizarVentas.setEnabled(true);
            }
        });

        vistaVentas.setVisible(true);
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
