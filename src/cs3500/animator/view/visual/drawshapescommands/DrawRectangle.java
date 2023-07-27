package cs3500.animator.view.visual.drawshapescommands;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Class that represents drawing a rectangle with given attributes.
 */
public class DrawRectangle implements IDrawShape {

  @Override
  public void drawShape(Graphics2D g2d, boolean isHighlighted, int r, int g, int b, int x, int y,
      int w, int h) {
    Color color = new Color(r, g, b);
    g2d.setColor(color);
    g2d.fillRect(x, y, w, h);
    if (isHighlighted) {
      g2d.setColor(Color.RED);
      g2d.setStroke(new BasicStroke(5));
      g2d.drawRect(x, y, w, h);
    }
  }
}
