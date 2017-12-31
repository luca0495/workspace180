package database;


	public class LoadUser {

	    private int id;
	    private String name;
	    private String surname;
	    private String email;
	    private String cod_fis;
	    private String pass_temp;
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

	    public void setId(int i) {
	        this.id = i;
	    }

	    public String getName(String p1) {
	        return name;
	    }

	    public String setName(String name) {
	        return this.name = name;
	    }

	    public String getSurname() {
	        return surname;
	    }

	    public String setSurname(String surname) {
	        return this.surname = surname;
	    }
	    public String getCod_fis() {
			return cod_fis;
		}

		public void setCod_fis(String cod_fis) {
			this.cod_fis = cod_fis;
		}
		public String getPass_temp() {
			return pass_temp;
		}

		public void setPass_temp(String pass_temp) {
			this.pass_temp = pass_temp;
		}
	}


