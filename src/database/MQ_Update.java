package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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
	
	public static void updateModUser1(List<String> r,String input, int col) throws SQLException
	{	
		
		String query = "UPDATE utente ";
		
		if(col == 2)
		{
			query += "SET nome = '" + input + "'";
		}
		else if(col == 3)
		{
			query += "SET cognome = '" + input + "'";
		}
		else if(col == 4)
		{
			query += "SET email = '" + input + "'";
		}
		else if(col == 6)
		{
			query += "SET inquadramento = '" + input + "'";
		}
		else if(col == 7)
		{
			query += "SET password = '" + input + "'";
		}
		else if(col == 9)
		{
			query += "SET ntel = '" + input + "'";
		}
		else if(col == 10)
		{
			query += "SET tipo_utente = '" + input + "'";
		}
		query += " WHERE nome = '" + r.get(1) + "' cognome = '" + r.get(2) + "' email = '" + r.get(3) + "' inquadramento = '" + r.get(5) + "' password = '" + r.get(6) + "' ntel = '" + r.get(8) + "' tipo_utente = '" + r.get(9) + "';";
		
		DBmanager.openConnection();
		DBmanager.executeUpdate(query);
		DBmanager.closeConnection();
	}
	
	public static void updateModUser(String nome,  String cognome, String email,  String inq, String pass, String ntel,String tipo_utente) throws SQLException
	{	

		String query1 = "UPDATE utente SET nome = '" + nome + "', cognome = '" + cognome + "' , email = '" + email + "' , password = '" + pass +
				"' , inquadramento = '" + inq + "' , ntel = '" + ntel +"' , tipo_utente = '" + tipo_utente +"';";
    	
		DBmanager.openConnection();
		DBmanager.executeUpdate(query1);
		DBmanager.closeConnection();
	}
	
	public static void updatePassForgot(String email,String pass, int i) throws SQLException
	{	

		String query1 = "UPDATE utente SET email = '" + email + "', password = '" + pass + "' , password_temp = '" + i + "';";
    	
		DBmanager.openConnection();
		DBmanager.executeUpdate(query1);
		DBmanager.closeConnection();
	}

	public static void updateLoginTry(String email, int tentativi) throws SQLException
	{	

		String tent= String.valueOf(tentativi);
		String query1 = "UPDATE utente SET password_temp_tentativi = '" + tent + "' WHERE email = '" + email + "';";
		
    	
		DBmanager.openConnection();
		DBmanager.executeUpdate(query1);
		DBmanager.closeConnection();
	}
	
	
	
	
}
