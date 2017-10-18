package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Books.Books;
import Table.TableBooks;
import Table.TableModelBooks;
import Table.TableUpdateBooks;
import database.MQ_Read;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ResearchBooks {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField2;
	private JPanel panel;
	private JPanel panelResearch;
	private JPanel panel_1;
    private TableBooks table;
    private TableUpdateBooks table1;
    private JTable table4;
    private JTable tableBooks;
	protected String ValToSearch;
	/**
	 * Create the application.
	 */
    
	public ResearchBooks(Component c) {
		initialize(c);
	}

	
	private void initialize(Component c) {
		frame = new JFrame();
		frame.setBounds(100, 100, 893, 545);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(c);
		frame.setVisible(true);
		
		JLabel lblResearch = new JLabel("Ricerca");
		lblResearch.setBounds(285, 11, 91, 14);
		frame.getContentPane().add(lblResearch);
		
		textField = new JTextField();
		textField.setBounds(337, 8, 315, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnResearch = new JButton("Ricerca");
		btnResearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			String s=textField.getText();
		    if(s.length()!=0){
			 try {
				TableBooks.PopulateData(s);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		    }else{
		      try {
				 TableBooks.PopulateData("");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		 }
	   }
   });
		btnResearch.setBounds(662, 7, 89, 23);
		frame.getContentPane().add(btnResearch);
		/*
		tableBooks = new JTable();
		tableBooks.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"codice", "nome_autore", "cognome_autore", "categoria", "titolo"
			}
		));
		tableBooks.setColumnSelectionAllowed(true);
		tableBooks.setCellSelectionEnabled(true);
		tableBooks.setBounds(10, 11, 837, 420);
		*/
		JPanel panelResearch = new JPanel();
		panelResearch.setBounds(10, 53, 857, 442);
		panelResearch.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelResearch.setBackground(Color.WHITE);
		frame.getContentPane().add(panelResearch);
		panelResearch.setLayout(null);
		
		TableBooks panelTableResearch = new TableBooks(frame);
		panelTableResearch.setBounds(10, 11, 837, 420);
		panelResearch.add(panelTableResearch);
		

	}
public void findBooks(String query){
	TableRowSorter<DefaultTableModel> tr=new TableRowSorter<DefaultTableModel>();
	tableBooks.setRowSorter(tr);
	
	tr.setRowFilter(RowFilter.regexFilter(query));
}
}
