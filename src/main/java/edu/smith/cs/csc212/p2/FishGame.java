package edu.smith.cs.csc212.p2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import me.jjfoley.gfx.IntPoint;

/**
 * This class manages our model of gameplay: missing and found fish, etc.
 * @author jfoley
 *
 */
public class FishGame {
	/**
	 * This is the world in which the fish are missing. (It's mostly a List!).
	 */
	World world;
	/**
	 * The player (a Fish.COLORS[0]-colored fish) goes seeking their friends.
	 */
	Fish player;
	/**
	 * PacMan's other lives
	 */
	List<Fish> lives;
	/**
	 * This is the food that fish can eat.
	 */
	FishFood food;
	/**
	 * Number of steps!
	 */
	int stepsTaken;
	/**
	 * Number of pellets left
	 */
	int numPellets;
	/**
	 * Score!
	 */
	int score;
	/**
	 * This is the number of lives PacMan has left
	 */
	int numLives = 3;
	/**
	 * This is the number of snails that we want to generate.
	 */
	int numGhosts = 4;
	/**
	 * This is the number of fish food that we want to generate.
	 */
	int numPowerPellets = 4;
	
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
				
		livingGhosts = new ArrayList<Snail>();
		
		world.insertRockRandomly();

		player = new Fish(0, world);
		player.setPosition(13,20);
		player.markAsPlayer();
		world.register(player);
		
		for (int ft = 0; ft < Snail.COLORS.length; ft++) {
			Snail ghost = world.insertSnailRandomly(ft);
			livingGhosts.add(ghost);
		}
		
		for (int i=0; i<numPowerPellets; i++) {
			world.insertFruitRandomly();
		}
		
		// Generate some pellets.
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
		
		// Make the number of lives.
		lives = new ArrayList<>();
		 for (int i=0;i<numLives;i++) {
			Fish life = new Fish(0,world);
			life.setPosition(i,0);
			lives.add(life);
			world.register(life);
		}
	}
	/**
	 * This method is how the PlayFish app tells whether we're done.
	 * @return true if the player has won (or maybe lost?).
	 */
	public boolean gameOver() {
		// Game over only if there are no fish in both the missing and found lists, i.e. all fish are home.
		if (lives.isEmpty() || numPellets + numPowerPellets<1) {
			return true; 
		}
		else {
			return false;
		}
	}
	/**
	 * Update positions of everything (the user has just pressed a button).
	 */
	public void step() {
		// Keep track of how long the game has run.
		this.stepsTaken += 1;
		// These are all the objects in the world in the same cell as the player.
		List<WorldObject> playerOverlap = this.player.findSameCell();
		// The player is there, too, let's skip them.
		playerOverlap.remove(this.player);
		// If we find an object...
		for (WorldObject wo : playerOverlap) {
			if (wo instanceof FishFood) {
				score += 10;
				numPellets--;
				world.remove(wo);
			}
			if (wo instanceof PacFruit) {
				this.player.invincible = true;
				for (Snail ghost : livingGhosts) {
					ghost.frozen = true;
				}
			}
			if (wo instanceof Snail) {
				if (!this.player.invincible) {
					world.remove(lives.remove(0));
				}
				else if (this.player.invincible) {
					world.remove(wo);
					score += 100;
				}
			}
		}
		world.stepAll();
	}
}