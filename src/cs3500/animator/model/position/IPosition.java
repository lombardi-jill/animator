package cs3500.animator.model.position;

/**
 * This interface represents a position.
 */
public interface IPosition {

  /**
   * Gets the x coordinate of this position.
   *
   * @return int representing x coordinate of position
   */
  int getX();

  /**
   * Gets the y coordinate of this position.
   *
   * @return int representing y coordinate of position
   */
  int getY();

  /**
   * Sets the x coordinate of this position.
   *
   * @param x int representing the x coordinate to set the position to.
   */
  void setX(int x);

  /**
   * Sets the y coordinate of this position.
   *
   * @param y int representing the y coordinate to set the position to.
   */
  void setY(int y);

}
