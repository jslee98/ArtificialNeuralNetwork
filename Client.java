package Network;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class Client {

	// Fields
	private int epochs;
	private int numInputs;
	private int numInputLines;
	private String tPath;
	private Layer[] layers;
	private double[] expectedList;
	private double[][] trainingValues;
	Scanner kb = new Scanner(System.in);

	/*
	 * Gets input data from user
	 * Creates layer array
	 */
	public void start() {
		
		System.out.println("Welcome to our Artificial Neural Network.");
		System.out.println("Please train your system.");

		System.out.print("Num training inputs: ");
		this.numInputLines = kb.nextInt();
		System.out.print("\nNum input neurons: ");
		this.numInputs = kb.nextInt();
		System.out.print("\nNum hidden layers: ");
		int numHLayers = kb.nextInt();
		System.out.print("\nNum neurons in hidden layers: ");
		int numHNeurons = kb.nextInt();
		System.out.print("\nNumber of Epochs: ");
		this.epochs = kb.nextInt();
		System.out.println("Enter training file path: ");
		kb.nextLine();
		this.tPath = kb.nextLine();

		int numONeurons = 1;
		int numLayers = 2 + numHLayers;
		this.layers = new Layer[numLayers];
		this.layers[0] = new InputLayer(this.numInputs, numHNeurons);
		for (int i = 1; i < numHLayers; i++) {
			this.layers[i] = new HiddenLayer(numHNeurons, numHNeurons);
		}
		this.layers[layers.length-2] = new HiddenLayer(numHNeurons, numONeurons);
		this.layers[layers.length-1] = new OutputLayer(numONeurons);
	}

	/*
	 * Reads training file into data structures
	 * Creates 2d array of training inputs and array of expected outputs
	 */
	public void readTrainingInput(){
		this.trainingValues = new double[this.numInputLines][0];
		this.expectedList = new double[this.numInputLines];
		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.tPath));
			String line = reader.readLine();
			int lineNum = 0;
			while (line != null) {
				String[] arguments = line.split(",");
				this.trainingValues[lineNum] = new double[arguments.length-1];
				for (int i = 0; i < arguments.length-1; i++) {
					this.trainingValues[lineNum][i] = Integer.parseInt(arguments[i]);
				}
				int exp = (int) Double.parseDouble(arguments[arguments.length-1]);
				this.expectedList[lineNum] = exp;
				line = reader.readLine();
				lineNum++;
			}
			reader.close();
		} catch (Exception e) {
			System.out.println("There is an error in your training data!");
			e.printStackTrace();
		}
	}

	/*
	 * Sends data through layers n times, as given by epochs
	 * Each layer updates the weights in the neuron
	 */
	public void train() {
		for(int i = 0; i < this.epochs; i++) {
			this.process();
		}
		System.out.println("Success! Your weights have been calculated after training " + this.epochs + " times");
		System.out.println("Would you like to use classification mode? (y/n):");
		String response = kb.nextLine();
		if(response.toLowerCase().equals("y")) {
			this.classification();
		} else {
			System.out.println("Thank you for training your Neural Network.");
		}
	}
	
	// Used by train()
	public void process() {
		for(int n = 0; n < this.trainingValues.length; n++) {
			layers[0].receiveInput(this.trainingValues[n], new double[0][0], this.expectedList[n]);
			for(int layerNum = 0; layerNum < layers.length - 1; layerNum++) {
				// Uncomment to print weight changes
				//System.out.println("Layer " + layerNum + " before: ");
				//printWeights(layers[layerNum]);
				layers[layerNum].sendOutput(layers[layerNum + 1]);
				//System.out.println("Layer " + layerNum + " after: ");
				//printWeights(layers[layerNum]);
			}
		}
	}

	/*
	 * Sends data through layers, output layer prints output
	 * Weights not updated this time
	 */
	public void classification() {
		System.out.print("Please enter " + this.numInputs + " arguments separated by commas: ");
		String response = kb.nextLine();
		String[] arguments = response.split(",");
		double[] classArgs = new double[this.numInputs];
		for (int i = 0; i < this.numInputs; i++) {
			classArgs[i] = Integer.parseInt(arguments[i]);
		}
		this.classify(classArgs);
		System.out.print("Enter 'y' if you would like to classify again, or enter to quit: ");
		String answer = kb.nextLine();
		if (answer.toLowerCase().equals("y")) {
			this.classification();
		} else {
			System.out.println("Thank you for using our ANN!");
		}
		kb.close();
	}
	
	// Used by classification()
	public void classify(double[] arguments) {
		layers[0].receiveArguments(arguments, new double[0][0]);
		for(int layerNum = 0; layerNum < layers.length - 1; layerNum++) {
			layers[layerNum].sendArguments(layers[layerNum + 1]); 
		}
	}
	

	/*
	 * Testing method to check if weights are updating
	 */
	public static void printWeights(Layer layer) {
		for(Neuron n : layer.neuronsInLayer) {
			for(double wgt : n.getWeights()) {
				System.out.print(wgt + ", ");
			}
			System.out.println();
		}
	}

	// Runs until trained, then allows user to decide what to do
	public static void main(String[] args) throws InterruptedException{
		Client temp = new Client();
		temp.start();
		try {
			temp.readTrainingInput();
		} catch (Exception e) {
			System.out.print("Did you mean to use this path: " + temp.tPath);
		}
		temp.train();
	}

}
