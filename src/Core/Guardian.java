package Core;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.mail.MessagingException;
import ProvaEmail.EmailSender;
import connections.Message;
import connections.MessageRealServer;
import connections.Server;
import database.MQ_Check;
import database.MQ_Read;
import database.MQ_Update;
 
 
public class Guardian implements Runnable {
	private Server 			meS;
	private Requests 		R;
	private boolean 		Stop=false;
	
	private boolean 		Busy=false;
	private Message 		MesServing;
	
	//-----------------------------------------------------------
	public Guardian (Server Srv,Requests Req){
		meS	=	Srv;
		R	=	Req;
		System.out.println("GPG :> Guardian operativo");	
	}
	//-----------------------------------------------------------
	
	public void run() {

		int xx=0;
		Calendar calendar = new GregorianCalendar();
		Date datacorrente = calendar.getTime();  
		Date dataultimocontrollo=calendar.getTime();  

		
		
		
		
		while(!Stop){
			Calendar c = new GregorianCalendar();
			datacorrente = c.getTime();  
			
			xx++;
			while (isBusy()){}	
			
			if ((datacorrente.getTime()-dataultimocontrollo.getTime())>30000) {				//controllo otni 30 secondi PER TEST	
					
				System.err.println("passati 5 secondi, controllo scadenze prestiti");				
				dataultimocontrollo = c.getTime();
				
				//PROCEDURE CONTROLLO SCADENZE
				//--------------------------------------------------------
				try {
					CheckLoans();
										} catch (InterruptedException e) {
													e.printStackTrace();
				}
				//--------------------------------------------------------
				try {
					checkLoansSendEmail();
										} catch (InterruptedException e) {
													e.printStackTrace();
				}
				
				//ctll ritiro non effettuato
				//procedura rientro
				
			}	
	
			
			if (xx==1000)
				xx=0;
			
			try {
				Thread.sleep(10);
				//System.out.println("GPG :> Guardian Valuta");					
			Val_BL();	// prima valutazione, le altre vengono richiamate se necessario
			} 	catch (Exception e) {
				System.out.println("guardian problemi");
				}
		}
	}
	
