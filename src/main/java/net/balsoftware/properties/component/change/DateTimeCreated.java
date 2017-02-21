package net.balsoftware.properties.component.change;

import java.time.ZonedDateTime;

import net.balsoftware.components.VEvent;
import net.balsoftware.components.VJournal;
import net.balsoftware.components.VTodo;
import net.balsoftware.properties.PropBaseUTC;

/**
 * CREATED
 * Date-Time Created
 * RFC 5545, 3.8.7.1, page 136
 * 
 * This property specifies the date and time that the calendar information
 * was created by the calendar user agent in the calendar store.
 * 
 * The value MUST be specified as a date with UTC time.
 * 
 * Example:
 * CREATED:19960329T133000Z
 * 
 * @author David Bal
 * 
 * The property can be specified in following components:
 * @see VEvent
 * @see VTodo
 * @see VJournal
 */
public class DateTimeCreated extends PropBaseUTC<DateTimeCreated>
{
    public DateTimeCreated(ZonedDateTime temporal)
    {
        super(temporal);
    }
    
    public DateTimeCreated(DateTimeCreated source)
    {
        super(source);
    }
    
    public DateTimeCreated()
    {
        super();
    }

    public static DateTimeCreated parse(String value)
    {
        DateTimeCreated property = new DateTimeCreated();
        property.parseContent(value);
        return property;
    }
}
