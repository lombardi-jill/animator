This file talks about the different interfaces, classes, and methods that we chose to
include in our cs3500.animator.model.Animator program and why we chose to make these design decisions.

---------------------------------
Note on Changes from Assignment 6
---------------------------------
- Added one method to the IAnimator model. This method dealt with editing key frames. Our previous method of
adding keyframes, does not allow a user to input a keyframe at a tick that was already specified. This
new method gave us the ability to edit an existing state for a shape in the animation.
- Added an edit state method to IShape to support editing key frames from the animator.
- Added a field to animation panel that kept track of the current shape's name that is selected in the GUI in
order to highlight it.
- Edited the paint component method to flag if a shape needs to be highlighted. This flag is used in
the commands for drawing shapes as a parameter in the method call.
- For both classes, DrawRectangle and DrawEllipse we added an if statement to recognize the flag parameter
and draw the shape with a border or "highlight" around it.
- Added three public methods to Animation panel: setHighlightedShape(), getCurrTick(), and resetTick();
This was done in order to deal with highlighting shapes, as well as give our main animation class the
ability to set and read the current tick of the animation.
- Added an interface that AnimationPanel implements to put these public methods into.
- Added a switch statement in the Main class for dealing with the new view type.
- Refactored our Error Message code into our util package in order to call it from our editable view
when bad user inputs are given.

---------------------------------
Notes on Edit View Implementation
---------------------------------
Our new view interface extends from our old one. It adds methods for the following functionalities:
pause, resume, start, restart, loop, unloop, change speed, add keyframe, edit keyframe,
delete keyframe, add shape, and delete shape.

Our implementation of this interface:
- Is built with java swing and uses our previous implementation of AnimationPanel to show the
animation.
- Takes in a IReadOnlyAnimator so that it cannot directly mutate the model it is animating,
but can query information without going through the controller
- Implements some low-level button listening functionalities that do not edit the model.
- Has public methods so that a controller can add itself as a listener and edit the model.
- Initializes an animation panel in the center that visually displays the animation. Also visualizes
a control panel to the right with necessary components to interact with the animation.
- Utilizes pop-up windows for dealing with entering new keyframes or editing existing ones

-----------------------------
Notes on IAnimationController
-----------------------------
Our controller interface has one public method that starts the animation. It is implemented by
UneditableControllerImpl that does not take in a model and controls the view without editing the model.
It is also implemented by EditableControllerImpl that takes in a model to edit and a view to reflect
these changes on.

For our EditableControllerImpl:
- We have the class implement ActionListener, ChangeListener, and ListSelectionListener
- When startAnimation() is called, the view is made visible and the controller adds itself as a listener
in order to respond to action events, change events, and list selection events from the GUI and
update the model if appropriate.
- We deal with ActionEvents by initializing a map of action commands as strings mapped to Runnable
function objects that respond to user inputs, update the model when appropriate, and reflect changes in
the view.



-----------------------
Notes from Assignment 6
-----------------------
---------------------------------
Note on Changes from Assignment 5
---------------------------------
- Refactored code from IAnimation about printing model information into the view implementation for text.
- Added setters to the IAnimation interface to let a user specify the size of the canvas
they desire and the location of the origin.
- Added getters to the IAnimation interface so our view could get necessary information about the canvas
- Refactored part of our IAnimation interface into IReadOnlyAnimator to use the model-view pattern
and having our view interact with an immutable object of the model.
- Added methods for adding a motion to our IAnimation object
- Added methods for removing states from our IAnimation object
- Added methods for removing motions from our IAnimation object

-----------------------------
Notes on View Implementations
-----------------------------
All of our views stem from our IAnimationView interface that has a method startView() that begins the
animation.

Visual View:
- For our visual view we used the library javax.awt.swing
- Our view is constructed in VisualViewImpl (an implementation of JFrame) by adding AnimationPanel
(an implementation of JPanel) that is decorated by a scroll pane.
- To handle ticks, our constructor in VisualViewImpl creates a javax.awt.swing.timer object that calls
an ActionEvent every tick.
- Our AnimationPanel implements ActionListener and increments a tick field and repaints the screen
when actionPerformed(ActionEvent e) is called.
- Paint component uses a tweening method to draw states not explicitly defined at certain ticks
- Our AnimationPanel initializes a map of commands to draw specific shapes where shape names are mapped to
function objects that implement IDrawShape and contains the method for drawing each specific shape

AbstractNonGUIView:
- abstract class to handle constructing non-GUI (text and SVG) views.
- contains a constructor in order to set shared fields (the model and the appendable)
- contains protected method that appends strings to the appendable and catches IOExceptions

Textual View:
- extends from AbstractNonGUIView
- startView() method prints canvas information followed by information about shapes and their
corresponding motions.
- initializes a map of commands: Map<String, BiFunction<String, Map<Integer, IState>, ITextShapeDescription>>
that is used to create Function objects of ITextShapeDescription that create text for each specific shape.

SVG View:
- extends from AbstractNonGUIView
- adds the field tick rate to keep track of when things occur in the animation
- startView() prints the canvas information followed by shapes and their motion in proper SVG format
- initializes a map of strings mapped to function objects ISVGShapes that create properly formatted
SVG for a specific shape and its motions.


