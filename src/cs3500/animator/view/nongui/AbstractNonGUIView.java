package cs3500.animator.view.nongui;

import cs3500.animator.viewmodel.IReadOnlyAnimator;
import cs3500.animator.view.IAnimationView;
import java.io.IOException;

/**
 * Represents an abstract class for functionality shared by the two non-visual views, text and SVG.
 */
public abstract class AbstractNonGUIView implements IAnimationView {

  protected final Appendable out;
  protected final IReadOnlyAnimator animator;

  /**
   * Constructs an instance off {@link AbstractNonGUIView} with the given output and model.
   *
   * @param out      an appendable object that represents where the view outputs to
   * @param animator an {@link IReadOnlyAnimator} that represents a read only version of the model
   *                 to animate to be used by the view
   * @throws IllegalArgumentException if the animator or appendable is null
   */
  protected AbstractNonGUIView(Appendable out, IReadOnlyAnimator animator) {
    if (out == null || animator == null) {
      throw new IllegalArgumentException("Appendable or Model cannot be null.");
    }
    this.out = out;
    this.animator = animator;
  }

  @Override
  public abstract void startView();

  /**
   * Tries to append the given string to the Appendable object that the game outputs.
   *
   * @param message string that is trying to be appended to the game output
   * @throws IllegalStateException if the Appendable object is unable transmit output
   */
  protected void tryToAppend(String message) throws IllegalStateException {
    //Try to append message to Appendable
    try {
      out.append(message);
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }
}
