package uo276255.controaldor.ventas;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import uo276255.modelo.compra.CompraModel;
import uo276255.modelo.factura.GeneradorFacturaPDF;
import uo276255.modelo.compra.Compra;
import uo276255.modelo.compra.CompraDetalle;
import uo276255.modelo.ventas.VentaDTO;
import uo276255.modelo.ventas.VentasModel;
import uo276255.vista.ventas.VistaVentas;

public class VentaController {
    private VentasModel ventaDAO;
    private CompraModel compraModel;
    private VistaVentas vistaVentas;

    public VentaController(VentasModel ventaDAO, CompraModel compraModel, VistaVentas vistaVentas) {
        this.ventaDAO = ventaDAO;
        this.compraModel = compraModel;
        this.vistaVentas = vistaVentas;
        initController();
    }

    private void initController() {
        vistaVentas.agregarListenerBtnBuscar(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarVentas();
            }
        });

        vistaVentas.agregarListenerTablaCompras(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    mostrarDetallesCompra();
                }
            }
        });
        
        vistaVentas.agregarListenerBtnGenerarFacturas(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarFactura();
            }
        });

    }

    private void buscarVentas() {
        Date fechaInicioUtil = vistaVentas.getFechaInicio();
        Date fechaFinUtil = vistaVentas.getFechaFin();

        if (fechaInicioUtil == null || fechaFinUtil == null) {
            // Las fechas son inválidas o no seleccionadas
            return;
        }

        if (fechaInicioUtil.after(fechaFinUtil)) {
            JOptionPane.showMessageDialog(vistaVentas, "La fecha de inicio no puede ser posterior a la fecha de fin.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Convertir java.util.Date a java.sql.Date
        java.sql.Date fechaInicio = new java.sql.Date(fechaInicioUtil.getTime());
        java.sql.Date fechaFin = new java.sql.Date(fechaFinUtil.getTime());

        try {
            // Obtener ventas
            List<VentaDTO> ventas = ventaDAO.obtenerVentasPorFecha(fechaInicio, fechaFin);
            vistaVentas.mostrarVentas(ventas);
            BigDecimal totalIngresos = ventas.stream()
                    .map(VentaDTO::getTotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            // Puedes decidir si deseas mostrar el total de ingresos en algún lugar

            // Obtener compras
            List<Compra> compras = compraModel.obtenerCompras(fechaInicio, fechaFin);
            System.out.println("Número de compras obtenidas: " + compras.size());
            vistaVentas.mostrarCompras(compras);
            BigDecimal totalCompras = compras.stream()
                    .map(compra -> BigDecimal.valueOf(compra.getTotal()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);


            vistaVentas.mostrarTotalCompras(totalCompras);

            vistaVentas.limpiarDetallesCompra(); // Limpiar detalles al buscar nuevas compras
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(vistaVentas, "Error al obtener los datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarDetallesCompra() {
        int filaSeleccionada = vistaVentas.getTablaCompras().getSelectedRow();
        if (filaSeleccionada != -1) {
        	Compra compraSeleccionada = vistaVentas.getCompraEnFila(filaSeleccionada);
            if (compraSeleccionada != null) {
                List<CompraDetalle> detalles = compraSeleccionada.getDetalles();
				vistaVentas.mostrarDetallesCompra(detalles);
            } else {
                vistaVentas.limpiarDetallesCompra();
            }
        }
    }
    
    private void generarFacturas() {
        try {
            // Solicitar datos del cliente una vez
            Map<String, String> datosCliente = solicitarDatosCliente();
            if (datosCliente == null) {
                JOptionPane.showMessageDialog(vistaVentas, "Operación cancelada por el usuario.", "Información", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Obtener todas las ventas existentes
            List<VentaDTO> ventas = ventaDAO.obtenerTodasLasVentasConDetalles();

            if (ventas.isEmpty()) {
                JOptionPane.showMessageDialog(vistaVentas, "No hay ventas para generar facturas.", "Información", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Generar factura para cada venta
            GeneradorFacturaPDF generadorPDF = new GeneradorFacturaPDF();
            for (VentaDTO venta : ventas) {
                generadorPDF.generarFactura(venta, datosCliente);
            }

            JOptionPane.showMessageDialog(vistaVentas, "Facturas generadas exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(vistaVentas, "Error al generar las facturas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    private Map<String, String> solicitarDatosCliente() {
        JPanel panel = new JPanel(new GridLayout(0, 2));

        JTextField txtNombre = new JTextField();
        JTextField txtDireccion = new JTextField();
        JTextField txtNIF = new JTextField();

        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Dirección:"));
        panel.add(txtDireccion);
        panel.add(new JLabel("NIF:"));
        panel.add(txtNIF);

        int result = JOptionPane.showConfirmDialog(vistaVentas, panel, "Ingrese los datos del cliente", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            Map<String, String> datosCliente = new HashMap<>();
            datosCliente.put("nombre", txtNombre.getText());
            datosCliente.put("direccion", txtDireccion.getText());
            datosCliente.put("nif", txtNIF.getText());
            return datosCliente;
        } else {
            return null;
        }
    }


}
