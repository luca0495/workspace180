package connections;

import java.sql.SQLException;

import database.CheckDBSetting;

public class ServerReal_cmd_LS {

	
	public static MessageBack exe (ServerReal ME,MessageRealServer M,MessageBack x) {
		x.setText("eseguito");
		//
		try {					
			System.out.println("RealServer :> Accodo M [ SL ]");
			System.out.println("RealServer :> AL in attesa prima... "+ME.getReq().getSL().getWr());
			ME.getReq().getSL().put(M);
			//******************************************************************************
			while (!ME.getGo()){
				try {
					System.out.println("REAL SERVER L-S:> in attesa GO da Guardian");
					Thread.sleep(10);
				} catch (Exception e) 
				{}
					System.out.println("REAL SERVER L-S:> go "+ME.getGo());						
			}	//Attesa del turno...									
			//******************************************************************************							
			switch (M.getMsg().getCommand()) {
				case tableExistSetting:									System.out.println("REAL SERVER :> fine attesa \nREAL SERVER :> Gestisco RICHIESTA :> tableExistSetting ");					
					try {
						
						CheckDBSetting.TableExistSetting();
						
						ME.getMeS().addMsg(ME.getmSg());
						x.setText(new String ("SRV :> CHECK TABLE Setting Exist :> OK"));	
					} catch (SQLException e) {
						ME.getMeS().addMsg(ME.getmSg());
						x.setText(new String ("SRV :> CHECK TABLE Setting Exist :> NG..."));
						System.out.println("problemi con controllo tabella Setting");
						e.printStackTrace();
					}
						System.out.println("SYS AL :> srv ritorna "+x.getText());										
					return x;									
				default:
					x.setText(new String ("SRV :> CHECK TABLE Setting Exist :> NG..."));
					return x;						
			}		
		}catch (Exception e) {						
		}
		return x;
	}		
}
