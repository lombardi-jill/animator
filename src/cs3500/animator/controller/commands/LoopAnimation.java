package cs3500.animator.controller.commands;

import cs3500.animator.view.visual.IEditableView;

/**
 * Represents an {@link Runnable} class that extends {@link AbstractNonModelEditingCommands} for
 * looping the {@link IEditableView}.
 */
public class LoopAnimation extends AbstractNonModelEditingCommands {

  /**
   * Constructs an {@link LoopAnimation} object with the given view to edit.
   *
   * @param view {@link IEditableView} that represents the view to loop
   */
  public LoopAnimation(IEditableView view) {
    super(view);
  }

  /**
   * Loops an {@link IEditableView} animation by delegating to the view to implement looping
   * functionality.
   */
  @Override
  public void run() {
    this.view.loopAnimation();
  }
}
