package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
		else if(col == 5)
		{
			query += "SET disponibilità = '" + input + "'";
		}
		else if(col == 6)
		{
			query += "SET prenotazioni_in_coda = '" + input + "'";
		}
		query += " WHERE codice = '" + cd + "';";
		
		DBmanager.openConnection();
		DBmanager.executeUpdate(query);
		DBmanager.closeConnection();
	}
	
	public static void updateTableLoans(String cd,String input, int col) throws SQLException
	{	
		
		String query = "UPDATE prestiti ";
		
		if(col == 0)
		{
			query += "SET codice = '" + input + "'";
		}
		else if(col == 1)
		{
			query += "SET id = '" + input + "'";
		}
		else if(col == 2)
		{
			query += "SET email = '" + input + "'";
		}
		else if(col == 3)
		{
			query += "SET data_inizio = '" + input + "'";
		}
		else if(col == 4)
		{
			query += "SET data_fine = '" + input + "'";
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

		String query1 = "UPDATE utente SET nome = '" + nome + "', cognome = '" + cognome + "' , email = '" + email + "' , inquadramento = '" + inq +
				"' , password = '" + pass + "' , ntel = '" + ntel +"' , tipo_utente = '" + tipo_utente +"' WHERE email = '" + email +"';";
    	
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

	public static String updateNewPassForgotGETQUERY (String email,String pass) {
		String q = "UPDATE utente SET password = '" + pass +"' WHERE email = '" + email +"';";
		return q;
	}
	
	public static void updateNewPassForgot(String q) throws SQLException
	{	
		//String query1 = "UPDATE utente SET password = '" + pass +"' WHERE email = '" + email +"';";
		DBmanager.openConnection();
		DBmanager.executeUpdate(q);
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

	public static String updateModUserIdGetQuery(int idus, String nome,  String cognome, String email,  String inq, String pass, String ntel,String tipo_utente) throws SQLException
	{
		String q=null;
		//int id = Integer.valueOf(idus);
		
		q = "UPDATE utente SET nome = '" + nome + "', cognome = '" + cognome + "' , email = '" + email + "' , password = '" + pass +
				"' , inquadramento = '" + inq + "' , ntel = '" + ntel +"' , tipo_utente = '" + tipo_utente +"' WHERE id = '" + idus +"';";
		return q;
	}
	

	
	public static void updateModUserIdbyQuery(String q)throws SQLException
	{		
		DBmanager.openConnection();
		DBmanager.executeUpdate(q);
		DBmanager.closeConnection();
		

	}
	
	
	
	
	
	public static String[] updateModUserId(String id, String nome,  String cognome, String email,  String inq, String pass, String ntel,String tipo_utente) throws SQLException
	{		
		
		//String query1 = "UPDATE utente SET nome = '" + nome + "', cognome = '" + cognome + "' , email = '" + email + "' , password = '" + pass +
		//		"' , inquadramento = '" + inq + "' , ntel = '" + ntel +"' , tipo_utente = '" + tipo_utente +"';";
		
		String query1 = "UPDATE utente SET nome = '" + nome + "', cognome = '" + cognome + "' , email = '" + email + "' , password = '" + pass +
				"' , inquadramento = '" + inq + "' , ntel = '" + ntel +"' , tipo_utente = '" + tipo_utente +"' WHERE id = '" + id +"';";
		
		
		DBmanager.openConnection();
		ResultSet rs = DBmanager.executeQuery(query1);
		
		List<String> results = new ArrayList<String>();
		String[] user = new String[8]; // nome,cognome,email,password,inquadramento,ntel,tipo_utente,numpren(mancante)
		
		if (!rs.isBeforeFirst()) 
		{
			results.add("Nessun Dato");
		}
		else
		{
			while(rs.next()) 
			{
				results.add(rs.getString("id"));
				results.add(rs.getString("nome"));
				results.add(rs.getString("cognome"));
				results.add(rs.getString("email"));
				results.add(rs.getString("password"));
				results.add(rs.getString("inquadramento"));
				results.add(rs.getString("ntel"));
				results.add(rs.getString("tipo_utente"));
			}
		}
		for(int i = 0; i<results.size(); i++)
		{
			user[i]=results.get(i);
		}
		
		rs.close();
		DBmanager.closeConnection();
		
		return user;
	}
	
	
	
	
}
