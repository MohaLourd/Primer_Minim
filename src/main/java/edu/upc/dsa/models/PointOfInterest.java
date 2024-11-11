package edu.upc.dsa.models;

public class PointOfInterest {
    private int x;
    private int y;
    private ElementType type;

    public PointOfInterest() {}

    public PointOfInterest(int x, int y, ElementType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    // Getters and Setters
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public ElementType getType() { return type; }
    public void setType(ElementType type) { this.type = type; }
}