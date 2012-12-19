import java.awt.Graphics;

import javax.swing.ImageIcon;

public abstract class GameEntity
{
	protected ImageIcon image; // Image of the object
	protected ImageIcon backGroundImage; // Background image
	protected String color; // Color of the object
	protected int posX, posY; // X and Y location of the object
	protected int height, width; // Height and Width of the object
	
	abstract void draw(Graphics g);
	abstract void update();

	protected ImageIcon loadImage(String i) {
		return new ImageIcon(getClass().getResource("images/" + i));
	}

	public void setColor(String c)
	{
		color = c;
	}

	public String getColor()
	{
		return color;
	}

	public void setPositionX(int x)
	{
		posX = x;
	}

	public int getPositionX()
	{
		return posX;
	}

	public void setPositionY(int y)
	{
		posY = y;
	}

	public int getPositionY()
	{
		return posY;
	}

	public void setHeight(int h)
	{
		height = h;
	}

	public int getHeight()
	{
		return height;
	}

	public void setWidth(int w)
	{
		width = w;
	}

	public int getWidth()
	{
		return width;
	}

	public void setImage(ImageIcon i)
	{
		image = i;
	}

	public ImageIcon getImage()
	{
		return image;
	}

	public void setBackground(ImageIcon i)
	{
		backGroundImage = i;
	}

	public ImageIcon getBackground()
	{
		return backGroundImage;
	}
}