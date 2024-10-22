package uo276255.modelo.empleado;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Types;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import uo276255.modelo.horario.HorarioDTO;

/**
 * Modelo de la entidad Empleado que maneja la interacción con la base de datos.
 */
public class EmpleadoModel {

    private static final String INSERT = "INSERT INTO empleados (nombre, apellido, dni, telefono, fecha_nacimiento, tipo_empleado, tipo_detalle, salario_anual_bruto) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String REMOVE = "DELETE FROM empleados WHERE id_empleado = ?;";
    private static final String MODIFY = "UPDATE empleados SET nombre = ?, apellido = ?, dni = ?, telefono = ?, fecha_nacimiento = ?, tipo_empleado = ?, tipo_detalle = ?, salario_anual_bruto = ? WHERE id_empleado = ?;";
    private static final String SELECT_BY_ID = "SELECT nombre, apellido, dni, telefono, fecha_nacimiento, tipo_empleado, tipo_detalle, salario_anual_bruto FROM empleados WHERE id_empleado = ?;";
    private static final String SELECT_ALL = "SELECT id_empleado, nombre, apellido, dni, telefono, fecha_nacimiento, tipo_empleado, tipo_detalle, salario_anual_bruto FROM empleados;";
    private static final String SELECT_BY_DNI = "SELECT id_empleado, nombre, apellido, dni, telefono, fecha_nacimiento, tipo_empleado, tipo_detalle, salario_anual_bruto FROM empleados WHERE dni = ?;";
    private static final String SELECT_NO_DEPORTIVOS = "SELECT id_empleado, nombre, apellido, dni, telefono, fecha_nacimiento, tipo_empleado, tipo_detalle, salario_anual_bruto FROM empleados WHERE tipo_empleado = 'No Deportivo';";
    private static final String OBTENER_HORARIO = "SELECT hora_inicio, hora_fin, es_semanal, dia_semana, fecha_especifica FROM horarios WHERE id_empleado = ?";
    private static final String INSERT_HORARIO = "INSERT INTO horarios (id_empleado, hora_inicio, hora_fin, es_semanal, dia_semana, fecha_especifica) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String ELIMINAR_HORARIO =  "DELETE FROM horarios WHERE id_empleado = ? AND hora_inicio = ? AND hora_fin = ? AND es_semanal = ? AND (dia_semana = ? OR fecha_especifica = ?)";

