package database;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MQ_Check {

	/**
	 * Questo metodo serve per controllare, se esiste nel database, un codice fiscale 
	 * @param cf
	 * @return
	 * @throws SQLException
	 */
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
	
	/**
	 * Questo metodo serve identificare l'email dell'utente 
	 * @param mail
	 * @return
	 * @throws SQLException
	 */
	public static String selectMail(String mail) throws SQLException
	{
		String query = "SELECT id FROM utente WHERE email = '" + mail + "';";
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
			datiCliente = rs.getString("id");
		}
		
		rs.close();
		DBmanager.closeConnection();
		
		return datiCliente;
	}
	
	/**
	 * Questo metodo serve per leggere i dati dei prestiti riferito ad un utente
	 * @param idut
	 * @return
	 * @throws SQLException
	 */
	public static String[] readReaderUserLoans(int idut) throws SQLException
	{
		
		String query = "SELECT codice,id,data_inizio,data_fine,rientrato,ritirato,scaduto,email_inviata FROM prestiti WHERE id ='"+idut+"';";
		DBmanager.openConnection();
		ResultSet rs = DBmanager.executeQuery(query);
		
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
				results.add(rs.getString("codice")); //0 
				results.add(rs.getString("id")); // 1
				results.add(rs.getString("data_inizio")); // 2
				results.add(rs.getString("data_fine")); // 3
				results.add(rs.getString("rientrato")); // 4
				results.add(rs.getString("ritirato")); // 5 
				results.add(rs.getString("scaduto")); // 6
				results.add(rs.getString("email_inviata")); // 7
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
	
	/**
	 * Questo metodo serve per leggere i dati delle prenotazioni riferite ad un utente
	 * @param idut
	 * @return
	 * @throws SQLException
	 */
	public static String[] readReaderUserBooking(int idut) throws SQLException
	{
		
		String query = "SELECT codice,id,priorit� FROM prenotazioni WHERE id ='"+idut+"';";
		DBmanager.openConnection();
		ResultSet rs = DBmanager.executeQuery(query);
		
		List<String> results = new ArrayList<String>();
		String[] user = new String[3]; // nome,cognome,email,password,inquadramento,ntel,tipo_utente,numpren(mancante)
		
		if (!rs.isBeforeFirst()) 
		{
			results.add("Nessun Dato");
		}
		else
		{
			while(rs.next()) 
			{
				results.add(rs.getString("codice")); //0 
				results.add(rs.getString("id")); // 1
				results.add(rs.getString("priorit�")); // 2
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
	
	/**
	 * Questo metodo restituisce l'id dell'utente riferite ad una password gi� esistente
	 * @param pass
	 * @return
	 * @throws SQLException
	 */
	public static String selectPass(String pass) throws SQLException
	{
		String query = "SELECT id FROM utente WHERE password = '" + pass + "';";
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
			datiCliente = rs.getString("id");
		}
		
		rs.close();
		DBmanager.closeConnection();
		
		return datiCliente;
	}
	
	/**
	 * Questo metodo restutisce tutti i dati dalla tabella prestiti per un certo id e l� inserisce in una matrice
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public static String [][] checkIdUserLoans (int id)throws SQLException{			
		String query = "SELECT * FROM prestiti WHERE id ='" + id + "';";
		DBmanager.openConnection();
		ResultSet rs = DBmanager.executeQuery(query);
		
		List<String> results = new ArrayList<String>();
		String[][] dati = null;
		
		if (!rs.isBeforeFirst()) 
		{
			dati = new String[1][5];
			dati[0][0] = null;
			dati[0][1] = null;
			dati[0][2] = null;
			dati[0][3] = null;

		
			
		}
		else
		{
			while(rs.next()) 
			{
				results.add(rs.getString("codice"));
				results.add(rs.getString("id"));
				results.add(rs.getString("data_inizio"));
				results.add(rs.getString("data_fine"));
	
				
				int cols = 4;
		    	int rows = results.size() / cols;
		    	
		    	dati = new String[rows][cols];
		    	
				for(int i = 0, d = 0; i < rows; i++)
				{
		    		for(int j = 0; j < cols; j++, d++)
		    		{
		    			dati[i][j] = results.get(d);
				    }
				}
			}
		}
 
		rs.close();
		DBmanager.closeConnection();
		
		return dati;
	}
	/**
	 * Questo metodo resistuisce l'ultimo libro immesso(una sola riga)
	 * @return
	 * @throws SQLException
	 */
	public static String checkCFLastInsert() throws SQLException
	{
		String query1 = "SELECT codice FROM libro ORDER by codice DESC LIMIT 1";
		DBmanager.openConnection();
		ResultSet rs = DBmanager.executeQuery(query1);
		
		String codCF = new String();
		
		if (!rs.isBeforeFirst()) 
		{ 
			codCF = "No Data";
		}
		else
		{
			rs.next();
			codCF = rs.getString("codice");
		}
		
		rs.close();
		DBmanager.closeConnection();
		
		return codCF;
	}
	
	/**
	 * Questo metodo resituisce il numero di libri presi in prestito da un utente che non li ha ancora restituiti(Max 5 prestiti)
	 * @param idut
	 * @param idbook
	 * @return
	 * @throws SQLException
	 */
	public static int checkLoansIdutIdbook_5(int idut,int idbook) throws SQLException
	{
		System.err.println("idut  :"+idut);
		System.err.println("idbook  :"+idbook);
		
		String q="SELECT count(codice) FROM prestiti WHERE id ='"+idut+"'AND data_fine is null";			
		DBmanager.openConnection();
		ResultSet rs = DBmanager.executeQuery(q);
		
		int count;
		
		if (!rs.isBeforeFirst()) 
		{ 
			count = 0;

			System.out.println("ottenuto count : NESSUN DATO DALLA QUERY");
			
			
		}
		else
		{
			rs.next();
			count = rs.getInt(1);
		}	
		
		
		System.out.println("ottenuto count : "+count);
		
		
		rs.close();
		DBmanager.closeConnection();
		return count;
	}

	/**
	 * /**
	 * Questo metodo aggiorna la tabella dei prestiti dicendo che un libro � scaduto, non � stato ancora resituito e sono passati i 30 giorni 
	 */
	public static void updatePrestitoScaduto() throws SQLException
	{	
		String q = "update prestiti set scaduto=true where data_fine is null AND scaduto = false AND current_date - data_inizio >= '30';";
		DBmanager.openConnection();
		DBmanager.executeUpdate(q);
		DBmanager.closeConnection();
	}
	
	
	
	
}
