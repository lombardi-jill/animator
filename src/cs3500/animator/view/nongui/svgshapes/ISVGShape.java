package cs3500.animator.view.nongui.svgshapes;

import cs3500.animator.model.state.IState;
import java.util.Map;

/**
 * Represents an interface for producing properly formatted SVG for different shapes.
 */
public interface ISVGShape {

  /**
   * Produces properly formatted SVG for a shape and its motions.
   *
   * @param objectID    String representing the name of the shape
   * @param shapeStates {@code Map<Integer, IState>} that holds this shape's motions throughout
   *                    time
   * @param tickRate    integer representing the ticks per second for the animation
   * @return a String with properly formatted SVG representing the shape and its motions throughout
   *         time
   */
  String makeSVGShape(String objectID, Map<Integer, IState> shapeStates, int tickRate);
}
