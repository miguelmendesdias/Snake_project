package snakeProject;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


/**
 * An avatar for a head element to display it in the GraphicalDisplay. This is
 * just a filled circle with color and radius set at instantiation time. The head
 * avatar is created and then it is never changed. This is enough because when a
 * body avatar occupies the position that is currently occupied by the head, the head
 * avatar will no longer be visible. This is because the body avatar is a full square.
 * 
 * Warning: after instantiation, the avatar must be added to the
 * GraphicalDisplay scene graph to be actually displayed.
 * 
 * This class uses the Java FX toolkit included in Oracle Java.
 * 
 * @author miguel dias
 *
 */
public class HeadAvatar extends Circle {
	/**
	 * Creates a circular avatar for the head, which will be displayed
	 * at the current head position once it has been added to the scene graph.
	 * 
	 * @param head  The head to display
	 * @param fgColor Foreground color of the circle
	 * @param diameter The diameter of the circle that represents the head
	 */
	public HeadAvatar(Position head, Color fgColor, double diameter) {
		super((head.getX()+0.5)*diameter, (head.getY()+0.5)*diameter, diameter/2, fgColor);
		setStroke(Color.BLACK);
		setStrokeWidth(3.0);
	}
}