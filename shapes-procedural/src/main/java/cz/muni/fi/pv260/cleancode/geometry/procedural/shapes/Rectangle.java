package cz.muni.fi.pv260.cleancode.geometry.procedural.shapes;

import cz.muni.fi.pv260.cleancode.geometry.procedural.Point;

public class Rectangle implements Shape {
    private Point topLeft;
    private double width;
    private double height;

    public Rectangle(Point topLeft, double width, double height) {
        this.topLeft = topLeft;
        this.width = width;
        this.height = height;
    }

    public Point getTopLeft() {
        return this.topLeft;
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }
}
