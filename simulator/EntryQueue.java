package simulator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import simulator.Log.MessageType;

//Manages entering elements in a queue
public class EntryQueue<Content> extends SimQueue<QueueElement<Content>>{
	private Queue<QueueElement<Content> > entryQueue = new LinkedList<QueueElement<Content> >();
	private Double populationRate;
	private Double populationInterval; //at what intervals should we populate?
	private QueueSimulator<Content> queueSimulator;
	
	
	// Adds a todo action to the actions list making a mention
	// of a new element to be added sometime later to the entry queue
	private void prepareNewElement() {

		
		QueueElement<Content> newElement = this.queueSimulator.getNewElement();
		this.queueSimulator.getLog().enter(new Message<Content>(newElement, MessageType.PREPARED));
		this.queueSimulator.getActionQueue().addAction(
				new Action<Content>(this.queueSimulator.getActionQueue(), newElement, Action.ActionType.ENTER));
		
	}
	
	
	//initialize
	protected EntryQueue(QueueSimulator<Content> queueSimulator){
		this.queueSimulator = queueSimulator;
	}
	
	//Check whether front element can be assigned to a counter and let it move if yes
	protected void move() {
		//Probe the head and let it take some counter that is free
		QueueElement<Content> head = entryQueue.peek();
		if (head != null) {
			if(this.queueSimulator.isOver()){
				//None free; all closed : send head directly to exit queue
				this.queueSimulator.getLog().enter(new Message<Content>(head, MessageType.DIRECT_EXIT));
				entryQueue.poll();
				this.queueSimulator.sendToExitQueue(head);
				move();
			}else{
				Counter<Content> counter = this.queueSimulator.getFreeCounter();
				if (counter != null) {
					//Free counter exists
					this.queueSimulator.beginTransaction(head, counter);
					this.entryQueue.poll();
					move(); //Rcursively check next element
				}
			}
		}
	}

	//Start the queue by preparing first element
	protected void start() {
		this.prepareNewElement();
	}

	//Addnew element to queue
	protected void addElement(QueueElement<Content> element) {		
		
		entryQueue.add(element);
		move();

		if(!this.queueSimulator.isOver()) //If simulation yet not over,
			//prepare next element to be addd
			this.prepareNewElement();
		
	}

	//Set rate at which queue is populated
	protected void setPopulationRate(Double populationRate) {
		this.populationRate = populationRate;
		this.populationInterval = new Double(1) / this.populationRate;
	}

	//At intervals is a new elemet to be added?
	protected Double getPopulationInterval() {
		return this.populationInterval;
	}

	public String toString(){
		String ret = "";
		Iterator<QueueElement<Content>> iterator = entryQueue.iterator();
		while(iterator.hasNext()){		
			String append = iterator.next().toString();
			//System.out.println(append);
			ret  = ret+"["+append+"] ";;
		}
		if(ret.compareTo("")==0){
			ret = "Empty Entry Queue";
		}
		return ret;
	}
	
}
