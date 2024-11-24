package uo276255.modelo.compra;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Compra {
    private int idCompra;
    private Date fecha;
    private int idVendedor;
    private List<CompraDetalle> detalles;
    private double total;

    public Compra(int idVendedor) {
        this.fecha = new Date();
        this.idVendedor = idVendedor;
        this.detalles = new ArrayList<>();
        this.total = 0.0;
    }

    public Compra() {
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public void setIdVendedor(int idVendedor) {
		this.idVendedor = idVendedor;
	}

	public void setDetalles(List<CompraDetalle> detalles) {
		this.detalles = detalles;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public void agregarDetalle(CompraDetalle detalle) {
        detalles.add(detalle);
        calcularTotal();
    }

    public void eliminarDetalle(CompraDetalle detalle) {
        detalles.remove(detalle);
        calcularTotal();
    }

    // Método para calcular el total de la compra
    public void calcularTotal() {
        total = detalles.stream().mapToDouble(CompraDetalle::getPrecioTotal).sum();
    }

    // Getters y Setters
    public int getIdCompra() { return idCompra; }
    public void setIdCompra(int idCompra) { this.idCompra = idCompra; }
    public Date getFecha() { return fecha; }
    public int getIdVendedor() { return idVendedor; }
    public List<CompraDetalle> getDetalles() { return detalles; }
    public double getTotal() { return total; }
    
    
}


