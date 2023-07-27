package cs3500.animator.model.shape;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import cs3500.animator.model.state.IState;
import cs3500.animator.model.state.State;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class checking that {@link IShape} methods work as intended.
 */
public class IShapeTest {

  IState ellipseState1;
  IState ellipseState2;
  IState ellipseState3;
  IShape ellipse1;
  IShape ellipse2;
  Map<Integer, IState> ellipseStates;
  IState rectState1;
  IState rectState2;
  IState rectState3;
  IShape rectangle1;
  IShape rectangle2;
  Map<Integer, IState> rectangleStates;

  @Before
  public void initializeShapes() {
    this.ellipseState1 = new State(0, 255, 0, 0, 0, 5, 7);
    this.ellipseState2 = new State(0, 255, 0, 25, 45, 5, 7);
    this.ellipseState3 = new State(0, 134, 32, 25, 45, 4, 5);
    this.ellipse1 = new EllipseShape();
    this.ellipseStates = new HashMap<>();
    this.ellipseStates.put(1, this.ellipseState1);
    this.ellipseStates.put(14, this.ellipseState2);
    this.ellipseStates.put(37, this.ellipseState3);
    this.ellipse2 = new EllipseShape(this.ellipseStates);
    this.rectState1 = new State(0, 0, 255, 0, 0, 10, 20);
    this.rectState2 = new State(0, 0, 255, 25, 45, 10, 20);
    this.rectState3 = new State(0, 134, 32, 25, 45, 10, 20);
    this.rectangle1 = new RectangleShape();
    this.rectangleStates = new HashMap<>();
    this.rectangleStates.put(6, this.rectState1);
    this.rectangleStates.put(23, this.rectState2);
    this.rectangleStates.put(67, this.rectState3);
    this.rectangle2 = new RectangleShape(this.rectangleStates);
  }

  @Test
  public void checkEmptyConstructor() {
    assertEquals(0, ellipse1.getShapeStates().size());
    assertEquals(0, rectangle1.getShapeStates().size());
  }

  @Test
  public void checkConstructorWithShapeStates() {
    assertEquals(ellipseStates, ellipse2.getShapeStates());
    assertEquals(rectangleStates, rectangle2.getShapeStates());
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkNoNegativeNumbersInRectangleMap() {
    this.rectangleStates.put(-4, rectState1);
    new RectangleShape(rectangleStates);
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkNoNegativeNumbersInEllipseMap() {
    this.ellipseStates.put(-9, ellipseState1);
    new EllipseShape(ellipseStates);
  }

  @Test(expected = IllegalArgumentException.class)
  public void tryingToAddExistingTimeToEllipse() {
    this.ellipse2.addState(14, ellipseState1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void tryingToAddExistingTimeToRectangle() {
    this.rectangle2.addState(67, rectState1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void tryingToAddNegativeTimeToEllipse() {
    this.ellipse1.addState(-2, ellipseState3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void tryingToAddNegativeTimeToRectangle() {
    this.rectangle1.addState(-4, rectState3);
  }

  @Test
  public void testAddingStateToShape() {
    rectangle1.addState(6, rectState1);
    rectangle1.addState(23, rectState2);
    rectangle1.addState(67, rectState3);
    assertEquals(rectangleStates, rectangle1.getShapeStates());
    ellipse1.addState(1, ellipseState1);
    ellipse1.addState(14, ellipseState2);
    ellipse1.addState(37, ellipseState3);
    assertEquals(ellipseStates, ellipse1.getShapeStates());
  }

  @Test
  public void testGetShapeStates() {
    assertEquals(3, rectangle2.getShapeStates().size());
    assertEquals(rectangleStates, rectangle2.getShapeStates());
    assertEquals(rectState1, rectangle2.getShapeStates().get(6));
    assertEquals(rectState2, rectangle2.getShapeStates().get(23));
    assertEquals(rectState3, rectangle2.getShapeStates().get(67));
    assertEquals(3, ellipse2.getShapeStates().size());
    assertEquals(ellipseStates, ellipse2.getShapeStates());
    assertEquals(ellipseState1, ellipse2.getShapeStates().get(1));
    assertEquals(ellipseState2, ellipse2.getShapeStates().get(14));
    assertEquals(ellipseState3, ellipse2.getShapeStates().get(37));
  }

  @Test
  public void testMakingShapeCopy() {
    IShape copyEllipse = ellipse2.getShape();
    assertEquals(ellipse2.getShapeStates(), copyEllipse.getShapeStates());
    IShape copyRectangle = rectangle2.getShape();
    assertEquals(rectangle2.getShapeStates(), copyRectangle.getShapeStates());
  }

  @Test
  public void testEqualsAndHashCode() {
    ellipse1.addState(37, ellipseState3);
    ellipse1.addState(1, ellipseState1);
    ellipse1.addState(14, ellipseState2);
    assertTrue(ellipse1.equals(ellipse2));
    assertEquals(ellipse1.hashCode(), ellipse2.hashCode());
    rectangle1.addState(1, ellipseState1);
    rectangle1.addState(14, ellipseState2);
    rectangle1.addState(37, ellipseState3);
    assertFalse(ellipse1.equals(rectangle1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddingMotionForSameTickWithClashingStartingState() {
    ellipse2.addMotion(ellipseState3, 1, ellipseState2, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddingMotionForSameTickWithClashingEndingState() {
    ellipse2.addMotion(ellipseState3, 2, ellipseState1, 14);
  }

  @Test
  public void testAddingMotionRemovesInterruptingStates() {
    rectangle2.addMotion(rectState3, 9, rectState1, 40);
    assertFalse(rectangle2.getShapeStates().containsKey(23));
    assertTrue(rectangle2.getShapeStates().containsKey(9));
    assertTrue(rectangle2.getShapeStates().containsKey(40));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddingMotionWithStartTimeBiggerThanEndTime() {
    ellipse1.addMotion(ellipseState1, 21, ellipseState3, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemovingNonexistentState() {
    ellipse2.removeState(13);
  }

  @Test
  public void testRemovingExistingState() {
    rectangle2.removeState(23);
    assertFalse(rectangle2.getShapeStates().containsKey(23));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemovingMotionNonExistentStartState() {
    rectangle2.removeMotion(7, 23);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemovingMotionNonExistentEndState() {
    rectangle2.removeMotion(6, 24);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemovingMotionInBetweenState() {
    rectangle2.removeMotion(6, 67);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemovingMotionCrisCrossedTimes() {
    rectangle2.removeMotion(23, 6);
  }

  @Test
  public void testRemoveMotion() {
    assertTrue(ellipse2.getShapeStates().containsKey(1));
    assertTrue(ellipse2.getShapeStates().containsKey(14));
    ellipse2.removeMotion(1, 14);
    assertFalse(ellipse2.getShapeStates().containsKey(1));
    assertFalse(ellipse2.getShapeStates().containsKey(14));
  }

  @Test
  public void testEditState() {
    IState state = new State(255, 255, 255, 0, 0, 10, 10);
    assertNotEquals(state, rectangle2.getShapeStates().get(23));
    rectangle2.editState(23, state);
    assertEquals(state, rectangle2.getShapeStates().get(23));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNoStateAtTime() {
    IState state = new State(255, 255, 255, 0, 0, 10, 10);
    rectangle2.editState(22, state);
  }
}
