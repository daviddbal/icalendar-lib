package net.balsoftware.icalendar.properties.component.change;

import java.time.ZonedDateTime;

import net.balsoftware.icalendar.components.DaylightSavingTime;
import net.balsoftware.icalendar.components.StandardTime;
import net.balsoftware.icalendar.components.VEvent;
import net.balsoftware.icalendar.components.VJournal;
import net.balsoftware.icalendar.components.VTodo;
import net.balsoftware.icalendar.properties.PropBaseUTC;
import net.balsoftware.icalendar.properties.component.change.LastModified;

/**
 * LAST-MODIFIED
 * RFC 5545, 3.8.7.3, page 138
 * 
 * This property specifies the date and time that the
 * information associated with the calendar component was last
 * revised in the calendar store.
 *
 * Note: This is analogous to the modification date and time for a
 * file in the file system.
 * 
 * The value MUST be specified as a date with UTC time.
 * 
 * Example:
 * LAST-MODIFIED:19960817T133000Z
 * 
 * @author David Bal
 * 
 * The property can be specified in following components:
 * @see VEvent
 * @see VTodo
 * @see VJournal
 * @see StandardTime
 * @see DaylightSavingTime
 */
public class LastModified extends PropBaseUTC<LastModified>
{    
    public LastModified(ZonedDateTime temporal)
    {
        super(temporal);
    }
    
    public LastModified(LastModified source)
    {
        super(source);
    }

    public LastModified()
    {
        super();
    }

    public static LastModified parse(String content)
    {
    	return LastModified.parse(new LastModified(), content);
    }
}
