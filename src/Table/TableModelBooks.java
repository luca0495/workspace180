package Table;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import Books.Books;
import database.MQ_Delete;
import database.MQ_Read;

public class TableModelBooks extends AbstractTableModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
    private String[] columnNames = {"Codice", "Nome_Autore", "Cognome_Autore", "Categoria", "Titolo", "Num_Prenotazioni"};
    private Object[][] data = null;
    
    public TableModelBooks()
    {
		try 
		{
			data = MQ_Read.RicercaLibro();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
    }
        
    public String[] getColumnNames(List<Books> books) {
		return columnNames;
	}

	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}

	@Override
    public void fireTableDataChanged()
    { 
		try 
		{
			data = MQ_Read.RicercaLibro();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
    }
    
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
        return data.length;
    }
	
	@Override
    public String getColumnName(int col)
	{
        return columnNames[col];
    }
	
	@Override
    public Object getValueAt(int row, int col)
	{
        return data[row][col];
    }
	
	@Override
    public void setValueAt(Object value, int row, int col)
	{
	    data[row][col] = value;
		fireTableCellUpdated(row, col);
    }

	public void setColumnNames(String string, String string2) {
		// TODO Auto-generated method stub
		
	}

}
