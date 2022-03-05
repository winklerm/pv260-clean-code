package cz.muni.fi.pv260.cleancode.geometry.procedural;

public class UnsupportedShapeException extends RuntimeException {

    public UnsupportedShapeException(String message) {
        super(message);
    }

    public UnsupportedShapeException(String message, Throwable cause) {
        super(message, cause);
    }
}
