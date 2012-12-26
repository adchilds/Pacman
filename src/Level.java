import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Level
{
	int level;
	ArrayList<Point> boundaries;
	ArrayList<String> boundariesDir;
	ImageIcon level_image;
	Point start_location;

	public Level(int l, ArrayList<Point> b, ArrayList<String> bd, Point s, String i)
	{
		level = l;
		boundaries = b;
		boundariesDir = bd;
		start_location = s;
		level_image = loadLevelImage("level" + l + "/" + i);
	}

	public void draw(Graphics g)
	{
		g.drawImage(level_image.getImage(), 0, 10, 405, 405, null);
	}

	public void update()
	{
		
	}

	public ImageIcon getLevelImage()
	{
		return level_image;
	}

	public ArrayList<Point> getBoundaries()
	{
		return boundaries;
	}

	public ArrayList<String> getBoundariesDir()
	{
		return boundariesDir;
	}

	public Point getStartPos()
	{
		return start_location;
	}

	public int getLevelNumber()
	{
		return level;
	}

	private ImageIcon loadLevelImage(String i)
	{
		return new ImageIcon(getClass().getResource("levels/" + i));
	}
}