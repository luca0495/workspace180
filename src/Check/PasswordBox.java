package Check;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPasswordField;

import gui.Account;

import javax.swing.JLabel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PasswordBox extends JDialog {
    
	private static final long serialVersionUID = 1L;
	private static JPasswordField passwordField;
	public final static String Action = "OK";

	
	public PasswordBox() 
	{
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Password");
		getContentPane().setLayout(null);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(24, 41, 221, 20);
		getContentPane().add(passwordField);
		passwordField.setActionCommand(Action);
		
		JLabel lblPassword = new JLabel("Inserisci qui la Password");
		lblPassword.setBounds(53, 11, 166, 14);
		getContentPane().add(lblPassword);
		
		JButton btnOk = new JButton("Ok");
		btnOk.setActionCommand(Action);
		btnOk.setBounds(46, 79, 89, 23);
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				char[] p = passwordField.getPassword();
				String 	p1 	= String.copyValueOf(p);
				
				if(Check.checkPassExist(p1)) // legge password per id utente
				{
					Account.ModPass= true;
					dispose();
					
				}
				else
				{
					PopUp.errorBox(getContentPane(),"La password non è corretta");
				}
		    

			}
		});
		getContentPane().add(btnOk);
        
        this.getRootPane().setDefaultButton(btnOk);
		
		JButton btnAnnulla = new JButton("Annulla");
		btnAnnulla.setBounds(151, 79, 89, 23);
		btnAnnulla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose();
			}
		});
		getContentPane().add(btnAnnulla);

		
		
	}
}

