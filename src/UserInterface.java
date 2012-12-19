import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * <p>The UserInterface class handles the view of the game and input
 * from the user.
 * 
 * @author Adam Childs
 * @version 0.01
 * @since 0.01
 */
public class UserInterface extends JFrame implements ActionListener, KeyListener
{
	private static final long serialVersionUID = 1L;
	private Image image;
	private ImageIcon start_menu, start_menu_arrow;
	private Game_State game_state = Game_State.START_SCREEN;
	private Arrow_Pos arrow_pos = Arrow_Pos.START_GAME;
	private Graphics graphics;
	private double version = 0.01;
	private ArrayList<Character> level;
	private ArrayList<Integer> blue_wall, small_dot, big_dot,
							pacman_start, ghost_house, ghost_house_exit,
							fruit, nothing, nextline;
	private Player pac = new Player(0, 0, -30, 175, 30, 30, loadImage( "pacman_right.png" ));
	private Player p = new Player(1, 1, 0, 0, 30, 30, loadImage( "pacman_left.png" ));

	public UserInterface()
	{
		createAndShowGUI();
	}

	/*
	 * Start screen's current arrow position
	 */
	private enum Arrow_Pos
	{
		START_GAME,
		CREDITS,
		HELP,
		QUIT
	}

	/*
	 * Current game state
	 */
	private enum Game_State
	{
		START_SCREEN,
		IN_GAME,
		GAME_OVER
	}

	public void createAndShowGUI()
	{
		setTitle( "Pacman" );
		setContentPane(getContentPane());
		addKeyListener(this);

		start_menu = loadImage( "start_menu.jpeg" );
		start_menu_arrow = loadImage( "start_menu_arrow.png" );

		setSize(405, 405);
		setResizable(false);
		setFocusable(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public ImageIcon loadImage(String i)
	{
		return new ImageIcon(getClass().getResource("images/" + i));
	}

	public void paint(Graphics g)
	{
		image = createImage(getWidth(), getHeight());
		graphics = image.getGraphics();

		paintComponent(graphics);
		g.drawImage(image, 0, 0, null);

		repaint();
	}

	public void paintComponent(Graphics g)
	{
		/*
		 * Check what the current game_state is and draw
		 * images according to the current state
		 */
		switch(game_state)
		{
			case START_SCREEN:
				g.drawImage(start_menu.getImage(), 0, 0, 405, 405, null);
				
				// Set the arrow point to the correct String
				int xPos, yPos;
				switch(arrow_pos)
				{
					case START_GAME:
						xPos = 275;
						yPos = 260;
						break;

					case CREDITS:
						xPos = 250;
						yPos = 290;
						break;

					case HELP:
						xPos = 235;
						yPos = 320;
						break;

					case QUIT:
						xPos = 235;
						yPos = 350;
						break;

					default:
						xPos = 275;
						yPos = 260;
						break;
				}
				g.drawImage(start_menu_arrow.getImage(), xPos, yPos, 45, 23, null);

				// Animate the ghosts chasing pacman across the screen
				pac.draw(g);
				pac.update();
				
				//g.setColor(Color.YELLOW);
				setForeground(Color.YELLOW);
				g.setFont(new Font("Dialog", Font.PLAIN, 25));
				g.drawString("Start Game", 135, 280);
				g.drawString("Credits", 160, 310);
				g.drawString("Help", 175, 340);
				g.drawString("Quit", 175, 370);
				break;

			case IN_GAME:
				// While the player has enough lives
				if (p.getLives() > 0)
				{
					//for (int i = 0; i < blue_wall.size(); i++)
						//g.drawImage(bluewall.getImage(), )
				}
				break;

			default:
				break;
		}
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP)
		{
			/*
			 * Check the arrows current location and move it accordingly
			 */
			switch (arrow_pos)
			{
				case START_GAME:
					arrow_pos = Arrow_Pos.QUIT;
					break;
				case CREDITS:
					arrow_pos = Arrow_Pos.START_GAME;
					break;
				case HELP:
					arrow_pos = Arrow_Pos.CREDITS;
					break;
				case QUIT:
					arrow_pos = Arrow_Pos.HELP;
					break;

				default:
					break;
			}
		}
		else if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			/*
			 * Check the arrows current location and move it accordingly
			 */
			switch (arrow_pos)
			{
				case START_GAME:
					arrow_pos = Arrow_Pos.CREDITS;
					break;
				case CREDITS:
					arrow_pos = Arrow_Pos.HELP;
					break;
				case HELP:
					arrow_pos = Arrow_Pos.QUIT;
					break;
				case QUIT:
					arrow_pos = Arrow_Pos.START_GAME;
					break;

				default:
					break;
			}
		}
		else if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			/*
			 * Check the arrows current location and choose
			 * the correct action
			 */
			switch(arrow_pos)
			{
				case START_GAME:
					game_state = Game_State.IN_GAME;
					LevelHandler lh = new LevelHandler();
					level = lh.loadLevelFromFile(new File("src/levels/level1.pac"));
					
					blue_wall = new ArrayList<Integer>();
					small_dot = new ArrayList<Integer>();
					big_dot = new ArrayList<Integer>();
					pacman_start = new ArrayList<Integer>();
					ghost_house = new ArrayList<Integer>();
					ghost_house_exit = new ArrayList<Integer>();
					fruit = new ArrayList<Integer>();
					nothing = new ArrayList<Integer>();
					nextline = new ArrayList<Integer>();

					// Loop over the level and load the correct image for each value
					for(int i = 0; i < level.size(); i++)
					{
						switch(level.get(i))
						{
							case 'B': // Blue wall
								blue_wall.add(i);
								break;
								
							case '.': // Small circle
								small_dot.add(i);
								break;
								
							case 'o': // Big circle
								big_dot.add(i);
								break;
								
							case 'X': // Pacman start
								pacman_start.add(i);
								break;
								
							case 'G': // Ghost house
								ghost_house.add(i);
								break;
								
							case 'E': // Ghost house exit
								ghost_house_exit.add(i);
								break;
								
							case 'F': // Possible fruit location
								fruit.add(i);
								break;
								
							case ' ': // Nothing
								nothing.add(i);
								break;
								
							case '\n': // ENDLINE
								nextline.add(i);
								break;

							default:
								break;
						}
					}

					break;
				case CREDITS:
					JOptionPane.showMessageDialog(this,
							"Adam Childs - adchilds@eckerd.edu",
							"Credits",
							JOptionPane.INFORMATION_MESSAGE);
					break;
				case HELP:
					DecimalFormat fmt = new DecimalFormat("#0.00");
					JOptionPane.showMessageDialog(this, "Current version: v" + fmt.format(version) + "\n" +
							"Created by Adam Childs" + "\n" +
							"adchilds@eckerd.edu",
							"Pacman v" + fmt.format(version),
							JOptionPane.INFORMATION_MESSAGE);
					break;
				case QUIT:
					System.exit(0);
					break; // Technically don't need this, but to keep consistency...

				default:
					break;
			}
		}
		else if (e.getKeyCode() == KeyEvent.VK_D)
		{
			pac.setPlayerDirection(new Random().nextInt(2));
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		
	}
}