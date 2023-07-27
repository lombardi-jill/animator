package cs3500.animator.view.nongui.textshapes;

/**
 * Represents an interface for creating function objects that can be called to get a textual
 * description of a shape and its states throughout time.
 */
public interface ITextShapeDescription {

  /**
   * Returns a string representing a shape's states throughout time. The first line contains the
   * shape's name and the type of shape. Each line afterwards contains the shape name, a starting
   * state and an ending state. The states are printed out in the following order: tick, x, y,
   * width, height, red, green, blue. The start time of each line (besides the first line) matches
   * the end time of the previous line.
   *
   * @return a formatted string representing the shape's states throughout time.
   */
  String getShapeDescription();
}
