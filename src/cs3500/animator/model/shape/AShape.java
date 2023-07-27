package cs3500.animator.model.shape;

import cs3500.animator.model.state.IState;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract base class for implementations of {@link IShape}.
 */
public abstract class AShape implements IShape {

  //INVARIANT: None of the shape's states are mapped to a negative number.
  protected final Map<Integer, IState> shapeStates;

  /**
   * Constructs an instance of an {@link AShape} initialized with an empty map of {@link IState}
   * objects mapped to points in time.
   */
  protected AShape() {
    this.shapeStates = new HashMap<>();
  }

  /**
   * Constructs an instance of an {@link AShape} with the given map of {@link IState} objects mapped
   * to points in time.
   *
   * @param shapeStates a map containing the states at specific points in time.
   * @throws IllegalArgumentException if a time key in shapeStates is less than 0.
   */
  protected AShape(Map<Integer, IState> shapeStates) {
    for (int key : shapeStates.keySet()) {
      if (key < 0) {
        throw new IllegalArgumentException("Cannot have a negative time.");
      }
    }
    this.shapeStates = new HashMap<>(shapeStates);
  }

  @Override
  public void addState(int time, IState state) throws IllegalArgumentException {
    if (shapeStates.containsKey(time)) {
      throw new IllegalArgumentException("Already a state entered for this time.");
    }
    if (time < 0) {
      throw new IllegalArgumentException("Cannot have a negative time.");
    }
    shapeStates.put(time, state);
  }

  @Override
  public void editState(int tick, IState state) {
    if (!shapeStates.containsKey(tick)) {
      throw new
          IllegalArgumentException("Cannot edit tick that has not been defined for this shape.");
    }
    shapeStates.put(tick, state);
  }

  @Override
  public void addMotion(IState start, int startTime, IState end, int endTime)
      throws IllegalArgumentException {
    if (endTime < startTime) {
      throw new IllegalArgumentException("Start time cannot be greater than end time.");
    }
    if (shapeStates.containsKey(startTime)) {
      if (!shapeStates.get(startTime).equals(start)) {
        throw new IllegalArgumentException("Already a state entered for this time.");
      }
    }
    if (shapeStates.containsKey(endTime)) {
      if (!shapeStates.get(endTime).equals(end)) {
        throw new IllegalArgumentException("Already a state entered for this time.");
      }
    }
    shapeStates.put(startTime, start);
    shapeStates.put(endTime, end);
    List<Integer> keys = new ArrayList<>(shapeStates.keySet());
    Collections.sort(keys);
    int diffBetweenStates = keys.indexOf(endTime) - keys.indexOf(startTime);
    //If there are states in between the motion, remove them
    if (diffBetweenStates > 1) {
      for (int i = keys.indexOf(startTime) + 1; i < keys.indexOf(endTime); i++) {
        removeState(keys.get(i));
      }
    }
  }

  @Override
  public void removeState(int tick) throws IllegalArgumentException {
    if (!shapeStates.containsKey(tick)) {
      throw new IllegalArgumentException("Shape does not contain a state at this tick.");
    }
    shapeStates.remove(tick);
  }

  @Override
  public void removeMotion(int startTime, int endTime) throws IllegalArgumentException {
    if (endTime < startTime) {
      throw new IllegalArgumentException("Start time cannot be greater than end time.");
    }
    List<Integer> keys = new ArrayList<>(shapeStates.keySet());
    Collections.sort(keys);
    int diffBetweenStates = keys.indexOf(endTime) - keys.indexOf(startTime);
    if (diffBetweenStates > 1) {
      throw new IllegalArgumentException("Not a motion because there are states in between.");
    }
    this.removeState(startTime);
    this.removeState(endTime);
  }

  @Override
  public Map<Integer, IState> getShapeStates() {
    return new HashMap<>(Map.copyOf(shapeStates));
  }

  @Override
  public abstract String getShapeType();

  @Override
  public abstract boolean equals(Object that);

  @Override
  public abstract int hashCode();

}
