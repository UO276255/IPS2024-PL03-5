package uo276255.vista.compra;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ComprarProductosView extends JFrame {
    private JTable tablaProductos;
    private JTable tablaCarrito;
    private JLabel lblTotal;
    private JButton btnAgregarAlCarrito;
    private JButton btnEliminarDelCarrito;
    private JButton btnFinalizarCompra;

    // Agregamos modelos de tabla para evitar NullPointerException
    private DefaultTableModel modeloTablaProductos;
    private DefaultTableModel modeloTablaCarrito;

    public ComprarProductosView() {
        setTitle("Tienda");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cambiado a DISPOSE para cerrar solo esta ventana
        setBounds(100, 100, 800, 600);
        getContentPane().setLayout(new BorderLayout());

        // Inicializar modelos de tabla
        modeloTablaProductos = new DefaultTableModel(new Object[]{"Nombre", "Tipo", "Precio"}, 0);
        modeloTablaCarrito = new DefaultTableModel(new Object[]{"Nombre", "Cantidad", "Precio Total"}, 0);

        // Panel de Productos
        JPanel panelProductos = new JPanel(new BorderLayout());
        panelProductos.setBorder(BorderFactory.createTitledBorder("Productos Disponibles"));

        tablaProductos = new JTable(modeloTablaProductos);
        JScrollPane scrollProductos = new JScrollPane(tablaProductos);
        panelProductos.add(scrollProductos, BorderLayout.CENTER);

        btnAgregarAlCarrito = new JButton("Agregar al Carrito");
        panelProductos.add(btnAgregarAlCarrito, BorderLayout.SOUTH);

        // Panel de Carrito
        JPanel panelCarrito = new JPanel(new BorderLayout());
        panelCarrito.setBorder(BorderFactory.createTitledBorder("Carrito de Compras"));

        tablaCarrito = new JTable(modeloTablaCarrito);
        JScrollPane scrollCarrito = new JScrollPane(tablaCarrito);
        panelCarrito.add(scrollCarrito, BorderLayout.CENTER);

        btnEliminarDelCarrito = new JButton("Eliminar del Carrito");
        panelCarrito.add(btnEliminarDelCarrito, BorderLayout.SOUTH);

        // Panel Inferior
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblTotal = new JLabel("Total: $0.00");
        btnFinalizarCompra = new JButton("Finalizar Compra");
        panelInferior.add(lblTotal);
        panelInferior.add(btnFinalizarCompra);

        // Agregar Paneles al Frame
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelProductos, panelCarrito);
        getContentPane().add(splitPane, BorderLayout.CENTER);
        getContentPane().add(panelInferior, BorderLayout.SOUTH);
    }

    // Métodos para acceder a los componentes desde el controlador
    public JTable getTablaProductos() { return tablaProductos; }
    public JTable getTablaCarrito() { return tablaCarrito; }
    public JLabel getLblTotal() { return lblTotal; }
    public JButton getBtnAgregarAlCarrito() { return btnAgregarAlCarrito; }
    public JButton getBtnEliminarDelCarrito() { return btnEliminarDelCarrito; }
    public JButton getBtnFinalizarCompra() { return btnFinalizarCompra; }

    // Métodos para acceder a los modelos de tabla
    public DefaultTableModel getModeloTablaProductos() { return modeloTablaProductos; }
    public DefaultTableModel getModeloTablaCarrito() { return modeloTablaCarrito; }
}
