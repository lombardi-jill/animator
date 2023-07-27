package cs3500.animator.controller.commands;

import cs3500.animator.model.IAnimator;
import cs3500.animator.model.shape.EllipseShape;
import cs3500.animator.model.shape.IShape;
import cs3500.animator.model.shape.RectangleShape;
import cs3500.animator.view.visual.IEditableView;

/**
 * Represents an {@link Runnable} class that extends {@link AbstractModelEditingCommands} for adding
 * shapes to the model and reflecting this change in the {@link IEditableView}.
 */
public class AddShape extends AbstractModelEditingCommands {

  /**
   * Constructs an instance of {@link AddShape}.
   *
   * @param model {@link IAnimator} representing the model to be changed when a shape is added
   * @param view  {@link IEditableView} represents the view to modify to reflect a shape has been
   *              added
   */
  public AddShape(IAnimator model, IEditableView view) {
    super(model, view);
  }

  /**
   * Adds new shapes to the {@link IAnimator} model and reflects this change in the {@link
   * IEditableView} view. Indicates to the view when the action was unsuccessful.
   */
  @Override
  public void run() {
    String[] info = this.view.getInfoOfShapeToAdd();
    String shapeType = info[0];
    IShape shape;
    switch (shapeType) {
      case "rectangle":
        shape = new RectangleShape();
        break;
      case "ellipse":
        shape = new EllipseShape();
        break;
      default:
        this.view.displayErrorMessage("Not a valid type of a shape.");
        return;
    }
    try {
      this.model.addShape(shape, info[1]);
    } catch (IllegalArgumentException e) {
      this.view.displayErrorMessage(e.getMessage());
    }
    this.view.refreshShapeList();
    this.view.repaintAnimation();
  }
}
