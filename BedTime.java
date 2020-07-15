//Written by Christopher White,

public class BedTime {
    //Set up BedTime attributes.
    private int ID;
    private String BedTime;
    private String WakeUp;

   //Setup the BedTime constructor.
   public BedTime(int ID, String BedTime, String WakeUp) {
       this.ID = ID; //Sets ID.
       this.BedTime = BedTime; //Sets BedTime.
       this.WakeUp = WakeUp; //Sets WakeUp.
   }
   
   //getID returns the ID.
   public int getID() {
      return ID;
   }
    
   //getBedTime returns the BedTime.
   public String getBedTime() {
      return BedTime;
   }
   
   //getFirstName returns the LastName.
   public String getWakeUp() {
      return WakeUp;
   }
   
   //setBedTime sets the BedTime to the string BedTime.
   public void setBedTime(String BedTime) {
      this.BedTime = BedTime;
   }
   
   //setWakeUp sets the WakeUp to the string WakeUp.
   public void setWakeUp(String WakeUp) {
	   this.WakeUp = WakeUp;
   }
   
   //Returns the BedTime as a string. 
   public String toString() {
      String s = ID + ", " + BedTime + ", " + WakeUp; //Sets BedTime to a String.
      return s;
   }
}