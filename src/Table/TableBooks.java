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
import java.util.ArrayList;
import java.util.List;

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

import Check.PopUp;
import database.DBmanager;

public class TableBooks extends JPanel implements TableModelListener {
	
	private static final long serialVersionUID = 1L;
	private static JTable table;
	private JFrame frame;
	private TableModelBooks tm;
	private int deleteRow;
	private int selectedR;
	private int selectedC;	
	private String oldValue;
	//private static DefaultTableModel dm;
	
	
	
    public TableBooks(JFrame frame)  
    {
        super(new GridLayout(1,0));
        this.frame = frame;
        tm = new TableModelBooks();
        table = new JTable(tm);
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("Delete");
        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	System.out.println("1");
                if(PopUp.confirmBox(frame))
                {
                	System.out.println("2");
                	List<String> rowData = new ArrayList<String>();
                	System.out.println("3");
        			for(int i = 0; i<5; i++)
        			{
        				System.out.println("4");
        				rowData.add((String) tm.getValueAt(deleteRow, i));
        			}
        			
        			try 
        			{ 
        				System.out.println("5");
						TableUpdateBooks.deleteRow(rowData, table);
						tm.fireTableDataChanged();
						table.repaint();
					} 
        			catch (SQLException e1)
        			{
						e1.printStackTrace();
					}
               }
            }
        });
        System.out.println("6");
        popupMenu.add(deleteItem);
        
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) 
			{
				System.out.println("7");
				if(SwingUtilities.isRightMouseButton(e)){ }
			}
            public void mouseReleased(MouseEvent e)
            {
				if(SwingUtilities.isRightMouseButton(e))
				{	
					System.out.println("8");
	                if (e.isPopupTrigger())
	                {
	                	System.out.println("9");
	                    JTable source = (JTable)e.getSource();
	                    deleteRow = source.rowAtPoint(e.getPoint());
	                    int column = source.columnAtPoint(e.getPoint());

	                    if (!source.isRowSelected(deleteRow))
	                    {
	                    	System.out.println("10");
	                        source.changeSelection(deleteRow, column, false, false);
	                    }
	                    System.out.println("11");
	                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
	                }
				}

            }
		});
		 System.out.println("12");
	    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ListSelectionModel listSelectionModel = table.getSelectionModel();
	    listSelectionModel.addListSelectionListener(new SharedListSelectionHandler(tm));	    
		table.getModel().addTableModelListener(this);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		table.getDefaultEditor(String.class).addCellEditorListener(ChangeNotification);
	    table.setCellSelectionEnabled(true);

	    listSelectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) 
			{
				if(!(table.getSelectedRow() == -1) && !(table.getSelectedColumn() == -1))
				{
					 System.out.println("13");
					selectedR = table.getSelectedRow(); // riga
					selectedC = table.getSelectedColumn(); // colonna	
					oldValue = (String) table.getValueAt(selectedR, selectedC);
				}
			}
	    });
	    System.out.println("14");
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane);
    }
    
    CellEditorListener ChangeNotification = new CellEditorListener() {
    	
    	@Override
        public void editingCanceled(ChangeEvent e) 
    	{            
    		if(TableUpdateBooks.isNotOk())
    		{
    			System.out.println("15");
    			tm.setValueAt(oldValue, selectedR, selectedC);
    			TableUpdateBooks.setNotOk(false);
    		}
        }

        @Override
        public void editingStopped(ChangeEvent e) 
        {            
    		if(TableUpdateBooks.isNotOk())
    		{    
    			System.out.println("16");
    			tm.setValueAt(oldValue, selectedR, selectedC);
    			TableUpdateBooks.setNotOk(false);
    		}
        }
    };
        
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
		model.addColumn("Num_Pren");
		
		System.out.println("Valore ritornato:" + x);
		String query = "SELECT * FROM libro" + " WHERE (nome_autore LIKE '%"+x+"%'"
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
		model.setValueAt(rs.getString("num_prenotazioni"), row, 5);
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
        if(!TableUpdateBooks.isNotOk())
		{
        	TableUpdateBooks.setColumn(column);
        	TableUpdateBooks.setInput((String)model.getValueAt(row, column));
        	TableUpdateBooks.check(frame, table);
		} 
	}
	
	
	public void update()
	{
		tm.fireTableDataChanged();
		table.repaint();
	}
	

}
