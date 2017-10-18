package Table;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import database.DBmanager;

public class TableBooks extends JPanel implements TableModelListener {
	
	private static final long serialVersionUID = 1L;
	private static JTable table;
	private JFrame frame;
	private TableModelBooks tm;
	//private static DefaultTableModel dm;
	
	
	
    public TableBooks(JFrame frame)  
    {
        super(new GridLayout(1,0));
        this.frame = frame;
        JScrollPane scrollPane = new JScrollPane();
        add(scrollPane);
        tm = new TableModelBooks();
        table = new JTable(tm);
        table.setCellSelectionEnabled(true);
        scrollPane.setViewportView(table);
        
          
     }
  
    public static void PopulateData(String x) throws SQLException {
		// Clear table
   
		table.setModel(new DefaultTableModel());
	
		// Model for Table
		
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		
		model.addColumn("Codice");
		model.addColumn("Nome_Autore");
		model.addColumn("Cognome_Autore");
		model.addColumn("Categoria");
		model.addColumn("Titolo");
		
		System.out.println("Valore ritornato:" + x);
		String query = "SELECT * FROM libro" + " WHERE (codice LIKE '%"+x+"%'" 
				                             + " OR nome_autore LIKE '%"+x+"%'"
		                                     + " OR cognome_autore LIKE '%"+x+"%'"
		                                     + " OR categoria LIKE '%"+x+"%'"
		                                     + " OR titolo LIKE '%"+x+"%')";
		if(x=="" || x==null){
			
		query = "SELECT * FROM libro ";
		}
		//"ORDER BY CustomerID ASC";
		DBmanager.openConnection();
		ResultSet rs = DBmanager.executeQuery(query);
		System.out.println(rs);
		System.out.println(query);
		int row = 0;
		System.out.println("Test1");
		while((rs!=null) && (rs.next()))
		{          
	    System.out.println("Test2");
		model.addRow(new Object[0]);
		System.out.println("Test3");
		model.setValueAt(rs.getString("codice"), row, 0);
		System.out.println("Test4");
		model.setValueAt(rs.getString("nome_autore"), row, 1);
		System.out.println("Test5");
		model.setValueAt(rs.getString("cognome_autore"), row, 2);
		System.out.println("Test6");
		model.setValueAt(rs.getString("categoria"), row, 3);
		System.out.println("Test7");
		model.setValueAt(rs.getString("titolo"), row, 4);
		System.out.println("Test8");
		row++;
		System.out.println("Test9");
		}
		System.out.println("Test10");
		rs.close();
		DBmanager.closeConnection();
		}
    
	@Override
	public void tableChanged(TableModelEvent e) 
	{
        int row = e.getFirstRow();
        int column = e.getColumn();
        TableModelBooks model = (TableModelBooks)e.getSource();
        String columnName = model.getColumnName(column);
        Object data = model.getValueAt(row, column);
        TableUpdateBooks.setColumn(column);
        TableUpdateBooks.setInput((String)model.getValueAt(row, column));
       
	}
	
	public void update(DefaultTableModel model)
	{
		tm.fireTableDataChanged();
		table.repaint();
	}
	

}
