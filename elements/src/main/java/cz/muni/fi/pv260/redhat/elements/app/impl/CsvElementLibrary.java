package cz.muni.fi.pv260.redhat.elements.app.impl;

import cz.muni.fi.pv260.redhat.elements.app.ElementLibrary;
import cz.muni.fi.pv260.redhat.elements.csv.CsvParser;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Library of chemical elements backed by CSV data source
 */
public class CsvElementLibrary implements ElementLibrary {

    CsvParser parser;
    URL resource;

    public CsvElementLibrary(CsvParser csvParser, URL resource) {
        this.parser = csvParser;
        this.resource = resource;
    }

    @Override
    public Optional<Element> findByAtomicNumber(int n) {
        for (var e : getElements()) if (e.atomic_number() == n) return Optional.of(e);
        return Optional.empty();
    }

    @Override
    public Optional<Element> findBySymbol(String s) {
        for (var e : getElements()) if (e.symbol().equals(s)) return Optional.of(e);
        return Optional.empty();
    }

    @Override
    public Optional<Element> findByName(String n) {
        for (var e : getElements()) if (e.name().equals(n)) return Optional.of(e);
        return Optional.empty();
    }

    @Override
    public List<Element> findByYear(int y) {
        var list = new ArrayList<Element>();
        for (var e : getElements()) if (e.year_discovered() == y) list.add(e);
        return list;
    }

    private Element toElement(Map<String, String> data) {
        return new Element(Integer.parseInt(data.get("atomicNumber")),
                data.get("symbol"), data.get("name"), data.get("standardState"),
                data.get("groupBlock"), Integer.parseInt(data.get("yearDiscovered")));
    }

    public List<Element> getElements() {
        List<Element> elements = new ArrayList<>();
        try (var reader = parser.openWithHeader(resource.openStream())) {
            reader.forEach(row -> elements.add(toElement(row)));
        } catch (IOException e) {
            System.out.println("Unable to read elements");
        }
        return elements;
    }
}
