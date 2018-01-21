package Core;

import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

import connections.Client;
import connections.Message;
import connections.MessageBack;
import database.MQ_Read;
import gui.AppMain;

public class ClientCMDuser {
	
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
					me.getSql()
					);
			MsgSend.setUType(Clients.Librarian);
			// **** Client invia Message
			me.sendM(MsgSend, Mb);	
		}	
	}

	
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
		System.out.println("ottengo campo 5 :"+datasetting[5]);
	}	

	
	
}
