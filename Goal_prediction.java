import org.json.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Goal_prediction {
    //
    //Possible Upgrades
    //BMI calculation reading for more accurate reading - Done
    //setting prediction by days, weeks, months etc
    //Weighing Health Conditions to set more healthy activity
    //Assuming these parameters are available

    //References
    //BMR Formula (Harris-Benedict)
    //https://community.fitbit.com/t5/Manage-Weight/How-many-calories-can-I-eat-if-BMR-is-1634-to-lose-weight/td-p/272486
    //https://www.diabetes.ca/diabetes-and-you/healthy-living-resources/weight-management/body-mass-index-bmi-calculator

    private static int ID;
    private static double Age;
    private static double Weight;
    private static double Height;
    private static String goal_type;
    private static int goal;
    private static String Gender;

    // Put this in the constructor later on
    private static String AuthorizationCode = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyMkQ5SjMiLCJzdWIiOiIzVlNMOVIiLCJpc3MiOiJGaXRiaXQiLCJ0eXAiOiJhY2Nlc3NfdG9rZW4iLCJzY29wZXMiOiJyYWN0IHJociByc2xlIiwiZXhwIjoxNTU2NDg4OTk1LCJpYXQiOjE1NTM4OTY5OTV9.Zc3AFKA5x9Eit1wAwVw6MJvbMbK87S1T_foiy4HUPLI";


    //goal constructor
    public Goal_prediction(int ID, String Gender, double Age, int Weight, int Height, String goal_type, int goal) {
        this.ID = ID; //Sets ID.
        this.Gender = Gender; //Sets Gender.
        this.Age = Age; //Sets Age.
        this.Weight = Weight; //Sets Weight.
        this.Height = Height; //Sets Height
        this.goal_type = goal_type;
        this.goal = goal;
    }


    static int steps_yesterday = 0;
    static int steps_today = 0;

    //Written Akash
    //For predicting goal weight by calories burnt
    static int steps_per_day = 12000;
    static int calorieOut_per_day = 12000/20;
    static int calorie_consumed = 2000;
    static int base_calorie = 1800;
    static double BMI = Weight/Height;
    static double BMR;
    static double current_weight = 0;
    static double goal_weight = 0;

    //Written Kostas
    //For predicting the days needed to achieve a Goal of step per day. 0
    static int stepsGoal = 0;
    static int days = 0;

    public static void initialize() throws FileNotFoundException {
    	current_weight = (double) Weight;
    	//goal_weight = (double) goal;
    }

    public static int compute() throws IOException {
    	if (goal_type.equals("calorie")) {
            goal_weight = (double) goal;
    	    //get_todays_step();
    	    BMR();
    		initialize();
    		return By_calorie();
    	} else if (goal_type.equals("step")) {
    		//get_todays_step();
            stepsGoal = goal;
    		BMR();
    		initialize();
    		return By_step();
    	}
        return 0;
    }

    //Assuming Patient is consuming calories of the BMR level
    public static void BMR() {
        if (Gender == "Male" || Gender == "M" || Gender == "m") {
    		BMR = 66.47 + (6.24 * Weight)+(12.7*Age)-(6.755*Age);
    		//System.out.println("This is the "+ BMR);
    	} else if (Gender == "Female" || Gender == "f" || Gender == "F") {
    		BMR = 655.1 + (4.35 * Weight)+(4.7*Age)-(4.7*Age);
    		//System.out.println("This is the "+ BMR);
    	}
    }

    public static int get_todays_step() {
    	//Make this part dynamic
        //Http reponse to the FitBit server
        DateTimeFormatter dft = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localdate = LocalDate.now();
        String f_d = dft.format(localdate);

        FitbitAPI A = new FitbitAPI();
        //System.out.println(A.API("Steps 1 Day", "2015-12-27", AuthorizationCode));
        String x = A.API("Steps 1 Day", "2015-12-27" , AuthorizationCode);
        A.CheckCode(AuthorizationCode);
        //System.out.println(x + "CHECK!!!");
        JSONObject obj = new JSONObject(x);
        //Reading JSON data saving it into a list
        List<String> list = new ArrayList<String>();
        JSONArray array = obj.getJSONArray("activities-steps");
        for (int i = 0 ; i < array.length() ; i++) {
            list.add(array.getJSONObject(i).getString("value"));
        }
        return steps_today = Integer.parseInt(list.get(0));
    }

    public static int get_yesterdays_step() {
        //Make this part dynamic
        //Http reponse to the FitBit server
        DateTimeFormatter dft = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localdate = LocalDate.now();
        LocalDate yesterday = localdate.minusDays(1);
        String f_d = dft.format(yesterday);

        FitbitAPI A = new FitbitAPI();
        //System.out.println(A.API("Steps 1 Day", "2015-12-27", AuthorizationCode));
        String x = A.API("Steps 1 Day", f_d , AuthorizationCode);
        A.CheckCode(AuthorizationCode);
        //System.out.println(x + "CHECK!!!");
        JSONObject obj = new JSONObject(x);
        //Reading JSON data saving it into a list
        List<String> list = new ArrayList<String>();
        JSONArray array = obj.getJSONArray("activities-steps");
        for (int i = 0 ; i < array.length() ; i++) {
            list.add(array.getJSONObject(i).getString("value"));
        }
        return steps_yesterday = Integer.parseInt(list.get(0));
    }

    static int By_calorie() throws IOException{
        //500 cal def reduces 1 lbs
    	//500/7 per day
        double reduction = (double) calorieOut_per_day/500/7;
        double g = (double) (current_weight - goal_weight);
        int d = (int) Math.round(g/reduction);
        // System.out.println("The number of days needed to achieve the goal weight is: "+d+" Days.Having maintained the BRM calories intake");
        save_data(d);
        return d;
    }

    //"D:\\Users\\akashdeoraj108\\workspace\\Second_year\\src\\UserGoals.txt"
    private static void save_data(int g) throws IOException {
    	try(FileWriter fw = new FileWriter("UserGoals.txt", true);
    		    BufferedWriter bw = new BufferedWriter(fw);
    		    PrintWriter out = new PrintWriter(bw))
    		{
    		    out.println(ID + " " + g + " "  + goal_type + " " + current_date() + " " + final_date(g));
    		    //more code
    		    //out.println("more text");
    		    //more code
    		} catch (IOException e) {}
    }


    public static String current_date() {

    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    	LocalDate localDate = LocalDate.now();
    	//System.out.println(dtf.format(localDate) + " Todays Date"); //2016/11/16

        String d = dtf.format(localDate);

    	return d;
    }

    public static String final_date(int g) {

    	String d = current_date();

        int add = g;
        String[] parts = d.split("-");
        int d_counter = Integer.parseInt(parts[0]);
        int m_counter = Integer.parseInt(parts[1]);
        int y_counter = Integer.parseInt(parts[2]);

        for (int x = 0; x < add; x++){

            d_counter = d_counter + 1;

            if (d_counter == 28) {
                //d_counter = 1;
                if (m_counter == 2) {
                    d_counter = 1;
                    m_counter = m_counter + 1;
                }
            } else if (d_counter == 30) {
                //d_counter = 1;
                if (m_counter == 4|| m_counter == 6|| m_counter == 9|| m_counter == 11) {
                    d_counter = 1;
                    m_counter = m_counter + 1;
                }
            } else if (d_counter == 31) {
                //d_counter = 1;
                if (m_counter == 1 || m_counter == 3 || m_counter == 5 || m_counter == 7|| m_counter == 8|| m_counter == 10|| m_counter == 12) {
                    d_counter = 1;
                    m_counter = m_counter + 1;
                }
            }
        }
        String final_date = d_counter+"-"+ m_counter+"-" + y_counter;
        // System.out.println(final_date + " Expected Finish date");
    	return final_date;
    }

    public static ArrayList load(int id) throws FileNotFoundException{
    	ArrayList result = new ArrayList<String>();
        Scanner scan = new Scanner(new File("UserGoals.txt"));
        while(scan.hasNext()){
            String line = scan.nextLine().toLowerCase().toString();
            int idValue = Integer.parseInt(line.split(" ")[0]);
            if (idValue == id) {
            	result.add(line);
            }
        }
        return result;
    }

    static int checkCounter = 0;

    private static int By_step() throws IOException{
        int progress = 0;
        int achieved = 0;

        if (checkCounter > 0) {
            int yesterdaySteps = get_yesterdays_step();
            int steps_per_day = get_todays_step();

            if (steps_per_day != yesterdaySteps) {
                progress = steps_per_day - yesterdaySteps;
                achieved = stepsGoal - progress;
                if (progress < 0) {
                    progress = - progress;
                }
                days = achieved / progress;

                if (steps_per_day == yesterdaySteps) {
                    days++ ;
                }
            }
        } else if (checkCounter == 0) {
            checkCounter = 1;
            days = 0;
            //System.out.println("Come back tomorrow");
        }
        save_data(days);
        return days;

    }

}
