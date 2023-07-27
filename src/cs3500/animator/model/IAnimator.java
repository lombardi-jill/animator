package cs3500.animator.model;

import cs3500.animator.model.shape.IShape;
import cs3500.animator.model.state.IState;
import cs3500.animator.viewmodel.IReadOnlyAnimator;

/**
 * Represents an cs3500.animator.model.Animator that contains {@link IShape} objects.
 */
public interface IAnimator extends IReadOnlyAnimator {

  /**
   * Adds a copy of the given {@link IShape} object mapped to its given name.
   *
   * @param shape represents the {@link IShape} object to add
   * @param name  string representing the name of the {@link IShape} object
   * @throws IllegalArgumentException if the map of objects to animate already contains this name
   */
  void addShape(IShape shape, String name) throws IllegalArgumentException;

  /**
   * Adds a state to an already existing shape in the map of objects to animate.
   *
   * @param name        string representing name of shape to add state to
   * @param stateToAdd  {@link IState} to add to the shape's map of states
   * @param timeOfState int representing time when added state occurs
   * @throws IllegalArgumentException if the given name of the shape is not contained in the map of
   *                                  objects to animate or if the shape's map of states already
   *                                  contains a state at the given time
   */
  void addShapeState(String name, IState stateToAdd, int timeOfState)
      throws IllegalArgumentException;

  /**
   * Edits the state occurring at the given tick and of the given shape. Accomplishes this by adding
   * a new state in place of the state previously at this tick value with the new values.
   *
   * @param name        String representing the name of the shape to edit
   * @param timeOfState int representing the time at which the edited state occurs
   * @param r           int representing the new red value of the state to edit
   * @param g           int representing the new green value of the state to edit
   * @param b           int representing the new blue value of the state to edit
   * @param x           int representing the new x position of the state to edit
   * @param y           int representing the new y position of the state to edit
   * @param w           int representing the new width of the state to edit
   * @param h           int representing the new height of the state to edit
   * @throws IllegalArgumentException if the given shape does not exist, if the given shape does not
   *                                  have a state at the given tick value, or if the values to make
   *                                  an {@link IState} with are poorly formed
   */
  void editShapeState(String name, int timeOfState, int r, int g, int b, int x, int y, int w, int h)
      throws IllegalArgumentException;

  /**
   * Adds a motion to the shape with the given name.
   *
   * @param name      String representing the name of the shape to add the motion to
   * @param start     {@link IState} representing the start state of the motion
   * @param startTime integer representing start time of the motion
   * @param end       {@link IState} representing the end state of the motion
   * @param endTime   integer representing end time of the motion
   * @throws IllegalArgumentException if a shape with this name is not in the {@link IAnimator}'s
   *                                  objects to animate or if the start time is greater than the
   *                                  end time
   */
  void addShapeMotion(String name, IState start, int startTime, IState end, int endTime)
      throws IllegalArgumentException;

  /**
   * Removes the shape with the given name from the {@link IAnimator}'s objects to animate.
   *
   * @param name String representing the name of the shape to remove
   * @throws IllegalArgumentException if a shape with this name is not in the {@link IAnimator}'s
   *                                  objects to animate
   */
  void removeShape(String name) throws IllegalArgumentException;

  /**
   * Removes the state with the given tick from the shape with the given name.
   *
   * @param name         String representing the name of the shape to remove the state from
   * @param tickToRemove integer representing the time at which to remove the state
   * @throws IllegalArgumentException if a shape with this name is not in the {@link IAnimator}'s
   *                                  objects to animate or if there is no state at the given tick
   */
  void removeShapeState(String name, int tickToRemove) throws IllegalArgumentException;

  /**
   * Removes a motion from the given shape starting at the given start time and ending at the given
   * end time.
   *
   * @param name      String representing the name of the shape to remove a motion from
   * @param startTime int representing the time at which the motion to remove begins
   * @param endTime   int representing the time at which the motion to remove ends
   * @throws IllegalArgumentException if the shape does not exist in this {@link IAnimator}, if the
   *                                  startTime or endTime are invalid, if there is a state that
   *                                  interrupts the two times, or if the starting time is greater
   *                                  than the ending time.
   */
  void removeShapeMotion(String name, int startTime, int endTime) throws IllegalArgumentException;

  /**
   * Sets the origin of the canvas by setting the given x and y as the top left corner.
   *
   * @param x an int representing the x-position to set the top-left corner of the canvas as
   * @param y an int representing the y-position to set the top-left corner of the canvas as
   */
  void setTopLeft(int x, int y);

  /**
   * Sets the width of the canvas of this animator.
   *
   * @param width int representing the desired canvas width
   */
  void setWidth(int width);

  /**
   * Sets the height of the canvas of this animator.
   *
   * @param height int representing the desired canvas height
   */
  void setHeight(int height);
}
