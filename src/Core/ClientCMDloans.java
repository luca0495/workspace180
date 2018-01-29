package Core;

import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

import Check.PopUp;
import Table.TableBooking;
import Table.TableBooks;
import Table.TableLoans;
import connections.Client;
import connections.Message;
import connections.MessageBack;

public class ClientCMDloans {

	public static void LoansNew(Client me) throws SendFailedException, MessagingException, SQLException, InterruptedException{	
		String mSg;
		Commands cmd = Commands.LoanASK;
		MessageBack Mb = new MessageBack();		
		System.err.println("dal client su clientCMDloans... idbook:"+me.getIdbook());			
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

	public static void LoansReturned(Client me) throws SendFailedException, MessagingException, SQLException, InterruptedException{	
		String mSg;
		Commands cmd = Commands.LoanReturn;
		MessageBack Mb = new MessageBack();		
		System.err.println("dal client su clientCMDloans... idbook:"+me.getIdbook());			
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
	public static void LoansReturnedRES(Client me,String mes,MessageBack Mb) {
		switch (mes){
		case "OK": 
			PopUp.infoBox(me.getActF(),	"RITORNO LOANS OK");					
			
			me.setActTable(Mb.getTab());
			me.setActF(null);
			me.setSql(null);
			me.setBusy(false);
			break;		
		case "NG": 
			PopUp.errorBox(me.getActF(), "RITORNO LOANS NG");					
			break;
		}
	}	
	
	
	
	public static void LoansDELETE(Client me) throws SendFailedException, MessagingException, SQLException, InterruptedException{	
		String mSg;
		Commands cmd = Commands.LoanDELETE;
		MessageBack Mb = new MessageBack();		
		System.err.println("dal client su clientCMDloans... idbook:"+me.getIdbook());		
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
	
	public static void LoansDELETERES(Client me,String mes,MessageBack Mb) {
		switch (mes){
		case "OK": 
			PopUp.infoBox(me.getActF(),	"dati loans cancellati OK");					
			me.setActTable(Mb.getTab());
			me.setActF(null);
			me.setSql(null);
			me.setBusy(false);
			break;		
		case "NG": 
			PopUp.errorBox(me.getActF(), "dati loans cancellati NG");					
			break;
		}
	}	

	public static void Loanspopulate(Client me) throws SendFailedException, MessagingException, SQLException, InterruptedException {
		String mSg;
		//Commands cmd = Commands.BookPopulate;
		Commands cmd = Commands.LoanExecuteQuery;		
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
					//me.getSelectedIdBook(),		// id libro
					//me.getSelectedIdUser()		// id utente
					
					);
			MsgSend.setUType(Clients.Librarian);
			// **** Client invia Message
			me.sendM(MsgSend, Mb);	
		}		
	}
	public static void LoanspopulateRES(Client me,String mes,MessageBack Mb) {
		switch (mes){
		case "OK": 
			PopUp.infoBox(me.getActF(), 		"dati tabella loans OK");					
			me.setActTable(Mb.getTab());
			TableLoans.getTable().setModel(Mb.getTab().getModel());
			me.setActF(null);
			me.setSql(null);
			me.setBusy(false);
			break;		
		case "NG": PopUp.errorBox(me.getActF(), "dati tabella loans NG");					
			break;
		}
	}
	
}
