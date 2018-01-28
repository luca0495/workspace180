package Table;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.sql.Date;
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
import Core.Commands;
import connections.Client;
import database.DBmanager;

public class TableBooking extends JPanel implements TableModelListener,Serializable{
	
	private static final long serialVersionUID = 1L;
	private static JTable table;
	private JFrame frame;
	private TableModelBooking tm;
	private int deleteRow;
	private int selectedR;
	private int selectedC;	
	private int 	selectedRow;
	private Client	me;
	
	private String oldValue;
	//private static DefaultTableModel dm;
	
	
	
    public TableBooking(JFrame frame,Client me) throws InterruptedException  
    {
        super(new GridLayout(1,0));
    	this.me=me;
        this.frame = frame;
        tm = new TableModelBooking(me);
        setTable(new JTable(tm));
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("Delete");
        //JMenuItem prenotaItem = new JMenuItem("Prenota");
        
        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	System.out.println("1");
                if(PopUp.confirmBox(frame))
                {
                	System.out.println("2");
                	List<String> rowData = new ArrayList<String>();
                	System.out.println("3");
        			for(int i = 0; i<4; i++)
        			{
        				System.out.println("4");
        				rowData.add((String) tm.getValueAt(deleteRow, i));
        			}        			
        			try 
        			{ 
        				System.out.println("5");      							
        				TableUpdateBooking.deleteRow(rowData, getTable(), me);
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
        
		getTable().addMouseListener(new MouseAdapter() {
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
	                }//fine trigger    
				}//fine click rx

				
				 
                JTable source 	= (JTable)e.getSource();
                selectedRow 	= source.rowAtPoint(e.getPoint());
                
                //dati TUPLA in selezione salvati su Client
                String idlibro				= (String) source.getValueAt(selectedRow, 0);
                //String idutente				= (String) source.getValueAt(selectedRow, 1);
                int 	idbook				=Integer.valueOf(idlibro);  
                //int 	iduser				=Integer.valueOf(idutente);  
                
                //setta su client idbook selezionato
                me.setSelectedIdBook(idbook);
               // me.setSelectedIdUser(iduser);
                
                PopUp.infoBox(frame, new String (	"\nottenuto idbook  : "+me.getSelectedIdBook()+
                									"\nottenuto iduser  : "+me.getSelectedIdUser() 
                			));
			
		
            }
		});
		 System.out.println("12");
	    getTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ListSelectionModel listSelectionModel = getTable().getSelectionModel();
	    listSelectionModel.addListSelectionListener(new SharedListSelectionHandler(tm));	    
		getTable().getModel().addTableModelListener(this);
		getTable().setPreferredScrollableViewportSize(new Dimension(500, 70));
		getTable().setFillsViewportHeight(true);
		getTable().getDefaultEditor(String.class).addCellEditorListener(ChangeNotification);
	    getTable().setCellSelectionEnabled(true);

	    listSelectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) 
			{
				if(!(getTable().getSelectedRow() == -1) && !(getTable().getSelectedColumn() == -1))
				{
					 System.out.println("13");
					selectedR = getTable().getSelectedRow(); // riga
					selectedC = getTable().getSelectedColumn(); // colonna	
					oldValue = (String) getTable().getValueAt(selectedR, selectedC);
				}
			}
	    });
	    System.out.println("14");
		JScrollPane scrollPane = new JScrollPane(getTable());
		add(scrollPane);
    }
    
    CellEditorListener ChangeNotification = new CellEditorListener() {
    	
    	@Override
        public void editingCanceled(ChangeEvent e) 
    	{            
    		if(TableUpdateBooking.isNotOk())
    		{
    			System.out.println("15");
    			tm.setValueAt(oldValue, selectedR, selectedC);
    			TableUpdateBooking.setNotOk(false);
    		}
        }

        @Override
        public void editingStopped(ChangeEvent e) 
        {            
    		if(TableUpdateBooking.isNotOk())
    		{    
    			System.out.println("16");
    			tm.setValueAt(oldValue, selectedR, selectedC);
    			TableUpdateBooking.setNotOk(false);
    		}
        }
    };
        
        
	@Override
	public void tableChanged(TableModelEvent e) 
	{
        int row = e.getFirstRow();
        int column = e.getColumn();
        TableModelBooking model = (TableModelBooking)e.getSource();
        String columnName = model.getColumnName(column);
        Object data = model.getValueAt(row, column);
        if(!TableUpdateBooking.isNotOk())
		{
        	TableUpdateBooking.setColumn(column);
        	TableUpdateBooking.setInput((String)model.getValueAt(row, column));
		} 
	}
	/*
	public void update()
	{
		tm.fireTableDataChanged();
		getTable().repaint();
	}
*/

	public static JTable getTable() {
		return table;
	}


	public static void setTable(JTable table) {
		TableBooking.table = table;
	}
}