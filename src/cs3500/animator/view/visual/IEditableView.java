package cs3500.animator.view.visual;

import cs3500.animator.view.IAnimationView;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionListener;

/**
 * An interface representing a view for the animation that user can interact with. Actions a user
 * can take when using a view implementing this interface are: pause, resume, start, restart, loop,
 * unloop, change speed, add keyframe, edit keyframe, delete keyframe, add shape, or delete shape.
 * The interface also provides functionality for adding listeners to a view in order for listeners
 * to get user commands and respond to them using the public methods provided.
 */
public interface IEditableView extends IAnimationView {

  /**
   * Repaints the view and its components to reflect changes based on user input.
   */
  void repaintAnimation();

  /**
   * Allows someone to specify the {@link ActionListener} object that they would like to observe
   * action events caused by the user in this view.
   *
   * @param actionListener represents the {@link ActionListener} object that listens to this view
   */
  void addActionEventListeners(ActionListener actionListener);

  /**
   * Allows someone to specify the {@link ChangeListener} object that they would like to observe
   * {@link javax.swing.event.ChangeEvent}'s caused by the user in this view.
   *
   * @param changeListener represents the {@link ChangeListener} object that listens to changes in
   *                       this view
   */
  void addChangeListener(ChangeListener changeListener);

  /**
   * Allows someone to specify the {@link ListSelectionListener} object that they would like to
   * observe {@link javax.swing.event.ListSelectionEvent}'s caused by the user in this view.
   *
   * @param listSelectionListener represents the {@link ListSelectionListener} object that listens
   *                              to changes in this view
   */
  void addListSelectionListener(ListSelectionListener listSelectionListener);

  /**
   * Pauses an animation. If an animation has not yet begun, has no effect. If an animation is
   * already paused, has no effect.
   */
  void pauseAnimation();

  /**
   * Resumes an animation. Is an animation is already playing, has no effect. Cannot be called when
   * the animation has not yet started.
   */
  void resumeAnimation();

  /**
   * Starts an animation. May only be called at the beginning of an animation before it has
   * started.
   */
  void startAnimation();

  /**
   * Restarts an animation by bringing it back to the beginning and waiting for user input to start
   * again. May not be called before an animation has started.
   */
  void restartAnimation();

  /**
   * Indicates that an animation should be looped. If an animation is already looping, calling this
   * has no effect.
   */
  void loopAnimation();

  /**
   * Indicates that an animation should no longer be looped. If an animation is not currently
   * looping and this is called, it has no effect.
   */
  void unLoopAnimation();

  /**
   * Updates the speed of an animation according to user inputs.
   */
  void updateSpeed();

  /**
   * Gets the shape currently selected by the user.
   *
   * @return a String representing the name of the shape currently selected
   */
  String getShapeSelected();

  /**
   * Gets the state (or keyframe) that is currently selected by the user. Returns null if there is
   * no state selected.
   *
   * @return an Integer representing the tick at which the selected state occurs
   */
  Integer getStateSelected();

  /**
   * Refreshes the list of shapes to be displayed by re-querying the valid shapes from the model and
   * repainting.
   */
  void refreshShapeList();

  /**
   * Refreshes the list of keyframes to be displayed to the user.
   */
  void refreshKeyFrameList();

  /**
   * Gets the values of the edited key frame by mapping the changed values (Integers) to identifiers
   * (name of attribute).
   *
   * @return a {@code Map<String, Integer>} of state attribute's names mapped to their changed
   *         values
   * @throws IllegalArgumentException if the user does not enter integers into the fields when
   *                                  adding or editing a key frame
   */
  Map<String, Integer> getEditedKeyframeValues() throws IllegalArgumentException;

  /**
   * Gets the values of the added key frame by mapping the added values (Integers) to identifiers
   * (name of attribute).
   *
   * @return a {@code Map<String, Integer>} of state attribute's names mapped to their added values
   * @throws IllegalArgumentException if the user does not enter integers into the fields when
   *                                  adding or editing a key frame
   */
  Map<String, Integer> getAddedKeyFrameValues() throws IllegalArgumentException;

  /**
   * Gets an array representing information of the shape to add. The array is of size 2. At position
   * 0, there is an identifier representing the type of the shape. At position 1, there is a String
   * with the name of the shape.
   *
   * @return a 1-D array of Strings where position 0 contains the type of shape and position 1
   *         contains the shape's name
   */
  String[] getInfoOfShapeToAdd();

  /**
   * Displays an error message to the user based on the given string.
   *
   * @param message String representing the message to display to the user.
   */
  void displayErrorMessage(String message);
}
