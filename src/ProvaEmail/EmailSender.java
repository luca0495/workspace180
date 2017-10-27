package ProvaEmail;

import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;

import javax.mail.internet.InternetAddress;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

import connections.Client;
import database.MQ_Insert;
import database.MQ_Read;



public class EmailSender{
	static String usr;
	static String pwd;
	//String subject;
	//String body;
	//String hash;
	static Client me;
	 //public static final String MAIL_REGISTRATION_SITE_LINK = "http://localhost:8085/workspace103/workspace103/ConfirmEmail";
	
	 //public static void send_uninsubria_email(String USER,String PASS,String to,Client Me) throws SendFailedException, MessagingException{
	 public static void send_uninsubria_email(String to,Client Me) throws SendFailedException, MessagingException, SQLException{
	    // String hasher = hash;	  
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
	     
	     //SMTPAuthenticator authenticator = new SMTPAuthenticator(me.getUSERNAME(), me.getPASSWORD());
		 Properties props = new Properties();
		 /*
		 props.put( "mail.smtp.host" , "smtp.live.com");
		 props.put( "mail.smtp.user" , USERNAME );
		 
		
		 props.put( "mail.smtp.starttls.enable" , "true" );
		 props.put( "mail.smtp.password" , PASSWORD);
        */
		 //props.setProperty("mail.smtp.submitter", authenticator.getPasswordAuthentication().getUserName());
		 props.setProperty("mail.smtp.auth" , "true" );
		 props.setProperty("mail.smtp.host",host);
	     props.setProperty("mail.smtp.port","587");
	     props.setProperty("mail.smtp.starttls.enable", "true");
	     
	    // props.put("mail.debug", "true");
	     System.out.println("Controllo props:" + props + "Controllo authenticator:" + authenticator );
	     Session session = Session.getInstance( props, authenticator );
		 // problema username e password perchè devi averle!!   
	   
	        
	     //String link = MAIL_REGISTRATION_SITE_LINK+"?scope=activation&userId="+to+"&hash="+hash;
		 StringBuilder bodyText = new StringBuilder(); 
			 bodyText.append("<div>").append("Caro Utente<br/><br/>").append("  Grazie per la Registrazione. <br/>")
			  .append("Il codice di attivazione temporaneo è").append("  <br/>").append(MQ_Read.ReadPassTemp())
		      .append("  <br/>").append("  Grazie <br/>").append("</div>");
		try{	
		 Message msg = new Message(session);
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
<<<<<<< HEAD
	 
=======
/*
>>>>>>> origin/master
	public static void main(String[] argv) {
	    try {
			String password="";
			String username="";
			String subject="";
			String to="";
			String body="";
			String hash="";

		    
		    final JTextField uf= new JTextField("name@studenti.uninsubria.it");
		    final JPasswordField pf = new JPasswordField();
		    final JTextField tf= new JTextField();
		    final JTextField sf= new JTextField("email subject");
		    final JTextArea bf= new JTextArea(null,"textual content of the email", 10,20);
		    final JTextField ha =new JTextField("ConfirmEmail");
		    
		    
		      
		    Object[] message = {
		  	    "Username / From:", uf,
		        "Password:", pf,
		        "To:", tf,
		        "Subject:", sf,
		        "Body:",bf,
		        "Hash:",ha,
		        
		    };


		    int option = JOptionPane.showOptionDialog( null, message, "Send email", 
		    		JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,null,new String[]{"Send", "Cancel"}, "Send");
		    if (option== JOptionPane.YES_OPTION){ 
		        password= new String( pf.getPassword());
		        username= new String(uf.getText());
		        to=new String(tf.getText());
		        subject=new String(sf.getText());
		        body=new String(bf.getText());
		         hash=new String(ha.getText());
		        send_uninsubria_email(hash);
		    }
		    
		} catch (MessagingException e) {
		    System.err.println("SMTP SEND FAILED:");
		    System.err.println(e.getMessage());
			
		}
	}
*/
	
}
