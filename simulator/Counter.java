package simulator;

import simulator.Log.MessageType;

public class Counter<Content> extends AbstractCounter<Content> {
	protected static enum CounterState {
		FREE, CLOSED, BUSY
	} //state of the counter

	private CounterState state;
	private CounterSet<Content> counterSet;
	
	private int reduceQuantity(int reductionAmount) { 
		//Removes as much as possible as perthe request
		//Returns amount that was transacted
		int transacted;
		if (this.quantity < reductionAmount) {
			//Too little available : empty the whole
			this.counterSet.getQueueSimulator().getLog().enter(new Message<Content>(this, quantity, 0, MessageType.PARTIAL_LOSS));
			transacted = quantity;
			this.quantity = 0;
		} else {
			
			transacted = reductionAmount;
			this.quantity -= reductionAmount;
			this.counterSet.getQueueSimulator().getLog().enter(new Message<Content>(this, reductionAmount, quantity, MessageType.COMPLETE));
		}
		return transacted;
	}

	protected Counter(CounterSet<Content> counterSet, int key) {
		state = CounterState.FREE; // When we run first update it will close
		quantity = 0;
		this.key = key;
		this.counterSet = counterSet;
	}

	protected void setQuantity(int quantity) { // To initialize value of amount
												// of goods present in a counter
		state = CounterState.FREE;
		this.quantity = quantity;
	}

	//Refer abstract class for what these do
	protected void freeCounter() {
		if(quantity>0){
			this.state = CounterState.FREE;
			this.counterSet.getQueueSimulator().getLog().enter(new Message<Content>(this, MessageType.FREED));
		}else{
			this.updateState();
		}
	}

	protected void updateState() {
		if (quantity == 0) {
			if (this.state != CounterState.CLOSED) {
				//If just closed, print it
				this.state = CounterState.CLOSED;
				this.counterSet.getQueueSimulator().getLog().enter(new Message<Content>(this, MessageType.CLOSED));
			}

		}
	}

	protected boolean isFree() {
		return (state == CounterState.FREE);
	}

	protected boolean isClosed() {
		return (state == CounterState.CLOSED);
	}

	protected int available(int limit) {
		if (quantity <= limit) {
			return quantity;
		}
		return limit;
	}

	//Complete transaction and return how much was transacted
	protected int finishTransaction(int requiredQuantity) {
		int transacted = reduceQuantity(requiredQuantity);//How much transacted
		freeCounter(); //free the counter
		updateState();
		return transacted;
	}

	public String toString() {
		return "Counter <" + key + ", " + this.quantity + ">";
	}
}
