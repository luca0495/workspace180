package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CheckDBSetting {
	// CHECKSETTINGDB
		public static void TableExistSetting()throws SQLException{
		Connection connection = DBmanager.getConnection("jdbc:postgresql://localhost:5432/schoolib", "postgres", "postgres");
		 DatabaseMetaData metadata = connection.getMetaData();
		 ResultSet resultSet;
		 Statement statement = connection.createStatement();
		 resultSet = metadata.getTables(null, null, "setting", null);
		 
		 if(!resultSet.next()){
		 
			 statement.executeUpdate
	                  ("CREATE TABLE setting ( "
	                  +"local_host varchar(30) primary key,"
	                  +"lan varchar(30) not null,"
	                  +"www varchar(30) not null,"
	                  +"srvType varchar(30) not null,"
	                  +"email varchar(35) not null,"
	                  +"password varchar(35) not null)");//6
			 
			 	      System.out.println("CheckDBSetting :> table Setting CREATED !");		 
		 }
		 else
		 {
			    System.out.println("CheckDBSetting :> exists table Setting !");
			}
		    resultSet.close();
			DBmanager.closeConnection();
		}
		
}
