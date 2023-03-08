package cz.muni.fi.pv260.redhat.elements;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestUtils {
    private TestUtils() {
    }

    public static Path resourcePath(String path) throws URISyntaxException {
        return Paths.get(TestUtils.class.getResource(path).toURI());
    }
}