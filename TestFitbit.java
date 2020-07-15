//Test Fitbit Classes

public class TestFitbit {

	private static String AuthorizationCode = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyMkQ5SjMiLCJzdWIiOiIzVlNMOVIiLCJpc3MiOiJGaXRiaXQiLCJ0eXAiOiJhY2Nlc3NfdG9rZW4iLCJzY29wZXMiOiJyYWN0IHJociByc2xlIiwiZXhwIjoxNTU2NDg4OTk1LCJpYXQiOjE1NTM4OTY5OTV9.Zc3AFKA5x9Eit1wAwVw6MJvbMbK87S1T_foiy4HUPLI";

	public static void main(String[] args) throws Exception {
		//Test FitbitAPI.
		FitbitAPI A = new FitbitAPI();
		System.out.println(A.API("Steps 1 Day", "2015-12-27", AuthorizationCode));
		A.CheckCode(AuthorizationCode);

		//Test FitbitData.
		FitbitData B = new FitbitData();

		//Tests Min Data.
		int[] T2 = B.getHRDataMin("2016-01-30", AuthorizationCode);

		for (int item : T2) {
			System.out.println(item);
		}

		//Tests Min Times.
		String[] T3 = B.getHRTimeMin("2016-01-30", AuthorizationCode);

		for (String item : T3) {
			System.out.println(item);
		}

		//Tests Sec Data.
		int[] T4 = B.getHRDataSec("2016-01-30", AuthorizationCode);

		for (int item : T4) {
			System.out.println(item);
		}

		//Tests Sec Time.
		String[] T5 = B.getHRTimeSec("2016-01-31", AuthorizationCode);

		for (String item : T5) {
			System.out.println(item);
		}

		//Tests Steps per Day.
		int[] T6 = B.getStepsDataDay("2016-01-30", AuthorizationCode);

		for (int item : T6) {
			System.out.println(item);
		}
		//Tests Steps per Week.
		int[] T7 = B.getStepsDataWeek("2016-01-30", AuthorizationCode);

		for (int item : T7) {
			System.out.println(item);
		}
		//Tests Steps per Month.
		int[] T8 = B.getStepsDataMonth("2016-01-30", AuthorizationCode);

		for (int item : T8) {
			System.out.println(item);
		}
	}
}
