package simulator;
import java.util.LinkedList;
import java.util.Queue;

import simulator.Log.MessageType;

public class EntryQueue<Content> extends SimQueue<QueueElement<Content>>{
	private Queue<QueueElement<Content> > entryQueue = new LinkedList<QueueElement<Content> >();
	private Double populationRate;
	private Double populationInterval; //at what intervals should we populate?
	private QueueSimulator<Content> queueSimulator;
	
	private void prepareNewElement() {
		// Adds a todo action to the actions list making a mention
		// of a new element to be added sometime later
		
		QueueElement<Content> newElement = this.queueSimulator.getNewElement();
		this.queueSimulator.getLog().enter(new Message<Content>(newElement, MessageType.PREPARED));
		this.queueSimulator.getActionQueue().addAction(
				new Action<Content>(this.queueSimulator.getActionQueue(), newElement, Action.ActionType.ENTER));
		
	}
	
	protected EntryQueue(QueueSimulator<Content> queueSimulator){
		this.queueSimulator = queueSimulator;
	}
	protected void move() {
		//Probe the head and let it take some counter that is free
		QueueElement<Content> head = entryQueue.peek();
		if (head != null) {
			if(this.queueSimulator.isOver()){
				//None free; all closed : send head directly to exit queue
				this.queueSimulator.getLog().enter(new Message<Content>(head, MessageType.DIRECT_EXIT));
				entryQueue.poll();
				this.queueSimulator.sendToExitQueue(head);
				
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

	protected void start() {
		this.prepareNewElement();
	}

	protected void addElement(QueueElement<Content> element) {
		//Addnew element to queue
		if (this.entryQueue.peek() == null) {
			entryQueue.add(element);
			move(); //If no other head, then check if it can transact
		} else {
			entryQueue.add(element);
		}
		if(!this.queueSimulator.isOver()) //If simulation yet not over,
			//prepare next element to be addd
			this.prepareNewElement();
		
	}

	protected void setPopulationRate(Double populationRate) {
		this.populationRate = populationRate;
		this.populationInterval = new Double(1) / this.populationRate;
	}

	protected Double getPopulationInterval() {
		return this.populationInterval;
	}

	
}
