package cs3500.animator.controller.commands;

import cs3500.animator.view.visual.IEditableView;

/**
 * Represents an abstract class for {@link Runnable} commands that interact with just the {@link
 * IEditableView} view and do not edit the underlying model.
 */
public abstract class AbstractNonModelEditingCommands implements Runnable {

  protected final IEditableView view;

  /**
   * Constructs an instance of an {@link AbstractNonModelEditingCommands} to handle commands that
   * have effects on the view.
   *
   * @param view {@link IEditableView} that is changed according to the given command
   */
  protected AbstractNonModelEditingCommands(IEditableView view) {
    this.view = view;
  }

  @Override
  public abstract void run();
}
