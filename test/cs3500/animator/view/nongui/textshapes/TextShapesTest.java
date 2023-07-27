package cs3500.animator.view.nongui.textshapes;

import static org.junit.Assert.assertEquals;

import cs3500.animator.model.shape.EllipseShape;
import cs3500.animator.model.shape.IShape;
import cs3500.animator.model.shape.RectangleShape;
import cs3500.animator.model.state.IState;
import cs3500.animator.model.state.State;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents tests for {@link ITextShapeDescription}.
 */
public class TextShapesTest {

  IState ellipseState1;
  IState ellipseState2;
  IState ellipseState3;
  IShape ellipse1;
  IShape ellipse2;
  ITextShapeDescription ellipse1desc;
  IState rectState1;
  IState rectState2;
  IState rectState3;
  IShape rectangle1;
  IShape rectangle2;
  ITextShapeDescription rect1desc;

  @Before
  public void initializeShapes() {
    this.ellipseState1 = new State(0, 255, 0, 0, 0, 5, 7);
    this.ellipseState2 = new State(0, 255, 0, 25, 45, 5, 7);
    this.ellipseState3 = new State(0, 134, 32, 25, 45, 4, 5);
    this.ellipse1 = new EllipseShape();
    this.ellipse1.addMotion(this.ellipseState1, 1, this.ellipseState2, 14);
    this.ellipse1.addState(37, this.ellipseState3);
    this.ellipse1desc = new TextEllipse("e", this.ellipse1.getShapeStates());
    this.ellipse2 = new EllipseShape();

    this.rectState1 = new State(0, 0, 255, 0, 0, 10, 20);
    this.rectState2 = new State(0, 0, 255, 25, 45, 10, 20);
    this.rectState3 = new State(0, 134, 32, 25, 45, 10, 20);
    this.rectangle1 = new RectangleShape();
    this.rectangle1.addMotion(this.rectState1, 6, this.rectState2, 23);
    this.rectangle1.addMotion(this.rectState2, 23, this.rectState3, 67);
    this.rect1desc = new TextRectangle("r", this.rectangle1.getShapeStates());
    this.rectangle2 = new RectangleShape();
  }

  @Test
  public void testPrintDescriptionEllipse() {
    assertEquals(""
            + "Shape e ellipse\n"
            + "motion e  1 0 0 5 7 0 255 0    14 25 45 5 7 0 255 0\n"
            + "motion e  14 25 45 5 7 0 255 0    37 25 45 4 5 0 134 32",
        ellipse1desc.getShapeDescription());
  }

  @Test
  public void testPrintDescriptionRectangle() {
    assertEquals(""
            + "Shape r rectangle\n"
            + "motion r  6 0 0 10 20 0 0 255    23 25 45 10 20 0 0 255\n"
            + "motion r  23 25 45 10 20 0 0 255    67 25 45 10 20 0 134 32",
        rect1desc.getShapeDescription());
  }


  @Test
  public void testPrintDescriptionWithOneShapeState() {
    rectangle2.addState(1, rectState1);
    ITextShapeDescription rect2desc = new TextRectangle("r", rectangle2.getShapeStates());
    assertEquals(""
            + "Shape r rectangle\n"
            + "motion r  1 0 0 10 20 0 0 255",
        rect2desc.getShapeDescription());

    ellipse2.addState(4, ellipseState1);
    ITextShapeDescription ellipse2desc = new TextEllipse("e", ellipse2.getShapeStates());
    assertEquals(""
            + "Shape e ellipse\n"
            + "motion e  4 0 0 5 7 0 255 0",
        ellipse2desc.getShapeDescription());
  }

  @Test
  public void testRectangleShapeDescriptionWhenAddingStatesOutOfOrder() {
    rectangle2.addState(1, rectState1);
    rectangle2.addState(5, rectState2);
    rectangle2.addState(14, rectState3);
    rectangle2.addState(9, rectState1);
    rectangle2.addState(7, rectState2);

    ITextShapeDescription rect2desc = new TextRectangle("r", rectangle2.getShapeStates());

    assertEquals(""
            + "Shape r rectangle\n"
            + "motion r  1 0 0 10 20 0 0 255    5 25 45 10 20 0 0 255\n"
            + "motion r  5 25 45 10 20 0 0 255    7 25 45 10 20 0 0 255\n"
            + "motion r  7 25 45 10 20 0 0 255    9 0 0 10 20 0 0 255\n"
            + "motion r  9 0 0 10 20 0 0 255    14 25 45 10 20 0 134 32",
        rect2desc.getShapeDescription());
  }

  @Test
  public void testEllipseShapeDescriptionWhenAddingStatesOutOfOrder() {
    ellipse2.addState(57, ellipseState3);
    ellipse2.addState(4, ellipseState1);
    ellipse2.addState(7, ellipseState2);

    ITextShapeDescription ellipse2desc = new TextEllipse("e", ellipse2.getShapeStates());

    assertEquals(""
            + "Shape e ellipse\n"
            + "motion e  4 0 0 5 7 0 255 0    7 25 45 5 7 0 255 0\n"
            + "motion e  7 25 45 5 7 0 255 0    57 25 45 4 5 0 134 32",
        ellipse2desc.getShapeDescription());
  }

  @Test
  public void testPrintDescriptionEmptyShapeStates() {
    assertEquals("Shape r rectangle",
        new TextRectangle("r", rectangle2.getShapeStates()).getShapeDescription());
    assertEquals("Shape e ellipse",
        new TextEllipse("e", ellipse2.getShapeStates()).getShapeDescription());
  }
}
