package cs3500.animator.view.visual.popupwindow;

import java.awt.event.ActionListener;
import java.util.Map;

/**
 * Represents an interface for a window that appears when a user wishes to either edit keyframes of
 * an existing shape or add keyframes to an existing shape in an animation.
 */
public interface IPopUpWindow {

  /**
   * Gets the integer values that have been entered by the user into the {@link
   * javax.swing.JTextField}'s that are on the window and stores them as a {@code Map<String,
   * Integer>}.
   *
   * @return a {@code Map<Integer, String>} that contains the values that the user entered for
   *         editing or adding a keyframe.
   * @throws IllegalArgumentException if the user does not enter integers into the fields when
   *                                  adding or editing a key frame
   */
  Map<String, Integer> getFieldValues() throws IllegalArgumentException;

  /**
   * Adds an {@link ActionListener} to the button that is on the window that pops up when editing or
   * adding a keyframe.
   *
   * @param l an {@link ActionListener} that will listen for when the button on the window is
   *          clicked
   */
  void addButtonListener(ActionListener l);

  /**
   * Makes the window for editing or adding a keyframe visible or invisible based on what is handed
   * to the method.
   *
   * @param visible a boolean that is true if the window is to be made visible and false if it is to
   *                be invisible
   */
  void makeWindowVisible(boolean visible);

  /**
   * Populates the {@link javax.swing.JTextField}'s on the window with the values that already exist
   * for that keyframe at that tick in the animation.
   *
   * @param tick   the tick that the keyframe occurs at
   * @param red    the red value of the shape at that keyframe
   * @param green  the green value of the shape at that keyframe
   * @param blue   the blue value of the shape at that keyframe
   * @param width  the width value of the shape at that keyframe
   * @param height the height value of the shape at that keyframe
   * @param x      the x-position of the shape at that keyframe
   * @param y      the y-position of the shape at that keyframe
   */
  void populateValues(int tick, int red, int green, int blue, int width, int height, int x, int y);

  /**
   * Clears the text that has been inputted into the {@link javax.swing.JTextField}'s on the window
   * for editing or adding keyframes.
   */
  void clearText();
}
