package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class MQ_Delete {
	
	public static void deleteRowBooks(List<String> r) throws SQLException
	{			
		String query = "DELETE FROM libro WHERE "
				+ "codice = '" 					+ r.get(0) 
				+ "' AND nome_autore = '" 		+ r.get(1) 
				+ "' AND cognome_autore = '" 	+ r.get(2) 
				+ "' AND categoria = '" 		+ r.get(3) 
				+ "' AND titolo = '" 			+ r.get(4) + "';";
    	
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
				+ "codice = '" 					+ r.get(0) 
				+ "' AND nome_autore = '" 		+ r.get(1) 
				+ "' AND cognome_autore = '" 	+ r.get(2) 
				+ "' AND categoria = '" 		+ r.get(3) 
				+ "' AND titolo = '" 			+ r.get(4) + "';";

<<<<<<< HEAD
	public static void deleteRowPerson(List<String> r) throws SQLException
	{			
		String query = " DELETE FROM utente WHERE "
				+ "id = '" 					+ r.get(0) 
				+ "' AND nome = '" 		    + r.get(1) 
				+ "' AND cognome  = '" 	    + r.get(2) 
				+ "' AND email = '" 	    + r.get(3) 
				+ "' AND codice_fiscale = '"+ r.get(4) 
		        + "' AND inquadramento = '" + r.get(5) 
		        + "' AND password = '"      + r.get(6)
		        + "' AND password_temp = '" + r.get(7) 
		        + "' AND ntel = '"          + r.get(8) 
		        + "' AND tipo_utente = '"   + r.get(9) + "';";
		
		DBmanager.openConnection();
		DBmanager.executeUpdate(query);
		DBmanager.closeConnection();
	}
	
	public static void deleteRowPerson1() throws SQLException
	{	
		Statement stmt;
		Connection con;
		con =DBmanager.openConnection1();
		stmt = con.createStatement();
		String query = " DELETE FROM utente ;";
		int deletedRows=stmt.executeUpdate(query);
		if(deletedRows>0){
		     System.out.println("Deleted All Rows In The Table Successfully...");
		   }else{
                        System.out.println("Table already empty."); 
		  }

		//DBmanager.executeUpdate(query);
		DBmanager.closeConnection();
	}
=======
		return 	query;
	}
	
>>>>>>> 24214355c305881e61c5843860a2b9aac969b2d2
}
