package cs3500.animator.view.nongui.svgshapes;

import cs3500.animator.model.position.IPosition;
import cs3500.animator.model.state.IState;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * An abstract class representing common functionality of producing properly formatted SVG for a
 * shape and its motions.
 */
public abstract class AbstractSVG implements ISVGShape {

  @Override
  public String makeSVGShape(String objectID, Map<Integer, IState> shapeStates, int tickRate) {
    List<Integer> keys = new ArrayList<>(shapeStates.keySet());
    Collections.sort(keys);

    // if a shape in the animation has no states, it is never visible and not of interest for SVG
    if (keys.size() == 0) {
      return "";
    }
    IState firstState = shapeStates.get(keys.get(0));
    StringJoiner joiner = new StringJoiner("\n");

    int previousTick = keys.remove(0);
    getShapeHeader(objectID, firstState, previousTick, tickRate, joiner);
    for (int key : keys) {
      IState prevState = shapeStates.get(previousTick);
      IState currState = shapeStates.get(key);
      double begin = getTimeMs(previousTick, tickRate);
      double dur = getTimeMs(key, tickRate) - begin;
      checkColor(prevState.getRGB(), currState.getRGB(), begin, dur, joiner);
      checkSize(prevState.getWidthAndHeight(), currState.getWidthAndHeight(), begin, dur, joiner);
      checkPosn(prevState.getPosition(), currState.getPosition(), begin, dur, joiner);

      previousTick = key;
    }

    joiner.add("</" + getShapeType() + ">");
    return joiner.toString();
  }

  /**
   * Produces a string declaring a shape and its initial state and time to appear in the animation.
   *
   * @param objectID   String representing the name of the shape
   * @param firstState {@link IState} with the shape's initial attributes
   * @param firstTick  integer representing the first tick at which this shape appears
   * @param tickRate   integer representing the number of ticks per second for the animation
   * @param joiner     {@link StringJoiner} to add the header to
   */
  private void getShapeHeader(String objectID, IState firstState, int firstTick, int tickRate,
      StringJoiner joiner) {
    String shapeType = getShapeType();
    int[] rgb = firstState.getRGB();
    int[] wh = firstState.getWidthAndHeight();
    IPosition pos = firstState.getPosition();
    String header =
        "<" + shapeType + " id=\"" + objectID + "\" " + getXName() + "=\"" + pos.getX() + "\" "
            + getYName() + "=\"" + pos.getY()
            + "\" " + getWidthName() + "=\""
            + getShapeSize(wh[0]) + "\" " + getHeightName() + "=\"" + getShapeSize(wh[1])
            + "\" fill=\"rgb(" + rgb[0] + "," + rgb[1] + ","
            + rgb[2] + ")\" visibility=\"hidden\" >";
    joiner.add(header);
    DecimalFormat numberFormat = new DecimalFormat("#.0");
    joiner.add(
        "\t\t<set attributeName=\"visibility\" to=\"visible\" begin=\""
            + numberFormat.format(getTimeMs(firstTick, tickRate)) + "ms\" />");
  }

  /**
   * Converts a tick to a time in milliseconds using the given tick rate.
   *
   * @param tick     integer representing a tick in the animation
   * @param tickRate integer representing the number of ticks per second
   * @return the tick converted to milliseconds based on the tick rate
   */
  private static double getTimeMs(int tick, int tickRate) {

    return (double) tick / (double) tickRate * 1000.0;
  }

  /**
   * Produces a string that specifies an attribute to be changed in the shape.
   *
   * @param begin double representing the time at which the change begins
   * @param dur   double representing the length of time the change occurs
   * @param name  String representing the name of the attribute to change
   * @param to    String representing the ending value of the attribute changed
   * @return String that contains information about a change in the shape
   */
  private String attribute(double begin, double dur, String name, String to) {
    DecimalFormat numberFormat = new DecimalFormat("#.0");
    return "\t\t<animate attributeType=\"xml\" begin=\"" + numberFormat.format(begin)
        + "ms\" dur=\""
        + numberFormat.format(dur) + "ms\" attributeName=\"" + name + "\" to=\"" + to
        + "\" fill =\"freeze\" />";
  }

