package cs3500.animator.view.visual;

import cs3500.animator.model.position.IPosition;
import cs3500.animator.model.state.IState;
import cs3500.animator.util.ErrorMessage;
import cs3500.animator.view.visual.popupwindow.AddKeyframePopUp;
import cs3500.animator.view.visual.popupwindow.EditKeyframePopUp;
import cs3500.animator.view.visual.popupwindow.IPopUpWindow;
import cs3500.animator.viewmodel.IReadOnlyAnimator;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionListener;

/**
 * Represents a GUI implementation of {@link IEditableView} where a user can directly interact with
 * a view by specifying inputs. This GUI is meant to interact with a controller that edits the
 * underlying model. This controller should call on public methods in order to represent these
 * changes visually in the {@link EditableViewImpl}. This view takes in a read only model so that it
 * cannot directly mutate the model it is animating, but can query information without going through
 * the controller to do so. For some basic functionalities that do not interact with the model, the
 * view implements them with private ActionListener classes. For most inputs, however, this view
 * should communicate with a controller that has added action listeners with {@code
 * addActionListener()}.
 */
public class EditableViewImpl extends JFrame implements IEditableView {

  private final IReadOnlyAnimator model;
  private final JButton pauseResume;
  private final JButton startRestart;
  private final JButton removeShapeButton;
  private final JButton removeStateButton;
  private final JButton addKeyframeButton;
  private final JButton editKeyframeButton;
  private final IPopUpWindow addKeyframeWindow;
  private final IPopUpWindow editKeyframeWindow;
  private final JCheckBox isLooped;
  private final JSlider speedControl;
  private final JButton addShapeButton;
  private final JTextField shapeName;
  private final JComboBox<String> shapeOptions;
  private final JList<String> shapesInAnimation;
  private final JList<String> shapeStateList;
  private final AnimationPanelImpl animationPanelImpl;
  private final Timer timer;
  private static final int TICK_MIN = 1;
  private static final int TICK_MAX = 100;
  private JLabel ticks;

