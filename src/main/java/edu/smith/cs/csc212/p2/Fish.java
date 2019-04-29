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
	 * A fish is only special because of its color now!
	 */
	/*public static Color[] COLORS = {
			Color.red,
			Color.green,
			Color.green,
			Color.green,
			Color.yellow,
			Color.cyan
			// Add more colors.
			// Maybe make a special fish that is more points?
	};*/
	/**
	 * This is an index into the {@link #COLORS} array.
	 */
	//int color;
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
	 * Whether or not the fish gets scared fast.
	 */
	//public boolean fastScared;
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
		/*this.color = color;
		// Designate different point values for different colors.
		if ((this.color == 1) || (this.color == 2) || (this.color == 3)) {
			this.points = 10;
		} else if ((this.color == 4) || (this.color == 5)) {
			this.points = 20;
		} else if (this.color == 6) {
			this.points = 30;
		} 
		// Randomize whether or not the fish is fastScared.
		this.fastScared = random.nextBoolean();*/
	}
	
	/**
	 * What actual color is this fish? We store an index, so get it here.
	 * @return the Color object from our array.
	 */
	/*public Color getColor() {
		return COLORS[this.color];
	}*/
	
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

		/*Shape body = new Ellipse2D.Double(-.40, -.2, .8, .4);
		Shape tail = new Ellipse2D.Double(+.2, -.3, .2, .6);
		Shape eye = new Ellipse2D.Double(-.25, -.1, .1, .1);
		
		Color color = getColor();
		Color tailColor = color.darker();*/

		
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
			//g.fill(arc2);
		}
		
		/*if (this.player) {
			flipped.setColor(Color.yellow);
			flipped.fill(circle);
		}*/
		
		/*if (this.player && this.invincible) {
			flipped.setColor(Color.blue);
			flipped.fill(circle);
		}*/

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
