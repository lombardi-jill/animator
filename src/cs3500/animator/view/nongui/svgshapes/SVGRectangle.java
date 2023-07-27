package cs3500.animator.view.nongui.svgshapes;

/**
 * Represents a class for producing properly formatted SVG for rectangles.
 */
public class SVGRectangle extends AbstractSVG {

  @Override
  protected String getShapeType() {
    return "rect";
  }

  @Override
  protected String getWidthName() {
    return "width";
  }

  @Override
  protected String getHeightName() {
    return "height";
  }

  @Override
  protected String getXName() {
    return "x";
  }

  @Override
  protected String getYName() {
    return "y";
  }

  @Override
  protected int getShapeSize(int s) {
    return s;
  }
}
