package Check;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import database.MQ_Check;
import database.MQ_Insert;
import database.MQ_Read;

public class Check {

	
	// METODI	
	
	public static boolean checkName(String s)
    {
	    s = s.trim();
	    
	    String nomePattern = new String("[a-zA-Z]*");

	    if(s == null || s.equals(""))
	    {
	    	return false;
	    }

	    if(!s.matches(nomePattern))
	    {
	    	return false;
	    }

	    return true;
    }
	
	public static boolean checkCat(String c)
    {
	    c = c.trim();
	    
	    String[] catPattern = new String[] {"romanzo","giallo"};

	    if(c == null || c.equals(""))
	    {
	    	return false;
	    }
	    
	    for(int i = 0; i < catPattern.length; i++)
    	{
    		if(catPattern[i].equals(c))
    		{
    			return true;
    		}
    	}
    	
    	return false;
    }

	
	public static boolean checkTel(String a)
    {		
		a = a.trim();
		
		String Telpattern = "[0-9]{10}$";
		
		if (a==null || a.equals(""))
			
		{
			
	    return false;
		}
		
		
		if(!a.matches(Telpattern))
		{
			
		return false;
		
		}
		return true;
    }

    public static boolean checkMail(String m)
    {
	    m = m.trim();
	    
	    String emailPattern = new String("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	
	    if(m == null || m.equals(""))
	    {
	    	return false;
	    }
	    
	    if(!m.matches(emailPattern))
	    {	
	        return false;
	    }    
	
	    return true;
    }
    
  
    public static boolean checkMailExist(String m)
    {
		String results = null;
		
		try 
		{
			results = MQ_Check.selectMail(m);
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		if(results.equals("No Data"))
		{
			return true;
		}
		else
		{
			return false;
		}	
    }
   
    
	public static boolean checkPass1(char[] c)
    {
    	String p = new String(c);
    	
	    p = p.trim();
	    
	    String emailPattern = new String("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})");

	    if(p == null || p.equals(""))
	    {
	    	return false;
	    }
	    
	    if(!p.matches(emailPattern))
	    {	
	        return false;
	    }    

	    return true;
    }
	
	public static boolean checkPass(String p)
    {    	
	    p = p.trim();
	    
	    String emailPattern = new String("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})");

	    if(p == null || p.equals(""))
	    {
	    	return false;
	    }
	    
	    if(!p.matches(emailPattern))
	    {	
	        return false;
	    }    

	    return true;
    }
	
	public static boolean checkPassEq(char[] c1,char[] c2)
    {
		boolean isUguale = false;
    	String p1 = new String(c1);
    	String p2 = new String(c2);
    	if((checkPass1(c1)) && (checkPass1(c2)))
    	{
    		isUguale = (p1.equals(p2));
    	}
    	
    	return isUguale;
    }

	public static boolean checkPassEq1(String c1,String c2)
    {
		boolean isUguale = false;
    	String p1 = new String(c1);
    	String p2 = new String(c2);
    	if((checkPass(c1)) && (checkPass(c2)))
    	{
    		isUguale = (p1.equals(p2));
    	}
    	
    	return isUguale;
    }
	public static boolean checkCF(String b)
    {
    	
	    b = b.trim();
	    
	    String CodFisPattern = new String("[A-Z0-9]{16}$");

	    if(b == null || b.equals(""))
	    {
	    	return false;
	    }
	    
	    if(!b.matches(CodFisPattern))
	    {	
	        return false;
	    }    

	    return true;
    }
	
	
	public static boolean checkCodFisExist(String c)
	{
		String results = null;
		
		try 
		{
			results = MQ_Check.selectCFexist(c);
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		if(results.equals("No Data"))
		{
			return true;
		}
		else
		{
			return false;
		}	
	}
	 public static boolean checkInq(String s)
	    {	
	    	return (s.length() <= 50);
	    }
	 
	 // esamina password TEMPORANEA
	 public static String checkAdminLogInFIRST(String email, String pass) throws SQLException {
	    	

	    	if(email.equals(null) || email.equals("") || pass.equals(null) || pass.equals("")){
	    			return new String("I Campi Non Possono Essere Vuoti");
	    	}else{
	    			
	    			//**********************************************************
	    			String[] datiUtente= MQ_Read.selectAdminLogInFIRST(email, pass);
	    			//**********************************************************
	    			
	    			if(datiUtente[0].equals("Nessun Dato"))	{
	   	        			return new String("L'Email Non Esiste");
	   	        	}else{        		
	   	        			if(pass.equals(datiUtente[1])){
	   	        					return new String("Login Corretto");
	   	        			}else{
	    							return new String("Password Errata");
	   	        			}
	   	        	}

	    	}
	    }
	 
	 // esamina password 
	  public static String checkAdminLogIn(String email, String pass) throws SQLException
	    {
	    	
	    	if(email.equals(null) || email.equals("") || pass.equals(null) || pass.equals(""))
	    	{
	    		return new String("I Campi Non Possono Essere Vuoti");
	    	}
	    	else
	    	{
	    		String[] datiUtente= MQ_Read.selectAdminLogIn(email, pass);
	   
	        	if(datiUtente[0].equals("Nessun Dato"))
	        	{
	        		
	        		return new String("L'Email Non Esiste");
	        	}
	        	else
	        	{        		
	    			if(pass.equals(datiUtente[1]))
	    			{
	    				
	    				return new String("Login Corretto");
	    				
	    			}
	    			else
	    			{
	    				
	    				return new String("Password Errata");
	    			}
	        	}

	    	}
			
	    }
	  public static boolean checkAllPassForg(String mail, char[] pass,char[] checkPassword )
	    {
	    
			return checkMail(mail) && checkMailExist(mail) && checkPass1(pass) &&  checkPass1(checkPassword) && checkPassEq(pass, checkPassword);
	    }
	 
	  public static boolean checkAllRegMod(String nome, String cognome,String mail, char[] pass,char[] checkPassword,String inq,String tel )
	    {
	    
			return checkName(nome) && checkName(cognome) && checkMail(mail) && checkMailExist(mail) && checkPass1(pass) &&  checkPass1(checkPassword) && 
	    			checkPassEq(pass, checkPassword) && checkInq(inq) && checkTel(tel) ;
	    }
	 public static boolean checkAllReg(String nome, String cognome, String telefono, String email, String codicefiscale, char[] pass, char[] passC, String inq)
	    {
	    	return checkName(nome) && checkName(cognome) && checkTel(telefono) && checkMail(email) && checkMailExist(email) && checkCF(codicefiscale) && checkCodFisExist(codicefiscale) && checkPass1(pass) 
	    			&& checkPass1(passC) && checkPassEq(pass, passC)&& checkInq(inq);
	    }
	 
	 public static boolean checkAllBooks(String nome, String cognome, String categoria, String titolo)
	    {
	    	return checkName(nome) && checkName(cognome) && checkCat(categoria) && checkName(titolo);
	    }
	
	 
	//********************************************
	/*
	public static boolean checkDate(String d) 
	{	
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    boolean valid = false;
	    formatter.setLenient(false);
	    Date date = new Date();
	    try 
	    { 	
	    	Date parsedDate = formatter.parse(d);
	    	if (date.equals((parsedDate)))
	    	{
	    		valid = true;
	    	}
	    	else if(date.before(parsedDate)) 
	    	{
	    		valid = true;
	    	}
	    	else
	    	{
	    		valid = false;
	    	}
	    } 
	    catch (ParseException e)
	    {
	    	valid = false;
	    }
	    
	    return valid;
	}
	
	public static boolean checkCompareDates(LocalDate l1, LocalDate l2) 
	{
		if(l2.isAfter(l1))
		{
			return true;
		}
		else
		{
			return false;
		}
    }
	
	public static boolean checkCompareDates(String d1, String d2) 
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dI = LocalDate.parse(d1, formatter);
		LocalDate dF = LocalDate.parse(d2, formatter);
		
		if(dI.isAfter(dF))
		{
			return false;
		}
		else
		{
			return true;
		}
    }
	
	public static boolean checkOccupiedSeats(List<String> data)
	{
		List<String> occupiedSeats = new ArrayList<String>();
		try 
		{
			occupiedSeats = Query.retrievePostiOccupati(data.get(2), data.get(3));
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		for(int i = 0; i<occupiedSeats.size(); i++)
		{
			if(occupiedSeats.get(i).equals((data.get(1))))
			{
				return false;
			}
		}
		return true;
	}
    
    public static boolean checkSeat(String s)
    {	
    	String[] posti = new String[]{"A1","A2","A3","A4","B1","B2","B3","B4","C1","C2","C3","C4"};
    	for(int i = 0; i < posti.length; i++)
    	{
    		if(posti[i].equals(s))
    		{
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    public static boolean checkSeats(List<String> selectedSeats)
    {	
    	if(selectedSeats.isEmpty())
    	{
        	return false;
    	}
    	else
    	{
        	return true;
    	}
    }
    
    //VEDERE QUI PER LIBRARIAN
    public static String checkLogIn(String mail, String pass) throws SQLException
    {
    	
    	if(mail.equals(null) || mail.equals("") || pass.equals(null) || pass.equals(""))
    	{
    		return new String("I Campi Non Possono Essere Vuoti");
    	}
    	else
    	{
    		String[] datiCliente = Query.selectLogIn(mail);
    		if(datiCliente[0].equals("Nessun Dato"))
    		{
    			return new String("Campi errati");
    		}
    		else
    		{
        		if(mail.equals(datiCliente[0]))
        		{
        			if(pass.equals(datiCliente[1]))
        			{
        				return new String("Login Corretto");
        			}
        			else
        			{
        				return new String("Password Errata");
        			}
        		}
        		else
        		{
        			return new String("Mail Errata");
        		}
    		}
    	}
		
    }
    // VEDERE QUI PER LIBRARIAN
    public static String checkAdminLogIn(String username, String pass) throws SQLException
    {
    	
    	if(username.equals(null) || username.equals("") || pass.equals(null) || pass.equals(""))
    	{
    		return new String("I Campi Non Possono Essere Vuoti");
    	}
    	else
    	{
    		String[] datiCliente = Query.selectAdminLogIn(username);
    		
        	if(datiCliente[0].equals("Nessun Dato"))
        	{
        		return new String("L'Username Non Esiste");
        	}
        	else
        	{        		
    			if(pass.equals(datiCliente[1]))
    			{
    				return new String("Login Corretto");
    			}
    			else
    			{
    				return new String("Password Errata");
    			}
        	}

    	}
		
    }
    */
    /*
     // CHECK ALLCLI
    
    public static boolean checkAllPre(List<String> data)
    {
    	return checkCF(data.get(0)) && !checkCodFisExist(data.get(0)) && checkSeat(data.get(1)) && checkDate(data.get(2)) && checkDate(data.get(3)) && checkCompareDates(data.get(2), data.get(3)) && checkInfo(data.get(4));
    }
    
    public static void checkYear() throws SQLException
    {
    	String[] dateStagione = Query.selectDatesAL();
    	String[] dateChisura = Query.selectDatesCloseAL();
    	
    	LocalDate dI = LocalDate.parse(dateStagione[0]);
		LocalDate dF = LocalDate.parse(dateStagione[1]);
		LocalDate dC = LocalDate.parse(dateChisura[0]);
		LocalDate dA = LocalDate.parse(dateChisura[1]);
    	
    	if(dF.isBefore(LocalDate.now()))
    	{
    		dI = dI.plusYears(1);
    		dF = dF.plusYears(1);
    		Query.updateDatesAL(dI.toString(), dF.toString());
    		
    	}
    	
    	if(dC.isBefore(LocalDate.now()))
    	{
    		dC = dC.plusYears(1);
    		dA = dA.plusYears(1);
    		Query.updateDatesCloseAL(dC.toString(), dA.toString());
    	}   	
    			
    }
    */
//******************************************************
}