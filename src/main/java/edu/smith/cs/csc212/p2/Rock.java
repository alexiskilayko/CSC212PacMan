package edu.smith.cs.csc212.p2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * This class is for our iconic blue walls.
 * @author jfoley
 */
public class Rock extends WorldObject {
	/**
	 * Construct a wall in our world.
	 * @param world - the grid world.
	 */
	public Rock(World world) {
		super(world);
	}
	
	/**
	 * Draw a wall!
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
		// Walls don't actually *do* anything.		
	}

}
