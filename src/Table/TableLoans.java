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
import Core.Clients;
import Core.Commands;
import connections.Client;
import gui.ResearchBooks;

/**
 * @author luca
 *
 */
public class TableLoans extends JPanel implements TableModelListener,Serializable{
	
	private static final long serialVersionUID = 1L;
	private static 	JTable table;
	private ResearchBooks frame;
//	private JFrame 	frame;
	private 		TableModelLoans tm;
	private int 	deleteRow;
	private int 	selectedR;
	private int 	selectedC;	
	private int 	selectedRow;
	private Client	me;
	
	private static boolean storico;
	
	private String oldValue;
	//private static DefaultTableModel dm;
	
	
	
    public TableLoans(ResearchBooks frame,Client me)  throws InterruptedException
    {
        super(new GridLayout(1,0));
    	this.me=me;
        this.setFrame(frame);
        
        setTm(new TableModelLoans(me));
        
        setTable(new JTable(getTm()));
        
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("Delete");

        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
                if(PopUp.confirmBox(frame))
                {
                	
                	List<String> rowData = new ArrayList<String>();
                	
        			for(int i = 0; i<8; i++)
        			{
        				
        				rowData.add((String) getTm().getValueAt(deleteRow, i));
        			}        			
        		
                     
                    		try {
    				me.getCmdLIST().put(Commands.LoanDELETE);
                    		} catch (InterruptedException e1) {e1.printStackTrace();}
        			
  
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
                me.setSelectedIdBook(Integer.valueOf(	idlibro));  
                me.setSelectedIdUser(Integer.valueOf(	idutente));
                
                
                
                
                
                
                PopUp.infoBox(frame, new String (	"\nottenuto idbook  : "+me.getSelectedIdBook()+
                									"\nottenuto iduser  : "+me.getSelectedIdUser()+
                									"\nottenuto idD init: "+me.getSelectedIdDataStart()+
													"\nottenuto idD stop: "+me.getSelectedIdDataStop())
                			);
				
		
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
		add(scrollPane);
    }
    
    CellEditorListener ChangeNotification = new CellEditorListener() {
    	
    	@Override
        public void editingCanceled(ChangeEvent e) 
    	{            
    		if(TableUpdateLoans.isNotOk())
    		{
    			
    			getTm().setValueAt(oldValue, selectedR, selectedC);
    			TableUpdateLoans.setNotOk(false);
    		}
        }

        @Override
        public void editingStopped(ChangeEvent e) 
        {            
    		if(TableUpdateLoans.isNotOk())
    		{    
    			
    			getTm().setValueAt(oldValue, selectedR, selectedC);
    			TableUpdateLoans.setNotOk(false);
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
		
    	System.err.println("storico:"+me.isStorico());
    	
    	// Clear table   		
    	getTable().setModel(new DefaultTableModel());	
    	// Model for Table		
		DefaultTableModel model = (DefaultTableModel)getTable().getModel();		
		model.addColumn("Codice");
		model.addColumn("Id");
		model.addColumn("Data_Inizio");
		model.addColumn("Data_Fine");
		model.addColumn("Rientrato");
		model.addColumn("Ritirato");
		model.addColumn("Scaduto");
		model.addColumn("Email_Inviata");
		String query=null;
		
		System.out.println("Client me " + me.toString());		
		System.out.println("Client me type " + me.getCliType().toString());		
		System.out.println("Valore ritornato:" + x);	
		System.out.println("RICONOSCO COME UTENTE : "+me.getIdut());

		
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
										if (me.isStorico()) {	query = "SELECT * FROM prestiti 						ORDER BY data_inizio DESC;";					
										}else {					query = "SELECT * FROM prestiti WHERE data_fine is null ORDER BY data_inizio DESC;";	
										}	
						me.setSql(query);
						me.getCmdLIST().put(Commands.LoanExecuteQuery);
						
						break;	
					
					case Reader:
						System.err.println("ti considero un READER");
										if (me.isStorico()) {	query = "SELECT * FROM prestiti WHERE id = '"+me.getIdut()+"' 						ORDER BY data_inizio DESC;";					
										}else {					query = "SELECT * FROM prestiti WHERE id = '"+me.getIdut()+"' AND data_fine is null ORDER BY data_inizio DESC;";	
										}	
						me.setSql(query);
						me.getCmdLIST().put(Commands.LoanExecuteQuery);
						
						break;					
										
					case Guest		:			break;
					case Default	:			break;			
					default			:			break;				
		}	
	}
  
    
	@Override
	public void tableChanged(TableModelEvent e) 
	{
        int row = e.getFirstRow();
        int column = e.getColumn();
        TableModelLoans model = (TableModelLoans)e.getSource(); // book
        String columnName = model.getColumnName(column);
        Object data = model.getValueAt(row, column);
        if(!TableUpdateLoans.isNotOk())
		{
        	TableUpdateLoans.setColumn(column);
        	TableUpdateLoans.setInput((String)model.getValueAt(row, column));
		} 
	}

	public static JTable getTable() {
		return table;
	}


	public static void setTable(JTable table) {
		TableLoans.table = table;
	}


	public TableModelLoans getTm() {
		return tm;
	}


	public void setTm(TableModelLoans tm) {
		this.tm = tm;
	}


	public ResearchBooks getFrame() {
		return frame;
	}


	public void setFrame(ResearchBooks frame) {
		this.frame = frame;
	}


	public static boolean isStorico() {
		return storico;
	}


	public static void setStorico(boolean storico) {
		storico = storico;
	}
}