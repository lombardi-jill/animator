package cs3500.animator.model.state;

import cs3500.animator.model.position.IPosition;
import cs3500.animator.model.position.Position2D;
import java.util.Objects;

/**
 * Represents an implementation of {@link IState} object.
 */
public class State implements IState {

  //INVARIANT: red value must be between 0 and 255 inclusive.
  private final int red;
  //INVARIANT: green value must be between 0 and 255 inclusive.
  private final int green;
  //INVARIANT: blue value must be between 0 and 255 inclusive.
  private final int blue;
  private final IPosition pos;
  //INVARIANT: width value must be greater than 0.
  private final int width;
  //INVARIANT: height value must be greater than 0.
  private final int height;

  /**
   * Constructs an instance of {@link State} with red, green, blue, x pos, y pos, width and height.
   *
   * @param red    int representing the red value of the color
   * @param green  int representing the green value of the color
   * @param blue   int representing the blue value of the color
   * @param x      int representing the x position of the {@link IPosition} object
   * @param y      int representing the y position of the {@link IPosition} object
   * @param width  int representing the width
   * @param height int representing the height
   * @throws IllegalArgumentException if red, green or blue are less than 0 or greater than 255 or
   *                                  if the width or height are less than or equal to 0.
   */
  public State(int red, int green, int blue, int x, int y, int width, int height) {
    this.checkColors(red, green, blue);
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.pos = new Position2D(x, y);
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Cannot have negative dimensions.");
    }
    this.width = width;
    this.height = height;
  }

  /**
   * Ensures that the color values are between 0 and 255 inclusive.
   *
   * @param color int representing a color value
   * @throws IllegalArgumentException if the color is less than 0 or greater than 255
   */
  private void checkColorRange(int color) throws IllegalArgumentException {
    if (color < 0 || color > 255) {
      throw new IllegalArgumentException("Range for color must be between 0 and 255 inclusive.");
    }
  }

  /**
   * Checks that each color is between 0 and 255 inclusive.
   *
   * @param r int representing red color value
   * @param g int representing green color value
   * @param b int representing blue color value
   * @throws IllegalArgumentException if the color is less than 0 or greater than 255
   */
  private void checkColors(int r, int g, int b) throws IllegalArgumentException {
    checkColorRange(r);
    checkColorRange(g);
    checkColorRange(b);
  }

  @Override
  public int[] getRGB() {
    int[] rGB = new int[3];
    rGB[0] = this.red;
    rGB[1] = this.green;
    rGB[2] = this.blue;
    return rGB;
  }

  @Override
  public IPosition getPosition() {
    return new Position2D(this.pos);
  }

  @Override
  public int[] getWidthAndHeight() {
    int[] wH = new int[2];
    wH[0] = this.width;
    wH[1] = this.height;
    return wH;
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (!(that instanceof State)) {
      return false;
    }
    State other = (State) that;
    return this.pos.equals(other.pos)
        && this.red == other.red
        && this.green == other.green
        && this.blue == other.blue
        && this.width == other.width
        && this.height == other.height;
  }

  @Override
  public int hashCode() {
    return Objects.hash(pos, red, green, blue, width, height);
  }
}
