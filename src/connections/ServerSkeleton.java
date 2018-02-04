package connections;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import Core.Commands;


public class ServerSkeleton implements IServer, Runnable {
			Socket 		_socket;
			ServerReal 	_meServer;
			boolean STOP=false;
			String mSg;
	private ObjectInputStream istream ;
	private ObjectOutputStream ostream ;
	
	
	public ServerSkeleton(Socket socket){	
			_socket = socket;
			try {
				istream = new ObjectInputStream	(socket.getInputStream	());
				ostream = new ObjectOutputStream(socket.getOutputStream	());
			}catch(IOException e ){
		}
	}
	public void run() {	
		try{
				while(!_socket.isClosed()||STOP){			
				
				Message myOper = ( Message ) istream.readObject() ;		//cast a Message della lettura dell'oggetto								
				System.out.println("SKELETON :> rx cmd :> "+myOper.getCommand().getCommandType().name());
				System.out.println("SKELETON :> rx cmd :> "+myOper.getCommand().Den);
				
				switch (myOper.getCommand().getCommandType()){	
				
				case CHANGE:
					System.out.println("Skeleton SWITCH Type:> rx cmd :> MODIFICA");
					MessageBack mb = new MessageBack();

					ostream.writeObject(modifica(myOper));
 					ostream.flush();					
					//------------------------------------------------------------------------------------------
 					
 					switch (myOper.getCommand()) {					
					case tableExistBook:						
						System.out.println("Skeleton SWITCH:>  test Table Book exist ");					
									
						break;						
						
                    case tableExistLoans:     					
                    	System.out.println("Skeleton SWITCH:>  test Table Loans exist ");	
							
						break;						
					
                    case tableExistPerson:     							
                    	System.out.println("Skeleton SWITCH:>  test Table Person exist ");	
									
						break;		
 					//------------------------------------------------------------------------------------------
					default: 					
						break;
 					}	
 					
 					break;
 					
				case READ:
					System.out.println("Skeleton SWITCH Type:> rx cmd :>  LETTURA");
					ostream.writeObject(visualizza(myOper));
 					ostream.flush();
 					
					break;		
					
				case TEST:
					// richiamato da crea dbtest della main gui
					
					System.out.println("Skeleton SWITCH Type:> rx cmd :>  TEST");
					ostream.writeObject(test(myOper));
 					ostream.flush();
 					//------------------------------------------------------------------------------------------
 					
 					switch (myOper.getCommand()) {					

					default:
						break;	
 					}
				break;		
						
						
				case CONNECTION:
					System.out.println("Skeleton SWITCH Type:> rx cmd :>  richiesta CONNECTIONs");
 					ostream.writeObject(connection(myOper));
 		 			ostream.flush(); 
		 					
 							if (myOper.getCommand()==Commands.ConnSTOP){
		 						System.out.println("attuale numero connessioni : "+ Server.getSrvconn() +"\n");
		 						//Thread.sleep(1000);
		 						_socket.close();
		 					}
 							
					break;		
					
					
				
				default:// CATEGORIE SPECIALI
					
						switch (myOper.getCommand()) {
											
									case tableExistBook:
									
									System.out.println("Skeleton :>  test DB exist ");
									System.out.println("Skeleton :>  testaggio esistenza DB");
									
								
				 					ostream.flush();									
									
									
									break;
									
								
                                   case tableExistLoans:     
									
									System.out.println("Skeleton :>  test DB exist ");
									System.out.println("Skeleton :>  testaggio esistenza DB");
									System.out.println("Loans");
									
				 					ostream.flush();									
									
									
									break;
									
                                   case tableExistPerson:     
   									
   									System.out.println("Skeleton :>  test DB exist ");
   									System.out.println("Skeleton :>  testaggio esistenza DB");
   									System.out.println("Loans");
   								
   				 					ostream.flush();									
   									
   									
   									break;
								default:
									System.out.println("Skeleton :> richiesta indefinita "); 
									break;
						
						}	//fine switch command
						break;
						
				}		
						
						
						
						
				}
				
				_meServer.getMeS().getFrame().setVisible(false);			
				_meServer.getSrv().setSrvconnDEC();		//DECREASE Server Connections counter
				_meServer.getSrv().getMeG().addMsg(mSg="numero connessioni attive"+ _meServer.getSrv().getSrvconn()+"");
				
				
				
			}catch(Exception e){
				e.printStackTrace();
				
				
				// Socket chiuso...
			}	
	}

	@Override
	public MessageBack testConnectionSS() {
		return null;
	}
	public void closeConnectionSS() throws InterruptedException {
		try{
			Thread.sleep(5000);					
			_socket.close();
			//Server.setSrvconn(Server.getSrvconn() - 1);
			System.out.println("attuale numero connessioni : "+ Server.getSrvconn() +"\n");	
		}catch(IOException e){	
		}
	}
	
	//**************************************************************************
	@Override
	public MessageBack visualizza(Message M) {
		return null;
	}
	//**************************************************************************
	@Override
	public MessageBack modifica(Message M) {
		return null;
	}
	//**************************************************************************
	
	// Connessione db	
	@Override
	public String testConnectionDB() {
		return null;
	}
	@Override
	public void closeConnectionDB() {	
	}		
	public ServerReal get_meServer() {
		return _meServer;
	}
	public void set_meServer(ServerReal _meServer) {
		this._meServer = _meServer;
	}	
	@Override
	public MessageBack SendRequest(Message x) throws IOException, ClassNotFoundException {
		return null;
	}
	@Override
	public MessageBack DBExist() {
		return null;
	}
	
	
	// implementati in server real
	@Override
	public MessageBack connection(Message M) throws RemoteException, InterruptedException, IOException {
		System.out.println("procede SKELETON");
		return null;
	}
	@Override
	public MessageBack test(Message M) throws RemoteException {		
		System.out.println("procede SKELETON");
		return null;
	}
	
	public void connstop() {
	}
	}
