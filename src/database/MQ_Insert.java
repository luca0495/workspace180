package database;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.sql.SQLException;


public class MQ_Insert {

	public static void insertUtente(String query) throws SQLException
	{
		DBmanager.openConnection();
		DBmanager.executeUpdate(query);
		DBmanager.closeConnection();
	}
	
	/**
	 * Questo metodo inserisce i dati utente nella tabella utente
	 * @param name
	 * @param surname
	 * @param inq
	 * @param mail
	 * @param cf
	 * @param tel
	 * @param pass
	 * @param i
	 * @throws SQLException
	 */
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
		
		/**
		 * Questo metodo inserisce i dati dei libri nella tabella libro
		 * @param codice
		 * @param name
		 * @param surname
		 * @param cat
		 * @param title
		 * @param disp
		 * @param pren_cod
		 * @return
		 * @throws SQLException
		 */
		public static String insertBooksGetQuery(
				int codice,
				String name, 
				String surname, 
				String cat, 
				String title,
				String disp,
				int pren_cod) throws SQLException
		{
			String query = 		"INSERT INTO libro("
					+ "codice, "
					+ "nome_autore, "
					+ "cognome_autore, "
					+ "categoria, "
					+ "titolo, "
					+ "disponibilità, "
					+ "prenotazioni_in_coda) "
					+ "VALUES('" 		+ codice	+ "' , '"
										+ name		+ "' , '" 
										+ surname	+ "' , '" 
										+ cat 		+ "' , '" 
										+ title 	+ "', '"
									    + disp 	    + "', '"          
									    + pren_cod 	+ "')";
			return 	query;
		}

		public static void insertBooking(String query) throws SQLException
		{
			DBmanager.openConnection();
			DBmanager.executeUpdate(query);
			DBmanager.closeConnection();
		}
		
		/**
		 * Questo metodo inserisce i dati prenotazioni nella tabella prenotazioni
		 * @param codice
		 * @param id
		 * @param priorita
		 * @param data_inizio
		 * @return query
		 * @throws SQLException
		 */
		public static String insertBookingGetQuery(
				int codice,
				int id,
				int priorita,
				Date data_inizio
				) throws SQLException
		{
			String data_F="";
			String query = 		"INSERT INTO prenotazioni("
					+ "codice, "
					+ "id, "
					+ "priorità,"
					+ "data_inizio )"
					+ "VALUES('" 		+ codice	            + "' , '"
										+ id		            + "' , '" 
										+ priorita				+ "' , '" 
			                            + data_inizio 	    + "')";
			return 	query;
		}		
		
		
		public static void insertLoans(String query) throws SQLException
		{
			DBmanager.openConnection();
			DBmanager.executeUpdate(query);
			DBmanager.closeConnection();
		}
		
		/**
		 *
		 * @param codice
		 * @param id
		 * @param data_inizio
		 * @return query
		 * @throws SQLException
		 */
		public static String insertLoansGetQuery(
				int codice,
				int id,
				Date data_inizio
				) throws SQLException
		{
			String data_F="";
			String falsita		= "false";
			
			String query = 		"INSERT INTO prestiti("
					+ "codice, "
					+ "id, "
					+ "data_inizio, "
					+ "rientrato, "
					+ "ritirato, "
					+ "scaduto, "
					+ "email_inviata)"
					+ "VALUES('" 		+ codice	            + "' , '"
										+ id		            + "' , '" 
										+ data_inizio        	+ "' , '" 
										+ falsita	          	+ "' , '"
										+ falsita           	+ "' , '"
			                            + falsita 	            + "' , '" 
			                            + falsita		 	    + "')";
			return 	query;
		}

/**
 * Questo metodo inserisce i dati dei prestiti
 * @param codice
 * @param id
 * @param priorita
 * @param data_inizio
 * @return query
 * @throws SQLException
 */
public static String insertLoansCodaGetQuery(		
				int codice,
				int id,
				int priorita,
				Date data_inizio 
				) throws SQLException
{			
			Calendar c = new GregorianCalendar();
			Date datacorrente = c.getTime();
			int pr = 10;

			String query = 	"INSERT INTO prenotazioni("
					+ "codice, "
					+ "id, "
					+ "priorità, "
					+ "data_inizio )"
					+ "VALUES('" 		+ codice	            + "' , '"
										+ id		            + "' , '" 
										+ priorita		        + "' , '" 
			                            + data_inizio	 	    + "')";
			return 	query;
}				
	


		
public static void insertLoansCoda(String q) throws SQLException
		{
			DBmanager.openConnection();
			DBmanager.executeUpdate(q);
			DBmanager.closeConnection();
}
		
}
