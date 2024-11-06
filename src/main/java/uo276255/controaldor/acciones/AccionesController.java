package uo276255.controaldor.acciones;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import uo276255.controaldor.accionistas.AccionistaController;
import uo276255.modelo.acciones.accion.AccionDTO;
import uo276255.modelo.acciones.accion.AccionesModel;
import uo276255.modelo.acciones.campaña.CampañaDTO;
import uo276255.modelo.accionistas.AccionistaDTO;
import uo276255.modelo.accionistas.AccionistaModel;
import uo276255.vista.acciones.AccionesDisponiblesVista;
import uo276255.vista.acciones.CompraAccionesVista;
import uo276255.vista.accionistas.AccionistaVista;
import uo276255.vista.accionistas.VenderAccionesVista;

/**
 * Clase que representa el controlador para la compra de acciones.
 */
public class AccionesController {

    private CompraAccionesVista vista;
    private AccionesModel modelo;
    private AccionesDisponiblesVista vistaCompra;
	private AccionistaModel modeloAccionista;
	private VenderAccionesVista vistaAccionista;
    /**
     * Constructor del controlador de compra de acciones.
     *
     * @param vista  La vista asociada al controlador.
     * @param modelo El modelo que maneja los datos.
     */
    public AccionesController(CompraAccionesVista vista, AccionesModel modelo) {
        this.vista = vista;
        this.modelo = modelo;
        inicializarControlador();
    }

    /**
     * Constructor del controlador.
     *
     * @param vista  La vista asociada.
     * @param modelo El modelo asociado.
     */
    public AccionesController(AccionesDisponiblesVista vista, AccionesModel modelo) {
        this.vistaCompra = vista;
        this.modelo = modelo;
        inicializarControladorCompra();      
    }
    
    /**
     * Constructor del controlador para la gestión de acciones del accionista.
     *
     * @param vistaAccionista   La vista asociada.
     * @param modeloAccionista  El modelo asociado.
     * @param modelo            El modelo de compra de acciones.
     */
    public AccionesController(VenderAccionesVista vistaAccionista, AccionistaModel modeloAccionista, AccionesModel modelo) {
        this.vistaAccionista = vistaAccionista;
        this.modeloAccionista = modeloAccionista;
        this.modelo = modelo;
        inicializarControladorAccionista();
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
     * Inicializa el controlador y carga los datos iniciales.
     */
    private void inicializarControladorCompra() {
    	vistaCompra.agregarListenerActualizar(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatos();
            }
        });

