package simulator;
import java.util.LinkedList;
import java.util.Queue;

import simulator.Log.MessageType;

import chrono.Time;

public class ExitQueue<Content> extends SimQueue<QueueElement<Content>> {
	private Queue<QueueElement<Content>> exitQueue = new LinkedList<QueueElement<Content>>();
	private QueueSimulator<Content> queueSimulator;
	private void updateHead(){
		//Keep in queue an action for when
		//this head element is to leave the exit queue
		QueueElement<Content> head = this.exitQueue.peek();
		if(head!=null){
			//This must be called only if the head is new
			this.queueSimulator.getLog().enter(new Message<Content>(head, MessageType.EXIT_READY));
			this.queueSimulator.prepareForExit(head);
		}
	}
	
	protected ExitQueue(QueueSimulator<Content> queueSimulator){
		this.queueSimulator = queueSimulator;
	}
	protected void move(){
		//Make the queue move (Remove the head element)
			QueueElement<Content> head = this.exitQueue.peek();
			if(head!=null){
				//If top element exists 
				if(new Time(head.getStartTime()).increment(head.getQuantity()).equalTo(this.queueSimulator.getTime())){
					//a redundant check actully; we've already ensured that this is the case
					this.exitQueue.poll();
					this.updateHead();
				}
			}
		}
	
	
	
	protected void addElement(QueueElement<Content> element){
		this.queueSimulator.getLog().enter(new Message<Content>(element, MessageType.JOIN_EXIT_QUEUE));
		if(this.exitQueue.peek()==null){
			//if the element that was added is the head
			exitQueue.add(element);
			this.updateHead();
		}else{			
			exitQueue.add(element);			
		}	
	}
	

}
