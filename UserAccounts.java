//Written by Christopher White, Morgan Netherway
import java.util.*;
import java.io.*;

public class UserAccounts {
	//Set up UserAccounts attributes.
	private static Vector<UserAccount> UserAccounts;

	//Setup the UserAccounts constructor.
    public UserAccounts() {
        UserAccounts = new Vector<UserAccount>();
    }

	//add adds a new Account to the UserAccounts list. It increments ID by 1.
    public boolean add(int ID, String Username, String Password, String FirstName, String LastName, String EmailAddress, String Gender, String Age, String Weight, String Height, String StartDate, String[] Disabilities, String AuthorizationCode, String phoneNumber) {
		for (int i = 0; i < UserAccounts.size(); ++i) {;
            if (UserAccounts.get(i).getUsername().equals(Username)){
				return false;
			}
		}

		UserAccounts.add(new UserAccount(ID, Username, Password, FirstName, LastName, EmailAddress, Gender, Age, Weight, Height, StartDate, Disabilities, AuthorizationCode, phoneNumber));
		return true;
    }

    public void add(UserAccount acc) {
        UserAccounts.add(acc);
    }

	//returns list of accounts for logging in.
	public Vector<UserAccount> getUsers(){
		return UserAccounts;
	}

	//delete removes the UserAccount with the id search.
	public void delete(int search) {
		importFile();
		CarerAccounts account = new CarerAccounts();
		account.importFile();

		for (int i = 0; i < UserAccounts.size(); ++i) {
            if (UserAccounts.get(i).getID() == search){
                UserAccounts.remove(i);
				account.deleteUser(search);
			}
		}
		account.exportFile();
		exportFile();
	}

	//length returns the size of the UserAccounts list.
	public int length() {
		return UserAccounts.size();
	}

	//UserNamePresent checks if the UserName exists.
	public int UserNamePresent(String search) {
		int item = -1;
		for (int i = 0; i < UserAccounts.size(); ++i) {
            if (UserAccounts.get(i).getUsername().equals(search)){
                item = UserAccounts.get(i).getID();
			}
		}
		return item;
	}

	//getAccountWithID returns the UserAccount of with the id search.
	public UserAccount getAccountWithID(int search){
		UserAccount item = null;

		int count = 0;
        for (int i = 0; i < UserAccounts.size(); ++i) {
            if (UserAccounts.get(i).getID() == search){
				count++;
                item = UserAccounts.get(i);
			}
		}
		return item;
	}

	//getAccountWithID returns the UserAccount of with the username search.
	public UserAccount getAccountWithUsername(String search){
		UserAccount item = null;

		int count = 0;
        for (int i = 0; i < UserAccounts.size(); ++i) {
            if (UserAccounts.get(i).getUsername().equals(search)){
				count++;
                item = UserAccounts.get(i);
			}
		}
		return item;
	}

	// Added Method to Ensure no Duplicate Accounts
	public Boolean isUnique(String search) {
		for (int i = 0; i < UserAccounts.size(); i++) {
            if (UserAccounts.get(i).getUsername().equals(search)){
                return false;
			}
		}
		return true;
	}

	// import a text file into the program (Janrey Mosuela)
	public void importFile() {
    try {
        File account_file = new File("accounts/userAccounts.txt");

        FileReader file_reader = new FileReader(account_file);
        BufferedReader buff_reader = new BufferedReader(file_reader);

        String row;
        while ((row = buff_reader.readLine()) != null) {
            String[] account = row.split(","); //implementing comma seperated value (CSV)
            String[] disability_list = account[11].split("-");
            this.add(Integer.parseInt(account[0]), account[1], account[2], account[3], account[4], account[5], account[6], account[7], account[8], account[9], account[10], disability_list, account[12], account[13]);
            }
        buff_reader.close();
        } catch (IOException e) {
            System.out.println("Unable to read text file this time.");
    	}
	}

	public void exportFile() {
		try {
			FileWriter file_write = new FileWriter("accounts/userAccounts.txt", false);
			String s = System.getProperty("line.separator");
			for (int i=0; i < UserAccounts.size();i++) {
				String disability_format = new String();
				for (String disability: UserAccounts.get(i).getDisabilities()) {
					disability_format = disability_format + disability.toString() + "-";
				}
				disability_format =  disability_format.substring(0, disability_format.length() - 1);
				String individual_user = UserAccounts.get(i).getID() + "," + UserAccounts.get(i).getUsername() + "," + UserAccounts.get(i).getPassword() + "," + UserAccounts.get(i).getFirstName() + "," + UserAccounts.get(i).getLastName() + "," + UserAccounts.get(i).getEmailAddress() + "," + UserAccounts.get(i).getGender() + "," + UserAccounts.get(i).getAge() + "," + UserAccounts.get(i).getWeight() + "," + UserAccounts.get(i).getHeight() + "," + UserAccounts.get(i).getStartDate() + "," + disability_format + "," + UserAccounts.get(i).getAuthorizationCode() + "," + UserAccounts.get(i).getPhoneNumber();
				file_write.write(individual_user + s);
			}
			file_write.close();

		} catch (IOException e) {
			System.out.println("Unable to export file.");
		}
	}

	//Returns the UserAccounts as a string.
    public String toString() {
        StringBuffer temp = new StringBuffer();
        for (int i = 0; i < UserAccounts.size(); ++i) {
			if(i != UserAccounts.size()-1){
				temp.append(UserAccounts.get(i).toString() + "\n" );
			}
			else{
				temp.append(UserAccounts.get(i).toString());
			}
        }
        return temp.toString();
    }
}
