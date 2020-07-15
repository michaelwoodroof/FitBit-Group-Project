//Written by Christopher White

// Adapted to Store ID to File by Michael Woodroof
import java.util.*;
import java.io.*;

public class CarerAccounts {
	//Set up CarerAccounts attributes.
	private static Vector<CarerAccount> CarerAccounts;

	//Setup the CarerAccounts constructor.
    public CarerAccounts() {
        CarerAccounts = new Vector<CarerAccount>();
    }

	//add adds a new CarerAccount to the CarerAccounts list. It increments ID by 1.
    public boolean add(int ID, String Username, String Password, String FirstName, String LastName, String EmailAddress, int[] Users) {
		for (int i = 0; i < CarerAccounts.size(); ++i) {;
            if (CarerAccounts.get(i).getUsername().equals(Username)) {
				return false;
			}
		}

		CarerAccounts.add(new CarerAccount(ID, Username, Password, FirstName, LastName, EmailAddress, Users));
		return true;
    }

    // Remove if this is Invalid way of Handling CarerAccounts
    public void add(CarerAccount acc) {
        CarerAccounts.add(acc);
    }

	//delete removes the question with the id search.
	public void delete(int search) {
		importFile();
		for (int i = 0; i < CarerAccounts.size(); ++i) {
            if (CarerAccounts.get(i).getID() == search) {
                CarerAccounts.remove(i);
			}
		}
		exportFile();
	}

	//when a user account is deleted, the association with a carer is removed
	public void deleteUser(int search) {
		if (CarerAccounts.isEmpty()) {
			importFile();
		}
		int account = 0;
		int[] id;
		int count = 0;
		int[] newUsers = null;
		for (int i = 0; i < CarerAccounts.size(); i++) {
			id = CarerAccounts.get(i).getUsers();
			for (int j = 0; j < id.length; j++) {
				if (id[j] == search) {
					account = i;
					newUsers = new int[id.length - 1];
					for (int k = 0; k < id.length; k++) {
						if (id[k] != search) {
							newUsers[count] = id[k];
							count++;
						}
					}
					break;
				}
			}
		}
		CarerAccounts.get(account).setUsers(newUsers);
		exportFile();
	}

	//length returns the size of the CarerAccounts list.
	public int length() {
		return CarerAccounts.size();
	}

	//returns the list of accounts for logging in
	public Vector<CarerAccount> getUsers(){
		return CarerAccounts;
	}

	//UserNamePresent checks if the UserName exists.
	public int UserNamePresent(String search) {
		int item = -1;
		for (int i = 0; i < CarerAccounts.size(); ++i) {
            if (CarerAccounts.get(i).getUsername().equals(search)){
                item = CarerAccounts.get(i).getID();
			}
		}
		return item;
	}

	public CarerAccount Search(String search) {
		for (int i = 0; i < CarerAccounts.size(); i++) {
			if (CarerAccounts.get(i).getUsername().equals(search)){
                return CarerAccounts.get(i);
			}
		}
		return null;
	}

	//getAccountWithID returns the CarerAccount of with the id search.
	public CarerAccount getAccountWithID(int search){
		CarerAccount item = null;

		int count = 0;
        for (int i = 0; i < CarerAccounts.size(); ++i) {
            if (CarerAccounts.get(i).getID() == search){
				count++;
                item = CarerAccounts.get(i);
			}
		}
		return item;
	}

	//getAccountWithID returns the CarerAccount of with the username search.
	public CarerAccount getAccountWithUsername(String search){
		CarerAccount item = null;

		int count = 0;
        for (int i = 0; i < CarerAccounts.size(); ++i) {
            if (CarerAccounts.get(i).getUsername().equals(search)){
				count++;
                item = CarerAccounts.get(i);
			}
		}
		return item;
	}

	// Added Method to Ensure no Duplicate Accounts
	public Boolean isUnique(String search) {
		for (int i = 0; i < CarerAccounts.size(); i++) {
            if (CarerAccounts.get(i).getUsername().equals(search)){
                return false;
			}
		}
		return true;
	}

	// import a text file into the program (written by Janrey Mosuela, adapted by Morgan Netherway)
	public void importFile() {
    	try {
	        File account_file = new File("accounts/CarerAccounts.txt");

	        FileReader file_reader = new FileReader(account_file);
	        BufferedReader buff_reader = new BufferedReader(file_reader);

	        String row;
	        while ((row = buff_reader.readLine()) != null) {
	            String[] account = row.split(","); //implementing comma seperated value (CSV)
	            String[] users = account[6].split("-");
	            int[] usersNew = new int[users.length];
	            for (int i = 0; i < users.length; i++) {
	            	usersNew[i] = Integer.parseInt(users[i].trim());
	            }
	            this.add(Integer.parseInt(account[0]), account[1], account[2], account[3], account[4], account[5], usersNew);
	        }
	        buff_reader.close();
        } catch (IOException e) {
            System.out.println("Unable to read text file this time.");
    	}
	}

	//exporting the users into a text file (Janrey Mosuela, adapted by Morgan Netherway)
	public void exportFile() {
		try {
			FileWriter file_write = new FileWriter("accounts/CarerAccounts.txt", false);
			String s = System.getProperty("line.separator");
			for (int i=0; i < CarerAccounts.size();i++) {
				String Users = new String();
				for (int user: CarerAccounts.get(i).getUsers()) {
					Users += Integer.toString(user) + "-";
				}
				String individual_user = CarerAccounts.get(i).getID() + "," + CarerAccounts.get(i).getUsername() + "," + CarerAccounts.get(i).getPassword() + "," + CarerAccounts.get(i).getFirstName() + "," + CarerAccounts.get(i).getLastName() + "," + CarerAccounts.get(i).getEmailAddress() + "," + Users;
				file_write.write(individual_user + s);
			}
			file_write.close();

		} catch (IOException e) {
			System.out.println("Unable to export file.");
		}
	}

	//Returns the CarerAccounts as a string.
    public String toString() {
        StringBuffer temp = new StringBuffer();
        for (int i = 0; i < CarerAccounts.size(); ++i) {
			if (i != CarerAccounts.size() - 1) {
				temp.append(CarerAccounts.get(i).toString() + "\n" );
			} else {
				temp.append(CarerAccounts.get(i).toString());
			}
        }
        return temp.toString();
    }
}
