package gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Popup;
import javax.swing.RowFilter;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.sun.org.apache.bcel.internal.generic.POP;


import Check.Check;
import Check.PopUp;
import Core.ClientCMDuser;
import Core.Clients;
import Core.Commands;
import Table.TableBooking;
import Table.TableBooks;
import Table.TableLoans;
import Table.TableModelBooks;
import Table.TableUpdateBooks;
import connections.Client;
import connections.ClientConnectionController;
import database.DBmanager;
import database.MQ_Insert;
import database.MQ_Read;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import java.awt.Toolkit;
import javax.swing.JComboBox;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;


public class ResearchBooks extends SL_JFrame {

	private Client  		me ;
	private static ClientConnectionController ccc;
	private ResearchBooks 	w;
	private JFrame frame;
	
	private JTextField textField;
	private JTextField textField2;
	private JPanel panel;
	private JPanel panelResearch;
	private JPanel panel_1;
    private JTable table4;
    
    private JButton button_1;//tasto riconsegna
    
    private JTable tableBooks;
   
    private JRadioButton rdbtnStoricoPrenotazioni;
    
	protected String 	ValToSearch;
	private JTextField 	txtName;
	private JTextField 	txtSurname;
	private JTextField 	txtCat;
	private JTextField 	txtTitle;
	private JLabel 		lblAdd;
	private JLabel 		lblEr1;
	private JLabel 		lblEr2;
	private JLabel 		lblEr3;
	private JLabel 		lblEr4;
    private JLabel  	lblPopUpCat;
	private JButton 	btnPrenotazione;
	private ImageIcon 	iconLogoQ;
	
	private boolean 	LastIDbookcheckinprogress=false;
	private int 		LastIDbookcheckResult;
	private JTextField 	txtInsertCDBook;

	private TableBooks 		panelTableResearch;
	private TableLoans 		panelTableLoansResearch;
	private TableBooking	panelTableBookingResearch;
	
	private Date			datarichiesta;

	private int 		idbookSelected;
	private JTextField  Datariconsegna;
	
	private boolean storico=false;
	
	/**
	 * Create the application.
	 * @param me2 
	 * @param me2 
	 * @throws InterruptedException 
	 */
    
