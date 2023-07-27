package cs3500.animator.controller.commands;

import cs3500.animator.view.visual.IEditableView;

/**
 * Represents an {@link Runnable} class that extends {@link AbstractNonModelEditingCommands} for
 * pausing the {@link IEditableView}.
 */
public class PauseAnimation extends AbstractNonModelEditingCommands {

  /**
   * Constructs an {@link LoopAnimation} object with the given view to pause.
   *
   * @param view {@link IEditableView} that represents the view to pause
   */
  public PauseAnimation(IEditableView view) {
    super(view);
  }

  /**
   * Pauses the {@link IEditableView} animation by delegating to the view to implement pausing
   * functionality.
   */
  @Override
  public void run() {
    this.view.pauseAnimation();
  }
}
