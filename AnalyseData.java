import java.util.*;
import java.io.*;

public class AnalyseData {
	//References:
	//https://en.wikipedia.org/wiki/Gaussian_function
	//https://en.wikipedia.org/wiki/Standard_deviation
	//https://knowm.org/open-source/xchart/ (used for testing)

	public static void calculateGaussianAverages(ArrayList<Double> FitnessData, String measurementName) { //Calculates the averages and bounds for the normally distributed dataset
		double mean = calculateMean(FitnessData);
		double standardDeviation = calculateStandardDeviation(FitnessData, mean);
		double variance = Math.pow(standardDeviation, 2);

		ArrayList<Double> XValues = new ArrayList<Double>(); //Adds the x-values for the normalised Gaussian curve
    	for (double i = 0; i < (mean * 2); i++) {
      		XValues.add(i);
    	}

		ArrayList<Double> YValues = new ArrayList<Double>(); //Adds the y-values for the normalised Gaussian curve using the variance and SD
		for (double x : XValues){
			YValues.add(Math.pow(Math.exp(-(((x - mean) * (x - mean)) / ((2 * variance)))), 1 / (standardDeviation * Math.sqrt(2 * Math.PI))));
		}


		int limit = YValues.size(); //Finds the position of the maximum value y-value
		double max = Integer.MIN_VALUE;
		double pos = -1;
		for (int i = 0; i < limit; i++) {
			if (YValues.get(i) > max) {
				max = YValues.get(i);
				pos = i;
			}
		}
		
		double median = 0;
		double lowerBound = 0;
		double upperBound = 0;
		
		try{
			median = XValues.get((int) pos); //The x-value at the maxiumum position is the median of the graph
			lowerBound = XValues.get((int) (pos * 0.70));
			upperBound = XValues.get((int) (pos * 1.30));
		}catch(Exception e){		}

		ArrayList<Double> Bounds = new ArrayList<>(); //From this we can work out the lower and upper bounds for reasonable data over a prolonged period
		Bounds.add(lowerBound);
		Bounds.add(upperBound);

		try {
			writeToFile(measurementName, median, Bounds);
		}
		catch(IOException e) {
			e.printStackTrace();
		}

		//Test code to produce a graph:
		//XYChart Chart = QuickChart.getChart("Gaussian Curve", "x", "f(x)", "Fitness Data", XValues, YValues);
		//new SwingWrapper<XYChart>(Chart).displayChart();
	}

	public static void checkIncomingData(ArrayList<Double> FitnessData, String measurementName, String date, String username) { //Compares incoming data to stored bounds
		String[] analytics = {};
		try {
			analytics = readFromFile(measurementName);
		} catch(IOException e) {
			e.printStackTrace();
		}

		double mean = calculateMean(FitnessData);
		double lowerBound = Double.parseDouble(analytics[2]);
		double upperBound = Double.parseDouble(analytics[3]);

		if (mean < lowerBound){
			String title = "Warning";
			String header = "Low " + measurementName;
			String content = "";
			try {
				if (LoggedInUser.GetUserType().equals("Carer")) {
					content = "On " + date + ", " + username + "'s " + measurementName + " was much lower than average.";
				} else {
					content = "On " + date + ", your " + measurementName + " was much lower than average.";
				}
			} catch(IOException e) {}

			Notifications.Display(content, header, title);
		}

		if (mean > upperBound) {
			String title = "Warning";
			String header = "High " + measurementName;
			String content = "";
			try {
				if (LoggedInUser.GetUserType().equals("Carer")) {
					content = "On " + date + ", " + username + "'s " + measurementName + " was much higher than average.";
				} else {
					content = "On " + date + ", your " + measurementName + " was much higher than average.";
				}
			} catch(IOException e) {}
			Notifications.Display(content, header, title);
		}
	}

	private static void writeToFile(String measurementName, Double median, ArrayList<Double> Bounds) throws IOException { //Writes the averages and bounds to a file for storage
		File dataFile = new File("analysis.txt");
		FileWriter writer = new FileWriter(dataFile, true);
	    BufferedReader reader = new BufferedReader(new FileReader(dataFile));
	    String line;
	    String[] split;
		ArrayList<String> contents = new ArrayList<String>();
		ArrayList<Double> bounds = new ArrayList<Double>();


		while ((line = reader.readLine()) != null) {
			split = line.split(",");
			if (split[0].equals(measurementName) != true) {
				contents.add(line);
			}
		}

		reader.close();
		writer.close();

		dataFile.delete();

		for (int i = 0; i < contents.size(); i++){
			split = contents.get(i).split(",");
			bounds.add(Double.parseDouble(split[2]));
			bounds.add(Double.parseDouble(split[3]));
			writeToFile(split[0], Double.parseDouble(split[1]), bounds);
			bounds.clear();
		}

		FileWriter writer2 = new FileWriter(dataFile, true);
		PrintWriter out = new PrintWriter(writer2);
	  	out.print(measurementName + "," + String.valueOf(median) + "," + Bounds.get(0).toString() + "," + Bounds.get(1).toString());
		out.println();
		out.close();
	}

	private static String[] readFromFile(String measurementName) throws IOException { //Reads the averages and bounds to a file to compare new incoming data to
		File dataFile = new File("analysis.txt");
		BufferedReader reader = new BufferedReader(new FileReader(dataFile));
		String[] analytics = {};

       	do {
       		String line = reader.readLine();
       		analytics = line.split(",");
       	} while (analytics[0].equals(measurementName) == false); //Looks for the correct measurements and pulls the bounds and averages recorded for that one

		reader.close();

		return analytics;
	}


	public static double calculateMean(ArrayList<Double> FitnessData) { //Calculates the mean of the dataset
		double total = 0;
		if(!FitnessData.isEmpty()) {
			for (double i : FitnessData){
				total = i + total;
			}
			return total/FitnessData.size();
		}
		return total;
	}

	public static double calculateStandardDeviation(ArrayList<Double> FitnessData, double mean) { //Calculates the standard deviation of the dataset
		double total = 0;
		if(!FitnessData.isEmpty()) {
			for (double i : FitnessData) {
				double squareDifferenceToMean = Math.pow(i - mean, 2);
				total = total + squareDifferenceToMean;
			}
		}
		double standardDeviation = Math.sqrt(total/FitnessData.size());
		return standardDeviation;
	}
}
