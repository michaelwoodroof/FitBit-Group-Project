import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javafx.application.Application;

public class Alerts {
	//References:
	//https://stackoverflow.com/questions/1220975/calling-a-function-every-10-minutes
	//https://dzone.com/articles/getting-current-date-time-in-java

	private String AuthorizationCode;
	private int UserID;

	//BedTime Information.
	CheckBedTimes CBT = new CheckBedTimes();

	//Sets up timer.
    long delay = 900000; // delay in milliseconds 900000
    Task task = new Task();
    Timer timer = new Timer("GetData");

    //To start the timer.
    public void start() {

		//Find out the user type and then start up if logged in.
		try {
			//Initialize all the acount data.
			CarerAccounts CA = new CarerAccounts();
			UserAccounts UA = new UserAccounts();
			UserAccount CurrentUser;
			CarerAccount CurrentCarer;

			String Type = LoggedInUser.GetUserType(); //Get the logged in user Type.

			//Depending on the type.
			if (Type.equals("User")) {
				//Get the user information.
				String Username = LoggedInUser.GetUsername();
				UA.importFile();
				CurrentUser = UA.getAccountWithUsername(Username);

				//Set the user information.
				AuthorizationCode = CurrentUser.getAuthorizationCode();
				UserID = CurrentUser.getID();
				String StartDate = CurrentUser.getStartDate();

				Startup(StartDate, AuthorizationCode); //Run startup for the user.
			} else if (Type.equals("Carer")) {
				//Get the carer information.
				String Username = LoggedInUser.GetUsername();
				CA.importFile();
				CurrentCarer = CA.getAccountWithUsername(Username);

				//Get the list of Users.
				int[] Users = CurrentCarer.getUsers();
				UA.importFile();

				//For every user run the startup. if they exist.
				for(int i = 0; i < Users.length; i++){
					CurrentUser = UA.getAccountWithID(Users[i]);
					if(CurrentUser != null){Startup(CurrentUser.getStartDate(), CurrentUser.getAuthorizationCode());}
				}
			} else {
				timer.cancel(); //Stop the timer if there is no one logged in.
			}
		}
		//If the file does not exist is stops the timer.
    	catch(IOException e) {timer.cancel();}

        timer.cancel();
        timer = new Timer("GetData");
        Date executionDate = new Date();
        timer.scheduleAtFixedRate(task, executionDate, delay);
    }

	//To Stop the timer.
    public void Startup(String StartDate, String AuthorizationCode) {
    	//Startup runs the analyse data starup methords.
    	HRStart(StartDate, AuthorizationCode);
    	StepsDayStart(StartDate, AuthorizationCode);
    	StepsWeekStart(StartDate, AuthorizationCode);
    	StepsMonthStart(StartDate, AuthorizationCode);
    }

	//To Stop the timer.
    public void Stop() {
    	timer.cancel();
    }

    //What runs when the timer needs it to.
    private class Task extends TimerTask {
		//Every time the timer needs to run.
        public void run() {
			//Gets Todays date.
			DateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date SystemDate = new Date();
			String Today = DateFormat.format(SystemDate);

			//Check user is logged in
			try {
				//Initialize all the acount data.
				CarerAccounts CA = new CarerAccounts();
				UserAccounts UA = new UserAccounts();
				UserAccount CurrentUser;
				CarerAccount CurrentCarer;

				String Type = LoggedInUser.GetUserType();

				//Depending on the type.
				if(Type.equals("User")){
					String Username = LoggedInUser.GetUsername();
					UA.importFile();

					//Check the user is the same user as when started.
					if (UA.getAccountWithUsername(Username).getAuthorizationCode().equals(AuthorizationCode)) {
						//Run the analyse data check.
						HRCheck(Today, AuthorizationCode, "");
						StepsDayCheck(Today, AuthorizationCode, "");
						StepsWeekCheck(Today, AuthorizationCode, "");
						StepsMonthCheck(Today, AuthorizationCode, "");

						//BedTimes.
						BedTimes BT = new BedTimes();
						BT.importFile();
						ArrayList<String> UserBedTimes = BT.GetBedTime(UserID);

						//Checks the time and alerts the user. For Bedtime.
						for (int i = 0; i < UserBedTimes.size(); ++i) {
							CBT.CheckTime(UserBedTimes.get(i), "BedTime");
						}

						//Checks the time and alerts the user. For Wake up.
						ArrayList<String> UserWakeUpTimes = BT.GetWakeUpTime(UserID);
						for (int i = 0; i < UserWakeUpTimes.size(); ++i) {
							CBT.CheckTime(UserWakeUpTimes.get(i), "WakeUp");
						}
					}
					//If a diffrent user is logged in we stop the timer.
					else {
						timer.cancel();
					}
				}
				else if(Type.equals("Carer")){
					//If the user is a carer.
					String Username = LoggedInUser.GetUsername();
					CA.importFile();
					CurrentCarer = CA.getAccountWithUsername(Username);
					int[] Users = CurrentCarer.getUsers();
					UA.importFile();

					//For each user if they exist run the check.
					for (int i = 0; i < Users.length; i++) {
						CurrentUser = UA.getAccountWithID(Users[i]);
						if(CurrentUser != null){
							String FullName = CurrentUser.getFirstName() + " " + CurrentUser.getLastName();

							HRCheck(Today, CurrentUser.getAuthorizationCode(), FullName);
							StepsDayCheck(Today, CurrentUser.getAuthorizationCode(), FullName);
							StepsWeekCheck(Today, CurrentUser.getAuthorizationCode(), FullName);
							StepsMonthCheck(Today, CurrentUser.getAuthorizationCode(), FullName);
						}
					}
				}
				//If the account is not these types stop the timer.
				else {
					timer.cancel();
				}
			}
			//If the file does not exist is stops the timer.
			catch(IOException e) {timer.cancel();}
        }
    }

