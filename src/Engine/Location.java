package Engine;

public class Location {

    private int x, y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int newX) {
        x = newX;
    }

    public int getY() {
        return y;
    }

    public void setY(int newY) {
        y = newY;
    }

    public void setLocation(Location newLocation) {
        setLocation(newLocation.getX(), newLocation.getY());
    }

    public void setLocation(int newX, int newY) {
        x = newX;
        y = newY;
    }

    public String toString() {
        return x + " : " + y;
    }
}
