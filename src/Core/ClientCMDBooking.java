package Core;
import java.sql.SQLException;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import Check.PopUp;
import Table.TableBooking;
import connections.Client;
import connections.Message;
import connections.MessageBack;

/**
 * @author Mauro
 *
 */
/**
 * @author Mauro
 *
 */
public class ClientCMDBooking {

<<<<<<< HEAD
	/**spedisce al server il comando di cancellazione prenotazione
=======
	/**
>>>>>>> b1453130cb80434d47050fe317ad670cfa60adf5
	 * @param me
	 * @throws SendFailedException
	 * @throws MessagingException
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	public static void BookingDelete(Client me) throws SendFailedException, MessagingException, SQLException, InterruptedException{	
		String mSg;
		Commands cmd = Commands.BookingListREMOVE;
		MessageBack Mb = new MessageBack();
		
		System.err.println("dal client su clientCMDloans... idut:  "+me.getSelectedIdUser());
		System.err.println("dal client su clientCMDloans... idbook:"+me.getSelectedIdBook());
		
		
		System.out.println("CLI :> Request ricevuto da GUI :> "+cmd.toString());
		if (!(me.isStubok())){
			Mb.setText(mSg = "CLI :>  nessuna connessione attiva , riprovare ");			
			System.out.println(mSg);			
			me.getActW().addMsg(new String ("Connection Test result"+mSg));
		}else{	
			System.out.println("CLI :> Stub OK");
			// **** Client crea Message						
			Message MsgSend = new Message(	
					cmd,						// Comando richiesto
					me.getCliType() ,			// tipo di Client , Admin,Librarian,Reader
					me.toString(),				// id Client
					
					me.getSelectedIdBook(),		// id libro
					me.getSelectedIdUser()		// id utente
					
					);
			MsgSend.setUType(Clients.Librarian);
			// **** Client invia Message
			me.sendM(MsgSend, Mb);	
		}	
	}
	
<<<<<<< HEAD
	/**	/**il messaggio di ritorno dal server é la conferma della prenotazione eliminata
=======
	/**
>>>>>>> b1453130cb80434d47050fe317ad670cfa60adf5
	 * @param me
	 * @param mes
	 */
	public static void BookingDeleteRES(Client me,String mes) {
		switch (mes){
		case "OK": PopUp.infoBox(me.getActF(), 	"Prenotazione Eliminata con successo");					break;
		case "NG": PopUp.errorBox(me.getActF(), "non é stato possibile eliminare la prenotazione");		break;
		}
	}
	
	
	public static void Bookingpopulate(Client me) throws SendFailedException, MessagingException, SQLException, InterruptedException {
		String mSg;
		//Commands cmd = Commands.BookPopulate;
		Commands cmd = Commands.BookingExecuteQuery;
		
		MessageBack Mb = new MessageBack();

		System.out.println("CLI :> Request ricevuto da GUI :> "+cmd.toString());
		if (!(me.isStubok())){
			Mb.setText(mSg = "CLI :>  nessuna connessione attiva , riprovare ");			
			System.out.println(mSg);			
			me.getActW().addMsg(new String ("Connection Test result"+mSg));
		}else{	
			System.out.println("CLI :> Stub OK");
			// **** Client crea Message						
			Message MsgSend = new Message(	
					cmd,						// Comando richiesto
					me.getCliType() ,			// tipo di Client , Admin,Librarian,Reader
					me.toString(),				// id Client
					me.getSql()
					
					);
			MsgSend.setUType(Clients.Librarian);
			// **** Client invia Message
			me.sendM(MsgSend, Mb);	
		}		
	}
<<<<<<< HEAD
	
	
	/**il messaggio di ritorno dal server viene usato per ripopolare la jtable booking
=======
	/**
>>>>>>> b1453130cb80434d47050fe317ad670cfa60adf5
	 * @param me
	 * @param mes
	 * @param Mb
	 */
	public static void BookingpopulateRES(Client me,String mes,MessageBack Mb) {
		switch (mes){
		case "OK": 
			//PopUp.infoBox(me.getActF(), 		"dati tabella Booking OK");					
			me.setActTable(Mb.getTab());			
			TableBooking.getTable().setModel(Mb.getTab().getModel());
			me.setActF(null);
			me.setSql(null);
			me.setBusy(false);
			break;		
		case "NG": PopUp.errorBox(me.getActF(), "dati tabella Booking NG");					
			me.setBusy(false);
			PopUp.errorBox(me.getActF(), "dati tabella booking NG");			
			break;
		}
	}
	
	
	
}
