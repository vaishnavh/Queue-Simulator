package simulator;

import java.io.IOException;

public abstract class AbstractQueueSimulator<Content> implements Simulator{

		
	public abstract void initialize() throws IOException; //Set up initial values
	public abstract void start(); //Begin simulation
	protected abstract void readInput() throws IOException; //Read values for initializing
	protected abstract void nextStep() ; //Conduct next step of simulation
	
	
	@Override
	public void execute() throws IOException { //Initialize and then start
		// TODO Auto-generated method stub
		this.initialize();
		this.start();
	}
	
}
