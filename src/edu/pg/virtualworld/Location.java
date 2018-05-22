package edu.pg.virtualworld;

public class Location {
    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Location(Location newLocation) {
        x = newLocation.x;
        y = newLocation.y;
    }

    public int x, y;

    public static Location getRandomLocation(int width, int height) {
        return new Location((int) (Math.random() * width), (int) (Math.random() * height));
    }

    public Location add(Location right) {
        return new Location(right.x + x, right.y + y);
    }

    public void changeTo(int newX, int newY) {
        x = newX;
        y = newY;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Location) {
            Location l = (Location) o;
            return (l.x == this.x && l.y == this.y);
        } else return super.equals(o);
    }
}