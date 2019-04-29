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
	
	boolean invincible = false;
	
	boolean mouthOpen = true;
	/**
	 * Random
	 */
    Random random = new Random();
;
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
	 * Animate our fish by facing left and then right over time.
	 */
	private int dt = 0;
	
	/**
	 * Go ahead and ignore this method if you're not into graphics.
	 * We use "dt" as a trick to make the fish change directions every second or so; this makes them feel a little more alive.
	 */
	@Override
	public void draw(Graphics2D g) {
		/*dt += 1;
		if (dt > 100) {
			dt = 0;
		}*/
				
		Shape circle = new Ellipse2D.Double(-0.5, -0.5, 1.0, 1.0);
		Shape arc1 = new Arc2D.Double(-0.5, -0.5, 1.0, 1.0, 45, 180, Arc2D.OPEN);
		Shape arc2 = new Arc2D.Double(-0.5, -0.5, 1.0, 1.0, 115, 180, Arc2D.OPEN);
		
		Graphics2D flipped = (Graphics2D) g.create();
		if (dt < 50) {
			flipped.scale(-1, 1);
		}
		
		if (mouthOpen) {
			g.setColor(Color.yellow);
			g.fill(arc1);
			g.fill(arc2);
		} else {
			g.fill(circle);
		}
		
		/*if (this.player) {
			flipped.setColor(Color.yellow);
			flipped.fill(circle);
		}*/
		
		/*if (this.player && this.invincible) {
		if (this.player && this.invincible) {
			flipped.setColor(Color.blue);
			flipped.fill(circle);
		}
		}
		else {
			flipped.setColor(Color.yellow);
			flipped.fill(circle);
		}

		// Draw the fish of size (1x1, roughly, at 0,0).
		/*flipped.setColor(color);
		flipped.fill(body);

		flipped.setColor(Color.black);
		flipped.fill(eye);

		// draw tail:
		flipped.setColor(tailColor);
		flipped.fill(tail);
		
		flipped.dispose();*/
	}
	
	@Override
	public void step() {
		//mouthOpen = !mouthOpen;
		// Fish are controlled at a higher level; see FishGame.
	}
}
