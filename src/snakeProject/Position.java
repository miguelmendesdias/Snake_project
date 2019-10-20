package snakeProject;

/**
 * A position can either save an actual position of coordinates x and y or represent
 * a direction. For example a positive horizontal direction can be x=1 and y=0.
 * 
 * @author miguel dias
 * 
 */
public class Position {
	private int x;
	private int y;

	public Position(int x, int y) {
		this.x=x;
		this.y=y;
	}

	/**
	 * This constructor is useful to create a position whose parameters are a copy of the
	 * parameters of another position.
	 * 
	 * Atention! We copy the parameters, not the reference of the original object!
	 * 
	 * @param pos The position we want to make a copy of
	 */
	public Position(Position pos) {
		this.x=pos.getX();
		this.y=pos.getY();
	}
	
	public Position() {	
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	/**
	 * This function is useful to check if 2 positions coincide.
	 * 
	 * Attention! To check this we check if the parameters have the same value,
	 * we do NOT compare the reference!
	 * 
	 * @param other The position we want to compare with.
	 * @return if the parameters x and y are equal or not in the compared positions
	 */
	public boolean equals(Position other) {
		if(this.x==other.getX()&&this.y==other.getY()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * We give the coordinates of the translation through a position.
	 * So this function allows to move in a certain direction.
	 * @param direction The direction we want to move through.
	 */
	public void translate(Position direction) {
		this.x+=direction.x;
		this.y+=direction.y;
	}

	/**
	 * This function is currently never used in my project. However, because it can be useful
	 * it is left here. It simply provides what to display when we call for example println
	 * to System.out. Could be useful for testing or debugging.
	 */
	public String toString() {
		return "Position: x="+x+", y="+y+".";
	}
}
