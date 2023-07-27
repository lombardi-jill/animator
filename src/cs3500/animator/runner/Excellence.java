package cs3500.animator.runner;

import cs3500.animator.controller.EditableControllerImpl;
import cs3500.animator.controller.IAnimationController;
import cs3500.animator.controller.UneditableControllerImpl;
import cs3500.animator.model.Animator;
import cs3500.animator.model.IAnimator;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.util.ErrorMessage;
import cs3500.animator.view.IAnimationView;
import cs3500.animator.view.nongui.SVGViewImpl;
import cs3500.animator.view.nongui.TextViewImpl;
import cs3500.animator.view.visual.VisualViewImpl;
import cs3500.animator.view.visual.EditableViewImpl;
import cs3500.animator.viewmodel.IReadOnlyAnimator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a class to initialize and run an animation with either a visual view, textual view, or
 * SVG view where the output has proper SVG format.
 */
public final class Excellence {

  /**
   * Runs an animation given command line arguments. The command line arguments include:
   * <ul>
   *   <li>"-in" followed by the input filepath</li>
   *   <li>"-view" followed by one of:/li>
   *     <ul>
   *       <li>"text"</li>
   *       <li>"visual"</li>
   *       <li>"svg"</li>
   *     </ul>
   *   <Li>"-speed" followed by the ticks per second as an integer</Li>
   *   <li>"-out" followed by an output filepath</li>
   * </ul>
   * <p>The "-in" and "-view" commands must be specified, otherwise an error will be thrown. If
   * "-speed" is not specified the default rate is 1 tick per second. If "-out" is not specified
   * the default is {@code System.out}. Invalid arguments also result in an error.</p>
   *
   * @param args an array of Strings representing the command line arguments passed by the user.
   */
  public static void main(String[] args) {
    IAnimator model = null;
    int tickRate = 1;
    Appendable out = System.out;
    Map<String, String> userInputs = new HashMap<>();

    try {
      for (int i = 0; i < args.length; i += 2) {

        userInputs.put(args[i], args[i + 1]);
      }
    } catch (IndexOutOfBoundsException | NullPointerException e) {
      new ErrorMessage("Invalid Arguments given. Valid command line arguments are:"
          + "\n• -in followed by input filepath"
          + "\n• -view followed by:"
          + "\n\t\t\t\t\t\t\t\t• text"
          + "\n\t\t\t\t\t\t\t\t• visual"
          + "\n\t\t\t\t\t\t\t\t• svg"
          + "\n• -speed followed by tick rate(as an integer)."
          + "\n• -out followed by output filepath.");
      System.exit(1);
    }

    String input = userInputs.getOrDefault("-in", "");
    try {
      model = (AnimationReader.parseFile(new FileReader(new File(input)), Animator.builder()));
    } catch (FileNotFoundException e) {
      new ErrorMessage("Unable to read in file.");
      System.exit(1);
    }
    String tickRateStr = userInputs.getOrDefault("-speed", "1");
    try {
      tickRate = Integer.parseInt(tickRateStr);
    } catch (NumberFormatException nfe) {
      new ErrorMessage("Speed must be an integer.");
      System.exit(1);
    }
    String outputPath = userInputs.getOrDefault("-out", null);
    if (outputPath != null) {
      try {
        out = new PrintStream(new File(outputPath));
      } catch (FileNotFoundException e) {
        new ErrorMessage("Unable to output to file.");
        System.exit(1);
      }
    }
    String viewStr = userInputs.getOrDefault("-view", null);

    IAnimationController controller;
    IAnimationView view;
    IReadOnlyAnimator modelReadOnly = model;

    switch (viewStr) {
      case "visual":
        view = new VisualViewImpl(modelReadOnly, tickRate);
        controller = new UneditableControllerImpl(view);
        break;
      case "text":
        view = new TextViewImpl(out, modelReadOnly);
        controller = new UneditableControllerImpl(view);
        break;
      case "svg":
        view = new SVGViewImpl(out, modelReadOnly, tickRate);
        controller = new UneditableControllerImpl(view);
        break;
      case "edit":
        controller = new EditableControllerImpl(model,
            new EditableViewImpl(modelReadOnly, tickRate));
        break;
      default:
        new ErrorMessage("View type and input source must be specified.");
        System.exit(1);
        return;
    }

    controller.startAnimation();
  }
}