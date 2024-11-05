package uo276255.modelo.accionistas;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa el modelo para gestionar accionistas.
 */
public class AccionistaModel {

    private Connection conexion;

    /**
     * Constructor que recibe una conexión a la base de datos.
     *
     * @param conexion La conexión a la base de datos.
     */
    public AccionistaModel(Connection conexion) {
        this.conexion = conexion;
    }

    /**
     * Obtiene un accionista por su DNI.
     *
     * @param dni El DNI del accionista.
     * @return Un objeto Accionista si se encuentra, de lo contrario null.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public AccionistaDTO obtenerAccionistaPorDNI(String dni) throws SQLException {
        String sql = "SELECT * FROM accionistas WHERE dni = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearAccionista(rs);
            } else {
                return null;
            }
        }
    }

    /**
     * Crea un nuevo accionista en la base de datos.
     *
     * @param accionista El objeto Accionista a crear.
     * @return El accionista creado con su ID asignado.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public AccionistaDTO crearAccionista(AccionistaDTO accionista) throws SQLException {
        String sql = "INSERT INTO accionistas (nombre, dni, telefono, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, accionista.getNombre());
            stmt.setString(2, accionista.getDni());
            stmt.setString(3, accionista.getTelefono());
            stmt.setString(4, accionista.getEmail());

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas == 0) {
                throw new SQLException("No se pudo crear el accionista.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    accionista.setIdAccionista(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("No se pudo obtener el ID del accionista creado.");
                }
            }
        }
        return accionista;
    }

    /**
     * Obtiene una lista de todos los accionistas.
     *
     * @return Una lista de objetos Accionista.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public List<AccionistaDTO> obtenerTodosLosAccionistas() throws SQLException {
        List<AccionistaDTO> accionistas = new ArrayList<>();
        String sql = "SELECT * FROM accionistas";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                accionistas.add(mapearAccionista(rs));
            }
        }
        return accionistas;
    }

    /**
     * Mapea un ResultSet a un objeto Accionista.
     *
     * @param rs El ResultSet obtenido de la consulta.
     * @return Un objeto Accionista con los datos del ResultSet.
     * @throws SQLException Si ocurre un error al leer el ResultSet.
     */
    private AccionistaDTO mapearAccionista(ResultSet rs) throws SQLException {
    	AccionistaDTO accionista = new AccionistaDTO();
        accionista.setIdAccionista(rs.getInt("id_accionista"));
        accionista.setNombre(rs.getString("nombre"));
        accionista.setDni(rs.getString("dni"));
        accionista.setTelefono(rs.getString("telefono"));
        accionista.setEmail(rs.getString("email"));
        return accionista;
    }

    /**
     * Actualiza la información de un accionista existente.
     *
     * @param accionista El objeto Accionista con los datos actualizados.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public void actualizarAccionista(AccionistaDTO accionista) throws SQLException {
        String sql = "UPDATE accionistas SET nombre = ?, telefono = ?, email = ? WHERE id_accionista = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, accionista.getNombre());
            stmt.setString(2, accionista.getTelefono());
            stmt.setString(3, accionista.getEmail());
            stmt.setInt(4, accionista.getIdAccionista());

            stmt.executeUpdate();
        }
    }

    /**
     * Elimina un accionista de la base de datos.
     *
     * @param idAccionista El ID del accionista a eliminar.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public void eliminarAccionista(int idAccionista) throws SQLException {
        String sql = "DELETE FROM accionistas WHERE id_accionista = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idAccionista);
            stmt.executeUpdate();
        }
    }

    /**
     * Actualiza la tabla intermedia accionista_campaña al crear una nueva campaña.
     * Establece el maximo de cada accionista al número de acciones que posee actualmente.
     *
     * @param idCampaña El ID de la nueva campaña creada.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public void actualizarAcciones(int idCampaña) throws SQLException {
        String sqlObtenerAccionistas = "SELECT id_accionista FROM accionistas";
        String sqlContarAcciones = "SELECT COUNT(*) AS total_acciones FROM acciones WHERE id_accionista = ?";
        String sqlInsertarAccionistaCampaña = "INSERT INTO accionista_campaña (id_accionista, id_campaña, max_acciones) VALUES (?, ?, ?)";

        try (Statement stmtAccionistas = conexion.createStatement();
             ResultSet rsAccionistas = stmtAccionistas.executeQuery(sqlObtenerAccionistas);
             PreparedStatement stmtContarAcciones = conexion.prepareStatement(sqlContarAcciones);
             PreparedStatement stmtInsertar = conexion.prepareStatement(sqlInsertarAccionistaCampaña)) {

        	conexion.setAutoCommit(false);

            while (rsAccionistas.next()) {
                int idAccionista = rsAccionistas.getInt("id_accionista");

                // Obtener el número de acciones que posee el accionista
                stmtContarAcciones.setInt(1, idAccionista);
                ResultSet rsContar = stmtContarAcciones.executeQuery();
                int numeroAcciones = 0;
                if (rsContar.next()) {
                    numeroAcciones = rsContar.getInt("total_acciones");
                }
                rsContar.close();

                // Insertar registro en accionista_campaña
                stmtInsertar.setInt(1, idAccionista);
                stmtInsertar.setInt(2, idCampaña);
                stmtInsertar.setInt(3, numeroAcciones);
                stmtInsertar.executeUpdate();
            }

            conexion.commit();

        } catch (SQLException e) {
        	conexion.rollback();
            throw new SQLException("Error al actualizar acciones de los accionistas: " + e.getMessage(), e);
        } finally {
        	conexion.setAutoCommit(true);
        }
    }
}
