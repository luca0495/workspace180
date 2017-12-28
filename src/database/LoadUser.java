package database;


	public class LoadUser {

	    private int id;
	    private String name;
	    private String surname;
	    private String email;
	    private String inq;
	    private String pass;
	    private String ntel;
	    private String type_user;
	    

	    public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getInq() {
			return inq;
		}

		public void setInq(String inq) {
			this.inq = inq;
		}

		public String getPass() {
			return pass;
		}

		public void setPass(String pass) {
			this.pass = pass;
		}

		public String getNtel() {
			return ntel;
		}

		public void setNtel(String ntel) {
			this.ntel = ntel;
		}

		public String getType_user() {
			return type_user;
		}

		public void setType_user(String type_user) {
			this.type_user = type_user;
		}

		public int getId() {
			return id;
		}

		public int getID() {
	        return id;
	    }

	    public void setId(int Id) {
	        this.id = Id;
	    }

	    public String getName() {
	        return name;
	    }

	    public String setName(String name) {
	        return this.name = name;
	    }

	    public String getSurname() {
	        return surname;
	    }

	    public void setSurname(String surname) {
	        this.surname = surname;
	    }

	}


