/**
 * This is the controller for a kaleidoscope animation, making
 * use of the Model-View-Controller design pattern and the Timer and
 * Observer/Observable classes.
 */
package kaleidoscope;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The Controller sets up the GUI and handles all the controls (buttons, menu
 * items, etc.)
 *
 * @author Josh Kessler
 * @author Kelley Loder
 * @author David Matuszek
 */
public class Controller extends JFrame {
	JPanel buttonPanel = new JPanel();
	JButton runButton = new JButton("Run");
	JButton stopButton = new JButton("Stop");
	JButton fasterButton = new JButton("Faster");
	JButton slowerButton = new JButton("Slower");
	JButton changeColorsButton = new JButton("Change Colors");
	JButton addShapeButton = new JButton("Add Shape");
	JButton removeShapeButton = new JButton("Remove Shape");
	JButton resetButton = new JButton("Reset");
	JButton clearButton = new JButton("Clear");
	Timer timer;

	View view;

	Shape circle1, circle2, rectangle1, rectangle2, triangle1, triangle2,
			diamond1, diamond2, circle3, circle4, rectangle3, rectangle4,
			triangle3, triangle4, diamond3, diamond4;

	// stores list of shapes so we can perform actions on them in batches or
	// select individual ones
	ArrayList<Shape> shapeList;

	// set min and max multipliers for speedFactor
	public final int MAX_SPEED = 10;
	public final int MIN_SPEED = 1;
	
	// variable for keeping track of how many drawings should appear on screen
	// at once
	public int numberOfDrawings = 1;

	/**
	 * Runs the kaleidoscope program.
	 * 
	 * @param args
	 *            Ignored.
	 */
	public static void main(String[] args) {
		Controller c = new Controller();
		c.init();
		c.display();
	}

	/**
	 * Sets up communication between the components, initializes shapes, adds
	 * shapes to array.
	 */
	private void init() {
		shapeList = new ArrayList<Shape>();
		circle1 = new Shape("circle", true);
		circle2 = new Shape("circle", false);
		rectangle1 = new Shape("rectangle", false);
		rectangle2 = new Shape("rectangle", false);
		triangle1 = new Shape("triangle", false);
		triangle2 = new Shape("triangle", false);
		diamond1 = new Shape("diamond", false);
		diamond2 = new Shape("diamond", false);
		circle3 = new Shape("circle", false);
		circle4 = new Shape("circle", false);
		rectangle3 = new Shape("rectangle", false);
		rectangle4 = new Shape("rectangle", false);
		triangle3 = new Shape("triangle", false);
		triangle4 = new Shape("triangle", false);
		diamond3 = new Shape("diamond", false);
		diamond4 = new Shape("diamond", false);
		shapeList.add(circle1);
		shapeList.add(rectangle1);
		shapeList.add(triangle1);
		shapeList.add(diamond1);
		shapeList.add(circle2);
		shapeList.add(rectangle2);
		shapeList.add(triangle2);
		shapeList.add(diamond2);
		shapeList.add(circle3);
		shapeList.add(rectangle3);
		shapeList.add(triangle3);
		shapeList.add(diamond3);
		shapeList.add(circle4);
		shapeList.add(rectangle4);
		shapeList.add(triangle4);
		shapeList.add(diamond4);

		view = new View(shapeList); // The view needs to know what model to look
									// at
		// loops through and adds observer and changes colors of each Shape
		for (Shape shape : shapeList) {
			shape.addObserver(view);
			shape.changeColors();
		}
	}

	/**
	 * Displays the GUI.
	 */
	private void display() {
		layOutComponents();
		attachListenersToComponents();
		setSize(800, 800);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Arranges the various components in the GUI.
	 */
	private void layOutComponents() {

		setLayout(new BorderLayout());
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(3, 3));

		buttonPanel.add(runButton);
		buttonPanel.add(stopButton);
		buttonPanel.add(changeColorsButton);

		buttonPanel.add(fasterButton);
		buttonPanel.add(addShapeButton);
		buttonPanel.add(resetButton);

		buttonPanel.add(slowerButton);
		buttonPanel.add(removeShapeButton);
		buttonPanel.add(clearButton);

		stopButton.setEnabled(false);
		resetButton.setEnabled(false);
		clearButton.setEnabled(false);
		fasterButton.setEnabled(false);
		slowerButton.setEnabled(false);
		changeColorsButton.setEnabled(false);
		removeShapeButton.setEnabled(false);
		addShapeButton.setEnabled(false);

		this.add(BorderLayout.SOUTH, buttonPanel);

		this.add(BorderLayout.CENTER, view);
	}

