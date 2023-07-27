package cs3500.animator.view.visual;

import cs3500.animator.viewmodel.IReadOnlyAnimator;
import cs3500.animator.view.IAnimationView;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.Timer;

/**
 * Represents a class to create a window to draw a visual animation in using the {@link javax.swing}
 * library.
 */
public class VisualViewImpl extends JFrame implements IAnimationView {

  /**
   * Creates an instance of {@link VisualViewImpl} for a visual representation of the animation by
   * creating a new {@link JFrame} containing a {@link JScrollPane} with a {@link
   * javax.swing.JPanel} that displays the animation.
   *
   * @param readOnlyAnimator an {@link IReadOnlyAnimator} that represents a read only version of the
   *                         model to animate to be used by the view
   * @param tickRate         an integer representing the number of ticks per second for the
   *                         animation
   */
  public VisualViewImpl(IReadOnlyAnimator readOnlyAnimator, int tickRate) {
    super();
    AnimationPanelImpl animation;
    Timer timer;
    JScrollPane scrollPane;

    this.setTitle("animation!");
    this.setPreferredSize(new Dimension(readOnlyAnimator.getWidth(), readOnlyAnimator.getHeight()));
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(true);

    this.setLayout(new BorderLayout());
    animation = new AnimationPanelImpl(readOnlyAnimator);
    animation.setPreferredSize(new Dimension(readOnlyAnimator.getWidth(),
        readOnlyAnimator.getHeight()));

    scrollPane = new JScrollPane(animation);
    this.add(scrollPane);

    timer = new Timer(1000 / tickRate, animation);
    timer.start();

    this.pack();
  }

  @Override
  public void startView() {
    this.setVisible(true);
  }
}
