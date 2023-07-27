package cs3500.animator.controller.commands;

import cs3500.animator.model.IAnimator;
import cs3500.animator.model.state.IState;
import cs3500.animator.model.state.State;
import cs3500.animator.view.visual.IEditableView;
import java.util.Map;

/**
 * Represents an {@link Runnable} class that extends {@link AbstractModelEditingCommands} for adding
 * states (or keyframes) to the model and reflecting this change in the {@link IEditableView}.
 */
public class AddState extends AbstractModelEditingCommands {

  /**
   * Constructs an instance of {@link AddState}.
   *
   * @param model {@link IAnimator} representing the model to be changed when a keyframe is added
   * @param view  {@link IEditableView} represents the view to modify to reflect a keyframe has
   *              been added
   */
  public AddState(IAnimator model, IEditableView view) {
    super(model, view);
  }

  /**
   * Adds new states (keyframes) to the correct shape in the {@link IAnimator} model and reflects
   * this change in the {@link IEditableView} view. Indicates to the view when the action was
   * unsuccessful.
   */
  @Override
  public void run() {
    String shape = view.getShapeSelected();
    Map<String, Integer> vals;
    try {
      vals = view.getAddedKeyFrameValues();
    } catch (IllegalArgumentException e) {
      this.view.displayErrorMessage(e.getMessage());
      return;
    }
    Integer tick = vals.get("tick");
    if (tick < 0) {
      this.view.displayErrorMessage("Cannot add state at a negative tick.");
      return;
    }
    IState stateToAdd;
    try {
      stateToAdd = new State(vals.get("red"), vals.get("green"), vals.get("blue"),
          vals.get("x"), vals.get("y"), vals.get("width"), vals.get("height"));
    } catch (IllegalArgumentException e) {
      this.view.displayErrorMessage(e.getMessage());
      return;
    }
    if (model.getObjectsToAnimate().get(shape).getShapeStates().containsKey(tick)) {
      this.view.displayErrorMessage("Already existing keyframe at " + tick);
      return;
    }
    model.addShapeState(shape, stateToAdd, tick);
    view.repaintAnimation();
    view.refreshKeyFrameList();
  }
}
