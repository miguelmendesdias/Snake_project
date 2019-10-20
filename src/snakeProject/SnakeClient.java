package snakeProject;

import java.net.Socket;
import java.net.UnknownHostException;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Node;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class SnakeClient extends Game{

	/** A simulation allows to simulate the game: the occupied positions, existing snakes, checking crashes,
	 * among other options. See the description of class Simulation to understand its many possibilities. */
	private Simulation simulation;
	
	/**allNodes is a list which stores all the graphical elements to display */
	private ArrayList<Node> allNodes;

	public static Socket clientSocket;
	private static String machine;
	private static int port;
	public static Position serverDir;
	public static Position clientDir;
	public static PrintWriter out;
	public static BufferedReader in;

	public SnakeClient() throws UnknownHostException, IOException {
		String player2 = gameMenu();
		allNodes = new ArrayList<Node>();
		simulation = new Simulation(GRID_SIZE,2,allNodes,"ServCl", player2);
		port=6789;
		machine="localhost";

		//creation of a socket to establish communication with the server
		clientSocket = new Socket(machine, port);

		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	public Collection<Node> gameStep() {
		Keyboard.readyToReceive();

		if (simulation.isTerminated()) {
				simulation.stop();	
			return allNodes;
		}
		
		//We update the client snake
		simulation.getSnakes().get(1).update();
		clientDir=simulation.getSnakes().get(1).getHeadDir();

		//We send it to the server
		try {
			serverDir = communication();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//We update the server snake with the info received
		simulation.getSnakes().get(0).setHeadDir(serverDir);
		
		// true because here we are in server-client mode
		simulation.update(allNodes,true);

		return allNodes;
	}
	private Position communication() throws IOException {
		//send to the server the client direction
		int x= clientDir.getX();
		int y= clientDir.getY();
		out.println(x);
		out.println(y);
		
		//receive the direction of the server snake
		String str = in.readLine();
		x = Integer.parseInt(str);
		str = in.readLine();
		y = Integer.parseInt(str);
		return new Position(x,y);
	}

	public String gameMenu() {
		Scanner reader = new Scanner(System.in);
		System.out.println("Player 2, what kind of snake are you? If you do not choose a valid option, you will be a random snake.");
		System.out.println("Options: R-Random. C-Controlled. I-Intelligent. S-Super Intelligent.");
		String player = reader.nextLine();
		reader.close();
		return player;
	}

}

