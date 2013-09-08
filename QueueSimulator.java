//TODO
//Make interfaces
//Make paremterized 
//probability distribution
//make population rate a pair of variables
//Implement actions
//Change visibility  modes
//Convert attribute accesses to functions

//Remove unnecesaary calls like elementAt
public class QueueSimulator implements Simulator<QueueElement, Clock> {
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
		// Set populationRate
		// /Set counters
		counters = new Market();
		actions = new ActionQueue();
		
		int[] values = new int[5]; 
		values[0] = values[1] = values[2] = values[3] = values[4] = 3;
		System.out.print(values.toString());
		counters.setCounters(5,values); //Take care of values
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		entry.start(); // Adds something to the action list
		while(this.getTime()!=null && this.key < 20){
			nextStep();
			clock.nextStep();
		}
	}

	@Override
	public void readInput() {
		// TODO Auto-generated method stub
		// Read populationRate
		// Read counterSize
		// Read counterQuantities
		// Read probability distribution
		entry.setPopulationRate(new Double(1));
		//counterSize = 5;
		
	}

	@Override
	public void nextStep() {
		// TODO Auto-generated method stub
		// Refresh counter states everytime
		
		counters.refresh();
		actions.performEndTransactions();
		actions.performActions();
		
	}

	public static Time getTime() {
		return clock.getTime();
	}

	public static ActionQueue getActionQueue() {
		return actions;
	}

	public static Double getPopulationInterval() {
		return entry.getPopulationInterval();
	}

	public static QueueElement getNewElement() {
		QueueElement q = new QueueElement(key++); // Implement quantity over here
										// extracting from prob distribution
										// function
		q.setQuantity(1);
		return q;
	}
	
	public static void allowNewElement(QueueElement element){
		entry.addElement(element);
	}
	
	public static void sendOff(){
		exit.move();
	}
	
	public static void sendToExitQueue(QueueElement element){
		exit.addElement(element);
	}
}
