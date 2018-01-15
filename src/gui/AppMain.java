package gui;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import Core.Clients;
import Core.Commands;
import Table.TableBooks;
import Table.TableUpdateBooks;
import connections.Client;
import connections.MessageBack;
import connections.Message;
import database.ChkDBandTab;
import database.MQ_Read;

import java.awt.Label;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPasswordField;

public class AppMain extends SL_JFrame  {
	
	private Client  		me ;
	
	private AppReader 		c;
	private AppLibrarian 	a;
	
	private JFrame 			frame;
	public 	JPanel 			ac = new JPanel();
	private JTextField 		text;
	private JTextField txtResearch;
	private JTable tableBooks;
	private JTextField textField;
	private JPanel panel;
	private JPanel panelResearch;
	private JPanel panel_1;
    private TableBooks table;
    private TableUpdateBooks table1;
    private JTable table4;
    private JPasswordField passwordField;
    private JTextField textField_1;
    private JPasswordField passwordField_1;
    private JTextField textField_2;
    private JPasswordField passwordField_2;
    private JPasswordField passwordField_3;
	
	/**
	 * Create the application.
	 */
	
	public AppMain() {
		initialize();		
		super.SL_Type = AppType.AppMain;	
		super.SL_Client		=null;
		addMsg("inizializzazione completata");	
	}

	public AppMain(Client x) {
		//me = x;
		//me.setActW(this);
		//me.setCliType(Clients.Guest);
		
		super.SL_Client 	= x;
		super.SL_Client.setActW(this);
		super.SL_Client.setCliType(Clients.Guest);
		super.SL_Type	=	AppType.AppMain;
		me=super.SL_Client;
		
		initialize();
	}
	
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		setFrame(new JFrame());
		getFrame().setTitle("Schoolib");
		getFrame().setBounds(100, 100, 890, 540);
		getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getFrame().getContentPane().setLayout(new CardLayout(0, 0));
		
		//PANNELLI
		
		JPanel panelLog = new JPanel();
		getFrame().getContentPane().add(panelLog);
		panelLog.setLayout(null);
		
		
		
		ImageIcon backgroundImage0 = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/Background0.jpg")));
		ImageIcon backgroundImage1 = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/Background1.jpg")));
		
		JButton btnNewButton = new JButton("check DB exist");
		btnNewButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
		

