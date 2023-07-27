package cs3500.animator.view.nongui.textshapes;

import cs3500.animator.model.state.IState;
import java.util.Map;

/**
 * Represents a function object for producing a properly formatted String describing an ellipse and
 * its states.
 */
public class TextEllipse extends AbstractTextShape {

  /**
   * Constructs an instance of {@link TextEllipse} with the specified name and map of states.
   *
   * @param name   String represents the name of the shape
   * @param states {@code Map<Integer, IState>} representing the states of the shape mapped to the
   *               time they occur
   */
  public TextEllipse(String name, Map<Integer, IState> states) {
    super(name, states);
  }

  @Override
  protected String getShapeType() {
    return "ellipse";
  }
}
