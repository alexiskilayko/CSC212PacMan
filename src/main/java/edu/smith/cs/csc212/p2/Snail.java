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
 * A Ghost can damage Pac-Man, but they can also be eaten by Pac-Man.
 * @author jfoley
 */
public class Snail extends WorldObject {
	/**
	 * This is the color of the Ghost's body.
	 */
	public static Color[] COLORS = {
		Color.red,
		Color.cyan,
		Color.magenta,
		Color.orange};
	/**
	 * This is an index into the {@link #COLORS} array.
	 */
	int color;
	/**
	 * This value corresponds to a certain direction.
	 */
	int direction;
	/**
	 * This is pupil of the snail color.
	 */
	public Color eyeColor = Color.black;
	/**
	 * What direction is the ghost moving in?
	 */
	public boolean movingLeft = false;
	public boolean movingRight = false;
	public boolean movingUp = false;
	public boolean movingDown = false;
	/**
	 * Ghosts have two states: a normal state and a frozen state.
	 */
	public boolean frozen = false;
	
    Random random = new Random();
	/**
	 * Create a new Ghost in a part of this world.
	 * @param world - the world where the snail moves/lives.
	 */
	public Snail(int color, World world) {
		super(world);
		this.color = color;
		this.direction = random.nextInt(3);
	}
	/**
	 * What actual color is this ghost? We store an index, so get it here.
	 * @return the Color object from our array.
	 */
	public Color getColor() {
		return COLORS[this.color];
	}
	/**
	 * Polishing up my Ghost draw method...
	 * This is kind of a mess, but that's graphics for you.
	 */
	@Override
	public void draw(Graphics2D input) {
		Graphics2D g = (Graphics2D) input.create();
		Shape eyeWhiteL = new Ellipse2D.Double(-0.4, -0.4, 0.2, 0.2);
		Shape eyePupilL = new Ellipse2D.Double(-0.35, -0.35, 0.1, 0.1);
		Shape eyeWhiteR = new Ellipse2D.Double(0.2, -0.4, 0.2, 0.2);
		Shape eyePupilR = new Ellipse2D.Double(0.25, -0.35, 0.1, 0.1);
		
		Shape head = new Arc2D.Double(-0.5, -0.5, 1.0, 1.0, 0, 180, Arc2D.OPEN);
		Shape body = new Rectangle2D.Double(-0.5, 0, 1.0, 0.3);
		Shape leg1 = new Rectangle2D.Double(-0.5, 0.2, 0.25, 0.3);
		Shape leg2 = new Rectangle2D.Double(-0.125, 0.2, 0.25, 0.3);
		Shape leg3 = new Rectangle2D.Double(0.25, 0.2, 0.25, 0.3);
		
		// Each ghost is a different color.
		Color bodyColor = getColor();
		
		// Two separate designs for when ghosts are normal and for when they are frozen.
		if (!frozen) {
			g.setColor(bodyColor);
			g.fill(head);
			g.fill(body);
			g.fill(leg1);
			g.fill(leg2);
			g.fill(leg3);
		} else {
			g.setColor(Color.gray);
			g.fill(head);
			g.fill(body);
			g.fill(leg1);
			g.fill(leg2);
			g.fill(leg3);
		}
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
	}
	/**
	 * This just gives us a random integer from 1 to 4. We implement this function so we don't have to repeat the code.
	 */
	public void pickNewDirection() {
		direction = random.nextInt(4);
	}
	/**
	 * The ghosts choose a random direction and continue moving in that direction until they cannot.
	 */
	@Override
	public void step() {
		if (direction == 0) {
			movingLeft = true;
			if (!moveLeft()) {
				movingLeft = false;
				pickNewDirection();
			}
		} else if (direction == 1) {
			movingRight = true;
			if (!moveRight()) {
				movingRight = false;
				pickNewDirection();
			}
		} else if (direction == 2) {
			movingUp = true;
			if (!moveUp()) {
				movingUp = false;
				pickNewDirection();
			}
		} else if (direction == 3) {
			movingDown = true;
			if (!moveDown()) {
				movingDown = false;
				pickNewDirection();
			}
		}
	}

}
