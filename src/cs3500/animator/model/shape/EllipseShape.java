package cs3500.animator.model.shape;

import cs3500.animator.model.state.IState;
import java.util.Map;
import java.util.Objects;

/**
 * An implementation of an {@link IShape} represented as an Ellipse.
 */
public class EllipseShape extends AShape {

  /**
   * Constructs an instance of an {@link EllipseShape} initialized with an empty map of {@link
   * IState} objects mapped to points in time.
   */
  public EllipseShape() {
    super();
  }

  /**
   * Constructs an instance of an {@link EllipseShape} with the given map of {@link IState} objects
   * mapped to points in time.
   *
   * @param shapeStates a map containing the states of the Ellipse at specific points in time.
   * @throws IllegalArgumentException if a time key in shapeStates is less than 0.
   */
  protected EllipseShape(Map<Integer, IState> shapeStates) {
    super(shapeStates);
  }

  @Override
  public String getShapeType() {
    return "ellipse";
  }

  @Override
  public IShape getShape() {
    return new EllipseShape(this.shapeStates);
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (!(that instanceof EllipseShape)) {
      return false;
    }
    EllipseShape other = (EllipseShape) that;
    return this.shapeStates.equals(other.shapeStates);
  }

  @Override
  public int hashCode() {
    return Objects.hash(shapeStates);
  }

}
