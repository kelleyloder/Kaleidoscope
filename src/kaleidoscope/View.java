package kaleidoscope;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

/**
 * The View "observes" and displays what is going on in the Model. In this
 * example, the Model contains multiple moving shapes that imitate a
 * kaleidoscope.
 * 
 * @author David Matuszek
 * @author Josh Kessler
 * @author Kelley Loder
 */
public class View extends JPanel implements Observer {

	Shape shape;
	ArrayList<Shape> shapeList;
	

	/**
	 * Constructor.
	 * 
	 * @param model
	 *            The Model whose working is to be displayed.
	 */
	View(ArrayList<Shape> shapeList) {
		this.shapeList = shapeList;
		for (Shape shape : shapeList) {
			this.shape = shape;
		}
	}

	/**
	 * Draws a circle and its reflections
	 * 
	 * @param g
	 * @param shape
	 */
	public void drawCircle(Graphics g, Shape shape) {
		g.setColor(new Color(shape.colorR, shape.colorG, shape.colorB));
		g.fillOval(shape.getX(), shape.getY(), shape.figureWidth,
				shape.figureHeight);
		g.fillOval(shape.getNegX(), shape.getY(), shape.figureWidth,
				shape.figureHeight);
		g.fillOval(shape.getX(), shape.getNegY(), shape.figureWidth,
				shape.figureHeight);
		g.fillOval(shape.getNegX(), shape.getNegY(), shape.figureWidth,
				shape.figureHeight);

		g.fillOval(shape.getNegInvY(), shape.getNegInvX(), shape.figureHeight,
				shape.figureWidth);
		g.fillOval(shape.getInvY(), shape.getNegInvX(), shape.figureHeight,
				shape.figureWidth);
		g.fillOval(shape.getNegInvY(), shape.getInvX(), shape.figureHeight,
				shape.figureWidth);

		// g.setColor(Color.blue);
		g.fillOval(shape.getInvY(), shape.getInvX(), shape.figureHeight,
				shape.figureWidth);
	}

	/**
	 * Draws a rectangle and its reflections
	 * 
	 * @param g
	 * @param shape
	 */
	public void drawRectangle(Graphics g, Shape shape) {
		g.setColor(new Color(shape.colorR, shape.colorG, shape.colorB));
		g.fillRect(shape.getX(), shape.getY(), shape.figureWidth,
				shape.figureHeight);
		g.fillRect(shape.getNegX(), shape.getY(), shape.figureWidth,
				shape.figureHeight);
		g.fillRect(shape.getX(), shape.getNegY(), shape.figureWidth,
				shape.figureHeight);
		g.fillRect(shape.getNegX(), shape.getNegY(), shape.figureWidth,
				shape.figureHeight);

		g.fillRect(shape.getInvY(), shape.getInvX(), shape.figureHeight,
				shape.figureWidth);
		g.fillRect(shape.getNegInvY(), shape.getNegInvX(), shape.figureHeight,
				shape.figureWidth);
		g.fillRect(shape.getInvY(), shape.getNegInvX(), shape.figureHeight,
				shape.figureWidth);
		g.fillRect(shape.getNegInvY(), shape.getInvX(), shape.figureHeight,
				shape.figureWidth);
	}

	/**
	 * Draws a triangle and its reflections
	 * 
	 * @param g
	 * @param shape
	 */
	public void drawTriangle(Graphics g, Shape shape) {
		// arrays of vertices of triangles
		int[] xTriangle = { shape.getX(), shape.getX() + shape.figureWidth / 2,
				shape.getX() - shape.figureWidth / 2 };
		int[] yTriangle = { shape.getY(), shape.getY() - shape.figureHeight,
				shape.getY() - shape.figureHeight };

		int[] negXTriangle = { shape.getNegX(),
				shape.getNegX() + shape.figureWidth / 2,
				shape.getNegX() - shape.figureWidth / 2 };
		int[] negYTriangle = { shape.getNegY() - shape.figureHeight,
				shape.getNegY(), shape.getNegY() };

		int[] invXTriangle = { shape.getInvX(),
				shape.getInvX() + shape.figureWidth / 2,
				shape.getInvX() - shape.figureWidth / 2 };
		int[] invYTriangle = { shape.getInvY() - shape.figureHeight,
				shape.getInvY(), shape.getInvY() };

		int[] negInvXTriangle = { shape.getNegInvX(),
				shape.getNegInvX() + shape.figureWidth / 2,
				shape.getNegInvX() - shape.figureWidth / 2 };
		int[] negInvYTriangle = { shape.getNegInvY(),
				shape.getNegInvY() - shape.figureHeight,
				shape.getNegInvY() - shape.figureHeight };

		g.setColor(new Color(shape.colorR, shape.colorG, shape.colorB));
		g.fillPolygon(xTriangle, yTriangle, 3);
		g.fillPolygon(negXTriangle, yTriangle, 3);
		g.fillPolygon(xTriangle, negYTriangle, 3);
		g.fillPolygon(negXTriangle, negYTriangle, 3);

		g.fillPolygon(invYTriangle, invXTriangle, 3);
		g.fillPolygon(negInvYTriangle, negInvXTriangle, 3);
		g.fillPolygon(invYTriangle, negInvXTriangle, 3);
		g.fillPolygon(negInvYTriangle, invXTriangle, 3);
	}

