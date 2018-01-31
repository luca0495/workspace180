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

import javax.swing.JFrame;
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
import gui.SL_JFrame;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class TableBooks extends JPanel implements TableModelListener,Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private ResearchBooks frame;
//	private 		JFrame frame;
	
	
	private static 	JTable table;

	private 		TableModelBooks tm;
	private int 	deleteRow;
	private int 	selectedR;
	private int 	selectedC;
	private int 	selectedRow;
	private Client	me;
	
	private String 	oldValue;
	//private static DefaultTableModel dm;
	
	
	
    public TableBooks(ResearchBooks frame,Client me) throws InterruptedException  
    {

        super(new GridLayout(1,0));
    	this.me=me;
        this.setFrame(frame);

        
        setTm(new TableModelBooks(me));
        
        setTable(new JTable(getTm()));
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
        			for(int i = 0; i<7; i++)
        			{
        				System.out.println("4");
        				rowData.add((String) getTm().getValueAt(deleteRow, i));
        			}        			
        			try 
        			{ 
        				System.out.println("5");      				
//TODO CANCELLA LIBRO PASSA METODO AL CLIENT        				
        				
        				//old OK
						//TableUpdateBooks.deleteRow(rowData, getTable());
        				

//TODO CANCELLA LIBRO PASSA METODO AL CLIENT        				
						
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
       //popupMenu.add(prenotaItem);
        
        
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
                    //colonna idbook == 0
                    String idb		= (String) source.getValueAt(selectedRow, 0);
                    int idbook=Integer.valueOf(idb);                    
                    //setta su client idbook selezionato
                   
                    me.setSelectedIdBook(idbook);
                    getFrame().getTxtInsertCDBook().setText(String.valueOf(idbook));
   
                    PopUp.infoBox(frame, new String ("ottenuto idbook: "+me.getSelectedIdBook())+""
                    		+ "\n id user corrente salvato in client risulta:"+me.getSelectedIdUser());
            }
		});
		 System.out.println("12");
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
					 System.out.println("13");
					selectedR = getTable().getSelectedRow(); // riga
					selectedC = getTable().getSelectedColumn(); // colonna	
					oldValue = (String) getTable().getValueAt(selectedR, selectedC);
				}
			}
	    });
	    System.out.println("14");
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
    			System.out.println("15");
    			getTm().setValueAt(oldValue, selectedR, selectedC);
    			TableUpdateBooks.setNotOk(false);
    		}
        }

        @Override
        public void editingStopped(ChangeEvent e) 
        {            
    		if(TableUpdateBooks.isNotOk())
    		{    
    			System.out.println("16");
    			getTm().setValueAt(oldValue, selectedR, selectedC);
    			TableUpdateBooks.setNotOk(false);
    		}
        }
    };
        
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
	

}
