package Table;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SharedListSelectionHandler implements ListSelectionListener  {

	private TableModelBooks tb;
	private boolean selection = false;
	
    public SharedListSelectionHandler(TableModelBooks t)
    {
    	super();
    	tb = t;
    	selection = true;
    }
	
	public void valueChanged(ListSelectionEvent e) 
	{		
		List<String> rowData = new ArrayList<String>();
		
		int index = e.getFirstIndex();
		
		boolean isAdjusting = e.getValueIsAdjusting();
		
		if(selection)
		{
			if(!isAdjusting)
			{
				for(int j = 0; j<5; j++)
				{
					rowData.add((String) tb.getValueAt(index, j));
				}
			}
			
			TableUpdateBooks.setRowData(rowData);
		}
		else
		{
			if(!isAdjusting)
			{
				for(int j = 0; j<6; j++)
				{
					rowData.add((String) tb.getValueAt(index, j));
				}
			}
			
			TableUpdateBooks.setRowData(rowData);
		}
	}

}
