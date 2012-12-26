import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
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
public class UserInterface extends JFrame implements ActionListener, KeyListener, MouseListener
{
	private static final long serialVersionUID = 1L;
	
	private boolean start_menu_initiated = false, game_objects_initialized = false, keypressed = false;
	private double version = 0.01;
	int arrowXPos = 275, arrowYPos = 260;
	private Arrow_Pos arrow_pos = Arrow_Pos.START_GAME;
	private Game_State game_state = Game_State.START_SCREEN;
	private Ghost gr, gp, gt, go;
	private Graphics graphics;
	private Image image;
	private ImageIcon start_menu, start_menu_arrow;
	private Player menu_pacman, player;
	private LevelHandler lh;

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
		addMouseListener(this);

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
				if (keypressed) // Only update if a key has been pressed
				{
					switch(arrow_pos)
					{
						case START_GAME:
							arrowXPos = 275;
							arrowYPos = 260;
							break;
	
						case CREDITS:
							arrowXPos = 250;
							arrowYPos = 290;
							break;
	
						case HELP:
							arrowXPos = 235;
							arrowYPos = 320;
							break;
	
						case QUIT:
							arrowXPos = 235;
							arrowYPos = 350;
							break;
	
						default:
							arrowXPos = 275;
							arrowYPos = 260;
							break;
					}
					keypressed = false;
				}
				g.drawImage(start_menu_arrow.getImage(), arrowXPos, arrowYPos, 45, 23, null);

				// Animate the ghosts chasing pacman across the screen
				if (!start_menu_initiated)
				{
					menu_pacman = new Player(null, 0, 0, -30, 175, 30, 30, loadImage( "pacman_right.png" ));
					gr = new Ghost(menu_pacman.getX() - 50, menu_pacman.getY(), loadImage( "ghost_red.png" ));
					gp = new Ghost(gr.getX() - 50, menu_pacman.getY(), loadImage( "ghost_pink.png" ));
					gt = new Ghost(gp.getX() - 50, menu_pacman.getY(), loadImage( "ghost_teal.png" ));
					go = new Ghost(gt.getX() - 50, menu_pacman.getY(), loadImage( "ghost_orange.png" ));
					start_menu_initiated = true;
				}
				menu_pacman.draw(g);
				menu_pacman.update();
				gr.draw(g);
				gr.update();
				gp.draw(g);
				gp.update();
				gt.draw(g);
				gt.update();
				go.draw(g);
				go.update();

				// Draw the menu options
				setForeground(Color.YELLOW); // g.setColor(Color.YELLOW);
				g.setFont(new Font("Dialog", Font.PLAIN, 25));
				g.drawString("Start Game", 135, 280);
				g.drawString("Credits", 160, 310);
				g.drawString("Help", 175, 340);
				g.drawString("Quit", 175, 370);
				break;

			case IN_GAME:
				g.setColor(Color.BLACK);

				if (!game_objects_initialized)
				{
					Point start_pos = lh.getLevel().getStartPos();
					
					// Level information:
					System.out.println( "Level: " + lh.getLevel().getLevelNumber());
					System.out.println( "Starting pos: (" + start_pos.x + ", " + start_pos.y + ")" );
					System.out.println( "Boundaries: " + lh.getLevel().getBoundaries() );
					player = new Player(lh.getLevel(), 1, 1, start_pos.x, start_pos.y, 18, 18, loadImage( "pacman_closed.png" ));
					game_objects_initialized = true;
				}

				// While the player has enough lives
				if (player.getLives() > 0)
				{
					// Render the level
					lh.getLevel().draw(g);
					lh.getLevel().update();
					
					// Render the player
					player.draw(g);
					player.update();
				} else {
					
				}
				break;

			default:
				break;
		}
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		keypressed = true;

		if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP)
		{
			if (game_state == Game_State.START_SCREEN)
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
			} else if (game_state == Game_State.IN_GAME) {
				player.setPlayerDirection(2);
			}
		}
		else if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			if (game_state == Game_State.START_SCREEN)
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
			} else if (game_state == Game_State.IN_GAME) {
				player.setPlayerDirection(3);
			}
		}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
		{
			if (game_state == Game_State.START_SCREEN)
			{
				
			} else if (game_state == Game_State.IN_GAME) {
				player.setPlayerDirection(1);
			}
		}
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
		{
			if (game_state == Game_State.START_SCREEN)
			{
				
			} else if (game_state == Game_State.IN_GAME) {
				player.setPlayerDirection(0);
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

					lh = new LevelHandler(1);
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
		else if (e.getKeyCode() == KeyEvent.VK_D) // Testing
		{
			menu_pacman.setPlayerDirection(new Random().nextInt(2));
		}
		else if (e.getKeyCode() == KeyEvent.VK_G) // Testing
		{
			player.setPlayerDirection(4);
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

	@Override
	public void mouseClicked(MouseEvent e)
	{
		System.out.println("(" + e.getX() + ", " + e.getY() + ")");
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}