package edu.smith.cs.csc212.p2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
/**
 * This class is for the pellets on our board.
 * @author alexiskilayko
 *
 */
public class FishFood extends WorldObject {

	public FishFood(World world) {
		super(world);
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.white);
		g.fill(new Ellipse2D.Double(-0.1, -0.1, 0.2, 0.2));
	}

	@Override
	public void step() {
		// Fish Food doesn't move.
	}
}