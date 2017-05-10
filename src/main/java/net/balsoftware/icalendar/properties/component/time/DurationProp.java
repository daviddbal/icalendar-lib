package net.balsoftware.icalendar.properties.component.time;

import java.time.temporal.TemporalAmount;

import net.balsoftware.icalendar.components.VAlarm;
import net.balsoftware.icalendar.components.VEvent;
import net.balsoftware.icalendar.components.VTodo;
import net.balsoftware.icalendar.properties.PropBaseLanguage;
import net.balsoftware.icalendar.properties.component.time.DurationProp;

/**
 * DURATION
 * RFC 5545, 3.8.2.5, page 99
 * 
 * This property specifies a positive duration of time.
 * 
 * When the "DURATION" property relates to a
 * "DTSTART" property that is specified as a DATE value, then the
 * "DURATION" property MUST be specified as a "dur-day" or "dur-week" value.
 * 
 * Based on ISO.8601.2004 (but Y and M for years and months is not supported by iCalendar)
 * 
 * Examples:
 * DURATION:PT1H0M0S
 * DURATION:PT15M
 * DURATION:P1D
 * 
 * @author David Bal
 * 
 * The property can be specified in following components:
 * @see VEvent
 * @see VTodo
 * @see VAlarm
 */
public class DurationProp extends PropBaseLanguage<TemporalAmount, DurationProp>
{
    public DurationProp(TemporalAmount value)
    {
        super(value);
    }

    public DurationProp(DurationProp source)
    {
        super(source);
    }
    
    public DurationProp()
    {
        super();
    }

    public static DurationProp parse(String content)
    {
    	return DurationProp.parse(new DurationProp(), content);
    }
}
