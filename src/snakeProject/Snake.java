package snakeProject;

import java.util.ArrayList;

/**
 * The class is abstract because:
 * 
 * 1)no matter what the type of snake is, it will always have need of some
 * attributes: a tail, body and head of which the snake consists and the direction of the movement: headDirection
 * 
 * 2)no matter what the type of snake is,
 * there must be a function that allows to calculate the next position for the head
 */
abstract public class Snake {

	/**
	 * A snake is just a group of positions. tail exists because its design is special.
	 * head exists because the next position must be chosen taking the head position into account
	 * and also because its design is special.
	 * headDirection is needed to determine the next possible positions for the head.
	 */
	
	/* head and headDirection are protected because the subclasses of snake need to use this attributes to calculate
	   the direction a snake should take */
	protected Position head;
	protected Position headDirection;
	private Position tail;
	private ArrayList<Position> body;

	public Snake(Position initialPosition,Position initialDirection) {
		//the tail and head coincide in the beginning of the game
		this.head=new Position(initialPosition);
		this.tail=new Position(initialPosition);
		this.headDirection=new Position(initialDirection);
		this.body = new ArrayList<Position>();
	}

	public Position getHead() {
		return this.head;
	}

	public Position getTail() {
		return tail;
	}

	public ArrayList<Position> getBody(){
		return body;
	}

	public Position getHeadDir() {
		return headDirection;
	}

	/**
	 * A snake, whatever its type, must have a function that allows to calculate
	 * the next position of the head
	 * @param head				With the head and its direction we can determine the 3 possible
	 * @param headDirection		next head positions. It is this function's job to choose from
	 * 							that list. How the choice is made will depend on the type of snake.
	 * @return the direction the snake will take in order to go to the next position
	 */
	abstract public Position calcNewHeadDirection(/*Position head, Position headDirection*/);

	/**
	 * When a snake is updated, the place where the head currently is becomes part of the body
	 * and the the head will occupy a new place. The direction to take is calculated by an abstract
	 * method because the way to calculate the direction to take depends on the snake type.
	 */
	public void update() {
		/*We skip this part if it is the first play of the game (in which case the head and the
		 *tail occupy the same position) because in that case the direction is already defined. */
		if(!head.equals(tail)) {
			body.add(head);
			headDirection=calcNewHeadDirection();
		}
	}

	/**
	 * This function is used in the client-server application only.
	 */
	public void setHeadDir(Position headDir) {
		this.headDirection=headDir;
	}

	/**
	 * This function is currently not used in the project, but it can be useful
	 * for testing or debugging purposes.
	 */
	public String toString() {
		String bodyStr="";
		for(Position pos : this.body) {
			bodyStr+=pos.toString();
		}
		return "TAIL: "+tail.toString()+"BODY: "+bodyStr+"HEAD: "+head.toString();
	}

}
