package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MQ_Update {


	public static void updateTableCliente(String cd,String input, int col) throws SQLException
	{	
		
		String query = "UPDATE libro ";
		
		if(col == 1)
		{
			query += "SET nome_autore = '" + input + "'";
		}
		else if(col == 2)
		{
			query += "SET cognome_autore = '" + input + "'";
		}
		else if(col == 3)
		{
			query += "SET categoria = '" + input + "'";
		}
		else if(col == 4)
		{
			query += "SET titolo = '" + input + "'";
		}
		query += " WHERE codice = '" + cd + "';";
		
		DBmanager.openConnection();
		DBmanager.executeUpdate(query);
		DBmanager.closeConnection();
	}
}
