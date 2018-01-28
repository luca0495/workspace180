package Core;
import java.sql.SQLException;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

import com.sun.org.apache.bcel.internal.generic.POP;

import Check.PopUp;
import Table.TableBooking;
import Table.TableBooks;
import Table.TableLoans;
import connections.Client;
import connections.Message;
import connections.MessageBack;

public class ClientCMDAllTables {

	public static void ATpopulate(Client me) throws SendFailedException, MessagingException, SQLException, InterruptedException {
		String mSg;
		Commands cmd = Commands.GetDataForTables;
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
	public static void ATpopulateRES(Client me,String mes,MessageBack Mb) {
		
		System.out.println("ritornato per GETdataFORallTABLES RES");
		
		switch (mes){
		case "OK": 
			System.out.println("ritornato AT RES OK");
			
			me.setDatabook(		Mb.getDatabook());
			me.setDatabooking(	Mb.getDatabooking());
			me.setDataloans(	Mb.getDataloans());

			if (!me.isRefreshData()) {
				//richiesto dalla prima apertura di searchbook...
				me.setActF(null);
				me.setSql(null);				
				me.getMeMain().setReady(true);
			}
			me.setBusy(false);
			me.setRefreshData(false);
			break;
			
		case "NG": 
			
			if (!me.isRefreshData()) {
				//richiesto dalla prima apertura di searchbook...
				me.getMeMain().setReady(true);	
			}
			me.setBusy(false);
			me.setRefreshData(false);
			break;
		
		default:
			break;
			
		}
	}	
	
}
