package Core;
import java.sql.SQLException;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import Check.PopUp;
import connections.Client;
import connections.Message;
import connections.MessageBack;

public class ClientCMDBook {


	/** spedisce al server comando bookexecutequery, la query viene prima memorizzata nel campo sql del Client,
	 * nella creazione del messaggio viene copiato nel campo sql. il ritorno � gestito da BookpopulateRES 
	/**
	 * @param me
	 * @throws SendFailedException
	 * @throws MessagingException
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	public static void Bookpopulate(Client me) throws SendFailedException, MessagingException, SQLException, InterruptedException {
		String mSg;
		//Commands cmd = Commands.BookPopulate;
		Commands cmd = Commands.BookExecuteQuery;
		
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
			me.sendM(MsgSend, Mb);	
		}		
	}

	/**il messaggio di ritorno dal server viene usato per ripopolare la jtable book
	/**
	 * @param me
	 * @param mes
	 * @param Mb
	 */
	public static void BookpopulateRES(Client me,String mes,MessageBack Mb) {
		
		System.out.println("ritornato per bookpopulate RES");
		
		switch (mes){
		case "OK": 
			System.out.println("ritornato per bookpopulate RES OK");
			PopUp.infoBox(me.getActF(), 		"dati tabella Book OK");			
			me.setActTable(Mb.getTab());
			me.setDatabook(Mb.getDatitabella());
			me.setActF(null);
			me.setSql(null);
			me.setBusy(false);
			break;		
		case "NG": PopUp.errorBox(me.getActF(), "dati tabella Book NG");					
			break;
		
		default:
			break;
			
		}
	}	
	
}
