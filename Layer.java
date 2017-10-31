package Network;

public abstract class Layer {
	
	// Fields
	protected Neuron[] neuronsInLayer;
	protected double[] neuronOutputs;
	public double expected;

	// Constructor
	public Layer(int numNeurons) {
		this.neuronsInLayer = new Neuron[numNeurons];
	}

	// Abstract methods for polymorphism in client
	public abstract double[] receiveInput(double[] values, double[][] weights, double expected);
	public abstract void sendOutput(Layer nextLayer);
	public abstract void receiveArguments(double[] arguments, double[][] weights);
	public abstract void sendArguments(Layer nextLayer);


}
