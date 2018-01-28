package connections;
import Core.Commands;
public class ClientConnectionController implements Runnable {
	private 			Client 				me;
	private 			MessageBack			mSgBack;
	private 			String				mSg;	
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
					if (controllo==1) {
						me.getMeMain().getText().setText("Controllo di connessione...");
						me.getCmdLIST().put(Commands.ConnTEST);			//CTLL Connessione
						controllo=2;
					}else {		
						if (me.isStubok()) {
						me.getMeMain().getText().setText("Aggiorno Dati sul Client...");
						me.setRefreshData(true);
						me.getCmdLIST().put(Commands.GetDataForTables);	//REFRESH data	
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
}
