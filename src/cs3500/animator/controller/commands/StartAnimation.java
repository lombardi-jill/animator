package cs3500.animator.controller.commands;

import cs3500.animator.view.visual.IEditableView;

/**
 * Represents an {@link Runnable} class that extends {@link AbstractNonModelEditingCommands} for
 * starting the {@link IEditableView}.
 */
public class StartAnimation extends AbstractNonModelEditingCommands {

  /**
   * Constructs an {@link StartAnimation} object with the given view to start.
   *
   * @param view {@link IEditableView} that represents the view to start
   */
  public StartAnimation(IEditableView view) {
    super(view);
  }

  /**
   * Starts the {@link IEditableView} animation by delegating to the view to implement starting
   * functionality.
   */
  @Override
  public void run() {
    this.view.startAnimation();
  }
}
