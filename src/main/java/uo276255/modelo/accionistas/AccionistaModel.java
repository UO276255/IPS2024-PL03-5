package uo276255.modelo.accionistas;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import uo276255.modelo.acciones.accion.AccionDTO;

/**
 * Clase que representa el modelo para gestionar accionistas.
 */
public class AccionistaModel {

    private Connection conexion;
    private static final String GET_LAST = "SELECT MAX(CAST(id_accionista AS INTEGER)) AS max_id FROM accionistas";

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
    public void crearAccionista(AccionistaDTO accionista) throws SQLException {
        String sql = "INSERT INTO accionistas (id_accionista,nombre, dni, telefono, email) VALUES (?,?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        	stmt.setInt(1, obtenerNuevoId());
            stmt.setString(2, accionista.getNombre());
            stmt.setString(3, accionista.getDni());
            stmt.setString(4, accionista.getTelefono());
            stmt.setString(5, accionista.getEmail());

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas == 0) {
                throw new SQLException("No se pudo crear el accionista.");
            }

        }
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
    
    /**
     * Obtiene el siguiente ID para una nueva campaña.
     *
     * @return El nuevo ID de campaña.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    private int obtenerNuevoId() throws SQLException {
        int nuevoId = 1; // Valor inicial por si no hay campañas previas
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(GET_LAST)) {
            if (rs.next()) {
                int maxId = rs.getInt("max_id");
                if (!rs.wasNull()) {
                    nuevoId = maxId + 1;
                }
            }
        }
        return nuevoId;
    }

    public AccionistaDTO obtenerUltimoAccionista() throws SQLException {
        String sql = "SELECT * FROM accionistas ORDER BY id_accionista DESC LIMIT 1";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                AccionistaDTO accionista = new AccionistaDTO();
                accionista.setIdAccionista(rs.getInt("id_accionista"));
                accionista.setNombre(rs.getString("nombre"));
                accionista.setDni(rs.getString("dni"));
                accionista.setTelefono(rs.getString("telefono"));
                accionista.setEmail(rs.getString("email"));
                return accionista;
            } else {
                return null; 
            }
        }
    }
    
    
    /**
     * Obtiene las acciones no en venta de un accionista.
     *
     * @param idAccionista El ID del accionista.
     * @return Una lista de AccionDTO.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public List<AccionDTO> obtenerAccionesNoEnVentaPorAccionista(int idAccionista) throws SQLException {
        String sql = "SELECT * FROM acciones WHERE id_accionista = ? AND en_venta = FALSE";
        List<AccionDTO> acciones = new ArrayList<>();
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idAccionista);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    AccionDTO accion = new AccionDTO();
                    accion.setIdAccion(rs.getString("id_accion"));
                    accion.setIdCampaña(rs.getInt("id_campaña"));
                    accion.setIdAccionista(rs.getInt("id_accionista"));
                    accion.setEnVenta(rs.getBoolean("en_venta"));
                    acciones.add(accion);
                }
            }
        }
        return acciones;
    }

    /**
     * Marca las acciones especificadas como en venta.
     *
     * @param idsAcciones Un arreglo de IDs de acciones.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public void marcarAccionesEnVenta(String[] idsAcciones) throws SQLException {
        String sql = "UPDATE acciones SET en_venta = TRUE WHERE id_accion = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            for (String idAccion : idsAcciones) {
                stmt.setString(1, idAccion);
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

}
