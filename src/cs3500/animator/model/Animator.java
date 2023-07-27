package cs3500.animator.model;

import cs3500.animator.model.shape.IShape;
import cs3500.animator.model.state.IState;
import cs3500.animator.model.state.State;
import cs3500.animator.viewmodel.IReadOnlyAnimator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * An implementation of an {@link IAnimator}.
 */
public class Animator implements IAnimator, IReadOnlyAnimator {

  private final Map<String, IShape> objectsToAnimate;
  //Sets the canvas values to default values in case they are never specified
  private int x = 0;
  private int y = 0;
  private int w = 500;
  private int h = 500;


  /**
   * Constructs an instance of {@link Animator} initialized with an empty map of {@link IShape}
   * objects mapped to their names.
   */
  public Animator() {
    this.objectsToAnimate = new LinkedHashMap<>();
  }

  @Override
  public void addShape(IShape shape, String name) throws IllegalArgumentException {
    if (this.objectsToAnimate.containsKey(name)) {
      throw new IllegalArgumentException("There already exists a shape with name: " + name);
    }
    this.objectsToAnimate.put(name, shape.getShape());
  }

  @Override
  public void addShapeState(String name, IState stateToAdd, int timeOfState)
      throws IllegalArgumentException {
    if (!this.objectsToAnimate.containsKey(name)) {
      throw new IllegalArgumentException(
          "Animator does not contain shape with name: " + name);
    }
    this.objectsToAnimate.get(name).addState(timeOfState, stateToAdd);
  }

  @Override
  public void editShapeState(String name, int timeOfState, int r, int g, int b, int x, int y,
      int w, int h) throws IllegalArgumentException {
    if (!this.objectsToAnimate.containsKey(name)) {
      throw new IllegalArgumentException(
          "Animator does not contain shape with name: " + name);
    }
    IState stateToAdd = new State(r, g, b, x, y, w, h);
    this.objectsToAnimate.get(name).editState(timeOfState, stateToAdd);
  }

  @Override
  public void addShapeMotion(String name, IState start, int startTime, IState end, int endTime)
      throws IllegalArgumentException {
    if (!this.objectsToAnimate.containsKey(name)) {
      throw new IllegalArgumentException(
          "Animator does not contain shape with name: " + name);
    }
    this.objectsToAnimate.get(name).addMotion(start, startTime, end, endTime);
  }

  @Override
  public void removeShape(String name) throws IllegalArgumentException {
    if (!this.objectsToAnimate.containsKey(name)) {
      throw new IllegalArgumentException("Animator does not contain shape with name: " + name);
    }
    this.objectsToAnimate.remove(name);
  }

  @Override
  public void removeShapeState(String name, int tickToRemove) throws IllegalArgumentException {
    if (!this.objectsToAnimate.containsKey(name)) {
      throw new IllegalArgumentException(
          "Animator does not contain shape with name: " + name);
    }
    this.objectsToAnimate.get(name).removeState(tickToRemove);
  }

  @Override
  public Map<String, IShape> getObjectsToAnimate() {
    Map<String, IShape> copyOfObjects = new LinkedHashMap<>();
    for (String key : this.objectsToAnimate.keySet()) {
      IShape shapeCopy = this.objectsToAnimate.get(key).getShape();
      copyOfObjects.put(key, shapeCopy);
    }
    return copyOfObjects;
  }

  @Override
  public void removeShapeMotion(String name, int startTime, int endTime)
      throws IllegalArgumentException {
    if (!this.objectsToAnimate.containsKey(name)) {
      throw new IllegalArgumentException(
          "Animator does not contain shape with name: " + name);
    }
    this.objectsToAnimate.get(name).removeMotion(startTime, endTime);
  }

  @Override
  public void setTopLeft(int x, int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public void setWidth(int width) {
    this.w = width;
  }

  @Override
  public void setHeight(int height) {
    this.h = height;
  }

  @Override
  public int getWidth() {
    return this.w;
  }

  @Override
  public int getHeight() {
    return this.h;
  }

  @Override
  public int getTopLeftX() {
    return this.x;
  }

  @Override
  public int getTopLeftY() {
    return this.y;
  }

  /**
   * Returns an {@link AnimationBuilderImpl} object in order to create an instance of this class
   * using the {@link AnimationBuilderImpl}'s methods.
   *
   * @return a {@link AnimationBuilderImpl} for initializing an instance of this Animator.
   */
  public static AnimationBuilderImpl builder() {
    return new AnimationBuilderImpl();
  }


}
