package Table;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import Check.Check;
import Check.PopUp;
import Core.Commands;
import connections.Client;
import database.MQ_Delete;
import database.MQ_Update;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTable;

public class TableUpdateBooks {
	private static String input;
	private static List<String> rowData;
	private static int column;
	private static boolean notOk = false;
	
	public static void check(JFrame frame, JTable t) 
	{	
		if(column == 1)
		{
			//Nome
			
			if(Check.checkName(input))
			{
				execute(frame, t);
			}
			else
			{
				PopUp.errorBox(frame, "Campo Invalido - Nome Errato");
				setNotOk(true);
			}
		}
		else if(column == 2)
		{
			//Cognome
			if(Check.checkName(input))
			{
				execute(frame, t);
			}
			else
			{
				PopUp.errorBox(frame, "Campo Invalido - Cognome Errato");
				setNotOk(true);
			}
		}
		else if(column == 3)
		{
			//nome
			if(Check.checkCat(input))
			{
				execute(frame, t);
			}
			else
			{
				PopUp.errorBox(frame, "Campo Invalido - Categoria errata");
				setNotOk(true);
			}
		}
		else if(column == 4)
		{
			if(Check.checkName(input))
			{
				execute(frame, t);
			}
			else
			{
				PopUp.errorBox(frame, "Campo Invalido - Titolo errato");
				setNotOk(true);
			}
		}
	}
	
	// 	OLD TEST OK
	 public static void deleteRow(List<String> r, JTable t) throws SQLException
		{
		 	MQ_Delete.deleteRowBooks(r);
		}
	 // NEW IN TEST 27.12.2017
	 public static void deleteRow(List<String> r, JTable t,Client me) throws SQLException
		{
		 	String q = MQ_Delete.deleteRowBooksGetQuery(r);
			me.setActTable(t);
			me.setSql(q);
			try {
				me.getCmdLIST().put(Commands.BookDELETE);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	 

	 
	public static int getColumn() {
		return column;
	}

	public static boolean isNotOk() {
		return notOk;
	}

	public static void setNotOk(boolean notOk) {
		TableUpdateBooks.notOk = notOk;
	}

	public static void execute(JFrame frame, JTable t) 
	{
		try 
		{
			MQ_Update.updateTableCliente(rowData.get(0), input, column);
			PopUp.infoBox(frame, "Modifica Corretta");
			if(!isNotOk())
			{
				t.getSelectionModel().clearSelection();
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
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
