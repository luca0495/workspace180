package gui;


import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JMenuBar;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import Table.TableBooks;
import connections.Client;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridLayout;


public class AppLibrarian extends JFrame  {

	private JFrame frmSchoolib;	
	private AppReader a;
	private JTextField textField;
	private JTextField txtEmailUser;
	private Client  me ;
	
	private JFrame frame;
    private JTable tableBooks;
	
	public AppLibrarian(Component c)
	{
		AppLibrarian(c);
	}


	public void AppLibrarian(Component c) {

		frmSchoolib = new JFrame();
		frmSchoolib.setTitle("Schoolib");
		frmSchoolib.setBounds(100, 100, 893, 545);
		frmSchoolib.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmSchoolib.setLocationRelativeTo(c);
		frmSchoolib.setVisible(true);
		frmSchoolib.getContentPane().setLayout(new CardLayout(0, 0));
		
		JPanel panelSelection1 = new JPanel();
		frmSchoolib.getContentPane().add(panelSelection1, "name_353237010061838");
		panelSelection1.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 877, 21);
		panelSelection1.add(menuBar);
		
		JButton btnAssistanceRead = new JButton("AssistereReader");
		menuBar.add(btnAssistanceRead);
		
		JButton btnDelete = new JButton("Cancella Profilo");
		menuBar.add(btnDelete);
		
		textField = new JTextField();
		textField.setColumns(10);
		menuBar.add(textField);
		
		JButton btnModify = new JButton("Modifica Profilo");
		menuBar.add(btnModify);
		
		JButton btnSaveBib = new JButton("Registrazione Bibliotecario");
		btnSaveBib.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		menuBar.add(btnSaveBib);
		
		JLabel lblAdmin = new JLabel("Admin");
		lblAdmin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAdmin.setBounds(333, 25, 107, 32);
		panelSelection1.add(lblAdmin);
		
		JLabel lblUserBook = new JLabel("Mostrare i prestiti in carico ad un utente");
		lblUserBook.setFont(new Font("Traditional Arabic", Font.BOLD, 18));
		lblUserBook.setBounds(205, 101, 413, 21);
		panelSelection1.add(lblUserBook);
		
		JLabel lblVisualizeBook = new JLabel("Visualizzare tutti i libri in prestito");
		lblVisualizeBook.setFont(new Font("Traditional Arabic", Font.BOLD, 18));
		lblVisualizeBook.setBounds(205, 172, 495, 21);
		panelSelection1.add(lblVisualizeBook);
		
		JLabel lblVisualizeBookTwo = new JLabel("Visualizzare tutti i libri prenotati");
		lblVisualizeBookTwo.setFont(new Font("Traditional Arabic", Font.BOLD, 18));
		lblVisualizeBookTwo.setBounds(205, 249, 495, 21);
		panelSelection1.add(lblVisualizeBookTwo);
		
		JLabel lblClassBook = new JLabel("Mostrare la classifica dei libri pi\u00F9 letti, assoluta, per categoria e per inquadramento");
		lblClassBook.setFont(new Font("Traditional Arabic", Font.BOLD, 18));
		lblClassBook.setBounds(205, 331, 672, 21);
		panelSelection1.add(lblClassBook);
		
		// PanelSearchUser
		
		JPanel panelSearchUser = new JPanel();
		frmSchoolib.getContentPane().add(panelSearchUser, "name_3985911164799");
		panelSearchUser.setLayout(null);
		
		JPanel panelSearchUser1 = new JPanel();
		panelSearchUser1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelSearchUser1.setBackground(Color.WHITE);
		panelSearchUser1.setBounds(10, 75, 857, 420);
		panelSearchUser.add(panelSearchUser1);
		panelSearchUser1.setLayout(null);
		
		TableBooks panelSearchUser2 = new TableBooks(frame,me);
		panelSearchUser2.setBounds(0, 0, 857, 420);
		panelSearchUser1.add(panelSearchUser2);
		panelSearchUser2.setLayout(new GridLayout(1, 0, 0, 0));
	
		JLabel lblEmailUser = new JLabel("Email User");
		lblEmailUser.setBounds(57, 23, 106, 36);
		panelSearchUser.add(lblEmailUser);
		
		txtEmailUser = new JTextField();
		txtEmailUser.setBounds(186, 31, 309, 20);
		panelSearchUser.add(txtEmailUser);
		txtEmailUser.setColumns(10);
		
		JButton btnSearchUser = new JButton("Cerca");
		btnSearchUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			String s=txtEmailUser.getText();
		    if(s.length()!=0){			 
//TODO PASSA A CLIENT da TableBooks		 				 
//me.getCmdLIST().put(Commands.tableBookPopulate);
			try {	 	 
				TableBooks.PopulateData(s,me);
			 } catch (SQLException e1) {
				e1.printStackTrace();	
			} catch (InterruptedException e1) {				
				e1.printStackTrace();
			}			
		    }else{
//TODO PASSA A CLIENT da TableBooks	
//me.getCmdLIST().put(Commands.tableBookPopulate);	
		      try {
				 TableBooks.PopulateData("",me);
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (InterruptedException e1) {				
				e1.printStackTrace();
			}    
		 }
	   }
	});
		btnSearchUser.setBounds(569, 23, 121, 30);
		panelSearchUser.add(btnSearchUser);
		
		JButton btnBack = new JButton("Cancella");
		btnBack.setBounds(731, 23, 114, 30);
		panelSearchUser.add(btnBack);
		
		
		
		
	}
}
