package uo276255.controaldor.accionistas;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import uo276255.modelo.acciones.accion.AccionistaCreadoListener;
import uo276255.modelo.acciones.accion.AccionesModel;
import uo276255.modelo.acciones.campaña.CampañaDTO;
import uo276255.modelo.accionistas.AccionistaDTO;
import uo276255.modelo.accionistas.AccionistaModel;
import uo276255.vista.acciones.AccionesDisponiblesVista;
import uo276255.vista.accionistas.AccionistaVista;

/**
 * Controlador para la creación de accionistas.
 */
public class AccionistaController {

    private AccionistaVista vista;
    private AccionesDisponiblesVista vistaCompra;
    private AccionistaModel modelo;
    private AccionesModel modeloAcciones;
    private AccionistaCreadoListener listener;

	public AccionistaController(AccionistaVista crearAccionistaVista, AccionistaModel accionistaModel,
			AccionesModel modeloAcciones,AccionesDisponiblesVista vistaCompra, CampañaDTO campaña, int cantidadAcciones) {
        this.vista = crearAccionistaVista;
        this.modelo = accionistaModel;
        this.vistaCompra = vistaCompra;
        this.modeloAcciones = modeloAcciones;
        inicializarControlador(campaña,cantidadAcciones);	
      }


	/**
     * Inicializa el controlador y los eventos de la vista.
     */
    private void inicializarControlador(CampañaDTO campaña, int cantidadAcciones) {
        vista.agregarListenerCrearAccionista(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearAccionista(campaña,cantidadAcciones);
               }
        });
    }
	/**
     * Crea un nuevo accionista y notifica al listener.
     */
    private void crearAccionista(CampañaDTO campaña, int cantidadAcciones) {
        AccionistaDTO nuevoAccionista = vista.obtenerDatosAccionista();
        try {
            modelo.crearAccionista(nuevoAccionista);
            vista.dispose();
            AccionistaDTO dto = modelo.obtenerUltimoAccionista();
            modeloAcciones.comprarAcciones(dto, campaña, cantidadAcciones);
            vistaCompra.mostrarMensajeExito("Compra realizada exitosamente en fase 3.");
           } catch (SQLException e) {
                    vistaCompra.mostrarMensajeError("Error al comprar acciones: " + e.getMessage());
                }
        	}
    }

