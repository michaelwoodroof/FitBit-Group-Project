import java.util.*;

class TestAnalytics {
	public static void main(String[] args) {
		ArrayList<Double> fitnessData = new ArrayList<>();
		int[] rawIntegerData;

		//Get test data intially
		rawIntegerData = FitbitData.getHRDataMin("2016-01-29", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyMkQ5SjMiLCJzdWIiOiIzVlNMOVIiLCJpc3MiOiJGaXRiaXQiLCJ0eXAiOiJhY2Nlc3NfdG9rZW4iLCJzY29wZXMiOiJyYWN0IHJociByc2xlIiwiZXhwIjoxNTUxOTY5NjgzLCJpYXQiOjE1NDk5MTE1ODB9.rEA0V5vLOwS-Eam_6aRbw5G62Cg1vPf5Pr41KASyi38");
		fitnessData = castingData(rawIntegerData);

		//Use this function with the data and the name of the measurement for intial set-up (produces the analytics file)
		AnalyseData.calculateGaussianAverages(fitnessData, "Heartrate");

		//Then get incoming data
		rawIntegerData = FitbitData.getHRDataMin("2016-01-30", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyMkQ5SjMiLCJzdWIiOiIzVlNMOVIiLCJpc3MiOiJGaXRiaXQiLCJ0eXAiOiJhY2Nlc3NfdG9rZW4iLCJzY29wZXMiOiJyYWN0IHJociByc2xlIiwiZXhwIjoxNTUxOTY5NjgzLCJpYXQiOjE1NDk5MTE1ODB9.rEA0V5vLOwS-Eam_6aRbw5G62Cg1vPf5Pr41KASyi38");
		fitnessData = castingData(rawIntegerData);

		//Use this function with the data and the name of the measurement to test it (which would trigger an alerts function if necessary)
		AnalyseData.checkIncomingData(fitnessData, "Heartrate", "2016-01-30", "");
	}


	public static ArrayList<Double> castingData(int[] rawIntegerData) { //Method to convert data into ArrayList<Double>
		Double[] rawData = new Double[rawIntegerData.length];
		for(int i=0; i<rawIntegerData.length; i++) {
    		rawData[i] = Double.valueOf(rawIntegerData[i]);
		}

		ArrayList<Double> fitnessData = new ArrayList<>();
		fitnessData.addAll(Arrays.asList(rawData));
		return fitnessData;
	}

}
