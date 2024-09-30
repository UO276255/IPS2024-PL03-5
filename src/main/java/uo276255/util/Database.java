package uo276255.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Database extends DbUtil {

	private static final String SQL_SCHEMA = "src/main/resources/schema.sql";
	private static final String SQL_SCHEMA_REMOVEDB = "src/main/resources/schemaDelete.sql";
	private static final String SQL_LOAD = "src/main/resources/data.sql";
    private static final String URL = "jdbc:hsqldb:hsql://localhost";	
	private static boolean databaseCreated=false;
	
	public Database() {
		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			@SuppressWarnings("unused")
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			createDatabase(databaseCreated);
			loadDatabase();
			System.out.println("OK----------------------");			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();//
		}
	}
	public void removeDatabase() {
		// TODO Auto-generated method stub
		executeScript(SQL_SCHEMA_REMOVEDB);
	}


	public void createDatabase(boolean onlyOnce) {
		//actua como singleton si onlyOnce=true: solo la primera vez que se instancia para mejorar rendimiento en pruebas
		if (!databaseCreated || !onlyOnce) { 
			executeScript(SQL_SCHEMA);
			databaseCreated=true; //NOSONAR
		}
	}

	/** 
	 * Carga de datos iniciales a partir del script data.sql en src/main/properties
	 * (si onlyOnce=true solo ejecutara el script la primera vez
	 */
	public void loadDatabase() {
		executeScript(SQL_LOAD);
	}
	
	@Override
	public String getUrl() {
		return URL;
	}
}