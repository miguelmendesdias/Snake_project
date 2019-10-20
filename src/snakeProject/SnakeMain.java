package snakeProject;

import java.util.ArrayList;


import java.util.Collection;

import javafx.application.Application;
import javafx.scene.Node;
import java.util.Scanner;

public class SnakeMain extends Game{

	/** A simulation allows to simulate the game: the occupied positions, existing snakes, checking crashes,
	 * among other options. See the description of class Simulation to understand its many possibilities. */
	private Simulation simulation;
	
	/**allNodes is a list which stores all the graphical elements to display */
	private ArrayList<Node> allNodes;

	public SnakeMain() {
		String[] players= gameMenu();
		allNodes= new ArrayList<Node>();
		simulation = new Simulation(GRID_SIZE,2,allNodes,players[0],players[1]);
		simulation.start();
	}

	public static void main(String[] args) { Application.launch(args); }

	public Collection<Node> gameStep() {
		/** If someone clicks quickly in two different keys between 2 game steps, only the first one is to be
		 * considered. This way, when a gameStep is called we need to make the keyboard ready to receive again. */
		Keyboard.readyToReceive();

		/** We only continue to update the game as long as it has not finished.
		 * If it has finished, the graphical windows stays as it is so that the players can see how the game ended. */
		if (simulation.isTerminated()) {
			simulation.stop();
			} else {
				//function called with false because here we are NOT in mode server client.
				simulation.update(allNodes,false);
			}

		return allNodes;
	}
	
	public String[] gameMenu() {
		Scanner reader = new Scanner(System.in);
		String[] players = new String[2];
		System.out.println("Player 1, what kind of snake are you? If you do not choose a valid option, you will be a random snake.");
		System.out.println("Options: R-Random. C-Controlled. I-Intelligent.");
		String answer1 = reader.nextLine();
		players[0]=answer1;
		System.out.println("And player 2, what kind of snake are you? If you do not choose a valid option, you will be a random snake.");
		System.out.println("Options: R-Random. C-Controlled. I-Intelligent. S-Super Intelligent.");
		String answer2 = reader.nextLine();
		players[1]=answer2;
		reader.close();
		return players;
	}
}