    	vistaCompra.agregarListenerComprarAccion(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comprarAccionSeleccionada();
            }
        });
    	
    	vistaCompra.agregarListenerComprarAccionesCampaña(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	procesarCompra();
            }
        });

        cargarDatos();
    }
    
    /**
     * Inicializa el controlador para la gestión de acciones del accionista.
     */
    private void inicializarControladorAccionista() {
        vistaAccionista.agregarListenerBuscarAcciones(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarAcciones();
            }
        });

        vistaAccionista.agregarListenerPonerEnVenta(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ponerAccionesEnVenta();
            }
        });
    }


    /**
     * Procesa la compra de acciones según la fase de la campaña.
     */
    private void procesarCompra() {
        try {
            CampañaDTO campaña = modelo.obtenerCampañaActiva();
            AccionistaDTO accionista = null;
            if (campaña == null) {
                vista.mostrarMensajeError("No hay una campaña activa en este momento.");
                return;
            }

            int faseActual = campaña.getFase();
        	int cantidad = vistaCompra.getCantidadAccionesComprar();

        	if (cantidad <= 0 ) {
        		vistaCompra.mostrarMensajeError("La cantidad de acciones debe ser un número positivo.");
            	return;
        	}
        	
        	if(cantidad > campaña.getNumeroAcciones()) {
        		vistaCompra.mostrarMensajeError("La cantidad de acciones debe ser menor que el total disponible.");
            	return;
        	}
        	
        	
            if(faseActual != 3) {
            	int idUsuario = vistaCompra.getIdEmpleado();
            	if(idUsuario <= 0) {
            		vistaCompra.mostrarMensajeError("La fase actual requiere un id de usuario");
                	return;
            	}
                accionista = modelo.obtenerAccionistaPorDNI(idUsuario);
                
            }
            
            if(campaña.getNumeroAcciones() == 0) {
        		vistaCompra.mostrarMensajeError("No quedan acciones en la campaña");
            	return;
            }
            
            
            switch (faseActual) {
            case 1:
                manejarFase1(accionista, campaña, cantidad);
                break;
            case 2:
                manejarFase2(accionista, campaña, cantidad);
                break;
            case 3:
                manejarFase3(accionista,campaña, cantidad);
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
        	vistaCompra.mostrarMensajeError("Debe ser un accionista existente para comprar en fase 1.");
            return;
        }

        int accionesPoseidas = modelo.contarAccionesAccionistaEnCampaña(accionista.getIdAccionista(), campaña.getIdCampaña());

        if (accionesPoseidas == 0) {
        	vistaCompra.mostrarMensajeError("No posee mas acciones para comprar en fase 1 en esta campaña .");
            return;
        }

        if (cantidadAcciones > accionesPoseidas) {
        	vistaCompra.mostrarMensajeError("No puede comprar más acciones de las que posee actualmente.");
            return;
        }
        modelo.comprarAcciones(accionista, campaña, cantidadAcciones);
        modelo.actualizarMaximo(accionista.getIdAccionista(), campaña.getIdCampaña(), cantidadAcciones);
        vistaCompra.mostrarMensajeExito("Compra realizada exitosamente en fase 1.");
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
        modelo.comprarAcciones(accionista, campaña, cantidadAcciones);
        vistaCompra.mostrarMensajeExito("Compra realizada exitosamente en fase 2.");
    }

    /**
     * Maneja la compra de acciones en la fase 3.
     *
     * @param accionista       El accionista que compra las acciones (puede ser null).
     * @param campaña          La campaña actual.
     * @param cantidadAcciones La cantidad de acciones a comprar.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    private void manejarFase3(AccionistaDTO accionista,CampañaDTO campaña, int cantidadAcciones) throws SQLException {   
    	if(accionista == null) {
            abrirVentanaCrearAccionista("No es accionista, cree una cuenta nueva", campaña, cantidadAcciones);
    	} else {
            modelo.comprarAcciones(accionista, campaña, cantidadAcciones);
            vistaCompra.mostrarMensajeExito("Compra realizada exitosamente en fase 3.");
    	}

    }
    
    /**
     * Carga los datos de las acciones en venta y la campaña activa.
     */
    private void cargarDatos() {
        try {
            CampañaDTO campañaActiva = modelo.obtenerCampañaActiva();
            if (campañaActiva != null) {
            	vistaCompra.mostrarCampañaActiva(campañaActiva.getNombre(), campañaActiva.getNumeroAcciones(),campañaActiva.getFase());
            } else {
            	vistaCompra.mostrarSinCampañaActiva();
            }

            // Cargar acciones en venta
            List<AccionDTO> accionesEnVenta = modelo.obtenerAccionesEnVenta();
            Object[][] datosTabla = new Object[accionesEnVenta.size()][4];
            for (int i = 0; i < accionesEnVenta.size(); i++) {
                AccionDTO accion = accionesEnVenta.get(i);
                datosTabla[i][0] = accion.getIdAccion();
                datosTabla[i][1] = accion.getIdAccionista();
            }
            vistaCompra.mostrarAccionesEnVenta(datosTabla);

        } catch (SQLException e) {
            vista.mostrarMensajeError("Error al cargar los datos: " + e.getMessage());
        }
    }
    
    /**
     * Procesa la compra de la acción seleccionada.
     */
    private void comprarAccionSeleccionada() {
        String idAccion = vistaCompra.getIdAccionSeleccionada();
        int idEmpleado = vistaCompra.getIdEmpleado();

        if (idAccion == null) {
        	vistaCompra.mostrarMensajeError("Debe seleccionar una acción de la tabla.");
            return;
        }

        if (idEmpleado <= 0) {
        	vistaCompra.mostrarMensajeError("Debe ingresar un ID de empleado válido.");
            return;
        }

        try {
            modelo.comprarAccion(Integer.parseInt(idAccion), idEmpleado);
            vistaCompra.mostrarMensajeExito("Acción comprada exitosamente.");
            cargarDatos();
        } catch (SQLException e) {
            vista.mostrarMensajeError("Error al comprar la acción: " + e.getMessage());
        }
    }
    
    /**
     * Abre la ventana para crear un nuevo accionista y procede con la compra después de crearlo.
     *
     * @param mensaje           El mensaje a mostrar al usuario.
     * @param campaña           La campaña actual.
     * @param cantidadAcciones  La cantidad de acciones a comprar.
     */
    private void abrirVentanaCrearAccionista(String mensaje, CampañaDTO campaña, int cantidadAcciones) {
    	AccionistaVista crearAccionistaVista = new AccionistaVista();
        crearAccionistaVista.mostrarMensaje(mensaje);
        AccionistaModel accionistaModel = new AccionistaModel(modelo.getConnect());
        @SuppressWarnings("unused")
		AccionistaController crearAccionistaController = new AccionistaController(crearAccionistaVista, accionistaModel,modelo,vistaCompra,campaña,cantidadAcciones);
        crearAccionistaVista.setVisible(true);
    }
     

    /**
     * Busca las acciones no en venta del accionista.
     */
    private void buscarAcciones() {
        int idAccionista = vistaAccionista.getIdAccionista();
        if (idAccionista <= 0) {
            vistaAccionista.mostrarMensajeError("Ingrese un ID de accionista válido.");
            return;
        }

        try {
            List<AccionDTO> acciones = modeloAccionista.obtenerAccionesNoEnVentaPorAccionista(idAccionista);
            if (acciones.isEmpty()) {
                vistaAccionista.mostrarMensajeError("No se encontraron acciones no en venta para este accionista.");
                return;
            }

            Object[][] datosTabla = new Object[acciones.size()][4];
            for (int i = 0; i < acciones.size(); i++) {
                AccionDTO accion = acciones.get(i);
                datosTabla[i][0] = accion.getIdAccion();
                datosTabla[i][1] = accion.getIdCampaña();
                datosTabla[i][3] = false;
            }
            vistaAccionista.mostrarAcciones(datosTabla);
        } catch (SQLException e) {
            vistaAccionista.mostrarMensajeError("Error al obtener las acciones: " + e.getMessage());
        }
    }

    /**
     * Marca las acciones seleccionadas como en venta.
     */
    private void ponerAccionesEnVenta() {
        String[] accionesSeleccionadas = vistaAccionista.getAccionesSeleccionadas();
        if (accionesSeleccionadas.length == 0) {
            vistaAccionista.mostrarMensajeError("Seleccione al menos una acción para poner en venta.");
            return;
        }

        try {
            modeloAccionista.marcarAccionesEnVenta(accionesSeleccionadas);
            vistaAccionista.mostrarMensajeExito("Las acciones seleccionadas se han puesto en venta.");
            vistaAccionista.dispose();
        } catch (SQLException e) {
            vistaAccionista.mostrarMensajeError("Error al poner acciones en venta: " + e.getMessage());
        }
    }
   
    
}
