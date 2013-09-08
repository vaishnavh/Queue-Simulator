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
		actions.add(i, action);	
	}
	
	public void performActions(){
		for(; 0<actions.size() && actions.elementAt(0).time.atLeast(QueueSimulator.getTime()); ){					
			actions.remove(0);
		}
	}
}
