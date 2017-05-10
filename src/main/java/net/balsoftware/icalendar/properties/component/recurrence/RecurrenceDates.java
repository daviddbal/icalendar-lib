package net.balsoftware.icalendar.properties.component.recurrence;

import java.time.temporal.Temporal;
import java.util.Set;

import net.balsoftware.icalendar.components.DaylightSavingTime;
import net.balsoftware.icalendar.components.StandardTime;
import net.balsoftware.icalendar.components.VEvent;
import net.balsoftware.icalendar.components.VJournal;
import net.balsoftware.icalendar.components.VTodo;
import net.balsoftware.icalendar.properties.component.recurrence.PropertyBaseRecurrence;
import net.balsoftware.icalendar.properties.component.recurrence.RecurrenceDates;

/**
 * RDATE
 * Recurrence Date-Times
 * RFC 5545 iCalendar 3.8.5.2, page 120.
 * 
 * This property defines the list of DATE-TIME values for
 * recurring events, to-dos, journal entries, or time zone definitions.
 * 
 * NOTE: DOESN'T CURRENTLY SUPPORT PERIOD VALUE TYPE
 * 
 * @author David Bal
 * @see VEvent
 * @see VTodo
 * @see VJournal
 * @see DaylightSavingTime
 * @see StandardTime
 */
public class RecurrenceDates extends PropertyBaseRecurrence<RecurrenceDates>
{       
    @SuppressWarnings("unchecked")
    public RecurrenceDates(Temporal...temporals)
    {
        super(temporals);
    }
    
    public RecurrenceDates(RecurrenceDates source)
    {
        super(source);
    }
    
    public RecurrenceDates(Set<Temporal> value)
    {
        super(value);
    }
    
    public RecurrenceDates()
    {
        super();
    }

    /** Parse string to Temporal.  Not type safe.  Implementation must
     * ensure parameterized type is the same as date-time represented by String parameter */
    public static RecurrenceDates parse(String content)
    {
    	return RecurrenceDates.parse(new RecurrenceDates(), content);
    }
    
    /** Parse string with Temporal class Exceptions provided as parameter */
    public static RecurrenceDates parse(Class<? extends Temporal> clazz, String content)
    {
    	RecurrenceDates property = RecurrenceDates.parse(new RecurrenceDates(), content);
        clazz.cast(property.getValue().iterator().next()); // class check
        return property;
    }
}
