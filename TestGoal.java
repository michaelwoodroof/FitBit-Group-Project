import java.io.IOException;

public class TestGoal {

    public static void main(String args[]) throws IOException{

    	//Below is a test program for goal object. Below are the parameters for the goal object
    	//int ID, String Gender, double Age, double Weight, double Height, String goal_type, int goal

    	//Add goal by ID to .txt
		//	(int ID, String Gender, double Age, int Weight, int Height, String goal_type, int goal
    	Goal_prediction goal = new Goal_prediction(25, "Male", 23, 85, 175, "step", 60);
        System.out.println(goal.compute());

        //Loading goals by UserID. Displays ID, Result, Goal type, start data and final date. Enter UserID in the parameter
        System.out.println(goal.load(25)+ " check this!");

        // If more than one goal exist for a user. See below.

        //Goal_prediction.load(int UserID) can be called from any method and will return an arraylist

        // Update this counter to select a different active goal. Only useful when more than one goal active.
    	int goal_count = 0;

    	if (Goal_prediction.load(25).size() > 1) {
    		System.out.println("More than one goal found");
    		String g = (String) Goal_prediction.load(25).get(goal_count);
    		String[] index = g.split(" ");
    		//index[0] represent  ID, index[1] represent Result, index[2] represent Goal type, index[3] represent start data and index[4] represent final date
    		System.out.println(index[1]);
    	} else if (Goal_prediction.load(25).size() == 1) {
    		System.out.println("One goal found");
    		String g = (String) Goal_prediction.load(25).get(0);
    		String[] index = g.split(" ");
    		//index[0] represent  ID, index[1] represent Result, index[2] represent Goal type, index[3] represent start data and index[4] represent final date
    		System.out.println(index[1]);
    	}



    }

}
