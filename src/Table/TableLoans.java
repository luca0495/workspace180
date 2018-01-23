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

public class TableLoans extends JPanel implements TableModelListener,Serializable{
	
	private static final long serialVersionUID = 1L;
	private static JTable table;
	private JFrame frame;
	private TableModelLoans tm;
	private int deleteRow;
	private int selectedR;
	private int selectedC;	
	private int 	selectedRow;
	private Client	me;
	
	private String oldValue;
	//private static DefaultTableModel dm;
	
	
	
    public TableLoans(JFrame frame,Client me)  
    {
        super(new GridLayout(1,0));
    	this.me=me;
        this.frame = frame;
        tm = new TableModelLoans();
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
        			for(int i = 0; i<8; i++)
        			{
        				System.out.println("4");
        				rowData.add((String) tm.getValueAt(deleteRow, i));
        			}        			
        			try 
        			{ 
        				System.out.println("5");      				
//TODO CANCELLA PRESTITO PASSA METODO AL CLIENT        	////////////////////////////////////////////////////////////////////////////////			
        				
        				//old OK
						//TableUpdateBooks.deleteRow(rowData, getTable());
        				

//TODO CANCELLA PRESTITO PASSA METODO AL CLIENT        	/////////////////////////////////////////////////////////////////////////////////			
						
        				//TableUpdateBooks.deleteRow(rowData, table);
						
        				//tm.fireTableDataChanged();
						//table.repaint();					
						
        				TableUpdateBooks.deleteRow(rowData, getTable(), me);

						//tm.fireTableDataChanged();
						//getTable().repaint();
					
        			
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
                String idutente				= (String) source.getValueAt(selectedRow, 1);
                String dataStart			= (String) source.getValueAt(selectedRow, 2);
                String dataStop				= (String) source.getValueAt(selectedRow, 3);
                
                int 	idbook		=Integer.valueOf(	idlibro);  
                int 	iduser		=Integer.valueOf(	idutente);  
                Date 	iddataStart =Date.valueOf(		dataStart);                  
                Date 	iddataStop  =Date.valueOf(		dataStop);            
                
                
                //setta su client idbook selezionato
                me.setSelectedIdBook(idbook);
                me.setSelectedIdUser(iduser);
                me.setSelectedIdDataStart(iddataStart);
                me.setSelectedIdDataStop(iddataStop);
                
                PopUp.infoBox(frame, new String (	"\nottenuto idbook  : "+me.getSelectedIdBook()+
                									"\nottenuto iduser  : "+me.getSelectedIdUser()+
                									"\nottenuto idD init: "+me.getSelectedIdDataStart()+
													"\nottenuto idD stop: "+me.getSelectedIdDataStop())
                			);
				
		
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
    		if(TableUpdateLoans.isNotOk())
    		{
    			System.out.println("15");
    			tm.setValueAt(oldValue, selectedR, selectedC);
    			TableUpdateLoans.setNotOk(false);
    		}
        }

        @Override
        public void editingStopped(ChangeEvent e) 
        {            
    		if(TableUpdateLoans.isNotOk())
    		{    
    			System.out.println("16");
    			tm.setValueAt(oldValue, selectedR, selectedC);
    			TableUpdateLoans.setNotOk(false);
    		}
        }
    };
        
    public static void PopulateDataLoans(String x,Client me) throws SQLException, InterruptedException {
		// Clear table   
		getTable().setModel(new DefaultTableModel());	
		// Model for Table		
		DefaultTableModel model = (DefaultTableModel)getTable().getModel();		
		model.addColumn("Codice");
		model.addColumn("Id");
		model.addColumn("Data_Inizio");
		model.addColumn("Data_Fine");
		String query=null;
		
		System.out.println("Valore ritornato:" + x);		
//IN TEST
		switch (me.getCliType()) {

		case Librarian:
			
			query = "SELECT * FROM prestiti" + " WHERE (codice LIKE '%"+x+"%'"
                    + " OR id LIKE '%"+x+"%'"
                    + " OR data_inizio LIKE '%"+x+"%'"
                    + " OR data_fine LIKE '%"+x+"%')"
                    + " ORDER BY id ASC";
			if(x=="" || x==null){			
			query = "SELECT * FROM prestiti ";
			}			
			me.setSql(query);
			me.getCmdLIST().put(Commands.BookExecuteQuery);
			break;
			
		case Reader:
			//modifica idut==id DA TESTARE CON DATI...
			query = "SELECT * FROM libro" + " WHERE ("
					+ "		nome_autore LIKE '%"+x+"%'"
                    + " OR 	cognome_autore LIKE '%"+x+"%'"
                    + " OR 	categoria LIKE '%"+x+"%'"
                    + " OR 	titolo LIKE '%"+x+"%' AND id="+ me.getIdut() +")" 
                    + " ORDER BY codice ASC";			
			
			if(x=="" || x==null){			
				query = "SELECT * FROM libro ";
			}
			me.setSql(query);
			me.getCmdLIST().put(Commands.BookExecuteQuery);
			break;
			
		case Guest:
			
			query = "SELECT * FROM libro" + " WHERE (nome_autore LIKE '%"+x+"%'"
                    + " OR cognome_autore LIKE '%"+x+"%'"
                    + " OR categoria LIKE '%"+x+"%'"
                    + " OR titolo LIKE '%"+x+"%' AND id="+ me.getIdut() +")" 
                    + " ORDER BY codice ASC";			
			
			if(x=="" || x==null){			
				query = "SELECT * FROM libro ";
			}
			me.setSql(query);
			me.getCmdLIST().put(Commands.BookExecuteQuery);
			break;
			
		case Default:
			
			query = "SELECT * FROM libro" + " WHERE (nome_autore LIKE '%"+x+"%'"
                    + " OR cognome_autore LIKE '%"+x+"%'"
                    + " OR categoria LIKE '%"+x+"%'"
                    + " OR titolo LIKE '%"+x+"%' AND id="+ me.getIdut() +")" 
                    + " ORDER BY codice ASC";			
			
			if(x=="" || x==null){			
				query = "SELECT * FROM libro ";
			}
			me.setSql(query);
			me.getCmdLIST().put(Commands.BookExecuteQuery);
			break;			
			
			
		default:
			break;
		}
		
		
		
		
		
		
		

		
		// TEST OK 27.12.2017
		///////////////////////////////////// da rivedere		
//		me.setSql(query);
//		me.getCmdLIST().put(Commands.BookExecuteQuery);
		
		// in test
		
		/* OLD
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
		
		*/
		
		}
  
    
	@Override
	public void tableChanged(TableModelEvent e) 
	{
        int row = e.getFirstRow();
        int column = e.getColumn();
        TableModelBooks model = (TableModelBooks)e.getSource();
        String columnName = model.getColumnName(column);
        Object data = model.getValueAt(row, column);
        if(!TableUpdateLoans.isNotOk())
		{
        	TableUpdateLoans.setColumn(column);
        	TableUpdateLoans.setInput((String)model.getValueAt(row, column));
        	TableUpdateLoans.check(frame, getTable());
		} 
	}
	
	public void update()
	{
		tm.fireTableDataChanged();
		getTable().repaint();
	}


	public static JTable getTable() {
		return table;
	}


	public static void setTable(JTable table) {
		TableLoans.table = table;
	}
}