




public class Market {
	private Counter[] counters;
	private int size;
	
	public void setCounters(int size, int[] values){
		counters = new Counter[size];
		this.size=  size;
		for(int i=0; i<size; i++){
			counters[i] = new Counter(i);
			counters[i].setQuantity(values[i]);
		}
	}
	
	public Counter getFreeCounter(){
		for(int i=0; i<size; i++){
			if(counters[i].isFree()){
				return counters[i];
			}
		}
		return null;
	}
	
	public boolean isExhausted(){
		for(int i=0; i<size; i++){
			if(!counters[i].isClose()){
				return false; //Even if there's one counter that ain't
				//closed yet, there's a possibility that the next element can be sent
				//to it
			}
		}
		return true; //All counters are closed
	}
	
	
	public void refresh(){		
		for(int i=0; i<size; i++){
			counters[i].updateState();
		}
	}
}
