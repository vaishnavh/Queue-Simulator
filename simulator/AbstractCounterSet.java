package simulator;

public abstract class AbstractCounterSet<Content> {
	//This gives an abstraction of the implementation : I can have various
	//implementations of the following extending from here
	protected abstract Counter<Content> getFreeCounter(); //Get one free counter if any; else null
	protected abstract boolean isExhausted(); //Is no counter free?
	protected abstract void refresh(); //Probe all counters and record their states
}
