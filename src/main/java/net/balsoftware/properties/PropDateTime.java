package net.balsoftware.properties;

import net.balsoftware.parameters.TimeZoneIdentifierParameter;
import net.balsoftware.properties.component.misc.UnknownProperty;

/**
 * Interface for all Date and Date-Time properties
 * 
 * @author David Bal
 *
 * @param <T> - property Temporal value type (LocalDate, LocalDateTime or ZonedDateTime)
 * @see PropBaseDateTime
 * @see UnknownProperty
 */
public interface PropDateTime<T> extends Property<T>
{
    /*
     * default Time Zone methods are overridden by classes that require them
     */
    TimeZoneIdentifierParameter getTimeZoneIdentifier();
    void setTimeZoneIdentifier(TimeZoneIdentifierParameter timeZoneIdentifier);
}
