package uo276255.modelo.acciones.accion;
import uo276255.modelo.acciones.campaña.CampañaDTO;
import uo276255.modelo.accionistas.AccionistaDTO;

import java.sql.*;

/**
 * Clase que representa el modelo para la compra de acciones.
 */
public class CompraAccionesModel {

    private Connection conexion;

    /**
     * Constructor que recibe una conexión a la base de datos.
     *
     * @param conexion La conexión a la base de datos.
     */
    public CompraAccionesModel(Connection conexion) {
        this.conexion = conexion;
    }

    /**
     * Obtiene la campaña activa actual.
     *
     * @return Un objeto Campaña si existe una campaña activa, de lo contrario null.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public CampañaDTO obtenerCampañaActiva() throws SQLException {
        String sql = "SELECT * FROM campañas WHERE activa = TRUE";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return mapearCampaña(rs);
            } else {
                return null;
            }
        }
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
     * Cuenta la cantidad de acciones que posee un accionista en una campaña.
     *
     * @param idAccionista El ID del accionista.
     * @param idCampaña    El ID de la campaña.
     * @return El número de acciones que posee el accionista en la campaña.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public int contarAccionesAccionistaEnCampaña(int idAccionista, int idCampaña) throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM acciones WHERE id_accionista = ? AND id_campaña = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idAccionista);
            stmt.setInt(2, idCampaña);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("total");
            } else {
                return 0;
            }
        }
    }

    /**
     * Registra la compra de acciones por parte de un accionista.
     *
     * @param accionista       El accionista que compra las acciones.
     * @param campaña          La campaña en la que se compran las acciones.
     * @param cantidadAcciones La cantidad de acciones a comprar.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public void comprarAcciones(AccionistaDTO accionista, CampañaDTO campaña, int cantidadAcciones) throws SQLException {
        String sqlInsertAccion = "INSERT INTO acciones (id_accion, id_empleado, id_campaña, id_accionista, en_venta) VALUES (?, NULL, ?, ?, FALSE)";
        String sqlActualizarNumeroAcciones = "UPDATE campañas SET numeroAcciones = numeroAcciones - ? WHERE id_campaña = ?";

        try (PreparedStatement stmtInsertAccion = conexion.prepareStatement(sqlInsertAccion);
             PreparedStatement stmtActualizarNumeroAcciones = conexion.prepareStatement(sqlActualizarNumeroAcciones)) {

            conexion.setAutoCommit(false);

            // Verificar si hay suficientes acciones disponibles
            if (campaña.getNumeroAcciones() < cantidadAcciones) {
                throw new SQLException("No hay suficientes acciones disponibles en la campaña.");
            }

            // Insertar las acciones
            for (int i = 0; i < cantidadAcciones; i++) {
                stmtInsertAccion.setString(1, generarIdAccion());
                stmtInsertAccion.setInt(2, campaña.getIdCampaña());
                stmtInsertAccion.setInt(3, accionista.getIdAccionista());
                stmtInsertAccion.executeUpdate();
            }

            // Actualizar el número de acciones disponibles en la campaña
            stmtActualizarNumeroAcciones.setInt(1, cantidadAcciones);
            stmtActualizarNumeroAcciones.setInt(2, campaña.getIdCampaña());
            stmtActualizarNumeroAcciones.executeUpdate();

            conexion.commit();
        } catch (SQLException e) {
            conexion.rollback();
            throw e;
        } finally {
            conexion.setAutoCommit(true);
        }
    }

    /**
     * Mapea un ResultSet a un objeto Campaña.
     *
     * @param rs El ResultSet obtenido de la consulta.
     * @return Un objeto Campaña con los datos del ResultSet.
     * @throws SQLException Si ocurre un error al leer el ResultSet.
     */
    private CampañaDTO mapearCampaña(ResultSet rs) throws SQLException {
    	CampañaDTO campaña = new CampañaDTO();
        campaña.setIdCampaña(rs.getInt("id_campaña"));
        campaña.setNombre(rs.getString("nombre"));
        campaña.setFase(rs.getInt("fase"));
        campaña.setNumeroAcciones(rs.getInt("numeroAcciones"));
        campaña.setActiva(rs.getBoolean("activa"));
        return campaña;
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
     * Verifica si hay acciones disponibles para comprar en la campaña activa.
     *
     * @return true si hay acciones disponibles, false en caso contrario.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public boolean hayAccionesEnVenta() throws SQLException {
        String sql = "SELECT numeroAcciones FROM campañas WHERE activa = TRUE";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                int numeroAcciones = rs.getInt("numeroAcciones");
                return numeroAcciones > 0;
            } else {
                return false;
            }
        }
    }
    
    /**
     * Genera un ID único para una acción.
     *
     * @return Un String representando el ID de la acción.
     */
    private String generarIdAccion() {
        // Puedes implementar una lógica para generar IDs únicos para las acciones
        return "ACCION_" + System.currentTimeMillis() + Math.random();
    }
}