	public ResearchBooks(Component c, Client x) throws InterruptedException {
		
		setW(this);
		
		me = x;
		me.setActC(c);
		me.setActF(this);
		me.setActW(this);
		me.setMeRes(this);
		
		//me.setCliType(Clients.Reader); // sicuro che sia Reader?
		//TODO LUCA per abilitazione bottone VISUALIZZA ELENCO PRESTITI		
		// me.getCliType()
		//IF LIBRAIO VEDI BOTTONE
		//ELSE NON LO VEDI
		initialize(c);
		super.SL_Type = AppType.AppReader;
		super.setModel("search");
	}

	
	public void initialize(Component c) throws InterruptedException {
		
		
		setFrame(new JFrame());
		getFrame().setBounds(100, 100, 1033, 700);
		getFrame().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getFrame().getContentPane().setLayout(null);
		getFrame().setLocationRelativeTo(c);
		getFrame().setVisible(true);
		
		//frmSchoolib.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//frmSchoolib.setLocationRelativeTo(c);
		//frmSchoolib.setVisible(true);
		//frmSchoolib.getContentPane().setLayout(new CardLayout(0, 0));
		
		JPanel panelModify = new JPanel();
		panelModify.setBounds(10, 36, 997, 614);
		panelModify.setBorder(new LineBorder(new Color(0, 0, 0)));
		getFrame().getContentPane().add(panelModify);
		panelModify.setLayout(new CardLayout(0, 0));
		
		JPanel panelResearch = new JPanel();
		me.setMePannelBook(panelResearch);
		panelModify.add(panelResearch);
		panelResearch.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelResearch.setBackground(Color.WHITE);
		panelResearch.setLayout(null);		
		
		JPanel panelLoans = new JPanel();
		me.setMePannelLoans(panelLoans);
		panelModify.add(panelLoans);
		panelLoans.setVisible(false);
		panelLoans.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelLoans.setBackground(Color.WHITE);
		panelLoans.setLayout(null);
		
		JPanel panelBooking = new JPanel();
		me.setMePannelBooking(panelBooking);
		panelModify.add(panelBooking);
		panelBooking.setVisible(false);
		panelBooking.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelBooking.setBackground(Color.WHITE);
		panelBooking.setLayout(null);
		
		panelTableResearch = new TableBooks(getW(),me);//PASSO RESEARCHBOOK
		panelTableResearch.setBounds(0, 11, 995, 416);
		panelResearch.add(panelTableResearch);
		
		panelTableLoansResearch = new TableLoans(getW(),me);
		panelTableLoansResearch.setBounds(0, 11, 995, 416);
		panelLoans.add(panelTableLoansResearch);
		
		JButton button = new JButton("CancellaPrenotazione");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
				
					//TODO prelevare idbook idutente 
					me.getCmdLIST().put(Commands.BookingListREMOVE);
				
				
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			
			}
		});
		button.setBounds(719, 438, 240, 51);
		panelBooking.add(button);
		
		panelTableBookingResearch = new TableBooking(getFrame(),me);
		panelTableBookingResearch.setBounds(0, 11, 995, 416);
		panelBooking.add(panelTableBookingResearch);
		
		ImageIcon iconLogoA = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/Add.png")));
		ImageIcon iconLogoT = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/Tick.png")));
		ImageIcon iconLogoC = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/Cross.png")));
		ImageIcon iconLogoQ = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/question.png")));
		setIconLogoQ(iconLogoQ);
		
		JLabel lblResearch = new JLabel("Ricerca");
		lblResearch.setBounds(285, 11, 91, 14);
		getFrame().getContentPane().add(lblResearch);
		
		JButton btnReturnBack = new JButton("Chiudi");
		btnReturnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				 WindowEvent close = new WindowEvent(getFrame(), WindowEvent.WINDOW_CLOSING);
				 getFrame().dispatchEvent(close);
				 
				 me.getStartWindow().getFrame().setVisible(true);	//System.out.println("creato start windows");
				 me.getStartWindow().addMsg("ResearchBook closed...");
				 me.setActW(me.getMeMain());
				

			}
		});
		btnReturnBack.setBounds(10, 7, 89, 23);
		getFrame().getContentPane().add(btnReturnBack);
		
		setTextField(new JTextField());
		getTextField().setBounds(337, 8, 315, 20);
		getFrame().getContentPane().add(getTextField());
		getTextField().setColumns(10);
		
		JButton btnResearch = new JButton("Ricerca");
		btnResearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			String s=getTextField().getText();
			
				if(s.length()!=0){		
					
					 System.err.println("cli query in esecuzione s "+s);
					 
					 
			    	//TODO PASSA A CLIENT da TableBooks		 				 
			    	//me.getCmdLIST().put(Commands.tableBookPopulate);
			    				try {	 	 
			    				TableBooks.PopulateData(s,me);	
			    				

			    				} catch (SQLException e1) {
			    					e1.printStackTrace();	
			    				} catch (InterruptedException e1) {				
			    					e1.printStackTrace();
			    				}			
			    }else{
			    	//TODO PASSA A CLIENT da TableBooks	
			    	//me.getCmdLIST().put(Commands.tableBookPopulate);	
			    	
			    	
			    	 System.err.println("cli query in esecuzione s==0");
			    	
			    			      try {
			    					
			    			    TableBooks.PopulateData("",me);
			    			    
			    
			    			      } catch (SQLException e1) {
			    					e1.printStackTrace();
			    				} catch (InterruptedException e1) {				
			    					e1.printStackTrace();
			    				}
			    			     
			    }
	    
	   }
   });
		btnResearch.setBounds(679, 7, 89, 23);
		getFrame().getContentPane().add(btnResearch);
		/*
		tableBooks = new JTable();
		tableBooks.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"codice", "nome_autore", "cognome_autore", "categoria", "titolo"
			}
		));
		tableBooks.setColumnSelectionAllowed(true);
		tableBooks.setCellSelectionEnabled(true);
		tableBooks.setBounds(10, 11, 837, 420);
		*/
		
		btnPrenotazione = new JButton("Prenotazione Libro");
		btnPrenotazione.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				if (askIdBook()) {		//	test login e test prenotazione con 
										//	me.iduser
										//	me.idbook
					
				//PopUp.infoBox(frame, "PRESTITO LIBRO "+me.getSelectedIdBook()+"ASSEGNATO ALL'UTENTE "+me.getSelectedIdUser());	
				
				};
				
			}
		});
		btnPrenotazione.setBounds(791, 7, 183, 23);
		getFrame().getContentPane().add(btnPrenotazione);
		
		JComboBox<String> comboBoxB = new JComboBox<String>();
		comboBoxB.addItem("Ricerca_Libro");
		comboBoxB.addItem("Prestiti");
		comboBoxB.addItem("Prenotazioni");
		comboBoxB.setSelectedItem("Ricerca_Libro");
		comboBoxB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(comboBoxB.getSelectedItem().equals("Ricerca_Libro"))
				{
					//panelTableResearch.update();
					panelResearch.setVisible(	true);
					panelLoans.setVisible(		false);
					panelBooking.setVisible(	false);
					
					ClientConnectionController.ONEcontrol(me);
					
					System.out.println("7" + comboBoxB.toString());
					lblEr1.setIcon(null);
					lblEr2.setIcon(null);
					lblEr3.setIcon(null);
					lblEr4.setIcon(null);
				}
				else if(comboBoxB.getSelectedItem().equals("Prestiti"))// mettere qui la di verifica lettore o libraio + query su check
				{
					
					//panelTableLoansResearch.update();
					panelBooking.setVisible(false);
					panelResearch.setVisible(false);
					panelLoans.setVisible(true);
					comboBoxB.setSelectedItem("Ricerca_Libro");
					//comboBoxB.setSelectedItem("Prenotazioni");
					
					ClientConnectionController.ONEcontrol(me);
					
					System.out.println("8" + comboBoxB.toString());

				//	icon a null
			    }
			   else if(comboBoxB.getSelectedItem().equals("Prenotazioni"))// mettere qui la di verifica lettore o libraio + query su check
			     {
				
				//panelTableBookingResearch.update();
				panelResearch.setVisible(false);
				panelLoans.setVisible(false);
				panelBooking.setVisible(true);
				comboBoxB.setSelectedItem("Ricerca_Libro");
				
				ClientConnectionController.ONEcontrol(me);
				
				System.out.println("9" + comboBoxB.toString());

			//	icon a null
			}
		}
				
		
			
	});
		comboBoxB.setBounds(31, 443, 324, 20);
		panelResearch.add(comboBoxB);
		
		
		JComboBox<String> comboBoxL = new JComboBox<String>();
		comboBoxL.addItem("Ricerca_Libro");
		comboBoxL.addItem("Prestiti");
		comboBoxL.addItem("Prenotazioni");
		comboBoxL.setSelectedItem("Prestiti");
		comboBoxL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(comboBoxL.getSelectedItem().equals("Ricerca_Libro"))
				{
					//panelTableResearch.update();
					panelResearch.setVisible(true);
					panelLoans.setVisible(false);
					panelBooking.setVisible(false);
					comboBoxL.setSelectedItem("Prestiti");
					//comboBoxL.setSelectedItem("Prenotazioni");
					System.out.println("1" + comboBoxL.toString());
					lblEr1.setIcon(null);
					lblEr2.setIcon(null);
					lblEr3.setIcon(null);
					lblEr4.setIcon(null);
					
					ClientConnectionController.ONEcontrol(me);
				}
				else if(comboBoxL.getSelectedItem().equals("Prestiti"))
				{
					//panelTableLoansResearch.update();
					panelBooking.setVisible(false);
					panelResearch.setVisible(false);
					panelLoans.setVisible(true);

					System.out.println("2" + comboBoxL.toString());
					
					ClientConnectionController.ONEcontrol(me);

				//	icon a null
				}
				else if(comboBoxL.getSelectedItem().equals("Prenotazioni"))
				{
					//panelTableBookingResearch.update();
					panelResearch.setVisible(false);
					panelLoans.setVisible(false);
					panelBooking.setVisible(true);
					comboBoxL.setSelectedItem("Prestiti");
					System.out.println("3" + comboBoxL.toString());
					
					ClientConnectionController.ONEcontrol(me);
					
				//	icon a null
				}
			}
		});
		comboBoxL.setBounds(10, 443, 200, 20);
		panelLoans.add(comboBoxL);
		
		JButton btnAggiornaLoansTest = new JButton("REFRESH");
		btnAggiornaLoansTest.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
			//PopUp.infoBox(comboBoxL, "REFRESH");
			
			try {	 	 
				
				TableLoans.PopulateData("",me);

				} catch (SQLException e1) {
					e1.printStackTrace();	
				} catch (InterruptedException e1) {				
					e1.printStackTrace();
				}	
			}
		});
		btnAggiornaLoansTest.setBounds(10, 477, 200, 23);
		panelLoans.add(btnAggiornaLoansTest);
		
		
		JButton btnAggiornabookingTest = new JButton("REFRESH");
		btnAggiornabookingTest.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
			//PopUp.infoBox(comboBoxL, "REFRESH");
			
			try {	 	 
				
				TableBooking.PopulateData("",me);

				} catch (SQLException e1) {
					e1.printStackTrace();	
				} catch (InterruptedException e1) {				
					e1.printStackTrace();
				}	
			}
		});
		btnAggiornabookingTest.setBounds(10, 477, 200, 23);
		panelBooking.add(btnAggiornabookingTest);
		
		
		
		
		
		rdbtnStoricoPrenotazioni = new JRadioButton("Storico Prenotazioni");
		
		rdbtnStoricoPrenotazioni.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				
				
				me.setStorico(getRdbtnStoricoPrenotazioni().isSelected());
				
			}
		});
		rdbtnStoricoPrenotazioni.setBounds(342, 442, 142, 23);
		panelLoans.add(rdbtnStoricoPrenotazioni);
		
		
		
		
		
		
		JComboBox<String> comboBoxBooking = new JComboBox<String>();
		comboBoxBooking.addItem("Ricerca_Libro");
		comboBoxBooking.addItem("Prestiti");
		comboBoxBooking.addItem("Prenotazioni");
		comboBoxBooking.setSelectedItem("Prenotazioni");
		comboBoxBooking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(comboBoxBooking.getSelectedItem().equals("Ricerca_Libro"))
				{
					//panelTableResearch.update();
					panelResearch.setVisible(true);
					panelLoans.setVisible(false);
					panelBooking.setVisible(false);
					comboBoxBooking.setSelectedItem("Prenotazioni");
					System.out.println("4" + comboBoxBooking.toString());
					
					lblEr1.setIcon(null);
					lblEr2.setIcon(null);
					lblEr3.setIcon(null);
					lblEr4.setIcon(null);
					
					ClientConnectionController.ONEcontrol(me);
				}
				else if(comboBoxBooking.getSelectedItem().equals("Prestiti"))
				{
					//panelTableLoansResearch.update();
					panelResearch.setVisible(false);
					panelLoans.setVisible(true);
					panelBooking.setVisible(false);
					comboBoxBooking.setSelectedItem("Prenotazioni");
					System.out.println("5" + comboBoxBooking.toString());
				//	icon a null
					
					ClientConnectionController.ONEcontrol(me);
				}
				else if(comboBoxBooking.getSelectedItem().equals("Prenotazioni"))
				{
					//panelTableBookingResearch.update();
					panelResearch.setVisible(false);
					panelLoans.setVisible(false);
					panelBooking.setVisible(true);

					System.out.println("6" + comboBoxBooking.toString());
					
					ClientConnectionController.ONEcontrol(me);

				//	icon a null
				}
			}
		});
		comboBoxBooking.setBounds(10, 443, 200, 20);
		panelBooking.add(comboBoxBooking);
		
		
		lblEr1 = new JLabel();
		lblEr1.setBounds(688, 516, 19, 14);
		panelResearch.add(lblEr1);
		
		lblEr2 = new JLabel();
		lblEr2.setBounds(859, 516, 19, 14);
		panelResearch.add(lblEr2);
		
		lblEr3 = new JLabel();
		lblEr3.setBounds(542, 438, 19, 14);
		panelResearch.add(lblEr3);
		
		lblEr4 = new JLabel();
		lblEr4.setBounds(542, 475, 19, 14);
		panelResearch.add(lblEr4);
		
		lblPopUpCat = new JLabel();
		lblPopUpCat.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//Per informazioni cercare la classe PopUp
				PopUp.infoBox(getFrame(),"Inserire uno tra: Romanzo,Storico,Giallo,Commedia,Fiaba,Fumetto,Narrativo,Poesia,Racconto,"
						     + "Fantasy,Azione,Avventura,Drammatico,Favola,Fantascienza,Western,Novella,Thriller,Umoristico,"
						     + "Psicologico"  );
			}
		});
		lblPopUpCat.setIcon(iconLogoQ);
		lblPopUpCat.setBounds(571, 469, 16, 16);
		panelResearch.add(lblPopUpCat);
		
		txtName = new JTextField();
		txtName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				
				if(Check.checkName(txtName.getText()))
				{
					lblEr1.setIcon(iconLogoT);
				}
				else
				{
					lblEr1.setIcon(iconLogoC);
				}
			}
		});	
		txtName.setBounds(629, 494, 156, 20);
		panelResearch.add(txtName);
		txtName.setColumns(10);
		
		txtSurname = new JTextField();
		txtSurname.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				
				if(Check.checkName(txtSurname.getText()))
				{
					lblEr2.setIcon(iconLogoT);
				}
				else
				{
					lblEr2.setIcon(iconLogoC);
				}
			}
		});
		txtSurname.setBounds(795, 494, 156, 20);
		panelResearch.add(txtSurname);
		txtSurname.setColumns(10);
		
		txtCat = new JTextField();
		txtCat.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				
				if(Check.checkCat(txtCat.getText()))
				{
					lblEr3.setIcon(iconLogoT);
				}
				else
				{
					lblEr3.setIcon(iconLogoC);
				}
			}
		});
		txtCat.setBounds(629, 438, 156, 20);
		panelResearch.add(txtCat);
		txtCat.setColumns(10);
		
		txtTitle = new JTextField();
		txtTitle.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				// mettere check sia con lettere che numeri(check name solo lettere)
				if(Check.checkTitle(txtTitle.getText()))
				{
					lblEr4.setIcon(iconLogoT);
				}
				else
				{
					lblEr4.setIcon(iconLogoC);
				}
			}
		});

		
		
		txtTitle.setBounds(629, 469, 322, 20);
		panelResearch.add(txtTitle);
		txtTitle.setColumns(10);
		
		lblAdd = new JLabel();
		System.out.println("1" );
		lblAdd.addMouseListener(new MouseAdapter() {
			@Override
	
	
			
			public void mousePressed(MouseEvent arg0) {				
				if(Check.checkAllBooks(txtName.getText(), txtSurname.getText(),txtCat.getText(),txtTitle.getText()))//controllo sintattico
				{
					try 
					{
						System.out.println("5" );	
						String disp = "Libero";
					    int pren_cod =0;
						System.out.println("inserimento nuovo libro: Client:" + me);						
						System.out.println("ricerca ultimo id assegnato");
						//in TEST 
						setLastIDbookcheckinprogress(true);
						//parte check last id user...
						checkLastIDbook();

						while (isLastIDbookcheckinprogress()) {	//attendi... //System.out.println("attesa per check LAST ID");		
							System.out.println("attendo check result"+getLastIDbookcheckResult());
							try {
								Thread.sleep(10);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						//---------------------------------------------------------------------------------------------------
						System.out.println("ritornato last id check result"+getLastIDbookcheckResult());
						int li = (int)getLastIDbookcheckResult();
						int newli;						
						if (li==0) {//id non ottenuto...
									newli = 0;
									PopUp.errorBox(getFrame(), "last book id non ottenuto...");							
						}else {
									newli = ++li;	
									me.setSql(MQ_Insert.insertBooksGetQuery(newli,txtName.getText(), txtSurname.getText(),txtCat.getText(),txtTitle.getText(),disp,pren_cod));													
									try {// accoda il comando alla lista comandi dalla quale legge il client
												System.out.println("GUI AppReader:> cmd inserisci LIBRO ");
									//	me.setCliType(Clients.Librarian);	
										me.getCmdLIST().put(Commands.BookADD);
									} catch (InterruptedException e2) {
												System.out.println("AppReader :> problemi con inserimento nuovo libro");	
										e2.printStackTrace();
									}				
									//pulizia campi di inserimento dati libro	
										System.out.println("6" );
										txtName.setText(null);
										txtSurname.setText(null);
										txtCat.setText(null);
										txtTitle.setText(null);
										System.out.println("7" );
										lblEr1.setIcon(null);
										lblEr2.setIcon(null);
										lblEr3.setIcon(null);
										lblEr4.setIcon(null);
										System.out.println("8");
										
										//panelTableResearch.update();
										
										System.out.println("9");
										PopUp.infoBox(getFrame(), "Inserimento Corretto");													
						}	
					} 
				 catch (SQLException e2) {
					System.out.println("AppReader :> creazione query NG ERRORE:");	
					e2.printStackTrace();
				}
				}
				
				else//controllo sintattico non corretto... 
				{
					PopUp.errorBox(getFrame(), "Campi Errati");
					System.out.println("9");
					if(Check.checkName(txtName.getText()))
					{
						System.out.println("10");
						lblEr1.setIcon(iconLogoT);
					}
					else
					{
						lblEr1.setIcon(iconLogoC);
					}

					if(Check.checkName(txtSurname.getText()))
					{
						lblEr2.setIcon(iconLogoT);
					}
					else
					{
						lblEr2.setIcon(iconLogoC);
					}
					
					if(Check.checkCat(txtCat.getText()))
					{
						lblEr3.setIcon(iconLogoT);
					}
					else
					{
						lblEr3.setIcon(iconLogoC);
					}
					
					if(Check.checkName(txtTitle.getText()))
					{
						lblEr4.setIcon(iconLogoT);
					}
					else
					{
						lblEr4.setIcon(iconLogoC);
					}
				}
			}
		});
		
		
		
		lblAdd.setBounds(932, 443, 19, 14);
		lblAdd.setIcon(iconLogoA);
		panelResearch.add(lblAdd);
		
		JLabel lblInsertBooks = new JLabel("Inserimento Libro");
		lblInsertBooks.setBounds(807, 438, 144, 22);
		panelResearch.add(lblInsertBooks);
		
		JLabel lblInsertName = new JLabel("Autore");
		lblInsertName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblInsertName.setBounds(542, 497, 77, 14);
		panelResearch.add(lblInsertName);
		
		JLabel lblInsertCat = new JLabel("Categoria");
		lblInsertCat.setHorizontalAlignment(SwingConstants.RIGHT);
		lblInsertCat.setBounds(567, 441, 56, 14);
		panelResearch.add(lblInsertCat);
		
		JLabel lblInsertTitle = new JLabel("Titolo");
		lblInsertTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		lblInsertTitle.setBounds(561, 469, 62, 14);
		panelResearch.add(lblInsertTitle);
				
				
// In selezione*****************************************************************************************************
		JLabel lblLine = new JLabel();
		lblLine.setBorder(new LineBorder(new Color(0, 0, 0), 5));
		lblLine.setBounds(0, 529, 995, 1);
		panelResearch.add(lblLine);
				
		JLabel lblInsertText = new JLabel("In Selezione");
		lblInsertText.setHorizontalAlignment(SwingConstants.CENTER);
		lblInsertText.setBounds(361, 561, 136, 14);
		panelResearch.add(lblInsertText);
		
		JLabel lblCDInsert = new JLabel("Codice");
		lblCDInsert.setHorizontalAlignment(SwingConstants.CENTER);
		lblCDInsert.setBounds(275, 540, 80, 14);
		panelResearch.add(lblCDInsert);
		
		
		
		
		
		
		
		//
		
		
		
		button_1 = new JButton("Riconsegna");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String dataC = getDatariconsegna().getText();
				if (dataC.equals("")||dataC==null||me.getSelectedIdBook()==0){
					//campo nullo
				
				}else {
					
					//if (checkDate(dataC)) {
						PopUp.infoBox(frame, 
								"  idL "+me.getSelectedIdBook()+
								"  idU "+me.getSelectedIdUser()+
								"  la data di consegna richiesta : "+dataC );
						
						try {
										me.setDataLoanReturn(dataC);							
										me.getCmdLIST().put(Commands.LoanReturn);
						} catch (InterruptedException e1) {e1.printStackTrace();}
						
					//}else {
						
						//PopUp.infoBox(btnReturnBack,"la data inserita, "+dataC+" non é nel formato correttto" );
					//}
				}
			}
		});
		button_1.setBounds(31, 557, 110, 23);
		panelResearch.add(button_1);
		
		txtInsertCDBook = new JTextField();
		txtInsertCDBook.setHorizontalAlignment(SwingConstants.CENTER);
		txtInsertCDBook.setColumns(10);
		txtInsertCDBook.setBounds(275, 558, 86, 20);
		panelResearch.add(txtInsertCDBook);
		
		JButton btnDeleteBookReturned = new JButton("Cancella");
		btnDeleteBookReturned.setBounds(816, 557, 98, 23);
		panelResearch.add(btnDeleteBookReturned);

		//Data riconsegna
		Datariconsegna = new JTextField();
		Datariconsegna.setHorizontalAlignment(SwingConstants.CENTER);
		Datariconsegna.setColumns(10);
		Datariconsegna.setBounds(151, 560, 98, 20);
		panelResearch.add(Datariconsegna);
		
		//setta data 
		Date datacorrente = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		getDatariconsegna().setText(formatter.format(datacorrente));

		
		JLabel lblDataRiconsegna = new JLabel("Data Riconsegna");
		lblDataRiconsegna.setHorizontalAlignment(SwingConstants.CENTER);
		lblDataRiconsegna.setBounds(151, 540, 98, 14);
		panelResearch.add(lblDataRiconsegna);
		
		/*
		if (	me.getCliType()==Clients.Admin||
				me.getCliType()==Clients.Librarian) {
		 // mettere qui frame
		}else {
			
			PopUp.infoBox(frame, "Reader non abilitato a visualizzare lista prestiti...");
			
			System.out.println("non inserisco combo box visualizzazione prestiti per client.type <> Librarian ");
		}
			
		*/
		
	}
	
	public boolean checkLastIDbook(){
		boolean checkok=true;
		
			System.out.println(" ***** sto controllando elenco libri per ottenere ultimo id libro ");
			//******************************************************************									
								try {
									me.setActW(getW());
									me.setActF(getFrame());
									//me.setCliType(Clients.Librarian);
									me.getCmdLIST().put(Commands.BookLast);
									
								} catch (InterruptedException e2) {
									System.out.println("GUI account:> NON ottenuti dati last book id  ");	
									e2.printStackTrace(); 
									setLastIDbookcheckResult(0);//0==id non ottenuto
								}
			//******************************************************************
			return checkok;
	}

