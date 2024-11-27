package uo276255.modelo.compra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uo276255.modelo.productos.Producto;
import uo276255.modelo.productos.ProductoModel;

public class CompraModel {
    private Connection connection;

    public CompraModel(Connection connection) {
        this.connection = connection;
    }

    public void guardarCompra(Compra compra) throws SQLException {
        String sqlCompra = "INSERT INTO Compra (fecha, id_vendedor, total) VALUES (?, ?, ?)";
        PreparedStatement psCompra = connection.prepareStatement(sqlCompra, Statement.RETURN_GENERATED_KEYS);
        psCompra.setTimestamp(1, new Timestamp(compra.getFecha().getTime()));
        psCompra.setInt(2, compra.getIdVendedor());
        psCompra.setDouble(3, compra.getTotal());
        psCompra.executeUpdate();

        ResultSet rsKeys = psCompra.getGeneratedKeys();
        if (rsKeys.next()) {
            int idCompraGenerada = rsKeys.getInt(1);
            compra.setIdCompra(idCompraGenerada);

            String sqlDetalle = "INSERT INTO Compra_Detalle (id_compra, id_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
            PreparedStatement psDetalle = connection.prepareStatement(sqlDetalle);

            for (CompraDetalle detalle : compra.getDetalles()) {
                psDetalle.setInt(1, idCompraGenerada);
                psDetalle.setInt(2, detalle.getProducto().getIdProducto());
                psDetalle.setInt(3, detalle.getCantidad());
                psDetalle.setDouble(4, detalle.getPrecioUnitario());
                psDetalle.addBatch();
            }
            psDetalle.executeBatch();
        }
    }
    
    public List<Compra> obtenerTodasLasCompras() throws SQLException {
        List<Compra> compras = new ArrayList<>();

        String sqlCompra = "SELECT * FROM Compra";
        PreparedStatement psCompra = connection.prepareStatement(sqlCompra);
        ResultSet rsCompra = psCompra.executeQuery();

        while (rsCompra.next()) {
            Compra compra = new Compra();
            compra.setIdCompra(rsCompra.getInt("id_compra"));
            compra.setFecha(rsCompra.getTimestamp("fecha"));
            compra.setIdVendedor(rsCompra.getInt("id_vendedor"));
            compra.setTotal(rsCompra.getDouble("total"));

            // Obtener los detalles de la compra
            String sqlDetalle = "SELECT * FROM Compra_Detalle WHERE id_compra = ?";
            PreparedStatement psDetalle = connection.prepareStatement(sqlDetalle);
            psDetalle.setInt(1, compra.getIdCompra());
            ResultSet rsDetalle = psDetalle.executeQuery();

            List<CompraDetalle> detalles = new ArrayList<>();
            while (rsDetalle.next()) {
                CompraDetalle detalle = new CompraDetalle();
                detalle.setCantidad(rsDetalle.getInt("cantidad"));
                detalle.setPrecioUnitario(rsDetalle.getDouble("precio_unitario"));
                int idProducto = rsDetalle.getInt("id_producto");
                ProductoModel productoModel = new ProductoModel(connection);
                Producto producto = productoModel.obtenerProductoPorId(idProducto);
                detalle.setProducto(producto);

                detalles.add(detalle);
            }

            compra.setDetalles(detalles);
            compras.add(compra);
        }

        return compras;
    }

    public List<Compra> obtenerCompras(Date fechaInicio, Date fechaFin) throws SQLException {
        List<Compra> compras = new ArrayList<>();

        String sqlCompra = "SELECT * FROM Compra WHERE fecha BETWEEN ? AND ?";
        try (PreparedStatement psCompra = connection.prepareStatement(sqlCompra)) {
            psCompra.setTimestamp(1, new java.sql.Timestamp(fechaInicio.getTime()));
            psCompra.setTimestamp(2, new java.sql.Timestamp(fechaFin.getTime()));
            ResultSet rsCompra = psCompra.executeQuery();

            while (rsCompra.next()) {
                Compra compra = new Compra();
                compra.setIdCompra(rsCompra.getInt("id_compra"));
                compra.setFecha(rsCompra.getTimestamp("fecha"));
                compra.setIdVendedor(rsCompra.getInt("id_vendedor"));
                compra.setTotal(rsCompra.getDouble("total"));

                // Obtener los detalles de la compra
                String sqlDetalle = "SELECT * FROM Compra_Detalle WHERE id_compra = ?";
                try (PreparedStatement psDetalle = connection.prepareStatement(sqlDetalle)) {
                    psDetalle.setInt(1, compra.getIdCompra());
                    ResultSet rsDetalle = psDetalle.executeQuery();

                    List<CompraDetalle> detalles = new ArrayList<>();
                    while (rsDetalle.next()) {
                        CompraDetalle detalle = new CompraDetalle();
                        detalle.setCantidad(rsDetalle.getInt("cantidad"));
                        detalle.setPrecioUnitario(rsDetalle.getDouble("precio_unitario"));
                        int idProducto = rsDetalle.getInt("id_producto");

                        // Obtener el producto asociado
                        ProductoModel productoModel = new ProductoModel(connection);
                        Producto producto = productoModel.obtenerProductoPorId(idProducto);
                        detalle.setProducto(producto);

                        detalles.add(detalle);
                    }
                    compra.setDetalles(detalles);
                }
                compras.add(compra);
            }
        }
        return compras;
    }

}
