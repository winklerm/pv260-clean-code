package cz.muni.fi.pv260.redhat.elements.app.output;

import java.util.Collection;

import cz.muni.fi.pv260.redhat.elements.app.impl.Element;

/**
 * Formatting of {@link Element} instances as ASCI block
 */
public class BlockFormatter implements ElementFormatter {

    @Override
    public String format(Element element) {
        String s = "";
        s += "-------------------------------------------------------------------------------------------------------";
        s += System.lineSeparator();
        s += row("#", "Symbol", "Name", "Standard State", "Group Block", "Year");
        s += System.lineSeparator();
        s += "=======================================================================================================";
        s += System.lineSeparator();
        s += row(String.valueOf(element.atomic_number()), element.symbol(), element.name(), element.standard_date(), element.group_block(), String.valueOf(element.year_discovered()));
        s += System.lineSeparator();
        s += "-------------------------------------------------------------------------------------------------------";
        return s;
    }

    @Override
    public String format(Collection<Element> elements) {
        String s = "";
        s += "-------------------------------------------------------------------------------------------------------";
        s += System.lineSeparator();
        s += row("#", "Symbol", "Name", "Standard State", "Group Block", "Year");
        s += System.lineSeparator();
        s += "=======================================================================================================";
        s += System.lineSeparator();

        for (var e : elements) {
            s += row(String.valueOf(e.atomic_number()), e.symbol(), e.name(), e.standard_date(), e.group_block(), String.valueOf(e.year_discovered())) + System.lineSeparator();
            s += "-------------------------------------------------------------------------------------------------------" + System.lineSeparator();
        }
        return s;
    }

    private String row(String... data) {
        if (data.length != 6) throw new IllegalArgumentException("Incorrect number of values");
        return String.format("|%10s|%10s|%20s|%20s|%25s|%10s|", data[0], data[1], data[2], data[3], data[4], data[5]);
    }
}