    /**
     * Obtiene un empleado por su ID.
     *
     * @param conn Conexión a la base de datos.
     * @param idEmpleado ID del empleado.
     * @return Un objeto EmpleadoDTO con los datos del empleado.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    public EmpleadoDTO obtenerEmpleadoId(Connection conn, int idEmpleado) throws SQLException {
        EmpleadoDTO empleado = null;

        try (PreparedStatement preparedStatement = conn.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setInt(1, idEmpleado);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                empleado = new EmpleadoDTO(
                    resultSet.getString("id_empleado"),
                    resultSet.getString("nombre"),
                    resultSet.getString("apellido"),
                    resultSet.getString("dni"),
                    resultSet.getString("telefono"),
                    resultSet.getDate("fecha_nacimiento").toLocalDate(),
                    resultSet.getString("tipo_empleado"),
                    resultSet.getString("tipo_detalle"),
                    resultSet.getDouble("salario_anual_bruto")
                );
            }
        }

        return empleado; 
    }

    /**
     * Obtiene un empleado por su DNI.
     *
     * @param conn Conexión a la base de datos.
     * @param dni DNI del empleado.
     * @return Un objeto EmpleadoDTO con los datos del empleado, o null si no se encuentra.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    public EmpleadoDTO obtenerEmpleadoPorDni(Connection conn, String dni) throws SQLException {
        EmpleadoDTO empleado = null;

        try (PreparedStatement preparedStatement = conn.prepareStatement(SELECT_BY_DNI)) {
            preparedStatement.setString(1, dni);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                empleado = new EmpleadoDTO(
                    resultSet.getString("id_empleado"),
                    resultSet.getString("nombre"),
                    resultSet.getString("apellido"),
                    resultSet.getString("dni"),
                    resultSet.getString("telefono"),
                    resultSet.getDate("fecha_nacimiento").toLocalDate(),
                    resultSet.getString("tipo_empleado"),
                    resultSet.getString("tipo_detalle"),
                    resultSet.getDouble("salario_anual_bruto")
                );
            }
        }

        return empleado;
    }

    /**
     * Obtiene una lista de todos los empleados.
     *
     * @param conn Conexión a la base de datos.
     * @return Una lista de objetos EmpleadoDTO con los datos de todos los empleados.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    public List<EmpleadoDTO> obtenerEmpleados(Connection conn) throws SQLException {
        List<EmpleadoDTO> empleados = new ArrayList<>();

        try (PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                EmpleadoDTO empleado = new EmpleadoDTO(
                    resultSet.getString("id_empleado"),
                    resultSet.getString("nombre"),
                    resultSet.getString("apellido"),
                    resultSet.getString("dni"),
                    resultSet.getString("telefono"),
                    resultSet.getDate("fecha_nacimiento").toLocalDate(),
                    resultSet.getString("tipo_empleado"),
                    resultSet.getString("tipo_detalle"),
                    resultSet.getDouble("salario_anual_bruto")
                );
                empleados.add(empleado);
            }
        }

        return empleados;  
    }

    /**
     * Agrega un empleado a la base de datos.
     *
     * @param conn Conexión a la base de datos.
     * @param empleado Objeto EmpleadoDTO con los datos del empleado a agregar.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    public void agregarEmpleado(Connection conn, EmpleadoDTO empleado) throws SQLException {
        try (PreparedStatement preparedStatement = conn.prepareStatement(INSERT)) {
            mapEmpleadoToPreparedStatement(preparedStatement, empleado);
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Elimina un empleado de la base de datos por su ID.
     *
     * @param conn Conexión a la base de datos.
     * @param idEmpleado ID del empleado a eliminar.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    public void eliminarEmpleado(Connection conn, String idEmpleado) throws SQLException {
        try (PreparedStatement preparedStatement = conn.prepareStatement(REMOVE)) {
            preparedStatement.setString(1, idEmpleado);
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Modifica los datos de un empleado en la base de datos.
     *
     * @param conn Conexión a la base de datos.
     * @param empleado Objeto EmpleadoDTO con los nuevos datos del empleado.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    public void modificarEmpleado(Connection conn, EmpleadoDTO empleado) throws SQLException {
        try (PreparedStatement preparedStatement = conn.prepareStatement(MODIFY)) {
            preparedStatement.setString(1, empleado.getNombre());
            preparedStatement.setString(2, empleado.getApellido());
            preparedStatement.setString(3, empleado.getDni());
            preparedStatement.setString(4, empleado.getTelefono());
            preparedStatement.setDate(5, java.sql.Date.valueOf(empleado.getFechaNacimiento()));
            preparedStatement.setString(6, empleado.getTipoEmpleado());
            preparedStatement.setString(7, empleado.getTipoDetalle());
            preparedStatement.setDouble(8, empleado.getSalarioAnualBruto());
            preparedStatement.setString(9, empleado.getId());
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Mapea los datos del empleado a un PreparedStatement para su uso en consultas SQL.
     *
     * @param preparedStatement PreparedStatement en el que se mapearán los datos.
     * @param empleado Objeto EmpleadoDTO con los datos del empleado.
     * @throws SQLException Si ocurre un error al mapear los datos.
     */
    private void mapEmpleadoToPreparedStatement(PreparedStatement preparedStatement, EmpleadoDTO empleado) throws SQLException {
        preparedStatement.setString(1, empleado.getNombre());
        preparedStatement.setString(2, empleado.getApellido());
        preparedStatement.setString(3, empleado.getDni());
        preparedStatement.setString(4, empleado.getTelefono());

        LocalDate fechaNacimiento = empleado.getFechaNacimiento();
        if (fechaNacimiento != null) {
            preparedStatement.setDate(5, Date.valueOf(fechaNacimiento));
        } else {
            preparedStatement.setDate(5, null);
        }

        preparedStatement.setString(6, empleado.getTipoEmpleado());
        preparedStatement.setString(7, empleado.getTipoDetalle());
        preparedStatement.setDouble(8, empleado.getSalarioAnualBruto());
    }
    
