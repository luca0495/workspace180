package gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Books.Books;
import Check.Check;
import Check.PopUp;
import Core.Clients;
import Core.Commands;
import Table.TableBooks;
import Table.TableLoans;
import Table.TableModelBooks;
import Table.TableUpdateBooks;
import connections.Client;
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

import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import java.awt.Toolkit;
import javax.swing.JComboBox;

public class ResearchBooks extends SL_JFrame {

	private Client  		me ;
	
	private ResearchBooks w;
	
	private JFrame frame;
	private JTextField textField;
	private JTextField textField2;
	private JPanel panel;
	private JPanel panelResearch;
	private JPanel panel_1;
    private JTable table4;
    
    private JTable tableBooks;
   
    
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
	private JTextField 	txtInsertNameBook;
	private JTextField 	txtInsertSurnameBook;
	private JTextField 	txtInsertCatBook;
	private JTextField 	txtInsertTitleBook;

	private int 		idbookSelected;
	
	/**
	 * Create the application.
	 * @param me2 
	 * @param me2 
	 */
    
	public ResearchBooks(Component c, Client x) {
		
		setW(this);
		me = x;
		me.setActW(this);
		me.setActC(c);
		//me.setCliType(Clients.Reader); // sicuro che sia Reader?
		
		
		
		//TODO LUCA per abilitazione bottone VISUALIZZA ELENCO PRESTITI		
		// me.getCliType()

		//IF LIBRAIO VEDI BOTTONE
		//ELSE NON LO VEDI
		
		
		initialize(c);
		super.SL_Type = AppType.AppReader;
	}

	
	public void initialize(Component c) {
		frame = new JFrame();
		frame.setBounds(100, 100, 1033, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(c);
		frame.setVisible(true);
		
		
		
		
		JPanel panelModify = new JPanel();
		panelModify.setBounds(10, 36, 997, 614);
		panelModify.setBorder(new LineBorder(new Color(0, 0, 0)));
		frame.getContentPane().add(panelModify);
		panelModify.setLayout(new CardLayout(0, 0));
		
		JPanel panelResearch = new JPanel();
		panelModify.add(panelResearch);
		panelResearch.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelResearch.setBackground(Color.WHITE);
		panelResearch.setLayout(null);		
		
		JPanel panelLoans = new JPanel();
		panelModify.add(panelLoans);
		panelLoans.setVisible(false);
		panelLoans.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelLoans.setBackground(Color.WHITE);
		panelLoans.setLayout(null);
		
		TableBooks panelTableResearch = new TableBooks(frame,me);
		
		panelTableResearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
		
				
			}
		});
		panelTableResearch.setBounds(0, 0, 995, 439);
		panelResearch.add(panelTableResearch);
		
		TableLoans panelTableLoansResearch = new TableLoans(frame,me);
		panelTableLoansResearch.setBounds(0, 11, 995, 416);
		panelLoans.add(panelTableLoansResearch);
		
		ImageIcon iconLogoA = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/Add.png")));
		ImageIcon iconLogoT = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/Tick.png")));
		ImageIcon iconLogoC = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/Cross.png")));
		ImageIcon iconLogoQ = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/question.png")));
		setIconLogoQ(iconLogoQ);
		
		JLabel lblResearch = new JLabel("Ricerca");
		lblResearch.setBounds(285, 11, 91, 14);
		frame.getContentPane().add(lblResearch);
		
		textField = new JTextField();
		textField.setBounds(337, 8, 315, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnResearch = new JButton("Ricerca");
		btnResearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			String s=textField.getText();
			    if(s.length()!=0){			 
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
		frame.getContentPane().add(btnResearch);
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
			
				askIdBook(getIdbookSelected());
				
			}
		});
		btnPrenotazione.setBounds(791, 7, 183, 23);
		frame.getContentPane().add(btnPrenotazione);
		
		JComboBox<String> comboBoxB = new JComboBox<String>();
		comboBoxB.addItem("Ricerca_Libro");
		comboBoxB.addItem("Prestiti");
		comboBoxB.setSelectedItem("Ricerca_Libro");
		comboBoxB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(comboBoxB.getSelectedItem().equals("Ricerca_Libro"))
				{
					panelTableResearch.update();
					panelResearch.setVisible(true);
					panelLoans.setVisible(false);
					
					lblEr1.setIcon(null);
					lblEr2.setIcon(null);
					lblEr3.setIcon(null);
					lblEr4.setIcon(null);
				}
				else if(comboBoxB.getSelectedItem().equals("Prestiti"))// mettere qui la di verifica lettore o libraio + query su check
				{
					
					panelTableLoansResearch.update();
					panelResearch.setVisible(false);
					panelLoans.setVisible(true);
					comboBoxB.setSelectedItem("Ricerca_Libro");
					

				//	icon a null
				}
			}
		});
		comboBoxB.setBounds(10, 443, 200, 20);
		panelResearch.add(comboBoxB);
		
		
		JComboBox<String> comboBoxL = new JComboBox<String>();
		comboBoxL.addItem("Ricerca_Libro");
		comboBoxL.addItem("Prestiti");
		comboBoxL.setSelectedItem("Prestiti");
		comboBoxL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(comboBoxL.getSelectedItem().equals("Ricerca_Libro"))
				{
					panelTableResearch.update();
					panelResearch.setVisible(true);
					panelLoans.setVisible(false);
					comboBoxL.setSelectedItem("Prestiti");
					
					lblEr1.setIcon(null);
					lblEr2.setIcon(null);
					lblEr3.setIcon(null);
					lblEr4.setIcon(null);
				}
				else if(comboBoxL.getSelectedItem().equals("Prestiti"))
				{
					panelTableLoansResearch.update();
					panelResearch.setVisible(false);
					panelLoans.setVisible(true);
					

				//	icon a null
				}
			}
		});
		comboBoxL.setBounds(10, 443, 200, 20);
		panelLoans.add(comboBoxL);
		
		
		lblEr1 = new JLabel();
		lblEr1.setBounds(69, 519, 19, 14);
		panelResearch.add(lblEr1);
		
		lblEr2 = new JLabel();
		lblEr2.setBounds(298, 519, 19, 14);
		panelResearch.add(lblEr2);
		
		lblEr3 = new JLabel();
		lblEr3.setBounds(553, 519, 19, 14);
		panelResearch.add(lblEr3);
		
		lblEr4 = new JLabel();
		lblEr4.setBounds(779, 519, 19, 14);
		panelResearch.add(lblEr4);
		
		lblPopUpCat = new JLabel();
		lblPopUpCat.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//Per informazioni cercare la classe PopUp
				PopUp.infoBox(frame,"Inserire uno tra: Romanzo,Storico,Giallo,Commedia,Fiaba,Fumetto,Narrativo,Poesia,Racconto,"
						     + "Fantasy,Azione,Avventura,Drammatico,Favola,Fantascienza,Western,Novella,Thriller,Umoristico,"
						     + "Psicologico"  );
			}
		});
		lblPopUpCat.setIcon(iconLogoQ);
		lblPopUpCat.setBounds(638, 502, 16, 16);
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
		txtName.setBounds(10, 499, 156, 20);
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
		txtSurname.setBounds(234, 499, 156, 20);
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
		txtCat.setBounds(478, 499, 156, 20);
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

		
		
		txtTitle.setBounds(708, 499, 156, 20);
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
									PopUp.errorBox(frame, "last book id non ottenuto...");							
						}else {
									newli = ++li;	
									me.setSql(MQ_Insert.insertBooksGetQuery(newli,txtName.getText(), txtSurname.getText(),txtCat.getText(),txtTitle.getText(),disp,pren_cod));													
									try {// accoda il comando alla lista comandi dalla quale legge il client
												System.out.println("GUI AppReader:> cmd inserisci LIBRO ");
										me.setCliType(Clients.Librarian);	
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
										PopUp.infoBox(frame, "Inserimento Corretto");													
						}	
					} 
				 catch (SQLException e2) {
					System.out.println("AppReader :> creazione query NG ERRORE:");	
					e2.printStackTrace();
				}
				}
				
				else//controllo sintattico non corretto... 
				{
					PopUp.errorBox(frame, "Campi Errati");
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
		
		
		
		lblAdd.setBounds(927, 504, 19, 14);
		lblAdd.setIcon(iconLogoA);
		panelResearch.add(lblAdd);
		
		JLabel lblInsertBooks = new JLabel("Inserimento Libro");
		lblInsertBooks.setBounds(369, 442, 186, 14);
		panelResearch.add(lblInsertBooks);
		
		JLabel lblInsertName = new JLabel("Nome_Autore");
		lblInsertName.setBounds(10, 474, 156, 14);
		panelResearch.add(lblInsertName);
		
		JLabel lblInsertSurname = new JLabel("Cognome_Autore");
		lblInsertSurname.setBounds(233, 474, 157, 14);
		panelResearch.add(lblInsertSurname);
		
		JLabel lblInsertCat = new JLabel("Categoria");
		lblInsertCat.setBounds(488, 474, 146, 14);
		panelResearch.add(lblInsertCat);
		
		JLabel lblInsertTitle = new JLabel("Titolo");
		lblInsertTitle.setBounds(715, 475, 149, 14);
		panelResearch.add(lblInsertTitle);
				
				
// In selezione*****************************************************************************************************
		JLabel lblLine = new JLabel();
		lblLine.setBorder(new LineBorder(new Color(0, 0, 0), 5));
		lblLine.setBounds(0, 529, 995, 1);
		panelResearch.add(lblLine);
				
		JLabel lblInsertText = new JLabel("In Selezione");
		lblInsertText.setBounds(369, 535, 160, 14);
		panelResearch.add(lblInsertText);
		
		JLabel lblCDInsert = new JLabel("Codice");
		lblCDInsert.setBounds(10, 556, 156, 14);
		panelResearch.add(lblCDInsert);
		
		JLabel lblNameInsert = new JLabel("Nome_Autore");
		lblNameInsert.setBounds(207, 560, 156, 14);
		panelResearch.add(lblNameInsert);
		
		JLabel lblSurnameInsert = new JLabel("Cognome_Autore");
		lblSurnameInsert.setBounds(379, 560, 156, 14);
		panelResearch.add(lblSurnameInsert);
		
		JLabel lblCatInsert = new JLabel("Categoria");
		lblCatInsert.setBounds(553, 560, 156, 14);
		panelResearch.add(lblCatInsert);
		
		JLabel lblTitleInsert = new JLabel("Titolo");
		lblTitleInsert.setBounds(719, 560, 156, 14);
		panelResearch.add(lblTitleInsert);
		
		txtInsertCDBook = new JTextField();
		txtInsertCDBook.setColumns(10);
		txtInsertCDBook.setBounds(10, 581, 156, 20);
		panelResearch.add(txtInsertCDBook);
		
		txtInsertNameBook = new JTextField();
		txtInsertNameBook.setColumns(10);
		txtInsertNameBook.setBounds(193, 581, 156, 20);
		panelResearch.add(txtInsertNameBook);
		
		txtInsertSurnameBook = new JTextField();
		txtInsertSurnameBook.setColumns(10);
		txtInsertSurnameBook.setBounds(369, 581, 156, 20);
		panelResearch.add(txtInsertSurnameBook);
		
		txtInsertCatBook = new JTextField();
		txtInsertCatBook.setColumns(10);
		txtInsertCatBook.setBounds(541, 581, 156, 20);
		panelResearch.add(txtInsertCatBook);
		
		txtInsertTitleBook = new JTextField();
		txtInsertTitleBook.setColumns(10);
		txtInsertTitleBook.setBounds(715, 581, 156, 20);
		panelResearch.add(txtInsertTitleBook);
		
		JButton btnReturned = new JButton("Rientrato");
		btnReturned.setBounds(896, 580, 89, 23);
		panelResearch.add(btnReturned);
		
		JButton btnDeleteBookReturned = new JButton("Cancella");
		btnDeleteBookReturned.setBounds(896, 541, 89, 23);
		panelResearch.add(btnDeleteBookReturned);
		
		
		if (	me.getCliType()==Clients.Admin||
				me.getCliType()==Clients.Librarian) {
		 // mettere qui frame
		}else {
			
			PopUp.infoBox(frame, "Reader non abilitato a visualizzare lista prestiti...");
			
			System.out.println("non inserisco combo box visualizzazione prestiti per client.type <> Librarian ");
		}
			
		
		
	}
	
	public boolean checkLastIDbook(){
		boolean checkok=true;
		
			System.out.println(" ***** sto controllando elenco libri per ottenere ultimo id libro ");
			//******************************************************************									
								try {
									me.setActW(getW());
									me.setActF(frame);
									me.setCliType(Clients.Librarian);
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
	public boolean askIdBook (int idbook){
		boolean checkok=true;
		//int idbookTEST = 1;
	try {
		System.out.println("GUI account:> ottenuti dati user ");
		me.setCliType(Clients.Librarian);
		System.err.println("passo prima del metodo idbook : "+idbook);
		me.setIdbook(idbook);
		me.getCmdLIST().put(Commands.LoanASK);
	} catch (InterruptedException e2) {
		System.out.println("GUI account:> NON ottenuti dati user ");
		checkok=false;
		e2.printStackTrace(); 
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





}
