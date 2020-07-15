//Written by Christopher White, Morgan Netherway, Janrey Mosuela

//Extends the Account class.
public class UserAccount extends Account {

	//Set up UserAccount attributes.
	private String gender;
	private String age;
	private String weight;
	private String height;
	private String StartDate;
	private String[] Disabilities;
	private String AuthorizationCode;
	private String phoneNumber;

    //Setup the UserAccount constructor.
    public UserAccount(int ID, String Username, String Password, String FirstName, String LastName, String EmailAddress, String Gender, String Age, String Weight, String Height, String StartDate, String[] Disabilities, String AuthorizationCode, String phoneNumber) {
		super(ID, Username, Password, FirstName, LastName, EmailAddress); //Runs the constructor of Account.
		this.gender = Gender; //Sets gender.
		this.age = Age; //Sets age.
		this.weight = Weight; //Sets weight.
		this.height = Height; //Sets height.
		this.StartDate = StartDate;
		this.Disabilities = Disabilities; //Sets up Disabilities.
		this.AuthorizationCode = AuthorizationCode; //Sets up AuthorizationCode.
		this.phoneNumber = phoneNumber;
    }

    // Fully Empty Constructor
    public UserAccount() {}

    //Empty Constructor
    public UserAccount(Account baseAccount) {
    	super(baseAccount.getID(), baseAccount.getUsername(), baseAccount.getPassword(), baseAccount.getFirstName(), baseAccount.getLastName(), baseAccount.getEmailAddress());
    }

    //getDisabilities returns the Disabilities.
    public String[] getDisabilities() {
    	return Disabilities;
    }

    //getgender returns the gender.
    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
    }

    public String getWeight() {
        return weight;
    }

    public String getHeight() {
        return height;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    //getAuthorizationCode returns the AuthorizationCode.
    public String getAuthorizationCode() {
    	return AuthorizationCode;
    }

    public String getStartDate() {
		return StartDate;
    }

    public void setStartDate(String StartDate) {
	    this.StartDate = StartDate;
    }

   	//setgender sets the gender to the string gender.
	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	// setAuthorizationCode sets the AuthorizationCode to the string AuthorizationCode.
    public void setAuthorizationCode(String AuthorizationCode) {
    	this.AuthorizationCode = AuthorizationCode;
    }

    //setDisabilities sets the Disabilities to the string Disabilities.
    public void setDisabilities(String[] Disabilities) {
    	this.Disabilities = Disabilities;
    }

   //Returns the account as a string.
    public String toString() {
		String STR = super.toString(); //Runs the toString of Account.

		//Set the String list Disabilities to a single string.
		StringBuffer DisabilitiesList = new StringBuffer();
		for (String s : Disabilities) {
		    if (s.equals(Disabilities[0])) {
			   if (s.equals(Disabilities[(Disabilities.length)-1])) {
				   DisabilitiesList.append(s);
			   } else {
				   DisabilitiesList.append(s + ", ");
			   }

		    }
		    else if (s.equals(Disabilities[(Disabilities.length)-1])){
				DisabilitiesList.append(s);
			} else {
			   DisabilitiesList.append(s + ", ");
			}
		}

    	STR = STR + ", " + gender + ", " + age + ", " + weight + ", " + height + ", " + StartDate + ", " + DisabilitiesList  + ", " + AuthorizationCode + ", " + phoneNumber; //Add the created string to the new string with the Disabilities.
    	return STR;
    }
}
