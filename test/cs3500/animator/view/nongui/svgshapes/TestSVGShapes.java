package cs3500.animator.view.nongui.svgshapes;

import static org.junit.Assert.assertEquals;

import cs3500.animator.model.shape.EllipseShape;
import cs3500.animator.model.shape.IShape;
import cs3500.animator.model.shape.RectangleShape;
import cs3500.animator.model.state.IState;
import cs3500.animator.model.state.State;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents tests for {@link ISVGShape}.
 */
public class TestSVGShapes {

  IState ellipseState1;
  IState ellipseState2;
  IState ellipseState3;
  IShape ellipse1;
  IShape ellipse2;
  ISVGShape ellipse1desc;
  IState rectState1;
  IState rectState2;
  IState rectState3;
  IShape rectangle1;
  IShape rectangle2;
  ISVGShape rect1desc;

  @Before
  public void initializeShapes() {
    this.ellipseState1 = new State(0, 255, 0, 0, 0, 5, 7);
    this.ellipseState2 = new State(0, 255, 0, 25, 45, 5, 7);
    this.ellipseState3 = new State(0, 134, 32, 25, 45, 4, 5);
    this.ellipse1 = new EllipseShape();
    this.ellipse1.addMotion(this.ellipseState1, 1, this.ellipseState2, 14);
    this.ellipse1.addState(37, this.ellipseState3);
    this.ellipse1desc = new SVGEllipse();
    this.ellipse2 = new EllipseShape();

    this.rectState1 = new State(0, 0, 255, 0, 0, 10, 20);
    this.rectState2 = new State(0, 0, 255, 25, 45, 10, 20);
    this.rectState3 = new State(0, 134, 32, 25, 45, 10, 20);
    this.rectangle1 = new RectangleShape();
    this.rectangle1.addMotion(this.rectState1, 6, this.rectState2, 23);
    this.rectangle1.addMotion(this.rectState2, 23, this.rectState3, 67);
    this.rect1desc = new SVGRectangle();
    this.rectangle2 = new RectangleShape();
  }

  @Test
  public void testPrintDescriptionEllipse() {
    assertEquals(""
            + "<ellipse id=\"e\" cx=\"0\" cy=\"0\" rx=\"2\" ry=\"3\" fill=\"rgb(0,255,0)\" "
            + "visibility=\"hidden\" >\n"
            + "\t\t<set attributeName=\"visibility\" to=\"visible\" begin=\"23.3ms\" />\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"23.3ms\" dur=\"302.3ms\" "
            + "attributeName=\"cx\" to=\"25\" fill =\"freeze\" />\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"23.3ms\" dur=\"302.3ms\" "
            + "attributeName=\"cy\" to=\"45\" fill =\"freeze\" />\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"325.6ms\" dur=\"534.9ms\" "
            + "attributeName=\"fill\" to=\"rgb(0,134,32)\" fill =\"freeze\" />\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"325.6ms\" dur=\"534.9ms\" "
            + "attributeName=\"rx\" to=\"2\" fill =\"freeze\" />\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"325.6ms\" dur=\"534.9ms\" "
            + "attributeName=\"ry\" to=\"2\" fill =\"freeze\" />\n"
            + "</ellipse>",
        ellipse1desc.makeSVGShape("e", ellipse1.getShapeStates(), 43));
  }

  @Test
  public void testPrintDescriptionRectangle() {
    assertEquals(""
            + "<rect id=\"r\" x=\"0\" y=\"0\" width=\"10\" height=\"20\" fill=\"rgb(0,0,255)\" "
            + "visibility=\"hidden\" >\n"
            + "\t\t<set attributeName=\"visibility\" to=\"visible\" begin=\"3000.0ms\" />\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"3000.0ms\" dur=\"8500.0ms\" "
            + "attributeName=\"x\" to=\"25\" fill =\"freeze\" />\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"3000.0ms\" dur=\"8500.0ms\" "
            + "attributeName=\"y\" to=\"45\" fill =\"freeze\" />\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"11500.0ms\" dur=\"22000.0ms\" "
            + "attributeName=\"fill\" to=\"rgb(0,134,32)\" fill =\"freeze\" />\n"
            + "</rect>",
        rect1desc.makeSVGShape("r", rectangle1.getShapeStates(), 2));
  }

  @Test
  public void testPrintDescriptionWithOneShapeState() {
    rectangle2.addState(1, rectState1);
    ISVGShape rect2desc = new SVGRectangle();
    assertEquals(""
            + "<rect id=\"yolo\" x=\"0\" y=\"0\" width=\"10\" height=\"20\" fill=\"rgb(0,0,255)\" "
            + "visibility=\"hidden\" >\n"
            + "\t\t<set attributeName=\"visibility\" to=\"visible\" begin=\"333.3ms\" />\n"
            + "</rect>",
        rect2desc.makeSVGShape("yolo", rectangle2.getShapeStates(), 3));

    ellipse2.addState(4, ellipseState1);
    ISVGShape ellipse2desc = new SVGEllipse();
    assertEquals(""
            + "<ellipse id=\"ellipsesAreCool\" cx=\"0\" cy=\"0\" rx=\"2\" ry=\"3\" "
            + "fill=\"rgb(0,255,0)\" visibility=\"hidden\" >\n"
            + "\t\t<set attributeName=\"visibility\" to=\"visible\" begin=\"166.7ms\" />\n"
            + "</ellipse>",
        ellipse2desc.makeSVGShape("ellipsesAreCool", ellipse2.getShapeStates(), 24));
  }

