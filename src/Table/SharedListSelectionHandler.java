package Table;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
    	setSelection(true);
    	setSel("book");
    }
    
    public SharedListSelectionHandler(TableModelLoans t)
    {
    	super();
    	tl = t;
    	setSelection(false);
    	setSel("loans");
    }
    
    public SharedListSelectionHandler(TableModelBooking t)
    {
    	super();
    	tc = t;
    	setSelection(false);
    	setSel("booking");
    }
	
    public void valueChanged(ListSelectionEvent e) 
	{		
		List<String> rowData = new ArrayList<String>();
		int index = e.getFirstIndex();
		boolean isAdjusting = e.getValueIsAdjusting();
		String sel=getSel();

		if(!isAdjusting)
		{		
				switch (sel) {
				 	case "loans":
						for(int j = 0; j<8; j++)
						{
							rowData.add((String) tl.getValueAt(index, j));
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
							rowData.add((String) tc.getValueAt(index, j));
						}
						TableUpdateBooking.setRowData(rowData);
						
						break;
					
					default:
						break;
				}
		}
		
	
		
	}

	public String getSel() {
		return sel;
	}

	public void setSel(String sel) {
		this.sel = sel;
	}

	public boolean isSelection() {
		return selection;
	}

	public void setSelection(boolean selection) {
		this.selection = selection;
	}
   
}
