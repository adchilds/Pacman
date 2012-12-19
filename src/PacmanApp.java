import javax.swing.SwingUtilities;

/**
 * <p>The PacmanApp class handles loading the program in a thread-safe manner.
 * 
 * @author Adam Childs
 * @version 0.01
 * @since 0.01
 */
class PacmanApp
{
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(
			new Runnable()
			{
				public void run()
				{
					new UserInterface();
				}
			}
		);
	}
}