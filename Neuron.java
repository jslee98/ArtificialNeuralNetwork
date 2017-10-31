package Network;

public class Neuron {

	// Fields
	private final double ALPHA = 0.3;
	private double theta;
	public double[] weights;
	public double value;
	
	// Constructor
	public Neuron(double[] weights) {
		this.value = 0;
		this.theta = 0;
		this.weights = weights;
	}
	
	// Getters & Setters
	public double[] getWeights() {
		return this.weights;
	}
	
	public double getValue() {
		return this.value;
	}
	
	public void setValue(double val) {
		this.value = val;
	}
	
	public void addDeltas (double[] deltas)  {
		for (int i = 0; i < weights.length; i++) {
			weights[i] += deltas[i];
		}
	}
	
	// Calculation
	public static double sig_activation(double net) {
		return 1.0/(1 + (Math.exp(-net)));
	}
	
	public double calculate(double[] values, double[] weights, double expected) {
		double net = 0;
		for (int i = 0; i < values.length; i++) {
			net += values[i] * weights[i];
		}
		net -= this.theta;
		double z = sig_activation(net);
		this.value = z;
		double error = expected - z;
		this.theta += (-this.ALPHA * error);
		double eGradient = z * (1.0 - z) * error;
		double deltaW = this.ALPHA * net * eGradient;
		return deltaW;
	}
	
	public void calculateClass(double[] values, double[] weights) {

		double net = 0;
		for (int i = 0; i < values.length; i++) {
			net += values[i] * weights[i];
		}
		net -= this.theta;
		this.value = sig_activation(net);
	}

}
