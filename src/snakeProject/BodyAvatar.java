package snakeProject;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * An avatar for a body element to display it in the GraphicalDisplay. This is
 * just a filled square. The body avatar is created and then it is never changed. This is enough because when a
 * body avatar occupiesa position it will stay there until the end of the game.
 * 
 * Warning: after instantiation, the avatar must be added to the
 * GraphicalDisplay scene graph to be actually displayed.
 * 
 * This class uses the Java FX toolkit included in Oracle Java.
 * 
 * @author miguel dias
 *
 */
public class BodyAvatar extends Rectangle {
	/**
	 * Creates a square avatar for the head, which will be displayed
	 * at the body position once it has been added to the scene graph.
	 * 
	 * @param x and y: Position of the body to display
	 * @param fgColor Foreground color of the square
	 * @param elementSize : the size of the side of the square
	 */
	public BodyAvatar(int x, int y, Color fgColor, double elementSize) {
		super(elementSize,elementSize, fgColor);
		this.setTranslateX(x*elementSize);
		this.setTranslateY(y*elementSize);
		setArcWidth((double)elementSize/4);
		setArcHeight((double)elementSize/4);
		setStroke(Color.BLACK);
		setStrokeWidth(3.0);
	}
}
