package cz.muni.fi.pv260.redhat.elements.app.impl;

import cz.muni.fi.pv260.redhat.elements.app.output.Messages;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemErrAndOutNormalized;
import static cz.muni.fi.pv260.redhat.elements.app.output.Messages.No_element_found;

@ExtendWith(SoftAssertionsExtension.class)
class ApplicationTest {

    @InjectSoftAssertions
    SoftAssertions softly;

    @Test
    void printsElementBySymbol() throws Exception {
        List<Element> elements = List.of(
                new Element(1, "H", "Hydrogen", "gas", "nonmetal", 1766),
                new Element(2, "He", "Helium", "gas", "noble gas", 1868),
                new Element(3, "Li", "Lithium", "solid", "alkali metal", 1817),
                new Element(113, "Nh", "Nihonium", "", "post-transition metal", 2003),
                new Element(115, "Mc", "Moscovium", "", "post-transition metal", 2003)
        );

        Element actualShort = executeAndCovert("-s", "He");
        Element actualLong = executeAndCovert("--symbol", "He");

        softly.assertThat(actualShort).isEqualTo(elements.get(1));
        softly.assertThat(actualLong).isEqualTo(elements.get(1));
    }

    @Test
    void printsElementByName() throws Exception {
        List<Element> elements = List.of(
                new Element(1, "H", "Hydrogen", "gas", "nonmetal", 1766),
                new Element(2, "He", "Helium", "gas", "noble gas", 1868),
                new Element(3, "Li", "Lithium", "solid", "alkali metal", 1817),
                new Element(113, "Nh", "Nihonium", "", "post-transition metal", 2003),
                new Element(115, "Mc", "Moscovium", "", "post-transition metal", 2003)
        );

        Element actual = executeAndCovert("--name", "Hydrogen");

        softly.assertThat(actual).isEqualTo(elements.get(0));
    }

    @Test
    void printsElementByAtomicNumber() throws Exception {
        List<Element> elements = List.of(
                new Element(1, "H", "Hydrogen", "gas", "nonmetal", 1766),
                new Element(2, "He", "Helium", "gas", "noble gas", 1868),
                new Element(3, "Li", "Lithium", "solid", "alkali metal", 1817),
                new Element(113, "Nh", "Nihonium", "", "post-transition metal", 2003),
                new Element(115, "Mc", "Moscovium", "", "post-transition metal", 2003)
        );

        Element actualShort = executeAndCovert("-n", "1");
        Element actualLong = executeAndCovert("--number", "1");

        softly.assertThat(actualShort).isEqualTo(elements.get(0));
        softly.assertThat(actualLong).isEqualTo(elements.get(0));
    }

    @Test
    void printsErrorOnWhenElementNotFound() throws Exception {
        String actual = execute("-n", "333");

        softly.assertThat(actual).contains(No_element_found);
    }

    @Test
    void printsElementByYear() throws Exception {
        List<Element> elements = List.of(
                new Element(1, "H", "Hydrogen", "gas", "nonmetal", 1766),
                new Element(2, "He", "Helium", "gas", "noble gas", 1868),
                new Element(3, "Li", "Lithium", "solid", "alkali metal", 1817),
                new Element(113, "Nh", "Nihonium", "", "post-transition metal", 2003),
                new Element(115, "Mc", "Moscovium", "", "post-transition metal", 2003)
        );

        List<Element> actualShort = executeAndCovertList("-y", "2003");
        List<Element> actualLong = executeAndCovertList("--year", "2003");

        softly.assertThat(actualShort).containsExactly(elements.get(3), elements.get(4));
        softly.assertThat(actualLong).contains(elements.get(3), elements.get(4));
    }

    @Test
    void printsErrorOnMultipleFilters() throws Exception {
        String actual = execute("-n", "1", "-s", "H");

        softly.assertThat(actual).contains(Messages.SINGLE_FILTER_REQUIRED);
    }

    private String execute(String... args) throws Exception {
        return tapSystemErrAndOutNormalized(() -> {
            List<String> cmd = new ArrayList<>();
            Collections.addAll(cmd, args);

            Application.main(cmd.toArray(new String[0]));
        });
    }

    private Element executeAndCovert(String... args) throws Exception {
        String output = execute(args);
        return elementsFromTable(output).findAny().get();
    }

    private List<Element> executeAndCovertList(String... args) throws Exception {
        String output = execute(args);
        return elementsFromTable(output).collect(Collectors.toList());
    }

    private Stream<Element> elementsFromTable(String table) {
        return table.lines()
                .skip(3)
                .filter(l -> l.startsWith("|"))
                .map(this::rowToElement);
    }

    private Element rowToElement(String row) {
        List<String> data = Arrays.stream(row.split("\\|"))
                .skip(1)
                .map(String::trim)
                .collect(Collectors.toList());
        return new Element(
                Integer.parseInt(data.get(0)),
                data.get(1),
                data.get(2),
                data.get(3),
                data.get(4),
                Integer.parseInt(data.get(5))
        );
    }

}