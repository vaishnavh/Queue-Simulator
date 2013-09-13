package probability;
import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;


public class EnumeratedInteger implements ProbabilityDistribution {
	private static EnumeratedIntegerDistribution pd;
	//Provide n numbers and their probabilties
	@Override
	public void initialize() throws IOException {
		// TODO Auto-generated method stub

		try {
			Scanner input = new Scanner(System.in);
			int size=0;
			//How many numbers?
			while(size<=0){
				System.out.print("Enter the number of points to define distribution on (>0): ");
				size = input.nextInt();
			}

			int[] singleton = new int[size];
			//Get numbers and their probabilities
			double[] probabilities = new double[size];
			for(int i=0; i<size; i++){
				singleton[i]=-1;
				while(singleton[i]<0){
					System.out.print("Enter the value  of x : ");
					singleton[i] = input.nextInt();
				}
				probabilities[i]=-1;
				while(probabilities[i]<0 || probabilities[i]>1){
					System.out.print("Enter the value  of P(X=x) : ");
					probabilities[i] = input.nextDouble();
				}
			}
			this.pd = new EnumeratedIntegerDistribution(singleton, probabilities); //set probability
			
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
