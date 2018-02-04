package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class DBmanager {
	//CAMPI
	private static Connection connection;
	//METODI
	public static Connection openConnection1() 			throws SQLException
	{
	
		String url ="jdbc:postgresql://localhost:5432/schoolib";
		String username="postgres";
		String password="postgres";
		return connection = DriverManager.getConnection(url,username,password);
	}
	
	public static void 		openConnection() 			throws SQLException
	{
		
		String url ="jdbc:postgresql://localhost:5432/schoolib";
		String username="postgres";
		String password="postgres";
		connection = DriverManager.getConnection(url,username,password);
	}
	
	public static void 		closeConnection() 			throws SQLException
	{	
		connection.close();
	}
	public static void 		execute(String query) 		throws SQLException
	{
		Statement stmt = connection.createStatement();
		stmt.execute(query);
		// rs ?
	}
	public static ResultSet executeQuery(String query) 	throws SQLException
	{
		//returns one ResultSet object.
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		return rs;
	}
	public static void 		executeUpdate(String query) throws SQLException
	{

		PreparedStatement stmt = connection.prepareStatement(query);
		stmt.executeUpdate();
	}	
	public static void 		executeUpdate1(Statement stmt2) throws SQLException
	{
		
		PreparedStatement stmt = connection.prepareStatement(null);
		stmt.executeUpdate();
	}
	public static Connection getConnection(String string, String string2, String string3) throws SQLException {
		
		String url = 		"jdbc:postgresql://localhost:5432/schoolib";
		String username=	"postgres";
		String password=	"postgres";
		
		return connection = DriverManager.getConnection(url,username,password);
		
	}
	
}