--------------------------------------------------------
Notes on specific model implementation from Assignment 5
--------------------------------------------------------

cs3500.animator.model.position.IPosition:
This interface was created to represent a position.

Methods:
-getX(): This is included so you can get the x coordinate of the position.
-getY(): This is included so you can get the y coordinate of the position.
-setX(): This is included so you can set the x coordinate of the position.
-setY(): This is included so you can set the y coordinate of the position.

cs3500.animator.model.state.IState:
This interface was created to represent a state of a shape.

Methods:
-getRGB(): This method retrieves the three color values and puts them in an array in the
order of red, green, and blue.
-getPosition(): This method retrieves the position that the state has and returns it as an
cs3500.animator.model.position.IPosition.
-getWidthAndHeight(): This method retrieves the width and height value of the state and
returns it as an array in the order of width then height. 
-getStateDescription(): This method returns a string with all of the information about
this state. 

cs3500.animator.model.shape.IShape:
This interface was created to represent a shape and all of the states that shape has
throughout time. 

Methods:
-addState(int time, cs3500.animator.model.state.IState state): This method will map the given state to the given time.
This time represents the time at which this shape will be in this exact state.
-getShape(): This method will return a copy of the shape object.
-getShapeStates((): This method will return a copy of the map with the shape's states
mapped to the time that those states occur.
-getShapeDescription(String name): This method will return a string containing all of the
states that the shape is in throughout time.

cs3500.animator.model.IAnimator:
This interface was created to represent all of shapes to be animated. 

Methods:
-printDescription(): This method will return a string describing each shape contained in
the animator and their states throughout time. 
-addShape(cs3500.animator.model.shape.IShape shape, String name): This method will add a shape to the objectsToAnimate
map mapped to its given name.
-addShapeState(String name, cs3500.animator.model.state.IState state, int time): This method will add a state to a
shape in the objectsToAnimate map, given the map contains the shape name. 
-getObjectsToAnimate(): This method will return a copy of the map of objectsToAnimate.

cs3500.animator.model.position.Position2D:
This class was created as an implementation of cs3500.animator.model.position.IPosition.

cs3500.animator.model.state.State:
This class was created as an implementation of cs3500.animator.model.state.IState.
A cs3500.animator.model.state.State is class that holds the different attributes of an object at an instantaneous
point in time. The class was created with the idea that shapes would generally have the
same attributes so we decided to make a general class containing these attributes and
instead create different implementations of cs3500.animator.model.shape.IShape to represent specific shapes. If we
come across a shape that does not share these same attributes we could extend this class
and adhere to the specifications of those other shapes.

cs3500.animator.model.shape.AShape:
This class was created as an abstract implementation of cs3500.animator.model.shape.IShape.
We decided to create different implementations of cs3500.animator.model.shape.IShape that represent different shapes.
The methods in these different implementations have a lot of commonality and therefore we
decided to have this abstract class to reduce code duplication. We use a Map<Integer,
cs3500.animator.model.state.IState> to represent this shape's states at specific points in time. This allows us to
store what we want the shape to look like at those very specific points in time. This led
us to only have to store those states once instead of having to store start and end
states. For example: If you want to have a red rectangle that is 10x20 start at position
(0,0) at tick 1, move to position (10, 10) by tick 4, and from tick 4 to tick 8, grow to
15x30. We would just store the states at tick 1, tick 4, and tick 8 as opposed to having
something like an array of arrays storing start and end states: [[1, 4], [4, 8]]. We don't
need to store tick 4 twice as both a start and end state. If we run into a case where a
shape only has 1 state, the shape will appear in that state at the time mapped to it and
exist until the animation ends. Another thing with our design we wanted to document is how
it deals with overlapping state changes which can best be explained with an example. If
someone wanted an animation where a shape moved from one position to another from tick 1 to
tick 10 and also change color from tick 5 to tick 7, they would have to break up each tick
and very specifically store this movement in the map. Tick 1 would have the original state
of the shape. Tick 5 would have the shape with the same color and size, but in a different
position. Tick 7 would have the shape again in a different position, a different color,
but the same size. Lastly, tick 10 would have the shape with the same color, the same
size, but a different position again. When the different states of the shape are broken up
like this, we can represent the shape as having overlapping changes that have different
start and/or end times. 

cs3500.animator.model.shape.RectangleShape:
This class was created as an implementation of cs3500.animator.model.shape.IShape and extends cs3500.animator.model.shape.AShape.

cs3500.animator.model.shape.EllipseShape:
This class was created as an implementation of cs3500.animator.model.shape.IShape and extends cs3500.animator.model.shape.AShape.
In the case of an Ellipse, since it uses the general cs3500.animator.model.state.State class, the width and height
fields represent the two diameters of that ellipse (aka the width will be equal to one of
the radii times 2 and the height will be equal to the other radii times 2).

cs3500.animator.model.Animator:
This class was created as an implementation of cs3500.animator.model.IAnimator.
We decided to store all of the objects to be animated as a Map<String, cs3500.animator.model.shape.IShape> where each
shape is mapped to its given name. 
