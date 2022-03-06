package cz.muni.fi.pv260.redhat.elements.app.impl;

import java.util.Objects;

/**
 * Representation of element
 */
public final class Element extends Discovery {

    private final int atomic_number;
    private final String symbol;
    private final String standard_state;
    private final String group_block;

    public Element(int atomic_number, String symbol, String name, String standard_date, String group_block, int year_discovered
    ) {
        super(name, null, null, year_discovered);
        this.atomic_number = atomic_number;
        this.symbol = symbol;
        this.standard_state = standard_date;
        this.group_block = group_block;
    }

    public int atomic_number() {
        return this.atomic_number;
    }

    public String symbol() {
        return this.symbol;
    }

    public String standard_date() {
        return this.standard_state;
    }

    public String group_block() {
        return this.group_block;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Element element = (Element) o;
        return atomic_number == element.atomic_number && Objects.equals(symbol, element.symbol) && Objects.equals(standard_state, element.standard_state) && Objects.equals(group_block, element.group_block);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), atomic_number, symbol, standard_state, group_block);
    }
}
