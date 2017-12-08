package database;

import java.sql.SQLException;
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

}
