package edu.smith.cs.csc212.p2;

import java.awt.Graphics2D;
/**
 * This class allows us to travel off one side of the board to the other.
 * @author alexiskilayko
 *
 */
public class Door extends WorldObject {

	public Door(World world) {
		super(world);
	}

	@Override
	public void draw(Graphics2D g) {
		// We don't actually need to draw the doors.
	}

	@Override
	public void step() {
		// Doors don't move!
	}

}
