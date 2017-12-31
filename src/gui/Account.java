package gui;


import java.awt.CardLayout;
import java.awt.Component;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Core.Clients;
import connections.Client;
import database.DBmanager;
import database.LoadUser;
import database.MQ_Read;

import javax.swing.JLabel;
import javax.swing.JButton;

public class Account extends JFrame{

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
    static String idUser = null;
    static int userRow = 0;
    static JLabel lblSetNome;
	static JLabel lblSetCognome;
	static JLabel lblSetEmail;
	static JLabel lblSetPass;
	static JLabel lblSetInq;
	static JLabel lblSetTipoUte;
	static JLabel lblSetTel;
	static JLabel lblSetNumPrenPend;
	static String[][] data = null;
	private String s3 =null;
	JLabel lb2;
	JLabel lb3;
	JLabel lb4;
	JLabel lb5;
	public Account(Component c)
	{
		Account(c);
		try {
		ReadUser1();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
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
		
	    lblSetNome = new JLabel();
	    //lblSetNome.setText(l.getName(p1));
		lblSetNome.setBounds(106, 19, 186, 20);
		panelAccount.add(lblSetNome);
		
		JLabel lblCognome = new JLabel("Cognome: ");
		lblCognome.setBounds(10, 64, 59, 14);
		panelAccount.add(lblCognome);
		
		lblSetCognome = new JLabel();
		lblSetCognome.setBounds(106, 64, 186, 20);
		panelAccount.add(lblSetCognome);
		
		JLabel lblEmail = new JLabel("Email: ");
		lblEmail.setBounds(10, 115, 46, 14);
		panelAccount.add(lblEmail);
		
		lblSetEmail = new JLabel();
		lblSetEmail.setBounds(106, 112, 186, 20);
		panelAccount.add(lblSetEmail);
		
		JLabel lblPass = new JLabel("Password:");
		lblPass.setBounds(10, 154, 59, 14);
		panelAccount.add(lblPass);
		
		lblSetPass = new JLabel();
		lblSetPass.setBounds(106, 154, 186, 20);
		panelAccount.add(lblSetPass);
		
		JLabel lblInq = new JLabel("Inquadramento: ");
		lblInq.setBounds(10, 195, 86, 14);
		panelAccount.add(lblInq);
		
		lblSetInq = new JLabel();
		lblSetInq.setBounds(106, 189, 186, 20);
		panelAccount.add(lblSetInq);
		
		JLabel lblTipoUte = new JLabel("Tipo Utente:");
		lblTipoUte.setBounds(10, 240, 74, 14);
		panelAccount.add(lblTipoUte);
		
		lblSetTipoUte = new JLabel();
		lblSetTipoUte.setBounds(106, 234, 186, 20);
		panelAccount.add(lblSetTipoUte);
		
		JLabel lblTel = new JLabel("Telefono:");
		lblTel.setBounds(10, 283, 59, 14);
		panelAccount.add(lblTel);
		
		lblSetTel = new JLabel();
		lblSetTel.setBounds(106, 283, 186, 14);
		panelAccount.add(lblSetTel);
		
		JLabel lblNumPrenPend = new JLabel("Numero Di Prenotazioni:");
		lblNumPrenPend.setBounds(10, 328, 132, 14);
		panelAccount.add(lblNumPrenPend);
		
		lblSetNumPrenPend = new JLabel();
		// mettere anche questa nel metodo
		lblSetNumPrenPend.setBounds(152, 328, 140, 14);
		panelAccount.add(lblSetNumPrenPend);
		
		JButton btnDelete = new JButton("Cancella Profilo");
		btnDelete.setBounds(192, 381, 193, 54);
		panelAccount.add(btnDelete);
		
		JButton btnModify = new JButton("Modifica Profilo");
		btnModify.setBounds(428, 381, 186, 54);
		panelAccount.add(btnModify);
		
		
	}
	
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
	

	public static String[] loadData()
	{
		String[] output = new String[10];

    	for(int i = 0; i<cols; i++)
    	{
    		output[i] = data[userRow][i];
    	}

		return output;
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
