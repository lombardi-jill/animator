package cs3500.animator.view.visual;

import cs3500.animator.model.position.IPosition;
import cs3500.animator.viewmodel.IReadOnlyAnimator;
import cs3500.animator.model.shape.IShape;
import cs3500.animator.model.state.IState;
import cs3500.animator.view.visual.drawshapescommands.DrawEllipse;
import cs3500.animator.view.visual.drawshapescommands.DrawRectangle;
import cs3500.animator.view.visual.drawshapescommands.IDrawShape;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;

/**
 * Class representing the {@link JPanel} that the animation is displayed on.
 */
public class AnimationPanelImpl extends JPanel implements ActionListener, IAnimationPanel {

  private final IReadOnlyAnimator animator;
  private static int currTick = 0;
  private final Map<String, IDrawShape> drawShapes;
  private String highlightedShape;

  /**
   * Creates an instance of {@link AnimationPanelImpl} that the animation from the given model will
   * be drawn and displayed on.
   *
   * @param animator an {@link IReadOnlyAnimator} that represents a read only version of the model
   *                 to animate to be used by the view
   */
  public AnimationPanelImpl(IReadOnlyAnimator animator) {
    super();
    this.animator = animator;
    this.setBackground(Color.white);
    this.drawShapes = this.setAvailableShapes();
  }

  /**
   * Creates a map that contains the known shapes mapped to function objects that draw the each
   * shape.
   *
   * @return a {@code Map<String, IDrawShape>} that holds the known shapes and the function objects
   *         to draw each shape.
   */
  private Map<String, IDrawShape> setAvailableShapes() {
    Map<String, IDrawShape> shapeMap = new HashMap<>();
    shapeMap.put("rectangle", new DrawRectangle());
    shapeMap.put("ellipse", new DrawEllipse());
    return shapeMap;
  }

  @Override
  public void setHighlightedShape(String name) {
    this.highlightedShape = name;
  }

  @Override
  public void resetTick() {
    currTick = 0;
  }

  @Override
  public int getCurrTick() {
    return currTick;
  }

  /**
   * Draws the shape using a graphics object at each tick throughout time.
   *
   * @param g graphics object that is used for drawing the shape
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.translate(animator.getTopLeftX() * -1, animator.getTopLeftY() * -1);

    Map<String, IShape> objectsToAnimate = animator.getObjectsToAnimate();
    for (String name : objectsToAnimate.keySet()) {
      IShape shape = objectsToAnimate.get(name);
      Map<Integer, IState> shapeStates = shape.getShapeStates();
      IState currState = shapeStates.getOrDefault(currTick, null);
      IDrawShape toDraw = drawShapes.get(shape.getShapeType());
      boolean isHighlighted = false;
      if (name.equals(this.highlightedShape)) {
        isHighlighted = true;
      }

      //If the tick at this point is not specified in the shape's map of states
      if (currState == null) {
        List<Integer> ticks = new ArrayList<>(shape.getShapeStates().keySet());
        Collections.sort(ticks);
        //Checks if the shape has appeared yet by comparing the current tick to the
        // shape's first tick
        if (ticks.size() > 0 && currTick >= ticks.get(0)) {
          int[] ticksToTween = getBeforeAndAfterTick(ticks);
          //Checks if the tick is in between two defined ticks for the shape
          if (!(ticksToTween[0] == -1 || ticksToTween[1] == -1)) {
            int start = ticksToTween[0];
            int end = ticksToTween[1];
            toDraw.drawShape(
                g2d,
                isHighlighted,
                tween(start, end, shapeStates.get(start).getRGB()[0],
                    shapeStates.get(end).getRGB()[0]),
                tween(start, end, shapeStates.get(start).getRGB()[1],
                    shapeStates.get(end).getRGB()[1]),
                tween(start, end, shapeStates.get(start).getRGB()[2],
                    shapeStates.get(end).getRGB()[2]),
                tween(start, end, shapeStates.get(start).getPosition().getX(),
                    shapeStates.get(end).getPosition().getX()),
                tween(start, end, shapeStates.get(start).getPosition().getY(),
                    shapeStates.get(end).getPosition().getY()),
                tween(start, end, shapeStates.get(start).getWidthAndHeight()[0],
                    shapeStates.get(end).getWidthAndHeight()[0]),
                tween(start, end, shapeStates.get(start).getWidthAndHeight()[1],
                    shapeStates.get(end).getWidthAndHeight()[1]));
          }
        }
      }
      //if the state exists in the shape's map of states and can be drawn without tweening
      else {
        int[] rgb = currState.getRGB();
        IPosition pos = currState.getPosition();
        int[] wh = currState.getWidthAndHeight();
        toDraw.drawShape(g2d, isHighlighted, rgb[0], rgb[1], rgb[2], pos.getX(), pos.getY(), wh[0],
            wh[1]);
      }
    }
  }


  /**
   * Finds the two ticks that the current tick is in between in the shape's list of ticks for which
   * motions have been explicitly defined.
   *
   * @param ticks list of ticks for which motions have been explicitly defined
   * @return integer array with the two ticks the current tick is in between or returns [-1, -1] if
   *         the tick is not between two existing ticks
   */
  private int[] getBeforeAndAfterTick(List<Integer> ticks) {
    int[] toReturn = new int[2];

    for (int i = 0; i < ticks.size() - 1; i++) {
      if (ticks.get(i) < currTick && ticks.get(i + 1) > currTick) {
        toReturn[0] = ticks.get(i);
        toReturn[1] = ticks.get(i + 1);
        return toReturn;
      }
    }
    toReturn[0] = -1;
    toReturn[1] = -1;
    return toReturn;
  }

  /**
   * Finds the in between value for the two given values depending on what the current tick of the
   * animation is. Uses a tweening function given to us in the assignment.
   *
   * @param startTick  integer representing the tick at which the starting value occurs
   * @param endTick    integer representing the tick at which the ending value occurs
   * @param startValue integer representing the value of the attribute in the starting state
   * @param endValue   integer representing the value of the attribute in the ending state
   * @return an int representing the in between value for the two given values depending on what the
   *         current tick is
   */
  private int tween(int startTick, int endTick, int startValue, int endValue) {
    if (startValue == endValue) {
      return startValue;
    }
    double value = (startValue * (double) (endTick - currTick) / (double) (endTick - startTick))
        + (endValue * (double) (currTick - startTick) / (double) (endTick - startTick));
    return (int) value;
  }

  /**
   * Represents the action to be performed each tick of the animation.
   *
   * @param e ActionEvent that represents the action that occurred
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    this.paintComponent(getGraphics());
    currTick += 1;
  }

}
