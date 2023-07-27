package cs3500.animator.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Represents tests for {@link cs3500.animator.util.AnimationBuilder}.
 */
public class AnimatorBuilderTest {

  @Test
  public void testSettingBounds() {
    IAnimator animator = Animator.builder().setBounds(20, 10, 400, 300).build();
    assertEquals(20, animator.getTopLeftX());
    assertEquals(10, animator.getTopLeftY());
    assertEquals(400, animator.getWidth());
    assertEquals(300, animator.getHeight());
  }

  @Test
  public void testAddShape() {
    IAnimator animator = Animator.builder().declareShape("r", "rectangle").build();
    assertTrue(animator.getObjectsToAnimate().containsKey("r"));
  }

  @Test
  public void testAddMotion() {
    IAnimator animator = Animator.builder().declareShape("r", "rectangle")
        .addMotion("r", 1, 100, 200, 75,
            50, 200, 100, 50, 5, 400, 375, 75, 80, 200, 100, 50).build();
    assertTrue(animator.getObjectsToAnimate().get("r").getShapeStates().containsKey(1));
    assertTrue(animator.getObjectsToAnimate().get("r").getShapeStates().containsKey(5));
  }

  @Test
  public void testAddKeyFrame() {
    IAnimator animator = Animator.builder().declareShape("r", "rectangle")
        .addKeyframe("r", 1, 50, 60, 40, 50, 25, 50, 75).build();
    assertTrue(animator.getObjectsToAnimate().get("r").getShapeStates().containsKey(1));
  }
}
