package ProvaEmail;

import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import connections.Client;
import database.MQ_Read;



public class EmailSender{
	static String usr;
	static String pwd;
	static Client me;
/**
 * Questo metodo invia una email all'utente che si è appena registrato (con il codice temporaneo da utilizzare sul primo accesso)
 * @param to
 * @param Me
 * @throws SendFailedException
 * @throws MessagingException
 * @throws SQLException
 */
public static void send_uninsubria_email(String to,Client Me) throws SendFailedException, MessagingException, SQLException{
  
		 me=Me;
		 System.out.println("Controllo errore:" + me.toString());
	     String host = "smtp.office365.com";
	     String from= me.getUSERNAME();
	     
	     Authenticator authenticator = new Authenticator()
	      {         @Override
	    	          protected PasswordAuthentication getPasswordAuthentication() {
	    	             return new PasswordAuthentication(me.getUSERNAME(), me.getPASSWORD());
	    	          }
	      };

		 Properties props = new Properties();
		 props.setProperty("mail.smtp.auth" , "true" );
		 props.setProperty("mail.smtp.host",host);
	     props.setProperty("mail.smtp.port","587");
	     props.setProperty("mail.smtp.starttls.enable", "true");
	     
	     System.out.println("Controllo props:" + props + "Controllo authenticator:" + authenticator );
	     Session session = Session.getInstance( props, authenticator );

	     
		 StringBuilder bodyText = new StringBuilder(); 
			 bodyText.append("<div>").append("Caro Utente<br/><br/>").append("  Grazie per la Registrazione. <br/>")
			  .append("Il codice di attivazione temporaneo è").append("  <br/>").append(MQ_Read.ReadPassTemp())
		      .append("  <br/>").append("  Grazie <br/>").append("</div>");
		try{	
			
		 Message msg = new MimeMessage(session);
		 msg.setFrom(new InternetAddress(from));
		 msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
		 msg.setSubject("Registrazione Email");
		 msg.setContent(bodyText.toString(), "text/html; charset=utf-8");
	      
		 System.out.println("Controllo msg:" + msg + "Controllo user:" + me.getUSERNAME() + "Controllo password:" + me.getPASSWORD());
		 Transport.send(msg,me.getUSERNAME(), me.getPASSWORD());	     
		 
		 //Transport.send(msg);
		 
	     System.out.println("\nMail was sent successfully.");   
		}catch(MessagingException exception)
        {
            exception.printStackTrace();
        }      
	 }
 




	 public static int randomGenerator1(){
	     Random randomGenerator = new Random();
		      int randomInt = randomGenerator.nextInt(100);
		      return randomInt;
	   }

	 
	 

