import java.io.FileNotFoundException;

public class finalCounter {

	//add code to get user's logged in ID

	public static void dayCounter(int days) {
		for(int i = 0; i <= days; i++) {
			if (i < days) {
				System.out.println("You are on " + i + " day out of the " + days + " until goal is done.");
			} else {
				System.out.println("You finished your goal.");
			}
		}
	}

	public static void finishGoal(int days) {
		dayCounter(0);
	}

	public static String calcGoals() throws FileNotFoundException {
		int goal_count = 0;
		if (Goal_prediction.load(25).size() > 1) {
			String g = (String) Goal_prediction.load(25).get(goal_count);
			String[] index = g.split(" ");
			String x = index[1];
			return x;
		} else {
			return (String) Goal_prediction.load(25).get(1);
		}
	}
}
