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
 * Most Fish behavior lives up in WorldObject (a Fish just looks special!).
 * Or it is in PlayFish, where the missing/found and player fish all act different!
 * 
 * @author jfoley
 */
public class Fish extends WorldObject {
	/**
	 * This is the number of points a fish is worth.
	 */
	int points;
	/**
	 * Whether or not this is the player;
	 */	
	boolean player = false;
	
	private int invincible = 0;
	
	boolean mouthOpen = true;
	
	boolean movingLeft = false;
	
	boolean movingRight = false;
	
	boolean movingUp = false;
	
	boolean movingDown = false;
	/**
	 * Random
	 */
    Random random = new Random();
	/**
	 * Called only on the Fish that is the player!
	 */
	public void markAsPlayer() {
		this.player = true;
	}
	/**
	 * A Fish knows what World it belongs to, because all WorldObjects do.
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
		invincible -- ;
		mouthOpen = !mouthOpen;
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
	boolean isInvincible() {
		return invincible > 0;
	}
	void setInvincible() {
		this.invincible = 20;
	}
}
