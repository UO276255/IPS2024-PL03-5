package uo276255.vista.ventas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.awt.*;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import uo276255.modelo.compra.Compra;
import uo276255.modelo.compra.CompraDetalle;
import uo276255.modelo.ventas.VentaDTO;

public class VistaVentas extends JFrame {
    private JPanel contentPane;
    private JDatePickerImpl datePickerInicio;
    private JDatePickerImpl datePickerFin;
    private JButton btnBuscar;
    private JButton btnGenerarFacturas; // Añadido
    private JTable tablaVentas;
    private JTable tablaCompras;
    private JTable tablaDetallesCompra;
    private JLabel lblTotalIngresos;
    private JLabel lblTotalCompras;

    private DefaultTableModel modeloVentas;
    private DefaultTableModel modeloCompras;
    private DefaultTableModel modeloDetallesCompra;

    // Listas para almacenar las ventas y compras
    private List<VentaDTO> listaVentas;
    private List<Compra> listaCompras;

    @SuppressWarnings("serial")
    public VistaVentas() {
        setTitle("Historial de Ventas y Compras");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 1000, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout(10, 10));
        setContentPane(contentPane);

        // Panel Superior
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new FlowLayout(FlowLayout.LEFT));
        contentPane.add(panelSuperior, BorderLayout.NORTH);

        JLabel lblFechaInicio = new JLabel("Fecha Inicio:");
        panelSuperior.add(lblFechaInicio);

        // Configurar el JDatePicker para Fecha Inicio
        UtilDateModel modelInicio = new UtilDateModel();
        Properties pInicio = new Properties();
        pInicio.put("text.today", "Hoy");
        pInicio.put("text.month", "Mes");
        pInicio.put("text.year", "Año");
        JDatePanelImpl datePanelInicio = new JDatePanelImpl(modelInicio, pInicio);
        datePickerInicio = new JDatePickerImpl(datePanelInicio, new DateLabelFormatter());
        panelSuperior.add(datePickerInicio);

        JLabel lblFechaFin = new JLabel("Fecha Fin:");
        panelSuperior.add(lblFechaFin);

        // Configurar el JDatePicker para Fecha Fin
        UtilDateModel modelFin = new UtilDateModel();
        Properties pFin = new Properties();
        pFin.put("text.today", "Hoy");
        pFin.put("text.month", "Mes");
        pFin.put("text.year", "Año");
        JDatePanelImpl datePanelFin = new JDatePanelImpl(modelFin, pFin);
        datePickerFin = new JDatePickerImpl(datePanelFin, new DateLabelFormatter());
        panelSuperior.add(datePickerFin);

        btnBuscar = new JButton("Buscar");
        panelSuperior.add(btnBuscar);

        // **Añadir el Botón "Generar Facturas"**
        btnGenerarFacturas = new JButton("Generar Facturas");
        panelSuperior.add(btnGenerarFacturas);

        // Panel Central
        JPanel panelCentral = new JPanel(new GridLayout(1, 2, 10, 10));
        contentPane.add(panelCentral, BorderLayout.CENTER);

        // Panel Ventas
        JPanel panelVentas = new JPanel(new BorderLayout(10, 10));
        panelCentral.add(panelVentas);

        JLabel lblVentas = new JLabel("Listado de Ventas");
        lblVentas.setFont(new Font("Tahoma", Font.BOLD, 14));
        panelVentas.add(lblVentas, BorderLayout.NORTH);

        JScrollPane scrollPaneVentas = new JScrollPane();
        panelVentas.add(scrollPaneVentas, BorderLayout.CENTER);

        tablaVentas = new JTable();
        modeloVentas = new DefaultTableModel(
                new Object[][] {},
                new String[] {"ID Venta", "Concepto", "Fecha y Hora", "Cuantía Total"}
        ) {
            boolean[] columnEditables = new boolean[] {false, false, false, false};

            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        };
        tablaVentas.setModel(modeloVentas);
        // Ocultar columna ID Venta (opcional)
        tablaVentas.getColumnModel().getColumn(0).setMinWidth(0);
        tablaVentas.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaVentas.getColumnModel().getColumn(0).setWidth(0);

        scrollPaneVentas.setViewportView(tablaVentas);

        // Panel Compras
        JPanel panelCompras = new JPanel(new BorderLayout(10, 10));
        panelCentral.add(panelCompras);

        JLabel lblCompras = new JLabel("Listado de Compras");
        lblCompras.setFont(new Font("Tahoma", Font.BOLD, 14));
        panelCompras.add(lblCompras, BorderLayout.NORTH);

        JScrollPane scrollPaneCompras = new JScrollPane();
        panelCompras.add(scrollPaneCompras, BorderLayout.CENTER);

        tablaCompras = new JTable();
        modeloCompras = new DefaultTableModel(
                new Object[][] {},
                new String[] {"ID Compra", "Fecha y Hora", "Total"}
        ) {
            boolean[] columnEditables = new boolean[] {false, false, false};

            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        };
        tablaCompras.setModel(modeloCompras);
        // Ocultar columna ID Compra (opcional)
        tablaCompras.getColumnModel().getColumn(0).setMinWidth(0);
        tablaCompras.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaCompras.getColumnModel().getColumn(0).setWidth(0);

        scrollPaneCompras.setViewportView(tablaCompras);

        // Panel Inferior (Solo Detalles de Compras)
        JPanel panelInferior = new JPanel(new BorderLayout(10, 10));
        contentPane.add(panelInferior, BorderLayout.SOUTH);

        lblTotalCompras = new JLabel("Total de Compras: 0.00");
        lblTotalCompras.setFont(new Font("Tahoma", Font.BOLD, 14));
        panelInferior.add(lblTotalCompras, BorderLayout.NORTH);

        JLabel lblDetallesCompra = new JLabel("Detalles de la Compra Seleccionada");
        lblDetallesCompra.setFont(new Font("Tahoma", Font.BOLD, 14));
        panelInferior.add(lblDetallesCompra, BorderLayout.CENTER);

        JScrollPane scrollPaneDetallesCompra = new JScrollPane();
        panelInferior.add(scrollPaneDetallesCompra, BorderLayout.SOUTH);

        tablaDetallesCompra = new JTable();
        modeloDetallesCompra = new DefaultTableModel(
                new Object[][] {},
                new String[] {"Producto", "Cantidad", "Precio Unitario", "Precio Total"}
        ) {
            boolean[] columnEditables = new boolean[] {false, false, false, false};

            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        };
        tablaDetallesCompra.setModel(modeloDetallesCompra);
        scrollPaneDetallesCompra.setViewportView(tablaDetallesCompra);
    }

    // Métodos para acceder a los componentes desde el controlador

    public Date getFechaInicio() {
        Object value = datePickerInicio.getModel().getValue();
        if (value != null) {
            if (value instanceof Calendar) {
                return ((Calendar) value).getTime();
            } else if (value instanceof Date) {
                return (Date) value;
            } else {
                JOptionPane.showMessageDialog(this, "Tipo de fecha de inicio desconocido.", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una fecha de inicio.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public Date getFechaFin() {
        Object value = datePickerFin.getModel().getValue();
        if (value != null) {
            if (value instanceof Calendar) {
                return ((Calendar) value).getTime();
            } else if (value instanceof Date) {
                return (Date) value;
            } else {
                JOptionPane.showMessageDialog(this, "Tipo de fecha de fin desconocido.", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una fecha de fin.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnGenerarFacturas() {
        return btnGenerarFacturas;
    }

    public JTable getTablaVentas() {
        return tablaVentas;
    }

    public JTable getTablaCompras() {
        return tablaCompras;
    }

    public void agregarListenerTablaVentas(ListSelectionListener listener) {
        tablaVentas.getSelectionModel().addListSelectionListener(listener);
    }

    public void agregarListenerTablaCompras(ListSelectionListener listener) {
        tablaCompras.getSelectionModel().addListSelectionListener(listener);
    }

    public void mostrarVentas(List<VentaDTO> ventas) {
        this.listaVentas = ventas;
        modeloVentas.setRowCount(0); // Limpiar la tabla
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        for (VentaDTO venta : ventas) {
            Object[] fila = {
                venta.getIdVenta(),
                venta.getTipo(),
                formatoFecha.format(venta.getFecha()),
                venta.getTotal()
            };
            modeloVentas.addRow(fila);
        }
    }

    public void mostrarCompras(List<Compra> compras) {
        this.listaCompras = compras;
        modeloCompras.setRowCount(0);
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        for (Compra compra : compras) {
            Object[] fila = {
                compra.getIdCompra(),
                formatoFecha.format(compra.getFecha()),
                compra.getTotal()
            };
            modeloCompras.addRow(fila);
        }
    }

    public void mostrarTotalCompras(BigDecimal totalCompras) {
        lblTotalCompras.setText("Total de Compras: " + totalCompras.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
    }

    public VentaDTO getVentaEnFila(int fila) {
        if (listaVentas != null && fila >= 0 && fila < listaVentas.size()) {
            return listaVentas.get(fila);
        } else {
            return null;
        }
    }

    public Compra getCompraEnFila(int fila) {
        if (listaCompras != null && fila >= 0 && fila < listaCompras.size()) {
            return listaCompras.get(fila);
        } else {
            return null;
        }
    }

    public void mostrarDetallesCompra(List<CompraDetalle> detalles) {
        modeloDetallesCompra.setRowCount(0); // Limpiar la tabla de detalles de compras
        for (CompraDetalle detalle : detalles) {
            Object[] fila = {
                detalle.getProducto().getNombre(),
                detalle.getCantidad(),
                detalle.getPrecioUnitario(),
                detalle.getPrecioTotal()
            };
            modeloDetallesCompra.addRow(fila);
        }
    }

    public void limpiarDetallesCompra() {
        modeloDetallesCompra.setRowCount(0);
    }

    // Métodos para agregar listeners en el controlador
    public void agregarListenerBtnBuscar(ActionListener listener) {
        btnBuscar.addActionListener(listener);
    }

    public void agregarListenerBtnGenerarFacturas(ActionListener listener) {
        btnGenerarFacturas.addActionListener(listener);
    }

    // Clase interna para formatear la fecha en el JDatePicker
    private class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

        private String datePattern = "dd/MM/yyyy";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            if (text == null || text.trim().isEmpty()) {
                return null;
            }
            Date date = dateFormatter.parse(text);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal;
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                if (value instanceof Calendar) {
                    Calendar cal = (Calendar) value;
                    return dateFormatter.format(cal.getTime());
                } else if (value instanceof Date) {
                    Date date = (Date) value;
                    return dateFormatter.format(date);
                } else {
                    throw new ParseException("Tipo de valor desconocido: " + value.getClass().getName(), 0);
                }
            }

            return "";
        }
    }
}
