package snakeProject;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * An avatar for a tail element to display it in the GraphicalDisplay. This is
 * just a filled triangle with color and orientation set at instantiation time.
 * As the tail is in the same place throughout the entire game, once created this avatar
 * is never changed. The orientation depends on the player to which the tail belongs.
 * 
 * Warning: after instantiation, the avatar must be added to the
 * GraphicalDisplay scene graph to be actually displayed.
 * 
 * This class uses the Java FX toolkit included in Oracle Java.
 * 
 * @author miguel dias
 *
 */
public class TailAvatar extends Polygon {
	/**
	 * Creates a triangular avatar for the tail by creating a polygon with 3 vertices.
	 * The created triangle will be horizontal and created assuming that the orientation
	 * is positive horizontal. If the orientation is the opposite we only need to rotate
	 * the avatar 180 degrees. The orientation to use depends on the player. The avatar will be
	 * displayed at the tail, which never changes, once it has been added to the scene graph.
	 * 
	 * @param tail  The tail to display
	 * @param fgColor Foreground color of the triangle
	 * @param size Base and height of the triangle
	 * @param player We need it to determine whether the horizontal orientation is positive or negative
	 */
	public TailAvatar(Position tail, Color fgColor, double size, int player) {
		super(new double[] {tail.getX()*size,tail.getY()*size+size/2,tail.getX()*size+size,tail.getY()*size,tail.getX()*size+size,tail.getY()*size+size});
		//to change the orientation we only need to rotate 180 degrees
		if(player==2) {
			this.setRotate(180);
		}
		setStroke(Color.BLACK);
		setStrokeWidth(3.0);
	}
}