				try {
					
					System.out.println("GUI :> comando inviato dalla gui main");//test tabelle iniziale
					
					me.setCliType(Clients.Librarian);				
					System.out.println("GUI :> sondo in CLI Busy prima : "+me.isBusy());	
					me.setBusy(true);		
					// Person				
						try {
							// ChkDBandTab.tableExistPerson();
							me.getCmdLIST().put(Commands.tableExistPerson);	
						} catch (Exception e) {
							System.out.println("appMain :> problemi con accodamento comando check table exist PERSON");					
						}
						
						
					// Book				
						try {
							// ChkDBandTab.tableExistPerson();
							me.getCmdLIST().put(Commands.tableExistBook);							
						} catch (Exception e) {
							System.out.println("appMain :> problemi con accodamento comando check table exist BOOK");					
						}

					// Loans				
						try {
							// ChkDBandTab.tableExistPerson();
							me.getCmdLIST().put(Commands.tableExistLoans);							
						} catch (Exception e) {
							System.out.println("appMain :> problemi con accodamento comando check table exist LOANS");					
						}
						
						
					
					
				/*	
				//  Loans	
					// ChkDBandTab.tableExistLoans();
					try {
						
						//ChkDBandTab.tableExistBook();
						MessageBack back = me.Request(Commands.tableExistLoans);    

						System.out.println("GUI :> risposta dal DB : "+back.getText());						
						if (back.getText()!=""){
							System.out.println("GUI :> LOANS : non ritorna nullo");

							System.out.println("GUI :> sondo in CLI Busy prima: "+me.isBusy());	
							me.setBusy(false);
							System.out.println("GUI :> sondo in CLI Busy dopo: "+me.isBusy());	
						}
					} catch (Exception e) {
						System.out.println("appMain :> problemi con table exist LOANS");
						me.setBusy(false);
					}
					*/			
				me.setCliType(Clients.Default);	
		
				} catch (Exception e) {
					
					e.printStackTrace();	
				}	
			}
				
		});
		
		JButton btnNewButton_2 = new JButton("LOGIN");
		
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton_2.addMouseListener(new MouseAdapter() {
		 @Override
			public void mousePressed(MouseEvent arg0) {
				EventQueue.invokeLater(new Runnable() {
						public void run() 
						{
						 try 
						{
						 Login lo = new Login(getFrame(), me);
					    } 
						catch (Exception e) 
						{
						e.printStackTrace();
						}
					
					}	
		
				 });    
			}
	 });
		btnNewButton_2.setBounds(124, 147, 154, 23);
		panelLog.add(btnNewButton_2);
		
		JButton btnNewButton_1 = new JButton("Attiva Account");
		
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//me.testconn();
				
			}
		});
		btnNewButton_1.setBounds(581, 181, 154, 23);
		panelLog.add(btnNewButton_1);
		btnNewButton.setBounds(31, 63, 167, 23);
		panelLog.add(btnNewButton);
		
		setText(new JTextField());
		getText().setBounds(208, 54, 628, 41);
		panelLog.add(getText());
		getText().setColumns(10);
		
		
		
		JButton btnAdmin = new JButton("Admin");
		
		btnAdmin.addMouseListener(new MouseAdapter() {
			 @Override
				public void mousePressed(MouseEvent arg0) {
					
				 
				 		EventQueue.invokeLater(new Runnable() {
							public void run() 
							{
								
							 try 
							{
							 AppLibrarian ak = new AppLibrarian(getFrame());
						    } 
							catch (Exception e) 
							{
							e.printStackTrace();
							}
								
						}	
			
					 });    
				}
		
		 });
		btnAdmin.setBounds(581, 227, 107, 23);
		panelLog.add(btnAdmin);
		
		
		JButton btnRegistrazione = new JButton("Registrazione");
		btnRegistrazione.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnRegistrazione.addMouseListener(new MouseAdapter() {
		 @Override
			public void mousePressed(MouseEvent arg0) {
				EventQueue.invokeLater(new Runnable() {
						public void run() 
						{
						 try 
						{
						 AppReader al = new AppReader(getFrame(),me);
					    } 
						catch (Exception e) 
						{
						e.printStackTrace();
						}
					
					}	
		
				 });    
			}
	 });
		
		btnRegistrazione.setBounds(124, 113, 154, 23);
		panelLog.add(btnRegistrazione);
								
		JButton btnEsci = new JButton("Esci");
		btnEsci.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnEsci.setBounds(212, 400, 129, 23);
		panelLog.add(btnEsci);
		
		JButton btnRicerca = new JButton("Ricerca");
		btnRicerca.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			
			try {
				
				
				System.out.println("GUI :> comando inviato dalla gui main");//test tabelle iniziale
				
				//me.setCliType(Clients.Librarian); // siamo sicuri che sia Librarian?
				
				System.out.println("GUI :> sondo in CLI Busy prima : "+me.isBusy());	
				//me.setBusy(true);
				
				
				
				/* OLD TEST NG
				// BookRead				
					try {
						me.getCmdLIST().put(Commands.BookREAD);	
					} catch (Exception e) {
						System.out.println("appMain :> problemi con accodamento comando per leggere database");					
					}
					
					
				// BookUpdate				
					try {
						
						me.getCmdLIST().put(Commands.BookUPDATE);							
					} catch (Exception e) {
						System.out.println("appMain :> problemi con accodamento comando per aggiornare tabella");					
					}

				// BookAdd				
					try {
						me.getCmdLIST().put(Commands.BookADD);							
					} catch (Exception e) {
						System.out.println("appMain :> problemi con accodamento comando per aggiungere libro");					
					}
					
				// BookDelete			
					try {
						me.getCmdLIST().put(Commands.BookDELETE);							
					} catch (Exception e) {
						System.out.println("appMain :> problemi con accodamento comando per eliminare libro");					
					}
				*/	
				
				
			/*	
			//  Loans	
				// ChkDBandTab.tableExistLoans();
				try {
					
					//ChkDBandTab.tableExistBook();
					MessageBack back = me.Request(Commands.tableExistLoans);    

					System.out.println("GUI :> risposta dal DB : "+back.getText());						
					if (back.getText()!=""){
						System.out.println("GUI :> LOANS : non ritorna nullo");

						System.out.println("GUI :> sondo in CLI Busy prima: "+me.isBusy());	
						me.setBusy(false);
						System.out.println("GUI :> sondo in CLI Busy dopo: "+me.isBusy());	
					}
				} catch (Exception e) {
					System.out.println("appMain :> problemi con table exist LOANS");
					me.setBusy(false);
				}
				*/			
			me.setCliType(Clients.Default);	
	
			} catch (Exception e) {
				
				e.printStackTrace();	
			}	
		}
	});
		btnRicerca.addMouseListener(new MouseAdapter() {
	 @Override
		public void mousePressed(MouseEvent arg0) {
			EventQueue.invokeLater(new Runnable() {
					public void run() 
					{
					 try 
					{
						 
						 
					 ResearchBooks rb = new ResearchBooks(getFrame(),me);
				    } 
					catch (Exception e) 
					{
					e.printStackTrace();
					}
				
				}	
	
			 });    
		}
 });
		btnRicerca.setBounds(279, 11, 89, 23);
		panelLog.add(btnRicerca);
		
		
		
		
		JLabel lblBackgound1 = new JLabel();
		lblBackgound1.setBounds(0, 0, 874, 501);
		lblBackgound1.setIcon(backgroundImage1);
		lblBackgound1.setBorder(null);
		panelLog.add(lblBackgound1);
		

	
	}
	public JTextField getText() {
		return text;
	}
	public void setText(JTextField text) {
		this.text = text;
	}
	
	
	/**
	 * Launch the application.
	 */
	
	@Override
	public void addMsg(String msg){
		text.setText(msg);	
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public void setFrame(JFrame frame) {
		this.frame = frame;
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				
				if (me!=null){
				me.closeconn();
				}
			}
		});
	}


	
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					Client x = new Client();
					
					AppMain window = new AppMain(x);					
					window.frame.setVisible(true);					
					
					new Thread(x).start();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
