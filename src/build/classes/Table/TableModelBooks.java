package Table;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import Books.Books;
import Core.Commands;
import connections.Client;
import database.MQ_Delete;
import database.MQ_Read;

public class TableModelBooks extends AbstractTableModel implements Serializable {
    private Client me;
    
	private static final long serialVersionUID = 1L;
    private String[] columnNames = {"Codice", "Nome_Autore", "Cognome_Autore", "Categoria", "Titolo","Disponibilità","Prenotazioni_in_coda"};
    private Object[][] data = null;
    
    public TableModelBooks(Client me) 
    {
    	setData(me.getDatabook());
    }
        
    
    public String[] getColumnNames(List<Books> books) {
		return columnNames;
	}

	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}
	/*
	 @Override
	public void fireTableDataChanged()
    { 
		 
		try {
			
			//me.getCmdLIST().put(Commands.BookPopulate);	
			data = MQ_Read.RicercaLibro();
			setData(me.getDatabook());
			}catch (Exception e)	{e.printStackTrace();		} 
    }
    */
    @Override
    public boolean isCellEditable(int row, int col)
    { 
    	return true; 
    }

	@Override
    public int getColumnCount()
	{
        return columnNames.length;
    }
	
	@Override
    public int getRowCount()
	{
        return getData().length;
    }
	
	@Override
    public String getColumnName(int col)
	{
        return columnNames[col];
    }
	
	@Override
    public Object getValueAt(int row, int col)
	{
        return getData()[row][col];
    }
	
	@Override
    public void setValueAt(Object value, int row, int col)
	{
	    getData()[row][col] = value;
		fireTableCellUpdated(row, col);
    }

	public void setColumnNames(String string, String string2) {
		// TODO Auto-generated method stub
		
	}


	public Object[][] getData() {
		return data;
	}

	public void setData(Object[][] data) {
		this.data = data;
	}



}
