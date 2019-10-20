package snakeProject;

import javafx.scene.input.KeyCode;

public class Keyboard {

	/** To know if since the last gameStep any key was read from the keyboard or not */
	private static boolean updated=false;

	/** After a step, the program is ready to receive another direction from the keyboard */
	public static void readyToReceive(){
		updated=false;
	}

	/** Last-pressed KeyCode */
	private static KeyCode lastKeyCode = KeyCode.ENTER;

	/** You shall not create a Keyboard object */
	private Keyboard() { }

	/**
	 * If somebody clicks very quickly in 2 different keys, it is the first one that must be
	 * taken into account. Thats why the pressed key is only accepted if since the last gameStep
	 * there wasnt any key accepted. If there was already a key accepted updated will be true and we
	 * must not save this new command.
	 * Automatically invoked by the keyboard event listener, stores the KeyCode of the first key pressed
	 * after the last gameStep.
	 */
	public static void storeLastKeyCode(KeyCode keycode) {
		//
		if(updated) {
			return;
		} else{
			lastKeyCode = keycode;
			updated=true;
		}
	}

	/** 
	 * Get the last typed KeyCode.
	 * @return the last pressed KeyCode.
	 */
	public static KeyCode getLastKeyCode() { return lastKeyCode; }

}
