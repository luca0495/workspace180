package database;

import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import Books.Books;
import Core.Commands;
import Table.TableBooks;
import gui.Account;
import gui.Login;


public class MQ_Read {
	
	public LoadUser l;
	
	public static String [][] RicercaLibro ()throws SQLException{			
	 	String 	query = "SELECT * FROM libro;";

			DBmanager.openConnection();
			ResultSet rs = DBmanager.executeQuery(query);
			
			List<String> results = new ArrayList<String>();
			String[][] dati = null;
			
			if (!rs.isBeforeFirst()) 
			{
				dati = new String[1][7];
				dati[0][0] = null;
				dati[0][1] = null;
				dati[0][2] = null;
				dati[0][3] = null;
				dati[0][4] = null;
				dati[0][5] = null;
				dati[0][6] = null;
				
			}
			else
			{
				while(rs.next()) 
				{
					results.add(rs.getString("codice"));
					results.add(rs.getString("nome_autore"));
					results.add(rs.getString("cognome_autore"));
					results.add(rs.getString("categoria"));
					results.add(rs.getString("titolo"));
					results.add(rs.getString("disponibilità"));
					results.add(rs.getString("prenotazioni_in_coda"));
					
					int cols = 7;
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
	
 public static String [][] ResearchLoans ()throws SQLException{			
	 	
	 	String 	query = "SELECT * FROM prestiti;";

		DBmanager.openConnection();
		ResultSet rs = DBmanager.executeQuery(query);
		
		List<String> results = new ArrayList<String>();
		String[][] dati = null;
		
		if (!rs.isBeforeFirst()) 
		{
			dati = new String[1][8];
			dati[0][0] = null;
			dati[0][1] = null;
			dati[0][2] = null;
			dati[0][3] = null;
			dati[0][4] = null;
			dati[0][5] = null;
			dati[0][6] = null;
			dati[0][7] = null;
			
		}
		else
		{
			while(rs.next()) 
			{
				results.add(rs.getString("codice"));
				results.add(rs.getString("id"));
				results.add(rs.getString("data_inizio"));
				results.add(rs.getString("data_fine"));
				results.add(rs.getString("rientrato"));
				results.add(rs.getString("ritirato"));
				results.add(rs.getString("scaduto"));
				results.add(rs.getString("email_inviata"));
				
				int cols = 8;
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
	
 public static String [][] ResearchBooking ()throws SQLException{			
	 	
	 	String 	query = "SELECT * FROM prenotazioni;";

		DBmanager.openConnection();
		ResultSet rs = DBmanager.executeQuery(query);
		
		List<String> results = new ArrayList<String>();
		String[][] dati = null;
		
		if (!rs.isBeforeFirst()) 
		{
			dati = new String[1][4];
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
				results.add(rs.getString("priorità"));
				results.add(rs.getString("data_inizio"));
				
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
	
	public static LoadUser getUserById(int UserId) {
		LoadUser loaduser = new LoadUser();
        try {
        	DBmanager.openConnection();
        	Connection conn = DBmanager.openConnection1();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * utente WHERE id = 1");
            pstmt.setInt(1, UserId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
            	loaduser.setId(rs.getInt(1));
            	loaduser.setName(rs.getString(2));
            	loaduser.setSurname(rs.getString(3));
            	loaduser.setEmail(rs.getString(4));
            	loaduser.setInq(rs.getString(6));
            	loaduser.setPass(rs.getString(7));
            	loaduser.setNtel(rs.getString(9));
            	loaduser.setType_user(rs.getString(10));
            }
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        return loaduser;
    }
	
	
	public static String [][] ReadUser ()throws SQLException{			
		String query = "SELECT * FROM utente;";
		DBmanager.openConnection();
		ResultSet rs = DBmanager.executeQuery(query);
		
		List<String> results = new ArrayList<String>();
		String[][] dati = null;
		
		if (!rs.isBeforeFirst()) 
		{
			dati = new String[1][10];
			dati[0][0] = null;
			dati[0][1] = null;
			dati[0][2] = null;
			dati[0][3] = null;
			dati[0][4] = null;
			dati[0][5] = null;
			dati[0][6] = null;
			dati[0][7] = null;
			dati[0][8] = null;
			dati[0][9] = null;
			
		}
		else
		{
			while(rs.next()) 
			{
				results.add(rs.getString("id"));
				results.add(rs.getString("nome"));
				results.add(rs.getString("cognome"));
				results.add(rs.getString("email"));
				results.add(rs.getString("codice_fiscale"));
				results.add(rs.getString("inquadramento"));
				results.add(rs.getString("password"));
				results.add(rs.getString("password_temp"));
				results.add(rs.getString("ntel"));
				results.add(rs.getString("tipo_utente"));
				
				int cols = 10;
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
	
	public static String ReadPassTemp1(String email) throws SQLException
	{
	String query = "SELECT password_temp FROM utente WHERE email ='" + email +"';";
	
	DBmanager.openConnection();
	ResultSet rs = DBmanager.executeQuery(query);
	String value = null;
	
	while(rs.next())
	{	
		value = rs.getString("password_temp");
	System.out.println("entrato...");
	}
	DBmanager.closeConnection();
	
	return value;
}
	
	public static String ReadPassTemp() throws SQLException
	{
	String query = "SELECT password_temp FROM utente ;";
	DBmanager.openConnection();
	ResultSet rs = DBmanager.executeQuery(query);
	String value = null;
	while(rs.next())
	{	
		value = rs.getString("password_temp");
	}
	DBmanager.closeConnection();
	
	return value;
}

	public static String[] UserLoginTryCounter(String email) throws SQLException
	{
		
		String query = "SELECT email, password_temp_tentativi, id FROM utente WHERE email = '" + email + "';";
		DBmanager.openConnection();
		ResultSet rs = DBmanager.executeQuery(query);
		System.out.println(query);
		String[] User = new String[3]; //3 email, 7 pass_temp
		
		if (!rs.isBeforeFirst()) 
		{  
			System.out.println("9");
			User[0] = "Nessun Dato";
		}
		else
		{
			System.out.println("10");
			rs.next();
			User[0] = rs.getString("email");
			User[1] = rs.getString("password_temp_tentativi");
			User[2] = rs.getString("id");
		}
		DBmanager.closeConnection();
		
		return User;
	}
	
	
	
	public static String[] selectAdminLogInFIRST(String email,String pass) throws SQLException
	{
		
		String query = "SELECT email, password_temp FROM utente WHERE email = '" + email + "';";
		DBmanager.openConnection();
		ResultSet rs = DBmanager.executeQuery(query);
		System.out.println(query);
		String[] User = new String[2]; //3 email, 7 pass_temp
		
		if (!rs.isBeforeFirst()) 
		{  
			System.out.println("9");
			User[0] = "Nessun Dato";
		}
		else
		{
			System.out.println("10");
			rs.next();
			User[0] = rs.getString("email");
			User[1] = rs.getString("password_temp");
		}
		DBmanager.closeConnection();
		
		return User;
	}
	
	
	public static String[] selectAdminLogIn(String email,String pass) throws SQLException
	{
		
		String query = "SELECT id,email, password,nome,cognome,tipo_utente FROM utente WHERE email = '" + email + "';";
		DBmanager.openConnection();
		ResultSet rs = DBmanager.executeQuery(query);
		System.out.println(query);
		String[] User = new String[6]; //3 email, 7 pass_temp
		
		if (!rs.isBeforeFirst()) 
		{  
			System.out.println("9");
			User[0] = "Nessun Dato";
		}
		else
		{
			System.out.println("10");
			rs.next();
			User[0] = rs.getString("id");
			User[1] = rs.getString("email");
			User[2] = rs.getString("password");
			User[3] = rs.getString("nome");
			User[4] = rs.getString("cognome");
			User[5] = rs.getString("tipo_utente");	
		}
		DBmanager.closeConnection();
		
		return User;
	}
	public static String[] selectUser(String email,String password) throws SQLException
	{
		
		String query = "SELECT nome,cognome,email,inquadramento,password,ntel,tipo_utente FROM utente WHERE email = '" + email + "' and password = '" + password + "';";
		DBmanager.openConnection();
		ResultSet rs = DBmanager.executeQuery(query);
		
		String[] Person = new String[7]; //3 email, 7 pass_temp
		
		if (!rs.isBeforeFirst()) 
		{  
			System.out.println("9");
			Person[0] = "Nessun Dato";
		}
		else
		{
			System.out.println("10");
			rs.next();
			Person[1] = rs.getString("nome");
			Person[2] = rs.getString("cognome");
			Person[3] = rs.getString("email");
			Person[5] = rs.getString("inquadramento");
			Person[6] = rs.getString("password");
			Person[9] = rs.getString("ntel");
			Person[10] = rs.getString("tipo_utente");
			
		}
		DBmanager.closeConnection();
		
		return Person;
	}
	

	//TODO DA USARE PER FINESTRA USER ACCOUNT DA LOGIN
	
	//TODO il comando deve comprendere:
	//TODO String q = MQ_READ.selectUserGetQuery(email);
	//TODO me.setSql(q);
	//TODO me.setActW(FINESTRA ATTIVA CON ACCOUNT UTENTE);
	//TODO me.getCmdLIST().put(Commands.UserREAD);

	public static String selectUserGetQuery(String email) throws SQLException
	{	
		String query = "SELECT nome,cognome,email,inquadramento,password,ntel,tipo_utente FROM utente WHERE email = '" + email +"';";
		return query;
	}
	//TODO DA USARE PER FINESTRA USER ACCOUNT DA LOGIN
	public static String[] selectUserByQuery(String q) throws SQLException
	{
		LoadUser loaduser = new LoadUser();
		DBmanager.openConnection();
		ResultSet rs = DBmanager.executeQuery(q);		
		String[] Person = new String[7]; //3 email, 7 pass_temp		
		if (!rs.isBeforeFirst()) 
		{  
			System.out.println("9");
			Person[0] = "Nessun Dato";
		}
		else
		{
			System.out.println("10");
			rs.next();
			Person[0] = loaduser.setName(rs.getString("nome"));
			Person[1] = loaduser.setSurname(rs.getString("cognome"));
			Person[2] = rs.getString("email");
			Person[3] = rs.getString("inquadramento");
			Person[4] = rs.getString("password");
			Person[5] = rs.getString("ntel");
			Person[6] = rs.getString("tipo_utente");
		}
		DBmanager.closeConnection();	
		return Person;
	}

	
	//TODO DA USARE PER FINESTRA USER ACCOUNT DA LOGIN
	public static String[] selectUser(String email) throws SQLException
	{
		
		String query = "SELECT nome,cognome,email,inquadramento,password,ntel,tipo_utente FROM utente WHERE email = '" + email +"';";
		DBmanager.openConnection();
		ResultSet rs = DBmanager.executeQuery(query);
		
		String[] Person = new String[7]; //3 email, 7 pass_temp
		
		if (!rs.isBeforeFirst()) 
		{  
			System.out.println("9");
			Person[0] = "Nessun Dato";
		}
		else
		{
			System.out.println("10");
			rs.next();
			Person[1] = rs.getString("nome");
			Person[2] = rs.getString("cognome");
			Person[3] = rs.getString("email");
			Person[5] = rs.getString("inquadramento");
			Person[6] = rs.getString("password");
			Person[9] = rs.getString("ntel");
			Person[10] = rs.getString("tipo_utente");
			
		}
		DBmanager.closeConnection();
		
		return Person;
	}

	
	public static String[] retrieveUserId() throws SQLException
	{		
		String query = "SELECT id, nome, cognome, email, password,inquadramento,ntel,tipo_utente FROM utente ORDER BY id DESC LIMIT 1;";
		//String query = "SELECT id, nome, cognome, email, password,inquadramento,ntel,tipo_utente FROM utente WHERE email = '" + email +"';";
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
				results.add(rs.getString("id"));//0
				results.add(rs.getString("nome"));//1
				results.add(rs.getString("cognome"));//2
				results.add(rs.getString("email"));//3
				results.add(rs.getString("password"));//4
				results.add(rs.getString("inquadramento"));//5
				results.add(rs.getString("ntel"));//6
				results.add(rs.getString("tipo_utente"));//7
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
	
	public static String[] retrieveUserIdbyemail(String email) throws SQLException
	{		
		
		String query = "SELECT id,nome, cognome, email, password,inquadramento,ntel,tipo_utente FROM utente WHERE email= '"+email+"';";
		//String query = "SELECT id,nome, cognome, email, password,inquadramento,ntel,tipo_utente FROM utente WHERE id= '"+idutente+"';";
		
		
		
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
				results.add(rs.getString("id")); //0 
				results.add(rs.getString("nome")); // 1
				results.add(rs.getString("cognome")); // 2
				results.add(rs.getString("email")); // 3
				results.add(rs.getString("password")); // 4
				results.add(rs.getString("inquadramento")); // 5 
				results.add(rs.getString("ntel")); // 6
				results.add(rs.getString("tipo_utente")); //7
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
	
	public static String[] retrieveUserIdbyid(int iduser) throws SQLException
	{		
		
		String query = "SELECT id,nome, cognome, email, password,inquadramento,ntel,tipo_utente FROM utente WHERE id= '"+iduser+"';";
		//String query = "SELECT id,nome, cognome, email, password,inquadramento,ntel,tipo_utente FROM utente WHERE id= '"+idutente+"';";
		
		
		
		DBmanager.openConnection();
		ResultSet rs = DBmanager.executeQuery(query);
		
		List<String> results = new ArrayList<String>();
		String[] user = new String[8]; // nome,cognome,email,password,inquadramento,ntel,tipo_utente,numpren(mancante)
		
		if (!rs.isBeforeFirst()) 
		{
			//results.add("Nessun Dato");
			results.add("");
		}
		else
		{
			while(rs.next()) 
			{
				results.add(rs.getString("id")); //0 
				results.add(rs.getString("nome")); // 1
				results.add(rs.getString("cognome")); // 2
				results.add(rs.getString("email")); // 3
				results.add(rs.getString("password")); // 4
				results.add(rs.getString("inquadramento")); // 5 
				results.add(rs.getString("ntel")); // 6
				results.add(rs.getString("tipo_utente")); //7
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
	
//***********************SETTING****************************************************
	
	public static String[] readSettingTable() throws SQLException
	{
		
		String query = "SELECT local_host,lan,www,srvType,email,password FROM setting;";
		DBmanager.openConnection();
		ResultSet rs = DBmanager.executeQuery(query);
		
		List<String> results = new ArrayList<String>();
		String[] user = new String[7]; // nome,cognome,email,password,inquadramento,ntel,tipo_utente,numpren(mancante)
		
		if (!rs.isBeforeFirst()) 
		{
			results.add("Nessun Dato");
		}
		else
		{
			while(rs.next()) 
			{
				results.add(rs.getString("local_host")); //0 
				results.add(rs.getString("lan")); // 1
				results.add(rs.getString("www")); // 2
				
				results.add(rs.getString("srvType")); // 3
				
				results.add(rs.getString("email")); // 4
				results.add(rs.getString("password")); // 5 
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

//PRESTITI***************************************************************************************************************
/*
select id,codice from prestiti 
where 
	data_fine is null 					AND
    scaduto = true						AND
	email_inviata  = false
    ;	
	*/
	
	public static String[] readLoansForSendEmail() throws SQLException
	{
		//restituisce il primo prestito SCADUTO con email NON inviata
		String query = "select prestiti.codice,utente.id,nome,cognome,email,tipo_utente,nome_autore,cognome_autore,titolo from prestiti ,utente,libro " + 
				"where 	prestiti.id		=utente.id		AND"	+ 
				"		prestiti.codice	=libro.codice	AND"	+
				
				"		data_fine 		is null 	AND" + 
				"    	scaduto 		= true		AND" + 
				"		email_inviata  	= false" + 
				"    ;";
		
		DBmanager.openConnection();
		ResultSet rs = DBmanager.executeQuery(query);

		String[] loan = new String[10]; // codice utente codice libro
		
		if (!rs.isBeforeFirst()) 
		{
			loan[0]=("Nessun Dato");
		}
		else
		{
			rs.next(); 
			
			loan[0]=(rs.getString("codice")); 			//0 
			loan[1]=(rs.getString("id"));				//1
			loan[2]=(rs.getString("nome")); 			//2
			loan[3]=(rs.getString("cognome")); 			//3
			loan[4]=(rs.getString("email")); 			//4
			loan[5]=(rs.getString("tipo_utente")); 		//5
			loan[6]=(rs.getString("nome_autore")); 		//6
			loan[7]=(rs.getString("cognome_autore")); 	//7
			loan[8]=(rs.getString("titolo")); 			//8
			
		}
		rs.close();
		DBmanager.closeConnection();
		return loan;
	}	
	
	
	

	
	public static int checkLoansIdutIdbook_2(int idut,int idbook) throws SQLException
	{
		System.err.println("idut  :"+idut);
		System.err.println("idbook  :"+idbook);
		
		String q="		SELECT count(codice) FROM prestiti WHERE codice='"+idbook+"' AND id ='"+idut+"';";			
		DBmanager.openConnection();
		ResultSet rs = DBmanager.executeQuery(q);
		
		int count;
		
		
		
		if (!rs.isBeforeFirst()) 
		{ 
			count = 0;
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
	
/*select count (scaduto) from prestiti where 
id = '1'		and
ritirato=true 	and
scaduto=true 	
;
*/
	public static int checkLoansIdutScaduti(int idut,int idbook) throws SQLException
	{
		System.err.println("idut  :"+idut);
		System.err.println("idbook  :"+idbook);
		
		String q="select count (scaduto) from prestiti where \r\n" + 
				"id = '"+idut+"'		and\r\n" + 
				"ritirato=true 	and\r\n" + 
				"scaduto=true 	\r\n" + 
				";";
				
		DBmanager.openConnection();
		ResultSet rs = DBmanager.executeQuery(q);
		int count;
		if (!rs.isBeforeFirst()) 
		{ 
			count = 0;
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


	public static int checkLoansIdBookWait(int idbook) throws SQLException
	{	
		System.err.println("idbook  :"+idbook);
		
		String q="select count (id) from prenotazioni where codice='"+idbook+"';";
				
		DBmanager.openConnection();
		ResultSet rs = DBmanager.executeQuery(q);
		int count;
		
		
		if (!rs.isBeforeFirst()) 
		{ 
			count = 0;
		}
		else
		{
			rs.next();
			
			count = rs.getInt(1);
	
		}	
		System.out.println("ottenuto count utenti in attesa di "+idbook+" : "+count);
		rs.close();
		DBmanager.closeConnection();
		return count;

	}
	
	public static boolean checkBookingPresente(int idut,int idbook) throws SQLException
	{
		System.err.println("idut  :"+idut);
		System.err.println("idbook  :"+idbook);
		
		String q="SELECT count(codice) FROM prenotazioni WHERE codice='"+idbook+"' AND id ='"+idut+"';";			
		DBmanager.openConnection();
		ResultSet rs = DBmanager.executeQuery(q);
		
		int count;
		
		if (!rs.isBeforeFirst()) 
		{ 
			count = 0;
		}
		else
		{
			rs.next();
			count = rs.getInt(1);
		}	
		 boolean presente=false;
		if (count>0)presente=true;
		
		System.out.println("ottenuto conto delle prenotazioni " + idut +" "+" "+idbook+ "  volte: "+rs.getInt(1));
		rs.close();
		DBmanager.closeConnection();
		return presente;
	} 
	
	
	
public static boolean checkLoansPresente(int idut,int idbook) throws SQLException
{
	System.err.println("idut  :"+idut);
	System.err.println("idbook  :"+idbook);
	
	String q="SELECT count(codice) FROM prestiti WHERE codice='"+idbook+"' AND id ='"+idut+"';";			
	DBmanager.openConnection();
	ResultSet rs = DBmanager.executeQuery(q);
	
	int count;
	
	if (!rs.isBeforeFirst()) 
	{ 
		count = 0;
	}
	else
	{
		rs.next();
		count = rs.getInt(1);
	}	
	 boolean presente=false;
	if (count>0)presente=true;
	
	System.out.println("ottenuto conto delle prestiti " + idut +" "+" "+idbook+ "  volte: "+rs.getInt(1));
	rs.close();
	DBmanager.closeConnection();
	return presente;
} 



}

