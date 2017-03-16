package net.balsoftware.icalendar.properties.component.relationship;

import java.net.URI;

import net.balsoftware.icalendar.components.VEvent;
import net.balsoftware.icalendar.components.VFreeBusy;
import net.balsoftware.icalendar.components.VJournal;
import net.balsoftware.icalendar.components.VTodo;
import net.balsoftware.icalendar.properties.VPropertyBase;

/**
 * DURATION
 * RFC 5545, 3.8.4.6, page 116
 * 
 * This property defines a Uniform Resource Locator (URL) associated with the iCalendar object.
 *
 * Examples:
 * URL:http://example.com/pub/calendars/jsmith/mytime.ics
 * 
 * @author David Bal
 * 
 * The property can be specified in following components:
 * @see VEvent
 * @see VTodo
 * @see VJournal
 * @see VFreeBusy
 */
public class UniformResourceLocator extends VPropertyBase<URI,UniformResourceLocator>
{    
    public UniformResourceLocator(URI value)
    {
        super(value);
    }
    
    public UniformResourceLocator(UniformResourceLocator source)
    {
        super(source);
    }
    
    public UniformResourceLocator()
    {
        super();
    }

    public static UniformResourceLocator parse(String propertyContent)
    {
        UniformResourceLocator property = new UniformResourceLocator();
        property.parseContent(propertyContent);
        return property;
    }
}
