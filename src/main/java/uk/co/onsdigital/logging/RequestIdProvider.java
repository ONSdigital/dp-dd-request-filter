package uk.co.onsdigital.logging;

/**
 * Component that provides the id of the current request
 */
public interface RequestIdProvider {
    String getId();
}