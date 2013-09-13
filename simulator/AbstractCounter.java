package simulator;

public abstract class AbstractCounter<Content> {
	

    protected int quantity; //How much resource does it have in stock?
    protected int key; //Unique ID
    
    
    protected abstract void freeCounter(); //Remove element from counter and make 
    //it free or closed
    protected abstract boolean isFree();   //Is the counter free to occupy? 

    protected abstract boolean isClosed(); //Ran out of stocks?
    protected abstract int available(int limit); //How much of limit can the counter share?
    protected abstract int finishTransaction(int requiredQuantity); //Do transaction for requiredAmount
    
}
