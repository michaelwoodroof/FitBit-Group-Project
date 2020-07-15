import java.util.Calendar;

public class UserHealth {

	public static String[] BMI(){
    	double BMI = 0.0;
    	String status = "";
    	try{
            UserAccounts userAccounts = new UserAccounts();
            userAccounts.importFile();
            UserAccount user = userAccounts.getAccountWithUsername(LoggedInUser.GetUsername());
            Double Weight = Double.parseDouble(user.getWeight());
            Double Height = Double.parseDouble(user.getHeight());
            BMI = Weight / ((Height / 100) * (Height / 100));

            if (BMI < 18.5) {
            	status = "Underweight";
            } else if (BMI >= 18.5 && BMI <= 24.9) {
            	status = "Normal";
            } else if (BMI > 24.9 && BMI <= 29.9) {
            	status = "Overweight";
            } else if (BMI > 29.9) {
            	status = "Obese";
            }
    	} catch (Exception e) {

        }
    	String results[] = new String[2];
    	results[0] = String.valueOf(BMI);
    	results[1] = status;
    	return results;
    }

    public static double BMR() {
    	double BMR = 0.0;
    	try {
            UserAccounts userAccounts = new UserAccounts();
            userAccounts.importFile();
            UserAccount user = userAccounts.getAccountWithUsername(LoggedInUser.GetUsername());
            String[] parts = user.getAge().split("-");
            Double Weight = Double.parseDouble(user.getWeight());
			Double Height = Double.parseDouble(user.getHeight());
            String Gender = user.getGender();
            int year_of_birth = Integer.parseInt(parts[0]);
            int current_year = Calendar.getInstance().get(Calendar.YEAR);
            Double Age = Double.valueOf(current_year - year_of_birth);


      		if (Gender.equals("Male") || Gender.equals("M") || Gender.equals("m")) {
    			BMR = 66.47 + (13.7 * Weight) + (5 * Height) - (6.8 * Age);
    	  	} else if (user.getGender() == "Female" || user.getGender() == "f" || user.getGender() == "F") {
    			BMR = 655.1 + (9.6 * Weight) + (1.8 * Height) - (4.7 * Age);
    	    } else {
    		    BMR = 360.785 + (11.65 * Weight) + (3.4 * Height) - (5.75 * Age);
    	    }

        } catch (Exception e) {

        }

    	return BMR;
    }


}
