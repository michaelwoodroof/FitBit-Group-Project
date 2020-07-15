//Written by Christopher White,
import java.util.*;
import java.io.*;

public class BedTimes {
	private static Vector<BedTime> BedTimes;

	//Setup the BedTimes constructor.
    public BedTimes() {
        BedTimes = new Vector<BedTime>();
    }

	//add adds a new BedTime to the BedTimes list.
    public boolean add(int ID, String BedTime, String WakeUp) {
		int BedTimeint;
		int WakeUpint;
		try {
		   BedTimeint = Integer.parseInt(BedTime);
		   WakeUpint = Integer.parseInt(WakeUp);
		   BedTimes.add(new BedTime(ID, BedTime, WakeUp));
		   return true;
		}
		catch (NumberFormatException e)
		{
			return false;
		}
    }

	//delete removes the BedTime with the id search.
	public void delete(int search) {
		for (int i = 0; i < BedTimes.size(); ++i) {
            if (BedTimes.get(i).getID() == search){
                BedTimes.remove(i);
			}
		}
	}

	//length returns the size of the BedTimes list.
	public int length() {
		return BedTimes.size();
	}

	//GetBedTime gets the BedTime with the id search.
	public ArrayList<String> GetBedTime(int search) {
		ArrayList<String> Out = new ArrayList<String>();

		for (int i = 0; i < BedTimes.size(); ++i) {
			if (BedTimes.get(i).getID() == search){
				Out.add(BedTimes.get(i).getBedTime());
			}
		}
		return Out;
	}

	//GetWakeUpTime gets the BedTime with the id search.
	public ArrayList<String> GetWakeUpTime(int search) {
		ArrayList<String> Out = new ArrayList<String>();

		for (int i = 0; i < BedTimes.size(); ++i) {
			if (BedTimes.get(i).getID() == search) {
				Out.add(BedTimes.get(i).getWakeUp());
			}
		}
		return Out;
	}

	//Output to file.
	public void exportFile() {
		try {
			FileWriter file_write = new FileWriter("BedTimes.txt", false);
			String s = System.getProperty("line.separator");
			for (int i=0; i < BedTimes.size();i++) {
				String individual = BedTimes.get(i).getID() + "," + BedTimes.get(i).getBedTime() + "," + BedTimes.get(i).getWakeUp();
				file_write.write(individual + s);
			}
			file_write.close();

		} catch (IOException e) {
			System.out.println("Unable to export file.");
		}
	}

	//Read file.
	public void importFile() {
		try {
			File account_file = new File("BedTimes.txt");

			FileReader file_reader = new FileReader(account_file);
			BufferedReader buff_reader = new BufferedReader(file_reader);

			String row;
			while ((row = buff_reader.readLine()) != null) {
				String[] Times = row.split(","); //implementing comma seperated value (CSV)
				this.add(Integer.valueOf(Times[0]), Times[1], Times[2]);
			}
			buff_reader.close();
		} catch (IOException e) {
			System.out.println("Unable to read text file this time.");
		}
	}

	//Returns the BedTimes as a string.
    public String toString() {
        StringBuffer temp = new StringBuffer();
        for (int i = 0; i < BedTimes.size(); ++i) {
			if(i != BedTimes.size()-1) {
				temp.append(BedTimes.get(i).toString() + "\n" );
			} else {
				temp.append(BedTimes.get(i).toString());
			}
        }
        return temp.toString();
    }
}
