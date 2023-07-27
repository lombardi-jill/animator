package cs3500.animator.controller.commands;

import cs3500.animator.model.IAnimator;
import cs3500.animator.view.visual.IEditableView;

/**
 * Represents an abstract class for {@link Runnable} commands that interact with both the {@link
 * IAnimator} model and the {@link IEditableView} view.
 */
abstract class AbstractModelEditingCommands implements Runnable {

  protected final IAnimator model;
  protected final IEditableView view;

  /**
   * Constructs an instance of an {@link AbstractModelEditingCommands} to handle commands that have
   * effects on both the model and view.
   *
   * @param model {@link IAnimator} representing the model to be changed with the {@link Runnable}
   *              command
   * @param view  {@link IEditableView} represents the view to modify and change states according
   *              to the command given
   */
  protected AbstractModelEditingCommands(IAnimator model, IEditableView view) {
    this.model = model;
    this.view = view;
  }
}
