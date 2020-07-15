//Written by Christopher White,

//Extends the Account class.
public class CarerAccount extends Account{

	//Set up CarerAccount attributes.
	private int[] Users;

	//Setup the CarerAccount constructor.
	public CarerAccount(int ID, String Username, String Password, String FirstName, String LastName, String EmailAddress, int[] Users) {
		super(ID, Username, Password, FirstName, LastName, EmailAddress); //Runs the constructor of Account.
		this.Users = Users; //Sets up Users.
	}

	public CarerAccount(Account baseAccount) {
       super(baseAccount.getID(), baseAccount.getUsername(), baseAccount.getPassword(), baseAccount.getFirstName(), baseAccount.getLastName(), baseAccount.getEmailAddress());
   }

	public CarerAccount() {}

	//getUsers returns the Disabilities.
	public int[] getUsers() {
		return Users;
	}

	//setUsers sets the Users to the int Users.
	public void setUsers(int[] Users) {
		this.Users = Users;
	}

	//Returns the account as a string.
	public String toString() {
		String STR = super.toString(); //Runs the toString of Account.
		//Set the int list Users to a single string.
		StringBuffer UsersList = new StringBuffer();
		for (int s : Users) {
			if (s == (Users[0])) {
				if(s == (Users[(Users.length)-1])) {
					UsersList.append(s);
				} else {
					UsersList.append(s + ", ");
				}
			} else if(s == (Users[(Users.length)-1])) {
				UsersList.append(s);
			} else {
				UsersList.append(s + ", ");
			}
		}

		STR = STR + ", " + UsersList; //Add the created string to the new string with the Users List.
		return STR;
	}
}
