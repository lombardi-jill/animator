import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import cs3500.animator.model.Animator;
import cs3500.animator.model.IAnimator;
import cs3500.animator.model.shape.EllipseShape;
import cs3500.animator.model.shape.IShape;
import cs3500.animator.model.shape.RectangleShape;
import cs3500.animator.model.state.IState;
import cs3500.animator.model.state.State;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class checking that {@link IAnimator} methods work as intended.
 */
public class AnimatorTest {

  IState rectState1;
  IState rectState2;
  IState rectState3;
  IState ellipseState1;
  IState ellipseState2;
  IState ellipseState3;
  IShape rectangle1;
  IShape ellipse1;
  IAnimator animator;

  @Before
  public void initializeAnimator() {
    this.rectState1 = new State(0, 0, 255, 0, 0, 10, 20);
    this.rectState2 = new State(0, 0, 255, 25, 45, 10, 20);
    this.rectState3 = new State(0, 134, 32, 25, 45, 10, 20);
    this.ellipseState1 = new State(0, 255, 0, 0, 0, 5, 7);
    this.ellipseState2 = new State(0, 255, 0, 25, 45, 5, 7);
    this.ellipseState3 = new State(0, 134, 32, 25, 45, 4, 5);
    this.rectangle1 = new RectangleShape();
    this.ellipse1 = new EllipseShape();
    this.rectangle1.addState(1, this.rectState1);
    this.rectangle1.addState(5, this.rectState2);
    this.rectangle1.addState(14, this.rectState3);
    this.ellipse1.addState(4, this.ellipseState1);
    this.ellipse1.addState(7, this.ellipseState2);
    this.ellipse1.addState(57, this.ellipseState3);
    this.animator = new Animator();
  }

