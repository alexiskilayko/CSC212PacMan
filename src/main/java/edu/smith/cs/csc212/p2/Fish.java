package edu.smith.cs.csc212.p2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * This class is for our Pac-Man character.
 * 
 * @author jfoley
 */
public class Fish extends WorldObject {
	/**
	 * Whether or not this is the player;
	 */	
	boolean player = false;
	/**
	 * We use this number later to limit how long Pac-Man is invincible for.
	 */
	private int invincible = 0;
	/**
	 * We use this for our Pac-Man animation.
	 */
	boolean mouthOpen = true;
	/**
	 * These booleans help us determine the direction Pac-Man needs to move in,
	 * and ensures he doesn't move in any other direction.
	 */
	boolean movingLeft = false;
	boolean movingRight = false;
	boolean movingUp = false;
	boolean movingDown = false;
	/**
	 * Random
	 */
    Random random = new Random();
	/**
	 * This code is leftover from the P2 code, but this tells us that this world object is our player.
	 */
	public void markAsPlayer() {
		this.player = true;
	}
	/**
	 * Pac-Man knows what World it belongs to, because all WorldObjects do.
	 * @param color Color by number.
	 * @param world The world itself.
	 */
	public Fish(int color, World world) {
		super(world);
	}
	/**
	 * Go ahead and ignore this method if you're not into graphics.
	 * We use "dt" as a trick to make the fish change directions every second or so; this makes them feel a little more alive.
	 */
	@Override
	public void draw(Graphics2D g) {	
		Shape circle = new Ellipse2D.Double(-0.5, -0.5, 1.0, 1.0);
		Shape arc1 = new Arc2D.Double(-0.5, -0.5, 1.0, 1.0, 45, 180, Arc2D.OPEN);
		Shape arc2 = new Arc2D.Double(-0.5, -0.5, 1.0, 1.0, 135, 180, Arc2D.OPEN);
		
		// This code rotates the Pac-Man sprite so that he can face the direction he's moving in.
		Graphics2D flipped = (Graphics2D) g.create();
		if (movingRight) {
			flipped.rotate(Math.toRadians(0));
		} else if (movingDown) {
			flipped.rotate(Math.toRadians(90));
		} else if (movingLeft) {
			flipped.rotate(Math.toRadians(180));
		} else if (movingUp) {
			flipped.rotate(Math.toRadians(270));
		}
		
		// This code is to create two separate designs for the iconic Pac-Man animation.
		if (mouthOpen) {
			flipped.setColor(Color.yellow);
			flipped.fill(arc1);
			flipped.fill(arc2);
		} else {
			flipped.setColor(Color.yellow);
			flipped.fill(circle);
		}
	}
	@Override
	public void step() {
		// Decrement invincible int.
		invincible -- ;
		// Alternate between open mouth and closed mouth sprite with every step.
		mouthOpen = !mouthOpen;
		// Determine the direction Pac-Man moves in.
		if (movingLeft) {
			if (!moveLeft()) {
				movingLeft = false;
			}
		} else if (movingRight) {
			if (!moveRight()) {
				movingRight = false;
			}
		} else if (movingUp) {
			if (!moveUp()) {
				movingUp = false;
			}
		} else if (movingDown) {
			if (!moveDown()) {
				movingDown = false;
			}
		}
	}
	// Ask if Pac-Man is invincible.
	boolean isInvincible() {
		return invincible > 0;
	}
	// If Pac-Man is invincible, then set it to a value that will decrement for a certain number of steps.
	void setInvincible() {
		this.invincible = 30;
	}
}
