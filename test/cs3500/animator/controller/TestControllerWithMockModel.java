package cs3500.animator.controller;

import static org.junit.Assert.assertEquals;

import cs3500.animator.model.IAnimator;
import cs3500.animator.model.MockIAnimator;
import cs3500.animator.view.MockView;
import cs3500.animator.view.visual.IEditableView;
import java.awt.event.ActionEvent;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests that controller correctly sends view inputs to model.
 */
public class TestControllerWithMockModel {

  IAnimator animator;
  IEditableView view;
  EditableControllerImpl controller;

  @Before
  public void initializeAnimator() {
    this.animator = new MockIAnimator();
    this.view = new MockView(this.animator, 1);
    this.controller = new EditableControllerImpl(animator, view);
  }

  @Test
  public void testMockAddShape() {
    ActionEvent event = new ActionEvent(view, 1, "add shape");

    this.controller.actionPerformed(event);

    assertEquals("ADDING SHAPE:\n"
        + "r\n\n", animator.toString());
  }

  @Test
  public void testMockAddState() {
    ActionEvent event = new ActionEvent(view, 2, "add keyframe");

    this.controller.actionPerformed(event);

    assertEquals("ADDING STATE TO: r\n"
        + "at1\n"
        + "\n", animator.toString());
  }

  @Test
  public void testMockEditState() {
    ActionEvent event = new ActionEvent(view, 2, "edit keyframe");

    this.controller.actionPerformed(event);

    assertEquals("EDITING STATE FOR SHAPE: r\n"
        + "AT TIME: 1\n"
        + "\n", animator.toString());
  }

  @Test
  public void testMockRemoveShape() {
    ActionEvent event = new ActionEvent(view, 2, "remove shape");

    this.controller.actionPerformed(event);

    assertEquals("REMOVING SHAPE: r\n\n", animator.toString());
  }

  @Test
  public void RemoveShapeState() {
    ActionEvent event = new ActionEvent(view, 2, "remove keyframe");

    this.controller.actionPerformed(event);

    assertEquals("REMOVING STATE FROM SHAPE AT TICK: 1\n\n", animator.toString());
  }
}
