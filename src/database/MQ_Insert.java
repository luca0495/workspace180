package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MQ_Insert {

	public static void insertUtente(String query) throws SQLException
	{
		DBmanager.openConnection();
		DBmanager.executeUpdate(query);
		DBmanager.closeConnection();
	}
	
	public static void insertUtente(
			String name, 
			String surname, 
			String inq, 
			String mail,  
			String cf, 
			String tel, 
			String pass, 
			int i) throws SQLException
	{
		String query = 		"INSERT INTO utente(nome,"
				+ "cognome,"
				+ "email,"
				+ "codice_fiscale,"
				+ "inquadramento,"
				+ "password,"
				+ "password_temp,"
				+ "ntel,) "
				+ "password_temp_tentativi) "
				+ "VALUES('" + name + "' , '" + surname + "' , '" + mail + "' , '" + cf + "' , '" + inq + "' , '" + pass + "' , '" + i + "' , '" + tel + "', '" + 0 + "')";
		DBmanager.openConnection();
		DBmanager.executeUpdate(query);
		DBmanager.closeConnection();
	}

	
		
		public static String insertUtenteGetQuery(
				int idus,
				String name, 
				String surname, 
				String inq, 
				String mail,  
				String cf, 
				String tel, 
				String pass,	
				int i, 
				int tentativi,
				String typePerson
				) throws SQLException
		{

		
			String query = 		"INSERT INTO utente("
					+ "nome,"
					+ "cognome,"
					+ "email,"
					+ "codice_fiscale,"
					+ "inquadramento,"
					+ "password,"
					+ "password_temp,"
					+ "password_temp_tentativi,"
					+ "ntel,"
					+ "tipo_utente ) "
					+ "VALUES('" 	+ name 
									+ "' , '" + surname 
									+ "' , '" + mail + "' , '" 
									+ cf + "' , '" 
									+ inq + "' , '" 
									+ pass + "' , '" 
									+ i + "' , '" 
									+ tentativi + "','"
									+ tel + "' , '"
									+ typePerson +"')";


			return 	query;
		}	

		
		public static void insertBooks(String q) throws SQLException
		{
			DBmanager.openConnection();
			DBmanager.executeUpdate(q);
			DBmanager.closeConnection();
		}
		
		public static void insertBooks(String name, String surname, String cat,String title,String disp,int pren_cod) throws SQLException
		{
			String query = "INSERT INTO libro(nome_autore, cognome_autore, categoria, titolo,disponibilità,prenotazioni_in_coda) "
					       + "VALUES('" + name + "' , '" + surname + "' , '" + cat + "' , '" + title + "', '" + disp + "' , '" + pren_cod + "')";
			DBmanager.openConnection();
			DBmanager.executeUpdate(query);
			DBmanager.closeConnection();
		}
		
		public static String insertBooksGetQuery(
				String name, 
				String surname, 
				String cat, 
				String title,
				String disp,
				int pren_cod) throws SQLException
		{
			String query = 		"INSERT INTO libro("
					+ "nome_autore, "
					+ "cognome_autore, "
					+ "categoria, "
					+ "titolo, "
					+ "disponibilità, "
					+ "prenotazioni_in_coda) "
					+ "VALUES('" 		+ name		+ "' , '" 
										+ surname	+ "' , '" 
										+ cat 		+ "' , '" 
										+ title 	+ "', '"
									    + disp 	    + "', '"          
									    + pren_cod 	+ "')";
			return 	query;
		}

		
/*
public static void insertPassTemp(int x) throws SQLException
{  // mettere null tutti
	String query = "INSERT INTO utente(password_temp) VALUES("+x+")";
	
	DBmanager.openConnection();
	DBmanager.executeUpdate(query);
	DBmanager.closeConnection();
}
*/
}
