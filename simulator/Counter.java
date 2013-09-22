package simulator;

import simulator.Log.MessageType;


//A counter object containing some quantity to be transacted; is always in state FREE, BUSY or CLOSED

public class Counter<Content> extends AbstractCounter<Content> {
	protected static enum CounterState {
		FREE, CLOSED, BUSY
	} //state of the counter

	private CounterState state;
	private CounterSet<Content> counterSet;
	private QueueElement<Content> element;
	
	//Makes a transaction for a request of atmost reductionAmount based on how much is available
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
			//Enough available; empty as much as the request
			transacted = reductionAmount;
			this.quantity -= reductionAmount;
			this.counterSet.getQueueSimulator().getLog().enter(new Message<Content>(this, reductionAmount, quantity, MessageType.COMPLETE));
		}
		return transacted;
	}

	//Initialize
	protected Counter(CounterSet<Content> counterSet, int key) {
		state = CounterState.FREE; // When we run first update it will close
		quantity = 0;
		this.key = key;
		this.counterSet = counterSet;
	}

	//Set how much the counter has
	protected void setQuantity(int quantity) {
												
		state = CounterState.FREE;
		this.quantity = quantity;
	}

	//Frees the counter of the transacting element
	protected void freeCounter() {
		element = null;
		if(quantity>0){
			this.state = CounterState.FREE;
			this.counterSet.getQueueSimulator().getLog().enter(new Message<Content>(this, MessageType.FREED));
		}else{
			this.updateState();
		}
	}

	//Checks whether there are any more goods left; if no, it closes the counter
	protected void updateState() {
		if (quantity == 0) {
			if (this.state != CounterState.CLOSED) {

				this.state = CounterState.CLOSED;
				this.counterSet.getQueueSimulator().getLog().enter(new Message<Content>(this, MessageType.CLOSED));
			}

		}
	}

	
	//Are there objects left?
	protected boolean isFree() {
		return (state == CounterState.FREE);
	}

	//Is the counter closed?
	protected boolean isClosed() {
		return (state == CounterState.CLOSED);
	}

	
	//Returns how much is avaliable at the counter  for the given request
	protected int available(int limit) {
		if (quantity <= limit) {
			return quantity;
		}
		return limit;
	}
	
	
	//What state is the counter at
	protected CounterState getState(){
		return this.state;
	}

	//Complete transaction and return how much was transacted
	protected int finishTransaction(int requiredQuantity) {
		int transacted = reduceQuantity(requiredQuantity);//How much transacted
		freeCounter(); //free the counter
		updateState();
		return transacted;
	}

	//Assigns an element to the counter and makes the counter busy
	protected void occupy(QueueElement<Content> element){
		this.state = CounterState.BUSY;
		this.element = element;
	}

	
	//Which element is transacting currently?
	protected QueueElement<Content> getElement(){
		return this.element;
	}
	
	
	public String toString() {
		String ret = "Counter <" + key + ", " + this.quantity + ">";
		if(element!=null){
			ret = ret + " <--> "+element.toString();
		}
		return ret;
	}
}
