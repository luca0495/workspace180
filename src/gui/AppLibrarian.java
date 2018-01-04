package gui;


import java.awt.CardLayout;
import java.awt.Component;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;


public class AppLibrarian extends JFrame  {

	private JFrame frmSchoolib;	
	private AppReader a;
	private JTextField textField;

	
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
	
		
		
	}
}
