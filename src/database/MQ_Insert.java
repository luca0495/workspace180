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
				+ "ntel) "
				+ "VALUES('" + name + "' , '" + surname + "' , '" + mail + "' , '" + cf + "' , '" + inq + "' , '" + pass + "' , '" + i + "' , '" + tel + "')";
		DBmanager.openConnection();
		DBmanager.executeUpdate(query);
		DBmanager.closeConnection();
	}

	
		
		public static String insertUtenteGetQuery(
				String name, 
				String surname, 
				String inq, 
				String mail,  
				String cf, 
				String tel, 
				String pass, 
				int i, 
				String typePerson
				) throws SQLException
		{
<<<<<<< HEAD
			String query = "INSERT INTO utente(nome,"
=======
			String query = 		"INSERT INTO utente("
					+ "nome,"
>>>>>>> 24214355c305881e61c5843860a2b9aac969b2d2
					+ "cognome,"
					+ "email,"
					+ "codice_fiscale,"
					+ "inquadramento,"
					+ "password,"
					+ "password_temp,"
<<<<<<< HEAD
					+ "ntel,"
					+ "tipo_utente) "
					+ "VALUES('" 	+ name 
									+ "' , '" + surname 
									+ "' , '" + mail + "' , '" 
									+ cf + "' , '" 
									+ inq + "' , '" 
									+ pass + "' , '" 
									+ i + "' , '" 
									+ tel + "' , '"
									+ typePerson +"')";
=======
					+ "ntel) "
					+ "VALUES('"		+ name 		+ "' , '" 	
										+ surname	+ "' , '"
										+ mail		+ "' , '" 
										+ cf		+ "' , '" 
										+ inq 		+ "' , '" 
										+ pass 		+ "' , '" 
										+ i 		+ "' , '" 
										+ tel 		+ "')";
>>>>>>> 24214355c305881e61c5843860a2b9aac969b2d2
			return 	query;
		}	

		
		public static void insertBooks(String q) throws SQLException
		{
			DBmanager.openConnection();
			DBmanager.executeUpdate(q);
			DBmanager.closeConnection();
		}
		
		public static void insertBooks(String name, String surname, String cat,String title) throws SQLException
		{
			String query = "INSERT INTO libro(nome_autore, cognome_autore, categoria, titolo) "
					       + "VALUES('" + name + "' , '" + surname + "' , '" + cat + "' , '" + title + "')";
			DBmanager.openConnection();
			DBmanager.executeUpdate(query);
			DBmanager.closeConnection();
		}
		
		public static String insertBooksGetQuery(
				String name, 
				String surname, 
				String cat, 
				String title) throws SQLException
		{
			String query = 		"INSERT INTO libro("
					+ "nome_autore, "
					+ "cognome_autore, "
					+ "categoria, "
					+ "titolo) "
					+ "VALUES('" 		+ name		+ "' , '" 
										+ surname	+ "' , '" 
										+ cat 		+ "' , '" 
										+ title 	+ "')";
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
