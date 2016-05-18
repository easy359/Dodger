package Game;

import java.awt.Color;
import java.util.ArrayList;

import Engine.GameLoop;
import Engine.HitBox;

public class Block {

	private Direction dir;
	private double x, y;
	private int width, height;
	private HitBox hitBox;
	private double speed;
	private int[] pixels;

	public Block(Direction dir, HitBox hitBox, int speed) {
		this.dir = dir;
		this.x = hitBox.getX();
		this.y = hitBox.getY();
		this.width = hitBox.getWidth();
		this.height = hitBox.getHeight();
		this.hitBox = hitBox;
		this.speed = (speed < 200 && speed > 50) ? speed : speed * 2;
		pixels = new int[width * height];
		int color = 0;
		int v = 0;
		if (speed > 50 && speed < 66) {
			Color red = new Color(Math.abs(v - (55 + (speed * 2))), v, v);
			color = red.getRGB();
		} else if (speed >= 66 && speed < 100) {
			Color green = new Color(v, Math.abs(v - (55 + (speed + 50))), v);
			color = green.getRGB();
		} else if (speed >= 100 && speed < 200) {
			Color blue = new Color(v, v, Math.abs(v - (55 + speed)));
			color = blue.getRGB();
		}
		for (int yy = 0; yy < height; yy++) {
			for (int xx = 0; xx < width; xx++) {
				pixels[width * yy + xx] = color;
			}
		}
	}

	public void update(Player player, ArrayList<Block> blocks) {
		if (dir == Direction.UP) {
			y -= speed * GameLoop.delta;
			hitBox.setY(getY());
			if (hitBox.intersects(player.getHitBox()) && player.canBePushed()) {
				HitBox temp = new HitBox(hitBox.getX(), hitBox.getY(), hitBox.getWidth(), 1);
				if (temp.intersects(player.getHitBox())) {
					player.changeY(y - player.getHitBox().getHeight());
					for (Block block : blocks) {
						if (player.getHitBox().intersects(block.getHitBox())) {
							player.hit(1);
							break;
						}
					}
					if (!(player.getHitBox().fullIntersects(Dodger.boarder))) {
						player.changeY(0);
						player.hit(1);
					}
				}
			}
		} else if (dir == Direction.LEFT) {
			x -= speed * GameLoop.delta;
			hitBox.setX(getX());
			if (hitBox.intersects(player.getHitBox()) && player.canBePushed()) {
				HitBox temp = new HitBox(hitBox.getX(), hitBox.getY(), 1, hitBox.getHeight());
				if (temp.intersects(player.getHitBox())) {
					player.changeX(x - player.getHitBox().getWidth());
					for (Block block : blocks) {
						if (player.getHitBox().intersects(block.getHitBox())) {
							player.hit(1);
							break;
						}
					}
					if (!(player.getHitBox().fullIntersects(Dodger.boarder))) {
						player.changeX(0);
						player.hit(1);
					}
				}
			}
		} else if (dir == Direction.DOWN) {
			y += speed * GameLoop.delta;
			hitBox.setY(getY());
			if (hitBox.intersects(player.getHitBox()) && player.canBePushed()) {
				HitBox temp = new HitBox(hitBox.getX(), hitBox.getY() + (hitBox.getHeight() - 1), hitBox.getWidth(), 1);
				if (temp.intersects(player.getHitBox())) {
					player.changeY(temp.getY() + 1);
					for (Block block : blocks) {
						if (player.getHitBox().intersects(block.getHitBox())) {
							player.hit(1);
							break;
						}
					}
					if (!(player.getHitBox().fullIntersects(Dodger.boarder))) {
						player.changeY(Dodger.boarder.getHeight() - player.getHitBox().getHeight());
						player.hit(1);
					}
				}
			}
		} else if (dir == Direction.RIGHT) {
			x += speed * GameLoop.delta;
			hitBox.setX(getX());
			if (hitBox.intersects(player.getHitBox()) && player.canBePushed()) {
				HitBox temp = new HitBox(hitBox.getX() + (hitBox.getWidth() - 1), hitBox.getY(), 1, hitBox.getHeight());
				if (temp.intersects(player.getHitBox())) {
					player.changeX(temp.getX() + 1);
					for (Block block : blocks) {
						if (player.getHitBox().intersects(block.getHitBox())) {
							player.hit(1);
							break;
						}
					}
					if (!(player.getHitBox().fullIntersects(Dodger.boarder))) {
						player.changeX(Dodger.boarder.getWidth() - player.getHitBox().getWidth());
						player.hit(1);
					}
				}
			}
		}
	}

	public int[] render() {
		return pixels;
	}

	public int getX() {
		double dAbs = Math.abs(x);
		int i = (int) dAbs;
		if (dAbs - i < 0.5) {
			return x < 0 ? -i : i;
		} else {
			return x < 0 ? -(i + 1) : i + 1;
		}
	}

	public int getY() {
		double dAbs = Math.abs(y);
		int i = (int) dAbs;
		if (dAbs - i < 0.5) {
			return y < 0 ? -i : i;
		} else {
			return y < 0 ? -(i + 1) : i + 1;
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public HitBox getHitBox() {
		return hitBox;
	}

	public enum Direction {
		UP, LEFT, DOWN, RIGHT;
	}

}
