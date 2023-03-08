package cz.muni.fi.pv260.redhat.elements.csv;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cz.muni.fi.pv260.redhat.elements.TestUtils.resourcePath;
import static org.mockito.Mockito.description;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@ExtendWith(SoftAssertionsExtension.class)
public class CsvParserWithReaderTest {

    @InjectSoftAssertions
    private SoftAssertions softly;

    private CsvParser parser;

    @BeforeEach
    void setUp() {
        parser = CsvToolkit.parser(",", StandardCharsets.UTF_8);
    }

    @Test
    @DisplayName("Loads plain CSV data")
    public void plainCsv() throws Exception {
        var expected = List.of(
                List.of("1", "H", "Hydrogen", "gas", "nonmetal", "1766"),
                List.of("2", "He", "Helium", "gas", "noble gas", "1868"),
                List.of("3", "Li", "Lithium", "solid", "alkali metal", "1817")
        );

        try (var reader = parser.open(resourcePath("/plain.csv"))) {
            for (int i = 0; i < 3; i++) {
                softly.assertThat(reader.read()).isEqualTo(expected.get(i));
            }
            softly.assertThat(reader.read()).isNull();
        }
    }

    @Test
    @DisplayName("Loads plain CSV data from InputStream")
    public void plainCsvFromIS() throws Exception {
        var csv = "1,H,Hydrogen,gas,nonmetal,1766\n" +
                "2,He,Helium,gas,noble gas,1868\n" +
                "3,Li,Lithium,solid,alkali metal,1817\n";

        var expected = List.of(
                List.of("1", "H", "Hydrogen", "gas", "nonmetal", "1766"),
                List.of("2", "He", "Helium", "gas", "noble gas", "1868"),
                List.of("3", "Li", "Lithium", "solid", "alkali metal", "1817")
        );

        var is = spy(new ByteArrayInputStream(csv.getBytes()));
        try (var reader = parser.open(is)) {
            for (int i = 0; i < 3; i++) {
                softly.assertThat(reader.read()).isEqualTo(expected.get(i));
            }
            softly.assertThat(reader.read()).isNull();
        }
        verify(is, description("Input stream was not closed")).close();
    }

    @Test
    @DisplayName("Loads plain CSV data from FileInputStream")
    public void plainCsvFromFIS() throws Exception {
        var expected = List.of(
                List.of("1", "H", "Hydrogen", "gas", "nonmetal", "1766"),
                List.of("2", "He", "Helium", "gas", "noble gas", "1868"),
                List.of("3", "Li", "Lithium", "solid", "alkali metal", "1817")
        );

        var is = spy(Files.newInputStream(resourcePath("/plain.csv")));
        try (var reader = parser.open(is)) {
            for (int i = 0; i < 3; i++) {
                softly.assertThat(reader.read()).isEqualTo(expected.get(i));
            }
            softly.assertThat(reader.read()).isNull();
        }
        verify(is, description("Input stream was not closed")).close();
    }

    @Test
    @DisplayName("Loads plain CSV data with different row lengths")
    public void plainCsvDifferentRowLength() throws Exception {
        var csv = "1,H,Hydrogen,gas,nonmetal,1766\n" +
                "2,He,Helium,gas,noble gas\n" +
                "3,Li,Lithium,solid,alkali metal,1817, extra\n";

        var expected = List.of(
                List.of("1", "H", "Hydrogen", "gas", "nonmetal", "1766"),
                List.of("2", "He", "Helium", "gas", "noble gas"),
                List.of("3", "Li", "Lithium", "solid", "alkali metal", "1817", "extra")
        );

        var is = spy(new ByteArrayInputStream(csv.getBytes()));
        try (var reader = parser.open(is)) {
            for (int i = 0; i < 3; i++) {
                softly.assertThat(reader.read()).isEqualTo(expected.get(i));
            }
            softly.assertThat(reader.read()).isNull();
        }
        verify(is, description("Input stream was not closed")).close();
    }

    @Test
    @DisplayName("Loads plain CSV data via consumer")
    public void plainCsvConsumer() throws Exception {
        var expected = List.of(
                List.of("1", "H", "Hydrogen", "gas", "nonmetal", "1766"),
                List.of("2", "He", "Helium", "gas", "noble gas", "1868"),
                List.of("3", "Li", "Lithium", "solid", "alkali metal", "1817")
        );

        List<List<String>> actual = new ArrayList<>();
        try (var reader = parser.open(resourcePath("/plain.csv"))) {
            reader.forEach(actual::add);
            softly.assertThat(reader.read()).isNull();
        }
        softly.assertThat(actual).containsExactlyElementsOf(expected);
    }