    public void agregarHorario(Connection conn, EmpleadoDTO empleado, HorarioDTO horario) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_HORARIO)) {
            stmt.setInt(1, Integer.parseInt(empleado.getId()));
            stmt.setTime(2, Time.valueOf(horario.getHoraInicio()));
            stmt.setTime(3, Time.valueOf(horario.getHoraFin()));
            stmt.setBoolean(4, horario.esSemanal());
            if (horario.esSemanal()) {
                stmt.setInt(5, horario.getDiaSemana().getValue());
                stmt.setNull(6, Types.DATE);
            } else {
                stmt.setNull(5, Types.INTEGER);
                stmt.setDate(6, Date.valueOf(horario.getFechaEspecifica()));
            }
            stmt.executeUpdate();
        }
    }
    
    public void eliminarHorario(Connection conn, EmpleadoDTO empleado, HorarioDTO horario) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(ELIMINAR_HORARIO)) {
            stmt.setInt(1, Integer.parseInt(empleado.getId()));
            stmt.setTime(2, Time.valueOf(horario.getHoraInicio()));
            stmt.setTime(3, Time.valueOf(horario.getHoraFin()));
            stmt.setBoolean(4, horario.esSemanal());
            if (horario.esSemanal()) {
                stmt.setInt(5, horario.getDiaSemana().getValue());
                stmt.setNull(6, Types.DATE);
            } else {
                stmt.setNull(5, Types.INTEGER);
                stmt.setDate(6, Date.valueOf(horario.getFechaEspecifica()));
            }
            stmt.executeUpdate();
        }
    }

    
    public List<HorarioDTO> obtenerHorariosEmpleado(Connection conn, EmpleadoDTO empleado) throws SQLException {
        List<HorarioDTO> horarios = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(OBTENER_HORARIO)) {
            stmt.setInt(1, Integer.parseInt(empleado.getId()));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                LocalTime horaInicio = rs.getTime("hora_inicio").toLocalTime();
                LocalTime horaFin = rs.getTime("hora_fin").toLocalTime();
                boolean esSemanal = rs.getBoolean("es_semanal");
                if (esSemanal) {
                    DayOfWeek diaSemana = DayOfWeek.of(rs.getInt("dia_semana"));
                    horarios.add(new HorarioDTO(diaSemana, horaInicio, horaFin));
                } else {
                    LocalDate fechaEspecifica = rs.getDate("fecha_especifica").toLocalDate();
                    horarios.add(new HorarioDTO(fechaEspecifica, horaInicio, horaFin));
                }
            }
        }
        return horarios;
    }

	public List<EmpleadoDTO> obtenerEmpleadosNoDeportivos(Connection conn) throws SQLException {
        List<EmpleadoDTO> empleados = new ArrayList<>();

        try (PreparedStatement preparedStatement = conn.prepareStatement(SELECT_NO_DEPORTIVOS)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                EmpleadoDTO empleado = new EmpleadoDTO(
                    resultSet.getString("id_empleado"),
                    resultSet.getString("nombre"),
                    resultSet.getString("apellido"),
                    resultSet.getString("dni"),
                    resultSet.getString("telefono"),
                    resultSet.getDate("fecha_nacimiento").toLocalDate(),
                    resultSet.getString("tipo_empleado"),
                    resultSet.getString("tipo_detalle"),
                    resultSet.getDouble("salario_anual_bruto")
                );
                empleados.add(empleado);
            }
        }

        return empleados;  
	}

    
}
