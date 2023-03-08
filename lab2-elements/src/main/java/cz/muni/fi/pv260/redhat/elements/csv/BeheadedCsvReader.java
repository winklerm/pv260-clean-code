package cz.muni.fi.pv260.redhat.elements.csv;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * {@link CsvReader implementation for plain CSV data
 */
public class BeheadedCsvReader extends AbstractCsvReader<List<String>> {

    /**
     * Creates {@link CsvReader} instance with given stream and delimiter
     *
     * @param is input stream of CSV data
     * @param delimiter delimiter
     * @param charset character encoding
     * @throws  UnsupportedEncodingException on invalid character encoding
     */
    public BeheadedCsvReader(InputStream is, String delimiter, Charset charset) throws UnsupportedEncodingException {
        super(is, delimiter, charset);
    }

    @Override
    public List<String> read() throws IOException {
        return readLine();
    }
}
