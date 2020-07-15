import java.io.*;

public class LoggedInUser { //A class to acess information on the current logged in user using a file

	/* Test code:

	public static void main(String args[]){
		try {
			ExportToFile("Bob", "Carer");
			System.out.println(GetUsername());
			System.out.println(GetUserType());
			DeleteFile();
		}
		catch(IOException e) {
			e.printStackTrace();
		}

	}*/

	public static void ExportToFile(String username, String userType) throws IOException { //Exports information on the currently logged in user to a file
	    File dataFile = new File("loggedin.txt");
	    BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile));
	    writer.write(username + "," + userType);
	    writer.close();
	}

	public static String GetUsername() throws IOException { //Retrieves the username of the currently logged in user
	    File dataFile = new File("loggedin.txt");
	    BufferedReader reader = new BufferedReader(new FileReader(dataFile));

	    String line = reader.readLine();
	    String[] loggedInUser = line.split(",");
	    reader.close();
	    return loggedInUser[0];
	}

	public static String GetUserType() throws IOException { //Retrieves the type of the currently logged in user (carer/caree)
	    File dataFile = new File("loggedin.txt");
	    BufferedReader reader = new BufferedReader(new FileReader(dataFile));

	    String line = reader.readLine();
	    String[] loggedInUser = line.split(",");
	    reader.close();
	    return loggedInUser[1];
	}

	public static void DeleteFile() { //Deletes the file containing the currently logged in user
		File dataFile = new File("loggedin.txt");
		dataFile.delete();
	}

}
