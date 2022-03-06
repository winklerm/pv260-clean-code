package cz.muni.fi.pv260.redhat.elements.app.proxy;

import java.net.URL;
import java.util.List;
import java.util.Optional;

import cz.muni.fi.pv260.redhat.elements.app.ElementLibrary;
import cz.muni.fi.pv260.redhat.elements.app.impl.CsvElementLibrary;
import cz.muni.fi.pv260.redhat.elements.app.impl.Element;
import cz.muni.fi.pv260.redhat.elements.csv.CsvParser;

/**
 * Library of chemical elements - proxy object.
 */
public class ElementLibraryProxy implements ElementLibrary {

    CsvElementLibrary csvElementLibrary;

    public ElementLibraryProxy(CsvParser csvParser, URL resource) {
        this.csvElementLibrary = new CsvElementLibrary(csvParser, resource);
    }

    @Override
    public Optional<Element> findByAtomicNumber(int n) {
        return this.csvElementLibrary.findByAtomicNumber(n);
    }

    @Override
    public Optional<Element> findBySymbol(String s) {
        return this.csvElementLibrary.findBySymbol(s);
    }

    @Override
    public Optional<Element> findByName(String n) {
        return this.csvElementLibrary.findByName(n);
    }

    @Override
    public List<Element> findByYear(int y) {
        return this.csvElementLibrary.findByYear(y);
    }
}
