package cz.muni.fi.pv260.redhat.elements.csv;

import java.nio.charset.Charset;

/**
 * Factory class for CSV processing
 */
public final class CsvToolkit {

    public CsvToolkit() {
    }

    /**
     * Creates instance of {@link CsvParser} with default delimiter and charset
     *
     * @return parser
     */
    public static CsvParser parser() {
        return parser(CsvParser.DEFAULT_DELIMITER, CsvParser.DEFAULT_CHARSET);
    }

    /**
     * Creates instance of {@link CsvParser} with given delimiter
     *
     * @param delimiter value delimiter
     * @param charset character encoding
     * @return parser
     */
    public static CsvParser parser(String delimiter, Charset charset) {
        return new CsvParserImpl(delimiter, charset);
    }
}
