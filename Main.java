import java.io.IOException;

import simulator.QueueSimulator;
import simulator.Simulator;


public class Main{
	public static void main(String args[]) throws IOException{
		Simulator q = new QueueSimulator<String>();
		q.initialize();
		q.start();
		
		System.out.println("Entry Queue : ");
		q.getLog().printEntryQueueStates();
		System.out.println("Exit Queue : ");
		q.getLog().printExitQueueStates();
		System.out.println("Counters : ");
		q.getLog().printCounterSetStates();
		System.out.println("Entry Queue : ");
		q.getLog().printEntryQueue();
		System.out.println("Exit Queue : ");
		q.getLog().printExitQueue();
		System.out.println("Counters : ");
		q.getLog().printCounterSet();
		System.out.println("Element 1 : ");
		q.getLog().printElement(1);
		System.out.println("Counter 1 : ");
		q.getLog().printCounter(1);

	}
	
	
}