	//------------------------------------------------------------
	/**considera in base ai contatori se prendere dalla coda un comando da eseguire o passare al successivo controllo [BR]
	 * @throws InterruptedException
	 */
	public void Val_BL() throws InterruptedException{	
		int x;
		
		if (R.getBL().getWr()>0){		//richieste in attesa per BL
				if(R.getLastserved()!=Requests.RS.BL){	//ultima richiesta servita diversa da 	BL
						ServeBL(0);				//SERVITA mod PRIMA RICHIESTA BL					
				}else{
						x = R.getBLcs();		//						
						if (x < 5){				//servite continuativamente MENO di 5 BL
								ServeBL(x);		//SERVITA mod CONTINUA...					
						}else{					//servite continuativamente GIA 5 BL ( provo altro )
								
								R.setBLcs(4);	//decremento conteggio BL
												//al prossimo turno verra servita
								
								Val_BR();		//altre valutazioni a partire da BR
						}		
				}		
		}else{								//nessuna richiesta in attesa per BL
				Val_BR();
		}
		
	}
	//------------------------------------------------------------
	/**considera in base ai contatori se prendere dalla coda un comando da eseguire o passare al successivo controllo [PL]
	 * @throws InterruptedException
	 */	
	public void Val_BR() throws InterruptedException{	
		
		//System.out.println("GPG :> Guardian valuta BR");
		
		int x;
		if (R.getBR().getWr()>0){		//richieste in attesa per BR
			
				if(R.getLastserved()!=Requests.RS.BR){	//ultima richiesta servita diversa da 	BR
						ServeBR(0);				//SERVITA mod PRIMA RICHIESTA BR					
				}else{
						x = R.getBRcs();		//						
						if (x < 5){				//servite continuativamente MENO di 5 BR
								ServeBR(x);		//SERVITA mod CONTINUA...					
						}else{					//servite continuativamente GIA 5 BR ( provo altro )
								
								R.setBRcs(4);	//decremento conteggio BR
												//al prossimo turno verra servita
								
								Val_PL();		//altre valutazioni a partire da AL
						}		
				}		
		}else{								//nessuna richiesta in attesa per BR
				Val_PL();
		}		
	}
	//------------------------------------------------------------
	/**considera in base ai contatori se prendere dalla coda un comando da eseguire o passare al successivo controllo [PR]
	 * @throws InterruptedException
	 */	
	public void Val_PL() throws InterruptedException{	
		int x;
	
		if (R.getPL().getWr()>0){		//richieste in attesa per BL
				if(R.getLastserved()!=Requests.RS.PL){	//ultima richiesta servita diversa da 	BL
						ServePL(0);				//SERVITA mod PRIMA RICHIESTA BL					
				}else{
						x = R.getPLcs();		//						
						if (x < 5){				//servite continuativamente MENO di 5 BL
								ServePL(x);		//SERVITA mod CONTINUA...					
						}else{					//servite continuativamente GIA 5 BL ( provo altro )
								
								R.setPLcs(4);	//decremento conteggio BL
												//al prossimo turno verra servita
								
								Val_PR();		//altre valutazioni a partire da BR
						}		
				}		
		}else{								//nessuna richiesta in attesa per BL
				Val_PR();
		}
		
	}
	//------------------------------------------------------------
	/**considera in base ai contatori se prendere dalla coda un comando da eseguire o passare al successivo controllo [BKL]
	 * @throws InterruptedException
	 */
	public void Val_PR() throws InterruptedException{	
		
		//System.out.println("GPG :> Guardian valuta BR");
		
		int x;
		if (R.getPR().getWr()>0){		//richieste in attesa per BR
			
				if(R.getLastserved()!=Requests.RS.PR){	//ultima richiesta servita diversa da 	BR
						ServePR(0);				//SERVITA mod PRIMA RICHIESTA BR					
				}else{
						x = R.getPRcs();		//						
						if (x < 5){				//servite continuativamente MENO di 5 BR
								ServePR(x);		//SERVITA mod CONTINUA...					
						}else{					//servite continuativamente GIA 5 BR ( provo altro )
								
								R.setPRcs(4);	//decremento conteggio BR
												//al prossimo turno verra servita
								
								Val_BKL();		//altre valutazioni a partire da AL
						}		
				}		
		}else{								//nessuna richiesta in attesa per BR
				//Val_AL();
				Val_BKL();
		}		
	}
	//------------------------------------------------------------
	/**considera in base ai contatori se prendere dalla coda un comando da eseguire o passare al successivo controllo [BKR]
	 * @throws InterruptedException
	 */
	public void Val_BKL() throws InterruptedException{	
		
		//System.out.println("GPG :> Guardian valuta BKL");
		
		int x;
		if (R.getBKL().getWr()>0){		//richieste in attesa per BKL
			
				if(R.getLastserved()!=Requests.RS.BKL){	//ultima richiesta servita diversa da 	BKL
						ServeBKL(0);				//SERVITA mod PRIMA RICHIESTA BR					
				}else{
						x = R.getBKLcs();		//						
						if (x < 5){				//servite continuativamente MENO di 5 BR
								ServeBKL(x);		//SERVITA mod CONTINUA...					
						}else{					//servite continuativamente GIA 5 BR ( provo altro )
								
								R.setBKLcs(4);	//decremento conteggio BR
												//al prossimo turno verra servita
								
								Val_BKR();		//altre valutazioni a partire da AL
						}		
				}		
		}else{								//nessuna richiesta in attesa per BR
				Val_BKR();
		}		
	}
	
	public void Val_BKR() throws InterruptedException{	
		
		//System.out.println("GPG :> Guardian valuta BKR");
		
		int x;
		if (R.getBKR().getWr()>0){		//richieste in attesa per BR
			
				if(R.getLastserved()!=Requests.RS.BKR){	//ultima richiesta servita diversa da 	BR
						ServePR(0);				//SERVITA mod PRIMA RICHIESTA BR					
				}else{
						x = R.getBKRcs();		//						
						if (x < 5){				//servite continuativamente MENO di 5 BR
								ServeBKR(x);		//SERVITA mod CONTINUA...					
						}else{					//servite continuativamente GIA 5 BR ( provo altro )
								
								R.setBKRcs(4);	//decremento conteggio BR
												//al prossimo turno verra servita
								
								Val_AL();		//altre valutazioni a partire da AL
						}		
				}		
		}else{								//nessuna richiesta in attesa per BR
				Val_AL();
		}		
	}
	//------------------------------------------------------------
	
