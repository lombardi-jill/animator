package cs3500.animator.view.nongui;

import cs3500.animator.model.shape.IShape;
import cs3500.animator.model.state.IState;
import cs3500.animator.view.nongui.textshapes.ITextShapeDescription;
import cs3500.animator.view.nongui.textshapes.TextEllipse;
import cs3500.animator.view.nongui.textshapes.TextRectangle;
import cs3500.animator.viewmodel.IReadOnlyAnimator;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.function.BiFunction;

/**
 * Represents a view that produces formatted text to represent the animation. It first returns
 * information about the canvas of the animation. Then for each shape, it details its name and
 * states throughout time.
 */
public final class TextViewImpl extends AbstractNonGUIView {

  private final Map<String, BiFunction<String, Map<Integer, IState>, ITextShapeDescription>>
      commands;

  /**
   * Constructs an instance of {@link TextViewImpl} with the specified output source and model.
   *
   * @param out      an appendable object that represents where the text outputs to
   * @param animator an {@link IReadOnlyAnimator} that represents a read only version of the model
   *                 to animate to be used by the view
   * @throws IllegalArgumentException if the animator or appendable is null
   */
  public TextViewImpl(Appendable out, IReadOnlyAnimator animator) {
    super(out, animator);

    this.commands = initializeCommands();
  }

  /**
   * Initializes the command map with the known shape types mapped to function objects that create
   * the text output for the shape.
   *
   * @return a {@code Map<String, ITextShapeDescription>} that holds the function objects to create
   *         text output for the known shapes in the animation.
   */
  private Map<String,
      BiFunction<String, Map<Integer, IState>, ITextShapeDescription>> initializeCommands() {
    Map<String, BiFunction
        <String, Map<Integer, IState>, ITextShapeDescription>> commandMap = new HashMap<>();
    commandMap.put("rectangle", TextRectangle::new);
    commandMap.put("ellipse", TextEllipse::new);
    return commandMap;
  }

  @Override
  public void startView() {
    String canvasInfo = "canvas " + animator.getTopLeftX() + " " + animator.getTopLeftY()
        + " " + animator.getWidth() + " " + animator.getHeight() + "\n";
    this.tryToAppend(canvasInfo);
    this.tryToAppend(this.printDescription());
  }

  /**
   * For each shape in the animation, this returns a string describing the shape and its motions.
   * The first line contains the shape's name and the type of shape. Each line afterwards contains
   * the shape name, a starting state and an ending state. The states are printed out in the
   * following order: tick, x, y, width, height, red, green, blue. The start time of each line
   * (besides the first line) matches the end time of the previous line.
   *
   * @return a string containing a description of the {@link IShape} objects to animate.
   */
  protected String printDescription() {
    Map<String, IShape> objectsToAnimate = animator.getObjectsToAnimate();
    StringJoiner description = new StringJoiner("\n");
    for (String key : objectsToAnimate.keySet()) {
      IShape shapeToAnimate = objectsToAnimate.get(key);
      Map<Integer, IState> shapeStates = shapeToAnimate.getShapeStates();
      String type = shapeToAnimate.getShapeType();

      String shapeDescription = this.commands.get(type).apply(key, shapeStates)
          .getShapeDescription();
      description.add(shapeDescription);
    }
    return description.toString();
  }
}
