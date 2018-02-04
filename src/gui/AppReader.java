package gui;


import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JTextField;
import javax.swing.JRadioButton;


import Check.Check;
import Check.PopUp;
import Core.Clients;
import Core.Commands;
import ProvaEmail.EmailSender;
import connections.Client;
import database.MQ_Insert;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.ButtonGroup;


public class AppReader extends SL_JFrame {
 
	private Client  		me ;
	private static final long serialVersionUID = 1L;
	private JFrame frmSchoolib;	
	private AppReader w;
	private JTextField txtCF;
	private JTextField txtName;
	private JTextField txtSurname;
	private JTextField txtEmail;
	private JTextField txtInquadr;
	private JTextField txtPhone;
	private JPasswordField passwordField;
	private JPasswordField passwordFieldCh;
	private int idUser ;
	private	JLabel lblCheckName; 
	private	JLabel lblCheckSurname; 
    private JLabel lblCheckEmail;
	private	JLabel lblCheckInq;
	private	JLabel lblCheckCF ;
	private	JLabel lblCheckPass ;
	private	JLabel lblCheckVerifyPass ;
	private	JLabel lblCheckPhone ;
	private JRadioButton rdbtnReader;
	private JRadioButton rdbtnLibrarian;
	private JTextField text;
	private String TypePerson;
	private JLabel 		lblCheckMail ;
	private ImageIcon 	iconLogoT;
	private ImageIcon 	iconLogoC;
	private ImageIcon iconLogoQ;
	private boolean 	cfcheckinprogress=false;
	private String 		cfcheckResult;
	private boolean 	mailcheckinprogress=false;
	private String 		mailcheckResult;
	private JLabel lblPopUpInq;
	private JLabel lblPopUpCF;
	private JLabel lblPopUpPass;
	private JLabel lblPopUpTel;


	public AppReader(Component c,Client x)
	{
		setW(this);
		me = x;
		me.setActW(this);
		me.setActC(c);
		me.setCliType(Clients.Reader);
				
		AppReader(c);
	}
	
