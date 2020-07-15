//Written by Christopher White,
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;

public class FitbitAPI {

	//Referances:
	//https://dev.fitbit.com/apps/oauthinteractivetutorial
	//https://dev.fitbit.com/build/reference/web-api/

	//Get Auth Code:
	//https://www.fitbit.com/oauth2/authorize?response_type=token&client_id=22D9J3&redirect_uri=https%3A%2F%2Fcuyear2group1.github.io&scope=activity%20heartrate%20sleep&expires_in=604800
	//https://www.fitbit.com/oauth2/authorize?response_type=token&client_id=22D9J3&redirect_uri=https%3A%2F%2Fcuyear2group1.github.io&scope=activity%20heartrate%20sleep&expires_in=2592000

	//FitbitAPI returns a string of data requested. Must supply the type of data e.g. Steps, Date and the users AuthCode.
	public static String API(String DataType, String EndDate, String AuthCode) {

		String URL = ""; //Creates and empty string to build the API URL.
		if (DataType == "Steps 1 Year") {
			//Steps data 1 year with end date.
			URL = "https://api.fitbit.com/1/user/-/activities/steps/date/"; //Selects the type of data.
			URL += EndDate + "/1y.json"; //Selects the date and time frame.
		} else if(DataType == "Steps 1 Month") {
			//Steps data 1 month with end date.
			URL = "https://api.fitbit.com/1/user/-/activities/steps/date/";
			URL += EndDate + "/1m.json";
		} else if (DataType == "Steps 7 Days") {
			//Steps data 7 days with end date.
			URL = "https://api.fitbit.com/1/user/-/activities/steps/date/";
			URL += EndDate + "/7d.json";
		} else if (DataType == "Steps 1 Day") {
			//Steps data 1 day with end date.
			URL = "https://api.fitbit.com/1/user/-/activities/steps/date/";
			URL += EndDate + "/1d.json";
		} else if (DataType == "HR 1 Day Min") {
			//Heart Rate data for a day for every minute.
			URL = "https://api.fitbit.com/1/user/-/activities/heart/date/";
			URL += EndDate + "/1d/1min.json";
		} else if (DataType == "HR 1 Day Sec") {
			//Heart Rate data for a day for every second.
			URL = "https://api.fitbit.com/1/user/-/activities/heart/date/";
			URL += EndDate + "/1d/1sec.json";
			//1sec or 1min
		} else if (DataType == "Sleep 1 Day") {
			//Sleep data for a day.
			URL = "https://api.fitbit.com/1.2/user/-/sleep/date/";
			URL += EndDate + ".json";
		}

		FitbitAPI request = new FitbitAPI(); //Creates a new request and sends the data.
		return request.SendRequest(URL, AuthCode); //Returns the requested data.
	}

	public static Boolean CheckCode(String Code){
		FitbitAPI request = new FitbitAPI(); //Creates a new request and sends the data.
		String Result = request.SendRequest("https://api.fitbit.com/1/user/-/activities/steps/date/today/7d.json", Code);
		if (Result == null) {
			return false;
		} else {
			return true;
		}
	}

	//SendRequest sends HTTP requests to a URL with the users AuthCode. Needs the URL and AuthCode.
	private String SendRequest(String url, String AuthCode){
		//Try to send the HTTP request.
		try {
			URL urlItem = new URL(url); //Creates a URL item for the url.
			HttpURLConnection connection = (HttpURLConnection) urlItem.openConnection(); //Opens a connection to the desired URL.
			connection.setRequestMethod("GET"); //Sets the methord to GET.
			connection.setRequestProperty("Authorization", AuthCode); //Creates a header with the Authorization information. Needed for Fitbit Authorization.


			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream())); //Opens the website as a buffer to read.
			StringBuffer result = new StringBuffer(); //Creates a string buffer called result to put the website result in.
			String inputLine; //Creates a varable to put each line in.

			while ((inputLine = in.readLine()) != null) {
				result.append(inputLine); //While there is data add it to the string buffer result.
			}
			in.close(); //Close the buffered reader.

			return result.toString(); //return the result as a string.

		} catch(IOException ex){}

		return null;
	}
}
