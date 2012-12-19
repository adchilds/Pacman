import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LevelHandler
{
	
	public LevelHandler()
	{
		
	}

	public void draw(Graphics g)
	{
		
	}

	public void update()
	{
		
	}

	/**
	 * <p>Reads information from a .pac file that contains
	 * level information which will be converted to graphic
	 * format.
	 * 
	 * @param f The level file that we'll need to create the level from
	 */
	public ArrayList<Character> loadLevelFromFile(File f)
	{
		ArrayList<Character> arr = new ArrayList<Character>();

		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(f));
			String line;
			while ((line = br.readLine()) != null)
			{
				// Process the individual lines so that we can build the level
				// B = BLUE WALL
				// . = SMALL CIRCLE
				// o = BIG CIRCLE
				// X = PACMAN START
				// G = GHOST HOUSE
				// E = GHOST HOUSE EXIT
				// F = POSSIBLE FRUIT LOCATION
				for (int i = 0; i < line.length(); i++)
				{
					char c = line.charAt(i);
					switch(c)
					{
						case 'B': // Blue wall
						case '.': // Small circle
						case 'o': // Big circle
						case 'X': // Pacman start
						case 'G': // Ghost house
						case 'E': // Ghost house exit
						case 'F': // Possible fruit location
						case ' ': // Nothing
						case '\n': // ENDLINE
							arr.add(c);
							break;

						default:
							break;
					}
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return arr;
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
}