	/**considera in base ai contatori se prendere dalla coda un comando da eseguire o passare al successivo controllo [AR]
	 * @throws InterruptedException
	 */
		public void Val_AL() throws InterruptedException{	
		
		//System.out.println("GPG :> Guardian valuta AL");
		
		int x;
		if (R.getAL().getWr()>0){		//richieste in attesa per AL
			
				if(R.getLastserved()!=Requests.RS.AL){	//ultima richiesta servita diversa da 	AL
						ServeAL(0);				//SERVITA mod PRIMA RICHIESTA AL					
				}else{
						x = R.getALcs();		//						
						if (x < 3){				//servite continuativamente MENO di 5 AL
								ServeAL(x);		//SERVITA mod CONTINUA...					
						}else{					//servite continuativamente GIA 3 AL ( provo altro )
								
								R.setALcs(2);	//decremento conteggio AL
												//al prossimo turno verra servita
								
								Val_AR();		//altre valutazioni a partire da AR
						}		
				}		
		}else{								//nessuna richiesta in attesa per AL
				Val_AR();
		}
	}
	//------------------------------------------------------------
		/**considera in base ai contatori se prendere dalla coda un comando da eseguire
		 * @throws InterruptedException
		 */
		
		public void Val_AR() throws InterruptedException{	
		
		//System.out.println("GPG :> Guardian valuta AR");
		
		int x;
		if (R.getAR().getWr()>0){				//richieste in attesa per AR
			
				if(R.getLastserved()!=Requests.RS.AR){	//ultima richiesta servita diversa da 	AR
						ServeAR(0);				//SERVITA mod PRIMA RICHIESTA AR					
				}else{
						x = R.getARcs();		//						
						ServeAR(x);				//SERVITA mod CONTINUA...						
				}		
		}else{								//nessuna richiesta in attesa per AR
											// RIPARTE CONTROLLO
		}
	}	
	//-----------------------------------------------------------
	

		//Procedure di Gestione Richieste	
	
