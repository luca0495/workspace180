package Table;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import Check.PopUp;
import Core.Commands;
import connections.Client;
import gui.ResearchBooks;
import java.awt.event.FocusAdapter;



public class TableBooks extends JPanel implements TableModelListener,Serializable{
	
	private static final long serialVersionUID = 1L;
	private static 	JTable table;	
	private ResearchBooks frame;
	private 		TableModelBooks tm;
	private int 	deleteRow;
	private int 	selectedR;
	private int 	selectedC;
	private int 	selectedRow;
	private Client	me;
	
	private String 	oldValue;
    public TableBooks(ResearchBooks frame,Client me) throws InterruptedException  
    {

        super(new GridLayout(1,0));
    	this.setMe(me);
        this.setFrame(frame);

        
        setTm(new TableModelBooks(me));
        
        setTable(new JTable(getTm()));
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("Delete");
        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
                if(PopUp.confirmBox(frame))
                {
                
                	List<String> rowData = new ArrayList<String>();
                	
        			for(int i = 0; i<7; i++)
        			{
        				
        				rowData.add((String) getTm().getValueAt(deleteRow, i));
        			}        			
        			try 
        			{ 
        							
        				TableUpdateBooks.deleteRow(rowData, getTable(), me);
        			
        			} 

        			catch (SQLException e1)
        			{
						e1.printStackTrace();
					}
               }
            }
        });
        popupMenu.add(deleteItem);
        
        
		getTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) 
			{
				if(SwingUtilities.isRightMouseButton(e)){ }
			}
            public void mouseReleased(MouseEvent e)
            {
				if(SwingUtilities.isRightMouseButton(e))
				{	
	                if (e.isPopupTrigger())
	                {
	                	
	                    JTable source = (JTable)e.getSource();
	                    deleteRow = source.rowAtPoint(e.getPoint());
	                    int column = source.columnAtPoint(e.getPoint());

	                    if (!source.isRowSelected(deleteRow))
	                    {
	                    
	                        source.changeSelection(deleteRow, column, false, false);
	                    }
	                   
	                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
	                }  
				}
                   
                    JTable source 	= (JTable)e.getSource();
                    selectedRow 	= source.rowAtPoint(e.getPoint());
                    String idb		= (String) source.getValueAt(selectedRow, 0);
                    int idbook=Integer.valueOf(idb);                    
                   
                    me.setSelectedIdBook(idbook);
                    getFrame().getTxtInsertCDBook().setText(String.valueOf(idbook));
            }
		});
		
	    getTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ListSelectionModel listSelectionModel = getTable().getSelectionModel();
	    listSelectionModel.addListSelectionListener(new SharedListSelectionHandler(getTm()));	    
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
					
					selectedR = getTable().getSelectedRow(); // riga
					selectedC = getTable().getSelectedColumn(); // colonna	
					oldValue = (String) getTable().getValueAt(selectedR, selectedC);
				}
			}
	    });
	    
		JScrollPane scrollPane = new JScrollPane(getTable());
		scrollPane.addFocusListener(new FocusAdapter() {

		});
		add(scrollPane);
    }
    
    CellEditorListener ChangeNotification = new CellEditorListener() {
    	
    	@Override
        public void editingCanceled(ChangeEvent e) 
    	{            
    		if(TableUpdateBooks.isNotOk())
    		{
    			
    			getTm().setValueAt(oldValue, selectedR, selectedC);
    			TableUpdateBooks.setNotOk(false);
    		}
        }

        @Override
        public void editingStopped(ChangeEvent e) 
        {            
    		if(TableUpdateBooks.isNotOk())
    		{    
    			
    			getTm().setValueAt(oldValue, selectedR, selectedC);
    			TableUpdateBooks.setNotOk(false);
    		}
        }
    };
        
    /**
     * @param x
     * @param me
     * @throws SQLException
     * @throws InterruptedException
     */
    public static void PopulateData(String x,Client me) throws SQLException, InterruptedException {
		// Clear table   
		getTable().setModel(new DefaultTableModel());	
		// Model for Table		
		DefaultTableModel model = (DefaultTableModel)getTable().getModel();		
		model.addColumn("Codice");
		model.addColumn("Nome_Autore");
		model.addColumn("Cognome_Autore");
		model.addColumn("Categoria");
		model.addColumn("Titolo");
		model.addColumn("Disponibilità");
		model.addColumn("Prenotazioni_in_coda");
		
		String query=null;
		
		System.out.println("Client me " + me.toString());		
		System.out.println("Client me type " + me.getCliType().toString());		
		System.out.println("Valore ritornato:" + x);		

//IN TEST		
		
		switch (me.getCliType()) {

		case Librarian:			
			query = "SELECT * FROM libro" + " WHERE ("
					+ "    nome_autore LIKE '%"+x+"%'"
                    + " OR cognome_autore LIKE '%"+x+"%'"
                    + " OR categoria LIKE '%"+x+"%'"
                    + " OR titolo LIKE '%"+x+"%')"
                    //+ " ORDER BY codice ASC"
                    ;			
			
			if(x=="" || x==null){			
				query = "SELECT * FROM libro ";
			}
			System.out.println("q:"+query);
			me.setSql(query);
			
			System.err.println("srv query in esecuzione "+me.getSql());
			
			me.getCmdLIST().put(Commands.BookExecuteQuery);					
			break;
			
		case Reader:
			query = "SELECT * FROM libro" + " WHERE (nome_autore LIKE '%"+x+"%'"
                    + " OR cognome_autore LIKE '%"+x+"%'"
                    + " OR categoria LIKE '%"+x+"%'"
                    + " OR titolo LIKE '%"+x+"%')" 
                    //+ " ORDER BY codice ASC"
                    ;			
			
			if(x=="" || x==null){			
				query = "SELECT * FROM libro ";
			}
			System.out.println("q:"+query);
			me.setSql(query);
			me.getCmdLIST().put(Commands.BookExecuteQuery);
			break;
			
		case Guest:			
			query = "SELECT * FROM libro" + " WHERE (nome_autore LIKE '%"+x+"%'"
                    + " OR cognome_autore LIKE '%"+x+"%'"
                    + " OR categoria LIKE '%"+x+"%'"
                    + " OR titolo LIKE '%"+x+"%')" 
                    //+ " ORDER BY codice ASC"
                    ;			
			
			if(x=="" || x==null){			
				query = "SELECT * FROM libro ";
			}
			System.out.println("q:"+query);
			me.setSql(query);
			me.getCmdLIST().put(Commands.BookExecuteQuery);
			break;
			
		case Default:		
			query = "SELECT * FROM libro" + " WHERE (nome_autore LIKE '%"+x+"%'"
                    + " OR cognome_autore LIKE '%"+x+"%'"
                    + " OR categoria LIKE '%"+x+"%'"
                    + " OR titolo LIKE '%"+x+"%')" 
                    //+ " ORDER BY codice ASC"
                    ;			
			
			if(x=="" || x==null){			
				query = "SELECT * FROM libro ";
			}
			System.out.println("q:"+query);
			me.setSql(query);
			me.getCmdLIST().put(Commands.BookExecuteQuery);
			break;			
		default:
			break;
		}
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
        	TableUpdateBooks.check(getFrame(), getTable());
		} 
	}

	public static JTable getTable() {
		return table;
	}


	public static void setTable(JTable table) {
		TableBooks.table = table;
	}


	public TableModelBooks getTm() {
		return tm;
	}


	public void setTm(TableModelBooks tm) {
		this.tm = tm;
	}


	public ResearchBooks getFrame() {
		return frame;
	}


	public void setFrame(ResearchBooks frame) {
		this.frame = frame;
	}


	public Client getMe() {
		return me;
	}


	public void setMe(Client me) {
		this.me = me;
	}
	

}
