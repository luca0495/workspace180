package connections;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Date;
import Core.Clients;
import Core.Commands;

// ver 2017 04 10 v 15
public class Message implements Serializable {	/* l'oggetto prodotto da questa classe verrà serializzato ed usato per tx info */
	private static final long serialVersionUID =1;	
	
	private 	Clients	 	UType;
	private 	Commands 	Cmd;
	private		String		MesId;
	private 	Date		DateOfRequest;
	private 	String		SQLQuery;	
	private 	String		SQLQuery2;
	private 	String  	text;
	private 	String  	pw;
	private 	ResultSet 	rs;
	private 	int			LoginTry;
	private		int			idut;
	private		int			idbook;

	private 	String  	Fbook;
	private 	String  	Fbooking;
	private 	String  	Floans;

	private		int			selectedIdBook;
	private		int			selectedIdUser;
	private		Date		selectedIdDataStart;
	private		Date		selectedIdDataStop;	
	private     String		selectedDataLoanReturn;
	
	
	//Costruttori
	public Message (Commands cmd){
		setCommands(cmd);
		setDateOfRequest(new Date());
	}	
	public Message (
			Commands cmd,
			Clients Ut,
			String IdClient){
		
		setCommands(cmd);
		setDateOfRequest(new Date());
		
		setUType(Ut);
		setDateOfRequest(new Date());
		setMesId(DateOfRequest.toString().concat(getCmd().toString().concat(IdClient)));
	}
	public Message (
			Commands cmd,
			Clients Ut,
			String IdClient,
			String SQLQuery){
		
		setCommands(cmd);
		setDateOfRequest(new Date());
		
		setUType(Ut);
		setDateOfRequest(new Date());
		setMesId(DateOfRequest.toString().concat(getCmd().toString().concat(IdClient)));
		setSQLQuery(SQLQuery);
	}	
	public Message (
			Commands cmd,
			Clients Ut,
			String IdClient,
			String SQLQuery,
			String SQLQuery2){
		
		setCommands(cmd);
		setDateOfRequest(new Date());
		
		setUType(Ut);
		setDateOfRequest(new Date());
		setMesId(DateOfRequest.toString().concat(getCmd().toString().concat(IdClient)));
		setSQLQuery(SQLQuery);
		setSQLQuery2(SQLQuery2);
		
	}
	public Message (
			Commands cmd,
			Clients Ut,
			String IdClient,
			String SQLQuery,
			String SQLQuery2,
			String pw){
		
		setCommands(cmd);
		setDateOfRequest(new Date());
		
		setUType(Ut);
		setDateOfRequest(new Date());
		setMesId(DateOfRequest.toString().concat(getCmd().toString().concat(IdClient)));
		setSQLQuery(SQLQuery);
		setSQLQuery2(SQLQuery2);
		setPw(pw);
		setIdut(idut);
	}	
	public Message (
			Commands cmd,
			Clients Ut,
			String IdClient,
			String SQLQuery,
			String SQLQuery2,
			int tentativi){
		
		setCommands(cmd);
		setDateOfRequest(new Date());
		
		setUType(Ut);
		setDateOfRequest(new Date());
		setMesId(DateOfRequest.toString().concat(getCmd().toString().concat(IdClient)));
		setSQLQuery(SQLQuery);
		setSQLQuery2(SQLQuery2);
		setLoginTry(tentativi);
		
	}	
	public Message (
			Commands cmd,
			Clients Ut,
			String IdClient,
			String SQLQuery,
			String SQLQuery2,
			int tentativi,
			int idut){
		
		setCommands(cmd);
		setDateOfRequest(new Date());
		
		setUType(Ut);
		setDateOfRequest(new Date());
		setMesId(DateOfRequest.toString().concat(getCmd().toString().concat(IdClient)));
		setSQLQuery(SQLQuery);
		setSQLQuery2(SQLQuery2);
		setIdut(idut);
	}	
	//usato in ASK Loans
	public Message (
			Commands cmd,
			Clients Ut,
			String IdClient,
			int idbook,
			int idut){
		
		setCommands(cmd);
		setDateOfRequest(new Date());
		setUType(Ut);
		setDateOfRequest(new Date());
		setMesId(DateOfRequest.toString().concat(getCmd().toString().concat(IdClient)));
		setSelectedIdUser(idut);
		setSelectedIdBook(idbook);
	}	
	
	
	//usato in Loans Returned
	public Message (
			Commands cmd,
			Clients Ut,
			String IdClient,
			String dataC,
			int idbook,
			int idut){
		
		setCommands(cmd);
		setDateOfRequest(new Date());
		setUType(Ut);
		setDateOfRequest(new Date());
		setMesId(DateOfRequest.toString().concat(getCmd().toString().concat(IdClient)));
		setSelectedDataLoanReturn(dataC); 
		setSelectedIdUser(idut);
		setSelectedIdBook(idbook);
	}	
	
	
	
	
	
	
	
	
	
	
	// Metodo utilizzato per verifica da GuardianTimeOut
	public boolean equalTo(Message x){
		boolean equal=false;
		if (x.getMesId()	==	this.getMesId()){
			equal=true;
		}
		return equal;
	}

