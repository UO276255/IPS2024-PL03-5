package uo276255.controaldor.accionistas;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import uo276255.modelo.accionistas.AccionistaDTO;
import uo276255.modelo.accionistas.AccionistaModel;
import uo276255.vista.accionistas.AccionistaVista;

/**
 * Clase que representa el controlador para gestionar accionistas.
 */
public class AccionistaController {

    private AccionistaVista vista;
    private AccionistaModel modelo;

    /**
     * Constructor del controlador de accionistas.
     *
     * @param vista  La vista asociada al controlador.
     * @param modelo El modelo que maneja los datos.
     */
    public AccionistaController(AccionistaVista vista, AccionistaModel modelo) {
        this.vista = vista;
        this.modelo = modelo;
    }
    /**
     * Maneja la creación de un nuevo accionista.
     */
    private void crearAccionista() {
        try {
        	AccionistaDTO nuevoAccionista = vista.obtenerDatosAccionista();
            if (nuevoAccionista != null) {
                // Verificar si el DNI ya existe
            	AccionistaDTO existente = modelo.obtenerAccionistaPorDNI(nuevoAccionista.getDni());
                if (existente != null) {
                    vista.mostrarMensajeError("Ya existe un accionista con el DNI proporcionado.");
                    return;
                }

                modelo.crearAccionista(nuevoAccionista);
                vista.mostrarMensajeExito("Accionista creado exitosamente.");
            }
        } catch (SQLException e) {
            vista.mostrarMensajeError("Error al crear el accionista: " + e.getMessage());
        }
    }

}
