import java.awt.Graphics;

public class Player extends GameEntity
{
	private int lives;
	private Player_State player_state;

	public Player()
	{
		setPlayerState(Player_State.ALIVE);
		setLives(3);
	}

	public enum Player_State
	{
		ALIVE,	// Player is currently alive
		DEAD,	// Player has died
		IMMUNE	// Player is immune to ghosts
	}

	@Override
	public void draw(Graphics g)
	{
		
	}

	@Override
	public void update()
	{
		
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
}