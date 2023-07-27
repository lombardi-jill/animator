package cs3500.animator.view.nongui.svgshapes;

/**
 * Represents a class for producing properly formatted SVG for ellipses.
 */
public class SVGEllipse extends AbstractSVG {

  @Override
  protected String getShapeType() {
    return "ellipse";
  }

  @Override
  protected String getWidthName() {
    return "rx";
  }

  @Override
  protected String getHeightName() {
    return "ry";
  }

  @Override
  protected String getXName() {
    return "cx";
  }

  @Override
  protected String getYName() {
    return "cy";
  }

  @Override
  protected int getShapeSize(int s) {
    return s / 2;
  }
}
