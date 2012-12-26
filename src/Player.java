import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Player extends GameEntity
{
	private boolean mouth_open = true;
	private int lives;
	private long score;
	private long prevTime = System.currentTimeMillis() / 100; // Seconds
	private Direction direction;
	private Player_State player_state;
	private ImageIcon pacman_closed, pacman_down, pacman_left,
						pacman_right, pacman_up;
	private Level currentLevel;
	private ArrayList<Point> pArr;
	private ArrayList<String> sArr;

	public Player(Level l, int state, int dir, int x, int y, int w, int h, ImageIcon i)
	{
		setPlayerState(state);
		setPlayerDirection(dir);
		setLives(3);
		currentLevel = l;
		posX = x;
		posY = y;
		height = h;
		width = w;
		image = i;
		score = 0;

		if (currentLevel != null)
			setBoundaries();

		pacman_closed = loadImage( "pacman_closed.png" );
		pacman_down = loadImage( "pacman_down.png" );
		pacman_left = loadImage( "pacman_left.png" );
		pacman_right = loadImage( "pacman_right.png" );
		pacman_up = loadImage( "pacman_up.png" );
	}

	public enum Direction
	{
		RIGHT, // Player is moving right
		LEFT, // Player is moving left
		UP, // Player is moving up
		DOWN, // Player is moving down
		STOPPED // Player is not moving
	}

	public enum Player_State
	{
		START_SCREEN, // Player is on the start screen
		ALIVE, // Player is currently alive
		DEAD, // Player has died
		IMMUNE // Player is immune to ghosts
	}

	@Override
	public void draw(Graphics g)
	{
		g.drawImage(image.getImage(), posX, posY, width, height, null);
	}

	@Override
	public void update()
	{
		long curTime = System.currentTimeMillis() / 100; // Seconds

		// Animate mouth
		if ((curTime - prevTime) > 1.75)
		{
			if (mouth_open)
			{
				image = pacman_closed;
				mouth_open = false;
				prevTime = curTime;
			} else {
				if (direction == Direction.RIGHT)
					image = pacman_right;
				else if (direction == Direction.LEFT)
					image = pacman_left;
				else if (direction == Direction.UP)
					image = pacman_up;
				else if (direction == Direction.DOWN)
					image = pacman_down;

				mouth_open = true;
				prevTime = curTime;
			}
		}

		/*
		 * Update pacman's position every cycle and if he has
		 * gone off the screen, we need to reset him to the
		 * other side.
		 */
		switch(player_state)
		{
			case START_SCREEN:
				if (direction == Direction.RIGHT)
				{
					posX++; // Increase X
					if (posX > 405)
						posX = -30;
				}
				else if (direction == Direction.LEFT)
				{
					posX--; // Decrease X

					if (posX < -30)
						posX = 435;
				}
				break;

			case ALIVE:
				if (direction == Direction.RIGHT)
				{
					if (inBounds())
					{
						posX++; // Increase X
						if (posX > 405)
							posX = -30;
					} else {
						direction = Direction.STOPPED;
					}
				}
				else if (direction == Direction.LEFT)
				{
					if (inBounds())
					{
						posX--; // Decrease X
	
						if (posX < -30)
							posX = 435;
					} else {
						direction = Direction.STOPPED;
					}
				}
				else if (direction == Direction.UP)
				{
					if (inBounds())
					{
						posY--; // Decrease Y
	
						if (posY < -30)
							posY = 435;
					} else {
						direction = Direction.STOPPED;
					}
				}
				else if (direction == Direction.DOWN)
				{
					if (inBounds())
					{
						posY++; // Increase Y
	
						if (posY > 435)
							posY = -30;
					} else {
						direction = Direction.STOPPED;
					}
				}
				else if (direction == Direction.STOPPED)
				{
					// Don't do anything
				}
				break;

			default:
				break;
		}
	}

	/**
	 * <p>Checks to make sure pacman is not offscreen or somewhere he's
	 * not supposed to be (i.e.: a set boundary)
	 * @return true if pacman is okay, false otherwise
	 */
	public boolean inBounds()
	{
		Point curPos = new Point(posX, posY);
		int a = pArr.indexOf(curPos); // Does the coordinate exist as a boundary?

		if (a == -1)
			return true;
		else
		{
			// If the boundary exists, is the player going the correct direction
			// where we want to stop them?
			if (Direction.valueOf(sArr.get(a).toUpperCase()) == direction)
				return false;
		}
		return true;
	}

	/**
	 * <p>Get the number of lives this player instance has left
	 * @return an int representing how many lives the play currently has
	 */
	public int getLives()
	{
		return lives;
	}

	/**
	 * <p>Set the player's lives
	 * 
	 * @param l an int to set the player's lives to
	 */
	public void setLives(int l)
	{
		lives = l;
	}

	/**
	 * <p>Get's the current state of the player instance. This is represented
	 * by an enum Player_State which may return one of several values.
	 * 
	 *	<br>
	 *	<br><b>START_SCREEN</b> - Player is currently in the start menu
	 * 	<br><b>ALIVE</b> - Player is currently playing Pacman
	 * 	<br><b>DEAD</b> - Player has died
	 * 	<br><b>IMMUNE</b> - Player is currently immune to the ghosts
	 * @return	an enum value representing this player's current state:
	 */
	public Player_State getPlayerState()
	{
		return player_state;
	}

	/**
	 * <p>Set's the player's state to the given Player_State value
	 * 
	 * @param ps a Player_State enum value representing the player's current game play state
	 *	 		<br><i>(0) START_SCREEN</i> - Player is currently in the start menu
	 * 			<br><i>(1) ALIVE</i> - Player is currently playing Pacman
	 * 			<br><i>(2) DEAD</i> - Player has died
	 * 			<br><i>(3) IMMUNE</i> - Player is currently immune to the ghosts
	 */
	public void setPlayerState(Player_State ps)
	{
		player_state = ps;
	}

	/**
	 * <p>Set's the player's state to the given int value (which represents
	 * a Player_State enum value)
	 * 
	 * @param s an int value representing the player's current game play state:
	 * 			<br><i>(0) START_SCREEN</i> - Player is currently in the start menu
	 * 			<br><i>(1) ALIVE</i> - Player is currently playing Pacman
	 * 			<br><i>(2) DEAD</i> - Player has died
	 * 			<br><i>(3) IMMUNE</i> - Player is currently immune to the ghosts
	 */
	public void setPlayerState(int s)
	{
		switch(s)
		{
			case 0:
				player_state = Player_State.START_SCREEN;
				break;
			case 1:
				player_state = Player_State.ALIVE;
				break;
			case 2:
				player_state = Player_State.DEAD;
				break;
			case 3:
				player_state = Player_State.IMMUNE;
				break;

			default:
				break;
		}
	}

	/**
	 * Get's the current direction the player is moving.
	 * 
	 * 	<br>
	 *	<br><i>Direction.RIGHT</i> - Player is moving right
	 *	<br><i>Direction.LEFT</i> - Player is moving left
	 *	<br><i>Direction.UP</i> - Player is moving up
	 *	<br><i>Direction.DOWN</i> - Player is moving down
	 *	<br><i>Direction.STOPPED</i> - Player is not moving
	 * @return a Direction enum value, representing the player's current movement direction:
	 */
	public Direction getPlayerDirection()
	{
		return direction;
	}

	/**
	 * <p>Sets the player's direction to the given Direction value
	 * 
	 * @param d a Direction enum value representing the player's direction of movement:
	 * 		<br><i>Direction.RIGHT</i> - Player is moving right
	 *		<br><i>Direction.LEFT</i> - Player is moving left
	 *		<br><i>Direction.UP</i> - Player is moving up
	 *		<br><i>Direction.DOWN</i> - Player is moving down
	 *		<br><i>Direction.STOPPED</i> - Player is not moving
	 */
	public void setPlayerDirection(Direction d)
	{
		direction = d;
	}

	/**
	 * <p>Sets the player's direction to the given int value (which represents
	 * a Direction enum value)
	 * 
	 * @param d a Direction enum value representing the player's direction of movement:
	 * 		<br><i>Direction.RIGHT</i> - Player is moving right
	 *		<br><i>Direction.LEFT</i> - Player is moving left
	 *		<br><i>Direction.UP</i> - Player is moving up
	 *		<br><i>Direction.DOWN</i> - Player is moving down
	 *		<br><i>Direction.STOPPED</i> - Player is not moving
	 */
	public void setPlayerDirection(int d)
	{
		switch(d)
		{
			case 0:
				direction = Direction.RIGHT;
				break;
			case 1:
				direction = Direction.LEFT;
				break;
			case 2:
				direction = Direction.UP;
				break;
			case 3:
				direction = Direction.DOWN;
				break;
			case 4:
				direction = Direction.STOPPED;
				break;

			default:
				break;
		}
	}

	/**
	 * <p>Gets the player's current score
	 * @return a long value representing the player's current score
	 */
	public long getScore()
	{
		return score;
	}

	/**
	 * <p>Sets the player's score to the given long value
	 * @param s a long value representing the player's current score
	 */
	public void setScore(long s)
	{
		score = s;
	}

	/**
	 * <p>Loads an image from the image folder, given an image filename
	 * @param a String representing the image's filename
	 */
	public ImageIcon loadImage(String i)
	{
		return new ImageIcon(getClass().getResource("images/" + i));
	}

	/**
	 * <p>A helper method to set the boundaries for pacman
	 * for the current level
	 */
	public void setBoundaries()
	{
		pArr = currentLevel.getBoundaries();
		sArr = currentLevel.getBoundariesDir();
	}
}