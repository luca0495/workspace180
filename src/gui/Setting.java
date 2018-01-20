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
import java.awt.event.WindowAdapter;
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

import com.sun.org.apache.bcel.internal.generic.SWITCH;

import Check.Check;
import Check.PasswordBox;
import Check.PopUp;
import Core.Clients;
import Core.Commands;
import ProvaEmail.EmailSender;
import connections.Client;
import database.DBmanager;
import database.LoadUser;
import database.MQ_Delete;
import database.MQ_Read;
import database.MQ_Update;

import javax.swing.JLabel;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JRadioButton;
import javax.swing.JPasswordField;
import java.awt.Font;

public class Setting extends SL_JFrame{
	

	private static final long 	serialVersionUID = 1L;
	private static String[] 	UserData = null;
    private static JTextField 	s1;
			static int 			userRow = 0;
		    static int			rows = 0;
		    static int			cols = 0; 		    
		    
	
		    
		    
	private Setting 			w;	
	private JFrame 				frmSchoolib;
	private AppReader 			a;
	private LoadUser 			l = new LoadUser();
	private Client 				me;
	
	private JTextField 			textFieldsrvIPlocal; 
	private JTextField 			textFieldsrvIPlan; 
	private JTextField 			textFieldsrvIPwww; 
	private JTextField 			textFieldsrvIPdefault; 
	private JTextField 			textFieldsrvMailAddress; 
	private JPasswordField		textFieldsrvMailPW; 
	
	
	
	private String TypePerson = "Lettore";
	private String input;
	private List<String> rowData = new ArrayList<String>();

	private String[] user = null;
	private String[] user1 = null;
	private boolean User = true;
	
	// in test 
	private String 		emailuser;
	private boolean 	cambioemail=false;
	private String [] 	userdata;
	



	
	//private JPanel panelAccount;
	//private JPanel panelModify;
	private JPanel panelChangePass;
	
	//private boolean mailcheckinprogress=false;
	public static boolean ModPass = false;
	//private JPasswordField passwordFieldConfMod;

	
	private int 				column;
	private int 				deleteRow;
    private int 				idUser ;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_4;
    private JTextField textField_5;
    private JTextField textField_6;
    private JPasswordField passwordField;
    private JTextField textField_3;
    private JLabel lblLanIp;
    private JLabel lblWwwIp;
    private JLabel lblDefault;
    private JLabel lblLocalhost;
    private JLabel lblEmailAddress;
    private JLabel lblEmailPw;
    private JButton button;
    private JButton button_1;

	
   public Setting(Component c,Client x)
	{
		
		setW(this);
		me = x;
		me.setActW(this);
		me.setActC(c);
		me.setCliType(Clients.Librarian);		
		
		Setting(c);
		
	}
	
