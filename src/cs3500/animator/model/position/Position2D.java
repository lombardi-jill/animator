package cs3500.animator.model.position;

import java.util.Objects;

/**
 * Represents an implementation of {@link IPosition} in 2D space.
 */
public class Position2D implements IPosition {

  private int x;
  private int y;

  /**
   * Constructs an instance of a {@link Position2D} object with x and y position.
   *
   * @param x integer representing the x position
   * @param y integer representing the y position
   */
  public Position2D(int x, int y) {
    this.setX(x);
    this.setY(y);
  }

  /**
   * Constructs an instance of a {@link Position2D} object with an {@link IPosition}.
   *
   * @param p the {@link IPosition} object to make a copy of
   */
  public Position2D(IPosition p) {
    this.setX(p.getX());
    this.setY(p.getY());
  }

  @Override
  public int getX() {
    return x;
  }

  @Override
  public int getY() {
    return y;
  }

  @Override
  public void setX(int x) {
    this.x = x;
  }

  @Override
  public void setY(int y) {
    this.y = y;
  }

  @Override
  public boolean equals(Object a) {
    if (this == a) {
      return true;
    }
    if (!(a instanceof Position2D)) {
      return false;
    }

    Position2D that = (Position2D) a;

    return ((Math.abs(this.x - that.x) < 0.01)
        && (Math.abs(this.y - that.y) < 0.01));
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.x, this.y);
  }

}
