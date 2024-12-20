package uo276255.modelo.compra;

import uo276255.modelo.productos.Producto;

public class CompraDetalle {
    private Producto producto;
    private int cantidad;
    private double precioUnitario;
    private double precioTotal;

    public CompraDetalle(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = producto.getPrecio();
        this.precioTotal = cantidad * precioUnitario;
    }

    public CompraDetalle() {
		// TODO Auto-generated constructor stub
	}

	// Getters y Setters
    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
    public double getPrecioTotal() { return precioTotal; }

    // Método para recalcular el precio total
    public void recalcularPrecioTotal() {
        this.precioTotal = this.cantidad * this.precioUnitario;
    }

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public void setPrecioTotal(double precioTotal) {
		this.precioTotal = precioTotal;
	}
}