//********************************************************	
	public boolean askIdBook(){
		boolean checkok=true;
		int idbook = me.getSelectedIdBook();
		int iduser = me.getIdut();//UTENTE LOGGATO		
		if (idbook == 0) {
			checkok=false;
			PopUp.errorBox(getFrame(), "NESSUN LIBRO SELEZIONATO ");			
		}else {
					//controllo LOGIN	
					if (iduser == 0) {
						checkok=false;
						PopUp.errorBox(getFrame(), "EFFETTUARE IL LOGIN PER RICHIEDERE UN PRESTITO ");								
					}else
					{//LOGIN OK
						try {				
							me.setSelectedIdUser(me.getIdut());				
							System.out.println("GUI account:> ottenuti dati user ");							

						//me.setCliType(Clients.Librarian);							
							System.err.println("passo prima del metodo idbook : "+idbook);
							me.setIdbook(idbook);
							me.getCmdLIST().put(Commands.LoanASK);	
						} catch (InterruptedException e2) {
							System.out.println("GUI account:> NON ottenuti dati user ");
							checkok=false;
							e2.printStackTrace(); 
						}
						}			
			
		}
		
		
		

		return checkok;
		}
//********************************************************	
	
	
	
	
	
//*****************************************************************************************************************	
	public JTextField getTxtName() {
		return txtName;
	}


	public void setTxtName(JTextField txtName) {
		this.txtName = txtName;
	}


	public JTextField getTxtSurname() {
		return txtSurname;
	}


	public void setTxtSurname(JTextField txtSurname) {
		this.txtSurname = txtSurname;
	}


	public JTextField getTxtCat() {
		return txtCat;
	}


	public void setTxtCat(JTextField txtCat) {
		this.txtCat = txtCat;
	}


	public JTextField getTxtTitle() {
		return txtTitle;
	}


	public void setTxtTitle(JTextField txtTitle) {
		this.txtTitle = txtTitle;
	}
	
	
	public boolean isLastIDbookcheckinprogress() {
		return LastIDbookcheckinprogress;
	}


	public void setLastIDbookcheckinprogress(boolean lastIDbookcheckinprogress) {
		LastIDbookcheckinprogress = lastIDbookcheckinprogress;
	}


	public int getLastIDbookcheckResult() {
		return LastIDbookcheckResult;
	}


	public void setLastIDbookcheckResult(int lastIDbookcheckResult) {
		LastIDbookcheckResult = lastIDbookcheckResult;
	}

