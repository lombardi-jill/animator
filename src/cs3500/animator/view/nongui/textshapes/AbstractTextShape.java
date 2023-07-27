package cs3500.animator.view.nongui.textshapes;

import cs3500.animator.model.position.IPosition;
import cs3500.animator.model.state.IState;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Represents a class for abstracting common functionality from different implementations of {@link
 * ITextShapeDescription}.
 */
public abstract class AbstractTextShape implements ITextShapeDescription {

  private final String name;
  private final Map<Integer, IState> states;

  /**
   * Constructs an instance of {@link AbstractTextShape} with the specified name and map of states.
   *
   * @param name   String represents the name of the shape
   * @param states {@code Map<Integer, IState>} representing the states of the shape mapped to the
   *               time they occur
   */
  protected AbstractTextShape(String name, Map<Integer, IState> states) {
    if (name == null || states == null) {
      throw new IllegalArgumentException("Name and states may not be null.");
    }
    this.name = name;
    this.states = states;
  }

  @Override
  public String getShapeDescription() {
    List<Integer> keys = new ArrayList<>(states.keySet());
    Collections.sort(keys);
    StringJoiner shapeDescription = new StringJoiner("\n");
    String header = "Shape " + name + " " + this.getShapeType();
    shapeDescription.add(header);
    if (keys.size() == 1) {
      return header + "\nmotion " + name + "  " + keys.get(0) + " "
          + getStateDescription(states.get(keys.get(0)));
    }
    for (int i = 0; i < keys.size() - 1; i++) {
      StringBuilder row = new StringBuilder();
      row.append("motion ").append(name).append("  ");
      row.append(keys.get(i)).append(" ");
      row.append(getStateDescription(states.get(keys.get(i))));
      row.append("    ");
      row.append(keys.get(i + 1)).append(" ");
      row.append(getStateDescription(states.get(keys.get(i + 1))));
      shapeDescription.add(row);
    }
    return shapeDescription.toString();
  }

  /**
   * Return a string with all of the values of the given {@link IState} object on one line with the
   * x position, y position, width, height, red value, green value and blue value each separated by
   * a space.
   *
   * @param state represents the {@link IState} to describe
   * @return a string describing the {@link IState} object.
   */
  private String getStateDescription(IState state) {
    StringBuilder str = new StringBuilder();
    int[] rgb = state.getRGB();
    int[] wh = state.getWidthAndHeight();
    IPosition pos = state.getPosition();
    str.append(pos.getX()).append(" ").append(pos.getY()).append(" ").append(wh[0]).append(" ")
        .append(wh[1]).append(" ").append(rgb[0]).append(" ").append(rgb[1]).append(" ")
        .append(rgb[2]);
    return str.toString();
  }

  /**
   * Delegates to concrete implementations to return their shape type as a String.
   *
   * @return String representing the type of shape
   */
  protected abstract String getShapeType();
}
