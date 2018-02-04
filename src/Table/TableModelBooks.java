package Table;

import java.io.Serializable;
import javax.swing.table.AbstractTableModel;
import connections.Client;


public class TableModelBooks extends AbstractTableModel implements Serializable {
    private Client me;
    
	private static final long serialVersionUID = 1L;
    private String[] columnNames = {"Codice", "Nome_Autore", "Cognome_Autore", "Categoria", "Titolo","Disponibilità","Prenotazioni_in_coda"};
    private Object[][] data = null;
    
    public TableModelBooks(Client me) 
    {
    	setData(me.getDatabook());
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
	
		
	}


	public Object[][] getData() {
		return data;
	}

	public void setData(Object[][] data) {
		this.data = data;
	}

	public Client getMe() {
		return me;
	}

	public void setMe(Client me) {
		this.me = me;
	}



}
