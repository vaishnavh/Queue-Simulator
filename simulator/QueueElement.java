package simulator;

import simulator.Log.MessageType;
import chrono.Time;



public class QueueElement<Content>{

	//Refer abstract class for explanation 
	
	private QueueSimulator<Content> queueSimulator;
	private int quantity;
	private int transacted;
	private int key;
	private Time startTime; 
	private Content content;
	private void resetStartTime(Time t){
		this.startTime = new Time(t);
	}
	
	
	protected QueueElement(QueueSimulator<Content> queueSimulator, int key, int quantity, Content content){
		this.key = key;
		this.quantity = quantity;
		this.content = content;
		this.queueSimulator = queueSimulator;
	}	
	
	protected void beginTransaction(Time t){
		this.resetStartTime(t);			
	}
	
	protected void finishTransaction(int transacted){		
		this.transacted = transacted;
		this.queueSimulator.getLog().enter(new Message<Content>(this,MessageType.LEAVE_TRANSACTION));
		}
	
	protected void getReadyToExit(Time t){
		this.resetStartTime(t);
	}
	
	protected void enter(){
		//Log.enter(this.toString()+"");
	}

	protected int getQuantity(){
		return quantity;
	}
	
	protected Time getStartTime(){
		return startTime;
	}
	public String toString(){
		if(content!=null)
		return "Element <" + this.key + ", "+this.quantity+">"+" containing "+ this.content.toString();
		else
			return "Element <" + this.key + ", "+this.quantity+">"+" containing NULL ";
	}
}
