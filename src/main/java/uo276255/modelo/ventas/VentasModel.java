package uo276255.modelo.ventas;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VentasModel {
    private Connection conexion;

    public VentasModel(Connection conexion) {
        this.conexion = conexion;
    }

    public List<VentaDTO> obtenerVentasPorFecha(Date fechaInicio, Date fechaFin) throws SQLException {
        List<VentaDTO> ventas = new ArrayList<>();
        String sql = "SELECT id_venta, fecha, tipo, total FROM Venta WHERE fecha BETWEEN ? AND ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setTimestamp(1, new java.sql.Timestamp(fechaInicio.getTime()));
            ps.setTimestamp(2, new java.sql.Timestamp(fechaFin.getTime()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                VentaDTO venta = new VentaDTO();
                venta.setIdVenta(rs.getInt("id_venta"));
                venta.setFecha(rs.getTimestamp("fecha"));
                venta.setTipo(rs.getString("tipo"));
                venta.setTotal(rs.getBigDecimal("total"));
                ventas.add(venta);
            }
        }
        return ventas;
    }

}
