package Table;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory.Default;

public class SharedListSelectionHandler implements ListSelectionListener  {

	private TableModelBooks tb;
	private TableModelLoans tl;
	private TableModelBooking tc;
	private boolean selection = false;
	private String sel="default";
	
	
    public SharedListSelectionHandler(TableModelBooks t)
    {
    	super();
    	tb = t;
    	selection = true;
    	setSel("book");
    }
    
    public SharedListSelectionHandler(TableModelLoans t)
    {
    	super();
    	tl = t;
    	selection = false;
    	setSel("loans");
    }
    
    public SharedListSelectionHandler(TableModelBooking t)
    {
    	super();
    	tc = t;
    	selection = false;
    	setSel("booking");
    }
	
    public void valueChanged(ListSelectionEvent e) 
	{		
		List<String> rowData = new ArrayList<String>();
		int index = e.getFirstIndex();
		boolean isAdjusting = e.getValueIsAdjusting();
		String sel=getSel();
		
		/*
		//mauro TEST
		if(!isAdjusting)
		{		
				switch (sel) {
				 	case "loans":
						for(int j = 0; j<8; j++)
						{
							rowData.add((String) tb.getValueAt(index, j));
						}
						TableUpdateLoans.setRowData(rowData);
						break;
					case "book":
					{
						for(int j = 0; j<7; j++)
						{
							rowData.add((String) tb.getValueAt(index, j));
						}
						TableUpdateBooks.setRowData(rowData);
					}
						break;
					
					case "booking":
						for(int j = 0; j<4; j++)
						{
							rowData.add((String) tb.getValueAt(index, j));
						}
						TableUpdateBooking.setRowData(rowData);
						
						break;
					
					default:
						break;
				}
		}
		*/
		
		
		if(selection)
		{
			if(!isAdjusting)
			{
				for(int j = 0; j<7; j++)
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
				for(int j = 0; j<4; j++)
				{
					
					//errore da booking
					rowData.add((String) tl.getValueAt(index, j));
				}
				
				//TableUpdateLoans.setRowData(rowData);
				TableUpdateBooking.setRowData(rowData);	
			}
			else
			{
				if(!isAdjusting)
				{
					for(int j = 0; j<4; j++)
					{
						rowData.add((String) tl.getValueAt(index, j));
					}
					TableUpdateBooking.setRowData(rowData);
				}
			}
			
		}
	
		
	}

	public String getSel() {
		return sel;
	}

	public void setSel(String sel) {
		this.sel = sel;
	}
    
    
    
  /*  
	public void valueChanged(ListSelectionEvent e) 
	{		
		List<String> rowData = new ArrayList<String>();
		
		int index = e.getFirstIndex();
		System.err.println("first index: "+e.toString());
		
		
		boolean isAdjusting = e.getValueIsAdjusting();
		
		if(selection)
		{
			if(!isAdjusting)
			{
				for(int j = 0; j<7; j++)
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
				for(int j = 0; j<4; j++)
				{
					
					//errore da booking
					rowData.add((String) tl.getValueAt(index, j));
				}
				
				//TableUpdateLoans.setRowData(rowData);
				TableUpdateBooking.setRowData(rowData);	
			}
			else
			{
				if(!isAdjusting)
				{
					for(int j = 0; j<4; j++)
					{
						rowData.add((String) tl.getValueAt(index, j));
					}
					TableUpdateBooking.setRowData(rowData);
				}
			}
			
		}
	}
*/
}
