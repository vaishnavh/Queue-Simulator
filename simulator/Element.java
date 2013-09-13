package simulator;

import chrono.Time;

public abstract class Element<Content> {
	
	
	//Most of these methods do little now : they merely print what occured. The QueueSimulator class handles
	//moving about of these objects from exit and entry queue. This is to give a separation between the queue's methods
	//and the elements themselves. These functions can be extended to do anything you want when the activities occur.
	protected abstract void beginTransaction(Time t);		//Marks beginning of some transaction
	protected abstract void finishTransaction(int transacted); //Marks end of transaction; gets to know how much was transacted
	protected abstract void getReadyToExit(Time t); //Marks appearance at the head of the exit queue
	protected abstract void enter();
	
	
}
