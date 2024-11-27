package uo276255.modelo.ventas;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class VentaDTO {
    private int idVenta;
    private Timestamp fecha;
    private String tipo; 
    private BigDecimal total;

    // Constructor vacío
    public VentaDTO() {}

    // Constructor con parámetros
    public VentaDTO(int idVenta, Timestamp fecha, String tipo, BigDecimal total) {
        this.idVenta = idVenta;
        this.fecha = fecha;
        this.tipo = tipo;
        this.total = total;
    }

    // Getters y Setters
    public int getIdVenta() {
        return idVenta;
    }
    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public Timestamp getFecha() {
        return fecha;
    }
    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getTotal() {
        return total;
    }
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
