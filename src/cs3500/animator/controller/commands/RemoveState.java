package cs3500.animator.controller.commands;

import cs3500.animator.model.IAnimator;
import cs3500.animator.view.visual.IEditableView;

/**
 * Represents an {@link Runnable} class that extends {@link AbstractModelEditingCommands} for
 * removing states (or keyframes) of existing shapes in the model and reflecting this change in the
 * {@link IEditableView}.
 */
public class RemoveState extends AbstractModelEditingCommands {

  /**
   * Constructs an instance of {@link RemoveState}.
   *
   * @param model {@link IAnimator} representing the model to be changed when a state (or keyframe)
   *              is removed
   * @param view  {@link IEditableView} represents the view to modify to reflect a state has been
   *              removed
   */
  public RemoveState(IAnimator model, IEditableView view) {
    super(model, view);
  }

  /**
   * Removes the correct state of the existing shape in the {@link IAnimator} model and reflects
   * this change in the {@link IEditableView} view. Indicates to the view when the action was
   * unsuccessful.
   */
  @Override
  public void run() {
    Integer tickToRemove = view.getStateSelected();
    if (tickToRemove == null) {
      this.view.displayErrorMessage("No state selected to remove");
      return;
    }
    String shapeSelected = view.getShapeSelected();
    if (shapeSelected == null) {
      this.view.displayErrorMessage("No shape to remove state from");
      return;
    }
    model.removeShapeState(shapeSelected, tickToRemove);
    view.refreshKeyFrameList();
    this.view.repaintAnimation();
  }
}
