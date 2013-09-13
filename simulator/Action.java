package simulator;

import simulator.Log.MessageType;
import chrono.Time;

public class Action<Content> {
	//This keeps note of which element is at which counter;
	//Or whether it is about to exit/enter
	private Counter<Content> counter;
	private QueueElement<Content> element;
	private Time time; //When this has to be executed
	private ActionQueue<Content> actionQueue;	
	

	protected static enum ActionType {
		ENTER, END_TRANSACTION, LEAVE
	}

	private ActionType type; //What kind of action

	protected Action(ActionQueue<Content> actionQueue, Counter<Content> counter, QueueElement<Content> element) {
		//Keep note of the fact that t units later the element
		//has to leave for the exit queue and end the transaction. t is the no. of goods
		//spent by the counter
		this.actionQueue = actionQueue;
		this.counter = counter;
		this.element = element;
		this.time = new Time(element.getStartTime());
		time.increment(counter.available(element.getQuantity()));
		type = ActionType.END_TRANSACTION;
	}

	protected Time getTime() {
		return this.time;
	}

	protected Action(ActionQueue<Content> actionQueue, QueueElement<Content> element, ActionType action) { 
		this.actionQueue = actionQueue;
		if (action == ActionType.LEAVE) {
			//The  element leaves the head of the queue t moments later
			// t = capacity of te element
			this.counter = null;
			this.element = element;
			this.type = ActionType.LEAVE;
			this.time = new Time(element.getStartTime()).increment(element.getQuantity());
		} else if (action == ActionType.ENTER) {
			//r moments later this new element will be joining the
			//entry queue
			this.time = new Time(this.getQueueSimulator().getTime())
					.increment(this.getQueueSimulator().getPopulationInterval());
			this.element = element;
			this.type = ActionType.ENTER;
			this.counter = null;
		} 
	}
	
	protected QueueSimulator<Content> getQueueSimulator(){
		return this.actionQueue.getQueueSimulator();
	}
	protected void perform() {
		//Execute the stored action
		Message<Content> message;
		if (this.isEnter()) {
			message = new Message<Content>(this.element, MessageType.QUEUE_ENTER);
			this.getQueueSimulator().getLog().enter(message);
			this.getQueueSimulator().allowNewElement(element);
		} else if (this.isEndTransaction()) {
			message = new Message<Content>(this.element, this.counter, MessageType.FINISH_TRANSACTION);
			System.out.println("Check this"+counter.toString());
			this.getQueueSimulator().getLog().enter(message);
			this.getQueueSimulator().endTransaction(element, counter);
		} else if (this.isLeave()) {
			message = new Message<Content>(this.element, MessageType.LEFT);
			this.getQueueSimulator().getLog().enter(message);
			this.getQueueSimulator().sendOff();
		}
	}

	
	//Boolean functions for actions
	protected boolean isEndTransaction() {
		return (this.type == ActionType.END_TRANSACTION);
	}

	protected boolean isEnter() {
		return (this.type == ActionType.ENTER);
	}

	protected boolean isLeave() {
		return (this.type == ActionType.LEAVE);
	}

	public String toString() {
		if (this.isEnter()) {
			return element.toString() + " is entering queue";
		} else if (this.isEndTransaction()) {
			return element.toString() + " finished transaction with "+ counter.toString();
		} else if (this.isLeave()) {
			return element.toString() + " has left";
		}
		return "";
	}
}
