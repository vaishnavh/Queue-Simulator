package simulator;

import java.util.Vector;

public class Log<Content> {
	    protected QueueSimulator<Content> queueSimulator;
	    Vector<Message<Content>> execution = new Vector<Message<Content>>();
	    protected enum MessageType{
	    	JOIN_EXIT_QUEUE, COUNTER_ASSIGNED, LEAVE_TRANSACTION, FREED, DIRECT_EXIT, ACTION, PREPARED, PARTIAL_LOSS, COMPLETE, EXIT_READY, CLOSED, QUEUE_ENTER,
	    	FINISH_TRANSACTION, LEFT
	    }
	    
		protected void enter(Message<Content> message){
			message.setTime( queueSimulator.getTime());
			execution.add(message);
		}
		
		protected Log(QueueSimulator<Content> queueSimulator){
			this.queueSimulator  =  queueSimulator;
		}
		protected void print(String message){
			System.out.println(message);
		}
		
		protected void printAll(){
			for(int i=0; i<execution.size(); i++){
				System.out.println(execution.elementAt(i).toString());
			}
		}
}
