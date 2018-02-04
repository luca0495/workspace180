package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import connections.MessageBack;


public class ChkDBandTab {
// DB exist?
	public static MessageBack DBExist()throws SQLException{
	MessageBack mb = null;
			
			
	String DB_URL = "jdbc:postgresql://localhost/";
    String USER = "postgres";
    String PASS = "postgres";
	Connection connection = null;
    Statement statement= null;
		
		
		try {
		     Class.forName("org.postgresql.Driver");
			 System.out.println("Creating a connection...");
		     connection = DriverManager.getConnection(DB_URL,USER,PASS);
		     ResultSet resultSet = connection.getMetaData().getCatalogs();
		    
						    if(!resultSet.next()){
						    	 
						    statement = connection.createStatement();
						    
						    String sql =  " CREATE DATABASE IF NOT EXISTS schoolib ";
						    
						    statement.executeUpdate(sql);
						    	System.out.println("Database created!");
						    	//mb.setText("SRV :> DATABASE CREATO");
						    	
						     }
						    
		
		
		} catch (SQLException sqlException) {
					   if (sqlException.getErrorCode() == 1007) {

					    	System.out.println("SYS :> DATABASE ALREADY EXIST");
					    	
					    	
					    } else {
					     
					   System.out.println("SYS :> OTHER PROBLEMS");
					       System.out.println("SYS :> "+sqlException.getErrorCode());					        
					    sqlException.printStackTrace();
 
					  }
		  }   catch (ClassNotFoundException e) {

			System.out.println("SYS :> class not found");
		}
		return mb;
		 
		}
	
	   
	 

	// CHECK TAB
	/**
	 * Questo metodo crea la tabella libro se non esiste già nel database
	 * @throws SQLException
	 */
	public static void tableExistBook()throws SQLException{
	Connection connection = DBmanager.getConnection("jdbc:postgresql://localhost:5432/schoolib", "postgres", "postgres");
	 DatabaseMetaData metadata = connection.getMetaData();
	 ResultSet resultSet;
	 Statement statement = connection.createStatement();
	 resultSet = metadata.getTables(null, null, "libro", null);
	 
	 if(!resultSet.next()){
	 
		 statement.executeUpdate
                  ("CREATE TABLE  libro ( "
                  +"codice serial primary key,"
                  +"nome_autore varchar(15) not null,"
                  +"cognome_autore varchar(15) not null,"
                  +"categoria varchar(15) not null,"
                  +"titolo varchar(35) not null,"
                  +"disponibilità varchar(35) not null,"
                  +"prenotazioni_in_coda integer not null)")  ; //7
		 
		 	      System.out.println("ChkDBandTable :> table Book CREATED !");		 
	 }
	 else
	 {
		    System.out.println("ChkDBandTable :> exists table Book !");
		}
	    resultSet.close();
		DBmanager.closeConnection();
	}
	
	/**
	 * Questo metodo crea la tabella utente se non esiste già nel database
	 * @throws SQLException
	 */
	public static void tableExistPerson()throws SQLException{
	Connection connection = DBmanager.getConnection("jdbc:postgresql://localhost:5432/schoolib", "postgres", "postgres");
	 DatabaseMetaData metadata = connection.getMetaData();
	 ResultSet resultSet;
	 Statement statement = connection.createStatement();
	 resultSet = metadata.getTables(null, null, "utente", null);
	 if(!resultSet.next()){
	 
		 statement.executeUpdate
                  ("CREATE TABLE  utente ( "
                  +"id serial primary key,"
                  +"nome varchar(20) null,"
                  +"cognome varchar(20) null,"
                  +"email varchar(40) null,"
                  +"codice_fiscale varchar(16) null,"
                  +"inquadramento varchar(40) null,"
                  +"password varchar(40) null,"
                  +"password_temp varchar(40) null,"
                  +"password_temp_tentativi varchar(5) null,"
                  +"ntel varchar(10) null,"
                  +"tipo_utente varchar(30) null)"
                  )  ;
		 
		 System.out.println("ChkDBandTable :> table Person CREATED !");
	 }
	 else
	 {
		 System.out.println("ChkDBandTable :> exists table Person !");
		    
		}
	    resultSet.close();
		DBmanager.closeConnection();

	}
	/**
	 * Questo metodo crea la tabella prestiti se non esiste già nel database
	 * @throws SQLException
	 */
	public static void tableExistLoans()throws SQLException{
		Connection connection = DBmanager.getConnection("jdbc:postgresql://localhost:5432/schoolib", "postgres", "postgres");
		 DatabaseMetaData metadata = connection.getMetaData();
		 ResultSet resultSet;
		 Statement statement = connection.createStatement();
		 resultSet = metadata.getTables(null, null, "prestiti", null);
		 if(!resultSet.next()){
		 
			 statement.executeUpdate
	                  ("CREATE TABLE  prestiti ( "
	                  +"codice serial not null,"
	                  +"id serial not null,"
	                  +"data_inizio date not null,"
	                  +"data_fine date null,"   
	                  +"rientrato boolean not null,"
	                  +"ritirato boolean not null,"
	                  +"scaduto boolean not null,"
	                  +"email_inviata boolean not null,"//8
	                  +"foreign key (id)   references utente (id) ON UPDATE CASCADE ON DELETE CASCADE,"
	                  +"foreign key (codice) references libro (codice) ON UPDATE CASCADE ON DELETE CASCADE)")  ;
			 
			 System.out.println("ChkDBandTable :> table Prestiti CREATED !");
		 
		 }
		 else
		 {
			 System.out.println("ChkDBandTable :> exists table Prestiti !");
			    
			}
		    resultSet.close();
			DBmanager.closeConnection();

		}
	/**
	 * Questo metodo crea la tabella prenotazioni se non esiste già nel database
	 * @throws SQLException
	 */
	public static void tableExistBooking()throws SQLException{
		Connection connection = DBmanager.getConnection("jdbc:postgresql://localhost:5432/schoolib", "postgres", "postgres");
		 DatabaseMetaData metadata = connection.getMetaData();
		 ResultSet resultSet;
		 Statement statement = connection.createStatement();
		 resultSet = metadata.getTables(null, null, "prenotazioni", null);
		 if(!resultSet.next()){
		 
			 statement.executeUpdate
	                  ("CREATE TABLE  prenotazioni ( "
	                  +"codice serial not null,"
	                  +"id serial not null,"
	                  +"priorità integer not null,"
	                  +"data_inizio date not null,"
	                  +"foreign key (id)   references utente (id) ON UPDATE CASCADE ON DELETE CASCADE,"
	                  +"foreign key (codice) references libro (codice) ON UPDATE CASCADE ON DELETE CASCADE)")  ;
			 
			 System.out.println("ChkDBandTable :> table Prenotazioni CREATED !");
		 
		 }
		 else
		 {
			 System.out.println("ChkDBandTable :> exists table Prenotazioni !");
			    
			}
		    resultSet.close();
			DBmanager.closeConnection();

		}

}

