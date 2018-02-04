package Core;

public enum Commands{	

// Connection Stub / Skeleton
			ConnSTOP				("StopConnection"				,	SearchFor.SystemPar,	CommandsType.CONNECTION,	4,	0), 
			ConnTEST				("TestConnection"				,	SearchFor.SystemPar,	CommandsType.CONNECTION,	10,	0),		

// Initialize
			DBExist 				("Check DB Exist"				,	SearchFor.Database,	    CommandsType.CHANGE,		1,	0),
			tableExistSetting		("Check Tb Exist"				,	SearchFor.Setting,		CommandsType.CHANGE,		1,	0),
			tableExistBook			("Check Tb Exist"				,	SearchFor.Book,			CommandsType.CHANGE,		1,	0),
			tableExistLoans			("Check Tb Exist"				,	SearchFor.Prenotation,	CommandsType.CHANGE,		1,	0),
			tableExistPerson		("Check Tb Exist"				,	SearchFor.Account,		CommandsType.CHANGE,		1,	0),
			tableExistBooking		("Check Tb Exist"				,	SearchFor.Booking,		CommandsType.CHANGE,		1,	0),
			
// Book
			BookUPDATE 				("Book Update		"			,	SearchFor.Book,			CommandsType.CHANGE,		2,	0),
			BookADD	        		("Book Add		    "			,	SearchFor.Book,			CommandsType.CHANGE,		3,	0),
			BookDELETE				("Book Delete		"			,	SearchFor.Book,			CommandsType.CHANGE,		4,	0),			
			BookExecuteQuery		("Book Execute Query"			,	SearchFor.Book,			CommandsType.READ,			1,	0),			
			BookREAD 				("Book Read			"			,	SearchFor.Book,			CommandsType.READ,			1,	0), 						
			BookLast 				("Book find last id	"			,	SearchFor.Book,			CommandsType.READ,			1,	0), 	
			BookPopulate			("Book Populate		"			,	SearchFor.Book,			CommandsType.READ,			1,	0), 	
			
			
// Loans - Prestiti			
			LoanListADD				("Loan add 			"			,	SearchFor.Prenotation,	CommandsType.CHANGE	,		4,	0),
			LoanListREMOVE			("Loan del 			"			,	SearchFor.Prenotation,	CommandsType.CHANGE	,		4,	0),
			LoanDELETE				("Loan Delete		"			,	SearchFor.Prenotation,	CommandsType.CHANGE,		4,	0),	
			LoanReturn				("Loan Return		"			,	SearchFor.Prenotation,	CommandsType.CHANGE,		1,	0),	
			LoanRetired				("Loan Retired		"			,	SearchFor.Prenotation,	CommandsType.CHANGE,		1,	0),	
			
			LoanNoticeAvaiable		("Loan notice av	"			,	SearchFor.Prenotation,	CommandsType.CHANGE	,		4,	0),
			LoanNoticeExpiration	("Loan notice ex	"			,	SearchFor.Prenotation,	CommandsType.CHANGE	,		4,	0),
			LoanNew					("Loan New			"			,	SearchFor.Prenotation,	CommandsType.CHANGE	,		4,	0),
			LoanBookGet				("Loan Get			"			,	SearchFor.Prenotation,	CommandsType.CHANGE	,		4,	0),
			LoanBookGiveback		("Loan Give back	"			,	SearchFor.Prenotation,	CommandsType.CHANGE	,		4,	0),
			LoanASK					("Loan Ask for 		"			,	SearchFor.Prenotation,	CommandsType.CHANGE ,		4,	0),
			LoanREAD				("Loan read			"			,	SearchFor.Prenotation,	CommandsType.READ	,		4,	0),
			LoanPopulate			("Loan Populate		"			,	SearchFor.Prenotation,	CommandsType.READ,			1,	0), 	
			LoanExecuteQuery		("Loan Execute Query"			,	SearchFor.Prenotation,	CommandsType.READ,			1,	0),			
			
			
			
			
			//		Loan					("Loan read			"			,	SearchFor.Prenotation,	CommandsType.READ	,		4,	0),
	//		Loan					("Loan read			"			,	SearchFor.Prenotation,	CommandsType.READ	,		4,	0),
			
// Booking - Prenotazioni			
			BookingListADD			("Booking add 			"		,	SearchFor.Booking,		CommandsType.CHANGE	,		4,	0),
			BookingListREMOVE		("Booking del 			"		,	SearchFor.Booking,		CommandsType.CHANGE	,		4,	0),
			BookingDELETE			("Booking del 			"		,	SearchFor.Booking,		CommandsType.CHANGE	,		4,	0),
			