  /**
   * Constructs a GUI representation of the given model using a {@link JFrame}. Uses a border layout
   * as its main layout. Initializes an animation panel in the center that visually displays the
   * animation as well as a control panel with several components to interact with this animation to
   * the right.
   *
   * @param model    represents the model to read information from and animate
   * @param tickRate represents the initial speed of the animation as number of ticks per second
   */
  public EditableViewImpl(IReadOnlyAnimator model, int tickRate) {
    super();
    this.model = model;

    this.setTitle("animation!");
    this.setPreferredSize(new Dimension(model.getWidth() + 275, model.getHeight()));
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(true);
    this.setLayout(new BorderLayout());

    this.animationPanelImpl = new AnimationPanelImpl(model);
    this.animationPanelImpl.setPreferredSize(new Dimension(model.getWidth(), model.getHeight()));
    JScrollPane animationScrollPane = new JScrollPane(animationPanelImpl);
    this.add(animationScrollPane, BorderLayout.CENTER);

    this.pauseResume = new JButton();
    this.startRestart = new JButton();
    this.isLooped = new JCheckBox("Looped Animation");
    this.speedControl = new JSlider(JSlider.HORIZONTAL, TICK_MIN, TICK_MAX, tickRate);
    String[] shapes = this.makeArray(this.model.getObjectsToAnimate().keySet());
    this.shapesInAnimation = new JList<>(shapes);
    this.removeShapeButton = new JButton();
    this.shapeStateList = new JList<>();
    this.removeStateButton = new JButton();
    this.addKeyframeButton = new JButton("Add Keyframe");
    this.editKeyframeButton = new JButton("Edit Keyframe");
    this.addKeyframeWindow = new AddKeyframePopUp();
    // adds an action to take upon closing the add key frame pop up window
    this.addKeyframeWindow.addButtonListener((e) -> {
      this.addKeyframeWindow.makeWindowVisible(false);
      this.addKeyframeWindow.clearText();
    });
    this.editKeyframeWindow = new EditKeyframePopUp();
    // adds an action to take upon closing the edit key frame pop up window
    this.editKeyframeWindow.addButtonListener((e) -> {
      this.editKeyframeWindow.makeWindowVisible(false);
      this.addKeyframeWindow.clearText();
    });
    this.addShapeButton = new JButton("Add Shape");
    this.shapeName = new JTextField(7);
    this.addShapeButton.addActionListener((e) -> this.shapeName.setText(""));
    this.shapeOptions = new JComboBox<>();
    JPanel rightPanel = this.initializeRightPanel();

    JScrollPane scrollButtons = new JScrollPane(rightPanel,
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    this.add(scrollButtons, BorderLayout.EAST);

    timer = new Timer(1000 / tickRate, animationPanelImpl);
    timer.addActionListener(new TimerListener());

    this.pack();
  }

  /**
   * Creates a String array from the given {@code Set<String>}.
   *
   * @param s the {@code Set<String>} to be converted into an array of Strings
   * @return a String[] that contains the same values as the given {@code Set<String>}
   */
  private String[] makeArray(Set<String> s) {
    String[] arr = new String[s.size()];
    int index = 0;
    for (String element : s) {
      arr[index++] = element;
    }
    return arr;
  }

  /**
   * Goes through all of the shapes in the animation and gets the biggest tick out of all of them.
   *
   * @return an integer representing the biggest tick in the animation
   */
  private int getMaxTick() {
    int maxTick = 0;
    for (String shape : model.getObjectsToAnimate().keySet()) {
      List<Integer> ticks = new ArrayList<>(
          model.getObjectsToAnimate().get(shape).getShapeStates().keySet());
      Collections.sort(ticks);
      if (ticks.size() != 0) {
        int tempMax = ticks.get(ticks.size() - 1);

        if (tempMax > maxTick) {
          maxTick = tempMax;
        }
      }
    }
    return maxTick;
  }

  /**
   * Creates a panel on the right side of the {@link JFrame} that will contain all of the objects
   * that the user can interact with.
   *
   * @return a {@link JPanel} that contains the components that the user will use to interact with
   *         the animation.
   */
  private JPanel initializeRightPanel() {
    JPanel tempPanel = new JPanel();
    tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.Y_AXIS));
    tempPanel.setPreferredSize(new Dimension(275, 800));

    // initializes the resume/pause and start/restart buttons
    JPanel controlPanel = initializeControlPanel();

    // initializes the current tick counter
    JPanel tickPanel = initializeTickPanel();

    JPanel topRightPanel = new JPanel();
    topRightPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    topRightPanel.setLayout(new BoxLayout(topRightPanel, BoxLayout.X_AXIS));
    topRightPanel.add(controlPanel);
    topRightPanel.add(Box.createRigidArea(new Dimension(20, 0)));
    topRightPanel.add(tickPanel);

    // panel with main control buttons and tick counter
    tempPanel.add(topRightPanel);

    // initializes check box to decide looping behavior
    initializeLoopBox();
    tempPanel.add(this.isLooped);
    tempPanel.add(Box.createRigidArea(new Dimension(0, 20)));

    // initializes a slider to control speed
    JPanel sliderPanel = initializeSlider();
    tempPanel.add(sliderPanel);
    tempPanel.add(Box.createRigidArea(new Dimension(0, 20)));

    // initializes a panel to create a JList of shapes in the animation and a JList with states
    // of the selected shapes
    JPanel listPanel = initializeListPanel();
    tempPanel.add(listPanel);
    tempPanel.add(Box.createRigidArea(new Dimension(0, 20)));

    // adds a panel for adding shapes to the animation
    JLabel addShapeLabel = new JLabel(
        "<html><center>To add a shape, select shape type and input a name.</center></html>");
    addShapeLabel.setHorizontalAlignment(JLabel.CENTER);
    tempPanel.add(addShapeLabel);
    JPanel addShapePanel = initializeAddShapePanel();
    addShapePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    tempPanel.add(addShapePanel);

