package Table;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import Core.Commands;
import connections.Client;
import database.MQ_Delete;
import database.MQ_Read;

public class TableModelBooking extends AbstractTableModel implements Serializable {
	private Client me;
	
	private static final long serialVersionUID = 1L;
    private String[] columnNames = {"Codice", "Id", "Priorità","Data_Inizio"};
    private Object[][] data = null;
    
    public TableModelBooking(Client me) throws InterruptedException
    {
			setData(me.getDatabooking());
    }

    /*
	@Override
    public void fireTableDataChanged()
    { 
		
		try 
		{
			
			//me.getCmdLIST().put(Commands.BookingPopulate);
			//data = MQ_Read.ResearchBooking();
			//setData(me.getDatabooking());
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
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