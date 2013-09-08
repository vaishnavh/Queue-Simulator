import java.util.Vector;




public class ActionQueue {
	//This is a queue of actions that are to be completed for an object
	//An action is identified by way of a change State
	public Vector<Action> actions = new Vector<Action>();
	
	public void addAction(Action action){
		//given an action inserts it in the queue maintaining the order of the
		//timing
		int i;
		for(i=actions.size()-1; i>=0 && action.time.greaterThan(actions.elementAt(i).time); i--);
		actions.add(i+1, action);
		Log.enter("Added action : "+action.toString());
	}
	

	
	public boolean isEmpty(){
		return actions.isEmpty();
	}
	
	public Time nextActionTime(){
		if(isEmpty()){
			return null;
		}else{
			return actions.elementAt(0).time;
		}
	}
	
	public void performEndTransactions(){
		for(int i=0; i<actions.size() && actions.elementAt(i).time.equalTo(QueueSimulator.getTime());){
			Action action = actions.elementAt(i);
			if(action.isEndTransaction()){				
				action.perform();
				actions.remove(i);				
				Log.enter("Removed action "+action.toString());
			}else{
				i++;
			}
		}
	}
	
	public void performActions(){
		for(; 0<actions.size() && actions.elementAt(0).time.equalTo(QueueSimulator.getTime());){	
				Log.enter("About to remove "+actions.elementAt(0).toString());
				Action action = actions.elementAt(0);
				action.perform();		
				Log.enter("Removed action "+action.toString());
		}
		Log.enter("Performed actions for the time : "+QueueSimulator.getTime().toString());
	}
}
