package gui;


import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Check.Check;
import Check.PopUp;
import Core.Clients;
import connections.Client;
import database.DBmanager;
import database.LoadUser;
import database.MQ_Delete;
import database.MQ_Read;
import database.MQ_Update;

import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JPasswordField;

public class Account extends SL_JFrame{

	
	
	private JFrame frmSchoolib;	
	private AppReader a;
	private LoadUser l = new LoadUser();
	private Client me;
	private static final long serialVersionUID = 1L;
	private static String[] UserData = null;
	private JTextField s = null;
	public String p1,p2,p3,p4,p5,p6,p7 = null; 
    static int rows = 0;
    static int cols = 0; 
    String r = null;
    private static JTextField s1;
    private String idUser= null;
	static int userRow = 0;
    private JLabel lblSetNome;
    private JLabel lblSetCognome;
    private JLabel lblSetEmail;
    private JLabel lblSetPass;
    private JLabel lblSetInq;
    private JLabel lblSetTipoUte;
    private JLabel lblSetTel;
    private JLabel lblSetNumPrenPend;
	private String TypePerson = "Lettore";
	private JTextField txtNameMod;
	private JTextField txtSurnameMod;
	private JTextField txtMailMod;
	private JTextField txtInqMod;
	private JTextField txtTelMod;
	private JPasswordField passwordFieldMod;
	private JPasswordField passwordFieldConfMod;
	private String input;
	private List<String> rowData;
	private int column;
	private int deleteRow;
	private String[] user = null;
	private boolean User = true;
	private JTextField passwordFieldMod1;
	private JTextField passwordFieldConfMod1;
	
	// in test 
	private String 	emailuser;
	private boolean cambioemail=false;
	
	
	public Account(Component c,Client x)
	{
		me = x;
		me.setActW(this);
		me.setActC(c);
		me.setCliType(Clients.Reader);		
		Account(c);
		
	}
	
