package uo276255.controaldor.acciones.campaña;

import uo276255.modelo.acciones.campaña.CampañaModel;
import uo276255.vista.acciones.CrearCampañaVista;
import uo276255.vista.acciones.ManejoCampañasVista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

/**
 * Clase que representa el controlador para gestionar campañas.
 */
public class CampañaController {

    private ManejoCampañasVista manejoVista;
    private CrearCampañaVista crearVista;
    private CampañaModel modelo;

    /**
     * Constructor para el controlador de campañas.
     *
     * @param manejoVista La vista de manejo de campañas.
     * @param modelo      El modelo de campañas.
     */
    public CampañaController(ManejoCampañasVista manejoVista, CampañaModel modelo) {
        this.manejoVista = manejoVista;
        this.modelo = modelo;

        inicializarControlador();
    }

    /**
     * Inicializa los listeners y el estado inicial de la vista.
     */
    private void inicializarControlador() {
        try {
            boolean existeCampaña = modelo.existeCampañaActiva();
            manejoVista.setCampañaExistente(existeCampaña);
        } catch (SQLException e) {
            manejoVista.mostrarMensaje("Error al verificar la existencia de campañas.");
        }

        manejoVista.agregarListenerCrearCampaña(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirCrearCampañaVista();
            }
        });

        manejoVista.agregarListenerAumentarFase(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aumentarFaseCampaña();
            }
        });
    }

    /**
     * Abre la vista para crear una nueva campaña.
     */
    private void abrirCrearCampañaVista() {
        try {
            if (modelo.existeCampañaActiva()) {
                manejoVista.mostrarMensaje("Ya existe una campaña activa.");
            } else {
                crearVista = new CrearCampañaVista();
                crearVista.setVisible(true);

                crearVista.agregarListenerCrear(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String nombre = crearVista.getNombre();
                        int maxAcciones = crearVista.getMaximoAcciones();

                        if (nombre.isEmpty()) {
                            crearVista.mostrarMensajeError("El nombre de la campaña no puede estar vacío.");
                        } else if (maxAcciones <= 0) {
                            crearVista.mostrarMensajeError("El máximo de acciones debe ser un número positivo.");
                        } else {
                            try {
                                modelo.crearCampaña(nombre, maxAcciones);
                                crearVista.mostrarMensajeExito("Campaña creada exitosamente.");
                                manejoVista.setCampañaExistente(true);
                            } catch (SQLException ex) {
                                crearVista.mostrarMensajeError("Error al crear la campaña.");
                            }
                        }
                    }
                });

                // Actualizar el estado cuando se cierre la ventana
                crearVista.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent e) {
                        try {
                            boolean existeCampaña = modelo.existeCampañaActiva();
                            manejoVista.setCampañaExistente(existeCampaña);
                        } catch (SQLException ex) {
                            manejoVista.mostrarMensaje("Error al verificar la existencia de campañas.");
                        }
                    }
                });
            }
        } catch (SQLException ex) {
            manejoVista.mostrarMensaje("Error al verificar la existencia de campañas.");
        }
    }

    /**
     * Aumenta la fase de la campaña activa.
     */
    private void aumentarFaseCampaña() {
        try {
            int faseActual = modelo.obtenerFaseActual();
            if (faseActual >= 1 && faseActual < 3) {
                modelo.aumentarFaseCampaña();
                manejoVista.mostrarMensaje("La fase de la campaña ha sido actualizada a " + (faseActual + 1) + ".");
            } else {
                // Mostrar mensaje de confirmación
                int opcion = manejoVista.mostrarConfirmacion("La campaña ha alcanzado la fase 3.\n¿Desea cerrar la campaña actual?");
                if (opcion == JOptionPane.YES_OPTION) {
                    modelo.cerrarCampaña();
                    manejoVista.mostrarMensaje("La campaña ha sido cerrada.");
                    manejoVista.setCampañaExistente(false);
                } else {
                    manejoVista.mostrarMensaje("La campaña continúa activa en la fase actual.");
                }
            }
        } catch (SQLException e) {
            manejoVista.mostrarMensaje(e.getMessage());
        }
    }

}
