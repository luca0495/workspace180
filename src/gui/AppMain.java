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
import Table.TableBooking;
import Table.TableBooks;
import Table.TableLoans;
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

import com.sun.org.apache.bcel.internal.generic.POP;

import Check.PopUp;

import javax.swing.JPasswordField;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JComboBox;

public class AppMain extends SL_JFrame  {
	
	private AppMain			meMain;
	
	private Client  		me ;
	private Setting 		s;
	private AppReader 		c;
	private AppLibrarian 	a;
	private boolean			ready;
	
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
	private JComboBox comboBox;
	
    private JButton btnAccount;
    private JTextField textField_3;
	/**
	 * Create the application.
	 */
	
	public AppMain() {
		initialize();		
		super.SL_Type = AppType.AppMain;	
		super.SL_Client		=null;
		addMsg("inizializzazione completata");
		setMeMain(this);
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
		setMeMain(this);
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
		
		System.out.println("testo client pre... "+me.getCliType());
		
		//PANNELLI
		
		JPanel panelLog = new JPanel();
		panelLog.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				System.err.println("testo client click... "+me.getCliType());
				
			}
			
		});

		
		getFrame().getContentPane().add(panelLog);
		panelLog.setLayout(null);
		
		System.out.println("testo client dopo... "+me.getCliType());
		
		
		ImageIcon backgroundImage0 = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/Background0.jpg")));
		ImageIcon backgroundImage1 = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/Background1.jpg")));
		
		JButton btnNewButton = new JButton("check DB exist");
		btnNewButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
		

				try {
					
					System.out.println("GUI :> comando inviato dalla gui main");//test tabelle iniziale
					Clients oldtype = me.getCliType();  
					me.setCliType(Clients.Librarian);	
					
					System.out.println("GUI :> sondo in CLI Busy prima : "+me.isBusy());	
					me.setBusy(true);		
					// Person		

					
					
						try {
							// ChkDBandTab.tableExistPerson();
							me.getCmdLIST().put(Commands.DBExist);	
						} catch (Exception e) {
							System.out.println("appMain :> problemi con accodamento comando check DBExist");					
						}
					
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
							me.setCliType(oldtype);	
							
						}

					// Loans				
						try {
							// ChkDBandTab.tableExistPerson();
							me.getCmdLIST().put(Commands.tableExistLoans);							
						} catch (Exception e) {
							System.out.println("appMain :> problemi con accodamento comando check table exist LOANS");					
							me.setCliType(oldtype);
						}
						
					// Booking				
						try {
							// ChkDBandTab.tableExistPerson();
							me.getCmdLIST().put(Commands.tableExistBooking );							
						} catch (Exception e) {
							System.out.println("appMain :> problemi con accodamento comando check table exist BOOKING");					
							me.setCliType(oldtype);
						}		
					// Setting				
						try {
							// ChkDBandTab.tableExistPerson();
							me.getCmdLIST().put(Commands.tableExistSetting);							
						} catch (Exception e) {
							System.out.println("appMain :> problemi con accodamento comando check table exist SETTING");					
							me.setCliType(oldtype);
						}	
						
						me.setCliType(oldtype);
		
				} catch (Exception e) {
					
					e.printStackTrace();	
				}	
			}
				
		});
		
		JButton btnNewButton_2 = new JButton("LOGIN/LOGOUT");
		
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton_2.addMouseListener(new MouseAdapter() {
		
			
			
			
		@Override
			public void mousePressed(MouseEvent arg0) {
			if (me.isStubok()) {
			 	//*************************************************************
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
				//************************************************************* 	 
			 }else {
				 
				 PopUp.errorBox(frame, "non collegato al Server...Attendere prego");
			 }    
			}
	 });
		
		setText(new JTextField());
		getText().setBounds(293, 30, 462, 23);
		panelLog.add(getText());
		getText().setColumns(10);
		btnNewButton_2.setBounds(134, 181, 154, 23);
		panelLog.add(btnNewButton_2);
		btnNewButton.setBounds(597, 433, 154, 23);
		panelLog.add(btnNewButton);
		
		
		
		
		
		btnAccount = new JButton("Account");
		btnAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//APRO FINESTRA ACCOUNT			
				//************************************************************		
				try {
					System.out.println("GUI AppMain:> apro account ");	
					me.getCmdLIST().put(Commands.UserREADbyEmailAcc);
				} catch (InterruptedException e2) {
					System.out.println("GUI AppMain:> apro account NG ");	
					e2.printStackTrace(); 
				}
				//***********************************************************
			}
		});
		btnAccount.setBounds(135, 227, 154, 23);
		panelLog.add(btnAccount);
		btnAccount.setVisible(false);
		
		
		JButton btnRegistrazione = new JButton("Registrazione");
		btnRegistrazione.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnRegistrazione.addMouseListener(new MouseAdapter() {
		 		
		@Override
			public void mousePressed(MouseEvent arg0) {
			
			 if (me.isStubok()) {
				 	//*************************************************************
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
					//************************************************************* 	 
				 }else {
					 
					 PopUp.errorBox(frame, "non collegato al Server...Attendere prego");
				 }
		}
	});

		
		
		btnRegistrazione.setBounds(134, 135, 154, 23);
		panelLog.add(btnRegistrazione);
								
		JButton btnEsci = new JButton("Esci");
		btnEsci.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				
				aggiornaSrvType();
				
				System.exit(0);
			}
		});
		btnEsci.setBounds(134, 433, 154, 23);
		panelLog.add(btnEsci);
		
		JButton btnRicerca = new JButton("Ricerca");
		btnRicerca.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			
			try {
				System.out.println("GUI :> comando inviato dalla gui main");//test tabelle iniziale
				System.out.println("GUI :> sondo in CLI Busy prima : "+me.isBusy());	
			} catch (Exception e) {
				e.printStackTrace();	
			}	
		}
	});
		btnRicerca.addMouseListener(new MouseAdapter() {
			 @Override
				public void mousePressed(MouseEvent arg0) {

						 if (me.isStubok()) {
						 //*************************************************************
							 setReady(false);
							 try {
							
								 	me.setMeMain(getMeMain());
								 	me.getCmdLIST().put(Commands.GetDataForTables);
									
									while (!isReady()) {
										System.out.println("attendo dati dal db...");
										Thread.sleep(100);
									}
								
							 		} catch (InterruptedException e1) {
							 		e1.printStackTrace();
							 		}
							 
					 	//*************************************************************
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
						//************************************************************* 	 
						 }else {
							 PopUp.errorBox(frame, "non collegato al Server...Attendere prego");
						 }
				 }
		});
		btnRicerca.setBounds(134, 88, 154, 23);
		panelLog.add(btnRicerca);
		
		JButton btnNewButton_1 = new JButton("SETTING");
		btnNewButton_1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				//vecchia prassi per interrompere connessione
				/*
				try {
					System.out.println(" CLICK ");
				me.setCliType(Clients.Reader);	
					me.getCmdLIST().put(Commands.ConnSTOP);
					
				} catch (InterruptedException e2) {
					System.out.println("  ");	
					e2.printStackTrace(); 
				}	
				 */
				
				EventQueue.invokeLater(new Runnable() {
					@SuppressWarnings("unchecked")
					public void run() 
					{
					 try 
					{
						 System.out.println("lancio la q");
						 String [] datasetting = MQ_Read.readSettingTable();
						 Setting s = new Setting(getFrame(), me,datasetting);
						 
						 
						 
						 /*
						 s.getTextFieldsrvIPlan().setText(datasetting[1]);
						 getS().getTextFieldsrvIPwww().setText(datasetting[2]);
						 getS().getTextFieldsrvIPdefault().setText(datasetting[3]);
						 getS().getTextFieldsrvMailAddress().setText(datasetting[4]);
						 getS().getTextFieldsrvMailPW().setText(datasetting[5]);
						 */
				    } 
					catch (Exception e) 
					{
					e.printStackTrace();
					}
				}		
				
				//lancia comando per inserire dati nei campi finestra setting	

				});			
				
				}
				
		});
		
				
				
		btnNewButton_1.setBounds(134, 374, 154, 23);
		panelLog.add(btnNewButton_1);
		
		setComboBox(new JComboBox());
		getComboBox().setForeground(new Color(255, 255, 51));
		getComboBox().setBackground(new Color(102, 51, 0));
		getComboBox().addItem("local");
		getComboBox().addItem("lan");
		getComboBox().addItem("www");

		//getComboBox().setSelectedItem("local	:localhost		172.0.0.1			");
		
		getComboBox().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Object x = getComboBox().getSelectedItem();
				aggiornaSrvType(x);	
				/*
				// gestisci cambio srvtype
				// chiusura attuale connessione
				try {
					me.getCmdLIST().put(Commands.ConnSTOP);
					Thread.sleep(20);					
				} catch (InterruptedException e2) {
					System.out.println("  ");	
					e2.printStackTrace(); 
				}					
				try {
					String [] datasetting = MQ_Read.readSettingTable();
					switch (getComboBox().getSelectedItem().toString()) {
					
					case "local":					
						getComboBox().setSelectedIndex(0);
						me.setSRVaddress(datasetting[0]);
						
						getTextField_3().setText(datasetting[0]);				
						System.out.println("selezionato srv: local");
						break;
					case "lan":
						getComboBox().setSelectedIndex(1);
						getTextField_3().setText(datasetting[1]);
						me.setSRVaddress(datasetting[1]);				//me.setSRVaddress("192.168.0.2");
						System.out.println("selezionato srv: lan");
						break;
					case "www":
						getComboBox().setSelectedIndex(2);
						getTextField_3().setText(datasetting[2]);
						me.setSRVaddress(datasetting[2]);				//me.setSRVaddress("dexa215.homepc.it");
						System.out.println("selezionato srv: www");
						break;	

					default:
						break;
					}						
				} catch (SQLException e) {
					e.printStackTrace();
				}
				*/	
			
			
			}
		});
		
		
		
		getComboBox().setBounds(135, 5, 153, 24);
		panelLog.add(getComboBox());
		
		setTextField_3(new JTextField());
		getTextField_3().setBounds(134, 31, 154, 22);
		panelLog.add(getTextField_3());
		getTextField_3().setColumns(10);
		//getTextField_3().setText("127.0.0.1");	
		
		
		
		JLabel lblBackgound1 = new JLabel();
		lblBackgound1.setBounds(0, 0, 874, 501);
		lblBackgound1.setIcon(backgroundImage1);
		lblBackgound1.setBorder(null);
		panelLog.add(lblBackgound1);
		
		JLabel lblServer = new JLabel("Server:");
		lblServer.setBackground(new Color(102, 51, 0));
		lblServer.setForeground(new Color(255, 255, 0));
		lblServer.setBounds(135, 6, 153, 23);
		panelLog.add(lblServer);
		

	
	}
	public JTextField getText() {
		return text;
	}
	public void setText(JTextField text) {
		this.text = text;
		text.setBackground(new Color(102, 51, 0));
		text.setForeground(Color.YELLOW);
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
		frame.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				
				System.out.println("tipo di client attivo ... "+me.getCliType());
				
			}
		});
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
	
	
	public void aggiornaSrvType() {
		try {
			me.getCmdLIST().put(Commands.ConnSTOP);
			Thread.sleep(20);
			
		} catch (InterruptedException e2) {
			System.out.println("  ");	
			e2.printStackTrace(); 
		}
	}
	
	public void aggiornaSrvType(Object x) {	
		// gestisci cambio srvtype
		// chiusura attuale connessione
		//Object x = getComboBox().getSelectedItem();

		try {
			
			me.setStubok(false);
			me.getCmdLIST().put(Commands.ConnSTOP);
			Thread.sleep(20);
			
		} catch (InterruptedException e2) {
			System.out.println("  ");	
			e2.printStackTrace(); 
		}	
		
		try {
			String [] datasetting = MQ_Read.readSettingTable();
			switch (getComboBox().getSelectedItem().toString()) {
			
			case "local":					
				getComboBox().setSelectedIndex(0);
				me.setSRVaddress(datasetting[0]);
				
				getTextField_3().setText(datasetting[0]);				
				System.out.println("selezionato srv: local");
				break;
			case "lan":
				getComboBox().setSelectedIndex(1);
				getTextField_3().setText(datasetting[1]);
				me.setSRVaddress(datasetting[1]);				//me.setSRVaddress("192.168.0.2");
				System.out.println("selezionato srv: lan");
				break;
			case "www":
				getComboBox().setSelectedIndex(2);
				getTextField_3().setText(datasetting[2]);
				me.setSRVaddress(datasetting[2]);				//me.setSRVaddress("dexa215.homepc.it");
				System.out.println("selezionato srv: www");
				break;	

			default:
				break;
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	
	public JButton getBtnAccount() {
		return btnAccount;
	}

	public void setBtnAccount(JButton btnAccount) {
		this.btnAccount = btnAccount;
	}

	public JTextField getTextField_3() {
		return textField_3;
	}

	public void setTextField_3(JTextField textField_3) {
		this.textField_3 = textField_3;
		textField_3.setForeground(new Color(255, 255, 0));
		textField_3.setBackground(new Color(102, 51, 0));
		textField_3.setHorizontalAlignment(SwingConstants.CENTER);
		textField_3.setEditable(false);
	}

	public Setting getS() {
		return s;
	}

	public void setS(Setting s) {
		this.s = s;
	}

	public JComboBox getComboBox() {
		return comboBox;
	}

	public void setComboBox(JComboBox comboBox) {
		this.comboBox = comboBox;
	}

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public AppMain getMeMain() {
		return meMain;
	}

	public void setMeMain(AppMain meMain) {
		this.meMain = meMain;
	}
}