  @Test
  public void testRectangleShapeDescriptionWhenAddingStatesOutOfOrder() {
    rectangle2.addState(1, rectState1);
    rectangle2.addState(5, rectState2);
    rectangle2.addState(14, rectState3);
    rectangle2.addState(9, rectState1);
    rectangle2.addState(7, rectState2);

    ISVGShape rect2desc = new SVGRectangle();

    assertEquals(""
            + "<rect id=\"r\" x=\"0\" y=\"0\" width=\"10\" height=\"20\" fill=\"rgb(0,0,255)\" "
            + "visibility=\"hidden\" >\n"
            + "\t\t<set attributeName=\"visibility\" to=\"visible\" begin=\"166.7ms\" />\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"166.7ms\" dur=\"666.7ms\" "
            + "attributeName=\"x\" to=\"25\" fill =\"freeze\" />\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"166.7ms\" dur=\"666.7ms\" "
            + "attributeName=\"y\" to=\"45\" fill =\"freeze\" />\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"1166.7ms\" dur=\"333.3ms\" "
            + "attributeName=\"x\" to=\"0\" fill =\"freeze\" />\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"1166.7ms\" dur=\"333.3ms\" "
            + "attributeName=\"y\" to=\"0\" fill =\"freeze\" />\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"1500.0ms\" dur=\"833.3ms\" "
            + "attributeName=\"fill\" to=\"rgb(0,134,32)\" fill =\"freeze\" />\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"1500.0ms\" dur=\"833.3ms\" "
            + "attributeName=\"x\" to=\"25\" fill =\"freeze\" />\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"1500.0ms\" dur=\"833.3ms\" "
            + "attributeName=\"y\" to=\"45\" fill =\"freeze\" />\n"
            + "</rect>",
        rect2desc.makeSVGShape("r", rectangle2.getShapeStates(), 6));
  }

  @Test
  public void testEllipseShapeDescriptionWhenAddingStatesOutOfOrder() {
    ellipse2.addState(57, ellipseState3);
    ellipse2.addState(4, ellipseState1);
    ellipse2.addState(7, ellipseState2);

    ISVGShape ellipse2desc = new SVGEllipse();

    assertEquals(""
            + "<ellipse id=\"e\" cx=\"0\" cy=\"0\" rx=\"2\" ry=\"3\" fill=\"rgb(0,255,0)\" "
            + "visibility=\"hidden\" >\n"
            + "\t\t<set attributeName=\"visibility\" to=\"visible\" begin=\"93.0ms\" />\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"93.0ms\" dur=\"69.8ms\" "
            + "attributeName=\"cx\" to=\"25\" fill =\"freeze\" />\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"93.0ms\" dur=\"69.8ms\" "
            + "attributeName=\"cy\" to=\"45\" fill =\"freeze\" />\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"162.8ms\" dur=\"1162.8ms\" "
            + "attributeName=\"fill\" to=\"rgb(0,134,32)\" fill =\"freeze\" />\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"162.8ms\" dur=\"1162.8ms\" "
            + "attributeName=\"rx\" to=\"2\" fill =\"freeze\" />\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"162.8ms\" dur=\"1162.8ms\" "
            + "attributeName=\"ry\" to=\"2\" fill =\"freeze\" />\n"
            + "</ellipse>",
        ellipse2desc.makeSVGShape("e", ellipse2.getShapeStates(), 43));
  }

  @Test
  public void testAddingSameStateMultipleTimesAtDiffTicks() {
    rectangle2.addMotion(rectState1, 3, rectState1, 5);
    rectangle2.addMotion(rectState1, 5, rectState1, 8);
    rectangle2.addMotion(rectState1, 8, rectState1, 40);
    rectangle2.addMotion(rectState1, 45, rectState1, 50);
    rectangle2.addState(67, rectState1);

    ISVGShape rect2desc = new SVGRectangle();

    assertEquals(""
            + "<rect id=\"hey there\" x=\"0\" y=\"0\" width=\"10\" height=\"20\" "
            + "fill=\"rgb(0,0,255)\" visibility=\"hidden\" >\n"
            + "\t\t<set attributeName=\"visibility\" to=\"visible\" begin=\"1000.0ms\" />\n"
            + "</rect>",
        rect2desc.makeSVGShape("hey there", rectangle2.getShapeStates(), 3));
  }

  @Test
  public void testPrintDescriptionEmptyShapeStates() {
    assertEquals("",
        new SVGRectangle().makeSVGShape("r", rectangle2.getShapeStates(), 4));
    assertEquals("",
        new SVGEllipse().makeSVGShape("e", ellipse2.getShapeStates(), 100));
  }
}
