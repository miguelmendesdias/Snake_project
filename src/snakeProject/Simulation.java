package snakeProject;

import java.util.ArrayList;
import java.util.Collection;

import javafx.scene.Node;
import javafx.scene.paint.Color;

/**
 * This class contains all the informations related to a game and it allows someone who uses it to:
 * 		-represent the current state of a game (positions occupied by the different snakes)
 * 		-check if the game is running or finished already
 * 		-get the winners
 * 
 * It is developed in such a way that it is as general as possible. This means that if by chance
 * a user wants a game with more than 2 snakes, the simulation will work, as both the snakes and
 * the winners are stored in lists, allowing to have any number of participants and of course
 * any number of winners. I decided not to leave the project with the possibility of more than
 * 2 snakes because the super smart snake wouldnt work.
 * 
 * But if I want to play for example with 4 snakes its ok, just need to make them start at different points!
 * 
 * Therefore, a call to its constructor allows to create a new game, to which a map(grid)
 * is associated, as well as the snakes which are playing and some other useful variables
 * that allow to know the state of a game at each moment.
 * 
 * @author miguel dias
 *
 */
public class Simulation {
	/** grid represents a map from which we can know if a certain position is occupied:
	 * 	true if a position is occupied, false if it free	*/
	private boolean[][] grid;
	
	/** snakes is a list which will contain all the snakes playing. It would be enough to
	 * have an array with 2 positions but this more generic approach allows to create a game
	 * with more than 2 snakes if desired. */
	private ArrayList<Snake> snakes;
	
	/** winners is a list for the same reason snakes is. If by chance someone wants to do a
	 *  game with more than 2 snakes there could be more than 1 winner. */
	private ArrayList<Integer> winners;
	
	/** very useful to know if the game has already finished (true) or not (false) */
	private boolean terminated;
	
	/** useful for the function stop to know if the game has just finished (true) or if it
	 * has finished at least one gameStep before the current one (false).
	 * 
	 * Why? Because only when the game has just finished will we want the function stop to
	 * inform that the game is finished and to present the winners. If the game had already
	 * finished for a while we do not want to repeat these informations.
	 */
	private boolean justFinished;

	
	
	/**
	 * NOTE!
	 * String... tupeOfSnake was there to allow the creation of any number of snakes. Currently,
	 * it could receive 2 String instead of that
	 */
	public Simulation(int size, int players,Collection<Node> allNodes, String... typeOfSnake) {
		grid = new boolean[size][size];
		snakes = new ArrayList<Snake>();
		winners = new ArrayList<Integer>(players);
		justFinished=false;
		
		//until a player looses, he is in the list of winners. If he looses he will be eliminated of this list
		for(int i=1;i<=players;i++) {
			winners.add(i);
		}
		
		// coordinate helper values
		int corner1 = (int) (0.25 * Game.GRID_SIZE);
		int corner2 = (int) (0.75 * Game.GRID_SIZE);
			
		Snake snakeAux;
		int i=1;
		int player=1;
		for(String snakeType : typeOfSnake) {
			if(player==1) {
				if(snakeType.equals("S")) {
					snakeType="R";
				}
			}
			switch(snakeType) {
			case "C" : snakeAux = new SControlled(new Position(corner1,corner1), new Position(i,0));
						break;
			case "I" : snakeAux = new SIntelligente(new Position(corner1,corner1), new Position(i,0), this.grid);
						break;
			case "S" : snakeAux = new SuperSmart(new Position(corner1,corner1), new Position(i,0), this.grid, this.getSnakes().get(0));
						break;
			case "ServCl" : snakeAux = new SnakeReseau(new Position(corner1,corner1), new Position(i,0));
						break;
			default:	snakeAux = new SAleatoire(new Position(corner1,corner1), new Position(i,0));
						break;
			}
			this.addSnake(snakeAux);
			TailAvatar tail1 = new TailAvatar(snakeAux.getTail(), Color.CRIMSON,(double)(Game.ELEMENT_SIZE),player);
			allNodes.add(tail1);
			occupyGrid(snakeAux.getTail());
			
			corner1=corner2;
			i=-i;
			player++;
		}

	}

	public void occupyGrid(Position pos) {
		grid[pos.getX()][pos.getY()]=true;
	}

	public void stop() {
		/*if the game has finished before the current game step, then the end of the game and
		 * winner were already announced, so return right away. */
		if(!justFinished) {
			return;
		}
		
		/*If the game has just finished, then we say it and announce the winner. */
		System.out.print("The game is over.");
		if(winners.size()==0) {
			System.out.println("It was a tie!");
		}
		else {
			//here I assume there are only 2 snakes playing so there is just 1 winner maximum
			System.out.println("The winner is Player "+winners.get(0)+". Congratulations!");
		}
		justFinished=false;
	}
	
	public void start() {
		System.out.println("Game started! Let the best win!");
	}

	public boolean isTerminated() {
		return terminated;
	}

	public void addSnake(Snake snake) {
		snakes.add(snake);
	}

	public ArrayList<Snake> getSnakes() {
		return snakes;
	}

	public boolean checkCrash(Position pos) {
		//if true, then the position is already occupied, there is a crash
		//if exception, there is a crash with the borders of the map
		try {
			return grid[pos.getX()][pos.getY()];
		} catch (ArrayIndexOutOfBoundsException exc) {
			return true;
		}
	}

	public void update(ArrayList<Node> allNodes,boolean reseau) {
		Position snake1Head=new Position();
		int i=1;
		Color color=Color.SLATEBLUE;
		for(Snake snake : snakes) {
			if(!snake.getHead().equals(snake.getTail())) {
				allNodes.add(new BodyAvatar(snake.getHead().getX(), snake.getHead().getY(),color, Game.ELEMENT_SIZE));
		}
			if(!reseau) {
				snake.update();
			}
			snake.getHead().translate(snake.getHeadDir());

			allNodes.add(new HeadAvatar(snake.getHead(), color,Game.ELEMENT_SIZE));
			

			if(checkCrash(snake.getHead())) {
				terminated=true;
				justFinished=true;
				//Attention! Remove the object of value i, not of index i!
				winners.remove((Object)i);
				/*
				 * if the 2nd snake looses because the occupied position she is going has just been occupied by the first snake,
				 * then the first snake also looses!
				 */
				if(i==2) {
					if(snake.getHead().equals(snake1Head)) {
						winners.remove((Object)(i-1));
					}
				}
			}

			if(!terminated) {
				grid[snake.getHead().getX()][snake.getHead().getY()]=true;
			}
			snake1Head=snake.getHeadDir();
			i++;
			color=Color.DARKGREEN;
		}
	}
}
