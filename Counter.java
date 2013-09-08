
public class Counter {
	
	
    public static enum CounterState {
    	FREE, CLOSED, BUSY
    } 
    private QueueElement element;
    private int quantity; //How much resource does it have in stock?
    private int key;
    private CounterState state; //Is the counter open or close?
    public Counter(int key){
    	state = CounterState.FREE; //When we run update it will close
    	quantity = 0;
    	this.key = key;
    }
    public void setQuantity(int quantity){ //To initialize value of amount of goods present in a counter   
    	state = CounterState.FREE;
    	this.quantity = quantity;
    }
    
    public void reduceQuantity(int reductionAmount){ //To take away value of amount of goods
    	this.quantity-=reductionAmount;
    	if(this.quantity<0) this.quantity = 0;
    	Log.enter(this.toString()+" has lost "+reductionAmount+" and has only "+quantity+" left");
    }
    
    public void freeCounter(){ //To set the state of the counter - Not an interface method : must be called internally by other functions
    	this.state = CounterState.FREE;
    	Log.enter(this.toString()+" is now free");
    	element = null;
    }
    
    public void updateState(){
    	if(quantity == 0){
    		this.state = CounterState.CLOSED;
    		Log.enter(this.toString()+" is now closed");
    	}
    }
    
    public void setElement(QueueElement element){
    	this.element = element;
    }
    
    public boolean isFree(){
    	return (state == CounterState.FREE);
    }
    

    public boolean isClose(){
    	return (state == CounterState.CLOSED);
    }
    
    public int available(int limit){
    	if(quantity <= limit){
    		return quantity;
    	}
    	return limit;
    }
    public int finishTransaction(int quantity){    	
    	reduceQuantity(quantity);    	
    	freeCounter();
    	updateState();    	
    	return quantity;
    }
    
    public String toString(){
    	return "Counter <"+key+", "+this.quantity+">";
    }
}
