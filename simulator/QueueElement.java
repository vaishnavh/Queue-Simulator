package simulator;

import simulator.Log.MessageType;
import chrono.Time;


//An object that is managed by the simulator; it transacts with the counter and
//carries some contents in it as specified by the user
public class QueueElement<Content>{

	//Refer abstract class for explanation 
	
	private QueueSimulator<Content> queueSimulator;
	private int quantity;
	private int transacted;
	private int key;
	private Time startTime; //When the transaction began
	private Content content;
	
	//Sets the start time of the object
	private void resetStartTime(Time t){
		this.startTime = new Time(t);
	}
	
	//Initialize
	protected QueueElement(QueueSimulator<Content> queueSimulator, int key, int quantity, Content content){
		this.key = key;
		this.quantity = quantity;
		this.content = content;
		this.queueSimulator = queueSimulator;
	}	
	
	//marks beginning of a transaction by resettting start time
	protected void beginTransaction(Time t){
		this.resetStartTime(t);			
	}
	
	//marks end of transaction by leaving a message. Movement to exit queue is managed by Simulator class
	protected void finishTransaction(int transacted){		
		this.transacted = transacted;
		this.queueSimulator.getLog().enter(new Message<Content>(this,MessageType.LEAVE_TRANSACTION));
		}
	
	
	//Marks reach of beginning of exit queue by setting time
	protected void getReadyToExit(Time t){
		this.resetStartTime(t);
	}
	
	
	//marks entry
	protected void enter(){
		//Log.enter(this.toString()+"");
	}

	//How much goods does this carry?
	protected int getQuantity(){
		return quantity;
	}
	
	
	//When did its current activity start
	protected Time getStartTime(){
		return startTime;
	}
	
	
	//How much was it able to transact?
	protected int getTransacted(){
		return this.transacted;
	}
	
	
	//Get unique ID of this object
	protected int getKey(){
		return this.key;
	}
	
	
	//What is it carrying?
	protected Content getContent(){
		return this.content;
	}
	
	
	//Format this object as a string
	public String toString(){
		if(content!=null)
		return "Element <" + this.key + ", "+this.quantity+">"+" containing "+ this.content.toString();
		else
			return "Element <" + this.key + ", "+this.quantity+">"+" containing NULL ";
	}
}
