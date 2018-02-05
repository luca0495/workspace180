package gui;


import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Check.Check;
import Check.PopUp;
import Core.Clients;
import connections.Client;
import database.MQ_Update;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;

public class Setting extends SL_JFrame{
	

	private static final long 	serialVersionUID = 1L;

	static int 			userRow = 0;
	static int			rows = 0;
    static int			cols = 0; 		    

	public 	JTextField			text;	    

		    
	private Setting 			w;	
	private JFrame 				frmSchoolib;
	private Client 				me;

	
	
	private JTextField 			textFieldsrvIPlocal; 
	private JTextField 			textFieldsrvIPlan; 
	private JTextField 			textFieldsrvIPwww; 
	private JTextField 			textFieldsrvIPdefault; 
	private JTextField 			textFieldsrvMailAddress; 
	private JPasswordField		textFieldsrvMailPW; 

	
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
    private int 				idUser ;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_4;
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
    private JComboBox comboBox;


public Setting(Component c,Client x,String[]userdata)
	{
		setW(this);
		me = x;
		me.setActW(this);
		me.setActC(c);
		me.setCliType(Clients.Librarian);		
		Setting(c);
		setUserdata(userdata);
		setf(userdata);
		
		System.out.println("da lettura query ... userdata [4] " + getUserdata()[4]);
		
		
		
		
	}
	
	public void Setting(Component c) {
	
		frmSchoolib = new JFrame();
		frmSchoolib.setTitle("Schoolib");
		frmSchoolib.setBounds(100, 100, 560, 545);
		frmSchoolib.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmSchoolib.setLocationRelativeTo(c);
		frmSchoolib.setVisible(true);
		frmSchoolib.getContentPane().setLayout(new CardLayout(0, 0));
	
		
		panelChangePass = new JPanel();
		frmSchoolib.getContentPane().add(panelChangePass, "name_443629321471336");
		panelChangePass.setLayout(null);
		
		ButtonGroup bgMod = new ButtonGroup();
		
		JLabel lblChange = new JLabel("SETTING");
		lblChange.setForeground(new Color(255, 51, 153));
		lblChange.setHorizontalAlignment(SwingConstants.CENTER);
		lblChange.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblChange.setBounds(10, 11, 524, 34);
		panelChangePass.add(lblChange);
		
		lblLocalhost = new JLabel("IP localhost");
		lblLocalhost.setBounds(64, 101, 127, 14);
		panelChangePass.add(lblLocalhost);
		
		lblLanIp = new JLabel("IP lan");
		lblLanIp.setBounds(64, 139, 127, 14);
		panelChangePass.add(lblLanIp);
		
		lblWwwIp = new JLabel("IP www");
		lblWwwIp.setBounds(64, 164, 127, 14);
		panelChangePass.add(lblWwwIp);
		
		lblEmailPw = new JLabel("email PW");
		lblEmailPw.setBounds(64, 312, 127, 14);
		panelChangePass.add(lblEmailPw);
		
		lblDefault = new JLabel("Default Server Type");
		lblDefault.setBounds(64, 210, 97, 14);
		panelChangePass.add(lblDefault);
		
		lblEmailAddress = new JLabel("email Address");
		lblEmailAddress.setBounds(64, 281, 127, 14);
		panelChangePass.add(lblEmailAddress);
		
		textField_2 = new JTextField();
		textField_2.setText( "127.0.0.1");
		
		textField_2.setBounds(208, 98, 137, 20);
		panelChangePass.add(textField_2);
		textField_2.setColumns(10);
		textField_2.setEditable(false);
		textField_2.setEnabled(false);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(208, 136, 137, 20);
		panelChangePass.add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(208, 167, 137, 20);
		panelChangePass.add(textField_4);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(208, 278, 272, 20);
		panelChangePass.add(textField_6);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(208, 309, 138, 20);
		panelChangePass.add(passwordField);
	
		
		button = new JButton("Conferma");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {

					me.setActW(me.getStartWindow());
					
					System.out.println("combo: " + getComboBox().getSelectedItem().toString());
					System.out.println("email user : " + getTextField_6().getText());
					System.out.println("email pw   : " + String.valueOf(getPasswordField().getPassword()));
					
					
					MQ_Update.updateSetting(
												getTextField_2().getText(),
												getTextField_3().getText(),
												getTextField_4().getText(),
												getComboBox().getSelectedItem().toString(),
												getTextField_6().getText(),
												String.valueOf(getPasswordField().getPassword())
																);

					Object x = getComboBox().getSelectedItem();
					me.getStartWindow().getComboBox().setSelectedItem(x);
					me.getStartWindow().aggiornaSrvType(x);
					
					
					frmSchoolib.setVisible(false);
					PopUp.infoBox(frmSchoolib, "dati modificati con successo ");					
					
				} catch (SQLException e) {
					
					PopUp.infoBox(frmSchoolib, "dati non modificati ");
					e.printStackTrace();
					}
			}
		});
		button.setBounds(322, 379, 158, 82);
		panelChangePass.add(button);
		
		
		button_1 = new JButton("Annulla");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				me.setActW(me.getStartWindow());
				
				String[] datasetting;
				
				PopUp.infoBox(frmSchoolib, "dati non modificati ");
				frmSchoolib.setVisible(false);

				
			}
		});
		button_1.setBounds(57, 379, 158, 82);
		panelChangePass.add(button_1);
		
		JLabel lblLocallanwww = new JLabel("{ local,lan,www }");
		lblLocallanwww.setBounds(64, 225, 120, 14);
		panelChangePass.add(lblLocallanwww);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(47, 250, 450, 109);
		panelChangePass.add(separator);
		
		comboBox = new JComboBox();
		comboBox.setBounds(208, 207, 137, 20);
		 getComboBox().addItem("local"); 
		 getComboBox().addItem("lan"); 
		 getComboBox().addItem("www"); 
		panelChangePass.add(comboBox);
	
	}
//********************************************************	
	public void setf(String[]userd) {
		getTextField_2().setText(userd[0]);
		getTextField_3().setText(userd[1]);
		getTextField_4().setText(userd[2]);
		switch (userd[3]) {	
			case "local":
				getComboBox().setSelectedIndex(0);
				break;
			
			case "lan":
				getComboBox().setSelectedIndex(1);
				break;
					
			case "www":
				getComboBox().setSelectedIndex(2);
				break;		
			
			default:
				break;
		}
		getTextField_6().setText(userd[4]);
		getPasswordField().setText(userd[5]);
	}
//********************************************************	
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
	
	   public JComboBox getComboBox() {
			return comboBox;
		}

		public void setComboBox(JComboBox comboBox) {
			this.comboBox = comboBox;
		}
}
