
public interface Simulator<R, C> {
	//R is the simulator type
	public void initialize(); //Initialize the simulator with values
	public void start(); //Start the simulator
	public void readInput(); //Is there some input that the simulator has to have read?
	public void nextStep(); //Executes the next step
	
}
