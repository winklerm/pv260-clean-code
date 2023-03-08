package cz.muni.fi.pv260.redhat.elements.app;

import cz.muni.fi.pv260.redhat.elements.app.impl.Element;

import java.util.List;
import java.util.Optional;

/**
 * Library of chemical elements
 */
public interface ElementLibrary {
    /**
     * Finds element by atomic number
     *
     * @param num atomic number
     * @return element with given atomic number or empty {@link Optional}
     */
    Optional<Element> findByAtomicNumber(int num);


    /**
     * Finds element by symbol
     *
     * @param symbol element's symbol
     * @return element with given symbol or empty {@link Optional}
     */
    Optional<Element> findBySymbol(String symbol);

    /**
     * Finds element by name
     *
     * @param name element's name
     * @return element with given name or empty {@link Optional}
     */
    Optional<Element> findByName(String name);

    /**
     * Finds elements discovered in given year
     *
     * @param year year of discovery
     * @return list of elements discovered in given year
     */
    List<Element> findByYear(int year);
}
