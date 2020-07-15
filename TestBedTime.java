//Written by Christopher White,
import java.util.*;

public class TestBedTime {

	public static void main(String[] args) {
		BedTimes Z = new BedTimes();
		Z.add(1, "0930", "5555");
		Z.add(1, "1830", "5555");
		Z.add(2, "0930", "5555");
		System.out.println(Z);
        
		Z.exportFile();

		BedTimes Y = new BedTimes();

		Y.importFile();

		ArrayList<String> AllBed = Y.GetBedTime(1);

		for (int i = 0; i < AllBed.size(); ++i) {
			System.out.println(AllBed.get(i));
		}
		CheckBedTimes A = new CheckBedTimes();

		ArrayList<String> AllBed2 = Z.GetBedTime(1);

		for (int i = 0; i < AllBed2.size(); ++i) {
			A.CheckTime(AllBed2.get(i),"BedTime");
			System.out.println(AllBed2.get(i));
		}
	}

}
