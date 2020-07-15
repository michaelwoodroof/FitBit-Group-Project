//Written by Janrey Mosuela, Akash Deoraj

import java.util.*;
import java.io.*;

public class PlotGraph {

	// Storing temporary values for Y Axis
	private ArrayList<Number>  tempYAxis;

	// Storing temporary values for Y Axis
	private ArrayList<String>  tempXAxis;

	// Initialising FitbitData for pulling user data
	FitbitData data = new FitbitData();

	// Method for plotting heart rate graph
	public GraphValues heartRate(String timeInstance, String date, String authorizationCode) {

		// Obtaining Y axis values and storing it to tempYAxis
		if (timeInstance.equals("sec")) {
			tempYAxis = convertToArrayList(data.getHRDataSec(date, authorizationCode));
			tempXAxis =  new ArrayList<String> (Arrays.asList(data.getHRTimeSec(date, authorizationCode)));
		} else if (timeInstance.equals("min")) {
			tempYAxis = convertToArrayList(data.getHRDataMin(date, authorizationCode));
			tempXAxis = new ArrayList<String> (Arrays.asList(data.getHRTimeMin(date, authorizationCode)));
		}

		return new GraphValues(tempXAxis, tempYAxis);
	}

	// Method for plotting steps graph
	public GraphValues steps(String timeInstance, String date, String authorizationCode) {
		// obtaining Y axis values and storing it to tempYAxis
		if (timeInstance.equals("day")) {
			tempYAxis = convertToArrayList(data.getStepsDataDay(date, authorizationCode));
		} else if (timeInstance.equals("week")) {
			tempYAxis = convertToArrayList(data.getStepsDataWeek(date, authorizationCode));
		} else if (timeInstance.equals("month")) {
			tempYAxis = convertToArrayList(data.getStepsDataMonth(date, authorizationCode));
		}

		tempXAxis = getXAxis(tempYAxis.size());

		return new GraphValues(tempXAxis, tempYAxis);
	}

	// Method for plotting sleep graph
	public GraphValues sleep(String timeInstance, String date, String authorizationCode) {

    	String d = date;
        ArrayList result = new ArrayList<Number>();
        String[] ary = d.split("-");
        String a = ary[0];
     	String b = ary[1];
        String s = ary[2];

        int foo = Integer.parseInt(s);

        for (int x = 0; x < 8; x++) {
            int z = foo-x;
            String n_d = a + "-" + b+  "-" + Integer.toString(z);
            tempYAxis = convertToArrayList(data.getSleepDuration(n_d, authorizationCode));

		    if (tempYAxis.isEmpty()) {

		    } else {
            	for (Number item : tempYAxis) {
			   		Number j = ((int) item / 60000) / 60;
		       		result.add(j);
              	}
            }
        }

        ArrayList result_new = new ArrayList<Number>();

        for (int y = result.size() - 1; y >= 0; y--) {
	    	result_new.add(result.get(y));
        }
        tempXAxis = getXAxis(8);

        return new GraphValues(tempXAxis, result_new);
	}

	// Method for converting int[] into ArrayList<Number>
	private ArrayList<Number> convertToArrayList(int[] array) {
		ArrayList<Number> arrayList = new ArrayList<Number>(array.length);
		for (int i = 0; i < array.length; i++) {
		  arrayList.add((Number) (array[i]));
		}
		return arrayList;
	}


	// Method for obtaining X Axis values
    public ArrayList<String> getXAxis(int yAxisSize) {
    	ArrayList<String> tempArrayList = new ArrayList<String>();
    	for (int i = yAxisSize; i >= 1; i--) {
    		if (i == 1) {
    			tempArrayList.add("Today");
    		} else if (i == 2) {
    			tempArrayList.add("Yesterday");
    		} else {
    			tempArrayList.add(String.valueOf(i - 1) + " days ago");
    		}
    	}
    	return tempArrayList;
    }
}
