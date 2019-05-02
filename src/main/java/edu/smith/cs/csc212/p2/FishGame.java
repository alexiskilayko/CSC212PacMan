package edu.smith.cs.csc212.p2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import me.jjfoley.gfx.IntPoint;

/**
 * This class manages our model of gameplay.
 * @author jfoley
 *
 */
public class FishGame {
	/**
	 * This is the world in which our Pac-Man characters exist.
	 */
	World world;
	/**
	 * The player is Pac-Man, and he eats pellets.
	 */
	Fish player;
	/**
	 * PacMan's other lives.
	 */
	List<Fish> lives;
	/**
	 * This is the food that Pac-Man can eat.
	 */
	FishFood food;
	/**
	 * Number of pellets left on the board.
	 */
	int numPellets;
	/**
	 * Score!
	 */
	int score;
	/**
	 * This is the number of lives PacMan has left.
	 */
	int numLives = 3;
	/**
	 * This is the number of ghosts that we want to generate.
	 */
	int numGhosts = 4;
	/**
	 * This is the number of fish food that we want to generate.
	 */
	int numPowerPellets = 4;
	/**
	 * This is a list of ghosts that are still alive in our game.
	 */
	List<Snail> livingGhosts;
	/**
	 * A reference to a random object, so we can randomize placement of objects in this world.
	 */
	private Random rand = ThreadLocalRandom.current();
	/**
	 * Create a FishGame of a particular size.
	 * @param w how wide is the grid?
	 * @param h how tall is the grid?
	 */
	public FishGame(int w, int h) {
		world = new World(w, h);
		
		// Instantiate our list of alive ghosts.
		livingGhosts = new ArrayList<Snail>();
		
		// Make the number of lives.
		lives = new ArrayList<>();
		 for (int i=0;i<numLives;i++) {
			Fish life = new Fish(0,world);
			life.setPosition(i,0);
			lives.add(life);
			world.register(life);
		}
		
		// Insert pre-configured walls.
		world.insertPacmanBoard();

		// Make our Pac-Man! We use the Fish class for this.
		player = new Fish(0, world);
		player.setPosition(13,20);
		player.markAsPlayer();
		world.register(player);
		
		// Insert ghosts.
		for (int ft = 0; ft < Snail.COLORS.length; ft++) {
			Snail ghost = world.insertSnailRandomly(ft);
			livingGhosts.add(ghost);
		}
		
		// Insert power pellets.
		for (int i=0; i<numPowerPellets; i++) {
			world.insertFruitRandomly();
		}
		
		// Generate some regular pellets.
		// Keep track of how many there are.
		numPellets = 0;
		for (int x=0; x<w; x++) {
			for (int y=0; y<h; y++) {
				if (world.find(x, y).size()==0) {
					FishFood pellet = new FishFood(world);
					pellet.setPosition(x, y);
					numPellets++;
					world.register(pellet);
				}
			}
		}
	
	}
	/**
	 * This method is how the PlayFish app tells whether we're done.
	 * @return true if the player has won (or maybe lost?).
	 */
	public boolean gameOver() {
		// Game over either when PacMan has no lives or when all pellets have been eaten.
		if (lives.isEmpty() || numPellets + numPowerPellets<1) {
			return true; 
		}
		else {
			return false;
		}
	}
	
	public void checkPlayer() {
		// These are all the objects in the world in the same cell as the player.
		List<WorldObject> playerOverlap = this.player.findSameCell();
		// The player is there, too, let's skip them.
		playerOverlap.remove(this.player);
		// If we find an object...
		for (WorldObject wo : playerOverlap) {
			// object is pellet
			if (wo instanceof FishFood) {
				score += 10;
				numPellets--;
				world.remove(wo);
			}
			// object is left or right door of board
			if (wo instanceof Door) {
				// move user to other side of board
				if (this.player.x == world.getWidth()-1) {
					this.player.x = 1;
				} else if (this.player.x == 0) {
					this.player.x = world.getWidth()-2;
				}
			}
			// object is power pellet
			if (wo instanceof PacFruit) {
				this.player.setInvincible();
			}
			// object is ghost
			if (wo instanceof Snail) {
				if (!this.player.isInvincible()) {
					if (lives.size() > 0) {
						world.remove(lives.remove(0));
						player.setPosition(13,20);
					}
				}
				else if (this.player.isInvincible()) {
					world.remove(wo);
					score += 100;
				}
			}
		}
	}
	/**
	 * Update positions of everything (the user has just pressed a button).
	 */
	public void step() {
		for (Snail ghost : livingGhosts) {
			ghost.frozen = this.player.isInvincible();
		}
		// Move the characters on the board and check for overlaps with Pac-Man.
		checkPlayer();
		this.player.step();
		checkPlayer();
		world.stepAll();
		checkPlayer();
	}
}