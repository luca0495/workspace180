package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MQ_Check {

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
	
	// check prestiti su bottone check box (fare parte reader e librarian)
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

}
