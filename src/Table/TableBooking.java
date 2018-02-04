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
import Core.Clients;
import Core.Commands;
import connections.Client;


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
	
	
	
    public TableBooking(JFrame frame,Client me) throws InterruptedException  
    {
        super(new GridLayout(1,0));
    	this.setMe(me);
        this.setFrame(frame);
        setTm(new TableModelBooking(me));
        setTable(new JTable(getTm()));
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("Delete");

        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
                if(PopUp.confirmBox(frame))
                {
                	
                	List<String> rowData = new ArrayList<String>();
                	
        			for(int i = 0; i<4; i++)
        			{
        				
        				rowData.add((String) getTm().getValueAt(deleteRow, i));
        			}        			
        			try 
        			{ 
        				    							
        				TableUpdateBooking.deleteRow(rowData, getTable(), me);
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
	                
	                //dati TUPLA in selezione salvati su Client
	                String idlibro				= (String) source.getValueAt(selectedRow, 0);
	                String idutente				= (String) source.getValueAt(selectedRow, 1);	
	                int 	idbook		=Integer.valueOf(	idlibro);  
	                int 	iduser		=Integer.valueOf(	idutente);  

	                //setta su client idbook selezionato
	                me.setSelectedIdBook(idbook);
	                me.setSelectedIdUser(iduser);
		
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
		add(scrollPane);
    }
    
    CellEditorListener ChangeNotification = new CellEditorListener() {
    	
    	@Override
        public void editingCanceled(ChangeEvent e) 
    	{            
    		if(TableUpdateBooking.isNotOk())
    		{
    			System.out.println("15");
    			getTm().setValueAt(oldValue, selectedR, selectedC);
    			TableUpdateBooking.setNotOk(false);
    		}
        }

        @Override
        public void editingStopped(ChangeEvent e) 
        {            
    		if(TableUpdateBooking.isNotOk())
    		{    
    			System.out.println("16");
    			getTm().setValueAt(oldValue, selectedR, selectedC);
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



	/**
	 * Questo metodo serve per popolare i dati della tabella booking e intanto controlla il tipo di utente
	 * @param x
	 * @param me
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	public static void PopulateData(String x,Client me) throws SQLException, InterruptedException {
		
		System.err.println("storico:"+me.isStorico());
		
		// Clear table   
		getTable().setModel(new DefaultTableModel());	
		// Model for Table		
		DefaultTableModel model = (DefaultTableModel)getTable().getModel();		
		model.addColumn("Codice");
		model.addColumn("Id");
		model.addColumn("Prioritá");
		model.addColumn("Data_Inizio");
		String query=null;
		
		System.out.println("Client me " + me.toString());		
		System.out.println("Client me type " + me.getCliType().toString());		
		System.out.println("Valore ritornato:" + x);		

	
		Clients tipo;
		//****************************************************************************************
		if (me.getIdut()==0) {//utente non loggato
			tipo = Clients.Guest;	
		}else{
			if (  me.getDatiUtente()[6].equals("Libraio")){ 	tipo = Clients.Librarian;
			}else {
				if (me.getDatiUtente()[6].equals("Lettore")){	tipo = Clients.Reader;
				}else {											tipo = Clients.Guest;		
				}
			}			
		}
		
		switch (tipo) {		//tipo di utente
					case Librarian	:			
						System.err.println("ti considero un LIBRARIAN");
										query = "SELECT * FROM prenotazioni ORDER BY data_inizio DESC;";						
						me.setSql(query);
						me.getCmdLIST().put(Commands.BookingExecuteQuery);
						me.setBusy(false);
						break;	
					
					case Reader:
						System.err.println("ti considero un READER");
										query = "SELECT * FROM prenotazioni WHERE id = '"+me.getIdut()+"' ORDER BY data_inizio DESC;";													
						me.setSql(query);
						me.getCmdLIST().put(Commands.BookingExecuteQuery);
						me.setBusy(false);
						break;					
										
					case Guest		:			break;
					case Default	:			break;			
					default			:			break;				
		}	
		


		
	}
	
	public static JTable getTable() {
		return table;
	}


	public static void setTable(JTable table) {
		TableBooking.table = table;
	}

	public TableModelBooking getTm() {
		return tm;
	}

	public void setTm(TableModelBooking tm) {
		this.tm = tm;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public Client getMe() {
		return me;
	}

	public void setMe(Client me) {
		this.me = me;
	}
}