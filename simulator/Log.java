package simulator;

import java.util.Vector;

import chrono.Time;

//Manages storage and display of all activities
public class Log<Content> {
	    protected QueueSimulator<Content> queueSimulator;

	    Vector<Message<Content>> execution = new Vector<Message<Content>>(); //log for all activites
	    Vector<String> entryLog, exitLog, countersLog; //Logs for queues and counters

	    //Add a log entry for the state of queues/counters
	    private void addState(Vector<String> stateLog, String state){

	    	Time time = queueSimulator.getTime();
	    	String timeStamped = time.toString()+" : "+state;
	    	if(!stateLog.isEmpty()){
	    		//Remove redundant log
	    		if(stateLog.elementAt(stateLog.size()-1).compareTo(timeStamped)!=0){
	    			stateLog.add(timeStamped);
	    		}
	    	}else{
	    		stateLog.add(timeStamped);
	    	}
	    	
	    	
	    	
	    }
	    
	    protected enum MessageType{
	    	JOIN_EXIT_QUEUE, COUNTER_ASSIGNED, LEAVE_TRANSACTION, FREED, DIRECT_EXIT, ACTION, PREPARED, PARTIAL_LOSS, COMPLETE, EXIT_READY, CLOSED, QUEUE_ENTER,
	    	FINISH_TRANSACTION, LEFT
	    }//Type of log
	    
	    //Add a message to the log class; takes care of update sub-logs
		protected void enter(Message<Content> message){
			Time time = queueSimulator.getTime();
			String timeStamp = time.toString()+" : ";
			this.addState(entryLog, queueSimulator.getEntryQueueState());
			this.addState(exitLog, queueSimulator.getExitQueueState());
			this.addState(countersLog, queueSimulator.getCounterSetState());
			message.setTime(time);
			execution.add(message);
		}
		
		
		//initialize
		protected Log(QueueSimulator<Content> queueSimulator){
			entryLog = new Vector<String>();
			exitLog = new Vector<String>();
			countersLog = new Vector<String>();

			this.queueSimulator  =  queueSimulator;
		}
		
		
		//print a message
		protected void print(String message){
			System.out.println(message);
		}
		
		
		//Print all log entries
		protected void printAll(){
			for(int i=0; i<execution.size(); i++){
				System.out.println(execution.elementAt(i).toString());
			}
		}
		
		
		//Print entry queue logs
		public void printEntryQueue(){
			for(int i=0; i<execution.size(); i++){
				if(execution.elementAt(i).isEntryQueue())
				System.out.println(execution.elementAt(i).toString());
			}
		}
		
		
		//Print exit queue logs
		public void printExitQueue(){
			for(int i=0; i<execution.size(); i++){
				if(execution.elementAt(i).isExitQueue())
				System.out.println(execution.elementAt(i).toString());
			}
		}
		
		
		//Print counters logs
		public void printCounterSet(){
			for(int i=0; i<execution.size(); i++){
				if(execution.elementAt(i).isCounterSet())
				System.out.println(execution.elementAt(i).toString());
			}
		}
		
		
		//Prints the state of entry queue at various check points
		public void printEntryQueueStates(){
			for(int i=0; i<this.entryLog.size(); i++){
				System.out.println(entryLog.elementAt(i));
			}
		}

		//Prints the state of exit queue at various check points		
		public void printExitQueueStates() {
			// TODO Auto-generated method stub
			for(int i=0; i<this.exitLog.size(); i++){
				System.out.println(exitLog.elementAt(i));
			}
		}
		
		
		//Prints the state of counters e at various check points
		public void printCounterSetStates() {
			// TODO Auto-generated method stub
			for(int i=0; i<this.countersLog.size(); i++){
				System.out.println(countersLog.elementAt(i));
			}
		}
		
		
		//Print logs for a specific counter
		public void printCounter(int key){
			for(int i=0; i<execution.size(); i++){
				Message message = execution.elementAt(i);
				if(message.isCounterSet()){
					if(message.getCounter().key == key){
						System.out.println(message.toString());
					}
				}
				
			}
		}
		
		//Print logs for specific element
		public void printElement(int key){
			for(int i=0; i<execution.size(); i++){
				Message message = execution.elementAt(i);
				if(message.isElement()){
					if(message.getElement().getKey() == key){
						System.out.println(message.toString());
					}
				}
				
			}
		}
}
