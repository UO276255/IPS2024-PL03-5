package uo276255.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database extends DbUtil {

    private static final String SQL_SCHEMA = "src/main/resources/schema.sql";
    private static final String SQL_SCHEMA_REMOVEDB = "src/main/resources/schemaDelete.sql";
    private static final String SQL_LOAD = "src/main/resources/data.sql";
    private static final String URL = "jdbc:hsqldb:hsql://localhost";
    
    private static Database instance = null;

    private static boolean databaseCreated = false;

    private Connection conn;

    private Database() {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            createDatabase(databaseCreated);  
            loadDatabase(); 
            System.out.println("Base de datos inicializada correctamente");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Error al inicializar la base de datos", e);
        }
    }

    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    // Método para eliminar la base de datos
    public void removeDatabase() {
        executeScript(SQL_SCHEMA_REMOVEDB);
        System.out.println("Base de datos eliminada correctamente.");
    }

    // Método para crear la base de datos
    public void createDatabase(boolean onlyOnce) {
        if (!databaseCreated || !onlyOnce) {
            executeScript(SQL_SCHEMA);
            databaseCreated = true;  // Marcar la base de datos como creada
        }
    }

    // Cargar los datos iniciales desde el archivo data.sql
    public void loadDatabase() {
        executeScript(SQL_LOAD);
        System.out.println("Datos iniciales cargados correctamente.");
    }

    // Obtener la URL de la base de datos
    @Override
    public String getUrl() {
        return URL;
    }

    // Cerrar la conexión
    public void cerrarConexion() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Conexión cerrada correctamente.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Obtener la conexión actual
    public Connection getConnection() {
        return conn;
    }
}
