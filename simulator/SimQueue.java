package simulator;

public abstract class SimQueue<E> {
	protected abstract void move();  
	protected abstract void addElement(E e);
}
