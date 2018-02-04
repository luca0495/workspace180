package Core;

import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

import Check.PopUp;
import Table.TableLoans;
import connections.Client;
import connections.Message;
import connections.MessageBack;

public class ClientCMDloans {

<<<<<<< HEAD
	/**spedisce al server il comando richiesta prestito
=======
	/**
>>>>>>> b1453130cb80434d47050fe317ad670cfa60adf5
	 * @param me
	 * @throws SendFailedException
	 * @throws MessagingException
	 * @throws SQLException
	 * @throws InterruptedException
	 */
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

<<<<<<< HEAD
	/**spedisce al server il comando ritorno prestito
	 * @param me 
=======
	/**
	 * @param me
>>>>>>> b1453130cb80434d47050fe317ad670cfa60adf5
	 * @throws SendFailedException
	 * @throws MessagingException
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	public static void LoansReturned(Client me) throws SendFailedException, MessagingException, SQLException, InterruptedException{	
		String mSg;
		Commands cmd = Commands.LoanReturn;
		MessageBack Mb = new MessageBack();		
		
		System.err.println("CLI> idbook: "+me.getSelectedIdBook());
		System.err.println("CLI> iduser: "+me.getSelectedIdUser());
		
		
		
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
					me.getDataLoanReturn(),
					me.getSelectedIdBook(),		// id libro
					me.getSelectedIdUser()		// id utente					
					);
			MsgSend.setUType(Clients.Librarian);
			// **** Client invia Message
			me.sendM(MsgSend, Mb);	
		}	
	}
<<<<<<< HEAD
	/**il ritorno é la conferma dell'avvenuta registrazione del ritorno prestito
=======
	/**
>>>>>>> b1453130cb80434d47050fe317ad670cfa60adf5
	 * @param me
	 * @param mes
	 * @param Mb
	 */
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
	
	/**spedisce al server il comando ritorno prestito
	 * @param me 
	 * @throws SendFailedException
	 * @throws MessagingException
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	public static void LoansRetired(Client me) throws SendFailedException, MessagingException, SQLException, InterruptedException{	
		String mSg;
		Commands cmd = Commands.LoanRetired;
		MessageBack Mb = new MessageBack();		
		
		System.out.println("CLI> idbook: "+me.getSelectedIdBook());
		System.out.println("CLI> iduser: "+me.getSelectedIdUser());

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
					me.getDataLoanReturn(),
					me.getSelectedIdBook(),		// id libro
					me.getSelectedIdUser()		// id utente					
					);
			
			
			MsgSend.setUType(Clients.Librarian);
			// **** Client invia Message
			me.sendM(MsgSend, Mb);	
		}	
	}
	/**il ritorno é la conferma dell'avvenuta registrazione del ritorno prestito
	 * @param me
	 * @param mes
	 * @param Mb
	 */
	public static void LoansRetiredRES(Client me,String mes,MessageBack Mb) {
		
		
		switch (mes){
		case "OK": 
			PopUp.infoBox(me.getActF(),	"RITORNO LOANS ritirato OK, "
					+ "\naggiornate "+Mb.getConteggio()+" tuple");					
			
			
			
			me.setActTable(Mb.getTab());
			me.setActF(null);
			me.setSql(null);
			me.setBusy(false);
			break;		
		case "NG": 
			PopUp.errorBox(me.getActF(), "RITORNO LOANS ritirato  NG");					
			break;
		}
	}	
	
	
<<<<<<< HEAD
	
	
	
	/**spedisce al server il comando di cancellazione prestito
=======
	/**
>>>>>>> b1453130cb80434d47050fe317ad670cfa60adf5
	 * @param me
	 * @throws SendFailedException
	 * @throws MessagingException
	 * @throws SQLException
	 * @throws InterruptedException
	 */
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
			//MsgSend.setUType(Clients.Librarian);
			// **** Client invia Message
			me.sendM(MsgSend, Mb);	
		}	
	}
	
<<<<<<< HEAD
	/**il ritorno rappresenta la conferma dell'avvenuta cancellazione prestito
=======
	/**
>>>>>>> b1453130cb80434d47050fe317ad670cfa60adf5
	 * @param me
	 * @param mes
	 * @param Mb
	 */
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

<<<<<<< HEAD
	/**invia al server la richiesta tabella prestiti per ripopolare tabella prestiti
=======
	/**
>>>>>>> b1453130cb80434d47050fe317ad670cfa60adf5
	 * @param me
	 * @throws SendFailedException
	 * @throws MessagingException
	 * @throws SQLException
	 * @throws InterruptedException
	 */
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
					
					);
			me.sendM(MsgSend, Mb);	
		}		
	}
<<<<<<< HEAD
	/**il ritorno contiene i dati per ripopoalre tabella prestiti
=======
	/**
>>>>>>> b1453130cb80434d47050fe317ad670cfa60adf5
	 * @param me
	 * @param mes
	 * @param Mb
	 */
	public static void LoanspopulateRES(Client me,String mes,MessageBack Mb) {
		switch (mes){
		case "OK": 	
			me.setActTable(Mb.getTab());
			me.setDataloans(Mb.getDatitabella());
			TableLoans.getTable().setModel(Mb.getTab().getModel());			
			me.setActF(null);
			me.setSql(null);
			me.setBusy(false);
			System.out.println("CLI ritorna da TABLE LOANS POPULATE OK LA TAB : "+ Mb.getTab().getRowCount());
			System.out.println("CLI ritorna da TABLE LOANS POPULATE OK LA TAB : "+ Mb.getTab().getEditingRow());								
			break;
			
		case "NG": 
			me.setBusy(false);	
			PopUp.errorBox(me.getActF(), "dati tabella loans NG");					
			
			break;
		}
	}
	
}
