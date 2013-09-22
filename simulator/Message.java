package simulator;

import simulator.Log.MessageType;
import chrono.Time;


// A packet of information specifying a log entry
public class Message<Content> {
	private Counter<Content> counter;
	private QueueElement<Content> element;
	private Time timeStamp;
	private int transacted, left;

	private MessageType messageType;

	//Set time to which this message corresponds to
	protected void setTime(Time timeStamp) {
		this.timeStamp = timeStamp;
	}

	//Initialize a message specific to an element only
	protected Message(QueueElement<Content> element,
			MessageType messageType) {
		this.element = element;
		this.messageType = messageType;
	}

	//Initialize a message specific to an element counter pair
	protected Message(QueueElement<Content> element, Counter<Content> counter, MessageType messageType) {
		this.element = element;
		this.messageType = messageType;
		this.counter = new Counter<Content>(null, counter.key); 
		this.counter.setQuantity(counter.quantity);

	}

	//Initialize message specific to a counter
	protected Message(Counter<Content> counter, MessageType messageType) {
		this.counter = new Counter<Content>(null, counter.key); 
		this.counter.setQuantity(counter.quantity);
		this.messageType = messageType;
	}

	
	//Initialize message specific to a counter, along with data about how much was transacted
	protected Message(Counter<Content> counter, int transacted, int left,	MessageType messageType) {
		this.counter = new Counter<Content>(null, counter.key); 
		this.counter.setQuantity(counter.quantity);
		this.transacted = transacted;
		this.left = left;
		this.messageType = messageType;
	}

	//Display string
	private String toMessage() {

		switch (messageType) {
		case JOIN_EXIT_QUEUE: 		return this.element.toString()+" joins exit queue";		
		case COUNTER_ASSIGNED: return this.element.toString()+"leaves entry queue and is assigned "+this.counter.toString();
		case LEAVE_TRANSACTION: return this.element.toString()+" transacted "+this.element.getTransacted()+" and heads to exit queue";
		case FREED: return this.counter.toString()+" is now free";
		case PREPARED: return this.element.toString()+" is generated";
		case DIRECT_EXIT: return this.element.toString()+"leaves entry queue and heads to exit queue as counters are closed";
		case PARTIAL_LOSS: return this.counter.toString()+" loses "+this.transacted+" and is left with nothing; partial transaction";
		case COMPLETE: return this.counter.toString()+" loses "+this.transacted+" and is left with "+this.left;
		case EXIT_READY: return this.element.toString()+" is at head of exit queue";
		case CLOSED: return this.counter.toString()+" is closed";
		case QUEUE_ENTER: return this.element.toString()+" has entered";
		case FINISH_TRANSACTION: return this.element.toString()+" has finished transaction with "+this.counter.toString();
		case LEFT: return this.element.toString()+"has left";
		}
		return "";
	}
	
	
	//Message corresponds to entry queue?
	protected boolean isEntryQueue(){
		switch(this.messageType){
		case COUNTER_ASSIGNED: 
		case DIRECT_EXIT: 
		case QUEUE_ENTER: return true;
		}
		return false;
	}
	
	//message corresponds to exit queue entry?
	protected boolean isExitQueue(){
		switch(this.messageType){
		case JOIN_EXIT_QUEUE:
		case EXIT_READY:
		case LEFT:	return true;
		}
		return false;
	}
	
	
	//message correspnds to set of counters?
	protected boolean isCounterSet(){
		switch(this.messageType){
		case COUNTER_ASSIGNED:
		case FREED:
		case CLOSED:
		case FINISH_TRANSACTION:
		case PARTIAL_LOSS:
		case COMPLETE: return true;
		}
		return false;
	}
	
	
	//Which counter does this message correspond to?
	protected Counter getCounter(){
		return this.counter;
	}

	//Which element does this message correspond to?
	protected QueueElement<Content> getElement(){
		return this.element;
	}
	
	//Does this correspond to an elemet?
	protected boolean isElement(){
		return this.element != null;
	}
	
	public String toString(){
		return this.timeStamp.toString() + " -- "+this.toMessage();
	}
}
