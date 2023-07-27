package cs3500.animator.util;

import cs3500.animator.runner.Excellence;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Represents an interactive panel for giving the user error encountered in the {@link Excellence}
 * main method.
 */
public class ErrorMessage {

  /**
   * Constructs an instance of {@link ErrorMessage} which displays a visual panel using {@link
   * javax.swing.JOptionPane} with the given message.
   *
   * @param message represents the String message to display.
   */
  public ErrorMessage(String message) {
    JFrame messageFrame;
    messageFrame = new JFrame();
    JOptionPane.showMessageDialog(messageFrame, message);
  }
}
