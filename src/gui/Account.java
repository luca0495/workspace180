package gui;


import java.awt.CardLayout;
import java.awt.Component;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import connections.Client;
import database.DBmanager;
import database.LoadUser;
import database.MQ_Read;

import javax.swing.JLabel;
import javax.swing.JButton;

public class Account extends JFrame{

	private JFrame frmSchoolib;	
	private AppReader a;
	private LoadUser l;
	private Client me;
	private static final long serialVersionUID = 1L;
	private static String[] UserData = null;
	private JTextField s = null;
	static String[][] data = null;
    static int rows = 0;
    static int cols = 0; 
    String r = null;
    static String idUser = null;
    int userRow = 0;
	
	public Account(Component c) {
		
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
	
		
		JPanel panelAccount = new JPanel();
		frmSchoolib.getContentPane().add(panelAccount, "name_353435345061838");
		panelAccount.setLayout(null);
		
		JLabel lblNome = new JLabel("Nome: ");
		lblNome.setBounds(10, 14, 42, 30);
		panelAccount.add(lblNome);
		
		JLabel lblSetNome = new JLabel();
		lblSetNome.setBounds(106, 19, 186, 20);
		panelAccount.add(lblSetNome);
		
		JLabel lblCognome = new JLabel("Cognome: ");
		lblCognome.setBounds(10, 64, 59, 14);
		panelAccount.add(lblCognome);
		
		JLabel lblSetCognome = new JLabel("");
		lblSetCognome.setBounds(106, 64, 186, 20);
		panelAccount.add(lblSetCognome);
		
		JLabel lblEmail = new JLabel("Email: ");
		lblEmail.setBounds(10, 115, 46, 14);
		panelAccount.add(lblEmail);
		
		JLabel lblSetEmail = new JLabel("");
		lblSetEmail.setBounds(106, 112, 186, 20);
		panelAccount.add(lblSetEmail);
		
		JLabel lblPass = new JLabel("Password:");
		lblPass.setBounds(10, 154, 59, 14);
		panelAccount.add(lblPass);
		
		JLabel lblSetPass = new JLabel("");
		lblSetPass.setBounds(106, 154, 186, 20);
		panelAccount.add(lblSetPass);
		
		JLabel lblInq = new JLabel("Inquadramento: ");
		lblInq.setBounds(10, 195, 86, 14);
		panelAccount.add(lblInq);
		
		JLabel lblSetInq = new JLabel("");
		lblSetInq.setBounds(106, 189, 186, 20);
		panelAccount.add(lblSetInq);
		
		JLabel lblTipoUte = new JLabel("Tipo Utente:");
		lblTipoUte.setBounds(10, 240, 74, 14);
		panelAccount.add(lblTipoUte);
		
		JLabel lblSetTipoUte = new JLabel("");
		lblSetTipoUte.setBounds(106, 234, 186, 20);
		panelAccount.add(lblSetTipoUte);
		
		JLabel lblTel = new JLabel("Telefono:");
		lblTel.setBounds(10, 283, 59, 14);
		panelAccount.add(lblTel);
		
		JLabel lblSetTel = new JLabel("");
		lblSetTel.setBounds(106, 283, 186, 14);
		panelAccount.add(lblSetTel);
		
		JLabel lblNumPrenPend = new JLabel("Numero Di Prenotazioni:");
		lblNumPrenPend.setBounds(10, 328, 132, 14);
		panelAccount.add(lblNumPrenPend);
		
		JLabel lblSetNumPrenPend = new JLabel("");
		lblSetNumPrenPend.setBounds(152, 328, 140, 14);
		panelAccount.add(lblSetNumPrenPend);
		
		JButton btnDelete = new JButton("Cancella Profilo");
		btnDelete.setBounds(192, 381, 193, 54);
		panelAccount.add(btnDelete);
		
		JButton btnModify = new JButton("Modifica Profilo");
		btnModify.setBounds(428, 381, 186, 54);
		panelAccount.add(btnModify);
		
		
	}
	/*
	public static String ReadUser(String mail) throws SQLException
	{
		
		try {
			MQ_Read.ReadUser();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		LoadUser l = new LoadUser(idUser, data, rows, cols);
		
		if(l.checkId())
		{
			UserData = l.loadData();
		}
		UserData = l.loadData();
		Connection conn = null;
		Statement stmt = null;
		String query = "SELECT nome,cognome,email,inquadramento,password,ntel,tipo_utente FROM utente ;";
		DBmanager.openConnection();
		ResultSet rs = DBmanager.executeQuery(query);
	
		 while(rs.next()){
	         //Retrieve by column name
	         int id  = rs.getInt("id");
	         int age = rs.getInt("age");
	         String first = rs.getString("first");
	         String last = rs.getString("last");
	}
		return query;
	}
	*/
}
