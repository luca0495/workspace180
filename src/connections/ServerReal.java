package connections;


import java.awt.event.WindowEvent;
import java.io.IOException;

import java.net.Socket;

import java.util.Date;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JTable;

import javax.swing.table.DefaultTableModel;

import Check.Check;

import Core.Guardian;
import Core.Requests;

import ProvaEmail.EmailSender;

import gui.SystemServerSkeleton;

import database.*;

//ver 2017 03 29 v1
public class ServerReal extends ServerSkeleton {
		private 	SystemServerSkeleton 	meS;		
		private 	Server					Srv;
		private 	Guardian				GpG;
		private 	Requests				Req;		
		private 	String 					mSg;		
		private  	Map<String,Message>  	listcmdDONE = new TreeMap<>();		
		public 		Boolean					Go;
		
		private 	MessageBack 			x; 
		//private 	MessageBack				mSgB;	
		private 	String [][] 			datitabellaBook 	= null;
		private 	String [][] 			datitabellaBooking 	= null;
		private 	String [][] 			datitabellaLoans 	= null;
		
//**-------------------------------------------------------------------------------------------------------------
		private boolean 	chkinprogress1=false;
		private String 		chkResult1;
		private boolean 	chkinprogress2=false;
		private String 		chkResult2;
		private boolean 	chkinprogress3=false;
		private String 		chkResult3;
		private boolean 	chkinprogress4=false;
		private String 		chkResult4;
		private boolean 	chkinprogress5=false;
		private String 		chkResult5;		
		private boolean 	chkinprogress6=false;
		private String 		chkResult6;	
//**-------------------------------------------------------------------------------------------------------------		
		public ServerReal(Socket socket,Server SrvRif) throws Exception{
		super(socket);//SERVER Skeleton...
		
		setSrv(SrvRif);
		// Ottiene i riferimenti di Request e Guardian dal Server
		setGpG(Srv.getG());
		setReq(Srv.getR());	
		Go=false;
		
		setMeS(new SystemServerSkeleton(this));
		getMeS().getFrame().setVisible(true);	
		
		Srv.setSrvconnINC();		//	INCrease Server Connections counter
		Srv.getMeG().addMsg(mSg="numero connessioni attive :"+ Srv.getSrvconn());	
	}	
//**------------------------------------------------------------------------------------------------------------- Connection
	//Connections
	@Override
	public MessageBack connection(Message msg) throws InterruptedException, IOException {	
				System.out.println("REAL SERVER :> GESTISCO RICHIESTA CONNNECTION");
		//MessageBack x = new MessageBack();
		x = new MessageBack();
		
		
		switch (msg.getCommand()) {		
			case ConnTEST:
				//System.out.println(mSg = "REALServer:> TEST connessione richiesto ");
				getMeS().addMsg(mSg);				
				x.setText(new String ("SRV - Connessione OK"));					
				break;												
			case ConnSTOP:			
				System.out.println(mSg = "REALServer:> CHIUSURA connessione richiesta ");
				getMeS().addMsg(mSg);
				x.setText(new String ("SRV - chiudo socket tra 5 secondi..."));			
				super.STOP=true;
				
				//Server.setSrvconn(Server.getSrvconn() - 1);	
				getSrv().removeOp(this);
				getMeS().getFrame().setVisible(false);

				Thread.sleep(10);
				
				System.out.println("REALServer:> attuale numero connessioni : "+ Server.getSrvconn() +"\n"); 	
				System.out.println("REALServer:> chiusura socket...");
				break;		
			default:
				break;
		}	
		return x;
	}
//**------------------------------------------------------------------------------------------------------------- Visualizza
	@Override
	public MessageBack 	visualizza(Message Mes) {	//----> [DB]	// Query DIRETTA al dbManager

		System.out.println("RealServer :> Rx Visualizza ");
		
		MessageRealServer M 	= this.MessageEncapsulation(Mes);
		//MessageBack x 			= new MessageBack();
		MessageBack AnswerM 		= new MessageBack();
		
		x 			= new MessageBack();

		/*
		if (!Srv.isDbOK()) {	
			System.out.println("SRV CONTROLLO DI ESISTENZA DB NG");
			x.setText("DB down");
			return x;
		}
		*/
		
		// ********************************
		switch (M.getMsg().getCommand()) {
		// ********************************


		
		case GetDataForTables:			
			
			System.out.println("RealServer :> GetDataForTables... ");
			
			try {
				datitabellaLoans 	= MQ_Read.ResearchLoans();		//Filtro opzionale
				datitabellaBook 	= MQ_Read.RicercaLibro();		//Filtro opzionale
				datitabellaBooking 	= MQ_Read.ResearchBooking(); 	//Filtro opzionale
				
				getMeS().addMsg(mSg);
				
				x.setDataloans(datitabellaLoans);
				x.setDatabook(datitabellaBook);
				x.setDatabooking(datitabellaBooking);
				
				x.setText(new String ("SRV :> tables populate :> OK"));		
		} 		
			catch (Exception e) {			
				x.setText(new String ("SRV :> tables populate :> NG"));}//break;
		return x;

		
		
			case LoanREAD://----> [DB] 
	
				System.out.println("REAL SERVER :> \nREAL SERVER :> Gestisco RICHIESTA :> Loan Execute Query ");					
				try {					
					JTable table=new JTable();
					// Clear table
					table.setModel(new DefaultTableModel());			
					// Model for Table				
					DefaultTableModel model = (DefaultTableModel)table.getModel();				
					model.addColumn("Codice");
					model.addColumn("Id");
					model.addColumn("Data_Inizio");
					model.addColumn("Data_Fine");
					model.addColumn("Rientrato");
					model.addColumn("Ritirato");
					model.addColumn("Scaduto");
					model.addColumn("Email_Inviata");	
	
					DBmanager.openConnection();
					ResultSet rs = DBmanager.executeQuery(M.getMsg().getSQLQuery());
	
					int row = 0;
					System.out.println("Test1");
					while((rs!=null) && (rs.next()))
					{
						System.out.println("Test2 addRow");	
						model.addRow(new Object[0]);
						System.out.println("Codice : "+rs.getString("codice"));	System.out.println("Test3");
					model.setValueAt(rs.getString("codice"), row, 0);			System.out.println("Test4");								
					model.setValueAt(rs.getString("id"), row, 1);				System.out.println("Test5");								
					model.setValueAt(rs.getString("data_inizio"), row, 2);		System.out.println("Test6");								
					model.setValueAt(rs.getString("data_fine"), row, 3);		System.out.println("Test7");
					model.setValueAt(rs.getString("rientrato"), row, 4);		System.out.println("Test7");
					model.setValueAt(rs.getString("ritirato"), row, 5);			System.out.println("Test7");
					model.setValueAt(rs.getString("scaduto"), row, 6);			System.out.println("Test7");
					model.setValueAt(rs.getString("email_inviata"), row, 7);	System.out.println("Test7");
					
					
					row++;						
					}
						System.out.println("Test9");
					
					rs.close();
					DBmanager.closeConnection();
					
					getMeS().addMsg(mSg);
					x.setTab(table);
					x.setText(new String ("SRV :> table Loans populate :> OK"));	
					
				} catch (SQLException e) {	
					System.out.println("problemi con query Loans table populate");
					e.printStackTrace();				
					
					getMeS().addMsg(mSg);
					x.setText(new String ("SRV :> table Loans populate :> NG"));
	
				}
				System.out.println("SYS AL :> srv ritorna "+x.getText());										
				return x;
//*
			case 	LoanExecuteQuery://----> [DB]
				System.out.println("REAL SERVER :> \nREAL SERVER :> Gestisco RICHIESTA :> Loans Execute Query ");					
				try {
					JTable table=new JTable();
					table.setModel(new DefaultTableModel());
					// Model for Table				
					DefaultTableModel model = (DefaultTableModel)table.getModel();				
					model.addColumn("Codice");
					model.addColumn("Id");
					model.addColumn("Data_Inizio");
					model.addColumn("Data_Fine");
					model.addColumn("Rientrato");
					model.addColumn("Ritirato");
					model.addColumn("Scaduto");
					model.addColumn("Email_Inviata");			

					DBmanager.openConnection();
					ResultSet rs = DBmanager.executeQuery(M.getMsg().getSQLQuery());
	
					int row = 0;
					System.out.println("Test1");
					while((rs!=null) && (rs.next()))
					{
						System.out.println("Test2 addRow");	
						model.addRow(new Object[0]);
						System.out.println("Codice : "+rs.getString("codice"));	System.out.println("Test3");
					model.setValueAt(rs.getString("codice"), row, 0);			System.out.println("Test4");								
					model.setValueAt(rs.getString("id"), row, 1);				System.out.println("Test5");								
					model.setValueAt(rs.getString("data_inizio"), row, 2);		System.out.println("Test6");								
					model.setValueAt(rs.getString("data_fine"), row, 3);		System.out.println("Test7");
					model.setValueAt(rs.getString("rientrato"), row, 4);		System.out.println("Test7");
					model.setValueAt(rs.getString("ritirato"), row, 5);			System.out.println("Test7");
					model.setValueAt(rs.getString("scaduto"), row, 6);			System.out.println("Test7");
					model.setValueAt(rs.getString("email_inviata"), row, 7);	System.out.println("Test7");
					row++;						
					}
						System.out.println("Test9");
					
					rs.close();
					DBmanager.closeConnection();
					
					//table.setModel(model);
					
					getMeS().addMsg(mSg);
					x.setTab(table);
					x.setText(new String ("SRV :> table loans populate :> OK"));	
					
				} catch (SQLException e) {	
					System.out.println("problemi con query loans table populate");
					e.printStackTrace();				
					
					getMeS().addMsg(mSg);
					x.setText(new String ("SRV :> table loans populate :> NG"));
	
				}
				System.out.println("SYS AL :> srv ritorna "+x.getText());										
				return x;			
				
				
				
			//*		
			case 	BookingExecuteQuery://----> [DB]
				System.out.println("REAL SERVER :> \nREAL SERVER :> Gestisco RICHIESTA :> Booking Execute Query ");					
				try {					
					JTable table=new JTable();
					// Clear table
					table.setModel(new DefaultTableModel());			
					// Model for Table				
					DefaultTableModel model = (DefaultTableModel)table.getModel();				
					model.addColumn("Codice");
					model.addColumn("Id");
					model.addColumn("Prioritá");
					model.addColumn("Data_Inizio");
	
					DBmanager.openConnection();
					ResultSet rs = DBmanager.executeQuery(M.getMsg().getSQLQuery());
	
					int row = 0;
					System.out.println("Test1");
					while((rs!=null) && (rs.next()))
					{
						System.out.println("Test2 addRow");	
						model.addRow(new Object[0]);
						System.out.println("Codice : "+rs.getString("codice"));	System.out.println("Test3");
					model.setValueAt(rs.getString("codice"), row, 0);			System.out.println("Test4");								
					model.setValueAt(rs.getString("id"), row, 1);				System.out.println("Test5");								
					model.setValueAt(rs.getString("priorità"), row, 2);			System.out.println("Test6");								
					model.setValueAt(rs.getString("data_inizio"), row, 3);		System.out.println("Test7");								
					row++;						
					}
						System.out.println("Test9");
					
					rs.close();
					DBmanager.closeConnection();
					
					getMeS().addMsg(mSg);
					x.setTab(table);
					x.setText(new String ("SRV :> table booking populate :> OK"));	
					
				} catch (SQLException e) {	
					System.out.println("problemi con query booking table populate");
					e.printStackTrace();				
					
					getMeS().addMsg(mSg);
					x.setText(new String ("SRV :> table booking populate :> NG"));
	
				}
				System.out.println("SYS AL :> srv ritorna "+x.getText());										
				return x;	
//*		
			case 	BookExecuteQuery://----> [DB]
				System.out.println("REAL SERVER :> \nREAL SERVER :> Gestisco RICHIESTA :> Book Execute Query ");					
				try {					
					JTable table=new JTable();
					// Clear table
					table.setModel(new DefaultTableModel());			
					// Model for Table				
					DefaultTableModel model = (DefaultTableModel)table.getModel();				
					model.addColumn("Codice");
					model.addColumn("Nome_Autore");
					model.addColumn("Cognome_Autore");
					model.addColumn("Categoria");
					model.addColumn("Titolo");
					model.addColumn("Disponibilità");
					model.addColumn("Prenotazioni_in_coda");
	
					DBmanager.openConnection();
					
					 System.err.println("srv query in esecuzione "+M.getMsg().getSQLQuery());
					
					ResultSet rs = DBmanager.executeQuery(M.getMsg().getSQLQuery());
	
					int row = 0;
					System.out.println("Test1");
					while((rs!=null) && (rs.next()))
					{
						System.out.println("Test2 addRow");	
						model.addRow(new Object[0]);
						System.out.println("Codice : "+rs.getString("codice"));																		System.out.println("Test3");
					model.setValueAt(rs.getString("codice"), row, 0);			System.out.println("Test4");								
					model.setValueAt(rs.getString("nome_autore"), row, 1);		System.out.println("Test5");								
					model.setValueAt(rs.getString("cognome_autore"), row, 2);	System.out.println("Test6");								
					model.setValueAt(rs.getString("categoria"), row, 3);		System.out.println("Test7");								
					model.setValueAt(rs.getString("titolo"), row, 4);			System.out.println("Test8");
					model.setValueAt(rs.getString("disponibilità"), row, 5);
					model.setValueAt(rs.getString("prenotazioni_in_coda"), row, 6);
					row++;						
					}
						System.out.println("Test9");
					
					rs.close();
					DBmanager.closeConnection();
					
					getMeS().addMsg(mSg);
					x.setTab(table);
					x.setText(new String ("SRV :> table book populate :> OK"));	
					
				} catch (SQLException e) {	
					System.out.println("problemi con query book table populate");
					e.printStackTrace();				
					
					getMeS().addMsg(mSg);
					x.setText(new String ("SRV :> table book populate :> NG"));
	
				}
				System.out.println("SYS AL :> srv ritorna "+x.getText());										
				return x;								

			case BookLast://----> [DB] 
				System.out.println("REAL SERVER :> \nREAL SERVER :> Gestisco RICHIESTA :> book last id ");					
				try {
					String idbook = MQ_Check.checkCFLastInsert();
								//PARAMETRO DI RITORNO 
					x.setIdBook(Integer.valueOf(idbook));	
					x.setText(new String ("SRV :> BookLastId :> OK"));
				} catch (SQLException e) {	
								System.out.println("problemi con BookLastId ");
								e.printStackTrace();				
					x.setIdBook(0);	
					x.setText(new String ("SRV :> BookLastId :> NG"));
					getMeS().addMsg(mSg);
					
				}
				System.out.println("SYS AL :> srv ritorna "+x.getText());										
				return x;					
				
			case UserREAD://----> [DB] 
					System.out.println("REAL SERVER :> \nREAL SERVER :> Gestisco RICHIESTA :> User Read ");					
					try {			
					String[] UserData = MQ_Read.selectUserByQuery(M.getMsg().getSQLQuery());
					x.setRowUser(UserData);
					x.setText(new String ("SRV :> selected user :> OK"));
					} catch (SQLException e) {	
						System.out.println("problemi con query select user ");
						e.printStackTrace();				
						
						getMeS().addMsg(mSg);
						x.setText(new String ("SRV :> selected user :> NG"));
					}
					System.out.println("SYS AL :> srv ritorna "+x.getText());										
					return x;				
			
			case UserREADcheckEmail://----> [DB] 
					System.out.println("REAL SERVER :> \nREAL SERVER :> Gestisco RICHIESTA :> User Read check email exist");					
					
					x.setWtype( M.getMsg().getSQLQuery2());//tipo finestra chiamante : { Account / AppReader }
					
					
					try {			
					Boolean Ex = Check.checkMailExist(M.getMsg().getSQLQuery());			
						if (Ex){	//email esistente
							x.setText(new String ("SRV :> URCE :> OK Exist"));
						}else {		//email libera
							x.setText(new String ("SRV :> URCE :> OK Not Exist"));
						}
					} catch (Exception e) {	
						System.out.println("problemi con query select user ");
						e.printStackTrace();								
						getMeS().addMsg(mSg);
						x.setText(new String ("SRV :> URCE :> NG"));
					}
					System.out.println("SYS AL :> srv ritorna "+x.getText());										
					return x;							
					
			case UserREADcheckCF://----> [DB] 
					System.out.println("REAL SERVER :> \nREAL SERVER :> Gestisco RICHIESTA :> User Read check CF exist");					
					try {			
					Boolean Ex = Check.checkCodFisExist(M.getMsg().getSQLQuery());			
						if (Ex){	//CF esistente
							x.setText(new String ("SRV :> URCCF :> OK Exist"));
						}else {		//CF libero
							x.setText(new String ("SRV :> URCCF :> OK Not Exist"));
						}
					} catch (Exception e) {	
						System.out.println("problemi con query select user ");
						e.printStackTrace();								
						getMeS().addMsg(mSg);
						x.setText(new String ("SRV :> URCCF :> NG"));
					}
					System.out.println("SYS AL :> srv ritorna "+x.getText());										
					return x;		
				
				
			case UserREADbyEmail://----> [DB] 
					System.out.println("REAL SERVER :> \nREAL SERVER :> Gestisco RICHIESTA :> User Read by email");					
					try {				
					
					String[] UserData = MQ_Read.retrieveUserIdbyemail(M.getMsg().getSQLQuery());//nel parametro SQL viene passata la email				
					x.setRowUser(UserData);
					
					x.setText(new String ("SRV :> selected user by email:> OK"));					
					} catch (SQLException e) {	
						System.out.println("problemi con query select user by email ");
						e.printStackTrace();				
						
						getMeS().addMsg(mSg);
						x.setText(new String ("SRV :> selected user by email:> NG"));
					}
					System.out.println("SYS AL :> srv ritorna "+x.getText());										
					return x;				
	
			case UserREADbyEmailAcc://----> [DB] 
					System.out.println("REAL SERVER :> \nREAL SERVER :> Gestisco RICHIESTA :> User Read by email acc");					
					try {				
					String[] UserData = MQ_Read.retrieveUserIdbyid(M.getMsg().getIdut());//nel parametro SQL viene passata la email				
					x.setRowUser(UserData);
					x.setText(new String ("SRV :> selected user by email panel Account:> OK"));					
					} catch (SQLException e) {	
						System.out.println("problemi con query select user by email panel Account");
						e.printStackTrace();				
						
						getMeS().addMsg(mSg);
						x.setIdUser(M.getMsg().getIdut());
						
						x.setText(new String ("SRV :> selected user by email panel Account:> NG"));
					}
					System.out.println("SYS AL :> srv ritorna "+x.getText());										
					return x;
			
			case UserREADbyEmailMod://----> [DB] 
					System.out.println("REAL SERVER :> \nREAL SERVER :> Gestisco RICHIESTA :> User Read by email mod");					
					try {				
					String[] UserData = MQ_Read.retrieveUserIdbyid(M.getMsg().getIdut());//nel parametro SQL viene passata la email				
					x.setRowUser(UserData);
					x.setText(new String ("SRV :> selected user by email panel Modify:> OK"));					
					} catch (SQLException e) {	
						System.out.println("problemi con query select user by email ");
						e.printStackTrace();				
						
						getMeS().addMsg(mSg);
						x.setIdUser(M.getMsg().getIdut());
						
						x.setText(new String ("SRV :> selected user by email panel Modify:> NG"));
					}
					System.out.println("SYS AL :> srv ritorna "+x.getText());										
					return x;
				
			case UserREADlogin://----> [DB] 
					System.out.println("REAL SERVER :> \nREAL SERVER :> Gestisco RICHIESTA :> User Read Login");					
					try {				
						String email 	= M.getMsg().getSQLQuery();
						String pass 	= M.getMsg().getSQLQuery2();							
						String []r 		= Check.checkAdminLogIn(email, pass);						
						System.out.println("ottenuto dalla query id utente"+r[1]);												
						if (r[1]==null) {
							x.setIdUser(0);
						}else {
							x.setIdUser(Integer.valueOf(r[1]));
						}
						
						x.setRowUser(r);
						x.setUserEmail(email);
						x.setText(new String ("SRV :> selected user login check:> "+r[0]));						
					} catch (SQLException e) {	
						System.out.println("problemi con \"SRV :> selected user login check ");
						e.printStackTrace();										
						getMeS().addMsg(mSg);
						x.setText(new String ("SRV :> selected user login check:> NG"));
					}
					System.out.println("SYS AL :> srv ritorna "+x.getText());										
					return x;				
			default:
					break;
	}		
		return AnswerM;
	}

