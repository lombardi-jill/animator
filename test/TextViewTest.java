import static org.junit.Assert.assertEquals;

import cs3500.animator.model.Animator;
import cs3500.animator.model.IAnimator;
import cs3500.animator.model.shape.EllipseShape;
import cs3500.animator.model.shape.IShape;
import cs3500.animator.model.shape.RectangleShape;
import cs3500.animator.model.state.IState;
import cs3500.animator.model.state.State;
import cs3500.animator.view.IAnimationView;
import cs3500.animator.view.nongui.TextViewImpl;
import cs3500.animator.viewmodel.IReadOnlyAnimator;

import org.junit.Before;
import org.junit.Test;

/**
 * Represents tests for {@link TextViewImpl}.
 */
public class TextViewTest {

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
    IAnimationView view = new TextViewImpl(System.out, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyOutConstructor() {
    IAnimationView view = new TextViewImpl(null, new Animator());
  }

  @Test
  public void testTextView() {
    IAnimationView view = new TextViewImpl(outputLog, model1);

    view.startView();

    assertEquals(
        "canvas 0 0 500 500\n"
            + "Shape r rectangle\n"
            + "motion r  1 100 200 75 50 200 100 50    5 400 375 75 80 200 100 50",
        outputLog.toString());
  }

  @Test
  public void testTextViewWIthMultipleShapes() {
    animator.addShape(rectangle1, "r");
    animator.addShape(ellipse1, "e");

    IAnimationView view = new TextViewImpl(outputLog, animator);

    view.startView();

    assertEquals(""
            + "canvas 0 0 500 500\n"
            + "Shape r rectangle\n"
            + "motion r  1 0 0 10 20 0 0 255    5 25 45 10 20 0 0 255\n"
            + "motion r  5 25 45 10 20 0 0 255    14 25 45 10 20 0 134 32\n"
            + "Shape e ellipse\n"
            + "motion e  4 0 0 5 7 0 255 0    7 25 45 5 7 0 255 0\n"
            + "motion e  7 25 45 5 7 0 255 0    57 25 45 4 5 0 134 32",
        outputLog.toString());
  }

  @Test
  public void printTextViewWithOneShape() {
    animator.addShape(rectangle1, "r");

    IAnimationView view = new TextViewImpl(outputLog, animator);

    view.startView();

    assertEquals(""
        + "canvas 0 0 500 500\n"
            + "Shape r rectangle\n"
            + "motion r  1 0 0 10 20 0 0 255    5 25 45 10 20 0 0 255\n"
            + "motion r  5 25 45 10 20 0 0 255    14 25 45 10 20 0 134 32",
        outputLog.toString());
  }

  @Test
  public void testTextViewWithNoShapes() {
    IAnimationView view = new TextViewImpl(outputLog, animator);

    view.startView();

    assertEquals("canvas 0 0 500 500\n", outputLog.toString());
  }
}
