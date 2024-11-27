package uo276255.modelo.factura;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import uo276255.modelo.compra.Compra;
import uo276255.modelo.compra.CompraDetalle;
import uo276255.modelo.ventas.VentaDTO;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class GeneradorFacturaPDF {

    public void generarPDF(Map<String, String> datosCliente, List<VentaDTO> ventas, List<Compra> compras) throws Exception {
        // Crear el documento PDF con tamaño y márgenes personalizados
        Document document = new Document(PageSize.A4, 50, 50, 70, 50);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Factura.pdf"));
            // Agregar el encabezado y pie de página
            HeaderFooter headerFooter = new HeaderFooter();
            writer.setPageEvent(headerFooter);

            document.open();

            // Agregar el encabezado con datos de la empresa
            agregarEncabezado(document);

            // Agregar datos del cliente
            agregarDatosCliente(document, datosCliente);

            // Agregar tabla de Ventas
            if (ventas != null && !ventas.isEmpty()) {
                agregarTablaVentas(document, ventas);
            }

            // Agregar tabla de Compras
            if (compras != null && !compras.isEmpty()) {
                agregarTablaCompras(document, compras);
            }

            // Agregar total general (si aplica)
            agregarTotalGeneral(document, ventas, compras);

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            document.close();
        }
    }

    private void agregarEncabezado(Document document) throws DocumentException, IOException {
        // Datos fijos de la empresa
        String nombreEmpresa = "ZeroSport";
        String direccionEmpresa = "Calle Tenerife, nº50, 28039 – Madrid";
        String CIFEmpresa = "B12345678";
        String telefonoEmpresa = "658425689";

        // Agregar el logo
        URL imageUrl = getClass().getResource("/logo.jpg");
        if (imageUrl != null) {
            Image logo = Image.getInstance(imageUrl);
            logo.scaleToFit(80, 80);
            logo.setAlignment(Image.ALIGN_LEFT);

            // Crear una tabla para el encabezado
            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);
            headerTable.setWidths(new int[]{1, 3});

            PdfPCell logoCell = new PdfPCell(logo);
            logoCell.setBorder(Rectangle.NO_BORDER);
            logoCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            headerTable.addCell(logoCell);

            PdfPCell empresaCell = new PdfPCell();
            empresaCell.setBorder(Rectangle.NO_BORDER);
            empresaCell.addElement(new Paragraph(nombreEmpresa, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
            empresaCell.addElement(new Paragraph("CIF: " + CIFEmpresa, FontFactory.getFont(FontFactory.HELVETICA, 12)));
            empresaCell.addElement(new Paragraph(direccionEmpresa, FontFactory.getFont(FontFactory.HELVETICA, 12)));
            empresaCell.addElement(new Paragraph("Teléfono: " + telefonoEmpresa, FontFactory.getFont(FontFactory.HELVETICA, 12)));
            headerTable.addCell(empresaCell);

            document.add(headerTable);
            document.add(new Paragraph("\n")); // Espacio después del encabezado
        } else {
            System.err.println("No se encontró el logo en /logo.jpg");
        }
    }

    private void agregarDatosCliente(Document document, Map<String, String> datosCliente) throws DocumentException {
        String nombreCliente = datosCliente.getOrDefault("nombre", "N/A");
        String direccionCliente = datosCliente.getOrDefault("direccion", "N/A");
        String NIFCliente = datosCliente.getOrDefault("nif", "N/A");

        PdfPTable clientTable = new PdfPTable(1);
        clientTable.setWidthPercentage(100);
        clientTable.setSpacingBefore(10f);

        PdfPCell clientCell = new PdfPCell();
        clientCell.setBackgroundColor(new Color(230, 230, 250)); // Un color suave para el fondo
        clientCell.setPadding(10);

        Paragraph clientInfo = new Paragraph();
        clientInfo.add(new Paragraph("Datos del Cliente", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Color.BLUE)));
        clientInfo.add(new Paragraph("Nombre: " + nombreCliente));
        clientInfo.add(new Paragraph("Dirección: " + direccionCliente));
        clientInfo.add(new Paragraph("NIF: " + NIFCliente));

        clientCell.addElement(clientInfo);
        clientTable.addCell(clientCell);

        document.add(clientTable);
    }

    private void agregarTablaVentas(Document document, List<VentaDTO> ventas) throws DocumentException {
        document.add(new Paragraph("\nVentas", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, Color.BLUE)));
        PdfPTable ventasTable = new PdfPTable(4); // 4 columnas: ID Venta, Fecha, Tipo, Total
        ventasTable.setWidthPercentage(100);
        ventasTable.setSpacingBefore(10f);
        ventasTable.setSpacingAfter(10f);

        // Establecer anchos relativos de las columnas
        ventasTable.setWidths(new float[]{2f, 3f, 3f, 2f});

        // Encabezados de la tabla con estilo
        agregarEncabezadosTabla(ventasTable, new String[]{"ID Venta", "Fecha", "Tipo", "Total"});

        BigDecimal totalVentas = BigDecimal.ZERO;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        for (VentaDTO venta : ventas) {
            int idVenta = venta.getIdVenta();
            Timestamp fecha = venta.getFecha();
            String tipo = venta.getTipo();
            BigDecimal total = venta.getTotal();
            totalVentas = totalVentas.add(total);

            ventasTable.addCell(crearCeldaCentrada(String.valueOf(idVenta)));
            ventasTable.addCell(crearCeldaCentrada(sdf.format(fecha)));
            ventasTable.addCell(crearCeldaCentrada(tipo));
            ventasTable.addCell(crearCeldaDerecha(String.format("%.2f €", total)));
        }
        document.add(ventasTable);

        Paragraph totalVentasParagraph = new Paragraph("Total Ventas: " + String.format("%.2f €", totalVentas), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
        totalVentasParagraph.setAlignment(Element.ALIGN_RIGHT);
        document.add(totalVentasParagraph);
    }

    private void agregarTablaCompras(Document document, List<Compra> compras) throws DocumentException {
        document.add(new Paragraph("\nCompras", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, Color.BLUE)));

        BigDecimal totalCompras = BigDecimal.ZERO;

        for (Compra compra : compras) {
            Paragraph compraInfo = new Paragraph();
            compraInfo.add(new Paragraph("ID Compra: " + compra.getIdCompra(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            compraInfo.add(new Paragraph("Fecha: " + new SimpleDateFormat("dd/MM/yyyy").format(compra.getFecha())));
            compraInfo.add(new Paragraph("ID Vendedor: " + compra.getIdVendedor()));
            compraInfo.setSpacingBefore(10);
            compraInfo.setSpacingAfter(5);
            document.add(compraInfo);

            // Tabla de detalles de la compra
            List<CompraDetalle> detalles = compra.getDetalles();
            if (detalles != null && !detalles.isEmpty()) {
                PdfPTable detallesTable = new PdfPTable(4); // 4 columnas: Producto, Cantidad, Precio Unitario, Total
                detallesTable.setWidthPercentage(100);
                detallesTable.setSpacingBefore(5f);
                detallesTable.setSpacingAfter(5f);

                // Establecer anchos relativos de las columnas
                detallesTable.setWidths(new float[]{4f, 2f, 2f, 2f});

                agregarEncabezadosTabla(detallesTable, new String[]{"Producto", "Cantidad", "Precio Unitario", "Total"});

                BigDecimal totalCompra = BigDecimal.ZERO;

                for (CompraDetalle detalle : detalles) {
                    String producto = detalle.getProducto().getNombre();
                    int cantidad = detalle.getCantidad();
                    double precioUnitario = detalle.getPrecioUnitario();
                    double totalDetalle = precioUnitario * cantidad;
                    totalCompra = totalCompra.add(BigDecimal.valueOf(totalDetalle));

                    detallesTable.addCell(crearCelda(producto));
                    detallesTable.addCell(crearCeldaCentrada(String.valueOf(cantidad)));
                    detallesTable.addCell(crearCeldaDerecha(String.format("%.2f €", precioUnitario)));
                    detallesTable.addCell(crearCeldaDerecha(String.format("%.2f €", totalDetalle)));
                }

                document.add(detallesTable);

                Paragraph totalCompraParagraph = new Paragraph("Total de la Compra: " + String.format("%.2f €", totalCompra), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
                totalCompraParagraph.setAlignment(Element.ALIGN_RIGHT);
                document.add(totalCompraParagraph);

                totalCompras = totalCompras.add(totalCompra);
            }
        }

        // Total general de compras
        Paragraph totalComprasParagraph = new Paragraph("Total Compras: " + String.format("%.2f €", totalCompras), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
        totalComprasParagraph.setAlignment(Element.ALIGN_RIGHT);
        totalComprasParagraph.setSpacingBefore(10);
        document.add(totalComprasParagraph);
    }

    private void agregarTotalGeneral(Document document, List<VentaDTO> ventas, List<Compra> compras) throws DocumentException {
        BigDecimal totalVentas = ventas.stream()
                .map(VentaDTO::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalCompras = BigDecimal.ZERO;
        for (Compra compra : compras) {
            BigDecimal totalCompra = compra.getDetalles().stream()
                    .map(detalle -> BigDecimal.valueOf(detalle.getPrecioUnitario() * detalle.getCantidad()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            totalCompras = totalCompras.add(totalCompra);
        }

        BigDecimal totalGeneral = totalVentas.add(totalCompras);

        Paragraph totalGeneralParagraph = new Paragraph("Total General: " + String.format("%.2f €", totalGeneral), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Color.BLUE));
        totalGeneralParagraph.setAlignment(Element.ALIGN_RIGHT);
        totalGeneralParagraph.setSpacingBefore(20);
        document.add(totalGeneralParagraph);
    }

    // Método auxiliar para agregar encabezados a una tabla con estilo
    private void agregarEncabezadosTabla(PdfPTable table, String[] headers) {
        for (String header : headers) {
            PdfPCell headerCell = new PdfPCell();
            headerCell.setBackgroundColor(new Color(173, 216, 230)); // Color azul claro
            headerCell.setBorderWidth(1);
            headerCell.setPhrase(new Phrase(header, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.WHITE)));
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setPadding(5);
            table.addCell(headerCell);
        }
    }

    // Métodos auxiliares para crear celdas con estilos
    private PdfPCell crearCelda(String texto) {
        PdfPCell cell = new PdfPCell(new Phrase(texto));
        cell.setPadding(5);
        return cell;
    }

    private PdfPCell crearCeldaCentrada(String texto) {
        PdfPCell cell = new PdfPCell(new Phrase(texto));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5);
        return cell;
    }

    private PdfPCell crearCeldaDerecha(String texto) {
        PdfPCell cell = new PdfPCell(new Phrase(texto));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setPadding(5);
        return cell;
    }

    // Clase interna para el encabezado y pie de página
    class HeaderFooter extends PdfPageEventHelper {
        Font footerFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.ITALIC, Color.GRAY);

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            PdfPTable footerTable = new PdfPTable(1);
            try {
                footerTable.setTotalWidth(527);
                footerTable.setWidths(new int[]{1});
                footerTable.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell footerCell = new PdfPCell(new Phrase("Página " + document.getPageNumber(), footerFont));
                footerCell.setBorder(Rectangle.NO_BORDER);
                footerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                footerTable.addCell(footerCell);

                footerTable.writeSelectedRows(0, -1, 34, 50, writer.getDirectContent());
            } catch (DocumentException de) {
                throw new ExceptionConverter(de);
            }
        }
    }
}
