package cz.muni.fi.pv260.redhat.elements.csv;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Reading and parsing of CSV data
 */
public class CsvParserImpl implements CsvParser {


    private final String delimiter;
    private final Charset charset;

    /**
     * Creates instance with {@link CsvParser#DEFAULT_DELIMITER}
     */
    public CsvParserImpl() {
        this(DEFAULT_DELIMITER, DEFAULT_CHARSET);
    }

    /**
     * Creates instance with given delimiter
     *
     * @param delimiter delimiter used to separate values
     * @param charset character encoding
     */
    public CsvParserImpl(String delimiter, Charset charset) {
        this.delimiter = delimiter;
        this.charset = charset;
    }

    @Override
    public BeheadedCsvReader open(Path path) throws IOException {
       return open(Files.newInputStream(path));
    }

    @Override
    public BeheadedCsvReader open(InputStream is) throws UnsupportedEncodingException {
        return new BeheadedCsvReader(is, delimiter, charset);
    }

    @Override
    public HeadedCsvReader openWithHeader(Path path) throws IOException {
        return openWithHeader(Files.newInputStream(path));
    }

    @Override
    public HeadedCsvReader openWithHeader(InputStream is) throws IOException {
        return new HeadedCsvReader(is, delimiter, charset);
    }

    @Override
    public List<List<String>> readAll(Path path) throws IOException {
        List<List<String>> data = new ArrayList<>();
        try(BeheadedCsvReader reader = open(path)) {
            reader.forEach(data::add);
        }
        return data;
    }

    @Override
    public List<Map<String, String>> readAllWithHeader(Path path) throws IOException {
        List<Map<String, String>> data = new ArrayList<>();
        try(HeadedCsvReader reader = openWithHeader(path)) {
            reader.forEach(data::add);
        }
        return data;
    }
}