	@Override
	public MessageBack modifica(Message Mes) {	//	----> 	[	Guardian	[GpG]				]
												//	---->	[	 			  Librarian	 		]
												//	---->	[						  Reader	]
												//	---->	[	Account		[AL]	[AR]		]---->	DB
												//	---->	[	BookL	 	[BL]	[BR]		]
												//	---->	[	Prenotation [PL]	[PR]		]
		
																// accodamento richiesta su Requests gestita da Guardian 	
																//System.out.println("RealServer :> Rx Change ");
		MessageRealServer M = this.MessageEncapsulation(Mes);	//System.out.println("RealServer :> Rx Change - vado in switch getUType : "+M.getMsg().getUType());
		MessageBack x 		= new MessageBack();
		MessageBack Answer 	= new MessageBack();


		//**********************************************************************************		
		switch (M.getMsg().getUType()) {			// estrae tipo di utente da Message 				
		//**********************************************************************************
//****
			case Librarian :				//-->[GpG [?L]] ---->[DB] 	System.out.println("RealServer :> Rx Librarian");						
							
				switch ( M.getMsg().getCommand().getTarget()){

				case Setting:	

					try {					
							System.out.println("RealServer :> Accodo M [ SL ]");
							System.out.println("RealServer :> AL in attesa prima... "+Req.getSL().getWr());
							Req.getSL().put(M);
							//******************************************************************************
							while (!Go){
								try {
									System.out.println("REAL SERVER L-S:> in attesa GO da Guardian");
									Thread.sleep(10);
								} catch (Exception e) 
								{}
									System.out.println("REAL SERVER L-S:> go "+Go);						
							}	//Attesa del turno...									
							//******************************************************************************							
							switch (M.getMsg().getCommand()) {
							
							case tableExistSetting:
								
								System.out.println("REAL SERVER :> fine attesa \nREAL SERVER :> Gestisco RICHIESTA :> tableExistSetting ");					
							try {
								CheckDBSetting.TableExistSetting();
								getMeS().addMsg(mSg);
								x.setText(new String ("SRV :> CHECK TABLE Setting Exist :> OK"));	
							} catch (SQLException e) {
								getMeS().addMsg(mSg);
								x.setText(new String ("SRV :> CHECK TABLE Setting Exist :> NG..."));
								System.out.println("problemi con controllo tabella Setting");
								e.printStackTrace();
							}
								System.out.println("SYS AL :> srv ritorna "+x.getText());										
							return x;								
							//break;		
							default:
								break;							
							}		
					}catch (Exception e) {						
					}
				break;

				
				case Booking:		
				
					try {					
							System.out.println("RealServer :> Accodo M [ BKL ]");
							System.out.println("RealServer :> AL in attesa prima... "+Req.getBKL().getWr());		
							Req.getBKL().put(M);
							//******************************************************************************
							while (!Go){
								try {
									System.out.println("REAL SERVER L-BK:> in attesa GO da Guardian");
									Thread.sleep(10);
								} catch (Exception e) 
								{}
									System.out.println("REAL SERVER L-BK:> go "+Go);						
							}	//Attesa del turno...									
							//******************************************************************************							
							switch (M.getMsg().getCommand()) {							
							case tableExistBooking:							
								System.out.println("REAL SERVER :> fine attesa \nREAL SERVER :> Gestisco RICHIESTA :> tableExistBooking ");					
								try {
									ChkDBandTab.tableExistBooking();
									getMeS().addMsg(mSg);
									x.setText(new String ("SRV :> CHECK TABLE Booking Exist :> OK"));	
								} catch (SQLException e) {
									getMeS().addMsg(mSg);
									x.setText(new String ("SRV :> CHECK TABLE Booking Exist :> NG..."));
									System.out.println("problemi con controllo tabella Booking");
									e.printStackTrace();
								}
									System.out.println("SYS AL :> srv ritorna "+x.getText());										
								return x;								
								//break;							
							case BookingListREMOVE:	
								System.out.println("REAL SERVER :> \nREAL SERVER :> Gestisco RICHIESTA :> Booking LIST DELETE ");					
								System.out.println("REAL SERVER :> id user passata dal client :"+M.getMsg().getSelectedIdUser());
								System.out.println("REAL SERVER :> id book passata dal client :"+M.getMsg().getSelectedIdBook());
								try {									
									int[]r=new int[2];
									r[0]=M.getMsg().getSelectedIdBook() ;
									r[1]=M.getMsg().getSelectedIdUser();											
									
									MQ_Delete.deleteRowBooking(r);		
									
									
									getMeS().addMsg(mSg);
									x.setText(new String ("SRV :> Del-Booking:> OK"));										
								} catch (SQLException e) {	
									System.out.println("problemi con query Del-Booking");
									e.printStackTrace();														
									getMeS().addMsg(mSg);
									x.setText(new String ("SRV :> Del-Booking:> NG"));
								}
								
								
								System.out.println("SYS AL :> srv ritorna "+x.getText());										
								return x;	
								//break;							
							default:
								break;							
							}		
					}catch (Exception e) {						
					}
				break;
			
					case Account:		
						try {					
										System.out.println("RealServer :> Accodo M [ AL ]");
										System.out.println("RealServer :> AL in attesa prima... "+Req.getAL().getWr());
			
								Req.getAL().put(M);
								//******************************************************************************
								while (!Go){
									try {
										System.out.println("REAL SERVER L-A:> in attesa GO da Guardian");
										Thread.sleep(10);
									} catch (Exception e) 
									{}
										System.out.println("REAL SERVER L-A:> go "+Go);						
								}	//Attesa del turno...									
								//******************************************************************************							
								switch (M.getMsg().getCommand()) {
															
								case UserRegistration:
									System.out.println("REAL SERVER :> fine attesa \nREAL SERVER :> Gestisco RICHIESTA :> userRegistration ");					
									try {									
										MQ_Insert.insertUtente(M.getMsg().getSQLQuery());														
										getMeS().addMsg(mSg);
										x.setText(new String ("SRV :> user Registration :> OK"));	
									} catch (SQLException e) {
										getMeS().addMsg(mSg);
										x.setText(new String ("SRV :> user Registration :> NG..."));
										System.out.println("problemi con controllo tabella person");
										e.printStackTrace();
									}
									System.out.println("SYS AL :> srv ritorna "+x.getText());										
									return x;								
									//break;	
								case UserPasswordRecovery:
									System.out.println("REAL SERVER :> fine attesa \nREAL SERVER :> Gestisco RICHIESTA :> UserPasswordRecovery ");					
									try {										
										//cambia query
										MQ_Update.updateNewPassForgot(M.getMsg().getSQLQuery());
										//invia mail
										EmailSender.send_uninsubria_recoverypassword(M.getMsg().getSQLQuery2(), 
																					null, 
																					M.getMsg().getPw());
										getMeS().addMsg(mSg);
										x.setText(new String ("SRV :> UPRecovery :> OK"));											
									} catch (Exception e) {
										getMeS().addMsg(mSg);
										x.setText(new String ("SRV :> UPRecovery :> NG"));
										System.out.println("problemi con password recovery");
										e.printStackTrace();
									}
									System.out.println("SYS AL :> srv ritorna "+x.getText());										
									return x;								
									//break;	
									
								case UserPasswordRemovetemp:
									int id 			= 	M.getMsg().getIdut();
									String passT 	= 	M.getMsg().getPw();
									
									System.out.println("REAL SERVER :> fine attesa \nREAL SERVER :> Gestisco RICHIESTA :> User Password REMOVE temp ");					
									try {										
										//cambia query
										MQ_Delete.deletePassTemp(id, passT);
										//cancella pw temp
										getMeS().addMsg(mSg);
										x.setText(new String ("SRV :> DEL pw TEMP :> OK"));											
									} catch (Exception e) {
										getMeS().addMsg(mSg);
										x.setText(new String ("SRV :> DEL pw TEMP :> NG"));
										System.out.println("problemi con password TEMP ");
										e.printStackTrace();
									}
									System.out.println("SYS AL :> srv ritorna "+x.getText());										
									return x;								
									
									
									
								case UserUPDATE:	//SRV UP
										System.out.println("REAL SERVER :> fine attesa \nREAL SERVER :> Gestisco RICHIESTA :> USER UPDATE ");					
									try {
										System.out.println("REAL SERVER :> Query passata dal client :"+M.getMsg().getSQLQuery().toString());
										System.out.println("REAL SERVER :> email user passata dal client :"+M.getMsg().getSQLQuery2().toString());
										System.out.println("REAL SERVER :> id user passata dal client :"+M.getMsg().getIdut());										
										MQ_Update.updateModUserIdbyQuery(M.getMsg().getSQLQuery());		
										getMeS().addMsg(mSg);
										x.setText(new String ("SRV :> UP :> OK"));
										x.setIdUser(M.getMsg().getIdut());
										x.setUserEmail(M.getMsg().getSQLQuery2());										
									} catch (Exception e) {
										getMeS().addMsg(mSg);
										x.setText(new String ("SRV :> UP :> NG"));
										System.out.println("problemi con controllo UPDATE UTENTE");
										e.printStackTrace();
									}
									System.out.println("SYS AL :> srv ritorna "+x.getText());										
									return x;								
									//break;									
								case UserDELETE:	
										System.out.println("REAL SERVER :> \nREAL SERVER :> Gestisco RICHIESTA :> USER DELETE ");					
										System.out.println("REAL SERVER :> id user passata dal client :"+M.getMsg().getIdut());
									try {														
										MQ_Delete.deleteRowPerson(M.getMsg().getIdut());										
										getMeS().addMsg(mSg);
										x.setText(new String ("SRV :> USER del :> OK"));										
									} catch (SQLException e) {	
										System.out.println("problemi con query USER del");
										e.printStackTrace();														
										getMeS().addMsg(mSg);
										x.setText(new String ("SRV :> USER del :> NG"));
									}
									System.out.println("SYS AL :> srv ritorna "+x.getText());										
									return x;	
									//break;			
									
								case UserREADloginFIRST:
										System.out.println("REAL SERVER :> \nREAL SERVER :> Gestisco RICHIESTA :> User Read Login first time");					
									try {				
										String email 	= M.getMsg().getSQLQuery();
										String pass 	= M.getMsg().getSQLQuery2();
										//Estrapola dati utente										
										String []userdata	= MQ_Read.selectAdminLogInFIRST(email, pass);
										System.err.println("email 	: "+ userdata[0]);
										System.err.println("pwtemp 	: "+ userdata[1]);
										System.err.println("idut 	: "+ userdata[2]);
										System.err.println("tipo 	: "+ userdata[3]);
										x.setUsertype(userdata[3]);
										x.setIdUser(Integer.valueOf(userdata[2]));
										
										String rf 		= Check.checkAdminLogInFIRST(email, pass);
										System.out.println(" checkadinloginfirst ritorna"+rf);										
										
										if(	rf.equals("I Campi Non Possono Essere Vuoti")
											||	
											rf.equals("Nessun Dato")
											||
											rf.equals("L'Email Non Esiste")) 
										{
											System.out.println("riscontrato campi vuoti "); 	
											//ritorna

											x.setText("SRV :> selected user login check FIRST:> "+rf);
											return x;
										}
										// RECUPERA VALORE NUMERO TENTATIVI										
										//*****************************************
										String[] user = MQ_Read.UserLoginTryCounter(email);
										//*****************************************										
										String tent = user[1];
										int tentativi = Integer.parseInt(tent);
											System.out.println("REAL SERVER L-A:> recuperati numero tentativi effettuati: "+tentativi);
										if (tentativi == 5) {
											String idUser = user[2];
											System.out.println("REAL SERVER L-A:> PROCEDURA CANCELLAZIONE UTENTE id "+idUser );
											// PROCEDURA CANCELLAZIONE UTENTE
											List<String> rowData = new ArrayList<String>();
											rowData.add(0, idUser);
											
											MQ_Delete.deleteRowPerson(rowData);
											
											x.setText(new String ("SRV :> selected user login check FIRST:> PROCEDURA CANCELLAZIONE UTENTE" ));										
										}else {
											System.out.println("REAL SERVER L-A:> PROCEDURA INCREMENTO NUMERO TENTATIVI ");
											// INCREMENTA CAMPO TENTATIVI
											tentativi++;
											System.out.println("REAL SERVER L-A:> recuperati numero tentativi aumentati: "+tentativi);	
											
											// AGGIORNA campo numero tentativi
											MQ_Update.updateLoginTry(email, tentativi);
											
											System.out.println("REAL SERVER L-A:> PROCEDURA dopo update numero tentativi ");
											//controllo esito operazione
											//tent= String.valueOf(tentativi);
											System.out.println("REAL SERVER L-A:> recuperati numero tentativi AGGIORNATO: "+tentativi);											
											System.out.println("email : "+email);
											System.out.println("pass  : "+pass);
											
												try {
													MQ_Delete.deletePassTempEP(email, pass);
												} catch (Exception e) {
													
												}
											

											
											x.setIdUser(Integer.valueOf(userdata[2]));
											
											x.setUserEmail(email);
											x.setText(new String ("SRV :> selected user login check FIRST:> "+rf));											
										}
		
									} catch (SQLException e) {	
										System.out.println("problemi con \"SRV :> selected user login check FIRST");
										e.printStackTrace();				
										
										getMeS().addMsg(mSg);
										x.setText(new String ("SRV :> selected user login check FIRST:> NG"));
									}
										System.out.println("SYS AL :> srv ritorna "+x.getText());										
									return x;		
					
								case UserREADlogin://----> [DB] 
									System.out.println("REAL SERVER :> \nREAL SERVER :> Gestisco RICHIESTA :> User Read Login");					
									try {				
										String email 	= M.getMsg().getSQLQuery();
										String pass 	= M.getMsg().getSQLQuery2();							
										String []r 		= Check.checkAdminLogIn(email, pass);						
										System.out.println("ottenuto dalla query id utente"+r[1]);												
										if (r[1]==null) {
											x.setIdUser(0);
										}else {
											x.setIdUser(Integer.valueOf(r[1]));
										}
										
										x.setRowUser(r);
										x.setUserEmail(email);
										x.setText(new String ("SRV :> selected user login check:> "+r[0]));						
									} catch (SQLException e) {	
										System.out.println("problemi con \"SRV :> selected user login check ");
										e.printStackTrace();										
										getMeS().addMsg(mSg);
										x.setText(new String ("SRV :> selected user login check:> NG"));
									}
									System.out.println("SYS AL :> srv ritorna "+x.getText());										
									return x;				
						
									
									
								case tableExistPerson:								
										System.out.println("REAL SERVER :> fine attesa \nREAL SERVER :> Gestisco RICHIESTA :> tableExistPerson ");					
									try {
										ChkDBandTab.tableExistPerson();
										getMeS().addMsg(mSg);
										x.setText(new String ("SRV :> CHECK TABLE Person Exist :> OK"));	
									} catch (SQLException e) {
										getMeS().addMsg(mSg);
										x.setText(new String ("SRV :> CHECK TABLE Person Exist :> NG..."));
										System.out.println("problemi con controllo tabella person");
										e.printStackTrace();
									}
										System.out.println("SYS AL :> srv ritorna "+x.getText());										
									return x;								
									//break;								
									
								default:
									break;
								} 							
								//******************************************************************************	
							} catch (InterruptedException e) {
								System.out.println("RealServer :> Problemi Accodamento CMD AL");
								e.printStackTrace();
								x.setText("RealServer :> Problemi Accodamento CMD AL");	
							}			
							break;
				
					case Book:			//BL	//-->[GpG [BL]] ---->[DB]
										//System.out.println("RealServer :> Rx Book");
							try {							
								System.out.println("RealServer :> Accodo M [ BL ]");
								System.out.println("RealServer :> BL in attesa prima... "+Req.getBL().getWr());
								Req.getBL().put(M);
								System.out.println("RealServer :> BL in attesa dopo... "+Req.getBL().getWr());	
								System.out.println("RealServer :> GO : "+Go);	
								//******************************************************************************
								while (!Go){
									try {
										Thread.sleep(10);
									} catch (Exception e) 
									{}
									System.out.println("REAL SERVER B-L:> go "+Go);						
								}	//Attesa del turno...
								//******************************************************************************
								System.out.println("REAL SERVER B-L:> fine attesa \nREAL SERVER :> Gestisco RICHIESTA :> Librarian, target BOOK ");					
									
									switch ( M.getMsg().getCommand()){
									//------------------------------------------------------------------------------			
									case tableExistBook:		
										System.out.println("REAL SERVER :> fine attesa \nREAL SERVER :> Gestisco RICHIESTA :> TableExistBook");
										try {
											
											ChkDBandTab.tableExistBook();
											
											getMeS().addMsg(mSg);
											x.setText(new String ("SRV :> CHECK TABLE book Exist :> OK"));
										} 
										catch (SQLException e) {
											getMeS().addMsg(mSg);
											x.setText(new String ("SRV :> CHECK TABLE book Exist :> NG..."));
											System.out.println("problemi con controllo tabella Book");
											e.printStackTrace();
										}
										break;	
									
									case BookADD:											
										System.out.println("REAL SERVER :> \nREAL SERVER :> Gestisco RICHIESTA :> Book Execute Query Add book");					
										try {					
											MQ_Insert.insertBooks(M.getMsg().getSQLQuery());							
											getMeS().addMsg(mSg);
											x.setText(new String ("SRV :> book add :> OK"));	
										
										} catch (SQLException e) {	
											System.out.println("problemi con query book Add");
											e.printStackTrace();														
											getMeS().addMsg(mSg);
											x.setText(new String ("SRV :> book add :> NG"));
										}
										break;
									
									case BookDELETE:										
										System.out.println("REAL SERVER :> \nREAL SERVER :> Gestisco RICHIESTA :> Book Execute Query Delete book");					
										try {				
											MQ_Delete.deleteRowBooks(M.getMsg().getSQLQuery());
											getMeS().addMsg(mSg);
											x.setText(new String ("SRV :> book del :> OK"));										
										} catch (SQLException e) {	
											System.out.println("problemi con query book delete");
											e.printStackTrace();														
											getMeS().addMsg(mSg);
											x.setText(new String ("SRV :> book del :> NG"));
										}
										break;		
									}
	
								System.out.println("SYS BL :> srv ritorna "+x.getText());
								return x;
								//******************************************************************************
												
							} catch (InterruptedException e) {
								System.out.println("RealServer :> Problemi Accodamento CMD BL");
								e.printStackTrace();
							}	
							break;				
					
							
					//Loans / Prestiti		
					case Prenotation:	//PL	//-->[GpG [PL]] ---->[DB]
										//System.out.println("RealServer :> Rx Prenotation");
							try {							
								System.out.println("RealServer :> Accodo M [ PL ]");
								System.out.println("RealServer :> PL in attesa prima... "+Req.getPL().getWr());
								Req.getPL().put(M);
								System.out.println("RealServer :> BL in attesa dopo... "+Req.getPL().getWr());	
								System.out.println("RealServer :> GO : "+Go);	
								//******************************************************************************
								while (!Go){
									try {
										Thread.sleep(10);
									} catch (Exception e) 
									{}
									System.out.println("REAL SERVER :> go "+Go);						
								}	//Attesa del turno...
								//******************************************************************************
								switch ( M.getMsg().getCommand()){
											
									case tableExistLoans:		
										System.out.println("REAL SERVER :> fine attesa \nREAL SERVER :> Gestisco RICHIESTA :> tableExistPrenotation ");					
										try {
											
											ChkDBandTab.tableExistLoans();
													
											getMeS().addMsg(mSg);
											x.setText(new String ("SRV :> CHECK TABLE Loans Exist :> OK"));	
										} catch (SQLException e) {
											getMeS().addMsg(mSg);
											x.setText(new String ("SRV :> CHECK TABLE Loans Exist :> NG..."));
											System.out.println("problemi con controllo tabella Loans ");
											e.printStackTrace();
										}
										System.out.println("SYS BL :> srv ritorna "+x.getText());
										return x;
										//break;
									
										
									case LoanDELETE:		
										System.out.println("REAL SERVER :> fine attesa \nREAL SERVER :> Gestisco RICHIESTA :> LoanDELETE ");					
										try {
											System.out.println("PASSATO AL SERVER ID UTENTE:"+M.getMsg().getSelectedIdUser());
											System.out.println("PASSATO AL SERVER ID BOOK:"+M.getMsg().getSelectedIdBook());
											
											//ChkDBandTab.tableExistLoans();
											M.getMsg().getSelectedIdBook();
											M.getMsg().getSelectedIdUser();
											
											List<String>r=new ArrayList <String>();
											
											r.add(String.valueOf( M.getMsg().getSelectedIdBook()));
											r.add(String.valueOf( M.getMsg().getSelectedIdUser()));
											
											String q = MQ_Delete.deleteRowLoansGetQuery(r);
											System.out.println("server ottengo query loans delete : "+ q);
											MQ_Delete.deleteRowLoans(q);
											getMeS().addMsg(mSg);
											
											x.setText(new String ("SRV :> Loans DELETE :> OK"));	
										} catch (SQLException e) {
											getMeS().addMsg(mSg);
											
											x.setText(new String ("SRV :> Loans DELETE :> NG"));
											System.out.println("problemi con controllo tabella Loans ");
											e.printStackTrace();
										}
										System.out.println("SYS BL :> srv ritorna "+x.getText());
										return x;
										//break;										
									
									case LoanRetired://aggiorna campo flag ritirato
										
										//ChkDBandTab.tableExistLoans();
										M.getMsg().getSelectedIdBook();
										M.getMsg().getSelectedIdUser();
										System.out.println("99");
										
										
										try {
											//query di aggiornamento campo ritirato	
											
										int aggiornate = MQ_Update.updateLoansRetired(M.getMsg().getSelectedIdUser(), M.getMsg().getSelectedIdBook());
											
											x.setConteggio(aggiornate);
											x.setText(new String ("SRV :> Loans RETIRED :> OK"));	
										} catch (Exception e) {
											
											x.setConteggio(0);
											x.setText(new String ("SRV :> Loans RETIRED:> NG"));	
										}
										
										
										
										return x;
									case LoanReturn://procedura loan return...
										
										System.err.println("SRV> Arrivato al server loan return...");
										
										try {
										
										String DataRiconsegna=M.getMsg().getSelectedDataLoanReturn();
										
										System.err.println("data di riconsegna ricevuta :"+DataRiconsegna);
										
										
										
										int iduserNext=0; 
										
										boolean bene=false;
										List <String> r = new ArrayList<String>(2);	
										
										Calendar calendar = new GregorianCalendar();
										Date datacorrente = calendar.getTime();
										Date dataultimocontrollo=calendar.getTime(); 
										
										
										
									//*** cancello dati dalla tabella prenotazioni	
												r.add(String.valueOf( M.getMsg().getSelectedIdBook()));
												r.add(String.valueOf( M.getMsg().getSelectedIdUser()));	
												int idbook=M.getMsg().getSelectedIdBook();
													System.err.println("idbook :"+r.get(0));
													System.err.println("iduser :"+r.get(1));
												String q = MQ_Delete.deleteRowBookingGetQuery(r);
													System.err.println("query ottenuta: "+q);
													System.err.println("arrivato al server loan return...1");
												MQ_Delete.deleteRowBooking(q);
													System.err.println("arrivato al server loan return...2");		
										
									//*** aggiorno Prestito [ rientrato _> true / data fine> now ]
												r.add(String.valueOf( M.getMsg().getSelectedIdBook()));		//0	book
												r.add(String.valueOf( M.getMsg().getSelectedIdUser()));		//1 user										
												
												//in test:
												//java.sql.Date dataS = new java.sql.Date(Calendar.getInstance().getTime().getTime());
												String dataS = DataRiconsegna;
												
												
												
												
												String q2 = MQ_Update.updateLoansReturnedGetQuery(	Integer.valueOf(r.get(0)),
																									Integer.valueOf(r.get(1)),
																									dataS);
												System.err.println("q ritornata : "+q2);
												MQ_Update.updateLoansReturned(q2);													
													System.err.println("arrivato al server loan return...3");
												
									//*** ricavo primo della lista prenotazioni	[id utente]								
												String[] primaprenotazione = MQ_Read.ResearchBookingFirst(M.getMsg().getSelectedIdBook());
													System.out.println("primo della lista: "+primaprenotazione[0]);
													System.err.println("arrivato al server loan return...4");
												
													if ( primaprenotazione[0]==null || primaprenotazione[0].equals("Nessun Dato")) {
														
															System.out.println("NESSUNO IN ATTESA");
															iduserNext=0;		
													}else 	{iduserNext=Integer.valueOf(primaprenotazione[0]);
													}
												
										if (iduserNext==0) {	//SE CODA PRENOTAZIONE VUOTA:
											System.err.println("arrivato al server loan return... idusernext == 0");
											//*** aggiorno STATO del Libro [ Libero ]								
												MQ_Update.updateBookFree(M.getMsg().getSelectedIdBook());
											
										}else {					//SE CODA PRENOTAZIONE NON VUOTA:	
											
											System.out.println("il libro "+idbook+" verra riassegnato a : "+iduserNext);
											System.err.println("arrivato al server loan return... idusernext != 0");
											//creo loans con il primo della lista...
									
											
											if (!M.getMsg().getDateOfRequest().equals(null)) {
												dataultimocontrollo=M.getMsg().getDateOfRequest();
											}
											
										
											
											//inserisce prossimo in lista prenotazioni come in prestito 
											String qINS = MQ_Insert.insertLoansGetQuery(idbook, iduserNext,datacorrente);
											MQ_Insert.insertLoans(qINS);
											
											//invio email di avviso
												String [] de = MQ_Read.sendEmailLoans();
												System.err.println("arrivato al server loan return... send email...");
											
											String [] userdata = MQ_Read.readSettingTable();
											
											System.out.println("ottengo userdata[0]: ["+userdata[0]+"]");
											System.out.println("ottengo userdata[1]: ["+userdata[1]+"]");
											System.out.println("ottengo userdata[2]: ["+userdata[2]+"]");
											System.out.println("ottengo userdata[3]: ["+userdata[3]+"]");
											System.out.println("ottengo userdata[4]: ["+userdata[4]+"]");
											System.out.println("ottengo userdata[5]: ["+userdata[5]+"]");
											System.out.println("ottengo userdata[6]: ["+userdata[6]+"]");
											
											
											
											String UN=userdata[4];
											String PW=userdata[5];											
											EmailSender.send_email_books_loans(
																			de[0],//codice
																			
																			de[2],//nome
																			de[3],//cognome
																			de[4],//email
																			de[5],//nome autore
																			de[6],//cognome autore
																			de[7],//titolo
																			java.sql.Date.valueOf(de[8]),//data inizio
																			//java.sql.Date.valueOf(de[9]),//data fine	
																			UN,
																			PW													
													);
												
											}

											x.setText(new String ("SRV :> Loans RETURNED :> OK" ));
											return x;
										
										}catch (Exception e) {
											
										 e.printStackTrace();
											
											
											
											x.setText(new String ("SRV :> Loans RETURNED :> NG" ));
											return x;
										
											
											
										}
										
										//break;
										
									case LoanASK:	
										
										int idut 		= M.getMsg().getSelectedIdUser();
										int idbook 		= M.getMsg().getSelectedIdBook();		
										
										System.err.println("server estrapola... idbook:"+idbook);
										System.out.println("REAL SERVER :> fine attesa \nREAL SERVER :> Gestisco RICHIESTA :> Loans ASK ");					
										
										try {
											
											Boolean CK = true;	//esito di tutti i controlli
											
												
											  setChkinprogress1(true);			//
											  setChkinprogress2(true);			//
											  setChkinprogress3(true);			//
											  setChkinprogress4(true);			//
											//setChkinprogress5(true);
											//setChkinprogress6(true);
											
											System.out.println("server estrapola... idbook:"+idbook);

											ck1(idut, idbook);		// limite massimo prestiti di 	2 volte per lo stesso LIBRO
											ck2(idut, idbook);		// limite massimo prestiti di 	5 volte per lo stesso UTENTE
											ck3(idut, idbook);		// limite massimo scaduti 		0 per lo stesso UTENTE
											ck4(idut, idbook);		// limite massimo prenotazioni  10 per lo stesso UTENTE
											
											boolean Timeout=true;
											int c=0;
											
											while (		isChkinprogress1()
													&& 	isChkinprogress2()
													&& 	isChkinprogress3()
													&& 	isChkinprogress4()
//													&& 	isChkinprogress5()
//													&& 	isChkinprogress6()
													
													&&	Timeout
													) 
											{//while //attesa termine di tutti i controlli...		
												try {
													Thread.sleep(10);
													c+=10;													
													if (c==2000) {
														Timeout=false;//scaduto
														System.out.println("tempo di attesa scaduto, errore di comunicazione...");
														CK=false;
														x.setText(new String ("SRV :> Loans ASK :> NG" ));	
													}	
												} catch (InterruptedException e1) {
													CK=false;
													x.setText(new String ("SRV :> Loans ASK :> NG" ));	
													e1.printStackTrace();
												}			
											}//while
																			String	rok = "SRV :> Loans ASK :> OK - PRESTITO ACCORDATO ";												
													if (			chkResult1.equals(	rok)
																&& 	chkResult2.equals(	rok)
																&& 	chkResult3.equals(	rok)
																&& 	chkResult4.equals(	rok)
															//	&& 	chkResult5.equals(	rok)
															//	&& 	chkResult6.equals(	rok)
														) 	
													{																									
														
														Calendar calendar = new GregorianCalendar();
														java.util.Date datacorrente = 	calendar.getTime();  
														String qL =  MQ_Insert.insertLoansGetQuery(idbook, idut,datacorrente);
														String qB =  MQ_Insert.insertBookingGetQuery(idbook, idut, 10, datacorrente);
														x.setText(new String ("SRV :> Loans ASK :> OK" ));//System.out.println(" TUTTI I CRITERI RISPETTaTI, LIBRO IN PRESTITO !!!! ");																					
														
														boolean presenteinprestito 	= MQ_Read.checkLoansPresente(idut,idbook);
														boolean presenteincoda 		= MQ_Read.checkBookingPresente(idut, idbook);
														
														
																if (presenteincoda || presenteinprestito) {		//		presente in coda prenotazioni
																																	
																	
																	String[] rokmsg =new String[6];																	
																	if (presenteincoda) {
																		x.setText(new String ("SRV :> Loans ASK :> OK , PRESTITO gia RICHIESTO" ));	
																		rokmsg[0]="Utente già presente in lista prenotazioni per il libro [id]:"+idbook;
																	}
																	if (presenteinprestito) {
																		x.setText(new String ("SRV :> Loans ASK :> OK , PRESTITO gia CONCESSO" ));	
																		rokmsg[0]="Utente già presente in lista prestiti con il libro [id]:"+idbook;
																	}
																							

																	x.setRowLoans(rokmsg);	
																}else {						//	NON	presente in coda prenotazioni
																	String[] rokmsg =new String[6];
																		//q conta utenti in coda per il libro
																		int coda = MQ_Read.checkLoansIdBookWait(idbook);
																		if (coda == 0) {																												
																		//q inserisci prestito	
																			MQ_Insert.insertLoans(qL);
																		//q inserisci prenotazione	
																			MQ_Insert.insertBooking(qB);		
																			String msg = "inserito prestito";
																			rokmsg =new String[2]; 
																			rokmsg[0]=msg;
																			x.setRowLoans(rokmsg);	
																		//q aggiorna campo LIBERO in OCCUPATO
																			MQ_Update.updateLoansStato(String.valueOf(idbook),"In prestito");
																		}else {		System.out.println("ci sono in coda "+coda+" utenti");	
																			String qw = MQ_Insert.insertLoansCodaGetQuery(idbook, idut, 10, datacorrente);
																		//q inserisci prenotazione	
																			MQ_Insert.insertLoansCoda(qw);
																		//q INSERIMENTO NUOVA PRENOTAZIONE											
																			String msg = "inserito in coda prenotazine";
																			rokmsg =new String[2]; 														
																			rokmsg[0]=msg;
																			coda++;
																			rokmsg[1]=String.valueOf(coda);//PRIORITA														
																			x.setRowLoans(rokmsg);								
																		//q aggiorna campo STATO libro in PRENOTATO...
																			MQ_Update.updateLoansStato(String.valueOf(idbook),"Prenotato, "+coda+" utenti in coda" );	
																		}
																}
													}else {													
															x.setText(new String ("SRV :> Loans ASK :> OK , PRESTITO NON CONSENTITO" ));
															String[] rokmsg =new String[6]; 			
															rokmsg[0]="NN";
															rokmsg[1]=chkResult1;
															rokmsg[2]=chkResult2;
															rokmsg[3]=chkResult3;
															rokmsg[4]=chkResult4;
															rokmsg[5]="NN";
															x.setRowLoans(rokmsg);															
												}
											
											getMeS().addMsg(mSg);
										} catch (Exception e) {
											getMeS().addMsg(mSg);
											x.setText(new String ("SRV :> Loans ASK :> NG"));	System.out.println("problemi con  SRV :> Loans ASK :> NG");
											e.printStackTrace();
										}
										System.out.println("SYS BL :> srv ritorna "+x.getText());
										return x;
										//break;									
									default:
										break;
								}
											
							} catch (InterruptedException e) {
								System.out.println("RealServer :> Problemi Accodamento CMD PL");
								e.printStackTrace();
							}	
							break;


				default:
				//
					// errore
					System.out.println("comando indefinito");	
				//	
				break;					
				}
			break;	

			case Reader :						//-->[GpG [?R]] ---->[DB] 
				System.out.println("RealServer :> Rx Reader");			
				
				switch ( M.getMsg().getCommand().getTarget()){
				//------------------------------------------------------------------------------			

				
				case Setting:	
							
							//SL	//-->[GpG [SR]] ---->[DB]	
							//System.out.println("RealServer :> Rx Account");
							try {					
									System.out.println("RealServer :> Accodo M [ SRL ]");
									System.out.println("RealServer :> AL in attesa prima... "+Req.getSR().getWr());
									Req.getSR().put(M);
									//******************************************************************************
									while (!Go){
										try {
											System.out.println("REAL SERVER L-S:> in attesa GO da Guardian");
											Thread.sleep(10);
										} catch (Exception e) 
										{}
											System.out.println("REAL SERVER L-S:> go "+Go);						
									}	//Attesa del turno...									
									//******************************************************************************							
									switch (M.getMsg().getCommand()) {
									
									case tableExistSetting:
										
										System.out.println("REAL SERVER :> fine attesa \nREAL SERVER :> Gestisco RICHIESTA :> tableExistSetting ");					
									try {
										CheckDBSetting.TableExistSetting();
										getMeS().addMsg(mSg);
										x.setText(new String ("SRV :> CHECK TABLE Setting Exist :> OK"));	
									} catch (SQLException e) {
										getMeS().addMsg(mSg);
										x.setText(new String ("SRV :> CHECK TABLE Setting Exist :> NG..."));
										System.out.println("problemi con controllo tabella Setting");
										e.printStackTrace();
									}
										System.out.println("SYS AL :> srv ritorna "+x.getText());										
									return x;								
									//break;		
									default:
										break;							
									}		
							}catch (Exception e) {						
							}
						break;

				
				
				
				
				
				
				case Account:				//-->[GpG [AR]] ---->[DB]		
						try {					
							System.out.println("RealServer :> Accodo M [ AR ]");
							System.out.println("RealServer :> AR in attesa prima... "+Req.getAR().getWr());
							Req.getAR().put(M);	
							//******************************************************************************
							while (!Go){
								try {	System.out.println("REAL SERVER R-A:> in attesa GO da Guardian");
										Thread.sleep(10);
								} catch (Exception e){}
										System.out.println("REAL SERVER R-A:> go "+Go);						
							}	//Attesa del turno...									
							//******************************************************************************							
					switch (M.getMsg().getCommand()) {
												
						case UserRegistration:
							System.out.println("REAL SERVER :> fine attesa \nREAL SERVER :> Gestisco RICHIESTA :> userRegistration ");					
							try {									
								MQ_Insert.insertUtente(M.getMsg().getSQLQuery());														
								getMeS().addMsg(mSg);
								x.setText(new String ("SRV :> user Registration :> OK"));	
							} catch (SQLException e) {
								getMeS().addMsg(mSg);
								x.setText(new String ("SRV :> user Registration :> NG..."));
								System.out.println("problemi con controllo tabella person");
								e.printStackTrace();
							}
							System.out.println("SYS AL :> srv ritorna "+x.getText());										
							return x;								
							//break;	
						case UserPasswordRecovery:
							System.out.println("REAL SERVER :> fine attesa \nREAL SERVER :> Gestisco RICHIESTA :> UserPasswordRecovery ");					
							try {										
								//cambia query
								MQ_Update.updateNewPassForgot(M.getMsg().getSQLQuery());
								//invia mail
								EmailSender.send_uninsubria_recoverypassword(M.getMsg().getSQLQuery2(), 
																			null, 
																			M.getMsg().getPw());
								getMeS().addMsg(mSg);
								x.setText(new String ("SRV :> UPRecovery :> OK"));											
							} catch (Exception e) {
								getMeS().addMsg(mSg);
								x.setText(new String ("SRV :> UPRecovery :> NG"));
								System.out.println("problemi con password recovery");
								e.printStackTrace();
							}
							System.out.println("SYS AL :> srv ritorna "+x.getText());										
							return x;								
							//break;	
							
						case UserPasswordRemovetemp:
							int id 			= 	M.getMsg().getIdut();
							String passT 	= 	M.getMsg().getPw();
							
							System.out.println("REAL SERVER :> fine attesa \nREAL SERVER :> Gestisco RICHIESTA :> User Password REMOVE temp ");					
							try {										
								//cambia query
								MQ_Delete.deletePassTemp(id, passT);
								//cancella pw temp
								getMeS().addMsg(mSg);
								x.setText(new String ("SRV :> DEL pw TEMP :> OK"));											
							} catch (Exception e) {
								getMeS().addMsg(mSg);
								x.setText(new String ("SRV :> DEL pw TEMP :> NG"));
								System.out.println("problemi con password TEMP ");
								e.printStackTrace();
							}
							System.out.println("SYS AL :> srv ritorna "+x.getText());										
							return x;								
							
							
							
						case UserUPDATE:	//SRV UP
								System.out.println("REAL SERVER :> fine attesa \nREAL SERVER :> Gestisco RICHIESTA :> USER UPDATE ");					
							try {
								System.out.println("REAL SERVER :> Query passata dal client :"+M.getMsg().getSQLQuery().toString());
								System.out.println("REAL SERVER :> email user passata dal client :"+M.getMsg().getSQLQuery2().toString());
								System.out.println("REAL SERVER :> id user passata dal client :"+M.getMsg().getIdut());										
								MQ_Update.updateModUserIdbyQuery(M.getMsg().getSQLQuery());		
								getMeS().addMsg(mSg);
								x.setText(new String ("SRV :> UP :> OK"));
								x.setIdUser(M.getMsg().getIdut());
								x.setUserEmail(M.getMsg().getSQLQuery2());										
							} catch (Exception e) {
								getMeS().addMsg(mSg);
								x.setText(new String ("SRV :> UP :> NG"));
								System.out.println("problemi con controllo UPDATE UTENTE");
								e.printStackTrace();
							}
							System.out.println("SYS AL :> srv ritorna "+x.getText());										
							return x;								
							//break;									
						case UserDELETE:	
								System.out.println("REAL SERVER :> \nREAL SERVER :> Gestisco RICHIESTA :> USER DELETE ");					
								System.out.println("REAL SERVER :> id user passata dal client :"+M.getMsg().getIdut());
							try {														
								MQ_Delete.deleteRowPerson(M.getMsg().getIdut());										
								getMeS().addMsg(mSg);
								x.setText(new String ("SRV :> USER del :> OK"));										
							} catch (SQLException e) {	
								System.out.println("problemi con query USER del");
								e.printStackTrace();														
								getMeS().addMsg(mSg);
								x.setText(new String ("SRV :> USER del :> NG"));
							}
							System.out.println("SYS AL :> srv ritorna "+x.getText());										
							return x;	
							//break;			
							
						case UserREADloginFIRST:
								System.out.println("REAL SERVER :> \nREAL SERVER :> Gestisco RICHIESTA :> User Read Login first time");					
							try {				
								String email 	= M.getMsg().getSQLQuery();
								String pass 	= M.getMsg().getSQLQuery2();
								//Estrapola dati utente										
								String []userdata	= MQ_Read.selectAdminLogInFIRST(email, pass);
								System.err.println("email 	: "+ userdata[0]);
								System.err.println("pwtemp 	: "+ userdata[1]);
								System.err.println("idut 	: "+ userdata[2]);
								System.err.println("tipo 	: "+ userdata[3]);
								x.setUsertype(userdata[3]);
								x.setIdUser(Integer.valueOf(userdata[2]));
								
								String rf 		= Check.checkAdminLogInFIRST(email, pass);
								System.out.println(" checkadinloginfirst ritorna"+rf);										
								
								if(	rf.equals("I Campi Non Possono Essere Vuoti")
									||	
									rf.equals("Nessun Dato")
									||
									rf.equals("L'Email Non Esiste")) 
								{
									System.out.println("riscontrato campi vuoti "); 	
									//ritorna
	
									x.setText("SRV :> selected user login check FIRST:> "+rf);
									return x;
								}
								// RECUPERA VALORE NUMERO TENTATIVI										
								//*****************************************
								String[] user = MQ_Read.UserLoginTryCounter(email);
								//*****************************************										
								String tent = user[1];
								int tentativi = Integer.parseInt(tent);
									System.out.println("REAL SERVER L-A:> recuperati numero tentativi effettuati: "+tentativi);
								if (tentativi == 5) {
									String idUser = user[2];
									System.out.println("REAL SERVER L-A:> PROCEDURA CANCELLAZIONE UTENTE id "+idUser );
									// PROCEDURA CANCELLAZIONE UTENTE
									List<String> rowData = new ArrayList<String>();
									rowData.add(0, idUser);
									
									MQ_Delete.deleteRowPerson(rowData);
									
									x.setText(new String ("SRV :> selected user login check FIRST:> PROCEDURA CANCELLAZIONE UTENTE" ));										
								}else {
									System.out.println("REAL SERVER L-A:> PROCEDURA INCREMENTO NUMERO TENTATIVI ");
									// INCREMENTA CAMPO TENTATIVI
									tentativi++;
									System.out.println("REAL SERVER L-A:> recuperati numero tentativi aumentati: "+tentativi);	
									
									// AGGIORNA campo numero tentativi
									MQ_Update.updateLoginTry(email, tentativi);
									
									System.out.println("REAL SERVER L-A:> PROCEDURA dopo update numero tentativi ");
									//controllo esito operazione
									//tent= String.valueOf(tentativi);
									System.out.println("REAL SERVER L-A:> recuperati numero tentativi AGGIORNATO: "+tentativi);											
									System.out.println("email : "+email);
									System.out.println("pass  : "+pass);
									
									
									MQ_Delete.deletePassTempEP(email, pass);

	
									
									x.setIdUser(Integer.valueOf(userdata[2]));
									
									x.setUserEmail(email);
									x.setText(new String ("SRV :> selected user login check FIRST:> "+rf));											
								}
	
							} catch (SQLException e) {	
								System.out.println("problemi con \"SRV :> selected user login check FIRST");
								e.printStackTrace();				
								
								getMeS().addMsg(mSg);
								x.setText(new String ("SRV :> selected user login check FIRST:> NG"));
							}
								System.out.println("SYS AL :> srv ritorna "+x.getText());										
							return x;		
						
						case UserREADlogin://----> [DB] 
							System.out.println("REAL SERVER :> \nREAL SERVER :> Gestisco RICHIESTA :> User Read Login");					
							try {				
								String email 	= M.getMsg().getSQLQuery();
								String pass 	= M.getMsg().getSQLQuery2();							
								String []r 		= Check.checkAdminLogIn(email, pass);						
								System.out.println("ottenuto dalla query id utente"+r[1]);												
								if (r[1]==null) {
									x.setIdUser(0);
								}else {
									x.setIdUser(Integer.valueOf(r[1]));
								}
								
								x.setRowUser(r);
								x.setUserEmail(email);
								x.setText(new String ("SRV :> selected user login check:> "+r[0]));						
							} catch (SQLException e) {	
								System.out.println("problemi con \"SRV :> selected user login check ");
								e.printStackTrace();										
								getMeS().addMsg(mSg);
								x.setText(new String ("SRV :> selected user login check:> NG"));
							}
							System.out.println("SYS AL :> srv ritorna "+x.getText());										
							return x;	

							
						case tableExistPerson:								
								System.out.println("REAL SERVER :> fine attesa \nREAL SERVER :> Gestisco RICHIESTA :> tableExistPerson ");					
							try {
								ChkDBandTab.tableExistPerson();
								getMeS().addMsg(mSg);
								x.setText(new String ("SRV :> CHECK TABLE Person Exist :> OK"));	
							} catch (SQLException e) {
								getMeS().addMsg(mSg);
								x.setText(new String ("SRV :> CHECK TABLE Person Exist :> NG..."));
								System.out.println("problemi con controllo tabella person");
								e.printStackTrace();
							}
								System.out.println("SYS AL :> srv ritorna "+x.getText());										
							return x;								
							//break;								
					
							
							
							
							
							
						default:
							break;
						} 							
								//******************************************************************************	
						} catch (InterruptedException e) {
							System.out.println("RealServer :> Problemi Accodamento CMD AL");
							e.printStackTrace();
							x.setText("RealServer :> Problemi Accodamento CMD AL");	
						}			
						break;
	

				case Booking:		//BKL	//-->[GpG [BKR]] ---->[DB]	
					//System.out.println("RealServer :> Rx Account");
					try {					
							System.out.println("RealServer :> Accodo M [ BKR ]");
							System.out.println("RealServer :> BKR in attesa prima... "+Req.getBKR().getWr());		
							Req.getBKR().put(M);
							//******************************************************************************
							while (!Go){
								try {
									System.out.println("REAL SERVER R-BK:> in attesa GO da Guardian");
									Thread.sleep(10);
								} catch (Exception e) 
								{}
									System.out.println("REAL SERVER R-BK:> go "+Go);						
							}	//Attesa del turno...									
							//******************************************************************************							
							switch (M.getMsg().getCommand()) {							
							case tableExistBooking:							
								System.out.println("REAL SERVER :> fine attesa \nREAL SERVER :> Gestisco RICHIESTA :> tableExistBooking ");					
								try {
									ChkDBandTab.tableExistBooking();
									getMeS().addMsg(mSg);
									x.setText(new String ("SRV :> CHECK TABLE Booking Exist :> OK"));	
								} catch (SQLException e) {
									getMeS().addMsg(mSg);
									x.setText(new String ("SRV :> CHECK TABLE Booking Exist :> NG..."));
									System.out.println("problemi con controllo tabella Booking");
									e.printStackTrace();
								}
									System.out.println("SYS AL :> srv ritorna "+x.getText());										
								return x;								
								//break;							
							case BookingListREMOVE:	
								System.out.println("REAL SERVER :> \nREAL SERVER :> Gestisco RICHIESTA :> Booking LIST DELETE ");					
								System.out.println("REAL SERVER :> id user passata dal client :"+M.getMsg().getSelectedIdUser());
								System.out.println("REAL SERVER :> id book passata dal client :"+M.getMsg().getSelectedIdBook());
								try {									
									int[]r=new int[2];
									r[0]=M.getMsg().getSelectedIdBook() ;
									r[1]=M.getMsg().getSelectedIdUser();											
									
									MQ_Delete.deleteRowBooking(r);		
									
									
									getMeS().addMsg(mSg);
									x.setText(new String ("SRV :> Del-Booking:> OK"));										
								} catch (SQLException e) {	
									System.out.println("problemi con query Del-Booking");
									e.printStackTrace();														
									getMeS().addMsg(mSg);
									x.setText(new String ("SRV :> Del-Booking:> NG"));
								}
								
								
								System.out.println("SYS AL :> srv ritorna "+x.getText());										
								return x;	
								//break;							
							default:
								break;							
							}		
					}catch (Exception e) {						
					}
				break;

						
						
							//------------------------------------------------------------------------------
					case Book:					//-->[GpG [BR]] ---->[DB]		
						try {					
							System.out.println("RealServer :> Accodo M [ BR ]");
							System.out.println("RealServer :> BR in attesa prima... "+Req.getBR().getWr());
							Req.getBR().put(M);	
							//******************************************************************************
							while (!Go){
								try {	System.out.println("REAL SERVER R-B:> in attesa GO da Guardian");
										Thread.sleep(10);
								} catch (Exception e){}
										System.out.println("REAL SERVER R-B:> go "+Go);						
							}	//Attesa del turno...									
							//******************************************************************************
							switch (M.getMsg().getCommand()) {
							
							case BookExecuteQuery://ES
							
							
							case tableExistBook:		
								System.out.println("REAL SERVER :> fine attesa \nREAL SERVER :> Gestisco RICHIESTA :> TableExistBook");
								try {
									
									ChkDBandTab.tableExistBook();
									
									getMeS().addMsg(mSg);
									x.setText(new String ("SRV :> CHECK TABLE book Exist :> OK"));
								} 
								catch (SQLException e) {
									getMeS().addMsg(mSg);
									x.setText(new String ("SRV :> CHECK TABLE book Exist :> NG..."));
									System.out.println("problemi con controllo tabella Book");
									e.printStackTrace();
								}
								break;	
							
							case BookADD:											
								System.out.println("REAL SERVER :> \nREAL SERVER :> Gestisco RICHIESTA :> Book Execute Query Add book");					
								try {					
									MQ_Insert.insertBooks(M.getMsg().getSQLQuery());							
									getMeS().addMsg(mSg);
									x.setText(new String ("SRV :> book add :> OK"));	
								
								} catch (SQLException e) {	
									System.out.println("problemi con query book Add");
									e.printStackTrace();														
									getMeS().addMsg(mSg);
									x.setText(new String ("SRV :> book add :> NG"));
								}
								break;
							
							case BookDELETE:										
								System.out.println("REAL SERVER :> \nREAL SERVER :> Gestisco RICHIESTA :> Book Execute Query Delete book");					
								try {				
									MQ_Delete.deleteRowBooks(M.getMsg().getSQLQuery());
									getMeS().addMsg(mSg);
									x.setText(new String ("SRV :> book del :> OK"));										
								} catch (SQLException e) {	
									System.out.println("problemi con query book delete");
									e.printStackTrace();														
									getMeS().addMsg(mSg);
									x.setText(new String ("SRV :> book del :> NG"));
								}
								break;		
							default:
								break;
							}							
							System.out.println("SYS BL :> srv ritorna "+x.getText());
							return x;
							//******************************************************************************
						} catch (InterruptedException e) {	System.out.println("RealServer :> Problemi Accodamento CMD BR");
							e.printStackTrace();
							x.setText("RealServer :> Problemi Accodamento CMD BR");	
						}
						break;
		

						
						//------------------------------------------------------------------------------				
					case Prenotation:			//-->[GpG [PR]] ---->[DB]		
						try {					
							System.out.println("RealServer :> Accodo M [ PR ]");
							System.out.println("RealServer :> AR in attesa prima... "+Req.getPR().getWr());
							Req.getPR().put(M);	
							//******************************************************************************
							while (!Go){
								try {	System.out.println("REAL SERVER R-P:> in attesa GO da Guardian");
										Thread.sleep(10);
								} catch (Exception e){}
										System.out.println("REAL SERVER R-P:> go "+Go);						
							}	//Attesa del turno...									
							//******************************************************************************
							switch ( M.getMsg().getCommand()){
												
										case tableExistLoans:		
											System.out.println("REAL SERVER :> fine attesa \nREAL SERVER :> Gestisco RICHIESTA :> tableExistPrenotation ");					
											try {
												
												ChkDBandTab.tableExistLoans();
														
												getMeS().addMsg(mSg);
												x.setText(new String ("SRV :> CHECK TABLE Loans Exist :> OK"));	
											} catch (SQLException e) {
												getMeS().addMsg(mSg);
												x.setText(new String ("SRV :> CHECK TABLE Loans Exist :> NG..."));
												System.out.println("problemi con controllo tabella Loans ");
												e.printStackTrace();
											}
											System.out.println("SYS BL :> srv ritorna "+x.getText());
											return x;
											//break;
										
											
										case LoanDELETE:		
											System.out.println("REAL SERVER :> fine attesa \nREAL SERVER :> Gestisco RICHIESTA :> LoanDELETE ");					
											try {
												System.out.println("PASSATO AL SERVER ID UTENTE:"+M.getMsg().getSelectedIdUser());
												System.out.println("PASSATO AL SERVER ID BOOK:"+M.getMsg().getSelectedIdBook());
												
												//ChkDBandTab.tableExistLoans();
												M.getMsg().getSelectedIdBook();
												M.getMsg().getSelectedIdUser();
												
												List<String>r=new ArrayList <String>();
												
												r.add(String.valueOf( M.getMsg().getSelectedIdBook()));
												r.add(String.valueOf( M.getMsg().getSelectedIdUser()));
												
												String q = MQ_Delete.deleteRowLoansGetQuery(r);
												System.out.println("server ottengo query loans delete : "+ q);
												MQ_Delete.deleteRowLoans(q);
												getMeS().addMsg(mSg);
												
												x.setText(new String ("SRV :> Loans DELETE :> OK"));	
											} catch (SQLException e) {
												getMeS().addMsg(mSg);
												
												x.setText(new String ("SRV :> Loans DELETE :> NG"));
												System.out.println("problemi con controllo tabella Loans ");
												e.printStackTrace();
											}
											System.out.println("SYS BL :> srv ritorna "+x.getText());
											return x;
											//break;										

											
										case LoanReturn://procedura loan return...
											
											System.err.println("SRV> Arrivato al server loan return...");
											
											try {
											
											String DataRiconsegna=M.getMsg().getSelectedDataLoanReturn();											
											System.err.println("data di riconsegna ricevuta :"+DataRiconsegna);
											int iduserNext=0;	
											boolean bene=false;
											List <String> r = new ArrayList<String>(2);	
											
											Calendar calendar = new GregorianCalendar();
											Date datacorrente = calendar.getTime();
											Date dataultimocontrollo=calendar.getTime(); 

										//*** cancello dati dalla tabella prenotazioni	
													r.add(String.valueOf( M.getMsg().getSelectedIdBook()));
													r.add(String.valueOf( M.getMsg().getSelectedIdUser()));	
													int idbook=M.getMsg().getSelectedIdBook();
														System.err.println("idbook :"+r.get(0));
														System.err.println("iduser :"+r.get(1));
													String q = MQ_Delete.deleteRowBookingGetQuery(r);
														System.err.println("query ottenuta: "+q);
														System.err.println("arrivato al server loan return...1");
													MQ_Delete.deleteRowBooking(q);
														System.err.println("arrivato al server loan return...2");		
											
										//*** aggiorno Prestito [ rientrato _> true / data fine> now ]
													r.add(String.valueOf( M.getMsg().getSelectedIdBook()));		//0	book
													r.add(String.valueOf( M.getMsg().getSelectedIdUser()));		//1 user										
													
													//in test:
													//java.sql.Date dataS = new java.sql.Date(Calendar.getInstance().getTime().getTime());
													String dataS = DataRiconsegna;
													String q2 = MQ_Update.updateLoansReturnedGetQuery(	Integer.valueOf(r.get(0)),
																										Integer.valueOf(r.get(1)),
																										dataS);
													System.err.println("q ritornata : "+q2);
													MQ_Update.updateLoansReturned(q2);													
														System.err.println("arrivato al server loan return...3");
													
										//*** ricavo primo della lista prenotazioni	[id utente]								
													String[] primaprenotazione = MQ_Read.ResearchBookingFirst(M.getMsg().getSelectedIdBook());
														System.out.println("primo della lista: "+primaprenotazione[0]);
														System.err.println("arrivato al server loan return...4");
													
														if ( primaprenotazione[0]==null || primaprenotazione[0].equals("Nessun Dato")) {
															
																System.out.println("NESSUNO IN ATTESA");
																iduserNext=0;		
														}else 	{iduserNext=Integer.valueOf(primaprenotazione[0]);
														}
													
											if (iduserNext==0) {	//SE CODA PRENOTAZIONE VUOTA:
												System.err.println("arrivato al server loan return... idusernext == 0");
												//*** aggiorno STATO del Libro [ Libero ]								
													MQ_Update.updateBookFree(M.getMsg().getSelectedIdBook());
												
											}else {					//SE CODA PRENOTAZIONE NON VUOTA:	
												
												System.out.println("il libro "+idbook+" verra riassegnato a : "+iduserNext);
												System.err.println("arrivato al server loan return... idusernext != 0");
	
												if (!M.getMsg().getDateOfRequest().equals(null)) {
													dataultimocontrollo=M.getMsg().getDateOfRequest();
												}
												
												//inserisce prossimo in lista prenotazioni come in prestito 
												String qINS = MQ_Insert.insertLoansGetQuery(idbook, iduserNext,datacorrente);
												MQ_Insert.insertLoans(qINS);
												
												//invio email di avviso
													String [] de = MQ_Read.sendEmailLoans();
													System.err.println("arrivato al server loan return... send email...");
												
												String [] userdata = MQ_Read.readSettingTable();
												
												System.out.println("ottengo userdata[0]: ["+userdata[0]+"]");
												System.out.println("ottengo userdata[1]: ["+userdata[1]+"]");
												System.out.println("ottengo userdata[2]: ["+userdata[2]+"]");
												System.out.println("ottengo userdata[3]: ["+userdata[3]+"]");
												System.out.println("ottengo userdata[4]: ["+userdata[4]+"]");
												System.out.println("ottengo userdata[5]: ["+userdata[5]+"]");
												System.out.println("ottengo userdata[6]: ["+userdata[6]+"]");
												
												
												
												String UN=userdata[4];
												String PW=userdata[5];											
												EmailSender.send_email_books_loans(
																				de[0],//codice
																				
																				de[2],//nome
																				de[3],//cognome
																				de[4],//email
																				de[5],//nome autore
																				de[6],//cognome autore
																				de[7],//titolo
																				java.sql.Date.valueOf(de[8]),//data inizio
																				//java.sql.Date.valueOf(de[9]),//data fine	
																				UN,
																				PW													
														);
													
												}

												x.setText(new String ("SRV :> Loans RETURNED :> OK" ));
												return x;
											
											}catch (Exception e) {
											
												
												
												
												x.setText(new String ("SRV :> Loans RETURNED :> NG" ));
												return x;
											
												
												
											}
											
											//break;
											
										case LoanASK:	
	//TODO copiare anche per reader ... far passare dal client 
											
											int idut 		= M.getMsg().getSelectedIdUser();
											int idbook 		= M.getMsg().getSelectedIdBook();		
											
											System.err.println("server estrapola... idbook:"+idbook);
											System.out.println("REAL SERVER :> fine attesa \nREAL SERVER :> Gestisco RICHIESTA :> Loans ASK ");					
											
											try {
												
												Boolean CK = true;	//esito di tutti i controlli
												
												//Controlli in concorrenza TEST OK	
												  setChkinprogress1(true);			//
												  setChkinprogress2(true);			//
												  setChkinprogress3(true);			//
												  setChkinprogress4(true);			//
												//setChkinprogress5(true);
												//setChkinprogress6(true);
												
												System.out.println("server estrapola... idbook:"+idbook);

												ck1(idut, idbook);		// limite massimo prestiti di 	2 volte per lo stesso LIBRO
												ck2(idut, idbook);		// limite massimo prestiti di 	5 volte per lo stesso UTENTE
												ck3(idut, idbook);		// limite massimo scaduti 		0 per lo stesso UTENTE
												ck4(idut, idbook);		// limite massimo prenotazioni  10 per lo stesso UTENTE
												
												boolean Timeout=true;
												int c=0;
												
												while (		isChkinprogress1()
														&& 	isChkinprogress2()
														&& 	isChkinprogress3()
														&& 	isChkinprogress4()
//														&& 	isChkinprogress5()
//														&& 	isChkinprogress6()
														
														&&	Timeout
														) 
												{//while //attesa termine di tutti i controlli...		
													try {
														Thread.sleep(10);
														c+=10;													
														if (c==2000) {
															Timeout=false;//scaduto
															System.out.println("tempo di attesa scaduto, errore di comunicazione...");
															CK=false;
															x.setText(new String ("SRV :> Loans ASK :> NG" ));	
														}	
													} catch (InterruptedException e1) {
														CK=false;
														x.setText(new String ("SRV :> Loans ASK :> NG" ));	
														e1.printStackTrace();
													}			
												}//while
																				String	rok = "SRV :> Loans ASK :> OK - PRESTITO ACCORDATO ";												
														if (			chkResult1.equals(	rok)
																	&& 	chkResult2.equals(	rok)
																	&& 	chkResult3.equals(	rok)
																	&& 	chkResult4.equals(	rok)
																//	&& 	chkResult5.equals(	rok)
																//	&& 	chkResult6.equals(	rok)
															) 	
														{//se tutti i check restituiscono esito positivo...																										
															
															Calendar calendar = new GregorianCalendar();
															java.util.Date datacorrente = 	calendar.getTime();  
															String qL =  MQ_Insert.insertLoansGetQuery(idbook, idut,datacorrente);
															String qB =  MQ_Insert.insertBookingGetQuery(idbook, idut, 10, datacorrente);
															x.setText(new String ("SRV :> Loans ASK :> OK" ));//System.out.println(" TUTTI I CRITERI RISPETTaTI, LIBRO IN PRESTITO !!!! ");																					

																	//q CHECK : prenotazione idut idbook ATTIVA
															
															boolean presenteinprestito 	= MQ_Read.checkLoansPresente(idut,idbook);
															boolean presenteincoda 		= MQ_Read.checkBookingPresente(idut, idbook);
															
															
															
															
																	if (presenteincoda || presenteinprestito) {		//		presente in coda prenotazioni
																																		
																		
																		String[] rokmsg =new String[6];																	
																		if (presenteincoda) {
																			x.setText(new String ("SRV :> Loans ASK :> OK , PRESTITO gia RICHIESTO" ));	
																			rokmsg[0]="Utente già presente in lista prenotazioni per il libro [id]:"+idbook;
																		}
																		if (presenteinprestito) {
																			x.setText(new String ("SRV :> Loans ASK :> OK , PRESTITO gia CONCESSO" ));	
																			rokmsg[0]="Utente già presente in lista prestiti con il libro [id]:"+idbook;
																		}
																								

																		x.setRowLoans(rokmsg);	
																	}else {						//	NON	presente in coda prenotazioni
																		String[] rokmsg =new String[6];
																			//q conta utenti in coda per il libro
																			int coda = MQ_Read.checkLoansIdBookWait(idbook);
																			if (coda == 0) {																												
																			//q inserisci prestito	
																				MQ_Insert.insertLoans(qL);
																			//q inserisci prenotazione	
																				MQ_Insert.insertBooking(qB);		
																				String msg = "inserito prestito";
																				rokmsg =new String[2]; 
																				rokmsg[0]=msg;
																				x.setRowLoans(rokmsg);	
																			//q aggiorna campo LIBERO in OCCUPATO
																				MQ_Update.updateLoansStato(String.valueOf(idbook),"In prestito");
																			}else {		System.out.println("ci sono in coda "+coda+" utenti");	
																				String qw = MQ_Insert.insertLoansCodaGetQuery(idbook, idut, 10, datacorrente);
																			//q inserisci prenotazione	
																				MQ_Insert.insertLoansCoda(qw);
																			//q INSERIMENTO NUOVA PRENOTAZIONE											
																				String msg = "inserito in coda prenotazine";
																				rokmsg =new String[2]; 														
																				rokmsg[0]=msg;
																				coda++;
																				rokmsg[1]=String.valueOf(coda);//PRIORITA														
																				x.setRowLoans(rokmsg);								
																			//q aggiorna campo STATO libro in PRENOTATO...
																				MQ_Update.updateLoansStato(String.valueOf(idbook),"Prenotato, "+coda+" utenti in coda" );	
																			}
																	}//non presente in coda prenotazione fine
														}else {	//criteri non rispettati...														
																x.setText(new String ("SRV :> Loans ASK :> OK , PRESTITO NON CONSENTITO" ));
																String[] rokmsg =new String[6]; 			
																rokmsg[0]="NN";
																rokmsg[1]=chkResult1;
																rokmsg[2]=chkResult2;
																rokmsg[3]=chkResult3;
																rokmsg[4]=chkResult4;
																rokmsg[5]="NN";
																x.setRowLoans(rokmsg);															
													}
												
												getMeS().addMsg(mSg);
											} catch (Exception e) {
												getMeS().addMsg(mSg);
												x.setText(new String ("SRV :> Loans ASK :> NG"));	System.out.println("problemi con  SRV :> Loans ASK :> NG");
												e.printStackTrace();
											}
											System.out.println("SYS BL :> srv ritorna "+x.getText());
											return x;
											//break;									
										default:
											break;
									}

						} catch (InterruptedException e) {	System.out.println("RealServer :> Problemi Accodamento CMD PR");
							e.printStackTrace();
							x.setText("RealServer :> Problemi Accodamento CMD PR");	
						}
						break;
						
					default:
						break;
				}
			
				break;		
			default:
				break;
			}
		return null;
	}

