import java.util.Arrays;
import java.util.Random;


public class TrainLine {

	private TrainStation leftTerminus;
	private TrainStation rightTerminus;
	private String lineName;
	private boolean goingRight;
	public TrainStation[] lineMap;
	public static Random rand;

	public TrainLine(TrainStation leftTerminus, TrainStation rightTerminus, String name, boolean goingRight) {
		this.leftTerminus = leftTerminus;
		this.rightTerminus = rightTerminus;
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;

		this.lineMap = this.getLineArray();
	}

	public TrainLine(TrainStation[] stationList, String name, boolean goingRight)
	/*
	 * Constructor for TrainStation input: stationList - An array of TrainStation
	 * containing the stations to be placed in the line name - Name of the line
	 * goingRight - boolean indicating the direction of travel
	 */
	{
		TrainStation leftT = stationList[0];
		TrainStation rightT = stationList[stationList.length - 1];

		stationList[0].setRight(stationList[stationList.length - 1]);
		stationList[stationList.length - 1].setLeft(stationList[0]);

		this.leftTerminus = stationList[0];
		this.rightTerminus = stationList[stationList.length - 1];
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;

		for (int i = 1; i < stationList.length - 1; i++) {
			this.addStation(stationList[i]);
		}

		this.lineMap = this.getLineArray();
	}

	public TrainLine(String[] stationNames, String name,
			boolean goingRight) {/*
									 * Constructor for TrainStation. input: stationNames - An array of String
									 * containing the name of the stations to be placed in the line name - Name of
									 * the line goingRight - boolean indicating the direction of travel
									 */
		TrainStation leftTerminus = new TrainStation(stationNames[0]);
		TrainStation rightTerminus = new TrainStation(stationNames[stationNames.length - 1]);

		leftTerminus.setRight(rightTerminus);
		rightTerminus.setLeft(leftTerminus);

		this.leftTerminus = leftTerminus;
		this.rightTerminus = rightTerminus;
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;
		for (int i = 1; i < stationNames.length - 1; i++) {
			this.addStation(new TrainStation(stationNames[i]));
		}

		this.lineMap = this.getLineArray();

	}

	// adds a station at the last position before the right terminus
	public void addStation(TrainStation stationToAdd) {
		TrainStation rTer = this.rightTerminus;
		TrainStation beforeTer = rTer.getLeft();
		rTer.setLeft(stationToAdd);
		stationToAdd.setRight(rTer);
		beforeTer.setRight(stationToAdd);
		stationToAdd.setLeft(beforeTer);

		stationToAdd.setTrainLine(this);

		this.lineMap = this.getLineArray();
	}

	public String getName() {
		return this.lineName;
	}

	public int getSize() {
		//create a variable to count the number of stations
		int count = 1;
		
		//Create a temp train station
		TrainStation temp = this.leftTerminus;
		
		boolean condition = true;
		
		//check if the current station is not the right terminal
		//and current station's reference is not null
		while(condition) {
			if(!(temp.isRightTerminal())) {
				count++;
				temp = temp.getRight();
			}
			else {
				condition = false;
			}
		}
		
		return count;
	}

	public void reverseDirection() {
		this.goingRight = !this.goingRight;
	}
	
	// You can modify the header to this method to handle an exception. You cannot make any other change to the header.
	public TrainStation travelOneStation(TrainStation current, TrainStation previous) {
		
		//check if the current station is on the line
		this.findStation(current.getName());
		
		//check if the current station has connection station to transfer
		if(current.hasConnection) {
			if(previous == null) {
				return current.getTransferStation();
			}
			else if(previous.equals(current.getTransferStation())) {
				return this.getNext(current);
			}
			else {
			return current.getTransferStation();
			}
		}
		else {
			return this.getNext(current);
		}
	}
	
	// You can modify the header to this method to handle an exception. You cannot make any other change to the header.
	public TrainStation getNext(TrainStation station) {
		//find the current station
		//this will throw an exception if the input station is not on the line
		TrainStation current = this.findStation(station.getName());
		
		
		//check if the current station is one of terminals
		if(!(this.goingRight) && current.isLeftTerminal()) {
			this.reverseDirection();
			return current.getRight();
		}
		else if(this.goingRight && current.isRightTerminal()) {
			this.reverseDirection();
			return current.getLeft();
		}
		
		//check the direction of the train, return the station next to respectively
		if(this.goingRight) {
			return current.getRight();
		}
		else {
			return current.getLeft();
		}
	}

	// You can modify the header to this method to handle an exception. You cannot make any other change to the header.
	public TrainStation findStation(String name) {
		//iterate all stations in the line
		for(int i = 0; i < this.getSize(); i++) {
			
			//if find the station with the input name, return it
			if(this.lineMap[i].getName().equals(name)) {
				return this.lineMap[i];
			}
		}
		
		//throw an exception otherwise
		throw new StationNotFoundException("The station with input name is not found");
	}
	
