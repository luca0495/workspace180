package Core;
import java.sql.SQLException;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;

import com.sun.org.apache.bcel.internal.generic.POP;

import Check.PopUp;
import Table.TableBooking;
import Table.TableBooks;
import connections.Client;
import connections.Message;
import connections.MessageBack;

public class ClientCMDBooking {

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
	
	public static void BookingDeleteRES(Client me,String mes) {
		switch (mes){
		case "OK": PopUp.infoBox(me.getActF(), 	"Prenotazione Eliminata con successo");					break;
		case "NG": PopUp.errorBox(me.getActF(), "non é stato possibile eliminare la prenotazione");		break;
		}
	}
	
	
	public static void Bookingpopulate(Client me) throws SendFailedException, MessagingException, SQLException, InterruptedException {
		String mSg;
		Commands cmd = Commands.BookingPopulate;
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
					
					me.getSelectedIdBook(),		// id libro
					me.getSelectedIdUser()		// id utente
					
					);
			MsgSend.setUType(Clients.Librarian);
			// **** Client invia Message
			me.sendM(MsgSend, Mb);	
		}		
	}
	public static void BookingpopulateRES(Client me,String mes,MessageBack Mb) {
		switch (mes){
		case "OK": 
			PopUp.infoBox(me.getActF(), 		"dati tabella Booking OK");					
			me.setActTable(Mb.getTab());
			TableBooking.getTable().setModel(Mb.getTab().getModel());
			me.setActF(null);
			me.setSql(null);
			me.setBusy(false);
			break;		
		case "NG": PopUp.errorBox(me.getActF(), "dati tabella Booking NG");					
			break;
		}
	}
	
	
	
}
