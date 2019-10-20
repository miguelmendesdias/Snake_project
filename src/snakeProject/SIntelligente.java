package snakeProject;

import java.util.ArrayList;

/**
 * This class represents a type of snake, one that chooses its next direction intelligently.
 * Being a snake, the thing it must do is implement a method to calculate the next
 * direction the head should take. In this case, this is done so that it chooses randomly,
 * but only between the directions that dont lead to immediate crash.
 * @author miguel dias
 *
 */
public class SIntelligente extends Snake {
	private boolean[][] grid;
		
	public SIntelligente(Position initialPosition, Position initialDirection, boolean[][] grid) {
		super(initialPosition, initialDirection);
		this.grid=grid;
	}
	
	public Position calcNewHeadDirection() {
		double rand = Math.random();
		
		ArrayList<Position> chooseDir = new ArrayList<Position>();
		
		/** listOfPossibleDir will have the 3 possible directions the snake can take.
		 * From there we will then choose and place the good options in chooseDir
		 */
		ArrayList<Position> listOfPossibleDir = new ArrayList<Position>();
		listOfPossibleDir.add(new Position(headDirection.getX(), headDirection.getY()));
		
		//if vertical can go right or left
		if(headDirection.getX()==0) {
			listOfPossibleDir.add(new Position(1, 0));
			listOfPossibleDir.add(new Position(-1, 0));
		}
		//if horizontal can go down or up
		else if(headDirection.getY()==0) {
			listOfPossibleDir.add(new Position(0, 1));
			listOfPossibleDir.add(new Position(0, -1));
		}
		
		for(Position pos : listOfPossibleDir) {
			try{
				if(!grid[head.getX()+pos.getX()][head.getY()+pos.getY()]) {
					chooseDir.add(pos);
				}
			} catch(ArrayIndexOutOfBoundsException exc) {
			//no treatment needed, by catching the exception the bad direction wont be added
		}
		}

		int cases = chooseDir.size();

		switch(cases) {
		case 3: if(rand<(double)1/3)
					return chooseDir.get(0);
				if(rand<(double)2/3)
					return chooseDir.get(1);
				return chooseDir.get(2);
		case 2: if(rand<(double)1/2)
					return chooseDir.get(0);
				return chooseDir.get(1);
		case 1: return chooseDir.get(0);
		default: return headDirection;		
		}
	}
		
}

