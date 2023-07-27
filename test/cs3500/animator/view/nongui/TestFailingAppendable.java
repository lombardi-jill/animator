package cs3500.animator.view.nongui;

import cs3500.animator.model.Animator;
import cs3500.animator.view.FailingAppendable;
import cs3500.animator.view.IAnimationView;
import org.junit.Test;

/**
 * Tests to make sure that failing to append throws an {@link IllegalStateException}, instead of an
 * {@link java.io.IOException}.
 */
public class TestFailingAppendable {

  @Test(expected = IllegalStateException.class)
  public void testFailingAppendableTextView() {
    IAnimationView view = new TextViewImpl(new FailingAppendable(), new Animator());
    view.startView();
  }

  @Test(expected = IllegalStateException.class)
  public void testFailingAppendableSVGView() {
    IAnimationView view = new SVGViewImpl(new FailingAppendable(), new Animator(), 1);
    view.startView();
  }
}
