package gui;
import connections.*;
import java.awt.Color;
import javax.swing.DropMode;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SystemServerSkeleton extends SL_JFrame {

	private static final long serialVersionUID = 1L;

	private ServerReal 	me ;
		
	// GRAFICA	
	private JFrame frame;
	private JTextArea text;
	
	public SystemServerSkeleton(ServerReal mme) throws Exception {
		setMe(mme);
		initialize();
	}
	
	private void initialize() {
		setFrame(new JFrame());
		getFrame().setBounds(100, 100, 510, 583);
		getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getFrame().getContentPane().setLayout(null);
		
		JTextArea txtrCiao = new JTextArea();
		txtrCiao.setText("Server gui test ...");
		txtrCiao.setForeground(Color.WHITE);
		text=txtrCiao;
		txtrCiao.setBounds(27, 104, 442, 206);
		txtrCiao.setFocusTraversalPolicyProvider(true);
		txtrCiao.setFocusCycleRoot(true);
		txtrCiao.setDropMode(DropMode.INSERT);
		txtrCiao.setDragEnabled(true);
		txtrCiao.setDoubleBuffered(true);
		txtrCiao.setColumns(2);
		txtrCiao.setBackground(Color.BLUE);
		txtrCiao.setAutoscrolls(true);
		getFrame().getContentPane().add(txtrCiao);
		
		JLabel lblNewLabel = new JLabel("Finestra Terminale");
		lblNewLabel.setForeground(Color.BLUE);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(27, 83, 442, 14);
		getFrame().getContentPane().add(lblNewLabel);
		
		JLabel lblSchoollibFinestraControllo = new JLabel("Skeleton");
		lblSchoollibFinestraControllo.setForeground(Color.BLUE);
		lblSchoollibFinestraControllo.setHorizontalAlignment(SwingConstants.CENTER);
		lblSchoollibFinestraControllo.setFont(new Font("Tahoma", Font.PLAIN, 50));
		lblSchoollibFinestraControllo.setBounds(10, 11, 459, 51);
		getFrame().getContentPane().add(lblSchoollibFinestraControllo);	
		
		JButton btnNewButton = new JButton("metodo 1");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
		
			
			}
		});
		btnNewButton.setBounds(27, 334, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("metodo 2");
		btnNewButton_1.setBounds(27, 368, 89, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("metodo 3");
		btnNewButton_2.setBounds(27, 402, 89, 23);
		frame.getContentPane().add(btnNewButton_2);
		
	}

	
	//-----------------------------------------------------------------------
			
	//-----------------------------------------------------------------------


	public JFrame getFrame() {
		return frame;
	}
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	
	public void addMsg(String msg){
		
		text.setText(msg);
		
	}

	public ServerReal getMe() {
		return me;
	}

	public void setMe(ServerReal me) {
		this.me = me;
	}
}
