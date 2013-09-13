package probability;
import java.io.IOException;


public interface ProbabilityDistribution {
	void initialize() throws IOException; //A function that will read input from somewhere
	int sample(); //A function that will sample from the probability distribution
	
}