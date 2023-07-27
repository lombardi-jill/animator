import static org.junit.Assert.assertEquals;

import cs3500.animator.model.Animator;
import cs3500.animator.model.IAnimator;
import cs3500.animator.model.shape.EllipseShape;
import cs3500.animator.model.shape.IShape;
import cs3500.animator.model.shape.RectangleShape;
import cs3500.animator.model.state.IState;
import cs3500.animator.model.state.State;
import cs3500.animator.view.IAnimationView;
import cs3500.animator.view.nongui.SVGViewImpl;
import cs3500.animator.viewmodel.IReadOnlyAnimator;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents tests for {@link SVGViewImpl}.
 */
public class SVGViewTests {

  StringBuilder outputLog;
  IReadOnlyAnimator model1;
  IState rectState1;
  IState rectState2;
  IState rectState3;
  IState ellipseState1;
  IState ellipseState2;
  IState ellipseState3;
  IShape rectangle1;
  IShape ellipse1;
  IAnimator animator;

  @Before
  public void initializeAnimator() {
    this.rectState1 = new State(0, 0, 255, 0, 0, 10, 20);
    this.rectState2 = new State(0, 0, 255, 25, 45, 10, 20);
    this.rectState3 = new State(0, 134, 32, 25, 45, 10, 20);
    this.ellipseState1 = new State(0, 255, 0, 0, 0, 5, 7);
    this.ellipseState2 = new State(0, 255, 0, 25, 45, 5, 7);
    this.ellipseState3 = new State(0, 134, 32, 25, 45, 4, 5);
    this.rectangle1 = new RectangleShape();
    this.ellipse1 = new EllipseShape();
    this.rectangle1.addState(1, this.rectState1);
    this.rectangle1.addState(5, this.rectState2);
    this.rectangle1.addState(14, this.rectState3);
    this.ellipse1.addState(4, this.ellipseState1);
    this.ellipse1.addState(7, this.ellipseState2);
    this.ellipse1.addState(57, this.ellipseState3);
    this.animator = new Animator();
    this.outputLog = new StringBuilder();
    this.model1 = Animator.builder().declareShape("r", "rectangle")
        .addMotion("r", 1, 100, 200, 75, 50, 200, 100, 50, 5, 400, 375, 75, 80, 200, 100, 50)
        .build();
  }


