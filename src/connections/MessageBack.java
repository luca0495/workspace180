package connections;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Date;

import javax.swing.JTable;

import Core.Clients;
import Core.Commands;
import Table.TableBooks;

// ver 2017 04 01 v7
public class MessageBack implements Serializable {	/* l'oggetto prodotto da questa classe verrà serializzato ed usato per tx info */
	private static final long serialVersionUID =1;	
	
	//private ServerReal	SrV;
	private Date		DateOfRequest;	
	private Boolean		Response;
	private String  	Text;
	private Record		Record;
	private RecordSet	RecordSet;
	//private ResultSet	rs;
	private JTable	tab;
	
	
	//Costruttori
	public MessageBack (){	
	}
	public MessageBack (String msg){
		this.Text = msg;	
	}
	
	
	//Comandi
	public String 		getText() {	
		return this.Text;
	}
	public void  		setText(String msg) {
		this.Text = msg;
	}	
	public Date 		getDateOfRequest() {
		return DateOfRequest;
	}
	public Boolean		getResponse(){
		return Response;
	}
	public Record		getRecord(){
		return Record;
	}
	public RecordSet	getRecordSet(){
		return RecordSet;
	}
	public JTable getTab() {
		return tab;
	}
	public void setTab(JTable tab) {
		this.tab = tab;
	}






}