	/**
	 * Attaches listeners to the components, and schedules a Timer.
	 */
	private void attachListenersToComponents() {

		// The Run button tells the Model to start
		runButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				runButton.setEnabled(false);
				stopButton.setEnabled(true);
				resetButton.setEnabled(true);
				clearButton.setEnabled(true);
				fasterButton.setEnabled(true);
				changeColorsButton.setEnabled(true);
				addShapeButton.setEnabled(true);
				shapeList.get(0).shouldBeDrawn = true;
				for (Shape shape : shapeList) {
					shape.start();
				}

			}
		});

		// The Stop button tells the Model to pause
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				runButton.setEnabled(true);
				stopButton.setEnabled(false);
				fasterButton.setEnabled(false);
				slowerButton.setEnabled(false);
				changeColorsButton.setEnabled(false);
				removeShapeButton.setEnabled(false);
				addShapeButton.setEnabled(false);
				for (Shape shape : shapeList) {
					shape.pause();
				}
			}
		});

		// The Faster button tells the Model to speed up the objects' movements
		// while they are moving slower than the max speed.
		fasterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				for (Shape shape : shapeList) {
					shape.speedUp();
				}
				// speedFactor is the same for all shapes, so compare the speed
				// of the first object
				// drawn to the maximum speed to determine when to disable the
				// button
				if (circle1.speedFactor == MAX_SPEED) {
					fasterButton.setEnabled(false);
				}
				slowerButton.setEnabled(true);
			}
		});

		// The Slower button tells the Model to slow down up the objects'
		// movements
		// while they are moving faster than the max speed.
		slowerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				for (Shape shape : shapeList) {
					shape.slowDown();
				}
				if (circle1.speedFactor == MIN_SPEED) {
					slowerButton.setEnabled(false);
				}
				fasterButton.setEnabled(true);
			}
		});

		// The Change Colors button tells the Model to randomly assign all
		// objects
		// a new color.
		changeColorsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				for (Shape shape : shapeList) {
					shape.changeColors();
				}
			}
		});

		// The Add Shape button tells the Model to draw a new object as long as
		// the number of shapes is less than the maximum number allowed. Every
		// time
		// a shape is added, so are its reflections.
		addShapeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				numberOfDrawings++;
				if (numberOfDrawings == shapeList.size()) {
					addShapeButton.setEnabled(false);
				}
				shapeList.get(numberOfDrawings - 1).shouldBeDrawn = true;
				removeShapeButton.setEnabled(true);
			}
		});

		// The Remove Shape button tells the Model to remove an object and its
		// reflections
		// as long as the number of shapes is two or greater.
		removeShapeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				numberOfDrawings--;
				if (numberOfDrawings == 1) {
					removeShapeButton.setEnabled(false);
				}
				shapeList.get(numberOfDrawings - 1).shouldBeDrawn = false;
				addShapeButton.setEnabled(true);
			}
		});

		// The Reset button tells the Model to stop the current drawing and draw
		// again
		// with the initial conditions.
		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				for (Shape shape : shapeList) {
					shape.clear();
				}
				shapeList.get(0).shouldBeDrawn = true;
				for (Shape shape : shapeList) {
					shape.start();
				}
				numberOfDrawings = 1;

				runButton.setEnabled(false);
				stopButton.setEnabled(true);
				resetButton.setEnabled(true);
				clearButton.setEnabled(true);
				fasterButton.setEnabled(true);
				slowerButton.setEnabled(false);
				changeColorsButton.setEnabled(true);
				addShapeButton.setEnabled(true);
				removeShapeButton.setEnabled(false);
			}
		});

		// The Clear button tells the Model to clear the current conditions and
		// clear the
		// screen. At this point, the Reset and Run buttons have the same
		// effect.
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				for (Shape shape : shapeList) {
					shape.clear();
				}
				numberOfDrawings = 1;

				runButton.setEnabled(true);
				stopButton.setEnabled(false);
				resetButton.setEnabled(true);
				clearButton.setEnabled(false);
				fasterButton.setEnabled(false);
				slowerButton.setEnabled(false);
				changeColorsButton.setEnabled(false);
				addShapeButton.setEnabled(false);
				removeShapeButton.setEnabled(false);
			}
		});

		// When the window is resized, the Model is given the new limits
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				for (Shape shape : shapeList) {
					shape.setLimits(view.getWidth(), view.getHeight());
				}
			}
		});
	}

}