	public void Account(Component c) {
		
		frmSchoolib = new JFrame();
		frmSchoolib.setTitle("Schoolib");
		frmSchoolib.setBounds(100, 100, 893, 545);
		frmSchoolib.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmSchoolib.setLocationRelativeTo(c);
		frmSchoolib.setVisible(true);
		frmSchoolib.getContentPane().setLayout(new CardLayout(0, 0));
		
		ImageIcon iconLogoT = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/Tick.png")));
		ImageIcon iconLogoC = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/Cross.png")));
	
		
		JPanel panelAccount = new JPanel();
		frmSchoolib.getContentPane().add(panelAccount, "name_353435345061838");
		panelAccount.setLayout(null);
		
		JPanel panelModify = new JPanel();
		frmSchoolib.getContentPane().add(panelModify, "name_454607080642439");
		panelModify.setLayout(null);
		
		//old ora comando passato da finestra login a client
		//***
		/*
		 
		 */
		 //***
		 
		 
		JLabel lblNome = new JLabel("Nome: ");
		lblNome.setBounds(10, 14, 42, 30);
		panelAccount.add(lblNome);
		
	    lblSetNome = new JLabel();
	    //lblSetNome.setText(user[0]);
		lblSetNome.setBounds(106, 19, 186, 20);
		panelAccount.add(lblSetNome);
		
		JLabel lblCognome = new JLabel("Cognome: ");
		lblCognome.setBounds(10, 64, 59, 14);
		panelAccount.add(lblCognome);
		
		lblSetCognome = new JLabel();
		//lblSetCognome.setText(user[1]);
		lblSetCognome.setBounds(106, 64, 186, 20);
		panelAccount.add(lblSetCognome);
		
		JLabel lblEmail = new JLabel("Email: ");
		lblEmail.setBounds(10, 115, 46, 14);
		panelAccount.add(lblEmail);
		
		lblSetEmail = new JLabel();
		//lblSetEmail.setText(user[2]);
		lblSetEmail.setBounds(106, 112, 186, 20);
		panelAccount.add(lblSetEmail);
			
		JLabel lblInq = new JLabel("Inquadramento: ");
		lblInq.setBounds(10, 159, 86, 14);
		panelAccount.add(lblInq);
		
		lblSetInq = new JLabel();
		//lblSetInq.setText(user[4]);
		lblSetInq.setBounds(106, 156, 186, 20);
		panelAccount.add(lblSetInq);
		
		JLabel lblTipoUte = new JLabel("Tipo Utente:");
		lblTipoUte.setBounds(10, 214, 74, 14);
		panelAccount.add(lblTipoUte);
		
		lblSetTipoUte = new JLabel();
		//lblSetTipoUte.setText(user[6]);
		lblSetTipoUte.setBounds(106, 211, 186, 20);
		panelAccount.add(lblSetTipoUte);
		
		JLabel lblTel = new JLabel("Telefono:");
		lblTel.setBounds(10, 261, 59, 14);
		panelAccount.add(lblTel);
		
		lblSetTel = new JLabel();
		//lblSetTel.setText(user[5]);
		lblSetTel.setBounds(106, 261, 186, 14);
		panelAccount.add(lblSetTel);
		
		JLabel lblNumPrenPend = new JLabel("Numero Di Prenotazioni:");
		lblNumPrenPend.setBounds(10, 307, 132, 14);
		panelAccount.add(lblNumPrenPend);
		
		lblSetNumPrenPend = new JLabel();
		// mettere anche questa nel metodo
		lblSetNumPrenPend.setBounds(152, 328, 140, 14);
		panelAccount.add(lblSetNumPrenPend);
		
		JButton btnDelete = new JButton("Cancella Profilo");
		btnDelete.setBackground(Color.RED);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
             PopUp.warningBox(frmSchoolib,"Questa azione cancellerà in modo completo e definitivo il profilo utenete attualmente in uso !!!");
				if(PopUp.confirmBox(frmSchoolib))
				{
                rowData = new ArrayList<String>();
					
				rowData.add(0, idUser);
			
				try {
					MQ_Delete.deleteRowPerson(rowData);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				WindowEvent close = new WindowEvent(frmSchoolib, WindowEvent.WINDOW_CLOSING);
			    frmSchoolib.dispatchEvent(close);
			}
			}
		});
		btnDelete.setBounds(192, 381, 193, 54);
		panelAccount.add(btnDelete);
		
		JButton btnModify = new JButton("Modifica Profilo");
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelAccount.setVisible(false);
				panelModify.setVisible(true);
			}
		});
		btnModify.setBounds(428, 381, 186, 54);
		panelAccount.add(btnModify);
		
		// PANEL MODIFY
		
		JLabel lblNameMod = new JLabel("Nome");
		lblNameMod.setBounds(10, 29, 127, 23);
		panelModify.add(lblNameMod);
		
		JLabel lblChangeNameCheck = new JLabel();
		lblChangeNameCheck.setBounds(362, 31, 21, 19);
		panelModify.add(lblChangeNameCheck);
		
		JLabel lblSurnameMod = new JLabel("Cognome");
		lblSurnameMod.setBounds(10, 101, 127, 23);
		panelModify.add(lblSurnameMod);
		
		JLabel lblChangeSurnameCheck = new JLabel();
		lblChangeSurnameCheck.setBounds(362, 105, 21, 19);
		panelModify.add(lblChangeSurnameCheck);
		
		JLabel lblMailMod = new JLabel("Email");
		lblMailMod.setBounds(10, 172, 127, 23);
		panelModify.add(lblMailMod);
		
		JLabel lblChangeEmailCheck = new JLabel();
		lblChangeEmailCheck.setBounds(362, 176, 21, 19);
		panelModify.add(lblChangeEmailCheck);
		
		JLabel lblPassMod = new JLabel("Password");
		lblPassMod.setBounds(10, 246, 100, 23);
		panelModify.add(lblPassMod);
		
		JLabel lblChangePassCheck = new JLabel();
		lblChangePassCheck.setBounds(362, 250, 21, 19);
		panelModify.add(lblChangePassCheck);
		
		JLabel lblPassConfMod = new JLabel("Conferma Password");
		lblPassConfMod.setBounds(10, 325, 100, 23);
		panelModify.add(lblPassConfMod);
		
		JLabel lblChangePassConfCheck = new JLabel();
		lblChangePassConfCheck.setBounds(362, 329, 21, 19);
		panelModify.add(lblChangePassConfCheck);
		
		JLabel lblInqMod = new JLabel("Inquadramento");
		lblInqMod.setBounds(431, 33, 132, 14);
		panelModify.add(lblInqMod);
		
		JLabel lblChangeInqCheck = new JLabel();
		lblChangeInqCheck.setBounds(807, 33, 21, 19);
		panelModify.add(lblChangeInqCheck);
		
		JLabel lblTelMod = new JLabel("Telefono");
		lblTelMod.setBounds(431, 105, 132, 14);
		panelModify.add(lblTelMod);
		
		JLabel lblChangePhoneCheck = new JLabel();
		lblChangePhoneCheck.setBounds(807, 105, 21, 19);
		panelModify.add(lblChangePhoneCheck);
		
		JLabel lblTypeUserMod = new JLabel("Tipo Utente");
		lblTypeUserMod.setBounds(611, 176, 157, 19);
		panelModify.add(lblTypeUserMod);
		
		try {
			user = MQ_Read.retrieveUserId();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		txtNameMod = new JTextField();
		txtNameMod.setEditable(false);
		txtNameMod.setText(user[1]);
		txtNameMod.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtNameMod.setEditable(true);
				txtNameMod.getCaret().setVisible(true);
			}
		});
		txtNameMod.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				
				if(Check.checkName(txtNameMod.getText()))
				{
					lblChangeNameCheck.setIcon(iconLogoT);
				}
				else
				{
					lblChangeNameCheck.setIcon(iconLogoC);
				}
			}
		});
		txtNameMod.setBounds(120, 30, 224, 20);
		panelModify.add(txtNameMod);
		txtNameMod.setColumns(10);
		
		txtSurnameMod = new JTextField();
		txtSurnameMod.setEditable(false);
		txtSurnameMod.setText(user[2]);
		txtSurnameMod.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtSurnameMod.setEditable(true);
				txtSurnameMod.getCaret().setVisible(true);
			}
		});
		txtSurnameMod.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				
				if(Check.checkName(txtSurnameMod.getText()))
				{
					lblChangeSurnameCheck.setIcon(iconLogoT);
				}
				else
				{
					lblChangeSurnameCheck.setIcon(iconLogoC);
				}
			}
		});
		txtSurnameMod.setBounds(120, 102, 224, 20);
		panelModify.add(txtSurnameMod);
		txtSurnameMod.setColumns(10);
		
		txtMailMod = new JTextField();
		txtMailMod.setEditable(false);
		txtMailMod.setText(user[3]);
		txtMailMod.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtMailMod.setEditable(true);
				txtMailMod.getCaret().setVisible(true);
			}
		});
		txtMailMod.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				
				//******************************************************************
				if(Check.checkMail(txtMailMod.getText())){
				//sintatticamente corretta	
				
				
								if (txtMailMod.getText()!=getEmailuser()) {
									// modifica alla email
									
									if ( Check.checkMailExist(txtMailMod.getText())) {
										// email inserita esiste gia... non va bene
										lblChangeEmailCheck.setIcon(iconLogoC);
										txtMailMod.setText(null);
									}else {
										// email non esiste gia
										lblChangeEmailCheck.setIcon(iconLogoT);
									}
								}else {	//non modificata
									lblChangeEmailCheck.setIcon(iconLogoT);
								}	
				}else {
				//sintatticamente non corretta
					lblChangeEmailCheck.setIcon(iconLogoC);
				}
				//******************************************************************	
				
				/*
				if(Check.checkMail(txtMailMod.getText()) && (!Check.checkMailExist(txtMailMod.getText())))
				{
					lblChangeEmailCheck.setIcon(iconLogoT);
				}
				else
				{
					lblChangeEmailCheck.setIcon(iconLogoC);
				}
				*/
				
			}
		});
		txtMailMod.setBounds(120, 173, 224, 20);
		panelModify.add(txtMailMod);
		txtMailMod.setColumns(10);
		/*
		passwordFieldMod1 = new JTextField();
		passwordFieldMod1.setEditable(false);
		passwordFieldMod1.setText(user[3]);
		passwordFieldMod1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				passwordFieldMod1.setEditable(true);
				passwordFieldConfMod.setEditable(true);
			}
		});
		passwordFieldMod1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				
				if(Check.checkPass(passwordFieldMod1.getText()))
				{
					lblChangePassCheck.setIcon(iconLogoT);
				}
				else
				{
					lblChangePassCheck.setIcon(iconLogoC);
				}
			}
		});
		passwordFieldMod1.setBounds(120, 247, 224, 20);
		panelModify.add(passwordFieldMod1);
		passwordFieldMod1.setColumns(10);
		
		passwordFieldConfMod1 = new JTextField();
		passwordFieldConfMod1.setEditable(false);
		passwordFieldConfMod1.setText(user[3]);
		passwordFieldConfMod1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				
				if(Check.checkPass(passwordFieldConfMod1.getText()))
				{					
					if(Check.checkPassEq1(passwordFieldMod1.getText(), passwordFieldConfMod1.getText()))
					{
						lblChangePassConfCheck.setIcon(iconLogoT);
					}
					else
					{
						lblChangePassConfCheck.setIcon(iconLogoC);
					}
				}
				else
				{
					lblChangePassConfCheck.setIcon(iconLogoC);
				}
				
			}
		});
		passwordFieldConfMod1.setBounds(120, 326, 224, 20);
		panelModify.add(passwordFieldConfMod1);
		passwordFieldConfMod1.setColumns(10);
		*/
       
		passwordFieldMod = new JPasswordField();
		passwordFieldMod.setEditable(false);
		passwordFieldMod.setText(user[4]);
		passwordFieldMod.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				passwordFieldMod.setEditable(true);
				passwordFieldConfMod.setEditable(true);
			}
		});
		passwordFieldMod.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				
				if(Check.checkPass1(passwordFieldMod.getPassword()))
				{
					lblChangePassCheck.setIcon(iconLogoT);
				}
				else
				{
					lblChangePassCheck.setIcon(iconLogoC);
				}
			}
		});
		passwordFieldMod.setBounds(120, 247, 224, 20);
		panelModify.add(passwordFieldMod);
		
		passwordFieldConfMod = new JPasswordField();
		passwordFieldConfMod.setEditable(false);
		passwordFieldConfMod.setText(user[4]);
		passwordFieldConfMod.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				
				if(Check.checkPass1(passwordFieldConfMod.getPassword()))
				{					
					if(Check.checkPassEq(passwordFieldMod.getPassword(), passwordFieldConfMod.getPassword()))
					{
						lblChangePassConfCheck.setIcon(iconLogoT);
					}
					else
					{
						lblChangePassConfCheck.setIcon(iconLogoC);
					}
				}
				else
				{
					lblChangePassConfCheck.setIcon(iconLogoC);
				}
				
			}
		});
		passwordFieldConfMod.setBounds(120, 326, 224, 20);
		panelModify.add(passwordFieldConfMod);
	   
		
		txtInqMod = new JTextField();
		txtInqMod.setEditable(false);
		txtInqMod.setText(user[5]);
		txtInqMod.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtInqMod.setEditable(true);
				txtInqMod.getCaret().setVisible(true);
			}
		});
		txtInqMod.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				
				if(Check.checkName(txtInqMod.getText()))
				{
					lblChangeInqCheck.setIcon(iconLogoT);
				}
				else
				{
					lblChangeInqCheck.setIcon(iconLogoC);
				}
			}
		});
		
		
		txtInqMod.setBounds(573, 30, 224, 20);
		panelModify.add(txtInqMod);
		txtInqMod.setColumns(10);
		
		txtTelMod = new JTextField();
		txtTelMod.setEditable(false);
		txtTelMod.setText(user[6]);
		txtTelMod.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtTelMod.setEditable(true);
				txtTelMod.getCaret().setVisible(true);
			}
		});
		txtTelMod.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				
				if(Check.checkTel(txtTelMod.getText()))
				{
					lblChangePhoneCheck.setIcon(iconLogoT);
				}
				else
				{
					lblChangePhoneCheck.setIcon(iconLogoC);
				}
			}
		});
		txtTelMod.setBounds(573, 102, 224, 20);
		panelModify.add(txtTelMod);
		txtTelMod.setColumns(10);
		
		JRadioButton rdbtnTypeUserLibMod = new JRadioButton("Libraio");
		rdbtnTypeUserLibMod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnTypeUserLibMod.isSelected())
				{
					TypePerson = "Libraio";
				}
			}
		});
		rdbtnTypeUserLibMod.setBounds(519, 246, 109, 23);
		panelModify.add(rdbtnTypeUserLibMod);
		
		JRadioButton rdbtnTypeUserLetMod = new JRadioButton("Lettore");
		rdbtnTypeUserLetMod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnTypeUserLetMod.isSelected())
				{
					TypePerson = "Lettore";
				}
			}
		});
		rdbtnTypeUserLetMod.setBounds(668, 246, 109, 23);
		panelModify.add(rdbtnTypeUserLetMod);
		
		ButtonGroup bgMod = new ButtonGroup();
		bgMod.add(rdbtnTypeUserLibMod);
		bgMod.add(rdbtnTypeUserLetMod);	
		
		JButton btnModData = new JButton("Modifica Dati");
		btnModData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//Assegna a delle variabili il contenuto dei text field
				
				String nome = txtNameMod.getText();
				String cognome = txtSurnameMod.getText();
				String mail = txtMailMod.getText();
				char[] pass = passwordFieldMod.getPassword();
				char[] checkPassword = passwordFieldConfMod.getPassword();
				String inq = txtInqMod.getText();
				String tel = txtTelMod.getText();
				String stato = TypePerson;
				System.out.println("1");
				
				if(Check.checkAllRegMod(nome,cognome,mail,pass,checkPassword,inq,tel))
				{
					System.out.println("2");
					try
					{
					System.out.println("3");
					String 	p 	= String.copyValueOf(passwordFieldMod.getPassword());
					MQ_Update.updateModUser(txtNameMod.getText(), txtSurnameMod.getText(), txtMailMod.getText(),txtInqMod.getText(),p,txtTelMod.getText(),stato);
					
					}
					catch (SQLException e) 
					{
						e.printStackTrace();
					}
					
					PopUp.infoBox(frmSchoolib,"Modifica avvenuta con successo");
					 try 
					{
					  user = MQ_Read.retrieveUserId();
					} catch (SQLException e) {
							e.printStackTrace();
					}
				    
					lblSetNome.setText(user[1]);
					lblSetCognome.setText(user[2]);
					lblSetEmail.setText(user[3]);
					lblSetInq.setText(user[5]);
					lblSetTel.setText(user[6]);
					lblSetTipoUte.setText(user[7]);
					 
					panelAccount.setVisible(true);
					panelModify.setVisible(false);
					
					txtNameMod.setEditable(false);
					txtSurnameMod.setEditable(false);
					txtMailMod.setEditable(false);
					passwordFieldMod.setEditable(false);
					passwordFieldConfMod.setEditable(false);
					txtInqMod.setEditable(false);
					txtTelMod.setEditable(false);
					
					lblChangeNameCheck.setIcon(null);
					lblChangeSurnameCheck.setIcon(null);
					lblChangeEmailCheck.setIcon(null);
					lblChangePassCheck.setIcon(null);
					lblChangePassCheck.setIcon(null);
					lblChangePassConfCheck.setIcon(null);
					lblChangeInqCheck.setIcon(null);
					lblChangePhoneCheck.setIcon(null);
					
			    }
				else 
				{
				PopUp.errorBox1(frmSchoolib,"Campi non corretti");
				
				if(Check.checkName(nome))
				{
					System.out.println("7");
					lblChangeNameCheck.setIcon(iconLogoT);
				}
				else
				{ 
					System.out.println("8");
					lblChangeNameCheck.setIcon(iconLogoC);
				}
				
				System.out.println("9");
				if(Check.checkName(cognome))
				{
					System.out.println("10");
					lblChangeSurnameCheck.setIcon(iconLogoT);
				}
				else
				{
					System.out.println("11");					
					lblChangeSurnameCheck.setIcon(iconLogoC);
				}


				if(Check.checkMail(mail) && (!Check.checkMailExist(mail)))
				{
					lblChangeEmailCheck.setIcon(iconLogoT);
				}
				else
				{
					lblChangeEmailCheck.setIcon(iconLogoC);
				}

				if(Check.checkPass1(pass))
				{
					lblChangePassCheck.setIcon(iconLogoT);
				}
				else
				{
					lblChangePassCheck.setIcon(iconLogoC);
				}
				
				if(Check.checkPass1(checkPassword))
				{
					lblChangePassConfCheck.setIcon(iconLogoT);
				}
				else
				{
					lblChangePassConfCheck.setIcon(iconLogoC);
				}
				
				if(Check.checkPassEq(pass, checkPassword))
				{
					lblChangePassConfCheck.setIcon(iconLogoT);
				}
				else
				{
					lblChangePassConfCheck.setIcon(iconLogoC);
				}
				System.out.println("12");	
				if(Check.checkName(inq))
				{
					System.out.println("13");	
					lblChangeInqCheck.setIcon(iconLogoT);
				}
				else
				{
					System.out.println("14");
					lblChangeInqCheck.setIcon(iconLogoC);
				}		
				if(Check.checkTel(tel))
				{
					lblChangePhoneCheck.setIcon(iconLogoT);
				}
				else
				{
					lblChangePhoneCheck.setIcon(iconLogoC);
				}	
				}
			 
				
		}
	});
	
		btnModData.setBounds(288, 391, 175, 67);
		panelModify.add(btnModData);
		
		JButton btnBackData = new JButton("Annulla");
		btnBackData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				PopUp.warningBox(frmSchoolib, "Le modifiche effettuate verranno annullate");
				
				if(PopUp.confirmBox(frmSchoolib))
				{
					
					panelAccount.setVisible(true);
					panelModify.setVisible(false);
					
					txtNameMod.setText(null);
					txtSurnameMod.setText(null);
					txtMailMod.setText(null);
					passwordFieldMod.setText(null);
					passwordFieldConfMod.setText(null);
					txtInqMod.setText(null);
					txtTelMod.setText(null);	
					rdbtnTypeUserLibMod.setSelected(false);
					rdbtnTypeUserLetMod.setSelected(true);
					
					txtNameMod.setEditable(false);
					txtSurnameMod.setEditable(false);
					txtMailMod.setEditable(false);
					passwordFieldMod.setEditable(false);
					passwordFieldConfMod.setEditable(false);
					txtInqMod.setEditable(false);
					txtTelMod.setEditable(false);
					
					lblChangeNameCheck.setIcon(null);
					lblChangeSurnameCheck.setIcon(null);
					lblChangeEmailCheck.setIcon(null);
					lblChangePassCheck.setIcon(null);
					lblChangePassCheck.setIcon(null);
					lblChangePassConfCheck.setIcon(null);
					lblChangeInqCheck.setIcon(null);
					lblChangePhoneCheck.setIcon(null);
				}
			}
		});
		btnBackData.setBounds(523, 391, 175, 67);
		panelModify.add(btnBackData);
		
		this.emailuser=txtMailMod.getText();
		
	}
	
	public void updateall(String[] user )
	{
		setIdUser(user[0]);
		lblSetNome.setText(user[1]);
		lblSetCognome.setText(user[2]);
		lblSetEmail.setText(user[3]);
		lblSetInq.setText(user[5]);
		lblSetTel.setText(user[6]);
		lblSetTipoUte.setText(user[7]);
		
	}
	
	
	public void updatelblSetNome(String[] user )
	{
		lblSetNome.setText(user[0]);	
	}
	public void updatelblSetId(String[] user )
	{
		setIdUser(user[0]);	
	}	
	
	public String getIdUser() {
			return idUser;
	}

	public void setIdUser(String idUser) {
			this.idUser = idUser;
	}
	//TODO E SEGUENTI CAMPI...

	public String getEmailuser() {
		return emailuser;
	}

	public void setEmailuser(String emailuser) {
		this.emailuser = emailuser;
	}

	public boolean isCambioemail() {
		return cambioemail;
	}

	public void setCambioemail(boolean cambioemail) {
		this.cambioemail = cambioemail;
	}
	
	
	
	/*
	public static void ReadUser1 ()throws SQLException{	
		String query = "SELECT * FROM utente ORDER BY id DESC LIMIT 1;";  //  
		DBmanager.openConnection();
		ResultSet rs = DBmanager.executeQuery(query);
		System.out.println(query);
		System.out.println("ewrtet");

		if(rs.next())
			{
				 System.out.println("tertret");
				
				 String s = rs.getString("nome");
				 lblSetNome.setText(s);
				
				 String s1 = rs.getString("cognome");
				 lblSetCognome.setText(s1);
				
				 String s2 = rs.getString("email");
				 lblSetEmail.setText(s2);
				
				 String s3 = rs.getString("tipo_utente");
				 lblSetTipoUte.setText(s3);
				
				 String s4 = rs.getString("inquadramento");
				 lblSetInq.setText(s4);
				
				 String s5 = rs.getString("password_temp");
				 lblSetPass.setText(s5);
	
				 String s6 = rs.getString("ntel");
				 lblSetTel.setText(s6);
				 
				 // mettere anche num_pren_correnti
				
		rs.close();
		DBmanager.closeConnection();
	
			}
		
	}
*/
}
