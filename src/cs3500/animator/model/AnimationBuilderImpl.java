package cs3500.animator.model;

import cs3500.animator.model.shape.EllipseShape;
import cs3500.animator.model.shape.IShape;
import cs3500.animator.model.shape.RectangleShape;
import cs3500.animator.model.state.IState;
import cs3500.animator.model.state.State;
import cs3500.animator.util.AnimationBuilder;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Builder class in order to create an instance of {@link Animator} with the desired
 * shapes and their respective states.
 */
public final class AnimationBuilderImpl implements AnimationBuilder<IAnimator> {

  private final IAnimator builder;
  private final Map<String, IShape> knownShapes;

  /**
   * Constructs an instance of {@link AnimationBuilderImpl} by creating a new {@link Animator}
   * object to fill with shapes and their states.
   */
  public AnimationBuilderImpl() {
    this.builder = new Animator();
    this.knownShapes = new HashMap<>();
    this.knownShapes.put("rectangle", new RectangleShape());
    this.knownShapes.put("ellipse", new EllipseShape());
  }

  @Override
  public IAnimator build() {
    return this.builder;
  }

  @Override
  public AnimationBuilder<IAnimator> setBounds(int x, int y, int width, int height) {
    this.builder.setTopLeft(x, y);
    this.builder.setWidth(width);
    this.builder.setHeight(height);

    return this;
  }

  @Override
  public AnimationBuilder<IAnimator> declareShape(String name, String type) {
    IShape toAdd = this.knownShapes.get(type);
    builder.addShape(toAdd, name);

    return this;
  }

  @Override
  public AnimationBuilder<IAnimator> addMotion(String name, int t1, int x1, int y1, int w1,
      int h1, int r1, int g1, int b1, int t2, int x2, int y2, int w2, int h2, int r2, int g2,
      int b2) {
    IState state1 = new State(r1, g1, b1, x1, y1, w1, h1);
    IState state2 = new State(r2, g2, b2, x2, y2, w2, h2);

    builder.addShapeMotion(name, state1, t1, state2, t2);

    return this;
  }

  @Override
  public AnimationBuilder<IAnimator> addKeyframe(String name, int t, int x, int y, int w, int h,
      int r, int g, int b) {
    IState state = new State(r, g, b, x, y, w, h);
    builder.addShapeState(name, state, t);

    return this;
  }
}
