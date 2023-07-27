package cs3500.animator.controller.commands;

import cs3500.animator.model.IAnimator;
import cs3500.animator.view.visual.IEditableView;

/**
 * Represents an {@link Runnable} class that extends {@link AbstractModelEditingCommands} for
 * removing existing shapes in the model and reflecting this change in the {@link
 * IEditableView}.
 */
public class RemoveShape extends AbstractModelEditingCommands {

  /**
   * Constructs an instance of {@link RemoveShape}.
   *
   * @param model {@link IAnimator} representing the model to be changed when a shape is removed
   * @param view  {@link IEditableView} represents the view to modify to reflect a shape has been
   *              removed
   */
  public RemoveShape(IAnimator model, IEditableView view) {
    super(model, view);
  }

  /**
   * Removes the correct shape from the {@link IAnimator} model and reflects this change in the
   * {@link IEditableView} view. Indicates to the view when the action was unsuccessful.
   */
  @Override
  public void run() {
    String shape = view.getShapeSelected();
    if (shape == null) {
      this.view.displayErrorMessage("No shape to remove.");
      return;
    }
    model.removeShape(shape);
    view.refreshShapeList();
    this.view.repaintAnimation();
  }
}
