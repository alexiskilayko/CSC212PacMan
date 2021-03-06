package edu.smith.cs.csc212.p2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import me.jjfoley.gfx.IntPoint;

/**
 * A World is a 2d grid, represented as a width, a height, and a list of WorldObjects in that world.
 * @author jfoley
 *
 */
public class World {
	/**
	 * The size of the grid (x-tiles).
	 */
	private int width;
	/**
	 * The size of the grid (y-tiles).
	 */
	private int height;
	/**
	 * A list of objects in the world (Pac-Man, Ghosts, Walls, etc.).
	 */
	private List<WorldObject> items;
	/**
	 * A reference to a random object, so we can randomize placement of objects in this world.
	 */
	private Random rand = ThreadLocalRandom.current();

	/**
	 * Create a new world of a given width and height.
	 * @param w - width of the world.
	 * @param h - height of the world.
	 */
	public World(int w, int h) {
		items = new ArrayList<>();
		width = w;
		height = h;
	}

	/**
	 * What is under this point?
	 * @param x - the tile-x.
	 * @param y - the tile-y.
	 * @return a list of objects!
	 */
	public List<WorldObject> find(int x, int y) {
		List<WorldObject> found = new ArrayList<>();
		
		// Check out every object in the world to find the ones at a particular point.
		for (WorldObject w : this.items) {
			// But only the ones that match are "found".
			if (x == w.getX() && y == w.getY()) {
				found.add(w);
			}
		}
		
		// Give back the list, even if empty.
		return found;
	}
	
	
	/**
	 * This is used by PlayGame to draw all our items!
	 * @return the list of items.
	 */
	public List<WorldObject> viewItems() {
		// Don't let anybody add to this list!
		// Make them use "register" and "remove".

		// This is kind of an advanced-Java trick to return a list where add/remove crash instead of working.
		return Collections.unmodifiableList(items);
	}

	/**
	 * Add an item to this World.
	 * @param item - the Fish, Rock, Snail, or other WorldObject.
	 */
	public void register(WorldObject item) {
		// Print out what we've added, for our sanity.
		System.out.println("register: "+item.getClass().getSimpleName());
		items.add(0, item);
	}
	
	/**
	 * This is the opposite of register. It removes an item (like a fish) from the World.
	 * @param item - the item to remove.
	 */
	public void remove(WorldObject item) {
		// Print out what we've removed, for our sanity.
		System.out.println("remove: "+item.getClass().getSimpleName());
		items.remove(item);
	}
	
	/**
	 * How big is the world we model?
	 * @return the width.
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * How big is the world we model?
	 * @return the height.
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Try to find an unused part of the World for a new object!
	 * @return a point (x,y) that has nothing else in the grid.
	 */
	public IntPoint pickUnusedSpace() {
		int tries = width * height;
		for (int i=0; i<tries; i++) {
			int x = rand.nextInt(width);
			int y = rand.nextInt(height);
			if (this.find(x, y).size() != 0) {
				continue;
			}
			return new IntPoint(x,y);
		}
		// If we get here, we tried a lot of times and couldn't find a random point.
		// Let's crash our Java program!
		throw new IllegalStateException("Tried to pickUnusedSpace "+tries+" times and it failed! Maybe your grid is too small!");
	}
	
	/**
	 * Insert an item randomly into the grid.
	 * @param item - the rock, fish, snail or other WorldObject.
	 */
	public void insertRandomly(WorldObject item) {
		item.setPosition(pickUnusedSpace());
		this.register(item);
		item.checkFindMyself();
	}
	
	/**
	 * Insert the Pac-Man game board.
	 */
	public void insertPacmanBoard() {
		// Hard-code the design of our game board.
		String[] mapData = {
				"XXXXXXXXXXXXXXXXXXXXXXXXXXXX",
				"X............XX............X",
				"X.XXXX.XXXXX.XX.XXXXX.XXXX.X",
				"X.XXXX.XXXXX.XX.XXXXX.XXXX.X",
				"X.XXXX.XXXXX.XX.XXXXX.XXXX.X",
				"X..........................X",
				"X.XXXX.XX.XXXXXXXX.XX.XXXX.X",
				"X.XXXX.XX.XXXXXXXX.XX.XXXX.X",
				"X......XX....XX....XX......X",
				"XXXXXX.XXXXX.XX.XXXXX.XXXXXX",
				"XXXXXX.XXXXX.XX.XXXXX.XXXXXX",
				"XXXXXX.XX..........XX.XXXXXX",
				"XXXXXX.XX.XXXXXXXX.XX.XXXXXX",
				"D.........XXXXXXXX.........D",
				"XXXXXX.XX.XXXXXXXX.XX.XXXXXX",
				"XXXXXX.XX..........XX.XXXXXX",
				"XXXXXX.XX.XXXXXXXX.XX.XXXXXX",
				"X............XX............X",
				"X.XXXX.XXXXX.XX.XXXXX.XXXX.X",
				"X.XXXX.XXXXX.XX.XXXXX.XXXX.X",
				"X...XX................XX...X",
				"XXX.XX.XX.XXXXXXXX.XX.XX.XXX",
				"XXX.XX.XX.XXXXXXXX.XX.XX.XXX",
				"X......XX....XX....XX......X",
				"X.XXXXXXXXXX.XX.XXXXXXXXXX.X",
				"X.XXXXXXXXXX.XX.XXXXXXXXXX.X",
				"X..........................X",
				"XXXXXXXXXXXXXXXXXXXXXXXXXXXX"
				};
		for (int y = 0; y < mapData.length; y++) {
			char[] row = mapData[y].toCharArray();
			for (int x = 0; x < row.length; x++) {
				WorldObject wo = null;
				if (row[x] == 'X') {
					wo = new Rock(this);
				} else if (row[x] == 'D') {
					wo = new Door(this);
				}
				if (wo == null) {
					continue;
				}
				wo.setPosition(x, y);
				this.register(wo);
			}
		}
	}
	
	/**
	 * Insert a new Ghost at random into the world.
	 * @return the ghost!
	 */
	public Snail insertSnailRandomly(int color) {
		Snail snail = new Snail(color, this);
		insertRandomly(snail);
		return snail;
	}
	
	/**
	 * Insert food into the world at a random position.
	 * @return the food.
	 */
	public FishFood insertFruitRandomly() {
		PacFruit fruit = new PacFruit(this);
		insertRandomly(fruit);
		return fruit;
	}
	
	/**
	 * Determine if a WorldObject can swim to a particular point.
	 * 
	 * @param whoIsAsking - the object (not just the player!)
	 * @param x - the x-tile.
	 * @param y - the y-tile.
	 * @return true if they can move there.
	 */
	public boolean canSwim(WorldObject whoIsAsking, int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height) {
			return false;
		}
		
		// This will be important.
		boolean isPlayer = whoIsAsking.isPlayer();
		
		// We will need to look at who all is in the spot to determine if we can move there.
		List<WorldObject> inSpot = this.find(x, y);
		
		for (WorldObject it : inSpot) {
			// Don't let us move over rocks as a Fish.
			if (it instanceof Rock) {
				return false;
			}
			if ((it instanceof Fish) && (whoIsAsking.isPlayer() == false)) {
				return true;
			}
			// Allow Pac-Man and ghosts to overlap.
			if (it instanceof Snail) {
				return true;
			}
		}
		
		// If we didn't see an obstacle, we can move there!
		return true;
	}
	
	/**
	 * This is how objects may move. Only Ghosts do right now.
	 */
	public void stepAll() {
		for (WorldObject it : this.items) {
			if (!(it instanceof Fish)) {
				it.step();
			}
		}
	}
}
