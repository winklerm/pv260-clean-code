package cz.muni.fi.pv260.redhat.elements.app.output;


import cz.muni.fi.pv260.redhat.elements.app.impl.Element;

import java.util.Collection;

/**
 * Formatting of Element instances as string
 */
public interface ElementFormatter {

    /**
     * Formats element as string
     *
     * @param element formatted element
     * @return String representation of given element
     */
    String format(Element element);

    /**
     * Formats collection of elements as string
     *
     * @param elements formatted elements
     * @return String representation of given elements
     */
    String format(Collection<Element> elements);
}
