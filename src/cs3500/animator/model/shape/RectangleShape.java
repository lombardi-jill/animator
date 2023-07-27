package cs3500.animator.model.shape;

import cs3500.animator.model.state.IState;
import java.util.Map;
import java.util.Objects;

/**
 * An implementation of an {@link IShape} represented as a Rectangle.
 */
public class RectangleShape extends AShape {

  /**
   * Constructs an instance of an {@link RectangleShape} initialized with an empty map of {@link
   * IState} objects mapped to points in time.
   */
  public RectangleShape() {
    super();
  }

  /**
   * Constructs an instance of an {@link RectangleShape} with the given map of {@link IState}
   * objects mapped to points in time.
   *
   * @param shapeStates a map containing the states of the Rectangle at specific points in time.
   * @throws IllegalArgumentException if a time key in shapeStates is less than 0.
   */
  protected RectangleShape(Map<Integer, IState> shapeStates) {
    super(shapeStates);
  }

  @Override
  public String getShapeType() {
    return "rectangle";
  }

  @Override
  public IShape getShape() {
    return new RectangleShape(this.shapeStates);
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (!(that instanceof RectangleShape)) {
      return false;
    }
    RectangleShape other = (RectangleShape) that;
    return this.shapeStates.equals(other.shapeStates);
  }

  @Override
  public int hashCode() {
    return Objects.hash(shapeStates);
  }

}