    @Test
    @DisplayName("Loads CSV data with header as plain")
    public void headedCsvAsPlain() throws Exception {
        var expected = List.of(
                List.of("atomicNumber", "symbol", "name", "standardState", "groupBlock", "yearDiscovered"),
                List.of("1", "H", "Hydrogen", "gas", "nonmetal", "1766"),
                List.of("2", "He", "Helium", "gas", "noble gas", "1868"),
                List.of("3", "Li", "Lithium", "solid", "alkali metal", "1817"),
                List.of("4", "Be", "Beryllium", "solid", "alkaline earth metal", "1798")
        );

        try (var reader = parser.open(resourcePath("/header.csv"))) {
            for (int i = 0; i < 5; i++) {
                softly.assertThat(reader.read()).isEqualTo(expected.get(i));
            }
            softly.assertThat(reader.read()).isNull();
        }
    }

    @Test
    @DisplayName("Loads CSV data with header")
    public void headedCsv() throws Exception {
        var expected = List.of(
                Map.of(
                        "atomicNumber", "1",
                        "symbol", "H",
                        "name", "Hydrogen",
                        "standardState", "gas",
                        "groupBlock", "nonmetal",
                        "yearDiscovered", "1766"
                ),
                Map.of(
                        "atomicNumber", "2",
                        "symbol", "He",
                        "name", "Helium",
                        "standardState", "gas",
                        "groupBlock", "noble gas",
                        "yearDiscovered", "1868"
                ),
                Map.of(
                        "atomicNumber", "3",
                        "symbol", "Li",
                        "name", "Lithium",
                        "standardState", "solid",
                        "groupBlock", "alkali metal",
                        "yearDiscovered", "1817"
                ),
                Map.of(
                        "atomicNumber", "4",
                        "symbol", "Be", "name",
                        "Beryllium", "standardState",
                        "solid", "groupBlock",
                        "alkaline earth metal",
                        "yearDiscovered",
                        "1798"
                )
        );

        try (var reader = parser.openWithHeader(resourcePath("/header.csv"))) {
            for (int i = 0; i < 4; i++) {
                softly.assertThat(reader.read()).isEqualTo(expected.get(i));
            }
            softly.assertThat(reader.read()).isNull();
        }
    }

    @Test
    @DisplayName("Loads CSV data with header via consumer")
    public void headedCsvConsumer() throws Exception {
        var expected = List.of(
                Map.of(
                        "atomicNumber", "1",
                        "symbol", "H",
                        "name", "Hydrogen",
                        "standardState", "gas",
                        "groupBlock", "nonmetal",
                        "yearDiscovered", "1766"
                ),
                Map.of(
                        "atomicNumber", "2",
                        "symbol", "He",
                        "name", "Helium",
                        "standardState", "gas",
                        "groupBlock", "noble gas",
                        "yearDiscovered", "1868"
                ),
                Map.of(
                        "atomicNumber", "3",
                        "symbol", "Li",
                        "name", "Lithium",
                        "standardState", "solid",
                        "groupBlock", "alkali metal",
                        "yearDiscovered", "1817"
                ),
                Map.of(
                        "atomicNumber", "4",
                        "symbol", "Be", "name",
                        "Beryllium", "standardState",
                        "solid", "groupBlock",
                        "alkaline earth metal",
                        "yearDiscovered",
                        "1798"
                )
        );

        List<Map<String, String>> actual = new ArrayList<>();
        try (var reader = parser.openWithHeader(resourcePath("/header.csv"))) {
            reader.forEach(actual::add);
            softly.assertThat(reader.read()).isNull();
        }
        softly.assertThat(actual).containsExactlyElementsOf(expected);
    }