	public void AppReader(Component c) 
	{
		
		frmSchoolib = new JFrame();
		frmSchoolib.setTitle("Schoolib");
		frmSchoolib.setBounds(100, 100, 893, 545);
		frmSchoolib.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmSchoolib.setLocationRelativeTo(c);
		frmSchoolib.setVisible(true);
		frmSchoolib.getContentPane().setLayout(new CardLayout(0, 0));
		
		JPanel panelSelection = new JPanel();
		frmSchoolib.getContentPane().add(panelSelection, "name_353237010061838");
		panelSelection.setLayout(null);

		
		ImageIcon iconLogoT = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/Tick.png")));
		ImageIcon iconLogoC = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/Cross.png")));
		ImageIcon iconLogoQ = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/question.png")));

		setIconLogoT(iconLogoT);
		setIconLogoC(iconLogoC);
		setIconLogoQ(iconLogoQ);

		
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNome.setBounds(20, 26, 77, 24);
		panelSelection.add(lblNome);
		
		JLabel lblCognome = new JLabel("Cognome");
		lblCognome.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblCognome.setBounds(20, 105, 77, 24);
		panelSelection.add(lblCognome);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblEmail.setBounds(20, 182, 77, 24);
		panelSelection.add(lblEmail);
		
		JLabel lblInquadr = new JLabel("Inquadramento");
		lblInquadr.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblInquadr.setBounds(20, 258, 113, 24);
		panelSelection.add(lblInquadr);
		
		JLabel lblCF = new JLabel("Codice Fiscale");
		lblCF.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblCF.setBounds(20, 334, 113, 24);
		panelSelection.add(lblCF);
		
		JLabel lblPass = new JLabel("Password");
		lblPass.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblPass.setBounds(470, 26, 92, 24);
		panelSelection.add(lblPass);
		
		JLabel lblVerifyPass = new JLabel("Conferma Password");
		lblVerifyPass.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblVerifyPass.setBounds(470, 108, 147, 24);
		panelSelection.add(lblVerifyPass);
		
		JLabel lblPhone = new JLabel("Telefono");
		lblPhone.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblPhone.setBounds(470, 190, 147, 24);
		panelSelection.add(lblPhone);
		
		lblCheckName = new JLabel();
		lblCheckName.setBounds(345, 34, 16, 16);
		panelSelection.add(lblCheckName);
		
		lblCheckSurname = new JLabel();
		lblCheckSurname.setBounds(345, 115, 16, 16);
		panelSelection.add(lblCheckSurname);
		
		lblCheckEmail = new JLabel();
	    this.setLblCheckMail(lblCheckEmail);
		lblCheckEmail.setBounds(345, 190, 16, 16);
		panelSelection.add(lblCheckEmail);
		
		lblCheckCF = new JLabel();
	    this.setLblCheckCF(lblCheckCF);
		lblCheckCF.setBounds(330, 342, 16, 16);
		panelSelection.add(lblCheckCF);
				
		lblCheckEmail = new JLabel();
		lblCheckEmail.setBounds(345, 190, 16, 16);
		panelSelection.add(lblCheckEmail);
		
		lblCheckInq = new JLabel();
		lblCheckInq.setBounds(345, 266, 16, 16);
		panelSelection.add(lblCheckInq);
	
		lblCheckPass = new JLabel();
		lblCheckPass.setBounds(829, 34, 16, 16);
		panelSelection.add(lblCheckPass);
		
		lblCheckVerifyPass = new JLabel();
		lblCheckVerifyPass.setBounds(829, 113, 16, 16);
		panelSelection.add(lblCheckVerifyPass);
		
		lblCheckPhone = new JLabel();
		lblCheckPhone.setBounds(829, 200, 16, 16);
		panelSelection.add(lblCheckPhone);
		
		lblPopUpInq = new JLabel();
		lblPopUpInq.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//Per informazioni cercare la classe PopUp
				PopUp.infoBox(frmSchoolib,"Si deve mettere uno tra questi campi:/n"
						       +          "Studente-1A,Studente-1B,Studente-1C,Studente-2A,Studente-2B,Studente-2C,/n"
						       +          "Studente-3A,Studente-3B,Studente-3C,Studente-4A,Studente-4B,Studente-4C,/n");
			}
		});
		lblPopUpInq.setIcon(iconLogoQ);
		lblPopUpInq.setBounds(219, 246, 16, 16);
		panelSelection.add(lblPopUpInq);
		
		lblPopUpCF = new JLabel();
		lblPopUpCF.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//Per informazioni cercare la classe PopUp
				PopUp.infoBox(frmSchoolib,"Immettere 16 caratteri tra numeri e lettere maiuscole");
			}
		});
		lblPopUpCF.setIcon(iconLogoQ);
		lblPopUpCF.setBounds(219, 322, 16, 16);
		panelSelection.add(lblPopUpCF);
		
		lblPopUpPass = new JLabel();
		lblPopUpPass.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//Per informazioni cercare la classe PopUp
				PopUp.infoBox(frmSchoolib,"La password deve contere un carattere maiuscolo, uno minuscolo, un numero e un carattere speciale");
			}
		});
		lblPopUpPass.setIcon(iconLogoQ);
		lblPopUpPass.setBounds(745, 11, 16, 16);
		panelSelection.add(lblPopUpPass);
		
		lblPopUpTel = new JLabel();
		lblPopUpTel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//Per informazioni cercare la classe PopUp
				PopUp.infoBox(frmSchoolib,"Immettere 10 numeri");
			}
		});
		lblPopUpTel.setIcon(iconLogoQ);
		lblPopUpTel.setBounds(725, 176, 16, 16);
		panelSelection.add(lblPopUpTel);
		
		JButton btnReg = new JButton("Registrazione");
		
		// verifico nome
		txtName = new JTextField();
		txtName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				panelSelection.getRootPane().setDefaultButton(btnReg);
			}
		});
		txtName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if(Check.checkName(txtName.getText()))
				{
					lblCheckName.setIcon(iconLogoT);
				}
				else
				{
					lblCheckName.setIcon(iconLogoC);		
				}
			
				
			}
		});
		txtName.setBounds(143, 31, 183, 20);
		panelSelection.add(txtName);
		txtName.setColumns(10);
		
		// verifico cognome
		txtSurname = new JTextField();
		txtSurname.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				panelSelection.getRootPane().setDefaultButton(btnReg);
			}
		});
		txtSurname.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				
				if(Check.checkName(txtSurname.getText()))
				{
					lblCheckSurname.setIcon(iconLogoT);
				}
				else
				{
					lblCheckSurname.setIcon(iconLogoC);		
				}
			}
		});
		txtSurname.setBounds(143, 110, 183, 20);
		panelSelection.add(txtSurname);
		txtSurname.setColumns(10);
		
		// verifico email
		txtEmail = new JTextField();
		txtEmail.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				panelSelection.getRootPane().setDefaultButton(btnReg);
			}
		});
		txtEmail.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				
				checkmail();

			
			}
		});
		txtEmail.setBounds(143, 187, 183, 20);
		panelSelection.add(txtEmail);
		txtEmail.setColumns(10);
	
		
		txtInquadr = new JTextField();
		txtInquadr.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				panelSelection.getRootPane().setDefaultButton(btnReg);
			}
		});
		txtInquadr.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				
			    if(Check.checkInqu(txtInquadr.getText())) 
				{
			    	lblCheckInq.setIcon(iconLogoT);	
				}
				else
				{
					lblCheckInq.setIcon(iconLogoC);	
				}
			
			}
		});
		txtInquadr.setBounds(143, 263, 183, 20);
		panelSelection.add(txtInquadr);
		txtInquadr.setColumns(10);
		
		// verifico codice fiscale
		txtCF = new JTextField();
		txtCF.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				panelSelection.getRootPane().setDefaultButton(btnReg);
			}
		});
		txtCF.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				
				checkcf();

				
			}
		});
		txtCF.setBounds(143, 339, 177, 20);
		panelSelection.add(txtCF);
		txtCF.setColumns(10);
		
		// verifico password
		passwordField = new JPasswordField();
		passwordField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				panelSelection.getRootPane().setDefaultButton(btnReg);
			}
		});
		passwordField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				
				if(Check.checkPass(passwordField.getPassword()))
				{
					lblCheckPass.setIcon(iconLogoT);
				}
				else
				{
					lblCheckPass.setIcon(iconLogoC);
				}
			}
		});
		passwordField.setBounds(650, 31, 169, 20);
		panelSelection.add(passwordField);

		passwordFieldCh = new JPasswordField();
		passwordFieldCh.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				panelSelection.getRootPane().setDefaultButton(btnReg);
			}
		});
		passwordFieldCh.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				
				if(Check.checkPass(passwordFieldCh.getPassword()) && Check.checkPassEq(passwordField.getPassword(), passwordFieldCh.getPassword()))
				{
					lblCheckVerifyPass.setIcon(iconLogoT);
				}
				else
				{
					lblCheckVerifyPass.setIcon(iconLogoC);
				}

			}
		});
		passwordFieldCh.setBounds(650, 110, 169, 20);
		panelSelection.add(passwordFieldCh);
		
		//verifico telefono
		txtPhone = new JTextField();
		txtPhone.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				panelSelection.getRootPane().setDefaultButton(btnReg);
			}
		});
		txtPhone.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				
				if(Check.checkTel(txtPhone.getText()))
				{
					lblCheckPhone.setIcon(iconLogoT);
				}
				else
				{
					lblCheckPhone.setIcon(iconLogoC);	
				}
			}
		});
		txtPhone.setBounds(637, 195, 183, 20);
		panelSelection.add(txtPhone);
		txtPhone.setColumns(10);
		
		JLabel lblChoise = new JLabel("Scegliere tipo di Utente");
		lblChoise.setBounds(582, 247, 147, 35);
		panelSelection.add(lblChoise);
		
		rdbtnReader = new JRadioButton("Lettore");
		rdbtnReader.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TypePerson="Lettore";
			}
		});
		rdbtnReader.setBounds(558, 300, 109, 23);
		panelSelection.add(rdbtnReader);
		
		rdbtnLibrarian = new JRadioButton("Libraio");
		rdbtnLibrarian.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TypePerson="Libraio";
			}
		});
		rdbtnLibrarian.setBounds(664, 300, 109, 23);
		panelSelection.add(rdbtnLibrarian);
		
		ButtonGroup bgMod = new ButtonGroup();
		bgMod.add(rdbtnReader);
		bgMod.add(rdbtnLibrarian);	
		
		btnReg.addActionListener(new ActionListener() {		
			public void actionPerformed(ActionEvent e) {
				
				me.setSql2("AppReader");
				
				//PRASSI ARDITO *****************************************
				//TEST OK 
				setMailcheckinprogress(true);
				setCfcheckinprogress(true);
				
				//parte check mail e check cf...
				checkmail();
				checkcf();
				
				while (isMailcheckinprogress()||(isCfcheckinprogress())) {	
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						
						e1.printStackTrace();
					} 
					try {
						Thread.sleep(10);
					} catch (InterruptedException ee) {
						ee.printStackTrace();
					}
				}	
				
				
					System.out.println("ritornato cf	check result"+getCfcheckResult());
					System.out.println("ritornato mail 	check result"+getMailcheckResult());
					
				if ( getMailcheckResult().equals("OK NE") && getCfcheckResult().equals("OK NE") ){
					
					System.out.println("procedo con il controllo sintattico altri campi");
					
					if	(	Check.checkAllReg				//controllo sintattico campi rimanenti OK
							(		txtName.getText(), 
									txtSurname.getText(),
									txtPhone.getText(),
									passwordField.getPassword(), 
									passwordFieldCh.getPassword(), 
									txtInquadr.getText()													
															)
																	)
					{
							String 	to 	= txtEmail.getText();	
							String 	p 	= String.copyValueOf(passwordField.getPassword());
							int 	tent	= 0;
							//*********************************************************************************
							// TEST OK 26 10 2017
							int 	n	=EmailSender.randomGenerator1();
							System.out.println("Destinatario:" + to +" Client:" + me);
							try {
								System.out.println("Destinatario:" + to +" Client:" + me);
								// crea la query da girare insieme al messaggio per il server [ cmd insert + query gia pronta ]
								me.setSql(MQ_Insert.insertUtenteGetQuery(	
								                        	getIdUser(),
															txtName.getText(), 
															txtSurname.getText(),
															txtInquadr.getText(),
															to,
															txtCF.getText(),
															txtPhone.getText(),
															p,
															n,
															tent,
															TypePerson
														)
								);
								
								
								System.out.println("Destinatario:" + to +" Client:" + me);
								me.setTo(to);														
								System.out.println("setto nel client destinatario :"+me.getTo());	// accoda il comando alla lista comandi dalla quale legge il client
								System.out.println("GUI AppReader:> creazione query ok:");	
								try {
									System.out.println("GUI AppReader:> cmd inserisci utente ");
									me.setCliType(Clients.Librarian);	
									me.getCmdLIST().put(Commands.UserRegistration);
									
								} catch (InterruptedException e2) {
									System.out.println("AppReader :> problemi con accodamento comando user registration");	
									e2.printStackTrace();
								}				
								WindowEvent close = new WindowEvent(frmSchoolib, WindowEvent.WINDOW_CLOSING);
								frmSchoolib.dispatchEvent(close);
							} catch (SQLException e2) {
								System.out.println("AppReader :> creazione query NG ERRORE:");	
								e2.printStackTrace();
							}
							
							//*********************************************************************************
							
					}
					else//controllo sintattico campi rimanenti NG
					{
							PopUp.warningBox(frmSchoolib, "Campi Errati");
							
							checkname();
							checksurname();
							checkPass1();
							checkPass2();
							checkPassEq();
							checkTel();
							checkinq();
					}

					
					
					
					
					
					
				}else {
					
					//checkmail  OR  checkcf FAIL
					
					PopUp.warningBox(frmSchoolib, " Riverificare inserimento email O cf ");
					
					
				}
				
				
				

				
							}
	});
	   
		btnReg.setBounds(470, 443, 147, 23);
		panelSelection.add(btnReg);
		
		JButton btnCancelReg = new JButton("Annulla");
		btnCancelReg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PopUp.warningBox(frmSchoolib, "Tutti i dati immessi verranno cancellati");
	
					 WindowEvent close = new WindowEvent(frmSchoolib, WindowEvent.WINDOW_CLOSING);
					 frmSchoolib.dispatchEvent(close);
					
			          
				}
			});
		btnCancelReg.setBounds(288, 443, 147, 23);
		panelSelection.add(btnCancelReg);
		
    }

	
	public boolean checkcf(){
		boolean checkok=true;
		
			System.out.println(" ***** sto controllando CODICE FISCALE ");
			System.out.println(" ***** sto controllando CODICE FISCALE : "+getTxtCF().getText());


			//******************************************************************
			if(Check.checkCF(getTxtCF().getText())){
			System.out.println(" ***** sto controllando CF : SINTATTICAMENTE Corretto");
			//sintatticamente correttO		
								String cf = getTxtCF().getText();
								me.setSql(cf);				
								me.setActW(getW());
								me.setActF(frmSchoolib);
								//me.setActC(c);									
								try {
									System.out.println("GUI account:> ottenuti dati user ");
								me.setCliType(Clients.Reader);	
									me.getCmdLIST().put(Commands.UserREADcheckCF);
								} catch (InterruptedException e2) {
									System.out.println("GUI account:> NON ottenuti dati user ");	
									e2.printStackTrace();
									setCfcheckResult("problemi con user read check cf");
									this.getLblCheckCF().setIcon(getIconLogoC());
								}
								//*************************************************************	
			}else {
			//sintatticamente non corretta
				
				//getLblChangeEmailCheck().setIcon(getIconLogoC());
				this.getLblCheckCF().setIcon(getIconLogoC());
				checkok=false;
				setCfcheckResult("sintatticamente non corretto");
				setCfcheckinprogress(false);
				
				System.out.println(" ***** sto controllando CF : sintatticamente non corretto "+getCfcheckResult());

			}
			//******************************************************************
			return checkok;
	}
	
	
	
	public boolean checkmail(){
		boolean checkok=true;
		
			System.out.println(" ***** sto controllando la email ");
			System.out.println(" ***** sto controllando la email : NEL CAMPO  : "+getTxtEmail().getText());

			//******************************************************************
			if(Check.checkMail(getTxtEmail().getText())){
			System.out.println(" ***** sto controllando la email : SINTATTICAMENTE Corretta");
			//sintatticamente corretta		
								System.out.println(" ***** sto controllando la email : email MODIFICATA");
								String email = getTxtEmail().getText();
								me.setSql(email);
								me.setSql2("AppReader");
								me.setActW(getW());
								me.setActF(frmSchoolib);
								//me.setActC(c);									
								try {
									System.out.println("GUI account:> ottenuti dati user ");
								me.setCliType(Clients.Reader);	
									
									me.getCmdLIST().put(Commands.UserREADcheckEmail);
									
									
								} catch (InterruptedException e2) {
									System.out.println("GUI account:> NON ottenuti dati user ");	
									e2.printStackTrace(); 
									setMailcheckResult("problemi con user read check mail");
									this.getLblCheckMail().setIcon(getIconLogoC());
								}
								//*************************************************************	
	
			}else {
			//sintatticamente non corretta
				System.out.println(" ***** sto controllando la email : sintatticamente non corretta");
				//getLblChangeEmailCheck().setIcon(getIconLogoC());
				this.getLblCheckMail().setIcon(getIconLogoC());
				checkok=false;
				setMailcheckResult("sintatticamente non corretta");
				setMailcheckinprogress(false);
			}
			//******************************************************************
			return checkok;
	}	
	
	public boolean checkname() {
		boolean checkok=true;
			if(Check.checkName(txtName.getText()))
			{
				lblCheckName.setIcon(iconLogoT);
			}
			else
			{
			checkok=false;
			lblCheckName.setIcon(iconLogoC);
			}
		return checkok;	
	}
	
	public boolean checksurname() {
		boolean checkok=true;
			if(Check.checkName(txtSurname.getText()))
			{
				 lblCheckSurname.setIcon(iconLogoT);
			}
			else
			{
				checkok=false;
				lblCheckSurname.setIcon(iconLogoC);
			}
		return checkok;	
	}	
	public boolean checkmail1() {
		boolean checkok=true;
			if(Check.checkMail(txtEmail.getText()) && (!Check.checkMailExist(txtEmail.getText())))
			{
				lblCheckEmail.setIcon(iconLogoT);
			}
			else
			{
				checkok=false;	
				lblCheckEmail.setIcon(iconLogoC);
			}
		return checkok;	
	}	
	public boolean checkCF1() {
		boolean checkok=true;
	if(Check.checkCF(txtCF.getText()) && (Check.checkCodFisExist(txtCF.getText())))
	{
		lblCheckCF.setIcon(iconLogoT);
	}
	else
	{
		checkok=false;	
		lblCheckCF.setIcon(iconLogoC);
	}
	return checkok;	
    }
	public boolean checkTel() {
		boolean checkok=true;
			if(Check.checkTel(txtPhone.getText()))
			{
				lblCheckPhone.setIcon(iconLogoT);
			}
			else
			{
			checkok=false;	
			lblCheckPhone.setIcon(iconLogoC);
			}
		return checkok;	
	}
	
	
	public boolean checkinq() {
		boolean checkok=true;
		if(Check.checkName(txtInquadr.getText()))
		{
			System.out.println("13");	
			lblCheckInq.setIcon(iconLogoT);
		}
		else
		{
			checkok=false;
			lblCheckInq.setIcon(iconLogoC);
		}
		return checkok;	
	}
	
	public boolean checkPass1() {
		boolean checkok=true;
		if(Check.checkPass(passwordField.getPassword()))
		{
			lblCheckPass.setIcon(iconLogoT);
		}
		else
		{
			checkok=false;
			lblCheckPass.setIcon(iconLogoC);
		}
		return checkok;	
	}	
	
	public boolean checkPass2() {
		boolean checkok=true;
		if(Check.checkPass(passwordFieldCh.getPassword()))
		{
			lblCheckVerifyPass.setIcon(iconLogoT);
		}
		else
		{
			checkok=false;
			lblCheckVerifyPass.setIcon(iconLogoC);
		}
		return checkok;	
	}	

	public boolean checkPassEq() {
		boolean checkok=true;
		if(Check.checkPassEq(passwordField.getPassword(),passwordFieldCh.getPassword()))
		{
			lblCheckVerifyPass.setIcon(iconLogoT);
		}
		else
		{
			checkok=false;
			lblCheckVerifyPass.setIcon(iconLogoC);
		}
		return checkok;	
	}	
