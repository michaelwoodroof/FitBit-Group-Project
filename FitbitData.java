//Written by Christopher White, Janrey Mosuela, Shaheer Khan
import org.json.*;

import java.util.ArrayList;
import java.util.List;

//IMPORTANT!! - USE THE LINK BELOW TO LOAD THE JSON-20180813.JAR FILE INTO EXTERNAL LIBRARY
//http://www.oxfordmathcenter.com/drupal7/node/44

public class FitbitData {

	// - - - - - -- - - - - - - - - -  H E A R T - R A T E - - - - - - - - - - - - - - - - - - - - - -

	//Returns the Heart Rate data for seconds as a int list.
	public static int[] getHRDataSec(String Date, String AuthCode) {
		return getHRData(Date, AuthCode, "HR 1 Day Sec");
	}

	//Returns the Heart Rate times for seconds as a string list.
	public static String[] getHRTimeSec(String Date, String AuthCode) {
		return getHRTime(Date, AuthCode, "HR 1 Day Sec");
	}

	//Returns the Heart Rate data for minuets as a int list.
	public static int[] getHRDataMin(String Date, String AuthCode) {
		return getHRData(Date, AuthCode, "HR 1 Day Min");
	}

	//Returns the Heart Rate times for seconds as a string list.
	public static String[] getHRTimeMin(String Date, String AuthCode) {
		return getHRTime(Date, AuthCode, "HR 1 Day Min");
	}

	//Gets the Time for a spesific date, and type (min/sec).
	private static String[] getHRTime(String Date, String AuthCode, String Type) {
		List<String> HRData = new ArrayList<String>();

		JSONArray array = JSONHR(Date, AuthCode, Type);

		for (int i = 0 ; i < array.length() ; i++) {
	    	HRData.add(array.getJSONObject(i).getString("time"));
	    }

		String[] Result = convertString(HRData);

		return Result;
	}

	//Gets the data for a spesific date, and type (min/sec).
	private static int[] getHRData(String Date, String AuthCode, String Type) {
		List<Integer> HRData = new ArrayList<Integer>();

		JSONArray array = JSONHR(Date, AuthCode, Type);

		for (int i = 0 ; i < array.length() ; i++) {
	    	HRData.add(array.getJSONObject(i).getInt("value"));
	    }

		int[] Result = convertIntegers(HRData);

		return Result;
	}

	//Gets the Fitbit data and returns it as a JSON array.
	private static JSONArray JSONHR(String Date, String AuthCode, String Type) {
	    FitbitAPI Data = new FitbitAPI();
		String APIOut = Data.API(Type, Date, AuthCode);

		if (APIOut == null) {
			return new JSONArray();
		}

		//Set API result to JSON Object.
		JSONObject obj = new JSONObject(APIOut);

		//Gets the Intraday Data.
		JSONObject obj2 = (JSONObject)obj.get("activities-heart-intraday");

		//Gets HR data set.
		JSONArray array = obj2.getJSONArray("dataset");

		return array;
	}

	// - - - - - -- - - - - - - - - -  S T E P S - - - - - - - - - - - - - - - - - - - - - -

	public static int[] getStepsDataDay(String Date, String AuthCode) {
		return getStepsData(Date, AuthCode, "Steps 1 Day");
	}

	public static String[] getStepsDateDay(String Date, String AuthCode) {
		return getStepsDates(Date, AuthCode, "Steps 1 Day");
	}

	public static int[] getStepsDataWeek(String Date, String AuthCode) {
		return getStepsData(Date, AuthCode, "Steps 7 Days");
	}

	public static String[] getStepsDateWeek(String Date, String AuthCode) {
		return getStepsDates(Date, AuthCode, "Steps 7 Days");
	}

	public static int[] getStepsDataMonth(String Date, String AuthCode) {
		return getStepsData(Date, AuthCode, "Steps 1 Month");
	}

	public static String[] getStepsDateMonth(String Date, String AuthCode) {
		return getStepsDates(Date, AuthCode, "Steps 1 Month");
	}

	private static String[] getStepsDates(String Date, String AuthCode, String Type) {
		List<String> StepsDates = new ArrayList<String>();

		JSONArray array = JSONSteps(Type, Date, AuthCode);

		for (int i = 0 ; i < array.length() ; i++) {
	    	StepsDates.add(array.getJSONObject(i).getString("dateTime"));
	    }

		String[] Result = convertString(StepsDates);

		return Result;
	}

	private static int[] getStepsData(String Date, String AuthCode,String Type) {
		List<Integer> StepsData = new ArrayList<Integer>();

		JSONArray array = JSONSteps(Type, Date, AuthCode);

		for (int i = 0 ; i < array.length() ; i++) {
	    	StepsData.add(array.getJSONObject(i).getInt("value"));
	    }

		int[] Result = convertIntegers(StepsData);

		return Result;
	}

	private static JSONArray JSONSteps(String Type, String Date, String AuthCode) {
		FitbitAPI Data = new FitbitAPI();
		String APIOut = Data.API(Type, Date, AuthCode);

		if (APIOut == null) {
			return new JSONArray();
		}

		//Set API result to JSON Object.
		JSONObject obj = new JSONObject(APIOut);
		JSONArray array = obj.getJSONArray("activities-steps");

		return array;
	}

	// - - - - - -- - - - - - - - - -  S L E E P - - - - - - - - - - - - - - - - - - - - - -

	public static int[] getSleepDuration(String Date, String AuthCode) {
		return getSleepData(Date, AuthCode, "Sleep 1 Day");
	}

	 private static int[] getSleepData(String Date, String AuthCode, String Type){
		List<Integer> SleepData = new ArrayList<Integer>();

		JSONArray array = JSONSleep(Date, AuthCode, Type);

		for (int i=0;i<array.length();i++) {
			SleepData.add(array.getJSONObject(i).getInt("duration"));
		}

		int[] SleepLog = convertIntegers(SleepData);

		return SleepLog;
	}

	private static JSONArray JSONSleep(String Date, String AuthCode, String Type) {
		FitbitAPI SleepData = new FitbitAPI();
		String APIOutput = SleepData.API(Type, Date, AuthCode);

		if (APIOutput == null) {
			return new JSONArray();
		}

		JSONObject obj = new JSONObject(APIOutput);

		JSONArray array = obj.getJSONArray("sleep");

		return array;
	}


	//Turns array list of integers to int list.
	private static String[] convertString(List<String> Alist) {
		//Based on https://stackoverflow.com/questions/718554/how-to-convert-an-arraylist-containing-integers-to-primitive-int-array

	    String[] output = new String[Alist.size()];

	    for (int i = 0; i < output.length; i++) {
	    	output[i] = Alist.get(i).toString();
	    }
	    return output;
	}

	//Turns array list of strings to a string list.
	private static int[] convertIntegers(List<Integer> Alist) {
		//Based on https://stackoverflow.com/questions/718554/how-to-convert-an-arraylist-containing-integers-to-primitive-int-array

	    int[] output = new int[Alist.size()];

	    for (int i = 0; i < output.length; i++) {
	    	output[i] = Alist.get(i).intValue();
	    }
	    return output;
	}

}
