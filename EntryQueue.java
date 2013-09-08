import java.util.LinkedList;
import java.util.Queue;


public class EntryQueue {
	private Queue<QueueElement> entryQueue = new LinkedList<QueueElement>();
	private Double populationRate;
	private Double populationInterval;
	public void move(){
		QueueElement head = entryQueue.peek();
		if(head!=null){
			Counter counter = QueueSimulator.counters.getFreeCounter();
			if(counter!=null){
				head.beginTransaction(counter);
				this.entryQueue.poll();
				move();
			}
		}
	}
	
	public void start(){
		this.prepareNewElement();
	}
	
	public void addElement(QueueElement element){
		if(this.entryQueue.peek()==null){
			entryQueue.add(element);
			move();
		}else{
			entryQueue.add(element);			
		}	
		this.prepareNewElement();
	}
	
	public void setPopultionRate(Double populationRate){
		this.populationRate = populationRate;
		this.populationInterval = new Double(1)/this.populationRate;
	}
	
	public Double getPopulationInterval(){
		return this.populationInterval;
	}
	
	
	public void prepareNewElement(){
		//Adds a todo action to the actions list making a mention
		//of a new element to be added
		QueueElement newElement = QueueSimulator.getNewElement();
		QueueSimulator.getActionQueue().addAction(new Action(newElement, Action.ActionType.ENTER));
	}
}
