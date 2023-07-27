package cs3500.animator.view.visual.popupwindow;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 * An abstract class that contains the common functionality between the {@link JFrame} that is for
 * adding a keyframe to a shape and the {@link JFrame} that is for editing a shape's existing
 * keyframe.
 */
abstract class AbstractKeyframePopUp extends JFrame implements IPopUpWindow {

  protected final JButton springWindowButton;
  protected final JTextField tick;
  protected final JTextField red;
  protected final JTextField green;
  protected final JTextField blue;
  protected final JTextField width;
  protected final JTextField height;
  protected final JTextField x;
  protected final JTextField y;

  /**
   * Constructs an instance of {@link AbstractKeyframePopUp} by creating an {@link JPanel} and
   * adding individual {@link JTextField}'s for each attribute of a keyframe which are the tick, red
   * value, green value, blue value, width, height, x and y value. Finally, it adds this {@link
   * JPanel} to a {@link JFrame}.
   */
  protected AbstractKeyframePopUp() {
    super();

    this.setPreferredSize(new Dimension(410, 120));
    this.setLayout(new SpringLayout());
    JPanel p = new JPanel();
    p.setLayout(new GridLayout(2, 8, 5, 5));
    String[] labels = new String[]{"Tick", "Red", "Green", "Blue", "Width", "Height", "X-pos",
        "Y-pos"};

    this.tick = new JTextField(3);
    this.red = new JTextField(3);
    this.green = new JTextField(3);
    this.blue = new JTextField(3);
    this.width = new JTextField(3);
    this.height = new JTextField(3);
    this.x = new JTextField(3);
    this.y = new JTextField(3);

    JTextField[] fields = new JTextField[]{this.tick, this.red, this.green, this.blue, this.width,
        this.height, this.x, this.y};

    for (int i = 0; i < labels.length; i++) {
      JLabel l = new JLabel(labels[i]);
      l.setHorizontalAlignment(JLabel.CENTER);
      p.add(l);
      fields[i].setHorizontalAlignment(JTextField.CENTER);
      l.setLabelFor(fields[i]);
      p.add(fields[i]);
    }

    JPanel temp = new JPanel();
    temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));
    this.springWindowButton = new JButton();
    this.springWindowButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    temp.add(p);
    temp.add(Box.createRigidArea(new Dimension(0, 10)));
    temp.add(this.springWindowButton);

    this.add(temp);
    this.setVisible(false);
  }

  @Override
  public Map<String, Integer> getFieldValues() throws IllegalArgumentException {
    Map<String, Integer> fieldVals = new HashMap<>();
    try {
      fieldVals.put("tick", Integer.parseInt(this.tick.getText()));
      fieldVals.put("red", Integer.parseInt(this.red.getText()));
      fieldVals.put("green", Integer.parseInt(this.green.getText()));
      fieldVals.put("blue", Integer.parseInt(this.blue.getText()));
      fieldVals.put("width", Integer.parseInt(this.width.getText()));
      fieldVals.put("height", Integer.parseInt(this.height.getText()));
      fieldVals.put("x", Integer.parseInt(this.x.getText()));
      fieldVals.put("y", Integer.parseInt(this.y.getText()));
    } catch (NumberFormatException nfe) {
      throw new IllegalArgumentException("Values entered must be integers.");
    }
    return fieldVals;
  }

  @Override
  public void addButtonListener(ActionListener l) {
    this.springWindowButton.addActionListener(l);
  }

  @Override
  public void makeWindowVisible(boolean visible) {
    this.setVisible(visible);
    this.pack();
  }

  @Override
  public abstract void populateValues(int tick, int red, int green, int blue, int width, int height,
      int x, int y);

  @Override
  public void clearText() {
    this.tick.setText("");
    this.red.setText("");
    this.green.setText("");
    this.blue.setText("");
    this.width.setText("");
    this.height.setText("");
    this.x.setText("");
    this.y.setText("");
  }
}
