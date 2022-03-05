package cz.muni.fi.pv260.cleancode.geometry.procedural;

import cz.muni.fi.pv260.cleancode.geometry.procedural.shapes.Rectangle;
import cz.muni.fi.pv260.cleancode.geometry.procedural.shapes.Shape;
import cz.muni.fi.pv260.cleancode.geometry.procedural.shapes.Square;

/**
 * Utility class providing basic geometrical operations on {@link Shape} implementations.
 */
public class Geometry {

    private Geometry() {
    }

    /**
     * Returns the geometric area occupied by a given shape.
     *
     * @param shape the Shape which area to calculate
     * @return the calculated area
     * @throws UnsupportedShapeException when the given shape is not supported by this operation
     */
    public static double area(Shape shape) {
        if (shape instanceof Square) {
            Square square = (Square) shape;
            return square.getSide() * square.getSide();
        } else if (shape instanceof Rectangle) {
            Rectangle rectangle = (Rectangle) shape;
            return rectangle.getHeight() * rectangle.getWidth();
        } else {
            throw new UnsupportedShapeException("Unsupported shape class: " + shape.getClass());
        }
    }

    /**
     * Returns the geometric perimeter occupied by a given shape.
     *
     * @param shape the Shape which perimeter to calculate
     * @return the calculated perimeter length
     * @throws UnsupportedShapeException when the given shape is not supported by this operation
     */
    public static double perimeter(Shape shape) {
        throw new UnsupportedOperationException();
    }
}