	public void Setting(Component c) {
	
		frmSchoolib = new JFrame();
		frmSchoolib.setTitle("Schoolib");
		frmSchoolib.setBounds(100, 100, 893, 545);
		frmSchoolib.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmSchoolib.setLocationRelativeTo(c);
		frmSchoolib.setVisible(true);
		frmSchoolib.getContentPane().setLayout(new CardLayout(0, 0));
	
		ImageIcon iconLogoT = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/Tick.png")));
		ImageIcon iconLogoC = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/Cross.png")));
		ImageIcon iconLogoRA = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/RedArrow.png")));
		ImageIcon iconLogoQ = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/question.png")));
		
		panelChangePass = new JPanel();
		frmSchoolib.getContentPane().add(panelChangePass, "name_443629321471336");
		panelChangePass.setLayout(null);
		
		ButtonGroup bgMod = new ButtonGroup();
		
		JLabel lblChange = new JLabel("SETTING");
		lblChange.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblChange.setBounds(172, 11, 348, 34);
		panelChangePass.add(lblChange);
		
		lblLocalhost = new JLabel("localhost");
		lblLocalhost.setBounds(27, 119, 120, 14);
		panelChangePass.add(lblLocalhost);
		
		lblLanIp = new JLabel("lan IP");
		lblLanIp.setBounds(24, 157, 123, 14);
		panelChangePass.add(lblLanIp);
		
		lblWwwIp = new JLabel("www IP");
		lblWwwIp.setBounds(27, 182, 120, 14);
		panelChangePass.add(lblWwwIp);
		
		lblEmailPw = new JLabel("email PW");
		lblEmailPw.setBounds(27, 299, 120, 14);
		panelChangePass.add(lblEmailPw);
		
		lblDefault = new JLabel("Default");
		lblDefault.setBounds(27, 228, 120, 14);
		panelChangePass.add(lblDefault);
		
		lblEmailAddress = new JLabel("email Address");
		lblEmailAddress.setBounds(27, 268, 120, 14);
		panelChangePass.add(lblEmailAddress);
		
		textField_2 = new JTextField();
		textField_2.setBounds(157, 116, 272, 20);
		panelChangePass.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(157, 154, 272, 20);
		panelChangePass.add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(157, 185, 272, 20);
		panelChangePass.add(textField_4);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(157, 225, 272, 20);
		panelChangePass.add(textField_5);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(157, 265, 272, 20);
		panelChangePass.add(textField_6);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(157, 296, 272, 20);
		panelChangePass.add(passwordField);
		
		button = new JButton("Conferma");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					MQ_Update.updateSetting(
												getTextField_2().getText(),
												getTextField_3().getText(),
												getTextField_4().getText(),
												getTextField_5().getText(),
												getTextField_6().getText(),
												getPasswordField().getText()
												
																);
				} catch (SQLException e) {
					// 
					e.printStackTrace();
				}
				
				
				
			}
		});
		button.setBounds(271, 378, 158, 82);
		panelChangePass.add(button);
		
		
		button_1 = new JButton("Annulla");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
			}
		});
		button_1.setBounds(85, 379, 158, 82);
		panelChangePass.add(button_1);
	
	}
	
	
	
		
			public JPasswordField getPasswordField() {
		return passwordField;
	}

	public void setPasswordField(JPasswordField passwordField) {
		this.passwordField = passwordField;
	}

			public JTextField getTextField() {
		return textField;
	}

	public void setTextField(JTextField textField) {
		this.textField = textField;
	}

	public JTextField getTextField_1() {
		return textField_1;
	}

	public void setTextField_1(JTextField textField_1) {
		this.textField_1 = textField_1;
	}

	public JTextField getTextField_2() {
		return textField_2;
	}

	public void setTextField_2(JTextField textField_2) {
		this.textField_2 = textField_2;
	}

	public JTextField getTextField_4() {
		return textField_4;
	}

	public void setTextField_4(JTextField textField_4) {
		this.textField_4 = textField_4;
	}

	public JTextField getTextField_5() {
		return textField_5;
	}

	public void setTextField_5(JTextField textField_5) {
		this.textField_5 = textField_5;
	}

	public JTextField getTextField_6() {
		return textField_6;
	}

	public void setTextField_6(JTextField textField_6) {
		this.textField_6 = textField_6;
	}

	public JTextField getTextField_3() {
		return textField_3;
	}

	public void setTextField_3(JTextField textField_3) {
		this.textField_3 = textField_3;
	}

			public void updateall(String[] user )
	{
		String idutente = user[0];
		if (idutente.equals("")||idutente.equals("Nessun Dato")) {
			setIdUser(0);	
		}else {
			setIdUser(Integer.valueOf(idutente));
		}
		
		
		getTextFieldsrvIPdefault().setText(user[0]);
		getTextFieldsrvIPlocal().setText(user[1]);
		getTextFieldsrvIPlan().setText(user[2]);
		getTextFieldsrvIPwww().setText(user[3]);
		
		getTextFieldsrvMailAddress().setText(user[4]);
		getTextFieldsrvMailPW().setText(user[5]);

		

		
	}	
// testaggio campi *************************************************************************************************************************** 	
	
	public JTextField getTextFieldsrvIPlocal() {
				return textFieldsrvIPlocal;
			}

			public void setTextFieldsrvIPlocal(JTextField textFieldsrvIPlocal) {
				this.textFieldsrvIPlocal = textFieldsrvIPlocal;
			}

			public JTextField getTextFieldsrvIPlan() {
				return textFieldsrvIPlan;
			}

			public void setTextFieldsrvIPlan(JTextField textFieldsrvIPlan) {
				this.textFieldsrvIPlan = textFieldsrvIPlan;
			}

			public JTextField getTextFieldsrvIPwww() {
				return textFieldsrvIPwww;
			}

			public void setTextFieldsrvIPwww(JTextField textFieldsrvIPwww) {
				this.textFieldsrvIPwww = textFieldsrvIPwww;
			}

			public JTextField getTextFieldsrvIPdefault() {
				return textFieldsrvIPdefault;
			}

			public void setTextFieldsrvIPdefault(JTextField textFieldsrvIPdefault) {
				this.textFieldsrvIPdefault = textFieldsrvIPdefault;
			}

			public JTextField getTextFieldsrvMailAddress() {
				return textFieldsrvMailAddress;
			}

			public void setTextFieldsrvMailAddress(JTextField textFieldsrvMailAddress) {
				this.textFieldsrvMailAddress = textFieldsrvMailAddress;
			}

			public JPasswordField getTextFieldsrvMailPW() {
				return textFieldsrvMailPW;
			}

			public void setTextFieldsrvMailPW(JPasswordField textFieldsrvMailPW) {
				this.textFieldsrvMailPW = textFieldsrvMailPW;
			}

	public boolean checkPass1() {
		boolean checkok=true;
		if(Check.checkPass(textFieldsrvMailPW.getPassword()))
		{

		}
		else
		{
					
		}
		return checkok;	
	}	


	public int getIdUser() {
			return idUser;
	}

	public void setIdUser(int idUser) {
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

	public String [] getUserdata() {
		return userdata;
	}

	public void setUserdata(String [] userdata) {
		this.userdata = userdata;
	}

	public Setting getW() {
		return w;
	}

	public void setW(Setting w) {
		this.w = w;
	}
}