	/**resetta contatori delle altre code, prende dalla coda un messageserver contenente comando da eseguire
	 * e realserver go da sbloccare
	 * @param cs
	 * @throws InterruptedException
	 */
	public void ServeBL(int cs) throws InterruptedException{		
		System.out.println("GPG :> Guardian SERVE BL");		
		
		R.incBLcs();		//incrementa contatore in ogni caso
		
		if (cs==0){			//richiesta diversa dall'ultima servita			
			R.setBRcs(0);
			R.setARcs(0);
			R.setALcs(0);
			R.setPLcs(0);
			R.setPRcs(0);
			R.setSLcs(0);
			R.setSRcs(0);
			R.setBKLcs(0);
			R.setBKRcs(0);
			R.setLastserved(Requests.RS.BL);
		}	else {			//continua richiesta per cs == 1 2 3 4
			}
		
		MessageRealServer r = R.getBL().take();//decrementato BLw
		System.out.println("GPG :> Guardian prelevato comando "+R.getBL().toString());
		System.out.println("GPG :> Guardian BL wl : "+R.getBL().getWr());
		
		
		//SERVE
		
		Busy			=true;		
		System.out.println("GPG :> SERVERREAL GO prima :"+r.getSrv().Go);		
		r.getSrv().Go	=true;

		System.out.println("GPG :> SERVERREAL GO dopo :"+r.getSrv().Go);
		Busy			=false;
		
		// Thread di controllo
		new Thread(new GuardianTimeOut(this,r,10)).start();	
		
	}
	/**resetta contatori delle altre code, prende dalla coda un messageserver contenente comando da eseguire
	 * e realserver go da sbloccare
	 * @param cs
	 * @throws InterruptedException
	 */
	public void ServeBR(int cs) throws InterruptedException{
		System.out.println("GPG :> Guardian SERVE BR");		
		R.incBRcs();		//incrementa contatore in ogni caso
		
		if (cs==0){			//richiesta diversa dall'ultima servita			
			R.setBLcs(0);
			R.setARcs(0);
			R.setALcs(0);
			R.setPLcs(0);
			R.setPRcs(0);	
			R.setSLcs(0);
			R.setSRcs(0);
			R.setBKLcs(0);
			R.setBKRcs(0);
			R.setLastserved(Requests.RS.BR);
		}	else {			//continua richiesta per cs == 1 2 3 4
			}
		
		MessageRealServer r = R.getBR().take();//decrementato BRw
		
		//SERVE
		
		Busy			=true;
		r.getSrv().Go	=true;
		Busy			=false;
		
		// Thread di controllo
		new Thread(new GuardianTimeOut(this,r,10)).start();	
		
	}
	/**resetta contatori delle altre code, prende dalla coda un messageserver contenente comando da eseguire
	 * e realserver go da sbloccare
	 * @param cs
	 * @throws InterruptedException
	 */	
	public void ServePR(int cs) throws InterruptedException{
		System.out.println("GPG :> Guardian SERVE PR");		
		R.incPRcs();		//incrementa contatore in ogni caso
		
		if (cs==0){			//richiesta diversa dall'ultima servita			
			R.setBLcs(0);
			R.setBRcs(0);
			R.setALcs(0);
			R.setARcs(0);
			R.setPLcs(0);
			R.setSLcs(0);
			R.setSRcs(0);
			R.setBKLcs(0);
			R.setBKRcs(0);
			//R.setPRcs(0);
			R.setLastserved(Requests.RS.PR);
		}	else {			//continua richiesta per cs == 1 2 3 4
			}
		
		MessageRealServer r = R.getPR().take();//decrementato BRw
		
		//SERVE
		
		Busy			=true;
		r.getSrv().Go	=true;
		Busy			=false;
		
		// Thread di controllo
		new Thread(new GuardianTimeOut(this,r,10)).start();	
	}
	/**resetta contatori delle altre code, prende dalla coda un messageserver contenente comando da eseguire
	 * e realserver go da sbloccare
	 * @param cs
	 * @throws InterruptedException
	 */	
	public void ServePL(int cs) throws InterruptedException{
		System.out.println("GPG :> Guardian SERVE PL");		
		R.incPLcs();		//incrementa contatore in ogni caso
		
		if (cs==0){			//richiesta diversa dall'ultima servita			
			R.setBLcs(0);
			R.setBRcs(0);
			R.setALcs(0);
			R.setARcs(0);			
			R.setPRcs(0);
			R.setSLcs(0);
			R.setSRcs(0);
			R.setBKLcs(0);
			R.setBKRcs(0);
			R.setLastserved(Requests.RS.PL);
		}	else {			//continua richiesta per cs == 1 2 3 4
			}
		
		MessageRealServer r = R.getPL().take();//decrementato BRw
		
		//SERVE
		
		Busy			=true;
		r.getSrv().Go	=true;
		Busy			=false;
		
		// Thread di controllo
		new Thread(new GuardianTimeOut(this,r,10)).start();	
	}
	/**resetta contatori delle altre code, prende dalla coda un messageserver contenente comando da eseguire
	 * e realserver go da sbloccare
	 * @param cs
	 * @throws InterruptedException
	 */	
	public void ServeAR(int cs) throws InterruptedException{
		System.out.println("GPG :> Guardian SERVE AR");		
		R.incARcs();		//incrementa contatore in ogni caso
		
		if (cs==0){			//richiesta diversa dall'ultima servita			
			R.setBLcs(0);
			R.setBRcs(0);
			R.setALcs(0);
			R.setPLcs(0);
			R.setPRcs(0);
			R.setSLcs(0);
			R.setSRcs(0);
			R.setBKLcs(0);
			R.setBKRcs(0);
			R.setLastserved(Requests.RS.AR);
		}	else {			//continua richiesta per cs == 1 2 3 4
			}
		
		MessageRealServer r = R.getAR().take();//decrementato BRw
		
		//SERVE
		
		Busy			=true;
		r.getSrv().Go	=true;
		Busy			=false;
		
		// Thread di controllo
		new Thread(new GuardianTimeOut(this,r,10)).start();	
	}
	/**resetta contatori delle altre code, prende dalla coda un messageserver contenente comando da eseguire
	 * e realserver go da sbloccare
	 * @param cs
	 * @throws InterruptedException
	 */
	public void ServeAL(int cs) throws InterruptedException{
		System.out.println("GPG :> Guardian SERVE AL");		
		R.incALcs();		//incrementa contatore in ogni caso
		
		if (cs==0){			//richiesta diversa dall'ultima servita			
			R.setBLcs(0);
			R.setBRcs(0);
			R.setARcs(0);
			R.setPLcs(0);
			R.setPRcs(0);
			R.setSLcs(0);
			R.setSRcs(0);
			R.setBKLcs(0);
			R.setBKRcs(0);
			
			R.setLastserved(Requests.RS.AL);
		}	else {			//continua richiesta per cs == 1 2 3 4
			}
		
		MessageRealServer r = R.getAL().take();//decrementato BRw
		
		//SERVE
		
		Busy			=true;
		r.getSrv().Go	=true;
		Busy			=false;
		
		// Thread di controllo
		new Thread(new GuardianTimeOut(this,r,10)).start();	
	}
	/**resetta contatori delle altre code, prende dalla coda un messageserver contenente comando da eseguire
	 * e realserver go da sbloccare
	 * @param cs
	 * @throws InterruptedException
	 */
	public void ServeBKL(int cs) throws InterruptedException{
		System.out.println("GPG :> Guardian SERVE BKL");		
		R.incBKLcs();		//incrementa contatore in ogni caso
		
		if (cs==0){			//richiesta diversa dall'ultima servita			
			R.setBLcs(0);
			R.setBRcs(0);
			R.setARcs(0);
			R.setPLcs(0);
			R.setPRcs(0);
			R.setSLcs(0);
			R.setSRcs(0);
			
			R.setBKRcs(0);
			R.setLastserved(Requests.RS.BKL);
		}	else {			//continua richiesta per cs == 1 2 3 4
			}
		
		MessageRealServer r = R.getBKL().take();//decrementato BRw
		
		//SERVE
		
		Busy			=true;
		r.getSrv().Go	=true;
		Busy			=false;
		
		// Thread di controllo
		new Thread(new GuardianTimeOut(this,r,10)).start();	
	}
	/**resetta contatori delle altre code, prende dalla coda un messageserver contenente comando da eseguire
	 * e realserver go da sbloccare
	 * @param cs
	 * @throws InterruptedException
	 */
	public void ServeBKR(int cs) throws InterruptedException{
		System.out.println("GPG :> Guardian SERVE BKR");		
		R.incBKRcs();		//incrementa contatore in ogni caso
		
		if (cs==0){			//richiesta diversa dall'ultima servita			
			R.setBLcs(0);
			R.setBRcs(0);
			R.setARcs(0);
			R.setPLcs(0);
			R.setPRcs(0);
			R.setSLcs(0);
			R.setSRcs(0);
			R.setBKLcs(0);
	
			R.setLastserved(Requests.RS.BKR);
		}	else {			//continua richiesta per cs == 1 2 3 4
			}
		
		MessageRealServer r = R.getBKR().take();//decrementato BRw
		
		//SERVE
		
		Busy			=true;
		r.getSrv().Go	=true;
		Busy			=false;
		
		// Thread di controllo
		new Thread(new GuardianTimeOut(this,r,10)).start();	
	}	
	/**resetta contatori delle altre code, prende dalla coda un messageserver contenente comando da eseguire
	 * e realserver go da sbloccare
	 * @param cs
	 * @throws InterruptedException
	 */
	public void ServeSL(int cs) throws InterruptedException{
		System.out.println("GPG :> Guardian SERVE SL");		
		R.incSLcs();		//incrementa contatore in ogni caso
		
		if (cs==0){			//richiesta diversa dall'ultima servita			
			R.setBLcs(0);
			R.setBRcs(0);
			R.setARcs(0);
			R.setPLcs(0);
			R.setPRcs(0);
	

			R.setSRcs(0);
			R.setBKLcs(0);
			R.setBKRcs(0);
			R.setLastserved(Requests.RS.BKL);
		}	else {			//continua richiesta per cs == 1 2 3 4
			}
		
		MessageRealServer r = R.getSL().take();//decrementato BRw
		
		//SERVE
		
		Busy			=true;
		r.getSrv().Go	=true;
		Busy			=false;
		
		// Thread di controllo
		new Thread(new GuardianTimeOut(this,r,10)).start();	
	}
	/**resetta contatori delle altre code, prende dalla coda un messageserver contenente comando da eseguire
	 * e realserver go da sbloccare
	 * @param cs
	 * @throws InterruptedException
	 */
	public void ServeSR(int cs) throws InterruptedException{
		System.out.println("GPG :> Guardian SERVE SR");		
		R.incSRcs();		//incrementa contatore in ogni caso
		
		if (cs==0){			//richiesta diversa dall'ultima servita			
			R.setBLcs(0);
			R.setBRcs(0);
			R.setARcs(0);
			R.setPLcs(0);
			R.setPRcs(0);
			R.setSLcs(0);

			R.setBKLcs(0);
			R.setBKRcs(0);			
			R.setLastserved(Requests.RS.SR);
		}	else {			//continua richiesta per cs == 1 2 3 4
			}
		
		MessageRealServer r = R.getSR().take();//decrementato BRw
		
		//SERVE
		
		Busy			=true;
		r.getSrv().Go	=true;
		Busy			=false;
		
		// Thread di controllo
		new Thread(new GuardianTimeOut(this,r,10)).start();	
	}
	
	
	
	
	