    @Test
    @DisplayName("Loads CSV data with header from InputStream")
    public void headedCsvFromIS() throws Exception {
        var csv = "atomicNumber, symbol, name, standardState, groupBlock, yearDiscovered\n" +
                "1,H,Hydrogen,gas,nonmetal,1766\n" +
                "2,He,Helium,gas,noble gas,1868";

        var expected = List.of(
                Map.of(
                        "atomicNumber", "1",
                        "symbol", "H",
                        "name", "Hydrogen",
                        "standardState", "gas",
                        "groupBlock", "nonmetal",
                        "yearDiscovered", "1766"
                ),
                Map.of(
                        "atomicNumber", "2",
                        "symbol", "He",
                        "name", "Helium",
                        "standardState", "gas",
                        "groupBlock", "noble gas",
                        "yearDiscovered", "1868"
                )
        );

        var is = spy(new ByteArrayInputStream(csv.getBytes()));
        try (var reader = parser.openWithHeader(is)) {
            for (int i = 0; i < 2; i++) {
                softly.assertThat(reader.read()).isEqualTo(expected.get(i));
            }
            softly.assertThat(reader.read()).isNull();
        }
        verify(is, description("Input stream was not closed")).close();
    }

    @Test
    @DisplayName("Errors with more elements when loading CSV data with header")
    public void headedCsvHigherRowLength() throws Exception {
        var csv = "atomicNumber, symbol, name, standardState, groupBlock, yearDiscovered\n" +
                "1,H,Hydrogen,gas,nonmetal,1766\n" +
                "2,He,Helium,gas,noble gas,1868, extra";

        var expected = Map.of(
                "atomicNumber", "1",
                "symbol", "H",
                "name", "Hydrogen",
                "standardState", "gas",
                "groupBlock", "nonmetal",
                "yearDiscovered", "1766"
        );

        var is = spy(new ByteArrayInputStream(csv.getBytes()));
        try (var reader = parser.openWithHeader(is)) {
            softly.assertThat(reader.read()).isEqualTo(expected);
            softly.assertThatThrownBy(reader::read)
                    .isInstanceOf(IOException.class)
                    .hasMessageContaining(Messages.INVALID_FORMAT);
        }
        verify(is, description("Input stream was not closed")).close();
    }

    @Test
    @DisplayName("Errors with less elements when loading CSV data with header")
    public void headedCsvHigherLowerLength() throws Exception {
        var csv = "atomicNumber, symbol, name, standardState, groupBlock, yearDiscovered\n" +
                "1,H,Hydrogen,gas,nonmetal,1766\n" +
                "2,He,Helium,gas,noble gas";

        var expected = Map.of(
                "atomicNumber", "1",
                "symbol", "H",
                "name", "Hydrogen",
                "standardState", "gas",
                "groupBlock", "nonmetal",
                "yearDiscovered", "1766"
        );

        var is = spy(new ByteArrayInputStream(csv.getBytes()));
        try (var reader = parser.openWithHeader(is)) {
            softly.assertThat(reader.read()).isEqualTo(expected);
            softly.assertThatThrownBy(reader::read)
                    .isInstanceOf(IOException.class)
                    .hasMessageContaining(Messages.INVALID_FORMAT);
        }
        verify(is, description("Input stream was not closed")).close();
    }

    @Test
    @DisplayName("Loads CSV data with header after error")
    public void headedCsvAfterError() throws Exception {
        var csv = "atomicNumber, symbol, name, standardState, groupBlock, yearDiscovered\n" +
                "1,H,Hydrogen,gas,nonmetal,1766, extra\n" +
                "2,He,Helium,gas,noble gas,1868";

        var expected = Map.of(
                "atomicNumber", "2",
                "symbol", "He",
                "name", "Helium",
                "standardState", "gas",
                "groupBlock", "noble gas",
                "yearDiscovered", "1868"
        );

        var is = spy(new ByteArrayInputStream(csv.getBytes()));
        try (var reader = parser.openWithHeader(is)) {
            softly.assertThatThrownBy(reader::read)
                    .isInstanceOf(IOException.class)
                    .hasMessageContaining(Messages.INVALID_FORMAT);
            softly.assertThat(reader.read()).isEqualTo(expected);
            softly.assertThat(reader.read()).isNull();
        }
        verify(is, description("Input stream was not closed")).close();
    }

    @Test
    @DisplayName("Errors with incorrect number of elements when loading CSV data with header via consumer")
    public void headedCsvErrorConsumer() throws Exception {
        try (var reader = parser.openWithHeader(resourcePath("/header_broken.csv"))) {
            softly.assertThatThrownBy(() -> reader.forEach(row -> {}))
                    .isInstanceOf(IOException.class)
                    .hasMessageContaining(Messages.INVALID_FORMAT);
        }
    }
}
