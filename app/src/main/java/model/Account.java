package model;

/**
 * Created by Asif on 3/14/2018.
 */

public class Account {

    String userid;

    public String getUserid() {
        return userid;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public String getAccounttype() {
        return accounttype;
    }

    public boolean isAccountstatus() {
        return accountstatus;
    }

    public Account(String userid, String firstname, String lastname, String emailaddress, String accounttype, boolean accountstatus) {

        this.userid = userid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.emailaddress = emailaddress;
        this.accounttype = accounttype;
        this.accountstatus = accountstatus;
    }

    String firstname,lastname,emailaddress,accounttype;
    boolean accountstatus;

    public Account() {
    }

}
