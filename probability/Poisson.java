package probability;
import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.math3.distribution.PoissonDistribution;


public class Poisson implements ProbabilityDistribution{
	private double lambda;
	private PoissonDistribution pd;
	@Override
	public void initialize() throws IOException {
		// TODO Auto-generated method stub

		try {
			Scanner input = new Scanner(System.in);
			lambda = -1;
			while(lambda<=0){
				System.out.print("Enter the value of lambda for the Poisson distribution (lambda > 0) : ");
				lambda = input.nextDouble();
			}
			pd = new PoissonDistribution(lambda);
			
		} catch (NumberFormatException nfe) {
			System.err.println("Invalid Format!");
		}
	}

	@Override
	public int sample() {
		// TODO Auto-generated method stub
		return pd.sample()+1;
	}

}
