package Engine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameLoop extends JPanel implements Runnable {

	public static double delta = 0;
	private static final long serialVersionUID = 1L;

	private Game game;
	private UserInput input;

	private JFrame frame;
	private BufferedImage image;
	private int[] pixels;

	private Thread thread;

	private String title;
	private int width, height;
	private int ups, fps;
	private boolean isRunning;

	public GameLoop(Game game, String title, int width, int height, int ups, int fps) {
		this.game = game;
		input = new UserInput();
		this.title = title;
		this.width = width;
		this.height = height;
		this.ups = ups;
		this.fps = fps;
		isRunning = false;

		setPreferredSize(new Dimension(width, height));
		setFocusable(true);
		requestFocus();

		addKeyListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
		addMouseWheelListener(input);

		frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.add(this);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

		thread = new Thread(this);
	}

	public void start() {
		isRunning = true;
		thread.start();
	}

	public void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		final double upsDelay = 1.0 / ups;
		final double fpsDelay = 1.0 / fps;

		double upsStart = System.nanoTime() / Static.NS;
		double fpsStart = upsStart;

		double upsNext = upsStart + upsDelay;
		double fpsNext = fpsStart + fpsDelay;

		double upsLast = upsStart;

		int frames = 0;
		int lastFrames = 0;
		int updates = 0;
		int lastUpdates = 0;
		while (isRunning) {
			double now = System.nanoTime() / Static.NS;
			if (now >= upsNext) {
				double d = now - upsLast;
				delta = d < 0.1 ? d : 0.1;
				update();
				updates++;
				upsNext += upsDelay;
				upsLast = now;
			}
			if (now >= fpsNext) {
				render();
				frames++;
				fpsNext += fpsDelay;
			}
			if (now - upsStart >= 1) {
				frame.setTitle(title + " - " + updates + " | " + lastFrames);
				lastUpdates = updates;
				updates = 0;
				upsStart = now;
			}
			if (now - fpsStart >= 1) {
				frame.setTitle(title + " - " + lastUpdates + " | " + frames);
				lastFrames = frames;
				frames = 0;
				fpsStart = now;
			}
		}
	}

	private void update() {
		input.update();
		game.update();
		Static.TIMER.update();
	}

	private void render() {
		Static.addPixels(pixels, width, height, game.render(), 0, 0, width, height);
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.black);
		g.fillRect(0, 0, width, height);
		g.drawImage(image, 0, 0, width, height, null);
		g.dispose();
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game newGame) {
		game = newGame;
	}

	public int[] getPixels() {
		return pixels;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String newTitle) {
		title = newTitle;
		frame.setTitle(title);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isRunning() {
		return isRunning;
	}
}
