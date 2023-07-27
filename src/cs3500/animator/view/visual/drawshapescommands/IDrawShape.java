package cs3500.animator.view.visual.drawshapescommands;

import java.awt.Graphics2D;

/**
 * Represents an interface for drawing specific shapes using a {@link Graphics2D} object.
 */
public interface IDrawShape {

  /**
   * Draws the shape using the given attributes.
   *
   * @param g2d           {@link Graphics2D} object used to draw the shape
   * @param isHighlighted boolean representing whether this shape should be drawn highlighted
   * @param r             int representing the red value of the color
   * @param g             int representing the green value of the color
   * @param b             int representing the blue value of the color
   * @param x             int representing the x position of the shape
   * @param y             int representing the y position of the shape
   * @param w             int representing the width
   * @param h             int representing the height
   */
  void drawShape(Graphics2D g2d, boolean isHighlighted, int r, int g, int b, int x, int y, int w,
      int h);
}
