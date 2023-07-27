package cs3500.animator.controller.commands;

import cs3500.animator.view.visual.IEditableView;

/**
 * Represents an {@link Runnable} class that extends {@link AbstractNonModelEditingCommands} for
 * resuming the {@link IEditableView}.
 */
public class ResumeAnimation extends AbstractNonModelEditingCommands {

  /**
   * Constructs an {@link ResumeAnimation} object with the given view to resume.
   *
   * @param view {@link IEditableView} that represents the view to resume
   */
  public ResumeAnimation(IEditableView view) {
    super(view);
  }

  /**
   * Resumes the {@link IEditableView} animation by delegating to the view to implement resuming
   * functionality.
   */
  @Override
  public void run() {
    this.view.resumeAnimation();
  }
}