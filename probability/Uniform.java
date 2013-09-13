package probability;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import org.apache.commons.math3.distribution.UniformIntegerDistribution;

public class Uniform implements ProbabilityDistribution {
	private int lowerLimit, upperLimit;
	UniformIntegerDistribution pd;
	@Override
	public void initialize() throws IOException {
		// TODO Auto-generated method stub

		try {
			Scanner input = new Scanner(System.in);
			
			lowerLimit = 0;
			while (lowerLimit == 0) {
				System.out.print("Enter lower limit for uniform probability distribution (positive) : ");
				lowerLimit = input.nextInt();
			}
			upperLimit = lowerLimit - 1;
			while (upperLimit < lowerLimit) {
				System.out.print("Enter upper limit for uniform probability distribution (positive) : ");
				upperLimit = input.nextInt();
			}
			pd = new UniformIntegerDistribution(lowerLimit, upperLimit);
			
		} catch (NumberFormatException nfe) {
			System.err.println("Invalid Format!");
		}
	}

	@Override
	public int sample() {
		// TODO Auto-generated method stub
		 return pd.sample();
	}
}
