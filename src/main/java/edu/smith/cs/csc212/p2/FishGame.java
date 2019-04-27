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
	 * This is the food that fish can eat.
	 */
	FishFood food;
	/**
	 * Number of steps!
	 */
	//int stepsTaken;
	/**
	 * Score!
	 */
	int score;
	/**
	 * This is the number of rocks that we want to generate.
	 */
	//public static final int NUM_ROCKS = 20;
	/**
	 * This is the number of snails that we want to generate.
	 */
	public static final int NUM_SNAILS = 3;
	/**
	 * This is the number of fish food that we want to generate.
	 */
	public static final int NUM_FOOD = 4;
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
		
		/*missing = new ArrayList<Fish>();
		found = new ArrayList<Fish>();
		atHome = new ArrayList<Fish>(); // Instantiate a list of fish that have returned home.*/
				
		// Generate some normal rocks.
		//for (int i=0; i<NUM_ROCKS/2; i++) {
			world.insertRockRandomly();
		//}
		
		
		// (lab) Make the snail!
		for (int i=0; i<NUM_SNAILS; i++) {
			world.insertSnailRandomly();
		}
				
		// Make the player out of the 0th fish color.
		player = new Fish(0, world);
		// Start the player at "home".
		player.setPosition(13,20);
		player.markAsPlayer();
		world.register(player);
		
		// Generate fish of all the colors but the first into the "missing" List.
		/*for (int ft = 1; ft < Fish.COLORS.length; ft++) {
			Fish friend = world.insertFishRandomly(ft);
			missing.add(friend);
		}*/
		
		// Generate pieces of fish food at random places.
		for (int i=0; i<NUM_FOOD; i++) {
			world.insertFruitRandomly();
		}
		
		// Generate some pellets.
				for (int x=0; x<w; x++) {
					for (int y=0; y<h; y++)
						if(world.find(x, y).size()==0) {
							FishFood pellet = new FishFood(world);
							pellet.setPosition(x, y);
							world.register(pellet);
						}
				}
	}
	
	
	/**
	 * How we tell if the game is over: if missingFishLeft() == 0.
	 * @return the size of the missing list.
	 */
	/*public int missingFishLeft() {
		return missing.size();
	}*/
	
	/**
	 * This method is how the PlayFish app tells whether we're done.
	 * @return true if the player has won (or maybe lost?).
	 */
	public boolean gameOver() {
		// Game over only if there are no fish in both the missing and found lists, i.e. all fish are home.
		return false;
	}

	/**
	 * Update positions of everything (the user has just pressed a button).
	 */
	public void step() {
		// Keep track of how long the game has run.
		//this.stepsTaken += 1;
				
		// These are all the objects in the world in the same cell as the player.
		List<WorldObject> playerOverlap = this.player.findSameCell();
		// The player is there, too, let's skip them.
		playerOverlap.remove(this.player);
		
		// If we find an object...
		for (WorldObject wo : playerOverlap) {
			// If we find a fish...
			// A fish is missing if it's in our missing list.
			/*if (missing.contains(wo)) {
				// Remove this fish from the missing list.
				missing.remove(wo);
				
				// Remove from world.
				// (lab): add to found instead! (So we see objectsFollow work!)
				Fish fish = (Fish)wo;
				found.add(fish);
				
				// Increase score when you find a fish!
				score += fish.points;
			// If we find food, score increases and remove from world.
			}*/ if (wo instanceof FishFood) {
				score += 10;
				world.remove(wo);
			// If we find the fish home, return our following fish and remove them from world.
			} /* else if (wo instanceof FishHome) {
				for (Fish f : found) {
					atHome.add(f);
				}
				for (Fish f : atHome) {
					found.remove(f);
					world.remove(f);
				} 
			}*/
		} 
		
		// If wandering fish find the fish food, remove food from world. Score does not increase.
		/*for (Fish f : missing) {
			// Find the objects that overlap with wandering fish.
			List<WorldObject> fishOverlap = f.findSameCell();
			// Exclude the player fish.
			fishOverlap.remove(this.player);
			for (WorldObject wo : fishOverlap) {
				if (wo instanceof FishFood) {
					world.remove(wo);
				}
			}
		}*/
									 
		// Make sure missing fish *do* something.
		/*wanderMissingFish();
		// When fish get added to "found" they will follow the player around.
		World.objectsFollow(player, found);*/
		// Step any world-objects that run themselves.
		world.stepAll();
	}
		
	/**
	 * Call moveRandomly() on all of the missing fish to make them seem alive.
	 */
	/*private void wanderMissingFish() {
		Random rand = ThreadLocalRandom.current();
		for (Fish lost : missing) {
			// 30% of the time, lost fish move randomly.
			if ((lost.fastScared == false) && (rand.nextDouble() < 0.3)) {
				lost.moveRandomly();
			// 80% of the time, lost fish move randomly.
			} else if ((lost.fastScared == true) && (rand.nextDouble() < 0.8)) {
				lost.moveRandomly();
			}
		}
	}*/

	/**
	 * This gets a click on the grid. We want it to destroy rocks that ruin the game.
	 * @param x - the x-tile.
	 * @param y - the y-tile.
	 */
	/*public void click(int x, int y) {
		System.out.println("Clicked on: "+x+","+y+ " world.canSwim(player,...)="+world.canSwim(player, x, y));
		List<WorldObject> atPoint = world.find(x, y);
		// If there is a rock at the point where the user clicks, remove.
		for (WorldObject point : atPoint) {
			if (point.isRock() == true) { 
				point.remove();
			}
		}
	}*/
	
}
