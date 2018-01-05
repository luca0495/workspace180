package gui;


import java.awt.Component;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.JMenu;

import java.awt.Font;
import java.awt.Frame;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import Check.Check;
import Check.PopUp;
import Core.Clients;
import Core.Commands;
import ProvaEmail.EmailSender;
import connections.Client;
import database.MQ_Check;
import database.MQ_Insert;

import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JMenuBar;


public class AppReader extends SL_JFrame {
 
	private Client  		me ;
	private static final long serialVersionUID = 1L;
	private JFrame frmSchoolib;	

	
	private JTextField txtCF;
	private JTextField txtName;
	private JTextField txtSurname;
	private JTextField txtEmail;
	private JTextField txtInquadr;
	private JTextField txtPhone;
	private JPasswordField passwordField;
	private JPasswordField passwordFieldCh;
	public static String SearchParam = "1A";
	private JTextField text;
	private String TypePerson;
	//private static JComboBox<String> Inq;
	//private static final String[] Students =
		// {"	A1" , "A2"};
	



	public AppReader(Component c,Client x)
	{
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
		
		JLabel lblCheckName = new JLabel();
		lblCheckName.setBounds(345, 34, 16, 16);
		panelSelection.add(lblCheckName);
		
		JLabel lblCheckSurname = new JLabel();
		lblCheckSurname.setBounds(345, 115, 16, 16);
		panelSelection.add(lblCheckSurname);
		
		JLabel lblCheckEmail = new JLabel();
		lblCheckEmail.setBounds(345, 190, 16, 16);
		panelSelection.add(lblCheckEmail);
		
		JLabel lblCheckInq = new JLabel();
		lblCheckInq.setBounds(345, 266, 16, 16);
		panelSelection.add(lblCheckInq);
		
		JLabel lblCheckCF = new JLabel();
		lblCheckCF.setBounds(330, 342, 16, 16);
		panelSelection.add(lblCheckCF);
		
		JLabel lblCheckPass = new JLabel();
		lblCheckPass.setBounds(829, 34, 16, 16);
		panelSelection.add(lblCheckPass);
		
		JLabel lblCheckVerifyPass = new JLabel();
		lblCheckVerifyPass.setBounds(829, 113, 16, 16);
		panelSelection.add(lblCheckVerifyPass);
		
		JLabel lblCheckPhone = new JLabel();
		lblCheckPhone.setBounds(829, 200, 16, 16);
		panelSelection.add(lblCheckPhone);
		
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
				
				if(Check.checkMail(txtEmail.getText()) && Check.checkMailExist(txtEmail.getText())) 
				{
					lblCheckEmail.setIcon(iconLogoT);	
				}
				else
				{
					lblCheckEmail.setIcon(iconLogoC);	
				}
			
			}
		});
		txtEmail.setBounds(143, 187, 183, 20);
		panelSelection.add(txtEmail);
		txtEmail.setColumns(10);
		
		
		//txtinqu e check
		
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
				
			    if(Check.checkInq(txtInquadr.getText())) 
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
				
				if(Check.checkCF(txtCF.getText()) && Check.checkCodFisExist(txtCF.getText()))
				{
					lblCheckCF.setIcon(iconLogoT);
				}
				else
				{
					lblCheckCF.setIcon(iconLogoC);	
				}
				
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
				
				if(Check.checkPass1(passwordField.getPassword()))
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
		
		// verifico la conferma della password
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
				
				if(Check.checkPass1(passwordFieldCh.getPassword()) && Check.checkPassEq(passwordField.getPassword(), passwordFieldCh.getPassword()))
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
		txtPhone.setBounds(636, 195, 183, 20);
		panelSelection.add(txtPhone);
		txtPhone.setColumns(10);
		
		JLabel lblChoise = new JLabel("Scegliere tipo di Utente");
		lblChoise.setBounds(582, 247, 147, 35);
		panelSelection.add(lblChoise);
		
		JRadioButton rdbtnReader = new JRadioButton("Lettore");
		rdbtnReader.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TypePerson="Lettore";
			}
		});
		rdbtnReader.setBounds(558, 300, 109, 23);
		panelSelection.add(rdbtnReader);
		
		JRadioButton rdbtnLibrarian = new JRadioButton("Libraio");
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
				if	(	Check.checkAllReg	(
																txtName.getText(), 
																txtSurname.getText(),
																txtPhone.getText(),
																txtEmail.getText(),
																txtCF.getText(),
																passwordField.getPassword(), 
																passwordFieldCh.getPassword(), 
																txtInquadr.getText()
																
											)
					)
				{
					
				String 	to 	= txtEmail.getText();	
				String 	p 	= String.copyValueOf(passwordField.getPassword());
				
				//*********************************************************************************
				// in test 26 10 2017
				int 	n	=EmailSender.randomGenerator1();
				System.out.println("Destinatario:" + to +" Client:" + me);
				try {
					System.out.println("Destinatario:" + to +" Client:" + me);
				// crea la query da girare insieme al messaggio per il server [ cmd insert + query gia pronta ]
					me.setSql(MQ_Insert.insertUtenteGetQuery(	
																txtName.getText(), 
																txtSurname.getText(),
																txtInquadr.getText(),
																to,
																txtCF.getText(),
																txtPhone.getText(),
																p,
																n,
																TypePerson
															)
							);
					System.out.println("Destinatario:" + to +" Client:" + me);
					me.setTo(to);

				// accoda il comando alla lista comandi dalla quale legge il client


					System.out.println("setto nel client destinatario :"+me.getTo());
					
					// accoda il comando alla lista comandi dalla quale legge il client

					System.out.println("GUI AppReader:> creazione query ok:");	
					try {
						System.out.println("GUI AppReader:> cmd inserisci utente ");
					me.setCliType(Clients.Librarian);	
						me.getCmdLIST().put(Commands.UserRegistration);
					} catch (InterruptedException e2) {
						System.out.println("AppReader :> problemi con accodamento comando user registration");	
						e2.printStackTrace();
					}				

				} catch (SQLException e2) {
					System.out.println("AppReader :> creazione query NG ERRORE:");	
					e2.printStackTrace();
				}
				//*********************************************************************************
				/*
				try 
					{	
						System.out.println("Controllo errore:" + me.toString());																		
						MQ_Insert.insertUtente	(
													txtName.getText(), 
													txtSurname.getText(), 
													txtInquadr.getText(), 
													txtEmail.getText(), 
													txtCF.getText(), 
													txtPhone.getText(), 
													p,
													EmailSender.randomGenerator1()
												);
						System.out.println("Controllo errore:" + 
								me.toString() + 
								me.getUSERNAME() + 
								me.getPASSWORD() );
						EmailSender.send_uninsubria_email(to,me);				
					}										
					catch (SQLException e1)			{e1.printStackTrace();} 
					catch (SendFailedException e1) 	{e1.printStackTrace();} 
					catch (MessagingException e1) 	{e1.printStackTrace();}
				
				
				*/
					//*********************************************************************************
				//TODO SPOSTARE ANCHE QUESTO SU CLIENT
					// PopUp.infoBox(c, "Registrazione avvenuta con successo, attiva account dal codice che ti abbiamo inviato");
					// timer per panel nuovo RIVEDERE Ciao!!!
				}
				else
				{
					PopUp.warningBox(frmSchoolib, "Campi Errati");
				}
				    // timer per panel nuovo RIVEDERE
							WindowEvent close = new WindowEvent(frmSchoolib, WindowEvent.WINDOW_CLOSING);
						    frmSchoolib.dispatchEvent(close);
				}
		});
	   
		btnReg.setBounds(470, 443, 147, 23);
		panelSelection.add(btnReg);
		
		JButton btnCancelReg = new JButton("Annulla");
		btnCancelReg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PopUp.warningBox(frmSchoolib, "Tutti i dati immessi verranno cancellati");
				
				if(PopUp.confirmBox(c))
				{
					
					 WindowEvent close = new WindowEvent(frmSchoolib, WindowEvent.WINDOW_CLOSING);
					 frmSchoolib.dispatchEvent(close);
					
			       }   
				}
			});
		btnCancelReg.setBounds(288, 443, 147, 23);
		panelSelection.add(btnCancelReg);
		
		
		text = new JTextField();
		text.setBounds(20, 444, 239, 20);
		panelSelection.add(text);
		text.setColumns(10);
	
		/*
		Inq = new JComboBox<String>(Students); // set up JComboBox
		Inq.setMaximumRowCount(5);
		Inq.setBounds(318, 263, 28, 20);
		panelSelection.add(Inq);
		
		JRadioButton rdbtnA1 = new JRadioButton("A1");
		rdbtnA1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SearchParam = "1A";
			}
		});
   
		rdbtnA1.setBounds(345, 262, 109, 23);
		panelSelection.add(rdbtnA1);
	*/	
    }

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
	
	
	@Override
	public void addMsg(String msg){
		text.setText(msg);	
	}
}
