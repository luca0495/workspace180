package database;

import java.sql.SQLException;
import java.util.List;

public class MQ_Delete {
	
	/**
	 * Questo metodo cancella il libro selezionato dall'utente(reader e libraio)
	 * @param r
	 * @throws SQLException
	 */
	public static void deleteRowBooks(List<String> r) throws SQLException
	{			
		String query = "DELETE FROM libro WHERE "
				+ "codice = '" 					   + r.get(0) 
				+ "' AND nome_autore = '" 		   + r.get(1) 
				+ "' AND cognome_autore = '" 	   + r.get(2) 
				+ "' AND categoria = '" 		   + r.get(3) 
				+ "' AND titolo = '" 			   + r.get(4) 
				+ "' AND disponibilità = '"        + r.get(5) 
		        + "' AND prenotazioni_in_coda = '" + r.get(6) + "';";
		DBmanager.openConnection();
		DBmanager.executeUpdate(query);
		DBmanager.closeConnection();
	}
	/**
	 * Questo metodo cancella il prestito selezionato dall'utente(libraio)
	 * @param r
	 * @throws SQLException
	 */
	public static void deleteRowLoans(List<String> r) throws SQLException
	{		
		String query = "DELETE FROM prestiti WHERE "
				+ "codice = '" 					   + r.get(0) 
				+ "' AND id = '" 		           + r.get(1) 
				+ "' AND data_inizio = '" 	       + r.get(2)
		        + "' AND data_fine = '"            + r.get(3) + "';";
		DBmanager.openConnection();
		DBmanager.executeUpdate(query);
		DBmanager.closeConnection();
	}	
	
	/**
	 * Questo metodo cancella la prenotazione selezionata dall'utente(libraio)
	 * @param r
	 * @throws SQLException
	 */
	public static void deleteRowBooking(List<String> r) throws SQLException
	{		
		String query = "DELETE FROM prenotazioni WHERE "
				+ "codice = '" 					   + r.get(0) 
				+ "' AND id = '" 		           + r.get(1) 
		        + "' AND priorità = '"            + r.get(2) + "';";
		DBmanager.openConnection();
		DBmanager.executeUpdate(query);
		DBmanager.closeConnection();
	}	
	
	public static void deleteRowBooks(String q) throws SQLException
	{			
		DBmanager.openConnection();
		DBmanager.executeUpdate(q);
		DBmanager.closeConnection();
	}

	public static String deleteRowBooksGetQuery(List<String> r) throws SQLException
	{		
		String query = "DELETE FROM libro WHERE "
				+ "codice = '" 					   + r.get(0) 
				+ "' AND nome_autore = '" 		   + r.get(1) 
				+ "' AND cognome_autore = '" 	   + r.get(2) 
				+ "' AND categoria = '" 		   + r.get(3) 
				+ "' AND titolo = '" 			   + r.get(4) 
		        + "' AND disponibilità = '"        + r.get(5) 
		        + "' AND prenotazioni_in_coda = '" + r.get(6) + "';";
		return query;
	}
	
	public static void deleteRowLoans(String q) throws SQLException
	{			
		DBmanager.openConnection();
		DBmanager.executeUpdate(q);
		DBmanager.closeConnection();
	}
	public static String deleteRowLoansGetQuery(List<String> r) throws SQLException
	{		
		String query = "DELETE FROM prestiti WHERE "
				+ "codice = '" 					   + r.get(0) 
				+ "' AND id = '" 		           + r.get(1)  
		        + "';";
		return query;
	}
	
	
	public static void deleteRowBooking(String q) throws SQLException
	{			
		DBmanager.openConnection();
		DBmanager.executeUpdate(q);
		DBmanager.closeConnection();
	}
	public static String deleteRowBookingGetQuery(List<String> r) throws SQLException
	{		
		String query = "DELETE FROM prenotazioni WHERE "
				+ "codice = '" 					   + r.get(0) 
				+ "' AND id = '" 		           + r.get(1) 
		        + "';";
		return query;
	}
	
	public static void deletePassTemp(int id,String pass) throws SQLException
	{			
		String query = "DELETE FROM utente WHERE id ='" + id + "' AND password_temp = '" + pass + "';";
    	
		System.err.println("query agg pass temp: "+query);
		
		DBmanager.openConnection();
		DBmanager.executeUpdate(query);
		DBmanager.closeConnection();
	}
	/**
	 * Questo metodo aggiorna la password temporanea dell'utente riferendosi all'email
	 * @param EM
	 * @param PW
	 * @throws SQLException
	 */
	public static void deletePassTempEP(String EM,String PW) throws SQLException
	{			
		String query = "UPDATE utente SET password_temp=0 WHERE email = '"+EM+"'";
		System.err.println("query agg pass temp: "+query);
		
		DBmanager.openConnection();
		DBmanager.executeUpdate(query);
		DBmanager.closeConnection();
	}
	
	
	//test OK
	public static String deleteRowPerson(List<String> r) throws SQLException
	{			
		
		String query = " DELETE FROM utente WHERE "
				+ "  id = '" 				+ r.get(0) + "';";

		
		DBmanager.openConnection();
		DBmanager.executeUpdate(query);
		DBmanager.closeConnection();
		
		return query;
		
	}

	public static String deleteRowPerson(int idUser) throws SQLException
	{				
		String query = " DELETE FROM utente WHERE "
				+ "  id = '" 				+ idUser + "';";
		DBmanager.openConnection();
		DBmanager.executeUpdate(query);
		DBmanager.closeConnection();		
		return query;
	}
	
	public static void deleteRowPerson1() throws SQLException
	{	
		
		String query = " DELETE FROM utente;";
		
		DBmanager.openConnection();
		DBmanager.executeUpdate(query);
		DBmanager.closeConnection();
	}

	public static void deleteRowBooking(int [] r) throws SQLException
	{		
		String query = "DELETE FROM prenotazioni WHERE "
				+ "			codice 		= '"   + r[0] 				//book
		        + "' AND 	id		 	= '"   + r[1] + "';";		//user
		DBmanager.openConnection();
		DBmanager.executeUpdate(query);
		DBmanager.closeConnection();
	}	
	

}
