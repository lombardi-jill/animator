package cs3500.animator.controller.commands;

import cs3500.animator.view.visual.IEditableView;

/**
 * Represents an {@link Runnable} class that extends {@link AbstractNonModelEditingCommands} for
 * stopping the looping of an {@link IEditableView}.
 */
public class UnLoopAnimation extends AbstractNonModelEditingCommands {

  /**
   * Constructs an {@link UnLoopAnimation} object with the given view to edit.
   *
   * @param view {@link IEditableView} that represents the view to stop looping
   */
  public UnLoopAnimation(IEditableView view) {
    super(view);
  }

  /**
   * Stops looping the {@link IEditableView} animation by delegating to the view to implement
   * loop-stopping functionality.
   */
  @Override
  public void run() {
    this.view.unLoopAnimation();
  }
}
