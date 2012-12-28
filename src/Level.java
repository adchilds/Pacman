import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Level
{
	int level;
	ArrayList<Point> boundaries;
	ImageIcon level_image;
	Point start_location;
	long high_score;

	public Level(int l, ArrayList<Point> b, Point s, String i, long hs)
	{
		level = l;
		boundaries = b;
		start_location = s;
		level_image = loadLevelImage("level" + l + "/" + i);
		high_score = hs;
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

	public Point getStartPos()
	{
		return start_location;
	}

	public int getLevelNumber()
	{
		return level;
	}

	public long getHighScore()
	{
		return high_score;
	}

	private ImageIcon loadLevelImage(String i)
	{
		return new ImageIcon(getClass().getResource("levels/" + i));
	}
}