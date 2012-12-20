import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Ghost extends GameEntity
{
	
	public Ghost(int x, int y, ImageIcon i)
	{
		posX = x;
		posY = y;
		image = i;
	}

	@Override
	public void draw(Graphics g)
	{
		g.drawImage(image.getImage(), posX, posY, 30, 28, null);
	}

	@Override
	public void update()
	{
		posX++;

		if (posX > 405)
			posX = -30;
	}
}