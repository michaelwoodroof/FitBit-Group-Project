//Written by Janrey Mosuela

import java.util.ArrayList;

final class GraphValues {

	//Storing values for Y Axis
	private ArrayList<Number> yAxis = new ArrayList<Number>();

	//Storing values for X Axis
	private ArrayList<String> xAxis = new ArrayList<String>();

	//Method for retrieving the x axis values
	public ArrayList<String> getXAxis() {
		return this.xAxis;
	}

	//Method for retrieving the x axis values
	public ArrayList<Number> getYAxis() {
		return this.yAxis;
	}

	public int getSize() {
		return this.yAxis.size();
	}

	//Method for setting the values
	public GraphValues(ArrayList<String> xAxis, ArrayList<Number> yAxis) {
		this.xAxis = xAxis;
		this.yAxis = yAxis;
	}

	public GraphValues() {}
}
