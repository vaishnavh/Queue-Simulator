package simulator;
import java.io.IOException;

public interface Simulator<Content>{
	public void execute() throws IOException; //Do everything from initializing to starting
	public void initialize() throws IOException; //Initialize the simulator
	public void start(); //Start the simulator and run till termination
	public Log getLog(); //Get the entry of activities
}
