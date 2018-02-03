package ProvaEmail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import connections.Client;

public class SMTPAuthenticator extends Authenticator {
    private String userName;
    private String password;
    public static Client me;
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public static Client getMe() {
		return me;
	}

	public static void setMe(Client me) {
		SMTPAuthenticator.me = me;
	}

	public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public SMTPAuthenticator(String userName,String password){
        this.userName=userName;
        this.password=password;
    }

    public PasswordAuthentication getPasswordAuthentication()
    {
        return new PasswordAuthentication(me.getUSERNAME(), me.getPASSWORD());
    }

}
