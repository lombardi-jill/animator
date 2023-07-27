package cs3500.animator.model.state;

import cs3500.animator.model.position.IPosition;

/**
 * A State represents an instantaneous state of an object to animate at a specific point in time.
 */
public interface IState {

  /**
   * Gets an array that contains the Red, Green, and Blue color values on the scale of 0 to 255 of
   * the {@link IState} object.
   *
   * @return an array with the red value at position 0, the green value at position 1, and the blue
   *         value at position 2.
   */
  int[] getRGB();

  /**
   * Gets a copy of the position of the {@link IState} object.
   *
   * @return {@link IPosition} representing the {@link IState} object's position.
   */
  IPosition getPosition();

  /**
   * Gets an array that contains the width and height of the {@link IState} object.
   *
   * @return an array with the width at position 0 and the height at position 1.
   */
  int[] getWidthAndHeight();
}
