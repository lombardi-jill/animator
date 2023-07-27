package cs3500.animator.view.nongui;

import cs3500.animator.viewmodel.IReadOnlyAnimator;
import cs3500.animator.model.shape.IShape;
import cs3500.animator.view.nongui.svgshapes.ISVGShape;
import cs3500.animator.view.nongui.svgshapes.SVGEllipse;
import cs3500.animator.view.nongui.svgshapes.SVGRectangle;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Represents a view that produces properly formatted SVG to represent the animation so that the
 * animation may be viewed in web browser.
 */
public final class SVGViewImpl extends AbstractNonGUIView {

  private final Map<String, ISVGShape> commands;
  private final int tickRate;

  /**
   * Constructs an instance of {@link SVGViewImpl} with the specified output, model, and ticks per
   * second.
   *
   * @param out      an appendable object that represents where the SVG outputs to
   * @param animator an {@link IReadOnlyAnimator} that represents a read only version of the model
   *                 to animate to be used by the view
   * @param tickRate an integer representing the ticks per second of the animation
   * @throws IllegalArgumentException if the animator or appendable is null or if the tick rate is
   *                                  less than 1
   */
  public SVGViewImpl(Appendable out, IReadOnlyAnimator animator, int tickRate) {
    super(out, animator);

    if (tickRate < 1) {
      throw new IllegalArgumentException("Tick rate cannot be smaller than 1 tick per second");
    }
    this.commands = initializeCommands();
    this.tickRate = tickRate;
  }

  /**
   * Initializes the command map with the known shape types mapped to function objects that create
   * the SVG for the shape.
   *
   * @return a {@code Map<String, ISVGShape>} that holds the function objects to create SVG for the
   *         known shapes in the animation.
   */
  private Map<String, ISVGShape> initializeCommands() {
    Map<String, ISVGShape> commandMap = new HashMap<>();
    commandMap.put("rectangle", new SVGRectangle());
    commandMap.put("ellipse", new SVGEllipse());
    return commandMap;
  }

  @Override
  public void startView() {
    StringJoiner joiner = new StringJoiner("\n\n");
    String svgInfo =
        "<svg viewBox=\"" + animator.getTopLeftX() + " " + animator.getTopLeftY() + " " + animator
            .getWidth() + " " + animator.getHeight()
            + "\" version=\"1.1\"\n\t\txmlns=\"http://www.w3.org/2000/svg\">";
    joiner.add(svgInfo);
    Map<String, IShape> toAnimate = animator.getObjectsToAnimate();

    for (String key : toAnimate.keySet()) {
      String shapeType = toAnimate.get(key).getShapeType();

      joiner.add(this.commands.get(shapeType)
          .makeSVGShape(key, toAnimate.get(key).getShapeStates(), tickRate));
    }
    joiner.add("</svg>");
    tryToAppend(joiner.toString());
  }
}
