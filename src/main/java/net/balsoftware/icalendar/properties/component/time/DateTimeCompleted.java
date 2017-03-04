package net.balsoftware.icalendar.properties.component.time;

import java.lang.reflect.Method;
import java.time.ZonedDateTime;
import java.util.Map;

import net.balsoftware.icalendar.VChild;
import net.balsoftware.icalendar.components.VTodo;
import net.balsoftware.icalendar.properties.PropBaseUTC;
import net.balsoftware.icalendar.utilities.ICalendarUtilities;

/**
 * COMPLETED
 * Date-Time Completed
 * RFC 5545, 3.8.2.1, page 94
 * 
 * This property defines the date and time that a to-do was actually completed.
 * The value MUST be specified as a date with UTC time.
 * 
 * Example:
 * COMPLETED:19960401T150000Z
 * 
 * @author David Bal
 *
 * The property can be specified in following components:
 * @see VTodo
 */
public class DateTimeCompleted extends PropBaseUTC<DateTimeCompleted>
{
	public static final Map<Class<?>, Method> SETTERS = ICalendarUtilities.collectSetterMap(DateTimeCompleted.class);
    protected Method getSetter(VChild newChild)
    {
    	return SETTERS.get(newChild.getClass());
    }
    
    public DateTimeCompleted(ZonedDateTime temporal)
    {
        super(temporal);
    }

//    public DateTimeCompleted(CharSequence contentLine)
//    {
//        super(contentLine);
//    }
    
    public DateTimeCompleted(DateTimeCompleted source)
    {
        super(source);
    }
    
    public DateTimeCompleted()
    {
        super();
    }

    public static DateTimeCompleted parse(String value)
    {
        DateTimeCompleted property = new DateTimeCompleted();
        property.parseContent(value);
        return property;
    }
}
