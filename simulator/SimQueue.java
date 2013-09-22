package simulator;


//A class parent to exit and entry queues of the simulator
public abstract class SimQueue<E> {
	protected abstract void move();   //equivalent of peek
	protected abstract void addElement(E e); //equivalent of add
}
