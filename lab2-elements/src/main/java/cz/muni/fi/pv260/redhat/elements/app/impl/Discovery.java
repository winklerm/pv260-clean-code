package cz.muni.fi.pv260.redhat.elements.app.impl;

import java.util.Objects;

/**
 * Representation of a scientific discovery.
 */
public class Discovery {
    private final String name;
    private final String discovery_description;
    private final String inventor;
    private final int year_discovered;

    public Discovery(String name, String discovery_description, String inventor, int year_discovered) {
        this.name = name;
        this.discovery_description = discovery_description;
        this.inventor = inventor;
        this.year_discovered = year_discovered;
    }

    public String name() {
        return name;
    }

    public String discovery_description() {
        return discovery_description;
    }

    public String inventor() {
        return inventor;
    }

    public int year_discovered() {
        return year_discovered;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Discovery discovery = (Discovery) o;
        return year_discovered == discovery.year_discovered && Objects.equals(name, discovery.name) && Objects.equals(discovery_description, discovery.discovery_description) && Objects.equals(inventor, discovery.inventor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, discovery_description, inventor, year_discovered);
    }
}