	// PROCEDURA CONTROLLO SCADENZE PRESTITI
	/** controllo su prestiti scaduti e aggiornamento flags 
	 * @param cs
	 * @throws InterruptedException
	 */
	public void CheckLoans() throws InterruptedException{
		meS.getMeG().addMsg("GpG:> connessioni attive: "+meS.getOperatori().size()+"\n"+
							"GpG:> Aggiorno Flag Prestiti scaduti... ");
		
		System.out.println("GPG :> Guardian SERVE CHECK LOANS");		
		//SERVE controllo prestiti scaduti
		Busy=true;
		try {
			MQ_Check.updatePrestitoScaduto();
		} catch (SQLException e) {
			System.out.println("Errore... "+e.getMessage());
			e.printStackTrace();
		}
		Busy=false;
	}
	
	/** controllo su flags di prestiti scaduti ed invio email di avviso 
	 * @param cs
	 * @throws InterruptedException
	 */	
	public void checkLoansSendEmail() throws InterruptedException{
		String [] em=null;
		
		System.out.println("GPG :> Guardian SERVE CHECK LOANS send Email");		
		//SERVE invio email 
		//AL PRIMO
		//in lista dei prestiti spediti con email non inviata 
		
		Busy=true;
		try {
			em = MQ_Read.readLoansForSendEmail();
			
			if (em[0].equals("Nessun Dato")) {
				
				System.out.println(("nessun prestito scaduto") );
				
				meS.getMeG().addMsg("GpG:> connessioni attive: "+meS.getOperatori().size()+"\n"+
						"GpG:> Controllo Prestiti scaduti... : nessuno");
				
				//non fare piu nulla
				
			}else {
				
				String codice 			= em[0];System.out.println("codice:"		+codice);
				String iduser 			= em[1];System.out.println("iduser:"		+iduser);
				String nome 			= em[2];System.out.println("nome:"			+nome);
				String cognome 			= em[3];System.out.println("cognome:"		+cognome);
				String email 			= em[4];System.out.println("email:"			+email);
				String tipoutente		= em[5];System.out.println("tipo utente:"	+tipoutente);
				String nome_autore		= em[6];System.out.println("nome 	autore:"+nome_autore);
				String cognome_autore	= em[7];System.out.println("cognome utente:"+cognome_autore);
				String titolo			= em[8];System.out.println("titolo:"		+titolo);
				
				MQ_Update.updateLoansEmailSent(iduser, codice);
				
//TODO ESTRAPOLA CREDENZIALI DA TABELLA SETTING
				
				String [] datasetting = MQ_Read.readSettingTable();
				String un = datasetting[4];
				String pw = datasetting[5];
				
//TODO send email
				
				try {
				EmailSender.send_LoansExpired(un, pw, email, codice, nome, cognome,nome_autore,cognome_autore,titolo);
				} catch (MessagingException e) {	
					e.printStackTrace();
				} 
				
				meS.getMeG().addMsg("GpG:> connessioni attive: "+meS.getOperatori().size()+"\n"+
						"GpG:> Controllo Prestiti scaduti... : INVIO eMAIL");
				
			}
			
			
			
			
		} catch (SQLException e) {
			System.out.println("Errore... "+e.getMessage());
			e.printStackTrace();
		}
		Busy=false;
	}

	public boolean isBusy() {
		return Busy;
	}
	public void setBusy(boolean busy) {
		Busy = busy;
	}
	public Message getMesServing() {
		return MesServing;
	}
	public void setMesServing(Message mesServing) {
		MesServing = mesServing;
	}

}
