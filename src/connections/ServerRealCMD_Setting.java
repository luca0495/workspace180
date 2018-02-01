//TODO RIORDINA METODI SERVER...
package connections;

import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

import com.sun.org.apache.bcel.internal.generic.BREAKPOINT;

import Core.Guardian;
import Core.Requests;
import database.CheckDBSetting;
import gui.SystemServerSkeleton;

public class ServerRealCMD_Setting {
	private		ServerReal  			me;
	
	private 	SystemServerSkeleton 	meS;		
	private 	Server					Srv;
	private 	Guardian				GpG;
	private 	Requests				Req;		
	private 	String 					mSg;		
	private  	Map<String,Message>  	listcmdDONE;		
	public 		Boolean					Go;
	private 	MessageBack				mSgB;	
	private 	String [][] 			datitabellaBook; 
	private 	String [][] 			datitabellaBooking;
	private 	String [][] 			datitabellaLoans;
	
	public void SelectorLibrarian (	ServerReal ME,
											Message Mes		){
		
		MessageRealServer		M 			= ME.MessageEncapsulation(Mes);
		//MessageBack				x 			= new MessageBack();

	//SL	//-->[GpG [SL]] ---->[DB]	//System.out.println("RealServer :> Rx Account");
	try {					
			System.out.println("RealServer :> Accodo M [ SL ]");
			System.out.println("RealServer :> AL in attesa prima... "+ME.getReq().getSL().getWr());
			
			ME.getReq().getSL().put(M);													//L
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
			
			case tableExistSetting:
				try {
					CheckDBSetting.TableExistSetting();
					ME.getMeS().addMsg(mSg);
					ME.getX().setText(new String ("SRV :> CHECK TABLE Setting Exist :> OK"));	
				} catch (SQLException e) {
					ME.getMeS().addMsg(mSg);
					ME.getX().setText(new String ("SRV :> CHECK TABLE Setting Exist :> NG..."));
					e.printStackTrace();
				}
				break;
				//return x;									
			default:
				break;							
			}		
	}catch (Exception e) {						
	}
	}
}
