//TODO
//Make interfaces
//Make paremterized 
//probability distribution
//make population rate a pair of variables
//Implement actions
//Change visibility  modes
public class QueueSimulator implements Simulator<QueueElement, Clock>{
	private static int key, countersSize;
	public static Clock clock = new Clock();
	public static Market counters;
	private static ActionQueue actions; 
	private static EntryQueue entry = new EntryQueue();
	private static ExitQueue exit = new ExitQueue();
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		key = 1;
		//Set populationRate
		///Set counters
		//counters.setCounters(counterSize, values); //Take care of values
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		entry.start(); //Adds something to the action list
		
	}

	@Override
	public void readInput() {
		// TODO Auto-generated method stub
		//Read populationRate
		//Read counterSize
		//Read counterQuantities
		//Read probability distribution		
	}

	@Override
	public void nextStep() {
		// TODO Auto-generated method stub
		//Refresh counter states everytime
	}
	
	public static Time getTime(){
		return clock.getTime();
	}
	
	public static ActionQueue getActionQueue(){
		return actions;
	}
	
	public static Double getPopulationInterval(){
		return entry.getPopulationInterval();
	}
	
	public static QueueElement getNewElement(){
		return new QueueElement(key++); //Implement quantity over here extracting from prob distribution function
	}
}
