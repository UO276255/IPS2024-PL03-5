package uo276255.modelo.compra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

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
}
