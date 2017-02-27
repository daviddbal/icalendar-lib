package net.balsoftware.icalendar.properties.component.recurrence;

import java.time.temporal.Temporal;
import java.util.Set;

import net.balsoftware.icalendar.components.DaylightSavingTime;
import net.balsoftware.icalendar.components.StandardTime;
import net.balsoftware.icalendar.components.VEvent;
import net.balsoftware.icalendar.components.VJournal;
import net.balsoftware.icalendar.components.VTodo;

/**
 * EXDATE
 * Exception Date-Times
 * RFC 5545 iCalendar 3.8.5.1, page 117.
 * 
 * This property defines the list of DATE-TIME exceptions for
 * recurring events, to-dos, journal entries, or time zone definitions.
 * 
 * @author David Bal
 * @see VEvent
 * @see VTodo
 * @see VJournal
 * @see DaylightSavingTime
 * @see StandardTime
 */
public class ExceptionDates extends PropertyBaseRecurrence<ExceptionDates>
{       
    @SuppressWarnings("unchecked")
    public ExceptionDates(Temporal...temporals)
    {
        super(temporals);
    }

    public ExceptionDates(ExceptionDates source)
    {
        super(source);
    }
    
    public ExceptionDates(Set<Temporal> value)
    {
        super(value);
    }
    
    public ExceptionDates()
    {
        super();
    }

    /** Parse string to Temporal.  Not type safe.  Implementation must
     * ensure parameterized type is the same as date-time represented by String parameter */
    public static ExceptionDates parse(String value)
    {
        ExceptionDates property = new ExceptionDates();
        property.parseContent(value);
        return property;
    }
    
    /** Parse string with Temporal class Exceptions provided as parameter */
    public static  ExceptionDates parse(Class<? extends Temporal> clazz, String value)
    {
        ExceptionDates property = new ExceptionDates();
        property.parseContent(value);
        clazz.cast(property.getValue().iterator().next()); // class check
        return property;
    }
}
