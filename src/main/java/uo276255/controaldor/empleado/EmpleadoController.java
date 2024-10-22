package uo276255.controaldor.empleado;

import uo276255.modelo.empleado.EmpleadoDTO;
import uo276255.modelo.empleado.EmpleadoModel;
import uo276255.modelo.horario.HorarioDTO;
import uo276255.vista.empleado.AgregarEmpleadoVista;
import uo276255.vista.empleado.EliminarEmpleadoVista;
import uo276255.vista.empleado.ModificarEmpleadoVista;
import uo276255.vista.horario.AgregarHorarioVista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Controlador para gestionar la lógica relacionada con los empleados.
 */
public class EmpleadoController {

    private AgregarEmpleadoVista vistaAgregar;
    private EliminarEmpleadoVista vistaElminarMod;
    private EmpleadoModel modelo;
    private Connection conn;

    /**
     * Constructor para inicializar el controlador con la vista de agregar empleado.
     *
     * @param vista Vista para agregar empleados.
     * @param modelo Modelo de empleado.
     * @param conn Conexión a la base de datos.
     */
    public EmpleadoController(AgregarEmpleadoVista vista, EmpleadoModel modelo, Connection conn) {
        this.vistaAgregar = vista;
        this.modelo = modelo;
        this.conn = conn;
        this.vistaAgregar.agregarListenerAgregar(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarEmpleado();
            }
        });
    }
    /**
     * Constructor para inicializar el controlador con la vista de eliminar o modificar empleados.
     *
     * @param vista Vista para eliminar o modificar empleados.
     * @param modelo Modelo de empleado.
     * @param conn Conexión a la base de datos.
     */
    public EmpleadoController(EliminarEmpleadoVista vista, EmpleadoModel modelo, Connection conn) {
        this.vistaElminarMod = vista;
        this.modelo = modelo;
        this.conn = conn;
        cargarListaEmpleados();

        this.vistaElminarMod.agregarListenerEliminar(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarEmpleadoSeleccionado();
            }
        });

        this.vistaElminarMod.agregarListenerModificar(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirFormularioModificarEmpleado();
            }
        });
    }
    public EmpleadoController(AgregarHorarioVista vista, EmpleadoModel modelo, Connection conn) {
        this.modelo = modelo;
        this.conn = conn;

        // Cargar la lista de empleados en la vista
        try {
            List<EmpleadoDTO> empleados = modelo.obtenerEmpleadosNoDeportivos(conn);
            vista.cargarEmpleados(empleados);
        } catch (SQLException e) {
            vista.mostrarMensajeError("Error al cargar empleados: " + e.getMessage());
        }

        vista.agregarListenerAgregarHorario(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.mostrarDialogoHorario();
            }
        });

        vista.agregarListenerGuardarHorario(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarHorario(vista);
            }
        });
    }
    
    private void agregarHorario(AgregarHorarioVista vista) {
        EmpleadoDTO empleadoSeleccionado = vista.getEmpleadoSeleccionado();
        if (empleadoSeleccionado == null) {
            vista.mostrarMensajeError("Debe seleccionar un empleado.");
            return;
        }
        try {
            HorarioDTO nuevoHorario = vista.obtenerDatosHorario();

            if (!nuevoHorario.esSemanal()) {
                DayOfWeek diaSemana = nuevoHorario.getFechaEspecifica().getDayOfWeek();                       
                List<HorarioDTO> horariosActuales = empleadoSeleccionado.getHorarios(); 
                for (HorarioDTO horario : horariosActuales) {
                	System.out.println(horario.getDiaSemana().getValue() + "	" + diaSemana.getValue());
                    if (horario.esSemanal() && horario.getDiaSemana().getValue() == diaSemana.getValue()) {
                    	
                        modelo.eliminarHorario(conn,empleadoSeleccionado,horario);
                        break; 
                    }
                }
            }

            // Agregar el nuevo horario al empleado
            empleadoSeleccionado.agregarHorarioSemanal(nuevoHorario);

            // Persistir en la base de datos
            modelo.agregarHorario(conn, empleadoSeleccionado, nuevoHorario);

            vista.mostrarMensajeExito("Horario agregado correctamente.");
            vista.cerrarDialogoHorario();
        } catch (IllegalArgumentException ex) {
            vista.mostrarMensajeError(ex.getMessage());
        } catch (SQLException ex) {
            vista.mostrarMensajeError("Error al agregar el horario: " + ex.getMessage());
        }
    }

    /**
     * Método para agregar un empleado al sistema.
     */
    public void agregarEmpleado() {
        try {
            String nombre = vistaAgregar.getNombre();
            String apellido = vistaAgregar.getApellido();
            String dni = vistaAgregar.getDni();
            String telefono = vistaAgregar.getTelefono();
            LocalDate fechaNacimiento = vistaAgregar.getFechaNacimiento();
            String tipoEmpleado = vistaAgregar.getTipoEmpleado();
            String posicion = vistaAgregar.getPosicion();
            double salarioAnualBruto = vistaAgregar.getSalario();

            if (!validarNombreYApellido(nombre)) {
                vistaAgregar.mostrarMensajeError("El nombre debe tener entre 3 y 40 caracteres.");
                return;
            }

            if (!validarNombreYApellido(apellido)) {
                vistaAgregar.mostrarMensajeError("El apellido debe tener entre 3 y 40 caracteres.");
                return;
            }

            if (!validarTelefono(telefono)) {
                vistaAgregar.mostrarMensajeError("El número de teléfono debe tener exactamente 9 dígitos.");
                return;
            }

            if (!validarDni(dni)) {
                vistaAgregar.mostrarMensajeError("El DNI debe tener 8 números y una letra final.");
                return;
            }

            if (salarioAnualBruto <= 0) {
                vistaAgregar.mostrarMensajeError("El salario debe ser mayor que 0.");
                return;
            }

            EmpleadoDTO empleado = new EmpleadoDTO(nombre, apellido, dni, telefono, fechaNacimiento, tipoEmpleado, posicion, salarioAnualBruto);
            modelo.agregarEmpleado(conn, empleado);
            vistaAgregar.mostrarMensajeExito("Empleado agregado correctamente.");

        } catch (SQLException e) {
            vistaAgregar.mostrarMensajeError("Error al agregar el empleado: " + e.getMessage());
        }
    }
    /**
     * Cargar la lista de empleados desde la base de datos.
     */
    public void cargarListaEmpleados() {
        try {
            List<EmpleadoDTO> empleados = modelo.obtenerEmpleados(conn);
            vistaElminarMod.cargarEmpleados(empleados);
        } catch (SQLException e) {
            vistaElminarMod.mostrarMensajeError("Error al cargar la lista de empleados: " + e.getMessage());
        }
    }

    /**
     * Eliminar el empleado seleccionado.
     */
    public void eliminarEmpleadoSeleccionado() {
        EmpleadoDTO empleadoSeleccionado = vistaElminarMod.getEmpleadoSeleccionado();
        if (empleadoSeleccionado == null) {
            vistaElminarMod.mostrarMensajeError("Debe seleccionar un empleado para eliminar.");
            return;
        }

        try {
            modelo.eliminarEmpleado(conn, empleadoSeleccionado.getId());
            vistaElminarMod.mostrarMensajeExito("Empleado eliminado correctamente.");
            cargarListaEmpleados();
        } catch (SQLException e) {
            vistaElminarMod.mostrarMensajeError("Error al eliminar el empleado: " + e.getMessage());
        }
    }

    /**
     * Abre el formulario para modificar un empleado seleccionado.
     */
    public void abrirFormularioModificarEmpleado() {
        EmpleadoDTO empleadoSeleccionado = vistaElminarMod.getEmpleadoSeleccionado();
        if (empleadoSeleccionado == null) {
            vistaElminarMod.mostrarMensajeError("Debe seleccionar un empleado para modificar.");
            return;
        }

        ModificarEmpleadoVista vistaModificar = new ModificarEmpleadoVista();
        vistaModificar.cargarEmpleado(empleadoSeleccionado);

        vistaModificar.agregarListenerModificar(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarEmpleado(empleadoSeleccionado.getId(), vistaModificar);
            }
        });

        vistaModificar.setVisible(true);
    }

    /**
     * Modifica un empleado en el sistema.
     *
     * @param idEmpleado ID del empleado a modificar.
     * @param vistaModificar Vista que contiene los datos del empleado a modificar.
     */
    public void modificarEmpleado(String idEmpleado, ModificarEmpleadoVista vistaModificar) {
        try {
            String id = modelo.obtenerEmpleadoPorDni(conn, vistaModificar.getDni()).getId();
            EmpleadoDTO empleadoModificado = new EmpleadoDTO(
                id,
                vistaModificar.getNombre(),
                vistaModificar.getApellido(),
                vistaModificar.getDni(),
                vistaModificar.getTelefono(),
                vistaModificar.getFechaNacimiento(),
                vistaModificar.getTipoEmpleado(),
                vistaModificar.getTipoDetalle(),
                vistaModificar.getSalarioAnualBruto()
            );
            modelo.modificarEmpleado(conn, empleadoModificado);
            vistaModificar.mostrarMensajeExito("Empleado modificado correctamente.");
            vistaModificar.dispose();
            cargarListaEmpleados();

        } catch (SQLException e) {
            vistaModificar.mostrarMensajeError("Error al modificar el empleado: " + e.getMessage());
        }
    }

    /**
     * Validar que el nombre o el apellido tengan entre 3 y 40 caracteres.
     *
     * @param texto Texto a validar.
     * @return true si el texto es válido, false de lo contrario.
     */
    private boolean validarNombreYApellido(String texto) {
        return texto != null && texto.length() >= 3 && texto.length() <= 40;
    }

    /**
     * Validar que el número de teléfono tenga exactamente 9 dígitos.
     *
     * @param telefono Teléfono a validar.
     * @return true si el teléfono es válido, false de lo contrario.
     */
    private boolean validarTelefono(String telefono) {
        return telefono != null && telefono.matches("\\d{9}");
    }

    /**
     * Validar que el DNI tenga 8 números seguidos de una letra.
     *
     * @param dni DNI a validar.
     * @return true si el DNI es válido, false de lo contrario.
     */
    private boolean validarDni(String dni) {
        return dni != null && dni.matches("\\d{8}[A-Za-z]");
    }

    /**
     * Validar que el salario sea mayor que 0.
     *
     * @param salario Salario a validar.
     * @return true si el salario es mayor que 0, false de lo contrario.
     */
    private boolean validarSalario(double salario) {
        return salario >= 0;
    }
}
