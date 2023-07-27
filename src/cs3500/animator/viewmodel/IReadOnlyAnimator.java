package cs3500.animator.viewmodel;

import cs3500.animator.model.shape.IShape;
import java.util.Map;

/**
 * Represents only the read functionality of an Animator. Offers functions to query Animator data,
 * but no abilities to change the underlying Animator.
 */
public interface IReadOnlyAnimator {

  /**
   * Returns a copy of the collection of {@link IShape} objects mapped to their name.
   *
   * @return a new map containing the {@link IShape} objects and their name.
   */
  Map<String, IShape> getObjectsToAnimate();

  /**
   * Queries the width of the canvas of the {@link IReadOnlyAnimator} object.
   *
   * @return an int representing the width of the canvas for this animator.
   */
  int getWidth();

  /**
   * Queries the height of the canvas of the {@link IReadOnlyAnimator} object.
   *
   * @return an int representing the height of the canvas for this animator.
   */
  int getHeight();

  /**
   * Queries the top left x-coordinate of the canvas origin.
   *
   * @return an int representing the x-coordinate of the top-left corner of the canvas
   */
  int getTopLeftX();

  /**
   * Queries the top left y-coordinate of the canvas origin.
   *
   * @return an int representing the y-coordinate of the top-left corner of the canvas
   */
  int getTopLeftY();
}
