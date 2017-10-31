package Network;

import java.util.Random;

public class HiddenLayer extends Layer{
	
	// Fields
	private int numOutputs;
	private int numNeurons;
	
	// Constructors
	public HiddenLayer(int numNeurons) {
		super(numNeurons);
	}
	
	public HiddenLayer(int numNeurons, int neuronsInNext) {
		super(numNeurons);
		this.numNeurons = numNeurons;
		this.numOutputs = neuronsInNext;
		for (int i = 0; i < numNeurons; i++) {
			double[] randomWeights = new double[neuronsInNext];
			for (int n = 0; n < neuronsInNext; n++) {
				Random r = new Random();
				randomWeights[n] = -.5 + r.nextDouble();
			}
			this.neuronsInLayer[i] = new Neuron(randomWeights);
		}	
	}

	// Senders
	public void sendOutput(Layer nextLayer) {
		double [][] weights = new double[this.numOutputs][this.numNeurons];
		double[] values = new double[this.numNeurons];
		int neuron = 0;
		for(Neuron n : this.neuronsInLayer) {
			values[neuron] = n.getValue();
			double[] tempWeights = n.getWeights();
			for(int i = 0; i < this.numOutputs; i++) {
				weights[i][neuron] = tempWeights[i];
			}
			neuron++;
		}
		double[] deltaW = nextLayer.receiveInput(values, weights, this.expected);
		this.updateWeights(deltaW);
	}
	
	@Override
	public void sendArguments(Layer nextLayer) {
		double [][] weights = new double[this.numOutputs][this.numNeurons];
		double[] values = new double[this.numNeurons];
		int neuron = 0;
		for(Neuron n : this.neuronsInLayer) {
			values[neuron] = n.getValue();
			double[] tempWeights = n.getWeights();
			for(int i = 0; i < this.numOutputs; i++) {
				weights[i][neuron] = tempWeights[i];
			}
			neuron++;
		}
		nextLayer.receiveArguments(values, weights);		
	}

	// Receivers
	@Override
	public double[] receiveInput(double[] values, double[][] weights, double expected) {
		this.expected = expected;
		double[] deltaW = new double[this.numNeurons];
		for (int i = 0; i < this.numNeurons; i++) {
			deltaW[i] = this.neuronsInLayer[i].calculate(values, weights[i], expected);
		}
		return deltaW;	
	}

	@Override
	public void receiveArguments(double[] args, double[][] weights) {
		for (int i = 0; i < this.numNeurons; i++) {
			this.neuronsInLayer[i].calculateClass(args, weights[i]);
		}
	}
	
	// Learn
	public void updateWeights(double[] deltas) {
		for(int i = 0; i < this.neuronsInLayer.length; i++) {
			neuronsInLayer[i].addDeltas(deltas);
		}
	}
	
}
