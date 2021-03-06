package connections;

//aggiorna drivers


import java.awt.EventQueue;


import javax.mail.MessagingException;
import javax.mail.SendFailedException;


import javax.swing.JFrame;


import javax.swing.JPanel;
import javax.swing.JTable;


import Check.PopUp;
import Core.ClientCMDloans;
import Core.ClientCMDuser;
import Core.ClientCMDBooking;
import Core.ClientCMDAllTables;
import Core.ClientCMDBook;

import Core.Clients;
import Core.Commands;
import Core.CommandsList;

import ProvaEmail.EmailSender;

import Table.TableBooks;

import Table.TableModelBooking;
import Table.TableModelBooks;
import Table.TableModelLoans;

import java.awt.event.WindowEvent;

import java.io.IOException;
import java.io.Serializable;

import java.sql.Date;

import java.sql.SQLException;


import gui.Account;

import gui.AppReader;
import gui.AppType;
import gui.Login;
import gui.ResearchBooks;
import gui.AppMain;
import gui.SL_JFrame;


import java.awt.Component;

/**
 * @author Mauro De Salvatore
 *
 */
public class Client implements Serializable, Runnable  {
	

	private static final long serialVersionUID = 1L;
	public 				int 				ctc=0;
	private				int 				LoginTry=0;
	private				int 				CurrentUser=0;	// if ( CurrentUser==0 ) non loggato 
	
	private 			AppMain 			StartWindow;
	private 			AppMain				meMain=null;
	private 			Account				meAcc=null;
	private				ResearchBooks		meRes=null;
	private 			JPanel				mePannelBook=null;
	private 			JPanel				mePannelBooking=null;
	private 			JPanel				mePannelLoans=null;
	

	private 			JFrame 				ActF			=null;	//Active Frame
	private 			SL_JFrame			ActW			=null;	//Active Window
	private 			Component			ActC			=null;	//Active Component	
	private 			JTable 				ActTable		=null;

	private 			Clients				ClientType;
	private				Clients				ClientTypeOLD;	
		
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
	
	private				boolean				Busy=false;			
	private				boolean				BusyControl=false;	
	private 			boolean				RefreshData=false;

	private 			String				SRVtype;
	private 			String				SRVaddress;	
	
	private 			String				ipaddLocal;
	private 			String				ipadLan;
	private 			String				ipadWww;
	
	private 			String				PASSWORD;	
	private 			String				USERNAME;	
	//SETTING *************************************************
	private 			String  			Fbook;
	private 			String  			Fbooking;
	private 			String  			Floans;
	
	private 			String 				DataLoanReturn;
	private 			String 				DataLoanRetired;
	
	private 			String 				Sql;
	private 			String 				Sql2;
	private				String				pw;
	private				String[] 			datiUtente;
	private 			TableModelBooks		tMbook;
	private 			TableModelBooking	tMbooking;
	private 			TableModelLoans		tMloans;

	private				int					selectedIdBook;
	private				int					selectedIdUser;
	private				Date				selectedIdDataStart;
	private				Date				selectedIdDataStop;	
	
	private 			Object[][]			databook;
	private 			Object[][]			databooking;
	private 			Object[][]			dataloans;

	private 			int 				idbook;	
	private 			int 				idut;
	private 			String				to;					//email destinatario per registrazione, PU� ESSERE IL PROBLEMA
	private 			boolean				storico=false;
	
	
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
					
					x.setStartWindow(new AppMain(x));
					x.setMeMain(x.getStartWindow());
					x.getStartWindow().getFrame().setVisible(true);	//System.out.println("creato start windows");
					
					ClientCMDuser.ClientGetDataFromSetting(x,x.getStartWindow());					
					ClientConnectionController y1 = new ClientConnectionController(x,10000);//1 controllo connessione al server OGNI 10 sec										
					
					x.getStartWindow().addMsg("Initialize...");	
					//new Thread(srvx).start();	//Server
					
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
										