public void findBooks(String query){
	TableRowSorter<DefaultTableModel> tr=new TableRowSorter<DefaultTableModel>();
	tableBooks.setRowSorter(tr);
	
	tr.setRowFilter(RowFilter.regexFilter(query));
}


public ResearchBooks getW() {
	return w;
}


public void setW(ResearchBooks w) {
	this.w = w;
}


public ImageIcon getIconLogoQ() {
	return iconLogoQ;
}


public void setIconLogoQ(ImageIcon iconLogoQ) {
	this.iconLogoQ = iconLogoQ;
}


public int getIdbookSelected() {
	return idbookSelected;
}


public void setIdbookSelected(int idbookSelected) {
	this.idbookSelected = idbookSelected;
}


public JTextField getTxtInsertCDBook() {
	return txtInsertCDBook;
}


public void setTxtInsertCDBook(JTextField txtInsertCDBook) {
	this.txtInsertCDBook = txtInsertCDBook;
}
public TableBooks getPanelTableResearch() {
	return panelTableResearch;
}


public void setPanelTableResearch(TableBooks panelTableResearch) {
	this.panelTableResearch = panelTableResearch;
}


public TableLoans getPanelTableLoansResearch() {
	return panelTableLoansResearch;
}


public void setPanelTableLoansResearch(TableLoans panelTableLoansResearch) {
	this.panelTableLoansResearch = panelTableLoansResearch;
}


