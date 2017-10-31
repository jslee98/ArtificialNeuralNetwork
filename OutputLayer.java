package Network;

public class OutputLayer extends Layer {

	// Fields
	private int numN;
	
	// Constructs
	public OutputLayer(int numNeurons) {
		super(numNeurons);
		this.numN = numNeurons;
		for (int i = 0; i < numNeurons; i++) {
			double[] randomWeights = {1};
			this.neuronsInLayer[i] = new Neuron(randomWeights);
		}	
	}

	// Receivers
	@Override
	public double[] receiveInput(double[] values, double[][] weights, double expected) {
		this.expected = expected;
		double[] deltaW = new double[this.numN];
		for (int i = 0; i < this.numN; i++) {
			deltaW[i] = this.neuronsInLayer[i].calculate(values, weights[i], expected);
		}
		return deltaW;	
	}
	
	@Override
	public void receiveArguments(double[] args, double[][] weights) {
		System.out.println("Output: ");
		for (int i = 0; i < this.numN; i++) {
			this.neuronsInLayer[i].calculateClass(args, weights[i]);
			System.out.println(this.neuronsInLayer[i].getValue());
		}
	}

	// Used for polymorphic abilities, ignore
	@Override
	public void sendOutput(Layer nextLayer) {
		// do nothing
	}
	
	@Override
	public void sendArguments(Layer nextLayer) {
		// do nothing
		
	}

}