			BookingNoticeAvaiable	("Booking notice av	"			,	SearchFor.Booking,		CommandsType.CHANGE	,		4,	0),
			BookingNoticeExpiration	("Booking notice ex	"			,	SearchFor.Booking,		CommandsType.CHANGE	,		4,	0),
			BookingNew				("Booking New			"		,	SearchFor.Booking,		CommandsType.CHANGE	,		4,	0),
			BookingBookGet			("Booking Get			"		,	SearchFor.Booking,		CommandsType.CHANGE	,		4,	0),
			BookingBookGiveback		("Booking Give back	"			,	SearchFor.Booking,		CommandsType.CHANGE	,		4,	0),			
			BookingREAD				("Booking read			"		,	SearchFor.Booking,		CommandsType.READ	,		4,	0),
			BookingPopulate			("Booking Populate		"		,	SearchFor.Booking,		CommandsType.READ,			1,	0), 	
			BookingExecuteQuery		("Booking Execute Query "		,	SearchFor.Booking,		CommandsType.READ,			1,	0),			

// User
			UserRegistration	("User registration"				,	SearchFor.Account,		CommandsType.CHANGE,		1,	0),
			UserActivation		("User activation"  				,	SearchFor.Account,		CommandsType.CHANGE,		1,	0),
			UserREADloginFIRST	("User Read login FIRST time"		,	SearchFor.Account,		CommandsType.CHANGE,		1,	0),	//change per l'incremento del numero di tentativi di login
			UserREADaccountMod	("User Read account mod		"		,	SearchFor.Account,		CommandsType.CHANGE,		1,	0),
			UserUPDATE 			("User Update		"				,	SearchFor.Account,		CommandsType.CHANGE,		2,	0),
			UserDELETE			("User Delete		"				,	SearchFor.Account,		CommandsType.CHANGE,		3,	0),				
			UserPasswordRecovery("User Password Recovery"			,	SearchFor.Account,		CommandsType.CHANGE,		3,	0),
			UserPasswordRemovetemp("User Password Remove temporany"	,	SearchFor.Account,		CommandsType.CHANGE,		3,	0),
			
			UserLOGIN 			("User Login"						,	SearchFor.Account,		CommandsType.READ,			1,	0), 
			UserLOGOUT 			("User Logout"						,	SearchFor.Account,		CommandsType.READ,			1,	0),
			UserREAD 			("User Read			"				,	SearchFor.Account,		CommandsType.READ,			1,	0),
			UserREADbyEmail		("User Read by email"				,	SearchFor.Account,		CommandsType.READ,			1,	0), 
			UserREADbyEmailAcc	("UR panel Account"					,	SearchFor.Account,		CommandsType.READ,			1,	0),
			UserREADbyEmailMod	("UR panel Modify"					,	SearchFor.Account,		CommandsType.READ,			1,	0), 
			UserREADlogin		("User Read login"					,	SearchFor.Account,		CommandsType.READ,			1,	0), 
			UserREADcheckEmail	("User Read check email"			,	SearchFor.Account,		CommandsType.READ,			1,	0),
			UserREADcheckCF		("User Read check cod fiscale"		,	SearchFor.Account,		CommandsType.READ,			1,	0),			
//	
			GetDataForTables	("Get data from db for Table"		,	SearchFor.Book,			CommandsType.READ,			1,	0);			
	
	
	
	
	
	//campi
			public 	String 			Den;
			private SearchFor 		Target;
			private CommandsType 	CmdType;
			public 	int 			Priority;
			public 	int 			Records;					
	//costruttori
			private Commands (	String 	Den, SearchFor Tar,CommandsType CType,int pri,int rec){
			this.Den = 					Den;			
			this.setTarget(Tar);			
			this.setCommandsType(		CType);
			this.Priority = 			pri;
			this.Records=				rec;
			}
	// SET GET Methods		
			public CommandsType 		getCommandType() {
				return CmdType;
			}
			public void 				setCommandsType(CommandsType commandType) {
				CmdType = commandType;
			}
			public SearchFor 			getTarget() {
				return Target;
			}
			public void 				setTarget(SearchFor target) {
				Target = target;
			}
	};	
