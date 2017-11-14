package gui;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.FlowLayout;

public class Login extends JFrame {
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
	
	public Login(Component c)
	{
		Login(c);
	}

	public void Login(Component c) {
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
		btnConferma.setBounds(201, 428, 141, 23);
		PanelFirstAcc.add(btnConferma);
		
	
		
		
	
		
		
		
	}
}
