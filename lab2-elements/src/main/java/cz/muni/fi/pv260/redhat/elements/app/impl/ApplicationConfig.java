package cz.muni.fi.pv260.redhat.elements.app.impl;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Provides the default application configuration.
 */
public final class ApplicationConfig {

    /**
     * Default output format
     */
    public static final String FORMAT = "plain";

    /**
     * Default delimiter is a comma
     */
    public static final String DEFAULT_DELIMITER = ",";

    /**
     * Default character encoding
     */
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
}
