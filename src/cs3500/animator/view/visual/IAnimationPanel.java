package cs3500.animator.view.visual;

/**
 * Represents an interface for functionality provided by the panel an animation is displayed on.
 */
public interface IAnimationPanel {

  /**
   * Sets which shape should be highlighted in the animation.
   *
   * @param name String representing the name of the shape in the animation to be highlighted
   */
  void setHighlightedShape(String name);

  /**
   * Gets the current tick of the animation.
   *
   * @return an integer representing the current tick of the animation
   */
  int getCurrTick();

  /**
   * Sets the current tick of the animation back to 0, essentially restarting the animation.
   */
  void resetTick();
}
