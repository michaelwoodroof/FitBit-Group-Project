
public class TestAlerts {

	public static void main(String[] args) {
	    Alerts Item = new Alerts();
		Item.start();

	    try{
	    	Thread.sleep(500000000);
		} catch (InterruptedException e) {}
	    Item.Stop();
	}

}
