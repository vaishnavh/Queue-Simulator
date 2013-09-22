package simulator;
import java.io.IOException;
import java.util.Scanner;

import chrono.Clock;
import chrono.Time;

import probability.EnumeratedInteger;
import probability.Poisson;
import probability.ProbabilityDistribution;
import probability.Uniform;
import simulator.Log.MessageType;

//TODO
// chmod 777 ./lib/commons-math3-3.2.jar
// javac -cp .:lib/commons-math3-3.2.jar Main.java
// java -cp .:lib/commons-math3-3.2.jar Main

//Manages the Entry and Exit queues, Counters and Elements
public class QueueSimulator<Content> extends AbstractQueueSimulator<Content> {
	
	private  int key; //Unique id to keep track of element generated
	private  ProbabilityDistribution pd;
	private Scanner input = new Scanner(System.in);
	private  Clock clock = new Clock(); //keep track of time
	private  ActionQueue<Content> actions; //A store for actions to be done
	private  CounterSet<Content> counters;
	private  EntryQueue<Content> entry = new EntryQueue<Content>(this);
	private  ExitQueue<Content> exit = new ExitQueue<Content>(this);
	private Log<Content> log = new Log<Content>(this); //a log of what happened
	

	//Set the rate at which entry queue is to be populated
	private void setPopulationRate(){
		double populationRate = 0;
		while(populationRate<=0){
			//Get only +ve value
			System.out.print("Enter the rate at which entry queue is populated : ");
			populationRate = input.nextDouble();
		}
		entry.setPopulationRate(new Double(populationRate));
	}
		

	//Gets input for the counters : the no. of counters, and their quantities and sets these values
	private  void setCounters(){
		int size = -1;
		while(size<0){
			//Get only nonnegative number of counters
			System.out.print("Enter the number of counters (>0) : ");
			size = input.nextInt();
		}
		
		int[] quantity = new int[size];
		for(int  i=0; i<size; i++){
			//Get quantitiy for each counter
			quantity[i] = -1;
			while(quantity[i]<0){
				//Get only positive quantities
				System.out.print("Enter the quantity of counter "+(i+1)+" (>=0) : ");
				quantity[i] = input.nextInt();
			}
		}
		this.counters.setCounters(size, quantity);
	}
	
	//Reads probability function and sets it
	private  void readProbability() throws IOException{
		System.out.print("Enter the probablity distribution of the time duration requests : ");
		String distribution = input.next();
	
		if(distribution.contains("Poisson")){
			this.pd = new Poisson();
		}else if(distribution.contains("Uniform")){
			this.pd = new Uniform();
		}else if(distribution.contains("Enumerated")){
			this.pd = new EnumeratedInteger();
		}else{
			input.close();
			System.err.println("Distribution not found. Exiting.");
			System.exit(0);
		}
		pd.initialize();
	}
	

	//Initialize stuff for the simulator : namely, create objects for counters, action storage and user specs
	@Override
	public void initialize() throws IOException {
		// TODO Auto-generated method stub
		key = 1;
		counters = new CounterSet<Content>(this);
		actions = new ActionQueue<Content>(this);
		this.readInput();		
		input.close();
	}

	//Activate the simulator
	@Override
	public void start() {
		// TODO Auto-generated method stub
		entry.start(); // Adds something to the action list		
		while(this.clock.isStopped() == false){
			//System.out.println(this.entry.toString());
			nextStep();
			//When can the next check point be? Assume all actions uptil this time have been done
			// If some counter is done transferring : would've been stored in action list
			// If some thing is done waiting in exit queue - woould'be been added (Check ExitQueue.getReadyToExit)
			// If some thing is being added to queue - would have been added to action list (Check EntryQueue.prepareNewElement)		

			Time next = actions.nextActionTime();
			clock.nextStep(next); //Take next step
		}
	}

