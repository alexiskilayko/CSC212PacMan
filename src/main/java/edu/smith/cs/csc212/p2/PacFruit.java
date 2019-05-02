package edu.smith.cs.csc212.p2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
/**
 * These are our power pellets! They give Pac-Man special powers.
 * @author alexiskilayko
 *
 */
public class PacFruit extends FishFood {
	public PacFruit (World world) {
		super(world);
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.green);
		g.fill(new Ellipse2D.Double(-0.4, -0.4, 0.8, 0.8));
	}

	@Override
	public void step() {
		// Fish Food doesn't move.
	}
}
