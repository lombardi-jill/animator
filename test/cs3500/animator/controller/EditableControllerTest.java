package cs3500.animator.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import cs3500.animator.controller.commands.AddShape;
import cs3500.animator.controller.commands.AddState;
import cs3500.animator.controller.commands.EditState;
import cs3500.animator.controller.commands.LoopAnimation;
import cs3500.animator.controller.commands.PauseAnimation;
import cs3500.animator.controller.commands.RemoveShape;
import cs3500.animator.controller.commands.RemoveState;
import cs3500.animator.controller.commands.RestartAnimation;
import cs3500.animator.controller.commands.ResumeAnimation;
import cs3500.animator.controller.commands.StartAnimation;
import cs3500.animator.controller.commands.UnLoopAnimation;
import cs3500.animator.model.Animator;
import cs3500.animator.model.IAnimator;
import cs3500.animator.model.state.IState;
import cs3500.animator.model.state.State;
import cs3500.animator.view.MockView;
import cs3500.animator.view.visual.IEditableView;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents tests for the editable controller. Ensures updates are made to the model and correct
 * functions in view are called. Uses a mock view.
 */
public class EditableControllerTest {

  IAnimator animator;
  IEditableView view;

  @Before
  public void initializeAnimator() {
    this.animator = new Animator();
    this.view = new MockView(this.animator, 1);
  }

  @Test
  public void testAddShapeHandler() {
    Runnable addShape = new AddShape(animator, view);
    addShape.run();
    assertTrue(animator.getObjectsToAnimate().containsKey("r"));
    assertEquals("added rectangle named r; shape list refreshed; repainted; ", view.toString());
  }

  @Test
  public void testAddStateHandler() {
    Runnable addShape = new AddShape(animator, view);
    addShape.run();

    Runnable addState = new AddState(animator, view);
    addState.run();

    assertTrue(animator.getObjectsToAnimate().get("r").getShapeStates().containsKey(1));
    assertEquals("added rectangle named r; shape list refreshed; repainted; r selected; "
        + "added key frame: 1, 255, 0, 0, 10, 30, 30, 30; repainted; key frame list refreshed; ",
        view.toString());
  }

  @Test
  public void testEditStateHandler() {
    Runnable addShape = new AddShape(animator, view);
    addShape.run();

    Runnable addState = new AddState(animator, view);
    addState.run();

    Runnable editState = new EditState(animator, view);
    editState.run();

    IState state = new State(0, 0, 0, 0, 300, 10, 10);

    assertEquals(this.animator.getObjectsToAnimate().get("r").getShapeStates().get(1), state);
    assertEquals("added rectangle named r; shape list refreshed; repainted; r selected; "
            + "added key frame: 1, 255, 0, 0, 10, 30, 30, 30; repainted; "
            + "key frame list refreshed; r selected; edit key frame to: "
            + "1, 0, 0, 0, 0, 300, 10, 10; repainted; key frame list refreshed; ",
        view.toString());
  }

  @Test
  public void testLoopAnimationHandler() {
    Runnable loopAnimation = new LoopAnimation(view);
    loopAnimation.run();
    assertEquals("animation looped; ",
        view.toString());
  }

  @Test
  public void testPauseAnimationHandler() {
    Runnable pauseAnimation = new PauseAnimation(view);
    pauseAnimation.run();
    assertEquals("animation paused; ", view.toString());
  }

  @Test
  public void testRemoveShapeHandler() {
    Runnable addShape = new AddShape(animator, view);
    addShape.run();
    assertTrue(animator.getObjectsToAnimate().containsKey("r"));

    Runnable removeShape = new RemoveShape(animator, view);
    removeShape.run();
    assertFalse(animator.getObjectsToAnimate().containsKey("r"));

    assertEquals("added rectangle named r; shape list refreshed; repainted; "
        + "r selected; shape list refreshed; repainted; ", view.toString());
  }

  @Test
  public void testRemoveStateHandler() {
    Runnable addShape = new AddShape(animator, view);
    addShape.run();

    Runnable addState = new AddState(animator, view);
    addState.run();
    assertTrue(animator.getObjectsToAnimate().get("r").getShapeStates().containsKey(1));

    Runnable removeState = new RemoveState(animator, view);
    removeState.run();
    assertFalse(animator.getObjectsToAnimate().get("r").getShapeStates().containsKey(1));

    assertEquals("added rectangle named r; shape list refreshed; repainted; r selected; "
        + "added key frame: 1, 255, 0, 0, 10, 30, 30, 30; repainted; "
        + "key frame list refreshed; state selected is 1; r selected; key frame list refreshed; "
        + "repainted; ", view.toString());
  }

  @Test
  public void testRestartAnimation() {
    Runnable restartAnimation = new RestartAnimation(view);
    restartAnimation.run();
    assertEquals("animation restarted; ", view.toString());
  }

  @Test
  public void testResumeAnimation() {
    Runnable resumeAnimation = new ResumeAnimation(view);
    resumeAnimation.run();
    assertEquals("animation resumed; ", view.toString());
  }

  @Test
  public void testStartAnimation() {
    Runnable startAnimation = new StartAnimation(view);
    startAnimation.run();
    assertEquals("animation started; ", view.toString());
  }

  @Test
  public void testUnLoopAnimation() {
    Runnable unLoopAnimation = new UnLoopAnimation(view);
    unLoopAnimation.run();
    assertEquals("animation unlooped; ", view.toString());
  }

  @Test
  public void testErrorMessage() {
    view.displayErrorMessage("Jill smells");

    assertEquals("error message displayed with message: Jill smells; ", view.toString());
  }

  @Test
  public void testControllerStarted() {
    new EditableControllerImpl(animator, view).startAnimation();

    assertEquals("added action listener; added change listener; "
            + "added list selection listener; view started; ",
        view.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModel() {
    new EditableControllerImpl(null, view);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullView() {
    new EditableControllerImpl(animator, null);
  }
}
