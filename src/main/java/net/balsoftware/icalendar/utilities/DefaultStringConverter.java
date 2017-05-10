package net.balsoftware.icalendar.utilities;

import net.balsoftware.icalendar.utilities.StringConverter;

/**
 * <p>{@link StringConverter} implementation for {@link String} values.</p>
 * Copied JavaFx implementation for use in Java EE
 */
public class DefaultStringConverter implements StringConverter<String> {
    /** {@inheritDoc} */
    @Override public String toString(String value) {
        return (value != null) ? value : "";
    }

    /** {@inheritDoc} */
    @Override public String fromString(String value) {
        return value;
    }
}
