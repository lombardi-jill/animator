package cs3500.animator.model;

import cs3500.animator.model.shape.IShape;
import cs3500.animator.model.shape.RectangleShape;
import cs3500.animator.model.state.IState;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a mock animator for testing inputs to model sent from controller.
 */
public class MockIAnimator implements IAnimator {

  StringBuilder log;

  public MockIAnimator() {
    log = new StringBuilder();
  }

  @Override
  public void addShape(IShape shape, String name) throws IllegalArgumentException {
    log.append("ADDING SHAPE:\n").append(name).append("\n\n");
  }

  @Override
  public void addShapeState(String name, IState stateToAdd, int timeOfState)
      throws IllegalArgumentException {
    log.append("ADDING STATE TO: ").append(name).append("\n")
        .append("at").append(timeOfState).append("\n\n");
  }

  @Override
  public void editShapeState(String name, int timeOfState, int r, int g, int b, int x, int y, int w,
      int h) throws IllegalArgumentException {
    log.append("EDITING STATE FOR SHAPE: ").append(name).append("\nAT TIME: ").append(timeOfState)
        .append("\n\n");
  }

  @Override
  public void addShapeMotion(String name, IState start, int startTime, IState end, int endTime) {
    // will not be called from controller do not need to test
  }

  @Override
  public void removeShape(String name) {
    log.append("REMOVING SHAPE: ").append(name).append("\n\n");
  }

  @Override
  public void removeShapeState(String name, int tickToRemove) {
    log.append("REMOVING STATE FROM SHAPE AT TICK: ").append(tickToRemove).append("\n\n");
  }

  @Override
  public void removeShapeMotion(String name, int startTime, int endTime)
      throws IllegalArgumentException {
    // will not be called from controller do not need to test
  }

  @Override
  public void setTopLeft(int x, int y) {
    log.append("Top left of canvas set as: (").append(x).append(",").append(y).append(")\n\n");
  }

  @Override
  public void setWidth(int width) {
    log.append("Width of canvas set as:").append(width).append("\n\n");
  }

  @Override
  public void setHeight(int height) {
    log.append("Height of canvas set as:").append(height).append("\n\n");
  }

  @Override
  public String toString() {
    return log.toString();
  }

  @Override
  public Map<String, IShape> getObjectsToAnimate() {
    Map<String, IShape> objs = new HashMap<>();
    objs.put("r", new RectangleShape());
    return objs;
  }

  @Override
  public int getWidth() {
    return 0;
  }

  @Override
  public int getHeight() {
    return 0;
  }

  @Override
  public int getTopLeftX() {
    return 0;
  }

  @Override
  public int getTopLeftY() {
    return 0;
  }
}
