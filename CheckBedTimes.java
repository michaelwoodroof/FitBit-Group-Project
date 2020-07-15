//Written by Christopher White,
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class CheckBedTimes {

	public static void CheckTime(String Time, String Type) {

		String[] Times = GetTimes(Time);

		Date SystemDate = new Date(); //Get today date
		DateFormat DateFormat = new SimpleDateFormat("HHmm"); //Set Dateformat to HHmm
		String TodayString = DateFormat.format(SystemDate); //Set TodayString to the current time.

		//Make Dates.
		Date StartTimeDate;
		Date EndTimeDate;
		Date TodayDate;

		try {
			StartTimeDate = DateFormat.parse(Times[0]);
			EndTimeDate = DateFormat.parse(Times[1]);
			TodayDate = DateFormat.parse(TodayString);

			if (TodayDate.before(EndTimeDate) && TodayDate.after(StartTimeDate)) {
				if (Type.equals("BedTime")) {
					System.out.println("BedTime Reminder For " + Time); //Alert User!
					Notifications.Display("BedTime Reminder For " + Time, "BedTime", "Reminder");
				} else if(Type.equals("WakeUp")) {
					System.out.println("Wake Up Reminder For " + Time); //Alert User!
					Notifications.Display("Wake Up Reminder For " + Time, "Wake Up", "Reminder");
				}
			}
		} catch (Exception e) {}

	}

	public static String[] GetTimes(String Time){
		int Hrs = Integer.parseInt(Time.substring(0, 2));
		int Mins = Integer.parseInt(Time.substring(2, 4));

		//Hrs
		int StartHrs = Hrs;
		int EndHrs = Hrs;

		//Mins
		int StartMins = Mins - 15;
		int EndMins = Mins + 15;

		//Check the time band is allowed.
		if(StartMins < 0) {
			StartMins += 60;
			StartHrs -= 1;
		}

		if (EndMins >= 60) {
			EndMins -= 60;
			EndHrs++;
		}

		if (StartHrs == -1) {
			StartHrs = 23;
		}

		if (EndHrs == 24) {
			EndHrs = 0;
		}

		String StartTimeString = StringDate(StartHrs, StartMins);
		String EndTimeString = StringDate(EndHrs, EndMins);

		return new String [] {StartTimeString, EndTimeString};

	}

	public static String StringDate(int Hrs, int Mins){
		String FullDate;
		if (Mins < 10 && Hrs < 10) {

			FullDate = "0" + Integer.toString(Hrs) + "0" + Integer.toString(Mins);

		} else if (Mins < 10) {

			FullDate = Integer.toString(Hrs) + "0" + Integer.toString(Mins);

		} else if(Hrs < 10) {

			FullDate = "0" + Integer.toString(Hrs)  + Integer.toString(Mins);

		} else {

			FullDate = Integer.toString(Hrs) + Integer.toString(Mins);

		}

		return FullDate;
	}
}
