package cs3500.animator.view.visual.popupwindow;

/**
 * An implementation of {@link IPopUpWindow} that provides a user with the ability to edit an
 * existing keyframe for an existing shape in the animation by entering in values for the shape's
 * attributes at that keyframe into {@link javax.swing.JTextField}'s.
 */
public class EditKeyframePopUp extends AbstractKeyframePopUp {

  /**
   * Constructs an instance of {@link EditKeyframePopUp} and adds a title to the {@link
   * javax.swing.JFrame}, text for the button on the window, and an action command for the button.
   */
  public EditKeyframePopUp() {
    super();
    this.setTitle("Edit Keyframe");
    this.springWindowButton.setText("Edit Keyframe");
    this.springWindowButton.setActionCommand("edit keyframe");
  }

  @Override
  public void populateValues(int tick, int red, int green, int blue, int width, int height, int x,
      int y) {
    this.tick.setText(String.valueOf(tick));
    this.tick.setEditable(false);
    this.red.setText(String.valueOf(red));
    this.green.setText(String.valueOf(green));
    this.blue.setText(String.valueOf(blue));
    this.width.setText(String.valueOf(width));
    this.height.setText(String.valueOf(height));
    this.x.setText(String.valueOf(x));
    this.y.setText(String.valueOf(y));
  }

}
