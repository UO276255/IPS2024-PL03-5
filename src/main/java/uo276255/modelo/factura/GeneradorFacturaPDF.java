package uo276255.modelo.factura;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.awt.Color;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import uo276255.modelo.compra.Compra;
import uo276255.modelo.compra.CompraDetalle;

public class GeneradorFacturaPDF {

    public void generarFactura(Compra compra, Map<String, String> datosCliente) throws Exception {
        // Nombre del archivo PDF
        String nombreArchivo = "Factura_Compra_" + compra.getIdCompra() + ".pdf";

        // Crear documento y escritor de PDF
        Document documento = new Document();
        PdfWriter.getInstance(documento, new FileOutputStream(nombreArchivo));

        documento.open();

        // Agregar logo de la empresa
        Image logo = Image.getInstance("ruta/al/logo.png"); // Reemplaza con la ruta de tu logo
        logo.scaleToFit(100, 50);
        documento.add(logo);

        // Datos fiscales de la empresa
        Paragraph datosEmpresa = new Paragraph();
        datosEmpresa.add(new Chunk("Nombre de la Empresa\n", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
        datosEmpresa.add(new Chunk("Dirección de la Empresa\n", FontFactory.getFont(FontFactory.HELVETICA, 12)));
        datosEmpresa.add(new Chunk("CIF: 12345678A\n", FontFactory.getFont(FontFactory.HELVETICA, 12)));
        datosEmpresa.setSpacingAfter(20);
        documento.add(datosEmpresa);

        // Datos del cliente
        Paragraph datosDelCliente = new Paragraph();
        datosDelCliente.add(new Chunk("Datos del Cliente:\n", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
        datosDelCliente.add(new Chunk("Nombre: " + datosCliente.get("nombre") + "\n", FontFactory.getFont(FontFactory.HELVETICA, 12)));
        datosDelCliente.add(new Chunk("Dirección: " + datosCliente.get("direccion") + "\n", FontFactory.getFont(FontFactory.HELVETICA, 12)));
        datosDelCliente.add(new Chunk("NIF: " + datosCliente.get("nif") + "\n", FontFactory.getFont(FontFactory.HELVETICA, 12)));
        datosDelCliente.setSpacingAfter(20);
        documento.add(datosDelCliente);

        // Fecha de la compra
        Paragraph fechaCompra = new Paragraph();
        fechaCompra.add(new Chunk("Fecha de la Compra: " + compra.getFecha().toString() + "\n", FontFactory.getFont(FontFactory.HELVETICA, 12)));
        fechaCompra.setSpacingAfter(20);
        documento.add(fechaCompra);

        // Tabla de productos
        PdfPTable tabla = new PdfPTable(4);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{3, 1, 2, 2});

        // Encabezados de la tabla
        tabla.addCell(crearCeldaEncabezado("Producto"));
        tabla.addCell(crearCeldaEncabezado("Cantidad"));
        tabla.addCell(crearCeldaEncabezado("Precio Unitario"));
        tabla.addCell(crearCeldaEncabezado("Total"));

        // Obtener detalles de la compra
        List<CompraDetalle> detalles = compra.getDetalles();

        // Agregar detalles de los productos
        for (CompraDetalle detalle : detalles) {
            tabla.addCell(crearCeldaCuerpo(detalle.getProducto().getNombre()));
            tabla.addCell(crearCeldaCuerpo(String.valueOf(detalle.getCantidad())));
            tabla.addCell(crearCeldaCuerpo(String.valueOf(detalle.getPrecioUnitario())));
            tabla.addCell(crearCeldaCuerpo(String.valueOf(detalle.getPrecioTotal())));
        }

        documento.add(tabla);

        // Total general de la compra
        Paragraph total = new Paragraph();
        total.add(new Chunk("Total de la Compra: " + compra.getTotal() + " €\n", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
        total.setAlignment(Element.ALIGN_RIGHT);
        documento.add(total);

        documento.close();
    }

    private PdfPCell crearCeldaEncabezado(String texto) {
        PdfPCell celda = new PdfPCell(new Phrase(texto, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setBackgroundColor(Color.LIGHT_GRAY);
        return celda;
    }

    private PdfPCell crearCeldaCuerpo(String texto) {
        PdfPCell celda = new PdfPCell(new Phrase(texto, FontFactory.getFont(FontFactory.HELVETICA, 12)));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        return celda;
    }
}