	//
	public ServerReal getSrvR() {
		return this;
	}
	public Server getSrv() {
		return Srv;
	}
	public void setSrv(Server srv) {
		Srv = srv;
	}
	public SystemServerSkeleton getMeS() {
		return meS;
	}
	public void setMeS(SystemServerSkeleton meS) {
		this.meS = meS;
	}
	public Guardian getGpG() {
		return GpG;
	}
	public void setGpG(Guardian gpG) {
		GpG = gpG;
	}
	public Requests getReq() {
		return Req;
	}
	public void setReq(Requests req) {
		Req = req;
	}
	public boolean isChkinprogress1() {
		return chkinprogress1;
	}
	public void setChkinprogress1(boolean chkinprogress1) {
		this.chkinprogress1 = chkinprogress1;
	}
	public String getChkResult1() {
		return chkResult1;
	}
	public void setChkResult1(String chkResult1) {
		this.chkResult1 = chkResult1;
	}
	public boolean isChkinprogress2() {
		return chkinprogress2;
	}
	public void setChkinprogress2(boolean chkinprogress2) {
		this.chkinprogress2 = chkinprogress2;
	}
	public String getChkResult2() {
		return chkResult2;
	}
	public void setChkResult2(String chkResult2) {
		this.chkResult2 = chkResult2;
	}
	public boolean isChkinprogress3() {
		return chkinprogress3;
	}
	public void setChkinprogress3(boolean chkinprogress3) {
		this.chkinprogress3 = chkinprogress3;
	}
	public String getChkResult3() {
		return chkResult3;
	}
	public void setChkResult3(String chkResult3) {
		this.chkResult3 = chkResult3;
	}
	public boolean isChkinprogress4() {
		return chkinprogress4;
	}
	public void setChkinprogress4(boolean chkinprogress4) {
		this.chkinprogress4 = chkinprogress4;
	}
	public String getChkResult4() {
		return chkResult4;
	}
	public void setChkResult4(String chkResult4) {
		this.chkResult4 = chkResult4;
	}
	public boolean isChkinprogress5() {
		return chkinprogress5;
	}
	public void setChkinprogress5(boolean chkinprogress5) {
		this.chkinprogress5 = chkinprogress5;
	}
	public String getChkResult6() {
		return chkResult6;
	}
	public void setChkResult6(String chkResult6) {
		this.chkResult6 = chkResult6;
	}
	public String getChkResult5() {
		return chkResult5;
	}
	public void setChkResult5(String chkResult5) {
		this.chkResult5 = chkResult5;
	}
	public boolean isChkinprogress6() {
		return chkinprogress6;
	}
	public void setChkinprogress6(boolean chkinprogress6) {
		this.chkinprogress6 = chkinprogress6;
	}
	public String[][] getDatitabellaBook() {
		return datitabellaBook;
	}
	public void setDatitabellaBook(String[][] datitabellaBook) {
		this.datitabellaBook = datitabellaBook;
	}
	public String[][] getDatitabellaBooking() {
		return datitabellaBooking;
	}
	public void setDatitabellaBooking(String[][] datitabellaBooking) {
		this.datitabellaBooking = datitabellaBooking;
	}
	public String[][] getDatitabellaLoans() {
		return datitabellaLoans;
	}
	public void setDatitabellaLoans(String[][] datitabellaLoans) {
		this.datitabellaLoans = datitabellaLoans;
	}
//**********************************************************************************************************************************************	
	/**accetta id_utente e id_book, check su PRESTITI, se l'utente ha gia ricevuto il prestito per 2 volte, 
	 * setCkkResult1== "SRV :> Loans ASK :> OK - PRESTITO NEGATO PER limite massimo prenotazioni (2) dello stesso libro raggiunto",
	 * altrimenti   == "SRV :> Loans ASK :> OK - PRESTITO ACCORDATO "
	/**
	 * @param idut
	 * @param idbook
	 */
	public void ck1(int idut,int idbook) {//TSST prestiti dello stesso libro, limite massimo 2 raggiunto	
		int pe;
		try {
			pe = MQ_Read.checkLoansIdutIdbook_2(idut, idbook);	
			if (pe==2||pe>2) {		
				setChkResult1("SRV :> Loans ASK :> OK - PRESTITO NEGATO PER limite massimo prenotazioni (2) dello stesso libro raggiunto ");
			}else {
				setChkResult1("SRV :> Loans ASK :> OK - PRESTITO ACCORDATO ");
				System.out.println("SRV :> Loans ASK :> OK CHECK 1 - MAX 2 PRESTITI PER LIBRO PER USER :> ");
			}
		} catch (SQLException e) {
				setChkResult1("SRV :> Loans ASK :> NG - " + e.toString());
			e.printStackTrace();
		}		//pe prestiti effettuati		
		setChkinprogress1(false);		
	}
//**********************************************************************************************************************************************	
	/**	/**accetta id_utente e id_book, check su PRESTITI, se l'utente ha gia ricevuto 5 prestiti, 
	 * setCkkResult2== "SRV :> Loans ASK :> OK - PRESTITO NEGATO PER limite massimo prestiti (5) per lo stesso utente raggiunto ",
	 * altrimenti   == "SRV :> Loans ASK :> OK - PRESTITO ACCORDATO "
	/**
	 * @param idut
	 * @param idbook
	 */
	public void ck2(int idut,int idbook) {//TEST prestiti dello stesso utente, limite massimo 5 raggiunto	
		int pe;
		try {		
			pe = MQ_Check.checkLoansIdutIdbook_5(idut, idbook);						
			if (pe==5||pe>5) {		//limite massimo di 5 raggiunto
				//prestito negato
				setChkResult2("SRV :> Loans ASK :> OK - PRESTITO NEGATO PER limite massimo prestiti (5) per lo stesso utente raggiunto ");
			}else {
				setChkResult2("SRV :> Loans ASK :> OK - PRESTITO ACCORDATO ");
				System.out.println("SRV :> Loans ASK :> OK CHECK 2 - MAX 5 PRESTITI PER USER :> ");
			}
		} catch (SQLException e) {
				setChkResult2("SRV :> Loans ASK :> NG - " + e.toString());
			e.printStackTrace();
		}		//pe prestiti effettuati
		setChkinprogress2(false);		
	}
//**********************************************************************************************************************************************	
	/**	accetta id_utente e id_book, check su PRESTITI, se l'utente ha almeno un prestito scaduto, 
	 * setCkkResult3== "SRV :> Loans ASK :> OK - PRESTITO NEGATO PER prestito SCADUTO risultante ",
	 * altrimenti   == "SRV :> Loans ASK :> OK - PRESTITO ACCORDATO "
	 * @param idut
     * @param idbook
	*/
		public void ck3(int idut,int idbook) {//TEST prestiti dello stesso utente almeno uno SCADUTO	
			int pe;
			try {							
				pe = MQ_Read.checkLoansIdutScaduti(idut, idbook);
							
				if (pe>0) {		//limite massimo di scaduti : 0 
					//prestito negato
					setChkResult3("SRV :> Loans ASK :> OK - PRESTITO NEGATO PER prestito SCADUTO risultante ");
				}else {
					setChkResult3("SRV :> Loans ASK :> OK - PRESTITO ACCORDATO ");
					System.out.println("SRV :> Loans ASK :> OK CHECK 3 -  :> ");
				}
			} catch (SQLException e) {
					setChkResult3("SRV :> Loans ASK :> NG - " + e.toString());
				e.printStackTrace();
			}		//pe prestiti effettuati
			setChkinprogress3(false);		
		}
//**********************************************************************************************************************************************	
		/**	accetta id_utente e id_book, check su PRENOTAZIONI, se l'utente ha giá 10 prenotazioni, 
		 * setCkkResult4== "SRV :> Loans ASK :> OK - PRESTITO NEGATO PER limite massimo prenotazioni (10) dello stesso utente ",
		 * altrimenti   == "SRV :> Loans ASK :> OK - PRESTITO ACCORDATO "
		 * @param idut
		 * @param idbook
		 */
		public void ck4(int idut,int idbook) {//TSST prenotazioni dello stesso utente limite massimo 10 raggiunto	
			int pe;
			try {

				pe = MQ_Read.checkBookingCount_10(idut, idbook);	
				
				if (pe==10||pe>10) {		//limite massimo di 10 raggiunto
					//prestito negato
					setChkResult4(		"SRV :> Loans ASK :> OK - PRESTITO NEGATO PER limite massimo prenotazioni (10) dello stesso utente ");
				}else {
					setChkResult4(		"SRV :> Loans ASK :> OK - PRESTITO ACCORDATO ");
					System.out.println(	"SRV :> Loans ASK :> OK CHECK 4 - MAX 10 Prenotazioni PER USER :> ");
				}
			} catch (SQLException e) {
					setChkResult4("SRV :> Loans ASK :> NG - " + e.toString());
				e.printStackTrace();
			}		//pe prestiti effettuati		
			setChkinprogress4(false);		
		}
		
		
		// *************************************************************	
	/**accetta Message e restituisce un MessageRealServer aggiungendo il riferimento al Client
	 * @param msg
	 * @return
	 */
	public MessageRealServer MessageEncapsulation (Message msg){
		MessageRealServer mrs=new MessageRealServer(msg, this);
		return mrs;
	}
	// *************************************************************
	@Override
	public void connstop() {
			getSrv().removeOp(this);
			getMeS().getFrame().setVisible(false);
			WindowEvent close = new WindowEvent(getMeS().getFrame(), WindowEvent.WINDOW_CLOSING);
			getMeS().getFrame().dispatchEvent(close);
	}
	public MessageBack getX() {
		return x;
	}
	public void setX(MessageBack x) {
		this.x = x;
	}
	public String getmSg() {
		return mSg;
	}
	public void setmSg(String mSg) {
		this.mSg = mSg;
	}
	public Map<String, Message> getListcmdDONE() {
		return listcmdDONE;
	}
	public void setListcmdDONE(Map<String, Message> listcmdDONE) {
		this.listcmdDONE = listcmdDONE;
	}
	public Boolean getGo() {
		return Go;
	}
	public void setGo(Boolean go) {
		Go = go;
	}

	
}
