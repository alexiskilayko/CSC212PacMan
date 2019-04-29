package edu.smith.cs.csc212.p2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

import me.jjfoley.gfx.GFX;
import me.jjfoley.gfx.IntPoint;
import me.jjfoley.gfx.TextBox;

/**
 * This is the graphical <b><i>view</i></b> of our "FishGame" class. Don't worry
 * about modifying this file for P2.
 * 
 * @author jfoley
 */
public class PlayFish extends GFX {
	/**
	 * Game size (visual). Try changing this to 600.
	 */
	public static int VISUAL_GRID_SIZE = 560;
	/**
	 * Game size (logical).
	 */
	public static int LOGICAL_GRID_SIZE = 28;
	/**
	 * The words appear in the top part of the screen.
	 */
	public static int TOP_PART = 50;
	/**
	 * There's a border to make it look pretty (the board is inset by this much).
	 */
	public static int BORDER = 5;
	/**
	 * This is where the game logic lives.
	 */
	FishGame game;
	/**
	 * This TextBox wraps up making fonts and centering text.
	 */
	TextBox gameState = new TextBox("");
	/**
	 * This is a rectangle representing the TOP_PART of the screen.
	 */
	Rectangle2D topRect;

	/**
	 * Construct a new fish game.
	 */
	public PlayFish() {
		super(VISUAL_GRID_SIZE + BORDER * 2, VISUAL_GRID_SIZE + BORDER * 2 + TOP_PART);
		game = new FishGame(LOGICAL_GRID_SIZE, LOGICAL_GRID_SIZE);
		gameState.color = Color.WHITE;
		gameState.setFont(TextBox.BOLD_FONT);
		gameState.setFontSize(TOP_PART / 3.0);
		topRect = new Rectangle2D.Double(0, 0, getWidth(), TOP_PART);
	}
	/**
	 * How big is a tile?
	 * @return this returns the tile width.
	 */
	private int getTileW() {
		return VISUAL_GRID_SIZE / game.world.getWidth();
	}
	/**
	 * How big is a tile?
	 * @return this returns the tile height.
	 */
	private int getTileH() {
		return VISUAL_GRID_SIZE / game.world.getWidth();
	}
	/**
	 * Draw the game state.
	 */
	@Override
	public void draw(Graphics2D g) {
		// Background of window is dark-dark green.
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		// Get a a reference to the game world to draw.
		World world = game.world;

		// Draw TOP_PART TextBox.
		this.gameState.centerInside(this.topRect);
		this.gameState.draw(g);

		// Slide the world down, and into the box.
		// This makes our rendering of the board easier.
		g.translate(BORDER, BORDER + TOP_PART);

		// Use the tile-sizes.
		int tw = getTileW();
		int th = getTileH();

		// Draw the ocean (not the whole screen).
		g.setColor(Color.black);
		g.fillRect(0, 0, tw * world.getWidth(), th * world.getHeight());
		// Draw a grid to better picture how the game works.
		g.setColor(Color.black);
		for (int x = 0; x < world.getWidth(); x++) {
			for (int y = 0; y < world.getHeight(); y++) {
				g.drawRect(x * tw, y * th, tw, th);
			}
		}

		// For everything in our world:
		for (WorldObject wo : world.viewItems()) {
			// Draw it with a 1x1 graphical world, with the center right in the middle of the tile.
			// I fiddled with this translate to get pixel-perfect. Maybe there's a nicer way, but it works for now.
			Graphics2D forWo = (Graphics2D) g.create();
			forWo.translate((int) ((wo.getX() + 0.5) * tw) + 1, (int) ((wo.getY() + 0.5) * th) + 1);
			forWo.scale(tw, th);
			wo.draw(forWo);
			forWo.dispose();
		}
	}
	int delay = 6;
	/**
	 * We separate our "PlayFish" game logic update here.
	 * @param secondsSinceLastUpdate - my GFX code can tell us how long it is between each update, but we don't actually care here.
	 */
	@Override
	public void update(double secondsSinceLastUpdate) {
		// Handle game-over and restart.
		if (game.gameOver()) {
			if (game.numPellets<1) {
				this.gameState.setString("You win! Click anywhere start again!");
			}
			else {
				this.gameState.setString("You lost! Click anywhere start again!");
			}
			if (this.processClick() != null) {
				this.game = new FishGame(LOGICAL_GRID_SIZE, LOGICAL_GRID_SIZE);
			}
			return;
		}
		
		// Update the text in the TextBox.
		this.gameState.setString("Score: "+ game.score);

		// Read the state of the keyboard:
		boolean up = this.processKey(KeyEvent.VK_W) || this.processKey(KeyEvent.VK_UP);
		boolean down = this.processKey(KeyEvent.VK_S) || this.processKey(KeyEvent.VK_DOWN);
		boolean left = this.processKey(KeyEvent.VK_A) || this.processKey(KeyEvent.VK_LEFT);
		boolean right = this.processKey(KeyEvent.VK_D) || this.processKey(KeyEvent.VK_RIGHT);
		boolean skip = this.processKey(KeyEvent.VK_SPACE);
		
		if (up) {
			this.game.player.movingUp = true;
			this.game.player.movingDown = false;
			this.game.player.movingRight = false;
			this.game.player.movingLeft = false;
		} else if (down) {
			this.game.player.movingUp = false;
			this.game.player.movingDown = true;
			this.game.player.movingRight = false;
			this.game.player.movingLeft = false;
		} else if (right) {
			this.game.player.movingUp = false;
			this.game.player.movingDown = false;
			this.game.player.movingRight = true;
			this.game.player.movingLeft = false;
		} else if (left) {
			this.game.player.movingUp = false;
			this.game.player.movingDown = false;
			this.game.player.movingRight = false;
			this.game.player.movingLeft = true;
		}
		
		delay --;
		
		if (delay == 0) {		
			this.game.step();
			delay = 6;
		}
	}
	/**
	 * Create and start the game!
	 * @param args - not run from the command line so no args are used.
	 */
	public static void main(String[] args) {
		PlayFish game = new PlayFish();
		game.start();
	}

}
