package battle_ground;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	// Set the screen size and scale of the game;
	public static int width = 400;
	public static int height = width / 16 * 9;
	public static int scale = 3;
	
	private Thread thread;
	private JFrame frame;
	private boolean running = false;
	
	private BufferedImage image = new BufferedImage(width, height,
								BufferedImage.TYPE_INT_RGB);
	private int[] pixels =
			((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	
	public Game() {
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size); // Canvas method;
		
		frame = new JFrame();
	}
	
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}
	
	public synchronized void stop() {
		running = false;
		try {
			thread.join();	
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	// Design the game loop;
	@Override
	public void run() {
		while (running) {
			update(); // handles game logics
			render(); // does rendering
		}
	}
	
	public void update() {	
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		
		// fill a rectangle starting from (0, 0) top-left
		g.fillRect(0, 0, getWidth(), getHeight());
		g.dispose();
		bs.show(); // make the buffer visible
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle("Battle Ground");
		game.frame.add(game);
		game.frame.pack();
		
		// close the process (game) when click the 'x' button;
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		game.frame.setLocationRelativeTo(null);
		
		// show the window;
		game.frame.setVisible(true);
		
		game.start();
	}
}

