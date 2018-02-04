package connections;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.rmi.RemoteException;
import java.io.EOFException;
import java.io.IOException;



/**
 * @author Mauro
 *
 */
public class ServerStub implements Serializable, IServer {

	private static final long serialVersionUID = 1L;
	private ObjectInputStream in;
	private ObjectOutputStream out; 
	private Socket _socket;
	private String mSg;
	private int conntentativi;
	private MessageBack msg;
	private Client Rifclient;
	
	private InetAddress addr;
	
	
	public ServerStub(Client client, String ADD) throws Exception{
		Rifclient = client;
		setConntentativi(0);			
		
		addr= InetAddress.getByName(ADD);							//test con Local host PASSATO dal client		
	
		
		
		
	System.out.println("addr = " + addr );
	try {
		_socket = new Socket(addr, IServer.PORT );
			
			System.out.print(mSg = "STUB:> Socket = " + _socket +"\n")		;// leggi testo nel messaggio);
			Rifclient.getActW().addMsg(mSg);	
		out = new ObjectOutputStream(_socket.getOutputStream());
		in 	= new ObjectInputStream(_socket.getInputStream());						
			
			System.out.print(mSg = "STUB:> Check in/out:> OK "+"\n")		;// leggi testo nel messaggio);
			Rifclient.getActW().addMsg(mSg);		
			Rifclient.setStubok(true);
		
	} catch (Exception e) {
			
			System.out.print(mSg = "STUB:> Server Assente \n")				;// leggi testo nel messaggio);
			
			if (Rifclient.getActW()!=null) {
			//Rifclient.getActW().addMsg(mSg);	
			}else {
				System.out.println("intercetto null pointer per actW su stub");
			}
			
			Rifclient.setStubok(false);
			Rifclient.setRepeatconn(true);	
	} 

}
		
	@Override
	public MessageBack SendRequest(Message x) throws IOException, ClassNotFoundException {
			MessageBack mb = new MessageBack();
			
			System.out.println("STUB :> Spedita richiesta comando a SKELETON");
		
		out.writeObject(x);
		
			try{				
		mb = (MessageBack)in.readObject();
			
		System.out.println("STUB :> Tornata risposta da SKELETON ");				
		System.out.println("STUB :> Tornata risposta da SKELETON code    : "+mb);
		System.out.println("STUB :> Tornata risposta da SKELETON gettest : "+mb.getText());
				
			}catch (IOException e){
			System.out.println("STUB:> ECCEZIONE");	
			e.printStackTrace();
			mb.setText(new String ("STUB :> Eccezione *** nessuna risposta da SKELETON"));
			}finally{
		return mb;
			}
	}
	
	
		// Connection SS
	@Override
		public MessageBack testConnectionSS() {	
		MessageBack mb = new MessageBack("STUB :> nessuna risposta...");
		
		//	out.println("<testConn>");
			try {		
				out.writeObject(new Message(Core.Commands.ConnTEST));
				mb = (MessageBack)in.readObject();	
				// test
				
				if ( mb == null){
					System.out.println("nessuna risposta");
				}
					
					
				System.out.print(mSg = "STUB:> RITORNO DA TEST CONN \n"+ mb.getText())		;// leggi testo nel messaggio);
				
				Rifclient.getActW().addMsg(mSg);		
		
				// test ok
				return mb;
				
			} catch (IOException | ClassNotFoundException e1) {				
				e1.printStackTrace();
				Rifclient.setStubok(false);
				Rifclient.setRepeatconn(true);
				mb.setText(new String ("STUB ERRORE LETTURA"));
				return mb;
			}		
	}		
		
		public void closeConnectionSS() throws EOFException {
			MessageBack msg=null;
			
			System.out.println("STUB : closing... ");
			//out.println("<end>");
			try {
				out.writeObject(new Message(Core.Commands.ConnSTOP));
				msg = (MessageBack)in.readObject();	
				
			} catch (IOException | ClassNotFoundException e1) {
				e1.printStackTrace();
				msg.setText("STUB:> problemi con la chiusura socket");
			}
			
		}
		// Connection DB
		@Override
		public MessageBack 	visualizza(Message M) {
			return null;
		}
		@Override
		public MessageBack modifica(Message M) {
			return null;
		}
		@Override
		public String 	testConnectionDB() {
			return null;
		}
		@Override
		public void 	closeConnectionDB() {
		}

		@Override
		public MessageBack  DBExist() {
			return null;
		}

		@Override
		public MessageBack connection(Message msg) {
			
			return null;
		}

		@Override
		public MessageBack test(Message M) throws RemoteException {
			
			return null;
		}

		public InetAddress getAddr() {
			return addr;
		}

		public void setAddr(InetAddress addr) {
			this.addr = addr;
		}

		public int getConntentativi() {
			return conntentativi;
		}

		public void setConntentativi(int conntentativi) {
			this.conntentativi = conntentativi;
		}

		public MessageBack getMsg() {
			return msg;
		}

		public void setMsg(MessageBack msg) {
			this.msg = msg;
		}

	
}