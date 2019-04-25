package edu.smith.cs.csc212.p2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * It would be awful nice to have multi-colored rocks at random.
 * This is not <a href="https://en.wikipedia.org/wiki/Dwayne_Johnson">the Rock</a>, but a Rock.
 * @author jfoley
 */
public class Rock extends WorldObject {
	/**
	 * I took these colors from Wikipedia's Cool and Warm Gray sections.
	 * https://en.wikipedia.org/wiki/Shades_of_gray#Cool_grays
	 * https://en.wikipedia.org/wiki/Shades_of_gray#Warm_grays
	 */
	// introduce a member here that indexes the ROCK_COLORS array.
	
	/**
	 * Construct a Rock in our world.
	 * @param world - the grid world.
	 */
	public Rock(World world) {
		super(world);
		// initialize your rock color index to a random number!
		// Note that all WorldObjects have a ``rand`` available so you don't need to make one.
	}
	

	/**
	 * Draw a rock!
	 */
	@Override
	public void draw(Graphics2D g) {
		// use the right color in here...
		Color color = Color.blue;
		g.setColor(color);
		Rectangle2D rock = new Rectangle2D.Double(-.5,-.5,1,1);
		g.fill(rock);
	}

	@Override
	public void step() {
		// Rocks don't actually *do* anything.		
	}

}
