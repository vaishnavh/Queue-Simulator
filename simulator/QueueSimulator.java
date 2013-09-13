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


//Remove unnecesaary calls like elementAt
//Commenting
//Documentation
//Each process of input write separate function
//Add testing in dcumentation
//remove 

public class QueueSimulator<Content> extends AbstractQueueSimulator<Content> {
	private  int key; //Unique id
	private  ProbabilityDistribution pd;
	private Scanner input = new Scanner(System.in);
	private  Clock clock = new Clock();
	private  ActionQueue<Content> actions; //A stored of actions
	private  CounterSet<Content> counters;
	private  EntryQueue<Content> entry = new EntryQueue<Content>(this);
	private  ExitQueue<Content> exit = new ExitQueue<Content>(this);
	private Log<Content> log = new Log<Content>(this);
	
	
	private void setPopulationRate(){
		double populationRate = 0;
		while(populationRate<=0){
			//Get only +ve values
			System.out.print("Enter the rate at which entry queue is populated : ");
			populationRate = input.nextDouble();
		}
		entry.setPopulationRate(new Double(populationRate));
	}
	
	
	
	
	private  void setCounters(){
		//Gets input for the counter size and quantities and sets them
		int size = -1;
		while(size<0){
			System.out.print("Enter the number of counters (>0) : ");
			size = input.nextInt();
		}
		int[] quantity = new int[size];
		for(int  i=0; i<size; i++){
			quantity[i] = -1;
			while(quantity[i]<0){
				System.out.print("Enter the quantity of counter "+(i+1)+" (>=0) : ");
				quantity[i] = input.nextInt();
			}
		}
		this.counters.setCounters(size, quantity);
	}
	
	
	private  void readProbability() throws IOException{
		//Reads probab function
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
	@Override
	protected void initialize() throws IOException {
		// TODO Auto-generated method stub
		key = 1;
		counters = new CounterSet<Content>(this);
		actions = new ActionQueue<Content>(this);
		this.readInput();		
		input.close();
	}

	@Override
	protected void start() {
		// TODO Auto-generated method stub
		entry.start(); // Adds something to the action list		
		while(this.clock.isStopped() == false){
			nextStep();
			//When can the next check point be? Assume all actions uptil this time have been done
			// If some counter is done transferring : would've been stored in action list
			// If some thing is done waiting in exit queue - woould'be been added (Check ExitQueue.getReadyToExit)
			// If some thing is being added to queue - would have been added to action list (Check EntryQueue.prepareNewElement)		

			Time next = actions.nextActionTime();
			clock.nextStep(next);
		}
		log.printAll();
		System.out.println("Done");
	}

	
	
	
	@Override
	protected void readInput() throws IOException {
		// TODO Auto-generated method stub
		this.readProbability();		// Read probability distribution	
		this.setPopulationRate();// Read populationRate
		this.setCounters(); // Read counterSize and quantities		
	}

	@Override
	protected void nextStep() {
		// TODO Auto-generated method stub
		// Refresh counter states everytime
		
		counters.refresh(); //Update states of counters
		actions.performEndTransactions(); //Perform transactions
		actions.performActions(); //Start transactions and perform other actions
		
	}

	protected  Time getTime() {
		return clock.getTime();
	}

	protected  ActionQueue<Content> getActionQueue() {
		return actions;
	}

	protected  Double getPopulationInterval() {
		return entry.getPopulationInterval();
	}

	protected Content getContent(){
		return null;
	}
	
	protected  QueueElement<Content> getNewElement() {
		//Create new element with random duration
		//Change this instantiation if you want
		QueueElement<Content> q = new QueueElement<Content>(this, key++, pd.sample(), null); 
		return q;
	}
	
	protected  void allowNewElement(QueueElement<Content> element){
		entry.addElement(element);
	}
	
	protected  void sendOff(){
		exit.move(); //let an element leave
	}
	
	protected  void sendToExitQueue(QueueElement<Content> element){
		exit.addElement(element); //finish trasnaction and sending an element to exit queue
	}

	
	protected  void beginTransaction(QueueElement<Content> element, Counter<Content> counter){
		//Begin transaction between element and counter
		log.enter(new Message<Content>(element, counter, MessageType.COUNTER_ASSIGNED));
		element.beginTransaction(clock.getTime());
		actions.addAction(new Action<Content>(this.getActionQueue(), counter, element));
	}
	
	protected  void endTransaction(QueueElement<Content> element, Counter<Content> counter){
		//Finished trasaction; send to exit queue
		int transacted = counter.finishTransaction(element.getQuantity());
		element.finishTransaction(transacted);
		sendToExitQueue(element);
	}
	
	protected  void prepareForExit(QueueElement<Content> element){
		//Just came to the head of the exit queue
		element.getReadyToExit(clock.getTime());
		actions.addAction(new Action<Content>(this.getActionQueue(), element, Action.ActionType.LEAVE));
	}
	
	protected  String getTimeStamp(){
		return clock.getTimeStamp();
	}
	
	protected  boolean isOver(){
		return counters.isExhausted();
	}
	
	protected  Counter<Content> getFreeCounter(){
		return counters.getFreeCounter();
	}
	
	protected Log<Content> getLog(){
		return this.log;
	}
}