public TableBooking getPanelTableBookingResearch() {
	return panelTableBookingResearch;
}


public void setPanelTableBookingResearch(TableBooking panelTableBookingResearch) {
	this.panelTableBookingResearch = panelTableBookingResearch;
}


public JFrame getFrame() {
	return frame;
}


public void setFrame(JFrame frame) {
	this.frame = frame;
}


public JTextField getTextField() {
	return textField;
}


public void setTextField(JTextField textField) {
	this.textField = textField;
}


public JTextField getDatariconsegna() {
	return Datariconsegna;
}


public void setDatariconsegna(JTextField datariconsegna) {
	this.Datariconsegna = datariconsegna;
}


public Date getDatarichiesta() {
	return datarichiesta;
}


public void setDatarichiesta(Date datarichiesta) {
	this.datarichiesta = datarichiesta;
}

public static boolean checkDate(String d) 
{	
	boolean valid = false;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    formatter.setLenient(false);
    Date date = new Date();
    try 
    { 	
    	Date parsedDate = formatter.parse(d);
    	if (date.equals((parsedDate)))
    	{
    		valid = true;
    	}
    	else if(date.before(parsedDate)) 
    	{
    		valid = true;
    	}
    	else
    	{
    		valid = false;
    	}
    } 
    catch (ParseException e)
    {
    	valid = false;
    }
    
    return valid;
}


public JRadioButton getRdbtnStoricoPrenotazioni() {
	return rdbtnStoricoPrenotazioni;
}


public void setRdbtnStoricoPrenotazioni(JRadioButton rdbtnStoricoPrenotazioni) {
	this.rdbtnStoricoPrenotazioni = rdbtnStoricoPrenotazioni;
}


public boolean isStorico() {
	return storico;
}


public void setStorico(boolean storico) {
	this.storico = storico;
}


public JButton getButton_1() {
	return button_1;
}


public void setButton_1(JButton button_1) {
	this.button_1 = button_1;
}
}
