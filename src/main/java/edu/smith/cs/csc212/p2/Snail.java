package edu.smith.cs.csc212.p2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.List;
import java.util.Random;

/**
 * A Snail moves left to right and bumps into things!
 * @author jfoley
 */
public class Snail extends WorldObject {
	
	/**
	 * This is the color of the Snail's body.
	 */
	public static Color[] COLORS = {
	Color.red,
	Color.cyan,
	Color.magenta,
	Color.orange 
	};
	// Add more colors.
	// Maybe make a special fish that is more points?
	/**
	* This is an index into the {@link #COLORS} array.
	*/
	int color;
	//public Color bodyColor = Color.magenta;
	/**
	 * This is the color of the snail shell.
	 */
	//public Color shellColor = Color.white;
	/**
	 * This is pupil of the snail color.
	 */
	public Color eyeColor = Color.black;
	/**
	 * Does the snail have its eyes open?
	 */
	public boolean eyesOpen = false;
	/**
	 * Is the snail going to the left?
	 */
	public boolean movingLeft = false;
	
    Random random = new Random();
	
	/**
	 * Create a new Snail in a part of this world.
	 * @param world - the world where the snail moves/lives.
	 */
	public Snail(int color, World world) {
		super(world);
		this.color = color;
	}

	/**
	 * What actual color is this fish? We store an index, so get it here.
	 * @return the Color object from our array.
	 */
	public Color getColor() {
		return COLORS[this.color];
	}
	
	/**
	 * Polishing up my Snail draw method...
	 * This is kind of a mess, but that's graphics for you.
	 */
	@Override
	public void draw(Graphics2D input) {
		Graphics2D g = (Graphics2D) input.create();
		/*g.scale(1.0/100.0, 1.0/100.0);
		g.translate(-30, 0);*/
		//Shape body = new Rectangle2D.Double(0, 0, 40, 50);
		//Shape tentacleL = new Rectangle2D.Double(0, -20, 5, 20);
		Shape eyeWhiteL = new Ellipse2D.Double(-0.4, -0.4, 0.2, 0.2);
		Shape eyePupilL = new Ellipse2D.Double(-0.35, -0.35, 0.1, 0.1);
		//Shape tentacleR = new Rectangle2D.Double(35, -20, 5, 20);
		Shape eyeWhiteR = new Ellipse2D.Double(0.2, -0.4, 0.2, 0.2);
		Shape eyePupilR = new Ellipse2D.Double(0.25, -0.35, 0.1, 0.1);
		
		//Shape body = new Ellipse2D.Double(-0.5, -0.5, 1.0, 1.0);
		Shape head = new Arc2D.Double(-0.5, -0.5, 1.0, 1.0, 0, 180, Arc2D.OPEN);
		Shape body = new Rectangle2D.Double(-0.5, 0, 1.0, 0.3);
		Shape leg1 = new Rectangle2D.Double(-0.5, 0.2, 0.25, 0.3);
		Shape leg2 = new Rectangle2D.Double(-0.125, 0.2, 0.25, 0.3);
		Shape leg3 = new Rectangle2D.Double(0.25, 0.2, 0.25, 0.3);
		
		Color bodyColor = getColor();
		g.setColor(bodyColor);
		g.fill(head);
		g.fill(body);
		g.fill(leg1);
		g.fill(leg2);
		g.fill(leg3);
		//g.fillArc(50, 50, 100, 100, 0, 180); 

		/*g.fill(body);
		g.fill(leg1);

		g.setColor(bodyColor);
		g.fill(leg2);*/

			g.fill(eyeWhiteL);
			g.fill(eyeWhiteR);
			g.setColor(Color.white);
			g.fill(eyeWhiteL);
			g.setColor(eyeColor);
			g.fill(eyePupilL);
			g.setColor(Color.white);
			g.fill(eyeWhiteR);
			g.setColor(eyeColor);
			g.fill(eyePupilR);

		/*Shape shell3 = new Ellipse2D.Double(45, 20, 10, 10);
		Shape shell2 = new Ellipse2D.Double(35, 10, 30, 30);
		Shape shell1 = new Ellipse2D.Double(25, 0, 50, 50);

		g.setColor(shellColor);
		g.fill(shell1);
		g.setColor(Color.black);
		g.draw(shell1);
		g.setColor(Color.black);
		g.draw(shell2);
		g.setColor(Color.black);
		g.draw(shell3);*/
	}

	/**
	 * Move the snail left until it hits an obstacle. 
	 * Then move it right until it hits an obstacle.
	 * Alternate eyes open/closed as it moves.
	 */
	@Override
	public void step() {
		this.moveRandomly();
		/*int rand = random.nextInt(4);
		
		int x = this.getX();
		int y = this.getY() + 1;
		
		if (rand == 0) {
			while (x.isRock()) {
				this.moveUp();
			}
		} else if (rand == 1) {
			this.moveRight();
		} else if (rand == 2) {
			this.moveDown();
		} else if (rand == 3) {
			this.moveLeft();
		}*/
		/*eyesOpen = !eyesOpen;
		if (movingLeft) {
			if (!moveLeft()) {
				movingLeft = false;
			}
		} else {
			if (!moveRight()) {
				movingLeft = true;
			}
		}*/
		
	}

}
