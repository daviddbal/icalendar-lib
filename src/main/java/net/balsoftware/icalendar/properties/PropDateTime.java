package net.balsoftware.icalendar.properties;

import net.balsoftware.icalendar.parameters.TimeZoneIdentifierParameter;
import net.balsoftware.icalendar.properties.component.misc.UnknownProperty;

/**
 * Interface for all Date and Date-Time properties
 * 
 * @author David Bal
 *
 * @param <T> - property Temporal value type (LocalDate, LocalDateTime or ZonedDateTime)
 * @see PropBaseDateTime
 * @see UnknownProperty
 */
public interface PropDateTime<T> extends VProperty<T>
{
    /*
     * default Time Zone methods are overridden by classes that require them
     */
    TimeZoneIdentifierParameter getTimeZoneIdentifier();
    void setTimeZoneIdentifier(TimeZoneIdentifierParameter timeZoneIdentifier);
}
