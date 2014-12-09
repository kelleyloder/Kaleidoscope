package kaleidoscope;

import java.util.Observable;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This is the Model class for a kaleidoscope program. It is an Observable,
 * which means that it can notifyObservers that something in the
 * model has changed, and they should take appropriate actions.
 *
 * @author Josh Kessler
 * @author Kelley Loder
  * @author David Matuszek
 */
public class Shape extends Observable {
    private int xPosition;
    private int yPosition;
    public int xLimit, yLimit;
    private int xDelta;
    private int yDelta;
    private Timer timer;

    public int figureWidth = 20;
    public int figureHeight = 20;
    
    //Multiplier used with xDelta and yDelta to speed up/slow down shapes.
    public int speedFactor = 1;
    //Used to determine when to randomize colors on objects.
    public boolean colorChangeClicked;
    public int colorR, colorG, colorB;
    
    //Used in shape constructor to tell it how to draw a particular shape
    public String shapeType;
    
    //Used to determine how many shapes should appear on-screen.
    public boolean shouldBeDrawn;

    /*
     */
    
    /**
     * Constructor for figures to draw on screen; randomizes figure's location, size, and speed.
     * @param shapeType
     * @param shouldBeDrawn
     */
    public Shape(String shapeType, boolean shouldBeDrawn) {
    	this.shapeType = shapeType;
    	this.shouldBeDrawn = shouldBeDrawn;
    	
    	Random r = new Random();
        //Starting x and y positions are hard-coded relative to starting size of window
        //so reflections will appear in correct portion of the screen.
    	xPosition = r.nextInt(405) + 390;
    	yPosition = r.nextInt(390) + 5;
    	xDelta = r.nextInt(8) + 2;
    	yDelta = r.nextInt(8) + 2;
    	figureWidth = r.nextInt(30) + 20;
    	figureHeight = r.nextInt(30) + 20;
    }    
    
    /**
     * Sets the "walls" that the shape should bounce off from.
     * 
     * @param xLimit The position (in pixels) of the wall on the right.
     * @param yLimit The position (in pixels) of the floor.
     */
    public void setLimits(int xLimit, int yLimit) {
        this.xLimit = xLimit - figureWidth;
        this.yLimit = yLimit - figureHeight;
        xPosition = Math.min(xPosition, xLimit);
        yPosition = Math.min(yPosition, yLimit);
    }

    /**
     * @return The shape's X position.
     */
    public int getX() {
        return xPosition;
    }

    /**
     * @return The shape's Y position.
     */
    public int getY() {
        return yPosition;
    }
    
    /**
     * @return The shape's horizontal distance from the midline of the window.
     */
    public int getLenFromXOrigin() {
    	return xPosition - (xLimit - figureWidth)/ 2;
    }
    
    /**
     * @return The shape's vertical distance from the midline of the window.
     */
    public int getLenFromYOrigin() {
    	return yPosition - (yLimit - figureHeight) / 2;
    }
    
    /**
	 * @return returns a reflected x coordinate
	 *         
	 */
    public int getNegX() {
    	return xLimit / 2 - getLenFromXOrigin();
    }
    
    /**
	 * @return returns a reflected y coordinate
	 *         
	 */
    public int getNegY() {
    	return yLimit / 2 - getLenFromYOrigin();
    }
    
    /**
	 * @return returns a reflected x coordinate
	 *         
	 */
    public int getInvX() {
    	return yLimit / 2 - getLenFromXOrigin();
    }
    
    /**
	 * @return returns a reflected y coordinate
	 *         
	 */
    public int getInvY() {
    	return xLimit / 2 - getLenFromYOrigin();
    }
    
    /**
	 * @return returns a reflected x coordinate
	 *         
	 */
    public int getNegInvX() {
    	return yLimit / 2 + getLenFromXOrigin();
    }
    
    /**
	 * @return returns a reflected y coordinate
	 *         
	 */
    public int getNegInvY() {
    	return xLimit / 2 + getLenFromYOrigin();
    }
    
   /**
     * Tells shapes to start moving using Timer and TimerTask and calls makeOneStep
     * 
     */
    public void start() {
        timer = new Timer(true);
        timer.schedule(new Strobe(), 0, 40); // 25 times a second  
    }
    
    /**
     * Tells each shape to stop where it is.
     */
    public void pause() {
    	timer.cancel();
    }
    
    /**
     * Tells each shape to advance one step in the direction that it is moving.
     * If it hits a wall, its direction of movement changes.
     */
    public void makeOneStep() {
        // Do the work
        xPosition += xDelta * speedFactor;
        if (xPosition < 0 || xPosition >= xLimit) {
            xDelta = -xDelta;
            xPosition += xDelta * speedFactor;
        }
        yPosition += yDelta * speedFactor;
        if (yPosition < 0 || yPosition >= yLimit) {
            yDelta = -yDelta;
            yPosition += yDelta * speedFactor;
        }
        // Notify observers
        setChanged();
        notifyObservers();
    }
    
    /**
     * Speeds up movement of each shape.
     */
    public void speedUp() {
    	this.speedFactor++;
    }
    
    /**
     * Slows down movement of each shape.
     */
    public void slowDown() {
    	this.speedFactor--;
    }
    
    /**
     * Randomly generate new colors for each shape.
     */
    public void changeColors() {
    	Random r = new Random();
    	this.colorR = r.nextInt(256);
    	this.colorB = r.nextInt(256);
    	this.colorG = r.nextInt(256);    	
    }
    
    /**
     * Tells the model to advance one "step."
     */
    private class Strobe extends TimerTask {
        @Override
        public void run() {
            makeOneStep();
        }
    }

    /**
     * Re-draw background of display with no shapes and sets variables back to initial values
     */
	public void clear() {
		timer.cancel();
		shouldBeDrawn = false;
		setChanged();
        notifyObservers();
        Random r = new Random();
    	xPosition = r.nextInt(xLimit / 2 + 5) + xLimit / 2 - 10;
		yPosition = r.nextInt(yLimit / 2 - 10) + 5;
		xDelta = r.nextInt(8) + 2;
		yDelta = r.nextInt(8) + 2;
		figureWidth = r.nextInt(30) + 2;
		figureHeight = r.nextInt(30) + 2;
		speedFactor = 1;
		changeColors();
		
	}
}