	 /**
	 * Questo metodo invia una email di recupero password all'utente(verrà inserita una password casuale)
	 * @param to
	 * @param Me
	 * @param newpassword
	 * @throws SendFailedException
	 * @throws MessagingException
	 * @throws SQLException
	 */
	public static void send_uninsubria_recoverypassword(String to,Client Me,String newpassword) throws SendFailedException, MessagingException, SQLException{
		 
		 	String [] userdata = MQ_Read.readSettingTable();
		 	String tox=userdata[4];
	 		String pwx=newpassword;
	 	

		     String host = "smtp.office365.com";
		     String from= tox;
		     
		     Authenticator authenticator = new Authenticator()
		      {         @Override
		    	          protected PasswordAuthentication getPasswordAuthentication() {
		    	             return new PasswordAuthentication(tox,userdata[5]);
		    	          }
		      };
			 Properties props = new Properties();
			 props.setProperty("mail.smtp.auth" , "true" );
			 props.setProperty("mail.smtp.host",host);
		     props.setProperty("mail.smtp.port","587");
		     props.setProperty("mail.smtp.starttls.enable", "true");
		     System.out.println("Controllo props:" + props + "Controllo authenticator:" + authenticator );
		     Session session = Session.getInstance( props, authenticator );
			 StringBuilder bodyText = new StringBuilder(); 
				 bodyText.append("<div>").append("Caro Utente<br/><br/>").append(", <br/>")
				  .append("la tua password temporanea è").append("  <br/>").append(pwx)
			      .append("  <br/>").append("  Grazie <br/>").append("</div>");
			try{	
			 Message msg = new MimeMessage(session);
			 msg.setFrom(new InternetAddress(from));
			 msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
			 msg.setSubject("Recovery Password");
			 msg.setContent(bodyText.toString(), "text/html; charset=utf-8");
			 System.out.println("Controllo msg:" + msg + "Controllo user:" + tox + "Controllo password:" + pwx);
			 Transport.send(msg,tox, pwx);	     			 
			 //Transport.send(msg);
		     System.out.println("\nMail was sent successfully.");   
			}catch(MessagingException exception)
	        {
	            exception.printStackTrace();
	        }      
		 }

	 
	 /**
	  * Questo metodo invia una email all'utente che ha un libro scaduto o in scadenza di uno o più prestiti
	 * @param UN
	 * @param PW
	 * @param email
	 * @param idlibro
	 * @param utnome
	 * @param utcognome
	 * @param nome_autore
	 * @param cognome_autore
	 * @param titolo
	 * @throws SendFailedException
	 * @throws MessagingException
	 * @throws SQLException
	 */
	public static void send_LoansExpired(	String UN,
			 								String PW,
			 
											 String email,
											 
											 String idlibro,
											 String utnome,
											 String utcognome,
											 
											 String nome_autore,
											 String cognome_autore,
											 String titolo
											 							 
			 ) throws SendFailedException, MessagingException, SQLException{
		     String host = "smtp.office365.com";
		     String from= UN; 
		     Authenticator authenticator = new Authenticator()
		      {         @Override
		    	          protected PasswordAuthentication getPasswordAuthentication() {
		    	             return new PasswordAuthentication(UN, PW);
		    	          }
		      };
			 Properties props = new Properties();
			 props.setProperty("mail.smtp.auth" , "true" );
			 props.setProperty("mail.smtp.host",host);
		     props.setProperty("mail.smtp.port","587");
		     props.setProperty("mail.smtp.starttls.enable", "true");		     
		     System.out.println("Controllo props:" + props + "Controllo authenticator:" + authenticator );
		     Session session = Session.getInstance( props, authenticator );
			 
		     StringBuilder bodyText = new StringBuilder(); 
				 bodyText.append("<div>").append("Caro "+utnome+" "+utcognome+"<br/><br/>").append("  La contattiamo per informarla della scadenza del prestito. <br/>")
				  			.append("Il prestito in oggetto : libro ID: "+idlibro).append("  <br/>")
				  			.append(" TITOLO: "+titolo).append("  <br/>")
				  			.append(" Autore: "+nome_autore+" "+cognome_autore ).append("  <br/>")
				  			.append("  <br/>").append(" Nell'invitarla alla restituzione il prima possibile, la ringraziamo in anticipo <br/>").append("</div>");
			try{		
			 
			Message msg = new MimeMessage(session);
			 msg.setFrom(new InternetAddress(from));
			 msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(email));
			 msg.setSubject("Avviso Scadenza Prestito");
			 msg.setContent(bodyText.toString(), "text/html; charset=utf-8");
			 
			 System.out.println("Controllo msg:" + msg + "Controllo user:" + UN + "Controllo password:" + PW);
			 
			 Transport.send(msg,UN,PW);	     			 	 
		     System.out.println("\nMail was sent successfully.");   
			}catch(MessagingException exception)
	        {
	            exception.printStackTrace();
	        }      
		 }
	 

	 /**
	 * Questo metodo invia una email all'utente che ha preso in prestito un libro
	 * @param idlibro
	 * @param utnome
	 * @param utcognome
	 * @param email
	 * @param nome_autore
	 * @param cognome_autore
	 * @param titolo
	 * @param data_inizio
	 * @param data_fine
	 * @param UN
	 * @param PW
	 * @throws SendFailedException
	 * @throws MessagingException
	 * @throws SQLException
	 */
	public static void send_email_books_loans(	String idlibro,
				 								String utnome,
				 								String utcognome,
				 								String email,
					                            String nome_autore,
					                            String cognome_autore,
					                            String titolo,
					                            Date  data_inizio,
					                            Date  data_fine,					                               
					                            String UN,
					                            String PW
				                               
				 ) throws SendFailedException, MessagingException, SQLException{
		 String[] all = MQ_Read.sendEmailLoans();
	     String host = "smtp.office365.com";
	     String from= UN; 
	     Authenticator authenticator = new Authenticator()
	      {         @Override
	    	          protected PasswordAuthentication getPasswordAuthentication() {
	    	             return new PasswordAuthentication(UN, PW);
	    	          }
	      };
		 Properties props = new Properties();
		 props.setProperty("mail.smtp.auth" , "true" );
		 props.setProperty("mail.smtp.host",host);
	     props.setProperty("mail.smtp.port","587");
	     props.setProperty("mail.smtp.starttls.enable", "true");		     
	     System.out.println("Controllo props:" + props + "Controllo authenticator:" + authenticator );
	     Session session = Session.getInstance( props, authenticator );
		 
	     StringBuilder bodyText = new StringBuilder(); 
			 bodyText.append("<div>").append("Caro "+utnome+" "+utcognome+"<br/><br/>").append("La contattiamo per dirle che ha preso in prestito il seguente libro. <br/>")
			  			.append("Libro ID: "+idlibro).append("  <br/>")
			  			.append("TITOLO: "+titolo).append("  <br/>")
			  			.append("Autore: "+nome_autore+" "+cognome_autore ).append("  <br/>")
			  			.append("La data di inizio prestito è:" + data_inizio ).append("  <br/>")
			  			.append("La data di fine prestito è: " + data_fine + 30).append("  <br/>")
			  			.append(" <br/>").append("Grazie per averci scelto, Arrivederci <br/>").append("</div>");
		try{		
		 
		Message msg = new MimeMessage(session);
		 msg.setFrom(new InternetAddress(from));
		 msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(email));
		 msg.setSubject("Avviso Scadenza Prestito");
		 msg.setContent(bodyText.toString(), "text/html; charset=utf-8");
		 
		 System.out.println("Controllo msg:" + msg + "Controllo user:" + UN + "Controllo password:" + PW);
		 
		 Transport.send(msg,UN,PW);	     			 
		 //Transport.send(msg);			 
	     System.out.println("\nMail was sent successfully.");   
		}catch(MessagingException exception)
        {
            exception.printStackTrace();
        }      
	 }

	 
	 
	
}