  @Test(expected = IllegalArgumentException.class)
  public void testEmptyModelConstructor() {
    new SVGViewImpl(System.out, null, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyOutConstructor() {
    new SVGViewImpl(null, new Animator(), 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testZeroTickRate() {
    new SVGViewImpl(null, new Animator(), 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeTickRate() {
    new SVGViewImpl(null, new Animator(), -1);
  }

  @Test
  public void testSVGView() {
    IAnimationView view = new SVGViewImpl(outputLog, model1, 39);

    view.startView();

    assertEquals(
        "<svg viewBox=\"0 0 500 500\" version=\"1.1\"\n"
            + "\t\txmlns=\"http://www.w3.org/2000/svg\">\n"
            + "\n"
            + "<rect id=\"r\" x=\"100\" y=\"200\" width=\"75\" height=\"50\" fill=\"rgb(200,100,50)"
            + "\" visibility=\"hidden\" >\n"
            + "\t\t<set attributeName=\"visibility\" to=\"visible\" begin=\"25.6ms\" />\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"25.6ms\" dur=\"102.6ms\" attributeName="
            + "\"height\" to=\"80\" fill =\"freeze\" />\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"25.6ms\" dur=\"102.6ms\" attributeName="
            + "\"x\" to=\"400\" fill =\"freeze\" />\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"25.6ms\" dur=\"102.6ms\" attributeName="
            + "\"y\" to=\"375\" fill =\"freeze\" />\n"
            + "</rect>\n"
            + "\n"
            + "</svg>",
        outputLog.toString());
  }

  @Test
  public void testSVGViewWIthMultipleShapes() {
    animator.addShape(rectangle1, "r");
    animator.addShape(ellipse1, "e");

    IAnimationView view = new SVGViewImpl(outputLog, animator, 666);

    view.startView();

    assertEquals(""
            + "<svg viewBox=\"0 0 500 500\" version=\"1.1\"\n"
            + "\t\txmlns=\"http://www.w3.org/2000/svg\">\n"
            + "\n"
            + "<rect id=\"r\" x=\"0\" y=\"0\" width=\"10\" height=\"20\" fill=\"rgb(0,0,255)\" "
            + "visibility=\"hidden\" >\n"
            + "\t\t<set attributeName=\"visibility\" to=\"visible\" begin=\"1.5ms\" />\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"1.5ms\" dur=\"6.0ms\" "
            + "attributeName=\"x\" to=\"25\" fill =\"freeze\" />\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"1.5ms\" dur=\"6.0ms\" "
            + "attributeName=\"y\" to=\"45\" fill =\"freeze\" />\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"7.5ms\" dur=\"13.5ms\" "
            + "attributeName=\"fill\" to=\"rgb(0,134,32)\" fill =\"freeze\" />\n"
            + "</rect>\n"
            + "\n"
            + "<ellipse id=\"e\" cx=\"0\" cy=\"0\" rx=\"2\" ry=\"3\" fill=\"rgb(0,255,0)\" "
            + "visibility=\"hidden\" >\n"
            + "\t\t<set attributeName=\"visibility\" to=\"visible\" begin=\"6.0ms\" />\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"6.0ms\" dur=\"4.5ms\" "
            + "attributeName=\"cx\" to=\"25\" fill =\"freeze\" />\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"6.0ms\" dur=\"4.5ms\" "
            + "attributeName=\"cy\" to=\"45\" fill =\"freeze\" />\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"10.5ms\" dur=\"75.1ms\" "
            + "attributeName=\"fill\" to=\"rgb(0,134,32)\" fill =\"freeze\" />\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"10.5ms\" dur=\"75.1ms\" "
            + "attributeName=\"rx\" to=\"2\" fill =\"freeze\" />\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"10.5ms\" dur=\"75.1ms\" "
            + "attributeName=\"ry\" to=\"2\" fill =\"freeze\" />\n"
            + "</ellipse>\n"
            + "\n"
            + "</svg>",
        outputLog.toString());
  }

  @Test
  public void SVGViewWithOneShape() {
    animator.addShape(rectangle1, "i love satan");

    IAnimationView view = new SVGViewImpl(outputLog, animator, 666);

    view.startView();

    assertEquals(""
            + "<svg viewBox=\"0 0 500 500\" version=\"1.1\"\n"
            + "\t\txmlns=\"http://www.w3.org/2000/svg\">\n"
            + "\n"
            + "<rect id=\"i love satan\" x=\"0\" y=\"0\" width=\"10\" height=\"20\" "
            + "fill=\"rgb(0,0,255)\" visibility=\"hidden\" >\n"
            + "\t\t<set attributeName=\"visibility\" to=\"visible\" begin=\"1.5ms\" />\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"1.5ms\" dur=\"6.0ms\" "
            + "attributeName=\"x\" to=\"25\" fill =\"freeze\" />\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"1.5ms\" dur=\"6.0ms\" "
            + "attributeName=\"y\" to=\"45\" fill =\"freeze\" />\n"
            + "\t\t<animate attributeType=\"xml\" begin=\"7.5ms\" dur=\"13.5ms\" "
            + "attributeName=\"fill\" to=\"rgb(0,134,32)\" fill =\"freeze\" />\n"
            + "</rect>\n"
            + "\n"
            + "</svg>",
        outputLog.toString());
  }

  @Test
  public void SVGViewWithNoShapes() {
    IAnimationView view = new SVGViewImpl(outputLog, animator, 69);

    view.startView();

    assertEquals(""
        + "<svg viewBox=\"0 0 500 500\" version=\"1.1\"\n"
        + "\t\txmlns=\"http://www.w3.org/2000/svg\">\n"
        + "\n"
        + "</svg>", outputLog.toString());
  }
}