  @Test
  public void addingShapesToAnimator() {
    animator.addShape(rectangle1, "r");
    animator.addShape(ellipse1, "e");
    assertTrue(animator.getObjectsToAnimate().containsKey("r"));
    assertTrue(animator.getObjectsToAnimate().containsKey("e"));
    assertTrue(animator.getObjectsToAnimate().containsValue(rectangle1));
    assertTrue(animator.getObjectsToAnimate().containsValue(ellipse1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void addingShapeWithNameAlreadyInMap() {
    animator.addShape(rectangle1, "r");
    animator.addShape(ellipse1, "r");
  }


  @Test(expected = IllegalArgumentException.class)
  public void testAddingStateForShapeThatDoesntExist() {
    animator.addShape(rectangle1, "r");
    animator.addShapeState("e", ellipseState1, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddingStateAtAlreadyExistingTime() {
    animator.addShape(ellipse1, "e");
    animator.addShapeState("e", ellipseState2, 7);
  }

  @Test
  public void testAddingStateToShape() {
    animator.addShape(rectangle1, "r");
    animator.addShape(ellipse1, "e");
    animator.addShapeState("r", rectState2, 123);
    animator.addShapeState("e", ellipseState3, 97);
    assertTrue(animator.getObjectsToAnimate().get("r").getShapeStates().containsKey(123));
    assertTrue(animator.getObjectsToAnimate().get("e").getShapeStates().containsKey(97));
  }

  @Test
  public void testAddingMotionToShapeWithNonClashingTimes() {
    animator.addShape(rectangle1, "r");
    animator.addShape(ellipse1, "e");
    animator.addShapeState("r", rectState2, 123);
    animator.addShapeState("e", ellipseState3, 97);
    animator.addShapeMotion("r", rectState3, 6, rectState2, 90);
    assertTrue(animator.getObjectsToAnimate().get("r").getShapeStates().containsKey(6));
    assertTrue(animator.getObjectsToAnimate().get("r").getShapeStates().containsKey(90));
    assertFalse(animator.getObjectsToAnimate().get("r").getShapeStates().containsKey(14));
  }

  @Test
  public void testAddingStateInBetweenMotion() {
    testAddingMotionToShapeWithNonClashingTimes();
    animator.addShapeState("r", rectState2, 45);
    assertTrue(animator.getObjectsToAnimate().get("r").getShapeStates().containsKey(45));
  }

  @Test
  public void testAddingMotionWithClashingStartTimeButEqualState() {
    testAddingStateInBetweenMotion();
    assertTrue(animator.getObjectsToAnimate().get("e").getShapeStates().containsKey(7));
    assertEquals(ellipseState2, animator.getObjectsToAnimate().get("e").getShapeStates().get(7));
    animator.addShapeMotion("e", ellipseState2, 7, ellipseState1, 35);
    assertTrue(animator.getObjectsToAnimate().get("e").getShapeStates().containsKey(7));
    assertEquals(ellipseState2, animator.getObjectsToAnimate().get("e").getShapeStates().get(7));
  }

  @Test
  public void testAddingMotionWithClashingEndTimeButEqualState() {
    testAddingMotionWithClashingStartTimeButEqualState();
    assertTrue(animator.getObjectsToAnimate().get("e").getShapeStates().containsKey(97));
    assertEquals(ellipseState3, animator.getObjectsToAnimate().get("e").getShapeStates().get(97));
    animator.addShapeMotion("e", ellipseState2, 58, ellipseState3, 97);
    assertTrue(animator.getObjectsToAnimate().get("e").getShapeStates().containsKey(97));
    assertEquals(ellipseState3, animator.getObjectsToAnimate().get("e").getShapeStates().get(97));
  }

  @Test
  public void addingMotionStartStateAndEndStateAreSame() {
    animator.addShape(ellipse1, "e");
    animator.addShapeMotion("e", ellipseState2, 1, ellipseState2, 1);
    assertTrue(animator.getObjectsToAnimate().get("e").getShapeStates().containsKey(1));
    assertEquals(ellipseState2, animator.getObjectsToAnimate().get("e").getShapeStates().get(1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddingMotionToNonexistentShape() {
    animator.addShape(rectangle1, "r");
    animator.addShapeMotion("hello", rectState3, 17, rectState1, 19);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddingMotionWithStartTimeGreaterThanEndTime() {
    animator.addShape(rectangle1, "r");
    animator.addShapeMotion("r", rectState2, 3, rectState3, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddingMotionWithClashingStartState() {
    animator.addShape(rectangle1, "r");
    animator.addShape(ellipse1, "e");
    animator.addShapeMotion("r", rectState3, 1, rectState2, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddingMotionWithClashingEndState() {
    animator.addShape(rectangle1, "r");
    animator.addShape(ellipse1, "e");
    animator.addShapeMotion("r", rectState3, 2, rectState1, 5);
  }

  @Test
  public void testRemovingShape() {
    animator.addShape(rectangle1, "r");
    animator.addShape(ellipse1, "e");
    animator.removeShape("e");
    assertFalse(animator.getObjectsToAnimate().containsKey("e"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemovingNonexistentShape() {
    animator.addShape(rectangle1, "r");
    animator.removeShape("yolo");
  }

  @Test
  public void testRemoveShapeState() {
    animator.addShape(ellipse1, "e");
    assertTrue(animator.getObjectsToAnimate().get("e").getShapeStates().containsKey(7));
    animator.removeShapeState("e", 7);
    assertFalse(animator.getObjectsToAnimate().get("e").getShapeStates().containsKey(7));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveStateFromNonexistentShape() {
    animator.addShape(rectangle1, "r");
    animator.removeShapeState("yeet", 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemovingNonexistentStateFromShape() {
    animator.addShape(ellipse1, "e");
    animator.removeShapeState("e", 1);
  }

  @Test
  public void testGettingObjectsToAnimateCopy() {
    animator.addShape(rectangle1, "r");
    animator.addShape(ellipse1, "e");
    Map<String, IShape> shapes = new HashMap<>();
    shapes.put("r", rectangle1);
    shapes.put("e", ellipse1);
    assertEquals(shapes, animator.getObjectsToAnimate());
    assertTrue(animator.getObjectsToAnimate().containsKey("r"));
    assertTrue(animator.getObjectsToAnimate().containsKey("e"));
  }

  @Test
  public void testGetters() {
    assertEquals(0, animator.getTopLeftX());
    assertEquals(0, animator.getTopLeftY());
    assertEquals(500, animator.getHeight());
    assertEquals(500, animator.getWidth());
  }

  @Test
  public void testSetters() {
    animator.setHeight(6);
    assertEquals(6, animator.getHeight());
    animator.setWidth(8);
    assertEquals(8, animator.getWidth());
    animator.setTopLeft(1, 2);
    assertEquals(1, animator.getTopLeftX());
    assertEquals(2, animator.getTopLeftY());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemovingMotionNonExistentStartState() {
    animator.addShape(rectangle1, "r");
    animator.removeShapeMotion("r", 2, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemovingMotionNonExistentEndState() {
    animator.addShape(rectangle1, "r");
    animator.removeShapeMotion("r", 5, 15);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemovingMotionInBetweenState() {
    animator.addShape(rectangle1, "r");
    animator.removeShapeMotion("r", 1, 14);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemovingCrisCrossingTimes() {
    animator.addShape(rectangle1, "r");
    animator.removeShapeMotion("r", 14, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemovingMotionFromNonExistentShape() {
    animator.addShape(rectangle1, "r");
    animator.removeShapeMotion("e", 1, 14);
  }

  @Test
  public void testRemoveMotion() {
    animator.addShape(ellipse1, "e");
    assertTrue(animator.getObjectsToAnimate().get("e").getShapeStates().containsKey(4));
    assertTrue(animator.getObjectsToAnimate().get("e").getShapeStates().containsKey(7));
    animator.removeShapeMotion("e", 4, 7);
    assertFalse(animator.getObjectsToAnimate().get("e").getShapeStates().containsKey(4));
    assertFalse(animator.getObjectsToAnimate().get("e").getShapeStates().containsKey(7));
  }

  @Test
  public void testEditShape() {
    IState state = new State(255, 255, 255, 0, 0, 10, 10);
    animator.addShape(ellipse1, "e");
    assertNotEquals(state, animator.getObjectsToAnimate().get("e").getShapeStates().get(4));
    animator.editShapeState("e", 4, 255, 255, 255, 0, 0, 10, 10);
    assertEquals(state, animator.getObjectsToAnimate().get("e").getShapeStates().get(4));
  }

  @Test(expected = IllegalArgumentException.class)
  public void editShapeNotInAnimator() {
    animator.editShapeState("r", 4, 255, 255, 255, 0, 0, 10, 10);
  }
}
