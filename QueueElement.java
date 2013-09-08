


public class QueueElement {
	private int quantity;
	public int key;
	private Time startTime; //From transaction
	private Counter counter;
	public static enum ElementState{
		WAIT, TRANSACT, EXIT
	}
	private ElementState state;
	
	
	public void setQuantity(int quantity){
		this.quantity = quantity;
	}
	
	public QueueElement(int key){
		this.key = key;
	}
	public int getQuantity(){
		return this.quantity;
	}
	
	public Time getStartTime(){
		return this.startTime;
	}
	
	public void resetStartTime(){
		this.startTime = QueueSimulator.getTime();
	}
	public void joinQueue(){
		
	}
	private void performActions(){
		
	}
	
	public void beginTransaction(Counter counter){
		this.resetStartTime();
		this.counter = counter;
		QueueSimulator.getActionQueue().addAction(new Action(counter, this));				
		
	}
	
	public void finishTransaction(){
		int transferred = counter.finishTransaction(quantity);
		//Print some output calling Display class
		//Add to exit queue
		Log.enter("Sending "+ this.toString());
		QueueSimulator.sendToExitQueue(this);
	}
	
	public void getReadyToExit(){
		this.resetStartTime();
		QueueSimulator.getActionQueue().addAction(new Action(this, Action.ActionType.LEAVE));
	}
	
	public void enter(){
		Log.enter("Adding" + this.toString());
		QueueSimulator.allowNewElement(this);
	}
	
	public String toString(){
		return "Element <" + this.key + ", "+this.quantity+">";
	}
}
