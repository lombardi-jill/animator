package cs3500.animator.controller;

import cs3500.animator.model.IAnimator;
import cs3500.animator.view.IAnimationView;

/**
 * Represents an implementation of {@link IAnimationController} that takes in a {@link IAnimator}
 * and a {@link IAnimationView} -- views of this interface offer no capabilities for accepting user
 * inputs, so this controller does not deal with inputs or edit the underlying model. Its
 * implementation of {@code void startAnimation()} simply makes the view visible.
 */
public class UneditableControllerImpl implements IAnimationController {

  private final IAnimationView view;

  /**
   * Constructs an instance of a controller for an {@link IAnimationView}.
   *
   * @param view {@link IAnimationView} representing the view to construct this controller with.
   * @throws IllegalArgumentException if the given view is null
   */
  public UneditableControllerImpl(IAnimationView view) {
    if (view == null) {
      throw new IllegalArgumentException("View may not be null.");
    }
    this.view = view;
  }

  @Override
  public void startAnimation() {
    this.view.startView();
  }
}
