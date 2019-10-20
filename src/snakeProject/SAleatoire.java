package snakeProject;

/**
 * This class represents a type of snake, one that chooses its next direction randomly.
 * Being a snake, the thing it must do is implement a method to calculate the next
 * direction the head should take. In this case, this is done randomly.
 * @author miguel dias
 *
 */
public class SAleatoire extends Snake{
	
	public SAleatoire(Position initialPosition, Position initialDirection) {
		super(initialPosition, initialDirection);
	}
	
	/**
	 * From the point of view of the snake, there are 3 possible directions:
	 * forward, left or right.
	 * 
	 * To choose randomly between the 3, we use the function Math.random(), from which
	 * we get a random double between 0 and 1. As such, the probability of a number being in
	 * the intervals [0,1/3], [1/3,2/3] or [2/3,1] is the same for the 3 cases.
	 * 
	 * This direction is obviously related with the current direction (cannot go backwards).
	 * 
	 * @return the new direction of the head
	 */
	public Position calcNewHeadDirection() {
		
		double rand = Math.random();

		if(rand<(double)1/3) {
			//if currently going vertical, then turn right
			if(headDirection.getX()==0) {
				return new Position(1,0);
			}
			
			//if currently going horizontal, then go up
			if(headDirection.getY()==0) {
				return new Position(0,1);
			}
		} else if(rand<(double)2/3){
			//if currently going vertical, then turn left
			if(headDirection.getX()==0) {
				return new Position(-1,0);
			}
			//if currently going horizontal, then go down
			if(headDirection.getY()==0) {
				return new Position(0,-1);
			}
		}
		
		//Finally, keep the current direction if the function has not yet returned.
		return headDirection;
	}

}
