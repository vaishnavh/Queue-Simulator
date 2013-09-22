package simulator;

//Manages the set of counters belonging to a queue simulator
public class CounterSet<Content> extends AbstractCounterSet<Content>{
	
	private Counter<Content>[] counters;
	private int size;
	private QueueSimulator<Content> queueSimulator;	
	
	//Initialize values for the counters
	protected void setCounters(int size, int[] values){
		counters = new Counter[size];
		this.size=  size;
		for(int i=0; i<size; i++){
			//initialize each counter
			counters[i] = new Counter<Content>(this,i+1);
			counters[i].setQuantity(values[i]);
		}
	}
	
	//Search for a free counter and return if any found
	protected Counter<Content> getFreeCounter(){
		for(int i=0; i<size; i++){
			if(counters[i].isFree()){
				return counters[i];
			}
		}
		return null;
	}
	
	
	//Are there no free counters?
	protected boolean isExhausted(){
		for(int i=0; i<size; i++){
			if(!counters[i].isClosed()){
				return false; //Even if there's one counter that ain't
				//closed yet, there's a possibility that the next element can be sent
				//to it
			}
		}
		return true; //All counters are closed
	}
	
	//Probe each counter and update their state
	protected void refresh(){		
		for(int i=0; i<size; i++){
			counters[i].updateState();
		}
	}
	
	
	//To which simulator is this counter set working for?
	protected QueueSimulator<Content> getQueueSimulator(){
		return queueSimulator;
	}
	
	//Initialize 
	public CounterSet(QueueSimulator<Content> queueSimulator){
		this.queueSimulator = queueSimulator;
	}
	
	public String toString(){
		String ret = "";
		for(int i=0; i<this.size; i++){
			ret = ret+"["+this.counters[i].toString()+" {"+this.counters[i].getState()+"}] ";
		}
		return ret;
	}
}
