package cz.muni.fi.pv260.cleancode.geometry.polymorphic;

import cz.muni.fi.pv260.cleancode.geometry.polymorphic.shapes.Rectangle;
import cz.muni.fi.pv260.cleancode.geometry.polymorphic.shapes.Square;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withPrecision;

public class GeometryTest {

    @Test
    public void testSquareArea() {
        final Square square = new Square(new Point(0, 0), 10);
        assertThat(square.area()).isEqualTo(100, withPrecision(0.1));
    }

    @Test
    public void testRectangleArea() {
        final Rectangle rectangle = new Rectangle(new Point(0, 0), 2, 3);
        assertThat(rectangle.area()).isEqualTo(6, withPrecision(0.1));
    }

}
