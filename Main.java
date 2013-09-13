import java.io.IOException;

import simulator.QueueSimulator;
import simulator.Simulator;


public class Main{
	public static void main(String args[]) throws IOException{
		Simulator q = new QueueSimulator<String>();
		q.execute();		 //The only public method apart from
		//toString methods in the whole of the package is this master method!
		
	}
	
	
}
