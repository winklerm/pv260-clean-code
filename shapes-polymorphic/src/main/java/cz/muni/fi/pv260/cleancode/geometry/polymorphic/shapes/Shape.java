package cz.muni.fi.pv260.cleancode.geometry.polymorphic.shapes;

/**
 * A geometrical shape capable of calculating its basic geometric characteristics, such as area.
 */
public interface Shape {

    /**
     * Returns the geometric area occupied by this shape.
     *
     * @return the calculated area
     */
    double area();
}
