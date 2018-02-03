package gui;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.print.DocFlavor.STRING;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;

import Check.Check;
import Check.CheckMail;
import Check.PopUp;
import Core.Clients;
import Core.Commands;
import ProvaEmail.EmailSender;
import connections.Client;
import database.MQ_Delete;
import database.MQ_Insert;
import database.MQ_Read;
import database.MQ_Update;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Login extends SL_JFrame  {
	
	private static final long serialVersionUID = 1L;
	private Login 				w;
	private JFrame 				frmSchoolib;
	private JPanel 				pr;
	private JPanel 				pfa;
	private JPasswordField 		passwordFieldUser;
	private JPasswordField 		passwordField_1;
	private JTextField 			txtUser;
	private JTextField 			textField_2;
	private JTextField 			textField;
	private boolean 			result = false;	
	private int 				clicked = 0;
	private JFrame 				frame;
	private Client 				me;
	private List<String> 		rowData;
	private String 				deleteRow;
	private int 				idUser;
	private String [] 			user;

	private JTextField 			txtEmail;
	private JTextField 			txtEmailForgot;
	
	private JPasswordField 		passwordFieldNewPass;
	private JPasswordField 		passwordFieldNewPassC;
	private JPasswordField 		passwordField_4;
	private JPasswordField 		passwordField_5;
	private JPasswordField 		passwordField_6;
	
	private boolean 			mailcheckinprogress=false;
	private String 				mailcheckResult;
	
	public Login(Component c,Client x)
	{
		
		setW(this);
		me = x;
		me.setActW(this);
		me.setActC(c);
		me.setCliType(Clients.Reader);
				
		Login(c);
	}
	public void Login(Component c)
	{
		super.setSL_Type(AppType.AppLogin);
		
		frmSchoolib = new JFrame();
		frmSchoolib.setTitle("Login");
		frmSchoolib.setBounds(100, 100, 893, 545);
		frmSchoolib.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmSchoolib.setLocationRelativeTo(c);
		frmSchoolib.setVisible(true);
		frmSchoolib.getContentPane().setLayout(new CardLayout(0, 0));
		
		JPanel PanelRegi = new JPanel();
		frmSchoolib.getContentPane().add(PanelRegi, "name_1495174434374087");
		PanelRegi.setLayout(null);
		setPr(PanelRegi);
		
		JPanel PanelFirstAcc = new JPanel();
		frmSchoolib.getContentPane().add(PanelFirstAcc, "name_120013554778492");
		PanelFirstAcc.setLayout(null);
		setPfa(PanelFirstAcc);
		
		JPanel PanelForgPass = new JPanel();
		frmSchoolib.getContentPane().add(PanelForgPass, "name_730685170965255");
		PanelForgPass.setLayout(null);
		
		ImageIcon iconLogoT = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/Tick.png")));
		ImageIcon iconLogoC = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/Cross.png")));
		
		JLabel lblLogin = new JLabel("LOGIN");
		lblLogin.setBounds(341, 11, 128, 46);
		lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 19));
		PanelRegi.add(lblLogin);
		
		JLabel lblEmailUser = new JLabel("Email");
		lblEmailUser.setBounds(80, 76, 82, 14);
		lblEmailUser.setFont(new Font("Tahoma", Font.PLAIN, 16));
		PanelRegi.add(lblEmailUser);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(80, 115, 89, 17);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		PanelRegi.add(lblPassword);
		
		JLabel lblPassReset = new JLabel("Password dimenticata?");
		lblPassReset.setBounds(287, 146, 159, 14);
		PanelRegi.add(lblPassReset);
		
		JLabel lblClickHere = new JLabel("Clicca qui");
		lblClickHere.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//***
				
		//Assegna a delle variabili il contenuto dei text field
				
				
				
				String mail = getTxtEmail().getText();
				setMailcheckinprogress(true);
				
				me.setSql2("LoginPWRecovery");
				//parte check mail...
				CheckMail mc = new CheckMail();
				
				mc.checkmailLoginForgotPassword(me,mail,mail, frmSchoolib, getW(), null);

				while (isMailcheckinprogress()) {	//attendi... //System.out.println("attesa per check email exist");		
					System.out.println("attendo check result"+getMailcheckResult());
					try {
						Thread.sleep(10);
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
				}
				//-------------------------------------------------------------------------------------------------------		
				System.out.println("GUI Login :> ritornato mail check result"+getMailcheckResult());

				switch (getMailcheckResult()) {
				
				case "problemi con user read check mail":					
					System.out.println("GUI Login :> problemi con user read check mail");
					PopUp.errorBox(frmSchoolib, "GUI Login :> problemi con user read check mail");
					break;
				
				case "sintatticamente non corretta":
					System.out.println("GUI Login :> sintatticamente non corretta");
					PopUp.errorBox(frmSchoolib, "GUI Login :> sintatticamente non corretta");
					break;
					
				case "nulla":
					System.out.println("GUI Login :> nulla");
					PopUp.errorBox(frmSchoolib, "GUI Login :> nulla");
					break;	
			
				//dopo test mail --> db	
				case "NG":
					String msgE1 = "la mail inserita ( "+mail+" ) ha causato un problema di interrogazione al database , la preghiamo di riprovare";
					System.out.println(msgE1);
					PopUp.errorBox(frmSchoolib, msgE1);
					break;
					
				//dopo test mail --> db	
				case "OK NE":
					String msgE = "la mail inserita ( "+mail+" ) non risulta essere di nessun utente registrato, inserire email valida";
					System.out.println(msgE);
					PopUp.errorBox(frmSchoolib, msgE);
					break;
				
				//dopo test mail --> db		
				case "OK E":
					System.out.println("ritornato dal check mail EXIST");
					PopUp.infoBox(frmSchoolib, "Inizio PROCEDURA INVIO EMAIL CON PW TEMP");
					//random password check ok
					String newpass= Check.s();
					//String q = MQ_Update.updateNewPassForgotGETQUERY(mail, newpass);
					
					String q = MQ_Update.updateNewPassForgotGETQUERY(mail, newpass);
					
					try
					{
					
					me.setPw(newpass);	
					me.setSql2(mail);
					me.setSql(q);
					
					me.setActW(getW());
					me.setActF(frmSchoolib);
					me.setActC(c);				
					try {
						System.out.println("GUI account:> ottenuti dati user ");
						Clients oldtype = me.getCliType();
						
					me.setCliType(Clients.Librarian);	
						me.getCmdLIST().put(Commands.UserPasswordRecovery);
						me.setCliType(oldtype);
					
					
					} catch (InterruptedException e2) {
						System.out.println("GUI account:> NON ottenuti dati user ");	
						e2.printStackTrace(); 
					}
					}
					catch (Exception exq) 
					{
						exq.printStackTrace();
					}					
					//*************************************************************						
					break;			

				default:
					break;
				} 	
//***

			}
		});
		lblClickHere.setForeground(Color.BLUE);
		lblClickHere.setBounds(456, 146, 67, 14);
		PanelRegi.add(lblClickHere);
		
		passwordFieldUser = new JPasswordField();
		passwordFieldUser.setBounds(287, 115, 282, 20);
		PanelRegi.add(passwordFieldUser);
		
		passwordFieldUser.setText(	"Pass0$");
									 
		txtUser = new JTextField();
		
		setTxtEmail(txtUser);
		
		txtUser.setBounds(287, 75, 282, 20);
		PanelRegi.add(txtUser);
		txtUser.setColumns(10);
		
		txtUser.setText("dexa@hotmail.it");
		
		
		JButton btnIndietro_1 = new JButton("Indietro");
		btnIndietro_1.setBounds(147, 212, 89, 46);
		btnIndietro_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				
				WindowEvent close = new WindowEvent(frmSchoolib, WindowEvent.WINDOW_CLOSING);
				frmSchoolib.dispatchEvent(close);	
			}
		});
		PanelRegi.add(btnIndietro_1);
		
		JButton btnEntra = new JButton("ENTRA");
		btnEntra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String email = txtUser.getText();
				String pass = String.copyValueOf(passwordFieldUser.getPassword());
				String r = null;
			
				try 
				{
					// query su db ma su tabella SETTING
					System.out.println(" password temp letta"+MQ_Read.ReadPassTemp1(email));					
					if(	MQ_Read.ReadPassTemp1(email) == null || (Integer.valueOf(MQ_Read.ReadPassTemp1(email)) == 0))
					{
						System.out.println("passo email    :"+email);
						System.out.println("passo password :"+pass);
							
						me.setSql(email);
						me.setSql2(pass);
						me.setActW(getW());
						me.setActF(frmSchoolib);
						me.setActC(c);
						
						//da client se login ok:
						//Account lo = new Account(getActF,this);				
						//me.setActW(lo);
						
						try {
							System.out.println("GUI login:> controllo user corretto ");
						//me.setCliType(Clients.Librarian);	
						
							Thread.sleep(100);
							
							me.getCmdLIST().put(Commands.UserREADlogin);
						} catch (InterruptedException e2) {
							System.out.println("GUI login :> problemi con controllo user corretto ");	
							e2.printStackTrace(); 
						}	
					}
					else
					{
						PopUp.errorBox(frmSchoolib, "Confermare account su bottone PRIMO ACCESSO");
					}
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			
				
				
				//OLD
				/*
				try 
				{
					r = Check.checkAdminLogIn(email, pass);
				} 
				catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
				*/

				
/*			
				if(r.equals("Login Corretto"))
				    {
					// cambiare pannello
					   System.out.println("2");
					   System.out.println("Numero di clicked1" + clicked);

*/
	// APERTA FINESTRA		
					   /*old passato a client
						try {	
						MQ_Delete.deletePassTemp(getIdUser(),pass);						
						} catch (SQLException e1) {							
							e1.printStackTrace();
						}
						*/		   
/*
					   	//System.out.println("passato da login a client la email : "+me.getSql());
						Account lo = new Account(getFrame(),me);				
						me.setActW(lo);
						me.setActF(frmSchoolib);
						
						PanelFirstAcc.setVisible(false);
						PanelRegi.setVisible(false);
						WindowEvent close = new WindowEvent(frmSchoolib, WindowEvent.WINDOW_CLOSING);
						frmSchoolib.dispatchEvent(close);	
						
				// ***
				
						try {
							System.out.println("GUI login:> cmd tentativo di login ");
						me.setCliType(Clients.Librarian);	
							me.getCmdLIST().put(Commands.UserREADbyEmail);
						} catch (InterruptedException e2) {
							System.out.println("GUI login :> problemi con tentativo di login ");	
							e2.printStackTrace(); 
						}	
						
				    } 
		    else 
		    {
			PopUp.errorBox(c, r);
		    }    
*/
		}
		
		
		
	});
		btnEntra.setBounds(287, 212, 104, 46);
		PanelRegi.add(btnEntra);
		
		JButton btnPrimoAccesso = new JButton("PRIMO ACCESSO");
		btnPrimoAccesso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				
			}
		});
		btnPrimoAccesso.setBounds(443, 212, 135, 46);
		btnPrimoAccesso.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				textField_2.setText(txtUser.getText());
				passwordFieldUser.getPassword();
				

				
				
				PanelRegi.setVisible(false);
				PanelFirstAcc.setVisible(true);
				PanelForgPass.setVisible(false);
				
				
				
				
				
				
				
			}
		});
		PanelRegi.add(btnPrimoAccesso);
		
		
		
		
		// panel FirstAcc rifare
		JLabel lblUser_1 = new JLabel("user");
		lblUser_1.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblUser_1.setBounds(20, 118, 46, 23);
		PanelFirstAcc.add(lblUser_1);
		
		JLabel lblPass = new JLabel("password temporanea");
		lblPass.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblPass.setBounds(20, 196, 215, 34);
		PanelFirstAcc.add(lblPass);
		
		textField_2 = new JTextField();
		textField_2.setBounds(266, 123, 244, 20);
		PanelFirstAcc.add(textField_2);
		textField_2.setColumns(10);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(266, 207, 244, 20);
		PanelFirstAcc.add(passwordField_1);
		
		JButton btnIndietro_2 = new JButton("Indietro");
		btnIndietro_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				PanelRegi.setVisible(true);
				PanelFirstAcc.setVisible(false);
				PanelForgPass.setVisible(false);
			}
		});
		btnIndietro_2.setBounds(376, 296, 141, 57);
		PanelFirstAcc.add(btnIndietro_2);
		
		JButton btnConferma = new JButton("Conferma");
		btnConferma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			String email = textField_2.getText();
			String pass = String.copyValueOf(passwordField_1.getPassword());
			String r = null;
		
			System.out.println("passo email    :"+email);
			System.out.println("passo password :"+pass);
			
			me.setSql(email);
			me.setSql2(pass);
			me.setActW(getW());
			me.setActF(frmSchoolib);
			me.setActC(c);
			
			try {
				System.out.println("GUI login:> controllo user corretto ");
			//me.setCliType(Clients.Librarian);
				
				me.getCmdLIST().put(Commands.UserREADloginFIRST);
				
			} catch (InterruptedException e2) {
				System.out.println("GUI login :> problemi con controllo user corretto ");	
				e2.printStackTrace(); 
			}
			
			
			
			
				
			/*old
			try 
			{
				  System.out.println("1");

				  // TEST OK
				  //System.out.println("passo email    :"+email);
				  //System.out.println("passo password :"+pass);
				  
				  r = Check.checkAdminLogInFIRST(email, pass);	
				
			} 
			catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
			*/
			
			/*
			if(r.equals("Login Corretto"))
			    {
				// cambiare pannello
				   System.out.println("2");
				   System.out.println("Numero di clicked1" + clicked);
			
// APERTA FINESTRA		
				   
					try {	
					MQ_Delete.deletePassTemp(pass);				
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
							   
				   
				   
				   	me.setSql(email);
				   	System.out.println("passato da login a client la email : "+me.getSql());
					
				   	Account lo = new Account(getFrame(),me);				
					me.setActW(lo);
					me.setActF(frmSchoolib);
					
					
					PanelFirstAcc.setVisible(false);
					PanelRegi.setVisible(false);
					WindowEvent close = new WindowEvent(frmSchoolib, WindowEvent.WINDOW_CLOSING);
					frmSchoolib.dispatchEvent(close);	
					
			// ***
			
					try {
						System.out.println("GUI login:> cmd tentativo di login ");
					me.setCliType(Clients.Librarian);	
						me.getCmdLIST().put(Commands.UserREADbyEmail);
					} catch (InterruptedException e2) {
						System.out.println("GUI login :> problemi con tentativo di login ");	
						e2.printStackTrace(); 
					}	
					
			// ***
				
			    }
			    else 
			    {
				    System.out.println("3");
				PopUp.errorBox(c, r);
				    System.out.println("Numero di clicked3: " + clicked);
				clicked++;
				if (clicked ==5){
					PopUp.errorBox(c, "Hai provato 5 volte, cancellazione automatica del profilo");

				   try {
					   
					   rowData = new ArrayList<String>();
						
						rowData.add(0, idUser);
					
						MQ_Delete.deleteRowPerson(rowData);
					   
				} catch (SQLException e1) {
					e1.printStackTrace();
				
				}
			  }
			  
			  
		}
			*/
			
			
      }
		
		
		
});
		btnConferma.setBounds(186, 296, 141, 57);
		PanelFirstAcc.add(btnConferma);	
		
		// Panel ForgotPass
		
		JButton btnNewPass = new JButton("Invia");
		
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(10, 31, 176, 33);
		PanelForgPass.add(lblEmail);
		
		JLabel lblCheckEmailForg = new JLabel();
		lblCheckEmailForg.setBounds(464, 40, 16, 16);
		PanelForgPass.add(lblCheckEmailForg);
		
		JLabel lblPassForgot = new JLabel("Nuova Password");
		lblPassForgot.setBounds(10, 122, 103, 14);
		PanelForgPass.add(lblPassForgot);
		
		JLabel lblCheckNewPass = new JLabel();
		lblCheckNewPass.setBounds(464, 122, 16, 16);
		PanelForgPass.add(lblCheckNewPass);
		
		JLabel lblPassCForgot = new JLabel("Conferma Nuova Passoword");
		lblPassCForgot.setBounds(10, 205, 152, 14);
		PanelForgPass.add(lblPassCForgot);
		
		JLabel lblCheckCNewPass = new JLabel();
		lblCheckCNewPass.setBounds(464, 205, 16, 16);
		PanelForgPass.add(lblCheckCNewPass);
	
		txtEmailForgot = new JTextField();
		txtEmailForgot.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				PanelForgPass.getRootPane().setDefaultButton(btnNewPass);
			}
		});
		txtEmailForgot.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				
				if(Check.checkMail(txtEmailForgot.getText())&& Check.checkMailExist(txtEmailForgot.getText())) 
				{
					lblCheckEmailForg.setIcon(iconLogoT);	
				}
				else
				{
					lblCheckEmailForg.setIcon(iconLogoC);	
				}
			
			}
		});
		txtEmailForgot.setBounds(171, 37, 283, 20);
		PanelForgPass.add(txtEmailForgot);
		txtEmailForgot.setColumns(10);
		
		passwordFieldNewPass = new JPasswordField();
		passwordFieldNewPass.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				PanelForgPass.getRootPane().setDefaultButton(btnNewPass);
			}
		});
		passwordFieldNewPass.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				
				if(Check.checkPass(passwordFieldNewPass.getPassword()))
				{
					lblCheckNewPass.setIcon(iconLogoT);
				}
				else
				{
					lblCheckNewPass.setIcon(iconLogoC);
				}
			}
		});
		passwordFieldNewPass.setBounds(171, 119, 283, 20);
		PanelForgPass.add(passwordFieldNewPass);
		
		passwordFieldNewPassC = new JPasswordField();
		passwordFieldNewPassC.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				PanelForgPass.getRootPane().setDefaultButton(btnNewPass);
			}
		});
		passwordFieldNewPassC.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				
				if(Check.checkPass(passwordFieldNewPassC.getPassword()) && Check.checkPassEq(passwordFieldNewPass.getPassword(), passwordFieldNewPassC.getPassword()))
				{
					lblCheckCNewPass.setIcon(iconLogoT);
				}
				else
				{
					lblCheckCNewPass.setIcon(iconLogoC);
				}

			}
		});
		passwordFieldNewPassC.setBounds(171, 203, 283, 20);
		PanelForgPass.add(passwordFieldNewPassC);
		
		// invia
		btnNewPass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if	(	Check.checkAllPassForg	(
						txtEmailForgot.getText(), 
						passwordFieldNewPass.getPassword(),
						passwordFieldNewPassC.getPassword()
						)
)
{
					String 	to 	= txtEmailForgot.getText();	
					String 	p 	= String.copyValueOf(passwordFieldNewPass.getPassword());
				    int 	n	= EmailSender.randomGenerator1();	
					
					
					try {
						
						MQ_Update.updatePassForgot(to,p,n);	
						try {
							EmailSender.send_uninsubria_email(to,me);
							PopUp.infoBox(frmSchoolib, "Modifiche effettuate con successo, riattiva account dal codice temporaneo che ti abbiamo inviato");
							
							PanelRegi.setVisible(true);
							PanelFirstAcc.setVisible(false);
							PanelForgPass.setVisible(false);
							
							txtEmailForgot.setText(null);
							passwordFieldNewPass.setText(null);
							passwordFieldNewPassC.setText(null);
							
							lblCheckEmailForg.setIcon(null);
							lblCheckNewPass.setIcon(null);
							lblCheckCNewPass.setIcon(null);
							
						} catch (SendFailedException e1) {
							
							e1.printStackTrace();
						} catch (MessagingException e1) {
							
							e1.printStackTrace();
						}			

					} catch (SQLException e2) {
						e2.printStackTrace();
					}
					
                 }
				else
				{
					PopUp.errorBox(frmSchoolib, "Campi Errati");
					
					if(Check.checkMail(txtEmailForgot.getText()) && (!Check.checkMailExist(txtEmailForgot.getText())))
					{
						lblCheckEmailForg.setIcon(iconLogoT);
					}
					else
					{
						lblCheckEmailForg.setIcon(iconLogoC);
					}

					if(Check.checkPass(passwordFieldNewPass.getPassword()))
					{
						lblCheckNewPass.setIcon(iconLogoT);
					}
					else
					{
						lblCheckNewPass.setIcon(iconLogoC);
					}
					
					if(Check.checkPass(passwordFieldNewPassC.getPassword()))
					{
						lblCheckCNewPass.setIcon(iconLogoT);
					}
					else
					{
						lblCheckCNewPass.setIcon(iconLogoC);
					}
					
					if(Check.checkPassEq(passwordFieldNewPass.getPassword(), passwordFieldNewPassC.getPassword()))
					{
						lblCheckCNewPass.setIcon(iconLogoT);
					}
					else
					{
						lblCheckCNewPass.setIcon(iconLogoC);
					}
				}
			}
});
		btnNewPass.setBounds(128, 286, 143, 71);
		PanelForgPass.add(btnNewPass);
		
		JButton btnNewBack = new JButton("Annulla");
		btnNewBack.setBounds(339, 285, 143, 72);
		PanelForgPass.add(btnNewBack);
	}
		//METODI
		public boolean isResult() {
			return result;
		}
		public void updatelblSetId(String[] user )
		{
			setIdUser(Integer.valueOf(user[0]));
		}	
		
		public int getIdUser() {
				return idUser;
		}

		public void setIdUser(int idUser) {
			this.idUser = idUser;
		}
		public JFrame getFrame() {
			return frame;
		}
	
		public JTextField getTextField_2() {
			return textField_2;
		}
		public JTextField setTextField_2(JTextField textField_2) {
			return this.textField_2 = textField_2;
		}
		public JPanel getPr() {
			return pr;
		}
		public void setPr(JPanel pr) {
			this.pr = pr;
		}
		public JPanel getPfa() {
			return pfa;
		}
		public void setPfa(JPanel pfa) {
			this.pfa = pfa;
		}
		public Login getW() {
			return w;
		}
		public void setW(Login w) {
			this.w = w;
		}
		public JTextField getTxtEmail() {
			return txtEmail;
		}
		public void setTxtEmail(JTextField txtEmail) {
			this.txtEmail = txtEmail;
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
	}
