package net.balsoftware.properties.component.relationship;

import java.net.URI;

import net.balsoftware.components.VEvent;
import net.balsoftware.components.VFreeBusy;
import net.balsoftware.components.VJournal;
import net.balsoftware.components.VTodo;
import net.balsoftware.properties.PropertyBase;

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
public class UniformResourceLocator extends PropertyBase<URI,UniformResourceLocator>
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
