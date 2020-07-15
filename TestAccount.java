//Test For Account.
import java.io.*;

public class TestAccount {

    public static void main( String args[] ) {
	    //Create Account.
	    System.out.println();
	    System.out.println("Accounts");
	    Account A = new Account(2, "FirstName", "LastName", "EmailAddress", "Username", "Password");
	    System.out.println(A);

	    System.out.println(A.CheckLogin("Username", "Password"));
	    System.out.println(A.CheckLogin("Usernam", "Password"));
	    System.out.println(A.CheckLogin("Username", "Passwor"));

	    //New String List.
	    String[] B = new String[]{"A","B"};

	    //Create UserAccount.
	    System.out.println();
	    System.out.println("UserAccount");
        UserAccount C = new UserAccount(1, "Username", "Password", "FirstName", "LastName", "EmailAddress", "Gender", "Age", "Weight", "Height", "StartDate", B, "Code", "phone");
	    System.out.println(C);

	    System.out.println(C.getFirstName());

	    System.out.println();
	    System.out.println("UserAccounts");

	    //UserAccounts
	    UserAccounts Z = new UserAccounts();
	    String[] Dis = new String[]{"A","B"};

	    System.out.println(Z.add(0, "Username1", "Password", "Test", "LastName", "EmailAddress", "Gender", "Age", "Weight", "Height", "2016-01-29", Dis, "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyMkQ5SjMiLCJzdWIiOiIzVlNMOVIiLCJpc3MiOiJGaXRiaXQiLCJ0eXAiOiJhY2Nlc3NfdG9rZW4iLCJzY29wZXMiOiJyYWN0IHJociByc2xlIiwiZXhwIjoxNTUxOTY5NjgzLCJpYXQiOjE1NDk5MTE1ODB9.rEA0V5vLOwS-Eam_6aRbw5G62Cg1vPf5Pr41KASyi38", "phone"));
	    System.out.println(Z.add(1, "Username", "Password", "FirstName", "LastName", "EmailAddress", "Gender", "Age", "Weight", "Height", "StartDate", Dis, "Code", "phone"));
	    System.out.println(Z.add(2, "Username2", "Password", "FirstName", "LastName", "EmailAddress", "Gender", "Age", "Weight", "Height", "StartDate", Dis, "Code", "phone"));
	    //Z.delete(1);
	    System.out.println(Z);
	    System.out.println("Length " + Z.length());

	    System.out.println(Z.UserNamePresent("Username"));
	    System.out.println(Z.getAccountWithID(2));
	    System.out.println(Z);

	    Account X = Z.getAccountWithID(Z.UserNamePresent("Username"));

	    if (X != null) {
		    System.out.println(X.CheckPassword("Password"));
	    } else {
		    System.out.println(false);
	    }

	    if (X != null) {
		    System.out.println(X.CheckPassword("P"));
	    } else {
		    System.out.println(false);
	    }

	    //New int List.
	    int[] M = new int[]{0,1};

	    //Create CarerAccount.
	    System.out.println();
	    System.out.println("CarerAccount");
        CarerAccount G = new CarerAccount(3, "Username", "Password", "FirstName", "LastName", "EmailAddress", M);
	    System.out.println(G);
	    System.out.println(G.getPassword());

	    //CarerAccounts
	    System.out.println();
	    System.out.println("CarerAccounts");

	    CarerAccounts Y = new CarerAccounts();
	    //int[] Acc = new int[]{0,1};
	    int[] Acc = new int[]{0};

	    System.out.println(Y.add(11, "Username1", "Password", "1FirstName", "LastName", "EmailAddress", Acc));
	    //System.out.println(Y.add("Username2", "Password", "2FirstName", "LastName", "EmailAddress", Acc));
	    //Y.delete(1);
	    System.out.println(Y);
	    System.out.println("Length " + Y.length());

	    System.out.println(Y.UserNamePresent("Username"));
	    System.out.println(Y.getAccountWithID(2));

	    Account Q = Y.getAccountWithID(Z.UserNamePresent("Username"));
	    System.out.println(Q.getPassword());

	    if (Q != null) {
		    System.out.println(Q.CheckPassword("Password"));
	    }
	    else{
		    System.out.println(false);
	    }

	    if (Q != null) {
		    System.out.println(Q.CheckPassword("P"));
	    } else {
		    System.out.println(false);
	    }

	    //Other Files!
	    Z.exportFile();
	    UserAccounts test = new UserAccounts();
	    test.importFile();
	    System.out.println(String.valueOf(test.length()));

	    System.out.println("Test");
	    System.out.println(test);
	    test.exportFile();

	    Y.exportFile();
	    CarerAccounts test2 = new CarerAccounts();
	    test2.importFile();
	    System.out.println(String.valueOf(test2.length()));

	    System.out.println("Test");
	    System.out.println(test2);
	    //test2.exportFile();

	    try {
		   LoggedInUser.ExportToFile("Username1", "Carer");
		} catch(IOException e) {
			e.printStackTrace();
		}
   }
}
