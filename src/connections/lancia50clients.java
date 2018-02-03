package connections;

import Core.Clients;
import Core.Commands;

public class lancia50clients {
Client pip;
	

	public static void main(String[] args) {
		
			for (int x=0;x <54;x++){
			
			try {
				Client p = new Client();				
				
				System.out.println("creato client no "+x);
				p.setCliType(Clients.Librarian);
				p.main(null);
				
				
				
				try {
					Thread.sleep(100);
					
					// Person				
					try {
						// ChkDBandTab.tableExistPerson();
						p.getCmdLIST().put(Commands.tableExistPerson);	
					} catch (Exception e) {
						System.out.println("appMain :> problemi con accodamento comando check table exist PERSON");					
					}
					
					
				// Book				
					try {
						// ChkDBandTab.tableExistPerson();
						p.getCmdLIST().put(Commands.tableExistBook);							
					} catch (Exception e) {
						System.out.println("appMain :> problemi con accodamento comando check table exist BOOK");					
					}
					
				} catch (Exception e) {
					// TODO: handle exception
				}
			
				
				
				
				
				p.setCliType(Clients.Guest);
				

				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}		
		
		
		
	} 
	

	
	
}