					while ( getCmdLIST().getCmdList().size() < 1){	//Attesa comandi...	//System.out.println("CLI :> cmdLIST.size : "+getCmdLIST().getCmdList().size());							
						try {		//System.out.println("CLI :> IN ATTESA di COMANDI... dormo100");
							Thread.sleep(10);
						} catch (Exception e) { e.printStackTrace();
						}
					}
							System.out.println("CLI :> cmdLIST.size : "+getCmdLIST().getCmdList().size());							
							System.out.println("CLI :> Comandi in LISTA ");					
					//Comando Arrivato					
					//Comando Prelevo dalla lista CmdLIST
					Commands com = getCmdLIST().take();
							System.out.println("CLI :> Trovata richiesta in coda "+ com.Den );																		
// !!!! --------------------------
					setBusy(true);
// !!!! --------------------------					
					System.out.println("CLI :> Trovata richiesta in coda "+ com.Den + " [CLI !BUSY] < ORA ESEGUO > ");						
					switch (com.getCommandType()) {				//   		Core\CommandsType	***   									
				//	****************************************************	Connections	   		***		
					case CONNECTION:										
							switch (com) {							
								case ConnSTOP: 				ClientConnectionClose();							//Core\Commands		***
															setBusy(false);								break;
								case ConnTEST: 				ClientConnectionTest();
															setBusy(false);								break;
							}
					break;
				//	****************************************************	Test		   		***	
					
				//	****************************************************	Read		   		***			
					case READ:
							switch (com) {							
								//  all tables
								case GetDataForTables:		ClientCMDAllTables.ATpopulate(this);		break;
								//	book
								case BookExecuteQuery:		ClientCMDBook.Bookpopulate(this);			break;//arriva DA GUI TABLEBOOKS //System.out.println("passato come parametro sql : "+this.Sql);
								case BookLast:				BookLast();									break;//arriva DA GUI TABLEBOOKS //	
								case BookPopulate:			ClientCMDBook.Bookpopulate(this);		    break;
								//	Loans
								case LoanREAD: 															break;
								case LoanPopulate:			ClientCMDloans.Loanspopulate(this);			break;
								case LoanExecuteQuery:		ClientCMDloans.Loanspopulate(this);			break;
								
								case BookingREAD: 														break;
								case BookingPopulate:		ClientCMDBooking.Bookingpopulate(this);		break;
								case BookingExecuteQuery:	ClientCMDBooking.Bookingpopulate(this);		break;
								
								//	Person
								case UserREAD: 				UserGetData();								break;	//necessario setSql con query completa			
								case UserREADbyEmail: 		UserGetDatabyEmail();						break;	//necessario setSql con email							
								case UserREADbyEmailAcc:	UserGetDatabyEmailAcc();					break;	//necessario setidut															
								case UserREADbyEmailMod: 	UserGetDatabyEmailMod();					break;	//necessario setidut																									
								case UserREADlogin: 		UserGetDataLogin();							break;	//necessario setSql con email setSql2 con password							
								case UserREADaccountMod: 	UserGetDataAccountMod();					break;	//necessario setSql con email setSql2 con password																				
								case UserREADcheckEmail: 	UserREADcheckEmailExist();					break;	//necessario setSql con email 																	
								case UserREADcheckCF:		UserREADcheckCfExist();						break;//necessario setSql con cf 					

								
								default:break;	
							}				
					break;
				//	****************************************************	Change		   		***			
					case CHANGE:
							switch (com) {		
							     // Database Exist
							   case DBExist:				ClientCheckExistDB();						break;
								//	All Table		
								case tableExistBook: 		ClientCheckExistTableBook();				break;	
								case tableExistLoans:		ClientCheckExistTableLoans();				break;
								case tableExistPerson: 		ClientCheckExistTablePerson();				break;
								case tableExistBooking:		ClientCheckExistTableBooking();				break;
								case tableExistSetting: 	ClientCheckExistTableSetting();				break;
								//	book			
								case BookADD:				ClientBookAdd();							break;	// arriva DA GUI 		TABLEBOOKS			
								case BookDELETE: 			ClientBookDelete();							break;	// arriva DA classe 	tableupdatebooks						
								//	Person
								case UserUPDATE: 			UserUPDATE();								break;
								case UserDELETE: 			UserDELETE();								break;	
								case UserREADloginFIRST: 	UserGetDataLoginFIRST();					break;	//necessario setSql con email setSql2 con password_temp									
								case UserRegistration: 		ClientCHANGEuserRegistration();				break;
								case UserPasswordRecovery: 	ClientCMDuser.UserPasswordRecovery(this);	break;
								case UserPasswordRemovetemp:ClientCMDuser.UserPasswordDelTemp(this);
								
								case UserActivation: 													break;				
								//	Loans
								case LoanReturn:			ClientCMDloans.LoansReturned(this);			break;
								case LoanRetired:			ClientCMDloans.LoansRetired(this);			break;
								case LoanASK:				ClientCMDloans.LoansNew(this);				break;	//richiesta nuovo prestito -- System.err.println("leggo da client ... idbook: "+getIdbook()); 
								case LoanNew: 															break;
								case LoanListADD: 														break;	
								case LoanDELETE: 			ClientCMDloans.LoansDELETE(this);			break;	
								case LoanListREMOVE: 													break;	
								case LoanBookGet: 														break;	
								case LoanBookGiveback: 													break;	
								case LoanNoticeAvaiable: 												break;	
								case LoanNoticeExpiration: 												break;
								//	Booking
								
								
								case BookingDELETE :													break;
								case BookingListREMOVE:		ClientCMDBooking.BookingDelete(this);		break;
								
								
								default:																break;
								
								
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
																try {Thread.sleep(100);//default 1000
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

		
	public MessageBack testconn(){
		
		try {
			return this.mSgBack = 	Request(Commands.ConnTEST);
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		return this.mSgBack;
		
	}
	
	public MessageBack closeconn(){	
		//srv.closeConnectionSS();
		try {
			return this.mSgBack = 	Request(Commands.ConnSTOP);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return this.mSgBack;
	}	

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


	/**il metodo accetta un Message contenente il comando ( Commands ) che invia al server, la risposta � contenuta in un MessageBack.
	 * dipendentemente dai comandi inviati la risposta potra contenere nei parametri del MessageBack di ritorno dal server dati di 
	 * diversa natura come recordset piuttosto che String  
	/**

	 * @param MsgSend
	 * @param Mb
	 * @throws SendFailedException
	 * @throws MessagingException
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	public void sendM(Message MsgSend,MessageBack Mb) throws SendFailedException, MessagingException, SQLException, InterruptedException{
			try {
						System.out.println("CLI :> spedisco a STUB comando: "+MsgSend.getCmd());	
						Mb = this.getSrv().SendRequest(MsgSend);	// SPEDISCE AL SRV [STUB] MESSAGE contenente COMMAND																
						// Reazioni di Client ai messaggi ritornati dal Server

						switch (Mb.getText()) {
							case "OK":	break;
	
							case "STUB :> Eccezione *** nessuna risposta da SKELETON":		System.out.println("CLI - ECCEZIONE DA STUB");	
								break;
							case "SRV :> user Registration :> OK":							
								PopUp.infoBox(getActC(), "Registrazione avvenuta con successo, attiva account dal codice che ti abbiamo inviato");							
								System.out.println("TO ARRIVATO AL CLIENT : "+getTo());			
								EmailSender.send_uninsubria_email(getTo(),this);								
								this.setTo(null);
								this.setSql(null);		
								this.getMeMain().getFrame().setVisible(true);
								this.getMeMain().getBtnAccount().setVisible(true);
								break;
							
							case "SRV :> UPRecovery :> OK":
								System.out.println("TORNATO AL CLIENT UPRECOVERY OK");
							
								Login X = (Login)ActW;
								
								X.getPr().setVisible(true);
								
								X.getPfa().setVisible(false);
						

								
								break;
								
							case "SRV :> UPRecovery :> NG":								
								System.out.println("TORNATO AL CLIENT UPRECOVERY NG");								
								break;
		
							case "SRV :> table book populate :> OK":	System.out.println("BOOK metodo old ritornato al client POPULATE OK : ");
						
								
								setActTable(Mb.getTab());
								TableBooks.getTable().setModel(Mb.getTab().getModel());
						
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

								if ( getActW().getSL_Type()==AppType.AppAccount ) {
									Account Ax = (Account)getActW();
									if (Ax.getPanelModify().isVisible()) {
										
										Ax.getTxtNameMod().setText(		Mb.getRowUser()[1]);
										Ax.getTxtSurnameMod().setText(	Mb.getRowUser()[2]);
										Ax.getTxtMailMod().setText(		Mb.getRowUser()[3]);
										//pw
										Ax.getTxtInqMod().setText(		Mb.getRowUser()[5]);
										Ax.getTxtTelMod().setText(		Mb.getRowUser()[6]);
										
										System.out.println("tipo utente "+Mb.getRowUser()[7]);
										
										if ( Mb.getRowUser()[7].equals("Libraio")) {
											Ax.getRdbtnTypeUserLibMod().setSelected(true);
											Ax.getRdbtnTypeUserLetMod().setSelected(false);	
										}else {
											if ( Mb.getRowUser()[7].equals("Lettore")){
												Ax.getRdbtnTypeUserLibMod().setSelected(false);
												Ax.getRdbtnTypeUserLetMod().setSelected(true);	
											}		
										}
	
									}
									if (Ax.getPanelAccount().isVisible()) {
										
										
										
									}

								}
								
								clrParFS();	
								break;
								
								// USER Read Data by email
							case "SRV :> selected user by email panel Account:> OK":								
								System.out.println("ritornato al client UserDATA by email OK : ");																													
								
								Account accX = new Account(getMeMain().getFrame(),this);				
								setActW(accX);
								
								accX.getPanelAccount().setVisible(true);
								
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
								
								
								System.out.println("ottenuto tipo utente : "+Mb.getRowUser()[6]);
								
								String tipoutente = Mb.getRowUser()[6];
									setCliType(Clients.Guest);
									
								if (tipoutente.equals("Libraio")) {
									setCliType(Clients.Librarian);	
								}
								if (tipoutente.equals("Lettore")) {
									setCliType(Clients.Reader);	
								}
							 
								
								
								setCurrentUser(Mb.getIdUser());			//settato id utente su Client
								setIdut(Mb.getIdUser());
								setSelectedIdUser(getIdut());
								
								setDatiUtente(Mb.getRowUser());
							
								Login l = (Login)getActW();								
								l.getPfa().setVisible(false);				//PanelFirstAcc.setVisible(false);
								l.getPr().setVisible(false);				//PanelRegi.setVisible(false);	
								WindowEvent close = new WindowEvent(getActF(), WindowEvent.WINDOW_CLOSING);
								getActF().dispatchEvent(close);
								
								//invio comando login
								try {
									
									this.getMeMain().getFrame().setVisible(true);
									this.getMeMain().getBtnAccount().setVisible(true);
									this.getMeMain().getText().setText(" benvenuto ["+getDatiUtente()[6]+"] "+getDatiUtente()[4]+" "+getDatiUtente()[5]+" ");
									
									
									
								} 
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
								Login lF = (Login)getActW();													
								lF.getPfa().setVisible(false);				//PanelFirstAcc.setVisible(false);
								lF.getPr().setVisible(false);				//PanelRegi.setVisible(false);
								WindowEvent closeF = new WindowEvent(getActF(), WindowEvent.WINDOW_CLOSING);
								getActF().dispatchEvent(closeF);
								System.err.println("CLI ottenuto IDUSER " + getIdut());
								
								setIdut(Mb.getIdUser());
								//rendi visibile tasto account
								getMeMain().getBtnAccount().setVisible(true);
								
								//invio comando login
								try {
									System.out.println("GUI login:> cmd tentativo di login ");
									
									this.getCmdLIST().put(Commands.UserPasswordRemovetemp);
									PopUp.infoBox(getActW(), "LOGIN EFFETTUATO CON SUCCESSO");
						

								} catch (InterruptedException e2) {
									System.out.println("GUI login :> problemi con tentativo di login ");	
									e2.printStackTrace(); 
								}
								break;
						
							
							case "SRV :> selected user login check FIRST:> PROCEDURA CANCELLAZIONE UTENTE":								
								PopUp.errorBox(getActC(), Mb.getText());
								clrParFS();	
								break;	
	
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
							
								
							//TASTO PRESTITO	
							case "SRV :> Loans ASK :> OK":								//PopUp.infoBox(getActF(), "PRESTITO ACCORDATO");
					
							if (Mb.getRowLoans()[0].equals("inserito prestito")) {		//prestito inserito
								PopUp.infoBox(getActF(),  "Libro assegnato in prestito, \n"
														+ "l'utente ha da ora 7 giorni di tempo per il ritiro presso la biblioteca.\n"
														+ "si ricorda che allo scadere del termine il libro verr� riassegnato. ");
							}else {														//inserito in coda prenotazione
								PopUp.infoBox(getActF(),  "Libro non disponibile, \n"
													   	 +"l'utente � inserito in coda PRENOTAZIONE con priorit� : "+Mb.getRowLoans()[1]);														
							}
								break;
							

							case "SRV :> Loans ASK :> NG":								
								PopUp.infoBox(getActF(), "Errore GESTIONE DB ");
								break;	
							case "SRV :> Loans ASK :> OK , PRESTITO gia RICHIESTO":
								PopUp.infoBox(getActF(), "SRV :> Loans ASK :> OK , PRESTITO gia RICHIESTO");
								break;
							case "SRV :> Loans ASK :> OK , PRESTITO gia CONCESSO":
								PopUp.infoBox(getActF(), "SRV :> Loans ASK :> OK , PRESTITO gia CONCESSO");	
								break;
							case "SRV :> Loans ASK :> OK , PRESTITO NON CONSENTITO":			
									for (int i = 1;i<Mb.getRowLoans().length;i++) {
										 if (Mb.getRowLoans()[i].equals("SRV :> Loans ASK :> OK - PRESTITO ACCORDATO ")) {
											 Mb.getRowLoans()[i]="";
										 }
									}
									PopUp.infoBox(getActF(), "PRESTITO NON CONSENTITO\n"
									+ Mb.getRowLoans()[1]+"\n"
									+ Mb.getRowLoans()[2]+"\n"
									+ Mb.getRowLoans()[3]+"\n"
									+ "\n"
									+ "\n"
									+ "\n"
									+ "\n");
								break;
							//TASTO PRESTITO	
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

								
							case 	"SRV :> Del-Booking:> OK": 	ClientCMDBooking.BookingDeleteRES(this, "OK");	break;
							case	"SRV :> Del-Booking:> NG":	ClientCMDBooking.BookingDeleteRES(this, "NG");	break;
								
							
							case	"SRV :> table loans populate :> OK":	ClientCMDloans.LoanspopulateRES(	this,"OK", Mb);	break;
							case	"SRV :> table loans populate :> NG":	ClientCMDloans.LoanspopulateRES(	this,"NG", Mb);	break;
							
							case	"SRV :> table booking populate :> OK":	ClientCMDBooking.BookingpopulateRES(this,"OK", Mb);	break;
							case	"SRV :> table booking populate :> NG":	ClientCMDBooking.BookingpopulateRES(this,"NG", Mb);	break;
							
							case	"SRV :> tables populate :> OK"		:ClientCMDAllTables.ATpopulateRES(this, "OK", Mb); 	break;
							case	"SRV :> tables populate :> NG"		:ClientCMDAllTables.ATpopulateRES(this, "NG", Mb); 	break;
							
							case 	"SRV :> Loans DELETE :> OK"			:ClientCMDloans.LoansDELETERES(		this, "OK", Mb);break;
							case	"SRV :> Loans DELETE :> NG"			:ClientCMDloans.LoansDELETERES(		this, "NG", Mb);break;
							
							case	"SRV :> Loans RETURNED :> OK"		:ClientCMDloans.LoansReturnedRES(	this, "OK", Mb);break;
							case	"SRV :> Loans RETURNED :> NG"		:ClientCMDloans.LoansReturnedRES(	this, "NG", Mb);break;
							
							case 	"SRV :> DEL pw TEMP :> OK"			:ClientCMDuser.UserPasswordDelTempRES(this, "OK", Mb);break;
							case 	"SRV :> DEL pw TEMP :> NG"			:ClientCMDuser.UserPasswordDelTempRES(this, "NG", Mb);break;
							
							case	"SRV :> Loans RETIRED :> OK"		:ClientCMDloans.LoansRetiredRES(this, "OK", Mb);break;
							case	"SRV :> Loans RETIRED :> NG"		:ClientCMDloans.LoansRetiredRES(this, "NG", Mb);break;
							
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
	
//	
//*********************************************************************************************************************************************
//	

	/**il metodo accetta un Message contenente il comando ( Commands ) che invia al server, la risposta � contenuta in un MessageBack.
	 * dipendentemente dai comandi inviati la risposta potra contenere nei parametri del MessageBack di ritorno dal server dati di 
	 * diversa natura come recordset piuttosto che String  
	 * @param MsgSend

	/**
	 * @throws SendFailedException
	 * @throws MessagingException
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	private void ClientCheckExistDB() throws SendFailedException, MessagingException, SQLException, InterruptedException{
		Commands cmd = Commands.DBExist;
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
			setClientTypeOLD(getCliType());
			// **** Client invia Message
			sendM(MsgSend, Mb);
		}	
	}		
		/**
		 * @throws SendFailedException
		 * @throws MessagingException
		 * @throws SQLException
		 * @throws InterruptedException
		 */
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
			setClientTypeOLD(getCliType());
			// **** Client invia Message
			sendM(MsgSend, Mb);
		}	
	}	
		/**
		 * @throws SendFailedException
		 * @throws MessagingException
		 * @throws SQLException
		 * @throws InterruptedException
		 */
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
			
			setClientTypeOLD(getCliType());
			setClientTypeOLD(getCliType());
			// **** Client invia Message
			sendM(MsgSend, Mb);
		}	
	}		
		
		/**
		 * @throws SendFailedException
		 * @throws MessagingException
		 * @throws SQLException
		 * @throws InterruptedException
		 */
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
			setClientTypeOLD(getCliType());
			// **** Client invia Message
			sendM(MsgSend, Mb);
		}	
	}			
		/**
		 * @throws SendFailedException
		 * @throws MessagingException
		 * @throws SQLException
		 * @throws InterruptedException
		 */
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
			setClientTypeOLD(getCliType());
			// **** Client invia Message
			sendM(MsgSend, Mb);
		}	
	}	
		/**
		 * @throws SendFailedException
		 * @throws MessagingException
		 * @throws SQLException
		 * @throws InterruptedException
		 */
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
			setClientTypeOLD(getCliType());
			// **** Client invia Message
			
			//???????
			sendM(MsgSend, Mb);
		}	
	}	
		/**
		 * @throws SendFailedException
		 * @throws MessagingException
		 * @throws SQLException
		 * @throws InterruptedException
		 */
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
			setClientTypeOLD(getCliType());
			// **** Client invia Message
			sendM(MsgSend, Mb);
			
			
		}	
	}
		/**
		 * @throws SendFailedException
		 * @throws MessagingException
		 * @throws SQLException
		 * @throws InterruptedException
		 */
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
		
		
		/**
		 * @throws SendFailedException
		 * @throws MessagingException
		 * @throws SQLException
		 * @throws InterruptedException
		 */
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
			//setClientTypeOLD(getCliType());
			// **** Client invia Message
			
			sendM(MsgSend, Mb);	
		}		
	}
		/**
		 * @throws SendFailedException
		 * @throws MessagingException
		 * @throws SQLException
		 * @throws InterruptedException
		 */
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
			
			// setClientTypeOLD(getCliType());
			// **** Client invia Message
			
			sendM(MsgSend, Mb);
		}	
	}
		/**
		 * @throws SendFailedException
		 * @throws MessagingException
		 * @throws SQLException
		 * @throws InterruptedException
		 */
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
				setClientTypeOLD(getCliType());
				// **** Client invia Message
				sendM(MsgSend, Mb);
			}	
		}	
		/**
		 * @throws SendFailedException
		 * @throws MessagingException
		 * @throws SQLException
		 * @throws InterruptedException
		 */
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
		/**
		 * @throws SendFailedException
		 * @throws MessagingException
		 * @throws SQLException
		 * @throws InterruptedException
		 */
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
		/**
		 * @throws SendFailedException
		 * @throws MessagingException
		 * @throws SQLException
		 * @throws InterruptedException
		 */
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
		/**
		 * @throws SendFailedException
		 * @throws MessagingException
		 * @throws SQLException
		 * @throws InterruptedException
		 */
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
		/**
		 * @throws SendFailedException
		 * @throws MessagingException
		 * @throws SQLException
		 * @throws InterruptedException
		 */
		private void UserGetDatabyEmailAcc() throws SendFailedException, MessagingException, SQLException, InterruptedException{
			Commands cmd = Commands.UserREADbyEmailAcc;
			MessageBack Mb = new MessageBack();
			
			System.out.println("IDUTENTE SU CLIENT: "+this.getIdut());
			System.out.println("selIDUTENTE SU CLIENT: "+this.getSelectedIdUser());
			
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
		/**
		 * @throws SendFailedException
		 * @throws MessagingException
		 * @throws SQLException
		 * @throws InterruptedException
		 */
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
		/**
		 * @throws SendFailedException
		 * @throws MessagingException
		 * @throws SQLException
		 * @throws InterruptedException
		 */
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
		/**
		 * @throws SendFailedException
		 * @throws MessagingException
		 * @throws SQLException
		 * @throws InterruptedException
		 */
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
						this.getSql(),			// la finestra login passa la stringa email 
						this.getSql2()			// la finestra login passa la stringa password 
						);
				sendM(MsgSend, Mb);
			}	
		}			
		/**
		 * @throws SendFailedException
		 * @throws MessagingException
		 * @throws SQLException
		 * @throws InterruptedException
		 */
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
		/**
		 * @throws SendFailedException
		 * @throws MessagingException
		 * @throws SQLException
		 * @throws InterruptedException
		 */
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
		/**
		 * @throws SendFailedException
		 * @throws MessagingException
		 * @throws SQLException
		 * @throws InterruptedException
		 */
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
	
		/**
		 * @throws SendFailedException
		 * @throws MessagingException
		 * @throws SQLException
		 * @throws InterruptedException
		 */
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
		
		/**scrive nel campo testo predefinito della GUI (ReseachBooks) la stringa t
		 * @param t
		 */
		public void printonResearch(String t) {
			
			JFrame x = getActF();
			ResearchBooks X = (ResearchBooks)x;
			X.getTxtInsertCDBook().setText(t);
			
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
			public AppMain getStartWindow() {
				return StartWindow;
			}
			public void setStartWindow(AppMain startWindow) {
				StartWindow = startWindow;
			}
			public int getIdbook() {
				return idbook;
			}
			public void setIdbook(int idbook) {
				this.idbook = idbook;
			}
			public int getCurrentUser() {
				return CurrentUser;
			}
			public void setCurrentUser(int currentUser) {
				CurrentUser = currentUser;
			}
			public int getSelectedIdBook() {
				return selectedIdBook;
			}
			public void setSelectedIdBook(int selectedIdBook) {
				this.selectedIdBook = selectedIdBook;
			}
			public int getSelectedIdUser() {
				return selectedIdUser;
			}
			public void setSelectedIdUser(int selectedIdUser) {
				this.selectedIdUser = selectedIdUser;
			}
			public TableModelBooks gettMbook() {
				return tMbook;
			}
			public void settMbook(TableModelBooks tMbook) {
				this.tMbook = tMbook;
			}
			public TableModelBooking gettMbooking() {
				return tMbooking;
			}
			public void settMbooking(TableModelBooking tMbooking) {
				this.tMbooking = tMbooking;
			}
			public TableModelLoans gettMloans() {
				return tMloans;
			}
			public void settMloans(TableModelLoans tMloans) {
				this.tMloans = tMloans;
			}
			public Date getSelectedIdDataStart() {
				return selectedIdDataStart;
			}
			public void setSelectedIdDataStart(Date selectedIdDataStart) {
				this.selectedIdDataStart = selectedIdDataStart;
			}
			public Date getSelectedIdDataStop() {
				return selectedIdDataStop;
			}
			public void setSelectedIdDataStop(Date selectedIdDataStop) {
				this.selectedIdDataStop = selectedIdDataStop;
			}
			public Object[][] getDatabook() {
				return databook;
			}
			public void setDatabook(Object[][] databook) {
				this.databook = databook;
			}
			public Object[][] getDatabooking() {
				return databooking;
			}
			public void setDatabooking(Object[][] databooking) {
				this.databooking = databooking;
			}
			public Object[][] getDataloans() {
				return dataloans;
			}
			public void setDataloans(Object[][] dataloans) {
				this.dataloans = dataloans;
			}
			public boolean isRefreshData() {
				return RefreshData;
			}
			public void setRefreshData(boolean refreshData) {
				RefreshData = refreshData;
			}
			public String getFbook() {
				return Fbook;
			}
			public void setFbook(String fbook) {
				Fbook = fbook;
			}
			public String getFbooking() {
				return Fbooking;
			}
			public void setFbooking(String fbooking) {
				Fbooking = fbooking;
			}
			public String getFloans() {
				return Floans;
			}
			public void setFloans(String floans) {
				Floans = floans;
			}
			public ResearchBooks getMeRes() {
				return meRes;
			}
			public void setMeRes(ResearchBooks meRes) {
				this.meRes = meRes;
			}
			
			public JPanel getMePannelBook() {
				return mePannelBook;
			}
			public void setMePannelBook(JPanel mePannelBook) {
				this.mePannelBook = mePannelBook;
			}
			public JPanel getMePannelBooking() {
				return mePannelBooking;
			}
			public void setMePannelBooking(JPanel mePannelBooking) {
				this.mePannelBooking = mePannelBooking;
			}
			public JPanel getMePannelLoans() {
				return mePannelLoans;
			}
			public void setMePannelLoans(JPanel mePannelLoans) {
				this.mePannelLoans = mePannelLoans;
			}
			public String getDataLoanReturn() {
				return DataLoanReturn;
			}
			public void setDataLoanReturn(String dataLoanReturn) {
				DataLoanReturn = dataLoanReturn;
			}
			public boolean isStorico() {
				return storico;
			}
			public void setStorico(boolean storico) {
				this.storico = storico;
			}
			public Clients getClientType() {
				return ClientType;
			}
			public void setClientType(Clients clientType) {
				ClientType = clientType;
			}
			public Clients getClientTypeOLD() {
				return ClientTypeOLD;
			}
			public void setClientTypeOLD(Clients clientTypeOLD) {
				ClientTypeOLD = clientTypeOLD;
			}
			public Account getMeAcc() {
				return meAcc;
			}
			public void setMeAcc(Account meAcc) {
				this.meAcc = meAcc;
			}
			public static int getConn() {
				return conn;
			}
			public static void setConn(int conn) {
				Client.conn = conn;
			}
			public String getTc() {
				return tc;
			}
			public void setTc(String tc) {
				this.tc = tc;
			}
			public Message getMess() {
				return mess;
			}
			public void setMess(Message mess) {
				this.mess = mess;
			}
			public Message getmSgTOsend() {
				return mSgTOsend;
			}
			public void setmSgTOsend(Message mSgTOsend) {
				this.mSgTOsend = mSgTOsend;
			}
			public String getIpaddLocal() {
				return ipaddLocal;
			}
			public void setIpaddLocal(String ipaddLocal) {
				this.ipaddLocal = ipaddLocal;
			}
			public String getIpadLan() {
				return ipadLan;
			}
			public void setIpadLan(String ipadLan) {
				this.ipadLan = ipadLan;
			}
			public String getIpadWww() {
				return ipadWww;
			}
			public void setIpadWww(String ipadWww) {
				this.ipadWww = ipadWww;
			}

			public String getDataLoanRetired() {
				return DataLoanRetired;
			}
			public void setDataLoanRetired(String dataLoanRetired) {
				DataLoanRetired = dataLoanRetired;
			}

		
	
	
}
