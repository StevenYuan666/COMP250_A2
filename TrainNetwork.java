public class TrainNetwork {
	final int swapFreq = 2;
	TrainLine[] networkLines;

    public TrainNetwork(int nLines) {
    	this.networkLines = new TrainLine[nLines];
    }
    
    public void addLines(TrainLine[] lines) {
    	this.networkLines = lines;
    }
    
    public TrainLine[] getLines() {
    	return this.networkLines;
    }
    
    public void dance() {
    	System.out.println("The tracks are moving!");
    	
    	//use a for loop to iterate all lines in the array
    	for(int i = 0; i < this.getLines().length; i++) {
    		this.getLines()[i].shuffleLine();
    	}
    }
    
    public void undance() {
    	
    	//use a for loop to iterate all line in the array
    	for(int i = 0; i < this.getLines().length; i++) {
    		this.getLines()[i].sortLine();
    	}
    }
    
    public int travel(String startStation, String startLine, String endStation, String endLine) {
    	
    	int hoursCount = 0;
    	System.out.println("Departing from "+startStation);
    	
    	try {
    	//use this variable to store the current line.
    	TrainLine curLine = this.getLineByName(startLine); 
    	//use this variable to store the current station. 
    	TrainStation curStation= curLine.findStation(startStation);
    	//create a new variable to store the previous station
    	
    	//create a new variable to store the previous station
    	TrainStation previous = null;
    	while(!(curStation.getName().equals(endStation) && curLine.getName().equals(endLine))){
        	
    		if(hoursCount == 168) {
    			System.out.println("Jumped off after spending a full week on the train. Might as well walk.");
    			return hoursCount;
    		}
    		
    		//prints an update on your current location in the network.
	    	System.out.println("Traveling on line "+curLine.getName()+":"+curLine.toString());
	    	System.out.println("Hour "+hoursCount+". Current station: "+curStation.getName()+" on line "+curLine.getName());
	    	System.out.println("=============================================");
	    	
	    	TrainStation nextStation = curLine.travelOneStation(curStation, previous);
        	previous = curStation;
        	curStation = nextStation;
        	curLine = curStation.getLine();
        	
    		//keep track of the number of stations visited
    		hoursCount++;
    		
    		if(hoursCount % 2 == 0 && hoursCount != 0) {
    			this.dance();
    		}
    	}
	    	
	    	System.out.println("Arrived at destination after "+hoursCount+" hours!");
	    	return hoursCount;
    	}
    		catch(StationNotFoundException e) {
    			return 168;
    		}
    		catch(LineNotFoundException e) {
    			return 168;
    		}
    }
    	
    
    
    //you can extend the method header if needed to include an exception. You cannot make any other change to the header.
    public TrainLine getLineByName(String lineName){
    	//check if the lineName is in the line array
    	for(int i = 0; i < this.getLines().length; i++) {
    		
    		//if there is a line with input name, then return it
    		if(this.getLines()[i].getName().equals(lineName)) {
    			return this.getLines()[i];
    		}
    	}
    	
    	//throw an exception otherwise
    	throw new LineNotFoundException("The Line with input name is not found");
    }
    
  //prints a plan of the network for you.
    public void printPlan() {
    	System.out.println("CURRENT TRAIN NETWORK PLAN");
    	System.out.println("----------------------------");
    	for(int i=0;i<this.networkLines.length;i++) {
    		System.out.println(this.networkLines[i].getName()+":"+this.networkLines[i].toString());
    		}
    	System.out.println("----------------------------");
    }
}

//exception when searching a network for a LineName and not finding any matching Line object.
class LineNotFoundException extends RuntimeException {
	   String name;

	   public LineNotFoundException(String n) {
	      name = n;
	   }

	   public String toString() {
	      return "LineNotFoundException[" + name + "]";
	   }
	}