package cs3500.animator.model.shape;

import cs3500.animator.model.state.IState;
import java.util.Map;

/**
 * Represents a shape and its states at specific points in time.
 */
public interface IShape {

  /**
   * Adds a state for an {@link IShape} object at a specific point in time.
   *
   * @param time  int representing the point in time the object has the given state.
   * @param state the {@link IState} object representing all of the information about the shape's
   *              color, size and position.
   * @throws IllegalArgumentException if the time is already in the shapeStates map or if the time
   *                                  given is less than 0.
   */
  void addState(int time, IState state) throws IllegalArgumentException;

  /**
   * Edits the state of the given shape at the given tick.
   *
   * @param tick  represents the tick the state to edit is at
   * @param state represents the new edited state to add at the given tick
   * @throws IllegalArgumentException if no state exists at the tick given to edit
   */
  void editState(int tick, IState state) throws IllegalArgumentException;

  /**
   * Adds a motion to an {@link IShape} by adding two {@link IState}'s and removing any existing
   * states that are between two added. If a stored state already exists at the start or end time,
   * it must be equal to the given state.
   *
   * @param start     {@link IState} representing the start state of the motion
   * @param startTime integer representing start time of the motion
   * @param end       {@link IState} representing the end state of the motion
   * @param endTime   integer representing end time of the motion
   * @throws IllegalArgumentException if adding a state at an already existing tick and the existing
   *                                  state does not equal the one being added or if the start time
   *                                  greater than the end time
   */
  void addMotion(IState start, int startTime, IState end, int endTime)
      throws IllegalArgumentException;

  /**
   * Removes the state from the shape at the given tick.
   *
   * @param tick integer representing the time of the state to be removed
   * @throws IllegalArgumentException if the shape does not contain a state at this tick
   */
  void removeState(int tick) throws IllegalArgumentException;

  /**
   * Returns a copy of the {@link IShape} object.
   *
   * @return a new {@link IShape} object containing the same information as this one.
   */
  IShape getShape();

  /**
   * Returns a copy of an {@link IShape} object's states mapped to the time they occur.
   *
   * @return a new {@code Map<Integer, cs3500.animator.model.state.IState>} containing this object's
   *         states.
   */
  Map<Integer, IState> getShapeStates();

  /**
   * Gets the name of the specific type of {@link IShape}.
   *
   * @return a String representing the type of shape.
   */
  String getShapeType();

  /**
   * Removes an uninterrupted motion between the given times.
   *
   * @param startTime int representing the start time of the motion to remove
   * @param endTime   int representing the end time of the motion to remove
   * @throws IllegalArgumentException if the startTime or endTime are invalid, if there is a state
   *                                  that interrupts the two times, or if the ending time is lesser
   *                                  than the starting time.
   */
  void removeMotion(int startTime, int endTime) throws IllegalArgumentException;

}
