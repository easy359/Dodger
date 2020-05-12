package Game;

import Engine.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Player implements AlarmTask {

    private double x, y;
    private HitBox hitBox;
    private double speed;
    private int health;
    private boolean isAlive, canBePushed, isIn;
    private int color;
    private int[] pixels;
    private Alarm hit, invs;

    public Player() {
        x = 400 - 25;
        y = 300 - 25;
        hitBox = new HitBox(getX(), getY(), 45, 45);
        speed = 100;
        health = 3;
        isAlive = true;
        canBePushed = true;
        isIn = false;
        pixels = new int[hitBox.getWidth() * hitBox.getHeight()];
        hit = new Alarm(this, 0.2);
        hit.pause();
        invs = new Alarm(this, 5);
        invs.pause();
        Static.TIMER.addAlarm(hit);
        Static.TIMER.addAlarm(invs);
        color = Color.yellow.getRGB();
        Arrays.fill(pixels, color);
    }

    public void update(ArrayList<Block> blocks) {
        ArrayList<HitBox> hitBoxes = new ArrayList<HitBox>();
        for (Block block : blocks) {
            hitBoxes.add(block.getHitBox());
        }
        if (canBePushed == false) {
            isIn = false;
            for (HitBox hBox : hitBoxes) {
                if (hitBox.intersects(hBox)) {
                    isIn = true;
                }
            }
            if (UserInput.isKeyPressed(Static.W) || UserInput.isKeyPressed(Static.UP)) {
                y -= speed * GameLoop.delta;
                hitBox.setY(getY());
                if (!(hitBox.fullIntersects(Dodger.boarder))) {
                    y = 0;
                    hitBox.setY(getY());
                }
            }
            if (UserInput.isKeyPressed(Static.A) || UserInput.isKeyPressed(Static.LEFT)) {
                x -= speed * GameLoop.delta;
                hitBox.setX(getX());
                if (!(hitBox.fullIntersects(Dodger.boarder))) {
                    x = 0;
                    hitBox.setX(getX());
                }
            }
            if (UserInput.isKeyPressed(Static.S) || UserInput.isKeyPressed(Static.DOWN)) {
                y += speed * GameLoop.delta;
                hitBox.setY(getY());
                if (!(hitBox.fullIntersects(Dodger.boarder))) {
                    y = Dodger.boarder.getHeight() - hitBox.getHeight();
                    hitBox.setY(getY());
                }
            }
            if (UserInput.isKeyPressed(Static.D) || UserInput.isKeyPressed(Static.RIGHT)) {
                x += speed * GameLoop.delta;
                hitBox.setX(getX());
                if (!(hitBox.fullIntersects(Dodger.boarder))) {
                    x = Dodger.boarder.getWidth() - hitBox.getWidth();
                    hitBox.setY(getY());
                }
            }

        } else {
            if (UserInput.isKeyPressed(Static.W) || UserInput.isKeyPressed(Static.UP)) {
                y -= speed * GameLoop.delta;
                hitBox.setY(getY());
                for (HitBox hBox : hitBoxes) {
                    if (hitBox.intersects(hBox)) {
                        y = hBox.getY() + hBox.getHeight() + 1;
                        hitBox.setY(getY());
                    }
                }
                if (!(hitBox.fullIntersects(Dodger.boarder))) {
                    y = 0;
                    hitBox.setY(getY());
                }
            }
            if (UserInput.isKeyPressed(Static.A) || UserInput.isKeyPressed(Static.LEFT)) {
                x -= speed * GameLoop.delta;
                hitBox.setX(getX());
                for (HitBox hBox : hitBoxes) {
                    if (hitBox.intersects(hBox)) {
                        x = hBox.getX() + hBox.getWidth() + 1;
                        hitBox.setX(getX());
                    }
                }
                if (!(hitBox.fullIntersects(Dodger.boarder))) {
                    x = 0;
                    hitBox.setX(getX());
                }
            }
            if (UserInput.isKeyPressed(Static.S) || UserInput.isKeyPressed(Static.DOWN)) {
                y += speed * GameLoop.delta;
                hitBox.setY(getY());
                for (HitBox hBox : hitBoxes) {
                    if (hitBox.intersects(hBox)) {
                        y = hBox.getY() - hitBox.getHeight() - 1;
                        hitBox.setY(getY());
                    }
                }
                if (!(hitBox.fullIntersects(Dodger.boarder))) {
                    y = Dodger.boarder.getHeight() - hitBox.getHeight();
                    hitBox.setY(getY());
                }
            }
            if (UserInput.isKeyPressed(Static.D) || UserInput.isKeyPressed(Static.RIGHT)) {
                x += speed * GameLoop.delta;
                hitBox.setX(getX());
                for (HitBox hBox : hitBoxes) {
                    if (hitBox.intersects(hBox)) {
                        x = hBox.getX() - hitBox.getWidth() - 1;
                        hitBox.setX(getX());
                    }
                }
                if (!(hitBox.fullIntersects(Dodger.boarder))) {
                    x = Dodger.boarder.getWidth() - hitBox.getWidth();
                    hitBox.setX(getX());
                }
            }
        }
    }

    public int[] render() {
        return pixels;
    }

    public void alarm(int alarmCode) {
        if (alarmCode == hit.getCode()) {
            flipColor();
        } else if (alarmCode == invs.getCode()) {
            if (!isIn) {
                invs.pause();
                hit.pause();
                canBePushed = true;
                invs.changeDelay(5);
                if (color == Color.pink.getRGB()) {
                    flipColor();
                }
            } else {
                invs.changeDelay(0.1);
            }
        }
    }

    private void flipColor() {
        color = (color == Color.yellow.getRGB()) ? Color.pink.getRGB() : Color.yellow.getRGB();
        Arrays.fill(pixels, color);
    }

    public void hit(int dmg) {
        hit.unpause(true);
        invs.unpause(true);
        canBePushed = false;
        health -= dmg;
        if (health < 1) {
            // kill();
        }
    }

    public void kill() {
        isAlive = false;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public boolean canBePushed() {
        return canBePushed;
    }

    public void changeX(double newX) {
        x = newX;
        hitBox.setX(getX());
    }

    public void changeY(double newY) {
        y = newY;
        hitBox.setY(getY());
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

    public HitBox getHitBox() {
        return hitBox;
    }

}
