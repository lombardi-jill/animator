package cs3500.animator.view.visual.popupwindow;

/**
 * An implementation of {@link IPopUpWindow} that provides a user with the ability to add a new
 * keyframe to an existing shape in the animation by entering in values for the shape's attributes
 * at that keyframe into {@link javax.swing.JTextField}'s.
 */
public class AddKeyframePopUp extends AbstractKeyframePopUp {

  /**
   * Constructs an instance of {@link AddKeyframePopUp} and adds a title to the {@link
   * javax.swing.JFrame}, text for the button on the window, and an action command for the button.
   */
  public AddKeyframePopUp() {
    super();
    this.setTitle("Add Keyframe!");
    this.springWindowButton.setText("Add Keyframe");
    this.springWindowButton.setActionCommand("add keyframe");
  }

  @Override
  public void populateValues(int tick, int red, int green, int blue, int width, int height, int x,
      int y) {
    throw new UnsupportedOperationException("Fields should be blank for user.");
  }

}
