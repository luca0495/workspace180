package connections;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

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
	private String  	UserEmail;
	private String  	Wtype;
	private int			IdUser;
	private int			IdBook;
	
	private String[] 	RowUser;
	private String[] 	RowBook;
	private String[] 	RowLoans;

	private String[][]	Datitabella;
	
	private 			Object[][]			databook;
	private 			Object[][]			databooking;
	private 			Object[][]			dataloans;
	
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
	public String[] getRowUser() {
		return RowUser;
	}
	public void setRowUser(String[] rowUser) {
		RowUser = rowUser;
	}
	public String[] getRowBook() {
		return RowBook;
	}
	public void setRowBook(String[] rowBook) {
		RowBook = rowBook;
	}
	public String[] getRowLoans() {
		return RowLoans;
	}
	public void setRowLoans(String[] rowLoans) {
		RowLoans = rowLoans;
	}
	public String getUserEmail() {
		return UserEmail;
	}
	public void setUserEmail(String userEmail) {
		UserEmail = userEmail;
	}
	public int getIdUser() {
		return IdUser;
	}
	public void setIdUser(int idUser) {
		IdUser = idUser;
	}
	public String getWtype() {
		return Wtype;
	}
	public void setWtype(String wtype) {
		Wtype = wtype;
	}
	public int getIdBook() {
		return IdBook;
	}
	public void setIdBook(int idBook) {
		IdBook = idBook;
	}
	public String[][] getDatitabella() {
		return Datitabella;
	}
	public void setDatitabella(String[][] datitabella) {
		Datitabella = datitabella;
	}
	public Object[][] getDatabook() {
		return databook;
	}
	public void setDatabook(Object[][] databook) {
		this.databook = databook;
	}
	public Object[][] getDatabooking() {
		return databooking;
	}
	public void setDatabooking(Object[][] databooking) {
		this.databooking = databooking;
	}
	public Object[][] getDataloans() {
		return dataloans;
	}
	public void setDataloans(Object[][] dataloans) {
		this.dataloans = dataloans;
	}






}
