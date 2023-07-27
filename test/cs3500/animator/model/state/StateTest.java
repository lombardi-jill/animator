package cs3500.animator.model.state;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import cs3500.animator.model.position.IPosition;
import cs3500.animator.model.position.Position2D;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class checking that {@link IState} methods work as intended.
 */
public class StateTest {

  IState ellipseState1;
  IState ellipseState2;
  IState ellipseState3;
  IState rectState1;
  IState rectState2;
  IState rectState3;

  @Before
  public void initializeStates() {
    this.ellipseState1 = new State(0, 255, 0, 0, 0, 5, 7);
    this.ellipseState2 = new State(0, 255, 0, 25, 45, 5, 7);
    this.ellipseState3 = new State(0, 134, 32, 25, 45, 4, 5);
    this.rectState1 = new State(0, 0, 255, 0, 0, 10, 20);
    this.rectState2 = new State(0, 0, 255, 30, 15, 10, 20);
    this.rectState3 = new State(0, 134, 32, -23, -45, 10, 20);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeRedValue() {
    new State(-10, 255, 0, 0, 0, 5, 7);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRedValueGreaterThan255() {
    new State(300, 255, 0, 0, 0, 5, 7);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeGreenValue() {
    new State(25, -1, 0, 0, 0, 5, 7);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGreenValueGreaterThan255() {
    new State(50, 256, 0, 0, 0, 5, 7);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeBlueValue() {
    new State(40, 255, -5, 0, 0, 5, 7);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBlueValueGreaterThan255() {
    new State(40, 255, 275, 0, 0, 5, 7);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeWidth() {
    new State(40, 255, 0, 0, 0, -1, 7);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeHeight() {
    new State(40, 255, 0, 0, 0, 5, -5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testZeroWidth() {
    new State(40, 255, 0, 0, 0, 0, 7);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testZeroHeight() {
    new State(40, 255, 0, 0, 0, 5, 0);
  }

  @Test
  public void testGetRGBValueArray() {
    int[] ellipseColorVals = new int[]{0, 255, 0};
    int[] rectangleColorVals = new int[]{0, 0, 255};
    assertArrayEquals(ellipseColorVals, ellipseState1.getRGB());
    assertArrayEquals(rectangleColorVals, rectState1.getRGB());
  }

  @Test
  public void testGetPositionFromState() {
    IPosition ellipsePos = new Position2D(25, 45);
    IPosition rectanglePos = new Position2D(30, 15);
    assertEquals(ellipsePos, ellipseState2.getPosition());
    assertEquals(rectanglePos, rectState2.getPosition());
  }

  @Test
  public void testGetWidthAndHeightArray() {
    int[] ellipseDims = new int[]{4, 5};
    int[] rectangleDims = new int[]{10, 20};
    assertArrayEquals(ellipseDims, ellipseState3.getWidthAndHeight());
    assertArrayEquals(rectangleDims, rectState3.getWidthAndHeight());
  }

  @Test
  public void testEqualsAndHashCode() {
    IState state1 = new State(20, 35, 0, 10, 10, 57, 32);
    IState state2 = new State(20, 35, 0, 10, 10, 57, 32);
    assertTrue(state1.equals(state2));
    assertEquals(state1.hashCode(), state2.hashCode());
  }

}
