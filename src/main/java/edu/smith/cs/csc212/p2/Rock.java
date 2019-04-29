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
	 * Construct a Rock in our world.
	 * @param world - the grid world.
	 */
	public Rock(World world) {
		super(world);
	}
	
	/**
	 * Draw a rock!
	 */
	@Override
	public void draw(Graphics2D g) {
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