	//Helper function for swapping two station inside a list
	public void swap(TrainStation t1, TrainStation t2) {
		if(t1.isLeftTerminal()) {
			t1.setNonTerminal();
			t2.setLeftTerminal();
			this.leftTerminus = t2;
		}
		else if(t2.isRightTerminal()) {
			t2.setNonTerminal();
			t1.setRightTerminal();
			this.rightTerminus = t1;
		}
		
		//create a temp variable to store the reference of t1
		TrainStation t0 = t1.getLeft();
		TrainStation t3 = t2.getRight();
		
		if(t0 != null && t3 !=null) {
			t0.setRight(t2);
			t3.setLeft(t1);
		}
		
		t2.setLeft(t1.getLeft());
		t1.setRight(t2.getRight());
		
		t2.setRight(t1);
		t1.setLeft(t2);
		
	}

	public void sortLine() {
		// set the condition to true initially
		boolean condition = true;
		
		//create a number to count how many numbers are sorted
		int count = 0;
		
		TrainStation temp = this.getLeftTerminus();
		
		//iterate
		while(condition) {
			
			//change the condition to false
			condition = false;
			
			//use the for loop to make the  biggest one to the end of the list
			for(int i = 0; i < this.getSize()-2-count; i++) {
				
				//the left if greater than the station right to it, swap them
				if(temp.getName().compareTo(temp.getRight().getName()) > 0) {
					swap(temp, temp.getRight());
					
					//reset the condition to true
					condition = true;
				}
				if(!(temp.isRightTerminal())) {
					temp = temp.getRight();
				}
				count++;
			}
		}
		this.lineMap = this.getLineArray();
		
		
	}

	public TrainStation[] getLineArray() {
		//create new empty array with the size of the line
		TrainStation[] LineArray = new TrainStation[this.getSize()];
		
		//initialize the left most station in index 0
		LineArray[0] = this.getLeftTerminus();
		LineArray[this.getSize()-1] = this.getRightTerminus();
		
		//iterate the array, initialize each station by using the left station calling getRight
		for(int i = 1; i < this.getSize()-1; i++) {
			LineArray[i] = LineArray[i-1].getRight();
		}
		
		return LineArray;
	}

	private TrainStation[] shuffleArray(TrainStation[] array) {
		Random rand = new Random();
		rand.setSeed(11);
		for (int i = 0; i < array.length; i++) {
			int randomIndexToSwap = rand.nextInt(array.length);
			TrainStation temp = array[randomIndexToSwap];
			array[randomIndexToSwap] = array[i];
			array[i] = temp;
		}
		this.lineMap = array;
		return array;
	}
	
	public void shuffleLine() {

		// you are given a shuffled array of trainStations to start with
		TrainStation[] lineArray = this.getLineArray();
		TrainStation[] shuffledArray = shuffleArray(lineArray);
		
		//set all stations are not the terminals
		for(int i = 0; i < shuffledArray.length; i++) {
			shuffledArray[i].setNonTerminal();
		}
		
		//set the corresponding attributes to the shuffled stations
		shuffledArray[0].setLeftTerminal();
		shuffledArray[0].setLeft(null);
		shuffledArray[shuffledArray.length-1].setRightTerminal();
		shuffledArray[shuffledArray.length-1].setRight(null);
		this.leftTerminus = shuffledArray[0];
		this.rightTerminus = shuffledArray[shuffledArray.length-1];
		
		//set all train stations' next right station is the one next to it in the shuffled station
		//iterate all stations until the right terminal
		 
		for(int i = 1; i < shuffledArray.length; i++) {
			shuffledArray[i-1].setRight(shuffledArray[i]);
		}
		//iterate all stations until the left terminal
		
		for(int i = shuffledArray.length-2; i >= 0; i--) {
			shuffledArray[i+1].setLeft(shuffledArray[i]);
			}
		}

	public String toString() {
		TrainStation[] lineArr = this.getLineArray();
		String[] nameArr = new String[lineArr.length];
		for (int i = 0; i < lineArr.length; i++) {
			nameArr[i] = lineArr[i].getName();
		}
		return Arrays.deepToString(nameArr);
	}

	public boolean equals(TrainLine line2) {

		// check for equality of each station
		TrainStation current = this.leftTerminus;
		TrainStation curr2 = line2.leftTerminus;

		try {
			while (current != null) {
				if (!current.equals(curr2))
					return false;
				else {
					current = current.getRight();
					curr2 = curr2.getRight();
				}
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public TrainStation getLeftTerminus() {
		return this.leftTerminus;
	}

	public TrainStation getRightTerminus() {
		return this.rightTerminus;
	}
}

//Exception for when searching a line for a station and not finding any station of the right name.
class StationNotFoundException extends RuntimeException {
	String name;

	public StationNotFoundException(String n) {
		name = n;
	}

	public String toString() {
		return "StationNotFoundException[" + name + "]";
	}
}