  /**
   * Checks if there is a change in color between states of a shape and if there is, adds a string
   * representing this change to the {@link StringJoiner}.
   *
   * @param prevColors integer array that holds the original RGB values of the shape
   * @param currColors integer array that holds the final RGB values of the shape
   * @param begin      double representing the time at which the change begins
   * @param dur        double representing the length of time the change occurs
   * @param sj         {@link StringJoiner} to add the attribute change (if any) to
   */
  private void checkColor(int[] prevColors, int[] currColors, double begin, double dur,
      StringJoiner sj) {
    if ((prevColors[0] != currColors[0] || prevColors[1] != currColors[1]
        || prevColors[2] != currColors[2])) {
      String newColor = "rgb(" + currColors[0] + "," + currColors[1] + "," + currColors[2] + ")";
      sj.add(attribute(begin, dur, "fill", newColor));
    }
  }

  /**
   * Checks if there is a change in size between states of a shape and if there is, adds a string
   * representing this change to the {@link StringJoiner}.
   *
   * @param prevSize integer array that holds the original width and height values of the shape
   * @param currSize integer array that holds the final width and height values of the shape
   * @param begin    double representing the time at which the change begins
   * @param dur      double representing the length of time the change occurs
   * @param sj       {@link StringJoiner} to add the attribute change (if any) to
   */
  private void checkSize(int[] prevSize, int[] currSize, double begin, double dur,
      StringJoiner sj) {
    if (prevSize[0] != currSize[0]) {
      sj.add(
          attribute(begin, dur, this.getWidthName(), Integer.toString(getShapeSize(currSize[0]))));
    }
    if (prevSize[1] != currSize[1]) {
      sj.add(
          attribute(begin, dur, this.getHeightName(), Integer.toString(getShapeSize(currSize[1]))));
    }
  }

  /**
   * Checks if there is a change in position between states of a shape and if there is, adds a
   * string representing this change to the {@link StringJoiner}.
   *
   * @param prevPosn {@link IPosition} object that holds the original x and y coordinates of the
   *                 shape
   * @param currPosn {@link IPosition} object that holds the final x and y coordinates of the shape
   * @param begin    double representing the time at which the change begins
   * @param dur      double representing the length of time the change occurs
   * @param sj       {@link StringJoiner} to add the attribute change (if any) to
   */
  private void checkPosn(IPosition prevPosn, IPosition currPosn, double begin, double dur,
      StringJoiner sj) {
    if (prevPosn.getX() != currPosn.getX()) {
      sj.add(attribute(begin, dur, this.getXName(), Integer.toString(currPosn.getX())));
    }
    if (prevPosn.getY() != currPosn.getY()) {
      sj.add(attribute(begin, dur, this.getYName(), Integer.toString(currPosn.getY())));
    }
  }

  /**
   * Gets the String that represents the shape type of the current shape.
   *
   * @return String representing the type of shape
   */
  protected abstract String getShapeType();

  /**
   * Gets the String that represents the name of the shape's width attribute in SVG formatting.
   *
   * @return String representing the name of the shape's width attribute
   */
  protected abstract String getWidthName();

  /**
   * Gets the String that represents the name of the shape's height attribute in SVG formatting.
   *
   * @return String representing the name of the shape's height attribute
   */
  protected abstract String getHeightName();

  /**
   * Gets the String that represents the name of the shape's x-coordinate attribute in SVG
   * formatting.
   *
   * @return String representing the name of the shape's x-coordinate attribute
   */
  protected abstract String getXName();

  /**
   * Gets the String that represents the name of the shape's y-coordinate attribute in SVG
   * formatting.
   *
   * @return String representing the name of the shape's y-coordinate attribute
   */
  protected abstract String getYName();

  /**
   * Returns an int that represents the proper dimension for creating a shape in SVG.
   *
   * @param s int representing the current dimension value of the shape
   * @return an int representing the proper dimension for creating a shape in SVG
   */
  protected abstract int getShapeSize(int s);
}
