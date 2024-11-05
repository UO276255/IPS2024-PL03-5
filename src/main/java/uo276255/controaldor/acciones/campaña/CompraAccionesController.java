package uo276255.controaldor.acciones.campaña;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import uo276255.modelo.acciones.accion.CompraAccionesModel;
import uo276255.modelo.acciones.campaña.CampañaDTO;
import uo276255.modelo.accionistas.AccionistaDTO;
import uo276255.vista.acciones.CompraAccionesVista;

/**
 * Clase que representa el controlador para la compra de acciones.
 */
public class CompraAccionesController {

    private CompraAccionesVista vista;
    private CompraAccionesModel modelo;

    /**
     * Constructor del controlador de compra de acciones.
     *
     * @param vista  La vista asociada al controlador.
     * @param modelo El modelo que maneja los datos.
     */
    public CompraAccionesController(CompraAccionesVista vista, CompraAccionesModel modelo) {
        this.vista = vista;
        this.modelo = modelo;
        inicializarControlador();
    }

    /**
     * Inicializa los listeners y actualiza la vista con los datos iniciales.
     */
    private void inicializarControlador() {
        // Agregar listener al botón de comprar acciones
        vista.agregarListenerComprarAcciones(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesarCompra();
            }
        });
    }

    /**
     * Procesa la compra de acciones según la fase de la campaña.
     */
    private void procesarCompra() {
        try {
            CampañaDTO campaña = modelo.obtenerCampañaActiva();
            if (campaña == null) {
                vista.mostrarMensajeError("No hay una campaña activa en este momento.");
                return;
            }

            int faseActual = campaña.getFase();
            String dniUsuario = vista.getDniUsuario();
            int cantidadAcciones = vista.getCantidadAcciones();

            if (cantidadAcciones <= 0) {
                vista.mostrarMensajeError("La cantidad de acciones debe ser un número positivo.");
                return;
            }

            AccionistaDTO accionista = modelo.obtenerAccionistaPorDNI(dniUsuario);

            switch (faseActual) {
                case 1:
                    manejarFase1(accionista, campaña, cantidadAcciones);
                    break;
                case 2:
                    manejarFase2(accionista, campaña, cantidadAcciones);
                    break;
                case 3:
                    manejarFase3(accionista, campaña, cantidadAcciones);
                    break;
                default:
                    vista.mostrarMensajeError("Fase de campaña no válida.");
            }

        } catch (SQLException e) {
            vista.mostrarMensajeError("Error al procesar la compra: " + e.getMessage());
        }
    }

    /**
     * Maneja la compra de acciones en la fase 1.
     *
     * @param accionista       El accionista que compra las acciones.
     * @param campaña          La campaña actual.
     * @param cantidadAcciones La cantidad de acciones a comprar.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    private void manejarFase1(AccionistaDTO accionista, CampañaDTO campaña, int cantidadAcciones) throws SQLException {
        if (accionista == null) {
            vista.mostrarMensajeError("Debe ser un accionista existente para comprar en fase 1.");
            return;
        }

        int accionesPoseidas = modelo.contarAccionesAccionistaEnCampaña(accionista.getIdAccionista(), campaña.getIdCampaña());

        if (accionesPoseidas == 0) {
            vista.mostrarMensajeError("No posee acciones en esta campaña para comprar en fase 1.");
            return;
        }

        if (cantidadAcciones > accionesPoseidas) {
            vista.mostrarMensajeError("No puede comprar más acciones de las que posee actualmente.");
            return;
        }

        // Procesar la compra
        modelo.comprarAcciones(accionista, campaña, cantidadAcciones);
        vista.mostrarMensajeExito("Compra realizada exitosamente en fase 1.");
    }

    /**
     * Maneja la compra de acciones en la fase 2.
     *
     * @param accionista       El accionista que compra las acciones.
     * @param campaña          La campaña actual.
     * @param cantidadAcciones La cantidad de acciones a comprar.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    private void manejarFase2(AccionistaDTO accionista, CampañaDTO campaña, int cantidadAcciones) throws SQLException {
        if (accionista == null) {
            vista.mostrarMensajeError("Debe ser un accionista existente para comprar en fase 2.");
            return;
        }

        // Procesar la compra sin restricciones
        modelo.comprarAcciones(accionista, campaña, cantidadAcciones);
        vista.mostrarMensajeExito("Compra realizada exitosamente en fase 2.");
    }

    /**
     * Maneja la compra de acciones en la fase 3.
     *
     * @param accionista       El accionista que compra las acciones (puede ser null).
     * @param campaña          La campaña actual.
     * @param cantidadAcciones La cantidad de acciones a comprar.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    private void manejarFase3(AccionistaDTO accionista, CampañaDTO campaña, int cantidadAcciones) throws SQLException {
        if (accionista == null) {
            // Crear nuevo accionista
        	AccionistaDTO nuevoAccionista = vista.obtenerDatosNuevoAccionista();
            if (nuevoAccionista == null) {
                vista.mostrarMensajeError("Debe proporcionar los datos para crear una cuenta de accionista.");
                return;
            }

            accionista = modelo.crearAccionista(nuevoAccionista);
        }

        // Procesar la compra
        modelo.comprarAcciones(accionista, campaña, cantidadAcciones);
        vista.mostrarMensajeExito("Compra realizada exitosamente en fase 3.");
    }
}
