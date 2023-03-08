package cz.muni.fi.pv260.cleancode.geometry.procedural.shapes;

import cz.muni.fi.pv260.cleancode.geometry.procedural.Point;

public class Square implements Shape {
    private Point topLeft;
    private double side;

    public Square(Point topLeft, double side) {
        this.topLeft = topLeft;
        this.side = side;
    }

    public Point getTopLeft() {
        return this.topLeft;
    }

    public double getSide() {
        return this.side;
    }
}