	/**
	 * Draws a diamond and its reflections
	 * 
	 * @param g
	 * @param shape
	 */
	public void drawDiamond(Graphics g, Shape shape) {
		// arrays of vertices of triangles
		int[] xDiamond = { shape.getX(), shape.getX() + shape.figureWidth / 2,
				shape.getX(), shape.getX() - shape.figureWidth / 2 };
		int[] yDiamond = { shape.getY(), shape.getY() - shape.figureHeight / 2,
				shape.getY() - shape.figureHeight,
				shape.getY() - shape.figureHeight / 2 };

		int[] negXDiamond = { shape.getNegX(),
				shape.getNegX() + shape.figureWidth / 2, shape.getNegX(),
				shape.getNegX() - shape.figureWidth / 2 };
		int[] negYDiamond = { shape.getNegY(),
				shape.getNegY() - shape.figureHeight / 2,
				shape.getNegY() - shape.figureHeight,
				shape.getNegY() - shape.figureHeight / 2 };

		int[] invXDiamond = { shape.getInvX(),
				shape.getInvX() + shape.figureWidth / 2, shape.getInvX(),
				shape.getInvX() - shape.figureWidth / 2 };
		int[] invYDiamond = { shape.getInvY(),
				shape.getInvY() - shape.figureHeight / 2,
				shape.getInvY() - shape.figureHeight,
				shape.getInvY() - shape.figureHeight / 2 };

		int[] negInvXDiamond = { shape.getNegInvX(),
				shape.getNegInvX() + shape.figureWidth / 2, shape.getNegInvX(),
				shape.getNegInvX() - shape.figureWidth / 2 };
		int[] negInvYDiamond = { shape.getNegInvY(),
				shape.getNegInvY() - shape.figureHeight / 2,
				shape.getNegInvY() - shape.figureHeight,
				shape.getNegInvY() - shape.figureHeight / 2 };

		g.setColor(new Color(shape.colorR, shape.colorG, shape.colorB));
		g.fillPolygon(xDiamond, yDiamond, 4);
		g.fillPolygon(negXDiamond, yDiamond, 4);
		g.fillPolygon(xDiamond, negYDiamond, 4);
		g.fillPolygon(negXDiamond, negYDiamond, 4);

		g.fillPolygon(invYDiamond, invXDiamond, 4);
		g.fillPolygon(negInvYDiamond, negInvXDiamond, 4);
		g.fillPolygon(invYDiamond, negInvXDiamond, 4);
		g.fillPolygon(negInvYDiamond, invXDiamond, 4);
	}

	/**
	 * Displays what is going on in the Model. Note: This method should NEVER be
	 * called directly; call repaint() instead.
	 * 
	 * @param g
	 *            The Graphics on which to paint things.
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		// sets background over which shapes move
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());

		// draws shapes
		for (Shape shape : shapeList) {
			if (shape.shouldBeDrawn) {
				if (shape.shapeType == "circle") {
					drawCircle(g, shape);
				} else if (shape.shapeType == "rectangle") {
					drawRectangle(g, shape);
				} else if (shape.shapeType == "triangle") {
					drawTriangle(g, shape);
				} else {
					drawDiamond(g, shape);
				}
			}
		}
	}

	/**
	 * When an Observer notifies Observers (and this View is an Observer), this
	 * is the method that gets called.
	 * 
	 * @param obs
	 *            Holds a reference to the object being observed.
	 * @param arg
	 *            If notifyObservers is given a parameter, it is received here.
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable obs, Object arg) {
		repaint();
	}
}