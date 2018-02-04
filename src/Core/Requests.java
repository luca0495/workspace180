package Core;

/**
 * @author Mauro
 *
 */
public class Requests {
	
	private static int Max;
	//Accounts
	private RequestsList AL;
	private RequestsList AR;	
	//Books
	private RequestsList BL;
	private RequestsList BR;	// NON USATO
	//Prenotations
	private RequestsList PL;
	private RequestsList PR;
	//Booking
	private RequestsList BKL;
	private RequestsList BKR;
	//SETTING
	private RequestsList SL;
	private RequestsList SR;
	
	// conteggio richieste servite consecutivamente  
	// cs = Countinuosly Served	
	private int BLcs=0;
	private int BRcs=0;
	private int ALcs=0;
	private int ARcs=0;
	private int PLcs=0;
	private int PRcs=0;
	private int SLcs=0;
	private int SRcs=0;
	private int BKLcs=0;
	private int BKRcs=0;
	
	
	
	private	Requests.RS lastserved;
	
	public enum RS {	//	Requests Served
		
		//COSTANTI ENUMERATIVE
		PL		("modify Prenotation 	by Librarian"	,	5,	0),
		PR		("modify Prenotation 	by Reader"		,	6,	0),
		BL		("modify Book 	 		by Librarian"	,	1,	0),
		BR		("modify Book 	 		by Reader"		,	2,	0),
		AL		("modify Account 		by Librarian"	,	3,	0),
		AR 		("modify Account 		by Reader" 		,	4,	0), 
		SL		("modify Setting 		by Librarian"	,	7,	0),
		SR 		("modify Setting 		by Reader" 		,	8,	0), 
		BKL		("modify Setting 		by Librarian"	,	9,	0),
		BKR		("modify Setting 		by Reader" 		,	10,	0) 
		
		
		
		;
		//campi
				public String Den;
				public int Priority;
				public int Records;				
		//costruttori
				private RS(String Den, int pri,int rec){
				this.Den = Den;	
				this.Priority = pri;
				this.Records=rec;
				}
		};	
		
	public Requests (int max){
		Max=max;
		
		setBL(new RequestsList(100));
		setBR(new RequestsList(100));
		setAL(new RequestsList(100));
		setAR(new RequestsList(100));
		setPL(new RequestsList(100));
		setPR(new RequestsList(100));
		setSL(new RequestsList(100));
		setSR(new RequestsList(100));
		setBKL(new RequestsList(100));
		setBKR(new RequestsList(100));
	}

	
public Requests.RS getLastserved() {
		return lastserved;
	}
	public void setLastserved(Requests.RS lastserved) {
		this.lastserved = lastserved;
	}

// Requests LISTS	
	public RequestsList getBL() {
		return BL;
	}
	public void setBL(RequestsList bL) {
		BL = bL;
	}
	public RequestsList getBR() {
		return BR;
	}
	public void setBR(RequestsList bR) {
		BR = bR;
	}
	public RequestsList getAL() {
		return AL;
	}
	public void setAL(RequestsList aL) {
		AL = aL;
	}
	public RequestsList getAR() {
		return AR;
	}
	public void setAR(RequestsList aR) {
		AR = aR;
	}

// richieste servite continuativamente
// cs = Countinuosly Served
	public int getBLcs() {
		return BLcs;
	}
	public void setBLcs(int bLser) {
		BLcs = bLser;
	}
	public int getBRcs() {
		return BRcs;
	}
	public void setBRcs(int bRser) {
		BRcs = bRser;
	}
	public int getALcs() {
		return ALcs;
	}
	public void setALcs(int aLser) {
		ALcs = aLser;
	}
	public int getARcs() {
		return ARcs;
	}
	public void setARcs(int aRser) {
		ARcs = aRser;
	}

	//incrementi
	public void incBLcs() {
		BLcs++;
	}
	public void incBRcs() {
		BRcs++;
	}
	public void incALcs() {
		ALcs++;
	}
	public void incARcs() {
		ARcs++;
	}
	public void incPLcs() {
		PLcs++;
	}
	public void incPRcs() {
		PRcs++;
	}
	public void incSLcs() {
		PLcs++;
	}
	public void incSRcs() {
		PRcs++;
	}
	public void incBKLcs() {
		PLcs++;
	}
	public void incBKRcs() {
		PRcs++;
	}
	
	

	public RequestsList getPL() {
		return PL;
	}


	public void setPL(RequestsList pL) {
		PL = pL;
	}


	public RequestsList getPR() {
		return PR;
	}


	public void setPR(RequestsList pR) {
		PR = pR;
	}


	public int getPRcs() {
		return PRcs;
	}


	public void setPRcs(int pRcs) {
		PRcs = pRcs;
	}


	public int getPLcs() {
		return PLcs;
	}


	public void setPLcs(int pLcs) {
		PLcs = pLcs;
	}
	
	public RequestsList getBKL() {
		return BKL;
	}


	public void setBKL(RequestsList bKL) {
		BKL = bKL;
	}


	public RequestsList getBKR() {
		return BKR;
	}


	public void setBKR(RequestsList bKR) {
		BKR = bKR;
	}


	public RequestsList getSL() {
		return SL;
	}


	public void setSL(RequestsList sL) {
		SL = sL;
	}


	public RequestsList getSR() {
		return SR;
	}


	public void setSR(RequestsList sR) {
		SR = sR;
	}


	public int getSLcs() {
		return SLcs;
	}


	public void setSLcs(int sLcs) {
		SLcs = sLcs;
	}


	public int getSRcs() {
		return SRcs;
	}


	public void setSRcs(int sRcs) {
		SRcs = sRcs;
	}


	public int getBKLcs() {
		return BKLcs;
	}


	public void setBKLcs(int bKLcs) {
		BKLcs = bKLcs;
	}


	public int getBKRcs() {
		return BKRcs;
	}


	public void setBKRcs(int bKRcs) {
		BKRcs = bKRcs;
	}

}