////////////////////////////////////////////////////////////////////////////////////
	public String getTypePerson() {
		return TypePerson;
	}

	public void setTypePerson(String typePerson) {
		TypePerson = typePerson;
	}

	public JFrame getFrmSchoolib() {
		return frmSchoolib;
	}

	public void setFrmSchoolib(JFrame frmSchoolib) {
		this.frmSchoolib = frmSchoolib;
	}

	public JTextField getTxtCF() {
		return txtCF;
	}

	public void setTxtCF(JTextField txtCF) {
		this.txtCF = txtCF;
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

	public JTextField getTxtEmail() {
		return txtEmail;
	}

	public void setTxtEmail(JTextField txtEmail) {
		this.txtEmail = txtEmail;
	}

	public JTextField getTxtInquadr() {
		return txtInquadr;
	}

	public void setTxtInquadr(JTextField txtInquadr) {
		this.txtInquadr = txtInquadr;
	}

	public JTextField getTxtPhone() {
		return txtPhone;
	}

	public void setTxtPhone(JTextField txtPhone) {
		this.txtPhone = txtPhone;
	}

	public JPasswordField getPasswordField() {
		return passwordField;
	}

	public void setPasswordField(JPasswordField passwordField) {
		this.passwordField = passwordField;
	}

	public JPasswordField getPasswordFieldCh() {
		return passwordFieldCh;
	}

	public void setPasswordFieldCh(JPasswordField passwordFieldCh) {
		this.passwordFieldCh = passwordFieldCh;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public AppReader  getW() {
		return w;
	}
	public void setW(AppReader w) {
		this.w = w;
	}

	//////////////////////////////////////////////////////////////////////

	
	public JRadioButton getRdbtnReader() {
		return rdbtnReader;
	}

	public void setRdbtnReader(JRadioButton rdbtnReader) {
		this.rdbtnReader = rdbtnReader;
	}

	public JRadioButton getRdbtnLibrarian() {
		return rdbtnLibrarian;
	}

	public void setRdbtnLibrarian(JRadioButton rdbtnLibrarian) {
		this.rdbtnLibrarian = rdbtnLibrarian;
	}


	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	
	@Override
	public void addMsg(String msg){
		text.setText(msg);	
	}

	public ImageIcon getIconLogoT() {
		return iconLogoT;
	}

	public void setIconLogoT(ImageIcon iconLogoT) {
		this.iconLogoT = iconLogoT;
	}

	public ImageIcon getIconLogoC() {
		return this.iconLogoC;
	}

	public void setIconLogoC(ImageIcon iconLogoC) {
		this.iconLogoC = iconLogoC;
	}
	public boolean isCfcheckinprogress() {
		return cfcheckinprogress;
	}

	public void setCfcheckinprogress(boolean cfcheckinprogress) {
		this.cfcheckinprogress = cfcheckinprogress;
	}

	public String getCfcheckResult() {
		return cfcheckResult;
	}

	public void setCfcheckResult(String cfcheckResult) {
		this.cfcheckResult = cfcheckResult;
	}

	public JLabel getLblCheckCF() {
		return this.lblCheckCF;
	}
	public void setLblCheckCF(JLabel lblCheckCF) {
		this.lblCheckCF = lblCheckCF;
	}	
	
	public JLabel getLblCheckMail() {
		return this.lblCheckMail;
	}
	public void setLblCheckMail(JLabel lblCheckMail) {
		this.lblCheckMail = lblCheckMail;
	}
	
	
	public boolean isMailcheckinprogress() {
		return mailcheckinprogress;
	}

	public void setMailcheckinprogress(boolean mailcheckinprogress) {
		this.mailcheckinprogress = mailcheckinprogress;
	}

	public String getMailcheckResult() {
		return mailcheckResult;
	}

	public void setMailcheckResult(String mailcheckResult) {
		this.mailcheckResult = mailcheckResult;
	}

	public ImageIcon getIconLogoQ() {
		return iconLogoQ;
	}

	public void setIconLogoQ(ImageIcon iconLogoQ) {
		this.iconLogoQ = iconLogoQ;
	}
	
}
