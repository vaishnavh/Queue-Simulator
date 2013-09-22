package simulator;
import java.util.Vector;

import chrono.Time;



//Manages a queue of actions and performing the actions at correct times
public class ActionQueue<Content> {
	//This is a queue of actions that are to be completed for an object ordered by time
	private Vector<Action<Content>> actions = new Vector<Action<Content>>();
	private QueueSimulator<Content> queueSimulator;	
	
	//Initialize
	protected ActionQueue(QueueSimulator<Content> queueSimulator){
		this.queueSimulator = queueSimulator;
	}

	//given an action inserts it in the queue maintaining the order of the
	//timing
	protected void addAction(Action<Content> action){

		int i;
		//Get position to insert
		for(i=actions.size()-1; i>=0 && actions.elementAt(i).getTime().greaterThan(action.getTime()); i--);
		actions.add(i+1, action);

	}
	

	//Is there no action to do?
	protected boolean isEmpty(){
		return actions.isEmpty();
	}
	
	
	//What time is the next set of actions occuring at?
	protected Time nextActionTime(){
		if(isEmpty()){
			return null;
		}else{
			return actions.elementAt(0).getTime();
		}
	}
	
	//Perform all transaction ending actions corresponding to this time
	protected void performEndTransactions(){
		for(int i=0; i<actions.size() && actions.elementAt(i).getTime().equalTo(this.queueSimulator.getTime());){
			//Traverse for actions corresponding to current time and stop when action for later time is found
			Action<Content> action = actions.elementAt(i);
			if(action.isEndTransaction()){				
				action.perform();
				actions.remove(i);				
				
			}else{
				i++;
			}
		}
	}
	
	
	//Perform all actions for this time
	protected void performActions(){
		for(; 0<actions.size() && actions.elementAt(0).getTime().equalTo(this.queueSimulator.getTime());){		
				Action<Content> action = actions.elementAt(0);
				//Perform and remove
				action.perform();		
				actions.remove(0);
		}
	}
	
	
	//Print actions left
	protected void print(){
		this.queueSimulator.getLog().print("---------  ACTIONS LEFT ---------");
		for(int i=0; i<actions.size(); i++){
			this.queueSimulator.getLog().print(actions.elementAt(i).toString());
		}
		this.queueSimulator.getLog().print("---------------------------------");
	}
	
	
	//To which simulator is this working for?
	protected QueueSimulator<Content> getQueueSimulator(){
		return this.queueSimulator;
	}
}