    return tempPanel;
  }

  /**
   * Creates the panel that includes the start/restart button and the pause/resume button.
   *
   * @return {@link JPanel} with the buttons to start, restart, pause, and resume an animation
   */
  private JPanel initializeControlPanel() {
    JPanel tempControlPanel = new JPanel();
    tempControlPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    tempControlPanel.setLayout(new BoxLayout(tempControlPanel, BoxLayout.Y_AXIS));

    this.pauseResume.setText("pause");
    this.pauseResume.setActionCommand("pause");
    this.pauseResume.setAlignmentX(Component.LEFT_ALIGNMENT);
    tempControlPanel.add(this.pauseResume);

    this.startRestart.setText("start");
    this.startRestart.setActionCommand("start");
    this.startRestart.setAlignmentX(Component.LEFT_ALIGNMENT);
    tempControlPanel.add(this.startRestart);

    return tempControlPanel;
  }

  /**
   * Creates a panel that will display the tick that the animation is currently on.
   *
   * @return a {@link JPanel} that holds an {@link JLabel} that displays the current tick.
   */
  private JPanel initializeTickPanel() {
    JPanel tempTickPanel = new JPanel();
    tempTickPanel.setLayout(new BoxLayout(tempTickPanel, BoxLayout.Y_AXIS));
    JLabel tickLabel = new JLabel("   Current tick:   ");
    tickLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    this.ticks = new JLabel("0");
    this.ticks.setAlignmentX(Component.CENTER_ALIGNMENT);
    tempTickPanel.add(tickLabel);
    tempTickPanel.add(ticks);
    tempTickPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    return tempTickPanel;
  }

  /**
   * Initializes the {@link JCheckBox} for marking whether an animation is looped or not, by setting
   * its action command, setting it to be unchecked, setting the alignment, and adding an {@link
   * ActionListener}.
   */
  private void initializeLoopBox() {
    this.isLooped.setActionCommand("notLooped");
    this.isLooped.setSelected(false);
    this.isLooped.setAlignmentX(Component.LEFT_ALIGNMENT);
  }

  /**
   * Initializes a {@link JSlider} that is used to dynamically control the tick rate of the
   * animation and creates a panel containing that slider.
   *
   * @return a {@link JPanel} that contains a {@link JSlider} used to control the tick rate of the
   *         animation
   */
  private JPanel initializeSlider() {
    JPanel tempSliderPanel = new JPanel();
    tempSliderPanel.setLayout(new BoxLayout(tempSliderPanel, BoxLayout.PAGE_AXIS));
    JLabel tickRateLabel = new JLabel("    Slide to Change Tick Rate");
    tempSliderPanel.add(tickRateLabel);

    Dictionary<Integer, JLabel> labelTable = new Hashtable<>();
    labelTable.put(1, new JLabel("1"));
    labelTable.put(25, new JLabel("25"));
    labelTable.put(50, new JLabel("50"));
    labelTable.put(75, new JLabel("75"));
    labelTable.put(100, new JLabel("100"));
    this.speedControl.setLabelTable(labelTable);
    this.speedControl.setMinimum(0);
    this.speedControl.setMinorTickSpacing(5);
    this.speedControl.setPaintTicks(true);
    this.speedControl.setPaintLabels(true);
    this.speedControl.setAlignmentX(Component.LEFT_ALIGNMENT);
    tempSliderPanel.add(this.speedControl);
    return tempSliderPanel;
  }

  /**
   * Creates a panel that contains a {@link JList} of shapes in the animation and a {@link JList} of
   * states for a selected shape as well as {@link JButton}'s for deleting a shape, adding a
   * keyframe, deleting a keyframe, or editing a keyframe.
   *
   * @return a {@link JPanel} containing components to view and interact with the shapes and states
   *         that an animation contains.
   */
  private JPanel initializeListPanel() {
    JPanel tempRemovePanel = new JPanel();
    tempRemovePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

    // adds label describing JList of shapes
    JLabel removeShapeLabel = new JLabel("Choose a shape in the animation");
    tempRemovePanel.add(removeShapeLabel);

    this.removeShapeButton.setText("Remove Shape");
    this.removeShapeButton.setActionCommand("remove shape");
    this.addKeyframeButton.setActionCommand("add keyframe");
    this.addKeyframeButton.addActionListener(new AddKeyFrameListener());

    // creates and adds panel for buttons to remove shapes and add keyframes to shapes
    JPanel shapeButtons = new JPanel();
    shapeButtons.setLayout(new BoxLayout(shapeButtons, BoxLayout.X_AXIS));
    shapeButtons.add(this.removeShapeButton);
    shapeButtons.add(this.addKeyframeButton);

    this.shapesInAnimation.setLayoutOrientation(JList.VERTICAL);
    this.shapesInAnimation.setVisibleRowCount(7);
    this.shapesInAnimation.setFixedCellHeight(15);
    this.shapesInAnimation.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    // wraps JList in scrollPane
    JScrollPane shapeScrollPane = new JScrollPane(this.shapesInAnimation);
    shapeScrollPane.setPreferredSize(new Dimension(250, 105));
    tempRemovePanel.add(shapeScrollPane);
    tempRemovePanel.add(shapeButtons);

    // adds a label describing JList of states
    JLabel removeStateLabel = new JLabel("Choose a keyframe for the shape");
    tempRemovePanel.add(removeStateLabel);

    this.removeStateButton.setText("Remove Keyframe");
    this.removeStateButton.setActionCommand("remove keyframe");
    this.editKeyframeButton.setActionCommand("edit keyframe");
    this.editKeyframeButton.addActionListener(new EditKeyFrameListener());

    // creates and adds panel for buttons to remove keyframes and edit keyframes of shapes
    JPanel stateButtons = new JPanel();
    stateButtons.setLayout(new BoxLayout(stateButtons, BoxLayout.X_AXIS));
    stateButtons.add(this.removeStateButton);
    stateButtons.add(this.editKeyframeButton);

    this.shapeStateList.setLayoutOrientation(JList.VERTICAL);
    this.shapeStateList.setVisibleRowCount(7);
    this.shapeStateList.setFixedCellHeight(15);
    this.shapeStateList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    // wraps JList in scrollPane
    JScrollPane statesScrollPane = new JScrollPane(this.shapeStateList);
    statesScrollPane.setPreferredSize(new Dimension(250, 105));
    tempRemovePanel.add(statesScrollPane);
    tempRemovePanel.add(stateButtons);

    return tempRemovePanel;
  }

  /**
   * Creates a panel for adding a shape to an animation by adding a {@link JComboBox} for selecting
   * the type of shape to add and a {@link JTextField} for giving the name of the new shape added.
   *
   * @return a {@link JPanel} that contains the components used to add a new shape to an animation
   */
  private JPanel initializeAddShapePanel() {
    JPanel tempPanel = new JPanel();
    tempPanel.setLayout(new FlowLayout());
    tempPanel.setPreferredSize(new Dimension(250, 15));
    this.shapeOptions.addItem("rectangle");
    this.shapeOptions.addItem("ellipse");
    tempPanel.add(this.shapeOptions);
    JLabel name = new JLabel("Name:");
    tempPanel.add(name);
    name.setLabelFor(this.shapeName);
    tempPanel.add(this.shapeName);
    this.addShapeButton.setActionCommand("add shape");
    tempPanel.add(this.addShapeButton);
    return tempPanel;
  }

  @Override
  public String getShapeSelected() {
    return this.shapesInAnimation.getSelectedValue();
  }

  @Override
  public Integer getStateSelected() {
    String shapeSelected = this.shapesInAnimation.getSelectedValue();
    int indexOfState = this.shapeStateList.getSelectedIndex();
    if (indexOfState == -1) {
      return null;
    }
    List<Integer> ticks = new ArrayList<>(
        model.getObjectsToAnimate().get(shapeSelected).getShapeStates().keySet());
    Collections.sort(ticks);
    return ticks.get(indexOfState);
  }

  @Override
  public void refreshShapeList() {
    Set<String> s = this.model.getObjectsToAnimate().keySet();
    String[] shapes = makeArray(s);
    this.shapesInAnimation.setListData(shapes);
  }

  @Override
  public Map<String, Integer> getEditedKeyframeValues() throws IllegalArgumentException {
    return this.editKeyframeWindow.getFieldValues();
  }

  @Override
  public Map<String, Integer> getAddedKeyFrameValues() throws IllegalArgumentException {
    return this.addKeyframeWindow.getFieldValues();
  }

  @Override
  public String[] getInfoOfShapeToAdd() {
    String[] info = new String[2];
    info[0] = (String) this.shapeOptions.getSelectedItem();
    info[1] = this.shapeName.getText();
    return info;
  }

  @Override
  public void repaintAnimation() {
    this.repaint();
  }

  @Override
  public void startView() {
    this.setVisible(true);
  }

  @Override
  public void pauseAnimation() {
    if (animationPanelImpl.getCurrTick() > 0) {
      pauseResume.setText("resume");
      pauseResume.setActionCommand("resume");
      timer.stop();
    }
  }

  @Override
  public void resumeAnimation() {
    if (animationPanelImpl.getCurrTick() > 0) {
      pauseResume.setText("pause");
      pauseResume.setActionCommand("pause");
      timer.start();
    }
  }

  @Override
  public void startAnimation() {
    startRestart.setText("restart");
    startRestart.setActionCommand("restart");
    timer.start();
  }

  @Override
  public void restartAnimation() {
    startRestart.setText("start");
    startRestart.setActionCommand("start");
    pauseResume.setText("pause");
    pauseResume.setActionCommand("pause");
    animationPanelImpl.resetTick();
    ticks.setText(String.valueOf(animationPanelImpl.getCurrTick()));
    timer.restart();
    animationPanelImpl.repaint();
    timer.stop();
  }

  @Override
  public void addActionEventListeners(ActionListener actionListener) {
    removeShapeButton.addActionListener(actionListener);
    removeStateButton.addActionListener(actionListener);
    addKeyframeWindow.addButtonListener(actionListener);
    editKeyframeWindow.addButtonListener(actionListener);
    pauseResume.addActionListener(actionListener);
    startRestart.addActionListener(actionListener);
    isLooped.addActionListener(actionListener);
    addShapeButton.addActionListener(actionListener);
  }

  @Override
  public void addChangeListener(ChangeListener changeListener) {
    speedControl.addChangeListener(changeListener);
  }

  @Override
  public void addListSelectionListener(ListSelectionListener listSelectionListener) {
    shapesInAnimation.addListSelectionListener(listSelectionListener);
  }

  @Override
  public void loopAnimation() {
    if (animationPanelImpl.getCurrTick() > getMaxTick() && pauseResume.getActionCommand()
        .equals("resume")) {
      pauseResume.setText("pause");
      pauseResume.setActionCommand("pause");
      startRestart.setText("start");
      startRestart.setActionCommand("start");
      animationPanelImpl.resetTick();
      ticks.setText(String.valueOf(animationPanelImpl.getCurrTick()));
      timer.restart();
      animationPanelImpl.repaint();
      timer.stop();
    }
    isLooped.setSelected(true);
    isLooped.setActionCommand("notLooped");
  }

  @Override
  public void unLoopAnimation() {
    isLooped.setSelected(false);
    isLooped.setActionCommand("looped");
  }

  @Override
  public void updateSpeed() {
    int speed = speedControl.getValue();
    if (speed == 0) {
      speed = 1;
    }
    timer.setDelay(1000 / speed);
  }

  @Override
  public void refreshKeyFrameList() {
    String shapeSelected = shapesInAnimation.getSelectedValue();
    animationPanelImpl.setHighlightedShape(shapeSelected);
    animationPanelImpl.repaint();
    Map<Integer, IState> states;
    try {
      states = model.getObjectsToAnimate().get(shapeSelected)
          .getShapeStates();
    } catch (NullPointerException npe) {
      String[] mt = new String[]{};
      shapeStateList.setListData(mt);
      return;
    }
    List<Integer> ticks = new ArrayList<>(states.keySet());
    Collections.sort(ticks);

    String[] toDisplay = new String[ticks.size()];
    int i = 0;
    for (int tick : ticks) {
      StringBuilder sb = new StringBuilder();
      sb.append("Tick: ");
      sb.append(tick);
      sb.append(" | Color: ");
      int[] rgb = states.get(tick).getRGB();
      sb.append("rgb(").append(rgb[0]).append(",").append(rgb[1]).append(",").append(rgb[2])
          .append(") ");
      sb.append("| Pos: ");
      IPosition pos = states.get(tick).getPosition();
      sb.append("(").append(pos.getX()).append(",").append(pos.getY()).append(") ");
      sb.append("| Dim: ");
      int[] wh = states.get(tick).getWidthAndHeight();
      sb.append("(").append(wh[0]).append(",").append(wh[1]).append(")");
      toDisplay[i] = sb.toString();
      i++;
    }
    shapeStateList.setListData(toDisplay);
  }

  @Override
  public void displayErrorMessage(String message) {
    new ErrorMessage(message);
  }

  /**
   * Represents a class that implements {@link ActionListener} and listens to the edit key frame
   * button.
   */
  private final class EditKeyFrameListener implements ActionListener {

    /**
     * Launches a pop up window by editing the {@link IPopUpWindow} editKeyFrameWindow.
     *
     * @param e represents the {@link ActionEvent} to respond to
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      Integer tick = getStateSelected();
      if (tick == null) {
        new ErrorMessage("No shape to add keyframe to selected");
        return;
      }
      String shape = getShapeSelected();
      IState stateSelected = model.getObjectsToAnimate().get(shape).getShapeStates().get(tick);
      int[] rgb = stateSelected.getRGB();
      int[] wh = stateSelected.getWidthAndHeight();
      IPosition pos = stateSelected.getPosition();

      editKeyframeWindow
          .populateValues(tick, rgb[0], rgb[1], rgb[2], wh[0], wh[1], pos.getX(), pos.getY());
      editKeyframeWindow.makeWindowVisible(true);
    }
  }

  /**
   * Represents a class that implements {@link ActionListener} and listens to the add key frame
   * button.
   */
  private final class AddKeyFrameListener implements ActionListener {

    /**
     * Launches a pop up window by editing the {@link IPopUpWindow} addKeyFrameWindow.
     *
     * @param e represents the {@link ActionEvent} to respond to
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      if (getShapeSelected() == null) {
        new ErrorMessage("No shape selected to add keyframe to.");
        return;
      }
      addKeyframeWindow.makeWindowVisible(true);
    }
  }

  /**
   * Represents a class that implements {@link ActionListener} and listens to a {@link Timer} for
   * creating a text box containing the current tick. Also decides when to loop the animation by
   * listening to the timer.
   */
  private final class TimerListener implements ActionListener {

    /**
     * Listens to the {@link Timer} in order to loop and display the current tick.
     *
     * @param e represents the {@link ActionEvent} to respond to
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      int maxTick = getMaxTick();
      int currTick = animationPanelImpl.getCurrTick();
      ticks.setText(String.valueOf(currTick));
      if (currTick > maxTick && isLooped.isSelected()) {
        animationPanelImpl.resetTick();
        ticks.setText(String.valueOf(animationPanelImpl.getCurrTick()));
      }
    }
  }
}
