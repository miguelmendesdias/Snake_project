package snakeProject;

/**
 * This class represents a type of snake, one whose next direction is chosen by the player.
 * Being a snake, the thing it must do is implement a method to calculate the next direction
 * the head should take. In this case, following the player's order through the keyboard.
 * @author miguel dias
 *
 */
public class SControlled extends Snake {
	
	public SControlled(Position initialPosition, Position initialDirection) {
		super(initialPosition, initialDirection);
	}

	/**
	 * From the point of view of the snake, there are 3 possible directions:
	 * forward, left or right.
	 * 
	 * Warning! If the player chooses the direction opposite of the current direction,
	 * the method will return the current direction and ignore the order of the player,
	 * as the snake cannot go backwards.
	 * 
	 * @return the new direction of the head
	 */
	public Position calcNewHeadDirection() {
		/*It cannot go back. If going horizontal, this means we do not
		 * take into account the cases LEFT nor RIGHT here.
		 * */
		if(headDirection.getY()==0) {
			switch (Keyboard.getLastKeyCode()) {
			case UP:    return(new Position(0,-1));
			case DOWN:	return(new Position(0,1));
			default:	return headDirection;
		}			
		}

		/*It cannot go back. If going vertical, this means we do not
		 * take into account the cases UP nor DOWN here.
		 */
		if(headDirection.getX()==0) {
			switch (Keyboard.getLastKeyCode()) {
			case RIGHT: return(new Position(1,0));
			case LEFT:	return(new Position(-1,0));
			default:	return headDirection;
		}
		}
		return headDirection;
	}
}
