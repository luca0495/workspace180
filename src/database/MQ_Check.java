package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MQ_Check {

	public static String selectCFexist(String cf) throws SQLException
	{
		String query = "SELECT codice_fiscale FROM utente WHERE codice_fiscale = '" + cf + "';";
		DBmanager.openConnection();
		ResultSet rs = DBmanager.executeQuery(query);
		
		String results = new String();
		
		if (!rs.isBeforeFirst()) 
		{ 
			results = "No Data";
		}
		else
		{
			rs.next();
			results = rs.getString("codice_fiscale");
		}
		
		rs.close();
		DBmanager.closeConnection();
		
		return results;
	}
	
	public static String selectMail(String mail) throws SQLException
	{
		String query = "SELECT codice_fiscale FROM utente WHERE email = '" + mail + "';";
		DBmanager.openConnection();
		ResultSet rs = DBmanager.executeQuery(query);
		
		String datiCliente = new String();
		
		if (!rs.isBeforeFirst()) 
		{ 
			datiCliente = "No Data";
		}
		else
		{
			rs.next();
			datiCliente = rs.getString("codice_fiscale");
		}
		
		rs.close();
		DBmanager.closeConnection();
		
		return datiCliente;
	}
	
	
}
