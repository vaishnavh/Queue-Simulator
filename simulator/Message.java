package simulator;

import simulator.Log.MessageType;
import chrono.Time;

public class Message<Content> {
	private Counter<Content> counter;
	private QueueElement<Content> element;
	private Time timeStamp;
	private int transacted, left;

	private MessageType messageType;

	protected void setTime(Time timeStamp) {
		this.timeStamp = timeStamp;
	}

	protected Message(QueueElement<Content> element,
			MessageType messageType) {
		this.element = element;
		this.messageType = messageType;
	}

	protected Message(QueueElement<Content> element, Counter<Content> counter, MessageType messageType) {
		this.element = element;
		this.messageType = messageType;
		this.counter = counter;
		
	}

	protected Message(Counter<Content> counter, MessageType messageType) {
		this.counter = counter;
		this.messageType = messageType;
	}

	protected Message(Counter<Content> counter, int transacted, int left,
			MessageType messageType) {
		this.counter = counter;
		this.transacted = transacted;
		this.left = left;
		this.messageType = messageType;
	}

	// protected enum MessageType{
	// JOIN_EXIT_QUEUE, COUNTER_ASSIGNED, LEAVE_TRANSACTION, FREED, DIRECT_EXIT,
	// ACTION, PREPARED, PARTIAL_LOSS, COMPLETE, EXIT_READY, CLOSED,
	// QUEUE_ENTER,
	// FINISH_TRANSACTION, LEFT
	// }

	private String toMessage() {
		switch (messageType) {
		case JOIN_EXIT_QUEUE: 		return this.element.toString()+" joins exit queue";		
		case COUNTER_ASSIGNED: return this.element.toString()+" assigned "+this.counter.toString();
		case LEAVE_TRANSACTION: return this.element.toString()+" transacted "+this.transacted+" and heads to exit queue";
		case FREED: return this.counter.toString()+" is now free";
		case PREPARED: return this.element.toString()+" is generated";
		case DIRECT_EXIT: return this.element.toString()+" heads to exit queue as coutners are closed";
		case PARTIAL_LOSS: return this.counter.toString()+" loses "+this.transacted+" and is left with nothing; partial transaction";
		case COMPLETE: return this.counter.toString()+" loses "+this.transacted+" and is left with "+this.left;
		case EXIT_READY: return this.element.toString()+" is at head of exit queue";
		case CLOSED: return this.counter.toString()+" is closed";
		case QUEUE_ENTER: return this.element.toString()+" has entered";
		case FINISH_TRANSACTION: return this.element.toString()+" has finished transaction with "+this.counter.toString();
		case LEFT: return this.element.toString()+" has left";
		}
		return "";
	}
	
	public String toString(){
		return this.timeStamp.toString() + " -- "+this.toMessage();
	}
}
