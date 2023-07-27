package cs3500.animator.view;

import cs3500.animator.model.IAnimator;
import cs3500.animator.view.visual.IEditableView;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionListener;

/**
 * Represents a MockView in order to test that the controller calls view methods correctly.
 */
public class MockView implements IEditableView {

  StringBuilder appendable;

  public MockView(IAnimator animator, int i) {
    this.appendable = new StringBuilder();
  }

  @Override
  public void repaintAnimation() {
    appendable.append("repainted; ");
  }

  @Override
  public void addActionEventListeners(ActionListener actionListener) {
    appendable.append("added action listener; ");
  }

  @Override
  public void addChangeListener(ChangeListener changeListener) {
    appendable.append("added change listener; ");
  }

  @Override
  public void addListSelectionListener(ListSelectionListener listSelectionListener) {
    appendable.append("added list selection listener; ");
  }

  @Override
  public void pauseAnimation() {
    appendable.append("animation paused; ");
  }

  @Override
  public void resumeAnimation() {
    appendable.append("animation resumed; ");
  }

  @Override
  public void startAnimation() {
    appendable.append("animation started; ");
  }

  @Override
  public void restartAnimation() {
    appendable.append("animation restarted; ");
  }

  @Override
  public void loopAnimation() {
    appendable.append("animation looped; ");
  }

  @Override
  public void unLoopAnimation() {
    appendable.append("animation unlooped; ");
  }

  @Override
  public void updateSpeed() {
    appendable.append("animation speed handled; ");
  }

  @Override
  public String getShapeSelected() {
    appendable.append("r selected; ");
    return "r";
  }

  @Override
  public Integer getStateSelected() {
    appendable.append("state selected is 1; ");
    return 1;
  }

  @Override
  public void refreshShapeList() {
    appendable.append("shape list refreshed; ");
  }

  @Override
  public void refreshKeyFrameList() {
    appendable.append("key frame list refreshed; ");
  }

  @Override
  public Map<String, Integer> getEditedKeyframeValues() {
    appendable.append("edit key frame to: 1, 0, 0, 0, 0, 300, 10, 10; ");
    Map<String, Integer> states = new HashMap<>();
    states.put("tick", 1);
    states.put("red", 0);
    states.put("green", 0);
    states.put("blue", 0);
    states.put("x", 0);
    states.put("y", 300);
    states.put("width", 10);
    states.put("height", 10);

    return states;
  }

  @Override
  public Map<String, Integer> getAddedKeyFrameValues() {
    appendable.append("added key frame: 1, 255, 0, 0, 10, 30, 30, 30; ");
    Map<String, Integer> states = new HashMap<>();
    states.put("tick", 1);
    states.put("red", 255);
    states.put("green", 0);
    states.put("blue", 0);
    states.put("x", 10);
    states.put("y", 30);
    states.put("width", 30);
    states.put("height", 30);

    return states;
  }

  @Override
  public String[] getInfoOfShapeToAdd() {
    appendable.append("added rectangle named r; ");
    return new String[]{"rectangle", "r"};
  }

  @Override
  public void displayErrorMessage(String message) {
    appendable.append("error message displayed with message: ").append(message).append("; ");
  }

  @Override
  public void startView() {
    appendable.append("view started; ");
  }

  @Override
  public String toString() {
    return appendable.toString();
  }
}
