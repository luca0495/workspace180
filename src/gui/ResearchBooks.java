package gui;

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

public class ResearchBooks extends SL_JFrame {

	private Client  		me ;
	
	private JFrame frame;
	private JTextField textField;
	private JTextField textField2;
	private JPanel panel;
	private JPanel panelResearch;
	private JPanel panel_1;
    private JTable table4;
    
    private JTable tableBooks;
    
	protected String ValToSearch;
	private JTextField txtCod;
	private JTextField txtName;
	private JTextField txtSurname;
	private JTextField txtCat;
	private JTextField txtTitle;
	private JLabel lblAdd;
	private JLabel lblEr1;
	private JLabel lblEr2;
	private JLabel lblEr3;
	private JLabel lblEr4;
	private JTextField txtLoans;
	private JButton btnPrenotazione;
	/**
	 * Create the application.
	 * @param me2 
	 * @param me2 
	 */
    
	public ResearchBooks(Component c, Client x) {
		
		me = x;
		me.setActW(this);
		me.setActC(c);
		//me.setCliType(Clients.Reader); // sicuro che sia Reader?
		
		
		
		//TODO		
		// me.getCliType()
		
		

		//IF LIBRAIO VEDI BOTTONE
		//ELSE NON LO VEDI
		
		
		
		
		
		initialize(c);
		super.SL_Type = AppType.AppReader;
	}

	
	public void initialize(Component c) {
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(c);
		frame.setVisible(true);
		
		ImageIcon iconLogoA = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/Add.png")));
		ImageIcon iconLogoT = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/Tick.png")));
		ImageIcon iconLogoC = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/Cross.png")));
		
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
		btnPrenotazione.setBounds(791, 7, 183, 23);
		frame.getContentPane().add(btnPrenotazione);
		JPanel panelResearch = new JPanel();
		panelResearch.setBounds(10, 36, 964, 470);
		panelResearch.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelResearch.setBackground(Color.WHITE);
		frame.getContentPane().add(panelResearch);
		panelResearch.setLayout(null);		
		
		TableBooks panelTableResearch = new TableBooks(frame,me);
		panelTableResearch.setBounds(10, 11, 944, 420);
		panelResearch.add(panelTableResearch);
		
		lblEr1 = new JLabel();
		lblEr1.setBounds(234, 450, 19, 14);
		panelResearch.add(lblEr1);
		
		lblEr2 = new JLabel();
		lblEr2.setBounds(389, 450, 19, 14);
		panelResearch.add(lblEr2);
		
		lblEr3 = new JLabel();
		lblEr3.setBounds(554, 450, 19, 14);
		panelResearch.add(lblEr3);
		
		lblEr4 = new JLabel();
		lblEr4.setBounds(726, 450, 19, 14);
		panelResearch.add(lblEr4);
		
		// codice ogni volta deve incrementarsi di uno quando c'è un aggiunta di un libro
		txtCod = new JTextField();
		txtCod.setEnabled(false);
		txtCod.setEditable(false);
		txtCod.setText("Codice");
		txtCod.setBounds(10, 428, 156, 20);
		panelResearch.add(txtCod);
		txtCod.setColumns(10);
		
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
		txtName.setBounds(163, 428, 156, 20);
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
		txtSurname.setBounds(318, 428, 156, 20);
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
		txtCat.setBounds(472, 428, 156, 20);
		panelResearch.add(txtCat);
		txtCat.setColumns(10);
		
		txtTitle = new JTextField();
		txtTitle.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				// mettere check sia con lettere che numeri(check name solo lettere)
				if(Check.checkName(txtTitle.getText()))
				{
					lblEr4.setIcon(iconLogoT);
				}
				else
				{
					lblEr4.setIcon(iconLogoC);
				}
			}
		});

		
		
		txtTitle.setBounds(626, 428, 156, 20);
		panelResearch.add(txtTitle);
		txtTitle.setColumns(10);
		
		// contatore per il numero di prenotazioni
		txtLoans = new JTextField();
		txtLoans.setEnabled(false);
		txtLoans.setEditable(false);
		txtLoans.setText("Numero prenotazioni");
		txtLoans.setBounds(781, 428, 156, 20);
		panelResearch.add(txtLoans);
		txtLoans.setColumns(10);
		
		lblAdd = new JLabel();
		System.out.println("1" );
		lblAdd.addMouseListener(new MouseAdapter() {
			@Override
	
	
			
			public void mousePressed(MouseEvent arg0) {
				
				if(Check.checkAllBooks(txtName.getText(), txtSurname.getText(),txtCat.getText(),txtTitle.getText()))
				{
					try 
					{
						System.out.println("5" );
						

//TODO INSERISCI **** PASSA A CLIENT												
					
						System.out.println("inserimento nuovo libro: Client:" + me);
						// crea la query da girare insieme al messaggio per il server [ cmd insert + query gia pronta ]
							me.setSql(MQ_Insert.insertBooksGetQuery(txtName.getText(), txtSurname.getText(),txtCat.getText(),txtTitle.getText()));
						// accoda il comando alla lista comandi dalla quale legge il client

							try {
								System.out.println("GUI AppReader:> cmd inserisci LIBRO ");
							me.setCliType(Clients.Librarian);	
								me.getCmdLIST().put(Commands.BookADD);
							} catch (InterruptedException e2) {
								System.out.println("AppReader :> problemi con inserimento nuovo libro");	
								e2.printStackTrace();
							}				

//TODO INSERISCI **** PASSA A CLIENT						
						
					/*	OLD TEST OK
						MQ_Insert.insertBooks(txtName.getText(), txtSurname.getText(),txtCat.getText(),txtTitle.getText());						
					*/	
						
						
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
						panelTableResearch.update();
						System.out.println("9");
						PopUp.infoBox(frame, "Inserimento Corretto");
						
					} 

				 catch (SQLException e2) {
					System.out.println("AppReader :> creazione query NG ERRORE:");	
					e2.printStackTrace();
				}
					

				}
				
				else
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
		lblAdd.setBounds(935, 450, 19, 14);
		lblAdd.setIcon(iconLogoA);
		panelResearch.add(lblAdd);
	
	}
	
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
public void findBooks(String query){
	TableRowSorter<DefaultTableModel> tr=new TableRowSorter<DefaultTableModel>();
	tableBooks.setRowSorter(tr);
	
	tr.setRowFilter(RowFilter.regexFilter(query));
}
}
