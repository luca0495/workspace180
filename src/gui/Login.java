package gui;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;

import Check.Check;
import Check.PopUp;
import Core.Clients;
import Core.Commands;
import connections.Client;
import database.MQ_Delete;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends SL_JFrame  {
	private JFrame frmSchoolib;	
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JPasswordField passwordField_2;
	private JPasswordField passwordField_3;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField;
	private JPasswordField passwordField_4;
	private JPasswordField passwordField_5;
	private JPasswordField passwordField_6;
	private boolean result = false;	
	private static final long serialVersionUID = 1L;
	private int clicked = 0;
	private JFrame 			frame;
	private Client me;
	private List<String> rowData = new ArrayList<String>();
	private String deleteRow;

	
	public Login(Component c,Client x)
	{
		me = x;
		me.setActW(this);
		me.setActC(c);
		me.setCliType(Clients.Reader);
				
		Login(c);
	}
	public void Login(Component c)
	{
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
		
		JPanel PanelFirstAcc = new JPanel();
		frmSchoolib.getContentPane().add(PanelFirstAcc, "name_120013554778492");
		PanelFirstAcc.setLayout(null);
		
		
		JLabel lblLogin = new JLabel("LOGIN");
		lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblLogin.setBounds(341, 11, 128, 46);
		PanelRegi.add(lblLogin);
		
		JLabel lblUser = new JLabel("user");
		lblUser.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblUser.setBounds(80, 76, 46, 14);
		PanelRegi.add(lblUser);
		
		JLabel lblPassword = new JLabel("password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPassword.setBounds(80, 115, 67, 17);
		PanelRegi.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(287, 115, 282, 20);
		PanelRegi.add(passwordField);
		
		textField_1 = new JTextField();
		textField_1.setBounds(287, 75, 282, 20);
		PanelRegi.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnIndietro_1 = new JButton("Indietro");
		btnIndietro_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				PanelRegi.setVisible(false);
			}
		});
		btnIndietro_1.setBounds(157, 180, 89, 23);
		PanelRegi.add(btnIndietro_1);
		
		JButton btnEntra = new JButton("ENTRA");
		btnEntra.setBounds(296, 180, 89, 23);
		PanelRegi.add(btnEntra);
		
		JButton btnPrimoAccesso = new JButton("PRIMO ACCESSO");
		btnPrimoAccesso.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				PanelRegi.setVisible(false);
				PanelFirstAcc.setVisible(true);
			}
		});
		btnPrimoAccesso.setBounds(434, 180, 135, 23);
		PanelRegi.add(btnPrimoAccesso);
		
		// panel FirstAcc rifare
		JLabel lblUser_1 = new JLabel("user");
		lblUser_1.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblUser_1.setBounds(20, 40, 46, 23);
		PanelFirstAcc.add(lblUser_1);
		
		JLabel lblPass = new JLabel("password temporanea");
		lblPass.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblPass.setBounds(20, 94, 215, 34);
		PanelFirstAcc.add(lblPass);
		
		textField_2 = new JTextField();
		textField_2.setBounds(266, 45, 244, 20);
		PanelFirstAcc.add(textField_2);
		textField_2.setColumns(10);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(266, 105, 244, 20);
		PanelFirstAcc.add(passwordField_1);
		
		JLabel lblCambioPassword = new JLabel("CAMBIO PASSWORD");
		lblCambioPassword.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblCambioPassword.setBounds(269, 196, 224, 34);
		PanelFirstAcc.add(lblCambioPassword);
		
		JLabel lblPassword_1 = new JLabel("password");
		lblPassword_1.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblPassword_1.setBounds(20, 262, 141, 28);
		PanelFirstAcc.add(lblPassword_1);
		
		JLabel lblPassword_2 = new JLabel("password");
		lblPassword_2.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblPassword_2.setBounds(20, 331, 141, 23);
		PanelFirstAcc.add(lblPassword_2);
		
		passwordField_2 = new JPasswordField();
		passwordField_2.setBounds(266, 270, 244, 20);
		PanelFirstAcc.add(passwordField_2);
		
		passwordField_3 = new JPasswordField();
		passwordField_3.setBounds(266, 336, 244, 20);
		PanelFirstAcc.add(passwordField_3);
		
		JButton btnIndietro_2 = new JButton("Indietro");
		btnIndietro_2.setBounds(369, 428, 141, 23);
		PanelFirstAcc.add(btnIndietro_2);
		
		JButton btnConferma = new JButton("Conferma");
		btnConferma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			String email = textField_2.getText();
			String pass = String.copyValueOf(passwordField_1.getPassword());
			String r = null;
			
			try 
			{
				  System.out.println("1");
				  
//TODO MAURO
//TODO Passare tramite server con controllo LETTURA
				  			
				  r = Check.checkAdminLogIn(email, pass);	
				
			} 
			catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
			if(r.equals("Login Corretto"))
			    {
				// cambiare pannello
				   System.out.println("2");
				   System.out.println("Numero di clicked1" + clicked);

// APERTA FINESTRA				   
				   
				   
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
					   
					   MQ_Delete.deleteRowPerson1();
					   
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				}
			  }   
		}
			
});
		btnConferma.setBounds(201, 428, 141, 23);
		PanelFirstAcc.add(btnConferma);	
	}
		//METODI
		public boolean isResult() {
			return result;
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
	}
