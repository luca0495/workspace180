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
import gui.ResearchBooks;
import javafx.scene.control.Separator;

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
					me.getFbook(),					
					me.getFbooking(),
					me.getFloans()
					);
			//MsgSend.setUType(Clients.Librarian);
			// **** Client invia Message
			me.sendM(MsgSend, Mb);	
		}		
	}
	public static void ATpopulateRES(Client me,String mes,MessageBack Mb) throws SQLException, InterruptedException {
		
		System.out.println("ritornato per GETdataFORallTABLES RES");
		
		switch (mes){
		case "OK": 
			System.out.println("ritornato AT RES OK");
			me.getMeMain().setReady(true);
			//*****************************************************************
			if (!me.isRefreshData()) {
				//richiesto dalla prima apertura di searchbook...
				me.setActF(null);
				me.setSql(null);				
				me.getMeMain().setReady(true);
			}//apertura finesta reser
			//*****************************************************************
			me.setDatabook(		Mb.getDatabook());
			me.setDatabooking(	Mb.getDatabooking());
			me.setDataloans(	Mb.getDataloans());
			
			
			
			if ( me.getActW().getModel()=="search") {
			//aggiorno tables
			 ResearchBooks x = (ResearchBooks) me.getActW();
			 
			// x.getPanelTableResearch().getTm().setData(me.getDatabook());
			// x.getPanelTableBookingResearch().getTm().setData(me.getDatabooking());
			// x.getPanelTableLoansResearch().getTm().setData(me.getDataloans()); 
				
			// TableBooks.PopulateData(null,me);
			// TableBooking.PopulateData(null,me);
			// TableLoans.PopulateData(null,me);			
			 
			 
			 Clients ClType = me.getCliType(); 
			 
			}

			me.setBusy(false);
			me.setRefreshData(false);
			azzerafiltri(me);
			break;
			
		case "NG": 
			
			if (!me.isRefreshData()) {
				//richiesto dalla prima apertura di searchbook...
				me.getMeMain().setReady(true);	
			}
			me.setBusy(false);
			me.setRefreshData(false);
			azzerafiltri(me);
			break;
		
		default:
			azzerafiltri(me);
			break;
			
		}
	}	
	


		public static void azzerafiltri(Client me) {
			me.setFbook(null);
			me.setFbooking(null);
			me.setFloans(null);	
		}	
}