	//Get user specification : population rate, probability and counter specs
	@Override
	protected void readInput() throws IOException {
		// TODO Auto-generated method stub
		this.readProbability();		// Read probability distribution	
		this.setPopulationRate();// Read populationRate
		this.setCounters(); // Read counterSize and quantities		
	}

	
	//Perform next step of the simulator : update counter state, perform pending actions and determine
	//next set of actions
	@Override
	protected void nextStep() {
		// TODO Auto-generated method stub
		// Refresh counter states everytime
		
		counters.refresh(); //Update states of counters
		actions.performEndTransactions(); //Perform transactions
		actions.performActions(); //Start transactions and perform other actions
		
	}

	
	//Get current time as a Time object
	protected  Time getTime() {
		return clock.getTime();
	}

	//Get the list of actions yet to be done as an ActionQueue object
	protected  ActionQueue<Content> getActionQueue() {
		return actions;
	}

	
	//Get the interval between two entries in the entry queue
	protected  Double getPopulationInterval() {
		return entry.getPopulationInterval();
	}

	
	//Implement this function to get input from the user; specifies
	//what is carried by the queue Element
	protected Content getContent(){
		return null;
	}
	
	
	//Create new element with random duration; by default this element will
	//carry no 'content'
	protected  QueueElement<Content> getNewElement() {		
		//Change this instantiation if you want
		QueueElement<Content> q = new QueueElement<Content>(this, key++, pd.sample(), null); 
		return q;
	}
	
	
	//Add new element to entry queue
	protected  void allowNewElement(QueueElement<Content> element){
		entry.addElement(element);
	}
	
	
	//Let an element exit from exit queue
	protected  void sendOff(){
		exit.move(); //let an element leave
	}
	
	
	//Send an element to the exit queue
	protected  void sendToExitQueue(QueueElement<Content> element){
		exit.addElement(element); //finish trasnaction and sending an element to exit queue
	}

	//Assign an element to a specified counter and begin its transaction
	protected  void beginTransaction(QueueElement<Content> element, Counter<Content> counter){
		//Begin transaction between element and counter
		counter.occupy(element);
		log.enter(new Message<Content>(element, counter, MessageType.COUNTER_ASSIGNED));
		element.beginTransaction(clock.getTime());
		actions.addAction(new Action<Content>(this.getActionQueue(), counter, element));
	}
	
	//Finish transaction of an element with a counter and send it to exit queue
	protected  void endTransaction( Counter<Content> counter){
		QueueElement<Content> element = counter.getElement();
		int transacted = counter.finishTransaction(element.getQuantity());
		element.finishTransaction(transacted);
		sendToExitQueue(element);
	}
	
	//Acknowledge that an element has reached beginning of exit queue and is about to leave
	protected  void prepareForExit(QueueElement<Content> element){
		
		element.getReadyToExit(clock.getTime());
		actions.addAction(new Action<Content>(this.getActionQueue(), element, Action.ActionType.LEAVE));
	}
	
	
	//Get current time in string format
	protected  String getTimeStamp(){
		return clock.getTimeStamp();
	}
	
	
	//Are there any counters that are still open?
	protected  boolean isOver(){
		return counters.isExhausted();
	}
	
	
	//Get some counter that is still open and is not busy
	protected  Counter<Content> getFreeCounter(){
		return counters.getFreeCounter();
	}
	
	
	//Get the current state of entry queue
	protected String getEntryQueueState(){
		return this.entry.toString();
	}
	
	
	//Get current state of exit queue
	protected String getExitQueueState(){
		return this.exit.toString();
	}
	
	
	//Get current state of counters
	protected String getCounterSetState(){
		return this.counters.toString();
	}
	
	
	//Get the whole log
	public Log<Content> getLog(){
		return this.log;
	}
	
	
	//Set the probability distribution
	public void setProbability(ProbabilityDistribution pd){
		this.pd = pd;
	}
	
	
	
}