	public Commands getCommand(){
	return getCmd();	
	}	
	public void setCommands (Commands cmd){
	this.setCmd(cmd);
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Date getDateOfRequest() {
		return DateOfRequest;
	}
	public void setDateOfRequest(Date dateOfRequest) {
		DateOfRequest = dateOfRequest;
	}
	public Clients getUType() {
		return UType;
	}
	public void setUType(Clients uType) {
		UType = uType;
	}
	public Commands getCmd() {
		return Cmd;
	}
	public void setCmd(Commands cmd) {
		Cmd = cmd;
	}
	public String getMesId() {
		return MesId;
	}
	public void setMesId(String mesId) {
		MesId = mesId;
	}
	public String getSQLQuery() {
		return SQLQuery;
	}
	public void setSQLQuery(String sQLQuery) {
		SQLQuery = sQLQuery;
	}
	public ResultSet getRs() {
		return rs;
	}
	public void setRs(ResultSet rs) {
		this.rs = rs;
	}
	public String getSQLQuery2() {
		return SQLQuery2;
	}
	public void setSQLQuery2(String sQLQuery2) {
		SQLQuery2 = sQLQuery2;
	}
	public int getLoginTry() {
		return LoginTry;
	}
	public void setLoginTry(int loginTry) {
		LoginTry = loginTry;
	}
	public int getIdut() {
		return idut;
	}
	public void setIdut(int idut) {
		this.idut = idut;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public int getIdbook() {
		return idbook;
	}
	public void setIdbook(int idbook) {
		this.idbook = idbook;
	}

	
	public int getSelectedIdBook() {
		return selectedIdBook;
	}
	public void setSelectedIdBook(int selectedIdBook) {
		this.selectedIdBook = selectedIdBook;
	}
	public int getSelectedIdUser() {
		return selectedIdUser;
	}
	public void setSelectedIdUser(int selectedIdUser) {
		this.selectedIdUser = selectedIdUser;
	}
	public Date getSelectedIdDataStart() {
		return selectedIdDataStart;
	}
	public void setSelectedIdDataStart(Date selectedIdDataStart) {
		this.selectedIdDataStart = selectedIdDataStart;
	}
	public Date getSelectedIdDataStop() {
		return selectedIdDataStop;
	}
	public void setSelectedIdDataStop(Date selectedIdDataStop) {
		this.selectedIdDataStop = selectedIdDataStop;
	}
	public String getFbook() {
		return Fbook;
	}
	public void setFbook(String fbook) {
		Fbook = fbook;
	}
	public String getFbooking() {
		return Fbooking;
	}
	public void setFbooking(String fbooking) {
		Fbooking = fbooking;
	}
	public String getFloans() {
		return Floans;
	}
	public void setFloans(String floans) {
		Floans = floans;
	}
	public String getSelectedDataLoanReturn() {
		return selectedDataLoanReturn;
	}
	public void setSelectedDataLoanReturn(String selectedDataLoanReturn) {
		this.selectedDataLoanReturn = selectedDataLoanReturn;
	}
}
