// Written by Christopher White, Janrey Mosuela, Morgan Netherway
// Formatting Changes and Correction to Coding Standards have been made
// For Reference height and weight will be stored in Metric format

public class Account {
    //Set up Account attributes.
    private int ID;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String emailAddress;


   //Setup the Account constructor.
    public Account(int ID, String username, String password, String firstName, String lastName, String emailAddress) {
        this.ID = ID; //Sets ID.
        this.firstName = firstName; //Sets firstName.
        this.lastName = lastName; //Sets lastName.
        this.emailAddress = emailAddress; //Sets emailAddress.
        this.username = username; //Sets username.
        this.password = password; //Sets password.
    }

    // Empty Constructor
    public Account() {}

   //Check Login
    public boolean CheckLogin(String username, String password) {
        if(this.username.equals(username)){
            if(this.password.equals(password)){
                return true;
            }
        }
       return false;
    }

   //Check username
    public boolean CheckUserName(String username) {
        if(this.username.equals(username)){
            return true;
        }
        return false;
    }

    //Check password
    public boolean CheckPassword(String password) {
        if(this.password.equals(password)){
            return true;
        }
        return false;
    }

    //getID returns the ID.
    public int getID() {
        return ID;
    }

    //getfirstName returns the firstName.
    public String getFirstName() {
        return firstName;
    }

    //getfirstName returns the lastName.
    public String getLastName() {
        return lastName;
    }

    //getemailAddress returns the emailAddress.
    public String getEmailAddress() {
        return emailAddress;
    }

    //getusername returns the username.
    public String getUsername() {
        return username;
    }

    //getpassword returns the password.
    public String getPassword() {
        return password;
    }

    //setID sets the ID to the integer ID.
    public void setID(String ID) {
        this.ID = Integer.valueOf(ID);
    }

    //setfirstName sets the firstName to the string firstName.
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    //setlastName sets the lastName to the string lastName.
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    //setemailAddress sets the emailAddress to the string emailAddress.
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

   //setusername sets the username to the string username.
    public void setUsername(String username) {
        this.username = username;
    }

    //setpassword sets the password to the string password.
    public void setPassword(String password) {
        this.password = password;
    }

   //Returns the account as a string.
    public String toString() {
       String s = ID + ", " + firstName + ", " + lastName + ", " + emailAddress + ", " + username + ", " + password; //Sets account to a String.
       return s;
    }
}
