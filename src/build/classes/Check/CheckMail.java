package Check;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JTextField;

import Core.Clients;
import Core.Commands;
import connections.Client;
import gui.Account;
import gui.AppReader;
import gui.Login;
import gui.SL_JFrame;

public class CheckMail {

	private 		JFrame 				ActF			=null;	//Active Frame
	private 		SL_JFrame			ActW			=null;	//Active Window
	private 		Component			ActC			=null;	//Active Component	
	
					Client 				me;
					
	private 		String 				txtMail;
	private 		String 				txtMailMod;

	//TODO DA TESTARE DA LOGIN
		public boolean checkmailLoginForgotPassword(
				Client 		me,
				String 		txtMail,			//getEmailuser()
				String 		txtMailMod,			//getTxtMailMod().getText())
				JFrame 		ActF,
				SL_JFrame	ActW,
				Component	ActC			){
						
				setMe(me);
				setActW(ActW);
				setActF(ActF);
				setActC(ActC);
				setTxtMail(txtMail);
				setTxtMailMod(txtMailMod);
			
				boolean checkok=true;	
				
			if (txtMailMod.equals("")) {
				System.out.println(" ***** sto controllando la email : null");
				checkok=false;
				((Login)getActW()).setMailcheckResult("nulla");
				((Login)getActW()).setMailcheckinprogress(false);
				return checkok;
			}else {
				System.out.println(" ***** sto controllando la email : NEL CAMPO  : "+getTxtMailMod());
				if(Check.checkMail(getTxtMailMod())){//sintatticamente corretta	System.out.println(" ***** sto controllando la email : SINTATTICAMENTE Corretta");
							String email = getTxtMailMod();
							me.setSql(email);				
							//me.setSql2("Account");	//settato prima del lancio metodo dalla finesta Account
							me.setActW(getActW());
							me.setActF(getActF());								
									try {
										me.setCliType(Clients.Reader);	
										me.getCmdLIST().put(Commands.UserREADcheckEmail);
									} catch (InterruptedException e2) {
										e2.printStackTrace(); 
										((Login)getActW()).setMailcheckResult("problemi con user read check mail");	
									}
					}else {								//sintatticamente non corretta
						System.out.println(" ***** sto controllando la email : sintatticamente non corretta");
						checkok=false;
						((Login)getActW()).setMailcheckResult("sintatticamente non corretta");
						((Login)getActW()).setMailcheckinprogress(false);
					}
					//******************************************************************
					return checkok;	
			}
		}	
	
	
	
//TODO DA TESTARE DA ACCOUNT
	public boolean checkmailAccount(
			Client 		me,
			String 		txtMail,			//getEmailuser()
			String 		txtMailMod,			//getTxtMailMod().getText())
			JFrame 		ActF,
			SL_JFrame	ActW,
			Component	ActC			){
		
			setMe(me);
			setActW(ActW);
			setActF(ActF);
			setActC(ActC);
			setTxtMail(txtMail);
			setTxtMailMod(txtMailMod);
			
		boolean checkok=true;
			
			System.out.println(" ***** sto controllando la email ");
			System.out.println(" ***** sto controllando la email : REGISTRATA : "+getTxtMail());
			System.out.println(" ***** sto controllando la email : NEL CAMPO  : "+getTxtMailMod());
		 
			//******************************************************************
			if(Check.checkMail(getTxtMailMod())){
			System.out.println(" ***** sto controllando la email : SINTATTICAMENTE Corretta");
			//sintatticamente corretta		
							if (!getTxtMailMod().equals(getTxtMail())) {
								// modifica alla email
								System.out.println(" ***** sto controllando la email : email MODIFICATA");
								String email = getTxtMailMod();
								me.setSql(email);				
								//me.setSql2("Account");	//settato prima del lancio metodo dalla finesta Account
								me.setActW(getActW());
								me.setActF(getActF());								
								try {
									System.out.println("GUI account:> ottenuti dati user ");
								me.setCliType(Clients.Reader);	
									me.getCmdLIST().put(Commands.UserREADcheckEmail);
								} catch (InterruptedException e2) {
									System.out.println("GUI account:> NON ottenuti dati user ");	
									e2.printStackTrace(); 
									//DA TESTARE...
									((Account)getActF()).setMailcheckResult("problemi con user read check mail");
									((Account)getActF()).getLblMAIL().setIcon(((Account)getActF()).getIconLogoC());
								}
								//*************************************************************	
							}else {	//non modificata
								System.out.println(" ***** sto controllando la email : email non modificata");
								((Account)getActF()).getLblMAIL().setIcon(((Account)getActF()).getIconLogoT());
								((Account)getActF()).setMailcheckResult("non modificata");
								((Account)getActF()).setMailcheckinprogress(false);
							}	
			}else {
			//sintatticamente non corretta
				System.out.println(" ***** sto controllando la email : sintatticamente non corretta");
				((Account)getActF()).getLblMAIL().setIcon(((Account)getActF()).getIconLogoC());
				checkok=false;
				((Account)getActF()).setMailcheckResult("sintatticamente non corretta");
				((Account)getActF()).setMailcheckinprogress(false);
			}
			//******************************************************************
			return checkok;
	}
	
//TODO DA TESTARE DA App Reader
	public boolean checkmailAppReader(
		
		Client 		me,
		//String 		txtMail,			//getEmailuser()
		String 		txtMailMod,			//getTxtMailMod().getText())
		JFrame 		ActF,
		SL_JFrame	ActW,
		Component	ActC			){
	
		setMe(me);
		setActW(ActW);
		setActF(ActF);
		setActC(ActC);
		//setTxtMail(txtMail);
		setTxtMailMod(txtMailMod);
		
		boolean checkok=true;
		
		System.out.println(" ***** sto controllando la email ");
		//System.out.println(" ***** sto controllando la email : REGISTRATA : "+getTxtMail());
		System.out.println(" ***** sto controllando la email : NEL CAMPO  : "+getTxtMailMod());

			//******************************************************************
			if(Check.checkMail(getTxtMailMod())){
			System.out.println(" ***** sto controllando la email : SINTATTICAMENTE Corretta");
			//sintatticamente corretta		
								System.out.println(" ***** sto controllando la email : email MODIFICATA");
								String email = getTxtMailMod();
								me.setSql(email);
								//me.setSql2("AppReader");//settato prima del lancio metodo dalla finesta AppReader
								me.setActW(getActW());
								me.setActF(getActF());								
								try {
									System.out.println("GUI account:> ottenuti dati user ");
								me.setCliType(Clients.Reader);	
									me.getCmdLIST().put(Commands.UserREADcheckEmail);	
								} catch (InterruptedException e2) {
									System.out.println("GUI account:> NON ottenuti dati user ");	
									e2.printStackTrace(); 
									((AppReader)getActF()).setMailcheckResult("problemi con user read check mail");
												//setMailcheckResult("problemi con user read check mail");
									((AppReader)getActF()).getLblCheckMail().setIcon(((AppReader)getActF()).getIconLogoC());
												//this.getLblCheckMail().setIcon(getIconLogoC());
								}
								//*************************************************************		
			}else {
			//sintatticamente non corretta
				System.out.println(" ***** sto controllando la email : sintatticamente non corretta");
				((AppReader)getActF()).getLblCheckMail().setIcon(((AppReader)getActF()).getIconLogoC());
								//this.getLblCheckMail().setIcon(getIconLogoC());
				checkok=false;
				((AppReader)getActF()).setMailcheckResult("sintatticamente non corretta");
								//setMailcheckResult("sintatticamente non corretta");
				((AppReader)getActF()).setMailcheckinprogress(false);
								//setMailcheckinprogress(false);
			}
			//******************************************************************
			return checkok;
	}	
	
	
	

	public Client getMe() {
		return me;
	}


	public void setMe(Client me) {
		this.me = me;
	}


	public String getTxtMail() {
		return txtMail;
	}


	public void setTxtMail(String txtMail) {
		this.txtMail = txtMail;
	}


	public String getTxtMailMod() {
		return txtMailMod;
	}


	public void setTxtMailMod(String txtMailMod) {
		this.txtMailMod = txtMailMod;
	}
	public JFrame getActF() {
		return ActF;
	}


	public void setActF(JFrame actF) {
		ActF = actF;
	}


	public SL_JFrame getActW() {
		return ActW;
	}


	public void setActW(SL_JFrame actW) {
		ActW = actW;
	}


	public Component getActC() {
		return ActC;
	}


	public void setActC(Component actC) {
		ActC = actC;
	}

	
	
}
