import java.util.LinkedList;
import java.util.Queue;

public class ExitQueue {
	private Queue<QueueElement> exitQueue = new LinkedList<QueueElement>();
	public void move(){
			QueueElement head = this.exitQueue.peek();
			if(head!=null){
				if(new Time(head.getStartTime()).increment(head.getQuantity()).equalTo(QueueSimulator.getTime())){
					this.exitQueue.poll();
				}
			}
		}
	
	private void updateHead(){
		QueueElement head = this.exitQueue.peek();
		if(head!=null){
			//This must be called only if the head is new
			head.getReadyToExit();
		}
	}
	
	public void addElement(QueueElement element){
		if(this.exitQueue.peek()==null){
			exitQueue.add(element);
			this.updateHead();
		}else{
			exitQueue.add(element);			
		}	
	}
	

}
