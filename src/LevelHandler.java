import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class LevelHandler
{
	private int currentLevel;
	private SAXBuilder builder;
	private Document doc;
	private Element root;
	private Level level;
	private ArrayList<Point> boundaries;
	private ArrayList<String> boundariesDir;
	private Point pacman_start;
	private String image_name;
	private long high_score;

	public LevelHandler(int cl)
	{
		currentLevel = cl;
		loadLevelFromFile();
		level = new Level(cl, boundaries, boundariesDir, pacman_start, image_name, high_score);
	}

	/**
	 * <p>Reads information from a .pac file that contains
	 * level information which will be converted to graphic
	 * format.
	 * 
	 * @param f The level file that we'll need to create the level from
	 */
	public boolean loadLevelFromFile()
	{
		try
		{
			builder = new SAXBuilder();
			doc = builder.build(new File("src/levels/level" + currentLevel + "/level" + currentLevel + ".xml"));
			root = doc.getRootElement();
		} catch (Exception e) {
			// Program must not have been installed. Install it!
			System.out.println( "Level file could not be found!" );
		}

		// Get the level image name
		image_name = root.getChildText("image");

		// Get the boundaries
		boundaries = new ArrayList<Point>();
		boundariesDir = new ArrayList<String>();
		List<?> b = root.getChild("boundaries").getChildren();
		
		int x, y, x2, y2;
		String dir;
		for (int i = 0; i < b.size(); i++)
		{
			x = Integer.parseInt(root.getChild("boundaries").getChild("b" + i).getChildText("x"));
			y = Integer.parseInt(root.getChild("boundaries").getChild("b" + i).getChildText("y"));
			x2 = Integer.parseInt(root.getChild("boundaries").getChild("b" + i).getChildText("x2"));
			y2 = Integer.parseInt(root.getChild("boundaries").getChild("b" + i).getChildText("y2"));
			dir = root.getChild("boundaries").getChild("b" + i).getChildText("dir");

			for(int m = 0; m <= (Math.abs(x - x2) + 1); m++)
				for (int n = 0; n <= (Math.abs(y - y2) + 1); n++)
				{
					boundaries.add(new Point(m + x, n + y));
					boundariesDir.add(dir);
				}
		}

		// Get pacman's starting location
		x = Integer.parseInt(root.getChild("pacman_start").getChildText("x"));
		y = Integer.parseInt(root.getChild("pacman_start").getChildText("y"));
		pacman_start = new Point(x, y);

		// High score for the current level
		high_score = Long.parseLong(root.getChildText("high_score"));

		return true;
	}

	public void listLevelFiles()
	{
		// Directory path here
		String path = "src/levels/"; 

		String file;
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles(); 
		for (int i = 0; i < listOfFiles.length; i++) 
		{
			if (listOfFiles[i].isFile() && !listOfFiles[i].getName().contains(".DS")) 
			{
				file = listOfFiles[i].getName();
				System.out.println(file);
			}
		}
	}

	/**
	 * <p>Gets the player's current level
	 * @return int representing current level
	 */
	public int getCurrentLevel()
	{
		return currentLevel;
	}

	public Level getLevel()
	{
		return level;
	}
}