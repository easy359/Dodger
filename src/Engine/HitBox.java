package Engine;

import java.awt.*;

public class HitBox {
    private int x, y;
    private int width, height;
    private Rectangle rectangle;

    public HitBox(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        rectangle = new Rectangle(x, y, width, height);
    }

    public HitBox(HitBox hitBox) {
        x = hitBox.getX();
        y = hitBox.getY();
        width = hitBox.getWidth();
        height = hitBox.getHeight();
        rectangle = new Rectangle(hitBox.rectangle);
    }

    public void setBounds(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        rectangle.setBounds(x, y, width, height);
    }

    public void setHitBox(HitBox other) {
        setBounds(other.x, other.y, other.width, other.height);
    }

    public boolean intersects(HitBox other) {
        return rectangle.intersects(other.rectangle);
    }

    public boolean fullIntersects(HitBox other) {

        if (!(other.rectangle.contains(x, y))) {
            return false;
        }
        if (!(other.rectangle.contains((x + width - 1), y))) {
            return false;
        }
        if (!(other.rectangle.contains(x, (y + height - 1)))) {
            return false;
        }
        if (!(other.rectangle.contains((x + width - 1), (y + height - 1)))) {
            return false;
        }

        return true;
    }

    public boolean centerIntersects(HitBox other) {
        Point one = new Point((x + width - 1) / 2, (y + height - 1) / 2);
        return (other.rectangle.contains(one)) ? true : false;
    }

    public int getDistanceX2toOX1(HitBox other) {
        return Math.abs(other.x - (x + width - 1));
    }

    public int getDistanceX1toOX2(HitBox other) {
        return Math.abs(((other.x + other.width - 1) - x));
    }

    public int getDistanceY2toOY1(HitBox other) {
        return Math.abs(other.y - (y + height - 1));
    }

    public int getDistanceY1toOY2(HitBox other) {
        return Math.abs((other.y + other.height - 1) - y);
    }

    public int getDistanceX2toOX2(HitBox other) {
        return Math.abs((other.x + other.width) - (x + width));
    }

    public int getDistanceX1toOX1(HitBox other) {
        return Math.abs(other.x - x);
    }

    public int getDistanceY2toOY2(HitBox other) {
        return Math.abs((other.y + other.height) - (y + height));
    }

    public int getDistanceY1toOY1(HitBox other) {
        return Math.abs(other.y - y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int newX) {
        x = newX;
        rectangle.setBounds(x, y, width, height);
    }

    public void setY(int newY) {
        y = newY;
        rectangle.setBounds(x, y, width, height);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int newWidth) {
        width = newWidth;
    }

    public void setHeight(int newHeight) {
        height = newHeight;
    }

}
