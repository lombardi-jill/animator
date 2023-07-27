package cs3500.animator.controller;

import cs3500.animator.controller.commands.AddShape;
import cs3500.animator.controller.commands.AddState;
import cs3500.animator.controller.commands.EditState;
import cs3500.animator.controller.commands.LoopAnimation;
import cs3500.animator.controller.commands.PauseAnimation;
import cs3500.animator.controller.commands.RemoveShape;
import cs3500.animator.controller.commands.RemoveState;
import cs3500.animator.controller.commands.RestartAnimation;
import cs3500.animator.controller.commands.ResumeAnimation;
import cs3500.animator.controller.commands.StartAnimation;
import cs3500.animator.controller.commands.UnLoopAnimation;
import cs3500.animator.model.IAnimator;
import cs3500.animator.view.visual.IEditableView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Represents an implementation of {@link IAnimationController} that deals with handling user inputs
 * from a {@link IEditableView} view that can change the underlying {@link IAnimator} model. In
 * order to handle user inputs, the controller adds itself as listener to view components -- due to
 * this, the class also implements the {@link ActionListener}, {@link ChangeListener}, and {@link
 * ListSelectionListener} interfaces. In order to deal with specific action events, the command
 * pattern of function objects is used. Ensures that all changes made are subsequently reflected in
 * the view and model appropriately.
 */
public class EditableControllerImpl implements IAnimationController, ActionListener,
    ChangeListener, ListSelectionListener {

  private final IAnimator model;
  private final IEditableView view;
  private final Map<String, Runnable> actionListenerCommands;

  /**
   * Constructs an {@link EditableControllerImpl} with the given {@link IAnimator} and {@link
   * IEditableView} objects in order to communicate with both the model and the view. Then, it
   * initializes a {@code Map<String, Runnable>} to represent actions to take based on action
   * commands received.
   *
   * @param model {@link IAnimator} object representing the model for the controller to communicate
   *              with
   * @param view  {@link IEditableView} object representing the view the controller receives
   *              commands from
   * @throws IllegalArgumentException if the given model or view is null
   */
  public EditableControllerImpl(IAnimator model, IEditableView view) {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Model or view is null.");
    }
    this.model = model;
    this.view = view;
    this.actionListenerCommands = this.initializeCommand();
  }

  /**
   * Initializes a map of commands by mapping strings of action commands to {@link Runnable} objects
   * that reflect the desired changes in the view and model.
   *
   * @return a {@code Map<String, Runnable>} representing the commands the controller handles
   */
  private Map<String, Runnable> initializeCommand() {
    Map<String, Runnable> cmds = new HashMap<>();
    cmds.put("remove shape", new RemoveShape(model, view));
    cmds.put("remove keyframe", new RemoveState(model, view));
    cmds.put("add shape", new AddShape(model, view));
    cmds.put("add keyframe", new AddState(model, view));
    cmds.put("edit keyframe", new EditState(model, view));
    cmds.put("pause", new PauseAnimation(view));
    cmds.put("resume", new ResumeAnimation(view));
    cmds.put("start", new StartAnimation(view));
    cmds.put("restart", new RestartAnimation(view));
    cmds.put("looped", new LoopAnimation(view));
    cmds.put("notLooped", new UnLoopAnimation(view));
    return cmds;
  }

  /**
   * Adds itself as an {@link ActionListener}, {@link ChangeListener}, and {@link
   * ListSelectionListener} to the {@link IEditableView} object. Then makes the view visible.
   */
  @Override
  public void startAnimation() {
    this.view.addActionEventListeners(this);
    this.view.addChangeListener(this);
    this.view.addListSelectionListener(this);
    this.view.startView();
  }

  /**
   * Handles action events received from the view by getting the correct {@link Runnable} object
   * from {@code actionListenerCommands}.
   *
   * @param e representing the {@link ActionEvent} received by the controller
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    String action = e.getActionCommand();
    Runnable cmd;
    try {
      cmd = actionListenerCommands.get(action);
    } catch (NullPointerException npe) {
      this.view.displayErrorMessage("Not a valid command");
      return;
    }
    cmd.run();
  }

  /**
   * Handles state change events from the view.
   *
   * @param e represents the {@link ChangeEvent} received by the controller
   */
  @Override
  public void stateChanged(ChangeEvent e) {
    this.view.updateSpeed();
  }

  /**
   * Handles the list selection events from the view.
   *
   * @param e represents the {@link ListSelectionEvent} received by the controller.
   */
  @Override
  public void valueChanged(ListSelectionEvent e) {
    this.view.refreshKeyFrameList();
  }
}