        //Startup for HR.
	public static void HRStart(String Date, String AuthorizationCode){
		ArrayList<Double> fitnessData = new ArrayList<>();
		int[] rawIntegerData;
		rawIntegerData = FitbitData.getHRDataMin(Date, AuthorizationCode);

		if (rawIntegerData.length != 0) {
			fitnessData = castingData(rawIntegerData);
			AnalyseData.calculateGaussianAverages(fitnessData, "Heartrate");
		}
	}

	//Check for HR.
	public static void HRCheck(String Date, String AuthorizationCode, String Username){
		ArrayList<Double> fitnessData = new ArrayList<>();
		int[] rawIntegerData;
		rawIntegerData = FitbitData.getHRDataMin(Date, AuthorizationCode);

		if (rawIntegerData.length != 0) {
			fitnessData = castingData(rawIntegerData);
			AnalyseData.checkIncomingData(fitnessData, "Heartrate", Date, Username);
		}
	}

	//Startup for Steps Day.
	public static void StepsDayStart(String Date, String AuthorizationCode){
		ArrayList<Double> fitnessData = new ArrayList<>();
		int[] rawIntegerData;
		rawIntegerData = FitbitData.getStepsDataDay(Date, AuthorizationCode);

		if (rawIntegerData.length != 0) {
			fitnessData = castingData(rawIntegerData);
			AnalyseData.calculateGaussianAverages(fitnessData, "Steps Day");
		}
	}

	//Check for Steps Day.
	public static void StepsDayCheck(String Date, String AuthorizationCode, String Username){
		ArrayList<Double> fitnessData = new ArrayList<>();
		int[] rawIntegerData;
		rawIntegerData = FitbitData.getStepsDataDay(Date, AuthorizationCode);

		if (rawIntegerData.length != 0) {
			fitnessData = castingData(rawIntegerData);
			AnalyseData.checkIncomingData(fitnessData, "Steps Day", Date, Username);
		}
	}

	//Startup for Steps Week.
	public static void StepsWeekStart(String Date, String AuthorizationCode){
		ArrayList<Double> fitnessData = new ArrayList<>();
		int[] rawIntegerData;
		rawIntegerData = FitbitData.getStepsDataWeek(Date, AuthorizationCode);

		if (rawIntegerData.length != 0) {
			fitnessData = castingData(rawIntegerData);
			AnalyseData.calculateGaussianAverages(fitnessData, "Steps Week");
		}
	}

	//Check for Steps Week.
	public static void StepsWeekCheck(String Date, String AuthorizationCode, String Username){
		ArrayList<Double> fitnessData = new ArrayList<>();
		int[] rawIntegerData;
		rawIntegerData = FitbitData.getStepsDataWeek(Date, AuthorizationCode);

		if(rawIntegerData.length != 0) {
			fitnessData = castingData(rawIntegerData);
			AnalyseData.checkIncomingData(fitnessData, "Steps Week", Date, Username);
		}
	}

	//Startup for Steps Month.
	public static void StepsMonthStart(String Date, String AuthorizationCode){
		ArrayList<Double> fitnessData = new ArrayList<>();
		int[] rawIntegerData;
		rawIntegerData = FitbitData.getStepsDataMonth(Date, AuthorizationCode);

		if(rawIntegerData.length != 0) {
			fitnessData = castingData(rawIntegerData);
			AnalyseData.calculateGaussianAverages(fitnessData, "Steps Month");
		}
	}

	//Check for Steps Week.
	public static void StepsMonthCheck(String Date, String AuthorizationCode, String Username){
		ArrayList<Double> fitnessData = new ArrayList<>();
		int[] rawIntegerData;
		rawIntegerData = FitbitData.getStepsDataMonth(Date, AuthorizationCode);

		if (rawIntegerData.length != 0) {
			fitnessData = castingData(rawIntegerData);
			AnalyseData.checkIncomingData(fitnessData, "Steps Month", Date, Username);
		}
	}

	//Converts data to Array List.
	private static ArrayList<Double> castingData(int[] rawIntegerData){ //Method to convert data into ArrayList<Double>
		Double[] rawData = new Double[rawIntegerData.length];
		for (int i = 0; i < rawIntegerData.length; i++) {
    		rawData[i] = Double.valueOf(rawIntegerData[i]);
		}
		ArrayList<Double> fitnessData = new ArrayList<>();
		fitnessData.addAll(Arrays.asList(rawData));
		return fitnessData;
	}
}
