package Table;

import java.util.List;

import javax.swing.table.DefaultTableModel;

public class TableUpdateBooks {
	private static String input;
	private static List<String> rowData;
	private static int column;
	public static String getInput() {
		return input;
	}
	public static void setInput(String input) {
		TableUpdateBooks.input = input;
	}
	public static List<String> getRowData() {
		return rowData;
	}
	public static void setRowData(List<String> rowData) {
		TableUpdateBooks.rowData = rowData;
	}
	public int getColumn(DefaultTableModel model) {
		return column;
	}
	public static void setColumn(int column) {
		TableUpdateBooks.column = column;
	} 
}
