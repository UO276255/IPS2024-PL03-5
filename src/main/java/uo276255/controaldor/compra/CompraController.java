package uo276255.controaldor.compra;

import uo276255.modelo.compra.Compra;
import uo276255.modelo.compra.CompraDetalle;
import uo276255.modelo.compra.CompraModel;
import uo276255.modelo.productos.Producto;
import uo276255.modelo.productos.ProductoModel;
import uo276255.vista.compra.ComprarProductosView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

public class CompraController {
    private ComprarProductosView vista;
    private ProductoModel productoDAO;
    private CompraModel compraM;
    private Compra compra;

    public CompraController(ComprarProductosView vista, ProductoModel productoDAO, CompraModel compraM) {
        this.vista = vista;
        this.compraM = compraM;
        this.productoDAO = productoDAO;
        this.compra = new Compra(0); // Asumiendo idVendedor = 1
        inicializar();
    }

    private void inicializar() {
        cargarProductos();
        configurarEventos();
    }

    private void cargarProductos() {
        try {
            List<Producto> productos = productoDAO.obtenerTodosLosProductos();
            DefaultTableModel modelo = vista.getModeloTablaProductos();
            for (Producto p : productos) {
                modelo.addRow(new Object[]{p.getNombre(), p.getTipo(), p.getPrecio()});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void configurarEventos() {
        vista.getBtnAgregarAlCarrito().addActionListener(e -> agregarAlCarrito());
        vista.getBtnEliminarDelCarrito().addActionListener(e -> eliminarDelCarrito());
        vista.getBtnFinalizarCompra().addActionListener(e -> finalizarCompra());
    }

    private void agregarAlCarrito() {
        int filaSeleccionada = vista.getTablaProductos().getSelectedRow();
        if (filaSeleccionada != -1) {
            DefaultTableModel modeloProductos = vista.getModeloTablaProductos();
            String nombreProducto = (String) modeloProductos.getValueAt(filaSeleccionada, 0);
            try {
                Producto productoSeleccionado = productoDAO.obtenerPorNombre(nombreProducto);

                if (productoSeleccionado != null) {
                    String cantidadStr = JOptionPane.showInputDialog(vista, "Ingrese la cantidad:", "Cantidad", JOptionPane.PLAIN_MESSAGE);
                    if (cantidadStr != null) {
                        try {
                            int cantidad = Integer.parseInt(cantidadStr);
                            if (cantidad > 0) {
                                CompraDetalle detalle = new CompraDetalle(productoSeleccionado, cantidad);
                                compra.agregarDetalle(detalle);
                                actualizarCarrito();
                                actualizarTotal();
                            } else {
                                JOptionPane.showMessageDialog(vista, "La cantidad debe ser mayor que cero.", "Cantidad inválida", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(vista, "Ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(vista, "Producto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(vista, "Error al obtener el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(vista, "Seleccione un producto.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminarDelCarrito() {
        int filaSeleccionada = vista.getTablaCarrito().getSelectedRow();
        if (filaSeleccionada != -1) {
            CompraDetalle detalle = compra.getDetalles().get(filaSeleccionada);
            String cantidadStr = JOptionPane.showInputDialog(vista, "Ingrese la cantidad a eliminar:", "Eliminar Cantidad", JOptionPane.PLAIN_MESSAGE);
            if (cantidadStr != null) {
                try {
                    int cantidadAEliminar = Integer.parseInt(cantidadStr);
                    if (cantidadAEliminar > 0) {
                        if (cantidadAEliminar >= detalle.getCantidad()) {
                            // Si la cantidad a eliminar es igual o mayor a la existente, eliminar el detalle completo
                            compra.eliminarDetalle(detalle);
                        } else {
                            // Reducir la cantidad del detalle y recalcular el precio total
                            detalle.setCantidad(detalle.getCantidad() - cantidadAEliminar);
                            detalle.recalcularPrecioTotal();
                            compra.calcularTotal();
                        }
                        actualizarCarrito();
                        actualizarTotal();
                    } else {
                        JOptionPane.showMessageDialog(vista, "La cantidad debe ser mayor que cero.", "Cantidad inválida", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(vista, "Ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(vista, "Seleccione un producto en el carrito.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }


    private void actualizarCarrito() {
        DefaultTableModel modeloCarrito = vista.getModeloTablaCarrito();
        modeloCarrito.setRowCount(0); 
        for (CompraDetalle detalle : compra.getDetalles()) {
            modeloCarrito.addRow(new Object[]{
                detalle.getProducto().getNombre(),
                detalle.getCantidad(),
                detalle.getPrecioTotal()
            });
        }
    }

    private void actualizarTotal() {
        vista.getLblTotal().setText("Total: $" + String.format("%.2f", compra.getTotal()));
    }

    private void finalizarCompra() {
        int confirmacion = JOptionPane.showConfirmDialog(vista, "¿Desea finalizar la compra?", "Confirmar Compra", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            if (compra.getDetalles().isEmpty()) {
                JOptionPane.showMessageDialog(vista, "El carrito está vacío.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                compraM.guardarCompra(compra);

                // Construir el mensaje con el precio total y los productos comprados
                StringBuilder mensaje = new StringBuilder();
                mensaje.append("Compra realizada con éxito.\n\n");
                mensaje.append("Productos comprados:\n");

                for (CompraDetalle detalle : compra.getDetalles()) {
                    mensaje.append("- ").append(detalle.getProducto().getNombre())
                           .append(" x ").append(detalle.getCantidad())
                           .append(" = $").append(String.format("%.2f", detalle.getPrecioTotal()))
                           .append("\n");
                }

                mensaje.append("\nPrecio total: $").append(String.format("%.2f", compra.getTotal()));

                JOptionPane.showMessageDialog(vista, mensaje.toString(), "Resumen de Compra", JOptionPane.INFORMATION_MESSAGE);

                // Reiniciar la compra
                compra = new Compra(compra.getIdVendedor());
                actualizarCarrito();
                actualizarTotal();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(vista, "Error al guardar la compra en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }



}
