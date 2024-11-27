package uo276255.modelo.productos;

import java.util.HashMap;
import java.util.Map;

public class Carrito {
    private Map<Producto, Integer> items;

    public Carrito() {
        items = new HashMap<>();
    }

    public void agregarProducto(Producto producto, int cantidad) {
        items.put(producto, items.getOrDefault(producto, 0) + cantidad);
    }

    public void eliminarProducto(Producto producto) {
        items.remove(producto);
    }

    public double calcularTotal() {
        double total = 0;
        for (Map.Entry<Producto, Integer> entry : items.entrySet()) {
            total += entry.getKey().getPrecio() * entry.getValue();
        }
        return total;
    }

    public Map<Producto, Integer> getItems() {
        return items;
    }
}
