package cs3500.animator.controller.commands;

import cs3500.animator.model.IAnimator;
import cs3500.animator.view.visual.IEditableView;
import java.util.Map;

/**
 * Represents an {@link Runnable} class that extends {@link AbstractModelEditingCommands} for
 * editing states (or keyframes) of existing shapes to the model and reflecting this change in the
 * {@link IEditableView}.
 */
public class EditState extends AbstractModelEditingCommands {

  /**
   * Constructs an instance of {@link EditState}.
   *
   * @param model {@link IAnimator} representing the model to be changed when a keyframe is edited
   * @param view  {@link IEditableView} represents the view to modify to reflect a keyframe has
   *              been edited
   */
  public EditState(IAnimator model, IEditableView view) {
    super(model, view);
  }

  /**
   * Edits states (keyframes) for the correct shape in the {@link IAnimator} model and reflects this
   * change in the {@link IEditableView} view. Indicates to the view when the action was
   * unsuccessful.
   */
  @Override
  public void run() {
    String shape = view.getShapeSelected();
    Map<String, Integer> vals;
    try {
      vals = view.getEditedKeyframeValues();
    } catch (IllegalArgumentException e) {
      this.view.displayErrorMessage(e.getMessage());
      return;
    }
    try {
      model.editShapeState(shape, vals.get("tick"), vals.get("red"), vals.get("green"),
          vals.get("blue"),
          vals.get("x"), vals.get("y"), vals.get("width"), vals.get("height"));
    } catch (IllegalArgumentException e) {
      this.view.displayErrorMessage(e.getMessage());
      return;
    }
    view.repaintAnimation();
    view.refreshKeyFrameList();
  }
}
