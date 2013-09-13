package simulator;
public class CounterSet<Content> extends AbstractCounterSet<Content>{
	private Counter<Content>[] counters;
	private int size;
	private QueueSimulator<Content> queueSimulator;	
	protected void setCounters(int size, int[] values){
		counters = new Counter[size];
		this.size=  size;
		for(int i=0; i<size; i++){
			counters[i] = new Counter<Content>(this,i);
			counters[i].setQuantity(values[i]);
		}
	}
	
	protected Counter<Content> getFreeCounter(){
		for(int i=0; i<size; i++){
			if(counters[i].isFree()){
				return counters[i];
			}
		}
		return null;
	}
	
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
	
	
	protected void refresh(){		
		for(int i=0; i<size; i++){
			counters[i].updateState();
		}
	}
	
	protected QueueSimulator<Content> getQueueSimulator(){
		return queueSimulator;
	}
	
	public CounterSet(QueueSimulator<Content> queueSimulator){
		this.queueSimulator = queueSimulator;
	}
	
	
}
