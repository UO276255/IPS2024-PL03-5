package uo276255.modelo.productos;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoModel{
    private Connection conexion;

    public ProductoModel(Connection conexion) {
        this.conexion = conexion;
    }
    
    public List<Producto> obtenerTodosLosProductos() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM Producto";
             Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int idProducto = rs.getInt("id_producto");
                String nombre = rs.getString("nombre");
                String tipo = rs.getString("tipo");
                double precio = rs.getDouble("precio");

                Producto producto = new Producto(idProducto, nombre, tipo, precio);
                productos.add(producto);
            }
    
        return productos;
    }
    public Producto obtenerPorNombre(String nombreProducto) throws SQLException {
        String sql = "SELECT * FROM Producto WHERE nombre = ?";
        PreparedStatement ps = conexion.prepareStatement(sql);
        ps.setString(1, nombreProducto);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new Producto(
                rs.getInt("id_producto"),
                rs.getString("nombre"),
                rs.getString("tipo"),
                rs.getDouble("precio")
            );
        } else {
            return null;
        }
    }
}
