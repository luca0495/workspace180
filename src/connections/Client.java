package connections;
//aggiorna drivers
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;


import javax.mail.MessagingException;
import javax.mail.SendFailedException;


import javax.mail.MessagingException;
import javax.mail.SendFailedException;


import javax.swing.DropMode;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.sun.org.apache.bcel.internal.generic.POP2;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import Check.PopUp;
import Core.ClientCMDuser;
import Core.Clients;
import Core.Commands;
import Core.CommandsList;
import Core.CommandsType;
import Core.Requests;
import Core.RequestsList;
import Core.SearchFor;
import ProvaEmail.EmailSender;
import Table.TableBooks;
import database.MQ_Delete;
import database.MQ_Insert;
import database.MQ_Read;
import database.MQ_Update;

import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.EventQueue;
import java.io.IOException;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Random;

import gui.Account;
import gui.AppLibrarian;
import gui.AppReader;
import gui.AppType;
import gui.Login;
import gui.ResearchBooks;
import gui.AppMain;
import gui.SL_JFrame;
import gui.SystemClientStub;
import sun.security.x509.IPAddressName;

import java.awt.Component;

public class Client implements Serializable, Runnable  {
	public 				int 				ctc=0;
	private				int 				LoginTry=0;
	
	private 			AppMain				meMain=null;
	private 			Account				meAcc=null;
	
	private 			JFrame 				ActF			=null;	//Active Frame
	private 			SL_JFrame			ActW			=null;	//Active Window
	private 			Component			ActC			=null;	//Active Component	
	private 			JTable 				ActTable		=null;
	private 			Clients				ClientType;
	private 			ServerStub			srv;		
	private 			boolean 			stubok			=false;
	private 			boolean 			repeatconn		=true;
	private 			int 				repeatconnCount = 1;	
	static private 		int 				conn;	
	private 			String 				tc  ; 
	private 			Message				mess;
	private 			MessageBack			mSgBack;
	private 			String				mSg;
	
	private				CommandsList		CmdLIST			=new CommandsList();
	private 			boolean				ClientON=true;
	
	private 			Message				mSgTOsend;
	
	private				boolean				Busy=false;			//SE ARRIVA RICHIESTA INVIO COMANDO BUSY == TRUE
	private				boolean				BusyControl=false;	//SE ARRIVA RICHIESTA INVIO COMANDO BUSY == TRUE

	//TEST OK...
	
	//private 			String				SRVaddress="127.0.0.1";		//localhost
	//private 			String				SRVaddress="192.168.0.6";	//localhost
	//private 			String				SRVaddress;

	//SETTING *************************************************
	private 			String				SRVtype;
	private 			String				SRVaddress;	
	
	private 			String				ipaddLocal;
	private 			String				ipadLan;
	private 			String				ipadWww;
	
	private 			String				PASSWORD;	//private String PASSWORD="ACmilan1994$";
	private 			String				USERNAME;	//private String USERNAME="llazzati@studenti.uninsubria.it";
	//SETTING *************************************************
	
	private 			String 				Sql;
	private 			String 				Sql2;
	private				String				pw;
	private				String[] 			datiUtente;
	
	private 			int 				idut;
	private 			String				to;					//email destinatario per registrazione, PUò ESSERE IL PROBLEMA
	//private 			String				emailR;				//email tramite la quale ricercare utente da LOGIN
	
	
	public Client() throws Exception {		
		this.setCliType(Clients.Default);
		System.out.println("Creato 		 Client");
		System.out.println("CType:		"+getCliType());	
	}
//------------------------------------------------------------------------------------------
	public static void main(String[] args) throws Exception {			
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					Client x  = new Client();
					AppMain StartWindow = new AppMain(x);
					x.setMeMain(StartWindow);
					StartWindow.getFrame().setVisible(true);	//System.out.println("creato start windows");
					
					ClientCMDuser.ClientGetDataFromSetting(x,StartWindow);					
					ClientConnectionController y1 = new ClientConnectionController(x,10000);//1 controllo connessione al server OGNI 10 sec										
					
					StartWindow.addMsg("Initialize...");	
					new Thread(x).start();	  // Logica di controllo comandi ricevuti
					new Thread(y1).start();  // Client Connection Controller [CCC]	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});	
	}	
//------------------------------------------------------------------------------------------
	@Override
	public void run() {	
		try {
			while (isClientON()){
					//Attesa comandi...					
					while ( getCmdLIST().getCmdList().size() < 1){								
							//System.out.println("CLI :> cmdLIST.size : "+getCmdLIST().getCmdList().size());							
							//System.out.println("CLI :> IN ATTESA di COMANDI... ");
						try {
							//System.out.println("CLI :> IN ATTESA di COMANDI... dormo100");
							Thread.sleep(100);
						} catch (Exception e) { e.printStackTrace();
						}
					}
							System.out.println("CLI :> cmdLIST.size : "+getCmdLIST().getCmdList().size());							
							System.out.println("CLI :> Comandi in LISTA ");					
					//Comando Arrivato					
					//Comando Prelevo dalla lista CmdLIST
					Commands com = getCmdLIST().take();
							System.out.println("CLI :> Trovata richiesta in coda "+ com.Den );																		
					/*		
					while (Busy){
							System.out.println("CLI :> Trovata richiesta in coda "+ com.Den + " [CLI  BUSY], attendo... ");
						try {
							Thread.sleep(10);
						} catch (Exception e) { e.printStackTrace();
						}						
					}
					*/
// !!!! --------------------------
					setBusy(true);
// !!!! --------------------------					
					System.out.println("CLI :> Trovata richiesta in coda "+ com.Den + " [CLI !BUSY] < ORA ESEGUO > ");						
					switch (com.getCommandType()) {				//   		Core\CommandsType	***   									
				//	****************************************************	Connections	   		***		
					case CONNECTION:										
								switch (com) {							
								case ConnSTOP: 					//   		Core\Commands		***
												ClientConnectionClose();
												setBusy(false);
									break;
								case ConnTEST: 
												ClientConnectionTest();
												setBusy(false);
									break;
								}
					break;
				//	****************************************************	Test		   		***	
					case TEST:
								switch (com) {							
								case DBExist:							
									
									break;
								default:
									
									break;
								}
					break;
				//	****************************************************	Read		   		***			
					case READ:
								switch (com) {							
						//	book
								case BookExecuteQuery:	//arriva DA GUI TABLEBOOKS //System.out.println("passato come parametro sql : "+this.Sql);
														BookPopulate();
									break;
								case BookLast:			//arriva DA GUI TABLEBOOKS //
														BookLast();
									break;
						//	Person
								case UserREAD: 						
														UserGetData();			//necessario setSql con query completa
									break;
								case UserREADbyEmail: 								
														UserGetDatabyEmail();		//necessario setSql con email
									break;
								case UserREADbyEmailAcc:																		
														UserGetDatabyEmailAcc();	//necessario setidut
									break;
								case UserREADbyEmailMod: 																										
														UserGetDatabyEmailMod();	//necessario setidut
									break;

								case UserREADlogin: 								
														UserGetDataLogin();			//necessario setSql con email setSql2 con password					
									break;
								case UserREADaccountMod: 								
														UserGetDataAccountMod();	//necessario setSql con email setSql2 con password					
									break;									
								case UserREADcheckEmail: 								
														UserREADcheckEmailExist();	//necessario setSql con email 					
									break;									
								case UserREADcheckCF:
														UserREADcheckCfExist();		//necessario setSql con cf 		
									break;		
						//	Loans
								case LoanREAD: 
									
									break;
						//	Booking			
								case BookingREAD: 
									
									break;	
									
									
									
								default:
									
									break;
								}				
					break;
				//	****************************************************	Change		   		***			
					case CHANGE:
								switch (com) {							
	
						//	All Table		
							case tableExistBook: 
														ClientCheckExistTableBook();
								break;	
							
							case tableExistLoans: 
														ClientCheckExistTableLoans();
								break;
							
							case tableExistPerson: 
														ClientCheckExistTablePerson();
								break;
							case tableExistBooking: 
														ClientCheckExistTableBooking();
								break;
							case tableExistSetting: 
														ClientCheckExistTableSetting();
								break;
								
								
								
						//	book			
							case BookADD:				// arriva DA GUI 		TABLEBOOKS									
														ClientBookAdd();
								break;
							case BookDELETE: 			// arriva DA classe 	tableupdatebooks
														ClientBookDelete();				
								break;
						
						//	Person
							case UserUPDATE: 
														UserUPDATE();	
								break;
							case UserDELETE: 
														UserDELETE();
								break;	
							
							case UserREADloginFIRST: 								
														UserGetDataLoginFIRST();//necessario setSql con email setSql2 con password_temp
								break;									

							case UserRegistration: 
														ClientCHANGEuserRegistration();
								break;
							case UserPasswordRecovery: 
														ClientCMDuser.UserPasswordRecovery(this);
								break;
							
							
							case UserActivation: 
											
								break;				
									
						//	Loans
							case LoanNew: 
								
								break;
							case LoanListADD: 
								
								break;								
							case LoanListREMOVE: 
								
								break;								
							case LoanBookGet: 
									
								break;
							case LoanBookGiveback: 
								
								break;
							case LoanNoticeAvaiable: 
								
								break;															
							case LoanNoticeExpiration: 
								
								break;								

								
								
								
								
								
								
							default:				
								break;
								}
					break;
				//	****************************************************	Default		   		***			
					default:
						
					break;
				}													
				
			}//Client settato off		
			//Logica();
		} catch (Exception e) {			
			e.printStackTrace();
		}		
	}
	//------------------------------------------------------------------------------------------

