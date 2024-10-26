package uo276255.modelo.acciones.campaña;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Clase que representa el modelo para gestionar campañas.
 */
public class CampañaModel {
    private static final String INSERT_CAMPAÑA =  "INSERT INTO campañas (id_campaña,nombre, numeroAcciones, fase,activa) VALUES (?,?,?,?,?)";
    private static final String GET_ACTIVAS =  "SELECT COUNT(*) FROM campañas WHERE activa = true";
    private static final String UPDATE_FASE = "UPDATE campañas SET fase = fase + 1 WHERE activa = true";
    private static final String GET_FASE = "SELECT fase FROM campañas WHERE activa = true";
    private static final String CLOSE_FASE = "UPDATE campañas SET activa = false WHERE activa = true";


    private Connection conn;

    /**
     * Constructor que recibe la conexión a la base de datos.
     *
     * @param conn La conexión a la base de datos.
     */
    public CampañaModel(Connection conn) {
        this.conn = conn;
    }

    /**
     * Verifica si existe una campaña activa en la base de datos.
     *
     * @return true si existe una campaña activa, false en caso contrario.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public boolean existeCampañaActiva() throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(GET_ACTIVAS);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
            return false;
        }
    }

    /**
     * Crea una nueva campaña en la base de datos.
     *
     * @param nombre          El nombre de la campaña.
     * @param maximoAcciones  El número máximo de acciones disponibles.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public void crearCampaña(String nombre, int maximoAcciones) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_CAMPAÑA)) {
        	stmt.setString(1, UUID.randomUUID().toString());
            stmt.setString(2, nombre);
            stmt.setInt(3, maximoAcciones);
            stmt.setInt(4, 1); // Iniciamos la campaña en la fase 1
            stmt.setBoolean(5, true); // La campaña está activa
            stmt.executeUpdate();
        }
    }

    /**
     * Aumenta la fase actual de la campaña activa.
     * Si la fase actual es 3, cierra la campaña.
     *
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public void aumentarFaseCampaña() throws SQLException {
        int faseActual = obtenerFaseActual();
        if (faseActual == 3) {
            cerrarCampaña();
        } else {
            try (PreparedStatement stmt = conn.prepareStatement(UPDATE_FASE)) {
                stmt.executeUpdate();
            }
        }
    }

    /**
     * Obtiene la fase actual de la campaña activa.
     *
     * @return El número de la fase actual, o -1 si no hay campaña activa.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public int obtenerFaseActual() throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(GET_FASE);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("fase");
            }
            return -1;
        }
    }

    /**
     * Cierra la campaña activa, marcándola como inactiva.
     *
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public void cerrarCampaña() throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(CLOSE_FASE)) {
            stmt.executeUpdate();
        }
    }
}
