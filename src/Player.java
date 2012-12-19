import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Player extends GameEntity
{
	private boolean mouth_open = true;
	private int lives;
	private long prevTime = System.currentTimeMillis() / 100; // Seconds
	private Direction direction;
	private Player_State player_state;
	private ImageIcon pacman_closed, pacman_down, pacman_left,
						pacman_right, pacman_up;

	public Player(int state, int dir, int x, int y, int w, int h, ImageIcon i)
	{
		setPlayerState(state);
		setPlayerDirection(dir);
		setLives(3);
		posX = x;
		posY = y;
		height = h;
		width = w;
		image = i;

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
		DOWN // Player is moving down
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
		switch(player_state)
		{
			case START_SCREEN:
				long curTime = System.currentTimeMillis() / 100; // Seconds

				/*
				 * If pacman has gone off the screen, we need to reset him to the
				 * other side.
				 */
				if (direction == Direction.RIGHT)
				{
					posX++; // Increase Pacman's position by one every cycle
					if (posX > 405)
						posX = -30;
					
					// Animate mouth
					if ((curTime - prevTime) > 1.75)
					{
						if (mouth_open)
						{
							image = pacman_closed;
							mouth_open = false;
							prevTime = curTime;
						} else {
							image = pacman_right;
							mouth_open = true;
							prevTime = curTime;
						}
					}
				}
				else if (direction == Direction.LEFT)
				{
					posX--; // Decrease Pacman's position by one every cycle

					if (posX < -30)
						posX = 435;

					// Animate mouth
					if ((curTime - prevTime) > 1.75)
					{
						if (mouth_open)
						{
							image = pacman_closed;
							mouth_open = false;
							prevTime = curTime;
						} else {
							image = pacman_left;
							mouth_open = true;
							prevTime = curTime;
						}
					}
				}
				break;

			default:
				break;
		}
	}

	public int getLives()
	{
		return lives;
	}

	public void setLives(int l)
	{
		lives = l;
	}

	public Player_State getPlayerState()
	{
		return player_state;
	}

	public void setPlayerState(Player_State ps)
	{
		player_state = ps;
	}

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

	public Direction getPlayerDirection()
	{
		return direction;
	}

	public void setPlayerDirection(Direction d)
	{
		direction = d;
	}

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

			default:
				break;
		}
	}

	public ImageIcon loadImage(String i)
	{
		return new ImageIcon(getClass().getResource("images/" + i));
	}
}