	void Logica() throws Exception {	
		 		int ctc=0;						//---- Connection test counter				
		 		while (isRepeatconn()) {		//---- Controllo continuo della connessione con il Server		
		 							while (Busy){
		 								System.out.println("CLI :> Logica > attesa ritorno CMD , skippo controllo connessione");
		 								try {
											System.out.println("CLI :> Logica > dormo 1000");	
		 									Thread.sleep(1000);
										} catch (Exception e) { e.printStackTrace();
										}	
		 							}
		 							setBusyControl(true);		 							
		 								try{									
										System.out.println(mSg = "CLI :> CType:"	+ getCliType());
										//System.err.println(mSg = "ActW:"	+ getActW());						
										//getActW().addMsg(mSg);											
														if (isStubok())  {	
															try {
															//	if ((mSg = srv.testConnectionSS().getText()).equals(new String ("SRV - Connessione OK"))){																	
															//  se impegnato in query non esegue conn test																
																
																this.ctc++;																					
																System.out.println("CLIENT :> ciclo di controllo "+ctc);																																							
																				try {											
																					this.mSgBack = 	Request(Commands.ConnTEST);																																							
																					this.mSg 	= 	mSgBack.getText();														
																				} catch (Exception e) {				
																					e.printStackTrace();
																					System.out.println("CLI:> Logica > messaggio non letto");
																				}
																			//if (mSg.equals(new String ("SRV - Connessione OK"))){	
																			if (mSgBack.getText().equals(new String ("SRV - Connessione OK"))){	
																				
																					if ( ActW.getSL_Type()==AppType.AppMain) {
																						getActW().addMsg(new String ("CLI:> Connection Test "+ctc+" result"+mSg));	
																					}
																					else {
																						if ( ActW.getSL_Type()==AppType.AppLoginReader) {
																							System.out.println(" finestra attiva READER...  CLI:> Connection Test "+ctc+" result"+mSg);																							
																						}
																					}			
																		}else{
																			setStubok(false);
																			
																			if ( ActW.getSL_Type()==AppType.AppMain) {
																				getActW().addMsg(new String ("CLI:> Connection Test result : NG"));	
																			}
																			else {
																				if ( ActW.getSL_Type()==AppType.AppLoginReader) {
																					System.out.println("  finestra attiva READER...  CLI:> Connection Test result : NG\\  ");																							
																				}
																			}			
																		}			
															} catch (Exception e) {							
																e.printStackTrace();
																System.out.println(mSg = "CLI :> srv stub ERROR ");	
																getActW().addMsg(mSg);		
																setStubok(false);
															}				
															Thread.sleep(1000);// test conn every 0.1 sec
														}
														else {															
															for (int i = 5;i>0;i--){
																try {Thread.sleep(1000);
																System.out.println(mSg = "CLI :> Verifica Stub NG \nTentativo "+getRepeatconnCount()+ " \nnuovo tentativo tra "+i+" secondi ");
																getActW().addMsg(mSg);
																} catch (Exception e) {
																}	
															}
															incRepeatconnCount();	
															srv  = new ServerStub(this,SRVaddress);	
															}
										}catch (Exception e) {e.printStackTrace();} finally {}//server.closeConnection();}	
		 								setBusyControl(false);
		 								
					}	//while					
			}		//Logica
//------------------------------------------------------------------------
		public MessageBack Request(Commands cmd)throws IOException{						

			MessageBack Mb = new MessageBack();	
			System.out.println("CLI :> Request ricevuto da GUI :> "+cmd.toString());
			if (!stubok){
				Mb.setText(mSg = "CLI :>  nessuna connessione attiva , riprovare ");
				getActW().addMsg(new String ("Connection Test result"+mSg));
			}else{	
				System.out.println("CLI :> Stub OK");
	// **** Client crea Message	
			Message MsgSend = new Message(	
			cmd,						// Comando richiesto
			this.getCliType() ,			// tipo di Client , Admin,Librarian,Reader
			this.toString()				// id Client 
										);		
	// ****	
			try {
					System.out.println("CLI :> spedisco a stub comando: "+MsgSend.getCmd());	
					Mb = this.getSrv().SendRequest(MsgSend);	// SPEDISCE AL SRV [STUB] MESSAGE contenente COMMAND	
			} 
			catch (IOException io){	
				io.printStackTrace();
				Mb.setText("CLI:>  collegamento mancante");						
			} 
			catch (ClassNotFoundException e) {
				e.printStackTrace();
				Mb.setText("CLI:>  class not found");	
			}
		}
			//************************************************************	
			// RITORNA UN MESSAGE-BACK
			return Mb;			
		//************************************************************
		}	
	// comandi
		
	/*
	public String testconn (){
		String testcon1=null;
		testcon1 = srv.testConnectionSS().getText();
		return testcon1;
	}
	*/
		
