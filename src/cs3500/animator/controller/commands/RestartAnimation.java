package cs3500.animator.controller.commands;

import cs3500.animator.view.visual.IEditableView;

/**
 * Represents an {@link Runnable} class that extends {@link AbstractNonModelEditingCommands} for
 * restarting the {@link IEditableView}.
 */
public class RestartAnimation extends AbstractNonModelEditingCommands {

  /**
   * Constructs an {@link RestartAnimation} object with the given view to restart.
   *
   * @param view {@link IEditableView} that represents the view to restart
   */
  public RestartAnimation(IEditableView view) {
    super(view);
  }

  /**
   * Restarts the {@link IEditableView} animation by delegating to the view to implement
   * restarting functionality.
   */
  @Override
  public void run() {
    this.view.restartAnimation();
  }
}
