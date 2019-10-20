package snakeProject;

/**
 * This class represents a type of snake, one that imitates another snake.
 * It is used only in the case of the client-server application.
 * 
 * 
 * It is very simple, it always keeps its current direction, and the job of
 * changing that direction is performed by the main function of the client or server application.
 * @author miguel dias
 *
 */
public class SnakeReseau extends Snake{
	
	public SnakeReseau(Position initialPosition, Position initialDirection) {
		super(initialPosition, initialDirection);
	}
	
	public Position calcNewHeadDirection() {
		return headDirection;
	}

}