	public MessageBack testconn(){
		//testcon1 = srv.testConnectionSS().getText();
		try {
			return this.mSgBack = 	Request(Commands.ConnTEST);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.mSgBack;
		
	}
	
	public MessageBack closeconn(){	
		//srv.closeConnectionSS();
		try {
			return this.mSgBack = 	Request(Commands.ConnSTOP);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.mSgBack;
	}	
	/*
	// test
	public void metodo1 () throws IOException, ClassNotFoundException{		
			for (int i = 0 ; i< 10; i++){	
				System.out.print("CLIENT : cmd no " + i + " Richiedo test 1");				
			
			String M1 = this.getSrv().metodotest1().getText();
				getActW().addMsg(M1);
				System.out.println("CLIENT : Elemento ritornato: " + M1);	
			}
	}
	public void metodo2 () throws IOException, ClassNotFoundException{		
		
			System.out.print("CLIENT : cmd no  Richiedo test 2");				
			
			String M1 = this.getSrv().metodotest2().getText();
				getActW().addMsg(M1);
				System.out.println("CLIENT : Elemento ritornato: " + M1);	
		
	}	
	public void metodo3 () throws IOException, ClassNotFoundException{		
		
			System.out.print("CLIENT : cmd no  Richiedo test 3");				
			
			String M1 = this.getSrv().metodotest3().getText();
				getActW().addMsg(M1);
				System.out.println("CLIENT : Elemento ritornato: " + M1);	
	
	}
*/
	void ClientConnectionTest() throws Exception {	
 								try{									
								System.out.println(mSg = "CLI :> CType:"	+ getCliType());										
												if (isStubok())  {	
													try {	
														ctc++;																					
														System.out.println("CLIENT :> ciclo di controllo "+ctc);																																							
																		try {											
																			this.mSgBack = 	Request(Commands.ConnTEST);																																							
																			this.mSg 	= 	mSgBack.getText();															
																		} catch (Exception e) {				
																			e.printStackTrace();
																			System.out.println("CLI:> Logica > messaggio non letto");
																		}																		
																		if (mSgBack.getText().equals(new String ("SRV - Connessione OK"))){
																			
																			if ( ActW.getSL_Type()==AppType.AppMain) {
																				getActW().addMsg(new String ("CLI:> Connection Test "+ctc+" result"+mSg));	
																			}
																			else {
																				if ( ActW.getSL_Type()==AppType.AppLoginReader) {
																					System.out.println(" finestra attiva READER...  CLI:> Connection Test "+ctc+" result"+mSg);																							
																				}
																			}
																		}else{
																			setStubok(false);
																		
																			if ( ActW.getSL_Type()==AppType.AppMain) {
																				getActW().addMsg(new String ("CLI:> Connection Test result : NG"));	
																			}
																			else {
																				if ( ActW.getSL_Type()==AppType.AppLoginReader) {
																					System.out.println("  finestra attiva READER...  CLI:> Connection Test result : NG\\  ");																							
																				}
																			}
																		}																		
													} catch (Exception e) {							
														e.printStackTrace();
														System.out.println(mSg = "CLI :> srv stub ERROR ");	
														getActW().addMsg(mSg);		
														setStubok(false);
													}				
													//Thread.sleep(1000);// test conn every 0.1 sec
												}
												else {// !isStubok															
													//conteggio...
														for (int i = 5;i>0;i--){
															try {Thread.sleep(1000);
															System.out.println(mSg = "CLI :> Verifica Stub NG \nTentativo "+getRepeatconnCount()+ " \nnuovo tentativo tra "+i+" secondi ");
															getActW().addMsg(mSg);
															} catch (Exception e) {
															}	
														}
													//conteggio...
													incRepeatconnCount();
													
													
													srv  = new ServerStub(this,SRVaddress);
													setBusy(false);
												}
								}catch (Exception e) {e.printStackTrace();} finally {}//server.closeConnection();}	 								
// !!!! --------------------------
 		setBusy(false);
// !!!! --------------------------										
	}//ClientConnectionTest
	
	void ClientConnectionClose() throws Exception {	
			try{									
		System.out.println(mSg = "CLI :> CType:"	+ getCliType());										
						if (isStubok())  {	
							try {	
								ctc++;																					
								System.out.println("CLIENT :> ciclo di controllo "+ctc);																																							
												try {											
												System.out.println("invo comando allo STUB...");
													
													this.mSgBack = 	Request(Commands.ConnSTOP);																																							
													this.mSg 	= 	mSgBack.getText();
													
												} catch (Exception e) {				
													e.printStackTrace();
													System.out.println("CLI:> Logica > messaggio non letto");
													
												
												
												}	
												
												
												if (mSgBack.getText().equals(new String ("SRV - chiudo socket tra 5 secondi..."))){
													
													if ( ActW.getSL_Type()==AppType.AppMain) {
														getActW().addMsg(new String ("CLI:> Connection Close in Progress... "+ctc+" result"+mSg));
														
														
													}
													else {
														if ( ActW.getSL_Type()==AppType.AppLoginReader) {
															System.out.println(" finestra attiva READER...  CLI:> Connection Test "+ctc+" result"+mSg);																							
														
														}
													}
												}else{
													setStubok(false);
												
													if ( ActW.getSL_Type()==AppType.AppMain) {
														getActW().addMsg(new String ("CLI:> Connection Test result : NG"));	
													}
													else {
														if ( ActW.getSL_Type()==AppType.AppLoginReader) {
															System.out.println("  finestra attiva READER...  CLI:> Connection Test result : NG\\  ");																							
														}
													}
												}																		
							} catch (Exception e) {							
								e.printStackTrace();
								System.out.println(mSg = "CLI :> srv stub ERROR ");	
								getActW().addMsg(mSg);		
								setStubok(false);
							}				
							//Thread.sleep(1000);// test conn every 0.1 sec
						}

		}catch (Exception e) {e.printStackTrace();} finally {}//server.closeConnection();}	 								
//!!!! --------------------------
setBusy(false);
//!!!! --------------------------										
}//ClientConnectionTest
	
	
	
	
	public void sendM(Message MsgSend,MessageBack Mb) throws SendFailedException, MessagingException, SQLException, InterruptedException{
			try {
						System.out.println("CLI :> spedisco a STUB comando: "+MsgSend.getCmd());	
						
						
						Mb = this.getSrv().SendRequest(MsgSend);	// SPEDISCE AL SRV [STUB] MESSAGE contenente COMMAND								
								
						// Reazioni di Client ai messaggi ritornati dal Server
						
						
						
						switch (Mb.getText()) {
							case "OK":							
								break;
	
							case "STUB :> Eccezione *** nessuna risposta da SKELETON":
								System.out.println("CLI - ECCEZIONE DA STUB");	
								break;
								
							case "SRV :> user Registration :> OK":							
								PopUp.infoBox(getActC(), "Registrazione avvenuta con successo, attiva account dal codice che ti abbiamo inviato");							
								System.out.println("TO ARRIVATO AL CLIENT : "+getTo());									
								EmailSender.send_uninsubria_email(getTo(),this);								
								this.setTo(null);
								this.setSql(null);		
								
								//TODO RIVEDI E CONTROLLA
								this.getMeMain().getFrame().setVisible(true);
								this.getMeMain().getBtnAccount().setVisible(true);
								
								break;
							
							case "SRV :> UPRecovery :> OK":
								System.out.println("TORNATO AL CLIENT UPRECOVERY OK");
								// mail     == getsql2
								// password == getpw
								
								EmailSender.send_uninsubria_recoverypassword(getSql2(), this, getPw());
								
								Login X = (Login)ActW;
								
								//X.PanelRegi.setVisible(false);
								X.getPr().setVisible(false);
								//X.PanelFirstAcc.setVisible(true);
								X.getPfa().setVisible(true);
								//X.PanelForgPass.setVisible(false);
								
								
								break;
								
							case "SRV :> UPRecovery :> NG":
								
								System.out.println("TORNATO AL CLIENT UPRECOVERY NG");
								
								break;
								
								
								
							case "SRV :> table book populate :> OK":	System.out.println("ritornato al client POPULATE OK : ");									
								//System.out.println(Mb.getTab().toString());
								setActTable(Mb.getTab());
								/*
								//test ok
								System.out.println("ricavo valore record 0 campo 0 : "+ getActTable().getModel().getValueAt(0, 0));
								System.out.println("ricavo valore record 0 campo 1 : "+ getActTable().getModel().getValueAt(0, 1));
								System.out.println("ricavo valore record 0 campo 2 : "+ getActTable().getModel().getValueAt(0, 2));
								System.out.println("ricavo valore record 0 campo 3 : "+ getActTable().getModel().getValueAt(0, 3));
								System.out.println("ricavo valore record 0 campo 4 : "+ getActTable().getModel().getValueAt(0, 4));
								System.out.println("ricavo valore record 0 campo 5 : "+ getActTable().getModel().getValueAt(0, 5));								
								System.out.println("ricavo valore record 1 campo 0 : "+ getActTable().getModel().getValueAt(1, 0));
								System.out.println("ricavo valore record 1 campo 1 : "+ getActTable().getModel().getValueAt(1, 1));
								System.out.println("ricavo valore record 1 campo 2 : "+ getActTable().getModel().getValueAt(1, 2));
								System.out.println("ricavo valore record 1 campo 3 : "+ getActTable().getModel().getValueAt(1, 3));
								System.out.println("ricavo valore record 1 campo 4 : "+ getActTable().getModel().getValueAt(1, 4));
								System.out.println("ricavo valore record 1 campo 5 : "+ getActTable().getModel().getValueAt(1, 5));
								//test ok
								*/								
								TableBooks.getTable().setModel(Mb.getTab().getModel());
								//getActTable().update(null);
								this.setActF(null);
								this.setSql(null);
								setBusy(false);
								break;		
							case "SRV :> table book populate :> NG":System.out.println("ritornato al client POPULATE NG : ");	
								clrParFS();
								break;
							
							// BOOK Add	
							case "SRV :> book add :> OK":			System.out.println("ritornato al client BOOK Add OK : ");
								//clrParFS();	
								TableBooks.PopulateData( null, this);//AGGIORNA TABLE me.ActTable
								break;
							case "SRV :> book add :> NG":
								System.out.println("ritornato al client BOOK Add NG : ");									
								clrParFS();
								break;
								
							// BOOK Delete
							case "SRV :> book del :> OK":			System.out.println("ritornato al client BOOK Del OK : ");									
								clrParFS();
								TableBooks.PopulateData( null, this);//AGGIORNA TABLE me.ActTable
								break;
							case "SRV :> book del :> NG":
								System.out.println("ritornato al client BOOK Del NG : ");									
								clrParFS();	
								break;
							
								
						// USER Read Data
							case "SRV :> selected user :> OK" :
								System.out.println("ritornato al client UserDATA OK : ");
								//contiene gli user's data	
								String name 		= Mb.getRowUser()[0];
								String surname 		= Mb.getRowUser()[1];

								clrParFS();	
								break;
								
							case "SRV :> selected user :> NG" :
								System.out.println("ritornato al client UserDATA NG : ");
								
								clrParFS();	
								break;
								
								
						// USER Read Data by email
							case "SRV :> selected user by email:> OK":
								
								System.out.println("ritornato al client UserDATA by email OK : ");
								
								//devo riottenere id utente e modificare typeuser QUI
								String [] UserData= Mb.getRowUser();
								String UserType = UserData[7];
								
								switch (UserType) {
								case "Administrator":
									System.out.println("riconosco "+ getCliType());
									setCliType(Clients.Admin);
									break;
								case "Lettore":
									setCliType(Clients.Reader);
									System.out.println("riconosco "+ getCliType());
									break;
								case "Libraio":
									setCliType(Clients.Librarian);
									System.out.println("riconosco "+ getCliType());
									break;
								case "Guest":
									setCliType(Clients.Guest);
									System.out.println("riconosco "+ getCliType());
									break;
								default:
									setCliType(Clients.Guest);
									System.out.println("riconosco ");
									break;
								}
								
								
								
								//OLD APRE FINESTRA ACCOUNT
								/*
								Account aX = (Account) ActW;
								System.out.println(" settato finestra attiva : "+ActW.toString());		
								
								aX.updateall(Mb.getRowUser());				// PER PANNELLO ACCOUNT 
								aX.updateallModify(Mb.getRowUser());		// PER PANNELLO MODIFY
								*/
								
								
								//System.out.println(" updatato finestra account ");		

								
								System.out.println("ricavo valore nome: "+Mb.getRowUser()[1]);
								System.out.println("ricavo valore nome: "+Mb.getRowUser()[2]);
								System.out.println("ricavo valore nome: "+Mb.getRowUser()[3]);
								System.out.println("ricavo valore nome: "+Mb.getRowUser()[4]);
								
								//RITORNA AD APP MAIN
								
								setIdut(Integer.valueOf(Mb.getRowUser()[0]));
								
								this.getMeMain().getFrame().setVisible(true);
								this.getMeMain().getBtnAccount().setVisible(true);
								
								
								clrParFS();	
								break;
								
								// USER Read Data by email
							case "SRV :> selected user by email panel Account:> OK":								
								System.out.println("ritornato al client UserDATA by email OK : ");																							
								
//INSERISCI ACTW NUOVA ACCOUNT								
								
								Account accX = new Account(getMeMain().getFrame(),this);				
								setActW(accX);
								accX.setAlwaysOnTop(true);	
								accX.getPanelAccount().setVisible(true);
								accX.getPanelModify().setVisible(false);
								accX.updateallAfterModify(Mb.getRowUser());			// PER PANNELLO ACCOUNT 
		
								clrParFS();	
								break;
								
								// USER Read Data by email
							case "SRV :> selected user by email panel Modify:> OK":								
								System.out.println("ritornato al client UserDATA by email OK : ");																							
								Account modX = (Account) ActW;

								modX.getPanelAccount().setVisible(false);
								modX.getPanelModify().setVisible(true);
								
								modX.updateall(Mb.getRowUser());					// PER PANNELLO MODIFY								
								clrParFS();	
								break;								
								
								
								
							case "SRV :> selected user by email :> NG" 				:  
							case "SRV :> selected user by email panel Modify:> NG"	:
							case "SRV :> selected user by email panel Account:> NG" :{
							
								System.out.println("ritornato al client UserDATA  by email NG : ");
								
								clrParFS();	
								break;								
							}	
							
								
						// USER Read Data Login check
							case "SRV :> selected user login check:> I Campi Non Possono Essere Vuoti":								
								PopUp.errorBox(getActC(), Mb.getText());
								clrParFS();	
								break;	
								
							case "SRV :> selected user login check:> Nessun Dato":
								PopUp.errorBox(getActC(), Mb.getText());
								clrParFS();	
								break;	
								
							case "SRV :> selected user login check:> L'Email Non Esiste":
								PopUp.errorBox(getActC(), Mb.getText());
								clrParFS();	
								break;									
								
							case "SRV :> selected user login check:> Password Errata":
								PopUp.errorBox(getActC(), Mb.getText());
								clrParFS();	
								break;	
	
							case "SRV :> selected user login check:> Login Corretto":
								
								setIdut(Mb.getIdUser());
								setDatiUtente(Mb.getRowUser());
								
								System.out.println("settato id utente..."+getIdut());
								
								Login l = (Login)getActW();								
								//System.out.println("login attiva: "+l.toString());								
								/*
								Account lo = new Account(getActF(),this);				
								setActW(lo);
								*/								
								//chiusura finesta login
								l.getPfa().setVisible(false);				//PanelFirstAcc.setVisible(false);
								l.getPr().setVisible(false);				//PanelRegi.setVisible(false);	
								WindowEvent close = new WindowEvent(getActF(), WindowEvent.WINDOW_CLOSING);
								getActF().dispatchEvent(close);
								
								//invio comando login
								try {
									System.out.println("GUI login:> cmd tentativo di login ");
									
									
									//this.setCliType(Clients.Librarian);
									
									
									//System.out.println("CLI sendMESSAGE return : riottengo da MB email "+Mb.getUserEmail());
									
									/*
									this.setSql(Mb.getUserEmail());			//risetta email in campo sql
									this.getCmdLIST().put(Commands.UserREADbyEmail);
									*/
									
									
									this.getMeMain().getFrame().setVisible(true);
									this.getMeMain().getBtnAccount().setVisible(true);
									this.getMeMain().getText().setText(" benvenuto ["+getDatiUtente()[6]+"] "+getDatiUtente()[4]+" "+getDatiUtente()[5]+" ");
									
									
									
								} /*catch (InterruptedException e2) {
									System.out.println("GUI login :> problemi con tentativo di login ");	
									e2.printStackTrace(); 
								
								}*/
								finally {
									
								}
								//clrParFS();	i campi verranno ripuliti da user read by email	
								break;
							// LOGIN FINE	
							
							// USER Read Data Login check FIRST ACCESS
							case "SRV :> selected user login check FIRST:> I Campi Non Possono Essere Vuoti":								
								PopUp.errorBox(getActC(), Mb.getText());
								clrParFS();	
								break;	
								
							case "SRV :> selected user login check FIRST:> Nessun Dato":
								PopUp.errorBox(getActC(), Mb.getText());
								clrParFS();	
								break;	
								
							case "SRV :> selected user login check FIRST:> L'Email Non Esiste":
								PopUp.errorBox(getActC(), Mb.getText());
								clrParFS();	
								break;									
								
							case "SRV :> selected user login check FIRST:> Password Errata":
								PopUp.errorBox(getActC(), Mb.getText());
								clrParFS();	
								break;	
	
							case "SRV :> selected user login check FIRST:> Login Corretto":
								Login lF = (Login)getActW();					//System.out.println("login attiva: "+l.toString());								
								Account loF = new Account(getActF(),this);				
								setActW(loF);
								//chiusura finesta login
								lF.getPfa().setVisible(false);				//PanelFirstAcc.setVisible(false);
								lF.getPr().setVisible(false);				//PanelRegi.setVisible(false);
								WindowEvent closeF = new WindowEvent(getActF(), WindowEvent.WINDOW_CLOSING);
								getActF().dispatchEvent(closeF);
								
				
								
								//invio comando login
								try {
									System.out.println("GUI login:> cmd tentativo di login ");
									this.setCliType(Clients.Librarian);
									//System.out.println("CLI sendMESSAGE return : riottengo da MB email "+Mb.getUserEmail());
									this.setSql(Mb.getUserEmail());			//risetta email in campo sql
//TODO ELIMINA PASSWORD TEMPORANEA
//MQ_Delete.deletePassTemp(pass);
									this.getCmdLIST().put(Commands.UserREADbyEmail);

								} catch (InterruptedException e2) {
									System.out.println("GUI login :> problemi con tentativo di login ");	
									e2.printStackTrace(); 
								}
								//clrParFS();	i campi verranno ripuliti da user read by email	
								break;
							// LOGIN FIRST ACCESS FINE	
							
							case "SRV :> selected user login check FIRST:> PROCEDURA CANCELLAZIONE UTENTE":								
								PopUp.errorBox(getActC(), Mb.getText());
								clrParFS();	
								break;	
								
//USER \ READ \ CK Email	
							case"SRV :> URCE :> NG":					
								switch (Mb.getWtype()) {
									case "Account":
										Account eNGX = (Account) ActW;
										eNGX.getLblMAIL().setIcon(eNGX.getIconLogoC());
										eNGX.getTxtMailMod().setText("Errore interrogazione DB");
										eNGX.setMailcheckResult("NG");
										eNGX.setMailcheckinprogress(false);									
										break;
									case "AppReader":
										AppReader erNGX = (AppReader) ActW;
										erNGX.getLblCheckMail().setIcon(erNGX.getIconLogoC());
										erNGX.getTxtEmail().setText("Errore interrogazione DB");
										erNGX.setMailcheckResult("NG");
										erNGX.setMailcheckinprogress(false);										
										break;
									case "LoginPWRecovery":
										Login lX = (Login) ActW;
										lX.getTxtEmail().setText("Errore interrogazione DB");
										lX.setMailcheckResult("NG");
										lX.setMailcheckinprogress(false);										
										break;
									
									default:
										break;
								}
								//PopUp.errorBox(getActC(), Mb.getText());
								//clrParFS();	
								break;
								
							case"SRV :> URCE :> OK Exist":										
								switch (Mb.getWtype()) {								
									case "Account":
										Account eX = (Account) ActW;
										eX.getLblMAIL().setIcon(eX.getIconLogoC());
										eX.getTxtMailMod().setText(getSql()+ " : email gia assegnata...");
										//PopUp.errorBox(getActC(), Mb.getText());
										eX.setMailcheckResult("OK E");
										eX.setMailcheckinprogress(false);
										//clrParFS();	
										break;
									case "AppReader":
										AppReader erNGX = (AppReader) ActW;								
										erNGX.getLblCheckMail().setIcon(erNGX.getIconLogoC());
										erNGX.getTxtEmail().setText(getSql()+ " : email gia assegnata...");
										erNGX.setMailcheckResult("OK E");
										erNGX.setMailcheckinprogress(false);										
										break;
									case "LoginPWRecovery":
										Login lX = (Login) ActW;
//TODO INSERIRE PROCEDURA											
										//PopUp.infoBox(lX, "INSERIRE PROCEDURA INVIO EMAIL CON PW TEMP");
										//PanelRegi.setVisible(false);
										//PanelFirstAcc.setVisible(false);
										
										lX.setMailcheckResult("OK E");
										lX.setMailcheckinprogress(false);		
										break;										
									default:
										break;
								}
								break;
	
							case"SRV :> URCE :> OK Not Exist":								
								switch (Mb.getWtype()) {								
									case "Account":
										Account neX = (Account) ActW;
										neX.getLblMAIL().setIcon(neX.getIconLogoT());
										//PopUp.infoBox(getActC(),Mb.getText() );
										System.out.println(" ***** sto controllando la email : email LIBERA , OK ");
										neX.setMailcheckResult("OK NE");
										neX.setMailcheckinprogress(false);
										//clrParFS();	
										break;
									case "AppReader":
										AppReader erNGX = (AppReader) ActW;
										erNGX.getLblCheckMail().setIcon(erNGX.getIconLogoT());
										System.out.println(" ***** sto controllando la email : email LIBERA , OK ");
										//erNGX.getTxtEmail().setText("");
										erNGX.setMailcheckResult("OK NE");
										erNGX.setMailcheckinprogress(false);										
										break;
									case "LoginPWRecovery":
										Login lX = (Login) ActW;
										lX.setMailcheckResult("OK NE");
										lX.setMailcheckinprogress(false);	
										break;
									default:
										break;
								}								
								break;
//USER \ READ \ CK cf									
							case"SRV :> URCCF :> NG":			
								AppReader cNGX = (AppReader) ActW;
								cNGX.getLblCheckCF().setIcon(cNGX.getIconLogoC());
								cNGX.getTxtCF().setText("Errore interrogazione DB");
								cNGX.setCfcheckResult("NG");
								cNGX.setCfcheckinprogress(false);
								//PopUp.errorBox(getActC(), Mb.getText());
								//clrParFS();	
									break;
							case"SRV :> URCCF :> OK Exist":								
								AppReader cX = (AppReader) ActW;
								cX.getLblCheckCF().setIcon(cX.getIconLogoC());
								cX.getTxtCF().setText("cf gia assegnato...");
								//PopUp.errorBox(getActC(), Mb.getText());
								cX.setCfcheckResult("OK E");
								cX.setCfcheckinprogress(false);
								//clrParFS();	
									break;
							case"SRV :> URCCF :> OK Not Exist":
								AppReader ncX = (AppReader) ActW;
								ncX.getLblCheckCF().setIcon(ncX.getIconLogoT());
								//PopUp.infoBox(getActC(),Mb.getText() );
								System.out.println(" ***** sto controllando codice fiscale : cf LIBERA , OK ");
								ncX.setCfcheckResult("OK NE");
								ncX.setCfcheckinprogress(false);
								//clrParFS();	
									break;													
//USER \ UPDATE
							case "SRV :> UP :> OK":
								Account upX = (Account) ActW;
								try {
									this.setSql(Mb.getUserEmail());			//risetta email in campo sql
									this.setIdut(Mb.getIdUser());									
									System.out.println("user ricevuto dal server "+this.getIdut());
									this.getCmdLIST().put(Commands.UserREADbyEmailAcc);
								} catch (InterruptedException e2) {
									System.out.println("GUI login :> problemi con tentativo di login ");	
									e2.printStackTrace(); 
								}
								PopUp.infoBox(getActC(),Mb.getText() );	
								System.out.println("SRV :> USER UPDATE :> OK ");								
								//clrParFS();	
								break;						
							case "SRV :> UP :> NG":	
								PopUp.errorBox(getActC(),Mb.getText() );	
								System.out.println("SRV :> USER UPDATE :> NG");
								clrParFS();	
								break;							
//USER \ DELETE	
							case "SRV :> USER del :> OK":	
								
								WindowEvent closeD = new WindowEvent(ActF, WindowEvent.WINDOW_CLOSING);
								ActF.dispatchEvent(closeD);
								
								PopUp.infoBox(ActW,"Dati cancellati con successo");	
								break;	
							case "SRV :> USER del :> NG":	
								
								PopUp.errorBox(getActC(),Mb.getText() );
								break;									
							
							
 					
//LOANS \ READ
							case "SRV :> table Loans populate :> OK":	System.out.println("ritornato al client POPULATE OK : ");									
							
								setActTable(Mb.getTab());
//TODO ADATTA A LOANS									
								TableBooks.getTable().setModel(Mb.getTab().getModel());

								this.setActF(null);
								this.setSql(null);
								setBusy(false);
								break;
							
							case "SRV :> table Loans populate :> NG":	System.out.println("ritornato al client POPULATE NG : ");
								clrParFS();
								break;	
								
								
//BOOK
							case	"SRV :> BookLastId :> OK":
								ResearchBooks resB = (ResearchBooks) ActW;						
								resB.setLastIDbookcheckResult(Mb.getIdBook());
								resB.setLastIDbookcheckinprogress(false);
								break;
							
							case	"SRV :> BookLastId :> NG":
								ResearchBooks resBk = (ResearchBooks) ActW;						
								resBk.setLastIDbookcheckResult(Mb.getIdBook());
								resBk.setLastIDbookcheckinprogress(false);
								break;								

							default:							
								System.out.println("CLI :> ritornato da STUB messaggio : "+Mb.getText());
								
								break;
						}
						// Reazioni di Client ai messaggi ritornati dal Server
						
						setBusy(false);
						System.out.println("CLI -> Ritornato mb :"+Mb.getText());		
			} 
			catch (IOException io){	
						io.printStackTrace();
						Mb.setText("CLI:>  collegamento mancante");	
						setBusy(false);
			} 
			catch (ClassNotFoundException e) {
						e.printStackTrace();
						Mb.setText("CLI:>  class not found");
						setBusy(false);
			}
	}
	
	
	// check
	private void ClientCheckExistTableSetting() throws SendFailedException, MessagingException, SQLException, InterruptedException{
		Commands cmd = Commands.tableExistSetting;
		MessageBack Mb = new MessageBack();
		
		System.out.println("CLI :> Request ricevuto da GUI :> "+cmd.toString());
		if (!stubok){
			Mb.setText(mSg = "CLI :>  nessuna connessione attiva , riprovare ");
			
			System.out.println(mSg);
			
			getActW().addMsg(new String ("Connection Test result"+mSg));
		}else{	
			System.out.println("CLI :> Stub OK");
			// **** Client crea Message	
			Message MsgSend = new Message(	
					cmd,						// Comando richiesto
					this.getCliType() ,			// tipo di Client , Admin,Librarian,Reader
					this.toString()				// id Client 
									);
			MsgSend.setUType(Clients.Librarian);
			// **** Client invia Message
			sendM(MsgSend, Mb);
		}	
	}	
	private void ClientCheckExistTableBooking() throws SendFailedException, MessagingException, SQLException, InterruptedException{
		Commands cmd = Commands.tableExistBooking;
		MessageBack Mb = new MessageBack();
		
		System.out.println("CLI :> Request ricevuto da GUI :> "+cmd.toString());
		if (!stubok){
			Mb.setText(mSg = "CLI :>  nessuna connessione attiva , riprovare ");
			
			System.out.println(mSg);
			
			getActW().addMsg(new String ("Connection Test result"+mSg));
		}else{	
			System.out.println("CLI :> Stub OK");
			// **** Client crea Message	
			Message MsgSend = new Message(	
					cmd,						// Comando richiesto
					this.getCliType() ,			// tipo di Client , Admin,Librarian,Reader
					this.toString()				// id Client 
									);
			MsgSend.setUType(Clients.Librarian);
			// **** Client invia Message
			sendM(MsgSend, Mb);
		}	
	}	
	
	
	
	
	private void ClientCheckExistTableBook() throws SendFailedException, MessagingException, SQLException, InterruptedException{
		Commands cmd = Commands.tableExistBook;
		MessageBack Mb = new MessageBack();
		
		System.out.println("CLI :> Request ricevuto da GUI :> "+cmd.toString());
		if (!stubok){
			Mb.setText(mSg = "CLI :>  nessuna connessione attiva , riprovare ");
			
			System.out.println(mSg);
			
			getActW().addMsg(new String ("Connection Test result"+mSg));
		}else{	
			System.out.println("CLI :> Stub OK");
			// **** Client crea Message	
			Message MsgSend = new Message(	
					cmd,						// Comando richiesto
					this.getCliType() ,			// tipo di Client , Admin,Librarian,Reader
					this.toString()				// id Client 
									);
			MsgSend.setUType(Clients.Librarian);
			// **** Client invia Message
			sendM(MsgSend, Mb);
		}	
	}			
	
	private void ClientCheckExistTablePerson() throws SendFailedException, MessagingException, SQLException, InterruptedException{
		Commands cmd = Commands.tableExistPerson;
		MessageBack Mb = new MessageBack();
		
		System.out.println("CLI :> Request ricevuto da GUI :> "+cmd.toString());
		if (!stubok){
			Mb.setText(mSg = "CLI :>  nessuna connessione attiva , riprovare ");
			
			System.out.println(mSg);
			
			getActW().addMsg(new String ("Connection Test result"+mSg));
		}else{	
			System.out.println("CLI :> Stub OK");
			// **** Client crea Message	
			Message MsgSend = new Message(	
					cmd,						// Comando richiesto
					this.getCliType() ,			// tipo di Client , Admin,Librarian,Reader
					this.toString()				// id Client 
									);
			MsgSend.setUType(Clients.Librarian);
			// **** Client invia Message
			sendM(MsgSend, Mb);
		}	
	}	
	
	private void ClientCheckExistTableLoans() throws SendFailedException, MessagingException, SQLException, InterruptedException{
		Commands cmd = Commands.tableExistLoans;
		MessageBack Mb = new MessageBack();
		
		System.out.println("CLI :> Request ricevuto da GUI :> "+cmd.toString());
		
		if (!stubok){
			Mb.setText(mSg = "CLI :>  nessuna connessione attiva , riprovare ");			
			System.out.println(mSg);			
			getActW().addMsg(new String ("Connection Test result"+mSg));
		}else{	
			System.out.println("CLI :> Stub OK");
			// **** Client crea Message	
			Message MsgSend = new Message(	
					cmd,						// Comando richiesto
					this.getCliType() ,			// tipo di Client , Admin,Librarian,Reader
					this.toString()				// id Client 
									);
			MsgSend.setUType(Clients.Librarian);
			// **** Client invia Message
			
			//???????
			sendM(MsgSend, Mb);
		}	
	}	
	
	private void ClientCHANGEuserRegistration() throws SendFailedException, MessagingException, SQLException, InterruptedException{
		Commands cmd = Commands.UserRegistration;
		MessageBack Mb = new MessageBack();
		
		System.out.println("CLI :> Request ricevuto da GUI :> "+cmd.toString());
		if (!stubok){
			Mb.setText(mSg = "CLI :>  nessuna connessione attiva , riprovare ");			
			System.out.println(mSg);			
			getActW().addMsg(new String ("Connection Test result"+mSg));
		}else{	
			System.out.println("CLI :> Stub OK");
			// **** Client crea Message						
			Message MsgSend = new Message(	
					cmd,						// Comando richiesto
					this.getCliType() ,			// tipo di Client , Admin,Librarian,Reader
					this.toString(),			// id Client 
					this.getSql()
					);
			MsgSend.setUType(Clients.Librarian);
			// **** Client invia Message
			sendM(MsgSend, Mb);
			
			
		}	
	}
	
//TODO ADATTA	
private void BookLast () throws SendFailedException, MessagingException, SQLException, InterruptedException{
		
		Commands cmd = Commands.BookLast;
		MessageBack Mb = new MessageBack();
		
		System.out.println("CLI :> Request ricevuto da GUI :> "+cmd.toString());
		if (!stubok){
			Mb.setText(mSg = "CLI :>  nessuna connessione attiva , riprovare ");			
			System.out.println(mSg);			
			getActW().addMsg(new String ("Connection Test result"+mSg));
		}else{	
			System.out.println("CLI :> Stub OK");					
			Message MsgSend = new Message(	
					cmd,						// Comando richiesto
					this.getCliType() ,			// tipo di Client , Admin,Librarian,Reader
					this.toString()				// id Client 
					);
			sendM(MsgSend, Mb);	
		}		
	}
	
	private void BookPopulate () throws SendFailedException, MessagingException, SQLException, InterruptedException{
		
		Commands cmd = Commands.BookExecuteQuery;
		
		MessageBack Mb = new MessageBack();
		
		System.out.println("CLI :> Request ricevuto da GUI :> "+cmd.toString());
		if (!stubok){
			Mb.setText(mSg = "CLI :>  nessuna connessione attiva , riprovare ");			
			System.out.println(mSg);			
			getActW().addMsg(new String ("Connection Test result"+mSg));
		}else{	
			System.out.println("CLI :> Stub OK");
			// **** Client crea Message						
			Message MsgSend = new Message(	
					cmd,						// Comando richiesto
					this.getCliType() ,			// tipo di Client , Admin,Librarian,Reader
					this.toString(),			// id Client 
					this.getSql()
					);
			//MsgSend.setUType(Clients.Librarian);
			// **** Client invia Message
			
			sendM(MsgSend, Mb);	
		}		
	}

	
//TODO SISTEMA	
	private void ClientBookAdd() throws SendFailedException, MessagingException, SQLException, InterruptedException{
		Commands cmd = Commands.BookADD;
		MessageBack Mb = new MessageBack();
		
		System.out.println("CLI :> Request ricevuto da GUI :> "+cmd.toString());
		if (!stubok){
			Mb.setText(mSg = "CLI :>  nessuna connessione attiva , riprovare ");			
			System.out.println(mSg);			
			getActW().addMsg(new String ("Connection Test result"+mSg));
		}else{	
			System.out.println("CLI :> Stub OK");
			// **** Client crea Message						
			Message MsgSend = new Message(	
					cmd,						// Comando richiesto
					this.getCliType() ,			// tipo di Client , Admin,Librarian,Reader
					this.toString(),			// id Client 
					this.getSql()
					);
			
			// MsgSend.setUType(Clients.Librarian);
			// **** Client invia Message
			
			sendM(MsgSend, Mb);
			
			
		}	
	}
		private void ClientBookDelete() throws SendFailedException, MessagingException, SQLException, InterruptedException{
			Commands cmd = Commands.BookDELETE;
			MessageBack Mb = new MessageBack();
			
			System.out.println("CLI :> Request ricevuto da GUI :> "+cmd.toString());
			if (!stubok){
				Mb.setText(mSg = "CLI :>  nessuna connessione attiva , riprovare ");			
				System.out.println(mSg);			
				getActW().addMsg(new String ("Connection Test result"+mSg));
			}else{	
				System.out.println("CLI :> Stub OK");
				// **** Client crea Message							
				Message MsgSend = new Message(	
						cmd,						// Comando richiesto
						this.getCliType() ,			// tipo di Client , Admin,Librarian,Reader
						this.toString(),			// id Client 
						this.getSql()
						);
				MsgSend.setUType(Clients.Librarian);
				// **** Client invia Message
				sendM(MsgSend, Mb);
			}	
		}	

		private void UserREADcheckCfExist() throws SendFailedException, MessagingException, SQLException, InterruptedException{
			Commands cmd = Commands.UserREADcheckCF;
			MessageBack Mb = new MessageBack();
			
			System.out.println("CLI :> Request ricevuto da GUI :> "+cmd.toString());
			if (!stubok){
				Mb.setText(mSg = "CLI :>  nessuna connessione attiva , riprovare ");			
				System.out.println(mSg);			
				getActW().addMsg(new String ("Connection Test result"+mSg));
			}else{	
				System.out.println("CLI :> Stub OK");
				// **** Client crea Message							
				Message MsgSend = new Message(	
						cmd,						// Comando richiesto
						this.getCliType() ,			// tipo di Client , Admin,Librarian,Reader
						this.toString(),			// id Client 
						this.getSql()				// cf da verificare
						
						);
				sendM(MsgSend, Mb);
			}	
		}
		
		
		private void UserREADcheckEmailExist() throws SendFailedException, MessagingException, SQLException, InterruptedException{
			Commands cmd = Commands.UserREADcheckEmail;
			MessageBack Mb = new MessageBack();
			
			System.out.println("CLI :> Request ricevuto da GUI :> "+cmd.toString());
			if (!stubok){
				Mb.setText(mSg = "CLI :>  nessuna connessione attiva , riprovare ");			
				System.out.println(mSg);			
				getActW().addMsg(new String ("Connection Test result"+mSg));
			}else{	
				System.out.println("CLI :> Stub OK");
				// **** Client crea Message							
				Message MsgSend = new Message(	
						cmd,						// Comando richiesto
						this.getCliType() ,			// tipo di Client , Admin,Librarian,Reader
						this.toString(),			// id Client 
						this.getSql(),				// email da verificare
						this.getSql2()				// finestra chiamante {Account,AppReader,LoginPWRecovery}
						);
				sendM(MsgSend, Mb);
			}	
		}		
		private void UserGetData() throws SendFailedException, MessagingException, SQLException, InterruptedException{
			Commands cmd = Commands.UserREAD;
			MessageBack Mb = new MessageBack();
			
			System.out.println("CLI :> Request ricevuto da GUI :> "+cmd.toString());
			if (!stubok){
				Mb.setText(mSg = "CLI :>  nessuna connessione attiva , riprovare ");			
				System.out.println(mSg);			
				getActW().addMsg(new String ("Connection Test result"+mSg));
			}else{	
				System.out.println("CLI :> Stub OK");
				// **** Client crea Message							
				Message MsgSend = new Message(	
						cmd,						// Comando richiesto
						this.getCliType() ,			// tipo di Client , Admin,Librarian,Reader
						this.toString(),			// id Client 
						this.getSql()				
						
						);
				sendM(MsgSend, Mb);
			}	
		}
		
		//BookLast();
		
		private void UserGetDatabyEmail() throws SendFailedException, MessagingException, SQLException, InterruptedException{
			Commands cmd = Commands.UserREADbyEmail;
			MessageBack Mb = new MessageBack();
			
			System.out.println("CLI :> Request ricevuto da GUI :> "+cmd.toString()+" by email: "+this.getSql());
			if (!stubok){
				Mb.setText(mSg = "CLI :>  nessuna connessione attiva , riprovare ");			
				System.out.println(mSg);			
				getActW().addMsg(new String ("Connection Test result"+mSg));
			}else{	
				System.out.println("CLI :> Stub OK");
				// **** Client crea Message							
				Message MsgSend = new Message(	
						cmd,						// Comando richiesto
						this.getCliType() ,			// tipo di Client , Admin,Librarian,Reader
						this.toString(),			// id Client 
						this.getSql()				// la finestra login passa la stringa email !!!!
						
						);
				sendM(MsgSend, Mb);
			}	
		}
		private void UserGetDatabyEmailAcc() throws SendFailedException, MessagingException, SQLException, InterruptedException{
			Commands cmd = Commands.UserREADbyEmailAcc;
			MessageBack Mb = new MessageBack();
			
			System.out.println("CLI :> Request ricevuto da GUI :> "+cmd.toString()+" by id: "+this.getIdut());
			if (!stubok){
				Mb.setText(mSg = "CLI :>  nessuna connessione attiva , riprovare ");			
				System.out.println(mSg);			
				getActW().addMsg(new String ("Connection Test result"+mSg));
			}else{	
				System.out.println("CLI :> Stub OK");
				// **** Client crea Message							
				Message MsgSend = new Message(	
						cmd,						// Comando richiesto
						this.getCliType() ,			// tipo di Client , Admin,Librarian,Reader
						this.toString(),			// id Client 
						null,
						null,
						0,
						this.getIdut()				// la finestra login passa ID UTENTE !!!!
						);
				sendM(MsgSend, Mb);
			}	
		}
		private void UserGetDatabyEmailMod() throws SendFailedException, MessagingException, SQLException, InterruptedException{
			Commands cmd = Commands.UserREADbyEmailMod;
			MessageBack Mb = new MessageBack();
			
			System.out.println("CLI :> Request ricevuto da GUI :> "+cmd.toString()+" by email: "+this.getIdut());
			if (!stubok){
				Mb.setText(mSg = "CLI :>  nessuna connessione attiva , riprovare ");			
				System.out.println(mSg);			
				getActW().addMsg(new String ("Connection Test result"+mSg));
			}else{	
				System.out.println("CLI :> Stub OK");
				// **** Client crea Message							
				Message MsgSend = new Message(	
						cmd,						// Comando richiesto
						this.getCliType() ,			// tipo di Client , Admin,Librarian,Reader
						this.toString(),			// id Client 
						null,
						null,
						0,
						this.getIdut()				// la finestra login passa ID UTENTE !!!!
						);
				sendM(MsgSend, Mb);
			}	
		}		
		
		
		private void UserGetDataLogin() throws SendFailedException, MessagingException, SQLException, InterruptedException{
			Commands cmd = Commands.UserREADlogin;
			MessageBack Mb = new MessageBack();
			
			System.out.println("CLI :> Request ricevuto da GUI :> "+cmd.toString());
			if (!stubok){
				Mb.setText(mSg = "CLI :>  nessuna connessione attiva , riprovare ");			
				System.out.println(mSg);			
				getActW().addMsg(new String ("Connection Test result"+mSg));
			}else{	
				System.out.println("CLI :> Stub OK");
				// **** Client crea Message						
				
				System.out.println("client prende parametro sql "+this.getSql());
				
				Message MsgSend = new Message(	
						cmd,						// Comando richiesto
						this.getCliType() ,			// tipo di Client , Admin,Librarian,Reader
						this.toString(),			// id Client 
						this.getSql(),				// la finestra login passa la stringa email 
						this.getSql2()				// la finestra login passa la stringa password
						
						);
				sendM(MsgSend, Mb);
			}	
		}
		private void UserGetDataLoginFIRST() throws SendFailedException, MessagingException, SQLException, InterruptedException{
			Commands cmd = Commands.UserREADloginFIRST;
			MessageBack Mb = new MessageBack();
			
			System.out.println("CLI :> Request ricevuto da GUI :> "+cmd.toString());
			if (!stubok){
				Mb.setText(mSg = "CLI :>  nessuna connessione attiva , riprovare ");			
				System.out.println(mSg);			
				getActW().addMsg(new String ("Connection Test result"+mSg));
			}else{	
				System.out.println("CLI :> Stub OK");
				// **** Client crea Message							
				Message MsgSend = new Message(	
						cmd,						// Comando richiesto
						this.getCliType() ,			// tipo di Client , Admin,Librarian,Reader
						this.toString(),			// id Client 
						this.getSql(),				// la finestra login passa la stringa email 
						this.getSql2()				// la finestra login passa la stringa password 
						
						);
				sendM(MsgSend, Mb);
			}	
		}		
				
		private void UserGetDataAccountMod() throws SendFailedException, MessagingException, SQLException, InterruptedException{
			Commands cmd = Commands.UserREADaccountMod;
			MessageBack Mb = new MessageBack();
			
			System.out.println("CLI :> Request ricevuto da GUI :> "+cmd.toString());
			if (!stubok){
				Mb.setText(mSg = "CLI :>  nessuna connessione attiva , riprovare ");			
				System.out.println(mSg);			
				getActW().addMsg(new String ("Connection Test result"+mSg));
			}else{	
				System.out.println("CLI :> Stub OK");
				// **** Client crea Message							
				Message MsgSend = new Message(	
						cmd,						// Comando richiesto
						this.getCliType() ,			// tipo di Client , Admin,Librarian,Reader
						this.toString(),			// id Client 
						this.getSql()				// la finestra login passa la stringa email 
						);
				sendM(MsgSend, Mb);
			}	
		}	
	
		private void UserUPDATE() throws SendFailedException, MessagingException, SQLException, InterruptedException{
			Commands cmd = Commands.UserUPDATE;
			MessageBack Mb = new MessageBack();
		
			System.out.println("CLI :> Request ricevuto da GUI :> "+cmd.toString());
			System.out.println("CLI :> Prendo Query sql2:> "+this.getSql2().toString());
			System.out.println("CLI :> Prendo Query sql :> "+this.getSql().toString());
			System.out.println("CLI :> Prendo id utente :> "+this.getIdut());
			
			if (!stubok){
				Mb.setText(mSg = "CLI :>  nessuna connessione attiva , riprovare ");			
				System.out.println(mSg);			
				getActW().addMsg(new String ("Connection Test result"+mSg));
			}else{	
				System.out.println("CLI :> Stub OK");
				// **** Client crea Message							
				Message MsgSend = new Message(	
						cmd,						// Comando richiesto
						this.getCliType() ,			// tipo di Client , Admin,Librarian,Reader
						this.toString(),			// id Client 
						this.getSql(),				// Account --> MQ_Update.updateModUserIdGetQuery
						this.getSql2(),				// Account --> email
						0,
						this.getIdut()				// Account --> idutente
						);
				sendM(MsgSend, Mb);
			}	
		}	
		
		private void UserDELETE() throws SendFailedException, MessagingException, SQLException, InterruptedException{
			Commands cmd = Commands.UserDELETE;
			MessageBack Mb = new MessageBack();
		
			System.out.println("CLI :> Request ricevuto da GUI :> "+cmd.toString());
			System.out.println("CLI :> Prendo id utente :> "+this.getIdut());
			
			if (!stubok){
				Mb.setText(mSg = "CLI :>  nessuna connessione attiva , riprovare ");			
				System.out.println(mSg);			
				getActW().addMsg(new String ("Connection Test result"+mSg));
			}else{	
				System.out.println("CLI :> Stub OK");
				// **** Client crea Message							
				Message MsgSend = new Message(	
						cmd,						// Comando richiesto
						this.getCliType() ,			// tipo di Client , Admin,Librarian,Reader
						this.toString(),			// id Client 
						this.getSql(),				// Account --> MQ_Update.updateModUserIdGetQuery
						this.getSql2(),				// Account --> email
						0,
						this.getIdut()				// Account --> idutente
						);
				sendM(MsgSend, Mb);
			}	
		}	

		
//TODO DA TESTARE		
		private void LoanGetData() throws SendFailedException, MessagingException, SQLException, InterruptedException{
			Commands cmd = Commands.LoanREAD;
			MessageBack Mb = new MessageBack();
			
			System.out.println("CLI :> Request ricevuto da GUI :> "+cmd.toString());
			if (!stubok){
				Mb.setText(mSg = "CLI :>  nessuna connessione attiva , riprovare ");			
				System.out.println(mSg);			
				getActW().addMsg(new String ("Connection Test result"+mSg));
			}else{	
				System.out.println("CLI :> Stub OK");
				// **** Client crea Message							
				Message MsgSend = new Message(	
						cmd,						// Comando richiesto
						this.getCliType() ,			// tipo di Client , Admin,Librarian,Reader
						this.toString(),			// id Client 
						this.getSql()				// query				
						
						);
				sendM(MsgSend, Mb);
			}	
		}
		
		
		
	//------------------------------------------------------------------------		 
	// get & set
		
			public Clients getCliType() {
				return ClientType;
			}
			public void setCliType(Clients cliType) {
				ClientType = cliType;
			}		
			public boolean isStubok() {
			return stubok;
		}
			public void setStubok(boolean stubok) {
			this.stubok = stubok;
		}
			public boolean isRepeatconn() {
			return repeatconn;
		}
			public void setRepeatconn(boolean repeatconn) {
			this.repeatconn = repeatconn;
		}
			public int getRepeatconnCount() {
			return repeatconnCount;
		}
			public void setRepeatconnCount(int repeatconnCount) {
			this.repeatconnCount = repeatconnCount;
		}
			public void incRepeatconnCount(){
			this.repeatconnCount++;
		}
			public ServerStub getSrv() {
			return srv;
		}
			public void setSrv(ServerStub srv) {
			this.srv = srv;
		}	
			public String getSql() {
				return Sql;
		}
			public void setSql(String sql) {
				Sql = sql;
		}
			public Component getActC() {
				return ActC;
		}
			public void setActC(Component actC) {
				ActC = actC;
		}
			public JFrame getActF() {
				return ActF;
			}
			public void setActF(JFrame actF) {
				ActF = actF;
			}
			public JTable getActTable() {
				return ActTable;
			}
			public void setActTable(JTable actTable) {
				ActTable = actTable;
			}		
			public String getTo() {
				return to;
			}
			public void setTo(String to) {
				this.to = to;
			}
			public String getPASSWORD() {
				return PASSWORD;
			}
			public void setPASSWORD(String pASSWORD) {
				PASSWORD = pASSWORD;
			}
			public String getUSERNAME() {
				return USERNAME;
			}
			public void setUSERNAME(String uSER) {
				USERNAME = uSER;
			}
			public SL_JFrame getActW() {
				return ActW;
			}
			public void setActW(SL_JFrame actW) {
				ActW = actW;
			}
			public boolean isBusy() {
				return Busy;
			}
			public void setBusy(boolean busy) {
				this.Busy = busy;
			}
			public boolean isBusyControl() {
				return BusyControl;
			}
			public void setBusyControl(boolean busyControl) {
				BusyControl = busyControl;
			}
			public CommandsList getCmdLIST() {
				return CmdLIST;
			}
			public void setCmdLIST(CommandsList cmdLIST) {
				CmdLIST = cmdLIST;
			}
			public boolean isClientON() {
				return ClientON;
			}
			public void setClientON(boolean clientON) {
				ClientON = clientON;
			}
			public String getSql2() {
				return Sql2;
			}
			public void setSql2(String sql2) {
				Sql2 = sql2;
			}
		/*	
			public SystemClientStub getMeG_Sys() {
				return meG_Sys;
			}
			public void setMeG_Sys(SystemClientStub meG_Sys) {
				this.meG_Sys = meG_Sys;
			}
			public Main getMeG_Main() {
				return meG_Main;
			}
			public void setMeG_Main(Main meG_Main) {
				this.meG_Main = meG_Main;
			}
			public AppLibrarian getMeG_Lib() {
				return meG_Lib;
			}
			public void setMeG_Lib(AppLibrarian meG_Lib) {
				this.meG_Lib = meG_Lib;
			}
			public AppReader getMeG_Rd() {
				return meG_Rd;
			}
			public void setMeG_Rd(AppReader meG_Rd) {
				this.meG_Rd = meG_Rd;
			}
		*/	
			
			
			//*************************************
			public void clrParFS () {
				this.setActF(null);	//F
				this.setSql(null);	//S	usato per email
				this.setSql2(null);	//S usato per password
				
				setBusy(false);
			}
			public int getLoginTry() {
				return LoginTry;
			}
			public void setLoginTry(int loginTry) {
				LoginTry = loginTry;
			}
			public void incLoginTry() {
				LoginTry++;
			}
			public int getIdut() {
				return idut;
			}
			public void setIdut(int idut) {
				this.idut = idut;
			}
			public String getPw() {
				return pw;
			}
			public void setPw(String pw) {
				this.pw = pw;
			}
			public AppMain getMeMain() {
				return meMain;
			}
			public void setMeMain(AppMain meMain) {
				this.meMain = meMain;
			}
			public String[] getDatiUtente() {
				return datiUtente;
			}
			public void setDatiUtente(String[] datiUtente) {
				this.datiUtente = datiUtente;
			}
			public String getSRVaddress() {
				return SRVaddress;
			}
			public void setSRVaddress(String sRVaddress) {
				SRVaddress = sRVaddress;
			}
			public String getSRVtype() {
				return SRVtype;
			}
			public void setSRVtype(String sRVtype) {
				SRVtype = sRVtype;
			}

			
	
	
		
		
		
	
	
}
