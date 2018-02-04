package connections;


import Core.Commands;
import Table.TableBooking;
import Table.TableBooks;
import Table.TableLoans;
import gui.ResearchBooks;
public class ClientConnectionController implements Runnable {
	private 			Client 				me;

	private 			int 				FCC=500;	//FrequenzaControlloConnessione [millisecondi]
	private 			int 				turniBusy=10;
//------------------------------------------------------------------------	
	public ClientConnectionController(Client Me){
		me=Me;
		System.out.println("CCC :> Creato");
	}
	public ClientConnectionController(Client Me,int msec){
		me=Me;
		FCC=msec;
		System.out.println("CCC :> Creato");
	}
//------------------------------------------------------------------------	
	public void run() {
		try {
			System.out.println("CCC :> Operativo");
			Logica();
		} catch (Exception e) {
			System.out.println("CCC :> Problemi con Lancio CCC ");
		}	
	}
//------------------------------------------------------------------------	
	void Logica() throws Exception {	
 		int ctc=0;						//---- Connection test counter				
 		int controllo=1;				//---- 1 controllo connessione, 2 refresh dati
 		
 		System.out.println("CCC :> Inizio Logica");
 		
 		while (me.isRepeatconn()){
			try {
				Thread.sleep(FCC);
			} catch (Exception e) {							
			}
 			
 			System.out.println("CCC :> lancio richiesta controllo connessione no. "+ctc);	
			int TurniBusy=turniBusy;
			ctc++;
 				try {
					while (me.isBusy()&&TurniBusy>0){	//se busy attende fino a 10 tentativi poi lascia in coda comando					
						System.out.println("CCC :> non lancio richiesta controllo connessione , rimandono turniBUSY da saltare: "+TurniBusy);
						try {
							Thread.sleep(FCC);
						} catch (Exception e) {							
						}
					TurniBusy--;							
					}
					
					//alterno i controlli					
					if (controllo==1) {				//CONTROLLO == 1 >> Connessione
						me.getMeMain().getText().setText("Controllo di connessione...");
						me.getCmdLIST().put(Commands.ConnTEST);			//CTLL Connessione
						controllo=2;
						
						
						
					}else {							//CONTROLLO == 2 >> Aggiorno Dati 		
						if (me.isStubok()) {
								me.getMeMain().getText().setText("Aggiorno Dati sul Client...la finestra attiva é "+me.getActW().toString());
								if (me.getMeRes()!=null) {//finesta attiva
										
										if (me.getMePannelBook().isVisible()) {
												
												System.err.println("vedo pannello book");
												try {
													String Fbook = ((ResearchBooks)me.getActW()).getTextField().getText();	
													System.out.println("dato nel campo testo: "+Fbook);
												//-----------------------------------------------------------------
												ResearchBooks x = (ResearchBooks)me.getActW();	
												if (me.getIdut()==0) {
													x.getButton_1().setVisible(false);//Guest
													x.getBtnNewButton().setVisible(false);
												}else {
														if (me.getDatiUtente()[6].equals("Libraio")) {//librarian	
															x.getButton_1().setVisible(true);
															x.getBtnNewButton().setVisible(true);
															System.out.println("per utente diverso da libraio copro il tasto riconsegna");
														}else {
															x.getButton_1().setVisible(false);//reader
															x.getBtnNewButton().setVisible(false);
														}
												}			
												//-----------------------------------------------------------------
												if (Fbook==null||Fbook.equals("")) {
													TableBooks.PopulateData(null,me);
												}else {
													TableBooks.PopulateData(Fbook,me);	
												}		
	
												} catch (Exception e) {
													
												}	
										}											
										if (me.getMePannelLoans().isVisible()) {
													
												System.err.println("vedo pannello loans");
												String Floans = ((ResearchBooks)me.getActW()).getTextField().getText();
												
												if (Floans==null||Floans.equals("")) {
													TableLoans.PopulateData("",me);
												}else {
													TableLoans.PopulateData(Floans,me);	
												}													
																					}
										if (me.getMePannelBooking().isVisible()) {
											
												System.err.println("vedo pannello booking");
												
												String Fbooking = ((ResearchBooks)me.getActW()).getTextField().getText();
												if (Fbooking==null||Fbooking.equals("")) {
													TableBooking.PopulateData("",me);
												}else {
													TableBooking.PopulateData(Fbooking,me);	
												}												
												
												
												
										}			
								}//me.getMeRes
								
								
								
								
								
						//me.setRefreshData(true);
						//me.getCmdLIST().put(Commands.GetDataForTables);	//REFRESH data	
						}else {
							me.getMeMain().getText().setText("REFRESH DATI non POSSIBILE [Server OFF-line...]");
						}
						controllo=1;
					}
				}catch (Exception e) {e.printStackTrace();
				} 								
		}	//while					
	}		//Logica
//------------------------------------------------------------------------
	
	

	/**esegue verifica pannello attivo nella GUI ResearchBook, in base al risultato
	 * aggiorna la relativa jtable interrogando il server, i dati richiesti sono in relazione
	 * al profilo utente in login ( settato sul Client ) 
	 * @param me
	 */
	public static void ONEcontrol (Client me) {
		
			if (me.getMePannelBook().isVisible()) {			
				System.err.println("ONE CONTROL vedo pannello book");
				try {
					String Fbook = ((ResearchBooks)me.getActW()).getTextField().getText();	
					System.out.println("ONE CONTROL  dato nel campo testo: "+Fbook);
						//-----------------------------------------------------------------
						ResearchBooks x = (ResearchBooks)me.getActW();	
						if (me.getIdut()==0) {
							x.getButton_1().setVisible(false);//Guest
						}else {
								if (me.getDatiUtente()[6].equals("Libraio")) {//librarian	
									x.getButton_1().setVisible(true);
									System.out.println("ONE CONTROL per utente diverso da libraio copro il tasto riconsegna");
								}else {
									x.getButton_1().setVisible(false);//reader
								}
						}			
						//-----------------------------------------------------------------
						if (Fbook==null||Fbook.equals("")) {
							TableBooks.PopulateData(null,me);
						}else {
							TableBooks.PopulateData(Fbook,me);	
						}		
				} catch (Exception e) {
				}		
			}
			
			if (me.getMePannelLoans().isVisible()) {
						
					System.err.println("ONE CONTROL vedo pannello loans");
					String Floans = ((ResearchBooks)me.getActW()).getTextField().getText();
					try {
						if (Floans==null||Floans.equals("")) {
							TableLoans.PopulateData("",me);
						}else {
							TableLoans.PopulateData(Floans,me);	
						}							
					} catch (Exception e) {
						e.printStackTrace();
					}
												
			}
			if (me.getMePannelBooking().isVisible()) {
				
					System.err.println("ONE CONTROL vedo pannello booking");
					String Fbooking = ((ResearchBooks)me.getActW()).getTextField().getText();
					try {
						if (Fbooking==null||Fbooking.equals("")) {
							TableBooking.PopulateData("",me);
						}else {
							TableBooking.PopulateData(Fbooking,me);	
						}	
					} catch (Exception e) {
						e.printStackTrace();
					}
																			
			}
			
}
		
	
	
	
	
	
	
	
}
