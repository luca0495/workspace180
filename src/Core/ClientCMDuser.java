package Core;

import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

import Check.PopUp;
import connections.Client;
import connections.Message;
import connections.MessageBack;
import database.MQ_Read;
import gui.AppMain;


/**
 * @author luca
 *
 */
public class ClientCMDuser {
	
	/**
	 * @param me
	 * @throws SendFailedException
	 * @throws MessagingException
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	public static void UserPasswordRecovery(Client me) throws SendFailedException, MessagingException, SQLException, InterruptedException{	
		String mSg;
		Commands cmd = Commands.UserPasswordRecovery;
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
					me.getSql(),				//q
					me.getSql2(),				//mail
					me.getPw()					//pw
					
					);
			MsgSend.setUType(Clients.Librarian);
			// **** Client invia Message
			me.sendM(MsgSend, Mb);	
		}	
	}

	//UserPasswordDelTemp
	
	/**
	 * @param me
	 * @throws SendFailedException
	 * @throws MessagingException
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	public static void UserPasswordDelTemp(Client me) throws SendFailedException, MessagingException, SQLException, InterruptedException{	
		String mSg;
		Commands cmd = Commands.UserPasswordRemovetemp;
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
					me.getSql(),
					me.getSql2()

					);
			//MsgSend.setUType(Clients.Librarian);
			// **** Client invia Message
			me.sendM(MsgSend, Mb);	
		}	
	}
/**
 * @param me
 * @param mes
 * @param Mb
 */
public static void UserPasswordDelTempRES(Client me,String mes,MessageBack Mb) {
		
		System.out.println("ritorna da password del temp RES");
		
		switch (mes){
		case "OK": 
			System.out.println("ritornato per bookpopulate RES OK");
			PopUp.infoBox(me.getActF(), 		"pw temp annullata 	OK");			
			me.setActTable(Mb.getTab());
			me.setActF(null);
			me.setSql(null);
			me.setBusy(false);
			break;		
		case "NG": PopUp.errorBox(me.getActF(), "pw temp annullata 	NG");					
			break;
		
		default:
			break;
			
		}
	}	
	
	
	
	
	
	/**
	 * @param me
	 * @param StartWindow
	 */
	public static void ClientGetDataFromSetting(Client me,AppMain StartWindow) {
		String dlh = "127.0.0.1";//default localhost
				
		try {
			String [] datasetting = MQ_Read.readSettingTable();
			ClientGetDataFromSettingTestResult(datasetting);
			
			me.setUSERNAME(	datasetting[4]);
			me.setPASSWORD( datasetting[5]);
			
			//analisi server type
			if (datasetting[3]==null){
										me.setSRVaddress(dlh);
			}else {
				me.setSRVtype(datasetting[3]);
				
					switch (datasetting[3]) {//tipo di server
					case "":
						
						break;
					case "www":
						me.setSRVaddress(datasetting[2]);
						StartWindow.getComboBox().setSelectedIndex(2);			
						break;
					case "lan":
						me.setSRVaddress(datasetting[1]);
						StartWindow.getComboBox().setSelectedIndex(1);
						break;
					case "local":
						me.setSRVaddress(datasetting[0]);
						StartWindow.getComboBox().setSelectedIndex(0);
						break;	
					default:
										me.setSRVaddress(dlh);
						break;
					}
			}
			
			System.out.println("indirizzo email utilizzato dal srv: "+me.getUSERNAME());
			System.out.println("tipo di 	server : "+ datasetting[3]);
			System.out.println("indirizzo 	server : "+ me.getSRVaddress());
			
			StartWindow.getTextField_3().setText(me.getSRVaddress());
			
		
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	
	
	}
	
	
	public static void ClientGetDataFromSettingTestResult(String[]datasetting) {
		//test
		System.out.println("ottengo campo 0 :"+datasetting[0]);
		System.out.println("ottengo campo 1 :"+datasetting[1]);
		System.out.println("ottengo campo 2 :"+datasetting[2]);
		System.out.println("ottengo campo 3 :"+datasetting[3]);
		System.out.println("ottengo campo 4 :"+datasetting[4]);
		//System.out.println("ottengo campo 5 :"+datasetting[5]);
	}	

	
	
}
