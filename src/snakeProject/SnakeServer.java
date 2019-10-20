package snakeProject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Node;
import java.lang.Integer;

public class SnakeServer extends Game{
	/** A simulation allows to simulate the game: the occupied positions, existing snakes, checking crashes,
	 * among other options. See the description of class Simulation to understand its many possibilities. */
	private Simulation simulation;
	
	/**allNodes is a list which stores all the graphical elements to display */
	private ArrayList<Node> allNodes;
	
	public static ServerSocket listenSocket;
	public static Socket clientSocket;
	public static Position clientDir;
	public static Position serverDir;
	public static PrintWriter out;
	public static BufferedReader in;

	public SnakeServer() throws IOException {
		String player1=gameMenu();
		allNodes = new ArrayList<Node>();
		simulation = new Simulation(GRID_SIZE,2,allNodes,player1, "ServCl");
		int listenPort=6789;
		//create a socket to listen for client requests
		listenSocket = new ServerSocket(listenPort);
		//when a request is received, establish a connection through clientSocket for the communication
		clientSocket=listenSocket.accept();

		//out and in to send and receive information via the socket
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}

	public static void main(String[] args) throws IOException {
		Application.launch(args);
	}	

	public Collection<Node> gameStep() {
		/** If someone clicks quickly in two different keys between 2 game steps, only the first one is to be
		 * considered. This way, when a gameStep is called we need to make the keyboard ready to receive again. */
		Keyboard.readyToReceive();

		/** We only continue to update the game as long as it has not finished.
		 * If it has finished, the graphical windows stays as it is so that the players can see how the game ended. */
		if (simulation.isTerminated()) {
			simulation.stop();	
			return allNodes;
		}

		//The server only updates his own snake!
		simulation.getSnakes().get(0).update();
		serverDir=simulation.getSnakes().get(0).getHeadDir();

		//Then he sends the chosen direction to the client
		try {
			clientDir = communication();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//he receives the direction from the client and does the update
		simulation.getSnakes().get(1).setHeadDir(clientDir);
		
		//true because we are in server-client mode
		simulation.update(allNodes,true);

		return allNodes;
	}
	
	/**
	 * This function establishes the communication between client and server
	 * Sends the direction of the server and gets the direction of the client and returns it
	 */
	private Position communication() throws IOException {
		//send the server direction to the client
		out.println(serverDir.getX());
		out.println(serverDir.getY());
		out.flush();
		
		//receive the client direction
		String str = in.readLine();
		int x = Integer.parseInt(str);
		str = in.readLine();
		int y = Integer.parseInt(str);
		return new Position(x,y);
	}
	
	public String gameMenu() {
		Scanner reader = new Scanner(System.in);
		System.out.println("Player 1, what kind of snake are you? If you do not choose a valid option, you will be a random snake.");
		System.out.println("Options: R-Random. C-Controlled. I-Intelligent.");
		String player = reader.nextLine();
		reader.close();
		return player;
	}

}